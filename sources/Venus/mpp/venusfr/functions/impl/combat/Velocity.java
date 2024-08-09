/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(name="Velocity", type=Category.Combat)
public class Velocity
extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Cancel", "Cancel", "Grim Skip", "Grim Cancel", "Funtime");
    private int skip = 0;
    private boolean cancel;
    boolean damaged;
    BlockPos blockPos;

    public Velocity() {
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        if (Velocity.mc.player == null) {
            return;
        }
        if (eventPacket.isReceive()) {
            Object object;
            IPacket<?> iPacket = eventPacket.getPacket();
            if (iPacket instanceof SEntityVelocityPacket && ((SEntityVelocityPacket)(object = (SEntityVelocityPacket)iPacket)).getEntityID() != Velocity.mc.player.getEntityId()) {
                return;
            }
            switch (this.mode.getIndex()) {
                case 0: {
                    if (!(eventPacket.getPacket() instanceof SEntityVelocityPacket)) break;
                    eventPacket.cancel();
                    break;
                }
                case 1: {
                    if (eventPacket.getPacket() instanceof SEntityVelocityPacket) {
                        this.skip = 6;
                        eventPacket.cancel();
                    }
                    if (!(eventPacket.getPacket() instanceof CPlayerPacket) || this.skip <= 0) break;
                    --this.skip;
                    eventPacket.cancel();
                    break;
                }
                case 2: {
                    if (eventPacket.getPacket() instanceof SEntityVelocityPacket) {
                        eventPacket.cancel();
                        this.cancel = true;
                    }
                    if (eventPacket.getPacket() instanceof SPlayerPositionLookPacket) {
                        this.skip = 3;
                    }
                    if (!(eventPacket.getPacket() instanceof CPlayerPacket)) break;
                    --this.skip;
                    if (!this.cancel) break;
                    if (this.skip <= 0) {
                        object = new BlockPos(Velocity.mc.player.getPositionVec());
                        Velocity.mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(Velocity.mc.player.getPosX(), Velocity.mc.player.getPosY(), Velocity.mc.player.getPosZ(), Velocity.mc.player.rotationYaw, Velocity.mc.player.rotationPitch, Velocity.mc.player.isOnGround()));
                        Velocity.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, (BlockPos)object, Direction.UP));
                    }
                    this.cancel = false;
                }
            }
            if (this.mode.is("Funtime")) {
                iPacket = eventPacket.getPacket();
                if (iPacket instanceof SEntityVelocityPacket) {
                    object = (SEntityVelocityPacket)iPacket;
                    if (this.skip >= 2) {
                        return;
                    }
                    if (((SEntityVelocityPacket)object).getEntityID() != Velocity.mc.player.getEntityId()) {
                        return;
                    }
                    eventPacket.cancel();
                    this.damaged = true;
                }
                if (eventPacket.getPacket() instanceof SPlayerPositionLookPacket) {
                    this.skip = 3;
                }
            }
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (this.mode.is("Funtime")) {
            --this.skip;
            if (this.damaged) {
                BlockPos blockPos = Velocity.mc.player.getPosition();
                Velocity.mc.player.connection.sendPacketWithoutEvent(new CPlayerPacket.PositionRotationPacket(Velocity.mc.player.getPosX(), Velocity.mc.player.getPosY(), Velocity.mc.player.getPosZ(), Velocity.mc.player.rotationYaw, Velocity.mc.player.rotationPitch, Velocity.mc.player.isOnGround()));
                Velocity.mc.player.connection.sendPacketWithoutEvent(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                this.damaged = false;
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.skip = 0;
        this.cancel = false;
        this.damaged = false;
    }
}

