package views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
public class HomeFrame extends JFrame{
    private JPanel rootPane;
    private JButton QuanLyNhanKhauButton;
    private JButton QuanLyPhatQuaButton;
    private JLabel user;
    private JButton logOutBtn;
    private JLabel welcomeLabel;

    /**
     * @author Tham
     * Trang chu cua chuong trinh
     */
    public HomeFrame(String user) {
        this.setTitle("Home Page");
        add(rootPane);
        this.user.setText(user);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));

        QuanLyPhatQuaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new QLPhatQuaFrame(user);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        QuanLyNhanKhauButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setResizable(false);
                mainFrame.setVisible(true);
            }
        });
        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginUI login = new LoginUI();
                login.setVisible(true);
            }
        });
    }


}
