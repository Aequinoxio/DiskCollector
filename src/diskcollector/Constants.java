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

    /**
     *
     * @return
     */
    public static Constants getInstance() {
        return ConstantsHolder.INSTANCE;
    }


    private Constants() {
    }

    /**
     *
     * @return
     */
    public String getLatestSavePath() {
        return prefs.get(SAVED_PATH, ".");
    }

    /**
     *
     * @param savePath
     */
    public void setLatestSavePath(String savePath) {
        prefs.put(SAVED_PATH, savePath);
    }

    /**
     *
     * @return
     */
    public String getLatestSaveFilename() {
        return prefs.get(SAVED_FILENAME, "DB.txt");
    }

    /**
     *
     * @param saveFilename
     */
    public void setLatestSaveFilename(String saveFilename) {
        prefs.put(SAVED_FILENAME, saveFilename);
    }

    private static class ConstantsHolder {

        private static final Constants INSTANCE = new Constants();

        private ConstantsHolder() {
        }
    }

}
