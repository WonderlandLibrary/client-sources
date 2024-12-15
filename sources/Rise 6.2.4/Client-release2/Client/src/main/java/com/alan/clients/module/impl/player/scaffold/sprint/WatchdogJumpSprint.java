package com.alan.clients.module.impl.player.scaffold.sprint;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.module.impl.player.scaffold.tower.WatchdogTower;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

import java.util.Objects;
import java.util.Random;

public class WatchdogJumpSprint extends Mode<Scaffold> {

    public WatchdogJumpSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    int previousBlockValue = -1;
    private boolean jump = false;
    private boolean jumpHandled = false;
    int previousTick = -1;
    int ongroundticks;
    int time;
    double speed;
    public Vec3i offset = new Vec3i(0, 0, 0);
    public static boolean hasC08Packet = false;
    public static boolean start2 = false;
    public static boolean start3 = false;
    public static boolean start4 = false;
    int startTriggerCount = 0;
    private int block;
    public final BooleanValue extraBlocks = new BooleanValue("Place Extra blocks", this, false);

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

if(event.isOnGround()){
    ongroundticks++;
} else{
    ongroundticks = 0;
}


        getParent().recursions = 1;
        if (mc.thePlayer.onGroundTicks > 2 && !mc.gameSettings.keyBindJump.isKeyDown() && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            MoveUtil.strafe();

            mc.thePlayer.motionZ *= 1.129;
            mc.thePlayer.motionX *= 1.129;
        } else if (mc.thePlayer.onGroundTicks > 2 && !mc.gameSettings.keyBindJump.isKeyDown() && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() && mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 >= 2) {
            MoveUtil.strafe();
            mc.thePlayer.motionZ *= 1.143;
            mc.thePlayer.motionX *= 1.143;
        } else if (mc.thePlayer.onGroundTicks > 2 && !mc.gameSettings.keyBindJump.isKeyDown() && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() && mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 == 1) {
            MoveUtil.strafe();
            mc.thePlayer.motionZ *= 1.131;
            mc.thePlayer.motionX *= 1.131;
        }
        if(Math.abs(mc.thePlayer.motionY) < 0.1 && mc.thePlayer.offGroundTicks<2){
        //    mc.thePlayer.onGround = true;
        }


