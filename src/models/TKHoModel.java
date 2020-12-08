package models;

import java.util.List;

public class TKHoModel {
	private int ID;
	private String tenChuHo;
	private String nam;
	private int soLuong;
	private int tongChiPhi;
	private List<PhanQuaModel> listQua;
	private List<PhanThuongModel> listThuong;

	public TKHoModel() {
		this.soLuong = 0;
		this.tongChiPhi = 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTenChuHo() {
		return tenChuHo;
	}

	public void setTenChuHo(String tenChuHo) {
		this.tenChuHo = tenChuHo;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public int getTongChiPhi() {
		return tongChiPhi;
	}

	public void setTongChiPhi(int tongChiPhi) {
		this.tongChiPhi = tongChiPhi;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public List<PhanQuaModel> getListQua() {
		return listQua;
	}

	public void setListQua(List<PhanQuaModel> listQua) {
		this.listQua = listQua;
	}

	public List<PhanThuongModel> getListThuong() {
		return listThuong;
	}

	public void setListThuong(List<PhanThuongModel> listThuong) {
		this.listThuong = listThuong;
	}
}
