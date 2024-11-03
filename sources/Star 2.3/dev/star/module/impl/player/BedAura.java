package dev.star.module.impl.player;

import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.player.UpdateEvent;
import dev.star.event.impl.render.Render3DEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.player.BlockUtils;
import dev.star.utils.player.RotationUtils;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.server.ServerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BedAura extends Module {
    private final Map<BlockPos, Float> breakProgressMap = new HashMap<>();
    public static boolean outsideSpawn = true;
    private int ticksAfterBreak = 0;
    private final int breakTickDelay = 5;
    private BlockPos nearestBlock;
    private int currentSlot = -1;
    public BlockPos currentBlock;
    public boolean stopAutoblock;
    public float vanillaProgress;
    private long lastCheck = 0;
    public float breakProgress;
    private boolean delayStart;
    public double lastProgress;
    private int lastSlot = -1;
    private BlockPos[] bedPos;
    private BlockPos spawnPos;
    private boolean rotate;
    private boolean check;

    private final ModeSetting mode = new ModeSetting("Break mode", "Swap","Legit", "Instant", "Swap");
    private final NumberSetting fov = new NumberSetting("FOV", 360.0, 360, 30.0, 4.0);
    private final NumberSetting range = new NumberSetting("Range", 4.5, 8, 1.0, 0.5);
    private final NumberSetting rate = new NumberSetting("Rate", 0.2, 3, 0.05, 0.05);
    private final BooleanSetting  breakNearBlock = new BooleanSetting("Break near block", false);
    private final BooleanSetting renderOutline = new BooleanSetting("Render block outline", true);
    private final BooleanSetting whitelistOwnBed = new BooleanSetting("White List Own Bed", true);
    private final BooleanSetting  renderFullBlock = new BooleanSetting("Render Full block", false);
    private final BooleanSetting sendAnimations = new BooleanSetting("Send animations", false);

    public BedAura() {
        super("BedAura", Category.PLAYER, "Breaks Beds");
        addSettings(mode, fov, range, rate, breakNearBlock,  renderOutline, sendAnimations, whitelistOwnBed, renderFullBlock);
    }

    @Override
    public void onDisable() {
        reset(true);
    }

    @Override
    public void onUpdateEvent(UpdateEvent e) {
        if (!this.isEnabled())
            return;
        if (ServerUtils.getBedwarsStatus() == 2) {

            if (whitelistOwnBed.isEnabled()) {
                if (check) {
                    spawnPos = mc.thePlayer.getPosition();
                    check = false;
                }
                if (spawnPos == null) {
                    outsideSpawn = true;
                }
                else {
                    outsideSpawn = mc.thePlayer.getDistanceSq(spawnPos) > 800;
                }
            }
            else {
                outsideSpawn = true;
            }
        }

        if (whitelistOwnBed.isEnabled() && !outsideSpawn) {
            reset(true);
            return;
        }

        if (!mc.thePlayer.capabilities.allowEdit || mc.thePlayer.isSpectator()) {
            reset(true);
            return;
        }
        if (bedPos == null) {
            if (System.currentTimeMillis() - lastCheck >= rate.getValue() * 1000) {
                lastCheck = System.currentTimeMillis();
                bedPos = getBedPos();
            }
            if (bedPos == null) {
                reset(true);
                return;
            }
        }
        else {
            if (!(BlockUtils.getBlockAtPos(bedPos[0]) instanceof BlockBed) || (currentBlock != null && BlockUtils.replaceable(currentBlock))) {
                reset(true);
                return;
            }
        }
        if (delayStart) {
            resetSlot();
            if (ticksAfterBreak++ <= breakTickDelay) {
                return;
            }
            else {
                delayStart = false;
                ticksAfterBreak = 0;
            }
        }
        else {
            ticksAfterBreak = 0;
        }
        if (breakNearBlock.isEnabled() && isCovered(bedPos[0]) && isCovered(bedPos[1])) {
            if (nearestBlock == null) {
                nearestBlock = getBestBlock(bedPos, true);
            }
            breakBlock(nearestBlock);
        }
        else {
            nearestBlock = null;
            resetSlot();
            breakBlock(getBestBlock(bedPos, false) != null ? getBestBlock(bedPos, false) : bedPos[0]);
        }
    }



    @Override
    public void onMotionEvent(MotionEvent e) {
        if (!this.isEnabled())
            return;
        if (rotate || breakProgress >= 1 && currentBlock != null) {
            float[] rotations = RotationUtils.getRotations(currentBlock, e.getYaw(), e.getPitch());
            if (!RotationUtils.inRange(currentBlock, range.getValue())) {
                return;
            }
            e.setYaw(rotations[0]);
            e.setPitch(rotations[1]);
            RotationUtils.setVisualRotations(rotations);
            rotate = false;
        }
    }

    @Override
    public void onRender3DEvent(Render3DEvent renderWorldLastEvent) {
        if (!this.isEnabled())
            return;
        if (!renderOutline.isEnabled() || currentBlock == null) {
            return;
        }

        renderBed(bedPos);
    }

    private void renderBed(final BlockPos[] array) {
        final double n = array[0].getX() - mc.getRenderManager().viewerPosX;
        final double n2 = array[0].getY() - mc.getRenderManager().viewerPosY;
        final double n3 = array[0].getZ() - mc.getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        final int e = ColorUtil.applyOpacity(Color.green, 0.5f).getRGB();
        final float n4 = (e >> 24 & 0xFF) / 255.0f;
        final float n5 = (e >> 16 & 0xFF) / 255.0f;
        final float n6 = (e >> 8 & 0xFF) / 255.0f;
        final float n7 = (e & 0xFF) / 255.0f;
        GL11.glColor4d(n5, n6, n7, n4);
        AxisAlignedBB axisAlignedBB;
        if (array[0].getX() != array[1].getX()) {
            if (array[0].getX() > array[1].getX()) {
                axisAlignedBB = new AxisAlignedBB(n - 1.0, n2, n3, n + 1.0, n2 + getBlockHeight(), n3 + 1.0);
            } else {
                axisAlignedBB = new AxisAlignedBB(n, n2, n3, n + 2.0, n2 + getBlockHeight(), n3 + 1.0);
            }
        } else if (array[0].getZ() > array[1].getZ()) {
            axisAlignedBB = new AxisAlignedBB(n, n2, n3 - 1.0, n + 1.0, n2 + getBlockHeight(), n3 + 1.0);
        } else {
            axisAlignedBB = new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + getBlockHeight(), n3 + 2.0);
        }
        Color color = ColorUtil.applyOpacity(Color.red, 1);
        RenderGlobal.drawOutlinedBoundingBox(axisAlignedBB, color.getRed(),color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    private float getBlockHeight() {
        return (renderFullBlock.isEnabled() ? 1 : 0.5625F);
    }

    private void resetSlot() {
        if (currentSlot != -1 && currentSlot != mc.thePlayer.inventory.currentItem && mode.is("Swap")) {
            setPacketSlot(mc.thePlayer.inventory.currentItem);
        }
        else if (lastSlot != -1) {
            lastSlot = mc.thePlayer.inventory.currentItem = lastSlot;
        }
    }


    private BlockPos[] getBedPos() {
        int range;
        priority:
        for (int n = range = this.range.getValue().intValue(); range >= -n; --range) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    final BlockPos blockPos = new BlockPos(mc.thePlayer.posX + j, mc.thePlayer.posY + range, mc.thePlayer.posZ + k);
                    final IBlockState getBlockState = mc.theWorld.getBlockState(blockPos);
                    if (getBlockState.getBlock() == Blocks.bed && getBlockState.getValue((IProperty) BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                        float fov = this.fov.getValue().floatValue();
                        if (fov != 360 && !RotationUtils.inFov(fov, blockPos)) {
                            continue priority;
                        }
                        return new BlockPos[]{blockPos, blockPos.offset((EnumFacing) getBlockState.getValue((IProperty) BlockBed.FACING))};
                    }
                }
            }
        }
        return null;
    }

    private BlockPos getBestBlock(BlockPos[] positions, boolean getSurrounding) {
        if (positions == null) {
            return null;
        }
        double maxRangeSquared = range.getValue() * range.getValue();
        double bestEfficiency = 0;
        BlockPos closestBlock = null;
        for (BlockPos pos : positions) {
            if (pos == null) {
                continue;
            }
            if (getSurrounding) {
                for (EnumFacing enumFacing : EnumFacing.values()) {
                    if (enumFacing == EnumFacing.DOWN) {
                        continue;
                    }
                    BlockPos offset = pos.offset(enumFacing);
                    if (Arrays.asList(positions).contains(offset)) {
                        continue;
                    }
                    if (!RotationUtils.inRange(offset, range.getValue())) {
                        continue;
                    }

                    double efficiency = getEfficiency(offset);
                    double distance = mc.thePlayer.getDistanceSqToCenter(offset);

                    if (betterBlock(distance, efficiency, maxRangeSquared, bestEfficiency)) {
                        maxRangeSquared = distance;
                        bestEfficiency = efficiency;
                        closestBlock = offset;
                    }
                }
            }
            else {
                if (!RotationUtils.inRange(pos, range.getValue())) {
                    continue;
                }

                double efficiency = getEfficiency(pos);
                double distance = mc.thePlayer.getDistanceSqToCenter(pos);

                if (betterBlock(distance, efficiency, maxRangeSquared, bestEfficiency)) {
                    maxRangeSquared = distance;
                    bestEfficiency = efficiency;
                    closestBlock = pos;
                }
            }
        }

        return closestBlock;
    }

    private double getEfficiency(BlockPos pos) {
        Block block = BlockUtils.getBlockAtPos(pos);
        ItemStack tool = (mode.is("Swap") && getTool(block) != -1) ? mc.thePlayer.inventory.getStackInSlot(getTool(block)) : mc.thePlayer.getHeldItem();
        double efficiency = BlockUtils.getBlockHardness(block, tool, false, false);

        if (breakProgressMap.get(pos) != null) {
            efficiency = breakProgressMap.get(pos);
        }

        return efficiency;
    }

    private boolean betterBlock(double distance, double efficiency, double maxRangeSquared, double bestEfficiency) {
        return (distance < maxRangeSquared || efficiency > bestEfficiency);
    }

    private void reset(boolean resetSlot) {
        if (resetSlot) {
            resetSlot();
            currentSlot = -1;
        }
        bedPos = null;
        breakProgress = 0;
        rotate = false;
        nearestBlock = null;
        ticksAfterBreak = 0;
        currentBlock = null;
        breakProgressMap.clear();
        lastSlot = -1;
        vanillaProgress = 0;
        delayStart = false;
        stopAutoblock = false;
        lastProgress = 0;
    }

    public void setPacketSlot(int slot) {
        if (slot == currentSlot || slot == -1) {
            return;
        }
        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot));
        currentSlot = slot;
    }

    private void startBreak(BlockPos blockPos) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
    }

    private void stopBreak(BlockPos blockPos) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
    }

    private void swing() {
        mc.thePlayer.swingItem();
    }

    private void breakBlock(BlockPos blockPos) {
        if (blockPos == null) {
            return;
        }
        float fov = this.fov.getValue().floatValue();
        if (fov != 360 && !RotationUtils.inFov(fov, blockPos)) {
            return;
        }
        if (!RotationUtils.inRange(blockPos, range.getValue())) {
            return;
        }
        if (BlockUtils.replaceable(currentBlock == null ? blockPos : currentBlock)) {
            reset(true);
            return;
        }
        currentBlock = blockPos;
        Block block = BlockUtils.getBlockAtPos(blockPos);

        swing();

        if (mode.is("Swap") || mode.is("Legit")) {
            if (breakProgress == 0) {
                resetSlot();
                stopAutoblock = true;
                rotate = true;
                if (mode.is("Legit")) {
                    setSlot(getTool(block));
                }
                startBreak(blockPos);
            }
            else if (breakProgress >= 1) {
                if (mode.is("Swap")) {
                    setPacketSlot(getTool(block));
                }
                stopBreak(blockPos);
                reset(false);
                stopAutoblock = true;
                delayStart = true;
                Iterator<Map.Entry<BlockPos, Float>> iterator = breakProgressMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<BlockPos, Float> entry = iterator.next();
                    if (entry.getKey().equals(blockPos)) {
                        iterator.remove();
                    }
                }
                mc.playerController.onPlayerDestroyBlock(blockPos, EnumFacing.UP);

                return;
            }
            else {
                if (mode.is("Legit")) {
                    stopAutoblock = true;
                    rotate = true;
                }
            }
            double progress = vanillaProgress = BlockUtils.getBlockHardness(block, (mode.is("Swap") && getTool(block) != -1) ? mc.thePlayer.inventory.getStackInSlot(getTool(block)) : mc.thePlayer.getHeldItem(), false, false);
            if (lastProgress != 0 && breakProgress >= lastProgress) {
                stopAutoblock = true;
            }
            breakProgress += progress;
            breakProgressMap.put(blockPos, breakProgress);
            if (sendAnimations.isEnabled()) {
                mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), blockPos, (int) ((breakProgress * 10)));
            }
            lastProgress = 0;
            while (lastProgress + progress < 1) {
                lastProgress += progress;
            }
        }
        else if (mode.is("Instant")) {
            stopAutoblock = true;
            rotate = true;
            swing();

            startBreak(blockPos);
            setSlot(getTool(block));
            stopBreak(blockPos);
        }
    }

    private void setSlot(int slot) {
        if (slot == -1 || slot == mc.thePlayer.inventory.currentItem) {
            return;
        }
        if (lastSlot == -1) {
            lastSlot = mc.thePlayer.inventory.currentItem;
        }
        mc.thePlayer.inventory.currentItem = slot;
    }



    private boolean isCovered(BlockPos blockPos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            BlockPos offset = blockPos.offset(enumFacing);
            if (BlockUtils.replaceable(offset) || BlockUtils.notFull(BlockUtils.getBlock(offset)) ) {
                return false;
            }
        }
        return true;
    }

    public static float getEfficiency(final ItemStack itemStack, final Block block) {
        float getStrVsBlock = itemStack.getStrVsBlock(block);
        if (getStrVsBlock > 1.0f) {
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            if (getEnchantmentLevel > 0) {
                getStrVsBlock += getEnchantmentLevel * getEnchantmentLevel + 1;
            }
        }
        return getStrVsBlock;
    }

    public static int getTool(Block block) {
        float n = 1.0f;
        int n2 = -1;
        for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null) {
                final float a = getEfficiency(getStackInSlot, block);
                if (a > n) {
                    n = a;
                    n2 = i;
                }
            }
        }
        return n2;
    }
}