package controllers;

import Bean.QLPhatQuaDanhMucBean;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        jbtItem.setBackground(new Color(0));
        jbtItem.setBackground(new Color(0));
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
            item.getJbt().addMouseListener(new ButtonEvent(this.jfrMain, item.getJbt(), item.getKind()));
        });
    }

    public void setDefaultColor() {
        this.listDanhMuc.forEach((item) -> {
            if (item.getKind().equals("TrangChu")) {
                item.getJbt().setBackground(new Color(0, 160, 50));
            } else {
                item.getJbt().setBackground(new Color(102,102,102));
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

        public ButtonEvent(JFrame jfrMain, JButton jbtItem, String kind) {
            this.jfrMain = jfrMain;
            this.kind = kind;
            this.jbtItem = jbtItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch(kind) {
                case "TrangChu":
                    jfrMain.dispose();
                    new HomeFrame(user);
                    break;
                case "DSQua":
                    try {
                        view = new PhatQuaPane(this.jfrMain);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "DSThuong":
                    try {
                        view = new PhatThuongPane(this.jfrMain);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "ThongKeDS":
                    try {
                        view = new ThongKeDSPane(this.jfrMain);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    break;
                case "ThongKeHo":
                    try {
                        view = new ThongKeHoPane(this.jfrMain);
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
            setDefaultColor();
            jbtItem.setBackground(new Color(0));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jbtItem.setBackground(Color.BLACK);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jbtItem.setBackground(Color.BLACK);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kind.equalsIgnoreCase(kindSelected)) {
                if (kind.equals("TrangChu")) {
                    jbtItem.setBackground(new Color(0, 160, 50));
                } else
                {
                    jbtItem.setBackground(new Color(102,102,102));
                }
            }
        }

    }
}
