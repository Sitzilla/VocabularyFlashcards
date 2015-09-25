package com.evansitzes.vocabularyflashcards.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class FlashcardsActivity extends AppCompatActivity {

    private Flashcard wordlist;
    private TextView question;
    private TextView answer;
    private TextView remaining;
    private int remainingCount;
    private Button showAnswer;
    private Button nextWord;
    private Button deleteWord;
    private Button rateWord;
    private Button back;
    private String level;

    @Override
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

        loadPage();
        listenForClickEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flashcards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadPage();
                return true;
            case R.id.action_reset_wordlist:
                resetWordlist();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPage() {
        question = (TextView) findViewById(R.id.questionTextView);
        answer = (TextView) findViewById(R.id.answerTextView);
        remaining = (TextView) findViewById(R.id.remainingWordsTtextView);
        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        nextWord = (Button) findViewById(R.id.nextWordButton);
        deleteWord = (Button) findViewById(R.id.deleteWordButton);
        rateWord = (Button) findViewById(R.id.rateButtonFlashcards);
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
    }

    private void listenForClickEvents() {


        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setTextColor(Color.parseColor("#efb200"));
            }
        });

        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWord();
            }
        });
        deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWord();
            }
        });

        rateWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateWord();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, LevelSelectionActivity.class);
                startActivity(intent);
            }
        });


    }

    private void deleteWord() {
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

    private void nextWord() {
        answer.setTextColor(Color.parseColor("#571935"));
        String currentWord = wordlist.getRandomForeignWord();
        question.setText(currentWord);
        answer.setText(wordlist.getEnglishFromForeign(currentWord));

    }

    private void rateWord() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // TODO Can probably be refactored

                        nextWord();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        nextWord();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you know the word").setPositiveButton("Got it", dialogClickListener)
                .setNegativeButton("Not yet", dialogClickListener).show();

    }

    private void resetWordlist() {
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
