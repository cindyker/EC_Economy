package br.com.explorecraft.ec_economy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.explorecraft.ec_economy.Main;
import br.com.explorecraft.ec_economy.PMoney;

public class Save implements Runnable{

	private double last_top_update = 0;
	
	@Override
	public void run() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					//int i = 0;
					Connection c = Main.database.getNewConnection();
					
					//Statement stmt = c.createStatement();
					for (String p : Main.plugin.accounts.keySet()){
							
						PMoney pm = Main.plugin.getPMoney(p);
						String cmd = "UPDATE Money SET Money='" + pm.getMoney() + "' WHERE Player='" + pm.getPlayerUUID() + "'";
						
						PreparedStatement stmt = c.prepareStatement(cmd);
						
						if (Main.plugin.value_modific.containsKey(pm)){
							if (Main.plugin.value_modific.get(pm) != pm.getMoney()){
								stmt.executeUpdate();
								Main.plugin.value_modific.put(pm, pm.getMoney());
								//i++;
							}
						} else {
							Main.plugin.value_modific.put(pm, pm.getMoney());
						}
					}
					if (last_top_update < System.currentTimeMillis()){
						String top = Main.plugin.getConfig().getString("magnata");
						String magnata = top;
						List<PMoney> pm = Main.plugin.getTop();
						Main.tops = pm;
						PMoney mmoney = Main.plugin.getPMoney(top);
						for (PMoney ap : pm){
							if (mmoney == null){
								mmoney = ap;
							}
							if (ap.getMoney() > mmoney.getMoney()){
								magnata = ap.getPlayerUUID();
								mmoney = ap;
							}
						}
						last_top_update = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3);
					}
					
					
					//Main.plugin.debug("Foram salvas " + i + " contas.");
					c.close();
				} catch (SQLException e){
					e.printStackTrace();
					Main.plugin.debug("Failed to save accounts.");
				}
			}
		}.runTaskTimerAsynchronously(Main.plugin, 0, 60*20);
	}
}
