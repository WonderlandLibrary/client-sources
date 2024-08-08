// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraft.util.EnumHand;
import me.perry.mcdonalds.features.command.Command;
import net.minecraft.block.BlockEnderChest;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import me.perry.mcdonalds.util.BlockUtil;
import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.McDonalds;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.util.EntityUtil;
import java.util.HashMap;
import java.util.HashSet;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import net.minecraft.util.math.Vec3d;
import java.util.Set;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Surround extends Module
{
    public static boolean isPlacing;
    private final Setting<Integer> blocksPerTick;
    private final Setting<Integer> delay;
    private final Setting<Boolean> noGhost;
    private final Setting<Boolean> center;
    private final Setting<Boolean> rotate;
    private final Timer timer;
    private final Timer retryTimer;
    private final Set<Vec3d> extendingBlocks;
    private final Map<BlockPos, Integer> retries;
    private int isSafe;
    private BlockPos startPos;
    private boolean didPlace;
    private boolean switchedItem;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int placements;
    private int extenders;
    private int obbySlot;
    private boolean offHand;
    
    public Surround() {
        super("Surround", "Surrounds you with Obsidian", Category.COMBAT, true, false, false);
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)30));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)0, (T)0, (T)500));
        this.noGhost = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.center = (Setting<Boolean>)this.register(new Setting("Center Lock", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.timer = new Timer();
        this.retryTimer = new Timer();
        this.extendingBlocks = new HashSet<Vec3d>();
        this.retries = new HashMap<BlockPos, Integer>();
        this.didPlace = false;
        this.placements = 0;
        this.extenders = 1;
        this.obbySlot = -1;
        this.offHand = false;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
        this.lastHotbarSlot = Surround.mc.player.inventory.currentItem;
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
        if (this.center.getValue()) {
            McDonalds.positionManager.setPositionPacket(this.startPos.getX() + 0.5, this.startPos.getY(), this.startPos.getZ() + 0.5, true, true, true);
        }
        this.retries.clear();
        this.retryTimer.reset();
    }
    
    @Override
    public void onTick() {
        this.doFeetPlace();
    }
    
    @Override
    public void onDisable() {
        if (nullCheck()) {
            return;
        }
        Surround.isPlacing = false;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
    }
    
    @Override
    public String getDisplayInfo() {
        switch (this.isSafe) {
            case 0: {
                return ChatFormatting.RED + "Unsafe";
            }
            case 1: {
                return ChatFormatting.YELLOW + "Safe";
            }
            default: {
                return ChatFormatting.GREEN + "Safe";
            }
        }
    }
    
    private void doFeetPlace() {
        if (this.check()) {
            return;
        }
        if (!EntityUtil.isSafe((Entity)Surround.mc.player, 0, true)) {
            this.isSafe = 0;
            this.placeBlocks(Surround.mc.player.getPositionVector(), EntityUtil.getUnsafeBlockArray((Entity)Surround.mc.player, 0, true), true, false, false);
        }
        else if (!EntityUtil.isSafe((Entity)Surround.mc.player, -1, false)) {
            this.isSafe = 1;
            this.placeBlocks(Surround.mc.player.getPositionVector(), EntityUtil.getUnsafeBlockArray((Entity)Surround.mc.player, -1, false), false, false, true);
        }
        else {
            this.isSafe = 2;
        }
        this.processExtendingBlocks();
        if (this.didPlace) {
            this.timer.reset();
        }
    }
    
    private void processExtendingBlocks() {
        if (this.extendingBlocks.size() == 2 && this.extenders < 1) {
            final Vec3d[] array = new Vec3d[2];
            int i = 0;
            final Iterator<Vec3d> iterator = this.extendingBlocks.iterator();
            while (iterator.hasNext()) {
                final Vec3d[] array2 = array;
                final int n = i;
                final Vec3d vec3d2 = iterator.next();
                array2[n] = vec3d2;
                final Vec3d vec3d = vec3d2;
                ++i;
            }
            final int placementsBefore = this.placements;
            if (this.areClose(array) != null) {
                this.placeBlocks(this.areClose(array), EntityUtil.getUnsafeBlockArrayFromVec3d(this.areClose(array), 0, true), true, false, true);
            }
            if (placementsBefore < this.placements) {
                this.extendingBlocks.clear();
            }
        }
        else if (this.extendingBlocks.size() > 2 || this.extenders >= 1) {
            this.extendingBlocks.clear();
        }
    }
    
    private Vec3d areClose(final Vec3d[] vec3ds) {
        int matches = 0;
        for (final Vec3d vec3d : vec3ds) {
            for (final Vec3d pos : EntityUtil.getUnsafeBlockArray((Entity)Surround.mc.player, 0, true)) {
                if (vec3d.equals((Object)pos)) {
                    ++matches;
                }
            }
        }
        if (matches == 2) {
            return Surround.mc.player.getPositionVector().add(vec3ds[0].add(vec3ds[1]));
        }
        return null;
    }
    
    private boolean placeBlocks(final Vec3d pos, final Vec3d[] vec3ds, final boolean hasHelpingBlocks, final boolean isHelping, final boolean isExtending) {
        boolean gotHelp = true;
        for (final Vec3d vec3d : vec3ds) {
            gotHelp = true;
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            switch (BlockUtil.isPositionPlaceable(position, false)) {
                case 1: {
                    if (this.retries.get(position) == null || this.retries.get(position) < 4) {
                        this.placeBlock(position);
                        this.retries.put(position, (this.retries.get(position) == null) ? 1 : (this.retries.get(position) + 1));
                        this.retryTimer.reset();
                        break;
                    }
                    if (McDonalds.speedManager.getSpeedKpH() != 0.0) {
                        break;
                    }
                    if (isExtending) {
                        break;
                    }
                    if (this.extenders >= 1) {
                        break;
                    }
                    this.placeBlocks(Surround.mc.player.getPositionVector().add(vec3d), EntityUtil.getUnsafeBlockArrayFromVec3d(Surround.mc.player.getPositionVector().add(vec3d), 0, true), hasHelpingBlocks, false, true);
                    this.extendingBlocks.add(vec3d);
                    ++this.extenders;
                    break;
                }
                case 2: {
                    if (!hasHelpingBlocks) {
                        break;
                    }
                    gotHelp = this.placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true, true);
                }
                case 3: {
                    if (gotHelp) {
                        this.placeBlock(position);
                    }
                    if (!isHelping) {
                        break;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean check() {
        if (nullCheck()) {
            return true;
        }
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            this.toggle();
        }
        this.offHand = InventoryUtil.isBlock(Surround.mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
        Surround.isPlacing = false;
        this.didPlace = false;
        this.extenders = 1;
        this.placements = 0;
        obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        final int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (this.isOff()) {
            return true;
        }
        if (this.retryTimer.passedMs(2500L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        if (obbySlot == -1 && !this.offHand && echestSlot == -1) {
            Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No Obsidian in hotbar disabling...");
            this.disable();
            return true;
        }
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        if (Surround.mc.player.inventory.currentItem != this.lastHotbarSlot && Surround.mc.player.inventory.currentItem != obbySlot && Surround.mc.player.inventory.currentItem != echestSlot) {
            this.lastHotbarSlot = Surround.mc.player.inventory.currentItem;
        }
        if (!this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player))) {
            this.disable();
            return true;
        }
        return !this.timer.passedMs(this.delay.getValue());
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < this.blocksPerTick.getValue()) {
            final int originalSlot = Surround.mc.player.inventory.currentItem;
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            if (obbySlot == -1 && eChestSot == -1) {
                this.toggle();
            }
            Surround.isPlacing = true;
            Surround.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
            Surround.mc.playerController.updateController();
            this.isSneaking = BlockUtil.placeBlock(pos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), this.isSneaking);
            Surround.mc.player.inventory.currentItem = originalSlot;
            Surround.mc.playerController.updateController();
            this.didPlace = true;
            ++this.placements;
        }
    }
    
    static {
        Surround.isPlacing = false;
    }
}
