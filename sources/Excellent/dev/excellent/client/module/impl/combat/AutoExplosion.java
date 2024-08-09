package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.other.BlockPlaceEvent;
import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.event.impl.player.StrafeEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.rotation.RotationUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.joml.Vector2f;

import java.util.List;

@ModuleInfo(name = "Auto Explosion", description = "Автоматически взрывает кристалл энда.", category = Category.COMBAT)
public class AutoExplosion extends Module {
    private BlockPos position = null;
    private Entity crystalEntity = null;
    private int oldSlot = -1;
    private final TimerUtil timerUtil = TimerUtil.create();

    private final BooleanValue saveSelf = new BooleanValue("Не взрывать себя", this, true);
    public final BooleanValue correction = new BooleanValue("Коррекция движения", this, true);

    public Vector2f server;

    public boolean check() {
        return server != null && correction.getValue() && crystalEntity != null && position != null;
    }

    private final Listener<WorldLoadEvent> onWorldLoad = event -> reset();
    private final Listener<WorldChangeEvent> onWorldChange = event -> reset();


    private void reset() {
        server = null;
        position = null;
        crystalEntity = null;
        oldSlot = -1;
    }

    private final Listener<UpdateEvent> onUpdate = this::handleUpdateEvent;
    private final Listener<MotionEvent> onMotion = this::handleMotionEvent;
    private final Listener<BlockPlaceEvent> onBlockPlace = event -> {
        if (event.getBlock() == Blocks.OBSIDIAN) {
            handleObsidianPlace(event.getPosition());
        }
    };
    private final Listener<MoveInputEvent> onMove = event -> {
        if (check()) {
            MoveUtil.fixMovement(event, server.x);
        }
    };
    private final Listener<StrafeEvent> onStrafe = event -> {
        if (check()) {
            event.setYaw(server.x);
        }
    };

    private void handleUpdateEvent(UpdateEvent updateEvent) {
        if (position != null) {
            List<Entity> crystals = mc.world.getEntitiesWithinAABBExcludingEntity(null,
                            new AxisAlignedBB(position.getX(),
                                    position.getY(),
                                    position.getZ(),
                                    position.getX() + 1.0,
                                    position.getY() + 2.0,
                                    position.getZ() + 1.0))
                    .stream()
                    .filter(entity -> entity instanceof EnderCrystalEntity)
                    .toList();

            crystals.forEach(this::attackEntity);
        }

        if (crystalEntity != null && !crystalEntity.isAlive()) {
            crystalEntity = null;
            position = null;
            server = null;
        }
    }

    private void handleObsidianPlace(BlockPos position) {
        final int crystalSlot = getSlotWithCrystal();

        this.oldSlot = mc.player.inventory.currentItem;


        if (crystalSlot == -1 || position == null) {
            return;
        }

        mc.player.inventory.currentItem = crystalSlot;

        BlockRayTraceResult rayTraceResult = new BlockRayTraceResult(
                new Vector3d(position.getX() + 0.5f, position.getY() + 0.5f, position.getZ() + 0.5f),
                Direction.UP,
                position,
                false
        );
        if (mc.playerController.rightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult)
                == ActionResultType.SUCCESS) {
            mc.player.swingArm(Hand.MAIN_HAND);
        }
        if (oldSlot != -1)
            mc.player.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
        this.position = position;
    }

    private void handleMotionEvent(MotionEvent motionEvent) {
        if (!isValid(crystalEntity)) {
            return;
        }

        server = RotationUtil.calculate(crystalEntity);

        motionEvent.setYaw(server.x);
        motionEvent.setPitch(server.y);
        mc.player.rotationYawHead = server.x;
        mc.player.renderYawOffset = server.x;
        mc.player.rotationPitchHead = server.y;
    }

    private void attackEntity(Entity base) {
        if (!isValid(base)
                || mc.player.getCooledAttackStrength(1) < 1) {
            return;
        }
        if (timerUtil.hasReached(400)) {

            mc.playerController.attackEntity(mc.player, base);
            mc.player.swingArm(Hand.MAIN_HAND);
            timerUtil.reset();
        }
        crystalEntity = base;
    }

    private int getSlotWithCrystal() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }

    private boolean isCorrectDistance() {
        if (position == null) {
            return false;
        }
        return mc.player.getPositionVec().distanceTo(
                new Vector3d(position.getX(),
                        position.getY(),
                        position.getZ())) <= mc.playerController.getBlockReachDistance();
    }

    private boolean isValid(Entity base) {
        if (base == null) {
            return false;
        }
        if (position == null) {
            return false;
        }
        if (saveSelf.getValue() && !(base.getPosition().getY() > mc.player.getPosition().getY())) {
            return false;
        }

        return isCorrectDistance();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        reset();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        reset();
    }
}
