package com.alan.clients.module.impl.player.scaffold.tower;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.util.player.*;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.other.Timer;

import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

import static java.lang.Double.NaN;


public class WatchdogTower extends Mode<Scaffold> {
    public static int tickCounter;

    public static int ticks;

    private boolean attempt;

    private int count = 0;
    float moveyaw;
    boolean move = true;
    boolean targetCalculated = false;
    private int ticks2;
    public static float angle;

    double targetZ = NaN;
    double targetX = NaN;
    private int lastY;

    public WatchdogTower(String name, Scaffold parent) {
        super(name, parent);
    }
    private boolean jump = false;

    @Override
    public void onEnable() {
        getComponent(Slot.class).setSlot(SlotUtil.findBlock());


count = 0;
        attempt = false;
        targetZ = NaN;
        tickCounter = 0;
        ticks2 = 0;
        angle = mc.thePlayer.rotationYaw;
        targetCalculated = false;
        if (mc.thePlayer.onGround) {
            ticks = 0;
        } else{
            ticks = 100;
        }
        if (Client.INSTANCE.getModuleManager().get(Scaffold.class).sameY.getValue().getName().equals("Off") && !mc.thePlayer.onGround){
            move = true;
        }
jump = false;

    }

    @EventLink
    public final Listener<JumpEvent> onJump = event -> {



    };
    @Override
    public void onDisable() {
        targetCalculated = false;
        move = false;
        attempt = false;
        angle = mc.thePlayer.rotationYaw;
      //  ChatUtil.display(ticks);
        ticks = 100;
        ticks2 = 0;

        if( mc.gameSettings.keyBindJump.isKeyDown()){
            MoveUtil.strafe(.23);



        }


    }

    @EventLink(value = Priorities.MEDIUM)
    public Listener<PreMotionEvent> preMotion = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown() && MoveUtil.isMoving()) {

            angle = mc.thePlayer.rotationYaw;
           ticks = 0;
            tickCounter = 0;
         //   return;
        } else if(!mc.gameSettings.keyBindJump.isKeyDown()){
            angle = mc.thePlayer.rotationYaw;
            ticks = 0;
            tickCounter = 0;
               return;
        }
if(MoveUtil.isMoving() ){
    tickCounter++;
} else if(tickCounter > 20){
    tickCounter--;
}
if(mc.gameSettings.keyBindJump.isKeyDown()){
    ticks++;
}


        if (tickCounter >= 23) {

            tickCounter = 1; // Reset the counter
            angle = mc.thePlayer.rotationYaw;
            ticks = 99;
        }

        if (mc.thePlayer.onGround) {
            ticks = 0;
            ticks2 = 0;
            angle = mc.thePlayer.rotationYaw;
        } else if (ticks==100){
            MoveUtil.strafe(0);
            MoveUtil.partialStrafePercent(10);
        }



//       ChatUtil.display(tickCounter + "mot y "+mc.thePlayer.motionY + "off "+mc.thePlayer.offGroundTicks);


