package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

@ModuleAnnotation(name = "AirJump", category = Category.MOVEMENT)
public class AirJump extends Module {

    @EventTarget
    public void onMotionEvent(EventMotion eventMotion) {
        if ((validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1.0f, mc.player.posZ))
                .getBlock()) || validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX,
                mc.player.posY - 1.0f, mc.player.posZ)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX - 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ - 1.0f)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX + 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ + 1.0f)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX - 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ + 1.0f)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX + 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ - 1.0f)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX + 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX - 1.0f,
                        mc.player.posY - 1.0f, mc.player.posZ)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX,
                        mc.player.posY - 1.0f, mc.player.posZ + 1.0f)).getBlock()) ||
                validBlocks(mc.world.getBlockState(new BlockPos(mc.player.posX,
                        mc.player.posY - 1.0f, mc.player.posZ - 1.0f)).getBlock()))
                && mc.player.ticksExisted % 2 == 0 && mc.gameSettings.keyBindJump.isKeyDown()) {

            mc.player.jumpTicks = 0;
            mc.player.fallDistance = 0.0f;
            eventMotion.setOnGround(true);
            mc.player.onGround = true;
        }
    }

    public boolean validBlocks(Block blocks) {
        return blocks != Blocks.AIR && !Arrays.asList(6, 27, 28, 31, 32, 37, 38, 39, 40, 44, 77, 143, 175).contains(Block.getIdFromBlock(blocks));
    }
}