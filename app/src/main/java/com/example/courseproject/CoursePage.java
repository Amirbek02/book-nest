package com.example.courseproject;

import static android.util.Log.v;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseproject.model.Course;
import com.example.courseproject.model.CourseApi;
import com.example.courseproject.model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoursePage extends AppCompatActivity {
    TextView back;
    TextView textView, textView4;

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://c6a8e086333c6b13.mokky.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static CourseApi courseApi = retrofit.create(CourseApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        textView = findViewById(R.id.textView2);
        textView4 =findViewById(R.id.textView4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursePage.this,AboutUsActivity.class );
                startActivity(intent);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursePage.this,ContactsActivity.class );
                startActivity(intent);
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button btnLink = findViewById(R.id.btn_link);
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Укажите URL-адрес
                String url = getIntent().getStringExtra("courseLink");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ConstraintLayout courseBg = findViewById(R.id.coursePageBg);
        ImageView courseImage = findViewById(R.id.coursePageImg);
        TextView courseTitle = findViewById(R.id.coursePageTitle);
        TextView courseDate = findViewById(R.id.coursePageDate);
        TextView courseText = findViewById(R.id.coursePageText);
        TextView courseAuthor = findViewById(R.id.coursePageAuthor);  // Новое поле для автора
        TextView coursePublisher = findViewById(R.id.coursePagePublisher);  // Новое поле для издательства
        TextView coursePages = findViewById(R.id.coursePagePages);  // Новое поле для количества страниц
        TextView courseLanguage = findViewById(R.id.coursePageLanguage);  // Новое поле для языка
        TextView courseForm = findViewById(R.id.coursePageFormat);
        // Новое поле для формы

        // Установка данных из Intent
        courseBg.setBackgroundColor(getIntent().getIntExtra("courseBg", 0));
        courseImage.setImageResource(getIntent().getIntExtra("courseImg", 0));

        courseTitle.setText(getIntent().getStringExtra("courseTitle"));
        courseDate.setText(getIntent().getStringExtra("courseDate"));
        courseText.setText(getIntent().getStringExtra("courseText"));
        courseAuthor.setText(getIntent().getStringExtra("courseAuthor"));  // Установка автора
        coursePublisher.setText(getIntent().getStringExtra("coursePublisher"));  // Установка издательства
        coursePages.setText(String.valueOf(getIntent().getIntExtra("coursePages", 0)));  // Установка страниц
        courseLanguage.setText(getIntent().getStringExtra("courseLanguage"));  // Установка языка
        courseForm.setText(getIntent().getStringExtra("courseForm"));  // Установка формы
    }

    public void addToCard(View view) {
        int item_id = getIntent().getIntExtra("courseId", 0);
        String courseImage = getIntent().getStringExtra("courseImg");
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseBg = getIntent().getStringExtra("courseBg");
        String courseDate = getIntent().getStringExtra("courseDate");
        String courseText = getIntent().getStringExtra("courseText");
        String courseAuthor = getIntent().getStringExtra("courseAuthor");  // Получение автора
        String coursePublisher = getIntent().getStringExtra("coursePublisher");  // Получение издательства
        int coursePages = getIntent().getIntExtra("coursePages", 0);  // Получение количества страниц
        String courseLanguage = getIntent().getStringExtra("courseLanguage");  // Получение языка
        String courseForm = getIntent().getStringExtra("courseForm");
        String courseLink = getIntent().getStringExtra("courseLink");

        if (Order.items_id.contains(item_id)) {
            Toast.makeText(this, "Курс уже добавлен!", Toast.LENGTH_SHORT).show();
            Log.d("Amirbek", "Course already added: " + item_id);
            return;
        }




        Call<Course> checkCall = courseApi.getOrdersById(item_id);
        checkCall.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CoursePage.this, "Курс уже добавлен!", Toast.LENGTH_SHORT).show();
                    Log.d("Amirbek", "Course already exists on server: " + item_id);
                } else {
                    Order.items_id.add(item_id);
                    Course addPostCourse = new Course(item_id, courseImage, courseTitle, courseDate, courseBg, courseText,
                            2, courseAuthor, coursePages, courseLanguage, courseForm, coursePublisher, courseLink);

                    Call<Void> postCall = courseApi.postCourse(addPostCourse);
                    postCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("Amirbek", "Post request successful");
                            } else {
                                Log.e("Error", "Request failed: " + response.code() + " - " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Error", "Request failed: " + t.getMessage());
                        }
                    });
                    Toast.makeText(CoursePage.this, "Добавлено! :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                Log.e("Error", "Check course request failed: " + t.getMessage());
            }
        });
    }
}
