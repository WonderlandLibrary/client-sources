/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class iDrop
extends Module {
    public Timer timer = new Timer();

    public iDrop() {
        super("/i-Drop", Module.Category.Exploits, -33896);
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.timer.hasPassed(725.0f)) {
            this.mc.thePlayer.sendChatMessage("/i poisonpotato 512");
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
}

