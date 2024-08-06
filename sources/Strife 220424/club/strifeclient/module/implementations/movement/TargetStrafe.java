package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.event.implementations.player.StrafeEvent;
import club.strifeclient.event.implementations.rendering.Render3DEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.combat.Criticals;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.player.RotationUtil;
import club.strifeclient.util.rendering.ColorUtil;
import club.strifeclient.util.rendering.InterpolateUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "TargetStrafe", description = "Circle around players automatically.", category = Category.MOVEMENT)
public final class TargetStrafe extends Module {

    public final DoubleSetting radiusSetting = new DoubleSetting("Radius", 1, 1, 5, 0.1);
    public final DoubleSetting verticesSetting = new DoubleSetting("Vertices", 16, 1, 32, 1);

    private Point currentPoint;
    private Criticals criticals;
    private EntityLivingBase target;
    private final List<Point> points = new ArrayList<>();

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = e -> {
        if (criticals.getTarget() != null) {
            double x = InterpolateUtil.interpolateRenderX(criticals.getTarget());
            double y = InterpolateUtil.interpolateRenderY(criticals.getTarget());
            double z = InterpolateUtil.interpolateRenderZ(criticals.getTarget());
            glPushMatrix();
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_POINT_SMOOTH);
            glDepthMask(false);
            glPointSize(10);
            glBegin(GL_POINTS);
            int i = 0;
            for(Point point : points) {
                double sin = radiusSetting.getDouble() * Math.sin((2 * Math.PI) * i / verticesSetting.getInt());
                double cos = radiusSetting.getDouble() * Math.cos((2 * Math.PI) * i / verticesSetting.getInt());
                ColorUtil.doColor(point == currentPoint ? new Color(43, 214, 60).getRGB() : new Color(87, 87, 87).getRGB());
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
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (e.isPre()) {
            if (criticals == null)
                criticals = Client.INSTANCE.getModuleManager().getModule(Criticals.class);
            target = criticals.getTarget();
            if (target != null) {
                points.clear();
                collectPoints(verticesSetting.getInt());
                currentPoint = getBestPoint(points);
            }
        }
    };

    public boolean strafeMove(MoveEvent e, double speed) {
        if (target != null && currentPoint != null) {
            MovementUtil.setSpeed(e, speed, mc.thePlayer.movementInput.moveForward,
                    mc.thePlayer.movementInput.moveStrafe, getRotationsForPoint(currentPoint)[0]);
            return true;
        }
        return false;
    }

    public boolean strafe(StrafeEvent e, double friction, float strafeComponent) {
        if (target != null && currentPoint != null && isEnabled()) {
            e.yaw = getRotationsForPoint(currentPoint)[0];
            e.setMotionPartialStrafe(friction, strafeComponent);
            return true;
        }
        return false;
    }

    private void collectPoints(final int vertices) {
        double x = target.posX;
        double y = target.posY;
        double z = target.posZ;
        for(int i = 0; i < vertices; i++) {
            double sin = radiusSetting.getValue() * Math.sin((2 * Math.PI) * i / vertices);
            double cos = radiusSetting.getValue() * Math.cos((2 * Math.PI) * i / vertices);
            final Point point = new Point(x + sin, y, z + cos);
            point.valid = isValidPoint(point);
            points.add(point);
        }
    }

    private Point getBestPoint(final List<Point> points) {
        if (target != null && !points.isEmpty()) {
            final List<Point> validPoints = getValidPoints(points);
            for (final Point point : validPoints) {
                final float yawDifference = RotationUtil.getYawDifference(getRotationsForPoint(point)[0], target.rotationYaw);
                if (Math.abs(yawDifference) >= 90)
                    return point;
            }
        }
        return null;
    }

    private List<Point> getValidPoints(final List<Point> points) {
        return points.stream().filter(point -> point.valid).collect(Collectors.toList());
    }

    private boolean isValidPoint(final Point point) {
        return true;
    }

    private float[] getRotationsForPoint(final Point point) {
        return RotationUtil.getRotationFromVector(point.pos);
    }

    private static final class Point {
        public Vec3 pos;
        public boolean valid;
        public Point(final double x, final double y, final double z) {
            pos = new Vec3(x, y, z);
        }
    }

}
