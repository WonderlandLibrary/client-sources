package com.alan.clients.module.impl.movement.longjump;



import com.alan.clients.Client;
import com.alan.clients.component.impl.player.*;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.component.impl.render.PercentageComponent;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostMotionEvent;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.*;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;

import java.util.ArrayList;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class WatchdogLongJump extends Mode<LongJump> {

    public WatchdogLongJump(String name, LongJump parent) {
        super(name, parent);

    }
    private final ArrayList<Packet<?>> packets = new ArrayList<>();

    private boolean active, receiving, buffer;
    int veloY = -1;

    double motiony =-1;
    private int jumps, ticks;

    @Override
    public void onEnable() {


        MoveUtil.stop();
        active = false;
        receiving = false;
        veloY = mc.thePlayer.inventory.currentItem;
        buffer = false;
        if (getItem() == -1) {
            ChatUtil.display("you need a projectile in your hotbar for this");
            return;
        }

        int itemSlot = getItem();
        if (itemSlot != -1) {

            mc.thePlayer.inventory.currentItem = itemSlot;
        } else{
            ChatUtil.display("you need a projectile in your hotbar for this");
        }

        ItemDamageComponent.damage(false);

        jumps = 0;
        ticks = 0;

    }

    private int getItem() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == Items.bow) {
                return i;
            }
        }

        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == Items.fishing_rod) {
                return i;
            }
        }

        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == Items.egg || getStackInSlot != null && getStackInSlot.getItem() == Items.snowball) {
                return i;
             }
        }

        return -1;
    }


    @Override
    public void onDisable() {

        MoveUtil.stop();
        if (veloY != -1) {
            mc.thePlayer.inventory.currentItem = veloY;
//ChatUtil.display(veloY);
        }
        packets.forEach(PacketUtil::receive);
        packets.clear();
    }

    @EventLink(EventBusPriorities.HIGH)
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (receiving) return;

        switch (event.getPacket()) {
            case S12PacketEntityVelocity velocity -> {

                if (!event.isCancelled() && velocity.getEntityID() == mc.thePlayer.getEntityId() && !buffer) {
                    motiony = mc.thePlayer.motionY;
                    Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);

                    mc.thePlayer.motionX = motion.getX();
                    mc.thePlayer.motionZ = motion.getY();
                    mc.thePlayer.motionY = motiony;
                    //  motiony =  velocity.getMotionY() / 8000.0D;
                    event.setCancelled();
                    active = true;
                    packets.add(velocity);
                  //    ChatUtil.display("yes");



                } else if(!event.isCancelled() && velocity.getEntityID() == mc.thePlayer.getEntityId()){
                    event.setCancelled();
                  //  mc.thePlayer.motionY =  velocity.getMotionY() / 8000.0D;
                }

            }

            case S32PacketConfirmTransaction transaction -> {
                if (active) {
                   // PingSpoofComponent.spoof(9999999, false, false, false, false, true);
                    packets.add(transaction);
                   event.setCancelled();
                }

            }
            default -> {
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (mc.thePlayer.ticksSinceVelocity == 1 && buffer) {
             // mc.thePlayer.motionX *= 1.05;
            //  mc.thePlayer.motionZ *= 1.05;
          //  MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.75 : 0.7) - Math.random() / 10000f);
//ChatUtil.display("erm");
        }

        if (mc.thePlayer.ticksSinceVelocity > 7 && buffer) {


         //   MoveUtil.strafe((mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.8 : 0.7) - Math.random() / 10000f);

        }

        if (mc.thePlayer.ticksSinceVelocity <= 120 && mc.thePlayer.ticksSinceVelocity >=100) {
           // mc.thePlayer.motionY += 0.001;

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
             //   mc.thePlayer.motionX *= 1.038;
            //    mc.thePlayer.motionZ *= 1.038;
            } else {
                if (mc.thePlayer.ticksSinceVelocity == 12 || mc.thePlayer.ticksSinceVelocity == 13) {
                 //   mc.thePlayer.motionX *= 1.1;
                  //  mc.thePlayer.motionZ *= 1.1;
                }

             //   mc.thePlayer.motionX *= 1.019;
             //   mc.thePlayer.motionZ *= 1.019;
            }
        }

      //  ChatUtil.display(mc.thePlayer.motionY);

        if (jumps < 4 && mc.thePlayer.offGroundTicks == 9) {
         //   ChatUtil.display("d");
          //  mc.thePlayer.motionY = MoveUtil.UNLOADED_CHUNK_MOTION;
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {


        /*
        if (mc.thePlayer.ticksSinceVelocity == 0 && jumps < 4) {
            getModule(LongJump.class).toggle();
            NotificationComponent.post("Long Jump", "Disabled Long Jump due to damage before initial jump.", 5000);
        }

         */

        if (getModule(LongJump.class).autoDisable.getValue() && !PacketlessDamageComponent.isActive() && mc.thePlayer.onGround && jumps>=999 && !active ) {
            getModule(LongJump.class).toggle();
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.offGroundTicks = 0;
        }
        if(mc.thePlayer.ticksSinceVelocity == 0 && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            MoveUtil.strafe();
            mc.thePlayer.motionX *= 2.47;
            mc.thePlayer.motionZ *= 2.47;
        } else if(mc.thePlayer.ticksSinceVelocity == 0){

            MoveUtil.strafe();
            mc.thePlayer.motionX *= 2.47;
            mc.thePlayer.motionZ *= 2.47;
//
}

        if(mc.thePlayer.ticksSinceVelocity == 2){



            mc.thePlayer.motionY*=1.195;

        }

        if(mc.thePlayer.ticksSinceVelocity == 3){

        }

      //  mc.thePlayer.motionZ *= 1.003;
      //  mc.thePlayer.motionX *= 1.003;
        if(mc.thePlayer.ticksSinceVelocity == 1 && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

            mc.thePlayer.motionX *= 1.07;
            mc.thePlayer.motionZ *= 1.07;
        } else if(mc.thePlayer.ticksSinceVelocity == 1){

            mc.thePlayer.motionX *= 1.07;
            mc.thePlayer.motionZ *= 1.07;
        }



        if(mc.thePlayer.offGroundTicks == 2){

            mc.thePlayer.motionX *= 1.008;
            mc.thePlayer.motionZ *= 1.008;
        }



        if(mc.thePlayer.ticksSinceVelocity == 3){

            mc.thePlayer.motionX *= 1.001;
            mc.thePlayer.motionZ *= 1.001;
        }


        if(mc.thePlayer.ticksSinceVelocity > 1 &&buffer){

           mc.thePlayer.motionY += .028;
        }

        if(mc.thePlayer.onGround && jumps >= 999 && active){
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
            mc.thePlayer.jump();
        }

        if (mc.thePlayer.offGroundTicks == 1 ) {
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed) ) {
                MoveUtil.strafe(0.48);

            } else {
                MoveUtil.strafe(0.33);
            }

          //  MoveUtil.strafe();

        }
        if(mc.thePlayer.offGroundTicks == 9){

        }
        if(mc.thePlayer.offGroundTicks == 10) {
            mc.thePlayer.motionX *= 1.01;
            mc.thePlayer.motionZ *= 1.01;
        }
        if (mc.thePlayer.offGroundTicks == 3) {


                mc.thePlayer.motionZ *= 1.003;
                mc.thePlayer.motionX *= 1.003;



        }


        boolean speed = mc.thePlayer.isPotionActive(Potion.moveSpeed);
        double currentSpeed = MoveUtil.speed();

        switch (mc.thePlayer.offGroundTicks) {

        }

    //    MoveUtil.useDiagonalSpeed();
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        if(mc.thePlayer.hurtTime>0){
            jumps = 999;
        }
        if ( jumps < 999) {
            MoveUtil.stop();
        }
//ChatUtil.display(jumps);

     if(active && mc.thePlayer.offGroundTicks == 11){
mc.thePlayer.ticksSinceVelocity = 0;

buffer = true;
            active = false;

            receiving = true;
            //  MoveUtil.strafe(MoveUtil.getbaseMoveSpeed() + Math.random() / 10);
            //   mc.thePlayer.jump();
            Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
           // double motiony = mc.thePlayer.motionY;

            packets.forEach(PacketUtil::receive);

           // MoveUtil.strafe();
      //   ChatUtil.display("recieved");

            packets.clear();
       //  mc.thePlayer.motionX = motion.getX();
       //  mc.thePlayer.motionZ = motion.getY();


    //     ChatUtil.display("jump");
           //   mc.thePlayer.motionY = motiony;
            receiving = false;
        }
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (jumps == 4) {



           // mc.timer.timerSpeed = 0.5f;
        //    PacketUtil.send(new C03PacketPlayer(true));
         //   ChatUtil.display(jumps);
        }
    };

}
