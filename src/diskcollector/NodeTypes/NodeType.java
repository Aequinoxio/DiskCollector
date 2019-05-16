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
public enum NodeType {

    /**
     * Tipo nodo Radice
     */
    ROOT,

    /**
     * Tipo nodo backup
     */
    BACKUP,

    /**
     * Tipo nodo File
     */
    FILE,

    /**
     * Tipo nodo Folder
     */
    FOLDER,

    /**
     * Tipo nodo generico
     */
    GENERIC,

    /**
     * Tipo per un nodo placeholder che non sia identificabile con nessuno degli altri tipi
     */
    EMPTY_PLACEHOLDER;
}
