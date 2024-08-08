package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.combat.KillAura;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.Stopwatch;

public class Annoy extends Module {

	public NumberSetting speed = new NumberSetting("Speed", 2.5, 1, 5, 0.5);
	public NumberSetting pitch = new NumberSetting("Pitch", 180, 0, 360, 1);
	public ModeSetting mode = new ModeSetting("Mode", "Crazy", "Crazy", "Witch");
	public Stopwatch yomama = new Stopwatch();
	private double var4;
	private double var5;

	public Annoy() {
		super("Annoy", 0, Category.MOVEMENT, "Annoy others");
		this.addSettings(speed, pitch, mode);
	}

	public void onEnable() {

	}

	public void onDisable() {
		Xatz.overridePitch = false;
		this.yomama.reset();
	}

	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			EventMotion em = (EventMotion)e;
			if(e.isPre()) {
				if(this.mode.getMode().equals("Crazy")) {
					float var1 = 0;
					if(var1 == 0) {

					float var2 = var5 == 0 ? (this.yomama.getElapsedTime() / 10) * (float)this.speed.getValue() : (float)var5;
					if(var2 > 360) {
						this.yomama.reset();
					}
					if(!KillAura.isAttacking) {
						em.setYaw(var2);
					mc.thePlayer.renderYawOffset = em.getYaw();
					mc.thePlayer.rotationYawHead = em.getYaw();
					
					Xatz.overridePitch = true;
					em.setPitch((float) this.pitch.getValue());
					mc.thePlayer.rotationPitchHead = em.getPitch();
					}
					
					}
				} else {
					float var1 = 0;
					if(var1 == 0 && !Xatz.overrideOverridePitch) {
					//mc.thePlayer.renderYawOffset = (float) (System.nanoTime() / 1000000 * this.speed.getValue());
					//mc.thePlayer.rotationYawHead = (float) (System.nanoTime() / 1000000 * this.speed.getValue());
					Xatz.overridePitch = true;
					mc.thePlayer.rotationPitchHead = (float) this.pitch.getValue();
					}
				}
			}
		}
	}

}
