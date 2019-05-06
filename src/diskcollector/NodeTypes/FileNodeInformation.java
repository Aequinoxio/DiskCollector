/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 * @author utente
 */
public class FileNodeInformation extends NodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un file
    private String path;              // 
    private Date lastModifiedDateTime = null;
    private Date lastAccessedDateTime = null;
    private Date createDateTime = null;
    private long size ;
    
    // Default to NodeType.FILE
    FileNodeInformation() {
        super(NodeType.FILE);
    }

    FileNodeInformation(String toString) {
        super(toString, NodeType.FILE);
    }

    public FileNodeInformation(Path path) {
        super(path.getFileName().toString(), NodeType.FILE);
        this.path = path.toString();
    }

    public FileNodeInformation(String string, Path path) {
        super(string, NodeType.FILE);
        this.path = path.toString();
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

    public Date getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(Date lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public Date getLastAccessedDateTime() {
        return lastAccessedDateTime;
    }

    public void setLastAccessedDateTime(Date lastAccessedDateTime) {
        this.lastAccessedDateTime = lastAccessedDateTime;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
