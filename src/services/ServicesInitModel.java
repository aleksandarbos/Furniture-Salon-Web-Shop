package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import beans.AdditionalService;
import beans.AdditionalServices;
import beans.FurnitureCategories;
import beans.FurnitureCategory;
import beans.FurnitureItem;
import beans.FurnitureItems;
import beans.Salons;



@Path("/init")
public class ServicesInitModel {
	
	@Context
	ServletContext ctx;

	
	public ServicesInitModel() {
		initSalonsFile();
	}
	
	@GET
	@Path("/model")
	public void initSalonsFile() {
		// TODO Auto-generated method stub
		
		 FurnitureCategories furnitureCategories = new FurnitureCategories();
		 ArrayList<FurnitureCategory> furnitureCategoryList = new ArrayList<FurnitureCategory>();
		 
		 FurnitureCategory krevet = new FurnitureCategory("-Krevet", "Mesto za leganje.", null);
		 FurnitureCategory kuhEl = new FurnitureCategory("-Kuhinjski element", "Vezano za kuhinju.", null);
		 FurnitureCategory kauc = new FurnitureCategory("-Kauč", "Mesto za leganje.", null);
		 FurnitureCategory sofa = new FurnitureCategory("-Sofa", "Za izležavanje.", null);
		 FurnitureCategory polica = new FurnitureCategory("-Polica", "Da se stavi nešto na to.", null);
		 FurnitureCategory sto = new FurnitureCategory("-Sto", "Ručak.", null);
		 FurnitureCategory stolica = new FurnitureCategory("-Stolica", "Da se sedne.", null);
		 FurnitureCategory orman = new FurnitureCategory("-Orman", "Da se sedne.", null);
		 
		 furnitureCategoryList.add(krevet);
		 furnitureCategoryList.add(kuhEl);
		 furnitureCategoryList.add(kauc);
		 furnitureCategoryList.add(sofa);
		 furnitureCategoryList.add(polica);
		 furnitureCategoryList.add(sto);
		 furnitureCategoryList.add(stolica);
		 furnitureCategories.setFurnitureCategories(furnitureCategoryList);
		 
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("furnitureCategories.dat");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(furnitureCategories);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + 
	        		 	Paths.get(".").toAbsolutePath().normalize().toString()
	         );
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
		
		
		File fiFile = new File(Paths.get(".").toAbsolutePath().normalize().toString()+"\\furnitureItems.txt");
		FurnitureItems furnitureItems = new FurnitureItems();
		
		BufferedReader in = null;
		String line, id = "", name = "", color = "", originCountry = "", producerName = "",
				furnitureCategoryName = "", sellingSalonGiroNumber = "", pictureUrl="";
		int price = 0, amountInStorage = 0, yearBuilt = 0;
		StringTokenizer st;
		
		try {
			in = new BufferedReader(new FileReader(fiFile));
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					color = st.nextToken().trim();
					originCountry = st.nextToken().trim();
					producerName = st.nextToken().trim();
					price = Integer.parseInt(st.nextToken().trim());
					amountInStorage = Integer.parseInt(st.nextToken().trim());
					furnitureCategoryName = st.nextToken().trim();
					yearBuilt = Integer.parseInt(st.nextToken().trim());
					sellingSalonGiroNumber = st.nextToken().trim();
					pictureUrl = st.nextToken().trim();
				}
				FurnitureCategory fCat = new FurnitureCategory("nameštaj","Celokupan nameštaj...",new FurnitureCategory("drveni", "Krevet opis...", null));	// inicijalni
				FurnitureItem furnitureItem = new FurnitureItem(id, name, color, originCountry, producerName, price, amountInStorage, furnitureCategoryName, yearBuilt, sellingSalonGiroNumber, pictureUrl, new Date(), null, 0.0,"Inicijalni opis predmeta. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus.","",fCat);
				furnitureItems.getFurnitureItems().add(furnitureItem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// init categories
		furnitureItems.getFurnitureItems().get(0).setFurnitureCategory(sofa);
		furnitureItems.getFurnitureItems().get(1).setFurnitureCategory(orman);
		furnitureItems.getFurnitureItems().get(2).setFurnitureCategory(polica);
		furnitureItems.getFurnitureItems().get(3).setFurnitureCategory(sofa);
		furnitureItems.getFurnitureItems().get(4).setFurnitureCategory(krevet);
		
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("furnitureItems.dat");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(furnitureItems);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + 
	        		 	Paths.get(".").toAbsolutePath().normalize().toString()
	         );
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }

		 System.out.println("saved as: " + Paths.get(".").toAbsolutePath().normalize().toString()+"\\furnitureItems.dat");	
	
		 AdditionalServices additionalServices = new AdditionalServices();
		 ArrayList<AdditionalService> listOfServices = additionalServices.getListOfServices();
		 listOfServices.add(new AdditionalService("-Ugradnja", "Pod ugradnjom se podrazumeva instalacija proizvoda na Vašoj adresi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras lobortis mauris sit amet sapien tempus bibendum. Nulla mattis ornare ligula nec ullamcorper. Proin erat magna, pretium et placerat eu, efficitur dictum nisi. Morbi ornare est id congue pellentesque. Duis eu dolor neque. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus. ", 1000.0));
		 listOfServices.add(new AdditionalService("-Transport", "Pod transportom se podrazumeva prevoz kupljenih proizvoda na Vašu adresu, važi samo za Republiku Srbiju.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras lobortis mauris sit amet sapien tempus bibendum. Nulla mattis ornare ligula nec ullamcorper. Proin erat magna, pretium et placerat eu, efficitur dictum nisi. Morbi ornare est id congue pellentesque. Duis eu dolor neque. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus. ", 1500.0));
		 //listOfServices.add(new AdditionalService("-Plaćanje na rate", "Plaćanje na rate važi samo za određene komade nameštaja. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras lobortis mauris sit amet sapien tempus bibendum. Nulla mattis ornare ligula nec ullamcorper. Proin erat magna, pretium et placerat eu, efficitur dictum nisi. Morbi ornare est id congue pellentesque. Duis eu dolor neque. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus. ", 0.0));
		 //listOfServices.add(new AdditionalService("-Garancija", "Garancija važi samo za određene komade nameštaja. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras lobortis mauris sit amet sapien tempus bibendum. Nulla mattis ornare ligula nec ullamcorper. Proin erat magna, pretium et placerat eu, efficitur dictum nisi. Morbi ornare est id congue pellentesque. Duis eu dolor neque. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus. ",0.0));
		 listOfServices.add(new AdditionalService("-Modifikacije nameštaja", "Modifikacija ili prepravka nameštaja je moguća, i važi samo za prostore Republike Srbije. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras lobortis mauris sit amet sapien tempus bibendum. Nulla mattis ornare ligula nec ullamcorper. Proin erat magna, pretium et placerat eu, efficitur dictum nisi. Morbi ornare est id congue pellentesque. Duis eu dolor neque. Suspendisse et ipsum pulvinar, lacinia neque auctor, pharetra ante. Nunc id tellus purus. ", 5000.0));
	
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("additionalServices.dat");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(additionalServices);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + 
	        		 	Paths.get(".").toAbsolutePath().normalize().toString()
	         );
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }

		 

		 
	}
	
}
