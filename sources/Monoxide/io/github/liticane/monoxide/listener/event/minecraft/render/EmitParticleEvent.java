package io.github.liticane.monoxide.listener.event.minecraft.render;

import net.minecraft.util.EnumParticleTypes;
import io.github.liticane.monoxide.listener.event.Event;

public class EmitParticleEvent extends Event {
	public int multiplier;
	public EnumParticleTypes particleTypes;

	public EmitParticleEvent(int multiplier, EnumParticleTypes particleTypes) {
		this.multiplier = multiplier;
		this.particleTypes = particleTypes;
	}

	public EnumParticleTypes getParticleTypes() {
		return particleTypes;
	}

	public void setParticleTypes(EnumParticleTypes particleTypes) {
		this.particleTypes = particleTypes;
	}

}