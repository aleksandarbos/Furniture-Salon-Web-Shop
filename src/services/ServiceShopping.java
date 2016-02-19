package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.AdditionalService;
import beans.AdditionalServices;
import beans.Bill;
import beans.FurnitureCategories;
import beans.FurnitureCategory;
import beans.FurnitureItem;
import beans.FurnitureItems;
import beans.Reports;
import beans.Salons;
import beans.ShoppingCart;
import beans.ShoppingCartItem;

@Path("/shopping")
public class ServiceShopping {

	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest req;
	@Context
	HttpServletResponse resp;
	
	@POST
	@Path("/getSalonItems")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FurnitureItem> getSalonItems(String query) {
		
		String[] queryItems = query.split(",");
		
		String salonRegNum = queryItems[0];
		String querySearch = queryItems[1];
		
		boolean search = false;
		
		String param = "";
		String val = "";
		
		if(!querySearch.equals("normal") && !querySearch.equals("action")) {
			String[] tokens = querySearch.split(";");
			param = tokens[0];
			val = tokens[1].toLowerCase();
			search = true;
		}
		
		ArrayList<FurnitureItem> listOfItems = new ArrayList<FurnitureItem>();
		for(FurnitureItem fi: getFurnitureItemsInstane().getFurnitureItems()) {
			if(querySearch.equals("normal")) {
				if(fi.getSellingSalonRegNum().equals(salonRegNum)) {
					listOfItems.add(fi);
				} 
			} else if(querySearch.equals("action")) {
				if(fi.getSellingSalonRegNum().equals(salonRegNum) && fi.getDiscountPercent() != 0.0) {
					listOfItems.add(fi);
				}
			} else if(search) {
				if(param.equals("name")) {
					if(fi.getName().toLowerCase().contains((val))) {
						listOfItems.add(fi);
					}
				} else if(param.equals("price")) {
					String[] range = val.split("-");
					if(fi.getPrice() >= Double.parseDouble(range[0]) &&
					   fi.getPrice() <= Double.parseDouble(range[1])) {
						listOfItems.add(fi);
					}
				} else if(param.equals("description")) {
					if(fi.getDescription().toLowerCase().contains(val)) {
						listOfItems.add(fi);
					}
				} else if(param.equals("category")) {
					if(fi.getFurnitureCategory().getName().toLowerCase().contains(val)) {	// cat
						listOfItems.add(fi);
					}
				} else if(param.equals("producerCountry")) {
					if(fi.getOriginCountry().toLowerCase().contains(val)) {
						listOfItems.add(fi);
					}
				} else if(param.equals("yearBuilt")) {
					if(fi.getYearBuilt() == Integer.parseInt(val)) {
						listOfItems.add(fi);
					}
				} else if(param.equals("color")) {
					if(fi.getColor().toLowerCase().contains(val)) {
						listOfItems.add(fi);
					}
				}else if(param.equals("producerName")) {
					if(fi.getProducerName().toLowerCase().contains(val)) {
						listOfItems.add(fi);
					}
				}
			}
		}
		return listOfItems;
	}

	@POST
	@Path("/addShoppingItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int addShoppingItem(FurnitureItem furnitureItem) {
		
		
		FurnitureItems furnitureItems = getFurnitureItemsInstane();
		ArrayList<FurnitureItem> furnitureItemsList = furnitureItems.getFurnitureItems();
		
		ArrayList<FurnitureCategory> furnitureCategories = getFurnitureCategoriesInstance().getFurnitureCategories();

		for(FurnitureCategory fc: furnitureCategories) {					// podesavanje kategorije
			if(furnitureItem.getFurnitureCategoryName().equals(fc.getName())) {
				furnitureItem.setFurnitureCategory(fc);
				break;
			}
		}

		
		for(FurnitureItem fi: furnitureItemsList) {
			if(furnitureItem.getId().equals(fi.getId()))  {
				return 1;		// id vec postoji
			}
		}
		
		furnitureItemsList.add(furnitureItem);
		furnitureItems.saveFile(ctx.getRealPath("")+"\\data\\furnitureItems.dat");
		
		return 0;	// sve ok, dodat je
	}

	@POST
	@Path("/deleteShoppingItem")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int addShoppingItem(String furnitureItemId) {
		
		FurnitureItems furnitureItems = getFurnitureItemsInstane();
		ArrayList<FurnitureItem> furnitureItemsList = furnitureItems.getFurnitureItems();
		
		for(int i = furnitureItemsList.size()-1; i > -1; i--){
			if(furnitureItemId.equals(furnitureItemsList.get(i).getId()))  {
				furnitureItemsList.remove(furnitureItemsList.get(i));		// id vec postoji
			}
		}	
		furnitureItems.saveFile(ctx.getRealPath("")+"\\data\\furnitureItems.dat");
		
		return 0;	// sve ok, dodat je
	}

