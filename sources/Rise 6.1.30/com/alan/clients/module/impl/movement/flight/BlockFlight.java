package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class BlockFlight extends Mode<Flight> {

    public BlockFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        getComponent(Slot.class).setSlot(SlotUtil.findBlock());
    };
    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(3) && mc.thePlayer.ticksSinceVelocity > 15) {
            if (Math.abs(MoveUtil.predictedMotion(0.42f) - mc.thePlayer.motionY) < 0.0001) {
                event.setOnGround(true);
//                MoveUtil.strafe(MoveUtil.getbaseMoveSpeed() - Math.random() / 100f);
//                mc.thePlayer.jump();
            } else {
                ChatUtil.display("Not Set");
                mc.thePlayer.motionY = 0.42f;
            }

            mc.thePlayer.motionY = 0.42f;

        }

        if (getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().getItem() instanceof ItemBlock) {
            if (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir) {
                PacketUtil.send(new C0APacketAnimation());

                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                        mc.thePlayer.getCurrentEquippedItem(),
                        new BlockPos(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY) - 1, mc.thePlayer.posZ),
                        EnumFacing.UP, new Vec3(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY) - 1, mc.thePlayer.posZ));
            }
        }
    };
}