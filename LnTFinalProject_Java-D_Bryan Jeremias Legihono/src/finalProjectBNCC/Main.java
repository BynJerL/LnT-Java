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

import com.mysql.cj.conf.PropertyDefinitions.DatabaseTerm;

import finalProjectBNCC.Database.sortingMode;

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
	String[] CB_CONTENT = {"Default",
			"Kode (naik)","Kode (turun)",
			"Nama (naik)","Nama (turun)",
			"Harga (naik)","Harga (turun)",
			"Stok (naik)","Stok (turun)"};
	
	// Random
	Random rand = new Random();
	
	// Database
	Database myDB = new Database();
	sortingMode sortOp;
	
	// Other
	boolean isSorted = false;
	int activeMenu = 0;
	
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
				// Remove Panel
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				// Add Panel
				try {
					insert(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Unused
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Unused
				
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
				try {
					view(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Unused
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Unused
				
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
				try {
					update(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainPanel.repaint();
				mainPanel.revalidate();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Unused
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Unused
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
				try {
					delete(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
		
		// Main menu:
		JPanel openingPanel = new JPanel(null);
		
		JLabel guideTitle = new JLabel("Petunjuk:");
		guideTitle.setBounds(10,10,100,25);
		openingPanel.add(guideTitle);
		
		JLabel guideContent = new JLabel("Untuk dapat menggunakan program ini, silahkan klik menu apapun yang terletak pada bagian menu bar.");
		guideContent.setBounds(10,20,600,50);
		guideContent.setFont(new Font("sans-serif", Font.PLAIN, 11));
		openingPanel.add(guideContent);
		
		mainPanel.add(openingPanel, BorderLayout.CENTER);
		
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	//Integer.MAX_VALUE
	
	public void insert(JPanel mainPanel) throws SQLException {
		listPreparation();
		
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
		
		submitButton = new JButton("Tambah");
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
				
				System.out.println("Input berhasil");
				
				// Message Dialog
				JOptionPane.showMessageDialog(null, "Menu ["+menuBaru.getKode()+"] telah ditambahkan.");
				
				// Reset
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				try {
					insert(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		insertMenu.setPreferredSize(new Dimension(420,insertMenu.getPreferredSize().height));
		insertMenu.setMaximumSize(new Dimension(420,insertMenu.getPreferredSize().height));
		insertMenu.setBorder(BorderFactory.createTitledBorder("*Insert"));
		
		mainPanel.add(insertMenu, BorderLayout.CENTER);
	}
	
	public void view(JPanel mainPanel) throws SQLException {		
		listPreparation();
		
		JPanel viewMenu = new JPanel(null);
		
		viewTable(viewMenu);
		
		JLabel menuCount = new JLabel("Jumlah Menu: "+Integer.toString(listMenu.size()));
		menuCount.setBounds(20,260,250,25);
		menuCount.setFont(new Font("sans-serif", Font.PLAIN, 12));
		viewMenu.add(menuCount);
		
		JLabel sortLabel = new JLabel("Opsi Pengurutan: ");
		sortLabel.setBounds(20,300,120,25);
		viewMenu.add(sortLabel);
		
		JComboBox sortingOptions = new JComboBox(CB_CONTENT);
		sortingOptions.setSelectedIndex(activeMenu);
		sortingOptions.setBounds(140,300,150,25);
		sortingOptions.setFont(new Font("sans-serif", Font.PLAIN, 12));
		viewMenu.add(sortingOptions);
		
		JButton applyButton = new JButton("Pilih");
		applyButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		applyButton.setBackground(Color.white);
		applyButton.setBounds(310,300,100,25);
		viewMenu.add(applyButton);
		
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pickedMode; boolean defaultMode = false;
				pickedMode = (String) sortingOptions.getSelectedItem().toString();
				
				int option = 0;
				while (option < CB_CONTENT.length) {
					if (CB_CONTENT[option].equals(pickedMode)) {
						break;
					}
					else {
						option++;
					}
				}
				
				activeMenu = option;
				
				switch(option) {
				case 0:
					defaultMode = true; break;
				case 1:
					sortOp = Database.sortingMode.CODE_ASC; break;
				case 2:
					sortOp = Database.sortingMode.CODE_DESC; break;
				case 3:
					sortOp = Database.sortingMode.NAME_ASC; break;
				case 4:
					sortOp = Database.sortingMode.NAME_DESC; break;
				case 5:
					sortOp = Database.sortingMode.PRICE_ASC; break;
				case 6:
					sortOp = Database.sortingMode.PRICE_DESC; break;
				case 7:
					sortOp = Database.sortingMode.STOCK_ASC; break;
				case 8:
					sortOp = Database.sortingMode.STOCK_DESC; break;
				}
				
				if (!defaultMode) {
					isSorted = true;
				} else {
					isSorted = false;
				}
				
				// Reset
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				
				try {
					view(mainPanel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		viewMenu.setBorder(BorderFactory.createTitledBorder("*View"));
		
		
		mainPanel.add(viewMenu, BorderLayout.CENTER);
	}
	
	public void listPreparation() throws SQLException {
		// Clear Menu Vector
		listMenu.clear();
		
		ResultSet dataMenu; 
		if (isSorted) {
			dataMenu = myDB.sort(sortOp);
		} else {
			dataMenu = myDB.getData();
		}
		
		while (dataMenu.next()) {
			String indexCode, indexName; int indexPrice, indexStock;
			indexCode = dataMenu.getString("Kode");
			indexName = dataMenu.getString("Nama");
			indexPrice = dataMenu.getInt("Harga");
			indexStock = dataMenu.getInt("Stok");
			
			Menu indexMenu = new Menu(indexCode, indexName, indexPrice, indexStock);
			
			listMenu.add(indexMenu);
		}
	}
	
	public void viewTable(JPanel vp){
		JLabel viewTitle = new JLabel("Daftar Menu:");
		viewTitle.setBounds(20, 20, 100, 25);
		vp.add(viewTitle);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("Kode");
		columnNames.addElement("Nama Menu");
		columnNames.addElement("Harga (Rp)");
		columnNames.addElement("Stok");
		
		Vector<Vector<String>> rowData = new Vector<>();
		
		for(int i = 0; i < listMenu.size(); i++) {
			Vector<String> indexVector = new Vector<String>();
			indexVector.add(listMenu.get(i).getKode());
			indexVector.add(listMenu.get(i).getNama());
			indexVector.add(Integer.toString(listMenu.get(i).getHarga()));
			indexVector.add(Integer.toString(listMenu.get(i).getStok()));
			
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
	
	public void update(JPanel mainPanel) throws SQLException {
		listPreparation();
		
		JPanel updateMenu =  new JPanel(null);
		
		updateMenu.setBorder(BorderFactory.createTitledBorder("*Update"));
		
		viewTable(updateMenu);
		
		JLabel codeLabel = new JLabel("Kode menu yang ingin diperbarui:");
		codeLabel.setBounds(20,260,200,25);
		updateMenu.add(codeLabel);
		
		JTextField codeInput = new JTextField();
		codeInput.setBounds(230,260,100,25);
		updateMenu.add(codeInput);
		
		JLabel notFoundLabel = new JLabel("");
		notFoundLabel.setBounds(350,260,200,25);
		notFoundLabel.setFont(new Font("sans-serif", Font.ITALIC, 11));
		notFoundLabel.setForeground(Color.red);
		updateMenu.add(notFoundLabel);
		
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
		
		JButton updateButton = new JButton("Perbarui");
		updateButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		updateButton.setBackground(Color.white);
		updateButton.setBounds(130,360,100,25);
		updateMenu.add(updateButton);
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Update data
				String refCode; int newPrice, newStock; boolean codeFound = false;
				
				refCode = codeInput.getText();
				
				for(int i = 0; i < listMenu.size(); i++) {
					if(listMenu.get(i).getKode().equals(refCode)) {
						codeFound = true;
						
						if(newPriceField.getText().equals("")) {
							newPrice = listMenu.get(i).getHarga();
						} else {
							newPrice = Integer.parseInt(newPriceField.getText());
						}
						
						if(newStockField.getText().equals("")) {
							newStock = listMenu.get(i).getStok();
						} else {
							newStock = Integer.parseInt(newStockField.getText());
						}
						
						myDB.update(refCode, newPrice, newStock);
						
						// Break if no changes:
						if(newPriceField.getText().equals("") && newStockField.getText().equals("")) {
							break;
						}
						
						// Message Dialog
						JOptionPane.showMessageDialog(null, "Menu ["+refCode+"] telah diperbarui.");
						
						// Reset
						mainPanel.removeAll();
						mainPanel.repaint();
						mainPanel.revalidate();
						
						try {
							update(mainPanel);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						mainPanel.repaint();
						mainPanel.revalidate();
					}
				}
				
				if(!codeFound) {
					notFoundLabel.setText("kode tidak ditemukan");
					
					System.out.println("kode tidak ditemukan.");
				} else {
					// Only executed when no changes on the menu
					notFoundLabel.setText("tidak ada perubahan pada data menu");
				}
			}
		});
		
		mainPanel.add(updateMenu, BorderLayout.CENTER);
	}
	
	public void delete(JPanel mainPanel) throws SQLException {
		listPreparation();
		
		JPanel deleteMenu =  new JPanel(null);
		
		deleteMenu.setBorder(BorderFactory.createTitledBorder("*Delete"));
		
		viewTable(deleteMenu);
		
		JLabel codeLabel = new JLabel("Kode dari menu yang ingin dihapus:");
		codeLabel.setBounds(20,260,200,25);
		deleteMenu.add(codeLabel);
		
		JTextField codeInput = new JTextField();
		codeInput.setBounds(230,260,100,25);
		deleteMenu.add(codeInput);
		
		JLabel notFoundLabel = new JLabel("");
		notFoundLabel.setBounds(350,260,200,25);
		notFoundLabel.setFont(new Font("sans-serif", Font.ITALIC, 11));
		notFoundLabel.setForeground(Color.red);
		deleteMenu.add(notFoundLabel);
		
		JButton deleteButton = new JButton("Hapus");
		deleteButton.setFont(new Font("sans-serif", Font.PLAIN, 12));
		deleteButton.setBackground(Color.white);
		deleteButton.setBounds(130,300,100,25);
		deleteMenu.add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Delete data
				String refCode; boolean codeFound = false;
				
				refCode = codeInput.getText();
				
				for(int i = 0; i < listMenu.size();i++) {
					if(listMenu.get(i).getKode().equals(refCode)) {
						codeFound = true;
						
						myDB.delete(refCode);
						
						// Message Dialog
						JOptionPane.showMessageDialog(null, "Menu ["+refCode+"] telah dihapus.");
						
						// Reset
						mainPanel.removeAll();
						mainPanel.repaint();
						mainPanel.revalidate();
						
						try {
							delete(mainPanel);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						mainPanel.repaint();
						mainPanel.revalidate();
					}
				}
				
				if(!codeFound) {
					notFoundLabel.setText("kode tidak ditemukan");
					
					System.out.println("kode tidak ditemukan.");
				}
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
