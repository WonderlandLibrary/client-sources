package me.protocol_client.modules;

import java.util.Random;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPrePlayerUpdate;

public class AutoClicker extends Module {

	public AutoClicker() {
		super("Auto Clicker", "autoclicker", 0, Category.COMBAT, new String[] { "autoclicker", "clicker", "autoc" });
	}

	private final ClampedValue<Float>	cps	= new ClampedValue<>("autoclicker_cps", 11F, 2F, 20F);

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	public int		ticks;
	double			random;
	public boolean	negative;

	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event) {
		Random r = new Random();
		ticks++;
		if (Wrapper.mc().gameSettings.keyBindAttack.pressed) {
			if (ticks >= (20 / (cps.getValue() + (negative ? -random : random)))) {
				Wrapper.getPlayer().swingItem();
				if (Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityLivingBase) {
					Wrapper.mc().playerController.attackEntity(Wrapper.getPlayer(), Minecraft.getMinecraft().objectMouseOver.entityHit);
				}
				random = Math.random() * 2;
				negative = r.nextBoolean();
				ticks = 0;
			}
		}
	}

	private Object		hacker	= new Object();
	private Runnable	hack	= () -> {
									while (true) {
										synchronized (hacker) {
											if (!this.isToggled() || Wrapper.getWorld() == null) {
												try {
													hacker.wait();
												} catch (Exception e) {
													System.out.println("you got hacked");
												}
											}
										}

									}
								};
}
