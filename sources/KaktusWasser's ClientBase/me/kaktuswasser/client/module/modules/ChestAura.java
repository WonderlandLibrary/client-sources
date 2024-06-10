// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PostMotion;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.BlockHelper;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.TimeHelper;
import net.minecraft.util.BlockPos;
import java.util.List;

public class ChestAura extends Module
{
    public static final List<BlockPos> openedBlocks;
    private final TimeHelper time;
    private boolean shouldBreak;
    BlockPos globalPos;
    
    static {
        openedBlocks = new CopyOnWriteArrayList<BlockPos>();
    }
    
    public ChestAura() {
        super("ChestAura", -8388608, Category.COMBAT);
        this.time = new TimeHelper();
        this.shouldBreak = false;
        this.setTag("Chest Aura");
        Client.getCommandManager().getCommands().add(new Command("chestauraclear", "none") {
            @Override
            public void run(final String message) {
                ChestAura.openedBlocks.clear();
                Logger.writeChat("Cleared Chest Aura list!");
            }
        });
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PreMotion) {
            if (ChestAura.mc.currentScreen instanceof GuiContainer) {
                this.time.reset();
            }
            final PreMotion pre = (PreMotion)event;
            int y;
            for (int radius = y = 3; y >= -radius; --y) {
                for (int x = -radius; x <= radius; ++x) {
                    for (int z = -radius; z <= radius; ++z) {
                        final BlockPos pos = new BlockPos(ChestAura.mc.thePlayer.posX - 0.5 + x, ChestAura.mc.thePlayer.posY - 0.5 + y, ChestAura.mc.thePlayer.posZ - 0.5 + z);
                        final Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                        if (this.getFacingDirection(pos) != null && pos != null && !(ChestAura.mc.currentScreen instanceof GuiContainer) && ChestAura.mc.thePlayer.getDistance(ChestAura.mc.thePlayer.posX + x, ChestAura.mc.thePlayer.posY + y, ChestAura.mc.thePlayer.posZ + z) < ChestAura.mc.playerController.getBlockReachDistance() - 0.5 && block instanceof BlockChest) {
                            this.shouldBreak = true;
                            final float[] rotations = BlockHelper.getBlockRotations(pos.getX(), pos.getY(), pos.getZ());
                            final HealingBot healingBot = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
                            if (!healingBot.isHealing()) {
                                pre.setYaw(rotations[0]);
                                pre.setPitch(rotations[1]);
                            }
                            this.globalPos = pos;
                            return;
                        }
                    }
                }
            }
        }
        else if (event instanceof PostMotion && this.globalPos != null && !(ChestAura.mc.currentScreen instanceof GuiContainer) && !ChestAura.openedBlocks.contains(this.globalPos) && this.shouldBreak) {
            ChestAura.mc.thePlayer.swingItem();
            final EnumFacing direction = this.getFacingDirection(this.globalPos);
            if (direction != null && this.time.hasReached(400L)) {
                ChestAura.mc.playerController.func_178890_a(ChestAura.mc.thePlayer, ChestAura.mc.theWorld, ChestAura.mc.thePlayer.getCurrentEquippedItem(), this.globalPos, direction, new Vec3(this.globalPos.getX(), this.globalPos.getY(), this.globalPos.getZ()));
                ChestAura.openedBlocks.add(this.globalPos);
                this.time.reset();
            }
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (ChestAura.mc.theWorld != null) {
            ChestAura.openedBlocks.clear();
        }
    }
    
    private EnumFacing getFacingDirection(final BlockPos pos) {
        EnumFacing direction = null;
        if (!ChestAura.mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.UP;
        }
        else if (!ChestAura.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.DOWN;
        }
        else if (!ChestAura.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.EAST;
        }
        else if (!ChestAura.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.WEST;
        }
        else if (!ChestAura.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.SOUTH;
        }
        else if (!ChestAura.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
            direction = EnumFacing.NORTH;
        }
        return direction;
    }
}
