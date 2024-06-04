package com.polarware.module.impl.movement.flight;

import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.BlockAABBEvent;
import com.polarware.event.impl.other.MoveEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

/**
 * @author Nicklas
 * @since 31.03.2022
 */

public class VerusFlight extends Mode<FlightModule> { // TODO: make sneaking go down

    // Sub Modes.
    private final ModeValue mode = new ModeValue("Sub-Mode", this)
            .add(new SubMode("Fast"))
            .setDefault("Fast");

    private int ticks = 0;

    public VerusFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
       // PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
       // PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
        switch (mode.getValue().getName()) {
            case "Fast": {
                // When U Press Space U Go Up By 0.42F Every 2 Ticks.
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.motionY = 0.42F;
                    }
                }

                break;
            }
        }

        ++ticks;
    };

    @EventLink()
    public final Listener<MoveEvent> onMove = event -> {

        if (mode.getValue().getName().equals("Fast")) {
            // Sets Y To 0.42F Every 14 ticks & When OnGround To Bypass Fly 4A.
            if (mc.thePlayer.onGround && ticks % 14 == 0) {
              //  mc.timer.timerSpeed = 0.1f;
                event.setPosY(0.42F);
                MoveUtil.strafe(0.69);
                mc.thePlayer.motionY = -(mc.thePlayer.posY - Math.floor(mc.thePlayer.posY));
            } else {
                //mc.timer.timerSpeed = 8.0f;
                // A Slight Speed Boost.
                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe(1.01 + MoveUtil.speedPotionAmp(0.15));
                    // Slows Down To Not Flag Speed11A.
                } else MoveUtil.strafe(0.41 + MoveUtil.speedPotionAmp(0.05));
            }

            mc.thePlayer.setSprinting(true);
            mc.thePlayer.omniSprint = true;
            if(mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 3.0f;
            } else {
                //0.8
                mc.timer.timerSpeed = 0.5f;
            }
        }

        ticks++;
    };

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        switch (mode.getValue().getName()) {
            case "Fast": {
                // Sets The Bounding Box To The Players Y Position.
                if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() || mc.gameSettings.keyBindJump.isKeyDown()) {
                    final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                    if (y < mc.thePlayer.posY) {
                        event.setBoundingBox(AxisAlignedBB.fromBounds(
                                -15,
                                -1,
                                -15,
                                15,
                                1,
                                15
                        ).offset(x, y, z));
                    }
                }
                break;
            }
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMoveInput = event -> {

        // Sets Sneaking To False So That We Can't Sneak When Flying Because That Can Cause Flags.
        event.setSneak(false);
    };
}