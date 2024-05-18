package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import me.gishreload.yukon.*;
import me.gishreload.yukon.events.Event;
import me.gishreload.yukon.events.EventListener;
import me.gishreload.yukon.events.HealthUpdateEvent;
import me.gishreload.yukon.events.MoveEvent;
import me.gishreload.yukon.events.PacketEvent;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.MoveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class Flight extends Module{
	
	public Flight() {
		super("Flight", Keyboard.KEY_F, Category.PLAYER);
	}
	int delay;
	double offset2 = 0.0625D;
	public double aacYSet = 0.8;
	public void onEnable(){
		if(Meanings.aac)
        if (mc.thePlayer.onGround) {
            for (int i3 = 0; (double) i3 <= (3.0D + 0.5) / offset2; ++i3) {
                mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX,
                        mc.thePlayer.posY + offset2, mc.thePlayer.posZ, false));
                mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX, mc.thePlayer.posY,
                        mc.thePlayer.posZ, (double) i3 == (3.0D + 0.5D) / offset2));
            }
        }
		if(Meanings.glidedamage){
		if (this.mc.thePlayer != null) {
            int i = 0;
            while ((double)i < 80.0 + 40.0 * (mc.glidedamage - 0.5)) {
                this.mc.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1D, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                ++i;
            }
            this.mc.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
        }
		}
		Meanings.fl = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eFlight \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	public void onDisable(){
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.onGround = false;
		Meanings.fl = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eFlight \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
		super.onDisable();
	}
	
	@Override
	public void onUpdate(){
		if(this.isToggled()){
			if(!Meanings.aac && !Meanings.ncp){
			Meanings.noaac = true;
			}
			if(Meanings.noaac){
			mc.thePlayer.capabilities.isFlying = true;
			
			if(mc.gameSettings.keyBindJump.isPressed()){
				mc.thePlayer.motionY += 0.2;
			}
			
			if(mc.gameSettings.keyBindSneak.isPressed()){
				mc.thePlayer.motionY -= 0.2;
			}
			
			if(mc.gameSettings.keyBindForward.isPressed()){
				mc.thePlayer.capabilities.setFlySpeed(mc.flightspeed);
			}
			}
		if(Meanings.ncp){
			mc.thePlayer.capabilities.isFlying = false;
			mc.thePlayer.motionY = -0.01;
	        if (this.mc.gameSettings.keyBindJump.pressed) {
	            mc.thePlayer.motionY = 0.3;
	        }
	        if (this.mc.gameSettings.keyBindSneak.pressed) {
	            mc.thePlayer.motionY = -0.3;
	        }
	        ++this.delay;
	        if (this.delay > 45) {
	            mc.thePlayer.onGround = true;
	            this.delay = 0;
			}
		}
		if(Meanings.aac){
			/*
			 if (event instanceof EventPacketRecieve) {
	                if (mc.thePlayer.fallDistance > 4.0f && ((EventPacketRecieve)event).getPacket() instanceof CPacketPlayer) {
	                    ((CPacketPlayer)((EventPacketRecieve)event).getPacket()).onGround = true;
	                    mc.thePlayer.fallDistance = 0.0f;
	                }
	            }
	            else if (event instanceof HealthUpdateEvent) {
	                if (((HealthUpdateEvent)event).getEntity() instanceof EntityPlayerSP && ((HealthUpdateEvent)event).getHealthDiffrence() < 0.0f) {
	                    mc.thePlayer.motionY = this.aacYSet;
	                }
	            }
	            else if (event instanceof EventTick) {
	                if (!mc.thePlayer.onGround && mc.thePlayer.isInWater()||mc.thePlayer.isInLava()) {
	                    MoveUtil.setSpeed(0.25f);
	                }
	                if (mc.thePlayer.fallDistance > 0.0f && mc.thePlayer.fallDistance < 0.1) {}
	            }
	        }
	        */
			if ((!this.mc.thePlayer.onGround) && (this.mc.thePlayer.fallDistance > 1.0F))
		      {
		        int i = 0;
		        for (int j = 0; j <= 100; j++)
		        {
		          BlockPos localBlockPos = new BlockPos(Minecraft.getMinecraft().thePlayer.motionX, Minecraft.getMinecraft().thePlayer.motionY - j, Minecraft.getMinecraft().thePlayer.motionZ);
		          if (mc.theWorld.getBlockState(localBlockPos).getBlock() != Blocks.AIR) {
		            i = 1;
		          }
		        }
		        if (i == 0) {
		        	if (this.mc.gameSettings.keyBindJump.pressed) {
			            this.mc.thePlayer.motionY = 0.8D;
		        	double offset = 0.0625D;
		            if (!mc.thePlayer.onGround) {
		                for (int i2 = 0; (double) i2 <= (3.0D + 0.5) / offset; ++i2) {
		                    mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX,
		                            mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
		                    mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX, mc.thePlayer.posY,
		                            mc.thePlayer.posZ, (double) i2 == (3.0D + 0.5D) / offset));
		                }
		            }
		        	}
		        }
		        }
		      }
		}
        super.onUpdate();
		}
	}




