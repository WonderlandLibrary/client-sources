package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventMotion;
import im.expensive.events.EventUpdate;
import im.expensive.events.PlaceObsidianEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
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

@FunctionRegister(name = "AutoExplosion", type = Category.Combat)
public class AutoExplosion extends Function {

    private final BooleanSetting safeYourSelf = new BooleanSetting("Не взрывать себя", true);

    private Entity crystalEntity = null;
    private BlockPos obsidianPosition = null;
    private int oldCurrentItemSlot = -1;
    private Vector2f rotationVectorToPosition = new Vector2f(0, 0);
    StopWatch nextAttackStopWatch = new StopWatch();
    StopWatch placeCrystalStopWatch = new StopWatch();
    boolean canPlaceCrystal = false;
    boolean itemInOffHand;

    public AutoExplosion() {
        addSettings(safeYourSelf);
    }

    @Subscribe
    public void onObsidianPlace(PlaceObsidianEvent e) {
        placeCrystalStopWatch.reset();
        this.obsidianPosition = e.getPos();
        canPlaceCrystal = true;
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        int slotInHotBar = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.END_CRYSTAL, true);

        if (shouldToPlaceCrystal(slotInHotBar)) {
            setAndUseCrystal(slotInHotBar, obsidianPosition);
        }

        if (obsidianPosition != null) {
            findEnderCrystals(obsidianPosition).forEach(this::attackCrystal);
        }

        if (crystalEntity != null) {
            if (!crystalEntity.isAlive()) {
                reset();
            }
        }
    }

    @Subscribe
    private void onMotion(EventMotion e) {
        if (isValid(crystalEntity)) {
            rotationVectorToPosition = MathUtil.rotationToEntity(crystalEntity);
            e.setYaw(rotationVectorToPosition.x);
            e.setPitch(rotationVectorToPosition.y);
            mc.player.renderYawOffset = rotationVectorToPosition.x;
            mc.player.rotationYawHead = rotationVectorToPosition.x;
            mc.player.rotationPitchHead = rotationVectorToPosition.y;
        }
    }

    private boolean shouldToPlaceCrystal(int slotInHotBar) {
        itemInOffHand = mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        return (slotInHotBar != -1 || itemInOffHand)
                && obsidianPosition != null
                && placeCrystalStopWatch.isReached(50L)
                && canPlaceCrystal;
    }

    private void setAndUseCrystal(int slot, BlockPos pos) {
        oldCurrentItemSlot = mc.player.inventory.currentItem;
        Vector3d blockCenter = new Vector3d(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
        Hand hand = itemInOffHand ? Hand.OFF_HAND : Hand.MAIN_HAND;

        if (!itemInOffHand) {
            mc.player.inventory.currentItem = slot;
        }
        if (mc.playerController.processRightClickBlock(
                mc.player, mc.world, hand,
                new BlockRayTraceResult(blockCenter, Direction.UP, pos, false)) == ActionResultType.SUCCESS) {
            mc.player.swingArm(Hand.MAIN_HAND);
        }

        if (!itemInOffHand) {
            mc.player.inventory.currentItem = oldCurrentItemSlot;
        }
        canPlaceCrystal = false;
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

    private void attackCrystal(Entity entity) {
        if (isValid(entity) && mc.player.getCooledAttackStrength(1) == 1 && nextAttackStopWatch.hasTimeElapsed()) {

            nextAttackStopWatch.setLastMS(500);
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(Hand.MAIN_HAND);
            crystalEntity = entity;
        }
        if (!entity.isAlive()) {
            reset();
        }
    }

    private boolean isValid(Entity base) {
        if (base == null) {
            return false;
        }
        if (obsidianPosition == null) {
            return false;
        }
        if (safeYourSelf.get() && mc.player.getPosY() > obsidianPosition.getY()) {
            return false;
        }
        return isCorrectDistance();
    }

    private boolean isCorrectDistance() {
        if (obsidianPosition == null) {
            return false;
        }
        return mc.player.getPositionVec().distanceTo(
                new Vector3d(obsidianPosition.getX(),
                        obsidianPosition.getY(),
                        obsidianPosition.getZ())) <= mc.playerController.getBlockReachDistance();
    }

    @Override
    public void onDisable() {
        reset();
        super.onDisable();
    }

    private void reset() {
        crystalEntity = null;
        rotationVectorToPosition = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
        oldCurrentItemSlot = -1;
        obsidianPosition = null;
        canPlaceCrystal = false;
        placeCrystalStopWatch.reset();
    }
}
