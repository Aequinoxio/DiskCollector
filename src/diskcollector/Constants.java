/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import java.util.prefs.Preferences;

/**
 *
 * @author utente
 */
public class Constants {

    // Preference keys for this package
    static final String ROOT_STRING = "Backup disponibili";
    private static final String SAVED_PATH = "SAVED_PATH";
    private static final String SAVED_FILENAME = "SAVED_FILENAME";
    private static final Preferences prefs = Preferences.userNodeForPackage(Constants.class);


    private Constants() {
    }

    public static Constants getInstance() {
        return ConstantsHolder.INSTANCE;
    }

    private static class ConstantsHolder {

        private static final Constants INSTANCE = new Constants();
    }

    public String getLatestSavePath() {
        return prefs.get(SAVED_PATH, ".");
    }

    public void setLatestSavePath(String savePath) {
        prefs.put(SAVED_PATH, savePath);
    }

    public String getLatestSaveFilename() {
        return prefs.get(SAVED_FILENAME, "DB.txt");
    }

    public void setLatestSaveFilename(String saveFilename) {
        prefs.put(SAVED_FILENAME, saveFilename);
    }

}
