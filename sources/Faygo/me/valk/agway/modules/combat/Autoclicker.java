package me.valk.agway.modules.combat;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Mouse;

import me.valk.event.EventListener;
import me.valk.event.events.other.EventTick;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.MathUtil;
import me.valk.utils.TimerUtils;
import me.valk.utils.value.RestrictedValue;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Autoclicker extends Module {

	private int random;
	public RestrictedValue<Double> AveregeCPS = new RestrictedValue<Double>("AveregeCPS", 10.0, 2.0, 20.0);
	public TimerUtils timer = new TimerUtils();

	public Autoclicker() {
		super(new ModData("Autoclicker", 0, new Color(255, 255, 255)), ModType.COMBAT);
		addValue(AveregeCPS);
	}

	@EventListener
	public void Tick(EventTick e) {
		if (mc.theWorld == null) {
			return;
		}
		if (p == null) {
			return;
		}

		if (Mouse.isButtonDown(0)) {
			this.random = MathUtil.getRandomInRange(1, 50);
			if (timer.hasReached((1000 / (this.AveregeCPS.getValue().intValue() + 10) + this.random))) {
				p.swingItem();
				if(mc.objectMouseOver.entityHit != null) {
				p.sendQueue.addToSendQueue(new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
				}
				mc.playerController.isHittingBlock = false;
				timer.reset();
			}

		}

	}

}
