package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.StepEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class VulcanSpeed extends Mode<Speed> {
    public VulcanSpeed(String name, Speed parent) {
        super(name, parent);
    }
    private final NumberValue Speed = new NumberValue("Speed", this, 2, 1, 10, 1);

    ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("BHop"))
            .add(new SubMode("Lowhop"))
            .add(new SubMode("Funny"))
            .add(new SubMode("Use Disabler"))
            .add(new SubMode("Ground"))
            .add(new SubMode("Yport"))
            .setDefault("BHop");

    private int lastRightClick;
    private int stage;
    private double moveSpeed;

    private int jump = 0;

    private double lastDeltaX = 0.0;
    private double lastDeltaZ = 0.0;
    private boolean wasSprinting = false;

    @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {
        jump++;
    };

    @Override
    public void onDisable() {
        if (mode.getValue().getName().equals("Ground")) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * 0.6 - 0.02);
        }
    }

    @EventLink
    public final Listener<StepEvent> onStep = event -> {
        switch (mode.getValue().getName()) {
            case "BHop":
                MoveUtil.strafe(0.22);

        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mode.getValue().getName().equals("Bhop")) {
            event.setSprinting(true);
        }
        if (mode.getValue().getName().equals("Ground")) {
if(mc.thePlayer.isJumping ){

   mc.thePlayer.motionX *= .75;
    mc.thePlayer.motionZ *= .75;
}


            if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
                double speed = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);

                boolean boost = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0) >= 2;
                switch (stage) {
                    case 1:
                        moveSpeed = 0.58f;
                        speed = boost ? speed + 0.2 : 0.487;
                        event.setOnGround(true);
                        break;
                    case 2:
                        speed = boost ? speed * 0.71 : .197;
                        moveSpeed -= 0.0784f;
                        event.setOnGround(false);
                        break;
                    default:
                        stage = 0;
                        speed /= boost ? 0.64 : 0.58;
                        event.setOnGround(true);
                        break;
                }

                MoveUtil.strafe(speed);
                stage++;
                event.setPosY(event.getPosY() + moveSpeed);
            } else {
                stage = 0;
            }
        }
    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        if (mode.getValue().getName().equals("Use Disabler")) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.42f;
            }

            int speed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0;
            boolean nextGround = mc.thePlayer.offGroundTicks == 11;
            boolean ground = mc.thePlayer.onGround;

            switch (speed) {
                case 0:
                    MoveUtil.strafe(nextGround ? 0.3225 : ground ? 0.6505 : 0.36);
                    break;

                case 1:
                    MoveUtil.strafe(nextGround ? 0.3975 : ground ? 0.7255 : 0.4275);
                    break;

                default:
                    MoveUtil.strafe(nextGround ? 0.4725 : ground ? 0.8005 : 0.495);
                    break;
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

        if (!MoveUtil.isMoving()) {
            return;
        }

        switch (mode.getValue().getName()) {

            case "Yport":
                if (MoveUtil.speed() < 0.22) {

                    MoveUtil.strafe(0.22);

                }

                double speed5 = Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ)));
                //   mc.thePlayer.motionZ = newMotionValues[1];
                if(speed5<.022){
                    MoveUtil.strafe();
                    //       ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }

                if((mc.thePlayer.hurtTime>0 || mc.thePlayer.ticksSinceVelocity<40) && getModule(Velocity.class).mode.getValue().getName()=="Vulcan"){



                    MoveUtil.strafe();
                } else{

                }
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.ticksSinceVelocity > 11) {
                        MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.433));
                    } else if (mc.thePlayer.ticksSinceVelocity > 11) {
                        //  ChatUtil.display("h");
                        MoveUtil.strafe(0.433);
                    } else{

                        MoveUtil.strafe();
                    }

                    mc.timer.timerSpeed = 1.004F;
                }
                if (mc.thePlayer.offGroundTicks == 1) {
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.ticksSinceVelocity > 11) {
                        MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.308));
                    } else if (mc.thePlayer.ticksSinceVelocity > 11) {
                        //  ChatUtil.display("h");
                        MoveUtil.strafe(0.308);
                    } else{

                        MoveUtil.strafe();
                    }




                }

                if (mc.thePlayer.offGroundTicks == 2) {
                    //   mc.thePlayer.jump();
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.ticksSinceVelocity > 11) {
                        MoveUtil.strafe((.053 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.3035));
                    } else if (mc.thePlayer.ticksSinceVelocity > 11) {
                        //  ChatUtil.display("h");
                        MoveUtil.strafe(0.3035);
                    } else{

                        MoveUtil.strafe();
                    }


                }





                break;
            case "Lowhop":
                if((mc.thePlayer.hurtTime>0 || mc.thePlayer.ticksSinceVelocity<40) && getModule(Velocity.class).mode.getValue().getName()=="Vulcan"){



                    MoveUtil.strafe();
                } else{

                }
                if (MoveUtil.speed() < 0.22) {

                    MoveUtil.strafe(0.22);

                }


              //  MoveUtil.partialStrafePercent(1);
                double baseSpeed = MoveUtil.getbaseMoveSpeed()/2.29;
                double speed3 = Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ)));
                //   mc.thePlayer.motionZ = newMotionValues[1];
                if(speed3<.022){
                    MoveUtil.strafe();
                    //       ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }


                switch (mc.thePlayer.offGroundTicks) {

                    case 5:
                        if ((jump % 4 == 1)) {
                            MoveUtil.strafe();
                        }
                        break;
                    case 0:
                        mc.thePlayer.jump();
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.ticksSinceVelocity > 11) {
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.485));
                        } else if (mc.thePlayer.ticksSinceVelocity > 11) {
                      //  ChatUtil.display("h");
                        MoveUtil.strafe(0.485);
                    } else{
                        MoveUtil.strafe();
                    }
                        break;

                    case 9:
                        if (!(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY,
                                0) instanceof BlockAir)) {
                            MoveUtil.strafe();
                        }
                        MoveUtil.strafe();
                        break;

                    case 8:

                        MoveUtil.strafe();
                        break;
                    case 2:
                        if (!(jump % 4 == 1) && !(mc.thePlayer.isCollidedVertically)) {
                            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
                        }
                        break;

                    case 1:
                        MoveUtil.strafe();
                        break;

                    case 4:
                        if (jump % 4 == 1 || mc.thePlayer.isCollidedVertically) {
                            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 4);
                        } else {

                        }
                        break;

                }
                burstMovement(mc.thePlayer.posZ, mc.thePlayer.posX, mc.thePlayer.isSprinting(), mc.thePlayer.ticksSprint, mc.thePlayer.offGroundTicks);

                break;

            case "BHop":
             //   MoveUtil.partialStrafePercent(1);
                    if (MoveUtil.speed() < 0.22) {

                        MoveUtil.strafe(0.22);

                    }

