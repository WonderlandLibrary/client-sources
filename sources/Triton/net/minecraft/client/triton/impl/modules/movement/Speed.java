// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.impl.modules.movement.speed.Bhop;
import net.minecraft.client.triton.impl.modules.movement.speed.FagPort;
import net.minecraft.client.triton.impl.modules.movement.speed.Jump;
import net.minecraft.client.triton.impl.modules.movement.speed.Vhop;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.LiquidUtils;
import net.minecraft.potion.Potion;

@Mod(displayName = "Speed")
public class Speed extends Module {
	public Bhop bhop;
	public Vhop vhop;
	private Jump jump;
	private FagPort port;
	@Op(min = 2.0, max = 4.5, increment = 0.25, name = "Boost")
	public static double boost;

	public Speed() {
		this.bhop = new Bhop("Bhop", true, this);
		this.vhop = new Vhop("Vhop", false, this);
		this.jump = new Jump("Jump", false, this);
		this.port = new FagPort("FagPort", false, this);
	}

	@Override
	public void postInitialize() {
		OptionManager.getOptionList().add(this.bhop);
		OptionManager.getOptionList().add(this.vhop);
		OptionManager.getOptionList().add(this.jump);
		OptionManager.getOptionList().add(this.port);
		this.updateSuffix();
		super.postInitialize();
	}

	@Override
	public void enable() {
		this.bhop.enable();
		this.vhop.enable();
		this.jump.enable();
		this.port.enable();
		super.enable();
	}

	@EventTarget
	private void onMove(final MoveEvent event) {
		if (LiquidUtils.isInLiquid()) {
			return;
		}
		this.bhop.onMove(event);
		this.vhop.onMove(event);
		this.jump.onMove(event);
		this.port.onMove(event);
	}

	@EventTarget
	private void onUpdate(final UpdateEvent event) {
		this.bhop.onUpdate(event);
		this.vhop.onUpdate(event);
		this.jump.onUpdate(event);
		this.port.onUpdate(event);
	}

	@EventTarget
	private void onTick(final TickEvent event) {
		this.updateSuffix();
	}

	private void updateSuffix() {
		if (this.bhop.getValue()) {
			this.setSuffix("Bhop");
		} else if (this.vhop.getValue()) {
			this.setSuffix("Vhop");
		} else if (this.port.getValue()) {
			this.setSuffix("FagPort");
		} else {
			this.setSuffix("Jump");
		}
	}

	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
			final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}
}
