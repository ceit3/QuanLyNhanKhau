package utility;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import models.DSThuongModel;
import models.PhanThuongModel;


public class TableModelThuong {
	public DefaultTableModel setTableDsThuong(List<DSThuongModel> listQua, String[] listColumn) {
        final int column = listColumn.length;
        
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                 return columnIndex == 4 ? Boolean.class : String.class;
            }
        };
        
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[column];
        
        listQua.forEach((DSThuongModel item) -> {
            obj[0] = item.getNamHoc();
            obj[1] = item.getTenDS();
            if(item.getListThuong() != null)
                obj[2] = item.getListThuong().size();
            else obj[2] = 0;
            obj[3] = item.getTongChiPhi();
            dtm.addRow(obj);
        });
        
        dtm.addRow(new Object[] {"", "", ""});

        return dtm;
    }
	
	public DefaultTableModel setTableThuong(List<PhanThuongModel> listQua, String[] listColumn) {
        final int column = listColumn.length;
        
        DefaultTableModel dtm = new DefaultTableModel()  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                 return columnIndex == 5 ? Boolean.class : String.class;
            }
        };
        
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        obj = new Object[column];
        
        listQua.forEach((PhanThuongModel item) -> {
            obj[0] = item.getTenNguoiNhan();
            obj[1] = item.getThanhTich();
            obj[2] = item.getTenThuong();
            obj[3] = item.getGiaTriThuong();
            dtm.addRow(obj);
        });
        
        dtm.addRow(new Object[] {"", "", "", ""});

        return dtm;
    }
}
