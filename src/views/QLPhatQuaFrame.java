package views;


import Bean.QLPhatQuaDanhMucBean;
import controllers.QLPhatQuaFrameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Khung giao dien cua nghiep vu QL Phat Qua
 * @author Lan
 * Tao file form giao dien
 * @author Tham
 * Dat lai ten
 */
public class QLPhatQuaFrame extends JFrame {

    private JPanel rootPane;
    private JButton homeButton;
    private JButton dsThuongButton;
    private JButton dsQuaButton;
    private JButton tkeDsButton;
    private JButton tkeHoButton;
    private JPanel sidePane;
    private JPanel viewPane;
    private JButton logOutBtn;
    private String user;

    public QLPhatQuaFrame(String user) throws SQLException, ClassNotFoundException {
        this.add(rootPane);
        this.user = user;
        setSize(new Dimension(GuiConstants.getWindowInitWidth(), GuiConstants.getWindowInitHeight()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        List<QLPhatQuaDanhMucBean> listDanhMuc = new ArrayList<>();
        listDanhMuc.add(new QLPhatQuaDanhMucBean("TrangChu", homeButton));
        listDanhMuc.add(new QLPhatQuaDanhMucBean("DSQua", dsQuaButton));
        listDanhMuc.add(new QLPhatQuaDanhMucBean("DSThuong", dsThuongButton));
        listDanhMuc.add(new QLPhatQuaDanhMucBean("ThongKeDS", tkeDsButton));
        listDanhMuc.add(new QLPhatQuaDanhMucBean("ThongKeHo", tkeHoButton));

        QLPhatQuaFrameController controller = new QLPhatQuaFrameController(viewPane, this,this.user);
        controller.setView(homeButton, "TrangChu");
        controller.setEvent(listDanhMuc);
        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOutBtnActionPerformed(e);
            }
        });
    }

    public void logOutBtnActionPerformed(java.awt.event.ActionEvent evt){
        this.dispose();
        LoginUI login = new LoginUI();
        login.setVisible(true);
    }


}
