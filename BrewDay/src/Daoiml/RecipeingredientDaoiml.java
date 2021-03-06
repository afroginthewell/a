package Daoiml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DBUtils;
import Dao.RecipeIngredientDao;
import model.RecipeIngredient;
import model.StorageIngredient;



public class RecipeingredientDaoiml implements RecipeIngredientDao{


    @Override
    public void add(RecipeIngredient p) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into recipeingredient(recipeingredient_index,amount,unit,recipeindex,name)values(?,?,?,?,?)";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getindex());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getUnit());
            ps.setInt(4, p.getrecipeIndex());
            //recipe ingredient need recipe index
            ps.setString(5, p.getName());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("add fail");
        }finally{
            DBUtils.close(null, ps, conn);
        }
    }

 
    @Override
    public void update(RecipeIngredient p) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update recipeingredient set amount=? where recipeingredient_index=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, p.getAmount());     
            ps.setInt(2, p.getindex());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("update fail");
        }finally{
            DBUtils.close(null, ps, conn);
        }        
    }

 
    @Override
    public void delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from recipeingredient where recipeingredient_index=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("");
        }finally{
            DBUtils.close(null, ps, conn);
        }        
    }

//find all recipe ingredient for that recipe
    @Override
    public RecipeIngredient findById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RecipeIngredient p = null;
        String sql = "select recipeingredient_index,amount,unit,recipeindex,name from recipeingredient where recipeingredient_index=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                p = new RecipeIngredient(id,rs.getString(5),rs.getDouble(2),rs.getString(3),rs.getInt(4));             
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("find fail");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return p;
    }

 
    @Override
    public List<RecipeIngredient> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RecipeIngredient p = null;
        List<RecipeIngredient> RecipeIngredients = new ArrayList<RecipeIngredient>();
        String sql = "select recipeingredient_index,amount,unit,recipeindex,name from recipeingredient";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            	p = new RecipeIngredient(rs.getInt(1),rs.getString(5),rs.getDouble(2),rs.getString(3),rs.getInt(4));                    
            	RecipeIngredients.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("find all fail");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return RecipeIngredients;
    }
    
    public List<RecipeIngredient> findbyrecipe(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RecipeIngredient p = null;
        List<RecipeIngredient> RecipeIngredients = new ArrayList<RecipeIngredient>();
        String sql = "select recipeingredient_index,amount,unit,recipeindex,name from recipeingredient where recipeindex=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
            	p = new RecipeIngredient(rs.getInt(1),rs.getString(5),rs.getDouble(2),rs.getString(3),rs.getInt(4));                    
            	RecipeIngredients.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("find all fail");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return RecipeIngredients;
    }
    
    @Override
    public int getMaxIndex() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RecipeIngredient p = null;
        List<RecipeIngredient> RecipeIngredients = new ArrayList<RecipeIngredient>();
        String sql = "select recipeingredient_index,amount,unit,recipeindex,name from recipeingredient";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            	p = new RecipeIngredient(rs.getInt(1),rs.getString(5),rs.getDouble(2),rs.getString(3),rs.getInt(4));                    
            	RecipeIngredients.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("find all fail");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        int max=0;
        int current;
        for(RecipeIngredient s:RecipeIngredients)
        {
        	current=s.getindex();
        	if(current>max)
        	{
        		max=current;
        	}
        	
        }
        return max;
    }
    
}
