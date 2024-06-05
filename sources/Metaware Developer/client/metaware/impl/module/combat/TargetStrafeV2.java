package client.metaware.impl.module.combat;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.util.other.MathUtils;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import client.metaware.impl.utils.util.player.RotationUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "TargetoStrafe", renderName = "Tagret Straef", category = Category.VISUALS)
public final class TargetStrafeV2 extends Module {

    public static final float DOUBLE_PI = (float) StrictMath.PI * 2;
    public final Property<Boolean> holdSpaceProperty = new Property<>("Hold Space", true);
    public final EnumProperty<TargetMode> targetMode = new EnumProperty<>("Target Mode", TargetMode.Auto);
    public final DoubleProperty targetRange = new DoubleProperty("Target Range", 8.0, 1.0, 32.0, 0.1, Property.Representation.DISTANCE, () -> targetMode.getValue() != TargetMode.Auto);
    public final EnumProperty<Mode> modeProperty = new EnumProperty<>("Mode", Mode.Follow);
    public final DoubleProperty pointsProperty = new DoubleProperty("Points", 12, 1, 90, 1);
    public final DoubleProperty radiusProperty = new DoubleProperty("Radius", 2.0, 0.1, 4.0, 0.1, Property.Representation.DISTANCE);
    public final Property<Boolean> adaptiveSpeedProperty = new Property<>("Adapt Speed", true);
    public final Property<Boolean> adaptiveRadiusProperty = new Property<>("Adapt (Test)", false);

    public final EnumProperty<RenderMode> renderProperty = new EnumProperty<>("Render Mode", RenderMode.Polygon);
    public final Property<Boolean> polyGradientProperty = new Property<>("Poly Gradient", true, () -> this.renderProperty.getValue() == RenderMode.Polygon);
    public final DoubleProperty widthProperty = new DoubleProperty("Width", 1.0F, 0.5F, 5.0F, 0.5F, () -> this.renderProperty.getValue() == RenderMode.Polygon);


    public final List<Point> currentPoints = new ArrayList<>();
    public EntityLivingBase currentTarget;
    public int direction = 1;
    public Point currentPoint;

    @EventHandler
    public final Listener<Render3DEvent> eventListener = event -> {
        if (this.shouldRender() && this.currentTarget != null) {

            final float partialTicks = event.getPartialTicks();
            final int dormantColor = 0xFFFFFFFF;
            final int invalidColor = 0xFFFF0000;

            glDisable(GL_TEXTURE_2D);
            RenderUtil.enableBlending();

            final boolean depthEnabled = glIsEnabled(GL_DEPTH_TEST);
            if (depthEnabled)
                RenderUtil.disableDepth();

            final double renderX = RenderManager.renderPosX;
            final double renderY = RenderManager.renderPosY;
            final double renderZ = RenderManager.renderPosZ;

            glTranslated(-renderX, -renderY, -renderZ);

            Point lastPoint = null;

            switch (this.renderProperty.getValue()) {
                case Polygon:
                    glEnable(GL_LINE_SMOOTH);
                    glLineWidth(this.widthProperty.getValue().floatValue());
                    final boolean polyGradient = this.polyGradientProperty.getValue();

                    if (polyGradient) glShadeModel(GL_SMOOTH);
                    else
                        RenderUtil.color(this.shouldStrafe() ? 0x8000FF00 : dormantColor);

                    glBegin(GL_LINE_LOOP);

                    for (final Point point : this.currentPoints) {
                        if (polyGradient) {
                            if (lastPoint == null ||
                                    lastPoint == currentPoint ||
                                    point == currentPoint ||
                                    lastPoint.valid != point.valid) {
                                int color;

                                if (currentPoint == point) color = Color.green.getRGB();
                                else if (point.valid) color = dormantColor;
                                else color = invalidColor;
                                RenderUtil.color(color);
                            }
                            lastPoint = point;
                        }

                        final double x = RenderUtil.interpolate(point.prevX, point.x, partialTicks);
                        final double y = RenderUtil.interpolate(point.prevY, point.y, partialTicks);
                        final double z = RenderUtil.interpolate(point.prevZ, point.z, partialTicks);

                        glVertex3d(x, y, z);
                    }

                    glEnd();

                    if (polyGradient)
                        glShadeModel(GL_FLAT);

                    glDisable(GL_LINE_SMOOTH);
                    break;
            }

            glTranslated(renderX, renderY, renderZ);

            if (depthEnabled)
                RenderUtil.enableDepth();

            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
        }
    };

