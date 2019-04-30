package Dao;

import java.sql.SQLException;
import java.util.List;

import model.Note;



public interface noteDao {
    //��ӷ���
    public void add(Note p)throws SQLException;
    
    //���·���
    public void update(Note p)throws SQLException;
    
    //ɾ������
    public void delete(int id)throws SQLException;
    
    //���ҷ���
    public Note findById(int id)throws SQLException;
    
    //��������
    public List<Note> findAll()throws SQLException;
    
} 