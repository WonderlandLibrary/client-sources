package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.ItemDamageComponent;
import com.alan.clients.component.impl.player.PacketlessDamageComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostMotionEvent;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.player.AntiFireBall;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import rip.vantage.commons.util.time.StopWatch;

import java.util.ArrayList;

public class WatchdogFlight extends Mode<Flight> {


    public WatchdogFlight(String name, Flight parent) {
        super(name, parent);
    }

    private int lastSlot = -1;
    private int ticks = -1;
    private boolean setSpeed;
    public static boolean stopModules;
    private boolean sentPlace;
    private int initTicks;
    private boolean thrown;
    private AntiFireBall antiFireBall;

    private final ArrayList<Packet<?>> packets = new ArrayList<>();
    StopWatch stopWatch = new StopWatch();
    private boolean active, receiving, buffer;
    int veloY = -1;
double speedMultiplier = 0;
    double motiony =-1;
    private int
            jumps;




    @Override
    public void onEnable() {

        antiFireBall = Client.INSTANCE.getModuleManager().get(AntiFireBall.class);
        jumps = 0;
        if (getFireball() == -1) {
            ChatUtil.display("Could not find Fireball");
            this.toggle();
            return;
        }




        stopModules = true;
        initTicks = 0;

          //  MoveUtil.stop();


        active = false;
        receiving = false;
        veloY = mc.thePlayer.inventory.currentItem;
        buffer = false;



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


        PingSpoofComponent.dispatch();



        if (veloY != -1) {
            mc.thePlayer.inventory.currentItem = veloY;
//ChatUtil.display(veloY);
        }
        packets.forEach(PacketUtil::receive);
        packets.clear();

        if (lastSlot != -1) {
            mc.thePlayer.inventory.currentItem = lastSlot;
        }



        ticks = lastSlot = -1;
        setSpeed = stopModules = sentPlace = false;
    }

    @EventLink(EventBusPriorities.HIGH)
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (receiving) return;

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
    public final Listener<PacketSendEvent> packetSendEventListener = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack() != null
                && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack().getItem() instanceof ItemFireball) {
            thrown = true;
            if (mc.thePlayer.onGround) {
            //   mc.thePlayer.jump();
            }
        }
    };


    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {






        if (mc.thePlayer.hurtTime == 10){
            //mc.thePlayer.motionY = mc.thePlayer.motionY+ .7f;
        }

        if (mc.thePlayer.ticksSinceVelocity == 1) {

            // Get the player's movement direction
            double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing)));

// Convert the angle to a value between 0 and 360
            double wrappedAngle = (angle + 360) % 360;

// Normalize the angle to fit a [0, 1] range where 0 = 0 degrees, 1 = 90 degrees, etc.
            double normalizedAngle = (wrappedAngle % 90) / 90.0;

// Use a quadratic-like curve to smoothly adjust speed (higher at multiples of 90 degrees)
            double speedMultiplier = 1.05 + 0.549 * (1 - 4 * normalizedAngle * (1 - normalizedAngle)); // Quadratic curve