	@POST
	@Path("/editShoppingItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int editShoppingItem(FurnitureItem furnitureItem) {
				
		FurnitureItems furnitureItems = getFurnitureItemsInstane();
		ArrayList<FurnitureItem> furnitureItemsList = furnitureItems.getFurnitureItems();
	
		ArrayList<FurnitureCategory> furnitureCategories = getFurnitureCategoriesInstance().getFurnitureCategories();
		
		for(FurnitureCategory fc: furnitureCategories) {					// podesavanje kategorije
			if(furnitureItem.getFurnitureCategoryName().equals(fc.getName())) {
				furnitureItem.setFurnitureCategory(fc);
				break;
			}
		}
		
		for(int i = furnitureItemsList.size()-1; i > -1; i--){	
			if(furnitureItem.getId().equals(furnitureItemsList.get(i).getId()))  {
				furnitureItemsList.set(i, furnitureItem);		// zameni novim namestajem
			}
		}
		
		furnitureItems.saveFile(ctx.getRealPath("")+"\\data\\furnitureItems.dat");
		
		return 0;	// sve ok, dodat je
	}

	@POST
	@Path("/addToShoppingCart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart addToShoppingCart(ShoppingCartItem shoppingCartItem) {
		
		ShoppingCart shoppingCart;
		Bill bill;
		
		if(req.getSession().getAttribute("shoppingCart") != null) {
			shoppingCart = (ShoppingCart) req.getSession().getAttribute("shoppingCart");
		} else {
			shoppingCart = new ShoppingCart();
		}
		
		if(req.getSession().getAttribute("bill") != null){
			bill = (Bill) req.getSession().getAttribute("bill");
		} else {
			bill = new Bill();
		}
		
		if(shoppingCartItem.getAdditionalServiceId() == null) {
			
			FurnitureItems furnitureItems = getFurnitureItemsInstane();
			FurnitureItem furnitureItem = new FurnitureItem();
			ArrayList<FurnitureItem> furnitureItemsList = furnitureItems.getFurnitureItems();
			
			
			for(int i = furnitureItemsList.size()-1; i > -1; i--){	
				if(shoppingCartItem.getFurnitureId().equals(furnitureItemsList.get(i).getId()))  {
					furnitureItem = furnitureItemsList.get(i);		// nasao sam item po id-u
					furnitureItem.setAmountInStorage(furnitureItem.getAmountInStorage()-shoppingCartItem.getFurnitureItemsAmount());
					break;
				}
			}
			shoppingCartItem.setFurnitureItem(furnitureItem);			// postavi ga u shoppingCartItem
			
			shoppingCart.setTotalItems(shoppingCart.getTotalItems()+		// ukupno itema
						shoppingCartItem.getFurnitureItemsAmount());
			
			if(furnitureItem.getDiscountPercent() == 0.0) {
				shoppingCart.setTotalPrice(shoppingCart.getTotalPrice()+		// totalna cena
							furnitureItem.getPrice()*
							shoppingCartItem.getFurnitureItemsAmount());
			} else {
				shoppingCart.setTotalPrice(shoppingCart.getTotalPrice()+		// totalna cena
						(furnitureItem.getPrice()-furnitureItem.getPrice()*
						(furnitureItem.getDiscountPercent()/100))*
						shoppingCartItem.getFurnitureItemsAmount());
			}
			bill.setShoppingCart(shoppingCart);
		} else {
			AdditionalServices additionalServices = getAdditionalServicesInstance();
			AdditionalService additionalService = new AdditionalService();
			ArrayList<AdditionalService> additionalServicesList = additionalServices.getListOfServices();
			
			for(int i = additionalServicesList.size()-1; i > -1; i--){	
				if(shoppingCartItem.getAdditionalServiceId().equals(additionalServicesList.get(i).getName()))  {
					additionalService = additionalServicesList.get(i);		// nasao sam item po id-u
					break;
				}
			}
			
			shoppingCartItem.setAdditionalService(additionalService);			// postavi ga u shoppingCartItem

			shoppingCart.setTotalItems(shoppingCart.getTotalItems()+1);			// dodao jos jednu porudzbinu
			shoppingCart.setTotalPrice(shoppingCart.getTotalPrice()+additionalService.getPrice()); // ukupna cena
			bill.setShoppingCart(shoppingCart);
		}
		
		shoppingCart.getShoppingCartItems().add(shoppingCartItem);	// dodaj kao jednu od stavki
		
		req.getSession().setAttribute("shoppingCart", shoppingCart);
		req.getSession().setAttribute("bill", bill);
				
		return shoppingCart;	// sve ok, dodat je,vrati celo stanje
	}

	
	@POST
	@Path("/setFurnitureItemAction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int setFurnitureItemAction(FurnitureItem furnitureItem) {
		
		FurnitureItems furnitureItems = getFurnitureItemsInstane();
		ArrayList<FurnitureItem> furnitureItemsList = furnitureItems.getFurnitureItems();
		
		int i;
		for(i = furnitureItemsList.size()-1; i > -1; i--){	
			if(furnitureItem.getId().equals(furnitureItemsList.get(i).getId()))  {
				break;
			}
		}
		
		furnitureItemsList.get(i).setActionDateBegin(furnitureItem.getActionDateBegin());	// ispodesavaj
		furnitureItemsList.get(i).setActionDateEnd(furnitureItem.getActionDateEnd());
		furnitureItemsList.get(i).setDiscountPercent(furnitureItem.getDiscountPercent());
		
		furnitureItems.saveFile(ctx.getRealPath("")+"\\data\\furnitureItems.dat");
		
		return 0;	// sve ok, dodat je
	}
	
	
	public FurnitureItems getFurnitureItemsInstane() {
		FurnitureItems furnitureItems = new FurnitureItems(ctx.getRealPath("")+"\\data\\furnitureItems.dat");
		return furnitureItems;
	}
	
