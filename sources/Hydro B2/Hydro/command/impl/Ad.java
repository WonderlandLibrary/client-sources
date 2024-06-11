package Hydro.command.impl;

import java.util.ArrayList;
import java.util.List;

import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Ad implements CommandExecutor {
	
	ArrayList<String> ads = new ArrayList<>();
	java.util.Random random = new java.util.Random();
	
	
	public Ad() {
		ads.add("you aren't based until you use Hydro client");
		ads.add("Get Hydro client at foxxy.lol/hydro");
	}

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		int randomInt = random.nextInt(ads.size());
		
		ChatUtils.sendMessage(ads.get(randomInt));
	}

}
