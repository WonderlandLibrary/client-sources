package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class GrimFlight extends Mode<Flight> {

    private final ModeValue Mode = new ModeValue("Sub-Mode", this)
            .add(new SubMode("Block"))
            .add(new SubMode("TNT"))
            .setDefault("Block");

    private Boolean Flight;

    public GrimFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        Flight = false;
        if(Mode.getValue().getName().equals("Block")) {
            ChatUtil.display("Any blocks you place will be ghost blocks. You have infinite range to place blocks.");
        } else {
            NotificationComponent.post("Grim TNT Fly","Get knock-back from a TNT to trigger the flight",2500);
        }
    }

    

    @EventLink
    private final Listener<PreMotionEvent> preMotion = event -> {
        switch(Mode.getValue().getName()) {
            case "Block":
                if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    // Creating a variable that gets the block that the user is looking at and creating another variable with incremented Y position of the position so that the user teleports on top of the block.
                    MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTrace(999, 1);
                    if (movingObjectPosition == null) return;

                    final BlockPos pos = movingObjectPosition.getBlockPos();
                    final BlockPos tpPos = pos.offset(movingObjectPosition.sideHit, 4);

                    Vector2f rotations = RotationUtil.calculate(
                            new Vector3d(tpPos.getX(), tpPos.getY(), tpPos.getZ()),
                            new Vector3d(pos.getX(), pos.getY(), pos.getZ()));
                    PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(tpPos.getX(), tpPos.getY() - 1, tpPos.getZ(), rotations.x, rotations.y, false));

                    mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, getComponent(Slot.class).getItemStack(),
                            movingObjectPosition.getBlockPos(), movingObjectPosition.sideHit, movingObjectPosition.hitVec);
                }
                break;
            case "TNT":
                if(Flight) {
                    final double yaw = Math.toRadians(MoveUtil.direction());

                    event.setPosX(mc.thePlayer.posX - Math.sin(yaw) * 500);
                    event.setPosY(MoveUtil.predictedMotion(mc.thePlayer.motionY));
                    event.setPosZ(mc.thePlayer.posZ + Math.cos(yaw) * 500);
                }
                break;
        }
    };

    @EventLink
    private final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {
        if (Mode.getValue().getName().equals("TNT")) {
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

                if (packet.getEntityID() != mc.thePlayer.getEntityId()) {
                    return;
                }

                Flight = true;
            }
        }
    };

    @EventLink
    private final Listener<TeleportEvent> teleport = event -> {
        if (Mode.getValue().getName().equals("Block") && mc.gameSettings.keyBindUseItem.isKeyDown()) event.setCancelled();
    };

}
