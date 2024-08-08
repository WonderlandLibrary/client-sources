package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals2 extends Module {

	int slotBefore;
	int bestSlot;
	float eating;
	public static boolean disable;

	public Criticals2() {
		super("Criticals2", Keyboard.KEY_NONE, Category.COMBAT, "Tries to critical every hit.");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}

	@Override
	public void onLeftClick() {

	}

	

	
	public static void crit(double xx, double yy, double zz) {
		// if(true) {
		// return;
		// }
		if (!Xatz.getModuleByName("Criticals2").isToggled()) {
			return;
		}
		if (!mc.thePlayer.onGround) {
			return;
		}
		double x = xx;
		double y = yy;
		double z = zz;
		// Xatz.chatMessage("crit");
		 mc.thePlayer.moveEntity(0, 0.005, 0);
	}

	public static void crit() {
		// if(true) {
		// return;
		// }
		// crit(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
	}

}
