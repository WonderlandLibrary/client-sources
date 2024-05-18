package me.gishreload.yukon.hacks;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;


public class KillAura extends Module{
	long lastMS;
    long currentMS;
	public KillAura() {
		super("KillAura", 0, Category.COMBAT);
	}
	
	public void onEnable(){
		Meanings.ka = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eKillAura \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Meanings.ka = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eKillAura \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(!Meanings.aac){
			Meanings.noaac = true;
			}
			if(Meanings.noaac){
			this.currentMS = System.nanoTime() / 900000L;
			float f1 = this.mc.thePlayer.getCooledAttackStrength(0.0F);
		    if (f1 >= 1.0F && Meanings.killaura10 || this.hasDelayRun(125) && !Meanings.killaura10){
	            Iterator i$ = mc.theWorld.loadedEntityList.iterator();
	            while (i$.hasNext())
	            {
	                Object o = i$.next();
	                if (o instanceof EntityPlayer)
	                {
	                    EntityPlayer e = (EntityPlayer)o;
	                        if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead)
	                        {
	                            mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	                            mc.playerController.attackEntity(mc.thePlayer, e);
	                            this.lastMS = System.nanoTime() / 900000L;
	                            break;
	                        }
	                
	                    else if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead && mc.thePlayer.canEntityBeSeen(e))
	                    {
	                        mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	                        mc.playerController.attackEntity(mc.thePlayer, e);
	                        this.lastMS = System.nanoTime() / 900000L;
	                        break;
	                    }
	                }
	                }
	                
		    }
	            }
	        }
			
						if(Meanings.aac){
							Meanings.noaac = false;
	                    	this.currentMS = System.nanoTime() / 900000L;
	            			float f1 = this.mc.thePlayer.getCooledAttackStrength(0.0F);
	            		    if (f1 >= 1.0F && Meanings.killaura10 || this.hasDelayRun(125) && !Meanings.killaura10){
	            	            Iterator i$ = mc.theWorld.loadedEntityList.iterator();
	            	            while (i$.hasNext())
	            	            {
	            	                Object o = i$.next();
	            	                if (o instanceof EntityPlayer)
	            	                {
	            	                    EntityPlayer e = (EntityPlayer)o;
	            	                    if(!(e instanceof EntityPlayerSP) && !e.isInvisible()){
	            	                    	if(Meanings.aacauraarrow){
	            	                    	e.setArrowCountInEntity(1);
	            	                    }
	            	                    	if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead && e.getArrowCountInEntity()==1 && !e.isInvisible())
	            	                    	{
	            	                    		mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	            	                    		mc.playerController.attackEntity(mc.thePlayer, e);
	            	                    		this.lastMS = System.nanoTime() / 900000L;
	            	                    		break;
	            	                    	}
	            	                    	else if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead && mc.thePlayer.canEntityBeSeen(e) && e.getArrowCountInEntity()==1 && !e.isInvisible())
	            	                    	{
	            	                    		mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
	            	                    		mc.playerController.attackEntity(mc.thePlayer, e);
	            	                    		this.lastMS = System.nanoTime() / 900000L;
	            	                    		break;
	            	                    	}
	                    }
	            	                }
	            	                }
	            	        }
		}
	super.onUpdate();
	            }
	
public boolean hasDelayRun(long time)
{
    return this.currentMS - this.lastMS >= time;
}
}
