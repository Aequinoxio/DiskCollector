/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import diskcollector.NodeTypes.NodeInformation;
import diskcollector.NodeTypes.FileNodeInformation;
import diskcollector.NodeTypes.FolderNodeInformation;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

// TODO: implementare il worker in modo che restituisca un progress status ed una stringa con il path
//       appena letto
/**
 *
 * @author utente
 */
public class FolderTreeReaderWorker extends SwingWorker<DefaultMutableTreeNode, String> {

    Path path;
    DefaultMutableTreeNode topNode;
    DefaultMutableTreeNode topNodeCancelledThread;
    JTextArea txtLogArea;
    FolderTreeReaderWorker mySelf = this;

    public FolderTreeReaderWorker(Path path, DefaultMutableTreeNode topNode) {
        this.path = path;
        this.topNode = topNode;
        this.topNodeCancelledThread = topNode; // TODO: da modificare, così crea un riferimento che ritorna comunque un nodo aggiornato
    }

    private DefaultMutableTreeNode readDirectory(Path path, DefaultMutableTreeNode topNode) {

        final DefaultMutableTreeNode albero = topNode;
        final Stack directoryStack = new Stack<DefaultMutableTreeNode>();

        try {
            Files.walkFileTree(path, new FileVisitor<Path>() {
                DefaultMutableTreeNode directoryTemp = null;

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    if (FolderTreeReaderWorker.this.isCancelled()) {
                        // System.out.println("CANCELLATO");
                        return FileVisitResult.TERMINATE;
                    }

                    //directoryTemp.add(new DefaultMutableTreeNode(file.getFileName().toString()));
                    FileNodeInformation fileNodeInformation = new FileNodeInformation(file);
                    //fileNodeInformation.setPath(file);
                    fileNodeInformation.setLastModifiedDateTime(new Date(file.toFile().lastModified()));
                    directoryTemp.add(new DefaultMutableTreeNode(fileNodeInformation));

                    publish(file.toString());

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Skip folders that can't be traversed

                    System.out.println("skipped: " + file + " (" + exc + ")");

                    DefaultMutableTreeNode TEMP = new DefaultMutableTreeNode(new NodeInformation(file.getFileName() + " -- Skipped --"));
                    // Se è una directory creo un nodo fittizio che rappresenta la folder non acceduta
                    if (file.toFile().isDirectory()) {
                        TEMP.add(new DefaultMutableTreeNode(new NodeInformation()));
                    }
                    directoryTemp.add(TEMP);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                    if (exc != null) {
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    } else {
                        if (directoryTemp.getDepth() == 0) {
                            //directoryTemp.add(new DefaultMutableTreeNode(new EmptyNodeInformation())); // TODO: Verificare se va bene
                        }

                        // Vedere come usare questa struttura per eliminare lo stack
                        directoryTemp = (DefaultMutableTreeNode) directoryTemp.getParent();
                    }
                    // Fa la stessa cosa di directoryTemp.getParent();
                    directoryStack.pop();  // Se esco dalla subdir la rimuovo dallo stack diquelle da visitare
                    // Ignore errors traversing a folder

                    System.out.println(dir.toString());
                    publish(dir.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path t, BasicFileAttributes bfa) throws IOException {
                    //System.out.println("Directory: " + t);
                    DefaultMutableTreeNode ultimoFiglio;

                    FolderNodeInformation foldernodeInformation = new FolderNodeInformation(t);
                    //filenodeInformation.setPath(t);
                    foldernodeInformation.setLastModifiedDateTime(new Date(t.toFile().lastModified()));

                    directoryTemp = new DefaultMutableTreeNode(foldernodeInformation);
                    // if (albero.getDepth() == 0) {
                    if (directoryStack.empty()) {
                        //ultimoFiglio = (DefaultMutableTreeNode) albero.getRoot();
                        ultimoFiglio = directoryTemp;
                        albero.add(ultimoFiglio);
                        directoryStack.push(ultimoFiglio);
                    } else {
                        ultimoFiglio = (DefaultMutableTreeNode) directoryStack.peek();
                        directoryStack.push(directoryTemp);
                        ultimoFiglio.add(directoryTemp);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }
        return albero;
    }

    /**
     * Ordina un DefaultMutableTreeNode Fonte:
     * https://java-swing-tips.blogspot.com/2013/09/how-to-sort-jtree-nodes.html
     *
     * @param root
     */
    public void sortTree(DefaultMutableTreeNode root) {
        Enumeration e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (!node.isLeaf()) {
                sort3(node);   //selection sort
                //sort3(node); //JDK 1.6.0: iterative merge sort
                //sort3(node); //JDK 1.7.0: TimSort
            }
        }
    }

    private void sort3(DefaultMutableTreeNode parent) {
        int n = parent.getChildCount();
        //@SuppressWarnings("unchecked")
        //Enumeration< DefaultMutableTreeNode> e = parent.children();
        //ArrayList< DefaultMutableTreeNode> children = Collections.list(e);
        List< DefaultMutableTreeNode> children = new ArrayList< DefaultMutableTreeNode>(n);
        for (int i = 0; i < n; i++) {
            children.add((DefaultMutableTreeNode) parent.getChildAt(i));
        }
        Collections.sort(children, tnc); //iterative merge sort
        parent.removeAllChildren();
        for (MutableTreeNode node : children) {
            parent.add(node);
        }
    }

    private Comparator< DefaultMutableTreeNode> tnc = new Comparator< DefaultMutableTreeNode>() {
        @Override
        public int compare(DefaultMutableTreeNode a, DefaultMutableTreeNode b) {
            //Sort the parent and child nodes separately:
            if (a.isLeaf() && !b.isLeaf()) {
                return 1;
            } else if (!a.isLeaf() && b.isLeaf()) {
                return -1;
            } else {
                String sa = a.getUserObject().toString();
                String sb = b.getUserObject().toString();
                return sa.compareToIgnoreCase(sb);
            }
        }
    };

    /////////// SWING WORKER METHODS ///////////
    @Override
    protected DefaultMutableTreeNode doInBackground() throws Exception {
        readDirectory(path, topNode);
        if (isCancelled()) {
            return topNodeCancelledThread;
        } else {
            return topNode;
        }
    }

    @Override
    protected void process(List<String> chunks) {
        for (String fileProcessed : chunks) {
            txtLogArea.append(fileProcessed + "\n");
//            System.out.println(fileProcessed);
        }

        System.out.println(chunks.size());
    }

    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.

    }

    public void setTextAreaLog(JTextArea area) {
        this.txtLogArea = area;
    }
}
