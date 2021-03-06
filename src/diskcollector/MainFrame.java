package diskcollector;

import SimpleAESEncryptionLib.*;
import diskcollector.NodeTypes.BackupNodeInformation;
import diskcollector.NodeTypes.CollectionNodeInformation;
import diskcollector.NodeTypes.FileNodeInformation;
import diskcollector.NodeTypes.FilesystemNodeInformation;
import diskcollector.NodeTypes.FolderNodeInformation;
import diskcollector.NodeTypes.NodeInformation;
import diskcollector.NodeTypes.NodeType;
import diskcollector.UI.DlgAbout;
import diskcollector.UI.DlgFilesystemAction;
import diskcollector.UI.DlgSearchAllResults;
import diskcollector.UI.DlgSwingWorkerLog;
import diskcollector.UI.RequestFocusListener;
import diskcollector.Worker.FilesystemActionWorker;
import diskcollector.Worker.FolderTreeReaderWorker;
import java.awt.Color;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author utente
 */
public class MainFrame extends javax.swing.JFrame implements TreeSelectionListener {

    DefaultMutableTreeNode m_selectedNode; // Nodo selezionato nel jtree
    private Enumeration m_searchingNodes;  // Enumeration per la ricerca, viene messo a null ogni volta che c'è una modifica sui dati dell'albero
    //private final String latestDBSaveParh = "";
    private static final Logger LOG = Logger.getLogger(MainFrame.class.getName());

    private Handler fileHandler = null;

    private String m_msgStatusBarDB;
    private String m_msgStatusBarUserPassword;

