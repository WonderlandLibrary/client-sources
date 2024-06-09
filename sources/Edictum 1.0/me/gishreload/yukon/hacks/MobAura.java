package me.gishreload.yukon.hacks;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;


public class MobAura extends Module{
	private float hitrange = 4.6F;
	long lastMS;
    long currentMS;
	public MobAura() {
		super("MobAura", 0, Category.COMBAT);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eMobAura \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eMobAura \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			this.currentMS = System.nanoTime() / 900000L;

	        if (this.hasDelayRun(133L))
	        {
	            for (int i = 0; i < mc.theWorld.loadedEntityList.size(); ++i)
	            {
	                Entity e = (Entity)mc.theWorld.getLoadedEntityList().get(i);

	                    if (e != mc.thePlayer && !e.isDead && mc.thePlayer.getDistanceToEntity(e) < this.hitrange && e instanceof EntityLiving)
	                    {
	                        
	                        mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	                        mc.playerController.attackEntity(mc.thePlayer, e);
	                        this.lastMS = System.nanoTime() / 900000L;
	                        break;
	                    }
	                
	                else if (e != mc.thePlayer && !e.isDead && mc.thePlayer.canEntityBeSeen(e) && mc.thePlayer.getDistanceToEntity(e) < this.hitrange && e instanceof EntityLiving)
	                {
	                   
	                    mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	                    mc.playerController.attackEntity(mc.thePlayer, e);
	                    this.lastMS = System.nanoTime() / 900000L;
	                    break;
	                }
	            }
	        }
	super.onUpdate();
		}
	}

public boolean hasDelayRun(long time)
{
    return this.currentMS - this.lastMS >= time;
}
}



