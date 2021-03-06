package com.evansitzes.vocabularyflashcards.utils;

import android.content.Context;
import android.os.Environment;

import com.evansitzes.vocabularyflashcards.VocabularyFlashcardsApplicationSettings;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by Evan Anger on 7/28/14.
 */
public class FileUtilities {

    public static boolean doesFileExist(Context context, String name) {
        File fileDirectory = getFileDirectory(context);
        File fileToCheck = new File(fileDirectory, name);

        if(fileToCheck.exists()) {
            return true;
        }
        return false;
    }

    public static void saveHashmap(Context context, String name, HashMap<String, String> vocabWords) {
        File fileDirectory = getFileDirectory(context);
        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(vocabWords);
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getHashmap(Context context, String name) {
        File fileDirectory = getFileDirectory(context);
        File fileToRead = new File(fileDirectory, name);
        HashMap<String, String> newlyReadInMap = new HashMap<String, String>();

        try {
            FileInputStream fileInputStream  = new FileInputStream(fileToRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            newlyReadInMap = (HashMap) objectInputStream.readObject();
            objectInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newlyReadInMap;
    }

    public static File getFileDirectory(Context context) {
        VocabularyFlashcardsApplicationSettings settings = new VocabularyFlashcardsApplicationSettings(context);
        String storageType = settings.getStoragePreference();
        if (storageType.equals(StorageType.INTERNAL)) {
            return context.getFilesDir();
        } else {
            if (isExternalStorageAvailable()) {
                if (storageType.equals(StorageType.PRIVATE_EXTERNAL)) {
                    return context.getExternalFilesDir(null);
                } else {
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                }
            } else {
                return context.getFilesDir();
            }
        }

    }

    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static File [] listFiles(Context context) {
        File fileDirectory = getFileDirectory(context);
        File [] filteredFiles = fileDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getAbsolutePath().contains(".jpg")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return filteredFiles;
    }
}