// Set the speed dynamically
          //  ChatUtil.display(speedMultiplier);
            if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                MoveUtil.strafe(speedMultiplier);
            } else{
                MoveUtil.strafe(speedMultiplier);
            }







        }

        if (mc.thePlayer.ticksSinceVelocity == 20 && !mc.gameSettings.keyBindJump.isPressed()) {

//MoveUtil.strafe(.53);
//ChatUtil.display(MoveUtil.speed());
        }



        if(mc.thePlayer.offGroundTicks > 3 &&(mc.thePlayer.offGroundTicks < 33) ){
         //   MoveUtil.useDiagonalSpeed();
            MoveUtil.strafe();
          // ChatUtil.display(mc.thePlayer.offGroundTicks);
        }






        if(mc.thePlayer.offGroundTicks == 31 && !mc.gameSettings.keyBindJump.isKeyDown()){
         //  mc.thePlayer.motionY = -0.028;
        }

        if(mc.thePlayer.offGroundTicks == 32 && !mc.gameSettings.keyBindJump.isKeyDown()){
         //   mc.thePlayer.motionY = -0.06;
        }

        if(mc.thePlayer.offGroundTicks == 33 &&!mc.gameSettings.keyBindJump.isKeyDown()){
         //  mc.thePlayer.motionY = -0.085f;
        }



        if(mc.thePlayer.ticksSinceVelocity==33){
            //   MoveUtil.strafe(0.37);
            //    MoveUtil.strafe(0.32);
            //     ChatUtil.display(mc.thePlayer.motionZ);
         //   mc.thePlayer.motionY = -0.082f;
        }


      //  ChatUtil.display(mc.thePlayer.offGroundTicks + "mot: "+ MoveUtil.speed());



    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if(mc.thePlayer.offGroundTicks < 4){
         //   event.setSprinting(true);
        }


        if(mc.thePlayer.offGroundTicks<2 &&mc.gameSettings.keyBindJump.isPressed()){
            mc.gameSettings.keyBindJump.setPressed(false);
        } else{

        }


        if (mc.thePlayer.onGround) {
            // mc.thePlayer.motionY = 0.03495f;
          //    event.setPosY(event.getPosY() +  1e-13);
        }
        /*
        if (mc.thePlayer.ticksSinceVelocity == 0 && jumps < 4) {
            getModule(LongJump.class).toggle();
            NotificationComponent.post("Long Jump", "Disabled Long Jump due to damage before initial jump.", 5000);
        }

         */
      //  ChatUtil.display(jumps);
        if (getModule(LongJump.class).autoDisable.getValue() && !PacketlessDamageComponent.isActive() && mc.thePlayer.onGround && jumps>=999 && !active ) {

        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.offGroundTicks = 0;
        }
        if(mc.thePlayer.ticksSinceVelocity == 0 && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

            //   mc.thePlayer.motionX *= 2.47;
            //    mc.thePlayer.motionZ *= 2.47;
        } else if(mc.thePlayer.ticksSinceVelocity == 0){


            // mc.thePlayer.motionX *= 2.47;
            //  mc.thePlayer.motionZ *= 2.47;
//
        }
        if(mc.thePlayer.ticksSinceVelocity > 4 && mc.thePlayer.ticksSinceVelocity < 22 && buffer && mc.gameSettings.keyBindJump.isKeyDown() && !(getModule(KillAura.class).isEnabled())){
            mc.thePlayer.motionY = 0.35;
            MoveUtil.strafe();

            //    mc.thePlayer.motionY*=1.195;

        } else if(mc.thePlayer.ticksSinceVelocity > 0 && mc.thePlayer.ticksSinceVelocity < 21 && buffer){
            mc.thePlayer.motionY = 0.005;
            MoveUtil.strafe();

            //    mc.thePlayer.motionY*=1.195;

        } else{

            mc.thePlayer.motionY += 0.028;
        }



if(mc.thePlayer.onGround && buffer){
    getModule(Flight.class).toggle();
}

        if(mc.thePlayer.ticksSinceVelocity == 3){

        }

        //  mc.thePlayer.motionZ *= 1.003;
        //  mc.thePlayer.motionX *= 1.003;
        if(mc.thePlayer.ticksSinceVelocity == 1){

            //   mc.thePlayer.motionX *= 1.08;
            //  mc.thePlayer.motionZ *= 1.08;
        }



        if(mc.thePlayer.offGroundTicks == 2){

            //   mc.thePlayer.motionX *= 1.008;
            //  mc.thePlayer.motionZ *= 1.008;
        }



        if(mc.thePlayer.ticksSinceVelocity == 3){

            //   mc.thePlayer.motionX *= 1.001;
            //  mc.thePlayer.motionZ *= 1.001;
        }




        if(mc.thePlayer.onGround && jumps >= 999 && active){



         //   MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance()+.1);
        //  mc.thePlayer.motionY = .8;
            //   mc.thePlayer.motionY += .1;
        }



        if(jumps >= 999 && active && mc.thePlayer.offGroundTicks == 0){

// Get the player's movement direction
            double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing)));