    @EventHandler
    public final Listener<UpdatePlayerEvent> eventListener1 = event -> {
        if (event.isPre()) {
            switch (targetMode.getValue()) {
                case Auto:
                    this.currentTarget = Metaware.INSTANCE.getModuleManager().getModuleByClass(KillAura.class).target;
                    break;
                case Closest:
                    this.currentTarget = null;

                    final List<EntityLivingBase> entities = PlayerUtil.getLivingEntities(this::isValid);

                    float closest = this.targetRange.getValue().floatValue();

                    for (EntityLivingBase entity : entities) {
                        final float dist = this.mc.thePlayer.getDistanceToEntity(entity);

                        if (dist < closest) {
                            this.currentTarget = entity;
                            closest = dist;
                        }
                    }
            }



            if (this.currentTarget != null) {
                if (this.adaptiveRadiusProperty.getValue()) {
                    double bestRadius = 0;
                    int mostPoints = 0;
                    double radius = this.radiusProperty.getValue();
                    final int points = this.pointsProperty.getValue().intValue();

                    for (; radius < this.radiusProperty.getMax(); radius += 0.5) {
                        this.collectPoints(points, radius, this.currentTarget, false);
                        final int vPoints = this.countPoints();

                        if (vPoints == points) {
                            bestRadius = radius;
                            break;
                        }

                        if (vPoints > mostPoints) {
                            bestRadius = radius;
                            mostPoints = vPoints;
                        }
                    }

                    this.collectPoints(points, bestRadius, this.currentTarget, true);
                } else {
                    this.collectPoints(this.pointsProperty.getValue().intValue(), this.radiusProperty.getValue(), this.currentTarget, true);
                }
                this.currentPoint = this.findOptimalPoint(this.currentTarget, this.currentPoints);
            } else {
                this.currentPoint = null;
            }
        }
    };


    public Point findOptimalPoint(EntityLivingBase target, List<Point> points) {
        switch (modeProperty.getValue()) {
            case Behind:
                float biggestDif = -1.0F;
                Point bestPoint = null;

                for (Point point : points) {
                    if (point.valid) {
                        final float yawChange = Math.abs(RotationUtils.getYawBetween(target.rotationYaw, target.posX, target.posZ, point.x, point.z));
                        if (yawChange > biggestDif) {
                            biggestDif = yawChange;
                            bestPoint = point;
                        }
                    }
                }
                return bestPoint;
            case Follow:
                return getClosestPoint(this.mc.thePlayer.posX, this.mc.thePlayer.posZ, points);
            default:
                final Point closest = getClosestPoint(this.mc.thePlayer.posX, this.mc.thePlayer.posZ, points);

                if (closest == null)
                    return null;

                final int pointsSize = points.size();

                if (pointsSize == 1)
                    return closest;

                final int closestIndex = points.indexOf(closest);

                Point nextPoint;

                int passes = 0;

                do {
                    if (passes > pointsSize) // Note :: Shit fix
                        return null;
                    int nextIndex = closestIndex + direction;
                    if (nextIndex < 0) nextIndex = pointsSize - 1;
                    else if (nextIndex >= pointsSize) nextIndex = 0;

                    nextPoint = points.get(nextIndex);

                    if (!nextPoint.valid)
                        this.direction = -this.direction;
                    ++passes;
                } while (!nextPoint.valid);

                return nextPoint;
        }
    }

    public void collectPoints(final int size, final double radius, final EntityLivingBase entity, final boolean rayCast) {
        this.currentPoints.clear();

        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;

        final double prevX = entity.prevPosX;
        final double prevY = entity.prevPosY;
        final double prevZ = entity.prevPosZ;

        for (int i = 0; i < size; i++) {
            double cos = radius * StrictMath.cos(i * DOUBLE_PI / size);
            double sin = radius * StrictMath.sin(i * DOUBLE_PI / size);

            double pointX = x + cos;
            double pointZ = z + sin;

            final Point point = new Point(
                    pointX, y, pointZ,
                    prevX + cos, prevY, prevZ + sin,
                    validatePoint(pointX, y, pointZ, rayCast));

            this.currentPoints.add(point);
        }
    }

    public int countPoints() {
        int count = 0;
        for (final Point point : this.currentPoints) {
            if (point.valid) count++;
        }
        return count;
    }

