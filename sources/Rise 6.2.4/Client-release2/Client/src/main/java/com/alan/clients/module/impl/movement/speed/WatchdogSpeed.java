package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.player.scaffold.tower.WatchdogTower;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.module.impl.combat.velocity.StandardVelocity;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class WatchdogSpeed extends Mode<Speed> {
    public WatchdogSpeed(String name, Speed parent) {
        super(name, parent);
    }
    private final ModeValue mode = new ModeValue("Type", this)
            .add(new SubMode("Strafe"))
            .add(new SubMode("Low Strafe"))
            .setDefault("Low Strafe");


    public final BooleanValue extraStrafe = new BooleanValue("Air Strafe", this, true);
    public final BooleanValue fallStrafe = new BooleanValue("Glide Strafe", this, true);
    public final BooleanValue frictionOverride = new BooleanValue("Friction Override", this, true);
    public final BooleanValue uhcMode = new BooleanValue("UHC Mode", this, false);
    public final BooleanValue motion = new BooleanValue("Alternate Motion", this, false);

    boolean ice =false;

    boolean slab =false;
    boolean disable2 =false;
    boolean disable3 =false;
    boolean disable4 =false;
    private double speed;
    private int boostTicks;
    private int amount;
    private boolean disable;
    private int buffer;
    private boolean reverse;
    private boolean attempt;

    private boolean enable;

    private boolean recentlyCollided;
    private static float lastInterpolatedYaw = 0.0f;
    private static final float YAW_STEP = 8.0f;



    @Override
    public void onEnable() {

        if (!BadPacketsComponent.bad(true, true, false, true, true) && !(getModule(Scaffold.class).isEnabled())) {
            Random random = new Random();
            float hitX = random.nextFloat();
            float hitZ = random.nextFloat();
            PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), EnumFacing.UP.getIndex(), new ItemStack(Items.water_bucket), hitX, 1.0F, hitZ));
        }
        slab = false;
        disable4 =false;

        if(Client.INSTANCE.getModuleManager().get(Scaffold.class).isEnabled()){


        }
disable3 = false;
        if((getModule(Scaffold.class).sameY.getValue().getName().equals("On")) && Client.INSTANCE.getModuleManager().get(Scaffold.class).isEnabled()){
          //  recentlyCollided = true;
    MoveUtil.stop();
        }

        if (mc.thePlayer.onGround) {
          //  mc.thePlayer.motionY = .42;
        }
        attempt = false;
        if(mc.thePlayer.offGroundTicks>2){
            disable = true;
        }

    }

    @Override
    public void onDisable() {
        WatchdogTower.ticks = 0;
        WatchdogTower.tickCounter = 0;
        ice = false;
        disable4 =false;
        if(Client.INSTANCE.getModuleManager().get(Scaffold.class).isEnabled()){
            mc.thePlayer.motionX *= .85;
        mc.thePlayer.motionZ *= .85;
    }

        disable = false;
        mc.thePlayer.omniSprint = false;
        //     attempt = falsee;

    }

    @EventLink
    public final Listener<MoveInputEvent> onInput = inputEvent -> inputEvent.setJump(false);
    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if(mc.thePlayer.isInWater() && mc.thePlayer.isInWeb && mc.thePlayer.isInLava() ) {
           disable = true;
            return;
        }


        if(getModule(Scaffold.class).isEnabled()){
            disable2 = true;
        }



        if(mc.thePlayer.isCollidedHorizontally || mc.thePlayer.ticksSinceTeleport < 2){
            recentlyCollided = true;
           // disable d= true;
            boostTicks = mc.thePlayer.ticksExisted+9;
        }
        if (!mc.thePlayer.isCollidedHorizontally && (mc.thePlayer.ticksExisted > boostTicks)){

            recentlyCollided = false;

        }

        if (PlayerUtil.blockRelativeToPlayer(0, -1.0, 0) == (Blocks.packed_ice) || PlayerUtil.blockRelativeToPlayer(0, -1.0, 0) == (Blocks.ice)) {

           ice = true;
        } else if(mc.thePlayer.offGroundTicks>1){
            ice = false;
        }


