package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemNameTag;
import net.minecraft.network.play.client.CPacketPlayer;


public class LeftClick extends Module{
	public  LeftClick() {
		super("LeftClick", 0, Category.OTHER);
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed){
				float leftClickCount = mc.leftClickCount;
				for(int num = 0; num < leftClickCount ; num++)
					Minecraft.getMinecraft().clickMouse();
			}
		}
	super.onUpdate();
	}
}



