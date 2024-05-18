package me.swezedcode.client.command.commands;

import java.util.List;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.utils.console.ConsoleUtils;

public class CommandHelp extends Command {

	@Override
	public void executeMsg(String[] args) {
		ConsoleUtils.logChat("§7Avaible Commands:");
		ConsoleUtils.logChat("§7-help | A Basic Command For Showing Commands.");
		ConsoleUtils.logChat("§7-aura <delay/range> <value> | Options for killaura");
		ConsoleUtils.logChat("§7-friend <add/del> <player>");
		ConsoleUtils.logChat("§7-bind <add/del> <module> <keycode>");
		ConsoleUtils.logChat("§7-mods | Shows A List Of All Mods.");
		ConsoleUtils.logChat("§7-velocity kb/knockback <value> | Set velocity taken");
		ConsoleUtils.logChat("§7-nameprotect set <string> | Set you're string you want");
		ConsoleUtils.logChat("§7-music volume <floatvalue> | Set music volume");
		ConsoleUtils.logChat("§7-vclip <amount> | Go up or down by choosen percentage");
		ConsoleUtils.logChat("§7-banwave | Ban all the players on the server");
		ConsoleUtils.logChat("§7-banwaveip | Ban all the players on the server (including ip)");
		ConsoleUtils.logChat("§7-autoclicker cps <value> | Set the average cps");
		ConsoleUtils.logChat("§7-load <hypixel/mc-central/cubecraft/ncp/safe> | Choose best settings for servers");
		ConsoleUtils.logChat("§7-blur <on/off> | Changes if you want to have blur in inventory background.");
		ConsoleUtils.logChat("§7-");
	}

	@Override
	public String getName() {
		return "help";
	}
}