/*
    getParent().recursions = 1;
        Container inventory = mc.thePlayer.inventoryContainer;
        int count = 0;

        for (int i = InventoryUtil.HOT_BAR_BEGIN; i < InventoryUtil.END; i++) {
            ItemStack stack = inventory.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock) {
                ItemBlock block = (ItemBlock) stack.getItem();
                if (!(block.getBlock() instanceof BlockFalling)) {
                    count += stack.stackSize;
                }
            }
        }

//ChatUtil.display(count);

        if (count < 4) {
            NotificationComponent.post("Watchdog Tower", "Automatically disabled Watchdog Tower to prevent flags", 1000);
           attempt = true;
        }

*/

        if (!MoveUtil.isMoving()) {
            // Calculate the player's facing direction in radians
            double rotationYaw = mc.thePlayer.rotationYaw;
            double yawRadians = Math.toRadians(rotationYaw);

            // Calculate the forward direction vector components
            double forwardX = -Math.sin(yawRadians);
            double forwardZ = Math.cos(yawRadians);

            // Calculate the dot product between the forward vector and the intended placement vector (0, 0, 1)
            double dotProduct = forwardZ * 1; // Since the Y component is 0, only Z is relevant

            // Determine if the placement vector is behind the player
            boolean isBehind = dotProduct < 0;

            // Set the number of recursions or iterations if needed
            getParent().recursions = 5;

            if (!targetCalculated) {
                // Calculate the target position only once
                if (!isBehind) {
                    targetZ = Math.floor(mc.thePlayer.posZ) + 0.999999999999;
                    targetX = Double.NaN; // Indicate that X-axis placement is not needed
                } else {
                    targetX = Math.floor(mc.thePlayer.posX) + 0.999999999999;
                    targetZ = Double.NaN; // Indicate that Z-axis placement is not needed
                }

                targetCalculated = true;
            }

            ticks2++;

            if (Math.abs(lastY - mc.thePlayer.posY) >= 1) {
                if (ticks2 == 1) {
                    // Step 1: Move to one-third of the distance to the target position
                    MoveUtil.stop();
                    if (!hasAdjacentBlock()) {
                        if (!Double.isNaN(targetX)) {
                            mc.thePlayer.setPosition(
                                    mc.thePlayer.posX + (targetX - mc.thePlayer.posX) / 3,
                                    mc.thePlayer.posY,
                                    mc.thePlayer.posZ
                            );
                         //   ChatUtil.display("Step 1: Moved one-third on X-axis.");
                        } else if (!Double.isNaN(targetZ)) {
                            mc.thePlayer.setPosition(
                                    mc.thePlayer.posX,
                                    mc.thePlayer.posY,
                                    mc.thePlayer.posZ + (targetZ - mc.thePlayer.posZ) / 3
                            );
                            //ChatUtil.display("Step 1: Moved one-third on Z-axis.");
                        }
                    }

                } else if (ticks2 == 2) {
                    // Step 2: Move to two-thirds of the distance to the target position
                    MoveUtil.stop();
                    if (!hasAdjacentBlock()) {
                        if (!Double.isNaN(targetX)) {
                            mc.thePlayer.setPosition(
                                    mc.thePlayer.posX + 2 * (targetX - mc.thePlayer.posX) / 3,
                                    mc.thePlayer.posY,
                                    mc.thePlayer.posZ
                            );
                          //  ChatUtil.display("Step 2: Moved two-thirds on X-axis.");
                        } else if (!Double.isNaN(targetZ)) {
                            mc.thePlayer.setPosition(
                                    mc.thePlayer.posX,
                                    mc.thePlayer.posY,
                                    mc.thePlayer.posZ + 2 * (targetZ - mc.thePlayer.posZ) / 3
                            );
                         //   ChatUtil.display("Step 2: Moved two-thirds on Z-axis.");
                        }
                        doSidePlacement();
                    } else {
                        doSidePlacement();
                     //   ChatUtil.display("Adjacent block detected. Skipping Step 2.");
                    }
                } else if (ticks2 == 3) {
                    // Step 3: Move to the final target position
                    MoveUtil.stop();
                    if (!hasAdjacentBlock()) {
                        if (!Double.isNaN(targetX)) {
                            mc.thePlayer.setPosition(
                                    targetX,
                                    mc.thePlayer.posY,
                                    mc.thePlayer.posZ
                            );
                      //      ChatUtil.display("Step 3: Moved to final X-axis position.");
                        } else if (!Double.isNaN(targetZ)) {
                            mc.thePlayer.setPosition(
                                    mc.thePlayer.posX,
                                    mc.thePlayer.posY,
                                    targetZ
                            );
                        //    ChatUtil.display("Step 3: Moved to final Z-axis position.");
                        }
                        doSidePlacement();

                        // Reset variables after reaching the final position
                        ticks2 = 0; // Reset the tick counter after reaching the final position
                        targetCalculated = false; // Reset the flag for the next cycle
                    } else {
                        doSidePlacement();
                     //   ChatUtil.display("Adjacent block detected. Skipping Step 3.");
                    }
                }
            } else {
                // Reset ticks2 if the Y position condition is not met
                ticks2 = 0;
                targetCalculated = false; // Reset the flag if the condition is not met
            }
        } else{
            targetCalculated = false;
        }


        if(attempt && !MoveUtil.isMoving()){

            mc.gameSettings.keyBindJump.setPressed(true);
        }








        if(mc.thePlayer.ticksSinceTeleport<1){
    targetCalculated = false;
}

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            jump = true;
        }



