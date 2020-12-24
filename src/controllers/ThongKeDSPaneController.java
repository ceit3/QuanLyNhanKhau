package controllers;

import Bean.NhanKhauBean;
import models.DSQuaModel;
import models.DSThuongModel;
import models.NhanKhauModel;
import services.DSQuaService;
import services.DSThuongService;
import services.NhanKhauService;
import services.StringService;
import utility.ClassTableModel;
import utility.TableModelQua;
import utility.TableModelThuong;
import views.infoViews.InfoJframe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class ThongKeDSPaneController {
    private JComboBox chooseBox;
    private JTextField fromTxf;
    private JTextField toTxf;
    private JPanel jpnView;
    private JFrame parentJFrame;
    private DSQuaService dsQuaService;
    private DSThuongService dsThuongService;
    private List<DSQuaModel> listDSQua;
    private List<DSThuongModel> listDSThuong;
    private TableModelQua tableModelQua;
    private TableModelThuong tableModelThuong;
    private final String[] COLUMNSQUA = {"Năm", "Tên danh sách", "Tên Dịp", "Số Lượng", "Tổng Chi Phí"};
    private final String[] COLUMNSTHUONG = {"Năm học", "Tên danh sách", "Số Lượng", "Tổng Chi Phí"};
    public DSQuaModel selectQua;
    public DSThuongModel selectThuong;
    private int from;
    private int to;

    public ThongKeDSPaneController(JComboBox chooseBox, JTextField fromTxf, JTextField toTxf, JPanel jpnView) throws SQLException, ClassNotFoundException {
        this.chooseBox = chooseBox;
        this.fromTxf = fromTxf;
        this.toTxf = toTxf;
        this.jpnView = jpnView;
        this.dsQuaService = new DSQuaService();
        this.dsThuongService = new DSThuongService();
        this.listDSQua = this.dsQuaService.getListDSQua();
        this.listDSThuong = this.dsThuongService.getListDSThuong();
        this.tableModelQua = new TableModelQua();
        this.tableModelThuong = new TableModelThuong();
        this.from = 0;
        this.to = 2100;
    }

    public void setData() throws SQLException, ClassNotFoundException {
        String type = StringService.covertToString((String)this.chooseBox.getSelectedItem());
        try {
            if (!this.fromTxf.getText().trim().isEmpty()) {
                this.from = Integer.parseInt(this.fromTxf.getText().trim());
            } else {
                this.from = 0;
            }
            if (!this.toTxf.getText().trim().isEmpty()) {
                this.to = Integer.parseInt(this.toTxf.getText().trim());
            } else {
                this.to = 2100;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(toTxf, "Vui lòng nhập đúng kiểu dữ liệu!!", "Warring", JOptionPane.ERROR_MESSAGE);
        }
        if(type == "DS Qua"){
            this.listDSQua = dsQuaService.filtDSQua(this.from, this.to);
            setDataTableQua(listDSQua);
        }else{
            this.listDSThuong = dsThuongService.filtDSThuong(this.from, this.to);
            setDataTableThuong(listDSThuong);
        }
    }

    private void setDataTableThuong(List<DSThuongModel> listDSThuong) {
        DefaultTableModel model = tableModelThuong.setTableDsThuong(listDSThuong, COLUMNSTHUONG);
        JTable table = new JTable(model){
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };

        // thiet ke bang

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(0).setMinWidth(80);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JOptionPane.showConfirmDialog(null, table.getSelectedRow());
                if (e.getClickCount() > 1) {
                    DSThuongModel temp = listDSThuong.get(table.getSelectedRow());
                    DSThuongModel info = null;
                    try {
                        info = dsThuongService.getDSThuong(temp.getID());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    selectThuong = temp;
                    InfoJframe infoJframe = new InfoJframe(info.toString(), parentJFrame);
                    infoJframe.setLocationRelativeTo(null);
                    infoJframe.setVisible(true);
                }
            }

        });

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1000, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    public void setDataTableQua(List<DSQuaModel> listDSQua) {
        DefaultTableModel model = tableModelQua.setTableDsQua(listDSQua, COLUMNSQUA);
        JTable table = new JTable(model){
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };

        // thiet ke bang

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(0).setMinWidth(80);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JOptionPane.showConfirmDialog(null, table.getSelectedRow());
                if (e.getClickCount() > 1) {
                    DSQuaModel temp = listDSQua.get(table.getSelectedRow());
                    DSQuaModel info = null;
                    try {
                        info = dsQuaService.getDSQua(temp.getID());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    selectQua = temp;
                    System.out.println(info.getID());
                    InfoJframe infoJframe = new InfoJframe(info.toString(), parentJFrame);
                    infoJframe.setLocationRelativeTo(null);
                    infoJframe.setVisible(true);
                }
            }

        });

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1000, 400));
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }
    public JComboBox getChooseBox() {
        return chooseBox;
    }

    public void setChooseBox(JComboBox chooseBox) {
        this.chooseBox = chooseBox;
    }

    public JTextField getFromTxf() {
        return fromTxf;
    }

    public void setFromTxf(JTextField fromTxf) {
        this.fromTxf = fromTxf;
    }

    public JTextField getToTxf() {
        return toTxf;
    }

    public void setToTxf(JTextField toTxf) {
        this.toTxf = toTxf;
    }

    public JPanel getJpnView() {
        return jpnView;
    }

    public void setJpnView(JPanel jpnView) {
        this.jpnView = jpnView;
    }

    public DSQuaService getDsQuaService() {
        return dsQuaService;
    }

    public void setDsQuaService(DSQuaService dsQuaService) {
        this.dsQuaService = dsQuaService;
    }

    public DSThuongService getDsThuongService() {
        return dsThuongService;
    }

    public void setDsThuongService(DSThuongService dsThuongService) {
        this.dsThuongService = dsThuongService;
    }

    public List<DSQuaModel> getListDSQua() {
        return listDSQua;
    }

    public void setListDSQua(List<DSQuaModel> listDSQua) {
        this.listDSQua = listDSQua;
    }

    public List<DSThuongModel> getListDSThuong() {
        return listDSThuong;
    }

    public void setListDSThuong(List<DSThuongModel> listDSThuong) {
        this.listDSThuong = listDSThuong;
    }

    public TableModelQua getTableModelQua() {
        return tableModelQua;
    }

    public void setTableModelQua(TableModelQua tableModelQua) {
        this.tableModelQua = tableModelQua;
    }

    public TableModelThuong getTableModelThuong() {
        return tableModelThuong;
    }

    public void setTableModelThuong(TableModelThuong tableModelThuong) {
        this.tableModelThuong = tableModelThuong;
    }

    public JFrame getParentJFrame() {
        return parentJFrame;
    }

    public void setParentJFrame(JFrame parentJFrame) {
        this.parentJFrame = parentJFrame;
    }
}
