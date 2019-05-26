/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.UI;

import diskcollector.Constants;
import diskcollector.Worker.FilesystemActionWorker;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author utente
 */
public class DlgFilesystemAction extends javax.swing.JDialog {

    FilesystemActionWorker actionWorker;
    DlgFilesystemAction m_myself;
    String m_msg;
    boolean isReadOperation;
    
    /**
     * Crea una dialog per gestire le azioni sul filesystem
     *
     * @param parent container padre
     * @param modal true se la dialog deve essere modale
     * @param actionWorker worker per l'azione sul filesystem
     * @param isReadOperation true se l'operazione da monitorare è di lettura file, false se è di scrittura
     */
    public DlgFilesystemAction(java.awt.Frame parent, boolean modal, FilesystemActionWorker actionWorker, boolean isReadOperation) {
        super(parent, modal);
        this.actionWorker = actionWorker;
        m_myself = this;
        this.isReadOperation=isReadOperation;
        initComponents();
        
        if (isReadOperation){
            StringBuilder msg=new StringBuilder("Carico il file");
            if (Constants.getInstance().getUserPassword()!=null){
                msg.append(" e provo ad usare automaticamente la password.");
            } 
            lblAction.setText(msg.toString());
            m_msg="Sto ancora caricando. Annullo il caricamento?";
        } else {
            lblAction.setText("Save file");            
            m_msg="Sto ancora salvando. Annullo il salvataggio?";
        }

        this.setLocationRelativeTo(parent);

        // Evento quanto termina il worker
        actionWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if ("state".equals(event.getPropertyName()) && SwingWorker.StateValue.DONE.equals(event.getNewValue())) {
                    m_myself.setVisible(false);
                }
            }
        });

    }

    /**
     *
     */
    public void startWorkerAndShowDialog() {
        actionWorker.execute();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        btnCancel = new javax.swing.JButton();
        lblAction = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filesystem Action");
        setModalityType(java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        setUndecorated(true);
        setResizable(false);

        jProgressBar1.setIndeterminate(true);

        btnCancel.setText("Annulla");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblAction.setText("Load Or Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        int val = JOptionPane.showConfirmDialog(this, m_msg, "Attenzione", JOptionPane.OK_CANCEL_OPTION);
        if (val == JOptionPane.OK_OPTION) {
            boolean cancellato = actionWorker.closeAndCancelWorker();
            JOptionPane.showMessageDialog(this.getParent(), cancellato ? "Cancellato" : "Non posso cancellarlo");
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DlgFilesystemAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DlgFilesystemAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DlgFilesystemAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DlgFilesystemAction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
////        /* Create and display the dialog */
////        java.awt.EventQueue.invokeLater(new Runnable() {
////            public void run() {
////                DlgFilesystemAction dialog = new DlgFilesystemAction(new javax.swing.JFrame(), true);
////                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
////                    @Override
////                    public void windowClosing(java.awt.event.WindowEvent e) {
////                        System.exit(0);
////                    }
////                });
////                dialog.setVisible(true);
////            }
////        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblAction;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(DlgFilesystemAction.class.getName());
}
