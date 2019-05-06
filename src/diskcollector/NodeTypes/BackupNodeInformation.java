/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.util.Date;

/**
 *
 * @author utente
 */
public class BackupNodeInformation extends NodeInformation {

    private Date retrievingDate = null;
    private long foldersTotal;
    private long filesTotal;
    private String details;

    public BackupNodeInformation() {
        super(NodeType.BACKUP);
    }

    public BackupNodeInformation(String displayString) {
        super(displayString, NodeType.BACKUP);
    }

    public BackupNodeInformation(String displayString, String details) {
        super(displayString, NodeType.BACKUP);
        this.details=details;
    }

    public long getFoldersTotal() {
        return foldersTotal;
    }

    public void setFoldersTotal(long foldersTotal) {
        this.foldersTotal = foldersTotal;
    }

    public long getFilesTotal() {
        return filesTotal;
    }

    public void setFilesTotal(long filesTotal) {
        this.filesTotal = filesTotal;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getRetrievingDate() {
        return retrievingDate;
    }

    public void setRetrievingDate(Date retrievingDate) {
        this.retrievingDate = retrievingDate;
    }

}
