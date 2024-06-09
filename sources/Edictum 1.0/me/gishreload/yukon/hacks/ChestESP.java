package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;


public class ChestESP extends Module{
	public ChestESP() {
		super("ChestESP", 0, Category.RENDER);
	}

	private int maxChests = 1000;
	public boolean shouldInform = true;

	@Override
	public void onEnable()
	{
		shouldInform = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eChestESP \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eChestESP \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	@Override
	public void onRender()
	{
		if(this.isToggled()){
			for(Object o : mc.theWorld.loadedTileEntityList){
			if(o instanceof TileEntityChest){
			RenderUtils.blockESPBox(((TileEntityChest)o).getPos());
			}else if(o instanceof TileEntityEnderChest){
			RenderUtils.blockESPBox2(((TileEntityEnderChest)o).getPos());
			}
			}
		}
	}
}


