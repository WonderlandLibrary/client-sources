package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.SlowDownEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;

public class VariableNoSlow extends Mode<NoSlow> {

    private final NumberValue multiplier = new NumberValue("Multiplier", this, 0.8, 0.2, 1, 0.05);

    public VariableNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if (getParent().foodValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood) {
            event.setForwardMultiplier(multiplier.getValue().floatValue());
            event.setStrafeMultiplier(multiplier.getValue().floatValue());
        }
        if (getParent().potionValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) {
            event.setForwardMultiplier(multiplier.getValue().floatValue());
            event.setStrafeMultiplier(multiplier.getValue().floatValue());
        }
        if (getParent().swordValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            event.setForwardMultiplier(multiplier.getValue().floatValue());
            event.setStrafeMultiplier(multiplier.getValue().floatValue());
        }
        if (getParent().bowValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
            event.setForwardMultiplier(multiplier.getValue().floatValue());
            event.setStrafeMultiplier(multiplier.getValue().floatValue());
        }
    };
}