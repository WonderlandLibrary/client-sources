package me.xatzdevelopments.xatz.client.modules;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.WaitTimer;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class WTap extends Module {

	WaitTimer timer = new WaitTimer();
	Random rand = new Random();

	
	public WTap() {
		super("MoreKnockback", Keyboard.KEY_NONE, Category.COMBAT,
				"Sprint and unsprints really fast for a more knockback!");
	}

	
	@Override
	public void onPacketSent(AbstractPacket e) {
		if (e instanceof C02PacketUseEntity && mc.thePlayer != null) {
			C02PacketUseEntity packet = (C02PacketUseEntity) e;
			if (packet.getAction() == C02PacketUseEntity.Action.ATTACK
					&& packet.getEntityFromWorld(mc.theWorld) != mc.thePlayer) {
				if (mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
					boolean sprint = mc.thePlayer.isSprinting();
					mc.thePlayer.setSprinting(false);
					mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer,
							C0BPacketEntityAction.Action.STOP_SPRINTING));
					mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer,
							C0BPacketEntityAction.Action.START_SPRINTING));
					mc.thePlayer.setSprinting(sprint);
				}
			}
		}
	}
}
