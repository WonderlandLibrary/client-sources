package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.*;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.*;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;



public final class WatchdogVelocity extends Mode<Velocity> {

    public WatchdogVelocity(String name, Velocity parent) {
        super(name, parent);
    }
    private long active2StartTime = -1; // Tracks when active2 was set to true
    boolean active2 = false;
    boolean send = false;
    public final BooleanValue stack = new BooleanValue("Stack", this, true);

    public final BooleanValue spoof = new BooleanValue("Ping Spoof", this, true);

    public final BooleanValue spoof2 = new BooleanValue("Watchdog Backtrack", this, false);

    public final BooleanValue boost = new BooleanValue("Damage Boost (not for hypixel)", this, false);

    private final NumberValue speed = new NumberValue("Damage Boost Speed", this, 1, 1, 10, .01);
    private boolean active, receiving, buffer;
    private Vec3 realPosition = new Vec3(0, 0, 0);
    public Entity targetEntity;
    private int amount;
    private final ArrayList<Packet<?>> packets = new ArrayList<>();
    private boolean isSpoof2Packet(Packet<?> packet) {
        return packet instanceof S14PacketEntity.S16PacketEntityLook ||
                packet instanceof S14PacketEntity.S15PacketEntityRelMove ||
                packet instanceof S14PacketEntity.S17PacketEntityLookMove ||
                packet instanceof S19PacketEntityHeadLook ||
                packet instanceof S18PacketEntityTeleport;
    }
    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {

        if (receiving || getModule(LongJump.class).isEnabled() && mc.thePlayer.offGroundTicks<29 || getModule(Flight.class).isEnabled()) return;

        switch (event.getPacket()) {
            case S12PacketEntityVelocity velocity -> {

                if (velocity.getEntityID() == mc.thePlayer.getEntityId()) {
                    if (stack.getValue() && amount < 1 && (!mc.thePlayer.onGround) && Math.random() < 0.73 && !(mc.thePlayer.offGroundTicks > 13) || mc.thePlayer.inWater  || (getModule(Scaffold.class).isEnabled() )) {
                        amount++;
                        buffer = true;
                        //   ChatUtil.display("stack");
                        event.setCancelled();
                        buffer = false;

                    }
                    if (!event.isCancelled() && (!getModule(Scaffold.class).isEnabled() ) && !(mc.thePlayer.offGroundTicks > 11) && ((!mc.thePlayer.onGround ) || (mc.thePlayer.onGround && !MoveUtil.isMoving())) &&  !(velocity.getMotionY()/800D < .08 && !(getModule(Speed.class).isEnabled() || mc.thePlayer.inWater || !mc.thePlayer.isInLava()) )) {
                        amount = 0;
                        active = true;
                        //  ChatUtil.display("Set Active");
                        packets.add(velocity);

                        event.setCancelled();

                    } else if (!event.isCancelled() ) {
                        if((!getModule(Scaffold.class).isEnabled() )){
                            mc.thePlayer.motionY = velocity.getMotionY() / 8000.0D;
                        }

                        //      ChatUtil.display("normal");
                        event.setCancelled();
                    }


                }

            }

            case S32PacketConfirmTransaction transaction -> {
                if (active && spoof.getValue()) {
                    packets.add(transaction);
                    event.setCancelled();
                    // PingSpoofComponent.spoof(50, true, false, false, false, false, false);
                }

            }

            case S00PacketKeepAlive keepAlive -> {
                if (active && spoof.getValue()) {
                    //   packets.add(keepAlive);
                        event.setCancelled();
                }
            }


            case S14PacketEntity.S16PacketEntityLook s16PacketEntityLook -> {
                if ((active2 || active)  && spoof2.getValue() && !send) {
                    packets.add(s16PacketEntityLook);
                    event.setCancelled();
                }
            }
            case S14PacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove -> {
                if ((active2 || active)  && spoof2.getValue()  && !send) {
                    packets.add(s15PacketEntityRelMove);
                    event.setCancelled();
                }
            }
            case S14PacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove -> {
                if ((active2 || active)  && spoof2.getValue()  && !send) {
                    packets.add(s17PacketEntityLookMove);
                    event.setCancelled();
                }
            }



            case S19PacketEntityHeadLook s19PacketEntityHeadLook -> {
                if ((active2 || active)  && spoof2.getValue()  && !send) {
                    packets.add(s19PacketEntityHeadLook);
                    event.setCancelled();
                }
            }



            default -> {
            }

        }
        List<EntityLivingBase> targets = TargetComponent.getTargets(12);
        targets.sort(Comparator.comparingDouble(entity -> entity.hurtTime));
        if (targets.isEmpty()) {
            targetEntity = null;
            // Add logic here to reset or flush your spoof2 states.
            // For example:
            if (spoof2.getValue()) {
                receiving = true;
                packets.stream().filter(this::isSpoof2Packet).forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
                active2 = false;
                send = false;
            }
            return;
        }
        Entity entity = targets.get(0);




        if (spoof2.getValue()) {








            if (entity != targetEntity) {
                targetEntity = entity;

                realPosition.xCoord = entity.posX;
                realPosition.yCoord = entity.posY;
                realPosition.zCoord = entity.posZ;
            }





            Vec3 playerEyesPos = mc.thePlayer.getPositionEyes(1.0F);
            Vec3 targetEyesPos = new Vec3(realPosition.xCoord, realPosition.yCoord, realPosition.zCoord);
            double distance = playerEyesPos.distanceTo(targetEyesPos);

            if ((distance > 4.5) && active || mc.thePlayer.offGroundTicks>9 && active) {
                send = true;
            } else if(active){
                send = false;
            }
//ChatUtil.display(distance);
            active2 = distance < 4.5;
if( mc.thePlayer.isSwingInProgress && distance>3.1&& distance<4.5 && getModule(KillAura.class).isEnabled()){
  // ChatUtil.display("reach:" + distance);
}




            if (active2) {
                // Check if this is the first frame where active2 is true
                if (active2StartTime == -1) {
                    active2StartTime = System.currentTimeMillis(); // Record the starting time
                }

                // Check if 200ms has passed
                if (System.currentTimeMillis() - active2StartTime >= Math.round((Math.random()*150))+100) {
                    receiving = true;
                    packets.stream()
                            .filter(this::isSpoof2Packet)
                            .forEach(PacketUtil::receive);
                    packets.clear();
                    receiving = false;

               //     ChatUtil.display(mc.thePlayer.ticksExisted);

                    // Reset active2 and timer
                    active2 = false;
                    active2StartTime = -1;
                }
            } else {
                // Reset the timer if active2 is false
                active2StartTime = -1;
            }

            if (send && active) {
               // ChatUtil.display(send);
                active2 = false; // If active2 is set to false, reset the timer
                receiving = true;
                packets.stream()
                        .filter(this::isSpoof2Packet)
                        .forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
            }

            if(mc.thePlayer.ticksExisted<100){
                active2 = false; // If active2 is set to false, reset the timer
                receiving = true;
                packets.stream()
                        .filter(this::isSpoof2Packet)
                        .forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
            }

            if (mc.thePlayer.hurtTime == 9) {
               // ChatUtil.display(send);
                active2 = false;
                receiving = true;
                packets.stream()
                        .filter(this::isSpoof2Packet)
                        .forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
            }
            if ((distance < 2.5)) {
                active2 = false;
                //  ChatUtil.display(send);
                receiving = true;
                packets.stream()
                        .filter(this::isSpoof2Packet)
                        .forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
            }

            if ((distance > 4.5)) {
                active2 = false;
              //  ChatUtil.display(send);
                receiving = true;
                packets.stream()
                        .filter(this::isSpoof2Packet)
                        .forEach(PacketUtil::receive);
                packets.clear();
                receiving = false;
            }
            final Packet<?> packet = event.getPacket();
            if (packet instanceof S14PacketEntity) {
                S14PacketEntity s14PacketEntity = ((S14PacketEntity) packet);

                if (s14PacketEntity.entityId == targetEntity.getEntityId()) {
                    realPosition.xCoord += s14PacketEntity.getPosX() / 32D;
                    realPosition.yCoord += s14PacketEntity.getPosY() / 32D;
                    realPosition.zCoord += s14PacketEntity.getPosZ() / 32D;
                }
            } else if (packet instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport s18PacketEntityTeleport = ((S18PacketEntityTeleport) packet);

                if (s18PacketEntityTeleport.getEntityId() == targetEntity.getEntityId()) {
                    realPosition = new Vec3(s18PacketEntityTeleport.getX() / 32D, s18PacketEntityTeleport.getY() / 32D, s18PacketEntityTeleport.getZ() / 32D);
                }
            }
        }


    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<JumpEvent> onJump = event -> {
        if (mc.thePlayer.onGround && active) {
            active = false;
            //  ChatUtil.display("Set Active");
            receiving = true;
            send = false;
            //  MoveUtil.strafe(MovdeUtil.getbaseMoveSpeed() + Math.random() / 10);
            //   mc.thePlayer.jump();
            Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
            packets.forEach(PacketUtil::receive);
            // ChatUtil.display("jump");
            packets.clear();

            if (!boost.getValue()) {
                mc.thePlayer.motionX = motion.getX();
                mc.thePlayer.motionZ = motion.getY();
            } else {
                MoveUtil.strafe();
                mc.thePlayer.motionX *= speed.getValue().doubleValue();
                mc.thePlayer.motionZ *= speed.getValue().doubleValue();

            }

            receiving = false;
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if(active2 && !active && spoof2.getValue()){
            PingSpoofComponent.spoof(50, true, false, false, false, true, false);
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<StrafeEvent> onStrafe = event -> {



        if (mc.thePlayer.onGround && active && !mc.thePlayer.isJumping) {
            active = false;

            receiving = true;
            send = false;
            //   MoveUtil.strafe(MoveUtil.WALK_SPEED);
            Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
            double motiony = mc.thePlayer.motionY;
         //   PingSpoofComponent.dispatch();
            packets.forEach(PacketUtil::receive);
            // ChatUtil.display("jump");
            packets.clear();
            mc.thePlayer.jump();
            //  PingSpoofComponent.dispatch();
            mc.thePlayer.motionX = motion.getX();
            mc.thePlayer.motionZ = motion.getY();

            //  active = false;
            receiving = false;
            // receiving = true;
            //   Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
            // packets.forEach(PacketUtil::receive);
            //  ChatUtil.display("normal");
            // packets.clear();
            //  mc.thePlayer.motionX = motion.getX();
            //  mc.thePlayer.motionZ = motion.getY();
            //   receiving = false;
        }

    };


    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {

        if (mc.thePlayer.offGroundTicks > 12 && active) {
            active = false;

            receiving = true;
            //  MoveUtil.strafe(MoveUtil.getbaseMoveSpeed() + Math.random() / 10);
            //   mc.thePlayer.jump();
            Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
            double motiony = mc.thePlayer.motionY;
         //   PingSpoofComponent.dispatch();
            packets.forEach(PacketUtil::receive);
            // ChatUtil.display("jump");
            packets.clear();
            //  PingSpoofComponent.dispatch();
            mc.thePlayer.motionX = motion.getX();
            mc.thePlayer.motionZ = motion.getY();
            //  mc.thePlayer.motionY = motiony;
            receiving = false;
        }
    };
    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (targetEntity == null || !spoof2.getValue() || (!active2 && !active)) {
            return;
        }


        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glDepthMask(false);

        double expand = -0.14;
        RenderUtil.color(Color.red, 45);

        RenderUtil.drawBoundingBox(mc.thePlayer.getEntityBoundingBox()
                .offset(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ)
                .offset(realPosition.xCoord, realPosition.yCoord, realPosition.zCoord)
                .expand(expand, -.3, expand));


        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GL11.glDepthMask(true);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    };

}
