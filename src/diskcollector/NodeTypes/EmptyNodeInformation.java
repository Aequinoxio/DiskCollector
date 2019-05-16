/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

/**
 * Clase generica per rappresentare un nodo senza un tipo particolare
 * @author utente
 */
public class EmptyNodeInformation extends NodeInformation{

    /**
     * Costruttore di default
     */
    public EmptyNodeInformation() {
        super("-- VUOTO --",NodeType.EMPTY_PLACEHOLDER); // TODO: cercare una stringa migliore
    }

    /**
     * Costruttore
     * Imposta la stringa da mostrare
     * @param displayString stringa da mostrare
     */
    public EmptyNodeInformation(String displayString) {
        super(displayString,NodeType.EMPTY_PLACEHOLDER);
    }
    
}