    public static Point getClosestPoint(final double srcX, final double srcZ, List<Point> points) {
        double closest = Double.MAX_VALUE;
        Point bestPoint = null;

        for (Point point : points) {
            if (point.valid) {
                final double dist = MathUtils.distance(srcX, srcZ, point.x, point.z);
                if (dist < closest) {
                    closest = dist;
                    bestPoint = point;
                }
            }
        }

        return bestPoint;
    }

    public boolean validatePoint(final double x, final double y, final double z, final boolean rayCast) {
        final EntityPlayer player = this.mc.thePlayer;
        final WorldClient world = this.mc.theWorld;
        final Vec3 pointVec = new Vec3(x, y, z);

        if (rayCast) {
            final MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(player.getPositionVector(), pointVec,
                    false, true, false);

            if (rayTraceResult != null && rayTraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                return false;
        }
        // TODO :: Replace this with bb check

        final IBlockState blockState = world.getBlockState(new BlockPos(pointVec));


        final IBlockState blockStateAbove = world.getBlockState(new BlockPos(pointVec.addVector(0, 1, 0)));

        return !isOverVoid(x, Math.min(y, this.mc.thePlayer.posY), z);
    }

    public static boolean isOverVoid(final double x, final double y, final double z) {
        for (double posY = y; posY > 0.0; posY--) {
            final IBlockState state = mc.theWorld.getBlockState(new BlockPos(x, posY, z));
            if (state.getBlock().canCollideCheck(state, false)) {
                return y - posY > 2;
            }
        }

        return true;
    }

    public boolean isValid(final EntityLivingBase entity) {
        return !(entity instanceof EntityPlayerSP) &&
                !entity.isInvisible() &&
                entity.isEntityAlive() &&
                entity.getDistanceToEntity(this.mc.thePlayer) < this.targetRange.getValue();
    }

    public boolean isCloseToPoint(final Point point) {
        return MathUtils.distance(this.mc.thePlayer.posX, this.mc.thePlayer.posZ, point.x, point.z) < 0.2;
    }

    public boolean shouldAdaptSpeed() {
        if (!this.adaptiveSpeedProperty.getValue())
            return false;
        return this.isCloseToPoint(this.currentPoint);
    }

    public double getAdaptedSpeed() {
        final EntityLivingBase entity = this.currentTarget;
        if (entity == null) return 0.0;
        return MovementUtils.getMotion(entity);
    }

    public boolean shouldStrafe() {
        return isToggled() &&
                (!this.holdSpaceProperty.getValue() || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) &&
                this.currentTarget != null &&
                this.currentPoint != null;
    }

    public void setSpeed(final MovePlayerEvent event, final double speed) {
        final EntityPlayerSP player = this.mc.thePlayer;
        final Point point = this.currentPoint;
        MovementUtils.setSpeed(event, speed, 1, 0,
                RotationUtils.getYawBetween(player.rotationYaw, player.posX, player.posZ, point.x, point.z));
    }

    public enum Mode {
        Behind, Follow, Circle
    }

    public enum TargetMode {
        Auto, Closest
    }

    public enum RenderMode {
        Off, Polygon
    }

    public static final class Point {
        public final double x;
        public final double y;
        public final double z;
        public final double prevX;
        public final double prevY;
        public final double prevZ;

        public final boolean valid;

        public Point(double x, double y, double z, double prevX, double prevY, double prevZ, boolean valid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prevX = prevX;
            this.prevY = prevY;
            this.prevZ = prevZ;
            this.valid = valid;
        }
    }

    public boolean shouldRender() {
        return this.renderProperty.getValue() != RenderMode.Off;
    }

    public static void drawLinesAroundPlayer(Entity entity, double radius, float partialTicks, int points, float width, int color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glDisable(GL_DEPTH_TEST);
        glLineWidth(width);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glBegin(GL_LINE_STRIP);
        final double x = RenderUtil.interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        final double y = RenderUtil.interpolate(entity.prevPosY, entity.posY, partialTicks) - RenderManager.viewerPosY;
        final double z = RenderUtil.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;
        RenderUtil.glColor(color);
        for (int i = 0; i <= points; i++)
            glVertex3d(x + radius * Math.cos(i * Math.PI * 2 / points), y, z + radius * Math.sin(i * Math.PI * 2 / points));
        glEnd();
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }
}