/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.data;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ZenitH
 */
public class DBConnection {
    Connection con = null;
    public Connection createConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cricketdb", "root", "");
            return con;
        }
        catch (Exception ex)
        {
            con = null;
            return con;
        }
    }
}
