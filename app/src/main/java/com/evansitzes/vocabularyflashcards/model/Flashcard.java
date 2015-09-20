package com.evansitzes.vocabularyflashcards.model;

/**
 * Created by evan on 9/20/15.
 */
public class Flashcard {
    private String vocabQuestion = "My Question";
    private String vocabAnswer = "Correct Answer";


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
