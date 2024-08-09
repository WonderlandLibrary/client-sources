//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket.Action;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(
        name = "Velocity",
        type = Category.Combat
)
public class Velocity extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Cancel", new String[]{"Cancel", "Grim Skip", "Grim Cancel", "Funtime"});
    private int skip = 0;
    private boolean cancel;
    boolean damaged;
    BlockPos blockPos;

    public Velocity() {
        this.addSettings(new Setting[]{this.mode});
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player != null) {
            if (e.isReceive()) {
                IPacket var3 = e.getPacket();
                SEntityVelocityPacket p;
                if (var3 instanceof SEntityVelocityPacket) {
                    p = (SEntityVelocityPacket)var3;
                    if (p.getEntityID() != mc.player.getEntityId()) {
                        return;
                    }
                }

                switch (this.mode.getIndex()) {
                    case 0:
                        if (e.getPacket() instanceof SEntityVelocityPacket) {
                            e.cancel();
                        }
                        break;
                    case 1:
                        if (e.getPacket() instanceof SEntityVelocityPacket) {
                            this.skip = 6;
                            e.cancel();
                        }

                        if (e.getPacket() instanceof CPlayerPacket && this.skip > 0) {
                            --this.skip;
                            e.cancel();
                        }
                        break;
                    case 2:
                        if (e.getPacket() instanceof SEntityVelocityPacket) {
                            e.cancel();
                            this.cancel = true;
                        }

                        if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                            this.skip = 3;
                        }

                        if (e.getPacket() instanceof CPlayerPacket) {
                            --this.skip;
                            if (this.cancel) {
                                if (this.skip <= 0) {
                                    BlockPos blockPos = new BlockPos(mc.player.getPositionVec());
                                    mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                                    mc.player.connection.sendPacket(new CPlayerDiggingPacket(Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                                }

                                this.cancel = false;
                            }
                        }
                }

                if (this.mode.is("Funtime")) {
                    var3 = e.getPacket();
                    if (var3 instanceof SEntityVelocityPacket) {
                        p = (SEntityVelocityPacket)var3;
                        if (this.skip >= 2) {
                            return;
                        }

                        if (p.getEntityID() != mc.player.getEntityId()) {
                            return;
                        }

                        e.cancel();
                        this.damaged = true;
                    }

                    if (e.getPacket() instanceof SPlayerPositionLookPacket) {
                        this.skip = 3;
                    }
                }
            }

        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (this.mode.is("Funtime")) {
            --this.skip;
            if (this.damaged) {
                BlockPos blockPos = mc.player.getPosition();
                mc.player.connection.sendPacketWithoutEvent(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                mc.player.connection.sendPacketWithoutEvent(new CPlayerDiggingPacket(Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                this.damaged = false;
            }
        }

    }

    public void onEnable() {
        super.onEnable();
        this.skip = 0;
        this.cancel = false;
        this.damaged = false;
    }
}
