package org.example.pliki;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountManager {
    static DatabaseConnection con;
    public AccountManager(DatabaseConnection con){
        this.con = con;
    }

    public static class Persisence{
        public int register(String username,String password) throws SQLException {

            PreparedStatement statement = con.getCon().prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1,username);
            statement.execute();

            if (statement.getResultSet().next()){
                throw new RuntimeException("User " + username + " already exists");
            }

            statement = con.getCon()
                    .prepareStatement("INSTER INTO users(name,password) values(?,?)");
            statement.setString(1,username);
            String hashPasswd = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            statement.setString(2,hashPasswd);
            statement.executeUpdate();
            statement = con.getCon().prepareStatement("SELECT id FROM users WHERE name LIKE ?");
            statement.setString(1,username);
            int id = statement.getResultSet().getInt(1);
            return id;
        }

        public boolean authenticate(String username, String password) throws SQLException {
            PreparedStatement statement = con.getCon()
                    .prepareStatement("SELECT password FROM users WHERE username = ?");
            statement.setString(1,username);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()){
                String hashPasswd = resultSet.getString("password");
                BCrypt.Result hashResult = BCrypt.verifyer().verify(password.toCharArray(),hashPasswd);
                return hashResult.verified;
            }
            else {
                throw new RuntimeException("User " +username+ " doesn't exist");
            }
        }

        public Account getAccount(String username) throws SQLException {
            PreparedStatement statement = con.getCon()
                    .prepareStatement("SELECT id FROM users WHERE username = ?");
            statement.setString(1,username);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                return new Account(id,username);
            }
            else {
                throw new RuntimeException("User "+username+" doesn't exist");
            }
        }
    }
}
