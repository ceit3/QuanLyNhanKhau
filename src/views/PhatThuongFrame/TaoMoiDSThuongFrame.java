package views.PhatThuongFrame;

import controllers.PhatQuaController.TaoMoiDSQuaController;
import controllers.PhatQuaPaneController;
import controllers.PhatThuongController.TaoMoiDSThuongController;
import controllers.PhatThuongPaneController;
import models.DSQuaModel;
import models.DSThuongModel;
import models.PhanQuaModel;
import models.PhanThuongModel;
import services.DSQuaService;
import services.DSThuongService;
import views.GuiConstants;
import views.PhatQuaFrame.TaoMoiDSQuaFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TaoMoiDSThuongFrame extends JFrame{
    private JLabel tenDSLabel;
    private JPanel rootPane;
    private JLabel namLb;
    private JTextField namTxf;
    private JLabel tenThuongLb;
    private JTextField tenThuongTxf;
    private JLabel giaTriLb;
    private JTextField giaTriTxf;
    private JButton themButton;
    private JTextField tenDSTextField;
    private JLabel tenNguoiNhanLb;
    private JTextField tenNguoiNhanTxf;
    private JScrollPane tableScrollPane;
    private JTable taoMoiTable;
    private JButton taoMoiButton;
    private JButton huyButton;
    private JLabel thanhTichLb;
    private JTextField thanhTichTxf;

    private DSThuongService dsThuongService;
    private PhatThuongPaneController parentController;
    private DSThuongModel dsThuongModel;
    private TaoMoiDSThuongController controller;
    private JFrame parentFrame;

    private List<PhanThuongModel> list;
    Vector data = new Vector<>();
    Vector header = new Vector<>();

    public TaoMoiDSThuongFrame(PhatThuongPaneController parentController, JFrame parentJFrame) {
        this.parentController = parentController;
        this.parentFrame = parentJFrame;
        this.parentFrame.setEnabled(false);
        this.dsThuongModel = new DSThuongModel();
        this.dsThuongService = new DSThuongService();
        this.list = new ArrayList<>();
        initComponents();
        setTitle("Tạo mới danh sách thưởng");
        setSize(1300,600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.controller = new TaoMoiDSThuongController();

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
        themButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themButtonActionPerformed(e);
            }
        });
    }


    private void initComponents() {
        header.add("Tên người nhận");
        header.add("Tên phần thưởng");
        header.add("Thành tích");
        header.add("Giá trị thưởng");
        taoMoiTable.setModel(new DefaultTableModel(data, header));

    }

    void close() {
        this.parentFrame.setEnabled(true);
        dispose();
    }

    public void themButtonActionPerformed(java.awt.event.ActionEvent evt){

        Vector row = new Vector();
        row.add(tenNguoiNhanTxf.getText());
        row.add(tenThuongTxf.getText());
        row.add(thanhTichTxf.getText());
        row.add(giaTriTxf.getText());
        data.add(row);
        taoMoiTable.setModel(new DefaultTableModel(data, header));
        taoMoiTable.setVisible(true);
    }

    public void taoMoiButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {//GEN-FIRST:event_CreateBtnActionPerformed
        if (validateValueInForm() && check()) {
            loadDataTable(this.list);

            this.dsThuongModel.setTenDS(tenDSTextField.getText());
            this.dsThuongModel.setNamHoc(Integer.parseInt(namTxf.getText()));
            this.dsThuongModel.setListThuong(this.list);
            try {
                if (dsThuongService.addDS(this.dsThuongModel)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!!");
                    this.close();
                    parentController.refreshData();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra. Vui long kiểm tra lại!!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public void huyButtonActionPerformed(java.awt.event.ActionEvent evt){
        this.close();
    }
    private boolean validateValueInForm() {
        if(tenDSTextField.getText().trim().isEmpty()
                || namTxf.getText().trim().isEmpty()
        ){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập hết các trường bắt buộc", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            String temp = namTxf.getText();
            if (temp != null)
                Integer.parseInt(temp);
            for(int i=0; i<data.size(); i++){
                Vector v = (Vector) data.get(i);
                v.setSize(4);
                String gt = String.valueOf(v.get(3));
                Integer.parseInt(gt);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đúng định dạng trường", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public   boolean check() throws SQLException, ClassNotFoundException {
        try {
            for (int i = 0; i < data.size(); i++) {
                Vector v = (Vector) data.get(i);
                v.setSize(4);
                String gt = String.valueOf(v.get(0));
                PhanThuongModel q = new PhanThuongModel();
                int tmp = q.getIdNguoiNhan(gt);
                System.out.println("NO"+tmp);
                if (tmp == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Thong tin sai!");
                    return false;
                }
            }
        }catch (Exception e){
                JOptionPane.showMessageDialog(rootPane, "Thong tin sai!");
                return false;
            }


        return true;
    }

    public void loadDataTable(List<PhanThuongModel> list) throws SQLException, ClassNotFoundException {
        int tong=0;
        for(int i=0; i<data.size(); i++){
            PhanThuongModel thuong = new PhanThuongModel();
            Vector v = (Vector) data.get(i);
            v.setSize(4);
            thuong.setTenNguoiNhan((String) v.get(0));
            int id = thuong.getIdNguoiNhan(thuong.getTenNguoiNhan());
            thuong.setIdNguoiNhan(id);
            thuong.setTenThuong((String) v.get(1));
            thuong.setThanhTich((String) v.get(2));
            String gt = String.valueOf(v.get(3)) ;
            thuong.setGiaTriThuong(Integer.parseInt(gt) );
            tong += thuong.getGiaTriThuong();
            this.list.add(thuong);
        }
        this.dsThuongModel.setTongChiPhi(tong);
    }


}
