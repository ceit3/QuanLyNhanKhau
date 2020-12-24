package views.PhatQuaFrame;

import com.toedter.calendar.JDateChooser;
import controllers.PhatQuaController.CapNhatDSQuaController;
import controllers.PhatQuaController.TaoMoiDSQuaController;
import controllers.PhatQuaPaneController;
import models.DSQuaModel;
import models.PhanQuaModel;
import services.DSQuaService;
import services.NhanKhauService;

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

public class CapNhatDSQuaFrame extends JFrame {
    private JPanel rootPane;
    private JLabel tenDSLabel;
    private JLabel tenDipLabel;
    private JTextField tenDipTextField;
    private JDateChooser namTextField;
    private JButton huyButton;
    private JTextField tenDSTextField;
    private JScrollPane tableScrollPane;
    private JLabel namLabel;
    private JButton updateBtn;
    private JTable updateTable;
    private JLabel tenDSTxf;
    private JLabel tenDipTxf;
    private JLabel namTxf;
    private JButton deleteBtn;
    private DSQuaService dsQuaService;
    private NhanKhauService nhanKhauService;
    private PhatQuaPaneController parentController;
    private JFrame parentFrame;
    private DSQuaModel dsQuaModel;
    private CapNhatDSQuaController controller;

    public List<PhanQuaModel> list = new ArrayList<>();
    Vector data = new Vector<>();
    Vector header = new Vector<>();

    public CapNhatDSQuaFrame(PhatQuaPaneController parentController, JFrame parentJFrame, DSQuaModel select) {
        this.dsQuaModel = dsQuaModel;
        this.parentController = parentController;
        this.parentFrame = parentJFrame;
        this.parentFrame.setEnabled(false);
        this.dsQuaModel = select;
        this.dsQuaService = new DSQuaService();
        initComponents();
        setTitle("Cập nhật danh sách quà");
        setSize(1000,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        controller = new CapNhatDSQuaController();

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
        tenDSTxf.setText(dsQuaModel.getTenDS());
        tenDipTxf.setText(dsQuaModel.getTenDip());
        namTxf.setText(String.valueOf(dsQuaModel.getThoiGian()));
        header.add("ID");
        header.add("Tên ngừoi nhận");
        header.add("Tên quà");
        header.add("Giá trị quà");
        List<PhanQuaModel> qua = new ArrayList<>();
        if(dsQuaModel.getListQua()!=null) {
            qua = dsQuaModel.getListQua();
            for (PhanQuaModel s : qua) {
                Vector row = new Vector();
                row.add(s.getID());
                row.add(s.getTenNguoiNhan());
                row.add(s.getTenQua());
                row.add(s.getGiaTri());
               // System.out.println("ID"+ s.getID());
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


    public void updateBtnActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {//GEN-FIRST:event_CreateBtnActionPerformed
        if (check(list)) {
            list = loadDataTable();
            this.dsQuaModel.setListQua(list);
            try {
                if (dsQuaService.updateDSQua(this.dsQuaModel.getID(), this.dsQuaModel.getListQua())) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!!");
                    close();
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
            if(dsQuaService.delete(dsQuaModel.getID())){
                JOptionPane.showMessageDialog(null, "Xóa thành công!!");
                close();
                parentController.refreshData();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra. Vui long kiểm tra lại!!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void huyButtonActionPerformed(java.awt.event.ActionEvent evt){
        this.dispose();
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
            v.setSize(4);

            qua.setID((Integer)v.get(0));
            qua.setTenNguoiNhan((String) v.get(1));
            int id = qua.getIdNguoiNhan(qua.getTenNguoiNhan());
            qua.setIdNguoiNhan(id);
            qua.setTenQua((String) v.get(2));
            String gt = String.valueOf(v.get(3)) ;
            qua.setGiaTri(Integer.parseInt(gt));
            dsQuaModel.setGiaTriQua(qua.getGiaTri());

            list.add(qua);
        }
        return list;
    }


}
