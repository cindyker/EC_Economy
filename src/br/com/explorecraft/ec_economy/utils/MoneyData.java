package br.com.explorecraft.ec_economy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.bukkit.Bukkit;

import br.com.explorecraft.ec_economy.Main;
import br.com.explorecraft.ec_economy.PMoney;

public class MoneyData {
	
	public static void createAccount(String nick) throws SQLException{
		PMoney pm = new PMoney(nick);
		pm.setMoney(0);
		pm.save();
		Main.plugin.accounts.put(nick, pm);
		Main.plugin.debug("§e " + nick + " - Successfully created account.");
	}
		
	public static void loadAccounts(){
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					//int i = 0;
					String cmd = "SELECT * FROM Money";
					Connection c = Main.database.getNewConnection();
					PreparedStatement stmt = c.prepareStatement(cmd);
					//Statement stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery();
					while (rs.next()){
						String player = rs.getString("Player");
						double money = Double.valueOf(rs.getString("Money"));
						PMoney pm = new PMoney(player);
						pm.setMoney(money);
						Main.plugin.accounts.put(player, pm);
						Main.plugin.value_modific.put(pm, money);
						//i++;
					}
					c.close();
					//stmt.close();
					rs.close();
					//Main.plugin.debug("Foram carregadas " + i + " contas.");
				} catch (SQLException e){
					e.printStackTrace();
					Bukkit.shutdown();
					Main.plugin.getLogger().warning("ERROR LOADING DATABASE PLAYER ACCOUNTS!");
				}
			}
		});
		th.start();
	}
}
