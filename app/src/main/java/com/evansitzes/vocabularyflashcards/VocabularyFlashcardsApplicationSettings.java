package com.evansitzes.vocabularyflashcards;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.evansitzes.vocabularyflashcards.utils.StorageType;

/**
 * Created by evan on 9/16/15.
 */
public class VocabularyFlashcardsApplicationSettings {
    SharedPreferences mSharedPreferences;

    public VocabularyFlashcardsApplicationSettings(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getStoragePreference() {
        return mSharedPreferences.getString("Storage", StorageType.INTERNAL);
    }

    public void setSharedPreference(String storageType) {
        mSharedPreferences
                .edit()
                .putString("Storage", storageType)
                .apply();
    }
}
