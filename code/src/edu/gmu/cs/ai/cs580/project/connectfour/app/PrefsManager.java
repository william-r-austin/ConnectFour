package edu.gmu.cs.ai.cs580.project.connectfour.app;

import java.util.prefs.Preferences;

public class PrefsManager {
    
    private Preferences prefs = Preferences.userNodeForPackage(PrefsManager.class);
   
    private static final String GAME_NUMBER_KEY = "game_number_key";
    private static final String GAME_OUTPUT_DIRECTORY_KEY = "game_output_directory_key";
    
    public Integer getNextGameNumber() {
        Integer nextGameNumber = prefs.getInt(GAME_NUMBER_KEY, 1);
        prefs.putInt(GAME_NUMBER_KEY, nextGameNumber + 1);
        return nextGameNumber;
    }

}
