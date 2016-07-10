package com.evansitzes.vocabularyflashcards.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evan on 9/20/15.
 */
public abstract class Flashcard {
    private String vocabQuestion = "My Question";
    private String vocabAnswer = "Correct Answer";
    private boolean reversed = false;
    private Word currentWord;
    protected List<Word> wordlist = new ArrayList<>();
    protected List<JSONObject> jsonResponseMain;

    public abstract void populateInitialWordlist();

    public void setJsonResponseMain(List<JSONObject> jsonResponseMain) {

    }

    public List<Word> getWordList() {
        return wordlist;
    }

    public int getSize() {
        return wordlist.size();
    }

    public String getRandomForeignWord() {
        final Random generator = new Random();
        currentWord = wordlist.get(generator.nextInt(wordlist.size()));

        if (reversed) {
            return currentWord.getEnglishWord();
        }
        return currentWord.getForeignWord();
    }

    public String getCurrentEnglish() {
        if (reversed) {
            return currentWord.getForeignWord();
        }
        return currentWord.getEnglishWord();    }

    public int getCurrentId() {
        return currentWord.getId();
    }

    public void removeForeignWord() {
        wordlist.remove(currentWord);
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


    public void reverseWordOrder() {
        if (reversed) {
            reversed = false;
        } else {
            reversed = true;
        }
    }
}
