package br.com.explorecraft.ec_economy.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.explorecraft.ec_economy.Main;
import br.com.explorecraft.ec_economy.PMoney;
import br.com.explorecraft.ec_economy.msgs.MsgStr;

public class MoneyCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label,
			String[] args) {
		Player p = null;
		if (sender instanceof Player){
			p = (Player) sender;
		}
		
		if (args.length == 0){
			// Saldo
			if (p != null){
				PMoney pm = Main.plugin.getPMoney(p.getName());
				if (pm == null){
					p.sendMessage(MsgStr.accdontexists);
				} else {
					p.sendMessage(MsgStr.yourbalance + MsgStr.currency + "§f" + Main.plugin.format(Main.plugin.getEconomy().getBalance(p)));
				}
			} else {
				sendHelp(sender);
			}
			return true;
		}
		if (args.length == 1){ // Help and saldo de outros
			if (args[0].equalsIgnoreCase("help")){
				if (p == null){
					sendHelp(sender);
				} else {
					if (!p.hasPermission("economy.admin"))
						sendPHelp(p);
					else {
						sendHelp(sender);
					}
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("top")){
				sender.sendMessage("");
				sender.sendMessage("§e§l===============" + MsgStr.moneytop + "===============");
				//sender.sendMessage("");
				//sender.sendMessage("  §7(Esta lista é atualizada a cada 5 minutos)");
				sender.sendMessage("");
				List<PMoney> pm = Main.tops;
				for (int a = 0; a < pm.size(); a++){
					PMoney b = pm.get(a);
					sender.sendMessage("  §6" +(a + 1) + ". §7" + b.getPlayer() + " - §2" + MsgStr.currency + "§f" + Main.plugin.format(b.getMoney()));
				}
				sender.sendMessage("");
				return true;
			}
			String player = args[0];
			boolean achou = false;
			for (String pm : Main.plugin.accounts.keySet()){
				if (pm.equalsIgnoreCase(player)){
					PMoney mb = Main.plugin.getPMoney(pm);
					sender.sendMessage(MsgStr.otherbalance + pm + " §2" + MsgStr.currency + "§f" + Main.plugin.format(mb.getMoney()));
					achou = true;
					break;
				}
			}
			if (!achou){
				sender.sendMessage(MsgStr.accdontexists);
			}
			return true;
		}
		
		if (args.length == 2){
			if (args[0].equalsIgnoreCase("delete")){
				if (p != null){
					if (!p.hasPermission("economy.admin")){
						p.sendMessage(MsgStr.donthavepermission);
						return true;
					}
				}
				String player = args[1];
				if (Main.plugin.getPMoney(player) != null){
					PMoney pm = Main.plugin.getPMoney(player);
					pm.setMoney(0);
					sender.sendMessage("§f"+  pm.getPlayer() + MsgStr.accreset);
				} else {
					sender.sendMessage(MsgStr.accdontexists);
				}
				return true;
			}
			sender.sendMessage(MsgStr.invalidargs);
		}
		
		if (args.length == 3){
			if (args[0].equalsIgnoreCase("add")){
				if (p != null){
					if (!p.hasPermission("economy.admin")){
						p.sendMessage(MsgStr.donthavepermission);
						return true;
					}
				}
				String player = args[1];
				if (Main.plugin.getPMoney(player) != null){
					PMoney pm = Main.plugin.getPMoney(player);
					String valor = args[2];
					if (isDouble(valor)){
						pm.setMoney(pm.getMoney() + Double.valueOf(valor));
						sender.sendMessage(MsgStr.MAdd + MsgStr.currency + "§f" + Main.plugin.format(Double.valueOf(valor)) + MsgStr.to + pm.getPlayer());
					} else {
						sender.sendMessage(MsgStr.InvValue);
					}
				} else {
					sender.sendMessage(MsgStr.accdontexists);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("set")){
				if (p != null){
					if (!p.hasPermission("economy.admin")){
						p.sendMessage(MsgStr.donthavepermission);
						return true;
					}
				}
				String player = args[1];
				if (Main.plugin.getPMoney(player) != null){
					PMoney pm = Main.plugin.getPMoney(player);
					String valor = args[2];
					if (isDouble(valor)){
						pm.setMoney(Double.valueOf(valor));
						sender.sendMessage(MsgStr.MSet + MsgStr.currency + "§f" + Main.plugin.format(Double.valueOf(valor)) + MsgStr.to + pm.getPlayer());
					} else {
						sender.sendMessage(MsgStr.InvValue);
					}
				} else {
					sender.sendMessage(MsgStr.accdontexists);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("enviar") || args[0].equalsIgnoreCase("pay")){
				if (p != null){
					String player = args[1];
					if (player.equalsIgnoreCase(p.getName())){
						p.sendMessage(MsgStr.yourself);
						return true;
					}
					String valor = args[2];
					Player o = Bukkit.getPlayer(player);
					
					if (o == null){
						p.sendMessage(MsgStr.accdontexists);
						return true;
					}
					
					PMoney pm = Main.plugin.getPMoney(p.getName());
					PMoney om = Main.plugin.getPMoney(player);
					
					if (pm == null || om == null){
						p.sendMessage(MsgStr.accdontexists);
					} else {
						if (isDouble(valor)){
							double value = Double.valueOf(valor);
							if (pm.getMoney() >= value){
								pm.setMoney(pm.getMoney() - value);
								om.setMoney(om.getMoney() + value);
								p.sendMessage(MsgStr.Smoney + MsgStr.currency + "§f" + Main.plugin.format(value) + MsgStr.to + om.getPlayer());
								o.sendMessage(MsgStr.Rmoney + MsgStr.currency + "§f" + Main.plugin.format(value) + MsgStr.to + pm.getPlayer());
							} else {
								p.sendMessage(MsgStr.donthave + MsgStr.currency + "§f" + Main.plugin.format(value) + MsgStr.tosend + MsgStr.to + om.getPlayer());
							}
						} else {
							p.sendMessage(MsgStr.InvValue);
						}
					}
				} else {
					sender.sendMessage(MsgStr.accdontexists);
				}
				return true;
			}
			sender.sendMessage(MsgStr.invalidargs);
			
		}
		//sender.sendMessage(MsgStr.invalidargs);
		return false;
	}
	public void sendHelp(CommandSender sender){
		sender.sendMessage("§b§lEC_Economy - Help");
		sender.sendMessage("§6 /money help §7§o: Show Commands" ); //ok
		sender.sendMessage("§6 /money <player> §7§o: Show others players balance"); //ok
		sender.sendMessage("§6 /money add <player> <amount> §7§o: Add money for players"); //ok
		sender.sendMessage("§6 /money delete <player> §7§o: Reset Player account");
		sender.sendMessage("§6 /money set <player> <amount> §7§o: Set Player's Balance"); //ok
		sender.sendMessage("§6 /money pay <player> <amount> §7§o: Sned money for other player"); //ok
	}
	public void sendPHelp(Player p){
		p.sendMessage("§b§lEC_Economy  - Help");
		p.sendMessage(" §6/money help " + MsgStr.hlpshowcmds);
		p.sendMessage(" §6/money <player> " + MsgStr.hlpothbalance );
		p.sendMessage(" §6/money pay <player> <amount> "+ MsgStr.hlppaymoney );
	}
	public boolean isDouble(String valor){
		try {
			double d = Double.valueOf(valor);
			if (d <= 0){
				return false;
			}
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
}
