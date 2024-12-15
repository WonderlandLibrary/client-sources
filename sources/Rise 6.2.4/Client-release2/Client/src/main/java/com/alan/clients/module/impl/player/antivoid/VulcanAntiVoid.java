package com.alan.clients.module.impl.player.antivoid;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.AntiVoid;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;

public class VulcanAntiVoid extends Mode<AntiVoid> {

    private final NumberValue distance = new NumberValue("Distance", this, 2.6, 0, 10, 0.1);

    private boolean teleported;

    private boolean noBlock;

    private Flight flight = null;
    private Speed speed = null;

    private LongJump longjump = null;
    private boolean speedWasEnabled = false;
    private int disable;

    public VulcanAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {


        if (flight == null) {
            flight = getModule(Flight.class);
        }
        if (speed == null) {
            speed = getModule(Speed.class);
        }
        if (longjump == null) {
            longjump = getModule(LongJump.class);
        }

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue() && !PlayerUtil.isBlockUnder()) {

            noBlock = true;
        }

        if (flight.isEnabled() || longjump.isEnabled()) {
            noBlock = false;
        }

        if (speed.isEnabled() && noBlock) {
            speedWasEnabled = true;
            speed.toggle();
        }

        if (!noBlock && !(speed.isEnabled()) && speedWasEnabled){
            speed.toggle();
            speedWasEnabled = false;
        }

    };
    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

            // Sets The Bounding Box To The Players Y Position.
            if (event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking() && noBlock) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }

        if (!(event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking()) && noBlock && !mc.thePlayer.isCollidedHorizontally) {
            noBlock = false;
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if(noBlock){
           MoveUtil.strafe(.1);
            if (mc.thePlayer.ticksExisted % 2 == 1|| !(mc.thePlayer.moveForward == 0 )) {

                event.setForward(1);
            } else {
                MoveUtil.strafe(0);
                event.setForward(-1);
            }
        }
   };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook posLook = ((S08PacketPlayerPosLook) packet);

            noBlock = false;
        }

    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        noBlock = false;
    };

    @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {
        if (noBlock) {
            event.setJumpMotion(0);

        }
    };
}

