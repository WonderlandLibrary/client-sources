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


public class RightClick extends Module{
	public  RightClick() {
		super("RightClick", 0, Category.OTHER);
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed){
				float rightClickCount = mc.rightClickCount;
				for(int num = 0; num < rightClickCount ; num++)
					Minecraft.getMinecraft().rightClickMouse();
			}
		}
	super.onUpdate();
	}
}



