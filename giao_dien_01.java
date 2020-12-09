import javax.swing.*;

public class giao_dien_01 {
    private JPanel panel1;
    private JButton QuanLyNhanKhauButton;
    private JButton QuanLyPhatQuaButton;


    public static void main(String[] args) {
        JFrame frame1 = new JFrame();
        frame1.setContentPane(new giao_dien_01().panel1);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }



}
