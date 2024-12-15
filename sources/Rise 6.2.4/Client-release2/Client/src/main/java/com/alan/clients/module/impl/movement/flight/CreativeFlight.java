package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.*;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.util.MathHelper;

public class CreativeFlight extends Mode<Flight> {

    public CreativeFlight(String name, Flight parent) {
        super(name, parent);
    }
     double lastPosX = Double.NaN;
     double lastPosY = Double.NaN;
     double lastPosZ = Double.NaN;
    private double serverPosX, serverPosY, serverPosZ;
    private double lastServerPosX, lastServerPosY, lastServerPosZ;
    private float serverYaw, serverPitch;
    private double cameraOffsetX = 0;
    private double cameraOffsetY = 0;
    private double cameraOffsetZ = 0;
    private float cameraYawOffset = 0;
    private float cameraPitchOffset = 0;
    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
if(mc.thePlayer.onGround){

    mc.thePlayer.jump();
}

      //  mc.thePlayer.cameraYaw = 0;
      //  mc.thePlayer.cameraPitch = 0;
   //     mc.gameSettings.keyBindJump.setPressed(true);

       //     mc.gameSettings.keyBindForward.setPressed(true);
        RotationComponent.setSmoothed(false);
        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw + 45, mc.thePlayer.rotationPitch), 10, MovementFix.NORMAL);


      //  MoveUtil.preventDiagonalSpeed();
    //  lastPosX = mc.thePlayer.lastTickPosX;
    //   lastPosY = mc.thePlayer.lastTickPosY;
   //    lastPosZ = mc.thePlayer.lastTickPosZ;
   //     mc.thePlayer.setPosition(lastPosX, lastPosY, lastPosZ);
      //  mc.timer.timerSpeed = 1f;
        mc.timer.timerSpeed = 3f;
        if(mc.thePlayer.offGroundTicks >=6){

            mc.thePlayer.capabilities.allowFlying = true;
mc.thePlayer.motionZ *= 9.5;
            mc.thePlayer.motionX *= 9.5;
            final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
            final double x = Math.sin(yaw) * 9;
            final double z = Math.cos(yaw) * 9;
         //   event.setPosX(event.getPosX() - x);
        //    event.setPosZ(event.getPosZ() + z);

mc.thePlayer.motionY = 0;

        }




      //  mc.thePlayer.setPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ);


    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        mc.timer.timerSpeed = 0f;
        if(mc.thePlayer.offGroundTicks>= 5){

            mc.thePlayer.motionY = .005;
        }


        //MoveUtil.strafe(9);
     //   mc.thePlayer.setPosition(lastPosX, lastPosY, lastPosZ);
    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {


        //MoveUtil.strafe(9);
    //    mc.thePlayer.setPosition(lastPosX, lastPosY, lastPosZ);
    };

    @EventLink
    public final Listener<PostMotionEvent> postMotionEventListener = event -> {


        //MoveUtil.strafe(9);
      //  mc.thePlayer.setPosition(lastPosX, lastPosY, lastPosZ);
    };

    @EventLink
    public final Listener<Render2DEvent> event = event -> {
        if(Math.hypot(serverPosX - lastServerPosX,serverPosZ - lastServerPosZ)>.4){
            mc.fontRendererObj.drawWithShadow("Teleported: "+ Math.round((Math.hypot(serverPosX - lastServerPosX,serverPosZ - lastServerPosZ))), (double) mc.scaledResolution.getScaledWidth() / 2.2, (double) mc.scaledResolution.getScaledHeight() / 2 + 20, -1);
            // ChatUtil.display("good");
        }
    };

    @EventLink
    public final Listener<PreUpdateEvent> PreUpdateEvent = event -> {
        PingSpoofComponent.spoof(300000, true, false, false, false, false);


        //MoveUtil.strafe(9);

    };


    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive){

        }


        if (p instanceof S39PacketPlayerAbilities) {
            final S39PacketPlayerAbilities wrapper = (S39PacketPlayerAbilities) p;
            wrapper.setFlying(mc.thePlayer.capabilities.isFlying);
        }

        if (p instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook posLookPacket = (S08PacketPlayerPosLook) p;

            // Update the player's position and rotation to match the server's data
            lastServerPosX = serverPosX;
            lastServerPosY = serverPosY;
            lastServerPosZ = serverPosZ;

            serverPosX = posLookPacket.getX();
            serverPosY = posLookPacket.getY();
            serverPosZ = posLookPacket.getZ();

            lastPosX =   posLookPacket.getX();
            lastPosZ  =    posLookPacket.getZ();
            lastPosY = posLookPacket.getY();

         //   mc.thePlayer.setPosition(lastPosX, lastPosY, lastPosZ);
            // Update the camera rotation
         //   event.setCancelled();

            // Optionally, reset motion to prevent client-side movement
         //   mc.thePlayer.motionX = 0;
         //   mc.thePlayer.motionY = 0;
        //    mc.thePlayer.motionZ = 0;


        }
    };

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        // Save the original render view entity
        double partialTicks = mc.timer.renderPartialTicks;
      //  mc.timer.timerSpeed = 10f;
        // Interpolate between the last and current server positions
        double interpolatedPosX = lastServerPosX + (serverPosX - lastServerPosX) * partialTicks;
        double interpolatedPosY = lastServerPosY + (serverPosY - lastServerPosY) * partialTicks;
        double interpolatedPosZ = lastServerPosZ + (serverPosZ - lastServerPosZ) * partialTicks;


        if(mc.thePlayer.offGroundTicks >=5) {
            // Set the render view entity's position to the interpolated position
            Entity renderViewEntity = mc.getRenderViewEntity();
            renderViewEntity.posX = interpolatedPosX;
            renderViewEntity.posY = interpolatedPosY;
            renderViewEntity.posZ = interpolatedPosZ;

            // Also update the previous positions to prevent any rendering jitter
            renderViewEntity.prevPosX = interpolatedPosX;
            renderViewEntity.prevPosY = interpolatedPosY;
            renderViewEntity.prevPosZ = interpolatedPosZ;
        }

        // Update the rotation
      //  renderViewEntity.rotationYaw = serverYaw;
      //  renderViewEntity.rotationPitch = serverPitch;
    };


    @Override
    public void onEnable() {
        cameraOffsetX = 0;
        cameraOffsetY = 0;
        cameraOffsetZ = 0;
        cameraYawOffset = 0;
        cameraPitchOffset = 0;

        lastPosX = mc.thePlayer.lastReportedPosX;
        lastPosY = mc.thePlayer.lastReportedPosY;
        lastPosZ = mc.thePlayer.lastReportedPosZ;
        PingSpoofComponent.dispatch();
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        mc.thePlayer.capabilities.isFlying = true;

    }

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
       // mc.gameSettings.keyBindForward.setPressed(false);
      //  mc.gameSettings.keyBindJump.setPressed(false);
     //  mc.gameSettings.keyBindForward.setPressed(false);
      //  mc.thePlayer.motionX *= 9;
      //  mc.thePlayer.motionZ *= 9;
        cameraOffsetX = 0;
        cameraOffsetY = 0;
        cameraOffsetZ = 0;
        cameraYawOffset = 0;
        cameraPitchOffset = 0;

        PingSpoofComponent.dispatch();

      //  lastPosX = mc.thePlayer.posX;
     //   lastPosY = mc.thePlayer.posY;
      //  lastPosZ = mc.thePlayer.posZ;
        mc.thePlayer.capabilities.allowFlying = mc.playerController.getCurrentGameType().isCreative();

    }

    @EventLink
    private final Listener<MoveEvent> moveEventListener = event -> {
        if (mc.thePlayer.offGroundTicks >= 6) {

        }
    };

}