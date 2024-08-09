/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;

import java.util.List;

public final class HitParticles extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<ParticleType<?>>> particles = sgGeneral.add(new ParticleTypeListSetting.Builder()
        .name("particles")
        .description("Particles to draw.")
        .defaultValue(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, ParticleTypes.CAMPFIRE_COSY_SMOKE)
        .build()
    );

    private final Setting<Integer> age = sgGeneral.add(new IntSetting.Builder()
        .name("Age")
        .defaultValue(20)
        .min(1)
        .build()
    );

    public HitParticles() {
        super(Categories.Render, "HitParticles", "Add custom particles on hit!");
    }

    @EventHandler
    private void onAttack(final AttackEntityEvent e) {
        for (ParticleType<?> particleType : particles.get()) {
            mc.particleManager.addEmitter(e.entity, (ParticleEffect) particleType, age.get());
        }
    }
}
