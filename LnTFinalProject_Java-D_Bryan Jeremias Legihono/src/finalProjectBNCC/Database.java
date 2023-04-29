package finalProjectBNCC;

import java.sql.*;

import javax.swing.JOptionPane;

public class Database {
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/puddingdb";
	static final String USER = "root";
	static final String PASS = "";
	
	Connection conn;
	Statement stat;
	PreparedStatement prepstat;
	ResultSet rs;
	
	public enum sortingMode {
		CODE_ASC,
		CODE_DESC,
		NAME_ASC,
		NAME_DESC,
		PRICE_ASC,
		PRICE_DESC,
		STOCK_ASC,
		STOCK_DESC
	}
	
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
			conn = DriverManager.getConnection(DB_URL,USER, PASS);
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Gagal terhubung ke database!");
			System.exit(0);
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
	
	public void insert(String code, String name, int price, int stock) {
		try {
			prepstat = conn.prepareStatement("INSERT INTO daftarmenu (Kode, Nama, Harga, Stok) VALUES (?, ?, ?, ?)");
			prepstat.setString(1, code);
			prepstat.setString(2, name);
			prepstat.setInt(3, price);
			prepstat.setInt(4, stock);
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
	
	public ResultSet sort(sortingMode sm) {
		switch (sm) {
		case CODE_ASC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Kode ASC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case CODE_DESC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Kode DESC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case NAME_ASC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Nama ASC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case NAME_DESC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Nama DESC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case PRICE_ASC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Harga ASC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case PRICE_DESC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Harga DESC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case STOCK_ASC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Stok ASC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		case STOCK_DESC:
			try {
				rs = stat.executeQuery("SELECT * FROM daftarmenu ORDER BY Stok DESC");
			} catch (SQLException e) {
				e.printStackTrace();
			} break;
		}
		
		return rs;
	}
}
