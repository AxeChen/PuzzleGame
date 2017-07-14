package com.mg.axe.puzzlegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PuzzleLayout puzzleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        puzzleLayout = (PuzzleLayout) findViewById(R.id.puzzleLayout);
    }

    public void normalMode(View view) {
        puzzleLayout.changeMode(PuzzleLayout.GAME_MODE_NORMAL);
    }

    public void exchangeMode(View view) {
        puzzleLayout.changeMode(PuzzleLayout.GAME_MODE_EXCHANGE);
    }
}
