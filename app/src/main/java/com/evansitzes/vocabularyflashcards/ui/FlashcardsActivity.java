package com.evansitzes.vocabularyflashcards.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.model.ApiFlashcard;
import com.evansitzes.vocabularyflashcards.model.Flashcard;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSink;

public class FlashcardsActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final String CATEGORIES_ENDPOINT = "https://vocabularyterms.herokuapp.com/{language}?category={category}";
    private static final String INCREASE_ENDPOINT = "https://vocabularyterms.herokuapp.com/{language}/{id}/increase";

    private Flashcard wordlist;
    private TextView question;
    private TextView answer;
    private TextView remaining;
    private int remainingCount;
    private Button showAnswer;
    private Button nextWord;
    private Button deleteWord;
    private Button back;
    private Button increase;
    private String category;
    private String language;
    ActionBar actionBar;
    List<JSONObject> jsonResponseMain = new ArrayList<JSONObject>();
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_flashcards);
        actionBar = getActionBar();
        category = getIntent().getStringExtra("category");
        language = getIntent().getStringExtra("language");
        this.wordlist = new ApiFlashcard();

        getWordList();
    }

    private void getWordList() {
        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(CATEGORIES_ENDPOINT.replace("{language}", language).replace("{category}", category))
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final JSONArray jsonData;
                        try {
                            jsonData = new JSONObject(response.body().string()).getJSONArray("words");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < jsonData.length(); i++) {
                                    try {
                                        jsonResponseMain.add(jsonData.getJSONObject(i));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                }
                                loadPage();
                                listenForClickEvents();
                            }
                        });
                    } else {
                        alertUserAboutError();
                    }

                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable",
                    Toast.LENGTH_LONG).show();
        }
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
            case R.id.toggle_wordlist:
                toggleWordOrder();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPage() {
        question = (TextView) findViewById(R.id.questionTextView);
        answer = (TextView) findViewById(R.id.answerTextView);
        answer.setMovementMethod(new ScrollingMovementMethod());
        remaining = (TextView) findViewById(R.id.remainingWordsTtextView);
        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        nextWord = (Button) findViewById(R.id.nextWordButton);
        deleteWord = (Button) findViewById(R.id.deleteWordButton);
        increase = (Button) findViewById(R.id.increaseButton);
        back = (Button) findViewById(R.id.backButtonFlashcards);

        wordlist.setJsonResponseMain(jsonResponseMain);
        wordlist.populateInitialWordlist();
//        }

        // Set counter for number of words remaining in list
        setWordlistCounter();

        if (remainingCount == 0) {
            setEndOfListView();
        } else {
            final String currentWord = wordlist.getRandomForeignWord();
            question.setText(currentWord);
            answer.setText(wordlist.getCurrentEnglish());
            currentId = wordlist.getCurrentId();
        }
    }

    private void listenForClickEvents() {
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcurrentWord();
            }
        });
        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });
        deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWord();
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
                deleteWord();
            }
        });

//        toggleWordOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleWordOrder();
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void deleteWord() {
        // delete current word from list of vocab words
        wordlist.removeForeignWord();
        setWordlistCounter();

        // No more words in the list scenario
        if (remainingCount == 0) {
            setEndOfListView();
        }
        // Else delete current word from Hashmap of vocab words
        else {
            showNextWord();
        }
    }

    private void increase() {
        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(INCREASE_ENDPOINT.replace("{language}", language).replace("{id}", Integer.toString(currentId)))
                    .put(buildRequestBody())
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable",
                    Toast.LENGTH_LONG).show();
        }

    }

    private RequestBody buildRequestBody() {

        final RequestBody request = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };

        return request;
    }

    private void toggleWordOrder() {
        wordlist.reverseWordOrder();
    }

    private void showcurrentWord() {
        answer.setVisibility(View.VISIBLE);
    }

    private void showNextWord() {
        answer.setVisibility(View.INVISIBLE);
        String currentWord = wordlist.getRandomForeignWord();
        question.setText(currentWord);
        answer.setText(wordlist.getCurrentEnglish());
        currentId = wordlist.getCurrentId();
    }

    private void setWordlistCounter() {
        remainingCount = wordlist.getSize();
        remaining.setText(remainingCount + " Remaining Words");
    }

    private void setEndOfListView() {
        question.setText("Congratulations! No more words remaining.");
        answer.setVisibility(View.VISIBLE);
        answer.setText("Start a new list or press \"Reset Wordlist\" to use this list again");
        showAnswer.setEnabled(false);
        nextWord.setEnabled(false);
        deleteWord.setEnabled(false);
        increase.setEnabled(false);
    }

    private void resetWordlist() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    wordlist.populateInitialWordlist();
                    showAnswer.setEnabled(true);
                    nextWord.setEnabled(true);
                    deleteWord.setEnabled(true);
                    increase.setEnabled(true);
                    setWordlistCounter();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset your current wordlist to the default list?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        showNextWord();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        Log.v("ERROR WITH API CALL", "ERROR");
    }

}
