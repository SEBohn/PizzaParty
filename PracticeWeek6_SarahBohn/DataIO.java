//package info for class
package data;

//import data for class with SQL connectivity
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import business.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DataIO {
   private final String CONNECTION_STRING = "jdbc:mysql://devry.edupe.net:8300/PizzaDB_3503"; //db connector
    private final String ORDER_TABLE = "PizzaOrder"; //table name
    private final String USER_NAME = "3503";  //user name
    private final String PASSWORD = "J3ck24681012";  //password
    
    private final DBConnect dbConnect;
    
    public DataIO()  {
        dbConnect = new DBConnect(CONNECTION_STRING, USER_NAME, PASSWORD);
    }
    public boolean add(PizzaOrder aOrder) {
        boolean success = false;
        PreparedStatement statement;
	StringBuilder sqlStr = new StringBuilder();
	int rowCount;
        
        if (aOrder != null && dbConnect != null && dbConnect.isConnected()) {
            try {
                sqlStr.append("INSERT INTO ");
                sqlStr.append(ORDER_TABLE);
                sqlStr.append(" (firstName, lastName, size, cheese, sausage, ham, total)");
                sqlStr.append(" VALUES (?,?,?,?,?,?,?)");
                
                statement = dbConnect.getConnection().prepareStatement(sqlStr.toString(), Statement.RETURN_GENERATED_KEYS);
               
               
               //code to add order to the database. week 6 
               statement.executeUpdate("INSERT INTO PizzaOrder" + "(firstName, lastName, size, cheese, sausage, ham, total)" );

                rowCount = statement.executeUpdate();
                if (rowCount == 1) {
                    ResultSet rs = statement.getGeneratedKeys();
                    if(rs.next()) {
                        aOrder.setId(rs.getInt(1));
                    }
                    success = true;
                }
            }
            catch  (SQLException e) {
                String prompt = e.getMessage() 
                            + " cannot save pizza order information for " 
                            + aOrder.getFullName();
                JOptionPane.showMessageDialog(null, prompt, "SQL Server Error: Insert", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (aOrder == null) {
            throw new NullPointerException("Pizza Order object is null");
        }
        else {
            throw new IllegalStateException("Database is not connected");
            
        }
        return success;
    }
    public boolean delete(PizzaOrder aOrder) throws ClassNotFoundException {
        boolean success = false;
        PreparedStatement statement = null;
	StringBuilder sqlStr = new StringBuilder();
	int rowCount;
        
        if(aOrder != null && dbConnect != null && dbConnect.isConnected()){
            try {
                //create the SQL statement to delete a record from the database with a given id added week 6
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
                PreparedStatement st = connection.prepareStatement("DELETE FROM Table WHERE name = ?");
                st.setString(1,"id");
                st.executeUpdate(); 
                
                statement = dbConnect.getConnection().prepareStatement(sqlStr.toString());
                statement.setInt(1, aOrder.getId());
                rowCount = statement.executeUpdate();
                if (rowCount == 1) {
                    success = true;
                }
            }
            catch  (SQLException e) {
                String prompt = e.getMessage() 
                                + " cannot delete pizza order information for " 
                                + aOrder.getFullName();
                JOptionPane.showMessageDialog(null, prompt, "SQL Server Error: Delete", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (aOrder == null) {
            throw new NullPointerException("Pizza Order object is null");
        }
        else {
            throw new IllegalStateException("Database is not connected");
            
        }
        return success;
    }
    public boolean update(PizzaOrder aOrder)  {
        boolean success = false;
        PreparedStatement statement = null;
	StringBuilder sqlStr = new StringBuilder();
	int rowCount;
        
        
        if(aOrder != null && dbConnect != null && dbConnect.isConnected()) {
            try {
                //SQL and prepared statements to update an order in the database week 6
                Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
                PreparedStatement st = connection.prepareStatement("UPDATE PizzaOrder SET id = ?, firstName = ?, lastName = ? WHERE type = ? AND total = ?");

                rowCount = statement.executeUpdate();
            }
            catch  (SQLException e) {
                String prompt = e.getMessage() 
                                + " cannot update pizza order information for " 
                                + aOrder.getFullName();
                JOptionPane.showMessageDialog(null, prompt, "SQL Server Error: Update", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (aOrder == null) {
            throw new NullPointerException("Pizza Order object is null");
        }
        else {
            throw new IllegalStateException("Database is not connected");
            
        }
        return success;
    }
    public ArrayList<PizzaOrder> getList() {
        ArrayList<PizzaOrder> list = new ArrayList<>();
        String sqlStr = "Select * from " + ORDER_TABLE;
        Statement statement;
        ResultSet rs;
        int id;
        PizzaOrder aOrder;
        
        try {
            statement = (Statement) dbConnect.getConnection().createStatement();
            rs = statement.executeQuery(sqlStr);
            while (rs.next()) {
                aOrder = new PizzaOrder();
                aOrder.setId(rs.getInt(1));
                aOrder.setFirstName(rs.getString(2));
                aOrder.setLastName(rs.getString(3));
                //TODO create the code to:
                //1. set the pizza size
                aOrder.setPizzaSize(rs.getString(4));
                //2. set the cheese selection
                aOrder.setCheese(rs.getBoolean(5));
                //3. set the sausage selection
                aOrder.setSausage(rs.getBoolean(6));
                //4. set the ham selection
                aOrder.setHam(rs.getBoolean(7));
                //5. set the total
                aOrder.setTotal(rs.getDouble(8));
                //6. add the order to the array list
                
            }
        }
        catch (SQLException ex) {
             String prompt = ex.getMessage() 
                                + " cannot retrieve the list from the server.";
            JOptionPane.showMessageDialog(null, prompt, "SQL Server Error: getList", JOptionPane.ERROR_MESSAGE);
	}
        return list;
    }
}
