package br.com.explorecraft.ec_economy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;

import br.com.explorecraft.ec_economy.msgs.MsgStr;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PMoney {
	
	private String player = "";
	private double money = 0;
	
	public PMoney(String player){
		this.player = player;
	//	loadData();
	}
	
	public void loadData() {
		try {
			Connection c = Main.database.getNewConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Money FROM Money WHERE Player='" + player + "'");
			if (rs.next()){
				this.money = Double.parseDouble(rs.getString("Money"));
				//Main.plugin.debug("Money de " + player + " retornado da database.");
			}
			c.close();
			stmt.close();
			rs.close();
		} catch (SQLException e){
			Bukkit.getPlayer(UUID.fromString(player)).kickPlayer(MsgStr.internalerror);
		}
	}
	
	public double getMoney(){
		return this.money;
	}

	//This is the Database key.
	public String getPlayerUUID(){return player;}

	public String getPlayerName(){
	    Player pp = Main.plugin.getServer().getPlayer(UUID.fromString(player));
        if ( pp!= null) {
            return pp.getName();
        } else {
            OfflinePlayer offp = Main.plugin.getServer().getOfflinePlayer(UUID.fromString(player));
            if (offp != null) {
                return offp.getName();
            }
            else
                return "UnknownPlayerName";
        }

	}
	public void setMoney(double value){
		this.money = value;
	}
	public void save() throws SQLException{
		Connection c = Main.database.getNewConnection();
		String cmd = "SELECT Player FROM Money WHERE Player='" + player + "'";
		//Statement stmt = c.createStatement();
		PreparedStatement stmt = c.prepareStatement(cmd);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			Main.database.update(String.valueOf(money), player);
		} else {
			Main.database.insert(String.valueOf(money), player);
		}
		c.close();
		//stmt.close();
		//rs.close();
		c = null;
		//Main.plugin.debug("Money de " + player + " salvo.");
	}
}
