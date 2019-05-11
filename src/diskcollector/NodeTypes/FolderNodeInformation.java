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

    private long filesTotal;        // Numero di files totali nella subfolder
    //public static final NodeType type = NodeType.FILE;


    // Default to NodeType.FILE
    FolderNodeInformation() {
        super(NodeType.FOLDER);
    }

    FolderNodeInformation(String toString) {
        super(toString, NodeType.FOLDER);
    }

    /**
     *
     * @param path
     */
    public FolderNodeInformation(Path path) {
        super(path, NodeType.FOLDER);
        //this.path = path.toString();
    }

    /**
     *
     * @param string
     * @param path
     */
    public FolderNodeInformation(String string, Path path) {
        super(string, NodeType.FOLDER);
        //this.path = path.toString();
    }

    /**
     *
     * @return
     */
    public long getFilesSizeInFolder() {
        return filesSizeInFolder;
    }

    /**
     *
     * @param filesSizeInFolder
     */
    public void setFilesSizeInFolder(long filesSizeInFolder) {
        this.filesSizeInFolder = filesSizeInFolder;
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

    /**
     *
     * @return
     */

    public long getSubFolders() {
        return subFolders;
    }

    /**
     *
     * @param subFolders
     */
    public void setSubFolders(long subFolders) {
        this.subFolders = subFolders;
    }

    /**
     *
     * @return
     */
    public long getFilesInFolder() {
        return filesInFolder;
    }

    /**
     *
     * @param filesInFolder
     */
    public void setFilesInFolder(long filesInFolder) {
        this.filesInFolder = filesInFolder;
    }

    /**
     *
     * @return
     */
    public long getFilesTotal() {
        return filesTotal;
    }

    /**
     *
     * @param filesTotal
     */
    public void setFilesTotal(long filesTotal) {
        this.filesTotal = filesTotal;
    }

    /**
     *
     * @return
     */
    public long getSizeTotal() {
        return sizeTotal;
    }

    /**
     *
     * @param sizeTotal
     */
    public void setSizeTotal(long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

}