if(mc.thePlayer.onGround){
    disable3 = false;
}
        if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air) {
            disable = false;
        }

        if(mc.thePlayer.isCollidedVertically && !mc.thePlayer.onGround && (mode.getValue().getName()=="Low Strafe") && PlayerUtil.isBlockOver(2.0, true)){
            disable = true;
        }

        if (mc.thePlayer.ticksSinceStep <= 10 ) {
         //   disable = false;
        }

        double posY = event.getPosY();
        if (Math.abs(posY - Math.round(posY)) > 0.03 && mc.thePlayer.onGround) {
   slab = true;
        } else if(mc.thePlayer.onGround){
            slab = false;
        }


        if (PlayerUtil.isBlockOver(0, true)){
           // disable = true;
        }



        if (FallDistanceComponent.distance > 1.4) {
         //  disable = true;
            //  ChatUtil.display("slab");
        }






        if (((mc.thePlayer.onGround) && !(Math.abs(posY - Math.round(posY)) > 0.03)) && !disable2) {
            // mc.thePlayer.motionY = 0.03495f;
         //   event.setPosY(event.getPosY() +  0.00001);
        }

        if(mc.thePlayer.offGroundTicks == 5 && !disable2){
            disable2 = false;
        }


        // event.setYaw(lastInterpolatedYaw);
    };

    @EventLink
    public final Listener<MoveEvent> moveEventListener = moveEvent -> {
        if (mc.thePlayer.motionY < 0) {
           //  rawSetSpeed(moveEvent, 10.75 / 200, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
         }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if(mc.thePlayer.isInWater() && mc.thePlayer.isInWeb && mc.thePlayer.isInLava()) {
            disable = true;
            return;
        }
        if(getModule(Scaffold.class).isEnabled() && mc.thePlayer.isPotionActive(Potion.moveSpeed)){
            recentlyCollided = true;
            // disable d= true;s
            boostTicks = mc.thePlayer.ticksExisted+8;
        }
        if((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest)  && (mode.getValue().getName()=="Low Strafe") && getModule(InventoryMove.class).isEnabled()) {
            attempt = true;

        } else if((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest) && getModule(InventoryMove.class).isEnabled()){
            return;
        }
        switch (mode.getValue().getName()) {
            case "Strafe":
                MoveUtil.useDiagonalSpeed();


                if (mc.thePlayer.onGround && disable) {
                    mc.thePlayer.motionY = .42;
                }

                mc.thePlayer.omniSprint = true;

                if (mc.thePlayer.onGround && !mc.thePlayer.isPotionActive(Potion.moveSpeed) && MoveUtil.isMoving() && !recentlyCollided) {
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                   // MoveUtil.strafe(0.29);


                    mc.thePlayer.jump();


                } else if(mc.thePlayer.onGround && MoveUtil.isMoving()  && !recentlyCollided) {


                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                    mc.thePlayer.jump();


                } else if((mc.thePlayer.onGround && MoveUtil.isMoving())){
                    MoveUtil.strafe(MoveUtil.getbaseMoveSpeed());
                    mc.thePlayer.jump();
                }

                if (mc.thePlayer.offGroundTicks == 1 && (mc.thePlayer.lastLastTickPosY - mc.thePlayer.lastTickPosY) > -.43 && !disable && !recentlyCollided) {

                   // mc.thePlayer.motionY += 0.015;


                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                        MoveUtil.strafe(0.48);

                    } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1){
                        MoveUtil.strafe(0.4);
                    } else{
                        MoveUtil.strafe(0.33);
                    }

                    MoveUtil.strafe();

                }

                if (mc.thePlayer.offGroundTicks == 2 && extraStrafe.getValue() && !disable) {
                    double motionX2 = mc.thePlayer.motionX;
                    double motionZ2 = mc.thePlayer.motionZ;

//MoveUtil.strafe();
  mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 1 + motionZ2*2) / 3;
                    mc.thePlayer.motionX = (mc.thePlayer.motionX * 1 + motionX2*2) / 3;

                    double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                    if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() - .05 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !recentlyCollided) {
                        //  MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance()-.05);
                        //    ChatUtil.display("2");
                    }


                }

                if (mc.thePlayer.offGroundTicks == 3 ) {

                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    //    mc.thePlayer.motionZ *= 1.001;
                    //    mc.thePlayer.motionX *= 1.001;
                    } else {
                   //     mc.thePlayer.motionZ *= 1.001;
                   //     mc.thePlayer.motionX *= 1.001;
                    }


                }


                if (!recentlyCollided && (mc.thePlayer.offGroundTicks == 2 || mc.thePlayer.offGroundTicks == 3 || mc.thePlayer.offGroundTicks == 4 || mc.thePlayer.offGroundTicks == 5 || mc.thePlayer.offGroundTicks == 6 || mc.thePlayer.offGroundTicks == 7 || mc.thePlayer.offGroundTicks == 8) && extraStrafe.getValue() && !recentlyCollided) {
               //     MoveUtil.partialStrafePercent(.69);


                }


                if (mc.thePlayer.offGroundTicks == 8 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY * 4.7, 0) != Blocks.air) {
                    //      double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                    //   if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance()-.01  || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0)) {
                    //      MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance()-.01);

                    //   }


                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        mc.thePlayer.motionZ *= 1.001;
                        mc.thePlayer.motionX *= 1.001;

                    } else {
                        mc.thePlayer.motionZ *= 1.001;
                        mc.thePlayer.motionX *= 1.001;

                    }
                }

                if (mc.thePlayer.offGroundTicks == 9 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY * 3.5, 0) != Blocks.air) {
                    if(!disable){


                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                            mc.thePlayer.motionZ *= 1.003;
                                 mc.thePlayer.motionX *= 1.003;
                        } else if(!getModule(Scaffold.class).isEnabled()){
                            mc.thePlayer.motionZ *= 1.003;
                           mc.thePlayer.motionX *= 1.003;
                        }



                    }
                    if(fallStrafe.getValue() && !recentlyCollided) {
                        double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                        if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() - .02 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !disable && !recentlyCollided) {
                         //   MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - .02);
                            //  ChatUtil.display("9");
                        }

                        mc.thePlayer.motionY += 0.075;
                        MoveUtil.strafe();
                    }

                }

                if (mc.thePlayer.offGroundTicks == 10 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY * 2, 0) != Blocks.air) {
                    if(!disable && !recentlyCollided) {

                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) ) {
                            mc.thePlayer.motionZ *= 1.005;
                            mc.thePlayer.motionX *= 1.005;
                        } else {
                            mc.thePlayer.motionZ *= 1.005;
                            mc.thePlayer.motionX *= 1.005;
                        }
                    }
