package com.evansitzes.vocabularyflashcards.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.model.Flashcard;
import com.evansitzes.vocabularyflashcards.model.KoreanWords;

import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.doesFileExist;
import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.getHashmap;
import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.saveHashmap;


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
    private Button resetWord;
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
        resetWord = (Button)findViewById(R.id.resetWordsButton);


        if (savedFileExists()) {
            koreanWords.setWordList(getHashmap(this, "wordFile"));
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
                // TODO This can be refactored out to a new method
                answer.setTextColor(Color.WHITE);
                String currentWord = koreanWords.getRandomKoreanWord();
                question.setText(currentWord);
                answer.setText(koreanWords.getEnglishFromKorean(currentWord));

            }
        });

        // TODO Add 'winning' scenario
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

        resetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSure();
//                // TODO Add an "are you sure?" question box
//                koreanWords.populateInitialWordlist();
//                answer.setTextColor(Color.WHITE);
//                String currentWord = koreanWords.getRandomKoreanWord();
//                question.setText(currentWord);
//                answer.setText(koreanWords.getEnglishFromKorean(currentWord));
            }
        });


    }

    private void areYouSure() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // TODO Can probably be refactored
                        koreanWords.populateInitialWordlist();
                        answer.setTextColor(Color.WHITE);
                        String currentWord = koreanWords.getRandomKoreanWord();
                        question.setText(currentWord);
                        answer.setText(koreanWords.getEnglishFromKorean(currentWord));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void saveFlashcard() {
        saveHashmap(this, "wordFile", koreanWords.getWordList());
    }

    private boolean savedFileExists() {
        return doesFileExist(this, "wordFile");
    }

}
