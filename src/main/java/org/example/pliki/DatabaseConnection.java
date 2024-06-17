package org.example.pliki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private static final Map<String,Connection> connections = new HashMap<>();

    public static Connection getCon() {
        return getCon("");
    }

    public static Connection getCon(String path){
        return connections.get(path);
    }

    public void connect(String path){
        connect(path,"");
    }

    public void connect(String path,String connectionName){
        try{
            Connection con = DriverManager.getConnection("jdbc:sqlite:"+path);
            connections.put(connectionName,con);
            System.out.println("Connected|"+connectionName);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void disconnect(){
        disconnect("");
    }
    public static void disconnect(String connectionName){
       try{
           Connection con = connections.get(connectionName);
           con.close();
           connections.remove(connectionName);
           System.out.println("Disconnected|"+connectionName);
       }
       catch(SQLException e){
           throw new RuntimeException(e);
       }
    }
}
