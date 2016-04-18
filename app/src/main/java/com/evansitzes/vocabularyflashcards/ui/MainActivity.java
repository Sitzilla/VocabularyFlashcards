package com.evansitzes.vocabularyflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.helpers.LanguageType;

public class MainActivity extends AppCompatActivity {

    private ImageButton koreanButton;
    private ImageButton japaneseButton;
    private ImageButton chineseButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        koreanButton = (ImageButton)findViewById(R.id.koreanButton);
        japaneseButton = (ImageButton)findViewById(R.id.japaneseButton);
        chineseButton = (ImageButton)findViewById(R.id.chineseButton);

        koreanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKoreanFlashcards();
            }
        });
        japaneseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJapaneseFlashcards();            }
        });
        chineseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChineseFlashcards();            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void startKoreanFlashcards() {
        Intent intent = new Intent(this, LevelSelectionActivityTest.class);
        intent.putExtra("language", LanguageType.KOREAN);
        startActivity(intent);
    }

    private void startJapaneseFlashcards() {
        Intent intent = new Intent(this, LevelSelectionActivityTest.class);
        intent.putExtra("language", LanguageType.JAPANESE);
        startActivity(intent);
    }

    private void startChineseFlashcards() {
        Intent intent = new Intent(this, LevelSelectionActivityTest.class);
        intent.putExtra("language", LanguageType.CHINESE);
        startActivity(intent);
    }
}
