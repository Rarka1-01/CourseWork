package main.java.Server;

import java.sql.*;

public class DataBase
{
    DataBase(String DB_URL)
    {
        this.DB_URL = DB_URL;
        try
        {
            connection = DriverManager.getConnection(DB_URL);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Error of close SQL table");
        }
    }

    private void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
    }

    private void executeSqlStatement(String sql, String description) throws SQLException
    {
        this.reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();

        if (description != null)
            System.out.println(description);
    }

    private void executeSqlStatement(String sql) throws SQLException
    {
        executeSqlStatement(sql, null);
    };

    public void createTable() throws SQLException
    {
        executeSqlStatement("CREATE TABLE IF NOT EXISTS shares(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "count_entity INTEGER NOT NULL," +
                "count_rabbit INTEGER NOT NULL," +
                "count_female_wolf INTEGER NOT NULL," +
                "count_male_wolf INTEGER NOT NULL," +
                "average_hungry_fw DECIMAL(10,2) NOT NULL," +
                "average_hungry_mw DECIMAL(10,2) NOT NULL," +
                "average_hungry_all DECIMAL(10,2) NOT NULL)");
    }

    public void update(int hash, int c, int c_r, int c_wf, int c_wm, float h_f, float h_m, float ah) throws SQLException
    {
        this.reopenConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate("UPDATE SHARES SET " +
                "count_entity=" + c +
                ", count_rabbit=" + c_r +
                ", count_female_wolf=" + c_wf +
                ", count_male_wolf=" + c_wm +
                ", average_hungry_fw=" + h_f +
                ", average_hungry_mw=" + h_m +
                ", average_hungry_all=" + ah + " WHERE ID=" + hash +";");

        statement.close();
    }
    public void getInfo(int hash) throws SQLException
    {
        this.reopenConnection();
        Statement statement = connection.createStatement();
        ResultSet rS = statement.executeQuery("SELECT * FROM SHARES WHERE ID=" + hash + ";");

        while(rS.next())
        {
            System.out.println("Hash: " + rS.getInt(1));
            System.out.println("Count entity: " + rS.getInt(2));
            System.out.println("Count rabbit: " + rS.getInt(3));
            System.out.println("Count female wolf: " + rS.getInt(4));
            System.out.println("Count male wolf: " + rS.getInt(5));
            System.out.println("Average hungry female wolf: " + rS.getFloat(6));
            System.out.println("Average hungry male wolf: " + rS.getFloat(7));
            System.out.println("Average hungry: " + rS.getFloat(8));
        }

        statement.close();
    }
    public void insert(String insert) throws SQLException
    {
        this.executeSqlStatement("INSERT INTO SHARES VALUES(" + insert + ");");
    }
    public void clear() throws SQLException
    {
        this.executeSqlStatement("DELETE FROM SHARES;");
    }

    private static Connection connection;
    private String DB_URL;
}
