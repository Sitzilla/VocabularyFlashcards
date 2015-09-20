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
import com.evansitzes.vocabularyflashcards.model.BasicKoreanWords;
import com.evansitzes.vocabularyflashcards.model.Flashcard;
import com.evansitzes.vocabularyflashcards.model.IntermediateKoreanWords;

import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.doesFileExist;
import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.getHashmap;
import static com.evansitzes.vocabularyflashcards.utils.FileUtilities.saveHashmap;


/**
 * Created by evan on 9/20/15.
 */
public class FlashcardActivity extends Activity {

    private Flashcard wordlist;
    private TextView question;
    private TextView answer;
    private TextView remaining;
    private int remainingCount;
    private Button showAnswer;
    private Button nextWord;
    private Button deleteWord;
    private Button resetWord;
    private Button back;
    private String level;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        level = getIntent().getStringExtra("level");

        // Determines what level of flashcards to use
        if (level.equals("basicKorean")) {
            this.wordlist = new BasicKoreanWords();
        } else {
            this.wordlist = new IntermediateKoreanWords();
        }


        question = (TextView) findViewById(R.id.questionTextView);
        answer = (TextView) findViewById(R.id.answerTextView);
        remaining = (TextView) findViewById(R.id.remainingWordsTtextView);
        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        nextWord = (Button) findViewById(R.id.nextWordButton);
        deleteWord = (Button) findViewById(R.id.deleteWordButton);
        resetWord = (Button) findViewById(R.id.resetWordsButton);
        back = (Button) findViewById(R.id.backButtonFlashcards);


        if (savedFileExists()) {
            wordlist.setWordList(getHashmap(this, "wordFile" + level));
        } else {
            wordlist.populateInitialWordlist();
        }

        // Set counter for number of words remaining in list
        remainingCount = wordlist.getSize();
        remaining.setText(remainingCount + " Remaining Words");

        if (wordlist.getSize() == 0) {
            question.setText("Congratulations! No more words remaining.");
            answer.setTextColor(Color.parseColor("#efb200"));
            answer.setText("Start a new list or press \"RESET WORD LIST\" to use this list again");
            showAnswer.setEnabled(false);
            nextWord.setEnabled(false);
            deleteWord.setEnabled(false);
        } else {
            final String currentWord = wordlist.getRandomForeignWord();
            question.setText(currentWord);
            answer.setText(wordlist.getEnglishFromForeign(currentWord));
        }
        loadFlashcard();
    }

    private void loadFlashcard() {


        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setTextColor(Color.parseColor("#efb200"));
            }
        });

        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO This can be refactored out to a new method
                answer.setTextColor(Color.parseColor("#571935"));
                String currentWord = wordlist.getRandomForeignWord();
                question.setText(currentWord);
                answer.setText(wordlist.getEnglishFromForeign(currentWord));

            }
        });
        deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delete current word from Hashmap of vocab words
                answer.setTextColor(Color.parseColor("#571935"));
                wordlist.removeForeignWord((String) question.getText());
                remainingCount--;
                remaining.setText(remainingCount + " Remaining Words");
                // No more words in the list scenario
                if (wordlist.getSize() == 0) {
                    question.setText("Congratulations! No more words remaining.");
                    answer.setTextColor(Color.parseColor("#efb200"));
                    answer.setText("Start a new list or press \"RESET WORD LIST\" to use this list again");
                    showAnswer.setEnabled(false);
                    nextWord.setEnabled(false);
                    deleteWord.setEnabled(false);
                }
                // Else delete current word from Hashmap of vocab words
                else {
                    String currentWord = wordlist.getRandomForeignWord();
                    question.setText(currentWord);
                    answer.setText(wordlist.getEnglishFromForeign(currentWord));
                }
                saveFlashcard();
            }
        });

        resetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSure();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, LevelSelectionActivity.class);
                startActivity(intent);
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
                        wordlist.populateInitialWordlist();
                        answer.setTextColor(Color.parseColor("#571935"));
                        String currentWord = wordlist.getRandomForeignWord();
                        question.setText(currentWord);
                        answer.setText(wordlist.getEnglishFromForeign(currentWord));
                        showAnswer.setEnabled(true);
                        nextWord.setEnabled(true);
                        deleteWord.setEnabled(true);
                        remainingCount = wordlist.getSize();
                        remaining.setText(remainingCount + " Remaining Words");
                        saveFlashcard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset your current wordlist to the default list?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void saveFlashcard() {
        saveHashmap(this, "wordFile" + level, wordlist.getWordList());
    }

    private boolean savedFileExists() {
        return doesFileExist(this, "wordFile" + level);
    }

}
