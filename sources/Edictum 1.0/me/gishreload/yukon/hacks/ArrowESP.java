package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;


public class ArrowESP extends Module{
	public ArrowESP() {
		super("ArrowESP", 0, Category.RENDER);
	}

	@Override
	public void onEnable()
	{
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eArrowESP \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eArrowESP \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	@Override
	public void onRender()
	{
		if(this.isToggled()){
			for(Object o : mc.theWorld.loadedEntityList){
			if(o instanceof EntityArrow){
				RenderUtils.entityESPBox((EntityArrow)o, 5);     
			}
			}
		}
	}
}


