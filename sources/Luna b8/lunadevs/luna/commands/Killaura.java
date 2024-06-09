package lunadevs.luna.commands;

import com.zCore.Core.zCore;

import lunadevs.luna.command.Command;

public class Killaura extends Command{


	
	@Override
	public String getAlias() {
		return "killaura";
	}

	@Override
	public String getDescription() {
		return "Changes killaura settings";
	}

	@Override
	public String getSyntax() {
		return "-killaura";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("switch")){
			    lunadevs.luna.module.combat.Killaura.mode=0;
				zCore.addChatMessageP("Killaura mode set to: Switch");

		}
		if(args[0].equalsIgnoreCase("tick")){
			lunadevs.luna.module.combat.Killaura.mode=1;
			zCore.addChatMessageP("Killaura mode set to: Tick");
		}
		if(args[0].equalsIgnoreCase("block")){
		      if (args[1].equalsIgnoreCase("true"))
		      {
				  lunadevs.luna.module.combat.Killaura.block = true;
		        zCore.addChatMessageP("KillauraBlock: True");
		      }
		      if (args[1].equalsIgnoreCase("false"))
		      {
				  lunadevs.luna.module.combat.Killaura.block = false;
		        zCore.addChatMessageP("KillauraBlock: False");
		      }
		}
		if(args[0].equalsIgnoreCase("friend")){
		      if (args[1].equalsIgnoreCase("true"))
		      {
				  lunadevs.luna.module.combat.Killaura.friend = true;
		        zCore.addChatMessageP("KillauraFriend: True");
		      }
		      if (args[1].equalsIgnoreCase("false"))
		      {
				  lunadevs.luna.module.combat.Killaura.friend = false;
		        zCore.addChatMessageP("KillauraFriend: False");
		      }
		}
		if(args[0].equalsIgnoreCase("range")){

		      lunadevs.luna.module.combat.Killaura.range = Float.parseFloat(args[1]);
		      zCore.addChatMessageP("KillauraRange: " + lunadevs.luna.module.combat.Killaura.range);
		}
		if(args[0].equalsIgnoreCase("speed")){
		      lunadevs.luna.module.combat.Killaura.speed = Float.parseFloat(args[1]);
		      zCore.addChatMessageP("KillauraSpeed: " + lunadevs.luna.module.combat.Killaura.speed);
		}
		if(args[0].equalsIgnoreCase("lock")){
		      if (args[1].equalsIgnoreCase("true"))
		      {
				  lunadevs.luna.module.combat.Killaura.lock = true;
		        zCore.addChatMessageP("KillauraLock: True");
		      }
		      if (args[1].equalsIgnoreCase("false"))
		      {
				  lunadevs.luna.module.combat.Killaura.lock = false;
		        zCore.addChatMessageP("KillauraLock: False");
		      }
		}
	}

}