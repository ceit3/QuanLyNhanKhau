package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.DSQuaModel;
import models.PhanQuaModel;
import models.TKHoModel;

/**
 * author: Nhóm 20;
 * project: Quản lý phát quà
 */

public class DSQuaService {
	// Them moi ds
	public boolean addDS(DSQuaModel ds) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO ds_qua(tenDs, tenDip, thoiGian, tongChiPhi, giaTriQua)" + " VALUES (?, ?, ?, ?, ?)";
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, ds.getTenDS());
			preparedStatement.setString(2, ds.getTenDip());
			preparedStatement.setInt(3, ds.getThoiGian());
			preparedStatement.setInt(4, ds.getTongChiPhi());
			preparedStatement.setInt(5, ds.getGiaTriQua());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int genID = rs.getInt(1);
				System.out.println("ID ds: " + genID);
				String sql = "INSERT INTO phan_qua(tenQua,idDsQua, idNguoiNhan)" + " VALUES (?, ?, ?)";
				PreparedStatement preStatement = connection.prepareStatement(sql);
				ds.getListQua().forEach((PhanQuaModel q) -> {
					try {
						q.setIdDsQua(genID);
						preStatement.setString(1, q.getTenQua());
						System.out.println(q.getTenQua() + " " +q.getIdNguoiNhan()+" "+q.getIdDsQua());
						preStatement.setInt(2, genID);
						preStatement.setInt(3, q.getIdNguoiNhan());

						preStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
					}

				});
				preStatement.close();
			}
			preparedStatement.close();
		} catch (Exception e){
			System.out.println(e.getStackTrace());
		}finally {
			connection.close();
		}
		return true;
	}

	// Lay ra 1 ds
	public DSQuaModel getDSQua(int id) throws SQLException, ClassNotFoundException {
		DSQuaModel ds = new DSQuaModel();

		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			String query = "SELECT * FROM ds_qua WHERE ID = " + id;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.next()) System.out.println("2");
			ds.setID(rs.getInt("ID"));
			ds.setTenDS(rs.getString("tenDS"));
			ds.setTenDip(rs.getString("tenDip"));
			ds.setThoiGian(rs.getInt("thoiGian"));
			ds.setTongChiPhi(rs.getInt("tongChiPhi"));
			ds.setGiaTriQua(rs.getInt("giaTriQua"));

			try {
				String sql = "SELECT * FROM phan_qua WHERE idDsQua = " +id;
				PreparedStatement prst = connection.prepareStatement(sql);
				ResultSet rs_1 = prst.executeQuery();
				List<PhanQuaModel> listQua = new ArrayList<>();
				while (rs_1.next()) {
					PhanQuaModel q = new PhanQuaModel();
					q.setID(rs_1.getInt("ID"));
					q.setTenQua(rs_1.getString("tenQua"));
					q.setIdNguoiNhan(rs_1.getInt("idNguoiNhan"));
					q.setTenNguoiNhan(q.getTenNguoiNhan(rs_1.getInt("idNguoiNhan")));
					q.setIdDsQua(id);
					q.setGiaTri(ds.getGiaTriQua());
					listQua.add(q);
				}
				ds.setListQua(listQua);
				prst.close();
			} catch (SQLException ex) {
				Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
			}

			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connection.close();
		}

		return ds;
	}

	// Lay ra list ds
	public List<DSQuaModel> getListDSQua() throws SQLException, ClassNotFoundException {
		List<DSQuaModel> list = new ArrayList<>();
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			String query = "SELECT ID FROM ds_qua";
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = getDSQua(rs.getInt("ID"));
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

	// cap nhat DS
	public boolean updateDSQua(int id, List<PhanQuaModel> listUpdate)
			throws ClassNotFoundException, SQLException {
		int tong=0;
		int gt = 0;

		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			// cap nhat thong tin da co trong ds
			if (listUpdate != null) {
				String sql_1 = "UPDATE phan_qua SET tenQua = ?, idNguoiNhan = ? WHERE idDsQua = "+id +" AND ID = ? ";

				listUpdate.forEach((PhanQuaModel q) -> {
					try {
						PreparedStatement preStatement = connection.prepareStatement(sql_1);
						q.setIdDsQua(id);
						preStatement.setString(1, q.getTenQua());
						preStatement.setInt(2, q.getIdNguoiNhan());
						preStatement.setInt(3, q.getID());
						System.out.println(q.getTenQua()+" "+q.getIdDsQua()+" "+q.getID());
						preStatement.executeUpdate();
						preStatement.close();
					} catch (SQLException ex) {
						Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
				gt = listUpdate.get(0).getGiaTri();
				tong = gt*listUpdate.size();

				String sql = "UPDATE ds_qua SET tongChiPhi = "+tong+" , giaTriQua = " + gt +" WHERE ID = " +id;
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}

		return true;
	}

	// Thong ke
	// Theo thoi gian
	public List<DSQuaModel> filtDSQua(int from, int to) throws SQLException, ClassNotFoundException {
		List<DSQuaModel> list = new ArrayList<>();
		String query = "SELECT * from ds_qua WHERE thoiGian >= " + from + " AND thoiGian <= " + to
				+ " ORDER BY thoiGian DESC";
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = this.getDSQua(rs.getInt(1));
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
	//search theo key
	public List<DSQuaModel> search(String keyword) throws SQLException, ClassNotFoundException {
		List<DSQuaModel> list = new ArrayList<>();
		String query = "SELECT ID FROM ds_qua ";
		if (!keyword.trim().isEmpty()) {
			query = query + "WHERE tenDS LIKE '%" + keyword + "%'";
		}
		Connection connection = MysqlConnection.getMysqlConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = this.getDSQua(rs.getInt(1));
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
		String query = "DELETE FROM `phan_qua` WHERE idDsQua = " + id;
		String sql = "DELETE FROM `ds_qua` WHERE ID = " + id;
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