package br.com.explorecraft.ec_economy.msgs;

import br.com.explorecraft.ec_economy.Main;

public class Messages {
	
	Main plugin;
	
	public Messages(Main plugin) {
		this.plugin = plugin;
		
		Main.plugin.cm.getConfigMsgs().options().header("=================================== EC_Economy Messages.yml ===========================================\n"
				+ "\n"
				+ "Author: TheExplorerBR\n"
				+ "Here you Can Translate the Messages of this Plugin");
		
		if(!Main.plugin.cm.getConfigMsgs().contains("Messages")){
			Main.plugin.cm.getConfigMsgs().set("Messages.Negative Value", "&cNegative Value!" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Account not exist", "&cAccount not exist!" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Internal Error", "&cAn internal error occurred!" );
			Main.plugin.cm.getConfigMsgs().set("Messages.No Permission", "&cYou do not have permission.!" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Acc Reset", " &ehas been reset.!" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Invalid Args", "&eArgument invalid, use '/money help'" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Your Balance", "&eYour Balance: &2" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Others Balance", "&eBalance of &f " );
			Main.plugin.cm.getConfigMsgs().set("Messages.Money Top", " &e&lMoney Top " );
			Main.plugin.cm.getConfigMsgs().set("Messages.To", "&e to &f" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Money Add", "&eYou add &2 " );
			Main.plugin.cm.getConfigMsgs().set("Messages.Money Set", "&eYou set &2 " );
			Main.plugin.cm.getConfigMsgs().set("Messages.Invalid Value", "&eInvalid Value" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Your Self", "&eYou can not send money to yourself" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Sent Money", "&eYou sent &2" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Received Money", "&eYou has received &2" );
			Main.plugin.cm.getConfigMsgs().set("Messages.To Send", " &eto send" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Dont Have", "&eYou do not have &2" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Help1", "&7&o: Show Commands" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Help2", "&7&o: Show others player balance" );
			Main.plugin.cm.getConfigMsgs().set("Messages.Help3", "&7&o: Send money for players or pay things" );
			
			
			Main.plugin.cm.saveConfigMsgs();
		}
	}

}