    /**
     * Creates new form MainFrame
     *
     */
    public MainFrame() {
        initComponents();

        // Aggiungo un file handler al logger
        // Check per eventuali problemi      
        try {
            fileHandler = new FileHandler(Constants.LOGFILENAME, 10485760, 1, true);
            fileHandler.setFormatter(new SimpleFormatter());

            if (Constants.getInstance().isExceptionsLogged()) {
                LOG.addHandler(fileHandler);
            }
            
        } catch (IOException ex) {
            showCustomLogDialog("Problema con la creazione del log file: " + Constants.LOGFILENAME,"Eccezione:", ex.toString());           
            LOG.log(Level.SEVERE, null, ex);
            
        } catch (SecurityException ex) {
            showCustomLogDialog("Problema con la creazione del log file: " + Constants.LOGFILENAME,"Eccezione:", ex.toString());            
            LOG.log(Level.SEVERE, null, ex);
            
        }

        LOG.log(Level.INFO, "Application started");

        if (Constants.getInstance().isDBEncrypted()) {
            m_msgStatusBarDB = "Il DB verrà salvato crittografato";
        } else {
            m_msgStatusBarDB = "Il DB non verrà salvato crittografato";
        }

        if (Constants.getInstance().getUserPassword() != null) {
            m_msgStatusBarUserPassword = "La password è impostata.";
        } else {
            m_msgStatusBarUserPassword = "La password non è impostata.";
        }

        updateStatusBar(null);

        mnuEncryptDB.setSelected(Constants.getInstance().isDBEncrypted());
        mnuLogTofile.setSelected(Constants.getInstance().isExceptionsLogged());

//        mnuEncryptDB.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                if (mnuEncryptDB.isSelected()) {
//                    Constants.getInstance().setDBEncrypted(true);
//                } else {
//                    Constants.getInstance().setDBEncrypted(false);
//                }
//
//                m_msgStatusBarDB = Constants.getInstance().isDBEncrypted()
//                        ? "Il Database verrà cifrato al salvataggio"
//                        : "Il Database non verrà cifrato";
//
//                updateStatusBar(null);
//            }
//        });
        //UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        // Inizializzo il tree
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(new NodeInformation(Constants.ROOT_STRING, NodeType.ROOT));
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(topNode);
        directoryTree.setModel(defaultTreeModel);
        directoryTree.addTreeSelectionListener(this);
        directoryTree.setCellRenderer(new NodeCellRenderer());
        NodeCellRenderer renderer = (NodeCellRenderer) directoryTree.getCellRenderer();
        renderer.setTextSelectionColor(Color.white);

        // Uso la stessa azione del bottone cerca quando premo invio
        txtSearchText.addActionListener(this::btnSearchActionPerformed);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu = new javax.swing.JPopupMenu();
        popMnuRename = new javax.swing.JMenuItem();
        btnDeleteNodeTree = new javax.swing.JButton();
        btnDeleteNodeTre = new javax.swing.JButton();
        txtSearchText = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnViewLog = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        directoryTree = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        btnSaveTree = new javax.swing.JButton();
        btnLoadTree = new javax.swing.JButton();
        btnNewBackup = new javax.swing.JButton();
        btnInsertNodeTree = new javax.swing.JButton();
        pnlStatusBar = new javax.swing.JPanel();
        lblStatusBar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblDBSavedType = new javax.swing.JLabel();
        lblPasswordSet = new javax.swing.JLabel();
        btnSearchAll = new javax.swing.JButton();
        ckbUppercaseOnly = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuSaveDB = new javax.swing.JMenuItem();
        mnuLoadDB = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuSetPassword = new javax.swing.JMenuItem();
        mnuEncryptDB = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuNewBackupSet = new javax.swing.JMenuItem();
        mnuModifyBackupSet = new javax.swing.JMenuItem();
        mnuDeleteBackupset = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnuInsertTree = new javax.swing.JMenuItem();
        mnuDeleteTree = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuDeleteAll = new javax.swing.JMenuItem();
        mnuTools = new javax.swing.JMenu();
        mnuLogTofile = new javax.swing.JCheckBoxMenuItem();
        mnuViewLogFile = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnuKeyLength = new javax.swing.JMenuItem();
        mnuAbout = new javax.swing.JMenu();
        mnuAboutItem = new javax.swing.JMenuItem();

        popMnuRename.setText("Rinomina e aggiorna");
        popMnuRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popMnuRenameActionPerformed(evt);
            }
        });
        popMenu.add(popMnuRename);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DiskCollector");
        setMinimumSize(new java.awt.Dimension(970, 800));
        setPreferredSize(new java.awt.Dimension(970, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnDeleteNodeTree.setText("Cancella ramo");
        btnDeleteNodeTree.setToolTipText("Cancella il ramo selezionato");
        btnDeleteNodeTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNodeTreeActionPerformed(evt);
            }
        });

        btnDeleteNodeTre.setText("Cancella Backup set");
        btnDeleteNodeTre.setToolTipText("Cancella il backup set selezionato");
        btnDeleteNodeTre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNodeTreActionPerformed(evt);
            }
        });

        txtSearchText.setText("Cerca");
        txtSearchText.setToolTipText("Usa una regexp per cercare nel DB");
        txtSearchText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchTextFocusGained(evt);
            }
        });

        btnSearch.setText("Cerca");
        btnSearch.setToolTipText("Cerca la prima ocorrenza nel sottoramo");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnViewLog.setText("Cancella tutto");
        btnViewLog.setToolTipText("Cancella tutti i backup sets ed i sottorami");
        btnViewLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewLogActionPerformed(evt);
            }
        });

        jSplitPane1.setMinimumSize(new java.awt.Dimension(640, 480));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 580));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 580));

        directoryTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                directoryTreeMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(directoryTree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(580, 520));

        txtDetails.setEditable(false);
        txtDetails.setColumns(20);
        txtDetails.setLineWrap(true);
        txtDetails.setRows(5);
        jScrollPane2.setViewportView(txtDetails);

        jSplitPane1.setRightComponent(jScrollPane2);

        btnSaveTree.setText("Salva DB");
        btnSaveTree.setToolTipText("Salva tutto il DB cifrandolo se è stato specificato");
        btnSaveTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTreeActionPerformed(evt);
            }
        });

        btnLoadTree.setText("Carica DB");
        btnLoadTree.setToolTipText("Carica il DB, se cifrato prova ad usare la password impostata");
        btnLoadTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadTreeActionPerformed(evt);
            }
        });

        btnNewBackup.setText("Nuovo Backup set");
        btnNewBackup.setToolTipText("Inserisce un nuovo backup set");
        btnNewBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBackupActionPerformed(evt);
            }
        });

        btnInsertNodeTree.setText("Inserisci ramo");
        btnInsertNodeTree.setToolTipText("Inserisce un ramo nel backup set");
        btnInsertNodeTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertNodeTreeActionPerformed(evt);
            }
        });

        pnlStatusBar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4), javax.swing.BorderFactory.createEtchedBorder()));

        lblStatusBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        lblStatusBar.setOpaque(true);

        javax.swing.GroupLayout pnlStatusBarLayout = new javax.swing.GroupLayout(pnlStatusBar);
        pnlStatusBar.setLayout(pnlStatusBarLayout);
        pnlStatusBarLayout.setHorizontalGroup(
            pnlStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusBarLayout.createSequentialGroup()
                .addComponent(lblStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlStatusBarLayout.setVerticalGroup(
            pnlStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4), javax.swing.BorderFactory.createEtchedBorder()));

        lblDBSavedType.setBackground(java.awt.Color.red);
        lblDBSavedType.setToolTipText("Il DB verrà cifrato al salvataggio?");
        lblDBSavedType.setOpaque(true);
        lblDBSavedType.setPreferredSize(new java.awt.Dimension(15, 15));

        lblPasswordSet.setBackground(java.awt.Color.red);
        lblPasswordSet.setForeground(java.awt.Color.lightGray);
        lblPasswordSet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPasswordSet.setToolTipText("La password per salvare o aprire il DB cifrato è impostata?");
        lblPasswordSet.setMaximumSize(new java.awt.Dimension(15, 15));
        lblPasswordSet.setMinimumSize(new java.awt.Dimension(5, 5));
        lblPasswordSet.setOpaque(true);
        lblPasswordSet.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPasswordSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDBSavedType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPasswordSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDBSavedType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSearchAll.setText("Cerca tutto");
        btnSearchAll.setToolTipText("Cerca in tutto il sottoramo");
        btnSearchAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchAllActionPerformed(evt);
            }
        });

        ckbUppercaseOnly.setText("Maiuscole");
        ckbUppercaseOnly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbUppercaseOnlyActionPerformed(evt);
            }
        });

        mnuFile.setText("File");

        mnuSaveDB.setText("Salva tutto il DB");
        mnuSaveDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveDBActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSaveDB);

        mnuLoadDB.setText("Carica DB");
        mnuLoadDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoadDBActionPerformed(evt);
            }
        });
        mnuFile.add(mnuLoadDB);
        mnuFile.add(jSeparator3);

        mnuSetPassword.setText("Imposta password");
        mnuSetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSetPasswordActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSetPassword);

        mnuEncryptDB.setSelected(true);
        mnuEncryptDB.setText("DB Cifrato");
        mnuEncryptDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEncryptDBActionPerformed(evt);
            }
        });
        mnuFile.add(mnuEncryptDB);
        mnuFile.add(jSeparator4);

        mnuExit.setText("Esci");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        jMenuBar1.add(mnuFile);

        mnuEdit.setText("Edit");

        mnuNewBackupSet.setText("Nuovo backup set");
        mnuNewBackupSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBackupActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuNewBackupSet);

        mnuModifyBackupSet.setText("Modifica backup set");
        mnuModifyBackupSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popMnuRenameActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuModifyBackupSet);

        mnuDeleteBackupset.setText("Cancella backup set");
        mnuDeleteBackupset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNodeTreActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuDeleteBackupset);
        mnuEdit.add(jSeparator6);

        mnuInsertTree.setText("Inserisci ramo");
        mnuInsertTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertNodeTreeActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuInsertTree);

        mnuDeleteTree.setText("Cancella ramo");
        mnuDeleteTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNodeTreeActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuDeleteTree);
        mnuEdit.add(jSeparator2);

        mnuDeleteAll.setText("Cancella tutto");
        mnuDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewLogActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuDeleteAll);

        jMenuBar1.add(mnuEdit);

        mnuTools.setText("Tools");

        mnuLogTofile.setSelected(true);
        mnuLogTofile.setText("Log eccezioni su file");
        mnuLogTofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLogTofileActionPerformed(evt);
            }
        });
        mnuTools.add(mnuLogTofile);

        mnuViewLogFile.setText("Vedi log file");
        mnuViewLogFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuViewLogFileActionPerformed(evt);
            }
        });
        mnuTools.add(mnuViewLogFile);
        mnuTools.add(jSeparator7);

        mnuKeyLength.setText("Verifica max lunghezza chiave ammissibile");
        mnuKeyLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuKeyLengthActionPerformed(evt);
            }
        });
        mnuTools.add(mnuKeyLength);

        jMenuBar1.add(mnuTools);

        mnuAbout.setText("About");

        mnuAboutItem.setText("About");
        mnuAboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutItemActionPerformed(evt);
            }
        });
        mnuAbout.add(mnuAboutItem);

        jMenuBar1.add(mnuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNewBackup)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteNodeTre)
                                .addGap(18, 18, 18)
                                .addComponent(btnInsertNodeTree)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteNodeTree)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnViewLog))
                            .addComponent(txtSearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbUppercaseOnly)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchAll))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSaveTree)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLoadTree)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteNodeTree)
                    .addComponent(btnDeleteNodeTre)
                    .addComponent(btnViewLog)
                    .addComponent(btnSaveTree)
                    .addComponent(btnLoadTree)
                    .addComponent(btnNewBackup)
                    .addComponent(btnInsertNodeTree))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnSearchAll)
                    .addComponent(ckbUppercaseOnly))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Inserisce un subtree controllando che il nodo padre sia un backup node
     *
     * return True se il sotto albero è stato inserito altrimenti false
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private boolean insertSubTree() throws InterruptedException, ExecutionException {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();

        //Nessuna selezione o selezionato un nodo che non è un backup set
        if (selectedNode == null || ((NodeInformation) selectedNode.getUserObject()).getType() != NodeType.BACKUP) {
            JOptionPane.showMessageDialog(this, "Selezionare un nodo Backup", "Informazione", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Directory da leggere");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) directoryTree.getModel();
        DefaultMutableTreeNode topNode
                = (m_selectedNode == null) ? (DefaultMutableTreeNode) defaultTreeModel.getRoot() : m_selectedNode;

        //DefaultMutableTreeNode topNodeTemp = new DefaultMutableTreeNode(new EmptyNodeInformation()); // Nodo temporaneo
        DefaultMutableTreeNode topNodeTemp = new DefaultMutableTreeNode(new BackupNodeInformation()); // Nodo temporaneo

        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        FolderTreeReaderWorker folderTreeReaderWorker = new FolderTreeReaderWorker(Paths.get(chooser.getSelectedFile().getAbsolutePath()), topNodeTemp);
        DlgSwingWorkerLog dlgSwingWorkerLog = new DlgSwingWorkerLog(this, true, folderTreeReaderWorker);

        dlgSwingWorkerLog.startWorkerAndShowDialog();

        if (folderTreeReaderWorker.isDone() && !folderTreeReaderWorker.isCancelled()) {
            // Aggiorno i dati del backupset
            if (((NodeInformation) topNode.getUserObject()).getType() != NodeType.BACKUP) {
                try {
                    throw new Exception("Qui non dovrei arrivare mai");
                } catch (Exception ex) {
                    //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            BackupNodeInformation bkpTemp = (BackupNodeInformation) topNodeTemp.getUserObject();  // Il primo è sicuramenteun backup
            BackupNodeInformation bkpCurrentTemp = (BackupNodeInformation) topNode.getUserObject(); // se arrivo qui anche questo è un backup

            bkpCurrentTemp.setFilesTotal(bkpCurrentTemp.getFilesTotal() + bkpTemp.getFilesTotal());
            bkpCurrentTemp.setFoldersTotal(bkpCurrentTemp.getFoldersTotal() + bkpTemp.getFoldersTotal() + 1); // Aggiungo anche la prima folder
            bkpCurrentTemp.setSizeTotal(bkpCurrentTemp.getSizeTotal() + bkpTemp.getSizeTotal());

            topNode = (DefaultMutableTreeNode) folderTreeReaderWorker.get().getChildAt(0); // Carico tutti i sottonodi dell'albero creato con il placeholder 
            // Oppure
            //topNode.add((MutableTreeNode) topNodeTemp.getFirstChild());
            folderTreeReaderWorker.sortTree(topNode);
            selectedNode.add(topNode);
        } else {
            topNodeTemp.removeAllChildren(); // Per sicurezza e liberare memoria cancello tutti i figli se ho avuto problemi
        }

        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        ((DefaultTreeModel) directoryTree.getModel()).reload();
        m_searchingNodes = null; // reimposto il nodo di ricerca in modo da ricominciare da capo

        return true;
    }

    private void btnDeleteNodeTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNodeTreeActionPerformed

        deleteSubTree();

        m_searchingNodes = null; // reimposto il nodo di ricerca in modo da ricominciare da capo
    }//GEN-LAST:event_btnDeleteNodeTreeActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        DefaultMutableTreeNode node = searchNode(txtSearchText.getText(), ckbUppercaseOnly.isSelected());
        if (node != null) {
            TreeNode[] nodes = ((DefaultTreeModel) directoryTree.getModel()).getPathToRoot(node);
            TreePath path = new TreePath(nodes);
            directoryTree.scrollPathToVisible(path);
            directoryTree.setSelectionPath(path);

        } else {
            JOptionPane.showMessageDialog(this, "Non ho trovato nulla", "Ricerca", JOptionPane.WARNING_MESSAGE);
            m_searchingNodes = null;
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTreeActionPerformed
        if (Constants.getInstance().getUserPassword() == null && Constants.getInstance().isDBEncrypted()) {
            JOptionPane.showMessageDialog(this, "Non posso salvare il DB cifrato senza una password impostata", "Attenzione", JOptionPane.OK_OPTION);
        } else {
            saveTheTree();
        }
    }//GEN-LAST:event_btnSaveTreeActionPerformed

    private void btnLoadTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadTreeActionPerformed
        openTheTree();
    }//GEN-LAST:event_btnLoadTreeActionPerformed

    private void btnViewLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewLogActionPerformed
        int val = JOptionPane.showConfirmDialog(this, "Cancello tutto?", "Conferma", JOptionPane.OK_CANCEL_OPTION);
        if (val != JOptionPane.OK_OPTION) {
            return;
        }
        ((DefaultMutableTreeNode) directoryTree.getModel().getRoot()).removeAllChildren();
        ((DefaultTreeModel) directoryTree.getModel()).reload();
        m_searchingNodes = null;
    }//GEN-LAST:event_btnViewLogActionPerformed

    private void btnNewBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBackupActionPerformed

        // Personalizzo la dialog
        JTextField txtBackupSetName = new JTextField();
        txtBackupSetName.addAncestorListener(new RequestFocusListener()); // Focus su questo campo

        JTextArea txtBackupSetDescription = new JTextArea(10, 50);
        JScrollPane backupDescScroll = new JScrollPane(txtBackupSetDescription);
        final JComponent[] inputs = new JComponent[]{
            new JLabel("Nome Backup Set :"),
            txtBackupSetName,
            new JLabel("Descrizione Backup Set :"),
            backupDescScroll
        };
        int result = JOptionPane.showConfirmDialog(this, inputs, "Inserimendo dati Backup Set", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String nome;
        String desc;
        if (result == JOptionPane.OK_OPTION) {
            nome = txtBackupSetName.getText().trim();
            desc = txtBackupSetDescription.getText().trim();
        } else {
            return;
        }

//If a string was not returned, return.
        if ((nome == null) || (nome.length() == 0)) {
            return;
        }

//If you're here, the return value was the string.
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) directoryTree.getModel().getRoot();
        BackupNodeInformation backupNodeInformation = new BackupNodeInformation(nome, desc);
        backupNodeInformation.setRetrievingDate(new Date(LocalDate.now().toEpochDay())); // TODO: trucchetto da verificare meglio come gestirlo

        DefaultMutableTreeNode newBackupNode = new DefaultMutableTreeNode(backupNodeInformation);
        defaultMutableTreeNode.add(newBackupNode);

        // Mostro il nuovo backupset appena creato
        m_selectedNode = newBackupNode;
        ((DefaultTreeModel) directoryTree.getModel()).reload();
        TreeNode[] treeNodes = ((DefaultTreeModel) directoryTree.getModel()).getPathToRoot(newBackupNode);
        TreePath treePath = new TreePath(treeNodes);
        directoryTree.setSelectionPath(treePath);
        directoryTree.scrollPathToVisible(treePath);
        directoryTree.expandPath(treePath);

        displayDetails(m_selectedNode);


    }//GEN-LAST:event_btnNewBackupActionPerformed

    private void btnDeleteNodeTreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNodeTreActionPerformed

        deleteSubTree();
    }//GEN-LAST:event_btnDeleteNodeTreActionPerformed

    private void btnInsertNodeTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertNodeTreeActionPerformed
        try {
            TreePath tp = directoryTree.getSelectionPath();
            if (insertSubTree()) {
                //DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();

                // Aggiorno e visualizzo il jtree
                directoryTree.setSelectionPath(tp);
                directoryTree.scrollPathToVisible(tp);
                directoryTree.expandPath(tp);
            }

        } catch (InterruptedException | ExecutionException ex) {
            // Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInsertNodeTreeActionPerformed

    private void mnuAboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutItemActionPerformed

        JDialog dialog = new DlgAbout(this, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

    }//GEN-LAST:event_mnuAboutItemActionPerformed

    private void mnuSaveDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveDBActionPerformed
        saveTheTree();
    }//GEN-LAST:event_mnuSaveDBActionPerformed

    private void mnuLoadDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoadDBActionPerformed
        openTheTree();
    }//GEN-LAST:event_mnuLoadDBActionPerformed

    private void txtSearchTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchTextFocusGained
