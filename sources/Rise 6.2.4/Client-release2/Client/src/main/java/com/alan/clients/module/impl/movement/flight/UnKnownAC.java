package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.component.impl.player.GUIDetectionComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class UnKnownAC extends Mode<Flight> {
    private final NumberValue speed = new NumberValue("Speed", this, 2, 1, 7, 0.25);
    private double y;
    private int ticks;
    private C08PacketPlayerBlockPlacement placement;

    public UnKnownAC(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        y = Math.floor(mc.thePlayer.posY);
        placement = null;
        ticks = 0;
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        final int slot = SlotUtil.findBlock();

        if (slot == -1) {
            return;
        }

        getComponent(Slot.class).setSlot(slot);

        if (placement != null) {
            return;
        }

        MoveUtil.stop();

        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), 10, MovementFix.TRADITIONAL);

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        if (RotationComponent.rotations.y >= 89 &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                mc.thePlayer.posY > mc.objectMouseOver.getBlockPos().add(0, 2, 0).getY()) {

            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, getComponent(Slot.class).getItemStack(),
                    mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);

            PacketUtil.send(new C0APacketAnimation());
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        final int slot = SlotUtil.findBlock();

        if (slot == -1) {
            return;
        }

        mc.timer.timerSpeed = Math.max(1, speed.getValue().floatValue() - ticks / 50f);

        if (placement == null) {
            return;
        }

        ticks++;

        if (ticks == 25) {
            PlayerUtil.fakeDamage();
        }

        if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
            y = mc.thePlayer.posY;
        }

        if (!GUIDetectionComponent.inGUI() && Math.random() > 0.7) {
            PacketUtil.send(placement);
        }

        if (mc.thePlayer.onGround) {
            if (!GUIDetectionComponent.inGUI()) {
                PacketUtil.send(placement);
            }

            mc.thePlayer.jump();
        }
    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (placement == null) {
            return;
        }

        if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() && (mc.thePlayer.posY < y + 1 || mc.gameSettings.keyBindJump.isKeyDown())) {
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
    };

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C08PacketPlayerBlockPlacement) {
            placement = (C08PacketPlayerBlockPlacement) packet;
        }
    };
}