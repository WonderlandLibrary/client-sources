// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import me.perry.mcdonalds.util.BlockUtil;
import me.perry.mcdonalds.util.EntityUtil;
import me.perry.mcdonalds.util.TestUtil;
import net.minecraft.block.BlockEnderChest;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import java.util.function.Consumer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.setting.Setting;
import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.features.modules.Module;

public class HoleFiller2 extends Module
{
    private static final BlockPos[] surroundOffset;
    private static HoleFiller2 INSTANCE;
    private final Setting<Integer> range;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerTick;
    private final Timer offTimer;
    private final Timer timer;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    private int blocksThisTick;
    private ArrayList<BlockPos> holes;
    private int trie;
    
    public HoleFiller2() {
        super("HoleFiller2", "Fills holes around you.", Category.COMBAT, true, false, true);
        this.range = (Setting<Integer>)this.register(new Setting("PlaceRange", (T)8, (T)0, (T)10));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)500));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)30));
        this.offTimer = new Timer();
        this.timer = new Timer();
        this.blocksThisTick = 0;
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.holes = new ArrayList<BlockPos>();
        this.setInstance();
    }
    
    public static HoleFiller2 getInstance() {
        if (HoleFiller2.INSTANCE == null) {
            HoleFiller2.INSTANCE = new HoleFiller2();
        }
        return HoleFiller2.INSTANCE;
    }
    
    private void setInstance() {
        HoleFiller2.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
        this.offTimer.reset();
        this.trie = 0;
    }
    
    @Override
    public void onTick() {
        if (this.isOn()) {
            this.doHoleFill();
        }
    }
    
    @Override
    public void onDisable() {
        this.retries.clear();
    }
    
    private void doHoleFill() {
        if (this.check()) {
            return;
        }
        this.holes = new ArrayList<BlockPos>();
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleFiller2.mc.player.getPosition().add(-this.range.getValue(), -this.range.getValue(), -this.range.getValue()), HoleFiller2.mc.player.getPosition().add((int)this.range.getValue(), (int)this.range.getValue(), (int)this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleFiller2.mc.world.getBlockState(pos).getMaterial().blocksMovement() && !HoleFiller2.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                final boolean solidNeighbours = (HoleFiller2.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFiller2.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFiller2.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | HoleFiller2.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (HoleFiller2.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFiller2.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFiller2.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | HoleFiller2.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) && HoleFiller2.mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && HoleFiller2.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && HoleFiller2.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
                if (!solidNeighbours) {
                    continue;
                }
                this.holes.add(pos);
            }
        }
        this.holes.forEach(this::placeBlock);
    }
    
    private void placeBlock(final BlockPos pos) {
        for (final Entity entity : HoleFiller2.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }
        if (this.blocksThisTick < this.blocksPerTick.getValue()) {
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            if (obbySlot == -1 && eChestSot == -1) {
                this.toggle();
            }
            final int originalSlot = HoleFiller2.mc.player.inventory.currentItem;
            HoleFiller2.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
            HoleFiller2.mc.playerController.updateController();
            TestUtil.placeBlock(pos);
            if (HoleFiller2.mc.player.inventory.currentItem != originalSlot) {
                HoleFiller2.mc.player.inventory.currentItem = originalSlot;
                HoleFiller2.mc.playerController.updateController();
            }
            this.timer.reset();
            ++this.blocksThisTick;
        }
    }
    
    private boolean check() {
        if (fullNullCheck()) {
            this.disable();
            return true;
        }
        this.blocksThisTick = 0;
        if (this.retryTimer.passedMs(2000L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        return !this.timer.passedMs(this.delay.getValue());
    }
    
    static {
        HoleFiller2.INSTANCE = new HoleFiller2();
        surroundOffset = BlockUtil.toBlockPos(EntityUtil.getOffsets(0, true));
    }
}
