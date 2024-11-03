package dev.stephen.nexus.module.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.render.EventRender2D;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.mixin.accesors.WorldRendererAccessor;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.combat.AntiBot;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.utils.render.DrawUtils;
import dev.stephen.nexus.utils.render.RenderUtils;
import dev.stephen.nexus.utils.render.ThemeUtils;
import dev.stephen.nexus.utils.render.W2SUtil;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4d;

import java.awt.*;

public class ESP extends Module {
    public static final BooleanSetting chams = new BooleanSetting("Chams", false);
    public static final BooleanSetting boxESP = new BooleanSetting("Box", false);
    public static final BooleanSetting _2dESP = new BooleanSetting("2D", false);
    public static final BooleanSetting _2dESPBox = new BooleanSetting("2D Box", false);
    public static final BooleanSetting _2dESPBoxFill = new BooleanSetting("2D Box Fill", false);
    public static final BooleanSetting _2dESPNameTags = new BooleanSetting("2D NameTags", false);
    public static final BooleanSetting _2dESPHealthBar = new BooleanSetting("2D HealthBar", false);

    public ESP() {
        super("ESP", "Always see players", 0, ModuleCategory.RENDER);
        this.addSettings(chams, boxESP, _2dESP, _2dESPBox, _2dESPBoxFill, _2dESPNameTags, _2dESPHealthBar);

        _2dESPBox.addDependency(_2dESP, true);

        _2dESPBoxFill.addDependency(_2dESP, true);
        _2dESPBoxFill.addDependency(_2dESPBox, true);

        _2dESPHealthBar.addDependency(_2dESP, true);
        _2dESPNameTags.addDependency(_2dESP, true);
    }

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (!_2dESP.getValue()) {
            return;
        }

        if (mc.getEntityRenderDispatcher().camera == null || mc.world == null || mc.player == null) {
            return;
        }

        final Matrix4f matrix = event.getContext().getMatrices().peek().getPositionMatrix();

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        for (final Entity entity : mc.world.getEntities()) {
            BufferBuilder bufferBuilder = null;

            if (!(entity instanceof LivingEntity)) {
                continue;
            }

            if (!(entity instanceof PlayerEntity) || entity == mc.player && mc.options.getPerspective().isFirstPerson()) {
                continue;
            }

            if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot((PlayerEntity) entity)) {
                continue;
            }

            final Box box = entity.getBoundingBox();

            if (!((WorldRendererAccessor) mc.worldRenderer).getFrustum().isVisible(box)) {
                continue;
            }

            final double x = entity.prevX + (entity.getX() - entity.prevX) * mc.getRenderTickCounter().getTickDelta(false);
            final double y = entity.prevY + (entity.getY() - entity.prevY) * mc.getRenderTickCounter().getTickDelta(false);
            final double z = entity.prevZ + (entity.getZ() - entity.prevZ) * mc.getRenderTickCounter().getTickDelta(false);

            final Box expandedBox = new Box(
                    box.minX - entity.getX() + x - 0.1,
                    box.minY - entity.getY() + y,
                    box.minZ - entity.getZ() + z - 0.1,
                    box.maxX - entity.getX() + x + 0.1,
                    box.maxY - entity.getY() + y + 0.12,
                    box.maxZ - entity.getZ() + z + 0.1
            );

            final Vec3d[] vectors = new Vec3d[]{
                    new Vec3d(expandedBox.minX, expandedBox.minY, expandedBox.minZ),
                    new Vec3d(expandedBox.minX, expandedBox.maxY, expandedBox.minZ),
                    new Vec3d(expandedBox.maxX, expandedBox.minY, expandedBox.minZ),
                    new Vec3d(expandedBox.maxX, expandedBox.maxY, expandedBox.minZ),
                    new Vec3d(expandedBox.minX, expandedBox.minY, expandedBox.maxZ),
                    new Vec3d(expandedBox.minX, expandedBox.maxY, expandedBox.maxZ),
                    new Vec3d(expandedBox.maxX, expandedBox.minY, expandedBox.maxZ),
                    new Vec3d(expandedBox.maxX, expandedBox.maxY, expandedBox.maxZ),
            };

