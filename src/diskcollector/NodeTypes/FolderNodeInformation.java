/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 * @author utente
 */
public class FolderNodeInformation extends NodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un folder
    private String path;              // 
    private Date lastModifiedDateTime = null;
    private Date lastAccessedDateTime = null;
    private Date createDateTime = null;
    private long sizeTotal ;
    private long subfolders;
    private long filesInFolder;
    private long filesTotal;
    //public static final NodeType type = NodeType.FILE;


    // Default to NodeType.FILE
    FolderNodeInformation() {
        super(NodeType.FOLDER);
    }

    FolderNodeInformation(String toString) {
        super(toString, NodeType.FOLDER);
    }

    public FolderNodeInformation(Path path) {
        super(path.getFileName() == null ? path.getRoot().toString():path.getFileName().toString(), NodeType.FOLDER);
        this.path = path.toString();
    }

    public FolderNodeInformation(String string, Path path) {
        super(string, NodeType.FOLDER);
        this.path = path.toString();
    }

////    public FolderNodeInformation(String displayString, NodeType type) {
////        super(displayString, type);
////    }
////
////    public FolderNodeInformation(String displayString, Path path, NodeType type) {
////        super(displayString, type);
////        this.path = path.toString();
////    }
////
////    public FolderNodeInformation(Path path, NodeType type) {
////        super(path.getFileName().toString(), type);
////        this.path = path.toString();
////    }
//
//    public FolderNodeInformation(NodeType type) {
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
    public long getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(long subfolders) {
        this.subfolders = subfolders;
    }

    public long getFilesInFolder() {
        return filesInFolder;
    }

    public void setFilesInFolder(long filesInFolder) {
        this.filesInFolder = filesInFolder;
    }

    public long getFilesTotal() {
        return filesTotal;
    }

    public void setFilesTotal(long filesTotal) {
        this.filesTotal = filesTotal;
    }

    public long getSizeTotal() {
        return sizeTotal;
    }

    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

}
