package controllers;

import models.DSQuaModel;
import models.DSThuongModel;
import models.TKHoModel;
import services.DSQuaService;
import services.DSThuongService;
import services.StringService;
import services.TKHoService;
import utility.TableModelQua;
import utility.TableModelThuong;
import utility.TableThongKeHo;
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

public class ThongKeHoPaneController {
    private JPanel jpnView;
    private JTextField fromTxf;
    private JTextField toTxf;
    private JTextField chuHoTxf;
    private TKHoService tkHoService;
    private List<TKHoModel> list;
    private TableThongKeHo tableThongKeHo;
    private final String[] COLUMNS = {"Năm", "Tên Chủ Hộ", "Số Lượng", "Tổng Chi Phí"};
    public TKHoModel select;
    private int from = 0;
    private int to = 2100;

    public ThongKeHoPaneController(JTextField chuHoTxf, JTextField fromTxf, JTextField toTxf, JPanel jpnView) throws SQLException, ClassNotFoundException {
        this.chuHoTxf = chuHoTxf;
        this.fromTxf = fromTxf;
        this.toTxf = toTxf;
        this.jpnView = jpnView;
        this.tkHoService = new TKHoService();
        this.list = tkHoService.search("", from, to);
        this.tableThongKeHo = new TableThongKeHo();
    }
    public void initAction(){
        this.chuHoTxf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = chuHoTxf.getText();
                try {
                    list = tkHoService.search(key.trim(), from, to);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    setData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String key = chuHoTxf.getText();
                try {
                    list = tkHoService.search(key.trim(), from, to);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    setData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String key = chuHoTxf.getText();
                try {
                    list = tkHoService.search(key.trim(), from, to);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    setData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
    }

    public void setData() throws SQLException, ClassNotFoundException {
        int from = 0;
        int to = 2100;
        String name = "";
        try {
            if (!this.fromTxf.getText().trim().isEmpty()) {
                from = Integer.parseInt(this.fromTxf.getText().trim());
            } else {
                from = 0;
            }
            if (!this.toTxf.getText().trim().isEmpty()) {
                to = Integer.parseInt(this.toTxf.getText().trim());
            } else {
                to = 2100;
            }
            if(!this.chuHoTxf.getText().trim().isEmpty()){
                name = this.chuHoTxf.getText();
            }else {
                name = "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(toTxf, "Vui lòng nhập đúng kiểu dữ liệu!!", "Warring", JOptionPane.ERROR_MESSAGE);
        }
        setDataTable(name, from, to);
    }

    public void setDataTable(String name, int from, int to) throws SQLException, ClassNotFoundException {

        list = tkHoService.search(name, from, to);
        DefaultTableModel model = tableThongKeHo.setTableTKHo(list, COLUMNS);
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
                    TKHoModel temp = list.get(table.getSelectedRow());
                    TKHoModel info = null;
                    try {
                        int t = temp.getIdChuHo();
                        info = tkHoService.getHo(temp.getID(t),from,to);
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    select = temp;
                    InfoJframe infoJframe = new InfoJframe(info.toString(), new JFrame());
                    infoJframe.setLocationRelativeTo(null);
                    infoJframe.setVisible(true);
                }
            }

        });

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    public JPanel getJpnView() {
        return jpnView;
    }

    public void setJpnView(JPanel jpnView) {
        this.jpnView = jpnView;
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

    public JTextField getChuHoTxf() {
        return chuHoTxf;
    }

    public void setChuHoTxf(JTextField chuHoTxf) {
        this.chuHoTxf = chuHoTxf;
    }

    public TKHoService getTkHoService() {
        return tkHoService;
    }

    public void setTkHoService(TKHoService tkHoService) {
        this.tkHoService = tkHoService;
    }

    public List<TKHoModel> getList() {
        return list;
    }

    public void setList(List<TKHoModel> list) {
        this.list = list;
    }

    public TableThongKeHo getTableThongKeHo() {
        return tableThongKeHo;
    }

    public void setTableThongKeHo(TableThongKeHo tableThongKeHo) {
        this.tableThongKeHo = tableThongKeHo;
    }
}