// Convert the angle to a value between 0 and 360
            double wrappedAngle = (angle + 360) % 360;

// Normalize the angle to fit a [0, 1] range where 0 = 0 degrees, 1 = 90 degrees, etc.
            double normalizedAngle = (wrappedAngle % 90) / 90.0;

// Use a quadratic-like curve to smoothly adjust speed (higher at multiples of 90 degrees)
             speedMultiplier = 1.05 + 0.35 * (1 - 4 * normalizedAngle * (1 - normalizedAngle)); // Quadratic curve

// Set the speed dynamically
         //   ChatUtil.display(speedMultiplier +"off: "+ mc.thePlayer.offGroundTicks);
            if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                MoveUtil.strafe(speedMultiplier);
            } else{
                MoveUtil.strafe(speedMultiplier);
            }




        }

        if(jumps >= 999 && active){

            MoveUtil.strafe(speedMultiplier);




        }


      //  ChatUtil.display(mc.thePlayer.rotationYaw);

        if (mc.thePlayer.offGroundTicks == 1 ) {
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed) ) {
                //    MoveUtil.strafe(0.48);

            } else {
                //   MoveUtil.strafe(0.33);
            }

            //  MoveUtil.strafe();

        }
        if(mc.thePlayer.offGroundTicks == 9){

        }
        if(mc.thePlayer.offGroundTicks == 10) {
            //   mc.thePlayer.motionX *= 1.01;
            //  mc.thePlayer.motionZ *= 1.01;
        }
        if (mc.thePlayer.offGroundTicks == 3) {


            //    mc.thePlayer.motionZ *= 1.003;
            //    mc.thePlayer.motionX *= 1.003;



        }


        boolean speed = mc.thePlayer.isPotionActive(Potion.moveSpeed);
        double currentSpeed = MoveUtil.speed();

        switch (mc.thePlayer.offGroundTicks) {

        }


        if (initTicks == 0) {

            event.setYaw(mc.thePlayer.rotationYaw - 180);
            event.setPitch(89);
            int fireballSlot = getFireball();
         //   ChatUtil.display("Switch");
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
                if (antiFireBall != null && antiFireBall.isEnabled()) {
                    antiFireBall.delay = 1500;
                    antiFireBall.stopWatch.reset();
                }
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
        //    this.setSpeed();
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
        //    setSpeed();
        }
        //    MoveUtil.useDiagonalSpeed();
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        if(mc.thePlayer.hurtTime>0){
            jumps = 999;
        }
        if ( jumps < 999) {
        //    MoveUtil.stop();
        }
//ChatUtil.display(jumps);

        if(active && mc.thePlayer.offGroundTicks == 7){
            mc.thePlayer.ticksSinceVelocity = 0;

            buffer = true;
            active = false;

            receiving = true;
            //  MoveUtil.strafe(MoveUtil.getbaseMoveSpeed() + Math.random() / 10);
            //   mc.thePlayer.jump();
            Vector2d motion = new Vector2d(mc.thePlayer.motionX, mc.thePlayer.motionZ);
            // double motiony = mc.thePlayer.motionY;
            PingSpoofComponent.dispatch();
            MoveUtil.strafe();
            packets.forEach(PacketUtil::receive);
            mc.thePlayer.motionY = 0.005;
       //     ChatUtil.display("s");
          //  mc.thePlayer.motionX *= 1.03;
          //  mc.thePlayer.motionZ *= 1.03;
             MoveUtil.strafe(1.59f);

            //   ChatUtil.display("recieved");
        //    ChatUtil.display(MoveUtil.speed());
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

    private void setSpeed() {

        MoveUtil.strafe(1.768f);

    }
}