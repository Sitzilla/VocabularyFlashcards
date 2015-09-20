package com.evansitzes.vocabularyflashcards.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.model.Flashcard;
import com.evansitzes.vocabularyflashcards.model.KoreanWords;

import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.getArrayList;
import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.saveArrayList;

/**
 * Created by evan on 9/20/15.
 */
public class FlashcardActivity extends Activity {

    private Flashcard flashcard = new Flashcard();
    private TextView question;
    private TextView answer;
    private Button showAnswer;
    private Button nextWord;
    private Button deleteWord;
    private KoreanWords koreanWords = new KoreanWords();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        Intent intent = getIntent();
        question = (TextView)findViewById(R.id.questionTextView);
        answer = (TextView)findViewById(R.id.answerTextView);
        showAnswer = (Button)findViewById(R.id.showAnswerButton);
        nextWord = (Button)findViewById(R.id.nextWordButton);
        deleteWord = (Button)findViewById(R.id.deleteWordButton);

        if (savedFileExists()) {
            koreanWords.setWordList(getArrayList(this, "wordFile"));
        } else {
            koreanWords.populateInitialWordlist();
        }


        loadFlashcard();
    }

    private void loadFlashcard() {
        final String currentWord = koreanWords.getRandomKoreanWord();
        question.setText(currentWord);
        answer.setText(koreanWords.getEnglishFromKorean(currentWord));


        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setTextColor(Color.BLACK);
            }
        });

        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setTextColor(Color.WHITE);
                String currentWord = koreanWords.getRandomKoreanWord();
                question.setText(currentWord);
                answer.setText(koreanWords.getEnglishFromKorean(currentWord));

            }
        });

        deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setTextColor(Color.WHITE);
                koreanWords.removeKoreanWord((String) question.getText());
                String currentWord = koreanWords.getRandomKoreanWord();
                question.setText(currentWord);
                answer.setText(koreanWords.getEnglishFromKorean(currentWord));
                saveFlashcard();
            }
        });

    }

    private void saveFlashcard() {
        saveArrayList(this, "wordFile", koreanWords.getWordList());
    }

    private boolean savedFileExists() {
        return true;
    }

}
