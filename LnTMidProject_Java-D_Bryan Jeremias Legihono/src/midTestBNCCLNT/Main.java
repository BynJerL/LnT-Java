package midTestBNCCLNT;

import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class Karyawan {
	// Data Karyawan
	private String kode;
	private String nama;
	private String jenisKelamin;
	private String jabatan;
	private int gaji;
	
	// Constructor
	public Karyawan(String kode, String nama, String jenisKelamin, String jabatan, int gaji) {
		this.kode = kode;
		this.nama = nama;
		this.jenisKelamin = jenisKelamin;
		this.jabatan = jabatan;
		this.gaji = gaji;
	}
	
	// Getter and Setter
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getJenisKelamin() {
		return jenisKelamin;
	}
	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}
	public String getJabatan() {
		return jabatan;
	}
	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}
	public int getGaji() {
		return gaji;
	}
	public void setGaji(int gaji) {
		this.gaji = gaji;
	}
	
	
}

public class Main {
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	Formatter fmt = new Formatter();
	Vector<Karyawan> listKaryawan = new Vector<>();
	
	String[] listJenisKelamin = new String[]{"Laki-laki","Perempuan"};
	String[] listJabatan = new String[]{"Manager","Supervisor","Admin"};
	
	String ALPHABETH_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String NUMBER_CHARS = "0123456789";
	
	public Main() {
		int menu = 0;
		
		do {
			System.out.println("DATA PT Meksiko");
			System.out.println("List Menu:");
			System.out.println("(1) INSERT data karyawan");
			System.out.println("(2) VIEW data karyawan");
			System.out.println("(3) UPDATE data karyawan");
			System.out.println("(4) DELETE data karyawan");
			System.out.println("(5) KELUAR");
			
			boolean isValid;
			do {
				try {
					System.out.print("Input: ");
					menu = scan.nextInt();
					isValid = true;
				} catch (Exception e) {
					System.out.println("Input harus angka bulat!");
					isValid = false;
				} finally {
					scan.nextLine();
				}
			} while (!isValid);
			
			
			// Nanti println-nya bakal dihapus
			switch(menu) {
			case 1:
				Insert();
				System.out.println("ENTER to return"); scan.nextLine();
				break;
			case 2:
				View();
				System.out.println("ENTER to return"); scan.nextLine();
				break;
			case 3:
				Update();
				System.out.println("ENTER to return"); scan.nextLine();
				break;
			case 4:
				Delete();
				System.out.println("ENTER to return"); scan.nextLine();
				break;
			case 5:
				System.out.println("ENTER to exit"); scan.nextLine();
				break;
			default:
				System.out.println("Menu tidak tersedia");
				System.out.println("ENTER to return"); scan.nextLine();
				break;
			}
			
		} while(menu != 5);
	}
	
	// Executor
	public static void main(String[] args) {
		new Main();
	}
	
