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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

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
            selectionButtonTwo.setVisibility(View.GONE);
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
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.BASIC_KOREAN);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadJapaneseSelection() {

        selectionButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.READING_JAPANESE);
                startActivity(intent);
            }
        });

        selectionButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.NOUNS_JAPANESE);
                startActivity(intent);
            }
        });

        selectionButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.KANJI_JAPANESE);
                startActivity(intent);
            }
        });

        selectionButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.ADJECTIVES_JAPANESE);
                startActivity(intent);
            }
        });

        selectionButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.SHINBUN_JAPANESE);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loadChineseSelection() {

        selectionButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", FlashcardType.BASIC_CHINESE);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }


}
