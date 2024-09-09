package com.example.gymmie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenu extends AppCompatActivity {

    TextView name;
    private Button chest, middle, lower,lats, glutes,quads, hams, shoulders, triceps, biceps, calves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.txtWelcome);
        chest = findViewById(R.id.chestBtn);
        middle = findViewById(R.id.middleBtn);
        lats = findViewById(R.id.latsBtn);
        lower = findViewById(R.id.lowerBtn);
        glutes = findViewById(R.id.glutesBtn);
        hams = findViewById(R.id.hamstringsBtn);
        quads = findViewById(R.id.quadsBtn);
        shoulders = findViewById(R.id.shouldersBtn);
        biceps = findViewById(R.id.bicepsBtn);
        triceps = findViewById(R.id.tricepsBtn);
        calves = findViewById(R.id.calvesBtn);

        setupButton(chest, "chest");
        setupButton(middle, "middle_back");
        setupButton(lower, "lower_back");
        setupButton(lats, "lats");
        setupButton(glutes, "glutes");
        setupButton(quads, "quadriceps");
        setupButton(hams, "hamstrings");
        setupButton(shoulders, "shoulders");
        setupButton(biceps, "biceps");
        setupButton(triceps, "triceps");
        setupButton(calves, "calves");
        Intent intent = getIntent();
        String username = intent.getStringExtra("uname");
        if (username != null) {
            name.setText("Welcome, " + username + "!");
        }
    }
    private void setupButton(Button button, final String muscleGroup) {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, ExerciseSelection.class);
            intent.putExtra("MUSCLE_GROUP", muscleGroup);
            startActivity(intent);
        });
    }


}
