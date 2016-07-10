package com.evansitzes.vocabularyflashcards.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evan on 4/17/16.
 */
public class ApiFlashcard extends Flashcard {

    @Override
    public void setJsonResponseMain(List<JSONObject> jsonResponseMain) {
        this.jsonResponseMain = jsonResponseMain;
    }

    @Override
    public void populateInitialWordlist() {
        for (final JSONObject response : jsonResponseMain) {
            wordlist.add(buildWord(response));
        }
    }

    private Word buildWord(final JSONObject response) {
        final Word word = new Word();

        try {
            word.setId(response.getInt("id"));
            word.setForeignWord(response.getString("foreignWord"));
            word.setEnglishWord(response.getString("englishWord"));
            word.setActive(response.getBoolean("active"));
            word.setLevel(response.getInt("level"));
            word.setCategory(response.getString("category"));
            word.setKnowledgeCount(response.getInt("knowledgeCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return word;
    }

}
