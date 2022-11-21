package ru.qdts.training.streams;

import java.time.LocalDate;

public class SalesRec {
	String region;
	String country;
	String itemType;
	String salesChannel;
	char orderPriority;
	LocalDate orderDate;
	int orderID;
	LocalDate shipDate;
	int unitsSold;
	float unitPrice;
	float unitCost;
	float totalRevenie;
	float totalCost;
	float totalProfit;
	
	public SalesRec(String region, String country, String itemType, String salesChannel, char orderPriority,
			LocalDate orderDate, int orderID, LocalDate shipDate, int unitsSold, float unitPrice, float unitCost,
			float totalRevenie, float totalCost, float totalProfit) {
		super();
		this.region = region;
		this.country = country;
		this.itemType = itemType;
		this.salesChannel = salesChannel;
		this.orderPriority = orderPriority;
		this.orderDate = orderDate;
		this.orderID = orderID;
		this.shipDate = shipDate;
		this.unitsSold = unitsSold;
		this.unitPrice = unitPrice;
		this.unitCost = unitCost;
		this.totalRevenie = totalRevenie;
		this.totalCost = totalCost;
		this.totalProfit = totalProfit;
	}
	
	public int compareToUnitsSold(SalesRec rc) {
		return unitsSold - rc.unitsSold;
	}

	public String getRegion() {
		return region;
	}

	public String getCountry() {
		return country;
	}

	public String getItemType() {
		return itemType;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public char getOrderPriority() {
		return orderPriority;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public int getOrderID() {
		return orderID;
	}

	public LocalDate getShipDate() {
		return shipDate;
	}

	public int getUnitsSold() {
		return unitsSold;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public float getUnitCost() {
		return unitCost;
	}

	public float getTotalRevenie() {
		return totalRevenie;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public float getTotalProfit() {
		return totalProfit;
	}
		
}
