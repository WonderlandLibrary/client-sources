/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;


import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.other.TrinaryBoolean;
import meteordevelopment.meteorclient.utils.player.CrystalsInvUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.meteorclient.utils.player.SwapType;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Hand;

import java.util.List;

public final class RetardBase extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The Minimum health")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> minHealth = sgGeneral.add(new DoubleSetting.Builder()
        .name("minHealth")
        .description("The Minimum health")
        .defaultValue(5)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private int timer;

    public RetardBase() {
        super(Categories.Combat, "RetardBase", "Retarded tickbase, idk why you would use this...");
    }
    public boolean stop;
    private int tickCounter = -1;

    @Override
    public void onActivate() {
        super.onActivate();
        tickCounter = -1;
        stop = false;
    }

    public int getTicks() {
        if (tickCounter-- > 0) {
            return -1;
        } else {
            stop = false;
        }

        Aura killAura = Modules.get().get(Aura.class);

        if (Modules.get().get(Aura.class).getTarget() != null
            && killAura.getTarget().isAlive()
            && killAura.getTarget().distanceTo(mc.player) > 3
            && killAura.getTarget().distanceTo(mc.player) < 5.5
            && mc.player.hurtTime <= 2) {
            return tickCounter = 8;
        }
        return 0;
    }

    @EventHandler
    private void onTick(final TickEvent.Post e) {
        int tick = getTicks();
        boolean skip = false;

        if (tick == -1) {
            skip = true;
        } else {
            if (tick > 0) {
                stop = true;
            }
        }

        if (stop && !skip) {
            try {
                Thread.sleep(180);
            } catch (Exception ignored) {
            }
        }
    }
}
