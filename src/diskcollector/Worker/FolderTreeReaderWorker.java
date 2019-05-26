package diskcollector.Worker;

import diskcollector.NodeTypes.BackupNodeInformation;
import diskcollector.NodeTypes.FileNodeInformation;
import diskcollector.NodeTypes.FolderNodeInformation;
import diskcollector.NodeTypes.NodeInformation;
import diskcollector.NodeTypes.NodeType;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author utente
 */
public class FolderTreeReaderWorker extends SwingWorker<DefaultMutableTreeNode, String> {
    private static final Logger LOG = Logger.getLogger(FolderTreeReaderWorker.class.getName());

    Path path;
    DefaultMutableTreeNode topNode;
    DefaultMutableTreeNode topNodeCancelledThread;
    JTextArea txtLogArea;
    FolderTreeReaderWorker mySelf = this;
    // WorkerCallBack workerCallBack;
    private final Comparator< DefaultMutableTreeNode> tnc = new Comparator< DefaultMutableTreeNode>() {
        @Override
        public int compare(DefaultMutableTreeNode a, DefaultMutableTreeNode b) {
            NodeInformation aNode = (NodeInformation) a.getUserObject();
            NodeInformation bNode = (NodeInformation) b.getUserObject();

            // Se è un folder comunque lo ordino per primo
            if (aNode.getType() == NodeType.FILE && bNode.getType() == NodeType.FOLDER) {
                return 1;
            } else if (aNode.getType() == NodeType.FOLDER && bNode.getType() == NodeType.FILE) {
                return -1;
            } else {
                return compareString(a, b);
            }

//            //Sort the parent and child nodes separately:
//            if (a.isLeaf() && !b.isLeaf()) {
//
//                    return 1;
//                
//            } else if (!a.isLeaf() && b.isLeaf()) {         
//                
//                    return -1;
//                
//            } else {
//
//                return compareString(a, b);
//            }
        }

        private int compareString(DefaultMutableTreeNode a, DefaultMutableTreeNode b) {
            String sa = a.getUserObject().toString();
            String sb = b.getUserObject().toString();
            return sa.compareToIgnoreCase(sb);
        }
    };

    /**
     *
     * @param path
     * @param topNode
     */
    public FolderTreeReaderWorker(Path path, DefaultMutableTreeNode topNode) {
        this.path = path;
        this.topNode = topNode;
        this.topNodeCancelledThread = topNode;

    }

