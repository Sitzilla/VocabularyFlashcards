package com.evansitzes.vocabularyflashcards.model;

/**
 * Created by evan on 7/9/16.
 */
public class Word {
    private int id;
    private String foreignWord;
    private String englishWord;
    private boolean active;
    private int level;
    private String category;
    private int knowledgeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getKnowledgeCount() {
        return knowledgeCount;
    }

    public void setKnowledgeCount(int knowledgeCount) {
        this.knowledgeCount = knowledgeCount;
    }
}
