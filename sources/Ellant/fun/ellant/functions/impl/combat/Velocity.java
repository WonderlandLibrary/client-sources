package fun.ellant.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(name="NoVelocity", type=Category.COMBAT, desc = "Убирает отталкивание?")
public class Velocity
        extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "Grim Skip", "Grim Cancel", "Funtime");
    private int skip = 0;
    private boolean cancel;
    boolean damaged;
    BlockPos blockPos;

    public Velocity() {
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (Velocity.mc.player == null) {
            return;
        }
        if (e.isReceive()) {
            SEntityVelocityPacket p;
            IPacket<?> iPacket = e.getPacket();
            if (iPacket instanceof SEntityVelocityPacket && (p = (SEntityVelocityPacket)iPacket).getEntityID() != Velocity.mc.player.getEntityId()) {
                return;
            }
            switch (this.mode.getIndex()) {
                case 0: {
                    if (!(e.getPacket() instanceof SEntityVelocityPacket)) break;
                    e.cancel();
                    break;
                }
                case 1: {
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        this.skip = 6;
                        e.cancel();
                    }
                    if (!(e.getPacket() instanceof CPlayerPacket) || this.skip <= 0) break;
                    --this.skip;
                    e.cancel();
                    break;
                }
                case 2: {
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.cancel();
                        this.cancel = true;
                    }
                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        this.skip = 3;
                    }
                    if (!(e.getPacket() instanceof CPlayerPacket)) break;
                    --this.skip;
                    if (!this.cancel) break;
                    if (this.skip <= 0) {
                        BlockPos blockPos = new BlockPos(Velocity.mc.player.getPositionVec());
                        Velocity.mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(Velocity.mc.player.getPosX(), Velocity.mc.player.getPosY(), Velocity.mc.player.getPosZ(), Velocity.mc.player.rotationYaw, Velocity.mc.player.rotationPitch, Velocity.mc.player.isOnGround()));
                        Velocity.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                    }
                    this.cancel = false;
                }
            }
            if (this.mode.is("Funtime")) {
                iPacket = e.getPacket();
                if (iPacket instanceof SEntityVelocityPacket) {
                    p = (SEntityVelocityPacket)iPacket;
                    if (this.skip >= 11) {
                        return;
                    }
                    if (p.getEntityID() != Velocity.mc.player.getEntityId()) {
                        return;
                    }
                    e.cancel();
                    this.damaged = true;
                }
                if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                    this.skip = 12;
                }
            }
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (this.mode.is("Funtime")) {
            --this.skip;
            if (this.damaged) {
                this.damaged = false;
            }
        }
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        this.skip = 0;
        this.damaged = false;
        return false;
    }
}

