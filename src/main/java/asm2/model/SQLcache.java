package asm2.model;

import java.io.File;
import java.sql.*;

public class SQLcache {
     public String dbURL = "jdbc:sqlite:src/main/resources/test.db";

    public void createDB() {
        File dbFile = new File("test.db");
        if (dbFile.exists()) {
            System.out.println("Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void removeDB() {
        File dbFile = new File("test.db");
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                System.exit(-1);
            } else {
            }
        } else {
        }
    }

    public void setupDB() {

        String createRatesTableSQL =
                """
                CREATE TABLE IF NOT EXISTS Rates (
                    base   TEXT    NOT NULL,
                    symbol TEXT    NOT NULL,
                    rate   TEXT    NOT NULL,
                    PRIMARY KEY (
                        base,
                        symbol
                    )
                );
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(createRatesTableSQL);
//            statement.execute(createCurrencyTableSQL);

            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    public void clearDB(){
//
        File dbFile = new File("src/main/resources/test.db");
        if (!dbFile.exists()) {
            return;
        }
//        String delete_rates = "DELETE FROM Rates;VACUUM;";
        try{
            Connection con = DriverManager.getConnection(this.dbURL);
            Statement stmt = con.createStatement();

            String delete_rates = "DELETE FROM Rates;VACUUM;";
            stmt.execute(delete_rates);
            con.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }
    public Boolean IfContains(String base,String symbol){
        this.createDB();
        this.setupDB();
        try{
            Connection con = DriverManager.getConnection(this.dbURL);
            Statement stmt = con.createStatement();
            String sql = String.format(
                    "SELECT * FROM Rates"+
                            " WHERE base = '%s' AND " +
                            "symbol = '%s';",
                    base, symbol);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
//                rs.close();
            }else{
                rs.close();
                stmt.close();
                return false;
//                rs.close();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public String getRate(String base,String symbol){
        String rate = "";
        try{
            Connection con = DriverManager.getConnection(this.dbURL);
            Statement stmt = con.createStatement();
            String sql = String.format(
                    "SELECT rate FROM Rates"+
                            " WHERE base = '%s' AND " +
                            "symbol = '%s';",
                    base, symbol);
            ResultSet rs = stmt.executeQuery(sql);
            rate = rs.getString(1);
            rs.close();
            stmt.close();
            return rate;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rate;
    }
    public void cache(String base,String symbol,String rate){
        this.createDB();
        this.setupDB();
        try{
            Connection con = DriverManager.getConnection(this.dbURL);
            Statement stmt = con.createStatement();
            String sql = String.format(
                    "SELECT * FROM Rates"+
                            " WHERE base = '%s' AND " +
                            "symbol = '%s';",
                    base, symbol);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                //                    System.out.println("uuu");
                PreparedStatement pss = con.prepareStatement("UPDATE " +
                        "Rates" +
                        " SET rate= ?  WHERE base = ? AND " +
                        "symbol =?;");
                pss.setString(1, rate);
                pss.setString(2, base);
                pss.setString(3, symbol);
                pss.executeUpdate();
                pss.close();
                rs.close();
            }else{
                PreparedStatement psr = con.prepareStatement("INSERT INTO" +
                        " " +
                        "Rates" +
                        " (base,symbol,rate) VALUES (?,?," +
                        "?);PRAGMA" +
                        " foreign_keys = ON;");
                psr.setString(1, base);
                psr.setString(2, symbol);
                psr.setString(3, rate);
                psr.executeUpdate();
                psr.close();
                rs.close();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
