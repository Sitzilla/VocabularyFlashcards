package com.evansitzes.vocabularyflashcards.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.evansitzes.vocabularyflashcards.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class LevelSelectionActivityTest extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    final public OkHttpClient client = new OkHttpClient();
    final ArrayList<String> categories = new ArrayList<String>();
    public LinearLayout container;
    Intent flashcardsIntent;
    private String language;
    private Button back;
    private String url = "https://vocabularyterms.herokuapp.com/{language}/categories";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_level_selection);
        flashcardsIntent = new Intent(LevelSelectionActivityTest.this, FlashcardsActivity.class);
        language = getIntent().getStringExtra("language");
        container = (LinearLayout)findViewById(R.id.container);

        setBackButton();

        getWordList(language);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public void showPage() {
        final int categorySize = categories.size();
        final Button buttons[] = new Button[categorySize];

        for (int i = 0; i < categorySize; i++){
            final Button button = new Button(this);
            final String category = categories.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFlashcardsActivity(category);
                }
            });

            buttons[i] = button;
            buttons[i].setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            buttons[i].setText(language + ": " + category);
            container.addView(buttons[i]);
        }

    }

    public void addBtn(View v) {

    }

    private void getWordList(final String language) {
        url = url.replace("{language}", language);

        if (isNetworkAvailable()) {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError(e.getMessage());
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    try {
                        final JSONArray jsonData;
                        try {
                            jsonData = new JSONArray(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            alertUserAboutError(e.getMessage());
                            return;
                        }
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < jsonData.length(); i++) {
                                        try {
                                            categories.add(jsonData.get(i).toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    showPage();
                                }
                            });
                        } else {
                            alertUserAboutError("Error loading JSON");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        } else {
            Toast.makeText(this, "Netowork is unavailable",
                    Toast.LENGTH_LONG).show();
        }
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

    private void startFlashcardsActivity(final String flashcardType) {
        flashcardsIntent.putExtra("category", flashcardType);
        flashcardsIntent.putExtra("language", language);

        startActivity(flashcardsIntent);
    }

    private void alertUserAboutError(final String error) {
        Log.v(TAG, error);
        Toast.makeText(this, error,
                Toast.LENGTH_LONG).show();
    }

    private void setBackButton() {
        back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void goBack() {
        Intent intent = new Intent(LevelSelectionActivityTest.this, MainActivity.class);
        startActivity(intent);
    }
}