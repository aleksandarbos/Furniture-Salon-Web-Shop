package beans;

import java.io.Serializable;
import java.util.Date;

public class FurnitureItem implements Serializable{

	private String id;
	private String name;
	private String color;
	private String originCountry;
	private String producerName;
	private int price;
	private int amountInStorage;
	private String furnitureCategoryName;
	private FurnitureCategory furnitureCategory;
	private int yearBuilt;
	private String sellingSalonRegNum;
	private String pictureUrl;
	private Date actionDateBegin;
	private Date actionDateEnd;
	private double discountPercent;
	private String description;
	private boolean changedId = false;
	private String vidUrl;
	
	public FurnitureItem() {}

	public FurnitureItem(String id, String name, String color,
			String originCountry, String producerName, int price,
			int amountInStorage, String furnitureCategoryName, int yearBuilt, String sellingSalonRegNum,
			String pictureUrl, Date actionDateBegin, Date actionDateEnd, double discountPercent, String description, String vidUrl, FurnitureCategory furnitureCategory) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.originCountry = originCountry;
		this.producerName = producerName;
		this.price = price;
		this.amountInStorage = amountInStorage;
		this.yearBuilt = yearBuilt;
		this.sellingSalonRegNum = sellingSalonRegNum;
		this.pictureUrl = pictureUrl;
		this.furnitureCategoryName = furnitureCategoryName;
		this.actionDateBegin = actionDateBegin;
		this.actionDateEnd = actionDateEnd;
		this.discountPercent = discountPercent;
		this.description = description;
		this.vidUrl = vidUrl;
		this.furnitureCategory = furnitureCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAmountInStorage() {
		return amountInStorage;
	}

	public void setAmountInStorage(int amountInStorage) {
		this.amountInStorage = amountInStorage;
	}

	public int getYearBuilt() {
		return yearBuilt;
	}

	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	public String getSellingSalonRegNum() {
		return sellingSalonRegNum;
	}

	public void setSellingSalonRegNum(String sellingSalonRegNum) {
		this.sellingSalonRegNum = sellingSalonRegNum;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getFurnitureCategoryName() {
		return furnitureCategoryName;
	}

	public void setFurnitureCategoryName(String furnitureCategoryName) {
		this.furnitureCategoryName = furnitureCategoryName;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChangedId() {
		return changedId;
	}

	public void setChangedId(boolean changedId) {
		this.changedId = changedId;
	}

	public Date getActionDateBegin() {
		return actionDateBegin;
	}

	public void setActionDateBegin(Date actionDateBegin) {
		this.actionDateBegin = actionDateBegin;
	}

	public Date getActionDateEnd() {
		return actionDateEnd;
	}

	public void setActionDateEnd(Date actionDateEnd) {
		this.actionDateEnd = actionDateEnd;
	}

	public String getVidUrl() {
		return vidUrl;
	}

	public void setVidUrl(String vidUrl) {
		this.vidUrl = vidUrl;
	}

	public FurnitureCategory getFurnitureCategory() {
		return furnitureCategory;
	}

	public void setFurnitureCategory(FurnitureCategory furnitureCategory) {
		this.furnitureCategory = furnitureCategory;
	}
	
	
	
}
