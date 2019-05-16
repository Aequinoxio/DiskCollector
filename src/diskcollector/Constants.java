/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import java.util.prefs.Preferences;

/**
 * Singleton con costanti e preferences utili per tutto il programma
 * @author utente
 */
public class Constants {

    // Preference keys for this package
    static final String ROOT_STRING = "Backup disponibili";
    private static final String SAVED_PATH = "SAVED_PATH";
    private static final String SAVED_FILENAME = "SAVED_FILENAME";
    private static final Preferences prefs = Preferences.userNodeForPackage(Constants.class);

    /**
     * Istanza del Singleton
     * @return
     */
    public static Constants getInstance() {
        return ConstantsHolder.INSTANCE;
    }


    private Constants() {
    }

    /**
     * Ritorna l'ultimo path dove sono stati salvati gli oggetti
     * @return Stringa che rappresenta il path di salvataggio
     */
    public String getLatestSavePath() {
        return prefs.get(SAVED_PATH, ".");
    }

    /**
     * Memorizza il path di salvataggio per futuro riferimento
     * @param savePath
     */
    public void setLatestSavePath(String savePath) {
        prefs.put(SAVED_PATH, savePath);
    }

    /**
     * Ritorna l'ultimo nome file usato per il salvataggio
     * @return Stringa con l'ultimo savename usato. DB.txt è il default
     */
    public String getLatestSaveFilename() {
        return prefs.get(SAVED_FILENAME, "DB.txt");
    }

    /**
     * Imposta l'ultimo nome file usato per il salvataggio
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