if((mc.thePlayer.hurtTime>0 || mc.thePlayer.ticksSincePlayerVelocity<50) && getModule(Velocity.class).mode.getValue().getName()=="Vulcan"){



    MoveUtil.strafe();
} else{

}

             //   ChatUtil.display(mc.thePlayer.ticksSincePlayerVelocity);

                double speed2 = Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ)));
                //   mc.thePlayer.motionZ = newMotionValues[1];
                if(speed2<.022){
                    MoveUtil.strafe();
                    //       ChatUtil.display(Math.hypot((mc.thePlayer.motionX -(mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX)),(mc.thePlayer.motionZ-(mc.thePlayer.lastTickPosZ-mc.thePlayer.lastLastTickPosZ))));
                }
                switch (mc.thePlayer.offGroundTicks) {

                    case 0:

                        mc.thePlayer.jump();
//MoveUtil.strafe();
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && MoveUtil.speed()<((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.487))) {
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.487));
                        } else {
if(MoveUtil.speed()<49){
    MoveUtil.strafe(0.487);
}




                          //  MoveUtil.strafe(0.487);
                        }
                        break;

                    case 9:
                        if (!(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY,
                                0) instanceof BlockAir)) {
                            MoveUtil.strafe();
                        }
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)   && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2 && MoveUtil.speed()<((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .46))) {
                           MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .46));
                        } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1 && MoveUtil.speed()<((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .385))){
                                MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .385));


                            //  MoveUtil.strafe(0.487);
                        } else if(MoveUtil.speed()<.299){
                            MoveUtil.strafe(0.299);

                        }
                     //   ChatUtil.display(MoveUtil.speed());
                        break;
                    case 1:
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) &&   mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2 && MoveUtil.speed()<(.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.487)) {
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.487));
                        } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1 && MoveUtil.speed()<((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .41))){
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .41));


                            //  MoveUtil.strafe(0.487);
                        } else{
                          MoveUtil.strafe();
                          if(MoveUtil.speed()<0.3355) {
                           MoveUtil.strafe(0.3355);
                          }
                        }
                   //
                     //   MoveUtil.strafe(0.335);


                        break;
                    case 4:
                        mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
                        break;
                    case 10:

                        break;

                }

    burstMovement(mc.thePlayer.posZ, mc.thePlayer.posX, mc.thePlayer.isSprinting(), mc.thePlayer.ticksSprint, mc.thePlayer.offGroundTicks);



                break;

            case "Funny":
                int blockSlot = SlotUtil.findBlock();

                if (blockSlot == -1) {
                    ChatUtil.display("This speed requires a block to be in your HotBar.");
                    return;
                }

                if (!BadPacketsComponent.bad(false, true, false, false, false)) {
                    getComponent(Slot.class).setSlot(blockSlot);
                }

                int speed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0;

                if (!BadPacketsComponent.bad(false, true, false, false, false) && lastRightClick < mc.thePlayer.ticksExisted) {
                    lastRightClick = mc.thePlayer.ticksExisted + 2;
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                }

                switch (mc.thePlayer.offGroundTicks) {
                    case 0:
                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.55);
                                break;
                            case 1:
                                MoveUtil.strafe(0.65 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.85 - 0.18);
                                break;
                        }

                        mc.thePlayer.motionY = 0.2f;
                        break;

                    case 1:
                        mc.thePlayer.motionY = MoveUtil.HEAD_HITTER_MOTION;

                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.45 - 0.02);
                                break;
                            case 1:
                                MoveUtil.strafe(0.6 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.75 - 0.18);
                                break;
                        }
                        break;

                    case 2:
                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.4 - 0.03);
                                break;
                            case 1:
                                MoveUtil.strafe(0.55 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.65 - 0.18);
                                break;
                        }
                        break;
                }
                break;
        }
    };

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        switch (mode.getValue().getName()) {
            case "Yport":
//ChatUtil.display(mc.thePlayer.offGroundTicks);
                if (mc.thePlayer.offGroundTicks == 0) {
                    MoveUtil.strafe();
                    mc.thePlayer.motionY = -0.05;
                }
                if (mc.thePlayer.offGroundTicks == 1) {
                    MoveUtil.strafe();
                    mc.thePlayer.motionY = -0.22319999363422365;
                }
                if (mc.thePlayer.offGroundTicks == 2) {
                    MoveUtil.strafe();
               //     mc.thePlayer.motionY = -3;
                }

                if (mc.thePlayer.offGroundTicks == 3){
                    MoveUtil.strafe();
                }
                burstMovement(mc.thePlayer.posZ, mc.thePlayer.posX, mc.thePlayer.isSprinting(), mc.thePlayer.ticksSprint, mc.thePlayer.offGroundTicks);

                break;
        }
    };

    public void burstMovement(double currentX, double currentZ, boolean sprinting, int sinceSprintingTicks, int airTicks) {
        double predictedX = (mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX) * 0.9100000262260437;
        double predictedZ = (mc.thePlayer.lastTickPosX-mc.thePlayer.lastLastTickPosX) * 0.9100000262260437;
// Creating a burst effect in X/Z directions
        double burstFactor = 1000000; // Factor to amplify current movement for maximizing `difference`
        double deltaX = currentX * burstFactor;
        double deltaZ = currentZ * burstFactor;

        // Calculate difference using modified delta values
        double differenceX = deltaX - predictedX;
        double differenceZ = deltaZ - predictedZ;
        double difference = Math.hypot(differenceX, differenceZ);

        // Apply sprint and offset logic for maximum difference
        difference /= (wasSprinting ? 1.3 : 1.3);
        difference -= ((sprinting || sinceSprintingTicks < 2) ? 0.026 : 0.026);

        // Check conditions for valid movement
        if(mc.thePlayer.ticksExisted %2 == 0){

        }
        boolean invalid = difference > 0.0075 && Math.hypot(deltaX, deltaZ) > 0.25 && airTicks > 2;

        if(!invalid){
  //  ChatUtil.display("Difference: " + difference + ", Invalid: " + invalid);

    MoveUtil.strafe();
}

        // Logging for test purposes
   //    ChatUtil.display("Difference: " + difference + ", Invalid: " + invalid);

        // Update last deltas and sprinting state
        lastDeltaX = deltaX;
        lastDeltaZ = deltaZ;
        wasSprinting = sprinting;
    }
}

