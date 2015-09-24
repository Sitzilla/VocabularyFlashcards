package com.evansitzes.vocabularyflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.evansitzes.vocabularyflashcards.R;

public class LevelSelectionActivity extends AppCompatActivity {

    private Button basicWordlist;
    private Button intermediateWordlist;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        basicWordlist = (Button) findViewById(R.id.basicWordListButton);
        intermediateWordlist = (Button) findViewById(R.id.intermediateWordListButton);
        back = (Button) findViewById(R.id.backButton);

        loadSelection();
    }

    private void loadSelection() {

        basicWordlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", "basicKorean");
                startActivity(intent);
            }
        });

        intermediateWordlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelSelectionActivity.this, FlashcardsActivity.class);
                intent.putExtra("level", "intermediateKorean");
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
