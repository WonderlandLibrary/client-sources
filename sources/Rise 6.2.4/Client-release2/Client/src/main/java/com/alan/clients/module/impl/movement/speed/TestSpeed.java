package com.alan.clients.module.impl.movement.speed;


import com.alan.clients.Client;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.*;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.Mode;
import com.alan.clients.util.chat.ChatUtil;
import com.viaversion.viaversion.bukkit.listeners.UpdateListener;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class TestSpeed extends Mode<Speed> {
    public TestSpeed(String name, Speed parent) {
        super(name, parent);
    }
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
        this.prevMoveYaw = (float)Math.toDegrees(MoveUtil.direction());
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
        ice = false;
        disable4 =false;
        if(Client.INSTANCE.getModuleManager().get(Scaffold.class).isEnabled()){
            mc.thePlayer.motionX *= .85;
            mc.thePlayer.motionZ *= .85;
        }

        disable = false;
        mc.thePlayer.omniSprint = false;
        //     attempt = false;

    }

    @EventLink
    public final Listener<MoveInputEvent> onInput = inputEvent -> inputEvent.setJump(false);
    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
    };

    private float prevMoveYaw;

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if(mc.thePlayer.isInWater() && mc.thePlayer.isInWeb && mc.thePlayer.isInLava() ) {
            disable = true;
            return;
        }

        if(mc.thePlayer.hurtTime == 9){
            MoveUtil.strafe();
            ChatUtil.display("d");
        }
        if(getModule(Scaffold.class).isEnabled()){
            disable2 = true;
        }

        if(mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.onGround){
            recentlyCollided = true;
            boostTicks = mc.thePlayer.ticksExisted+10;
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


        double posY = event.getPosY();
        if (Math.abs(posY - Math.round(posY)) > 0.03 && mc.thePlayer.onGround) {
            slab = true;
        } else if(mc.thePlayer.onGround){
            slab = false;
        }


        if (PlayerUtil.isBlockOver(0, true)){
            //  disable = true;
        }



        if(mc.thePlayer.offGroundTicks == 5 && !disable2){
            disable2 = false;
        }

    };

    @EventLink
    public final Listener<MoveEvent> moveEventListener = moveEvent -> {
        if (mc.thePlayer.motionY < 0) {
            //  rawSetSpeed(moveEvent, 10.75 / 200, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
        }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (mc.thePlayer.isInWater() && mc.thePlayer.isInWeb && mc.thePlayer.isInLava()) {
            disable = true;
            return;
        }
        if (getModule(Scaffold.class).isEnabled() && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            recentlyCollided = true;
            // disable d= true;s
            boostTicks = mc.thePlayer.ticksExisted + 10;
        }
        if ((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest)) {
            attempt = true;

        } else if ((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest) && getModule(InventoryMove.class).isEnabled()) {
            return;
        }

        MoveUtil.useDiagonalSpeed();


        if (mc.thePlayer.onGround && disable) {
            mc.thePlayer.motionY = .42;
        }

        mc.thePlayer.omniSprint = true;

        if (mc.thePlayer.onGround && !mc.thePlayer.isPotionActive(Potion.moveSpeed) && MoveUtil.isMoving() && !recentlyCollided && MoveUtil.isMoving()) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
            // MoveUtil.strafe(0.29);


            mc.thePlayer.jump();


        } else if (mc.thePlayer.onGround && !recentlyCollided && MoveUtil.isMoving()) {


            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
            mc.thePlayer.jump();


        } else if ((mc.thePlayer.onGround) && MoveUtil.isMoving()) {
            MoveUtil.strafe(MoveUtil.getbaseMoveSpeed());
            mc.thePlayer.jump();
        }

        if (mc.thePlayer.offGroundTicks == 1 && (mc.thePlayer.lastLastTickPosY - mc.thePlayer.lastTickPosY) > -.43 && !disable && !recentlyCollided) {

            //   mc.thePlayer.motionY += 0.025;


            if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                MoveUtil.strafe(0.48);

            } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1) {
                MoveUtil.strafe(0.4);
            } else {
                MoveUtil.strafe(0.33);
            }

            MoveUtil.strafe();

        }

        if (mc.thePlayer.offGroundTicks == 2) {


            double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
            if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() - .05 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !recentlyCollided) {
                //  MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance()-.05);
                //    ChatUtil.display("2");
            }


        }

        if (mc.thePlayer.offGroundTicks == 4 && mc.thePlayer.hurtTime < 6) {
            mc.thePlayer.motionY -= 0.03;
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                //       mc.thePlayer.motionZ *= 1.001;
                //      mc.thePlayer.motionX *= 1.001;
            } else {
                //        mc.thePlayer.motionZ *= 1.001;
                //         mc.thePlayer.motionX *= 1.001;
            }


        }


        if (mc.thePlayer.offGroundTicks == 7 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY * 4.7, 0) != Blocks.air) {


            if (mc.thePlayer.offGroundTicks == 7 && mc.thePlayer.hurtTime < 6) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    mc.thePlayer.motionZ *= 1.02;
                    mc.thePlayer.motionX *= 1.02;

                } else {
                    mc.thePlayer.motionZ *= 1.005;
                    mc.thePlayer.motionX *= 1.005;

                }
            }

        }

        if (mc.thePlayer.offGroundTicks == 8) {
            if (!disable) {

                if (mc.thePlayer.hurtTime < 6) {
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        mc.thePlayer.motionZ *= 1.03;
                        mc.thePlayer.motionX *= 1.03;
                    } else if (!getModule(Scaffold.class).isEnabled()) {
                        mc.thePlayer.motionZ *= 1.008;
                        mc.thePlayer.motionX *= 1.008;

                    }
                }


            }
            if (!recentlyCollided) {
                double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() - .02 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !disable && !recentlyCollided) {
                    //   MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - .02);
                    //  ChatUtil.display("9");
                }

                //  mc.thePlayer.motionY += 0.075;
                //     MoveUtil.strafe();
            }

        }

        if (mc.thePlayer.offGroundTicks == 9 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY * 2, 0) != Blocks.air) {
            if (!disable && !recentlyCollided) {
                if (mc.thePlayer.hurtTime < 6) {
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        mc.thePlayer.motionZ *= 1.07;
                        mc.thePlayer.motionX *= 1.07;
                    } else {
                        mc.thePlayer.motionZ *= 1.008;
                        mc.thePlayer.motionX *= 1.008;
                    }
                }
            }
            if (!recentlyCollided) {
                double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                if ((hypotenuse < MoveUtil.getAllowedHorizontalDistance() || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) && !disable && !recentlyCollided) {
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                    //     ChatUtil.display("10");
                }
                MoveUtil.strafe();

            }

        }

        if (mc.thePlayer.offGroundTicks == 10 && PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air) {
            //   ChatUtil.display("11");
            MoveUtil.strafe();

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
                MoveUtil.strafe(0.45);
            } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1) {
                MoveUtil.strafe(0.375);
            } else {
                MoveUtil.strafe(0.31);
            }


        }
        double baseSpeed = MoveUtil.getbaseMoveSpeed() / 2.29;
        speed = Math.hypot((mc.thePlayer.motionX - (mc.thePlayer.lastTickPosX - mc.thePlayer.lastLastTickPosX)), (mc.thePlayer.motionZ - (mc.thePlayer.lastTickPosZ - mc.thePlayer.lastLastTickPosZ)));
        //   mc.thePlayer.motionZ = newMotionValues[1];



        //    ChatUtil.display(mc.thePlayer.offGroundTicks);
        if (speed < .0125) {
            MoveUtil.strafe();
        }
        //  ChatUtil.display("airtick: "+mc.thePlayer.offGroundTicks+"motion: "/*+  mc.thePlayer.motionY*/);
        float moveYaw = (float) Math.toDegrees(MoveUtil.direction());
        if (Math.abs(moveYaw - this.prevMoveYaw) > 40) {

            MoveUtil.strafe(MoveUtil.speed() * 0.9D);


            this.prevMoveYaw = moveYaw;
        } else {
            MoveUtil.strafe(MoveUtil.speed());
        }
    };


    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {

        double attempt_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction()));
        double movement_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(mc.thePlayer.motionZ, mc.thePlayer.motionX)) - 90);

                if (MathUtil.wrappedDifference(attempt_angle, movement_angle) > 90 && mc.thePlayer.hurtTime == 0) {
                    MoveUtil.strafe(MoveUtil.speed(), (float) movement_angle - 180);
            }


};



        @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {

            event.setJumpMotion(0.42F);




    };

}

