/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Nodo generico contenente informazioni di un oggetto del filesystem (folder o file)
 * @author utente
 */
public class FilesystemNodeInformation extends NodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un file o folder
    private String path;              // 
    private long lastModifiedDateTime;
    private long lastAccessedDateTime;
    private long createDateTime;
    
    /**
     * Costruttore
     * 
     * @param nodeType tipo nodo
     */
    FilesystemNodeInformation(NodeType nodeType) {
        super(nodeType);
    }

    /**
     * Costruttore
     * Viene impostata la stringa da mostrare ed il tipo di nodo
     * @param toString stringa da mostrare
     * @param nodeType tipo di nodo
     */
    FilesystemNodeInformation(String toString,NodeType nodeType) {
        super(toString, nodeType);
    }

    /**
     * Costruttore
     * Viene impostato il path ed il tipo di nodo
     * @param path Path
     * @param nodeType tipo di nodo
     */
    public FilesystemNodeInformation(Path path, NodeType nodeType) {
        super(path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString(), nodeType);
        this.path = path.toString();
    }

    /**
     * Costruttore
     * Vengono impostati la stringa da mostrare, il path ed il tipo di nodo
     * @param string Stringa da mostrare
     * @param path Path
     * @param nodeType tipo di nodo
     */
    public FilesystemNodeInformation(String string, Path path, NodeType nodeType) {
        super(string, nodeType);
        this.path = path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString();
    }

    /**
     * Restituisce il path
     * @return path
     */
    public Path getPath() {
        return Paths.get(path);
    }

    /**
     * Imposta il path
     * @param path Path
     */
    public void setPath(Path path) {
        this.path = path.toString();
    }

    /**
     * Restituisce il tempo di ultima modifica in millisecondi
     * @return
     */
    public long getLastModifiedDateTimeMillis() {
        return lastModifiedDateTime;
    }

    /**
     * Imposta il tempo di ultima modifica in millisecondi
     * @param lastModifiedDateTime
     */
    public void setLastModifiedDateTimeMillis(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    /**
     * Restituisce il tempo di ultimo accesso in millisecondi
     * @return
     */
    public long getLastAccessedDateTimeMillis() {
        return lastAccessedDateTime;
    }

    /**
     * Imposta il tempo di ultimo accesso in millisecondi
     * @param lastAccessedDateTime
     */
    public void setLastAccessedDateTimeMillis(long lastAccessedDateTime) {
        this.lastAccessedDateTime = lastAccessedDateTime;
    }

    /**
     * Restituisce il tempo di creazione in millisecondi
     * @return
     */
    public long getCreateDateTimeMillis() {
        return createDateTime;
    }

    /**
     * Impostail tempo di creazione in millisecondi
     * @param createDateTime
     */
    public void setCreateDateTimeMillis(long createDateTime) {
        this.createDateTime = createDateTime;
    }
}
