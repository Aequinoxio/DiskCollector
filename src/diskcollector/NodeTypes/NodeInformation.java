/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.io.Serializable;
import java.util.logging.Logger;
/**
 * 
 * @author utente
 */
public class NodeInformation implements Serializable {
    private static final Logger LOG = Logger.getLogger(NodeInformation.class.getName());
    private String displayString;   // Stringa da mostrare nel tree  
    private NodeType type;

    /**
     * Costruttore generico
     */
    public NodeInformation() {
        this.displayString="NONE";
        this.type= NodeType.GENERIC;
    }

    /**
     * Costruttore
     * Viene impostato il valore di default per la stringa da mostrare  e per il nodo
     * @param displayString Stringa da mostrare
     * @param type Tipo di noto
     */
    public NodeInformation(String displayString, NodeType type) {
        this.displayString = displayString;
        this.type = type;
    }

    /**
     * Costruttore
     * Viene impostato il valore di default per la stringa da mostrare 
     * @param type Tipo di nodo
     */
    public NodeInformation(NodeType type) {
        this.displayString="NONE";
        this.type = type;
    }

    /**
     * Costruttore
     * Viene impostato il tipo nodo generico di default
     * @param displayString 
     */
    public NodeInformation(String displayString) {
        this.displayString = displayString;
        this.type= NodeType.GENERIC;
    }

    /**
     * Restituisce il tipo di nodo
     * @return Tipo di nodo
     */
    public NodeType getType() {
        return type;
    }

    /**
     * Restituisce la stringa da mostrare
     * @return Stringa da mostrare
     */
    public String getDisplayString() {
        return displayString;
    }

    /**
     * Imposta la stringa da mostrare sovrascrivendo quella usada nel costruttore
     * @param displayString Stringa da mostrare
     */
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    /**
     * Restituisce la stringa da nostrare
     * @return Strinda da mostrare
     */
    @Override
    public String toString(){
        return displayString;
    }
}
