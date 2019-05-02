package Dao;
import java.sql.SQLException;
import java.util.List;

import model.RecipeIngredient;



public interface RecipeIngredientDao {
    //��ӷ���
    public void add(RecipeIngredient p)throws SQLException;
    
    //���·���
    public void update(RecipeIngredient p)throws SQLException;
    
    //ɾ������
    public void delete(int id)throws SQLException;
    
    //���ҷ���
    public RecipeIngredient findById(int id)throws SQLException;
    
    //��������
    public List<RecipeIngredient> findAll()throws SQLException;
    
    public List<RecipeIngredient> findbyrecipe(int id)throws SQLException;
} 