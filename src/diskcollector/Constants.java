/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import java.util.Arrays;
import java.util.prefs.Preferences;

/**
 * Singleton con costanti e preferences utili per tutto il programma
 *
 * @author utente
 */
public class Constants {

    // Preference keys for this package
    static final String ROOT_STRING = "Backup disponibili";
    private static final String SAVED_PATH = "SAVED_PATH";
    private static final String SAVED_FILENAME = "SAVED_FILENAME";
    private static final String DBEncryptedKEY = "DBENCRYPTED";
    private static final Preferences prefs = Preferences.userNodeForPackage(Constants.class);
    /**
     * Istanza del Singleton
     *
     * @return
     */
    public static Constants getInstance() {
        return ConstantsHolder.INSTANCE;
    }

    // Parametri per cifrare l'oggetto sealed
    private final byte[] salt = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private final byte[] IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private boolean DBEncrypted;

    // TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro
    private char[] userPassword;

    private Constants() {
        DBEncrypted = prefs.getBoolean(DBEncryptedKEY, true);
    }

// TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro
    public char[] getUserPassword() {
        if (userPassword != null) {
            return Arrays.copyOf(userPassword, userPassword.length);
        } else {
            return null;
        }
    }

// TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro
    public void setUserPassword(char[] userPassword) {
        if (userPassword != null) {
            this.userPassword = Arrays.copyOf(userPassword, userPassword.length);
        } else { 
            // cancello la password e la dimentico
            for (int i = 0; i< this.userPassword.length;i++){
                this.userPassword[i]='\00';
            }
            this.userPassword=null;
        }
    }

    public boolean isDBEncrypted() {
        return DBEncrypted;
    }

    public void setDBEncrypted(boolean DBEncrypted) {
        this.DBEncrypted = DBEncrypted;
        prefs.putBoolean(DBEncryptedKEY, DBEncrypted);
    }

    public byte[] getSalt() {
        return Arrays.copyOf(salt, salt.length);
    }

    public byte[] getIV() {
        return Arrays.copyOf(IV, IV.length);
    }


    /**
     * Ritorna l'ultimo path dove sono stati salvati gli oggetti
     *
     * @return Stringa che rappresenta il path di salvataggio
     */
    public String getLatestSavePath() {
        return prefs.get(SAVED_PATH, ".");
    }

    /**
     * Memorizza il path di salvataggio per futuro riferimento
     *
     * @param savePath
     */
    public void setLatestSavePath(String savePath) {
        prefs.put(SAVED_PATH, savePath);
    }

    /**
     * Ritorna l'ultimo nome file usato per il salvataggio
     *
     * @return Stringa con l'ultimo savename usato. DB.txt è il default
     */
    public String getLatestSaveFilename() {
        return prefs.get(SAVED_FILENAME, "DB.txt");
    }

    /**
     * Imposta l'ultimo nome file usato per il salvataggio
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
