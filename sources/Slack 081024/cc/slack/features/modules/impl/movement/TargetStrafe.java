package cc.slack.features.modules.impl.movement;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(
        name = "TargetStrafe",
        category = Category.MOVEMENT
)
public class TargetStrafe extends Module {

    private final NumberValue<Double> distance = new NumberValue<>("Distance", 1.5D, 0.1D, 5D, 0.1D);
    private final BooleanValue holdSpace = new BooleanValue("Hold Space", true);
    private final BooleanValue fallCheck = new BooleanValue("Fall Check", true);
    private final BooleanValue wallCheck = new BooleanValue("Wall Check", true);
    private final BooleanValue speedOnly = new BooleanValue("Speed Only", true);
    private final BooleanValue onStrafe = new BooleanValue("Only On Strafe", true);


    private static int direction = -1;
    private static int index = 0, ticks = 0;

    public TargetStrafe() {
        addSettings(distance,holdSpace,fallCheck,wallCheck,speedOnly,onStrafe);
    }

    @Override
    public void onEnable() {
        index = 0;
        direction = -1;
        ticks = 0;
    }

    @Listen
    public void onMove (MoveEvent event) {
        if (!MovementUtil.onStrafe && onStrafe.getValue())
            return;

        setSpeed(event, MovementUtil.getSpeed());
    }


    public static void setSpeed(MoveEvent e, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        TargetStrafe ts = Slack.getInstance().getModuleManager().getInstance(TargetStrafe.class);
        if(Slack.getInstance().getModuleManager().getInstance(KillAura.class).target == null) {
            return;
        }
        if((ts.holdSpace.getValue() && !mc.gameSettings.keyBindJump.isKeyDown()) || (!(Slack.getInstance().getModuleManager().getInstance(Speed.class).isToggle() || Slack.getInstance().getModuleManager().getInstance(Flight.class).isToggle()) && ts.speedOnly.getValue())) {
            if(Slack.getInstance().getModuleManager().getInstance(Flight.class).isToggle() || Slack.getInstance().getModuleManager().getInstance(Speed.class).isToggle()) {
                setSpeedTargetStrafe(e, speed, mc.thePlayer.rotationYaw, mc.thePlayer.moveStrafing, mc.thePlayer.movementInput.moveForward);
            }
            return;
        }
        List<Point> points = ts.getPoints(Slack.getInstance().getModuleManager().getInstance(KillAura.class).target);
        index = ts.getIndex(points);

        if((!points.get(index).isValid() || mc.thePlayer.isCollidedHorizontally) && ticks > 5) {
            direction = -direction;
            ticks = 0;
        }

        ++ticks;

        ts.changeIndex();

        index = Math.max(index, 0);
        Point point = points.get(index);
        float yaw = getRotationFromPosition(point.getX(), point.getZ());
        setSpeedTargetStrafe(e, speed, yaw, -direction, mc.thePlayer.getDistanceToEntity(Slack.getInstance().getModuleManager().getInstance(KillAura.class).target) > ts.distance.getValue() ? 1 : 0);
    }

    public int getIndex(List<Point> points) {
        List<Point> points1 = new ArrayList<>(points);
        points1.sort(Comparator.comparingDouble(p -> mc.thePlayer.getDistance(p.getX(), p.getY(), p.getZ())));
        return points.indexOf(points1.get(0));
    }

    public void changeIndex() {
        if(direction == 1) {
            index -= 5;
            if(index < 0) {
                index = 360 + index;
            }
        } else if(direction == -1) {
            index += 5;
            if(index > 360) {
                index = index - 360;
            }
        }
    }

    public List<Point> getPoints(EntityLivingBase entity) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i <= 360; i++) {
            Point point = new Point((entity.posX + (entity.posX - entity.lastTickPosX)) + -Math.sin(Math.toRadians(i)) * distance.getValue(), entity.posY, (entity.posZ + (entity.posZ - entity.lastTickPosZ)) + Math.cos(Math.toRadians(i)) * distance.getValue(), true);

            Block block = new BlockPos(point.getX(), point.getY(), point.getZ()).getBlock();
            if((fallCheck.getValue() && !couldFall(point)) || (wallCheck.getValue() && block.getMaterial().isSolid())) {
                point.setValid(false);
            }

            points.add(point);
        }

        return points;
    }

    public boolean couldFall(Point point) {
        if(Slack.getInstance().getModuleManager().getInstance(Flight.class).isToggle()) {
            return true;
        }
        for (int i = (int)point.getY(); i > point.getY() - 3; i--) {
            if(!(new BlockPos(point.getX(), i, point.getZ()).getBlock() instanceof BlockAir)) {
                return true;
            }
        }

        return false;
    }

    public static class Point {
        private double x, y, z;
        private boolean valid;

        public Point(double x, double y, double z, boolean valid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.valid = valid;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }

    public static void setSpeedTargetStrafe(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static float getRotationFromPosition(final double x, final double z) {
        final double xDiff = x - mc.thePlayer.posX;
        final double zDiff = z - mc.thePlayer.posZ;
        return (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0f;
    }

}