	// Menu
	public void Insert() {
		String kode, nama, jenisKelamin, jabatan;
		int gaji, jumlahAdminTanpaBonus = 0, jumlahSupervisorTanpaBonus = 0, jumlahManagerTanpaBonus = 0;
		int jumlahAdminDenganBonus = 0, jumlahSupervisorDenganBonus = 0, jumlahManagerDenganBonus = 0;
		
		// Scan Nama
		do {
			System.out.print("Input nama karyawan [>= 3]: ");
			nama = scan.nextLine();
		} while ((nama.length() < 3) || !(nama.matches("^(?:\\s*\\p{L}){3}[\\p{L}\\s]*$")));
		
		boolean isValid = false;
		// Scan Jenis Kelamin
		do {
			System.out.print("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			jenisKelamin = scan.nextLine();
			
			for(String x: listJenisKelamin) {
				if(x.equals(jenisKelamin)) {
					isValid = true;
					break;
				} else {
					isValid = false;
				}
			}
		} while(!isValid);
		
		// Scan Jabatan
		do {
			System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			jabatan = scan.nextLine();
			
			for(String x: listJabatan) {
				if(x.equals(jabatan)) {
					isValid = true;
					break;
				} else {
					isValid = false;
				}
			}
		} while (!isValid);
		
		// Generate kode
		kode = "";
		do {
			kode = codeShuffle();
			if(listKaryawan.size() != 0) {
				for(int i = 0; i < listKaryawan.size(); i++) {
					if (!listKaryawan.get(i).getKode().equals(kode)) {
						isValid = true;
					} else {
						isValid = false;
					}
				}
			}
		} while (!isValid);
		
		// Generate gaji
		if (jabatan.equals("Manager")) {
			gaji = 8000000;
		} else if (jabatan.equals("Supervisor")) {
			gaji = 6000000;
		} else if (jabatan.equals("Admin")) {
			gaji = 4000000;
		} else {
			gaji = 0;
		}
		
		// Input data
		Karyawan karyawanBaru = new Karyawan(kode, nama, jenisKelamin, jabatan, gaji);
		listKaryawan.add(karyawanBaru);
		
		// Konfirmasi Keberhasilan
		System.out.println("Berhasil menambahkan karyawan dengan id "+kode);
		
		// Cek gaji
		for(int i = 0; i < listKaryawan.size(); i++) {
			if (listKaryawan.get(i).getGaji() == 4000000) {
				jumlahAdminTanpaBonus++;
			} else if (listKaryawan.get(i).getGaji() == 6000000) {
				jumlahSupervisorTanpaBonus++;
			} else if (listKaryawan.get(i).getGaji() == 8000000) {
				jumlahManagerTanpaBonus++;
			}
		}
		
		// Penambahan bonus
		if(jumlahAdminTanpaBonus == 3) {
			for(int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).getGaji() == 4000000) {
					listKaryawan.get(i).setGaji((int)(1.05*4000000)); // Bonus 5%
				}
			}
		}
		if(jumlahSupervisorTanpaBonus == 3) {
			for(int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).getGaji() == 6000000) {
					listKaryawan.get(i).setGaji((int)(1.075*6000000)); // Bonus 7.5%
				}
			}
		}
		if(jumlahManagerTanpaBonus == 3) {
			for(int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).getGaji() == 8000000) {
					listKaryawan.get(i).setGaji((int)(1.1*8000000)); // Bonus 10%
				}
			}
		}
		
		// Cek jumlah karyawan yang mendapatkan bonus gaji
		for(int i = 0; i < listKaryawan.size(); i++) {
			if (listKaryawan.get(i).getGaji() == (int)(1.05*4000000)) {
				jumlahAdminDenganBonus++;
			} else if (listKaryawan.get(i).getGaji() == (int)(1.075*6000000)) {
				jumlahSupervisorDenganBonus++;
			} else if (listKaryawan.get(i).getGaji() == (int)(1.1*8000000)) {
				jumlahManagerDenganBonus++;
			}
		}
		
		// Pengumuman Penambahan Bonus
		if(jumlahAdminDenganBonus != 0) {
			int yangSudahDisebut = 0;
			System.out.print("Bonus sebesar 5% telah diberikan kepada karyawan dengan id ");
			for(int i = 0; i < listKaryawan.size(); i++) {
				if (listKaryawan.get(i).getGaji() == (int)(1.05*4000000)) {
					System.out.print(listKaryawan.get(i).getKode());
					yangSudahDisebut++;
					if (yangSudahDisebut < jumlahAdminDenganBonus) {
						System.out.print(", ");
					} else {
						System.out.print("\n");
					}
				}
			}
		}
		if(jumlahSupervisorDenganBonus != 0) {
			int yangSudahDisebut = 0;
			System.out.print("Bonus sebesar 7.5% telah diberikan kepada karyawan dengan id ");
			for(int i = 0; i < listKaryawan.size(); i++) {
				if (listKaryawan.get(i).getGaji() == (int)(1.075*6000000)) {
					System.out.print(listKaryawan.get(i).getKode());
					yangSudahDisebut++;
					if (yangSudahDisebut < jumlahSupervisorDenganBonus) {
						System.out.print(", ");
					} else {
						System.out.print("\n");
					}
				}
			}
		}
		if(jumlahManagerDenganBonus != 0) {
			int yangSudahDisebut = 0;
			System.out.print("Bonus sebesar 10% telah diberikan kepada karyawan dengan id ");
			for(int i = 0; i < listKaryawan.size(); i++) {
				if (listKaryawan.get(i).getGaji() == (int)(1.1*8000000)) {
					System.out.print(listKaryawan.get(i).getKode());
					yangSudahDisebut++;
					if (yangSudahDisebut < jumlahManagerDenganBonus) {
						System.out.print(", ");
					} else {
						System.out.print("\n");
					}
				}
			}
		}
	}
	
	public void View() {
		listKaryawan.sort((o1,o2) -> o1.getNama().compareTo(o2.getNama()));
		
		System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
		System.out.println("|No  |Kode Karyawan    |Nama Keryawan                 |Jenis Kelamin  |Jabatan       |Gaji Karyawan|");
		System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
		if(listKaryawan.size() == 0) {
			System.out.printf("|%4s|%17s|%30s|%15s|%14s|%13s|\n","N/A","N/A","N/A","N/A","N/A","N/A");
		} else {
			for(int i = 0; i < listKaryawan.size(); i++) {
				System.out.printf("|%4s|%17s|%30s|%15s|%14s|%13s|\n", i+1,
						listKaryawan.get(i).getKode(),
						listKaryawan.get(i).getNama(),
						listKaryawan.get(i).getJenisKelamin(),
						listKaryawan.get(i).getJabatan(),
						listKaryawan.get(i).getGaji());
			}
		}		
		System.out.println("|----|-----------------|------------------------------|---------------|--------------|-------------|");
	}
	public void Update() {
		int indeks = 0, gaji;
		String nama, jenisKelamin, jabatan;
		
		View();
		if(listKaryawan.size() != 0) {
			boolean isValid = false;
			do {
				try {
					System.out.print("Input nomor urutan karyawan yang ingin diupdate: ");
					indeks = scan.nextInt();
					isValid = true;
				} catch (Exception e) {
					isValid = false;
				} finally {
					scan.nextLine();
				}
			} while (indeks > listKaryawan.size() || indeks < 0 || !isValid);
			
			if (indeks != 0) {
				indeks--;
				
				// Scan Nama
				do {
					System.out.print("Input nama karyawan [>= 3]: ");
					nama = scan.nextLine();
				} while ((nama.length() < 3) || !(nama.matches("^(?:\\s*\\p{L}){3}[\\p{L}\\s]*$")));
				
				// Scan Jenis Kelamin
				do {
					System.out.print("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
					jenisKelamin = scan.nextLine();
					
					for(String x: listJenisKelamin) {
						if(x.equals(jenisKelamin)) {
							isValid = true;
							break;
						} else {
							isValid = false;
						}
					}
				} while(!isValid);
				
				// Scan Jabatan
				do {
					System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
					jabatan = scan.nextLine();
					
					for(String x: listJabatan) {
						if(x.equals(jabatan)) {
							isValid = true;
							break;
						} else {
							isValid = false;
						}
					}
				} while (!isValid);
				
				// Update gaji
				if (jabatan.equals("Manager")) {
					gaji = 8000000;
				} else if (jabatan.equals("Supervisor")) {
					gaji = 6000000;
				} else if (jabatan.equals("Admin")) {
					gaji = 4000000;
				} else {
					gaji = 0;
				}
				
				listKaryawan.get(indeks).setNama(nama);
				listKaryawan.get(indeks).setJenisKelamin(jenisKelamin);
				listKaryawan.get(indeks).setJabatan(jabatan);
				listKaryawan.get(indeks).setGaji(gaji);
				
				// Tanda berhasil
				System.out.println("Berhasil mengupdate karyawan dengan id "+listKaryawan.get(indeks).getKode());
			}
		} else {}
	}
	public void Delete() {
		int indeks = 0;
		String kode;
		
		View();
		if(listKaryawan.size() != 0) {
			boolean isValid = false;
			do {
				try {
					System.out.print("Input nomor urutan karyawan yang ingin dihapus: ");
					indeks = scan.nextInt();
					isValid = true;
				} catch (Exception e) {
					isValid = false;
				} finally {
					scan.nextLine();
				}
			} while (indeks > listKaryawan.size() || indeks < 0 || !isValid);
			
//			do {
//				System.out.print("Input nomor urutan karyawan yang ingin dihapus: ");
//				indeks = scan.nextInt(); scan.nextLine();
//			} while (indeks > listKaryawan.size() || indeks <= 0);
			
			if(indeks != 0) {
				indeks--;
				kode = listKaryawan.get(indeks).getKode();
				
				// Menghapus data
				listKaryawan.remove(indeks);
				
				// Tanda berhasil
				System.out.println("Berhasil menghapus karyawan dengan id "+kode);
			}			
		} else {}
	}
	public String codeShuffle() {
		String kode = "";
		for(int i = 0; i < 7; i++) {
			if(i < 2) {
				kode += ALPHABETH_CHARS.charAt(rand.nextInt(ALPHABETH_CHARS.length())); 
			} else if (i > 2) {
				kode += NUMBER_CHARS.charAt(rand.nextInt(NUMBER_CHARS.length()));
			} else {
				kode += "-";
			}
		}
		
		return kode;
	}
}
