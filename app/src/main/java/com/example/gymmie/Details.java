package com.example.gymmie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Details extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private EditText editWeight, editSets, editReps;
    private TextView txtExercise, txtLastProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        txtExercise = findViewById(R.id.txtExercise);
        txtLastProgress = findViewById(R.id.txtProgress);
        editWeight = findViewById(R.id.editWeight);
        editSets = findViewById(R.id.editSets);
        editReps = findViewById(R.id.editReps);
        Button logBtn = findViewById(R.id.logBtn);

        String exercisename = getIntent().getStringExtra("exercisename");

        txtExercise.setText(exercisename);

        loadLastProgress(exercisename);

        logBtn.setOnClickListener(v -> logProgress(exercisename));
    }

    private void loadLastProgress(String exercisename) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_PROGRESS,
                new String[]{DatabaseHelper.COLUMN_SETS, DatabaseHelper.COLUMN_REPS, DatabaseHelper.COLUMN_WEIGHT, DatabaseHelper.COLUMN_DATE},
                DatabaseHelper.COLUMN_EXERCISE + " = ?",
                new String[]{exercisename},
                null, null, DatabaseHelper.COLUMN_DATE + " DESC",
                "1");

        if (cursor != null && cursor.moveToFirst()) {
            int sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SETS));
            int reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPS));
            float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEIGHT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            cursor.close();

            String lastProgress = String.format("Last Progress: %d sets, %d reps, %.2f kg on %s", sets, reps, weight, date);
            txtLastProgress.setText(lastProgress);
        } else {
            txtLastProgress.setText("No previous progress found.");
        }
    }

    private void logProgress(String exercisename) {
        int sets = Integer.parseInt(editSets.getText().toString());
        int reps = Integer.parseInt(editReps.getText().toString());
        float weight = Float.parseFloat(editWeight.getText().toString());
        String date = "2024-07-26"; // For simplicity, using a hardcoded date. Replace with actual date handling.

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXERCISE, exercisename);
        values.put(DatabaseHelper.COLUMN_SETS, sets);
        values.put(DatabaseHelper.COLUMN_REPS, reps);
        values.put(DatabaseHelper.COLUMN_WEIGHT, weight);
        values.put(DatabaseHelper.COLUMN_DATE, date);

        long newRowId = db.insert(DatabaseHelper.TABLE_PROGRESS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Progress logged successfully!", Toast.LENGTH_SHORT).show();
            loadLastProgress(exercisename); // Refresh last progress
        } else {
            Toast.makeText(this, "Error logging progress.", Toast.LENGTH_SHORT).show();
        }
    }
}
