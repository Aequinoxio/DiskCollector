/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.NodeTypes;

import java.util.logging.Logger;

/**
 * Clase generica per rappresentare un nodo senza un tipo particolare
 * @author utente
 */
public class EmptyNodeInformation extends NodeInformation{
    private static final Logger LOG = Logger.getLogger(EmptyNodeInformation.class.getName());

    /**
     * Costruttore di default
     */
    public EmptyNodeInformation() {
        super("-- EMPTY --",NodeType.EMPTY_PLACEHOLDER); // TODO: cercare una stringa migliore
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
