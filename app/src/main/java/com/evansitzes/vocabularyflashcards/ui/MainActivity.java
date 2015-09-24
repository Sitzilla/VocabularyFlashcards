package com.evansitzes.vocabularyflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.evansitzes.vocabularyflashcards.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton koreanButton;
    private ImageButton japaneseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        koreanButton = (ImageButton)findViewById(R.id.koreanButton);
        japaneseButton = (ImageButton)findViewById(R.id.japaneseButton);

        japaneseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sorry, no Japanese vocabulary words yet", Toast.LENGTH_SHORT).show();
            }
        });

        koreanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKoreanFlashcards();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void startKoreanFlashcards() {
        Intent intent = new Intent(this, LevelSelectionActivity.class);
        startActivity(intent);
    }


}
