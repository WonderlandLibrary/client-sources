package me.rich.module.render;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.util.EnumParticleTypes;

public class ParticleTrails extends Feature{

	public ParticleTrails() {
		super("ParticleTrails", 0, Category.RENDER);
		// TODO Auto-generated constructor stub
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		mc.world.spawnParticle(EnumParticleTypes.DRIP_WATER, mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.motionX, mc.player.motionY, mc.player.motionZ, 2);

	}

}
