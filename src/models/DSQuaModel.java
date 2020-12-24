package models;

import java.sql.Date;
import java.util.List;

public class DSQuaModel {
	private int ID;
	private String tenDS;
	private String tenDip;
	private int thoiGian;
	private int giaTriQua;
	private int tongChiPhi;
	private List<PhanQuaModel> listQua;

	public DSQuaModel() {
		this.giaTriQua = 0;
		this.tongChiPhi = 0;
	}

	public String getTenDS() {
		return tenDS;
	}

	public void setTenDS(String tenDS) {
		this.tenDS = tenDS;
	}

	public List<PhanQuaModel> getListQua() {
		return listQua;
	}

	public void setListQua(List<PhanQuaModel> listQua) {
		this.listQua = listQua;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTenDip() {
		return tenDip;
	}

	public void setTenDip(String tenDip) {
		this.tenDip = tenDip;
	}

	public int getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(int thoiGian) {
		this.thoiGian = thoiGian;
	}

	public int getGiaTriQua() {
		return giaTriQua;
	}

	public void setGiaTriQua(int giaTriQua) {
		this.giaTriQua = giaTriQua;
	}

	public int getTongChiPhi() {
		return tongChiPhi;
	}

	public void setTongChiPhi(int tongChiPhi) {
		this.tongChiPhi = tongChiPhi;
	}

	public String toString() {
		int s = 0;
		if(listQua != null) s = this.listQua.size();
		String res = "<html><style>p {padding: 5px; margin-left: 20px} table, th, td {border: 1px solid black; border-collapse: collapse;} table {width: 500px}</style> <div>"
				+"<h3> Thông tin danh sách "+this.tenDS+"</h3>"
				+"<p>Mã danh sách: <b>"+this.ID+"</p>"
				+"<p>Tên danh sách: <b>"+this.tenDip+"</p>"
				+"<p>Thời gian: <b>"+this.thoiGian+"</p>"
				+ "<p>Số lượng: <b>" + s + "</p>"
				+"<p>Tổng chi phí: <b>"+this.tongChiPhi+"</p>"
				+ "<h4>Danh sach các phần quà</h4>"
				+ "<table>"
                + "<tr>"
                + "<th>Tên người nhận</th>"
                + "<th>Phần quà</th>"
                + "<th>Giá trị</th>"
                + "</tr>";
		if(listQua != null) {
			for (int i = 0; i < listQua.size(); i++) {
				res += "<tr>"
						+ "<td>" + listQua.get(i).getTenNguoiNhan() + "</td>"
						+ "<td>" + listQua.get(i).getTenQua() + "</td>"
						+ "<td>" + listQua.get(i).getGiaTri() + "</td>"
						+ "</tr>";
			}
		}
		res += "</table></div></html>";
        return res;
	}

}