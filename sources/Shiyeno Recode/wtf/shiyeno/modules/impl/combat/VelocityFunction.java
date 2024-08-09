package wtf.shiyeno.modules.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(name="Velocity", type=Type.Combat)
public class VelocityFunction
        extends Function {
    private final ModeSetting mode = new ModeSetting("Режим", "Cancel", "Cancel", "Grim Updated", "Grim", "Grim New", "Near Boat", "Packet Grim");
    public BooleanOption restart1 = new BooleanOption("Сообщения о перегреве", false).setVisible(() -> this.mode.is("Grim New"));
    private final TimerUtil timerUtil = new TimerUtil();
    private int toSkip;
    private int await;
    BlockPos blockPos;
    boolean damaged;
    boolean restart = true;

    public VelocityFunction() {
        this.addSettings(this.mode, this.restart1);
    }

    @Override
    public void onEvent(Event event) {
        EventPacket e;
        if (VelocityFunction.mc.player == null || VelocityFunction.mc.world == null) {
            return;
        }
        if (event instanceof EventPacket && (e = (EventPacket)event).isReceivePacket()) {
            switch (this.mode.get()) {
                case "Near Boat": {
                    for (Entity entity : VelocityFunction.mc.world.getAllEntities()) {
                        if (!(entity instanceof BoatEntity) || !((double)VelocityFunction.mc.player.getDistance(entity) < 2.3) || !(e.getPacket() instanceof SEntityVelocityPacket)) continue;
                        e.setCancel(true);
                    }
                    break;
                }
                case "Packet Grim": {
                    IPacket iPacket = e.getPacket();
                    if (iPacket instanceof SPlayerPositionLookPacket) {
                        SPlayerPositionLookPacket p = (SPlayerPositionLookPacket)iPacket;
                        VelocityFunction.mc.player.func_242277_a(new Vector3d(p.getX(), p.getY(), p.getZ()));
                        VelocityFunction.mc.player.setRawPosition(p.getX(), p.getY(), p.getZ());
                        this.toggle();
                    }
                    if (e.getPacket() instanceof SEntityVelocityPacket) {
                        e.setCancel(true);
                    }
                    if (!(e.getPacket() instanceof SConfirmTransactionPacket)) break;
                    e.setCancel(true);
                    break;
                }
                case "Cancel": {
                    if (!(e.getPacket() instanceof SEntityVelocityPacket)) break;
                    e.setCancel(true);
                    break;
                }
                case "Grim New": {
                    if (VelocityFunction.mc.player.isElytraFlying() || VelocityFunction.mc.player.isInWater() || VelocityFunction.mc.player.isInLava()) {
                        return;
                    }
                    IPacket iPacket = e.getPacket();
                    if (iPacket instanceof SPlayerPositionLookPacket) {
                        SPlayerPositionLookPacket p = (SPlayerPositionLookPacket)iPacket;
                        VelocityFunction.mc.player.func_242277_a(new Vector3d(p.getX(), p.getY(), p.getZ()));
                        VelocityFunction.mc.player.setRawPosition(p.getX(), p.getY(), p.getZ());
                        return;
                    }
                    if (VelocityFunction.mc.player.fallDistance > 15.0f) break;
                    if (e.getPacket() instanceof SConfirmTransactionPacket && (double)VelocityFunction.mc.player.fallDistance > 0.09) {
                        e.setCancel(true);
                    }
                    if (!(e.getPacket() instanceof SEntityVelocityPacket) || !((double)VelocityFunction.mc.player.fallDistance > 0.09) || ((SEntityVelocityPacket)e.getPacket()).getEntityID() != VelocityFunction.mc.player.getEntityId()) break;
                    e.setCancel(true);
                    break;
                }
                case "Grim": {
                    IPacket iPacket = e.getPacket();
                    if (iPacket instanceof SEntityVelocityPacket) {
                        SEntityVelocityPacket p = (SEntityVelocityPacket)iPacket;
                        if (p.getEntityID() != VelocityFunction.mc.player.getEntityId() || this.toSkip < 0) {
                            return;
                        }
                        this.toSkip = 8;
                        event.setCancel(true);
                    }
                    if (e.getPacket() instanceof SConfirmTransactionPacket) {
                        if (this.toSkip < 0) {
                            ++this.toSkip;
                        } else if (this.toSkip > 1) {
                            --this.toSkip;
                            event.setCancel(true);
                        }
                    }
                    if (!(e.getPacket() instanceof SPlayerPositionLookPacket)) break;
                    this.toSkip = -8;
                    break;
                }
                case "Grim Updated": {
                    IPacket iPacket = e.getPacket();
                    if (!(iPacket instanceof SEntityVelocityPacket)) break;
                    SEntityVelocityPacket p = (SEntityVelocityPacket)iPacket;
                    if (p.getEntityID() != VelocityFunction.mc.player.getEntityId() || this.await > -5) {
                        return;
                    }
                    this.await = 2;
                    this.damaged = true;
                    event.setCancel(true);
                }
            }
        }
        if (event instanceof EventUpdate && this.mode.is("Grim Updated")) {
            --this.await;
            if (this.damaged) {
                this.blockPos = new BlockPos(VelocityFunction.mc.player.getPositionVec());
                VelocityFunction.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, this.blockPos, Direction.UP));
                VelocityFunction.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, this.blockPos, Direction.UP));
                this.damaged = false;
            }
        }
    }

    private void reset() {
        this.toSkip = 0;
        this.await = 0;
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        this.reset();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        this.reset();
    }
}