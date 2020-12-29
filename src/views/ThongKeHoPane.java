package views;

import controllers.ThongKeDSPaneController;
import controllers.ThongKeHoPaneController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @author Nhom 20
 * Panel cho muc thong ke theo ho
 */
public class ThongKeHoPane extends JPanel{
    private JFrame parentFrame;
    private ThongKeHoPaneController controller;

    public ThongKeHoPane(JFrame parentFrame) throws SQLException, ClassNotFoundException {
        this.parentFrame = parentFrame;
        this.setSize(GuiConstants.getPaneInitWidth(), GuiConstants.getPaneInitHeight());
        this.add(rootPane);
        this.controller = new ThongKeHoPaneController(chuHoTxf, fromTxf, toTxf,tableJpn);
        this.controller.setDataTable("",0,2100);
        filtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(e);
            }
        });
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            this.controller.setData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Khong duoc thay doi vi cho Form
    private JPanel rootPane;
    private JButton filtBtn;
    private JTextField fromTxf;
    private JTextField toTxf;
    private JTextField chuHoTxf;
    private JLabel fromLb;
    private JLabel toLb;
    private JLabel chuHoLabel;
    private JPanel tableJpn;
}