	public AdditionalServices getAdditionalServicesInstance() {
		AdditionalServices additionalServices = new AdditionalServices(ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return additionalServices;
	}
	
	public FurnitureCategories getFurnitureCategoriesInstance() {
		FurnitureCategories furnitureCategories = new FurnitureCategories(ctx.getRealPath("")+"\\data\\furnitureCategories.dat");
		return furnitureCategories;
	}

	public Reports getReportsInstance() {
		Reports reports = new Reports(ctx.getRealPath("")+"\\data\\reports.dat");
		return reports;
	}

	@POST
	@Path("/uploadPicture")
	@Produces(MediaType.TEXT_PLAIN)
	public int uploadPicture() {
				
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// preko ove veli�ine, fajlovi se snimaju direktno na disk
			factory.setSizeThreshold(2000000);
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ukupna veli�ina celog zahteva
			upload.setSizeMax(3000000);
			// Parse the request
			List<FileItem> items = upload.parseRequest(req);
						// Process the uploaded items
			for (FileItem item : items) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString(req
							.getCharacterEncoding());
				} else {
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					boolean isInMemory = item.isInMemory();
					long sizeInBytes = item.getSize();
					
					int idx = fileName.lastIndexOf("\\"); // ako browser
					// daje puno ime fajla (sa putanjom)
					if (idx != -1)
						fileName = fileName.substring(idx + 1);
					if (sizeInBytes != 0) {
						File uploadedFile = new File(ctx.getRealPath("") + "\\images\\furnitureItems\\" + fileName);
						System.out.println(ctx.getRealPath("") + "/images/furnitureItems/" + fileName);
						System.out.println("{" + uploadedFile.getCanonicalPath() + "}");
						item.write(uploadedFile);
					}
				}
			}						
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return 0;	// sve ok, dodat je
	}
	
	@POST
	@Path("/getAdditionalServices")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AdditionalService> getAdditionalServices(String query) {
		ArrayList<AdditionalService> resultList = new ArrayList<AdditionalService>();
		if(query.equals("normal")) {
			return getAdditionalServicesInstance().getListOfServices();
		} else {
			String[] tokens = query.split(";");
			String param = tokens[0];
			String val = tokens[1];
			
			for(AdditionalService as: getAdditionalServicesInstance().getListOfServices()) {
				if(param.equals("name")) {
					if(as.getName().contains(val)) {
						resultList.add(as);
					}
				} else if(param.equals("description")) {
					if(as.getDescription().contains(val)) {
						resultList.add(as);
					}
				}
			}
		}
		return resultList;
	}
	
	@POST
	@Path("/editAdditionalService")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int editShoppingItem(AdditionalService additionalService) {
				
		AdditionalServices additionalServices = getAdditionalServicesInstance();
		ArrayList<AdditionalService> additionalServicesList = additionalServices.getListOfServices();
	
		for(int i = additionalServicesList.size()-1; i > -1; i--){	
			if(additionalService.getName().equals(additionalServicesList.get(i).getName()))  {
				additionalServicesList.set(i, additionalService);		// zameni novim namestajem
			}
		}
		
		additionalServices.saveFile(ctx.getRealPath("")+"\\data\\additionalServices.dat");
		System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
	}

	@POST
	@Path("/deleteAdditionalService")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int deleteAdditionalService(String additionalServiceName) {
		
		AdditionalServices additionalServices = getAdditionalServicesInstance();
		ArrayList<AdditionalService> additionalServicesList = additionalServices.getListOfServices();
	
		for(int i = additionalServicesList.size()-1; i > -1; i--){	
			if(additionalServiceName.equals(additionalServicesList.get(i).getName()))  {
				additionalServicesList.remove(additionalServicesList.get(i));		// zameni novim namestajem
			}
		}
		
		additionalServices.saveFile(ctx.getRealPath("")+"\\data\\additionalServices.dat");
		System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
		
	}

	@POST
	@Path("/addAdditionalService")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int addAdditionalService(AdditionalService additionalService) {
		
		AdditionalServices additionalServices = getAdditionalServicesInstance();
		ArrayList<AdditionalService> additionalServicesList = additionalServices.getListOfServices();
	
		for(int i = additionalServicesList.size()-1; i > -1; i--){	
			if(additionalService.getName().equals(additionalServicesList.get(i).getName()))  {
				return 1;		// zameni novim namestajem
			}
		}
		
		additionalServicesList.add(additionalService);
		
		additionalServices.saveFile(ctx.getRealPath("")+"\\data\\additionalServices.dat");
		
		System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
		
	}
	
	@POST
	@Path("/getCategories")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FurnitureCategory> getCategories(String query) {
		
		
		Reports reports = getReportsInstance();
		req.getSession().setAttribute("reports", reports);		// init reports...

		req.getSession().setAttribute("categories", getFurnitureCategoriesInstance());

		ArrayList<FurnitureCategory> resultList = new ArrayList<FurnitureCategory>();
		if(query.equals("normal")) {
			return getFurnitureCategoriesInstance().getFurnitureCategories();
		} else {
			for(FurnitureCategory fc: getFurnitureCategoriesInstance().getFurnitureCategories()) {
				if(fc.getName().equals(query)) {
					resultList.add(fc);
				}
			}
		}
		return resultList;
	}
	
	@POST
	@Path("/deleteCategory")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int deleteCategory(String categoryName) {
		
		FurnitureCategories furnitureCategories = getFurnitureCategoriesInstance();
		ArrayList<FurnitureCategory> categoryList = furnitureCategories.getFurnitureCategories();
	
		for(int i = categoryList.size()-1; i > -1; i--){	
			if(categoryName.equals(categoryList.get(i).getName()))  {
				categoryList.remove(categoryList.get(i));		// zameni novim namestajem
			}
		}
		
		furnitureCategories.saveFile(ctx.getRealPath("")+"\\data\\furnitureCategories.dat");
		//System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
		
	}
	
	@POST
	@Path("/editCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int editCategory(FurnitureCategory furnitureCategory) {
		
		FurnitureCategories furnitureCategories = getFurnitureCategoriesInstance();
		ArrayList<FurnitureCategory> categoryList = furnitureCategories.getFurnitureCategories();
	
		for(int i = categoryList.size()-1; i > -1; i--){	
			if(furnitureCategory.getName().equals(categoryList.get(i).getName()))  {
				categoryList.set(i,furnitureCategory);		// zameni novim namestajem
			}
		}
		
		furnitureCategories.saveFile(ctx.getRealPath("")+"\\data\\furnitureCategories.dat");
		//System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
		
	}
	
	@POST
	@Path("/addNewCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int addNewCategory(FurnitureCategory furnitureCategory) {
		
		FurnitureCategories furnitureCategories = getFurnitureCategoriesInstance();
		ArrayList<FurnitureCategory> categoryList = furnitureCategories.getFurnitureCategories();
	
		categoryList.add(furnitureCategory);		// zameni novim namestajem
		
		
		furnitureCategories.saveFile(ctx.getRealPath("")+"\\data\\furnitureCategories.dat");
		//System.out.println("addServ sacuvan u : " + ctx.getRealPath("")+"\\data\\additionalServices.dat");
		return 0;	// sve ok, dodat je
		
	}

	@GET
	@Path("/resetShoppingCart")
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int resetShoppingCart() {
		
		Reports reports = getReportsInstance();
		
		reports.getReportsList().add((Bill) req.getSession().getAttribute("bill"));
		reports.saveFile(ctx.getRealPath("")+"\\data\\reports.dat");
		System.out.println("reports u : " + ctx.getRealPath("")+"\\data\\reports.dat");
		
		Salons salons = new Salons(ctx.getRealPath("")+"\\data\\salons.dat");
		reports.setSalons(salons.getSalons());
		
		req.getSession().setAttribute("shoppingCart", new ShoppingCart());
		req.getSession().setAttribute("bill", new Bill());
		req.getSession().setAttribute("reports", reports);

		return 0;	// sve ok, dodat je
		
	}
	
	
	@GET
	@Path("/initReports")
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized int initReports() {
		
		Reports reports = getReportsInstance();
		Salons salons = new Salons(ctx.getRealPath("")+"\\data\\salons.dat");
		reports.setSalons(salons.getSalons());
		
		req.getSession().setAttribute("reports", reports);

		return 0;	// sve ok, dodat je
		
	}
	
	
	
}
