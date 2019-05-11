package model;

import java.sql.SQLException;

import view.View;

public class Recipe extends Model{
	
	private int recipeIndex;
	private String name;
	private double quantity;
	private String unit;
	private double tmpLackAmount;
	
	// Constructor
	public Recipe() {
		super();
		
	}
	
	
	public Recipe(int recipeIndex, String name, double quantity, String unit) {
		super();
		this.recipeIndex = recipeIndex;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}
	
	
	// Getter and Setter
	public int getRecipeIndex() {
		return recipeIndex;
	}

	public void setRecipeIndex(int recipeIndex) {
		this.recipeIndex = recipeIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void notifyView() throws SQLException {
		for (View v: super.views) {
			v.update();
		}
	}


	public void setLackAmount(double tmpLackAmount) {
		this.tmpLackAmount=tmpLackAmount;
		
	} 
	public double getLackAmount() {
		return this.tmpLackAmount;
	} 
}
