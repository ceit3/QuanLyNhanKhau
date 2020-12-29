package views;

import controllers.ThongKeDSPaneController;
import controllers.ThongKePanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @author Tham
 * Panel cho thong ke theo danh sach
 */
public class ThongKeDSPane extends JPanel{
    private JFrame parentFrame;
    private ThongKeDSPaneController controller = null;

    public ThongKeDSPane(JFrame parentFrame) throws SQLException, ClassNotFoundException {
        this.parentFrame = parentFrame;
        this.setSize(GuiConstants.getPaneInitWidth(), GuiConstants.getPaneInitHeight());
        this.add(rootPane);
        this.controller = new ThongKeDSPaneController(chooseBox, fromTxf, toTxf,tableJpn);
        this.controller.setParentJFrame(parentFrame);
        this.controller.setDataTableQua(this.controller.getListDSQua());
        filtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jButton1ActionPerformed(e);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {//GEN-FIRST:event_jButton1ActionPerformed
        this.controller.setData();
    }
    // Khong duoc thay doi vi cho Form
    private JPanel rootPane;
    private JComboBox chooseBox;
    private JTextField fromTxf;
    private JTextField toTxf;
    private JButton filtBtn;
    private JLabel ThongKeLb;
    private JLabel toLb;
    private JLabel fromLb;
    private JPanel tableJpn;
}

