package br.com.explorecraft.ec_economy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.explorecraft.ec_economy.Main;


public class SQL {
	
	public String host, database, username, password;
	private String table = "Money";
	public int port;
	public SQL(){
		if (Main.plugin.getConfig().getBoolean("MySQL.enable")) {
		try {
			
			host = Main.plugin.getConfig().getString("MySQL.hostname");
			database = Main.plugin.getConfig().getString("MySQL.database");
			username = Main.plugin.getConfig().getString("MySQL.username");
			password = Main.plugin.getConfig().getString("MySQL.password");		
			port = Main.plugin.getConfig().getInt("MySQL.port");
			
			
			
			Connection c;
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false",
					this.username, this.password);
			String cmd = "CREATE TABLE IF NOT EXISTS Money (Player TEXT, Money TEXT)";
			PreparedStatement stmt = c.prepareStatement(cmd);
			stmt.execute();
			System.out.println("[EC_Economy] MySQL Connected.");
			c.close();
			//stmt.close();
		} catch (Exception e){
			System.out.println("MySQL Error");
			e.printStackTrace();
		}} else {
			try {
				Connection c;
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:plugins/EC_Economy/database.db");
				String cmd = "CREATE TABLE IF NOT EXISTS Money (Player TEXT, Money TEXT)";
				PreparedStatement stmt = c.prepareStatement(cmd);
				stmt.execute();
				System.out.println("[EC_Economy] SQLite Connected.");
				c.close();
				//stmt.close();
			} catch (Exception e){
				System.out.println("SQLite Error");
				e.printStackTrace();
			}
		}
	}
	public void setTable(String table){
		this.table = table;
	}
	public Connection getNewConnection() throws SQLException {
		if (Main.plugin.getConfig().getBoolean("MySQL.enable")) {
		return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false",
				this.username, this.password);} else {
					return DriverManager.getConnection("jdbc:sqlite:plugins/EC_Economy/database.db");
				}
	}
	public void insert(String value, String player) throws SQLException{
		Connection c = getNewConnection();
		String r= "INSERT INTO " + table + " (Player, Money) VALUES ('" + player + "','" + value + "');";
		PreparedStatement stmt = c.prepareStatement(r);
		stmt.execute();
		c.close();
		//stmt.close();
	}
	public void update(String value, String player) throws SQLException{
		Connection c = getNewConnection();
		String q = "UPDATE " + table + " SET Money='" + value + "' WHERE Player='" + player + "'";
		PreparedStatement stmt = c.prepareStatement(q);
		stmt.executeUpdate();
		c.close();
		//stmt.close();
	}

}
