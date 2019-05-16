/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

/**
 *
 * @author utente
 */
public interface CollectionNodeInformation {
        /**
     * Restituisce il numero complessivo dei sotto folder 
     * @return numero di sotto folder
     */
    public long getFoldersTotal() ;

    /**
     * Imposta il numero complessivo di sotto folder
     * @param foldersTotal numero di sottofolder
     */
    public void setFoldersTotal(long foldersTotal) ;

    /**
     * Restituisce il numero totale dei files nel backup
     * @return numero files del backup
     */
    public long getFilesTotal() ;
    /**
     * Imposta il numero complessivo dei files del backup
     * @param filesTotal numero di files del backup
     */
    public void setFilesTotal(long filesTotal) ;

        /**
     * Restituisce la dimensione totale dei files del folder e dei sottofolder 
     * @return dimensione
     */
    public long getSizeTotal() ;

    /**
     * Imposta la dimensione totale dei files del folder e dei sottofolder
     * @param sizeTotal dimensione
     */
    public void setSizeTotal(long sizeTotal) ;    
}
