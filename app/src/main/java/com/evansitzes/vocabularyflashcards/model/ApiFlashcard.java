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
        for (final JSONObject word : jsonResponseMain) {
            try {
                wordlist.put(word.getString("foreignWord"), word.getString("englishWord"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reverseWordOrder() {
        HashMap<String, String> wordlistInverse = new HashMap<String, String>();

        for(Map.Entry<String, String> entry : wordlist.entrySet()){
            wordlistInverse.put(entry.getValue(), entry.getKey());
        }

        wordlist = wordlistInverse;
    }
}
