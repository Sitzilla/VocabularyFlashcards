package com.evansitzes.vocabularyflashcards.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by evan on 9/20/15.
 */
public abstract class Flashcard {
    private String vocabQuestion = "My Question";
    private String vocabAnswer = "Correct Answer";
    protected HashMap<String, String> foreignToEnglish = new HashMap<String, String>();
    protected List<JSONObject> jsonResponseMain;

    public abstract void populateInitialWordlist();

    public void setJsonResponseMain(List<JSONObject> jsonResponseMain) {

    }

    public HashMap<String, String> getWordList() {
        return foreignToEnglish;
    }

    public int getSize() {
        return foreignToEnglish.size();
    }

    public String getRandomForeignWord() {
        Random generator = new Random();
        Object[] words = foreignToEnglish.keySet().toArray();
        Object randomValue = words[generator.nextInt(words.length)];

        return randomValue.toString();
    }

    public String getEnglishFromForeign(String foreignWord) {
        return foreignToEnglish.get(foreignWord);
    }

    public void removeForeignWord(String foreignWord) {
        foreignToEnglish.remove(foreignWord);
    }

    public void setWordList(HashMap<String, String> foreignToEnglish) {
        this.foreignToEnglish = foreignToEnglish;
    }

    public String getVocabQuestion() {
        return vocabQuestion;
    }

    public void setVocabQuestion(String vocabQuestion) {
        this.vocabQuestion = vocabQuestion;
    }

    public String getVocabAnswer() {
        return vocabAnswer;
    }

    public void setVocabAnswer(String vocabAnswer) {
        this.vocabAnswer = vocabAnswer;
    }



}
