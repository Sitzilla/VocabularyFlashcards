package com.evansitzes.vocabularyflashcards.model;

/**
 * Created by evan on 9/24/15.
 */
public class AdvancedKoreanWords extends Flashcard {

    @Override
    public void populateInitialWordlist() {
        foreignToEnglish.put("hardword1 ", "hardanswer1");
        foreignToEnglish.put("hardword2", "hardanswer2");
        foreignToEnglish.put("hardword3", "hardanswer3");
    }
}
