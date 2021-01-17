package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.List;

public class Database {
    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bada?&serverTimezone=UTC&jdbcCompliantTruncation=false", "root", "1234");
    Statement myStmt = myConn.createStatement();
    ResultSet myRS;

    public Database() throws SQLException {

    }

    public void insert(String tabela, String wartosci) throws SQLException {
        myStmt.execute("INSERT INTO " + tabela + " VALUES" + wartosci);
    }

    public ArrayList<String> select(String co, String skad, String warunek) throws SQLException {

        ArrayList<String> zwracanie = new ArrayList<>();
        String[] kolumny = co.split(",");

        if (co.equals("*")) {
            kolumny = getColumns(skad).toArray(new String[0]);
        } else
            for (int i = 0; i < kolumny.length; i++) {
                kolumny[i] = kolumny[i].trim();
            }

        if (!warunek.equals("")) {
            myRS = myStmt.executeQuery("SELECT * FROM " + skad + " WHERE " + warunek);
            while (myRS.next()) {
                for (String s : kolumny) zwracanie.add((myRS.getString(s)));
            }
        } else {
            myRS = myStmt.executeQuery("SELECT * FROM " + skad);
            while (myRS.next()) {
                for (String s : kolumny) zwracanie.add((myRS.getString(s)));
            }
        }
        return zwracanie;
    }

    public int validate(String login, String password) throws SQLException {
        ArrayList<String> haslo_z_bazy = select("password", "USERS", "login = " + "'" + login + "'");
        if (password.equals(haslo_z_bazy.get(0))) {
            return Integer.parseInt(select("poziom_dostepu", "USERS", "login = " + "'" + login + "'").get(0));
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {

        Database bazadanych = new Database();

        bazadanych.addPrimaryKey("users", "czas");

    }


    public String getPrimaryKey(String tabela) throws SQLException {
        String x = "";
        myRS = myStmt.executeQuery("show index from " + tabela + " where Key_name = 'PRIMARY'");
        while (myRS.next()) {
            x = myRS.getString(5);
        }
        return x;
    }

    public void deleteRecord(String tabela, String warunek) throws SQLException {
        myStmt.executeUpdate("DELETE FROM " + tabela + " WHERE " + warunek);
    }

    public void updateRecord(String tabela, String wartosc, String kolumna, String warunek) throws SQLException {
        myStmt.executeUpdate("UPDATE " + tabela + " SET " + kolumna + "=" + wartosc + " WHERE " + warunek);
    }


    public ArrayList<String> getColumns(String tabela) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        myRS = myStmt.executeQuery("SELECT * FROM " + tabela);
        ResultSetMetaData rsmd = myRS.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount != 0) {
            for (int i = 1; i <= columnCount; i++) {
                result.add(rsmd.getColumnName(i));
            }
        }
        return result;
    }

    public ArrayList<String> getTables() throws SQLException {
        ArrayList<String> lista_tabel = new ArrayList<>();
        myRS = myStmt.executeQuery("SHOW TABLES");
        while (myRS.next()) {
            lista_tabel.add(myRS.getString(1));
        }
        return lista_tabel;
    }

    public ArrayList<String> getTablesWithoutUsers() throws SQLException {
        ArrayList<String> lista_tabel = new ArrayList<>();
        myRS = myStmt.executeQuery("SHOW TABLES");
        while (myRS.next() && !myRS.getString(1).equals("users")) {
            lista_tabel.add(myRS.getString(1));

        }
        return lista_tabel;
    }




