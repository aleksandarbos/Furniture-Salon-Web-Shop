package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class Salons implements Serializable {
	
	private ArrayList<Salon> salons = new ArrayList<Salon>();
	
	public Salons() {}
	
	public Salons(String filePath) {
		
		readFile(filePath);
		
		System.out.println();
		
	}

	private void readFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Salons readedSalons = (Salons) in.readObject();
			this.salons = readedSalons.getSalons();
			
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
	
	Collection<Salon> values() {
		return salons;
	}

	Collection<Salon> getValues() {
		return salons;
	}

	public ArrayList<Salon> getSalons() {
		return salons;
	}

	public void setSalons(ArrayList<Salon> salons) {
		this.salons = salons;
	}


}
