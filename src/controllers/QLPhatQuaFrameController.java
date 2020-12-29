package controllers;

import Bean.QLPhatQuaDanhMucBean;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.cert.CertificateParsingException;
import java.sql.SQLException;
import java.util.List;

public class QLPhatQuaFrameController {
    private JFrame jfrMain;
    private JPanel root;
    private String kindSelected;
    private List<QLPhatQuaDanhMucBean> listDanhMuc;
    private String user;

    public QLPhatQuaFrameController(JPanel root, JFrame jfrMain, String user) {
        this.jfrMain = jfrMain;
        this.root = root;
        this.user = user;
    }


    // set panel for root
    public void setView(JButton jbtItem, String kind) throws SQLException, ClassNotFoundException {
        this.kindSelected = kind;

        JPanel view = new  JPanel();
        switch(kind) {
            case "TrangChu":
                view = new PhatQuaPane(this.jfrMain);
                break;
            case "DSQua":
                view = new PhatQuaPane(this.jfrMain);
                break;
            case "DSThuong":
                view = new PhatThuongPane(this.jfrMain);
                break;
            case "ThongKeDS":
                view = new ThongKeDSPane(this.jfrMain);
                break;
            case "ThongKeHo":
                view = new ThongKeHoPane(this.jfrMain);
                break;
            //any more
            default:
                break;
        }
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(view);
        root.validate();
        root.repaint();
    }

    //set animation for menu panel
    public void setEvent(List<QLPhatQuaDanhMucBean> listDanhMuc) {
        this.listDanhMuc = listDanhMuc;
        this.listDanhMuc.forEach((item) -> {
            item.getJbt().addMouseListener(new ButtonEvent(this.jfrMain, item.getJbt(), item.getKind(), this.listDanhMuc));
        });
    }

    public void setDefaultColor(List<QLPhatQuaDanhMucBean> listDanhMuc) {
        listDanhMuc.forEach((item) -> {
            if (item.getKind().equals("TrangChu")) {
                item.getJbt().setBackground(new Color(0, 160, 50));
            } else {
                item.getJbt().setBackground(new Color(0, 0, 0));
            }
        });
    }

    class ButtonEvent implements MouseListener {

        private JPanel view = new JPanel();
        private JFrame jfrMain;
        private String kind;
        private JButton jbtItem;

        public ButtonEvent(JButton jbtItem, String kind) {
            this.kind = kind;
            this.jbtItem = jbtItem;
        }

        public ButtonEvent(JFrame jfrMain, JButton jbtItem, String kind, List<QLPhatQuaDanhMucBean> listDanhMuc) {
            this.jfrMain = jfrMain;
            this.kind = kind;
            this.jbtItem = jbtItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            setDefaultColor(listDanhMuc);
            switch(kind) {
                case "TrangChu":
                    jfrMain.dispose();
                    new HomeFrame(user);
                    break;
                case "DSQua":
                    try {
                        view = new PhatQuaPane(this.jfrMain);
                        this.jbtItem.setBackground(Color.gray);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "DSThuong":
                    try {
                        view = new PhatThuongPane(this.jfrMain);
                        this.jbtItem.setBackground(Color.gray);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "ThongKeDS":
                    try {
                        view = new ThongKeDSPane(this.jfrMain);
                        this.jbtItem.setBackground(Color.gray);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "ThongKeHo":
                    try {
                        view = new ThongKeHoPane(this.jfrMain);
                        this.jbtItem.setBackground(Color.gray);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                //any more
                default:
                    break;
            }

            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(view);
            root.validate();
            root.repaint();

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }


        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}