if(fallStrafe.getValue()  && !recentlyCollided){
    double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !disable && !recentlyCollided) {
        MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() );
        //     ChatUtil.display("10");
    }
    MoveUtil.strafe();

}

                }

                if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air ) {
                 //   ChatUtil.display("11");
                    MoveUtil.strafe();
                    if(mc.thePlayer.offGroundTicks == 11) {
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                            MoveUtil.strafe(0.45);
                        } else if(mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1){
                            MoveUtil.strafe(0.375);
                        } else{
                            MoveUtil.strafe(0.31);
                        }
                    }


                }
                double baseSpeed = MoveUtil.getbaseMoveSpeed()/2.29;
                speed = Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ)));
                //   mc.thePlayer.motionZ = newMotionValues[1];
                if(speed<.0125 && frictionOverride.getValue()){
                    MoveUtil.strafe();
             //       ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }
                if(!mc.thePlayer.onGround && !(mc.thePlayer.offGroundTicks == 11) && !(mc.thePlayer.offGroundTicks==1)){
                    // MoveUtil.strafe();
                    // MoveUtil.partialStrafePercent(50);
                    //  mc.thePlayer.motionX = (mc.thePlayer.motionX * 4 + motionX2) / 6;
                    //  mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 4 + motionZ2) / 6;
                }
                double motionX2 = mc.thePlayer.motionX;
                double motionZ2 = mc.thePlayer.motionZ;


                if(speed>.0125 && speed<MoveUtil.getbaseMoveSpeed() && frictionOverride.getValue()){

                    //  MoveUtil.partialStrafePercent(50);
                    // mc.thePlayer.motionX = (mc.thePlayer.motionX * 3 + motionX2) / 4;
                    //   mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 3 + motionZ2) / 4;
                  //  MoveUtil.partialStrafePercent(3.5);
               //     ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }

                // ChatUtil.display(disable);
                break;



            case "Low Strafe":
                MoveUtil.useDiagonalSpeed();
                if (PlayerUtil.isBlockOver(0.5, true)){
                 //   disable = true;
                }



                if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.onGround) {
                    if(!(getModule(Scaffold.class).isEnabled()) && !recentlyCollided){
                        MoveUtil.strafe(fallStrafe.getValue() ? MoveUtil.getAllowedHorizontalDistance() : MoveUtil.getAllowedHorizontalDistance() * 0.994);
                        mc.thePlayer.jump();
                    } else if((getModule(Scaffold.class).isEnabled()) && !recentlyCollided){
                        MoveUtil.strafe(0.29);
                        mc.thePlayer.jump();
                    } else{
                        MoveUtil.strafe(0.29);
                        mc.thePlayer.jump();
                    }




                } else if (mc.thePlayer.onGround) {

                    if(!recentlyCollided) {
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());

                    }else if((getModule(Scaffold.class).isEnabled())){
                        MoveUtil.strafe(.23);
                    } else{
                      //  ChatUtil.display("s");
                        MoveUtil.strafe(MoveUtil.getbaseMoveSpeed());
                    }
                    mc.thePlayer.jump();
                }

                if (mc.thePlayer.offGroundTicks == 1 && !disable ) {
disable4 = true;
                    mc.thePlayer.motionY +=0.057f;



                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)  && !(getModule(Scaffold.class).isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()) &&  mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2 && !disable && !recentlyCollided) {
                        MoveUtil.strafe(0.48);

                    } else  if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2 ){
                        MoveUtil.strafe(0.4);

                    }  else if(mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1){
                        MoveUtil.strafe(0.405);
                    } else{
                        MoveUtil.strafe(0.33);
                    }





                }


                if (mc.thePlayer.offGroundTicks == 2 && !disable && extraStrafe.getValue()) {
                    double motionX3 = mc.thePlayer.motionX;
                    double motionZ3 = mc.thePlayer.motionZ;
                 //   MoveUtil.strafe();
                    mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 1 + motionZ3*2) / 3;
                    mc.thePlayer.motionX = (mc.thePlayer.motionX * 1 + motionX3*2) / 3;
                 //   mc.thePlayer.motionY +=0.025f;
                }


                if (mc.thePlayer.offGroundTicks == 3  && !disable) {
                    mc.thePlayer.motionY -= 0.1309f;
                }

                if (mc.thePlayer.offGroundTicks == 4 && !disable) {
                //
                    mc.thePlayer.motionY -= 0.2;
                }





           //     ChatUtil.display(mc.thePlayer.offGroundTicks);

                if ((mc.thePlayer.offGroundTicks > 1 && mc.thePlayer.offGroundTicks < 6) && mc.thePlayer.isPotionActive(Potion.moveSpeed) && extraStrafe.getValue()) {
                  //  MoveUtil.partialStrafePercent(.25);
                } else if ((mc.thePlayer.offGroundTicks > 1 && mc.thePlayer.offGroundTicks < 6) && extraStrafe.getValue()) {
               //     MoveUtil.partialStrafePercent(.1);
                }


                if (mc.thePlayer.offGroundTicks == 6 && !disable && (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY*3, 0) != Blocks.air && fallStrafe.getValue())) {
                    mc.thePlayer.motionY += 0.075;
                    MoveUtil.strafe();
                //    ChatUtil.display("6");
                    double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                    if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !disable && (!recentlyCollided && mc.thePlayer.isPotionActive(Potion.moveSpeed)) && !getModule(Scaffold.class).isEnabled() ) {
                        MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - 0.01);

                    } else if(!disable &&  !getModule(Scaffold.class).isEnabled() && (hypotenuse < MoveUtil.getAllowedHorizontalDistance() || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0)){
                        MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - 0.05);
                    }
                    //   MoveUtil.strafe();
                }

                if( mc.thePlayer.offGroundTicks<7 &&  (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0)!= Blocks.air) && mc.thePlayer.isPotionActive(Potion.moveSpeed) && !slab){
//disable = true;


                //    ChatUtil.display("7"+Math.random());
                    boostTicks = mc.thePlayer.ticksExisted+9;
recentlyCollided = true;
            }

                if (mc.thePlayer.offGroundTicks == 7 && !disable && (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY*2, 0) != Blocks.air) && !getModule(Scaffold.class).isEnabled()) {
                    MoveUtil.strafe(fallStrafe.getValue() ? MoveUtil.speed() : MoveUtil.getAllowedHorizontalDistance() * 1.1);
                   // ChatUtil.display("7");
                    //   mc.thePlayer.motionY+= 0.075;

                }

                if (mc.thePlayer.offGroundTicks == 9) {


                }
               //   ChatUtil.display("airtick: "+mc.thePlayer.offGroundTicks+"motion: "+ mc.thePlayer.motionY);


                if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && !disable && fallStrafe.getValue() && !uhcMode.getValue()   && (mc.thePlayer.offGroundTicks>7 ) && !disable3) {
                 //   ChatUtil.display(mc.thePlayer.offGroundTicks);
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * 1.079);
                    disable3 = true;
                } else if(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && !disable && !uhcMode.getValue() && mc.thePlayer.offGroundTicks>6  && !disable3){
                    if(mc.thePlayer.offGroundTicks == 7){
                   //     ChatUtil.display(mc.thePlayer.offGroundTicks);
                     //   mc.thePlayer.motionY += 0.075;
                    }
                 //   ChatUtil.display(mc.thePlayer.offGroundTicks);
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                    disable3 = true;
                } else if(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && mc.thePlayer.offGroundTicks>5  && !disable3){

                    if(mc.thePlayer.offGroundTicks == 6 ){
                     //   ChatUtil.display(mc.thePlayer.offGroundTicks);
                    //    mc.thePlayer.motionY += 0.075;
                    }
              //
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                    disable3 = true;
                }

                if(getModule(Scaffold.class).isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()){
                    if (mc.thePlayer.onGround && attempt) {
                        getParent().toggle();


                        attempt = false;
                    }
                } else{
                    if (((disable ||(mc.thePlayer.offGroundTicks > 3)||mc.thePlayer.onGround) && attempt)) {
                        getParent().toggle();


                        attempt = false;
                    }
                }



                double speed2 = Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ)));
                //   mc.thePlayer.motionZ = newMotionValues[1];
                if(speed2<.0125 && frictionOverride.getValue()){
                    MoveUtil.strafe();
                 //   ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }
                if(!mc.thePlayer.onGround && !(mc.thePlayer.offGroundTicks == 11) && !(mc.thePlayer.offGroundTicks==1)){
                    // MoveUtil.strafe();
                    // MoveUtil.partialStrafePercent(50);
                    //  mc.thePlayer.motionX = (mc.thePlayer.motionX * 4 + motionX2) / 6;
                    //  mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 4 + motionZ2) / 6;
                }
                double motionX3 = mc.thePlayer.motionX;
                double motionZ3 = mc.thePlayer.motionZ;





                break;

        }


        if(MoveUtil.speed()<.45 ||mc.thePlayer.isPotionActive(Potion.moveSpeed)&& mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1 && MoveUtil.speed()<.55 || (mc.thePlayer.isPotionActive(Potion.moveSpeed)&& mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) && MoveUtil.speed()<.61){
            if(ice && mc.thePlayer.onGround && !disable ){

                // ChatUtil.display(MoveUtil.speed());
                mc.thePlayer.motionX *= 1.5;
                mc.thePlayer.motionZ *= 1.5;
            }

            if(ice && (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) == Blocks.ice|| PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) == Blocks.packed_ice )&& !disable && !disable   ){

                //  ChatUtil.display(MoveUtil.speed());
                mc.thePlayer.motionX *= 1.1;
                mc.thePlayer.motionZ *= 1.1;
            }

            if(ice && mc.thePlayer.offGroundTicks == 1 && !disable  ){

                //   ChatUtil.display(MoveUtil.speed());
                mc.thePlayer.motionX *= 1.25;
                mc.thePlayer.motionZ *= 1.25;
            }
            if(ice && mc.thePlayer.offGroundTicks > 1 && !disable &&  (mode.getValue().getName()=="Low Strafe")){

                //   ChatUtil.display(MoveUtil.speed());
                mc.thePlayer.motionX *= 1.015;
                mc.thePlayer.motionZ *= 1.015;
            }
        }
      //  ChatUtil.display("airtick: "+mc.thePlayer.offGroundTicks+"motion: "/*+  mc.thePlayer.motionY*/);


    };



    public static void rawSetSpeed(MoveEvent e, double speed, float forward, float strafing, float currentYaw) {
        if (forward == 0.0F && strafing == 0.0F) {
            return;
        }

        float targetYaw = currentYaw;
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            targetYaw += 180.0f;
        if (strafing > 0.0f)
            targetYaw -= strafingYaw;
        else if (strafing < 0.0f)
            targetYaw += strafingYaw;

        targetYaw = (targetYaw + 360) % 360;

        float yawDifference = targetYaw - lastInterpolatedYaw;
        yawDifference = (yawDifference + 180) % 360 - 180;

        if (Math.abs(yawDifference) < YAW_STEP) {
            lastInterpolatedYaw = targetYaw; //snap
        } else {
            lastInterpolatedYaw += Math.signum(yawDifference) * YAW_STEP;
        }
        lastInterpolatedYaw = (lastInterpolatedYaw + 360) % 360;

        double x = StrictMath.cos(Math.toRadians(lastInterpolatedYaw + 90.0));
        double z = StrictMath.cos(Math.toRadians(lastInterpolatedYaw));

        e.setPosX(x * speed);
        e.setPosZ(z * speed);
    }

    @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {
        if (!disable && (mode.getValue().getName()=="Low Strafe") && motion.getValue()) {
            event.setJumpMotion((float)(0.42001));


        } else if((mode.getValue().getName()=="Low Strafe") && motion.getValue()){
            event.setJumpMotion((float)(0.42001));

        }



    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {

        double attempt_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction()));
        double movement_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(mc.thePlayer.motionZ, mc.thePlayer.motionX)) - 90);
        if(extraStrafe.getValue() && (mode.getValue().getName()=="Strafe")){
                if (MathUtil.wrappedDifference(attempt_angle, movement_angle) > 90) {
                    //   ChatUtil.display("s");
                    if(mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                        MoveUtil.strafe(MoveUtil.speed(), (float) movement_angle - 180);
                    } else{
                        MoveUtil.strafe(MoveUtil.speed(), (float) movement_angle - 180);
                    }


            } else{
                    double motionX2 = mc.thePlayer.motionX;
                    double motionZ2 = mc.thePlayer.motionZ;
               //     MoveUtil.strafe();
                   //     mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 1 + motionZ2*20) / 21;
                   //    mc.thePlayer.motionX = (mc.thePlayer.motionX * 1 + motionX2*20) / 21;

            }
        } else if(extraStrafe.getValue()){
                if (MathUtil.wrappedDifference(attempt_angle, movement_angle) > 90) {
                    //   ChatUtil.display("s");
                    MoveUtil.strafe(MoveUtil.speed(), (float) movement_angle - 180);
                } else {
                    double motionX2 = mc.thePlayer.motionX;
                    double motionZ2 = mc.thePlayer.motionZ;

                  //  MoveUtil.strafe();
                 //   mc.thePlayer.motionZ = (mc.thePlayer.motionZ * 1 + motionZ2*20) / 21;
                 //   mc.thePlayer.motionX = (mc.thePlayer.motionX * 1 + motionX2*20) / 21;

                }
            }







    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        if ((event.getBlock() instanceof BlockIce || event.getBlock() instanceof  BlockPackedIce)){
//ice = true;

        } else if(mc.thePlayer.onGroundTicks>1){
        //    ice = false;
        }
//ChatUtil.display(ice);

    };



    @EventLink
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if ((mode.getValue().getName()=="Low Strafe") && (event.getKeyCode() == getParent().getKey() && !attempt)) {
            event.setCancelled();
            attempt = true;
        }


    };


}



