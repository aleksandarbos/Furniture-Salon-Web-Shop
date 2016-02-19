package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AdditionalServices implements Serializable {

	private ArrayList<AdditionalService> listOfServices = new ArrayList<AdditionalService>();
	
	public AdditionalServices() {}
	
	public AdditionalServices(String filePath) {
		readFile(filePath);
	}
	
	private void readFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			AdditionalServices additionalServices = (AdditionalServices) in.readObject();
			this.listOfServices = additionalServices.getListOfServices();
			
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


	public ArrayList<AdditionalService> getListOfServices() {
		return listOfServices;
	}

	public void setListOfServices(ArrayList<AdditionalService> listOfServices) {
		this.listOfServices = listOfServices;
	}
	
	
	
}
