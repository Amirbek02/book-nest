package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.courseproject.model.Course;
import com.example.courseproject.model.CourseApi;
import com.example.courseproject.model.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderPage extends AppCompatActivity {
    List<String> coursesTitle = new ArrayList<>();
    List<Integer> courseIds = new ArrayList<>();
    TextView back;

    ArrayAdapter<String> adapter;
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://c6a8e086333c6b13.mokky.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static CourseApi courseApi = retrofit.create(CourseApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ListView orders_list = findViewById(R.id.orders_list);
        back = findViewById(R.id.back);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coursesTitle);
        orders_list.setAdapter(adapter);

        getOrders();

        back.setOnClickListener(view -> {
            Intent intent = new Intent(OrderPage.this, MainActivity.class);
            startActivity(intent);
        });

        orders_list.setOnItemClickListener((parent, view, position, id) -> {
            // When an item is clicked, delete it from the cart
            int courseId = courseIds.get(position);
            deleteCourse(courseId);
        });
    }

    public void getOrders() {
        Call<List<Course>> call = courseApi.getOrderCourses();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Course> courses = response.body();
                    for(Course c : courses) {
                        coursesTitle.add(c.getTitle());
                        courseIds.add(c.getId()); // Assuming Course class has getId() method
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                } else {
                    Log.e("Amirbek", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.e("Amirbek", "Network request failed", t);
            }
        });
    }

    public void deleteCourse(int courseId) {
        Call<Void> deleteCall = courseApi.deleteCourse(courseId);
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove the course from the local list and update the UI
                    int index = courseIds.indexOf(courseId);
                    if (index != -1) {
                        coursesTitle.remove(index);
                        courseIds.remove(index);
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                    }
                } else {
                    Log.e("Amirbek", "Failed to delete course");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Amirbek", "Failed to delete course", t);
            }
        });
    }
}

