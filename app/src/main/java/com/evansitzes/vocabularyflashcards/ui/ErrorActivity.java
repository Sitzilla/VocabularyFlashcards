package com.evansitzes.vocabularyflashcards.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evansitzes.vocabularyflashcards.R;
import com.evansitzes.vocabularyflashcards.helpers.ExceptionHandler;

/**
 * Created by evan on 4/21/16.
 */
public class ErrorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_error);

    }
}
