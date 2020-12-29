package views;

import controllers.PhatQuaPaneController;
import controllers.PhatThuongPaneController;
import services.DSQuaService;
import services.DSThuongService;
import views.PhatQuaFrame.CapNhatDSQuaFrame;
import views.PhatQuaFrame.TaoMoiDSQuaFrame;
import views.PhatThuongFrame.CapNhatDSThuongFrame;
import views.PhatThuongFrame.TaoMoiDSThuongFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PhatThuongPane extends JPanel{
    private JPanel rootPane;
    private JButton addNewBtn;
    private JTextField jtfSearch;
    private JButton updateBtn;
    private JPanel tablePanel;

    private PhatThuongPaneController controller = null;
    private JFrame parentJFrame;
    private DSThuongService dsThuongService;

    public PhatThuongPane(JFrame parentFrame) throws SQLException, ClassNotFoundException {
        this.parentJFrame = parentFrame;
        this.setSize(GuiConstants.getPaneInitWidth(), GuiConstants.getPaneInitHeight());
        this.add(rootPane);
        initComponents();
        controller = new PhatThuongPaneController(tablePanel, jtfSearch);
        controller.setParentJFrame(parentJFrame);
        controller.setDataTable();
    }

    private void initComponents() {
        addNewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBtnActionPerformed(e);
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
    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TaoMoiDSThuongFrame addNewJFrame = new TaoMoiDSThuongFrame(this.controller, this.parentJFrame);
        addNewJFrame.setLocationRelativeTo(null);
        addNewJFrame.setResizable(false);
        addNewJFrame.setVisible(true);
    }

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {
        CapNhatDSThuongFrame updateJFrame = new CapNhatDSThuongFrame(this.controller, this.parentJFrame, controller.select);
        updateJFrame.setLocationRelativeTo(null);
        updateJFrame.setResizable(false);
        updateJFrame.setVisible(true);
    }
}
