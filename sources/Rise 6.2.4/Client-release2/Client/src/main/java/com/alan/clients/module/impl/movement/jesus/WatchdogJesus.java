package com.alan.clients.module.impl.movement.jesus;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Jesus;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class WatchdogJesus extends Mode<Jesus> {
    public WatchdogJesus(String name, Jesus parent) {
        super(name, parent);
    }
    private Boolean jesus = false;


    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (event.getBlock() instanceof BlockLiquid) {
          //  ChatUtil.display("to get on semi blocks of water jump into the water since you will flag if you ascend in the water");
           jesus = true;
            final int x = event.getBlockPos().getX();
            final int y = event.getBlockPos().getY();
            final int z = event.getBlockPos().getZ();


            event.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1));
        } else{
            jesus = false;
        }
    };


    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (PlayerUtil.onLiquid()) {
            event.setPosY(event.getPosY() - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.15625 : 0.10625));
            event.setOnGround(false);


        }
    };


    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (PlayerUtil.onLiquid()) {
            event.setSpeed(.152);
        }
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {


        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity && jesus ||p instanceof S12PacketEntityVelocity && PlayerUtil.onLiquid()) {

            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                        event.setCancelled();
                        return;
                }



        }
    };
}
