package com.ai.caro;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(controller);
    }

    private void initialize() {
        controller = new GameController(this);
        BitmapManager.initialize(getApplication());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGame:
                controller.reset();
                break;
            case R.id.difficultyEasy:
                controller.setDifficulty(3);
                Toast.makeText(this, "Change diffculty to easy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.difficultyNormal:
                controller.setDifficulty(5);
                Toast.makeText(this, "Change diffculty to normal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.difficultyHard:
                controller.setDifficulty(7);
                Toast.makeText(this, "Change diffculty to hard", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
