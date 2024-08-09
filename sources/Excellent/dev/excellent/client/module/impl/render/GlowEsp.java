package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.render.Render3DPosedEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.CustomFramebuffer;
import dev.excellent.impl.util.render.OutlineShader;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.shader.ShaderLink;
import dev.excellent.impl.util.shader.impl.ESPShader;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.client.MainWindow;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2d;

@ModuleInfo(name = "GlowEsp", description = "Подсвечивает ближайших сущностей.", category = Category.RENDER)
public class GlowEsp extends Module {
    public static Singleton<GlowEsp> singleton = Singleton.create(() -> Module.link(GlowEsp.class));

    private final CustomFramebuffer buffer = new CustomFramebuffer(true);
    private final ESPShader bloom = new ESPShader();
    private final NumberValue glow = new NumberValue("Сила", this, 6, 3, 10, 1);

    private final NumberValue outline = new NumberValue("Обводка", this, 1, 1, 3, 1);

    public Vector3d[] getCorners(AxisAlignedBB AABB) {
        return new Vector3d[]{
                new Vector3d(AABB.minX, AABB.minY, AABB.minZ),
                new Vector3d(AABB.minX, AABB.minY, AABB.maxZ),
                new Vector3d(AABB.minX, AABB.maxY, AABB.minZ),
                new Vector3d(AABB.minX, AABB.maxY, AABB.maxZ),
                new Vector3d(AABB.maxX, AABB.minY, AABB.minZ),
                new Vector3d(AABB.maxX, AABB.minY, AABB.maxZ),
                new Vector3d(AABB.maxX, AABB.maxY, AABB.minZ),
                new Vector3d(AABB.maxX, AABB.maxY, AABB.maxZ)
        };
    }

    public ShaderLink gradient = ShaderLink.create("gradient");
    private final float MAX_DISTANCE = 128;

    public void patch(PlayerEntity entity, Runnable runnable) {

        Vector3d interpolated = entity.getPositionVec().subtract(entity.getPositionVec(mc.getRenderPartialTicks()));

        AxisAlignedBB AABB = entity.getBoundingBox().offset(interpolated.inverse().add(interpolated.scale(mc.getRenderPartialTicks())));
        Vector2d center = RenderUtil.project2D(AABB.getCenter());

        if (center == null) {
            return;
        }

        double minX = center.x, minY = center.y, maxX = center.x, maxY = center.y;

        for (Vector3d corner : getCorners(AABB)) {
            Vector2d vec = RenderUtil.project2D(corner);

            if (vec == null) {
                continue;
            }

            minX = Math.min(minX, vec.x);
            minY = Math.min(minY, vec.y);
            maxX = Math.max(maxX, vec.x);
            maxY = Math.max(maxY, vec.y);
        }

        double posX = minX, posY = minY, width = maxX - minX, height = maxY - minY;
        MainWindow mw = mc.getMainWindow();
        gradient.init();
        gradient.setUniformf("location", (float) (posX * mw.getScaleFactor()), (float) ((mw.getHeight() - (height * mw.getScaleFactor())) - (posY * mw.getScaleFactor())));
        gradient.setUniformf("rectSize", (float) (width * mw.getScaleFactor()), (float) (height * mw.getScaleFactor()));
        gradient.setUniformi("tex", 0);

        gradient.setUniformf("color1", ColorUtil.getRGBAf(getTheme().getClientColor(0)));
        gradient.setUniformf("color2", ColorUtil.getRGBAf(getTheme().getClientColor(90)));
        gradient.setUniformf("color3", ColorUtil.getRGBAf(getTheme().getClientColor(45)));
        gradient.setUniformf("color4", ColorUtil.getRGBAf(getTheme().getClientColor(135)));


        runnable.run();

        gradient.unload();
    }

    public Listener<Render3DPosedEvent> onWorld = e -> {
        MatrixStack stack = e.getMatrix();
        buffer.setup(false);

        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (entity instanceof ClientPlayerEntity && mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON || mc.player.getDistance(entity) > MAX_DISTANCE)
                continue;
            patch(entity, () -> {
                EntityRendererManager rendererManager = mc.getRenderManager();
                stack.push();
                stack.translate(-rendererManager.renderPosX(), -rendererManager.renderPosY(), -rendererManager.renderPosZ());
                GlStateManager.depthMask(true);
                rendererManager.setRenderShadow(false);
                rendererManager.setRenderName(false);
                IRenderTypeBuffer.Impl irendertypebuffer$impl = mc.getRenderTypeBuffers().getBufferSource();

                Vector3d pos = entity.getPositionVec(e.getPartialTicks());
                EntityRenderer<?> renderer = rendererManager.getRenderer(entity);
                boolean nameVisible = renderer.isRenderName();
                if (nameVisible) renderer.setRenderName(false);
                rendererManager.renderEntityStaticGlow(entity, pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, e.getPartialTicks(), stack, irendertypebuffer$impl, rendererManager.getPackedLight(entity, e.getPartialTicks()));
                if (nameVisible) renderer.setRenderName(true);

                irendertypebuffer$impl.finish();
                rendererManager.setRenderName(true);
                rendererManager.setRenderShadow(true);
                GlStateManager.depthMask(false);
                GlStateManager.enableDepthTest();
                stack.pop();
            });
        }

        buffer.stop();
    };

    public Listener<Render2DEvent> onDisplay = e -> {
        OutlineShader.addTask2D(buffer::draw);
        OutlineShader.draw(outline.getValue().intValue());
        bloom.addTask2D(buffer::draw);
        bloom.draw(Math.max(glow.getValue().intValue() / 2, 3), glow.getValue().floatValue(), ESPShader.RenderType.DISPLAY, 2);
        buffer.framebufferClear(false);
        mc.getFramebuffer().bindFramebuffer(true);
    };


}
