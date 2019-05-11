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

    public FilesystemNodeInformation(Path path, NodeType nodeType) {
        super(path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString(), nodeType);
        this.path = path.toString();
    }

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

    public Path getPath() {
        return Paths.get(path);
    }

    public void setPath(Path path) {
        this.path = path.toString();
    }

    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public long getLastAccessedDateTime() {
        return lastAccessedDateTime;
    }

    public void setLastAccessedDateTime(long lastAccessedDateTime) {
        this.lastAccessedDateTime = lastAccessedDateTime;
    }

    public long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(long createDateTime) {
        this.createDateTime = createDateTime;
    }
}
