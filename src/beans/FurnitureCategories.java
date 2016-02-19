package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FurnitureCategories implements Serializable {
	
	ArrayList<FurnitureCategory> furnitureCategories = new ArrayList<FurnitureCategory>();
	
	public FurnitureCategories() {}

	public FurnitureCategories(String filePath) {
		readFile(filePath);

	}
	
	public void readFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			FurnitureCategories furnCategories = (FurnitureCategories) in.readObject();
			this.furnitureCategories = furnCategories.getFurnitureCategories();
			
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


	public ArrayList<FurnitureCategory> getFurnitureCategories() {
		return furnitureCategories;
	}

	public void setFurnitureCategories(
			ArrayList<FurnitureCategory> furnitureCategories) {
		this.furnitureCategories = furnitureCategories;
	}
	
	
	
}
