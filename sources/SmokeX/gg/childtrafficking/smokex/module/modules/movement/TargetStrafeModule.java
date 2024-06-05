// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import java.awt.Color;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import net.minecraft.client.renderer.entity.RenderManager;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import org.lwjgl.opengl.GL11;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.render.EventRender3D;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.entity.EntityLivingBase;
import java.util.List;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "TargetStrafe", renderName = "Target Strafe", category = ModuleCategory.MOVEMENT)
public final class TargetStrafeModule extends Module
{
    private static final float DOUBLE_PI = 6.2831855f;
    public double dir;
    public final NumberProperty<Double> radiusProperty;
    public final NumberProperty<Integer> pointsProperty;
    public final BooleanProperty renderProperty;
    private final List<Point3D> currentPoints;
    private Point3D currentPoint;
    private EntityLivingBase currentTarget;
    public final EventListener<EventUpdate> playerEventEventCallback;
    private final EventListener<EventRender3D> eventRender3DEventCallback;
    
    public TargetStrafeModule() {
        this.dir = 1.0;
        this.radiusProperty = new NumberProperty<Double>("Radius", 1.0, 0.1, 4.0, 0.1);
        this.pointsProperty = new NumberProperty<Integer>("Points", 10, 3, 30, 1);
        this.renderProperty = new BooleanProperty("Render Circle", true);
        this.currentPoints = new ArrayList<Point3D>();
        this.playerEventEventCallback = (event -> {
            this.currentTarget = ModuleManager.getInstance(KillauraModule.class).getTarget();
            if (event.isPre()) {
                if (this.currentTarget != null && ModuleManager.getInstance(KillauraModule.class).isToggled()) {
                    this.collectPoints(this.currentTarget);
                    this.currentPoint = this.findOptimalPoint(this.currentTarget, this.currentPoints);
                }
                else {
                    this.currentTarget = null;
                    this.currentPoint = null;
                }
            }
            return;
        });
        this.eventRender3DEventCallback = (event -> {
            if (this.shouldStrafe() && this.renderProperty.getValue() && this.currentTarget != null) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                RenderingUtils.startSmooth();
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(2.0f);
                GL11.glBegin(3);
                final EntityLivingBase entity = this.currentTarget;
                final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * this.mc.timer.elapsedPartialTicks - RenderManager.viewerPosX;
                final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * this.mc.timer.elapsedPartialTicks - RenderManager.viewerPosY;
                final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * this.mc.timer.elapsedPartialTicks - RenderManager.viewerPosZ;
                final int color1 = new Color(ModuleManager.getInstance(HUDModule.class).getColor()).getRGB();
                final double pix2 = 6.283185307179586;
                for (int i = 0; i <= 90; ++i) {
                    RenderingUtils.color(color1);
                    GL11.glVertex3d(x + this.radiusProperty.getValue() * Math.cos(i * pix2 / this.pointsProperty.getValue()), y, z + this.radiusProperty.getValue() * Math.sin(i * pix2 / this.pointsProperty.getValue()));
                }
                GL11.glEnd();
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                RenderingUtils.endSmooth();
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        });
    }
    
    @Override
    public void onDisable() {
        this.currentTarget = null;
        this.currentPoint = null;
        super.onDisable();
    }
    
    public boolean shouldStrafe() {
        return this.currentPoint != null;
    }
    
    public void setSpeed(final EventMove event, final double speed) {
        MovementUtils.rawSetSpeed(event, speed, 1.0f, 0.0f, this.getYawToPoint(this.currentPoint));
    }
    
    private float getYawToPoint(final Point3D point) {
        final EntityPlayerSP player = this.mc.thePlayer;
        final double xDist = point.x - player.posX;
        final double zDist = point.z - player.posZ;
        final float rotationYaw = player.rotationYaw;
        final float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }
    
    private Point3D findOptimalPoint(final EntityLivingBase target, final List<Point3D> points) {
        Point3D bestPoint = null;
        if (!points.isEmpty()) {
            final Point3D closest = getClosestPoint(points);
            int currOff = 0;
            final int pointsSize = points.size();
            currOff -= (int)this.dir;
            if (-currOff >= pointsSize) {
                return null;
            }
            final int nextIndex = points.indexOf(closest) - currOff;
            final Point3D nextPoint = points.get((nextIndex < 0) ? (pointsSize - 1) : ((nextIndex >= pointsSize) ? 0 : nextIndex));
            if (!nextPoint.valid) {
                this.dir = -this.dir;
            }
            bestPoint = nextPoint;
        }
        return bestPoint;
    }
    
    private static Point3D getClosestPoint(final List<Point3D> points) {
        double closest = Double.MAX_VALUE;
        Point3D bestPoint = null;
        for (final Point3D point : points) {
            if (point.valid) {
                final double dist = getDistXZToPoint(point);
                if (dist >= closest) {
                    continue;
                }
                closest = dist;
                bestPoint = point;
            }
        }
        return bestPoint;
    }
    
    private static double getDistXZToPoint(final Point3D point) {
        final Entity localPlayer = Minecraft.getMinecraft().thePlayer;
        final double xDist = point.x - localPlayer.posX;
        final double zDist = point.z - localPlayer.posZ;
        return Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    private static float getYawChangeToPoint(final EntityLivingBase target, final Point3D point) {
        final double xDist = point.x - target.posX;
        final double zDist = point.z - target.posZ;
        final float rotationYaw = target.rotationYaw;
        final float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }
    
    private void collectPoints(final EntityLivingBase entity) {
        final int size = this.pointsProperty.getValue();
        final double radius = this.radiusProperty.getValue();
        this.currentPoints.clear();
        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;
        final double prevX = entity.prevPosX;
        final double prevY = entity.prevPosY;
        final double prevZ = entity.prevPosZ;
        for (int i = 0; i < size; ++i) {
            final double cos = radius * StrictMath.cos(i * 6.2831855f / size);
            final double sin = radius * StrictMath.sin(i * 6.2831855f / size);
            final double pointX = x + cos;
            final double pointZ = z + sin;
            final Point3D point = new Point3D(pointX, y, pointZ, prevX + cos, prevY, prevZ + sin, this.validatePoint(pointX, pointZ));
            this.currentPoints.add(point);
        }
    }
    
    private boolean validatePoint(final double x, final double z) {
        final Vec3 pointVec = new Vec3(x, this.mc.thePlayer.posY, z);
        final IBlockState blockState = this.mc.theWorld.getBlockState(new BlockPos(pointVec));
        final boolean canBeSeen = this.mc.theWorld.rayTraceBlocks(this.mc.thePlayer.getPositionVector(), pointVec, false, false, false) == null;
        return !this.isOverVoid(x, z) && !blockState.getBlock().canCollideCheck(blockState, false) && canBeSeen;
    }
    
    private boolean isOverVoid(final double x, final double z) {
        for (double posY = this.mc.thePlayer.posY; posY > 0.0; --posY) {
            if (!(this.mc.theWorld.getBlockState(new BlockPos(x, posY, z)).getBlock() instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }
    
    private static final class Point3D
    {
        private final double x;
        private final double y;
        private final double z;
        private final double prevX;
        private final double prevY;
        private final double prevZ;
        private final boolean valid;
        
        public Point3D(final double x, final double y, final double z, final double prevX, final double prevY, final double prevZ, final boolean valid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prevX = prevX;
            this.prevY = prevY;
            this.prevZ = prevZ;
            this.valid = valid;
        }
    }
}
