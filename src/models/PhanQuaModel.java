package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.MysqlConnection;

public class PhanQuaModel {
    private int ID;
    private String tenQua;
    private int idDsQua;
    private int idNguoiNhan;
    private String tenNguoiNhan;
    private int giaTri;
    
    public PhanQuaModel() {
    	this.giaTri= 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenQua() {
		return tenQua;
	}

	public void setTenQua(String tenQua) {
		this.tenQua = tenQua;
	}

    public int getIdDsQua() {
        return idDsQua;
    }

    public void setIdDsQua(int idDsQua) {
        this.idDsQua = idDsQua;
    }

    public int getIdNguoiNhan() {
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
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		this.tenNguoiNhan = rs.getString(1);
		preparedStatement.close();
		connection.close();
		return tenNguoiNhan;
	}

	public void setTenNguoiNhan(String tenNguoiNhan) {
		this.tenNguoiNhan = tenNguoiNhan;
	}

	public int getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(int giaTri) {
		this.giaTri = giaTri;
	}
}
