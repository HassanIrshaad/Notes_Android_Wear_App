package com.test.hassan.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.wearable.activity.ConfirmationActivity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hassan on 05-Apr-17.
 */

public class Helper {
    public static String saveNote(Note note, Context context){
    /*
        Context is an abstract class that allows you to reference (or call)
        application specific methods like SharedPreferences
     */
    //SharedPreferences used to save our data in app
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Since we're making changes to the data we need an editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //SharedPreferences allows you to save and retrieve data in the form of
        //key/value pairs. This instance points to the file that has the saved data
        String id = String.valueOf(System.currentTimeMillis());
        editor.putString(id, note.getTitle());

        editor.commit(); //to save data

        return id;
    }

    public static ArrayList<Note> getAllNotes(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<Note> notes = new ArrayList<>();

        //Maps consist of a set of keys and values in which each key is mapped to a single value
        Map<String, ?> key = sharedPreferences.getAll();

        for(Map.Entry<String, ?> entry : key.entrySet()){
            String savedData = (String) entry.getValue();

            if(savedData != null){
                Note note = new Note(entry.getKey(), savedData);

                notes.add(note);
            }
        }
        return notes;
    }

    public static void removeNote(String id, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(id);

        editor.commit(); //saves data
    }

    public static void displayConfirmation(String message, Context context){
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);

        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, message);
        context.startActivity(intent);
    }
}
