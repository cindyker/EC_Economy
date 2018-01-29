package br.com.explorecraft.ec_economy.msgs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import br.com.explorecraft.ec_economy.Main;





public class ConfigManager {
	
	
	
	public FileConfiguration configmsg;	
	
	public File messagesfile;
	
	


	public void setup() {
		if(!Main.plugin.getDataFolder().exists()) {
			Main.plugin.getDataFolder().mkdir();
		}
		
		
	messagesfile = new File(Main.plugin.getDataFolder(), "messages.yml");
		
		
		
		if (!messagesfile.exists()) {
			try {
				messagesfile.createNewFile();
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "The messages.yml file has been created!");
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not create the messages.yml file!");
			}
		}
			
		
		configmsg = YamlConfiguration.loadConfiguration(messagesfile);
		
		
	}


	public FileConfiguration getConfigMsgs() {
		return configmsg;
	}


	public void saveConfigMsgs() {
		try {
			configmsg.save(messagesfile);
			Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "The messages.yml file has been saved!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not save the messages.yml file!");
		}
	}
	
	  public void reloadConfigMsgs() {
		  configmsg = YamlConfiguration.loadConfiguration(messagesfile);
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "The messages.yml file has been reloaded!");
	    }
    
}    
   