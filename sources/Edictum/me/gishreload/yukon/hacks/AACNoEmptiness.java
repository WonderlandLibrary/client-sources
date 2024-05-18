package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;


public class AACNoEmptiness extends Module{
	public AACNoEmptiness() {
		super("NoEmptiness", 0, Category.PLAYER);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eNoEmptiness \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eNoEmptiness \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){		
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
		            this.mc.thePlayer.motionY = 0.4D;
		          } else if (this.mc.gameSettings.keyBindSneak.pressed) {
		            this.mc.thePlayer.motionY = -0.4D;
		          } else {
		            this.mc.thePlayer.motionY = -0.05D;
		          }
		        }
		      }
		}
	super.onUpdate();
	}
}