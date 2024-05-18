package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;


public class MobESP extends Module{
	public MobESP() {
		super("MobESP", 0, Category.RENDER);
	}

	
	@Override
	public void onRender()
	{
		if(this.isToggled()){
			for(Object entity : mc.theWorld.loadedEntityList)
				if(entity instanceof EntityLiving)
					RenderUtils.entityESPBox((Entity)entity, 0);
						                      
		}
	}
}