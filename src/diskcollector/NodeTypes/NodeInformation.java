/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.io.Serializable;
/**
 * 
 * @author utente
 */
public class NodeInformation implements Serializable {
    private String displayString;   // Stringa da mostrare nel tree  
    private NodeType type;

    /**
     *
     */
    public NodeInformation() {
        this.displayString="NONE";
        this.type= NodeType.GENERIC;
    }

    /**
     *
     * @param displayString
     * @param type
     */
    public NodeInformation(String displayString, NodeType type) {
        this.displayString = displayString;
        this.type = type;
    }

    /**
     *
     * @param type
     */
    public NodeInformation(NodeType type) {
        this.displayString="NONE";
        this.type = type;
    }

    /**
     *
     * @param displayString
     */
    public NodeInformation(String displayString) {
        this.displayString = displayString;
        this.type= NodeType.GENERIC;
    }

    /**
     *
     * @return
     */
    public NodeType getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getDisplayString() {
        return displayString;
    }

    /**
     *
     * @param displayString
     */
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    /**
     *
     * @return
     */
    public String toString(){
        return displayString;
    }
}
