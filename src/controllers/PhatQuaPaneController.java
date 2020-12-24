package controllers;

import models.DSQuaModel;
import services.DSQuaService;
import utility.TableModelQua;
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

public class PhatQuaPaneController {
    private JPanel jpnView;
    private JTextField jtfSearch;
    private DSQuaService dsQuaService;
    private List<DSQuaModel> listDsQua;
    private TableModelQua tableModelQua;
    private final String[] COLUMNS = { "Năm","Tên danh sách", "Tên Dịp", "Số lượng","Tổng chi phí"};
    private JFrame parentJFrame;
    public DSQuaModel select;

    public PhatQuaPaneController(JPanel jpnView, JTextField jtfSearch ) throws SQLException, ClassNotFoundException {
        this.jpnView = jpnView;
        this.jtfSearch = jtfSearch;
        tableModelQua = new TableModelQua();
        this.dsQuaService = new DSQuaService();
        this.listDsQua = this.dsQuaService.getListDSQua();
        this.select = this.dsQuaService.getDSQua(2);
        initAction();
    }

    public void initAction(){
        this.jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = jtfSearch.getText();
                try {
                    listDsQua = dsQuaService.search(key.trim());
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
                    listDsQua = dsQuaService.search(key.trim());
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
                    listDsQua = dsQuaService.search(key.trim());
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
        DefaultTableModel model = tableModelQua.setTableDsQua(listDsQua, COLUMNS);
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
                    DSQuaModel temp = listDsQua.get(table.getSelectedRow());
                    DSQuaModel info = null;
                    try {
                        info = dsQuaService.getDSQua(temp.getID());
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

    public void refreshData() {
        try {
            this.listDsQua = this.dsQuaService.getListDSQua();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
