package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.AntiVoid;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import rip.vantage.commons.util.time.StopWatch;

public class WatchdogFireball extends Mode<LongJump> {
    public final BooleanValue boost = new BooleanValue("Boost", this, true);
    public WatchdogFireball(String name, LongJump parent) {
        super(name, parent);
    }

    private int lastSlot = -1;
    private int ticks = -1;
    private boolean setSpeed;
    public static boolean stopModules;
    private boolean sentPlace;
    private int initTicks;
    private boolean thrown;

    private boolean antivoid;

    StopWatch stopWatch = new StopWatch();
    @EventLink
    public final Listener<PacketSendEvent> packetSendEventListener = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack() != null
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack().getItem() instanceof ItemFireball) {
            thrown = true;
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() != mc.thePlayer.getEntityId()) {
                return;
            }
            if (thrown) {
                ticks = 0;
                setSpeed = true;
                thrown = false;
                stopModules = true;
            }
        }
    };

    @EventLink(Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> preMotionEventListener = event -> {

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }


        if (mc.thePlayer.hurtTime == 10){

           mc.thePlayer.motionY =1.1f;
        }

        if (mc.thePlayer.ticksSinceVelocity <= 80 && mc.thePlayer.ticksSinceVelocity >= 1 && (mc.thePlayer.ticksSinceVelocity % 1 == 0 || mc.thePlayer.ticksSinceVelocity <= 15)) {
//
            mc.thePlayer.motionY += 0.028f;
        }

        if(mc.thePlayer.ticksSinceVelocity==28){
//MoveUtil.strafe(0.39);

          if ( boost.getValue()){
              MoveUtil.strafe(0.42);
          }

         //   ChatUtil.display(mc.thePlayer.motionZ);
       //     MoveUtil.partialStrafePercent(0);
            mc.thePlayer.motionY = 0.16f;

        }

        if(mc.thePlayer.ticksSinceVelocity==33){
         //   MoveUtil.strafe(0.37);
        //    MoveUtil.strafe(0.32);
       //     ChatUtil.display(mc.thePlayer.motionZ);
            mc.thePlayer.motionY = -0.082f;
        }

        if(mc.thePlayer.ticksSinceVelocity==27){


           // ChatUtil.display(mc.thePlayer.motionZ);
        //    MoveUtil.strafe(0.39);
          //  mc.thePlayer.motionY =+ 0.01f;
        }
        if(mc.thePlayer.ticksSinceVelocity==22){

        }





        if(mc.thePlayer.ticksSinceVelocity >= 35 && mc.thePlayer.ticksSinceVelocity <= 50){
            MoveUtil.strafe();
            mc.thePlayer.posY = mc.thePlayer.posY+ .029f;

        }

        if(mc.thePlayer.ticksSinceVelocity >= 3 && mc.thePlayer.ticksSinceVelocity <= 50){
            MoveUtil.strafe();


        }




        if (initTicks == 0) {

            event.setYaw(mc.thePlayer.rotationYaw - 180);
            event.setPitch(89);
            int fireballSlot = getFireball();
            if (fireballSlot != -1 && fireballSlot != mc.thePlayer.inventory.currentItem) {
                lastSlot = mc.thePlayer.inventory.currentItem;
                mc.thePlayer.inventory.currentItem = fireballSlot;
            }
        } else{

        }
        if (initTicks == 1) {

            if (!sentPlace) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                sentPlace = true;

            }
        } else if (initTicks == 2) {

            if (lastSlot != -1) {
                mc.thePlayer.inventory.currentItem = lastSlot;
                lastSlot = -1;
            }
        }
        if (ticks > 1) {

            this.toggle();
            return;
        }
        if (setSpeed) {

            stopModules = true;
            this.setSpeed();
            ticks++;
        }
        if (initTicks < 3) {
            initTicks++;
        }

        if (setSpeed) {
            if (ticks > 1) {
                stopModules = setSpeed = false;
                ticks = 0;
                return;
            }
            stopModules = true;
            ticks++;
            setSpeed();
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {


   //ChatUtil.display(mc.thePlayer.ticksSinceVelocity +"motion: "+ mc.thePlayer.motionY);
        if (mc.thePlayer.ticksSinceVelocity <= 70 && mc.thePlayer.ticksSinceVelocity >= 1 && (mc.thePlayer.ticksSinceVelocity % 1 == 0 || mc.thePlayer.ticksSinceVelocity <= 15)) {

      mc.thePlayer.motionX *= 1.0003;
         mc.thePlayer.motionZ *= 1.0003;

        }



        if (mc.thePlayer.hurtTime == 10){
            //mc.thePlayer.motionY = mc.thePlayer.motionY+ .7f;
        }

        if (mc.thePlayer.ticksSinceVelocity == 1) {
            mc.thePlayer.motionX *= 1.15;
              mc.thePlayer.motionZ *= 1.15;

         //   mc.thePlayer.motionX *= 1.15;
          //  mc.thePlayer.motionZ *= 1.15;
        }


        if (mc.thePlayer.hurtTime == 8){

            mc.thePlayer.motionX *= 1.02;
            mc.thePlayer.motionZ *= 1.02;
        }

        if (mc.thePlayer.hurtTime == 7){
            mc.thePlayer.motionX *= 1.0004;
            mc.thePlayer.motionZ *= 1.0004;
        }

        if (mc.thePlayer.hurtTime == 6){
            mc.thePlayer.motionX *= 1.0004;
            mc.thePlayer.motionZ *= 1.0004;
        }

        if (mc.thePlayer.hurtTime == 5){
            mc.thePlayer.motionX *= 1.0004;
            mc.thePlayer.motionZ *= 1.0004;
        }

        if (mc.thePlayer.hurtTime <= 4 && !(mc.thePlayer.hurtTime == 0)){
            mc.thePlayer.motionX *= 1.0004;
            mc.thePlayer.motionZ *= 1.0004;
        }


    };

    public void onDisable() {

        MoveUtil.stop();
        if (lastSlot != -1) {
            mc.thePlayer.inventory.currentItem = lastSlot;
        }


        ticks = lastSlot = -1;
        setSpeed = stopModules = sentPlace = false;
        initTicks = 0;
    }

    public void onEnable() {

        if (getFireball() == -1) {
            ChatUtil.display("Could not find Fireball");
            this.toggle();
            return;
        }




        stopModules = true;
        initTicks = 0;
    }

    private void setSpeed() {

        MoveUtil.strafe(1.768f);

    }


    private int getFireball() {
        int a = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null && getStackInSlot.getItem() == Items.fire_charge) {
                a = i;
                break;
            }
        }
        return a;
    }
}