/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * Singleton con costanti e preferences utili per tutto il programma
 *
 * @author utente
 */
public class Constants {

    // Preference keys for this package
    static final String ROOT_STRING = "Backup disponibili";
    static final String LOGFILENAME = "DiskCollector_logger.log";
    static final int LOGTEXTAREA_ROWS=25;
    static final int LOGTEXTAREA_COLS=80;
    private static final String SAVED_PATH = "SAVED_PATH";
    private static final String SAVED_FILENAME = "SAVED_FILENAME";
    private static final String DBENCRYPTEDKEY = "DBENCRYPTED";
    private static final String EXCEPTIONSLOGGEDKEY = "EXCEPTIONSLOGGED";
    private static final Preferences PREFS = Preferences.userNodeForPackage(Constants.class);
    private static final Logger LOG = Logger.getLogger(Constants.class.getName());

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

    private boolean DBEncrypted;        // True se devo salvare il DB Cifrato
    private boolean ExceptionsLogged;   // True se devo salvare le eccezioni nel file di log

    // TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro
    private char[] userPassword;

    private Constants() {
        DBEncrypted = PREFS.getBoolean(DBENCRYPTEDKEY, true);
        ExceptionsLogged = PREFS.getBoolean(EXCEPTIONSLOGGEDKEY, true);        
    }

// TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro

    /**
     *
     * @return
     */
    public char[] getUserPassword() {
        if (userPassword != null) {
            return Arrays.copyOf(userPassword, userPassword.length);
        } else {
            return null;
        }
    }

// TODO: Molto brutto, usare uno storage sicuro per non mostrarla in memoria in chiaro

    /**
     *
     * @param userPassword
     */
    public void setUserPassword(char[] userPassword) {
        if (userPassword != null) {
            this.userPassword = Arrays.copyOf(userPassword, userPassword.length);
        } else {
            // cancello la password e la dimentico
            for (int i = 0; i < this.userPassword.length; i++) {
                this.userPassword[i] = '\00';
            }
            this.userPassword = null;
        }
    }

    /**
     * Ritorna il valore salvato nelle preferences
     *
     * @return True se il DB va salvato cifrato, false altrimenti
     */
    public boolean isDBEncrypted() {
        return DBEncrypted;
    }

    /**
     * Imposta e salva nelle preferences se il DB va cifrato
     *
     * @param DBEncrypted True se il DB va cifrato, false altrimenti
     */
    public void setDBEncrypted(boolean DBEncrypted) {
        this.DBEncrypted = DBEncrypted;
        PREFS.putBoolean(DBENCRYPTEDKEY, DBEncrypted);
    }

    /**
     * Ritorna il valore salvato nelle Preferences
     * @return True se le eccezioni vanno salvate nel log file, false altrimenti
     */
    public boolean isExceptionsLogged() {
        return ExceptionsLogged;
    }

    /**
     * Imposta e salva nelle preferences se le eccezioni vanno salvate nel log
     * @param ExceptionsLogged 
     */
    public void setExceptionsLogged(boolean ExceptionsLogged) {
        this.ExceptionsLogged = ExceptionsLogged;
        PREFS.putBoolean(EXCEPTIONSLOGGEDKEY, ExceptionsLogged);
    }

    /**
     *
     * @return
     */
    public byte[] getSalt() {
        return Arrays.copyOf(salt, salt.length);
    }

    /**
     *
     * @return
     */
    public byte[] getIV() {
        return Arrays.copyOf(IV, IV.length);
    }

    /**
     * Ritorna l'ultimo path dove sono stati salvati gli oggetti
     *
     * @return Stringa che rappresenta il path di salvataggio
     */
    public String getLatestSavePath() {
        return PREFS.get(SAVED_PATH, ".");
    }

    /**
     * Memorizza il path di salvataggio per futuro riferimento
     *
     * @param savePath
     */
    public void setLatestSavePath(String savePath) {
        PREFS.put(SAVED_PATH, savePath);
    }

    /**
     * Ritorna l'ultimo nome file usato per il salvataggio
     *
     * @return Stringa con l'ultimo savename usato. DB.txt è il default
     */
    public String getLatestSaveFilename() {
        return PREFS.get(SAVED_FILENAME, "DB.txt");
    }

    /**
     * Imposta l'ultimo nome file usato per il salvataggio
     *
     * @param saveFilename
     */
    public void setLatestSaveFilename(String saveFilename) {
        PREFS.put(SAVED_FILENAME, saveFilename);
    }

    private static class ConstantsHolder {

        private static final Constants INSTANCE = new Constants();

        private ConstantsHolder() {
        }
    }

}
