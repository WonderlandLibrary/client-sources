package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import me.swezedcode.client.manager.managers.FriendManager;
import me.swezedcode.client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class CommandBanwave extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (args[0].equalsIgnoreCase("banwave")) {
			for (Object o : mc.theWorld.loadedEntityList) {
				if ((!(o instanceof EntityPlayerSP)) && ((o instanceof EntityPlayer))) {
					EntityLivingBase ent = (EntityLivingBase) o;
					if ((ent != null) && (ent.getName() != mc.thePlayer.getName())
							&& (!FriendManager.isFriend(ent.getName())) && ent != mc.thePlayer) {
						mc.thePlayer.sendChatMessage("/ban " + ent.getName() + " #TeaClient YT/SwezedCode");
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "banwave";
	}

}