       // ChatUtil.display(mc.thePlayer.motionY);

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            return;
        }

        int currentTick = mc.thePlayer.ticksExisted;
        int currentBlockValue = block;
        if ( !getModule(Flight.class).isEnabled() &&  !getModule(LongJump.class).isEnabled()) {
            if (!BadPacketsComponent.bad(true, true, false, true, false) && Math.random()>0.5 && start3 && MoveUtil.isMoving()) {
//ChatUtil.display("ice on my wrist");

                Random random = new Random();
                float hitX = random.nextFloat();
                float hitZ = random.nextFloat();

                PacketUtil.send(new C08PacketPlayerBlockPlacement(
                        new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                        EnumFacing.UP.getIndex(),
                        new ItemStack(Blocks.ice),
                        hitX,
                        1.0F,
                        hitZ
                ));
                start3 = false;
            }
        }



        if (previousTick != -1 && currentTick - previousTick >= 4) {

            previousBlockValue = currentBlockValue;
        } else if (previousTick == -1) {

            previousTick = currentTick;
            previousBlockValue = currentBlockValue;
        }


        if (mc.thePlayer.onGround) {

            MoveUtil.strafe();
        }



        if (mc.thePlayer.onGround) {
            event.setPosY(event.getPosY() + 0.0001);
        }

        if (jump && !mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.thePlayer.motionY > .331 && mc.thePlayer.motionY < .34) {


            } else if (mc.thePlayer.motionY > .15) {


            }
            WatchdogTower.angle = mc.thePlayer.rotationYaw;
            WatchdogTower.tickCounter = 17;
            WatchdogTower.ticks = 0;

            MoveUtil.stop();

            jump = false;

            RotationComponent.setRotations(new Vector2f((float) (mc.thePlayer.rotationYaw + (Math.random() - 0.5) * 3), (float) (90)), 10, MovementFix.NORMAL);// Store the BlockPos in a variable

            if (!(PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.lastGroundY - 2, mc.thePlayer.posY) instanceof BlockAir)) {
                getParent().startY = mc.thePlayer.lastGroundY - 1;
            }
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            jump = true;
        }
    };

    @Override
    public void onEnable() {
        start3 = false;
        start4 = false;
        if (!(getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump"))) {
            if (mc.thePlayer.onGroundTicks > 9) {
                hasC08Packet = true;
                jumpHandled = true;

            }
        } else {
            mc.thePlayer.safeWalk = false;
        }


        start2 = true;


        jump = false;

        if (!mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionX *= 0;
            mc.thePlayer.motionZ *= 0;
        }

        previousBlockValue = -1;
        previousTick = -1;
        block = 0;
        start2 = true;
    }

    @Override
    public void onDisable() {
        start3 = false;
        start3 = false;
        mc.thePlayer.safeWalk = false;
        hasC08Packet = false;
        jumpHandled = false;
        if (mc.thePlayer.ticksSinceTeleport < 20) {
            MoveUtil.stop();
        }
        start2 = false;
        if (jump) {
            MoveUtil.stop();
        }

        PingSpoofComponent.dispatch();
    }

    @EventLink(value = Priorities.MEDIUM)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        boolean start = mc.thePlayer.lastGroundY == getParent().startY;

        if (!extraBlocks.getValue()) {
            start = true;
        }
        if (getComponent(Slot.class).getItemStack() != null &&
                mc.thePlayer.posY > getParent().startY &&
                mc.thePlayer.posY + MoveUtil.predictedMotion(mc.thePlayer.motionY, 2) <
                        getParent().startY + 1 && ((!jumpHandled || hasC08Packet) && Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()) && !(getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump")) || getComponent(Slot.class).getItemStack() != null &&
                mc.thePlayer.posY > getParent().startY &&
                mc.thePlayer.posY + MoveUtil.predictedMotion(mc.thePlayer.motionY, 2) <
                        getParent().startY + 1 && getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump")) {

            getComponent(Slot.class).setSlot(SlotUtil.findBlock());
        }


        if (Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()) {
            start = false;
        }
        if (jumpHandled) {
            start = true;


        }

        if (mc.thePlayer.offGroundTicks > 8) {
            jumpHandled = false;
        }

        if (!start && mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - .01);
            mc.thePlayer.jump();


        }

        if (!(PlayerUtil.isBlockUnder(2) || PlayerUtil.isBlockUnder(1) || PlayerUtil.isBlockUnder(0) || PlayerUtil.isBlockUnder(3)) && !mc.gameSettings.keyBindJump.isPressed() && !mc.thePlayer.isPotionActive(Potion.moveSpeed) && !jump) {
        } else if (mc.thePlayer.onGroundTicks < 1) {
            mc.thePlayer.safeWalk = false;
        }


        if (mc.gameSettings.keyBindJump.isPressed() && mc.thePlayer.onGround && !jump && (start2 || getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump")) && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()) {
            start2 = false;
            MoveUtil.strafe(MoveUtil.getbaseMoveSpeed() * .9);
            if ((getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump"))) {
                MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - .001);
            }
        }


        if (mc.thePlayer.offGroundTicks == 4 && !mc.gameSettings.keyBindJump.isKeyDown() && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled() && !start && getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump")) {
            mc.thePlayer.motionY = mc.thePlayer.motionY - .19;
        }

        if (mc.thePlayer.offGroundTicks == 1 && !mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.strafe();
        }

        if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && mc.thePlayer.offGroundTicks > 2) {
            MoveUtil.strafe();
        }


        if (start && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.onGround && !getModule(Speed.class).isEnabled() && extraBlocks.getValue()) {
            MoveUtil.stop();
        } else if (start && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.onGround && extraBlocks.getValue()) {
            MoveUtil.stop();
        }


        if (start && mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
            startTriggerCount++;
        }

        if (!start) {
            startTriggerCount = 0;
        }
    };


    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (event.getPacket() instanceof S23PacketBlockChange) {

            start3 = true;
            block++;
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {




        if (!(getModule(Scaffold.class).sameY.getValue().getName().equals("Auto Jump"))) {
            if (mc.thePlayer.onGround && jumpHandled && hasC08Packet && !Client.INSTANCE.getModuleManager().get(Speed.class).isEnabled()) {
                mc.thePlayer.motionY = .42;
                hasC08Packet = false;
            }
        }
    };
}



