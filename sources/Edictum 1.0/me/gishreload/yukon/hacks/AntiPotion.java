package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;


public class AntiPotion extends Module{
	public AntiPotion() {
		super("AntiPotion", 0, Category.COMBAT);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAntiPotion \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAntiPotion \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()){
			EntityPlayerSP player = mc.thePlayer;
			if(!player.capabilities.isCreativeMode && player.onGround
				&& !player.getActivePotionEffects().isEmpty())
				if(player.isPotionActive(MobEffects.HUNGER)
					|| player.isPotionActive(MobEffects.SLOWNESS)
					|| player.isPotionActive(MobEffects.MINING_FATIGUE)
					|| player.isPotionActive(MobEffects.INSTANT_DAMAGE)
					|| player.isPotionActive(MobEffects.NAUSEA)
					|| player.isPotionActive(MobEffects.BLINDNESS)
					|| player.isPotionActive(MobEffects.WEAKNESS)
					|| player.isPotionActive(MobEffects.WITHER)
					|| player.isPotionActive(MobEffects.POISON))
					for(int i = 0; i < 1000; i++)
						player.connection.sendPacket(new CPacketPlayer());
		}
	super.onUpdate();
	}
}



