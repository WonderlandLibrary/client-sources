package wtf.shiyeno.modules.impl.combat;

import java.util.List;
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
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventInput;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.player.EventObsidianPlace;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.movement.MoveUtil;

@FunctionAnnotation(
        name = "AutoExplosion",
        type = Type.Combat
)
public class AutoExplosionFunction extends Function {
    private BlockPos position = null;
    private Entity crystalEntity = null;
    private int oldSlot = -1;
    private final TimerUtil timerUtil = new TimerUtil();
    private final BooleanOption saveSelf = new BooleanOption("Не взрывать себя", true);
    public final BooleanOption correction = new BooleanOption("Коррекция движения", true);
    public Vector2f server;

    public AutoExplosionFunction() {
        this.addSettings(new Setting[]{this.saveSelf, this.correction});
    }

    public boolean check() {
        return this.server != null && this.correction.get() && this.crystalEntity != null && this.position != null && this.state;
    }

    public void onEvent(Event event) {
        if (event instanceof EventInput e) {
            if (this.check()) {
                MoveUtil.fixMovement(e, this.server.x);
            }
        }

        if (event instanceof EventObsidianPlace obsidianPlace) {
            this.handleObsidianPlace(obsidianPlace.getPos());
        }

        if (event instanceof EventUpdate updateEvent) {
            this.handleUpdateEvent(updateEvent);
        }

        if (event instanceof EventMotion motionEvent) {
            this.handleMotionEvent(motionEvent);
        }
    }

    private void handleUpdateEvent(EventUpdate updateEvent) {
        if (this.position != null) {
            List<Entity> crystals = mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB((double)this.position.getX(), (double)this.position.getY(), (double)this.position.getZ(), (double)(this.position.getX() + 1), (double)this.position.getY() + 2.0, (double)(this.position.getZ() + 1))).stream().filter((entity) -> {
                return entity instanceof EnderCrystalEntity;
            }).toList();
            crystals.forEach(this::attackEntity);
        }

        if (this.crystalEntity != null && !this.crystalEntity.isAlive()) {
            this.crystalEntity = null;
            this.position = null;
            this.server = null;
        }
    }

    private void handleObsidianPlace(BlockPos position) {
        int crystalSlot = this.getSlotWithCrystal();
        this.oldSlot = mc.player.inventory.currentItem;
        if (crystalSlot != -1 && position != null) {
            mc.player.inventory.currentItem = crystalSlot;
            BlockRayTraceResult rayTraceResult = new BlockRayTraceResult(new Vector3d((double)((float)position.getX() + 0.5F), (double)((float)position.getY() + 0.5F), (double)((float)position.getZ() + 0.5F)), Direction.UP, position, false);
            if (mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult) == ActionResultType.SUCCESS) {
                mc.player.swingArm(Hand.MAIN_HAND);
            }

            if (this.oldSlot != -1) {
                mc.player.inventory.currentItem = this.oldSlot;
            }

            this.oldSlot = -1;
            this.position = position;
        }
    }

    private void handleMotionEvent(EventMotion motionEvent) {
        if (this.isValid(this.crystalEntity)) {
            this.server = MathUtil.rotationToEntity(this.crystalEntity);
            motionEvent.setYaw(this.server.x);
            motionEvent.setPitch(this.server.y);
            mc.player.rotationYawHead = this.server.x;
            mc.player.renderYawOffset = this.server.x;
            mc.player.rotationPitchHead = this.server.y;
        }
    }

    private void attackEntity(Entity base) {
        if (this.isValid(base) && !((double)mc.player.getCooledAttackStrength(1.0F) < 0.01)) {
            mc.playerController.attackEntity(mc.player, base);
            mc.player.swingArm(Hand.MAIN_HAND);
            this.crystalEntity = base;
        }
    }

    private int getSlotWithCrystal() {
        for(int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }

        return -1;
    }

    private boolean isCorrectDistance() {
        if (this.position == null) {
            return false;
        } else {
            return mc.player.getPositionVec().distanceTo(new Vector3d((double)this.position.getX(), (double)this.position.getY(), (double)this.position.getZ())) <= (double)mc.playerController.getBlockReachDistance();
        }
    }

    private boolean isValid(Entity base) {
        if (base == null) {
            return false;
        } else if (this.position == null) {
            return false;
        } else {
            return this.saveSelf.get() && base.getPosition().getY() <= mc.player.getPosition().getY() ? false : this.isCorrectDistance();
        }
    }

    protected void onDisable() {
        this.server = null;
        this.position = null;
        this.crystalEntity = null;
        this.oldSlot = -1;
        super.onDisable();
    }
}