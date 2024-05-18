/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.movement.NoSlow;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.SprintEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MovementInput;

@Module.Mod(shown=false)
public class AutoSprint
extends Module {
    @Option.Op(name="Multi-Directional")
    private boolean multiDirection = true;
    @Option.Op(name="Auto Sprint")
    private boolean auto = true;
    @Option.Op(name="Client Sided")
    private boolean clientSide = true;
    @Option.Op
    private boolean legit;

    @EventTarget
    private void onUpdate(TickEvent event) {
        if (ClientUtils.player() != null && this.canSprint()) {
            ClientUtils.player().setSprinting(true);
        }
    }

    @EventTarget
    private void onSprint(SprintEvent event) {
        if (this.canSprint()) {
            event.setSprinting(true);
        }
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        C0BPacketEntityAction packet;
        if (event.getState().equals((Object)Event.State.PRE) && this.clientSide && event.getPacket() instanceof C0BPacketEntityAction && ((packet = (C0BPacketEntityAction)event.getPacket()).func_180764_b() == C0BPacketEntityAction.Action.START_SPRINTING || packet.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING)) {
            event.setCancelled(true);
        }
    }

    private boolean canSprint() {
        block4 : {
            block6 : {
                block5 : {
                    if (!this.auto || ClientUtils.player().isCollidedHorizontally || ClientUtils.player().isSneaking() || this.legit && !new NoSlow().getInstance().isEnabled() && (!this.legit || ClientUtils.player().getFoodStats().getFoodLevel() <= 5 || ClientUtils.player().isUsingItem())) break block4;
                    if (!this.multiDirection) break block5;
                    if (MovementInput.moveForward != 0.0f) break block6;
                    if (MovementInput.moveStrafe == 0.0f) break block4;
                    break block6;
                }
                if (!(MovementInput.moveForward > 0.0f)) break block4;
            }
            return true;
        }
        return false;
    }
}

