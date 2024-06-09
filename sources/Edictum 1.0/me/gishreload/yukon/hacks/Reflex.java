package me.gishreload.yukon.hacks;

import java.util.EventListener;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import me.gishreload.yukon.*;
import me.gishreload.yukon.events.PacketEvent;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntity.S17PacketEntityLookMove;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;

public class Reflex extends Module implements EventListener{
	
	public Reflex() {
		super("Reflex", 0, Category.ANTICHEAT);
	}
	
	public void onEnable(){
		Meanings.reflex = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a79Обход Reflex активирован.");
	}

	public void onDisable(){
		Meanings.reflex = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74Обход Reflex деактивирован.");
		super.onDisable();
	}
	
	@EventTarget
    public void onUpdate(final PacketEvent e) {
		if(this.isToggled()){
    	if (e.getPacket() instanceof SPacketSpawnMob)
            e.setCancelled(true);
        if (e.getPacket() instanceof SPacketSpawnObject)
            e.setCancelled(true);
        if (e.getPacket() instanceof SPacketSpawnGlobalEntity)
            e.setCancelled(true);
        if (e.getPacket() instanceof S17PacketEntityLookMove) {
            S17PacketEntityLookMove entityLookMove = (S17PacketEntityLookMove) e.getPacket();
            Entity en = entityLookMove.getEntity(mc.theWorld);
            if (en.isInvisible()) {
                e.setCancelled(true);
                mc.theWorld.removeEntity(en);
            }
        }
		}
        }
        
}
