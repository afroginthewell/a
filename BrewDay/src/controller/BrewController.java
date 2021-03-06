package controller;

import model.Brew;
import model.Equipment;
import model.Recipe;
import model.RecipeIngredient;
import model.StorageIngredient;

import Dao.storageingredientDao;
import Dao.recipeDao;
import Dao.RecipeIngredientDao;
import Dao.equipDao;
import Dao.noteDao;
import Daoiml.equipDaoiml;
import Daoiml.historyDaoiml;
import Daoiml.noteDaoiml;
import Daoiml.recipeDaoiml;
import Daoiml.RecipeingredientDaoiml;
import Daoiml.storageingredientDaoiml;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class BrewController extends Controller {
	private Brew model;

	public BrewController(Brew model) {
		super(model);
		this.model = model;
	}

	// FUNCTIONS
	public boolean implement(int recipeindex, double batchSize) throws SQLException {

		// Create arraylist to store the data fetch from database
		ArrayList<StorageIngredient> SIList = new ArrayList<StorageIngredient>();

		recipeDao rdi = new recipeDaoiml();
		Recipe recipe = rdi.findById(recipeindex);
		// Implement the DAO object
		storageingredientDao sIDao = new storageingredientDaoiml();
		RecipeIngredientDao rIngDao = new RecipeingredientDaoiml();
		equipDao e = new equipDaoiml();

		// Fetch data from database
		SIList = (ArrayList<StorageIngredient>) sIDao.findAll();

		// Error handle: If batch size is smaller than 0, reject
		if (batchSize <= 0)
			return false;

		// Error handle: If batch size is larger than avaliable capacity, reject
		if (e.getTotalCapacity() < batchSize) {
			return false;
		}

		// subtract the amount
		double amount = batchSize / recipe.getQuantity();
		ArrayList<RecipeIngredient> corrRIList = new ArrayList<RecipeIngredient>();
		corrRIList = (ArrayList<RecipeIngredient>) rIngDao.findbyrecipe(recipe.getRecipeIndex());
		for (RecipeIngredient ri : corrRIList) {
			double needAmount = ri.getAmount() * amount;
			for (StorageIngredient si : SIList) {
				// Fine the matched ingredient
				if (ri.getName().equals(si.getName())) {
					// Check if the Stored amount is larger than need amount
					si.setAmount(si.getAmount() - needAmount);
					sIDao.update(si);
				}
			}
		}

		// Write the note

		return true;
	}

	public boolean computeamount(int recipeindex, double batchSize) throws SQLException{

		storageingredientDao sIDao = new storageingredientDaoiml();
		RecipeIngredientDao rIngDao = new RecipeingredientDaoiml();
		recipeDao rdi = new recipeDaoiml();
		ArrayList<StorageIngredient> SIList = new ArrayList<StorageIngredient>();
		ArrayList<Double> shopingAmount = new ArrayList<Double>();
		Recipe recipe = rdi.findById(recipeindex);
		equipDao e = new equipDaoiml();

		// Fetch data from database
		SIList = (ArrayList<StorageIngredient>) sIDao.findAll();

		// Error handle: If batch size is smaller than 0, reject
		if (batchSize <= 0)
			return false;

		// Error handle: If batch size is larger than avaliable capacity, reject
		if (e.getTotalCapacity() < batchSize) {
			return false;
		}

		// subtract the amount
		double amount=0;
		
		amount = batchSize / recipe.getQuantity();
		
		ArrayList<RecipeIngredient> corrRIList = new ArrayList<RecipeIngredient>();
		corrRIList = (ArrayList<RecipeIngredient>) rIngDao.findbyrecipe(recipe.getRecipeIndex());
		for (RecipeIngredient ri : corrRIList) {
			double needAmount = ri.getAmount() * amount;
			for (StorageIngredient si : SIList) {
				// Fine the matched ingredient
				if (ri.getName().equals(si.getName())) {
					// Check if the Stored amount is larger than need amount
					shopingAmount.add(needAmount - si.getAmount());
				}
			}
		}
		model.setShopingAmount(shopingAmount);
		// Write the note

		return true;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Recipe> recommendRecipe(double batchSize) throws SQLException {

		// Create arraylist to store the data fetch from database
		ArrayList<Recipe> sortednRecipeList = new ArrayList<Recipe>();
		ArrayList<Recipe> rRecipeList = new ArrayList<Recipe>();
		ArrayList<Recipe> nRecipeList = new ArrayList<Recipe>();
		ArrayList<Recipe> allRecipeList = new ArrayList<Recipe>();
		ArrayList<StorageIngredient> SIList = new ArrayList<StorageIngredient>();
		ArrayList<RecipeIngredient> RIList = new ArrayList<RecipeIngredient>();

		// Implement the DAO object
		recipeDao RecipeDAO = new recipeDaoiml();
		storageingredientDao sIDao = new storageingredientDaoiml();
		RecipeIngredientDao rIngDao = new RecipeingredientDaoiml();

		// Fetch data from database
		allRecipeList = (ArrayList<Recipe>) RecipeDAO.findAll();
		RIList = (ArrayList<RecipeIngredient>) rIngDao.findAll();
		SIList = (ArrayList<StorageIngredient>) sIDao.findAll();

		double coverRatio;
		// Subtract out the fake avaliable recipes
		for (Recipe i : allRecipeList)
		{
			double totalamount=0;
			coverRatio= 1;
			int flag = 0;
			// Fine the corresponding recipe ingredient for this recipe
			ArrayList<RecipeIngredient> corrRIList = new ArrayList<RecipeIngredient>();
			corrRIList = (ArrayList<RecipeIngredient>) rIngDao.findbyrecipe(i.getRecipeIndex());
			double amount = batchSize / i.getQuantity();
			for (RecipeIngredient ri : corrRIList)
			{
				
				
				// Get this Recipe ingredient's need amount
				double needAmount = ri.getAmount() * amount;
				totalamount+=needAmount;
				// compare with this ingredient in store
				// Fetch the information of store Ingredient firstly
				int siID = 0;
				for (StorageIngredient si : SIList)
				{
					// Fine the matched ingredient
					if (ri.getName().equals(si.getName()))
					{
						// Check if the Stored amount is larger than need amount
						if (si.getAmount() < needAmount) 
						{
							flag = 1;
							// i.setLackAmount(tmpLackAmount);
							if (coverRatio >= si.getAmount() / needAmount) 
							{
								coverRatio = si.getAmount() / needAmount;
							}
						}
					}
				}
				
			}
			
			// Get the actual amount of beer can be brewed
			i.setLackAmount(coverRatio * batchSize);
			i.settotalingredient(totalamount);
			if (flag == 0) {
				rRecipeList.add(i);
			}
			if (flag == 1) {
				nRecipeList.add(i);
			}
			
		}
		
		//for result recipe, sort by total ingredient used
		//for non-execcutable recipe, sort by the max batch size
		Collections.sort(nRecipeList,new Comparators());
		Collections.sort(rRecipeList,new compareamount());

		model.setRecommendedRecipeIndex(rRecipeList);
		model.setnotRecommendedRecipeIndex(nRecipeList);
		return rRecipeList;
	}

	public double getCapacity(Equipment equipment) {
		return equipment.getCapacity();
	}
	
	// add brew history into database
	public boolean addHistory() throws SQLException {
	
		historyDaoiml h=new historyDaoiml();
		
		noteDao e=new noteDaoiml();
		model.setnoteindex(e.getMaxIndex()+1);
		h.add(model);
		return true;
	}
	
	//this is for search function
	
	public void SearchByDate(String searchdate) throws SQLException {
		ArrayList<Brew> searchList = new ArrayList<Brew>();
		
		historyDaoiml h=new historyDaoiml();
		searchList=(ArrayList<Brew>) h.find(searchdate);
		
		model.sethistory(searchList);
		
		for(Brew i :searchList)
		{
			System.out.println(i.getDate());
		}
		
	}
	
		   


}
		  
	
		


