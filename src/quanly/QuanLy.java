package quanly;

import java.util.Calendar;
import javax.swing.UIManager;
import views.LoginUI;

/**
 *
 * @author Nhung
 * edit: Nhóm 20
 */
public class QuanLy {

    public static Calendar calendar = Calendar.getInstance();
    
    public static void main(String[] args){

        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true); 
    }
    
}
