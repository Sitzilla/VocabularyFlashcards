package com.evansitzes.vocabularyflashcards.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
        for (final JSONObject word : jsonResponseMain) {
            try {
                foreignToEnglish.put(word.getString("foreignWord"), word.getString("englishWord"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
