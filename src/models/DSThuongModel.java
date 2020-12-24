package models;

import java.util.List;

public class DSThuongModel {
    private int ID;
    private String tenDS;
    private int namHoc;
    private int tongChiPhi;
    private List<PhanThuongModel> listThuong;
    
    public DSThuongModel() {
    	this.tongChiPhi=0;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }

    public int getTongChiPhi() {
        return tongChiPhi;
    }

    public void setTongChiPhi(int tongChiPhi) {
        this.tongChiPhi = tongChiPhi;
    }

	public String getTenDS() {
		return tenDS;
	}

	public void setTenDS(String tenDS) {
		this.tenDS = tenDS;
	}

	public List<PhanThuongModel> getListThuong() {
		return listThuong;
	}

	public void setListThuong(List<PhanThuongModel> listThuong) {
		this.listThuong = listThuong;
	}

	public String toString() {
		String res = "<html><style>p {padding: 5px; margin-left: 20px} table, th, td {border: 1px solid black; border-collapse: collapse;} table {width: 500px}</style> <div>"
				+"<h3> Thông tin danh sách "+tenDS+"</h3>"
				+"<p>Mã danh sách: <b>"+this.getID()+"</p>"
				+"<p>Năm: <b>"+this.getNamHoc()+"</p>"
				+ "<p>Số lượng: <b>" +this.listThuong.size()
				+"<p>Tổng chi phí: <b>"+this.getTongChiPhi()+"</p>"
				+ "<h4>Danh sách phần thưởng</h4>"
				+" <table>"
                + "<tr>"
                + "<th>Tên người nhận</th>"
                + "<th>Thành tích</th>"
                + "<th>Phần Thưởng</th>"
                + "<th>Giá trị</th>"
                + "</tr>";
		for(int i=0; i<listThuong.size(); i++) {
			res +="<tr>"
					+"<td>"+listThuong.get(i).getTenNguoiNhan()+"</td>"
					+"<td>"+listThuong.get(i).getThanhTich()+"</td>"
					+"<td>"+listThuong.get(i).getTenThuong()+"</td>"
					+"<td>"+listThuong.get(i).getGiaTriThuong()+"</td>"
					+"</tr>";
		}
		res += "</table></div></html>";
        return res;
	}
	
}
	
