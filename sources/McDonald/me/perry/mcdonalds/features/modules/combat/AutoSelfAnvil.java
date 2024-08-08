// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockEnderChest;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.block.BlockAnvil;
import java.util.Comparator;
import net.minecraft.util.math.Vec3i;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import me.perry.mcdonalds.util.BlockUtil;
import me.perry.mcdonalds.util.EntityUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.perry.mcdonalds.event.events.UpdateWalkingPlayerEvent;
import java.util.HashMap;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class AutoSelfAnvil extends Module
{
    private final Setting<Integer> blocksPerTick;
    private final Setting<Integer> delay;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> disableTime;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> packet;
    private final Timer offTimer;
    private final Timer timer;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    private int blocksThisTick;
    private boolean isSneaking;
    private boolean hasOffhand;
    
    public AutoSelfAnvil() {
        super("AutoSelfAnvil", "Lure your enemies in!", Category.COMBAT, true, false, true);
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)20));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)250));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.disableTime = (Setting<Integer>)this.register(new Setting("DisableTime", (T)200, (T)50, (T)500));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.offTimer = new Timer();
        this.timer = new Timer();
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.blocksThisTick = 0;
        this.hasOffhand = false;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
        this.offTimer.reset();
    }
    
    @Override
    public void onTick() {
        if (this.isOn() && (this.blocksPerTick.getValue() != 1 || !this.rotate.getValue())) {
            this.doHoleFill();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (this.isOn() && event.getStage() == 0 && this.blocksPerTick.getValue() == 1 && this.rotate.getValue()) {
            this.doHoleFill();
        }
    }
    
    @Override
    public void onDisable() {
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.retries.clear();
        this.hasOffhand = false;
    }
    
    private void doHoleFill() {
        if (this.check()) {
            return;
        }
        for (final BlockPos position : this.getPositions()) {
            final int placeability = BlockUtil.isPositionPlaceable(position, false);
            if (placeability == 1 && (this.retries.get(position) == null || this.retries.get(position) < 4)) {
                this.placeBlock(position);
                this.retries.put(position, (this.retries.get(position) == null) ? 1 : (this.retries.get(position) + 1));
            }
            if (placeability != 3) {
                continue;
            }
            this.placeBlock(position);
        }
    }
    
    private List<BlockPos> getPositions() {
        final ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        positions.add(new BlockPos(AutoSelfAnvil.mc.player.posX, AutoSelfAnvil.mc.player.posY + 2.0, AutoSelfAnvil.mc.player.posZ));
        final int placeability = BlockUtil.isPositionPlaceable(positions.get(0), false);
        switch (placeability) {
            case 0: {
                return new ArrayList<BlockPos>();
            }
            case 3: {
                return positions;
            }
            case 1: {
                if (BlockUtil.isPositionPlaceable(positions.get(0), false, false) == 3) {
                    return positions;
                }
            }
            case 2: {
                positions.add(new BlockPos(AutoSelfAnvil.mc.player.posX + 1.0, AutoSelfAnvil.mc.player.posY + 1.0, AutoSelfAnvil.mc.player.posZ));
                positions.add(new BlockPos(AutoSelfAnvil.mc.player.posX + 1.0, AutoSelfAnvil.mc.player.posY + 2.0, AutoSelfAnvil.mc.player.posZ));
                break;
            }
        }
        positions.sort(Comparator.comparingDouble(Vec3i::func_177956_o));
        return positions;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.blocksThisTick < this.blocksPerTick.getValue()) {
            final boolean smartRotate = this.blocksPerTick.getValue() == 1 && this.rotate.getValue();
            final int originalSlot = AutoSelfAnvil.mc.player.inventory.currentItem;
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockAnvil.class);
            final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            if (obbySlot == -1 && eChestSot == -1) {
                this.toggle();
            }
            AutoSelfAnvil.mc.player.inventory.currentItem = ((obbySlot == -1) ? eChestSot : obbySlot);
            AutoSelfAnvil.mc.playerController.updateController();
            this.isSneaking = (smartRotate ? BlockUtil.placeBlockSmartRotate(pos, this.hasOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, true, this.packet.getValue(), this.isSneaking) : BlockUtil.placeBlock(pos, this.hasOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking));
            AutoSelfAnvil.mc.player.inventory.currentItem = originalSlot;
            AutoSelfAnvil.mc.playerController.updateController();
            this.timer.reset();
            ++this.blocksThisTick;
        }
    }
    
    private boolean check() {
        if (fullNullCheck()) {
            this.disable();
            return true;
        }
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockAnvil.class);
        final int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            this.toggle();
        }
        this.blocksThisTick = 0;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        if (this.retryTimer.passedMs(2000L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        if (!EntityUtil.isSafe((Entity)AutoSelfAnvil.mc.player)) {
            this.offTimer.reset();
            return true;
        }
        if (this.disable.getValue() && this.offTimer.passedMs(this.disableTime.getValue())) {
            this.disable();
            return true;
        }
        return !this.timer.passedMs(this.delay.getValue());
    }
}
