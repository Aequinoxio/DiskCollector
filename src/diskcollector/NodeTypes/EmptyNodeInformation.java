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
public class EmptyNodeInformation extends NodeInformation{

    public EmptyNodeInformation() {
        super("-- VUOTO --",NodeType.EMPTY_PLACEHOLDER); // TODO: cercare una stringa migliore
    }

    public EmptyNodeInformation(String displayString) {
        super(displayString,NodeType.EMPTY_PLACEHOLDER);
    }
    
}
