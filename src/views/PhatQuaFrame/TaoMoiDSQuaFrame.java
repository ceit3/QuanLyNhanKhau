package views.PhatQuaFrame;

import controllers.PhatQuaController.TaoMoiDSQuaController;
import controllers.PhatQuaPaneController;
import models.DSQuaModel;
import models.PhanQuaModel;
import services.DSQuaService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import services.NhanKhauService;

public class TaoMoiDSQuaFrame extends JFrame{
    private JLabel namLabel;
    private JLabel tenDSLabel;
    private JLabel tenDipLabel;
    private JTextField namTextField;
    private JTextField tenDipTextField;
    private JTextField tenDSTextField;
    private JTextField tenNguoiNhanTxf;
    private JTextField tenQuaTxf;
    private JTextField giaTriTxf;
    private JButton taoMoiButton;
    private JButton huyButton;
    private JButton themButton;
    private JTable taoMoiTable;
    private JLabel frameLabel;
    private JScrollPane tableScrollPane;
    private PhatQuaPaneController parentController;
    private JFrame parentFrame;
    private DSQuaModel dsQuaModel;
    private TaoMoiDSQuaController controller;
    private JPanel rootPane;

    private DSQuaService dsQuaService;
    private NhanKhauService nhanKhauService;

    private List<PhanQuaModel> list;
    Vector data = new Vector<>();
    Vector header = new Vector<>();

    public TaoMoiDSQuaFrame(PhatQuaPaneController parentController, JFrame parentJFrame) {
        this.parentController = parentController;
        this.parentFrame = parentJFrame;
        this.parentFrame.setEnabled(false);
        this.dsQuaModel = new DSQuaModel();
        this.dsQuaService = new DSQuaService();
        this.nhanKhauService = new NhanKhauService();
        this.list = new ArrayList<>();
        initComponents();
        setTitle("Tạo mới danh sách quà");
        setSize(1300,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        controller = new TaoMoiDSQuaController();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to close??", "Warning!!", JOptionPane.YES_NO_OPTION) == 0) {
                    close();
                }
            }

        });
        this.setContentPane(rootPane);
        taoMoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    taoMoiButtonActionPerformed(e);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        huyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huyButtonActionPerformed(e);
            }
        });
    }

    private void initComponents() {
        header.add("Tên người nhận");
        header.add("Tên quà");
        header.add("Giá trị quà");

        List<String> qua =  new ArrayList<>();
        qua = nhanKhauService.getNhanKhauUnder18();
        for(String s: qua){
            Vector row = new Vector();
            row.add(s);
            data.add(row);
        }
        taoMoiTable.setModel(new DefaultTableModel(data, header));
        taoMoiTable.setVisible(true);
    }

    void close() {
        this.parentFrame.setEnabled(true);
        dispose();
    }


    public void taoMoiButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {//GEN-FIRST:event_CreateBtnActionPerformed
        if (validateValueInForm() && check(this.list)) {
            this.list = loadDataTable();
            this.dsQuaModel.setTenDS(tenDSTextField.getText());
            this.dsQuaModel.setThoiGian(Integer.parseInt(namTextField.getText()) );
            this.dsQuaModel.setTenDip(tenDipTextField.getText());
            this.dsQuaModel.setListQua(this.list);
            int tong = this.dsQuaModel.getGiaTriQua() * this.list.size();
            this.dsQuaModel.setTongChiPhi(tong);
            try {
               // boolean t = dsQuaService.addDS(this.dsQuaModel);
                if (dsQuaService.addDS(this.dsQuaModel)) {
                    JOptionPane.showMessageDialog(rootPane, "Thêm thành công!!");
                    //close();
                    parentController.refreshData();
                }
            } catch (SQLException e) {
                System.out.println(e.getSQLState());
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra. Vui lòng kiểm tra lại!!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public void huyButtonActionPerformed(java.awt.event.ActionEvent evt){
        this.dispose();
    }
    private boolean validateValueInForm() {
        if(tenDSTextField.getText().trim().isEmpty()
                || tenDipTextField.getText().trim().isEmpty()
                || namTextField.getText().trim().isEmpty()
        ){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập hết các trường bắt buộc", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public   boolean check(List<PhanQuaModel> list) throws SQLException, ClassNotFoundException {
        for(PhanQuaModel q:list){
            int tmp = q.getIdNguoiNhan() ;
            if(tmp == -1 ){
                JOptionPane.showMessageDialog(rootPane, "Thông tin sai!");
                return false;
            }
        }
        return true;
    }
    public List<PhanQuaModel> loadDataTable() throws SQLException, ClassNotFoundException {
        List<PhanQuaModel> list = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            PhanQuaModel qua = new PhanQuaModel();
            Vector v = (Vector) data.get(i);
            v.setSize(3);
            qua.setTenNguoiNhan((String) v.get(0));
            int id = qua.getIdNguoiNhan(qua.getTenNguoiNhan());
            qua.setIdNguoiNhan(id);
            qua.setTenQua((String) v.get(1));
            String gt = String.valueOf(v.get(2));
            qua.setGiaTri( Integer.parseInt(gt));
            dsQuaModel.setGiaTriQua(qua.getGiaTri());
            list.add(qua);
        }
        return list;
    }


}
