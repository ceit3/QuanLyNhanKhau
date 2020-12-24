package models;

import services.MysqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TKHoModel {
	private int idChuHo;
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

	public int getIdChuHo() {
		return idChuHo;
	}

	public void setIdChuHo(int idChuHo) {
		this.idChuHo = idChuHo;
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
	public int getID(int idChuHo) throws SQLException, ClassNotFoundException {
		int id=0;
		String query = "SELECT idHoKhau from thanh_vien_cua_ho WHERE idNhanKhau = " + idChuHo;
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				id = rs.getInt("idHoKhau");
			}
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return id;
	}
	public String toString() {
		String res = "<html><style>p {padding: 5px; margin-left: 20px} table, th, td {border: 1px solid black; border-collapse: collapse;} table {width: 500px}</style> <div>"
				+"<h3> Thông tin Hộ "+idChuHo+"</h3>"
				+"<p>Tên chủ hộ: <b>"+this.getTenChuHo()+"</p>"
				+"<p>Năm: <b>"+this.getNam()+"</p>"
				+ "<p>Số lượng: <b>" +this.getSoLuong()
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
		res +="</table><br>";
		res += "<h4>Danh sach các phần quà</h4>"
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
