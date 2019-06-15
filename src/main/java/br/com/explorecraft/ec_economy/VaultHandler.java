package br.com.explorecraft.ec_economy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.OfflinePlayer;

import br.com.explorecraft.ec_economy.msgs.MsgStr;
import br.com.explorecraft.ec_economy.utils.MoneyData;

public class VaultHandler implements Economy{

	@Override
	public EconomyResponse bankBalance(String arg0) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n&o possui suporte para este tipo de acao.");
	}

	@Override
	public boolean createPlayerAccount(String arg0) {
		boolean sucess = false;
		try {
			MoneyData.createAccount(arg0);
			sucess = true;
		} catch (SQLException e) {
	//		e.printStackTrace();
		}
		return sucess;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {
		boolean sucess = false;
		try {
			MoneyData.createAccount(arg0.getName());
			sucess = true;
		} catch (SQLException e) {
	//		e.printStackTrace();
		}
		return sucess;
	}

	@Override
	public boolean createPlayerAccount(String player, String arg1) {
		boolean sucess = false;
		try {
			MoneyData.createAccount(player);
			sucess = true;
		} catch (SQLException e) {
	//		e.printStackTrace();
		}
		return sucess;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {
		boolean sucess = false;
		try {
			MoneyData.createAccount(arg0.getName());
			sucess = true;
		} catch (SQLException e) {
	//		e.printStackTrace();
		}
		return sucess;
	}

	@Override
	public String currencyNamePlural() {
		return "Money";
	}

	@Override
	public String currencyNameSingular() {
		return "Money";
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n�o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse depositPlayer(String player, double valor) {
		if (valor > 0){
			PMoney p = Main.plugin.getPMoney(player);
			if (p == null){
				return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account not exist");
			}
			p.setMoney(p.getMoney() + valor);
			return new EconomyResponse(0, p.getMoney(), ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, Main.plugin.cm.getConfigMsgs().getString("Messages.Negative Value"));
		}
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double valor) {
		return depositPlayer(player.getName(), valor);
	}

	@Override
	public EconomyResponse depositPlayer(String arg0, String arg1, double arg2) {
		return depositPlayer(arg0, arg2);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String arg1,
			double valor) {
		return depositPlayer(player.getName(), valor);
	}

	@Override
	public String format(double valor) {
		return Main.plugin.format(valor);
	}

	@Override
	public int fractionalDigits() {
		return -1;
	}

	@Override
	public double getBalance(String player) {
		if (Main.plugin.getPMoney(player) == null){
			return 0.0D;
		} else {
			return Main.plugin.getPMoney(player).getMoney();
		}
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return getBalance(player.getName());
	}

	@Override
	public double getBalance(String player, String arg1) {
		return getBalance(player);
	}

	@Override
	public double getBalance(OfflinePlayer player, String arg1) {
		return getBalance(player.getName());
	}

	@Override
	public List<String> getBanks() {
		
		return new ArrayList<String>();
		
	}

	@Override
	public String getName() {
		return "SmartEconomy";
	}

	@Override
	public boolean has(String player, double valor) {
		
		PMoney p = Main.plugin.getPMoney(player);
		if (p != null){
			if (valor >= 0){
				return p.getMoney() >= valor;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean has(OfflinePlayer player, double valor) {
		return has(player.getName(), valor);
	}

	@Override
	public boolean has(String player, String arg1, double valor) {
		return has(player, valor);
	}

	@Override
	public boolean has(OfflinePlayer player, String arg1, double valor) {
		return has(player.getName(), valor);
	}

	@Override
	public boolean hasAccount(String player) {
		return Main.plugin.getPMoney(player) != null;
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0) {
		return hasAccount(arg0.getName());
	}

	@Override
	public boolean hasAccount(String arg0, String arg1) {
		return hasAccount(arg0);
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0, String arg1) {
		return hasAccount(arg0.getName());
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n�o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n�o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n�o possui suporte para este tipo de acao.");
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0.0, 0.0, ResponseType.NOT_IMPLEMENTED, "Este plugin n�o possui suporte para este tipo de acao.");
	}

	@Override
	public boolean isEnabled() {
		return Main.plugin.isEnabled();
	}

	@Override
	public EconomyResponse withdrawPlayer(String player, double valor) {
		
		PMoney p = Main.plugin.getPMoney(player);
		if (p == null){
			return new EconomyResponse(0, 0, ResponseType.FAILURE, MsgStr.accdontexists);
		}
		if (valor < 0){
			return new EconomyResponse(0, 0, ResponseType.FAILURE, MsgStr.negvalue);
		}
		p.setMoney(p.getMoney() - valor);
		return new EconomyResponse(0, p.getMoney(), ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double valor) {
		return withdrawPlayer(player.getName(), valor);
	}

	@Override
	public EconomyResponse withdrawPlayer(String player, String arg1, double valor) {
		return withdrawPlayer(player, valor);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String arg1,
			double valor) {
		return withdrawPlayer(player.getName(), valor);
	}
}
