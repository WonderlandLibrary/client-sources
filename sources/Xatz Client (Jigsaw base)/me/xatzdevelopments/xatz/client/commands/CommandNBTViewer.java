package me.xatzdevelopments.xatz.client.commands;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import me.xatzdevelopments.xatz.client.main.Xatz;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;

public class CommandNBTViewer extends Command {

	@Override
	public void run(String[] commands) {
		ItemStack b = mc.thePlayer.getCurrentEquippedItem();
				
		if(b.getTagCompound() == null){
			Xatz.chatMessage("This item has no nbttags!");
		}
		
		
		else{
		Xatz.chatMessage(b.getTagCompound().toString().replaceAll("§", "&"));
		}

		
		
		
		
	}
	@Override
	public String getActivator() {
		return ".nbtviewer";
	}

	@Override
	public String getSyntax() {
		return ".nbtviewer";
	}

	@Override
	public String getDesc() {
		return "Shows u all the nbttags on a item.";
	}
}
