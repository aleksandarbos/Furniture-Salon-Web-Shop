package beans;

import java.io.Serializable;

import services.ServiceShopping;

public class ShoppingCartItem implements Serializable{

	private FurnitureItem furnitureItem;
	private String furnitureId;
	private int furnitureItemsAmount;
	private AdditionalService additionalService;
	private String additionalServiceId;
	private String salonName;
	
	//private .... ostale stvari koje se mogu kupiti
	

	public String getFurnitureId() {
		return furnitureId;
	}

	public void setFurnitureId(String furnitureId) {
		this.furnitureId = furnitureId;
	}

	public int getFurnitureItemsAmount() {
		return furnitureItemsAmount;
	}

	public void setFurnitureItemsAmount(int furnitureItemsAmount) {
		this.furnitureItemsAmount = furnitureItemsAmount;
	}

	public FurnitureItem getFurnitureItem() {
		return furnitureItem;
	}

	public void setFurnitureItem(FurnitureItem furnitureItem) {
		this.furnitureItem = furnitureItem;
	}

	public AdditionalService getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(AdditionalService additionalService) {
		this.additionalService = additionalService;
	}

	public String getAdditionalServiceId() {
		return additionalServiceId;
	}

	public void setAdditionalServiceId(String additionalServiceId) {
		this.additionalServiceId = additionalServiceId;
	}
	
	
	
}
