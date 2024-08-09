package wtf.resolute.utiled.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;
import wtf.resolute.utiled.client.IMinecraft;

import java.util.ArrayList;
import java.util.List;

public class RectUtils implements IMinecraft {
    public static final List<DisplayUtils.Vec2fColored> VERTEXES_COLORED = new ArrayList<>();
    public static void drawRect2(MatrixStack matrix, float x, float y, float x2, float y2, int color1, int color2, int color3, int color4, boolean bloom, boolean texture) {
        color1 = color2 = color3 = color4 = -1;
        VERTEXES_COLORED.clear();
        VERTEXES_COLORED.add(new DisplayUtils.Vec2fColored(x, y, color1));
        VERTEXES_COLORED.add(new DisplayUtils.Vec2fColored(x2, y, color2));
        VERTEXES_COLORED.add(new DisplayUtils.Vec2fColored(x2, y2, color3));
        VERTEXES_COLORED.add(new DisplayUtils.Vec2fColored(x, y2, color4));
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POLYGON, texture, bloom);
    }
    public static void drawVertexesList(MatrixStack matrix, List<DisplayUtils.Vec2fColored> vec2c, int begin, boolean texture, boolean bloom) {
        setupRenderRect(texture, bloom);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(begin, texture ? DefaultVertexFormats.POSITION_TEX_COLOR : DefaultVertexFormats.POSITION_COLOR);
        int counter = 0;
        for (final DisplayUtils.Vec2fColored vec : vec2c) {
            float[] rgba = ColorUtils.getRGBAf(vec.getColor());
            bufferbuilder.pos(matrix.getLast().getMatrix(), vec.getX(), vec.getY(), 0);
            if (texture) bufferbuilder.tex(counter == 0 || counter == 3 ? 0 : 1, counter == 0 || counter == 1 ? 0 : 1);
            bufferbuilder.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            bufferbuilder.endVertex();
            counter++;
        }
        Tessellator.getInstance().draw();
        endRenderRect(bloom);
    }
    public static void endRenderRect(boolean bloom) {
        RenderSystem.enableAlphaTest();
        if (bloom)
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        RenderSystem.depthMask(true);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableCull();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
//        RenderSystem.enableDepthTest();
        RenderSystem.clearCurrentColor();
    }
    public static void setupRenderRect(boolean texture, boolean bloom) {
        if (texture) RenderSystem.enableTexture();
        else RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.shadeModel(7425);
//        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.disableAlphaTest();
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
    }
    public Vector2d project2D(net.minecraft.util.math.vector.Vector3d vec) {
        return project2D(vec.x, vec.y, vec.z);
    }

    public Vector2d project2D(double x, double y, double z) {
        if (mc.getRenderManager().info == null) return new Vector2d();
        net.minecraft.util.math.vector.Vector3d cameraPosition = mc.getRenderManager().info.getProjectedView();
        Quaternion cameraRotation = mc.getRenderManager().getCameraOrientation().copy();
        cameraRotation.conjugate();

        Vector3f relativePosition = new Vector3f((float) (cameraPosition.x - x), (float) (cameraPosition.y - y), (float) (cameraPosition.z - z));
        relativePosition.transform(cameraRotation);

        if (mc.gameSettings.viewBobbing) {
            Entity renderViewEntity = mc.getRenderViewEntity();
            if (renderViewEntity instanceof PlayerEntity playerEntity) {
                float walkedDistance = playerEntity.distanceWalkedModified;

                float deltaDistance = walkedDistance - playerEntity.prevDistanceWalkedModified;
                float interpolatedDistance = -(walkedDistance + deltaDistance * mc.getRenderPartialTicks());
                float cameraYaw = MathHelper.lerp(mc.getRenderPartialTicks(), playerEntity.prevCameraYaw, playerEntity.cameraYaw);

                Quaternion bobQuaternionX = new Quaternion(Vector3f.XP, Math.abs(MathHelper.cos(interpolatedDistance * (float) Math.PI - 0.2F) * cameraYaw) * 5.0F, true);
                bobQuaternionX.conjugate();
                relativePosition.transform(bobQuaternionX);

                Quaternion bobQuaternionZ = new Quaternion(Vector3f.ZP, MathHelper.sin(interpolatedDistance * (float) Math.PI) * cameraYaw * 3.0F, true);
                bobQuaternionZ.conjugate();
                relativePosition.transform(bobQuaternionZ);

                Vector3f bobTranslation = new Vector3f((MathHelper.sin(interpolatedDistance * (float) Math.PI) * cameraYaw * 0.5F), (-Math.abs(MathHelper.cos(interpolatedDistance * (float) Math.PI) * cameraYaw)), 0.0f);
                bobTranslation.setY(-bobTranslation.getY());
                relativePosition.add(bobTranslation);
            }
        }

        double fieldOfView = (float) mc.gameRenderer.getFOVModifier(mc.getRenderManager().info, mc.getRenderPartialTicks(), true);

        float halfHeight = (float) mc.getMainWindow().getScaledHeight() / 2.0F;
        float scaleFactor = halfHeight / (relativePosition.getZ() * (float) Math.tan(Math.toRadians(fieldOfView / 2.0F)));

        if (relativePosition.getZ() < 0.0F) {
            return new Vector2d(-relativePosition.getX() * scaleFactor + (float) (mc.getMainWindow().getScaledWidth() / 2), (float) (mc.getMainWindow().getScaledHeight() / 2) - relativePosition.getY() * scaleFactor);
        }
        return null;
    }
}
