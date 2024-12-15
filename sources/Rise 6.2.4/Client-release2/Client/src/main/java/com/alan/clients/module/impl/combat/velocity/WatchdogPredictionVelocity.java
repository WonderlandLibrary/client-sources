package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

import java.util.ArrayList;
import java.util.List;

public class WatchdogPredictionVelocity extends Mode<Velocity> {
    public WatchdogPredictionVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    private boolean active, receiving;
    private int offGroundTicks;
    private final ArrayList<Packet<?>> packets = new ArrayList<>();
    private float desiredYaw;
    private double velX, velZ;
    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {


        if (receiving || getModule(LongJump.class).isEnabled() && mc.thePlayer.offGroundTicks < 29 || getModule(Flight.class).isEnabled())
            return;

        switch (event.getPacket()) {
            case S12PacketEntityVelocity velocity -> {

                if (velocity.getEntityID() == mc.thePlayer.getEntityId()) {
                    if (!event.isCancelled()) {
                        active = true;
                      //  packets.add(velocity);
                        //  PingSpoofComponent.spoof(50, true, false, false, false, false, false);

                     //   mc.thePlayer.motionY = velocity.getMotionY() / 8000.0D;
                        active = true;
                        // Calculate desiredYaw based on incoming velocity
                        double velX = velocity.getMotionX() / 8000.0D;
                        double velZ = velocity.getMotionZ() / 8000.0D;
                        desiredYaw = (float) Math.toDegrees(Math.atan2(velZ, velX));

                        // Adjust desiredYaw to match the player's yaw range (-180 to 180)
                        if (desiredYaw < -180) desiredYaw += 360;
                        if (desiredYaw > 180) desiredYaw -= 360;

    packets.add(velocity);
    event.setCancelled();

                        this.velX = velX;
                        this.velZ = velZ;

                        velocity.motionY *= 1;

                        event.setPacket(velocity);

                    }


                }

            }

            case S32PacketConfirmTransaction transaction -> {
                if (active) {
                    packets.add(transaction);
                    event.setCancelled();
                    //   active = false;
                    // PingSpoofComponent.spoof(50, true, false, false, false, false, false);
                }

            }

            case S00PacketKeepAlive keepAlive -> {
                if (active) {
                    if(mc.thePlayer.ticksExisted % 3 ==0){
                   //     ChatUtil.display("added");
                        packets.add(keepAlive);
                    }else{
                   //     ChatUtil.display("lost");
                    }

                    event.setCancelled();
                }
            }

            default -> {
            }
        }

    };


    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        List<EntityLivingBase> targets = TargetComponent.getTargets(7);

        if (targets.isEmpty()) return;




        if(active && mc.thePlayer.onGround || receiving){
            double cancelX = -this.velX;
            double cancelZ = -this.velZ;
          //  ChatUtil.display("s");
            mc.thePlayer.motionX = cancelX;
            mc.thePlayer.motionZ = cancelZ;
          //  event.setPosY(event.getPosY() -  1);

        } else if(mc.thePlayer.onGround && mc.thePlayer.hurtTime > 7){


            double cancelX = -this.velX;
            double cancelZ = -this.velZ;
          //  cancelX = Math.max(-1D, Math.min(1D, cancelX));
          //  cancelZ = Math.max(-1D, Math.min(1D, cancelZ));
          //  ChatUtil.display("s");
            mc.thePlayer.motionX *= cancelX;
            mc.thePlayer.motionZ *= cancelZ;
            mc.thePlayer.jump();
        }
    };



    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (active) {
            // Get the player's current yaw
            float playerYaw = mc.thePlayer.rotationYaw % 360F;
            if (playerYaw < -180F) playerYaw += 360F;
            if (playerYaw > 180F) playerYaw -= 360F;

            // Calculate the difference between the player's yaw and desiredYaw
            float yawDifference = Math.abs(playerYaw - desiredYaw);

            // Define the leeway in degrees (e.g., 10 degrees)
            float leeway = 20F;

            // Check if the yaw difference is within the leeway
            if (yawDifference <= leeway || yawDifference >= (360F - leeway)) {
                // Process the packets to give a boost
                receiving = true;
                active = false;
                ChatUtil.display("boost");
                packets.forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
                offGroundTicks = 0;

            } else if(mc.thePlayer.onGround ) {
              //  ChatUtil.display("jump reset");
                if(!mc.thePlayer.isJumping){
                 //   mc.thePlayer.jump();
                 //   ChatUtil.display("jump reset 2");
                }
                mc.thePlayer.motionX *= -1D;
                mc.thePlayer.motionZ *= -1D;
                // If offGroundTicks exceed 11, release the packets when back on the ground


                        receiving = true;
                        active = false;
                        packets.forEach(PacketUtil::receive);
                        packets.clear();

                        receiving = false;

                    } else if (mc.thePlayer.offGroundTicks>12){
                receiving = true;
                active = false;
                packets.forEach(PacketUtil::receive);
                packets.clear();
                mc.thePlayer.jump();
                mc.thePlayer.motionX *= 0.6D;
                mc.thePlayer.motionZ *= 0.6D;
                receiving = false;
            }


        }
    };
}
