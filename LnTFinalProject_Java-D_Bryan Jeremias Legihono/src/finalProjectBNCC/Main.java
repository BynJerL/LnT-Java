package finalProjectBNCC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Main extends JFrame{
	
	// Colors
	Color northColor = new Color(9,125,36);
	Color menuListColor = new Color(134,255,130);
	
	// Menus
	JMenu insert, view, update, delete;
	
	// Button
	JButton submitButton;
	
	// TextField
	JTextField nameField, priceField, stockField;
	
	// Vector
	Vector<Menu> listMenu = new Vector<>();
	
	// Chars
	String ALPHABETH_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String NUMBER_CHARS = "0123456789";
	
	// Random
	Random rand = new Random();
	
	// Database
	Database myDB = new Database();
	
	public Main() {
		window();
	}

	public static void main(String[] args) {
		new Main();
	}
	
	// Window
	public void window() {
		this.setTitle("Database PT Pudding");
		this.setSize(new Dimension(600,600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setLocation(0,0);
		
		
		components();
		// Windows color (Header & content background)
		// Windows icon customization
		// Windows layout customization
		// Anything for making a good windows
		// Customization of Cursor Arrow
		// Custom text color
		
		this.setVisible(true);
	}
	
	// All components
	public void components() {		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.white);
		menuBar.setOpaque(true);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		insert = new JMenu("Insert");
		view = new JMenu("View");
		update = new JMenu("Update");
		delete = new JMenu("Delete");
		
		insert.setFont(new Font("sans-serif", Font.PLAIN, 12));
		view.setFont(new Font("sans-serif", Font.PLAIN, 12));
		update.setFont(new Font("sans-serif", Font.PLAIN, 12));
		delete.setFont(new Font("sans-serif", Font.PLAIN, 12));
		
		menuBar.add(insert);
		insert.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				// Preparation
				view(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Remove Panel
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Add Panel
				insert(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menuBar.add(view);
		view.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				// Remove Panel
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Add Panel
				view(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menuBar.add(update);
		update.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				// Remove Panel
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Add Panel
				update(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menuBar.add(delete);
		delete.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				// Remove Panel
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Add Panel
				delete(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// unused :)
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// unused :)
			}
		});
		
		this.setJMenuBar(menuBar);
		
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	//Integer.MAX_VALUE
	
	public void insert(JPanel mainPanel) {
		JPanel insertMenu = new JPanel(null);
		
		JLabel nameLabel= new JLabel("Nama Menu:", SwingConstants.LEFT);
		nameLabel.setBounds(20, 20, 100, 25);
		insertMenu.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(120,20,250,25);
		insertMenu.add(nameField);
		
		JLabel priceLabel = new JLabel("Harga:", SwingConstants.LEFT);
		priceLabel.setBounds(20, 50, 100, 25);
		insertMenu.add(priceLabel);
		
		priceField = new JTextField();
		priceField.setBounds(120, 50, 250, 25);
		insertMenu.add(priceField);
		
		JLabel stockLabel = new JLabel("Stok:", SwingConstants.LEFT);
		stockLabel.setBounds(20, 80, 100, 25);
		insertMenu.add(stockLabel);
		
		stockField = new JTextField();
		stockField.setBounds(120, 80, 250, 25);
		insertMenu.add(stockField);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		submitButton.setBackground(Color.white);
		submitButton.setBounds(120, 120, 100, 25);
		insertMenu.add(submitButton);
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Input data
				String kodeMenu, namaMenu; int hargaMenu, stokMenu; boolean isValid = true;
				namaMenu = nameField.getText();
				hargaMenu = Integer.parseInt(priceField.getText());
				stokMenu = Integer.parseInt(stockField.getText());
				
				kodeMenu = "";
				do {
					kodeMenu = codeShuffle();
					if(listMenu.size() != 0) {
						for(int i = 0; i < listMenu.size(); i++) {
							if (!listMenu.get(i).getKode().equals(kodeMenu)) {
								isValid = true;
							} else {
								isValid = false;
							}
						}
					}
				} while (!isValid);
				
				
				Menu menuBaru = new Menu(kodeMenu, namaMenu, hargaMenu, stokMenu);
				myDB.insert(menuBaru);
//				listMenu.add(menuBaru);
				
				System.out.println("Input berhasil");
				
				// Message Dialog
				JOptionPane.showMessageDialog(null, "Menu ["+menuBaru.getKode()+"] telah ditambahkan.");
				
				// Reset
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				insert(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		insertMenu.setPreferredSize(new Dimension(420,insertMenu.getPreferredSize().height));
		insertMenu.setMaximumSize(new Dimension(420,insertMenu.getPreferredSize().height));
		insertMenu.setBorder(BorderFactory.createTitledBorder("*Insert"));
		
		mainPanel.add(insertMenu, BorderLayout.CENTER);
	}
	
	public void view(JPanel mainPanel) {		
		JPanel viewMenu = new JPanel(null);
		
		try {
			viewTable(viewMenu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JLabel menuCount = new JLabel("Jumlah Menu: "+Integer.toString(listMenu.size()));
		menuCount.setBounds(20,260,250,25);
		menuCount.setFont(new Font("sans-serif", Font.PLAIN, 12));
		viewMenu.add(menuCount);
		
		viewMenu.setBorder(BorderFactory.createTitledBorder("*View"));
		
		
		mainPanel.add(viewMenu, BorderLayout.CENTER);
	}
	
	public void viewTable(JPanel vp) throws SQLException {
		JLabel viewTitle = new JLabel("Daftar Menu:");
		viewTitle.setBounds(20, 20, 100, 25);
		vp.add(viewTitle);
		
		ResultSet dataMenu = myDB.getData();
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("Kode");
		columnNames.addElement("Nama Menu");
		columnNames.addElement("Harga (Rp)");
		columnNames.addElement("Stok");
		
		Vector<Vector<String>> rowData = new Vector<>();
		
		// Clear Menu Vector
		listMenu.clear();
		
		while (dataMenu.next()) {
			Vector<String> indexVector = new Vector<String>();
			String indexCode, indexName; int indexPrice, indexStock;
			indexCode = dataMenu.getString("Kode");
			indexName = dataMenu.getString("Nama");
			indexPrice = dataMenu.getInt("Harga");
			indexStock = dataMenu.getInt("Stok");
			
			Menu indexMenu = new Menu(indexCode, indexName, indexPrice, indexStock);
			
			indexVector.add(indexCode);
			indexVector.add(indexName);
			indexVector.add(Integer.toString(indexPrice));
			indexVector.add(Integer.toString(indexStock));
			
			listMenu.add(indexMenu);
			
			rowData.add(indexVector);
		}
		
		JTable tabelPersediaan = new JTable(rowData, columnNames);
		tabelPersediaan.setRowHeight(30);
		tabelPersediaan.getColumnModel().getColumn(0).setPreferredWidth(70);
		tabelPersediaan.getColumnModel().getColumn(1).setPreferredWidth(250);
		tabelPersediaan.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabelPersediaan.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		JScrollPane scroll = new JScrollPane(tabelPersediaan);
		scroll.setBounds(20,50,540,200);
		vp.add(scroll);
	}
	
	public void update(JPanel mainPanel) {
		JPanel updateMenu =  new JPanel(null);
		
		updateMenu.setBorder(BorderFactory.createTitledBorder("*Update"));
		
		try {
			viewTable(updateMenu);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JLabel codeLabel = new JLabel("Kode Menu yang Ingin Di-update:");
		codeLabel.setBounds(20,260,200,25);
		updateMenu.add(codeLabel);
		
		JTextField codeInput = new JTextField();
		codeInput.setBounds(230,260,100,25);
		updateMenu.add(codeInput);
		
		JLabel newPriceLabel = new JLabel("Harga baru:");
		newPriceLabel.setBounds(20,290,100,25);
		updateMenu.add(newPriceLabel);
		
		JTextField newPriceField = new JTextField();
		newPriceField.setBounds(130,290,200,25);
		updateMenu.add(newPriceField);
		
		JLabel newStockLabel = new JLabel("Stok baru:");
		newStockLabel.setBounds(20,320,100,25);
		updateMenu.add(newStockLabel);
		
		JTextField newStockField = new JTextField();
		newStockField.setBounds(130,320,200,25);
		updateMenu.add(newStockField);
		
		JButton updateButton = new JButton("Update");
		updateButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		updateButton.setBackground(Color.white);
		updateButton.setBounds(130,360,100,25);
		updateMenu.add(updateButton);
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Update data
				String refCode; int newPrice, newStock;
				
				refCode = codeInput.getText();
				
				newPrice = Integer.parseInt(newPriceField.getText());
				newStock = Integer.parseInt(newStockField.getText());
				
				for(int i = 0; i < listMenu.size(); i++) {
					if(listMenu.get(i).getKode().equals(refCode)) {
						myDB.update(refCode, newPrice, newStock);
						
						// Message Dialog
						JOptionPane.showMessageDialog(null, "Menu ["+refCode+"] telah di-update.");
					}
				}
				
				// Reset
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				update(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		mainPanel.add(updateMenu, BorderLayout.CENTER);
	}
	
	public void delete(JPanel mainPanel) {
		JPanel deleteMenu =  new JPanel(null);
		
		deleteMenu.setBorder(BorderFactory.createTitledBorder("*Delete"));
		
		try {
			viewTable(deleteMenu);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JLabel codeLabel = new JLabel("Kode dari menu yang ingin dihapus:");
		codeLabel.setBounds(20,260,200,25);
		deleteMenu.add(codeLabel);
		
		JTextField codeInput = new JTextField();
		codeInput.setBounds(230,260,100,25);
		deleteMenu.add(codeInput);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		deleteButton.setBackground(Color.white);
		deleteButton.setBounds(130,300,100,25);
		deleteMenu.add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Delete data
				String refCode;
				
				refCode = codeInput.getText();
				
				for(int i = 0; i < listMenu.size();i++) {
					if(listMenu.get(i).getKode().equals(refCode)) {
						myDB.delete(refCode);
						
						// Message Dialog
						JOptionPane.showMessageDialog(null, "Menu ["+refCode+"] telah dihapus.");
					}
				}
				
				// Reset
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				delete(mainPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		mainPanel.add(deleteMenu, BorderLayout.CENTER);
	}

	public String codeShuffle() {
		String kode = "PD-";
		for(int i = 0; i < 3; i++) {
			kode += NUMBER_CHARS.charAt(rand.nextInt(NUMBER_CHARS.length()));
		}
		
		return kode;
	}
}
