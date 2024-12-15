package com.alan.clients.module.impl.movement.phase;

import com.alan.clients.component.impl.render.SmoothCameraComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PushOutOfBlockEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Phase;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class VulcanPhase extends Mode<Phase> {

    private boolean teleport = false;

    private boolean speed2 = false;
    private boolean enable = true;
    private int teleportCount = 0;
    private int timer1 = 0;
    private int tickCounter = 0;
    private boolean lastRightClick = false;
    private boolean done = true;
    private boolean flag = true;

    public VulcanPhase(String name, Phase parent) {
        super(name, parent);
    }
    @Override
    public void onEnable() {
        speed2 = false;
        done = true;
        flag = true;
        timer1 = 0;
        teleportCount = 0;
        teleport = false;
       enable = true;
       if( mc.thePlayer.onGround ){
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            MoveUtil.stop();
        } else{
           ChatUtil.display("You must me on the ground to do this");
           getModule(Phase.class).toggle();
       }

    }

    @Override
    public void onDisable() {
        speed2 = false;
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        mc.thePlayer.cameraYaw = 0.1F;
if( timer1 > 25) {

    if(PlayerUtil.insideBlock()) {
if(mc.thePlayer.motionY>0){

}

        mc.thePlayer.onGround = false;

        }


}

        if(PlayerUtil.insideBlock()){
            timer1++;
        }


        if(done && timer1<25) {

            SmoothCameraComponent.setY();

        } else if(flag){

        }

if((PlayerUtil.insideBlock() && !enable && flag)){
    flag = false;
    ChatUtil.display("Phased");

        }

        if((PlayerUtil.insideBlock() && !enable)){

        }

    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (PlayerUtil.insideBlock()) {


            event.setBoundingBox(null);

            // Sets The Bounding Box To The Players Y Position.
            if (!(event.getBlock() instanceof BlockAir) && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }
        } else if (!teleport){


            if (event.getBlock() instanceof BlockAir && !mc.thePlayer.isSneaking()) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }
        }

        else if (teleport && !PlayerUtil.insideBlock()){
            ChatUtil.display("Disabled due to not being in a block");
            getModule(Phase.class).toggle();

        }
    };

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        int blockSlot = SlotUtil.findBlock();

        if(mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.hurtTime>0){
            mc.thePlayer.motionY=.99;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()){
            mc.thePlayer.motionY=-.4;
        } else if (!mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.insideBlock() &&  timer1 > 25){

            mc.thePlayer.motionY=0;

        }
        if(PlayerUtil.insideBlock()) {

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                event.setSpeed(((.0765*(1+(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) +.306));
            } else {
                event.setSpeed(.306);
            }

        }

        if (mc.thePlayer.onGround && enable && teleport){

mc.thePlayer.jump();
            teleport = false;
            enable = false;

        }
        if (mc.thePlayer.onGround && !teleport &&!enable) {

            if (mc.thePlayer.ticksExisted % 2 == 1|| !(mc.thePlayer.moveForward ==0 )) {

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
            teleport = true;
            teleportCount++;

        }

        if (teleportCount > 4) {

            done = false;

        } else{
            done = true;
        }

    };

    @EventLink
    public final Listener<PushOutOfBlockEvent> onPushOutOfBlock = event -> {
        event.setCancelled();
    };

}
