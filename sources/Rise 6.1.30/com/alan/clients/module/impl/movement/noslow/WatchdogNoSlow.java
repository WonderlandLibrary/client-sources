package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.SlowDownEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class WatchdogNoSlow extends Mode<NoSlow> {
    private boolean using;
    private int eatTicks = 0;

    public WatchdogNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (this.mc.thePlayer.isUsingItem()) {
            if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood && getParent().foodValue.getValue()) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 0, null, 0, 0, 0));
            }

            if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion && getParent().potionValue.getValue()) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 0, null, 0, 0, 0));
                this.mc.thePlayer.motionX *= 0.8f;
                this.mc.thePlayer.motionZ *= 0.8f;

            }
        }

        if (!this.mc.thePlayer.isUsingItem() && !this.mc.thePlayer.isSprinting() && mc.thePlayer.onGround && mc.thePlayer.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if (this.mc.thePlayer.isUsingItem()) {
            if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow || this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                return;
            }
        }
        if (getParent().potionValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) event.setCancelled();
        if (getParent().foodValue.getValue() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood) event.setCancelled();
    };
}