package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Trails", description = "Отображает хвост появляющийся при вашем движении.", category = Category.RENDER)
public class Trails extends Module implements IRenderAccess {
    private final List<Point> points = new ArrayList<>();

    private void clear() {
        points.clear();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        clear();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        clear();
    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> clear();
    private final Listener<WorldLoadEvent> onWorldLoad = event -> clear();
    private final Listener<Render3DPosedEvent> onRender3D = event -> {
        ClientPlayerEntity player = mc.player;

        MatrixStack matrix = event.getMatrix();
        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        RenderSystem.pushMatrix();
        matrix.push();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        if (light)
            RenderSystem.disableLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        matrix.push();

        if (!mc.gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON)) {
            points.removeIf(point -> point.time.hasReached(300));
            Vector3d playerPos = interpolatePlayerPosition(player, event.getPartialTicks());
            points.add(new Point(playerPos));
            drawTrail(matrix);
        }

        matrix.pop();

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.clearCurrentColor();
        GL11.glShadeModel(GL11.GL_FLAT);
        if (light)
            RenderSystem.enableLighting();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableAlphaTest();
        matrix.pop();
        RenderSystem.popMatrix();
    };

    private Vector3d interpolatePlayerPosition(LivingEntity entity, float partialTicks) {
        return new Vector3d(
                Interpolator.lerp(entity.prevPosX, entity.getPosX(), partialTicks),
                Interpolator.lerp(entity.prevPosY, entity.getPosY(), partialTicks),
                Interpolator.lerp(entity.prevPosZ, entity.getPosZ(), partialTicks)
        );
    }

    private void drawTrail(MatrixStack matrix) {
        startRendering();
        BUFFER.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        Vector3d projectedView = mc.getRenderManager().info.getProjectedView();
        int index = 0;
        for (Point point : points) {
            int color = getTheme().getClientColor(index);
            float red = ColorUtil.redf(color);
            float green = ColorUtil.greenf(color);
            float blue = ColorUtil.bluef(color);
            float alpha = (float) index / (float) points.size() * 0.5F;
            Vector3d pos = point.pos.subtract(projectedView);
            BUFFER.pos(matrix.getLast().getMatrix(), (float) pos.x, (float) (pos.y + mc.player.getHeight()), (float) pos.z).color(red, green, blue, alpha).endVertex();
            BUFFER.pos(matrix.getLast().getMatrix(), (float) pos.x, (float) pos.y + 0.0005F, (float) pos.z).color(red, green, blue, alpha).endVertex();
            index++;
        }
        TESSELLATOR.draw();
        RenderSystem.lineWidth(2);
        drawLine(matrix, points, true);
        drawLine(matrix, points, false);
        stopRendering();
    }

    private void drawLine(MatrixStack matrix, List<Point> points, boolean withHeight) {

        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        Vector3d projectedView = mc.getRenderManager().info.getProjectedView();
        int index = 0;
        for (Point point : points) {
            int color = getTheme().getClientColor(index);
            float red = ColorUtil.redf(color);
            float green = ColorUtil.greenf(color);
            float blue = ColorUtil.bluef(color);
            float alpha = (float) index / (float) points.size();
            alpha = Math.min(alpha, 2F);
            Vector3d pos = point.pos.subtract(projectedView);
            if (withHeight)
                BUFFER.pos(matrix.getLast().getMatrix(), (float) pos.x, (float) (pos.y + mc.player.getHeight()), (float) pos.z).color(red, green, blue, alpha).endVertex();
            else
                BUFFER.pos(matrix.getLast().getMatrix(), (float) pos.x, (float) pos.y + 0.0005F, (float) pos.z).color(red, green, blue, alpha).endVertex();
            index++;
        }
        TESSELLATOR.draw();
    }

    private static class Point {
        public Vector3d pos;
        public TimerUtil time = TimerUtil.create();

        public Point(Vector3d pos) {
            this.pos = pos;
        }
    }

    private void startRendering() {
        RenderSystem.pushMatrix();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.color4f(0, 0, 0, 0.1f);

    }

    private void stopRendering() {
        RenderSystem.enableAlphaTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}
