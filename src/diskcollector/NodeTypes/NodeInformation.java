/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
/**
 * 
 * @author utente
 */
public class NodeInformation implements Serializable {
    private String displayString;   // Stringa da mostrare nel tree  
    private NodeType type;

    public NodeType getType() {
        return type;
    }

    public NodeInformation() {
        this.displayString="NONE";
        this.type= NodeType.GENERIC;
    }

    public NodeInformation(String displayString, NodeType type) {
        this.displayString = displayString;
        this.type = type;
    }

    public NodeInformation(NodeType type) {
        this.displayString="NONE";
        this.type = type;
    }

    public NodeInformation(String displayString) {
        this.displayString = displayString;
        this.type= NodeType.GENERIC;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    public String toString(){
        return displayString;
    }
}
