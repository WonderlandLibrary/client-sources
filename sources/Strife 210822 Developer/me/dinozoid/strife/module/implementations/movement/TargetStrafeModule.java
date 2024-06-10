package me.dinozoid.strife.module.implementations.movement;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.player.MovePlayerEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.event.implementations.render.Render2DEvent;
import me.dinozoid.strife.event.implementations.render.Render3DEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.render.RenderUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "TargetStrafe", renderName = "TargetStrafe", description = "Automatically strafe around entities.", category = Category.COMBAT)
public class TargetStrafeModule extends Module {

    private final EnumProperty<StrafeDirection> strafeDirectionProperty = new EnumProperty("Direction", StrafeDirection.CYCLE);
    private final DoubleProperty cycleDelayProperty = new DoubleProperty("Cycle Delay", 10, 1, 100, 5, () -> strafeDirectionProperty.value() == StrafeDirection.CYCLE);
    private final DoubleProperty radiusProperty = new DoubleProperty("Radius", 1, 1, 70, 0.1);
    private final DoubleProperty verticesProperty = new DoubleProperty("Vertices", 16, 2, 48, 1, Property.Representation.INT);
    private final Property<Boolean> autoProperty = new Property("Auto", false);
    private final Property<Boolean> thirdPersonProperty = new Property("Third Person", true);