if(mc.thePlayer.motionY < .3 && attempt && mc.thePlayer.motionY > .17){
    getParent().toggle();

    if(mc.thePlayer.onGround){
        mc.thePlayer.jump();
    }
    attempt = false;
}
       // ChatUtil.display(jump);

        if (jump && !mc.gameSettings.keyBindJump.isKeyDown()) {
            if(!(mc.thePlayer.motionY < .3 && attempt && mc.thePlayer.motionY > .17)){
              //  ChatUtil.display("s");

            }
MoveUtil.stop();

            // Call MoveUtil.strafe(0.23) when the jump key is released
//ChatUtil.display("s");
            jump = false;
            //  ChatUtil.display(ticks);





            // Set jumpHandled to true to prevent repeated activation
            //   jumpHandled = true;
        }

        if(!mc.gameSettings.keyBindJump.isPressed() && !mc.gameSettings.keyBindJump.isKeyDown()){
          //  tickCounter = 0;
        //    ticks2 = 0;
      //    return;

        }

        float step = ticks == 1 ? 90 : 0;

        if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < step) {
            angle = mc.thePlayer.rotationYaw;
        } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < 0) {
            angle -= step;
        } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) > 0) {
            angle += step;
        }
        boolean forward = mc.gameSettings.keyBindForward.isKeyDown();
        boolean back = mc.gameSettings.keyBindBack.isKeyDown();
        boolean left = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean right = mc.gameSettings.keyBindRight.isKeyDown();

        float angle = mc.thePlayer.rotationYaw;

        // Calculate the angle based on input
        if (forward && !back) {
            if (left && !right) {
                angle -= 45; // Forward-left
            } else if (right && !left) {
                angle += 45; // Forward-right
            }
        } else if (back && !forward) {
            if (left && !right) {
                angle -= 135; // Backward-left
            } else if (right && !left) {
                angle += 135; // Backward-right
            } else {
                angle += 180; // Backward
            }
        } else if (left && !right) {
            angle -= 90; // Left
        } else if (right && !left) {
            angle += 90; // Right
        }
        mc.thePlayer.movementYaw = angle;

        if (tickCounter < 20 && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() ) {
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

            } else{

            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

                } else{

                }
//            getParent().startY = Math.floor(mc.thePlayer.posY);


                switch (ticks) {


                    case 0:

                        MoveUtil.strafe(MoveUtil.speed(), angle );

                           // event.setOnGround(true);



                            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)  && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                                mc.thePlayer.motionZ *= 1.045;
                                mc.thePlayer.motionX *= 1.045;
                            //    MoveUtil.strafe(.32);
                              //  MoveUtil.strafe();
                            } else if(mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                                mc.thePlayer.motionZ *= 1.035;
                                mc.thePlayer.motionX *= 1.035;
                            }
                        if (Client.INSTANCE.getModuleManager().get(Scaffold.class).sameY.getValue().getName().equals("Off")) {
                            MoveUtil.strafe(.28);
                        }




    mc.thePlayer.motionY = 0.42F;




                        break;

                    case 1:
                        moveyaw = angle;
                        MoveUtil.strafe(MoveUtil.speed(), angle );

                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)  && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                      //      mc.thePlayer.motionZ *= 1.008;
                     //       mc.thePlayer.motionX *= 1.008;
                            //    MoveUtil.strafe(.32);
                            //  MoveUtil.strafe();
                        }
                     //   RotationComponent.setSmoothed(false);
                     //   RotationComponent.setRotations(new Vector2f((float) (mc.thePlayer.rotationYaw-(89+(Math.random()))), (float) (86)), 10, MovementFix.OFF);// Store the BlockPos in a variabl


                      //  mc.thePlayer.motionY=MoveUtil.predictedMotion(mc.thePlayer.motionY);


    mc.thePlayer.motionY = 0.33;









                        if (Client.INSTANCE.getModuleManager().get(Scaffold.class).sameY.getValue().getName().equals("Off")) {
                            MoveUtil.strafe(.26);
                        }
                        break;

                    case 2:
                     //   MoveUtil.strafe(MoveUtil.speed(), moveyaw );
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

                        //    MoveUtil.partialStrafePercent(2);
                        }else{

                          //  MoveUtil.partialStrafePercent(2);
                        }

//ChatUtil.display(mc.thePlayer.motionY);

                            mc.thePlayer.motionY = 1 - mc.thePlayer.posY % 1;




                        break;
                }
            }
        } else {
if(!mc.thePlayer.onGround){
  //  mc.thePlayer.motionY = -.16;
}
        }

if(mc.thePlayer.motionY>0 &&  !mc.gameSettings.keyBindJump.isKeyDown()){
   // ChatUtil.display("s")
   // mc.thePlayer.motionY =.16;
//MoveUtil.strafe(-.1);
}


