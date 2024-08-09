package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import im.expensive.functions.impl.combat.KillAura;
import im.expensive.functions.settings.impl.SliderSetting;
import net.minecraftforge.eventbus.api.Event;

@FunctionRegister(name = "TargetStrafe", type = Category.Movement)
public class TargetStrafe extends Function {
    private static final int MIN_TICKS_BEFORE_STRAFE = 20;
    private final KillAura killAura;
    private boolean switchDirection = true;
    private double currentSpeed = 0.0;

    public SliderSetting groundSpeed = new SliderSetting("Скорость на земле", 0.07f, 0.01f, 1.0f, 0.01f);
    public SliderSetting airSpeed = new SliderSetting("Скорость в воздухе", 0.18f, 0.01f, 1.0f, 0.01f);
    public SliderSetting distanceFromTarget = new SliderSetting("Дистанция до цели", 0.6f, 0.5f, 6.0f, 0.1f);
    public BooleanSetting autoJump = new BooleanSetting("Авто прыжок", true);

    public TargetStrafe(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(this.distanceFromTarget, this.groundSpeed, this.airSpeed, this.autoJump);
    }

    @Subscribe
    public boolean onEvent(Event event) {
        LivingEntity auraTarget = this.getTarget();
        if (auraTarget != null && TargetStrafe.mc.player.ticksExisted >= MIN_TICKS_BEFORE_STRAFE) {
            double targetSpeed = TargetStrafe.mc.player.isOnGround() ? this.groundSpeed.get() : this.airSpeed.get();
            double distance = TargetStrafe.mc.player.getDistance(auraTarget);
            double angleToTarget = Math.atan2(TargetStrafe.mc.player.getPosZ() - auraTarget.getPosZ(), TargetStrafe.mc.player.getPosX() - auraTarget.getPosX());
            double directionModifier = this.switchDirection ? targetSpeed / distance : -targetSpeed / distance;

            angleToTarget += directionModifier;

            double x = auraTarget.getPosX() + this.distanceFromTarget.get() * Math.cos(angleToTarget);
            double z = auraTarget.getPosZ() + this.distanceFromTarget.get() * Math.sin(angleToTarget);

            if (shouldSwitchDirection(x, z)) {
                this.switchDirection = !this.switchDirection;
                directionModifier = this.switchDirection ? targetSpeed / distance : -targetSpeed / distance;
                angleToTarget += directionModifier;

                x = auraTarget.getPosX() + this.distanceFromTarget.get() * Math.cos(angleToTarget);
                z = auraTarget.getPosZ() + this.distanceFromTarget.get() * Math.sin(angleToTarget);
            }

            double wrappedDegrees = wrapDegrees(x, z);

            if (currentSpeed < targetSpeed) {
                currentSpeed += 0.01;
                if (currentSpeed > targetSpeed) {
                    currentSpeed = targetSpeed;
                }
            } else if (currentSpeed > targetSpeed) {
                currentSpeed -= 0.01;
                if (currentSpeed < targetSpeed) {
                    currentSpeed = targetSpeed;
                }
            }

            TargetStrafe.mc.player.motion.x = currentSpeed * -Math.sin(Math.toRadians(wrappedDegrees));
            TargetStrafe.mc.player.motion.z = currentSpeed * Math.cos(Math.toRadians(wrappedDegrees));

            if (this.autoJump.get() && TargetStrafe.mc.player.isOnGround()) {
                TargetStrafe.mc.player.jump();
            }

            return false;
        }
        return false;
    }


    private boolean shouldSwitchDirection(double x, double z) {
        if (TargetStrafe.mc.player.collidedHorizontally ||
                TargetStrafe.mc.gameSettings.keyBindLeft.pressed ||
                TargetStrafe.mc.gameSettings.keyBindRight.pressed) {
            return true;
        }

        for (int y = (int) (TargetStrafe.mc.player.getPosY() + 4.0); y >= 0; --y) {
            BlockPos playerPos = new BlockPos(x, y, z);
            if (isHazardousBlock(TargetStrafe.mc.world.getBlockState(playerPos).getBlock())) {
                return true;
            }
            if (!TargetStrafe.mc.world.isAirBlock(playerPos)) {
                return false;
            }
        }
        return true;
    }

    private boolean isHazardousBlock(Block block) {
        return block.equals(Blocks.LAVA) || block.equals(Blocks.FIRE);
    }

    private LivingEntity getTarget() {
        return this.killAura.isState() ? this.killAura.getTarget() : null;
    }

    private double wrapDegrees(double x, double z) {
        double diffX = x - TargetStrafe.mc.player.getPosX();
        double diffZ = z - TargetStrafe.mc.player.getPosZ();
        return Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
    }
}