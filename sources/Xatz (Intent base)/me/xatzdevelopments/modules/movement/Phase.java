package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.util.HTimer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldSettings;

public class Phase
extends Module {
    public HTimer timer = new HTimer();

    public Phase() {
        super("Phase", 0, Module.Category.MOVEMENT, "Go through blocks");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (event.isPre()) {
                this.mc.thePlayer.setGameType(WorldSettings.GameType.CREATIVE);
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                if (this.timer.hasTimedElapsed(30000L, false)) {
                    this.timer.reset();
                }
            }
        }
    }

    @Override
    public void onDisable() {
    }
}
