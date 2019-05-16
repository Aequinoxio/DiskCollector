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
public class FileNodeInformation extends FilesystemNodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un file
    private long size ;
    

    /**
     * Costruttore di default
     */
    FileNodeInformation() {
        super(NodeType.FILE);
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare
     * @param toString stringa da mostrare
     */
    FileNodeInformation(String toString) {
        super(toString, NodeType.FILE);
    }

    /**
     * Costruttore
     * Viene impostato il path
     * @param path Path
     */
    public FileNodeInformation(Path path) {
        super(path, NodeType.FILE);
        //this.path = path.toString();
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare ed il path
     * @param string stringa da mostrare
     * @param path Path
     */
    public FileNodeInformation(String string, Path path) {
        super(string, path, NodeType.FILE);
    }

    /**
     * Restituisce la dimensione del file
     * @return
     */
    public long getSize() {
        return size;
    }

    /**
     * Imposta la dimensione del file
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

}
