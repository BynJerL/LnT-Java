package finalProjectBNCC;

import java.sql.*;

public class Database {
	
	Connection conn;
	Statement stat;
	PreparedStatement prepstat;
	ResultSet rs;
	
	public Database() {
		connect();
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/puddingdb","root","");
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(Menu menu) {
		try {
			prepstat = conn.prepareStatement("INSERT INTO daftarmenu (Kode, Nama, Harga, Stok) VALUES (?, ?, ?, ?)");
			prepstat.setString(1, menu.getKode());
			prepstat.setString(2, menu.getNama());
			prepstat.setInt(3, menu.getHarga());
			prepstat.setInt(4, menu.getStok());
			prepstat.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getData() {
		try {
			rs = stat.executeQuery("SELECT * FROM daftarmenu");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void update(String code, int price, int stock) {
		try {
			prepstat = conn.prepareStatement("UPDATE daftarmenu SET Harga = ?, Stok = ? WHERE Kode = ?");
			prepstat.setInt(1, price);
			prepstat.setInt(2, stock);
			prepstat.setString(3, code);
			prepstat.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String code) {
		try {
			prepstat = conn.prepareStatement("DELETE FROM daftarmenu WHERE Kode = ?");
			prepstat.setString(1, code);
			prepstat.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
