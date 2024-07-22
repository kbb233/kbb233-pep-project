package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    //TODO
    /*
      1.NEW ACC (INSERT USERNAME AND PASSWORD)
      2.USER LOGIN (CHECK USERNAME AND PASSWORD)

     */


     public Account register(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username,password) VALUES(?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, acc.getUsername(),acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserName(String un) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, un);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return acc;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
