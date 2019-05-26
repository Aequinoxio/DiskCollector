/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Classe per identificare un nodo di Backup
 * @author utente
 */
public class BackupNodeInformation extends NodeInformation implements CollectionNodeInformation {
    private static final Logger LOG = Logger.getLogger(BackupNodeInformation.class.getName());

    private Date retrievingDate = null; // Data alla quale sono state recuperate le informazioni sul backup
    private String details;             // descrizione del backup
    private long foldersTotal;          // folders totali nel backup
    private long filesTotal;            // files totali nel backup
    private long sizeTotal ;            // dimensione totale dei files in tutti i subfolder
    
    /**
     * Costruttore di default
     */
    public BackupNodeInformation() {
        super(NodeType.BACKUP);
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare
     * @param displayString stringa da mostrare
     */
    public BackupNodeInformation(String displayString) {
        super(displayString, NodeType.BACKUP);
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare ed i dettagli
     * @param displayString stringa da mostrare
     * @param details stringa con i dettagli
     */
    public BackupNodeInformation(String displayString, String details) {
        super(displayString, NodeType.BACKUP);
        this.details=details;
    }

    /**
     * Restituisce il numero complessivo dei sotto folder 
     * @return numero di sotto folder
     */
    @Override
    public long getFoldersTotal() {
        return foldersTotal;
    }

    /**
     * Imposta il numero complessivo di sotto folder
     * @param foldersTotal numero di sottofolder
     */
    @Override
    public void setFoldersTotal(long foldersTotal) {
        this.foldersTotal = foldersTotal;
    }

    /**
     * Restituisce il numero totale dei files nel backup
     * @return numero files del backup
     */
    @Override
    public long getFilesTotal() {
        return filesTotal;
    }

    /**
     * Imposta il numero complessivo dei files del backup
     * @param filesTotal numero di files del backup
     */
    @Override
    public void setFilesTotal(long filesTotal) {
        this.filesTotal = filesTotal;
    }

    /**
     * Restituiscei i dettagli 
     * @return stringa con i dettagli
     */
    public String getDetails() {
        return details;
    }

    /**
     * Imposta i dettagli
     * @param details stringa con i dettagli
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Restituisce la data alla quale sono state recuperate le informazioni di backup
     * @return
     */
    public Date getRetrievingDate() {
        return retrievingDate;
    }

    /**
     * Imposta la data alla quale sono state recuperate le informazioni di backup
     * @param retrievingDate
     */
    public void setRetrievingDate(Date retrievingDate) {
        this.retrievingDate = retrievingDate;
    }

    /**
     * Restituisce la dimensione totale dei files del folder e dei sottofolder 
     * @return dimensione
     */
    @Override
    public long getSizeTotal() {
        return sizeTotal;
    }

    /**
     * Imposta la dimensione totale dei files del folder e dei sottofolder
     * @param sizeTotal dimensione
     */
    @Override
    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

}
