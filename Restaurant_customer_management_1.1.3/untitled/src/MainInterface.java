import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class MainInterface extends JFrame {
    private Connection con = null;
    private JPanel background;
    public JButton reservationButton;
    private JPanel mainContentPanel;
    private JPanel blankPanel;
    private JButton waitlistButton;
    private JPanel reservationPanel;
    private JPanel resInputPanel;
    private JTable reservationTable;
    private JLabel resDayLabel;
    private JPanel datePanel;
    private JButton save;
    private JLabel resTime;
    private JPanel timePanel;
    private JLabel partySize;
    private JTextField partyTextfield;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel childrenLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField childrenTextfield;
    private JButton updateButton;
    private JLabel phoneNLabel;
    private JTextField phoneNTextField;
    private JLabel RIDLabel;
    private JLabel RIDPanel;
    private JButton cleanButton;
    private JButton deleteButton;
    private JPanel waitListPanel;
    private JPanel waitlistInputPanel;
    private JTable waitlistTable;
    private JLabel firstNameLabel1;
    private JTextField firstNameTextField1;
    private JLabel lastNameLabel1;
    private JTextField lastNameTextField1;
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberTextField;
    private JLabel childrenLabel1;
    private JTextField childrenTextField1;
    private JLabel partySizeLabel;
    private JTextField partySizeTextField;
    private JLabel WID;
    private JLabel WIDPanel;
    private JButton saveWaitlistButton;
    private JButton customersButton;
    private JPanel customerPanel;
    private JTable customerTable;
    private JButton checkinCustomerButton;
    private JPanel checkinPanel;
    private JButton checkinDetailButton;
    private JTable detailTable;
    private JComboBox<String> tabelcomboBox;
    private JComboBox<String> waiterComboBox;
    private JLabel waiterIDLabel;
    private JLabel partySizeDetailLabel;
    private JButton callorderButton;
    private JButton historyButton;
    private JPanel historyPanel;
    private JTable historyTable;
    private JButton checkoutButton;

    public String OID;


    DatePickerSettings s1 = new DatePickerSettings();
    TimePickerSettings s2 = new TimePickerSettings();

    DatePicker p1 = new DatePicker(s1);
    TimePicker p2 = new TimePicker(s2);

    public MainInterface(){

    }

    public MainInterface(String title){
        super(title);
        this.setContentPane(background);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainContentPanel.setVisible(true);
        blankPanel.setVisible(true);

        s2.setFormatForDisplayTime(PickerUtilities.createFormatterFromPatternString(
                "HH:mm:ss", s2.getLocale()));
        s2.setFormatForMenuTimes(PickerUtilities.createFormatterFromPatternString(
                "HH:mm", s2.getLocale()));
        s1.setFormatForDatesCommonEra("yyyy-MM-dd");
        s1.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");

        datePanel.add(p1);
        timePanel.add(p2);

        this.pack();

        this.setLocationRelativeTo(null);
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAllPanel();
                reservationPanel.setVisible(true);

                try {
                    resTableImport();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setResTime();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setCustomer();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println(p1.getText());
                System.out.println(p2.getText());
                try {
                    resTableImport();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cleanText();

            }


        });
        reservationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    getRowFromtable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setUpdateButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    resTableImport();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cleanText();

            }
        });
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanText();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setDeleteButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    resTableImport();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cleanText();
            }
        });
        waitlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAllPanel();
                cleanText();
                waitListPanel.setVisible(true);
                try {
                    setWaitlistTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        saveWaitlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setSaveWaitlistButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setWaitlistTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                cleanText();
            }
        });
        waitlistTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    getRowFromWaitlistTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        customersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                closeAllPanel();
                customerPanel.setVisible(true);

                try {
                    setCustomerTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setTabelcomboBox();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setWaiterComboBox();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        checkinDetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAllPanel();
                checkinPanel.setVisible(true);
                try {
                    setDetailTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getRowFromCustTable();
            }
        });
        waiterComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                waiterIDLabel.setText("Name");
                if(e.getStateChange() == ItemEvent.SELECTED){
                    PreparedStatement pst;
                    ResultSet rs;
                    String query;
                    String waiterID = waiterComboBox.getSelectedItem().toString();
                    query = "SELECT firstName FROM waiters where waiterID = '" + waiterID+ "'";
                    con = sqlConnection.connectToDatabase();
                    try {
                        pst = con.prepareStatement(query);
                        rs = pst.executeQuery();
                        while (rs.next()){
                            String name = rs.getString("firstName");
                            waiterIDLabel.setText(name);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        tabelcomboBox.addItemListener(new ItemListener() {
            @Override

            public void itemStateChanged(ItemEvent e) {
                partySizeDetailLabel.setText("0");
                if(e.getStateChange() == ItemEvent.SELECTED){
                    PreparedStatement pst;
                    ResultSet rs;
                    String query;
                    String TID = tabelcomboBox.getSelectedItem().toString();
                    query = "SELECT size FROM rtables where TID = '" + TID+ "'";
                    con = sqlConnection.connectToDatabase();
                    try {
                        pst = con.prepareStatement(query);
                        rs = pst.executeQuery();
                        while (rs.next()){
                            partySizeDetailLabel.setText(rs.getString(1));
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        checkinCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setCheckinCustomerButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    generateOID();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    importToHistoryTabel();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setCustomerTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setTabelcomboBox();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        callorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(OID);
                OderItems f1 = null;
                try {
                    f1 = new OderItems(OID);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAllPanel();
                historyPanel.setVisible(true);
                try {
                    setHistoryTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        detailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getRowFromdetailTable();
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setCheckoutButton();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setDetailTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void closeAllPanel(){
        reservationPanel.setVisible(false);
        waitListPanel.setVisible(false);
        blankPanel.setVisible(false);
        customerPanel.setVisible(false);
        checkinPanel.setVisible(false);
        historyPanel.setVisible(false);
    }
    public void setUpdateButton() throws SQLException{
        // a MySQL statement
        PreparedStatement pst;
        // a MySQL query
        String query;
        String query2;

        con = sqlConnection.connectToDatabase();
        query = "UPDATE customers SET firstName=?, lastName=?, phoneN=?, children=?  WHERE RID=?";
        pst = con.prepareStatement(query);
        pst.setString(1, firstNameTextField.getText());
        pst.setString(2, lastNameTextField.getText());
        pst.setString(3, phoneNTextField.getText());
        pst.setString(4, childrenTextfield.getText());
        pst.setString(5, RIDPanel.getText());
        pst.execute();
        query2 = "UPDATE reservations SET resDate=?, resTIME=?, partySize=? WHERE RID=?";
        pst = con.prepareStatement(query2);
        pst.setString(1, p1.getText());
        pst.setString(2,p2.getText());
        pst.setString(3, partyTextfield.getText());
        pst.setString(4, RIDPanel.getText());
        pst.execute();
        System.out.println(RIDPanel.getText());
    }

    public void resTableImport() throws SQLException {

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"RID", "createAt", "resDate", "resTime", "partySize", "phoneN", "firstName", "lastName", "children"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        reservationTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT\n" +
                "\tRID,\n" +
                "    createAT,\n" +
                "    resDate,\n" +
                "    resTIME,\n" +
                "    partysize,\n" +
                "    phoneN,\n" +
                "    firstName,\n" +
                "    lastName,\n" +
                "    children\n" +
                "FROM reservations\n" +
                "Left join customers using(RID);";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void setWaitlistTable() throws SQLException{

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"WID", "joinAt", "firstName", "lastName", "phoneN", "children", "partySize"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        waitlistTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT\n" +
                "\tWID,\n" +
                "\tjoinAt,\n" +
                "\tfirstName,\n" +
                "\tlastName,\n" +
                "\tphoneN,\n" +
                "\tchildren,\n" +
                "\tpartySize\n" +
                "FROM waitlists\n" +
                "LEFT JOIN customers USING(WID);";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void setDetailTable() throws SQLException{

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"TID", "phoneN", "creatAT", "comment", "waiterID", "OID"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        detailTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT * FROM checkin;";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void setHistoryTable() throws SQLException{

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"createAt", "TID", "WID", "RID","phoneN", "waiterID", "OID", "firstName", "lastName", "children",  "updateAt"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        historyTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT * FROM rhistory;";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10), rs.getString(11)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }

    public void setCustomerTable() throws SQLException{

        // a MySQL statement
        Statement stmt;
        // a MySQL query
        String query;
        // the results from a MySQL query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object[][] rowData = {};
        // array to hold column names
        Object[] columnNames = {"phoneN", "firstName", "lastName", "RID", "WID", "children"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        customerTable.setModel(mTableModel);

        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "SELECT *\n" +
                "FROM customers;";
        // make a statement with the server
        assert con != null;
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

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

    public void setResTime() throws  SQLException{
        // a MySQL statement
        PreparedStatement stmt;
        // a MySQL query
        String query;


        con = sqlConnection.connectToDatabase();

        // run the desired query
        query = "INSERT INTO reservations (resDate, resTime, partySize) VALUES (?, ?, ?)";
        // make a statement with the server
        assert con != null;


        stmt = con.prepareStatement(query);
        stmt.setString(1, p1.getText());
        stmt.setString(2, p2.getText());
        stmt.setString(3,partyTextfield.getText());
        stmt.execute();
        con.close();


    }
    public void setCustomer() throws SQLException{
        PreparedStatement stmt;
        // a MySQL query
        String query;
        ResultSet rs;
        String RID = "";

        con = sqlConnection.connectToDatabase();
        query = "SELECT RID FROM reservations order by RID desc LIMIT 1;";
        assert con != null;
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        if(rs.next()){
            RID = rs.getString(1);
        }


        query = "INSERT INTO customers (firstName, lastName, children, phoneN, RID) VALUES (?, ?, ?, ?, ?)";
        assert con != null;
        stmt = con.prepareStatement(query);
        stmt.setString(1, firstNameTextField.getText());
        stmt.setString(2, lastNameTextField.getText());
        stmt.setString(3, childrenTextfield.getText());
        stmt.setString(4, phoneNTextField.getText());
        stmt.setString(5, RID);
        stmt.execute();
        con.close();
    }

    public void setSaveWaitlistButton() throws SQLException{
        PreparedStatement stmt;
        // a MySQL query
        String query;
        ResultSet rs;
        String WID = "";

        query = "INSERT INTO waitlists (partySize) Values (?)";
        stmt = con.prepareStatement(query);
        stmt.setString(1, partySizeTextField.getText());
        stmt.execute();

        query = "SELECT WID FROM waitlists ORDER BY WID DESC LIMIT 1";
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        if(rs.next()){
            WID = rs.getString(1);
        }

        con = sqlConnection.connectToDatabase();
        query = "INSERT INTO customers (firstName, lastName, children, phoneN, WID) VALUES (?, ?, ?, ?, ?)";
        assert con != null;
        stmt = con.prepareStatement(query);
        stmt.setString(1, firstNameTextField1.getText());
        stmt.setString(2, lastNameTextField1.getText());
        stmt.setString(3, childrenTextField1.getText());
        stmt.setString(4, phoneNumberTextField.getText());
        stmt.setString(5, WID);
        stmt.execute();
        con.close();
    }

    public void getRowFromtable() throws  SQLException{
        PreparedStatement pst;
        String query;
        ResultSet rs;
        int rowN = reservationTable.getSelectedRow();
        String rowDetail = (reservationTable.getModel().getValueAt(rowN, 0).toString());
        query = "SELECT\n" +
                "\tRID,\n" +
                "    createAT,\n" +
                "    resDate,\n" +
                "    resTIME,\n" +
                "    partysize,\n" +
                "    phoneN,\n" +
                "    firstName,\n" +
                "    lastName,\n" +
                "    children\n" +
                "FROM reservations\n" +
                "Left join customers using(RID)\n" +
                "WHERE RID='" + rowDetail + "'";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        if(rs.next()){
            firstNameTextField.setText(rs.getString(7));
            p1.setText(rs.getString(3));
            RIDPanel.setText(rs.getString(1));
            p2.setText(rs.getString(4));
            partyTextfield.setText(rs.getString(5));
            lastNameTextField.setText(rs.getString(8));
            childrenTextfield.setText(rs.getString(9));
            phoneNTextField.setText(rs.getString(6));

        }
        System.out.println(rowDetail);


    }

    public void getRowFromWaitlistTable() throws SQLException{
        PreparedStatement pst;
        String query;
        ResultSet rs;
        int rowN = waitlistTable.getSelectedRow();
        String rowDetail = (waitlistTable.getModel().getValueAt(rowN, 0).toString());
        query = "SELECT\n" +
                "\tWID,\n" +
                "\tjoinAt,\n" +
                "\tfirstName,\n" +
                "\tlastName,\n" +
                "\tphoneN,\n" +
                "\tchildren,\n" +
                "\tpartySize\n" +
                "FROM waitlists\n" +
                "LEFT JOIN customers USING(WID)" +
                "WHERE WID='" + rowDetail + "'";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        if(rs.next()){
            firstNameTextField1.setText(rs.getString(3));
            WIDPanel.setText(rs.getString(1));
            partySizeTextField.setText(rs.getString(7));
            lastNameTextField1.setText(rs.getString(4));
            childrenTextField1.setText(rs.getString(6));
            phoneNumberTextField.setText(rs.getString(5));

        }
    }

    public void getRowFromCustTable(){

        int rowN = customerTable.getSelectedRow();
        String rowDetail = (customerTable.getModel().getValueAt(rowN, 0).toString());
        phoneNumberTextField.setText(rowDetail);
        if(customerTable.getModel().getValueAt(rowN,3) != null){
            String RID = (customerTable.getModel().getValueAt(rowN, 3).toString());
            RIDPanel.setText(RID);
            WIDPanel.setText("");
            System.out.println(RID);
        }
        else {
            String WID = (customerTable.getModel().getValueAt(rowN, 4).toString());
            WIDPanel.setText(WID);
            RIDPanel.setText("");
            System.out.println(WID);
        }

        System.out.println(phoneNumberTextField.getText());

    }

    public void getRowFromdetailTable(){
        int rowN = detailTable.getSelectedRow();
        String rowDetail = (detailTable.getModel().getValueAt(rowN, 5).toString());
        OID = rowDetail;
        System.out.println(OID);
    }
    public void generateOID() throws SQLException{
        PreparedStatement pst;
        String query;
        con = sqlConnection.connectToDatabase();
        query = "INSERT INTO orders(total) VALUES (0)";
        pst = con.prepareStatement(query);
        pst.execute();

        query = "UPDATE checkin SET OID = (SELECT OID FROM orders ORDER BY OID DESC LIMIT 1)" +
                "ORDER BY createAt DESC LIMIT 1";
        pst = con.prepareStatement(query);
        pst.execute();
        con.close();
    }
    public void setDeleteButton() throws  SQLException{
        PreparedStatement pst;
        String query1;
        String query2;
        query1 = "DELETE FROM customers where RID='" + RIDPanel.getText()+"'";
        query2 = "DELETE FROM reservations where RID='" + RIDPanel.getText()+"'";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query1);

        pst.execute();
        pst = con.prepareStatement(query2);
        pst.execute();

        con.close();

    }

    public void setTabelcomboBox() throws SQLException{
        tabelcomboBox.removeAllItems();
        PreparedStatement pst;
        ResultSet rs;
        String query;
        query = "SELECT TID FROM " +
                "rtables " +
                "LEFT JOIN checkin USING(TID)" +
                "WHERE checkin.TID IS NULL;";
        con = sqlConnection.connectToDatabase();
        assert con != null;
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()){
            String TID = rs.getString("TID");
            tabelcomboBox.addItem(TID);
        }
        tabelcomboBox.setSelectedIndex(-1);
    }

    public void setWaiterComboBox() throws SQLException{
        waiterComboBox.removeAllItems();
        PreparedStatement pst;
        ResultSet rs;
        String query;
        query = "SELECT waiterID FROM waiters;";
        con = sqlConnection.connectToDatabase();
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()){
            String waiterID = rs.getString("waiterID");
            waiterComboBox.addItem(waiterID);
        }
        waiterComboBox.setSelectedIndex(-1);
    }

    public void setCheckinCustomerButton() throws SQLException{
        PreparedStatement pst;
        String query;
        con = sqlConnection.connectToDatabase();
        query = "INSERT INTO checkin(TID, phoneN, waiterID) VALUES (?,?,?)";
        pst = con.prepareStatement(query);
        pst.setString(1, tabelcomboBox.getSelectedItem().toString());
        pst.setString(2,phoneNumberTextField.getText());
        pst.setString(3, waiterComboBox.getSelectedItem().toString());
        pst.execute();


    }

    public void importToHistoryTabel() throws SQLException{
        PreparedStatement pst;
        String query;
        con = sqlConnection.connectToDatabase();
        query = "INSERT INTO rhistory( OID,firstName, lastName, children, phoneN, RID, WID, TID, waiterID)\n" +
                "SELECT OID, firstName, lastname, children, phoneN, RID, WID, TID, waiterID \n" +
                "FROM customers RIGHT JOIN checkin USING(phoneN) \n" +
                "WHERE customers.phoneN = ?";
        pst = con.prepareStatement(query);
        pst.setString(1,phoneNumberTextField.getText());
        pst.execute();


        //delete row from customers table
        query = "DELETE FROM customers WHERE phoneN = ?";
        pst = con.prepareStatement(query);
        pst.setString(1, phoneNumberTextField.getText());
        pst.execute();

        if(!RIDPanel.getText().isEmpty()){
            System.out.println(RIDPanel.getText());
            query = "DELETE FROM reservations WHERE RID = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, RIDPanel.getText());
            pst.execute();
        }
        else {
            query = "DELETE FROM waitlists WHERE WID = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, WIDPanel.getText());
            pst.execute();
        }
        con.close();
    }

    public void setCheckoutButton()throws SQLException{
        PreparedStatement pst;
        String q;
        con = sqlConnection.connectToDatabase();
        q = "DELETE FROM checkin\n" +
                "WHERE OID = ?";
        pst = con.prepareStatement(q);
        pst.setString(1, OID);
        pst.execute();
    }


    public void cleanText(){
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        p1.setText("");
        p2.setText("");
        partyTextfield.setText("");
        childrenTextfield.setText("");
        phoneNTextField.setText("");
        RIDPanel.setText("");
        phoneNumberTextField.setText("");
        firstNameTextField1.setText("");
        lastNameTextField1.setText("");
        childrenTextField1.setText("");
        partySizeTextField.setText("");

    }


    public static void main(String[] args){
        JFrame f = new MainInterface("Restuarant Customer Management");
        f.setVisible(true);
    }
}
