package controllers;

import models.DSQuaModel;
import models.DSThuongModel;
import services.DSQuaService;
import services.DSThuongService;
import utility.TableModelQua;
import utility.TableModelThuong;
import views.infoViews.InfoJframe;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;

public class PhatThuongPaneController {
    private JPanel jpnView;
    private JTextField jtfSearch;
    private DSThuongService dsThuongService;
    private List<DSThuongModel> listDSThuong;
    private TableModelThuong tableModelThuong;
    private final String[] COLUMNS = { "Năm học", "Tên danh sách", "Số lượng", "Tổng chi phí"};
    private JFrame parentJFrame;
    public DSThuongModel select;

    public PhatThuongPaneController(JPanel jpnView, JTextField jtfSearch ) throws SQLException, ClassNotFoundException {
        this.jpnView = jpnView;
        this.jtfSearch = jtfSearch;
        tableModelThuong = new TableModelThuong();
        this.dsThuongService = new DSThuongService();
        this.listDSThuong = this.dsThuongService.getListDSThuong();
        this.select = this.dsThuongService.getDSThuong(2);
        initAction();
    }

    public void initAction(){
        this.jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = jtfSearch.getText();
                try {
                    listDSThuong = dsThuongService.search(key.trim());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                setDataTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String key = jtfSearch.getText();
                try {
                    listDSThuong = dsThuongService.search(key.trim());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                setDataTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String key = jtfSearch.getText();
                try {
                    listDSThuong = dsThuongService.search(key.trim());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                setDataTable();
            }
        });
    }

    public void setDataTable() {
        DefaultTableModel model = tableModelThuong.setTableDsThuong(listDSThuong, COLUMNS);
        JTable table = new JTable(model) {
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
                    select = info;
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

    public void setParentJFrame(JFrame parentJFrame) {
        this.parentJFrame = parentJFrame;
    }

    public void refreshData() throws SQLException, ClassNotFoundException {
        this.listDSThuong = this.dsThuongService.getListDSThuong();
        setDataTable();
    }
    public JPanel getJpnView() {
        return jpnView;
    }

    public void setJpnView(JPanel jpnView) {
        this.jpnView = jpnView;
    }

    public JTextField getJtfSearch() {
        return jtfSearch;
    }

    public void setJtfSearch(JTextField jtfSearch) {
        this.jtfSearch = jtfSearch;
    }
}
