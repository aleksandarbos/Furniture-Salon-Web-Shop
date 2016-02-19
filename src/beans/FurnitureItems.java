package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class FurnitureItems implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<FurnitureItem> furnitureItems = new ArrayList<FurnitureItem>();
	
	public FurnitureItems() {
		
	}
	
	public FurnitureItems(String filePath) {
		
		readFile(filePath);
		
		System.out.println();

	}
	
	
	private void readFile(String filePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			FurnitureItems furnItems = (FurnitureItems) in.readObject();
			this.furnitureItems = furnItems.getFurnitureItems();
			
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

	public Collection<FurnitureItem> values() {
		return furnitureItems;
	}
	public Collection<FurnitureItem> getValues() {
		return furnitureItems;
	}

	public ArrayList<FurnitureItem> getFurnitureItems() {
		return furnitureItems;
	}

	public void setFurnitureItems(ArrayList<FurnitureItem> furnitureItems) {
		this.furnitureItems = furnitureItems;
	}
	
	
}
