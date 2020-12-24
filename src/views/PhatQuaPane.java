package views;

import controllers.PhatQuaPaneController;
import models.DSQuaModel;
import services.DSQuaService;
import views.PhatQuaFrame.CapNhatDSQuaFrame;
import views.PhatQuaFrame.TaoMoiDSQuaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @author Nhung

 */
public class PhatQuaPane extends javax.swing.JPanel {
    private PhatQuaPaneController controller = null;
    private JFrame parentJFrame;
    private DSQuaService dsQuaService;

    public PhatQuaPane(JFrame parentFrame) throws SQLException, ClassNotFoundException {
        this.add(rootPane);
        this.parentJFrame = parentFrame;
        initComponents();
        controller = new PhatQuaPaneController(tablePanel, jtfSearch);
        controller.setParentJFrame(parentJFrame);
        controller.setDataTable();
    }

    private void initComponents() {
        addNewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewBtnActionPerformed(e);
            }
        });
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtnActionPerformed(e);
            }
        });

    }

    //tao moi
    private void addNewBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TaoMoiDSQuaFrame addNewJFrame = new TaoMoiDSQuaFrame(this.controller, this.parentJFrame);
        addNewJFrame.setLocationRelativeTo(null);
        addNewJFrame.setResizable(false);
        addNewJFrame.setVisible(true);
    }

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        CapNhatDSQuaFrame updateJFrame = new CapNhatDSQuaFrame(this.controller, this.parentJFrame, controller.select);
        updateJFrame.setLocationRelativeTo(null);
        updateJFrame.setResizable(false);
        updateJFrame.setVisible(true);
    }

    private javax.swing.JButton addNewBtn;
    private javax.swing.JTextField jtfSearch;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JButton updateBtn;
    private JPanel rootPane;
}
