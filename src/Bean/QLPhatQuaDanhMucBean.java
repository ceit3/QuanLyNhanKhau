package Bean;

import javax.swing.*;

/**
 * @author Tham
 * Tao danh muc bean cho controller side bar cua QL Phat Qua
 * Khac voi DanhMucBean vi truyen vao la JButton chu k phai JPanel
 */
public class QLPhatQuaDanhMucBean {
    private String kind;
    private JButton jbt;

    public QLPhatQuaDanhMucBean() {
    }

    public QLPhatQuaDanhMucBean(String kind, JButton jbt) {
        this.kind = kind;
        this.jbt = jbt;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JButton getJbt() {
        return jbt;
    }

    public void setJbt(JButton jpn) {
        this.jbt = jpn;
    }

}
