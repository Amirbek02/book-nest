package com.example.courseproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseproject.CoursePage;
import com.example.courseproject.R;
import com.example.courseproject.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    Context context;
    List<Course> courses;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseItems = LayoutInflater.from(context).inflate(R.layout.course_items, parent, false);
        return new CourseViewHolder(courseItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.courseTitle.setText(courses.get(position).getTitle());
        holder.courseBg.setCardBackgroundColor(Color.parseColor(courses.get(position).getColor()));
        holder.courseDate.setText(courses.get(position).getDate());
        int imageId = context.getResources().getIdentifier("ic_" + courses.get(position).getImg(), "drawable", context.getPackageName());
        holder.courseImage.setImageResource(imageId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CoursePage.class);

                // Создание анимации перехода для изображения
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        new Pair<View, String>(holder.courseImage, "courseImg"));

                // Передача данных в Intent
                intent.putExtra("courseBg", Color.parseColor(courses.get(position).getColor()));
                intent.putExtra("courseImg", imageId);
                intent.putExtra("courseTitle", courses.get(position).getTitle());
                intent.putExtra("courseDate", courses.get(position).getDate());
                intent.putExtra("courseText", courses.get(position).getText());
                intent.putExtra("courseId", courses.get(position).getId());

                // Передача дополнительных данных
                intent.putExtra("courseAuthor", courses.get(position).getAuthor());  // Автор
                intent.putExtra("coursePublisher", courses.get(position).getPublisher());  // Издательство
                intent.putExtra("coursePages", courses.get(position).getPages());  // Количество страниц
                intent.putExtra("courseLanguage", courses.get(position).getLanguage());  // Язык
                intent.putExtra("courseForm", courses.get(position).getForm());  // Форма книги

                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static final class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle, courseDate, courseLevel;
        ImageView courseImage;
        CardView courseBg;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.courseTitle);
            courseDate = itemView.findViewById(R.id.courseDate);
            courseLevel = itemView.findViewById(R.id.courseLevel);
            courseImage = itemView.findViewById(R.id.courseImg);
            courseBg = itemView.findViewById(R.id.courseBg);
        }
    }
}
