package utility;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import models.DSQuaModel;
import models.PhanQuaModel;

//import models.TieuSuModel;
 
public class TableModelQua {
	
	public DefaultTableModel setTableDsQua(List<DSQuaModel> listQua, String[] listColumn) {
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
        
        listQua.forEach((DSQuaModel item) -> {
            obj[0] = item.getThoiGian();
            obj[1] = item.getTenDS();
            obj[2] = item.getTenDip();
           if(item.getListQua() != null) obj[3] = item.getListQua().size();
            else obj[3] = 0;
            obj[4] = item.getTongChiPhi();
            dtm.addRow(obj);
        });
        
        dtm.addRow(new Object[] {"", "", "", "", ""});

        return dtm;
    }
	// Lay chi tiet 1 ds
	public DefaultTableModel setTableQua(List<PhanQuaModel> listQua, String[] listColumn) {
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
        
        listQua.forEach((PhanQuaModel item) -> {
            obj[0] = item.getTenNguoiNhan();
            obj[1] = item.getTenQua();
            obj[2] = item.getGiaTri();
            dtm.addRow(obj);
        });
        
        dtm.addRow(new Object[] {"", "", ""});

        return dtm;
    }
}
