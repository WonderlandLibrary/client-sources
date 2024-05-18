package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;


public class AACJesus extends Module{
	public AACJesus() {
		super("Jesus", 0, Category.PLAYER);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eJesus \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eJesus \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
            if (Minecraft.thePlayer.isInWater()||Minecraft.thePlayer.isInLava()) {
                Minecraft.thePlayer.motionY = 5.9;
            }
            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
            thePlayer.jumpMovementFactor *= 0.9f;
            if (Minecraft.thePlayer.isInWater()||Minecraft.thePlayer.isInLava()) {
                Minecraft.thePlayer.motionY = 0.2;
		}
	super.onUpdate();
	}
}
}



