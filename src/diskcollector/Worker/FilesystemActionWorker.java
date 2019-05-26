/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.Worker;

import SimpleAESEncryptionLib.*;
import diskcollector.Constants;
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
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 * Classe per le operazioni gravose sul filesystem per es quelle associate al
 * caricamento e salvataggio del DB
 *
 * @author utente
 */
public class FilesystemActionWorker extends SwingWorker<TreeModel, Void> {
    private static final Logger LOG = Logger.getLogger(FilesystemActionWorker.class.getName());

    FilesystemWorkerErrorTypes m_error = FilesystemWorkerErrorTypes.OK;

    private final String absolutePath;
    TreeModel atm = null;
    boolean isReadOperation;

    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;

    /**
     * Costruttore del worker
     * @param absolutePath Path assoluto del file su cui lacorare
     * @param readOperation True se devo leggere, false per scrivere
     */
    public FilesystemActionWorker(String absolutePath, boolean readOperation) {
        this.absolutePath = absolutePath;
        isReadOperation = readOperation;
    }

    /**
     * Operazione sul file
     * @return
     */
    @Override
    protected TreeModel doInBackground() {
        if (isReadOperation) {
            return readFile();
        } else {
            writeFile();
            return null;
        }
    }

    /**
     * Imposta l'oggetto che verrà scritto
     * @param atm Treemodel da scrivere su file (deve essere serializzabile)
     */
    public void setObjectToWrite(TreeModel atm) {
        this.atm = atm;
    }

    /**
     * Metodo interno per scrivere il file
     */
    private void writeFile() {
        if (atm == null) { // Se non ho impostato nulla allora non ho nulla da salvare ed esco
            return;
        }

        // TODO: Se devo cifrare ma la password non è impostata ritorno
        // Ok, da qui proseguo normalmente
        String absolutePathTemp = absolutePath + ".part";          // Creo un file di appoggio    

        try {
            outStream = new ObjectOutputStream(new FileOutputStream(absolutePathTemp));

            // Se devo cifrare il DB allora procedo, altrimenti salvo il DB così com'è
            if (Constants.getInstance().isDBEncrypted()) {

                AESCryptography aESCryptography = new AESCryptography();

                // TODO: salvare salt e IV in una struttura separata. Meglio se questi parametri vengono impostati dall'esterno e salvati a parte

                aESCryptography.initSecretParameters(
                        Constants.getInstance().getUserPassword(),
                        Constants.getInstance().getSalt(),
                        Constants.getInstance().getIV());

                Cipher cipher = aESCryptography.createCipher(true);
                SealedObject sealedObject = new SealedObject((DefaultTreeModel) atm, cipher);
                outStream.writeObject(sealedObject);

            } else {
                outStream.writeObject(atm);
            }
        } catch (FileNotFoundException ex) {
            m_error = FilesystemWorkerErrorTypes.ERROR_WRITING_FILE;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            m_error = FilesystemWorkerErrorTypes.ERROR_ENCRYPTING_FILE;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if ("Stream Closed".equals(ex.getMessage())) {
                // Se intercetto uno Stream Closed, non faccio nulla in quanto 
                // l'ha chiuso l'utente annullando il caricamento
            } else {
                m_error = FilesystemWorkerErrorTypes.IO_ERROR;
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                m_error = FilesystemWorkerErrorTypes.IO_ERROR;
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
                m_error = FilesystemWorkerErrorTypes.IO_ERROR;
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Cancello comunque il file parziale
        try {
            Files.delete(pathTemp);
        } catch (IOException ex) {
            m_error = FilesystemWorkerErrorTypes.IO_ERROR;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TreeModel readFile() {
        try {
            inStream = new ObjectInputStream(new FileInputStream(absolutePath));
                        
            // TODO: salvare salt e IV in una struttura separata. Meglio se questi parametri vengono impostati dall'esterno e salvati a parte

            // Senza password impostata provo a leggere gli oggetti come se fossero non cifrati
            if (Constants.getInstance().getUserPassword() == null) {
                atm = (TreeModel) inStream.readObject();
            } else {
                AESCryptography aESCryptography = new AESCryptography();

//                aESCryptography.setPass(Constants.getInstance().getUserPassword(), Constants.getInstance().getSalt());
//                aESCryptography.setIVVector(Constants.getInstance().getIV());
//                aESCryptography.createKey();

                aESCryptography.initSecretParameters(Constants.getInstance().getUserPassword(), Constants.getInstance().getSalt(), Constants.getInstance().getIV());
                Cipher cipher = aESCryptography.createCipher(false);

                Object atm_object = inStream.readObject();

                // Provo comunque a verificare se è un oggetto cifrato o meno
                if (atm_object instanceof SealedObject) {
                    atm = (TreeModel) ((SealedObject) atm_object).getObject(cipher);
                } else {
                    atm = (TreeModel) atm_object;
                }
            }
            
        } catch (FileNotFoundException ex) {
            m_error = FilesystemWorkerErrorTypes.ERROR_READING_FILE;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            m_error = FilesystemWorkerErrorTypes.ERROR_LOADING_CLASS;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            m_error = FilesystemWorkerErrorTypes.WRONG_PASSWORD;
            Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if ("Stream Closed".equals(ex.getMessage())) {
                // Se intercetto uno Stream Closed, non faccio nulla in quanto 
                // l'ha chiuso l'utente annullando il caricamento
            } else {
                m_error = FilesystemWorkerErrorTypes.IO_ERROR;
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException ex) {
                m_error = FilesystemWorkerErrorTypes.IO_ERROR;
                Logger.getLogger(FilesystemActionWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return atm;
    }

    /**
     * Chiuso il worker e gli stream (sembra l'unico modo per far terminare le
     * chiamate bloccanti: readObject o writeObject. Va gestito il caso nella 
     * catch dei rispettivi metodi di lettura e scrittura file)
     * @return Esito dell'operazione
     */
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

    /**
     *
     * @return
     */
    public FilesystemWorkerErrorTypes getError() {
        return m_error;
    }
}
