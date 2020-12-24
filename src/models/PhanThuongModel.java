package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.MysqlConnection;

public class PhanThuongModel {
	private int ID;
	private String tenThuong;
	private int idDsThuong;
	private int idNguoiNhan;
	private String thanhTich;
	private int giaTriThuong;
	private String tenNguoiNhan;

	public PhanThuongModel() {
		this.giaTriThuong = 0;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTenThuong() {
		return tenThuong;
	}

	public void setTenThuong(String tenThuong) {
		this.tenThuong = tenThuong;
	}

	public String getThanhTich() {
		return thanhTich;
	}

	public void setThanhTich(String thanhTich) {
		this.thanhTich = thanhTich;
	}

	public int getGiaTriThuong() {
		return giaTriThuong;
	}

	public void setGiaTriThuong(int giaTriThuong) {
		this.giaTriThuong = giaTriThuong;
	}

	public int getIdDsThuong() {
		return idDsThuong;
	}

	public void setIdDsThuong(int idDsThuong) {
		this.idDsThuong = idDsThuong;
	}

	public int getIdNguoiNhan() {
		return idNguoiNhan;
	}
	public int getIdNguoiNhan(String hoTen) throws ClassNotFoundException, SQLException{
		String query = "SELECT ID from nhan_khau where hoTen = '"+hoTen + "'";
		Connection connection = MysqlConnection.getMysqlConnection();
		try{
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				this.idNguoiNhan = rs.getInt("ID");
			}
			preparedStatement.close();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}finally {
			connection.close();
		}
		return idNguoiNhan;
	}

	public void setIdNguoiNhan(int idNguoiNhan) {
		this.idNguoiNhan = idNguoiNhan;
	}

	public String getTenNguoiNhan() {
		return tenNguoiNhan;
	}
	
	public String getTenNguoiNhan(int id) throws ClassNotFoundException, SQLException {
		String query = "SELECT hoTen from nhan_khau where ID = "+id;
		Connection connection = MysqlConnection.getMysqlConnection();
		try{
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next())
				this.tenNguoiNhan = rs.getString("hoTen");
			preparedStatement.close();
		}catch (Exception e){
			System.out.println(e.getStackTrace());
		}finally {
			connection.close();
		}
		return tenNguoiNhan;
	}


	public void setTenNguoiNhan(String tenNguoiNhan) {
		this.tenNguoiNhan = tenNguoiNhan;
	}
}