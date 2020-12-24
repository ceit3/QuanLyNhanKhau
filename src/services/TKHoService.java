package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.PhanQuaModel;
import models.PhanThuongModel;
import models.TKHoModel;

public class TKHoService {
	// Theo chi tiet thuong 1 Ho co id
	public List<PhanThuongModel> filtDSThuong(int id, int from, int to) throws SQLException, ClassNotFoundException {
		List<PhanThuongModel> list = new ArrayList<>();
		String query = "SELECT * from phan_thuong q INNER JOIN " + "thanh_vien_cua_ho tv ON q.idNguoiNhan = tv.idNhanKhau "
				+ " INNER JOIN ds_thuong ds ON ds.ID = q.idDsThuong"
		+" WHERE tv.idHoKhau = " + id
				+ " AND ds.namHoc >= " + from + " AND ds.namHoc <= " + to;
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {

				PhanThuongModel q = new PhanThuongModel();
				q.setID(rs.getInt("ID"));
				q.setTenThuong(rs.getString("tenThuong"));
				q.setIdNguoiNhan(rs.getInt("idNguoiNhan"));
				q.setGiaTriThuong(rs.getInt("giaTriThuong"));
				q.setThanhTich(rs.getString("thanhTich"));
				q.setIdDsThuong(rs.getInt("idDsThuong"));
				String h = q.getTenNguoiNhan(rs.getInt("idNguoiNhan"));
				q.setTenNguoiNhan(h);

				list.add(q);

			}
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return list;
	}

	// Theo chi tiet qua cua 1 ho co id
	public List<PhanQuaModel> filtDSQua(int id, int from, int to) throws SQLException, ClassNotFoundException {
		List<PhanQuaModel> list = new ArrayList<>();
		String query = "SELECT * from phan_qua q INNER JOIN " + "thanh_vien_cua_ho tv ON q.idNguoiNhan = tv.idNhanKhau "
				+ " INNER JOIN ds_qua ds ON ds.ID = q.idDsQua "
				+ " WHERE tv.idHoKhau = " + id
				+ " AND ds.thoiGian >= " + from + " AND ds.thoiGian <= " + to;
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				PhanQuaModel q = new PhanQuaModel();
				q.setID(rs.getInt("ID"));
				q.setTenQua(rs.getString("tenQua"));
				q.setIdNguoiNhan(rs.getInt("idNguoiNhan"));
				q.setTenNguoiNhan(q.getTenNguoiNhan(rs.getInt("idNguoiNhan")));
				q.setIdDsQua(rs.getInt("idDsQua"));
				q.setGiaTri(rs.getInt("giaTriQua"));
				list.add(q);
			}
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return list;
	}

	// Lay danh sach 1 ho loc theo khoang thoi gian
	public TKHoModel getHo(int id, int from, int to) throws ClassNotFoundException, SQLException {
		TKHoModel h = new TKHoModel();
		List<PhanQuaModel> listQua = this.filtDSQua(id, from, to);
		List<PhanThuongModel> listThuong = this.filtDSThuong(id, from, to);
		h.setListQua(listQua);
		h.setListThuong(listThuong);
		String year = "";
		if(from != 0) year = year + from;
		year = year + " - ";
		if(to != 2100) year = year + to;
		h.setNam(year);
		int s=0;
		if(listQua != null)
			s+= listQua.size();
		if(listThuong != null)
			s+= listThuong.size();
		h.setSoLuong(s);

		int tong = 0;
		if(listThuong!= null)
			for (PhanThuongModel q : listThuong) {
			tong = tong + q.getGiaTriThuong();
			}
		if(listQua != null)
			for (PhanQuaModel q : listQua) {
			tong = tong + q.getGiaTri();
			}
		h.setTongChiPhi(tong);

		String query = "SELECT n.ID, n.hoTen FROM thanh_vien_cua_ho tv "
				+ " INNER JOIN nhan_khau n ON n.ID = tv.idNhanKhau"
		+" WHERE tv.idHoKhau = " + id;
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				h.setIdChuHo(rs.getInt("ID"));
				h.setTenChuHo(rs.getString("hoTen"));
				preparedStatement.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return h;
	}

	// Search ho
	public List<TKHoModel> search(String keyword, int from, int to) throws SQLException, ClassNotFoundException {
		List<TKHoModel> list = new ArrayList<>();
		String query = "SELECT DISTINCT tv.idHoKhau FROM thanh_vien_cua_ho tv INNER JOIN nhan_khau n ON tv.idNhanKhau = n.ID  ";
		if (keyword != null) {
			query = query + "WHERE n.hoTen LIKE '%" + keyword + "%'";
		}

		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				TKHoModel temp = new TKHoModel();
				temp = this.getHo(rs.getInt("idHoKhau"), from, to);
				if (temp != null)
					list.add(temp);
			}
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return list;
	}

}
