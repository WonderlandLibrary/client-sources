package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Timer;


public class AACSpeed extends Module{
	public AACSpeed() {
		super("SpeedHack", 0, Category.PLAYER);
	}
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eSpeedHack \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eSpeedHack \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(Meanings.ncp){
			Minecraft.thePlayer.setSprinting(true);
            if (Minecraft.thePlayer.onGround && Minecraft.thePlayer.moveForward > 0.17) {
                Minecraft.thePlayer.jump();
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + Minecraft.thePlayer.motionX * 0.1, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Minecraft.thePlayer.motionZ * 0.1);
                final Timer timer = this.mc.timer;
                Timer.timerSpeed = 1.11f;
                Minecraft.thePlayer.distanceWalkedOnStepModified = 44.0f;
                
            }
            else {
                Minecraft.thePlayer.distanceWalkedOnStepModified = 44.0f;
                Minecraft.thePlayer.cameraPitch = 0.0f;
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + Minecraft.thePlayer.motionX * 0.3, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Minecraft.thePlayer.motionZ * 0.3);
                Minecraft.thePlayer.motionY = -1.0;
                final Timer timer2 = this.mc.timer;
                Timer.timerSpeed = 1.0f;
            }
		}
		else if(Meanings.aac){
		 if ((Minecraft.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) && !mc.thePlayer.isCollidedHorizontally) {
            if (Minecraft.thePlayer.fallDistance > 3.994) {
                return;
            }
            if (Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.isOnLadder()) {
                return;
            }
            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
            thePlayer.posY -= 0.3993000090122223;
            Minecraft.thePlayer.motionY = -1000.0;
            Minecraft.thePlayer.cameraPitch = 0.3f;
            Minecraft.thePlayer.distanceWalkedModified = 44.0f;
            Timer.timerSpeed = 1.0f;
        }
        if (Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.isOnLadder()) {
            return;
        }
        if (Minecraft.thePlayer.onGround && (Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f) && !Minecraft.thePlayer.isCollidedHorizontally) {
            final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
            thePlayer2.posY += 0.3993000090122223;
            Minecraft.thePlayer.motionY = 0.3993000090122223;
            Minecraft.thePlayer.distanceWalkedOnStepModified = 44.0f;
            final EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
            thePlayer3.motionX *= 1.546000033378601;
            final EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
            thePlayer4.motionZ *= 1.546000033378601;
            Minecraft.thePlayer.cameraPitch = 0.0f;
            Timer.timerSpeed = 1.199f;
	}  
	super.onUpdate();
}	
		}
	}
}



