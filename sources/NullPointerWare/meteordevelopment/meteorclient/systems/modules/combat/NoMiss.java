/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.events.entity.player.AttackPreEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.hit.HitResult;

public final class NoMiss extends Module {
    // private final SettingGroup sgGeneral = settings.getDefaultGroup();

    //private final Setting<Boolean> swingHand = sgGeneral.add(new BoolSetting.Builder()
    //    .name("Swing Hand")
    //    .description("Swings your hand even if you missed...")
    //    .defaultValue(true)
    //    .build()
    //);



//    private boolean missed = false;

    public NoMiss() {
        super(Categories.Combat, "NoMiss", "Cancels missed hits!");
    }

    @EventHandler
    private void onMiss(final AttackPreEvent e) {
        if (e.hitResult.getType().equals(HitResult.Type.MISS))
            e.setCancelled(true);
            // missed = true;
    }

    @EventHandler
    private void onAttack(final AttackEntityEvent e) {
        if(mc.crosshairTarget.getType() == HitResult.Type.MISS)
            e.cancel();
    }

}
