/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.Worker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.tree.TreeModel;

/**
 * Classe per le operazioni gravose sul filesystem per es quelle associate al
 * caricamento e salvataggio del DB
 *
 * @author utente
 */
public class FilesystemActionWorker extends SwingWorker<TreeModel, Void> {

    private final String absolutePath;
    TreeModel atm = null;
    boolean isReadOperation;

    public FilesystemActionWorker(String absolutePath, boolean readOperation) {
        this.absolutePath = absolutePath;
        isReadOperation = readOperation;
    }

    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;

    @Override
    protected TreeModel doInBackground() {
        if (isReadOperation) {
            return readFile();
        } else {
            writeFile();
            return null;
        }
    }

    public void setObjectToWrite(TreeModel atm) {
        this.atm = atm;
    }

    private void writeFile() {
        if (atm == null) { // Se non ho impostato nulla allora non ho nulal da salvare ed esco
            return;
        }

        String absolutePathTemp = absolutePath + ".part";          // Creo un file di appoggio    

        try {
            outStream = new ObjectOutputStream(new FileOutputStream(absolutePathTemp));
            outStream.writeObject(atm);
            //atm = (TreeModel) inStream.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if ("Stream Closed".equals(ex.getMessage())) {
                // Se intercetto uno Stream Closed, non faccio nulla in quanto 
                // l'ha chiuso l'utente annullando il caricamento
            } else {
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Copio il file temporaneo su quello originale solo se non ho cancellato il worker
        Path pathTemp = Paths.get(absolutePathTemp);
        Path path = Paths.get(absolutePath);
        if (!isCancelled()) {
            try {
                Files.copy(pathTemp, path, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException ex) {
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // Cancello comunque il file parziale
        try {
            Files.delete(pathTemp);
        } catch (IOException ex) {
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TreeModel readFile() {
        try {
            inStream = new ObjectInputStream(new FileInputStream(absolutePath));
            atm = (TreeModel) inStream.readObject();
        } catch (FileNotFoundException | ClassNotFoundException ex) {
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if ("Stream Closed".equals(ex.getMessage())) {
                // Se intercetto uno Stream Closed, non faccio nulla in quanto 
                // l'ha chiuso l'utente annullando il caricamento
            } else {
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return atm;

    }

    public boolean closeAndCancelWorker() {
        boolean retval = this.cancel(true);

        try {
            if (isReadOperation) {
                inStream.close();
            } else {
                outStream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, atm);
        atm = null;
        return (retval);
    }

//    // TEMP
//    public long objectSize(){
//        try {
//            return in_TEMP.available();
//        } catch (IOException ex) {
//            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return -1;
//    }
}
