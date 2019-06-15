package br.com.explorecraft.ec_economy.listeners;

import java.sql.SQLException;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.explorecraft.ec_economy.Main;
import br.com.explorecraft.ec_economy.msgs.MsgStr;
import br.com.explorecraft.ec_economy.utils.MoneyData;


public class PlayerJoin implements Listener{
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e){
		//if (Main.plugin.getPMoney(e.getPlayer().getName()) == null){
		if (Main.plugin.getPMoney(e.getPlayer().getUniqueId().toString()) == null){
			try {
				MoneyData.createAccount(e.getPlayer().getUniqueId().toString());
			} catch (SQLException e1) {
				e.getPlayer().kickPlayer(MsgStr.internalerror);
				e1.printStackTrace();
			}
		}
	}
}

