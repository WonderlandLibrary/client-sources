package cc.slack.features.modules.impl.movement.flights.impl.verus;

import cc.slack.events.State;
import cc.slack.events.impl.input.onMoveInputEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;

public class VerusFloatFlight implements IFlight {


    private int ticks = 0;



    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.thePlayer.motionY = 0.42F;
                }
            }
            ++ticks;
        }
    }


    @Override
    public void onMove(MoveEvent event) {
            if (mc.thePlayer.onGround && ticks % 14 == 0) {
                event.setY(0.42F);
                MovementUtil.strafe(0.69F);
                mc.thePlayer.motionY = -(mc.thePlayer.posY - Math.floor(mc.thePlayer.posY));
            } else {
                if (mc.thePlayer.onGround) {
                    MovementUtil.strafe((float) (1.1F + MovementUtil.getSpeedPotAMP(0.15)));
                } else MovementUtil.strafe((float) (0.43 + MovementUtil.getSpeedPotAMP(0.05)));
            }
            mc.thePlayer.setSprinting(true);
            PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        ticks++;
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() || mc.gameSettings.keyBindJump.isKeyDown()) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(
                        -15,
                        -1,
                        -15,
                        15,
                        1,
                        15
                ).offset(x, y, z));
            }
        }
    }

    @Override
    public void onDisable() {
        MovementUtil.resetMotion();
        PacketUtil.sendNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }

    @Override
    public void onMoveInput(onMoveInputEvent event) {
        event.sneak = false;
    }

    @Override
    public String toString() {
        return "Verus Float";
    }
}
