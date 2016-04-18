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
import com.evansitzes.vocabularyflashcards.helpers.FlashcardType;
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

    private Button selectionButtonOne;
    private Button selectionButtonTwo;
    private Button selectionButtonThree;
    private Button selectionButtonFour;
    private Button selectionButtonFive;
    private Button back;
    private String language;
    Intent flashcardsIntent;
    public OkHttpClient client = new OkHttpClient();
    ArrayList<String> jsonResponseMain = new ArrayList<String>();
    public LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        flashcardsIntent = new Intent(LevelSelectionActivityTest.this, FlashcardsActivity.class);

        language = getIntent().getStringExtra("language");
        container = (LinearLayout)findViewById(R.id.container);
        getWordList(language);

    }

    public void showPage() {
        Button buttons[] = new Button[jsonResponseMain.size()];

        for (int i = 0; i < jsonResponseMain.size(); i++){

            final Button button = new Button(this);
            final String category = jsonResponseMain.get(i);
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

        back = (Button) findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void getWordList(final String language) {
        String categoriesEndpoint = "https://vocabularyterms.herokuapp.com/" + language + "/categories";
        if (isNetworkAvailable()) {
//            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(categoriesEndpoint)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
//                    toggleRefresh();
                    alertUserAboutError();

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    toggleRefresh();
                    try {
                        final JSONArray jsonData;
                        try {
                            jsonData = new JSONArray(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < jsonData.length(); i++) {
                                        try {
                                            jsonResponseMain.add(jsonData.get(i).toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    showPage();
                                }
                            });
                        } else {
                            alertUserAboutError();
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

    public void addBtn(View v) {

    }

    private void toggleRefresh() {

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

    private void startFlashcardsActivity(final String flashcardType) {
        flashcardsIntent.putExtra("category", flashcardType);
        flashcardsIntent.putExtra("language", language);

        startActivity(flashcardsIntent);
    }

    private void goBack() {
        Intent intent = new Intent(LevelSelectionActivityTest.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void alertUserAboutError() {
        System.out.println("ERROR WITH API CALL");
    }
}
