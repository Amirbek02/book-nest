package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.courseproject.adapter.CategoryAdapter;
import com.example.courseproject.adapter.CourseAdapter;
import com.example.courseproject.model.Category;
import com.example.courseproject.model.Course;
import com.example.courseproject.model.CourseApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
RecyclerView categoryRecycler, courseRecycler;
CategoryAdapter categoryAdapter;
TextView textView, textView4;
 static CourseAdapter courseAdapter;
    static List<Course> courseList = new ArrayList<>();
    static List<Course> fullcourseList = new ArrayList<>();

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://c6a8e086333c6b13.mokky.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static CourseApi courseApi = retrofit.create(CourseApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView2);
        textView4 =findViewById(R.id.textView4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutUsActivity.class );
                startActivity(intent);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ContactsActivity.class );
                startActivity(intent);
            }
        });


        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Игры"));
        categoryList.add(new Category(2, "Сайты"));
        categoryList.add(new Category(3, "Языки"));
        categoryList.add(new Category(4, "Прочее"));

        setCategoryRecycler(categoryList);
        loadCourses();
    }
private void loadCourses() {
    Call<List<Course>> call = courseApi.getCourses();
    Log.d("Amirbek", call.toString());

    call.enqueue(new Callback<List<Course>>() {
        @Override
        public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
            if(response.isSuccessful() && response.body() != null) {
                List<Course> courses = response.body();
                courseList.addAll(courses);
                Log.d("AmirbekL", courses.toString());
                fullcourseList.addAll(courseList);
                runOnUiThread(() -> setCourseRecycler(courseList));
            } else {
                Log.e("AmirbekL", "Response not successful or body is null");
            }
        }

        @Override
        public void onFailure(Call<List<Course>> call, Throwable t) {
            Log.e("AmirbekL", "Network request failed", t);
        }
    });
}

//
//    @Override
//            public void onFailure(Call<List<Course>> call, Throwable t) {
//                // Обработка ошибки здесь
//            }
//        });
//    }
    public void openShopCard(View view) {
        Intent intent = new Intent(this, OrderPage.class);
        startActivity(intent);

    }

    private void setCourseRecycler(List<Course> courseList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        courseRecycler = findViewById(R.id.courseRecycler);
        courseRecycler.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(this, courseList);
        courseRecycler.setAdapter(courseAdapter);
    }

    private void setCategoryRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    public static  void showCoursesByCategory(int category) {
        courseList.clear();
        courseList.addAll(fullcourseList);
        List<Course> filterCourses = new ArrayList<>();

        for(Course c : courseList) {
            if(c.getCategory() == category) {
                filterCourses.add(c);
            }
        }

        courseList.clear();
        courseList.addAll(filterCourses);

        courseAdapter.notifyDataSetChanged();
    }
}