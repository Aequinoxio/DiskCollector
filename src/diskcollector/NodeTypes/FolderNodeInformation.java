/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.nio.file.Path;

/**
 *
 * @author utente
 */
public class FolderNodeInformation extends FilesystemNodeInformation implements CollectionNodeInformation{

    // Informazioni nel caso in cui l'oggetto del tree sia un folder
    private long subFirstFolders;           // Sub folders immediatamente collegate
    private long filesFirstSizeInFolder;    // size dei files direttamente figli nella folder
    private long firstFilesInFolder;        // numero di files direttamente nella folder
    private long foldersTotal;              // folders complessive
    private long sizeTotal ;                // dimensioni totali del folder
    private long filesTotal;                // Numero di files totali nel folder e subfolder


    /**
     * Costruttore
     */
    FolderNodeInformation() {
        super(NodeType.FOLDER);
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare
     * @param toString Stringa da mostrare
     */
    FolderNodeInformation(String toString) {
        super(toString, NodeType.FOLDER);
    }

    /**
     * Costruttore
     * Viene impostato il path del nodo folder
     * @param path Path del folder
     */
    public FolderNodeInformation(Path path) {
        super(path, NodeType.FOLDER);
        //this.path = path.toString();
    }

    /**
     * Costruttore
     * Vengono impostate la stringa da mostrare ed il path del folder
     * @param string
     * @param path
     */
    public FolderNodeInformation(String string, Path path) {
        super(string, NodeType.FOLDER);
        //this.path = path.toString();
    }

    /**
     * Restituisce la dimensione dei files direttamente figli nella folder
     * @return Dimensione dei files
     */
    public long getFirstFilesSizeInFolder() {
        return filesFirstSizeInFolder;
    }

    /**
     * Imposta la dimensione dei files direttamente figli nel folder
     * @param filesSizeInFolder Dimensione dei files
     */
    public void setFirstFilesSizeInFolder(long filesSizeInFolder) {
        this.filesFirstSizeInFolder = filesSizeInFolder;
    }

    /**
     * Restituisce il numero dei sotto folder
     * @return numero sotto folder
     */

    public long getFirstSubFolders() {
        return subFirstFolders;
    }

    /**
     * Imposta il numero dei sotto folder
     * @param subFolders numero sottofolder
     */
    public void setFirstSubFolders(long subFolders) {
        this.subFirstFolders = subFolders;
    }

    /**
     * Restituisce il numero dei files direttamente figli del folder
     * @return numero figli del folder
     */
    public long getFirstFilesInFolder() {
        return firstFilesInFolder;
    }

    /**
     * Imposta il numero dei files direttamente figli del folder
     * @param firstFilesInFolder numero figli del folder
     */
    public void setFirstFilesInFolder(long firstFilesInFolder) {
        this.firstFilesInFolder = firstFilesInFolder;
    }

    /**
     * Restituisce il numero dei files totali di tutti i sottofolder 
     * @return numrero totale dei files
     */
    public long getFilesTotal() {
        return filesTotal;
    }

    /**
     * Imposta il numero dei files totali di tutti i sottofolder 
     * @param filesTotal numero totale dei files
     */
    public void setFilesTotal(long filesTotal) {
        this.filesTotal = filesTotal;
    }

    /**
     * Restituisce la dimensione totale dei files del folder e dei sottofolder 
     * @return dimensione
     */
    public long getSizeTotal() {
        return sizeTotal;
    }

    /**
     * Imposta la dimensione totale dei files del folder e dei sottofolder
     * @param sizeTotal dimensione
     */
    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

     /**
     * Restituisce il numero complessivo dei sotto folder 
     * @return numero di sotto folder
     */
    public long getFoldersTotal() {
        return foldersTotal;
    }

    /**
     * Imposta il numero complessivo di sotto folder
     * @param foldersTotal numero di sottofolder
     */
    public void setFoldersTotal(long foldersTotal) {
        this.foldersTotal = foldersTotal;
    }

}
