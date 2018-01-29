package br.com.explorecraft.ec_economy.api;

import org.bukkit.plugin.Plugin;

import br.com.explorecraft.ec_economy.Main;
import br.com.explorecraft.ec_economy.PMoney;

public class Economia {

	private Plugin plugin;
	
	public Economia(Plugin plugin){
		this.plugin = plugin;
		Main.plugin.debug("Synchronized API with " + plugin.getName() + " v" + plugin.getDescription().getVersion());
	}
	public Plugin getPlugin(){
		return this.plugin;
	}
	
	public double getBalance(String player){
		PMoney p = Main.plugin.getPMoney(player);
		if (p != null){
			return p.getMoney();
		}
		return 0;
	}
	public PMoney getPlayerEconomy(String player){
		return Main.plugin.getPMoney(player);
	}
	public Plugin getInstance(){
		return Main.plugin;
	}
}
