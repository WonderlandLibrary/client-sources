/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class Hotbar
extends Module {
    private Timer timer;

    public Hotbar() {
        super("Hotbar-Drop", Module.Category.World, -33896);
        this.setBind(38);
        this.timer = new Timer();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        this.mc.rightClickDelayTimer = 0;
        if (this.hotbarFull() && this.timer.hasPassed(600.0f)) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(0));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(1));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(2));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(3));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(4));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(5));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(6));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(7));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(8));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.timer.reset();
        }
    }

    public boolean hotbarFull() {
        boolean full = true;
        int i2 = 37;
        while (i2 < 45) {
            if (!this.mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack()) {
                full = false;
            }
            ++i2;
        }
        return full;
    }
}

