package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;

import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.settings.impl.ModeSetting;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(name = "Velocity", type = Category.Combat)
public class   Velocity extends Function {

    private final ModeSetting mode = new ModeSetting("Mode", "Cancel", "Cancel", "Grim Skip", "Grim Cancel", "Funtime");

    private int skip = 0;
    private boolean cancel;
    boolean damaged;

    public Velocity() {
        addSettings(mode);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null) return;
        if (e.isReceive()) {
            if (e.getPacket() instanceof SEntityVelocityPacket p && p.getEntityID() != mc.player.getEntityId()) return;
            switch (mode.getIndex()) {
                case 0 -> { // Cancel
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.cancel();
                    }
                }

                case 1 -> { // Grim Skip
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        skip = 6;
                        e.cancel();
                    }

                    if (e.getPacket() instanceof CPlayerPacket) {
                        if (skip > 0) {
                            skip--;
                            e.cancel();
                        }
                    }
                }

                case 2 -> { // Grim Cancel
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.cancel();
                        cancel = true;
                    }
                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        skip = 3;
                    }

                    if (e.getPacket() instanceof CPlayerPacket) {
                        skip--;
                        if (cancel) {
                            if (skip <= 0) {
                                BlockPos blockPos = new BlockPos(mc.player.getPositionVec());
                                mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                            }
                            cancel = false;
                        }
                    }
                }
            }

            if (mode.is("Funtime")) {
                if (e.getPacket() instanceof SEntityVelocityPacket p) {
                    if (skip >= 2) {
                        return;
                    }
                    if (p.getEntityID() != mc.player.getEntityId()) {
                        return;
                    }
                    e.cancel();
                    damaged = true;
                }
                if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                    skip = 3;
                }
            }
        }

    }

    BlockPos blockPos;

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mode.is("Funtime")) {
            skip--;
            if (damaged) {
                BlockPos blockPos = mc.player.getPosition();
                mc.player.connection.sendPacketWithoutEvent(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                mc.player.connection.sendPacketWithoutEvent(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                damaged = false;
            }
        }
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        skip = 0;
        cancel = false;
        damaged = false;
        return false;
    }
}