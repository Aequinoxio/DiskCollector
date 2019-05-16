/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector;

import diskcollector.NodeTypes.NodeInformation;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author utente
 *
 * Seen https://www.logicbig.com/tutorials/java-swing/jtree-renderer.html
 */
public class NodeCellRenderer extends DefaultTreeCellRenderer {

    private static final String SPAN_FORMAT = "<span style='color:%s;'>%s</span>";
    private static final String SPAN_FORMAT_FOLDER = "<span style='color:%s;'><b>%s</b></span>";
    private static final String SPAN_FORMAT_BACKUP = "<span style='color:%s;'><font size=+1><i>%s</i></font></span>";
    private static final String SPAN_FORMAT_ROOT = "<span style='color:%s;'><font size=+1><i>%s</i></font></span>";

    private final ImageIcon mainIcon;
    private final ImageIcon backupTextIcon;
    private final ImageIcon folderIconClose;
    private final ImageIcon folderIconOpen;

    /**
     *
     */
    public NodeCellRenderer() {
        mainIcon = new ImageIcon(NodeCellRenderer.class.getResource("/images/Settings-Backup-Sync-icon_32.png"));
        backupTextIcon = new ImageIcon(NodeCellRenderer.class.getResource("/images/cd-burner-copy-icon_32.png"));
        folderIconClose = new ImageIcon(NodeCellRenderer.class.getResource("/images/folder-close-icon_24.png"));
        folderIconOpen = new ImageIcon(NodeCellRenderer.class.getResource("/images/folder-open-icon_24.png"));
    }

    /**
     *
     * @param tree
     * @param value
     * @param sel
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     * @return
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        NodeInformation userObject = (NodeInformation) node.getUserObject();

        if (null == userObject.getType()) {
            // Non dovrebbe mai arrivarci
            String text = String.format(SPAN_FORMAT, "red", userObject);
            this.setText("<html>" + text + "</html>");
        } else // Rendering
        {
            switch (userObject.getType()) {
                case BACKUP: {

                    String text = String.format(SPAN_FORMAT_BACKUP, "blue", userObject.getDisplayString());
                    //text += " [" + String.format(SPAN_FORMAT, "orange", userObject.getRole()) + "]";
                    this.setText("<html>" + text + "</html>");
                    this.setIcon(backupTextIcon);
                    break;
                }
                case FILE: {
                    String text = String.format(SPAN_FORMAT, "green", userObject.getDisplayString());
                    this.setText("<html>" + text + "</html>");
                    break;
                }
                case FOLDER: {
                    String text = String.format(SPAN_FORMAT_FOLDER, "green", userObject.getDisplayString());
                    this.setText("<html>" + text + "</html>");
                    if (expanded) {
                        this.setIcon(folderIconOpen);
                    } else {
                        this.setIcon(folderIconClose);
                    }
                    break;
                }

                case ROOT: {
                    String text = String.format(SPAN_FORMAT_ROOT, "blue", userObject.getDisplayString());
                    this.setText("<html>" + text + "</html>");
                    this.setIcon(mainIcon);
                    break;
                }

                default: {
                    String text = String.format(SPAN_FORMAT, "red", userObject.getDisplayString());
                    this.setText("<html>" + text + "</html>");
                    break;
                }
            }
        }
        return this;
    }
}
