/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Laurent Cardon <laurent.cardon@jsb.be>
 */
public class DBManager {
    
    private String dbServeur = "//localhost:3306";
    private String nomDB = "robomagsine_dev";
    private String username = "root";
    private String password = "";
    
    private static DBManager instance = null;
    
    private DBManager(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.out.print(e.getException().getMessage());
        }
        
    }
    
    public static DBManager getInstance(){
        if(instance==null){
            instance = new DBManager();
        }
        
        return instance;
    }
    
    private Connection initierConnection(){
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql:"+dbServeur+"/"+nomDB+"?zeroDateTimeBehavior=convertToNull",username,password);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
    
    public DefaultTableModel getMachines(){
        String query = "SELECT ouvriersNecessaires, Nom, description FROM machine"; 
                
        Connection conn = this.initierConnection();
        
        DefaultTableModel tableauMachines = new DefaultTableModel();
        
        if(conn != null){
            java.sql.Statement stat;
            try {
                stat = conn.createStatement();
                tableauMachines = this.createTableModel(stat.executeQuery(query));
                          
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return tableauMachines;
    }
    
    private DefaultTableModel createTableModel(ResultSet result) throws SQLException {
        
        ResultSetMetaData md = result.getMetaData();
        
        result.last();
        int nbLigne = result.getRow();
        result.beforeFirst();
        
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnCount(md.getColumnCount());
        dtm.setRowCount(nbLigne);
         
        Object[] headers = new Object[md.getColumnCount()];
                
        for(int i=0;i<md.getColumnCount();i++){
            headers[i] = md.getColumnName(i+1);
        }
       
        while(result.next()){
            for(int i=0;i<md.getColumnCount();i++){
                dtm.setValueAt(result.getString(i+1), result.getRow()-1, i);
            }
            
        }
        
        dtm.setColumnIdentifiers(headers);
       
        return dtm;
        
    }
    
    
    
}
