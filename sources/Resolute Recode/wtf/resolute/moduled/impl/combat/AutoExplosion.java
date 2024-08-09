package wtf.resolute.moduled.impl.combat;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventMotion;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.PlaceObsidianEvent;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.StopWatch;
import wtf.resolute.utiled.player.InventoryUtil;
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

import java.util.List;
import java.util.stream.Collectors;

@ModuleAnontion(name = "AutoExplosion", type = Categories.Combat, server = "")
public class AutoExplosion extends Module {

    private final BooleanSetting safeYourSelf = new BooleanSetting("Не взрывать себя", true);
    private final BooleanSetting enableMovementCorrection = new BooleanSetting("Коррекция движения", true);

    private Entity crystalEntity = null;
    private BlockPos obsidianPos = null;
    private int oldCurrentSlot = -1;
    private StopWatch attackStopWatch = new StopWatch();
    private boolean correctingMovement = false;

    public AutoExplosion() {
        addSettings(safeYourSelf, enableMovementCorrection);
    }

    @Subscribe
    public void onObsidianPlace(PlaceObsidianEvent e) {
        BlockPos placedObsidianPos = e.getPos();

        if (placedObsidianPos != null) {
            obsidianPos = placedObsidianPos;
            setAndUseCrystal(placedObsidianPos);
            correctingMovement = false;
        }
    }

    private void setAndUseCrystal(BlockPos pos) {
        int crystalSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.END_CRYSTAL, true);
        if (crystalSlot == -1) {
            return;
        }

        int prevSlot = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = crystalSlot;

        Vector3d center = new Vector3d(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);

        Hand hand = Hand.MAIN_HAND;
        if (mc.playerController.processRightClickBlock(mc.player, mc.world, hand,
                new BlockRayTraceResult(center, Direction.UP, pos, false)) == ActionResultType.SUCCESS) {
            mc.player.swingArm(Hand.MAIN_HAND);
        }

        mc.player.inventory.currentItem = prevSlot;
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (obsidianPos != null)
            findEnderCrystals(obsidianPos).forEach(this::attackCrystal);

        if (crystalEntity != null && !crystalEntity.isAlive()) {
            reset();
        }
    }

    @Subscribe
    private void onMotion(EventMotion e) {
        if (obsidianPos != null && !correctingMovement && enableMovementCorrection.get()) {
            Vector3d obsidianCenter = new Vector3d(obsidianPos.getX() + 0.5, obsidianPos.getY() + 0.5, obsidianPos.getZ() + 0.5);
            Vector3d playerPos = mc.player.getPositionVec();
            Vector3d lookVec = obsidianCenter.subtract(playerPos).normalize();
            double pitch = Math.asin(lookVec.y);
            double yaw = Math.atan2(-lookVec.x, lookVec.z);
            yaw = Math.toDegrees(yaw);
            pitch = Math.toDegrees(pitch);
            mc.player.rotationYawHead = (float) yaw;
            mc.player.rotationPitchHead = (float) pitch;

            correctingMovement = true;
        }
    }

    private void attackCrystal(Entity entity) {
        if (isValid(entity) && mc.player.getCooledAttackStrength(1.0f) >= 1.0f && attackStopWatch.hasTimeElapsed()) {
            attackStopWatch.setLastMS(500);
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(Hand.MAIN_HAND);
            crystalEntity = entity;
        }
    }

    private boolean isValid(Entity base) {
        if (base == null || obsidianPos == null || (safeYourSelf.get() && mc.player.getPosY() > obsidianPos.getY()))
            return false;
        return isCorrectDistance();
    }

    private boolean isCorrectDistance() {
        if (obsidianPos == null) {
            return false;
        }
        return mc.player.getPositionVec().distanceTo(
                new Vector3d(obsidianPos.getX(),
                        obsidianPos.getY(),
                        obsidianPos.getZ())) <= mc.playerController.getBlockReachDistance();
    }

    public List<Entity> findEnderCrystals(BlockPos position) {
        return mc.world.getEntitiesWithinAABBExcludingEntity(null,
                        new AxisAlignedBB(
                                position.getX(),
                                position.getY(),
                                position.getZ(),
                                position.getX() + 1.0,
                                position.getY() + 2.0,
                                position.getZ() + 1.0))
                .stream()
                .filter(entity -> entity instanceof EnderCrystalEntity)
                .collect(Collectors.toList());
    }

    private void reset() {
        crystalEntity = null;
        obsidianPos = null;
        oldCurrentSlot = -1;
    }

    @Override
    public void onDisable() {
        reset();
        correctingMovement = false;
        super.onDisable();
    }
}