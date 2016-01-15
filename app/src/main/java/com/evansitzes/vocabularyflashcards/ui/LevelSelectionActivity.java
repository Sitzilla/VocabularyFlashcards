package com.evansitzes.vocabularyflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.helpers.FlashcardType;
import com.evansitzes.vocabularyflashcards.helpers.LanguageType;

public class LevelSelectionActivity extends AppCompatActivity {

    private Button selectionButtonOne;
    private Button selectionButtonTwo;
    private Button selectionButtonThree;
    private Button selectionButtonFour;
    private Button selectionButtonFive;
    private Button back;
    private String level;
    Intent flashcardsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        flashcardsIntent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);

        level = getIntent().getStringExtra("language");
        selectionButtonOne = (Button) findViewById(R.id.selectionButtonOne);
        selectionButtonTwo = (Button) findViewById(R.id.selectionButtonTwo);
        selectionButtonThree = (Button) findViewById(R.id.selectionButtonThree);
        selectionButtonFour = (Button) findViewById(R.id.selectionButtonFour);
        selectionButtonFive = (Button) findViewById(R.id.selectionButtonFive);
        back = (Button) findViewById(R.id.backButton);

        // Determines what level of flashcards to use
        if (level.equals(LanguageType.KOREAN)) {
            selectionButtonOne.setText("Basic Korean Words");
            selectionButtonTwo.setText("Japanese Korean Words");
            selectionButtonThree.setVisibility(View.GONE);
            selectionButtonFour.setVisibility(View.GONE);
            selectionButtonFive.setVisibility(View.GONE);
            loadKoreanSelection();
        } else if (level.equals(LanguageType.JAPANESE)) {
            selectionButtonOne.setText("JLPT N2 - Reading Vocab");
            selectionButtonTwo.setText("JLPT N2 - Nouns");
            selectionButtonThree.setText("JLPT N2 - Kanji");
            selectionButtonFour.setText("Basic Japanese Adjectives/Adverbs");
            selectionButtonFive.setText("Japanese 新聞　Vocabulary");
            loadJapaneseSelection();
        } else if (level.equals(LanguageType.CHINESE)) {
            selectionButtonOne.setText("Basic Chinese Words");
            selectionButtonTwo.setVisibility(View.GONE);
            selectionButtonThree.setVisibility(View.GONE);
            selectionButtonFour.setVisibility(View.GONE);
            selectionButtonFive.setVisibility(View.GONE);
            loadChineseSelection();
        }
    }

    private void loadKoreanSelection() {

        selectionButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.BASIC_KOREAN);
            }
        });

        selectionButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.JAPANESE_KOREAN);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void loadJapaneseSelection() {

        selectionButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.READING_JAPANESE);
            }
        });

        selectionButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.NOUNS_JAPANESE);
            }
        });

        selectionButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.KANJI_JAPANESE);
            }
        });

        selectionButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.ADJECTIVES_JAPANESE);
            }
        });

        selectionButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.SHINBUN_JAPANESE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void loadChineseSelection() {

        selectionButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlashcardsActivity(FlashcardType.BASIC_CHINESE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


    }

    private void startFlashcardsActivity(String flashcardType) {
        flashcardsIntent.putExtra("level", flashcardType);
        startActivity(flashcardsIntent);
    }

    private void goBack() {
        Intent intent = new Intent(LevelSelectionActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
