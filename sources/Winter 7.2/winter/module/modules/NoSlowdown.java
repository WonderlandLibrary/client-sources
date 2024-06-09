/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.EventPriority;
import winter.event.EventPriorityListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class NoSlowdown
extends Module {
    public NoSlowdown() {
        super("NoSlowdown", Module.Category.Movement, -202);
        this.setBind(38);
    }

    @EventPriorityListener(priority=EventPriority.HIGHEST)
    public void onUpdate(UpdateEvent event) {
        boolean wasBlocking = this.mc.thePlayer.isBlocking();
        if ((this.mc.gameSettings.keyBindForward.pressed || this.mc.gameSettings.keyBindBack.pressed || this.mc.gameSettings.keyBindRight.pressed || this.mc.gameSettings.keyBindRight.pressed) && event.isPre() && this.mc.thePlayer.isBlocking()) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}

