package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostMotionEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.vector.Vector2d;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ModuleInfo(
        aliases = {"Jump Circles"},
        description = "Draws circles around the player when they jump",
        category = Category.RENDER
)
public final class JumpCirclesModule extends Module {

    private final Queue<Circle> circles = new ConcurrentLinkedQueue<>();

    private final Animation alphaAnimation = new Animation(Easing.EASE_IN_OUT_CUBIC, 300);

    private boolean playerWasInAir = false;

    private static final float ADJUSTMENT = 0.004F;

    private static final int LINE_WIDTH = 2;

    private static final float RADIUS = 2f;

    private static final float DUAL_RADIUS = RADIUS * 2;

    @Override
    public void onDisable() {
        if (this.circles.isEmpty()) {
            return;
        }

        this.circles.clear();
    }

    @EventLink
    private final Listener<PostMotionEvent> postMotionEventListener = event -> {
        if (this.mc.thePlayer.onGround && this.playerWasInAir) {
            final double lerpedX = MathUtil.lerp(this.mc.thePlayer.prevPosX, this.mc.thePlayer.posX, this.mc.timer.renderPartialTicks);
            final double lerpedY = MathUtil.lerp(this.mc.thePlayer.prevPosY, this.mc.thePlayer.posY, this.mc.timer.renderPartialTicks);
            final double lerpedZ = MathUtil.lerp(this.mc.thePlayer.prevPosZ, this.mc.thePlayer.posZ, this.mc.timer.renderPartialTicks);

            circles.add(new Circle(new Vec3(lerpedX, lerpedY, lerpedZ), 0, 255));
            this.playerWasInAir = false;
        } else if (!this.mc.thePlayer.onGround) {
            this.playerWasInAir = true;
        }

        for (final Circle circle : this.circles) {
            if (circle.alpha > 0) {
                return;
            }

            this.circles.clear();
        }
    };

    @EventLink
    private final Listener<Render3DEvent> render3DEventListener = this::onRender3DEvent;

    private void onRender3DEvent(final Render3DEvent render3DEvent) {
        for (final Circle circle : this.circles) {
            final Vec3 pos = circle.getPosition();
            final double y = pos.yCoord - this.mc.getRenderManager().renderPosY;

            circle.adjustRadius(ADJUSTMENT);

            if (circle.getRadius() <= RADIUS) {
                this.setupGLForCircleRendering();

                this.alphaAnimation.run(circle.alpha -= (float) (circle.getRadius() / DUAL_RADIUS));

                if (circle.getAlpha() > 0) {
                    circle.setAlpha(circle.alpha -= (float) (circle.getRadius() / DUAL_RADIUS));
                }

                this.renderCircleOutline(circle, pos, y);

                this.restoreGLState();
            } else {
                this.circles.remove();
            }
        }
    }

    private void setupGLForCircleRendering() {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void renderCircleOutline(final Circle circle, final Vec3 pos, final double y) {
        GL11.glLineWidth(LINE_WIDTH);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int i = 0; i <= 360; i++) {

            final Color outerColor = this.getModule(Interface.class).getTheme().getAccentColor(new Vector2d(i, i));

            final double[] outerAnglePosition = this.getPosition(pos.xCoord, pos.zCoord, i, circle.radius);

            final double x = outerAnglePosition[0] - this.mc.getRenderManager().renderPosX;
            final double z = outerAnglePosition[1] - this.mc.getRenderManager().renderPosZ;

            GL11.glColor4f(outerColor.getRed() / 255F, outerColor.getGreen() / 255F, outerColor.getBlue() / 255F, circle.getAlpha() / 255f);
            GL11.glVertex3d(x, y, z);
        }

        GL11.glEnd();
        GL11.glPopMatrix();

    }

    private void restoreGLState() {
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public double[] getPosition(final double x, final double z, double angle, final double radius) {
        angle = MathHelper.wrapAngleTo180_double(angle);

        final double math = angle * Math.PI / 180;
        final double newX = x - Math.sin(math) * radius;
        final double newZ = z + Math.cos(math) * radius;

        return new double[] {newX, newZ};
    }

    private static final class Circle {
        private final Vec3 position;
        private double radius;
        private float alpha;

        private Circle(final Vec3 position, final double radius, final float alpha) {
            this.position = position;
            this.radius = radius;
            this.alpha = alpha;
        }

        public void adjustRadius(final double adjustment) {
            this.radius += adjustment;
        }

        public Vec3 getPosition() {
            return this.position;
        }

        public double getRadius() {
            return this.radius;
        }

        public float getAlpha() {
            return this.alpha;
        }

        public void setAlpha(final float alpha) {
            this.alpha = alpha;
        }
    }

}
