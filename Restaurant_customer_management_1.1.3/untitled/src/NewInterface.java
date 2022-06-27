import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NewInterface extends  JFrame {

    private JPanel background;
    private JButton button1;
    private JButton button2;
    private JPanel mainContent;
    private JPanel updated;
    private JTextField textField1;
    private JPanel customersPanel;
    private JTable table1;
    private JPanel blankPanel;
    private JButton saveButton;

    private Connection con = null;
    private  String URL = "jdbc:mysql://127.0.0.1:3306/resturant";
    private  String driver = "com.mysql.cj.jdbc.Driver";
    private  String user = "root";
    private  String pass = "asdfghjkl";

    public  NewInterface(String title){
        super(title);
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(background);
        blankPanel.setVisible(true);
        updated.setVisible(false);
        this.pack();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    importTo();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                customersPanel.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updated.setVisible(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // a MySQL statement
                PreparedStatement stmt;
                // a MySQL query
                String query;
                // the results from a MySQL query
                ResultSet rs;

                // run the desired query
                query = "INSERT INTO customers (phoneN) VALUES (?)";
                // make a statement with the server
                try {
                    Class.forName(driver).newInstance();
                    con = DriverManager.getConnection(URL, user, pass);
                    stmt =con.prepareStatement(query);
                    stmt.setString(1, textField1.getText());
                    stmt.execute();

                } catch (Exception ex) {
                    System.err.println("Exception: " + ex.getMessage());
                }

            }
        });
    }

    public void importTo() throws SQLException {

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object rowData[][] = {{"Row1-Column1", "Row1-Column2", "Row1-Column3"}};
        // array to hold column names
        Object columnNames[] = {"Column One", "Column Two", "Column Three", "4", "5", "6"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        table1.setModel(mTableModel);
        // try and connect to the database
//        try {
//            Class.forName(driver).newInstance();
//            con = DriverManager.getConnection(URL, user, pass);
//        } catch (Exception e) {
//            System.err.println("Exception: " + e.getMessage());
//        }
        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT * FROM customers";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create the gui

        // remove the temp row
//        mTableModel.removeRow(0);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void launch(){
        JFrame j = new NewInterface("123");
        j.setVisible(true);
    }


}

