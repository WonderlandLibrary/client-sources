package me.gishreload.yukon.hacks;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;


public class Aimbot extends Module{
	long lastMS;
    long currentMS;
	public Aimbot() {
		super("Aimbot", 0, Category.COMBAT);
	}
	
	@Override
	public void onEnable() {
		Meanings.Aimbot = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAimbot \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	
	@Override
	public void onDisable() {
		Meanings.Aimbot = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAimbot \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(!Meanings.aac){
			Meanings.noaac = true;
			}
			if(Meanings.noaac){
		if(Meanings.Aimbot){
			this.currentMS = System.nanoTime() / 900000L;

	        if (this.hasDelayRun(133L))
	        {
	            Iterator i$ = mc.theWorld.loadedEntityList.iterator();

	            while (i$.hasNext())
	            {
	                Object o = i$.next();

	                if (o instanceof EntityPlayer)
	                {
	                    EntityPlayer e = (EntityPlayer)o;

	                    if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead && mc.thePlayer.canEntityBeSeen(e))
	                    {
	                        this.faceEntity(e);
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
		if(Meanings.Aimbot){	
		this.currentMS = System.nanoTime() / 900000L;

        if (this.hasDelayRun(133L))
        {
            Iterator i$ = mc.theWorld.loadedEntityList.iterator();

            while (i$.hasNext())
            {
                Object o = i$.next();

                if (o instanceof EntityPlayer)
                {
                    EntityPlayer e = (EntityPlayer)o;

                    if (!(e instanceof EntityPlayerSP) && mc.thePlayer.getDistanceToEntity(e) <= mc.hitrange && !e.isDead && mc.thePlayer.canEntityBeSeen(e)&& e.getArrowCountInEntity()==1 && !e.isInvisible())
                    {
                        this.faceEntity(e);
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
public static void faceEntity(Entity e)
{
    double x = e.posX - Minecraft.thePlayer.posX;
    double y = e.posY - Minecraft.thePlayer.posY;
    double z = e.posZ - Minecraft.thePlayer.posZ;
    double d1 = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - (e.posY + (double)e.getEyeHeight());
    double d3 = (double)MathHelper.sqrt_double(x * x + z * z);
    float f = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
    float f1 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
    Minecraft.thePlayer.setPositionAndRotation(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, f, -f1);
}

public boolean hasDelayRun(long time)
{
    return this.currentMS - this.lastMS >= time;
}
}
