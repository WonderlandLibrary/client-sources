/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.stream.Collectors;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.PlaceObsidianEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="AutoExplosion", type=Category.Combat)
public class AutoExplosion
extends Function {
    private final BooleanSetting safeYourSelf = new BooleanSetting("\u041d\u0435 \u0432\u0437\u0440\u044b\u0432\u0430\u0442\u044c \u0441\u0435\u0431\u044f", true);
    private Entity crystalEntity = null;
    private BlockPos obsidianPos = null;
    private int oldCurrentSlot = -1;
    private Vector2f rotationVector = new Vector2f(0.0f, 0.0f);
    StopWatch attackStopWatch = new StopWatch();
    int bestSlot = -1;
    int oldSlot = -1;

    public AutoExplosion() {
        this.addSettings(this.safeYourSelf);
    }

    @Subscribe
    public void onObsidianPlace(PlaceObsidianEvent placeObsidianEvent) {
        boolean bl;
        BlockPos blockPos = placeObsidianEvent.getPos();
        boolean bl2 = AutoExplosion.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        int n = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.END_CRYSTAL, true);
        int n2 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.END_CRYSTAL, false);
        this.bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
        boolean bl3 = bl = AutoExplosion.mc.player.inventory.getStackInSlot(this.bestSlot).getItem() != Items.AIR;
        if (bl2 && blockPos != null) {
            this.setAndUseCrystal(this.bestSlot, blockPos);
            this.obsidianPos = blockPos;
        }
        if (n2 == -1 && n != -1 && this.bestSlot != -1) {
            InventoryUtil.moveItem(n, this.bestSlot + 36, bl);
            if (bl && this.oldSlot == -1) {
                this.oldSlot = n;
            }
            if (blockPos != null) {
                this.oldCurrentSlot = AutoExplosion.mc.player.inventory.currentItem;
                this.setAndUseCrystal(this.bestSlot, blockPos);
                AutoExplosion.mc.player.inventory.currentItem = this.oldCurrentSlot;
                this.obsidianPos = blockPos;
            }
            AutoExplosion.mc.playerController.windowClick(0, this.oldSlot, 0, ClickType.PICKUP, AutoExplosion.mc.player);
            AutoExplosion.mc.playerController.windowClick(0, this.bestSlot + 36, 0, ClickType.PICKUP, AutoExplosion.mc.player);
            AutoExplosion.mc.playerController.windowClickFixed(0, this.oldSlot, 0, ClickType.PICKUP, AutoExplosion.mc.player, 250);
        } else if (n2 != -1 && blockPos != null) {
            this.oldCurrentSlot = AutoExplosion.mc.player.inventory.currentItem;
            this.setAndUseCrystal(n2, blockPos);
            AutoExplosion.mc.player.inventory.currentItem = this.oldCurrentSlot;
            this.obsidianPos = blockPos;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (this.obsidianPos != null) {
            this.findEnderCrystals(this.obsidianPos).forEach(this::attackCrystal);
        }
        if (this.crystalEntity != null && !this.crystalEntity.isAlive()) {
            this.reset();
        }
    }

    @Subscribe
    private void onMotion(EventMotion eventMotion) {
        if (this.isValid(this.crystalEntity)) {
            this.rotationVector = MathUtil.rotationToEntity(this.crystalEntity);
            eventMotion.setYaw(this.rotationVector.x);
            eventMotion.setPitch(this.rotationVector.y);
            AutoExplosion.mc.player.renderYawOffset = this.rotationVector.x;
            AutoExplosion.mc.player.rotationYawHead = this.rotationVector.x;
            AutoExplosion.mc.player.rotationPitchHead = this.rotationVector.y;
        }
    }

    @Override
    public void onDisable() {
        this.reset();
        super.onDisable();
    }

    private void attackCrystal(Entity entity2) {
        if (this.isValid(entity2) && AutoExplosion.mc.player.getCooledAttackStrength(1.0f) >= 1.0f && this.attackStopWatch.hasTimeElapsed()) {
            this.attackStopWatch.setLastMS(500L);
            AutoExplosion.mc.playerController.attackEntity(AutoExplosion.mc.player, entity2);
            AutoExplosion.mc.player.swingArm(Hand.MAIN_HAND);
            this.crystalEntity = entity2;
        }
        if (!entity2.isAlive()) {
            this.reset();
        }
    }

    private void setAndUseCrystal(int n, BlockPos blockPos) {
        Hand hand;
        boolean bl = AutoExplosion.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        Vector3d vector3d = new Vector3d((float)blockPos.getX() + 0.5f, (float)blockPos.getY() + 0.5f, (float)blockPos.getZ() + 0.5f);
        if (!bl) {
            AutoExplosion.mc.player.inventory.currentItem = n;
        }
        Hand hand2 = hand = bl ? Hand.OFF_HAND : Hand.MAIN_HAND;
        if (AutoExplosion.mc.playerController.processRightClickBlock(AutoExplosion.mc.player, AutoExplosion.mc.world, hand, new BlockRayTraceResult(vector3d, Direction.UP, blockPos, false)) == ActionResultType.SUCCESS) {
            AutoExplosion.mc.player.swingArm(Hand.MAIN_HAND);
        }
    }

    private boolean isValid(Entity entity2) {
        if (entity2 == null) {
            return true;
        }
        if (this.obsidianPos == null) {
            return true;
        }
        if (((Boolean)this.safeYourSelf.get()).booleanValue() && AutoExplosion.mc.player.getPosY() > (double)this.obsidianPos.getY()) {
            return true;
        }
        return this.isCorrectDistance();
    }

    private boolean isCorrectDistance() {
        if (this.obsidianPos == null) {
            return true;
        }
        return AutoExplosion.mc.player.getPositionVec().distanceTo(new Vector3d(this.obsidianPos.getX(), this.obsidianPos.getY(), this.obsidianPos.getZ())) <= (double)AutoExplosion.mc.playerController.getBlockReachDistance();
    }

    public List<Entity> findEnderCrystals(BlockPos blockPos) {
        return AutoExplosion.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), (double)blockPos.getX() + 1.0, (double)blockPos.getY() + 2.0, (double)blockPos.getZ() + 1.0)).stream().filter(AutoExplosion::lambda$findEnderCrystals$0).collect(Collectors.toList());
    }

    private void reset() {
        this.crystalEntity = null;
        this.obsidianPos = null;
        this.rotationVector = new Vector2f(AutoExplosion.mc.player.rotationYaw, AutoExplosion.mc.player.rotationPitch);
        this.oldCurrentSlot = -1;
        this.bestSlot = -1;
    }

    private static boolean lambda$findEnderCrystals$0(Entity entity2) {
        return entity2 instanceof EnderCrystalEntity;
    }
}

