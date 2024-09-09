package com.example.gymmie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Exercise> data;
    private Context context;
    private DatabaseHelper dbHelper;

    public RecyclerAdapter(Context context, List<Exercise> data) {
        this.context = context;
        this.data = data;
        dbHelper = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, lastProgress;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.exercisename);
            lastProgress = view.findViewById(R.id.textView2); // Assuming this ID for last progress TextView
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Exercise exercise = data.get(position);
        holder.name.setText(exercise.getExerciseName());

        // Fetch and display last progress
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PROGRESS,
                new String[]{DatabaseHelper.COLUMN_SETS, DatabaseHelper.COLUMN_REPS, DatabaseHelper.COLUMN_WEIGHT, DatabaseHelper.COLUMN_DATE},
                DatabaseHelper.COLUMN_EXERCISE + " = ?",
                new String[]{exercise.getExerciseName()},
                null, null, DatabaseHelper.COLUMN_DATE + " DESC",
                "1");

        if (cursor != null && cursor.moveToFirst()) {
            int sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SETS));
            int reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPS));
            float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEIGHT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            cursor.close();

            String lastProgress = String.format("%d sets, %d reps, %.2f kg on %s", sets, reps, weight, date);
            holder.lastProgress.setText(lastProgress);
        } else {
            holder.lastProgress.setText("No previous progress found.");
        }

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("exercisename", exercise.getExerciseName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
