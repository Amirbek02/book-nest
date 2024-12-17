package com.example.courseproject.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CourseApi {
    @GET("items")
    Call<List<Course>> getCourses();
    @GET("orders/{id}")
    Call<Course> getOrdersById(@Path("id") int id);
    @POST("orders")
    Call<Void>postCourse(@Body Course course);
    @GET("orders")
    Call<List<Course>> getOrderCourses();
    @DELETE("orders/{courseId}")
    Call<Void> deleteOrder(@Path("courseId") int courseId);

    @DELETE("orders/{id}")
    Call<Void> deleteCourse(@Path("id") int courseId);
}
