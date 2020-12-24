package utility;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import models.TKHoModel;


public class TableThongKeHo {
	public DefaultTableModel setTableTKHo(List<TKHoModel> list, String[] listColumn) {
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
        
        list.forEach((TKHoModel item) -> {
            obj[0] = item.getNam();
            obj[1] = item.getTenChuHo();
            obj[2] = item.getSoLuong();
            obj[3] = item.getTongChiPhi();
            dtm.addRow(obj);
        });
        
        dtm.addRow(new Object[] {"", "", "", ""});

        return dtm;
    }
}
