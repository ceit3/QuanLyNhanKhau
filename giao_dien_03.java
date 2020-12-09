import javax.swing.*;

public class giao_dien_03 {
    private JButton TrangChuButton;
    private JButton PhatThuongButton;
    private JButton PhatQuaButton;
    private JButton ThongKeButton;
    private JButton TaoMoiButton;
    private JButton CapNhatButton;
    private JTextField NhapTenDanhSachTextField;
    private JPanel panel3;

    public static void main(String[] args) {
        JFrame frame3 = new JFrame();
        frame3.setContentPane(new giao_dien_03().panel3);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setSize(500,400);
        frame3.setVisible(true);
    }
}
