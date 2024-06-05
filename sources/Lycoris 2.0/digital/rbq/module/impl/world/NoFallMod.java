/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.world;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import digital.rbq.annotations.Label;
import digital.rbq.events.packet.SendPacketEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.movement.FlightMod;
import digital.rbq.module.option.impl.EnumOption;

@Label(value="No Fall")
@Category(value=ModuleCategory.WORLD)
@Aliases(value={"nofall"})
public final class NoFallMod
extends Module {
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.HYPIXEL);

    public NoFallMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode);
    }

    @Listener(value=SendPacketEvent.class)
    public final void onSendPacket(SendPacketEvent event) {
        if (this.mode.getValue() == Mode.TEST && event.getPacket() instanceof C03PacketPlayer && NoFallMod.mc.thePlayer.fallDistance > 2.0f && !FlightMod.getInstance().isEnabled()) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)event.getPacket();
            if (!packetPlayer.isMoving()) {
                packetPlayer.onGround = true;
            } else {
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
            }
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        if (event.isPre()) {
            switch ((Mode)((Object)this.mode.getValue())) {
                case HYPIXEL: {
                    if (!(NoFallMod.mc.thePlayer.fallDistance > 3.0f) || !this.isBlockUnder() || FlightMod.getInstance().isEnabled() || NoFallMod.mc.thePlayer.posY % 0.0625 == 0.0 && NoFallMod.mc.thePlayer.posY % 0.015256 == 0.0) break;
                    mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
                    NoFallMod.mc.thePlayer.fallDistance = 0.0f;
                    break;
                }
                case GROUND: {
                    if (!(NoFallMod.mc.thePlayer.fallDistance > 3.0f) || FlightMod.getInstance().isEnabled()) break;
                    event.setOnGround(true);
                }
            }
        } else if (this.mode.getValue() == Mode.HYPIXEL && NoFallMod.mc.thePlayer.fallDistance > 3.0f && this.isBlockUnder() && !FlightMod.getInstance().isEnabled() && (NoFallMod.mc.thePlayer.posY % 0.0625 != 0.0 || NoFallMod.mc.thePlayer.posY % 0.015256 != 0.0)) {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
            NoFallMod.mc.thePlayer.fallDistance = 0.0f;
        }
    }

    private boolean isBlockUnder() {
        for (int i = (int)(NoFallMod.mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(NoFallMod.mc.thePlayer.posX, (double)i, NoFallMod.mc.thePlayer.posZ);
            if (NoFallMod.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    public static enum Mode {
        HYPIXEL,
        GROUND,
        TEST;

    }
}

