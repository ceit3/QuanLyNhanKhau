package quanly;

import java.util.Calendar;
import javax.swing.UIManager;
import views.LoginUI;

/**
 *
 * @author Hai
 * edit: Nhóm 20
 */
public class QuanLy {

    public static Calendar calendar = Calendar.getInstance();
    
    public static void main(String[] args){
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }*/
//        MainFrame mainFrame = new MainFrame();
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setResizable(false);
//        mainFrame.setVisible(true);
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true); 
    }
    
}
