import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OderItems extends MainInterface{
    private Connection con = null;
    private JComboBox foodNameBox;
    private JButton addFoodButton;
    private JButton backButton;
    private JButton deleteFoodButton;
    private JTable itemTable;
    private JLabel orderIDLabel;
    private JComboBox foodquantityComboBox;
    private JPanel orderBackgound;
    private JLabel OIDNPanel;
    private JLabel FIDLabel;
    private JLabel FIDdetailLabel;
    private JLabel FoodLabel;
    private JLabel TotalLabel;
    private JLabel totaldetailLabel;


    public OderItems(String OID) throws SQLException {
        OIDNPanel.setText(OID);
        JFrame j = new JFrame();
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        j.setPreferredSize(new Dimension(400,600));
        j.pack();
        j.setLocationRelativeTo(null);
        j.setVisible(true);

        j.setContentPane(orderBackgound);
        orderBackgound.setVisible(true);
        setItemTable(OIDNPanel.getText());
        System.out.println(OID);

        setFoodNameBox();
        setTotaldetailLabel();

        for(int i = 0; i < 6; i ++){
            foodquantityComboBox.addItem(i);
        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                j.dispatchEvent(new WindowEvent(j, WindowEvent.WINDOW_CLOSING));
            }
        });

        foodNameBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                FIDdetailLabel.setText("");
                if(e.getStateChange() == ItemEvent.SELECTED){
                    PreparedStatement pst;
                    ResultSet rs;
                    String query;
                    String foodname = foodNameBox.getSelectedItem().toString();
                    query = "SELECT FID FROM menu where foodName = '" + foodname+ "'";
                    con = sqlConnection.connectToDatabase();
                    try {
                        pst = con.prepareStatement(query);
                        rs = pst.executeQuery();
                        while (rs.next()){
                            FIDdetailLabel.setText(rs.getString(1));
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setAddFoodButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setItemTable(OIDNPanel.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setTotaldetailLabel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        itemTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getRowDetailFromitemTable();
            }
        });
        deleteFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setDeleteFoodButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setItemTable(OIDNPanel.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setTotaldetailLabel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



    public void setItemTable(String OID ) throws SQLException{
        System.out.println(OID);
        // a MySQL statement
        PreparedStatement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"OID", "FID", "foodName", "price", "quanity"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        itemTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT OID, FID, foodName, price, quantity FROM orderitems\n" +
                "right join menu USING(FID)\n" +
                "WHERE OID = ?";
        // make a statement with the server

        assert con != null;
        stmt = con.prepareStatement(query);


        stmt.setString(1, OID );
        // execute the query and return the result

        rs = stmt.executeQuery();

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void setFoodNameBox() throws SQLException{
        foodNameBox.removeAllItems();
        PreparedStatement pst;
        ResultSet rs;
        String query;
        query = "SELECT foodName FROM menu;";
        con = sqlConnection.connectToDatabase();
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()){
            String waiterID = rs.getString("foodName");
            foodNameBox.addItem(waiterID);
        }
        foodNameBox.setSelectedIndex(-1);
    }

    public void setAddFoodButton() throws SQLException {
        PreparedStatement pst;
        String query;

        query = "INSERT INTO orderitems (FID, quantity, OID) VALUES (?,?, ?)";
        con = sqlConnection.connectToDatabase();
        pst = con.prepareStatement(query);
        pst.setString(1, FIDdetailLabel.getText());
        pst.setString(2, foodquantityComboBox.getSelectedItem().toString());
        pst.setString(3,OIDNPanel.getText());
        pst.execute();


    }

    public void setDeleteFoodButton() throws SQLException{
        PreparedStatement pst;
        String query1;
        query1 = "DELETE FROM orderitems where ( FID=? AND OID = ?)";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query1);
        pst.setString(1, FIDdetailLabel.getText());
        pst.setString(2, OIDNPanel.getText());
        pst.execute();

        con.close();
    }

    public void setTotaldetailLabel()throws SQLException{
        PreparedStatement pst;
        String query;
        ResultSet rs;

        query = "SELECT SUM(price * quantity)\n" +
                "FROM orders\n" +
                "join orderitems USING (OID)\n" +
                "join menu using(FID)\n" +
                "WHERE OID = ?\n" +
                "GROUP BY OID;";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query);
        pst.setString(1, OIDNPanel.getText());
        rs = pst.executeQuery();
        while (rs.next()){
            totaldetailLabel.setText(rs.getString(1));
        }

        if(!totaldetailLabel.getText().isEmpty()){
            con = sqlConnection.connectToDatabase();
            query = "UPDATE orders\n" +
                    "SET total = ?\n" +
                    "WHERE OID = ?;";
            assert con != null;
            pst = con.prepareStatement(query);
            pst.setString(1, totaldetailLabel.getText());
            pst.setString(2,OIDNPanel.getText());
            pst.execute();
            con.close();
        }
    }

    public void getRowDetailFromitemTable(){
        int rowN = itemTable.getSelectedRow();
        String rowDetail = (itemTable.getModel().getValueAt(rowN, 1).toString());
        FIDdetailLabel.setText(rowDetail);
    }
}
