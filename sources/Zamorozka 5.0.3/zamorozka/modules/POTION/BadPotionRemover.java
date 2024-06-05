package zamorozka.modules.POTION;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Wrapper;

public class BadPotionRemover extends Module {

	public BadPotionRemover() {
		super("BadPotionRemover", Keyboard.KEY_NONE, Category.POTION);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(25))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(25));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(2))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(2));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(4))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(4));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(9))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(9));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(15))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(15));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(17))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(17));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(18))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(18));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(27))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(27));
		}
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(20))) {
			Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(20));
		}
	}
}