    private final List<StrafePoint> points = new ArrayList<>();
    private StrafePoint currentPoint;
    private final TimerUtil timer = new TimerUtil();
    private int index;
    private boolean thirdPerson;

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if (event.state() == EventState.PRE) {
            EntityLivingBase target = KillAuraModule.instance().target();
            if (target != null) {
                loadPoints(target, verticesProperty.value().intValue());
                currentPoint = getBestPoint(target);
            } else {
                currentPoint = null;
                points.clear();
            }
        }
    });

    @EventHandler
    private final Listener<Render3DEvent> render3DListener = new Listener<>(event -> {
        if (canStrafe())
            drawCircle(KillAuraModule.instance().target(), event.partialTicks());
    });

    public void strafe(MovePlayerEvent event, double moveSpeed) {
        if (currentPoint != null && canStrafe()) {
            if (thirdPersonProperty.value() && !thirdPerson || mc.gameSettings.thirdPersonView == 0) {
                mc.gameSettings.thirdPersonView = 1;
                thirdPerson = true;
            }
            MovementUtil.setSpeed(event, moveSpeed, 1.0f, -1.0f, getYawDifferenceToPoint(currentPoint, mc.thePlayer));
        } else {
            if(thirdPersonProperty.value() && thirdPerson) {
                PlayerUtil.sendMessageWithPrefix("why");
                mc.gameSettings.thirdPersonView = 0;
                thirdPerson = false;
            }
            MovementUtil.setSpeed(event, moveSpeed, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.rotationYaw);
        }
    }

    /**
     * Calculates position for each point and adds point to a list
     *
     * @param entity   for position
     * @param vertices for number of vertices
     **/
    private void loadPoints(Entity entity, int vertices) {
        points.clear();
        double x = entity.posX;
        double y = entity.posY;
        double z = entity.posZ;
        double prevX = entity.prevPosX;
        double prevY = entity.prevPosY;
        double prevZ = entity.prevPosZ;
        for (int i = 0; i < vertices; i++) {
            // setting vertices
            // we use Math.PI * 2 to get 360 degrees
            double sin = radiusProperty.value() * Math.sin((2 * Math.PI) * i / vertices);
            double cos = radiusProperty.value() * Math.cos((2 * Math.PI) * i / vertices);
            // creating a new point from the calculated pos
            StrafePoint strafePoint = new StrafePoint(x + sin, y, z + cos, prevX + sin, prevY, prevZ + cos);
            // checking if the point should be disabled
            strafePoint.disabled = !isValid(strafePoint);
            points.add(strafePoint);
        }
    }


    /**
     * Finds the best suitable point by sorting it by direction.
     *
     * @param target targeted player
     **/
    private StrafePoint getBestPoint(Entity target) {
        List<StrafePoint> filteredPoints = enabledPoints();
        if (strafeDirectionProperty.value() == StrafeDirection.CYCLE) {
            if (timer.hasElapsed(cycleDelayProperty.value().longValue())) {
                index++;
                timer.reset();
            }
        } else
            filteredPoints.sort(strafeDirectionProperty.value() == StrafeDirection.FORWARD ? Comparator.comparingDouble(point -> Math.abs(getYawDifferenceToPoint(point, target))) : Comparator.comparingDouble(point -> Math.abs(getYawDifferenceToPoint((StrafePoint) point, target))).reversed());
        if (strafeDirectionProperty.value() == StrafeDirection.CYCLE) {
            if (index >= filteredPoints.size())
                index = 0;
        }
        return filteredPoints.isEmpty() ? null : filteredPoints.get(index);
    }

    /**
     * Gets the yaw difference from the entity to the point
     *
     * @param point  for position
     * @param target for yaw
     **/
    public float getYawDifferenceToPoint(StrafePoint point, Entity target) {
        double diffX = point.x - target.posX;
        double diffZ = point.z - target.posZ;
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        // yaw + difference
        return target.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - target.rotationYaw);
    }

    /**
     * Checks whether or not the point is valid.
     *
     * @param strafePoint for point
     * @return if point is valid
     **/
    private boolean isValid(StrafePoint strafePoint) {
        // vector of point used to raytrace
        Vec3 pointVector = new Vec3(strafePoint.x, strafePoint.y, strafePoint.z);
        // block state of block where point is
        IBlockState blockState = mc.theWorld.getBlockState(new BlockPos(pointVector));
        // raytraces the player position to the point to see if it can be seen
        boolean canBeSeen = mc.theWorld.rayTraceBlocks(mc.thePlayer.getPositionVector(), pointVector, false, false, false) == null;
        boolean didCollide = blockState.getBlock().canCollideCheck(blockState, false);
        // if block is under point, point can be seen, and didn't collide
        return PlayerUtil.isBlockUnder(strafePoint.x, strafePoint.y, strafePoint.z) && canBeSeen && !didCollide;
    }

    /**
     * Gets the enabled points.
     *
     * @return all enabled points
     **/
    public List<StrafePoint> enabledPoints() {
        return points.stream().filter(point -> !point.disabled).collect(Collectors.toList());
    }

    /**
     * Draws the circle with the points
     *
     * @param entity       for position
     * @param partialTicks for smooth interpolation
     **/
    private void drawCircle(Entity entity, float partialTicks) {
        double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        double y = RenderUtil.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_POINT_SMOOTH);
        glDepthMask(false);
        glPointSize(5);
        glBegin(GL_POINTS);
        int i = 0;
        for (StrafePoint point : points) {
            double sin = radiusProperty.value() * Math.sin((2 * Math.PI) * i / verticesProperty.value());
            double cos = radiusProperty.value() * Math.cos((2 * Math.PI) * i / verticesProperty.value());
            RenderUtil.color(point.disabled ? new Color(214, 43, 43) : point == currentPoint ? new Color(43, 214, 60) : new Color(87, 87, 87));
            glVertex3d(x + sin, y, z + cos);
            i++;
        }
        glEnd();
        glDepthMask(true);
        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    private final class StrafePoint {

        private double x, y, z, prevX, prevY, prevZ;
        private boolean disabled;

        public StrafePoint(double x, double y, double z, double prevX, double prevY, double prevZ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prevX = prevX;
            this.prevY = prevY;
            this.prevZ = prevZ;
        }
    }

    private enum StrafeDirection {
        FORWARD, BACKWARD, CYCLE
    }

    /**
     * Checks if you can strafe.
     * @return if you can strafe
     **/
    public boolean canStrafe() {
        return (Keyboard.isKeyDown(Keyboard.KEY_SPACE) || autoProperty.value()) && !points.isEmpty() && currentPoint != null && KillAuraModule.instance().target() != null && (SpeedModule.instance().toggled() || FlightModule.instance().toggled());
    }

    public static TargetStrafeModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(TargetStrafeModule.class);
    }

    public DoubleProperty radiusProperty() {
        return radiusProperty;
    }
}
