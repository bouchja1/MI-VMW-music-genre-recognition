/*
 * Author - Jan Dufek, dufeja@gmail.com
 * Copying and using only with permission of the author.
 */
package cz.cvut.fit.vmw.genre.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan Dufek
 */
public class Db {
    
    private Connection connection;
    private static Db db;
    
    private PreparedStatement insertSongPS;
    private PreparedStatement selectSongPS;

    private Db() {
        connect();
    }
    
    public static Db getInstance(){
        if (db == null){
            db = new Db();
        }
        return db;
    }
    
    private void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/vmw?"
                                    + "user=vmw&password=vmw");            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }         
    }
    
    public PreparedStatement prepareStatement(String s) throws SQLException{
        return connection.prepareStatement(s);
    }
    public PreparedStatement prepareStatement(String s, int flag) throws SQLException{
        return connection.prepareStatement(s, flag);
    }
    
    private void makePreparedStatements(){
        try {            
            insertSongPS = connection.prepareStatement("INSERT INTO data (song_id, feature_id, vector_id) VALUES (?, ?, ?, ?)");
            selectSongPS = connection.prepareStatement("INSERT INTO data (song_id, feature_id, vector_id) VALUES (?, ?, ?, ?)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
