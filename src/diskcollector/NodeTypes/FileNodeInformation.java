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
//    private String path;              // 
//    private long lastModifiedDateTime;
//    private long lastAccessedDateTime;
//    private long createDateTime;
    private long size ;
    
    // Default to NodeType.FILE
    FileNodeInformation() {
        super(NodeType.FILE);
    }

    FileNodeInformation(String toString) {
        super(toString, NodeType.FILE);
    }

    /**
     *
     * @param path
     */
    public FileNodeInformation(Path path) {
        super(path, NodeType.FILE);
        //this.path = path.toString();
    }

    /**
     *
     * @param string
     * @param path
     */
    public FileNodeInformation(String string, Path path) {
        super(string, path, NodeType.FILE);
        //this.path = path.toString();
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

//    public Path getPath() {
//        return Paths.get(path);
//    }
//
//    public void setPath(Path path) {
//        this.path = path.toString();
//    }
//
//    public long getLastModifiedDateTime() {
//        return lastModifiedDateTime;
//    }
//
//    public void setLastModifiedDateTime(long lastModifiedDateTime) {
//        this.lastModifiedDateTime = lastModifiedDateTime;
//    }
//
//    public long getLastAccessedDateTime() {
//        return lastAccessedDateTime;
//    }
//
//    public void setLastAccessedDateTime(long lastAccessedDateTime) {
//        this.lastAccessedDateTime = lastAccessedDateTime;
//    }
//
//    public long getCreateDateTime() {
//        return createDateTime;
//    }
//
//    public void setCreateDateTime(long createDateTime) {
//        this.createDateTime = createDateTime;
//    }

    /**
     *
     * @return
     */

    public long getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

}