            Vector4d position = null;

            for (final Vec3d vector : vectors) {
                final Vec3d vectorToScreen = W2SUtil.getCoords(vector);

                if (vectorToScreen.z > 0 && vectorToScreen.z < 1) {
                    if (position == null) {
                        position = new Vector4d(vectorToScreen.x, vectorToScreen.y, vectorToScreen.z, 0);
                    }

                    position.x = Math.min(vectorToScreen.x, position.x);
                    position.y = Math.min(vectorToScreen.y, position.y);
                    position.z = Math.max(vectorToScreen.x, position.z);
                    position.w = Math.max(vectorToScreen.y, position.w);
                }
            }

            if (position != null) {
                final float posX = (float) position.x, posY = (float) position.y;
                final float endPosX = (float) position.z, endPosY = (float) position.w;

                bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

                if (_2dESPBox.getValue()) {
                    // box shadow
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 1.0f, posY, posX + 0.5f, endPosY + 0.5f, Color.BLACK);
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 1.0f, posY - 0.5f, endPosX + 0.5f, posY + 1.0f, Color.BLACK);
                    DrawUtils.rectPoint4(bufferBuilder, matrix, endPosX - 1.0f, posY, endPosX + 0.5f, endPosY + 0.5f, Color.BLACK);
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 1.0f, endPosY - 1.0f, endPosX + 0.5f, endPosY + 0.5f, Color.BLACK);

                    // actual box
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 0.5f, posY, posX, endPosY, ThemeUtils.getMainColor());
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX, endPosY - 0.5f, endPosX, endPosY, ThemeUtils.getMainColor());
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 0.5f, posY, endPosX, posY + 0.5f, ThemeUtils.getMainColor());
                    DrawUtils.rectPoint4(bufferBuilder, matrix, endPosX - 0.5f, posY, endPosX, endPosY, ThemeUtils.getMainColor());

                    if (_2dESPBoxFill.getValue()) {
                        DrawUtils.rectPoint4(bufferBuilder, matrix, endPosX, endPosY, posX, posY, new Color(0, 0, 0, 80));
                    }
                }

                if (_2dESPHealthBar.getValue()) {
                    float healthRatio = ((PlayerEntity) entity).getHealth() / ((PlayerEntity) entity).getMaxHealth();
                    float healthBarTop = endPosY + (posY - endPosY) * healthRatio;
                    float yOffset = 0.3f;

                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 5.5f, posY, posX - 2.5f, endPosY, Color.BLACK);
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 5.0f, healthBarTop - yOffset, posX - 3f, healthBarTop, Color.BLACK);
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 5.0f, endPosY, posX - 3f, endPosY + yOffset, Color.BLACK);

                    DrawUtils.rectPoint4VerticalGradient(bufferBuilder, matrix, posX - 5.0f, healthBarTop, posX - 3.0f, endPosY, ThemeUtils.getMainColor(), ThemeUtils.getMainColor().darker().darker());
                }

                if (_2dESPNameTags.getValue()) {
                    DrawUtils.rectPoint4(bufferBuilder, matrix, posX - 0.5f, posY - 6f, endPosX, posY - 1f, new Color(0, 0, 0, 120));
                }
            }

            if (bufferBuilder != null && (_2dESPBox.getValue() || _2dESPBoxFill.getValue() || _2dESPHealthBar.getValue() || _2dESPNameTags.getValue())) {
                BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            }
        }

        RenderSystem.disableBlend();
    };

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (isNull()) {
            return;
        }

        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (entity instanceof PlayerEntity && entity != mc.player) {
                if (Client.INSTANCE.getModuleManager().getModule(AntiBot.class).isBot(entity)) {
                    continue;
                }
                if (boxESP.getValue()) {
                    RenderUtils.draw3DBox(event.getMatrixStack().peek().getPositionMatrix(), entity.getBoundingBox(), ThemeUtils.getMainColor());
                }
            }
        }
    };
}