    public JTable getTable(String tabela) throws SQLException {
        JTable finalowy = new JTable();
        Vector<String> row = new Vector<>();
        ArrayList<List<String>> testowe = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel();
        for (int i = 0; i < getColumns(tabela).size(); i++) {
            model.addColumn(getColumns(tabela).get(i));
        }
        for (int i = 0; i < getNumberOfRows(tabela) * getColumns(tabela).size(); i = i + getColumns(tabela).size()) {
            testowe.add(select("*", tabela, "").subList(i, i + getColumns(tabela).size()));
        }
        for (List<String> strings : testowe) {
            for (int j = 0; j < strings.size(); j++) {
                row.add(strings.get(j));
                if (j == strings.size() - 1) {
                    model.addRow(row);
                    row = new Vector<>();
                }
            }
        }

        finalowy.setModel(model);
        finalowy.setRowHeight(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        finalowy.setFont(new Font("Verdana", Font.PLAIN, 17));
        JTableHeader tableHeader = finalowy.getTableHeader();
        tableHeader.setBackground(Color.lightGray);
        tableHeader.setForeground(Color.black);
        Font headerFont = new Font("Verdana", Font.PLAIN, 25);
        tableHeader.setFont(headerFont);
        DefaultTableCellRenderer headerrenderer = new DefaultTableCellRenderer();
        headerrenderer.setBackground(new Color(255, 100, 50));
        headerrenderer.setForeground(Color.black);
        headerrenderer.setFont(headerFont);
        headerrenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < getColumns(tabela).size(); i++) {
            if (finalowy.getColumnModel().getColumn(i).getHeaderValue().toString().equals(getPrimaryKey(tabela))) {
                finalowy.getColumnModel().getColumn(i).setHeaderRenderer(headerrenderer);
                finalowy.setFont(new Font("Verdana", Font.PLAIN, 25));
                finalowy.getColumnModel().moveColumn(i, 0);
                finalowy.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            }
            finalowy.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return finalowy;
    }

    public int getNumberOfRows(String tabela) throws SQLException {
        int x = 0;
        myRS = myStmt.executeQuery("SELECT COUNT(*) FROM " + tabela);
        while (myRS.next()) {
            x = Integer.parseInt(myRS.getString(1));
        }
        return x;
    }


    public Map<String, String> getColumnsAndTypes(String tabela) throws SQLException {
        Map<String, String> map = new HashMap<>();
        myRS = myStmt.executeQuery("SHOW COLUMNS FROM " + tabela);
        while (myRS.next()) {
            map.put(myRS.getString(1), myRS.getString(2));
        }


        return map;

    }

    public String dropTable(String tabela) throws SQLException {
        myStmt.executeUpdate("DROP TABLE " + tabela);
        return "Tabela " + tabela + " została usunięta";
    }


    public void createTable(ArrayList<JTextField> nazwyKolumn, ArrayList<JTextField> ArgumentyTypowDanych, ArrayList<JComboBox<String>> listaZTypamiDanych, JTextField nazwaTabeli, ArrayList<Boolean> notnull) throws SQLException {
        String p = listaZTypamiDanych.get(0).getSelectedItem().toString().replaceAll("x", ArgumentyTypowDanych.get(0).getText());
        if(notnull.get(0)){myStmt.executeUpdate("CREATE TABLE " + nazwaTabeli.getText() + " (" + nazwyKolumn.get(0).getText() + " " + p  + " NOT NULL" + ")");}else
        {myStmt.executeUpdate("CREATE TABLE " + nazwaTabeli.getText() + " (" + nazwyKolumn.get(0).getText() + " " + p + ")");}

        for (int i = 1; i < nazwyKolumn.size(); i++) {
            p = listaZTypamiDanych.get(i).getSelectedItem().toString().replaceAll("x", ArgumentyTypowDanych.get(i).getText());
            if(notnull.get(i)){myStmt.executeUpdate("ALTER TABLE " + nazwaTabeli.getText() + " ADD " + nazwyKolumn.get(i).getText() + " " + p + " NOT NULL");}
            else myStmt.executeUpdate("ALTER TABLE " + nazwaTabeli.getText() + " ADD " + nazwyKolumn.get(i).getText() + " " + p);

        }


    }


    public void addPrimaryKey(String tabela, String pk) throws SQLException {
        myStmt.executeUpdate("ALTER TABLE " + tabela + " ADD PRIMARY KEY " + "(" + pk + ")");
    }

    public void dropPrimaryKey(String tabela) throws SQLException {
        myStmt.executeUpdate("ALTER TABLE " + tabela + " DROP PRIMARY KEY");
    }

    public boolean isNullable(String kolumna) throws SQLException {
        ArrayList<String> h1 = new ArrayList<>();
        ArrayList<String> h2 = new ArrayList<>();

        myRS = myStmt.executeQuery("select c.table_schema as database_name,\n" +
                "       c.table_name,\n" +
                "       c.column_name,\n" +
                "       case c.is_nullable\n" +
                "            when 'NO' then 'not nullable'\n" +
                "            when 'YES' then 'is nullable'\n" +
                "       end as nullable\n" +
                "from information_schema.columns c\n" +
                "join information_schema.tables t\n" +
                "     on c.table_schema = t.table_schema \n" +
                "     and c.table_name = t.table_name\n" +
                "where c.table_schema not in ('mysql', 'sys', 'information_schema',\n" +
                "                             'performance_schema')\n" +
                "      and t.table_type = 'BASE TABLE'\n" +
                "      -- and t.table_schema = 'database_name' -- put your database name here\n" +
                "order by t.table_schema,\n" +
                "         t.table_name,\n" +
                "         c.column_name;");
        while (myRS.next()) {
            h1.add((myRS.getString(3)));
            h2.add(myRS.getString(4));
        }
        for (int i = 0; i < h1.size(); i++) {
            if (kolumna.equals(h1.get(i))) {
                switch (h2.get(i)) {
                    case "is nullable":
                        return true;
                    case "not nullabe":
                        return false;

                }
            }
        }
        return false;


    }

    public String md5Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
