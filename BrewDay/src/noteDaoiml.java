import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * PersonDao�ľ���ʵ����
 * @author lamp
 *
 */
public class noteDaoiml implements noteDao{

    /**
     * ʵ����ӷ���
     */
    @Override
    public void add(Note p) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into note(noteindex,content,createdate)values(?,?,?)";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getNoteIndex());
            ps.setString(2, p.getContent());
            ps.setString(3, p.getCreateDate());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("�������ʧ��");
        }finally{
            DBUtils.close(null, ps, conn);
        }
    }

    /**
     * ���·���
     */
    @Override
    public void update(Note p) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update note set content=? where noteindex=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getContent());     
            ps.setInt(4, p.getNoteIndex());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("��������ʧ��");
        }finally{
            DBUtils.close(null, ps, conn);
        }        
    }

    /**
     * ɾ������
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from note where noteindex=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException(" ɾ������ʧ��");
        }finally{
            DBUtils.close(null, ps, conn);
        }        
    }

    /**
     * ����ID��ѯһ������
     */
    @Override
    public Note findById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Note p = null;
        String sql = "select noteindex,content,createdate from note where noteindex=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                p = new Note();
                p.setNoteIndex(id);
                p.setContent(rs.getString(2));
                p.setCreateDate(rs.getString(3));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("����ID��ѯ����ʧ��");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return p;
    }

    /**
     * ��ѯ��������
     */
    @Override
    public List<Note> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Note p = null;
        List<Note> notes = new ArrayList<Note>();
        String sql = "select noteindex,content,createdate from note";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                p = new Note();
                p = new Note();
                p.setNoteIndex(rs.getInt(1));
                p.setContent(rs.getString(2));
                p.setCreateDate(rs.getString(3));
                notes.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("��ѯ��������ʧ��");
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return notes;
    }

}
