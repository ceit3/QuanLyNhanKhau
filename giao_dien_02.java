import javax.swing.*;

public class giao_dien_02 extends JFrame {

    private JPanel panel2;
    private JButton TrangChuButton;
    private JButton PhatThuongButton;
    private JButton PhatQuaButton;
    private JButton ThongKeButton;

    public static void main(String[] args) {
        JFrame frame2 = new JFrame();
        frame2.setContentPane(new giao_dien_02().panel2);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500,400);
        frame2.setVisible(true);
    }
}
