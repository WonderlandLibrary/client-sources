package cc.slack.features.modules.impl.player.antivoids.impl;

import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.movement.LongJump;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.features.modules.impl.player.AntiVoid;
import cc.slack.features.modules.impl.player.antivoids.IAntiVoid;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;

public class VulcanAntiVoid implements IAntiVoid {

    private boolean teleported;

    private boolean noBlock;

    private Flight flight = null;
    private Speed speed = null;

    private LongJump longjump = null;
    private boolean speedWasEnabled = false;
    private int disable;

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE) {
            if (flight == null) {
                flight = Slack.getInstance().getModuleManager().getInstance(Flight.class);
            }
            if (speed == null) {
                speed = Slack.getInstance().getModuleManager().getInstance(Speed.class);
            }
            if (longjump == null) {
                longjump = Slack.getInstance().getModuleManager().getInstance(LongJump.class);
            }

            if (mc.thePlayer.fallDistance > Slack.getInstance().getModuleManager().getInstance(AntiVoid.class).vulcandistance.getValue() && !PlayerUtil.isBlockUnder()) {

                noBlock = true;
            }

            if (flight.isToggle() || longjump.isToggle()) {
                noBlock = false;
            }

            if (speed.isToggle() && noBlock) {
                speedWasEnabled = true;
                speed.toggle();
            }

            if (!noBlock && !(speed.isToggle()) && speedWasEnabled){
                speed.toggle();
                speedWasEnabled = false;
            }
        }
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking() && noBlock) {
            final double x = event.getX(), y = event.getY(), z = event.getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }

        if (!(event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking()) && noBlock && !mc.thePlayer.isCollidedHorizontally) {
            noBlock = false;
        }
    }

    @Override
    public void onStrafe(StrafeEvent event) {
        if(noBlock){
            MovementUtil.strafe(.1F);
            if (mc.thePlayer.ticksExisted % 2 == 1|| !(mc.thePlayer.moveForward == 0 )) {

                event.setForward(1);
            } else {
                MovementUtil.strafe(0);
                event.setForward(-1);
            }
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook posLook = ((S08PacketPlayerPosLook) event.getPacket());

            noBlock = false;
        }
    }

    @Override
    public void onWorld(WorldEvent event) {
        noBlock = false;
    }

    @Override
    public void onJump(JumpEvent event) {
        if (noBlock) {
            event.setJumpMotion(0);
        }
    }

    @Override
    public String toString() {
        return "Vulcan Ghost";
    }
}
