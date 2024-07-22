package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    //TODO
    /*
      1.NEW MESSAGE
      2.RETRIEVE ALL MESSAGE
      3.RETRIEVE MESSAGE BY MESSAGE ID
      4.DELETE MESSAGE BY MESSAGE ID
      5.UPDATE A MESSAGE BY MESSAGE ID
      6.RETRIEVE ALL MESSAGE BY USER ID

     */

     public Message createMessage(Message msg){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by,message_text,time_posted_epoch) VALUES(?,?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, msg.getPosted_by());
            preparedStatement.setString(2, msg.getMessage_text());
            preparedStatement.setLong(3, msg.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_author_id, msg.getPosted_by(),msg.getMessage_text(),msg.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                msgs.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgs;
        
    }

    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    public Boolean deleteMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            String sql2 = "SELECT * FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, id);
            ResultSet rs2 = preparedStatement2.executeQuery();

            while(rs2.next()){
                return false;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
        
    }

    public Message updateMessageByID(int mid ,String mtext){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET  message_text = ?  WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mtext);
            preparedStatement.setInt(2, mid);
            preparedStatement.executeUpdate();

            String selectSql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, mid);
            ResultSet rs = selectStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }
    
    public List<Message> getAllMessageByUserId(int uid){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> msgs = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                msgs.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgs;
        
    }

    public boolean findUserID(int posted_by) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, posted_by);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
