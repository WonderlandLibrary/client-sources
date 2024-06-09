// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import xyz.niggfaclient.utils.player.MoveUtils;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "NoSlowdown", description = "Prevents you from slowing down when using items", cat = Category.MOVEMENT)
public class NoSlowdown extends Module
{
    private final EnumProperty<Mode> mode;
    private int ticks;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public NoSlowdown() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Vanilla);
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            if (MoveUtils.isMoving() && (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isBlocking())) {
                switch (this.mode.getValue()) {
                    case NCP: {
                        if (e.isPre()) {
                            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            break;
                        }
                        else {
                            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        ++this.ticks;
                        if (this.ticks == 2) {
                            PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                        }
                        else {
                            PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        }
                        PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
                        break;
                    }
                }
            }
            return;
        });
        C0BPacketEntityAction c0b;
        this.packetEventListener = (e -> {
            if (e.getState() != PacketEvent.State.RECEIVE || this.mode.getValue() == Mode.Watchdog) {}
            if (e.getState() == PacketEvent.State.SEND && this.mode.getValue() == Mode.Watchdog) {
                if (e.getPacket() instanceof C03PacketPlayer) {
                    PacketUtil.sendPacketNoEvent(new C0CPacketInput(0.0f, 0.0f, this.mc.thePlayer.movementInput.jump, this.mc.thePlayer.movementInput.sneak));
                }
                if (e.getPacket() instanceof C0BPacketEntityAction) {
                    c0b = (C0BPacketEntityAction)e.getPacket();
                    if (c0b.getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
                        e.setCancelled();
                    }
                }
                if ((e.getPacket() instanceof C02PacketUseEntity || e.getPacket() instanceof C08PacketPlayerBlockPlacement || e.getPacket() instanceof C07PacketPlayerDigging || e.getPacket() instanceof C0APacketAnimation) && this.ticks % 5 != 0) {
                    e.setCancelled(true);
                }
            }
        });
    }
    
    public enum Mode
    {
        Vanilla, 
        NCP, 
        Watchdog;
    }
}