if(tickCounter == 20 || tickCounter == 21 ){
    moveyaw = angle;
    MoveUtil.strafe(MoveUtil.speed(), angle );

} else if (tickCounter>20){
    MoveUtil.strafe(MoveUtil.speed(), moveyaw);
}

        if(tickCounter == 22 && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()  ){
            if( mc.thePlayer.onGroundTicks == 0 && !mc.thePlayer.isPotionActive(Potion.moveSpeed)){
            //   MoveUtil.strafe(MoveUtil.getbaseMoveSpeed()*.97);
                 //   getParent().offset = new Vec3i(0, 0, 1);
                //


            }
           // ChatUtil.display("hi");
            if( mc.thePlayer.onGroundTicks == 1){
             //   MoveUtil.strafe();
                //   getParent().offset = new Vec3i(0, 0, 1);



            }


          //  mc.thePlayer.moteionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 3);
            mc.thePlayer.motionY -=.42;
















            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
             //   MoveUtil.strafe(.32);

            }else {
            //    MoveUtil.strafe(.26);
            }








            }

        if (ticks == 2) ticks = -1;
        getParent().recursions = 1;
    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        if(ticks == 2){
            MoveUtil.strafe(MoveUtil.speed(), moveyaw );
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
       MoveUtil.preventDiagonalSpeed();

if(MoveUtil.speed()<.19 && mc.gameSettings.keyBindJump.isKeyDown()){
    MoveUtil.strafe(.19);
}
      //  ChatUtil.display(mc.thePlayer.rotationYaw);

        if (!(tickCounter < 16) && mc.thePlayer.isPotionActive(Potion.moveSpeed)){

        }
    };
    public void doSidePlacement() {
        // Update lastY based on player's current Y position
        lastY = (int) Math.floor(mc.thePlayer.posY);

        // Calculate the player's facing direction in radians
        double rotationYaw = mc.thePlayer.rotationYaw;
        double yawRadians = Math.toRadians(rotationYaw);

        // Calculate the forward direction vector components
        double forwardX = -Math.sin(yawRadians);
        double forwardZ = Math.cos(yawRadians);

        // Calculate the dot product between the forward vector and the intended placement vector (0, 0, 1)
        double dotProduct = forwardZ * 1; // Since the Y component is 0, only Z is relevant

        // Determine if the placement vector is behind the player
        boolean isBehind = dotProduct < 0;

        // Decide the placement vector based on the placement direction
        Vector3d placementVector;

        if (isBehind) {
          //  ChatUtil.display("behind");
            RotationComponent.setSmoothed(false);

            RotationComponent.setRotations(new Vector2f((float)(mc.thePlayer.rotationYaw-164 + (Math.random() - 0.5) * 3), 86f), 10, MovementFix.OFF);

            placementVector = new Vector3d(1, 0, 0); // Front
            // Alternatively, to place on the left, use:
            // placementVector = new Vector3d(-1, 0, 0); // Left side
        } else {
          //  ChatUtil.display("good");
            // Place block in front on the Z-axis
            placementVector = new Vector3d(0, 0, 1); // Right side

        }

        // Set the parent offset to the chosen placement vector
        getParent().offset = placementVector;

        // Calculate the necessary rotations based on the placement vector
        final Vector2f rotations = RotationUtil.calculate(placementVector);

        // Apply rotations with slight randomization
        if (!MoveUtil.isMoving() && !isBehind) {
            RotationComponent.setSmoothed(false);

            // Add slight random variation to rotationYaw for variability

            float randomYaw = (float) (rotationYaw+164 + (Math.random() - 0.5) * 3);
            float pitch = 86.0f; // Fixed pitch
         //   ChatUtil.display("good");
            RotationComponent.setSmoothed(false);

            RotationComponent.setRotations(new Vector2f(randomYaw, pitch), 10, MovementFix.OFF);
        }
    }



    @EventLink
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (event.getKeyCode() == getParent().getKey() && !attempt && mc.gameSettings.keyBindJump.isKeyDown()  && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()) {
            event.setCancelled();
            attempt = true;

        }


    };
    @EventLink
    public final Listener<TickEvent> onTick = event -> {

    };

    @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {
        if(!getModule(Speed.class).isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()){
            MoveUtil.strafe(MoveUtil.getbaseMoveSpeed());
        }
        event.setJumpMotion(0.42f);
    };
    private boolean hasAdjacentBlock() {
        // Get player's current block position
        int playerX = (int) Math.floor(mc.thePlayer.posX);
        int playerY = (int) Math.floor(mc.thePlayer.posY);
        int playerZ = (int) Math.floor(mc.thePlayer.posZ);

        Block[] adjacentBlocks = new Block[] {
                PlayerUtil.block(playerX + 1, playerY, playerZ), // +X
                PlayerUtil.block(playerX - 1, playerY, playerZ), // -X
                PlayerUtil.block(playerX, playerY, playerZ + 1), // +Z
                PlayerUtil.block(playerX, playerY, playerZ - 1)  // -Z
        };

// Check if all adjacent blocks are not air
        for (Block block : adjacentBlocks) {
            if (block instanceof BlockAir) {
                return false; // Found air block, so the player is not surrounded on all sides
            }
        }

        return true; // All adjacent blocks are not air, the player is surrounded on all sides


    }

}
