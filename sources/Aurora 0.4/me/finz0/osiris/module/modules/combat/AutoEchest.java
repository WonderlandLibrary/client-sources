package me.finz0.osiris.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.BlockInteractionHelper;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

import static me.finz0.osiris.util.BlockInteractionHelper.*;

//kaaaaaaami
public class AutoEchest extends Module {
    public AutoEchest() {
        super("AutoEchest", Category.COMBAT, "Places obsidian at your feet");
    }
    private List<Block> whiteList = Arrays.asList(new Block[] {
            Blocks.ENDER_CHEST,
    });
    Setting rotate2;
    Setting timeoutTicks2;
    Setting announceUsage2;
    Setting blocksPerTick2;
    Setting tickDelay2;
    private int totalTicksRunning = 0;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    private int delayStep = 0;
    private boolean firstRun;
    private boolean isSneaking = false;
    /* Autocenter */

    private Vec3d playerPos;
    private BlockPos basePos;
    public void setup(){
        timeoutTicks2 = new Setting("TimeoutTicks", this, 4, 0, 20, true, "SurroundTimeoutTicks2");
        AuroraMod.getInstance().settingsManager.rSetting(timeoutTicks2);
        tickDelay2 = new Setting("TickDelay", this, 4, 0, 20, true, "SurroundTickDelay2");
        AuroraMod.getInstance().settingsManager.rSetting(tickDelay2);
        blocksPerTick2 = new Setting("BlocksPerTick", this, 4, 0, 20, true, "SurroundBlocksPerTick2");
        AuroraMod.getInstance().settingsManager.rSetting(blocksPerTick2);
        rotate2 = new Setting("Rotate", this, false, "SurroundRotate2");
        AuroraMod.getInstance().settingsManager.rSetting(rotate2);
        announceUsage2 = new Setting("AnnounceUsage", this, false, "SurroundAnnounceUsage2");
        AuroraMod.getInstance().settingsManager.rSetting(announceUsage2);

    }

    private void centerPlayer(double x, double y, double z) {
        {

        }
        mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
        mc.player.setPosition(x, y, z);
    }
    double getDst(Vec3d vec) {
        return playerPos.distanceTo(vec);
    }
    /* End of Autocenter */
    @Override
    protected void onEnable() {

        if (mc.player == null) {
            this.disable();
            return;
        }
        /* Autocenter */
        BlockPos centerPos = mc.player.getPosition();
        playerPos = mc.player.getPositionVector();
        double y = centerPos.getY();
        double x = centerPos.getX();
        double z = centerPos.getZ();

        final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);


        firstRun = true;

        // save initial player hand
        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;

        if (announceUsage2.getValBoolean()) {
            Command.sendClientMessage("[AutoEchest] " + ChatFormatting.GREEN.toString() + "Enabled!");
        }

    }

    @Override
    protected void onDisable() {

        if (mc.player == null) {
            return;
        }

        // load initial player hand
        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }

        if (isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;

        if (announceUsage2.getValBoolean()) {
            Command.sendClientMessage("[AutoEchest] " + ChatFormatting.RED.toString() + "Disabled!");
        }

    }

    @Override
    public void onUpdate() {
        if (!firstRun) {
            if (delayStep < tickDelay2.getValInt()) {
                delayStep++;
                return;
            } else {
                delayStep = 0;
            }
        }

        if (firstRun) {
            firstRun = false;
        }

        int blocksPlaced = 0;

        while (blocksPlaced < blocksPerTick2.getValInt()) {

            Vec3d[] offsetPattern = new Vec3d[0];
            int maxSteps = 0;



             {
                offsetPattern = Offsets.SURROUND;
                maxSteps = Offsets.SURROUND.length;
            }

            if (offsetStep >= maxSteps) {
                offsetStep = 0;
                break;
            }

            BlockPos offsetPos = new BlockPos(offsetPattern[offsetStep]);
            BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

            if (placeBlock(targetPos)) {
                blocksPlaced++;
            }

            offsetStep++;

        }

        if (blocksPlaced > 0) {

            if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
                mc.player.inventory.currentItem = playerHotbarSlot;
                lastHotbarSlot = playerHotbarSlot;
            }

            if (isSneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                isSneaking = false;
            }

        }

        totalTicksRunning++;

    }

    private boolean placeBlock(BlockPos pos) {

        // check if block is already placed
        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }

        // check if entity blocks placing
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }

        EnumFacing side = getPlaceableSide(pos);

        // check if we have a block adjacent to blockpos to click at
        if (side == null) {
            return false;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        // check if neighbor can be right clicked
        if (!canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        int obiSlot = findObiInHotbar();

        if (obiSlot == -1) {
            this.disable();
        }

        if (lastHotbarSlot != obiSlot) {
            mc.player.inventory.currentItem = obiSlot;
            lastHotbarSlot = obiSlot;
        }

        if (!isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }

        if (rotate2.getValBoolean()) {
            faceVectorPacketInstant(hitVec);
        }

        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;

        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));



        return true;

    }

    private int findObiInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockEnderChest) {
                slot = i;
                break;
            }

        }

        return slot;

    }

    private static class Offsets {

        private static final Vec3d[] SURROUND = {
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, 0, -1),
                new Vec3d(1, 1, 0),
                new Vec3d(0, 1, 1),
                new Vec3d(-1, 1, 0),
                new Vec3d(0, 1, -1),

        };

    }

}