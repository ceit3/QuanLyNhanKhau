package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.DSThuongModel;
import models.PhanThuongModel;

/**
 * author: Nhóm 20;
 * project: Quản lý phát quà
 */

public class DSThuongService {
	// Them moi ds
	public boolean addDS(DSThuongModel ds) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			String query = "INSERT INTO ds_thuong(tenDs, namHoc, tongChiPhi) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, ds.getTenDS());
			preparedStatement.setInt(2, ds.getNamHoc());
			preparedStatement.setInt(3, ds.getTongChiPhi());

			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int genID = rs.getInt(1);
				String sql = "INSERT INTO phan_thuong( tenThuong,idDsThuong, idNguoiNhan, giaTriThuong, thanhTich)"
						+ " VALUES (?, ?, ?, ?, ?)";
				PreparedStatement preStatement = connection.prepareStatement(sql);
				ds.getListThuong().forEach((PhanThuongModel q) -> {
					try {
						q.setIdDsThuong(genID);
						preStatement.setString(1, q.getTenThuong());
						preStatement.setInt(2, genID);
						preStatement.setInt(3, q.getIdNguoiNhan());
						preStatement.setInt(4, q.getGiaTriThuong());
						preStatement.setString(5, q.getThanhTich());
						preStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
					}

				});
				preStatement.close();
			}
			preparedStatement.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}

		return true;
	}

	// Lay thong tin 1 ds
	public DSThuongModel getDSThuong(int id) throws SQLException, ClassNotFoundException {
		DSThuongModel ds = new DSThuongModel();
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			String query = "SELECT * FROM ds_thuong WHERE ID = " + id;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				ds.setID(rs.getInt("ID"));
				ds.setTenDS(rs.getString("tenDS"));
				ds.setNamHoc(rs.getInt("namHoc"));
				ds.setTongChiPhi(rs.getInt("tongChiPhi"));

				try {
					String sql = "SELECT * FROM phan_thuong WHERE idDsThuong = " + id;
					PreparedStatement prst = connection.prepareStatement(sql);
					ResultSet rs_1 = prst.executeQuery();
					List<PhanThuongModel> listThuong = new ArrayList<>();
					while (rs_1.next()) {
						PhanThuongModel q = new PhanThuongModel();
						q.setID(rs_1.getInt("ID"));
						q.setTenThuong(rs_1.getString("tenThuong"));
						q.setIdNguoiNhan(rs_1.getInt("idNguoiNhan"));
						q.setGiaTriThuong(rs_1.getInt("giaTriThuong"));
						q.setThanhTich(rs_1.getString("thanhTich"));
						q.setIdDsThuong(id);
						q.setTenNguoiNhan(q.getTenNguoiNhan(rs_1.getInt("idNguoiNhan")));
						listThuong.add(q);
					}
					ds.setListThuong(listThuong);
					prst.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}

		return ds;
	}

	// Lay list ds
	public List<DSThuongModel> getListDSThuong() throws SQLException, ClassNotFoundException {
		List<DSThuongModel> list = new ArrayList<>();
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			String query = "SELECT * FROM ds_thuong";
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt("ID"));
				System.out.println(temp.getID());
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

	// Cap nhat DS da chon
	public boolean updateDSThuong(int id, List<PhanThuongModel> listUpdate, List<PhanThuongModel> listNew)
			throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();

		try {
			// Them moi phan thuong trong ds
			if (listNew != null) {
				String sql = "INSERT INTO phan_thuong(tenThuong, idDsThuong, idNguoiNhan, giaTriThuong, thanhTich)"
						+ " VALUES (?, ?, ?, ?, ?)";
				listNew.forEach((PhanThuongModel q) -> {
					try {
						PreparedStatement preparedStatement = connection.prepareStatement(sql);
						System.out.println("hello1");
						preparedStatement.setString(1, q.getTenThuong());
						preparedStatement.setInt(2, id);
						preparedStatement.setInt(3, q.getIdNguoiNhan());
						preparedStatement.setInt(4, q.getGiaTriThuong());
						preparedStatement.setString(5, q.getThanhTich());

						preparedStatement.executeUpdate();
						preparedStatement.close();
					} catch (SQLException ex) {
						System.out.println(ex.getCause());
						Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
					}

				});

			}

			// chinh sua thong tin da co trong ds
			if (listUpdate != null) {
				String sql_1 = "UPDATE phan_thuong SET tenThuong = ?, idNguoiNhan = ?, giaTriThuong = ?, thanhTich = ? WHERE ID = ?";

				listNew.forEach((PhanThuongModel q) -> {
					try {
						PreparedStatement preStatement = connection.prepareStatement(sql_1);
						q.setIdDsThuong(id);
						preStatement.setString(1, q.getTenThuong());
						preStatement.setInt(2, q.getIdNguoiNhan());
						preStatement.setInt(3, q.getGiaTriThuong());
						preStatement.setString(4, q.getThanhTich());
						preStatement.setInt(5, q.getID());

						preStatement.executeUpdate();
						preStatement.close();
					} catch (SQLException ex) {
						Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
				String sql_2 = "UPDATE `ds_thuong` SET `tongChiPhi`= ( SELECT SUM(giaTriThuong) FROM phan_thuong WHERE idDsThuong = "
						+ id + " ) " + " WHERE ID = " + id;
				PreparedStatement p = connection.prepareStatement(sql_2);
				p.executeUpdate();
				p.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}

		return true;
	}

	// Thong ke
	// Theo thoi gian
	public List<DSThuongModel> filtDSThuong(int from, int to) throws SQLException, ClassNotFoundException {
		List<DSThuongModel> list = new ArrayList<>();
		String query = "SELECT * from ds_thuong WHERE namHoc >= " + from + " AND namHoc <= " + to
				+ " ORDER BY namHoc DESC";
		Connection connection = MysqlConnection.getMysqlConnection();
		try {

			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt(1));
				list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	//search theo keyword
	public List<DSThuongModel> search(String keyword) throws SQLException, ClassNotFoundException {
		List<DSThuongModel> list = new ArrayList<>();
		String query = "SELECT ID FROM ds_thuong ";
		if (!keyword.trim().isEmpty()) {
			query = query + "WHERE tenDS LIKE '%" + keyword + "%'";
		}
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt(1));
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

	//xoa danh sach
	public boolean delete(int id) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "DELETE FROM `phan_thuong` WHERE idDsThuong = " + id;
		String sql = "DELETE FROM `ds_thuong` WHERE ID = " + id;
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			PreparedStatement preStatement = connection.prepareStatement(sql);
			preStatement.executeUpdate();
			preStatement.close();
		} catch (Exception e){
			System.out.println(e.getStackTrace());
		}finally {
			connection.close();
		}
		return true;
	}

	
}

