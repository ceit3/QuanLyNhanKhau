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
	public List<PhanThuongModel> filtDSThuong(int id, int from, int to) {
		List<PhanThuongModel> list = new ArrayList<>();
		String query = "SELECT * from phan_thuong q INNER JOIN" + "thanh_vien_ho tv ON q.idNguoiNhan = tv.idNhanKhau "
				+ " INNER JOIN ds_thuong ds ON ds.ID = q.idDsQua" + " WHERE tv.idHoKhau = " + id
				+ "AND Year(ds.namHoc) >= " + from + "AND Year(ds.namHoc) <= " + to;
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
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
				q.setTenNguoiNhan(q.getTenNguoiNhan(rs.getInt("idNguoiNhan")));
				list.add(q);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	// Theo chi tiet qua cua 1 ho co id
	public List<PhanQuaModel> filtDSQua(int id, int from, int to) {
		List<PhanQuaModel> list = new ArrayList<>();
		String query = "SELECT * from phan_qua q INNER JOIN" + "thanh_vien_ho tv ON q.idNguoiNhan = tv.idNhanKhau "
				+ " INNER JOIN ds_qua ds ON ds.ID = q.idDsQua" + " WHERE tv.idHoKhau = " + id
				+ "AND Year(ds.thoiGian) >= " + from + "AND Year(ds.thoiGian) <= " + to;
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				PhanQuaModel q = new PhanQuaModel();
				q.setID(rs.getInt("ID"));
				q.setTenQua(rs.getString("tenQua"));
				q.setIdNguoiNhan(rs.getInt("idNguoiNhan"));
				q.setTenNguoiNhan(q.getTenNguoiNhan(rs.getInt("idNguoiNhan")));
				q.setIdDsQua(rs.getInt("idDsQua"));
				list.add(q);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	// Lay danh sach 1 ho loc theo khoang thoi gian
	public TKHoModel getHo(int id, int from, int to) throws ClassNotFoundException, SQLException {
		TKHoModel h = new TKHoModel();
		List<PhanQuaModel> listQua = this.filtDSQua(id, from, to);
		List<PhanThuongModel> listThuong = this.filtDSThuong(id, from, to);
		h.setID(id);
		h.setListQua(listQua);
		h.setListThuong(listThuong);
		h.setNam(from + "-" + to);
		h.setSoLuong(listQua.size() + listThuong.size());
		int tong = 0;
		for (PhanThuongModel q : listThuong) {
			tong = tong + q.getGiaTriThuong();
		}
		for (PhanQuaModel q : listQua) {
			tong = tong + q.getGiaTri();
		}
		h.setTongChiPhi(tong);

		String query = "SELECT tenChuHo FROM ho_khau WHERE ID = " + id;
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			h.setTenChuHo(rs.getString(1));
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return h;
	}

	// Search ho
	public List<TKHoModel> search(String keyword, int from, int to) {
		List<TKHoModel> list = new ArrayList<>();
		String query = "SELECT ID FROM ho_khau";
		if (!keyword.trim().isEmpty()) {
			query = query + "WHERE tenChuHo LIKE '%" + keyword + "%'";
		}
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				TKHoModel temp = new TKHoModel();
				temp = this.getHo(rs.getInt(1), from, to);
				if (temp != null)
					list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}