//        txtSearchText.setText("");
    }//GEN-LAST:event_txtSearchTextFocusGained

    private void mnuSetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSetPasswordActionPerformed
        JLabel label_password = new JLabel("Password:");
        JPasswordField password = new JPasswordField();
        password.addAncestorListener(new RequestFocusListener());

        Object[] array = {label_password, password};
        String[] buttonMessages = new String[]{"Imposta", "Dimentica", "Annulla"};
        int res = JOptionPane.showOptionDialog(this, array, "Imposta password",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                buttonMessages,
                buttonMessages[2]
        );

        switch (res) {
            case JOptionPane.YES_OPTION:
                Constants.getInstance().setUserPassword(password.getPassword());
                m_msgStatusBarUserPassword = "Password impostata";
                break;
            case JOptionPane.NO_OPTION:
                Constants.getInstance().setUserPassword(null);
                m_msgStatusBarUserPassword = "Password dimenticata";
                break;
            default:
                // Se annullo esco senza aggiornare nulla
                return;
        }

        updateStatusBar(null);
    }//GEN-LAST:event_mnuSetPasswordActionPerformed

    private void btnSearchAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAllActionPerformed
        ArrayList<DefaultMutableTreeNode> results = searchAllNodes(txtSearchText.getText(), ckbUppercaseOnly.isSelected());
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non ho trovato nulla", "Ricerca", JOptionPane.WARNING_MESSAGE);
            m_searchingNodes = null;
            return;
        }
        DlgSearchAllResults dlgSearchAllResults = new DlgSearchAllResults(this, false, directoryTree);
        dlgSearchAllResults.setLocationRelativeTo(this);
        dlgSearchAllResults.setSearchResults(results);
        dlgSearchAllResults.setVisible(true);
    }//GEN-LAST:event_btnSearchAllActionPerformed

    private void popMnuRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popMnuRenameActionPerformed
        if (((NodeInformation) m_selectedNode.getUserObject()).getType() == NodeType.BACKUP) {
            BackupNodeInformation backupNode = (BackupNodeInformation) m_selectedNode.getUserObject();

            JTextField displayString = new JTextField(backupNode.getDisplayString());
            JTextArea details = new JTextArea(backupNode.getDetails(), 10, 40);
            JScrollPane detailsScrollPane = new JScrollPane(details);

            // Mi sembra più efficiente questa impostazione che quella nel btnNewBackupActionPerformed
            Object[] message = {
                "Nome Backup Set :", displayString,
                "Descrizione Backup Set :", detailsScrollPane
            };

            // Dialog per l'aggiornamento
            int option = JOptionPane.showConfirmDialog(this, message, "Aggiorna", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                backupNode.setDisplayString(displayString.getText().trim());
                backupNode.setDetails(details.getText().trim());

                // Ricarica il tree salvando lo stato di apertura                
                Enumeration<TreePath> openTreePath = directoryTree.getExpandedDescendants(
                        new TreePath(directoryTree.getModel().getRoot()));

                ((DefaultTreeModel) directoryTree.getModel()).reload(m_selectedNode);
                displayDetails(m_selectedNode);

                // Ripristina i path aperti
                while (openTreePath.hasMoreElements()) {
                    directoryTree.expandPath(openTreePath.nextElement());
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Scegliere un nodo backup set", "Attenzione", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_popMnuRenameActionPerformed

    private void directoryTreeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directoryTreeMouseReleased
        if (evt.isPopupTrigger()) {
            int row = directoryTree.getClosestRowForLocation(evt.getX(), evt.getY());
            directoryTree.setSelectionRow(row);
            m_selectedNode = (DefaultMutableTreeNode) directoryTree.getSelectionPath().getLastPathComponent();
            popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_directoryTreeMouseReleased

    private void ckbUppercaseOnlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbUppercaseOnlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbUppercaseOnlyActionPerformed

    private void cleanupOnExit() {
        if (fileHandler != null) {
            fileHandler.flush();
            fileHandler.close();
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        LOG.log(Level.INFO, "Application ended");
        cleanupOnExit();
    }//GEN-LAST:event_formWindowClosing

    private void mnuLogTofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLogTofileActionPerformed
        if (mnuLogTofile.isSelected()) {
            LOG.addHandler(fileHandler);
            LOG.log(Level.INFO, "Log avviato");
            fileHandler.flush();
            JOptionPane.showMessageDialog(this, "Scrivo i log sul file: \n" + Constants.LOGFILENAME + "\ncreato nella folder dove è stato lanciato il programma", "Informazione", JOptionPane.PLAIN_MESSAGE);

        } else {
            LOG.log(Level.INFO, "Log fermato");
            fileHandler.flush();
            LOG.removeHandler(fileHandler);
            JOptionPane.showMessageDialog(this, "Logging fermato.\nIl file: " + Constants.LOGFILENAME + "\ncreato nella folder dove è stato lanciato il programma contiene i log ad ora.", "Informazione", JOptionPane.PLAIN_MESSAGE);
        }
        Constants.getInstance().setExceptionsLogged(mnuLogTofile.isSelected());
    }//GEN-LAST:event_mnuLogTofileActionPerformed

    private void mnuEncryptDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEncryptDBActionPerformed
        if (mnuEncryptDB.isSelected()) {
            Constants.getInstance().setDBEncrypted(true);
        } else {
            Constants.getInstance().setDBEncrypted(false);
        }

        m_msgStatusBarDB = Constants.getInstance().isDBEncrypted()
                ? "Il Database verrà cifrato al salvataggio"
                : "Il Database non verrà cifrato";

        updateStatusBar(null);
    }//GEN-LAST:event_mnuEncryptDBActionPerformed

    private void mnuKeyLengthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuKeyLengthActionPerformed
        int keyLength = CryptographyBaseClass.getMaxAllowedKeyLength();
        String msg;
        if (keyLength == Integer.MAX_VALUE) {
            msg = "JCE Unlimited Strength Jurisdiction Policy impostato.\nAES non ha problemi riguardo alla lunghezza della chiave.";
        } else {
            msg = String.format("Lunghezza chiave di %d bit. AES non può usare chiavi a 256 bit.\nPer usarla devi impostare la JCE Unlimited Strength Jurisdiction Policy.", keyLength);
        }
        JOptionPane.showMessageDialog(this, msg, "Informazione", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_mnuKeyLengthActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
//        int confirm = JOptionPane.showOptionDialog(this,
//                "Esco dall'applicazione?",
//                "Conferma", JOptionPane.YES_NO_OPTION,
//                JOptionPane.QUESTION_MESSAGE, null, null, null);
//        if (confirm == JOptionPane.YES_OPTION) {
//            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            this.setVisible(false);
//            this.dispose();
//            System.exit(0);
//        }

// Faccio le stesse operazioni dell'evento associato a windowsclosing
// TODO: Dovrebbe essere possibile gestirlo in modo unitario. DA FARE
        LOG.log(Level.INFO, "Application ended");

        cleanupOnExit();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
        this.dispose();
        //System.exit(0);

    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuViewLogFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuViewLogFileActionPerformed
        BufferedReader breader = null;
        try {
//            JTextArea txtLogView = new JTextArea(Constants.LOGTEXTAREA_ROWS, Constants.LOGTEXTAREA_COLS);
            breader = new BufferedReader(new FileReader(Constants.LOGFILENAME));
            String linea;
            StringBuilder sb = new StringBuilder();
            while ((linea = breader.readLine()) != null) {
                sb.append(linea);
                sb.append("\n");
            }   
            breader.close();
            showCustomLogDialog("Visualuzza file di log: " + Constants.LOGFILENAME,"Contenuto log :", sb.toString());
//            txtLogView.setEditable(false);
//            JScrollPane logScroll = new JScrollPane(txtLogView);
//            final JComponent[] array = new JComponent[]{
//                new JLabel("Contenuto log :"),
//                logScroll,};
//
//            String[] buttonMessages = new String[]{"Ok"};
//            JOptionPane.showOptionDialog(this, array, "Visualuzza file di log: " + Constants.LOGFILENAME,
//                    JOptionPane.OK_OPTION,
//                    JOptionPane.PLAIN_MESSAGE,
//                    null,
//                    buttonMessages,
//                    buttonMessages[0]
//            );

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                breader.close();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mnuViewLogFileActionPerformed

    private void showCustomLogDialog(String title, String labelValue, String txtAreaValue) {
        JTextArea txtLogView = new JTextArea(Constants.LOGTEXTAREA_ROWS, Constants.LOGTEXTAREA_COLS);

        txtLogView.setText(txtAreaValue);
        
        txtLogView.setEditable(false);
        JScrollPane logScroll = new JScrollPane(txtLogView);
        final JComponent[] array = new JComponent[]{
            new JLabel(labelValue),
            logScroll,};

        String[] buttonMessages = new String[]{"Ok"};
        JOptionPane.showOptionDialog(this, array, title,
                JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                buttonMessages,
                buttonMessages[0]
        );

    }

    /**
     * Aggiorna la status bar
     *
     * @param statusBarText stringa da mostrare. Se null mostro la
     * concatenazione di sue stringhe di classe: DB cifrato e set password
     */
    private void updateStatusBar(String statusBarText) {
        // Aggiorno la status bar secondo questa logica:
        //      DBCifrato = true e password impostata: verde
        //      DBCifrato = true e password non impostata: arancione
        //      DBCifrato = false e password impostata: arancione
        //      DBCifrato = false e paspassword non impostata: rosso

        boolean isDBEncrypted = Constants.getInstance().isDBEncrypted();
        boolean isUserPasswordSet = Constants.getInstance().getUserPassword() != null;
        lblStatusBar.setForeground(Color.BLACK);
        if (isDBEncrypted && isUserPasswordSet) {
            lblStatusBar.setBackground(Color.GREEN);
        } else if ((!isDBEncrypted && isUserPasswordSet) || (isDBEncrypted && !isUserPasswordSet)) {
            lblStatusBar.setBackground(Color.ORANGE);
        } else if (!isDBEncrypted && !isUserPasswordSet) {
            lblStatusBar.setBackground(Color.RED);
            lblStatusBar.setForeground(Color.WHITE);
        }

        if (statusBarText == null) {
            statusBarText = m_msgStatusBarDB + " - " + m_msgStatusBarUserPassword;
        }
        lblStatusBar.setText(statusBarText);

        if (isUserPasswordSet) {
            lblPasswordSet.setBackground(Color.GREEN);
        } else {
            lblPasswordSet.setBackground(Color.RED);
        }

        if (isDBEncrypted) {
            lblDBSavedType.setBackground(Color.GREEN);
            lblDBSavedType.setForeground(Color.BLACK);
        } else {
            lblDBSavedType.setBackground(Color.RED);
            lblDBSavedType.setForeground(Color.LIGHT_GRAY);
        }

    }

    /**
     * Cancella il subTree selezionato
     */
    private void deleteSubTree() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
        if (node == null) { //Nothing is selected.     
            JOptionPane.showMessageDialog(this, "Selezionare un nodo", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cancello solo i nodi Backup o folder TODO: Da rivedere sulla base dell'UX
        NodeType nt = ((NodeInformation) node.getUserObject()).getType();
        if (nt == NodeType.BACKUP || nt == NodeType.FOLDER) {
            int val = JOptionPane.showConfirmDialog(
                    this,
                    "Cancello il sottoramo?",
                    "Conferma",
                    JOptionPane.YES_NO_OPTION);
            if (val != JOptionPane.YES_OPTION) {
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selezionare un nodo Backup set o Folder", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Salvo il Parent per poter lasciare aperto il tree
        TreeNode treeNodeParent = node.getParent();
        // Sono sulla root per cui non la cancello
        if (treeNodeParent == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Non è possibile cancellare questo nodo",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Salvo la posizione del fratello per espanderlo dopo aver cancellato il nodo
        DefaultMutableTreeNode nodeSibling = node.getNextSibling(); // Per aprire il fratello
        if (nodeSibling == null) {
            nodeSibling = (DefaultMutableTreeNode) node.getParent();
        }

        updateParentNode((DefaultMutableTreeNode) treeNodeParent, node);

        // Ok a questo punto posso cancellare il nodo
        node.removeFromParent();

        ((DefaultTreeModel) directoryTree.getModel()).reload(treeNodeParent);
        m_searchingNodes = null;

        // Espando il fratello, visto che recupero comunque il nodo padre dovrei sempre passare l'if
        if (nodeSibling != null) {
            TreeNode[] nodes = ((DefaultTreeModel) directoryTree.getModel()).getPathToRoot(nodeSibling);
            TreePath path = new TreePath(nodes);
            directoryTree.scrollPathToVisible(path);
            directoryTree.setSelectionPath(path);
        }
    }

    private void updateParentNode(DefaultMutableTreeNode parentTreeNode, DefaultMutableTreeNode actualTreeNode) {
        NodeType nt = ((NodeInformation) actualTreeNode.getUserObject()).getType();

        switch (nt) {
            case FOLDER:
            case BACKUP:
                //CollectionNodeInformation ob = (CollectionNodeInformation) actualTreeNode.getUserObject();

                TreeNode[] nodePath = actualTreeNode.getPath();
                long filesTotalTemp = 0;
                long foldersTotalTemp = 0;
                long sizeTotalTemp = 0;

                // Variabili temporanee per aggiornare i nodi superiori
                DefaultMutableTreeNode nodeTemp;
                CollectionNodeInformation cniTemp; // Nodo generico di una collezione (folder o backup set)
                nodeTemp = (DefaultMutableTreeNode) nodePath[nodePath.length - 1]; // Nodo padre???
                cniTemp = (CollectionNodeInformation) nodeTemp.getUserObject();

                filesTotalTemp = cniTemp.getFilesTotal();
                foldersTotalTemp = cniTemp.getFoldersTotal();
                sizeTotalTemp = cniTemp.getSizeTotal();

                CollectionNodeInformation cniTempParent;
                DefaultMutableTreeNode nodeTempParent;
                // Ciclo dal secondo nodo al penultimo: il primo è la root mentre l'ultimo è il nodo da cancellare
                for (int i = nodePath.length - 2; i >= 1; i--) { // Salto il primo e l'ultimo livello (Nodo Radice e foglia da cancellare risoettivamente)
                    nodeTempParent = (DefaultMutableTreeNode) nodePath[i];
                    cniTempParent = (CollectionNodeInformation) nodeTempParent.getUserObject();
                    cniTempParent.setFilesTotal(cniTempParent.getFilesTotal() - filesTotalTemp);
                    cniTempParent.setFoldersTotal(cniTempParent.getFoldersTotal() - foldersTotalTemp - 1); // Elimino anche la subdir stessa
                    cniTempParent.setSizeTotal(cniTempParent.getSizeTotal() - sizeTotalTemp);
                }

                // Aggiorno il nodo padre                
                NodeInformation parentNode = (NodeInformation) parentTreeNode.getUserObject();

                // Verifico se sia un container (probabilmente è superfluo)
                switch (parentNode.getType()) {
                    case FOLDER:
                    case BACKUP:
                    case ROOT:

                        break;
                    default: // Se sono qui qualcosa è andato molto storto per cui non faccio nulla
                        JOptionPane.showMessageDialog(
                                this,
                                "Qualcosa è andato molto storto, il nodo da aggiornare non è né un folder né un backup, non faccio nulla",
                                "Attenzione",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // Aggiorno le subdir dirette del parent
                // PROBABILE BUG, se il nodo non è né folder né backup ho un problema (provo a chiamare il metodo comune tra backup node e filde rnode)
                if (parentNode.getType() == NodeType.FOLDER) {
                    FolderNodeInformation folderNodeTemp = (FolderNodeInformation) parentNode; // Per comodità
                    folderNodeTemp.setFirstSubFolders(folderNodeTemp.getFirstSubFolders() - 1);
                }

                break;

            default: // Se sono qui qualcosa è andato molto storto per cui non faccio nulla
                JOptionPane.showMessageDialog(
                        this,
                        "Qualcosa è andato molto storto, il nodo da cancellare non è né un folder né un backup non faccio nulla",
                        "Attenzione",
                        JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            LOG.log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true); //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Recupera i dettagli e li mostra
     *
     * @param e Evento
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        //Returns the last path element of the selection.
        //This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();

        if (node == null) //Nothing is selected.     
        {
            return;
        }

        displayDetails(node);
    }

    /**
     * Mostra i dettagli nella textarea prevista
     *
     * @param node Nodo di cui mostrare i dettagli
     */
    private void displayDetails(DefaultMutableTreeNode node) {
        NodeInformation nodeInfo = (NodeInformation) node.getUserObject();
        m_selectedNode = node;

        StringBuilder sb = new StringBuilder();

        if (node.isRoot()) {
            sb = sb.append(" << ROOT >>").append("\n");
        } else {

            switch (nodeInfo.getType()) {
                case BACKUP: {
                    BackupNodeInformation backupNodeTemp = (BackupNodeInformation) nodeInfo;
                    sb.append("<< Backup set  >>").append("\n");
                    sb.append(nodeInfo.toString()).append("\n");
                    sb.append(backupNodeTemp.getDetails()).append("\n");
                    sb.append("Files totali: ").append(String.valueOf(backupNodeTemp.getFilesTotal())).append("\n");
                    sb.append("Directory totali: ").append(String.valueOf(backupNodeTemp.getFoldersTotal())).append("\n");
                    sb.append("Dimensioni totali: ").append(String.valueOf(backupNodeTemp.getSizeTotal())).append("\n");
                }
                break;
                case FOLDER:
                    sb = sb.append(" << DIRECTORY >>").append("\n");
                    //sb.append(nodeInfo.toString()).append("\n");
                    sb.append(nodeInfo.getDisplayString()).append(" (");
                    sb.append(((FilesystemNodeInformation) nodeInfo).getPath()).append(")\n");

                    sb.append(String.valueOf(((FolderNodeInformation) nodeInfo).getFirstFilesInFolder())).append(" files in folder \n");
                    sb.append(String.valueOf(((FolderNodeInformation) nodeInfo).getFirstFilesSizeInFolder())).append(" file size in folder\n");
                    sb.append(String.valueOf(((FolderNodeInformation) nodeInfo).getFirstSubFolders())).append(" subfolders in folder\n");
                    sb.append(String.valueOf(((FolderNodeInformation) nodeInfo).getFilesTotal())).append(" files totali sotto il folder \n");
                    sb.append(String.valueOf(((FolderNodeInformation) nodeInfo).getSizeTotal())).append(" file size totali nel folder\n");
                    sb.append(String.valueOf(((CollectionNodeInformation) nodeInfo).getFoldersTotal())).append(" folder totali nel folder\n");

                    break;
                case FILE: {
                    sb = sb.append(" << FILE >>").append("\n");
                    sb.append(nodeInfo.getDisplayString()).append(" (");
                    sb.append(((FilesystemNodeInformation) nodeInfo).getPath()).append(")\n");
                    sb.append("File size: ").append(String.valueOf(((FileNodeInformation) nodeInfo).getSize())).append("\n");
                }
                break;

                case EMPTY_PLACEHOLDER: {
                    sb.append(nodeInfo.toString()).append("\n");
                }
                break;

                case GENERIC: {
                    sb.append(nodeInfo.toString()).append("\n");
                    //System.out.println("");
                }
                break;

            }
        }
//        if (nodeInfo instanceof FileNodeInformation) {
//            Date d = ((FileNodeInformation) nodeInfo).getLastModifiedDateTime();
//            sb.append("\n").append("LastModified: ").append(d.toString()).append("\n");
////            System.out.println( nodeInfo.getDisplayString());
//        }
        if (nodeInfo.getType() == NodeType.FILE || nodeInfo.getType() == NodeType.FOLDER) {
            Date d = new Date(((FilesystemNodeInformation) nodeInfo).getCreateDateTimeMillis());
            sb.append("Created: ").append(d.toString()).append("\n");
            d = new Date(((FilesystemNodeInformation) nodeInfo).getLastModifiedDateTimeMillis());
            sb.append("Last modified time: ").append(d.toString()).append("\n");
            d = new Date(((FilesystemNodeInformation) nodeInfo).getLastAccessedDateTimeMillis());
            sb.append("Last access time: ").append(d.toString()).append("\n");
        }

        txtDetails.setText(sb.toString());
    }

    /**
     * Cerca un nodo a partire dal pattern. Come effetto collaterale aggiorna
     * una variabile di classe in modo da poter continuare la ricerca
     * semplicemente invocando la funzione
     *
     * @param nodeStr Pattern di ricerca (regexp)
     * @param caseSensitive True se il patter va cercato distinguendo tra
     * maiuscole e miniscole, false altrimenti
     * @return Noodo trovato.
     */
    private DefaultMutableTreeNode searchNode(String nodeStr, boolean caseSensitive) {
        DefaultMutableTreeNode node = null;

        if (m_searchingNodes == null || !m_searchingNodes.hasMoreElements()) {
            m_searchingNodes = ((DefaultMutableTreeNode) directoryTree.getModel().getRoot()).depthFirstEnumeration();
        }

        Pattern pattern;
        if (caseSensitive) {
            pattern = Pattern.compile(nodeStr);
        } else {
            pattern = Pattern.compile(nodeStr, Pattern.CASE_INSENSITIVE);
        }
        while (m_searchingNodes.hasMoreElements()) {
            node = (DefaultMutableTreeNode) m_searchingNodes.nextElement();
            Matcher matcher = pattern.matcher(node.getUserObject().toString());
            if (matcher.find()) {
                return node;
            }
        }
        return null;
    }

    /**
     * Cerca il pattern in tutti i nodi
     *
     * @param searchPattern pattern da cercare (regexp)
     * @param caseSensitive true se il pattern va cercato rispettando le
     * maiuscole/minuscole, false altrimenti
     * @return ArrayList con tutti i nodi trovati o null se non ha trovato nulla
     */
    private ArrayList<DefaultMutableTreeNode> searchAllNodes(String searchPattern, boolean caseSensitive) {
        DefaultMutableTreeNode node = null;

// Cerco dalla root
        //Enumeration searchingNodes = ((DefaultMutableTreeNode) directoryTree.getModel().getRoot()).depthFirstEnumeration();
// Cerco dall'ultimo nodo selezionato
        Enumeration searchingNodes = null;

        if (m_selectedNode == null) { // Se non seleziono nulla parto dalla root
            searchingNodes = ((DefaultMutableTreeNode) directoryTree.getModel().getRoot()).depthFirstEnumeration();
        } else {
            searchingNodes = m_selectedNode.depthFirstEnumeration();
        }
        ArrayList<DefaultMutableTreeNode> nodeInformations = new ArrayList<>();

        Pattern pattern;
        if (caseSensitive) {
            pattern = Pattern.compile(searchPattern);
        } else {
            pattern = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
        }
        while (searchingNodes.hasMoreElements()) {
            node = (DefaultMutableTreeNode) searchingNodes.nextElement();
            Matcher matcher = pattern.matcher(node.getUserObject().toString());
            if (matcher.find()) {
                nodeInformations.add(node);
            }
        }

        return nodeInformations;
    }

    /**
     * Salva il tree
     */
    public void saveTheTree() {

        boolean toOverwrite = false;

        JFileChooser chooser = new JFileChooser();
        Path currentPath = Paths.get(Constants.getInstance().getLatestSavePath() + File.separator + Constants.getInstance().getLatestSaveFilename());
        chooser.setSelectedFile(new File(currentPath.toAbsolutePath().toString()));
        chooser.setDialogTitle("Salvo il DB");
        //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(true);

        //    
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        if (chooser.getSelectedFile().exists()) {
            int val = JOptionPane.showConfirmDialog(
                    this,
                    "Il file esiste, lo sovrascrivo?",
                    "Conferma",
                    JOptionPane.YES_NO_OPTION);
            if (val != JOptionPane.YES_OPTION) {
                return;
            }
            toOverwrite = true;
        }

        Constants.getInstance().setLatestSavePath(chooser.getSelectedFile().getParent());
        Constants.getInstance().setLatestSaveFilename(chooser.getSelectedFile().getName());

        TreeModel tm = directoryTree.getModel();   //tree is of type MyTree which extends JTree. It creates a tree from an XML document

        FilesystemActionWorker filesystemActionWorker = new FilesystemActionWorker(chooser.getSelectedFile().getAbsolutePath(), false);
        filesystemActionWorker.setObjectToWrite(tm);

        DlgFilesystemAction dlgFilesystemAction = new DlgFilesystemAction(this, true, filesystemActionWorker, false);

        dlgFilesystemAction.startWorkerAndShowDialog(); // Aspetto che il thread finisca

        if (filesystemActionWorker.isCancelled()) {
            String msg = "Nessun file è stato salvato" + (toOverwrite ? ". Il file esistente è stato conservato" : "");
            JOptionPane.showMessageDialog(this, msg, "Attenzione", JOptionPane.OK_OPTION);
        }
    }

    /**
     * Carica il tree
     */
    public void openTheTree() {
        JFileChooser chooser = new JFileChooser();
        Path currentPath = Paths.get(Constants.getInstance().getLatestSavePath() + File.separator + Constants.getInstance().getLatestSaveFilename());
        chooser.setSelectedFile(new File(currentPath.toAbsolutePath().toString()));
        chooser.setDialogTitle("Carico il DB");
        //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(true);

        //    
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        // Salvo la posizione corrente
        Constants.getInstance().setLatestSavePath(chooser.getSelectedFile().getParent());
        Constants.getInstance().setLatestSaveFilename(chooser.getSelectedFile().getName());

        m_searchingNodes = null; // reinizializzo la ricerca
        m_selectedNode = null;

        try {
            TreeModel atm;

            // Avvio i componenti per caricare i dati (worker e dialog)
            FilesystemActionWorker filesystemActionWorker = new FilesystemActionWorker(chooser.getSelectedFile().getAbsolutePath(), true);
            DlgFilesystemAction dlgFilesystemAction = new DlgFilesystemAction(this, true, filesystemActionWorker, true);

            dlgFilesystemAction.startWorkerAndShowDialog(); // Aspetto che il thread finisca

            // Sembra che si blocchi comunque anche se il thread è stato cancellato
            if (filesystemActionWorker.isCancelled()) {
                return;
            }

            atm = filesystemActionWorker.get(); // Recupero il treemodel            
            if (atm == null) { // Se è nullo esco in quanto ci potrebbe essere stato un problema nel leggere il file o decifrare il file
                String msgDialog = null;
                switch (filesystemActionWorker.getError()) {
                    case WRONG_PASSWORD:
                        msgDialog = "Errore nel decifrare il file, controlla la password";
                        break;
                    case ERROR_LOADING_CLASS:
                        msgDialog = "Errore nel caricare l'oggetto serializzato";
                        break;
                    case ERROR_READING_FILE:
                        msgDialog = "Errore nel caricare il file";
                        break;
                    case ERROR_WRITING_FILE:
                        msgDialog = "Errore nel salvare il file";
                        break;
                    case IO_ERROR:
                        msgDialog = "Errore IO";
                        break;
                }

                JOptionPane.showMessageDialog(this, msgDialog, "Attenzione", JOptionPane.OK_OPTION);

                return;
            }

            // A questo punto agisco
            // Cancello tutto il tree
            DefaultTreeModel defaultTreeModel = new DefaultTreeModel(null);
            directoryTree.setModel(defaultTreeModel);
            ((DefaultTreeModel) directoryTree.getModel()).reload();

            // Aggiono il modello
            directoryTree.setModel(atm);
            ((DefaultTreeModel) directoryTree.getModel()).reload();

        } catch (InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(this, "Qualcosa è andato storto.\nSe hai provato a caricare un DB cifrato\naccertati di aver inserito la password corretta.", "Attenzione", JOptionPane.OK_OPTION);
            //java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteNodeTre;
    private javax.swing.JButton btnDeleteNodeTree;
    private javax.swing.JButton btnInsertNodeTree;
    private javax.swing.JButton btnLoadTree;
    private javax.swing.JButton btnNewBackup;
    private javax.swing.JButton btnSaveTree;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchAll;
    private javax.swing.JButton btnViewLog;
    private javax.swing.JCheckBox ckbUppercaseOnly;
    private javax.swing.JTree directoryTree;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblDBSavedType;
    private javax.swing.JLabel lblPasswordSet;
    private javax.swing.JLabel lblStatusBar;
    private javax.swing.JMenu mnuAbout;
    private javax.swing.JMenuItem mnuAboutItem;
    private javax.swing.JMenuItem mnuDeleteAll;
    private javax.swing.JMenuItem mnuDeleteBackupset;
    private javax.swing.JMenuItem mnuDeleteTree;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JCheckBoxMenuItem mnuEncryptDB;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuInsertTree;
    private javax.swing.JMenuItem mnuKeyLength;
    private javax.swing.JMenuItem mnuLoadDB;
    private javax.swing.JCheckBoxMenuItem mnuLogTofile;
    private javax.swing.JMenuItem mnuModifyBackupSet;
    private javax.swing.JMenuItem mnuNewBackupSet;
    private javax.swing.JMenuItem mnuSaveDB;
    private javax.swing.JMenuItem mnuSetPassword;
    private javax.swing.JMenu mnuTools;
    private javax.swing.JMenuItem mnuViewLogFile;
    private javax.swing.JPanel pnlStatusBar;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JMenuItem popMnuRename;
    private javax.swing.JTextArea txtDetails;
    private javax.swing.JTextField txtSearchText;
    // End of variables declaration//GEN-END:variables

}