    private DefaultMutableTreeNode readDirectory(Path path, DefaultMutableTreeNode topNode) {

        final DefaultMutableTreeNode albero = topNode;
        final Stack<DefaultMutableTreeNode> directoryStack = new Stack<>();

        try {
            Files.walkFileTree(path, new FileVisitor<Path>() {

                DefaultMutableTreeNode directoryTemp = null;

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    if (FolderTreeReaderWorker.this.isCancelled()) {
                        return FileVisitResult.TERMINATE;
                    }

                    //directoryTemp.add(new DefaultMutableTreeNode(file.getFileName().toString()));
                    FileNodeInformation fileNodeInformation = new FileNodeInformation(file);

                    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                    fileNodeInformation.setCreateDateTimeMillis(attr.creationTime().toMillis());
                    fileNodeInformation.setLastModifiedDateTimeMillis(attr.lastModifiedTime().toMillis());
                    fileNodeInformation.setLastAccessedDateTimeMillis(attr.lastAccessTime().toMillis());
                    fileNodeInformation.setSize(attr.size());

                    directoryTemp.add(new DefaultMutableTreeNode(fileNodeInformation));

                    updateFilesInFolderInformation((FolderNodeInformation) directoryTemp.getUserObject(), file.toFile());

                    //publish(file.toString());  // Commentato in quanto troppo oneroso mostrare i singoli file in una textarea
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Skip folders that can't be traversed

                    //System.out.println("skipped: " + file + " (" + exc + ")");
                    publish(" --- Skipped: " + file + " (" + exc + ")");
                    
                    // Aggiorno l'upfolder                         
                    if (directoryTemp == null) {
                        directoryTemp = albero;
                    }
                    
                    DefaultMutableTreeNode TEMP;
                    // Se è una directory creo un nodo fittizio che rappresenta la folder non acceduta
                    if (file.toFile().isDirectory()) {
                        // Se il nodo è una directory allora mi comporto come in postVisit ma con un nodo ad hoc
                        TEMP = new DefaultMutableTreeNode(new FolderNodeInformation(file.getFileName().toString() + " -- Skipped --", file.getFileName()));

                        
                        NodeInformation upFolder = ((NodeInformation) directoryTemp.getUserObject());
                        if (upFolder.getType() == NodeType.FOLDER) {                            
                            updateUpFolder((FolderNodeInformation) upFolder, (FolderNodeInformation) TEMP.getUserObject());
                        } else if (upFolder.getType() == NodeType.BACKUP) {  
                            updateBackupNode((BackupNodeInformation) upFolder, (FolderNodeInformation) TEMP.getUserObject());
                        }

                    } else {
                        TEMP = new DefaultMutableTreeNode(new FileNodeInformation(file.getFileName().toString() + " -- Skipped --", file.getFileName()));

                        // Aggiorno il folder TODO: DA TESTARE se il primo parametro è corretto così. Un possibile bug è se l'upfolder è un backupnode 
                        // Non dovrebbe essere possibile visto che aggiungo solo le folder e non i fingoli files ma va testato.
                        updateFilesInFolderInformation((FolderNodeInformation) directoryTemp.getUserObject(), file.toFile());
                    }
                    directoryTemp.add(TEMP);
                    return FileVisitResult.CONTINUE;
                }

                @Override

                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                    if (exc != null) {
                        publish(" --- Problemi ad attraversare: " + dir + " (" + exc + ")");
                    } else {
                        NodeInformation upFolder = (NodeInformation) ((DefaultMutableTreeNode) directoryTemp.getParent()).getUserObject();
                        if (upFolder.getType() == NodeType.FOLDER) {
                            updateUpFolder((FolderNodeInformation) upFolder, (FolderNodeInformation) directoryTemp.getUserObject());
                        } else if (upFolder.getType() == NodeType.BACKUP) {  // TODO: Gestire il tipo nodo backup e folder come container e usare i metodi base identici per aggiornare i totali
                            updateBackupNode((BackupNodeInformation) upFolder, (FolderNodeInformation) directoryTemp.getUserObject());
                        }
                        // Vedere come usare questa struttura per eliminare lo stack
                        directoryTemp = (DefaultMutableTreeNode) directoryTemp.getParent();
                    }
                    // Fa la stessa cosa di directoryTemp.getParent();
                    directoryStack.pop();  // Se esco dalla subdir la rimuovo dallo stack diquelle da visitare                    

                    publish(dir.toString() + "\n");

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path folderPath, BasicFileAttributes bfa) throws IOException {

                    DefaultMutableTreeNode ultimoFiglio;

                    FolderNodeInformation foldernodeInformation = new FolderNodeInformation(folderPath);

                    BasicFileAttributes attr = Files.readAttributes(folderPath, BasicFileAttributes.class);

                    foldernodeInformation.setCreateDateTimeMillis(attr.creationTime().toMillis());
                    foldernodeInformation.setLastModifiedDateTimeMillis(attr.lastModifiedTime().toMillis());
                    foldernodeInformation.setLastAccessedDateTimeMillis(attr.lastAccessTime().toMillis());

                    directoryTemp = new DefaultMutableTreeNode(foldernodeInformation);
                    if (directoryStack.empty()) {
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

                /**
                 * Aggiorno le informazioni sulla folder - incremento il numero
                 * di files nella folder - incremento la size della folder come
                 * somma delle dimensioni dei file direttamente figli della
                 * folder
                 *
                 * @param folderNode
                 */
                private void updateFilesInFolderInformation(FolderNodeInformation folderNode, File file) {
                    long infoTemp = folderNode.getFirstFilesInFolder() + 1;    // File diretti
                    folderNode.setFirstFilesInFolder(infoTemp);

                    // Files totali
                    folderNode.setFilesTotal(folderNode.getFilesTotal() + 1);  // Aggiorno anche questo valore che verrà utilizzato nella post visit per aggiornare l'upfolder

                    // Dimensioni dei files nella folder
                    infoTemp = folderNode.getFirstFilesSizeInFolder();
                    infoTemp += file.length();
                    folderNode.setFirstFilesSizeInFolder(infoTemp);

                    // Dimensioni totali dei files
                    folderNode.setSizeTotal(folderNode.getSizeTotal() + file.length());
                }

                /**
                 * Aggiorna l'upFolder con le info della currentFolder
                 *
                 * @param upFolder
                 * @param currentFolder
                 */
                private void updateUpFolder(FolderNodeInformation upFolder, FolderNodeInformation currentFolder) {
                    long infoTemp;

                    // File totali
                    infoTemp = upFolder.getFilesTotal() + currentFolder.getFilesTotal();
                    upFolder.setFilesTotal(infoTemp);

                    // Dimensioni totali
                    infoTemp = upFolder.getSizeTotal() + currentFolder.getSizeTotal();
                    upFolder.setSizeTotal(infoTemp);

                    // Subfolder dirette
                    upFolder.setFirstSubFolders(upFolder.getFirstSubFolders() + 1);

                    // Folder totali
                    upFolder.setFoldersTotal(1 + upFolder.getFoldersTotal() + currentFolder.getFoldersTotal());

                }

                private void updateBackupNode(BackupNodeInformation upFolder, FolderNodeInformation currentFolder) {
                    upFolder.setFilesTotal(upFolder.getFilesTotal() + currentFolder.getFilesTotal());
                    upFolder.setFoldersTotal(upFolder.getFoldersTotal() + currentFolder.getFoldersTotal());
                    upFolder.setSizeTotal(upFolder.getSizeTotal() + currentFolder.getSizeTotal());
                }
            }
            );
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
                sortNodes(node);   //selection sort
            }
        }
    }

    private void sortNodes(DefaultMutableTreeNode parent) {
        int n = parent.getChildCount();

        List< DefaultMutableTreeNode> children = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            children.add((DefaultMutableTreeNode) parent.getChildAt(i));
        }
        Collections.sort(children, tnc); //iterative merge sort
        parent.removeAllChildren();
        children.forEach((node) -> {
            parent.add(node);
        });
    }

    /////////// SWING WORKER METHODS ///////////
    /**
     *
     * @return @throws Exception
     */
    @Override
    protected DefaultMutableTreeNode doInBackground() throws Exception {
        readDirectory(path, topNode);
        if (isCancelled()) {
            return topNodeCancelledThread;
        } else {
            return topNode;
        }
    }

    /**
     *
     * @param chunks
     */
    @Override
    protected void process(List<String> chunks) {
        chunks.forEach((fileProcessed) -> {
            txtLogArea.append(fileProcessed);
        });
    }

    /**
     *
     */
    @Override
    protected void done() {
        super.done();
        // Viene lanciato un PropertyChangeEvent con valore SwingWorker.StateValue.DONE
        // Gestisco la UI nel relativo listener della dialog parent 
    }

    /**
     *
     * @param area
     */
    public void setTextAreaLog(JTextArea area) {
        this.txtLogArea = area;
    }
}
