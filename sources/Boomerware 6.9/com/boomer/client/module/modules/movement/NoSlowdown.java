package com.boomer.client.module.modules.movement;

import java.awt.Color;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.SlowdownEvent;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.value.impl.BooleanValue;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


/**
 * made by oHare for HTB V4
 *
 * @since 4/22/2019
 **/
public class NoSlowdown extends Module {
    private BooleanValue vanilla = new BooleanValue("Vanilla", false);
    private BooleanValue items = new BooleanValue("Items", true);
    private BooleanValue sprint = new BooleanValue("Sprint", true);
    private BooleanValue water = new BooleanValue("Water", true);
    private BooleanValue soulsand = new BooleanValue("SoulSand", true);

    public NoSlowdown() {
        super("NoSlowdown", Category.MOVEMENT, new Color(102, 100, 100, 255).getRGB());
        setDescription("No slow down from blocking / eating");
        setRenderlabel("No Slowdown");
        addValues(vanilla, items, sprint, water, soulsand);
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (vanilla.getValue()) return;
        if (event.isPre()) {
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.isBlocking() && mc.thePlayer.onGround && (mc.gameSettings.keyBindForward.isKeyDown() | mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else {
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.isBlocking() && mc.thePlayer.onGround && (mc.gameSettings.keyBindForward.isKeyDown() | mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())) {
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
            }
        }
    }

    @Handler
    public void onSlowDown(SlowdownEvent event) {
        switch (event.getType()) {
            case Item:
                if (items.getValue()) event.setCanceled(true);
                break;
            case Sprinting:
                if (sprint.getValue()) event.setCanceled(true);
                break;
            case SoulSand:
                if (soulsand.getValue()) event.setCanceled(true);
                break;
            case Water:
                if (water.getValue()) event.setCanceled(true);
                break;
        }
    }
}
