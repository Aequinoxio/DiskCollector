/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Date;

/**
 *
 * @author utente
 */
public class FolderNodeInformation extends FilesystemNodeInformation {

    // Informazioni nel caso in cui l'oggetto del tree sia un folder
//    private String path;              // 
//    private long lastModifiedDateTime;
//    private long lastAccessedDateTime;
//    private long createDateTime;
    private long sizeTotal ;
    private long subFolders;
    private long filesInFolder;
    private long filesSizeInFolder;

    public long getFilesSizeInFolder() {
        return filesSizeInFolder;
    }

    public void setFilesSizeInFolder(long filesSizeInFolder) {
        this.filesSizeInFolder = filesSizeInFolder;
    }
    private long filesTotal;        // Numero di files totali nella subfolder
    //public static final NodeType type = NodeType.FILE;


    // Default to NodeType.FILE
    FolderNodeInformation() {
        super(NodeType.FOLDER);
    }

    FolderNodeInformation(String toString) {
        super(toString, NodeType.FOLDER);
    }

    public FolderNodeInformation(Path path) {
        super(path, NodeType.FOLDER);
        //this.path = path.toString();
    }

    public FolderNodeInformation(String string, Path path) {
        super(string, NodeType.FOLDER);
        //this.path = path.toString();
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

    public long getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(long subFolders) {
        this.subFolders = subFolders;
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
