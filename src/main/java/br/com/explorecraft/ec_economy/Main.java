package br.com.explorecraft.ec_economy;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.explorecraft.ec_economy.comandos.MoneyCMD;
import br.com.explorecraft.ec_economy.listeners.PlayerJoin;
import br.com.explorecraft.ec_economy.msgs.ConfigManager;
import br.com.explorecraft.ec_economy.msgs.Messages;
import br.com.explorecraft.ec_economy.utils.Formater;
import br.com.explorecraft.ec_economy.utils.MoneyData;
import br.com.explorecraft.ec_economy.utils.SQL;

import br.com.explorecraft.ec_economy.utils.Save;


public class Main extends JavaPlugin{
	
	public static Main plugin;
	public HashMap<String, PMoney> accounts = new HashMap<>();
	public HashMap<PMoney, Double> value_modific = new HashMap<>();
	public static SQL database;
	public ConfigManager cm;
	public static List<PMoney> tops = new ArrayList<>();
	private Economy vault;
	
	public Economy getEconomy(){
		return vault;
	}
	
	public void onEnable(){
		plugin = this;
		
		File f = new File("plugins/EC_Economy");
		if (!f.exists()){
			f.mkdirs();
		}
		
		saveDefaultConfig();
		loadMessages();
		
		database = new SQL();
				
		debug("Loading accounts...");
		MoneyData.loadAccounts();
		
		getCommand("money").setExecutor(new MoneyCMD());
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		
		debug("Enabled. v" + getDescription().getVersion());
		Save save = new Save();
		Thread th = new Thread(save);
		th.start();
		setupVault();
	}
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[EC_Economy disabled]");
		saveOnDisable();
	}
	
	public void debug(String msg){
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EC_Economy] " + msg);
	}
	public PMoney getPMoney(String player){
		if (accounts.containsKey(player)){
			return accounts.get(player);
		}
		return null;
	}
	public String format(double value){
		return Formater.format(value);
	}
	public List<PMoney> getTop(){
		
		List<PMoney> moneys = new ArrayList<>();
		for (String a : accounts.keySet()){
			moneys.add(accounts.get(a));
		}
		List<PMoney> convert = new ArrayList<>();
		for (PMoney pms : moneys){
			convert.add(pms);
		}
		Collections.sort(convert, new Comparator<PMoney>() {
				
			@Override
			public int compare(PMoney pt1, PMoney pt2){
				
				Float f1 = (float)pt1.getMoney();
				Float f2 = (float)pt2.getMoney();
				
				return f2.compareTo(f1);
				
			}
			
		});
	//	Collections.reverse(convert);
		if (convert.size() > 5){
			convert = convert.subList(0, 5);
		}
		return convert;
			
	}
	public void setupVault(){
		vault = new VaultHandler();
		getServer().getServicesManager().register(Economy.class, vault, this, ServicePriority.Highest);
		debug("Vault support activated.");
	}
	
	public void loadMessages() {
		cm = new ConfigManager();
		cm.setup();
		new Messages(this);
	}
	
	public void saveOnDisable() {
		try {
			Connection c = database.getNewConnection();
			//Statement stmt = c.createStatement();
			//int i = 0;
			for (String p : accounts.keySet()){
				
				PMoney pm = getPMoney(p);
				String cmd= "UPDATE Money SET Money='" + pm.getMoney() + "' WHERE Player='" + pm.getPlayerName() + "'";
				PreparedStatement stmt = c.prepareStatement(cmd);
				if (value_modific.containsKey(pm)){
					if (value_modific.get(pm) != pm.getMoney()){
						stmt.executeUpdate();
						value_modific.put(pm, pm.getMoney());
						//i++;
					}
				}
			}
			//debug("Salvas " + i + " contas, desabilitando..");
			c.close();
			//stmt.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
