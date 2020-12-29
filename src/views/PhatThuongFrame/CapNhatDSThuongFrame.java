package views.PhatThuongFrame;

import controllers.PhatThuongController.TaoMoiDSThuongController;
import controllers.PhatThuongPaneController;
import models.DSThuongModel;
import models.PhanQuaModel;
import models.PhanThuongModel;
import services.DSThuongService;
import services.NhanKhauService;
import views.GuiConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CapNhatDSThuongFrame extends JFrame{
    private JLabel tenDSLabel;
    private JPanel rootPane;
    private JLabel namLb;
    private JLabel namTxf;
    private JLabel tenThuongLb;
    private JTextField tenThuongTxf;
    private JLabel giaTriLb;
    private JTextField giaTriTxf;
    private JButton themButton;
    private JLabel tenDSTxf;
    private JLabel tenNguoiNhanLb;
    private JTextField tenNguoiNhanTxf;
    private JScrollPane tableScrollPane;
    private JButton huyButton;
    private JLabel thanhTichLb;
    private JTextField thanhTichTxf;
    private JButton updateBtn;
    private JTable updateTable;
    private JButton deleteBtn;

    private DSThuongService dsThuongService;
    private PhatThuongPaneController parentController;
    private DSThuongModel dsThuongModel;
    private TaoMoiDSThuongController controller;
    private JFrame parentFrame;

    private List<PhanThuongModel> listUpdate;
    private List<PhanThuongModel> listNew;
    Vector data = new Vector<>();
    Vector header = new Vector<>();

    public CapNhatDSThuongFrame(PhatThuongPaneController parentController, JFrame parentJFrame, DSThuongModel select) {
        this.parentController = parentController;
        this.parentFrame = parentJFrame;
        this.parentFrame.setEnabled(false);
        this.dsThuongModel = select;
        this.dsThuongService = new DSThuongService();
        this.listUpdate = dsThuongModel.getListThuong();
        this.listNew = new ArrayList<>();
        initComponents();
        setTitle("Cập nhật danh sách thưởng");
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
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateBtnActionPerformed(e);
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
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteBtnActionPerfooemrd(e);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
    }


    private void initComponents() {
        tenDSTxf.setText(dsThuongModel.getTenDS());
        namTxf.setText(String.valueOf(dsThuongModel.getNamHoc()));
        header.add("ID");
        header.add("Tên người nhận");
        header.add("Tên phần thưởng");
        header.add("Thành tích");
        header.add("Giá trị thưởng");
        List<PhanThuongModel> qua = new ArrayList<>();
        if(dsThuongModel.getListThuong()!=null) {
            qua = dsThuongModel.getListThuong();
            for (PhanThuongModel s : qua) {
                Vector row = new Vector();
                row.add(s.getID());
                System.out.println(s.getID());
                row.add(s.getTenNguoiNhan());
                row.add(s.getTenThuong());
                row.add(s.getThanhTich());
                row.add(s.getGiaTriThuong());
                data.add(row);
            }
        }

        updateTable.setModel(new DefaultTableModel(data, header));
        updateTable.setVisible(true);
    }

    void close() {
        this.parentFrame.setEnabled(true);
        dispose();
    }

    public void themButtonActionPerformed(java.awt.event.ActionEvent evt){

        Vector row = new Vector();
        row.add(" ");
        row.add(tenNguoiNhanTxf.getText());
        row.add(tenThuongTxf.getText());
        row.add(thanhTichTxf.getText());
        row.add(giaTriTxf.getText());
        data.add(row);
        updateTable.setModel(new DefaultTableModel(data, header));
        updateTable.setVisible(true);
    }

    public void updateBtnActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {//GEN-FIRST:event_CreateBtnActionPerformed
        if (validateValueInForm() && check()) {
            loadDataTable(this.listUpdate, this.listNew);
            try {
                if (dsThuongService.updateDSThuong(dsThuongModel.getID(), this.listUpdate, this.listNew)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!!");
                    this.close();
                    parentController.refreshData();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra. Vui long kiểm tra lại!!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
    public void deleteBtnActionPerfooemrd(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {
        try{
            if(dsThuongService.delete(dsThuongModel.getID())){
                JOptionPane.showMessageDialog(null, "Xóa thành công!!");
                this.close();
                parentController.refreshData();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra. Vui long kiểm tra lại!!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void huyButtonActionPerformed(java.awt.event.ActionEvent evt){
        this.close();
    }

    public   boolean check() throws SQLException, ClassNotFoundException {
        try {
            for (int i = 0; i < data.size(); i++) {
                Vector v = (Vector) data.get(i);
                v.setSize(4);
                String gt = String.valueOf(v.get(1));
                PhanThuongModel q = new PhanThuongModel();
                int tmp = q.getIdNguoiNhan(gt);
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
    private boolean validateValueInForm() {
        try {
            for(int i=0; i<data.size(); i++){
                Vector v = (Vector) data.get(i);
                v.setSize(5);
                String gt = String.valueOf(v.get(4));
                Integer.parseInt(gt);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đúng định dạng trường", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void loadDataTable(List<PhanThuongModel> listUpdate, List<PhanThuongModel> listNew) throws SQLException, ClassNotFoundException {
        List<PhanThuongModel> list1 = new ArrayList<>();
        if (this.listUpdate != null) {
            for (int i = 0; i < listUpdate.size(); i++) {
                PhanThuongModel thuong = new PhanThuongModel();
                Vector v = (Vector) data.get(i);
                v.setSize(5);
                String ids = String.valueOf(v.get(0));
                thuong.setIdDsThuong(Integer.parseInt(ids));
                thuong.setTenNguoiNhan((String) v.get(1));
                int id = thuong.getIdNguoiNhan(thuong.getTenNguoiNhan());
                thuong.setIdNguoiNhan(id);
                thuong.setTenThuong((String) v.get(2));
                thuong.setThanhTich((String) v.get(3));
                String t = String.valueOf(v.get(4));
                thuong.setGiaTriThuong(Integer.parseInt(t));
                list1.add(thuong);
            }
        }
            this.listUpdate = list1;

            List<PhanThuongModel> list2 = new ArrayList<>();
            int cnt;
            if (this.listUpdate == null) cnt = 0;
            else cnt = this.listUpdate.size();
            for (int i = cnt; i < data.size(); i++) {
                PhanThuongModel thuong = new PhanThuongModel();
                Vector v = (Vector) data.get(i);
                v.setSize(5);
                thuong.setTenNguoiNhan((String) v.get(1));
                int id = thuong.getIdNguoiNhan(thuong.getTenNguoiNhan());
                thuong.setIdDsThuong(this.dsThuongModel.getID());
                thuong.setIdNguoiNhan(id);
                thuong.setTenThuong((String) v.get(2));
                thuong.setThanhTich((String) v.get(3));
                String t = String.valueOf(v.get(4));
                thuong.setGiaTriThuong(Integer.parseInt(t));
                list2.add(thuong);
            }

            this.listNew = list2;
        }



}
