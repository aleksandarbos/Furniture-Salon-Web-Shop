package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Reports implements Serializable{
	ArrayList<Salon> salons = new ArrayList<Salon>();
	ArrayList<Bill> reportsList = new ArrayList<Bill>();
	
	public Reports() {}
	
	public Reports(String filePath) {
		readFile(filePath);

	}
	
	public void readFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Reports reports = (Reports) in.readObject();
			this.reportsList = reports.getReportsList();
			
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void saveFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(this);
			
			fileOut.close();
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Bill> getReportsList() {
		return reportsList;
	}

	public void setReportsList(ArrayList<Bill> reportsList) {
		this.reportsList = reportsList;
	}

	public ArrayList<Salon> getSalons() {
		return salons;
	}

	public void setSalons(ArrayList<Salon> salons) {
		this.salons = salons;
	}
	
	
	
}
