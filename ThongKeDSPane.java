package views.PhatQuaFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongKeDSPane extends JFrame{
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JTable table1;
    private JPanel rootPane;
    private JTextField textField2;
    private JButton locButton;

    public ThongKeDSPane(){
        add(rootPane);
        setSize(800, 600);

        locButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void main(String args[]){
        ThongKeDSPane frame = new ThongKeDSPane();
        frame.setVisible(true);
    }
}

