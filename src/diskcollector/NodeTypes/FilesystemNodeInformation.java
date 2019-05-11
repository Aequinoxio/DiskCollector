/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Informazioni generiche di un oggetto del filesystem (folder o file)
 * @author utente
 */
public class FilesystemNodeInformation extends NodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un file o folder
    private String path;              // 
    private long lastModifiedDateTime;
    private long lastAccessedDateTime;
    private long createDateTime;
    
    // Default to NodeType.FILE
    FilesystemNodeInformation(NodeType nodeType) {
        super(nodeType);
    }

    FilesystemNodeInformation(String toString,NodeType nodeType) {
        super(toString, nodeType);
    }

    /**
     *
     * @param path
     * @param nodeType
     */
    public FilesystemNodeInformation(Path path, NodeType nodeType) {
        super(path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString(), nodeType);
        this.path = path.toString();
    }

    /**
     *
     * @param string
     * @param path
     * @param nodeType
     */
    public FilesystemNodeInformation(String string, Path path, NodeType nodeType) {
        super(string, nodeType);
        this.path = path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString();
    }

//    public FileNodeInformation(String displayString, NodeType type) {
//        super(displayString, type);
//    }
//
//    public FileNodeInformation(String displayString, Path path, NodeType type) {
//        super(displayString, type);
//        this.path = path.toString();
//    }
//
//    public FileNodeInformation(Path path, NodeType type) {
//        super(path.getFileName().toString(), type);
//        this.path = path.toString();
//    }
//
//    public FileNodeInformation(NodeType type) {
//        super(type);
//    }

    /**
     *
     * @return
     */

    public Path getPath() {
        return Paths.get(path);
    }

    /**
     *
     * @param path
     */
    public void setPath(Path path) {
        this.path = path.toString();
    }

    /**
     *
     * @return
     */
    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    /**
     *
     * @param lastModifiedDateTime
     */
    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    /**
     *
     * @return
     */
    public long getLastAccessedDateTime() {
        return lastAccessedDateTime;
    }

    /**
     *
     * @param lastAccessedDateTime
     */
    public void setLastAccessedDateTime(long lastAccessedDateTime) {
        this.lastAccessedDateTime = lastAccessedDateTime;
    }

    /**
     *
     * @return
     */
    public long getCreateDateTime() {
        return createDateTime;
    }

    /**
     *
     * @param createDateTime
     */
    public void setCreateDateTime(long createDateTime) {
        this.createDateTime = createDateTime;
    }
}
