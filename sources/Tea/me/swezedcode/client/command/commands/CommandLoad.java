package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Player.MagicCarpet;
import me.swezedcode.client.module.modules.World.AntiBot;

public class CommandLoad extends Command {

	private final KillAura aura = new KillAura();
	private final MagicCarpet scaffold = new MagicCarpet();
	private final AntiBot antibot = new AntiBot();
	
	@Override
	public void executeMsg(String[] args) {
		if(args.length == 0) {
			error("Invalid usage: -load <hypixel/mc-central/cubecraft/ncp/mineplex/safe>");
		}
		if(args[0].equalsIgnoreCase("hypixel")) {
			this.aura.reach = 6;
			this.aura.delay = 17;
			this.aura.autoblock.setValue(true);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Switch";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(false);
			this.antibot.aac.setValue(false);
			this.antibot.watchdog.setValue(true);
			this.scaffold.slowplacing.setValue(false);
			this.scaffold.timerTower.setValue(true);
			this.scaffold.needblock.setValue(false);
			this.scaffold.safewalk.setValue(true);
			this.scaffold.noswing.setValue(true);
			msg("§cSuccessfully loaded hypixel settings.");
		}else if(args[0].equalsIgnoreCase("mc-central")) {
			this.aura.reach = (4.25F);
			this.aura.delay = (16);
			this.aura.autoblock.setValue(true);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Switch";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(false);
			msg("§cSuccessfully loaded mc-central settings.");
		}else if(args[0].equalsIgnoreCase("cubecraft")) {
			this.aura.reach = 4.25F;
			this.aura.delay = (12);
			this.aura.autoblock.setValue(false);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Switch";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(false);
			msg("§cSuccessfully loaded cubecraft settings.");
		}else if(args[0].equalsIgnoreCase("ncp")) {
			this.aura.reach = 4.4F;
			this.aura.delay = 12;
			this.aura.autoblock.setValue(true);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Switch";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(false);
		}else if(args[0].equalsIgnoreCase("mineplex")) {
			this.aura.reach = 4F;
			this.aura.delay = 14;
			this.aura.autoblock.setValue(false);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Switch";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(false);
			this.scaffold.timerTower.setValue(false);
			this.scaffold.needblock.setValue(true);
			this.scaffold.safewalk.setValue(true);
			this.scaffold.noswing.setValue(false);
			this.antibot.aac.setValue(true);
			this.antibot.watchdog.setValue(false);
			msg("§cSuccessfully loaded mineplex settings.");
		}else if(args[0].equalsIgnoreCase("safe")) {
			this.aura.reach = 3.7F;
			this.aura.delay = 10;
			this.aura.autoblock.setValue(false);
			this.aura.jumpcrit.setValue(false);
			this.aura.mode = "Advanced";
			this.aura.teams.setValue(true);
			this.aura.randomDelay.setValue(true);
			msg("§cSuccessfully loaded safe settings.");
		}
	}

	@Override
	public String getName() {
		return "load";
	}

}
