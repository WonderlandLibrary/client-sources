/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.utils.PostInit;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public final class RenderUtils {
    public static Vec3d center;

    private static final Pool<RenderBlock> renderBlockPool = new Pool<>(RenderBlock::new);
    private static final List<RenderBlock> renderBlocks = new ArrayList<>();

    @PostInit
    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(RenderUtils.class);
    }

    // Items
    public static void drawItem(DrawContext drawContext, ItemStack itemStack, int x, int y, float scale, boolean overlay, String countOverride) {
        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.scale(scale, scale, 1f);
        matrices.translate(0, 0, 401); // Thanks Mojang

        int scaledX = (int) (x / scale);
        int scaledY = (int) (y / scale);

        drawContext.drawItem(itemStack, scaledX, scaledY);
        if (overlay) drawContext.drawItemInSlot(mc.textRenderer, itemStack, scaledX, scaledY, countOverride);

        matrices.pop();
    }

    public static void drawItem(DrawContext drawContext, ItemStack itemStack, int x, int y, float scale, boolean overlay) {
        drawItem(drawContext, itemStack, x, y, scale, overlay, null);
    }

    public static void updateScreenCenter() {
        MinecraftClient mc = MinecraftClient.getInstance();

        Vector3f pos = new Vector3f(0, 0, 1);

        if (mc.options.getBobView().getValue()) {
            MatrixStack bobViewMatrices = new MatrixStack();

            bobView(bobViewMatrices);
            pos.mulPosition(bobViewMatrices.peek().getPositionMatrix().invert());
        }

        center = new Vec3d(pos.x, -pos.y, pos.z)
            .rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch()))
            .rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw()))
            .add(mc.gameRenderer.getCamera().getPos());
    }

    private static void bobView(final MatrixStack matrices) {
        Entity cameraEntity = MinecraftClient.getInstance().getCameraEntity();

        if (cameraEntity instanceof PlayerEntity playerEntity) {
            float f = MinecraftClient.getInstance().getTickDelta();
            float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
            float h = -(playerEntity.horizontalSpeed + g * f);
            float i = MathHelper.lerp(f, playerEntity.prevStrideDistance, playerEntity.strideDistance);

            matrices.translate(-(MathHelper.sin(h * 3.1415927f) * i * 0.5), Math.abs(MathHelper.cos(h * 3.1415927f) * i), 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(h * 3.1415927f) * i * 3));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(h * 3.1415927f - 0.2f) * i) * 5));
        }
    }

    public static void renderTickingBlock(BlockPos blockPos, Color sideColor, Color lineColor, ShapeMode shapeMode, int excludeDir, int duration, boolean fade, boolean shrink) {
        // Ensure there aren't multiple fading blocks in one pos
        Iterator<RenderBlock> iterator = renderBlocks.iterator();
        while (iterator.hasNext()) {
            RenderBlock next = iterator.next();
            if (next.pos.equals(blockPos)) {
                iterator.remove();
                renderBlockPool.free(next);
            }
        }

        renderBlocks.add(renderBlockPool.get().set(blockPos, sideColor, lineColor, shapeMode, excludeDir, duration, fade, shrink));
    }

    @EventHandler
    private static void onTick(final TickEvent.Pre event) {
        if (renderBlocks.isEmpty()) return;

        renderBlocks.forEach(RenderBlock::tick);

        Iterator<RenderBlock> iterator = renderBlocks.iterator();
        while (iterator.hasNext()) {
            RenderBlock next = iterator.next();
            if (next.ticks <= 0) {
                iterator.remove();
                renderBlockPool.free(next);
            }
        }
    }

    @EventHandler
    private static void onRender(final Render3DEvent event) {
        renderBlocks.forEach(block -> block.render(event));
    }

    public static class RenderBlock {
        public BlockPos.Mutable pos = new BlockPos.Mutable();

        public Color sideColor, lineColor;
        public ShapeMode shapeMode;
        public int excludeDir;

        public int ticks, duration;
        public boolean fade, shrink;

        public RenderBlock set(BlockPos blockPos, Color sideColor, Color lineColor, ShapeMode shapeMode, int excludeDir, int duration, boolean fade, boolean shrink) {
            pos.set(blockPos);
            this.sideColor = sideColor;
            this.lineColor = lineColor;
            this.shapeMode = shapeMode;
            this.excludeDir = excludeDir;
            this.fade = fade;
            this.shrink = shrink;
            this.ticks = duration;
            this.duration = duration;

            return this;
        }

        public void tick() {
            ticks--;
        }

        public void render(final Render3DEvent event) {
            int preSideA = sideColor.a;
            int preLineA = lineColor.a;
            double x1 = pos.getX(), y1 = pos.getY(), z1 = pos.getZ(),
                   x2 = pos.getX() + 1, y2 = pos.getY() + 1, z2 = pos.getZ() + 1;

            if (fade) {
                sideColor.a *= (double) ticks / duration;
                lineColor.a *= (double) ticks / duration;
            }
            if (shrink) {
                double d = (double) ticks / duration;
                x1 += d; y1 += d; z1 += d;
                x2 -= d; y2 -= d; z2 -= d;
            }

            event.renderer.box(x1, y1, z1, x2, y2, z2, sideColor, lineColor, shapeMode, excludeDir);

            sideColor.a = preSideA;
            lineColor.a = preLineA;
        }
    }

    public static void renderCircle(double range, double height, Color color, Entity entity, final Render3DEvent e) {
        Vec3d pos = entity.getPos().subtract(RenderUtils.getInterpolationOffset(entity));

        double lastX = 0;
        double lastZ = range;
        for (int angle = 0; angle <= 360; angle += 6) {
            float cos = MathHelper.cos((float) Math.toRadians(angle));
            float sin = MathHelper.sin((float) Math.toRadians(angle));

            double x = range * sin;
            double z = range * cos;

            e.renderer.line(
                pos.x + lastX, pos.y + height, pos.z + lastZ,
                pos.x + x, pos.y + height, pos.z + z, color
            );
            lastX = x;
            lastZ = z;
        }
    }

    public static Vec3d getInterpolationOffset(Entity e) {
        if (MinecraftClient.getInstance().isPaused()) {
            return Vec3d.ZERO;
        }

        double tickDelta = MinecraftClient.getInstance().getTickDelta();
        return new Vec3d(
            e.getX() - MathHelper.lerp(tickDelta, e.lastRenderX, e.getX()),
            e.getY() - MathHelper.lerp(tickDelta, e.lastRenderY, e.getY()),
            e.getZ() - MathHelper.lerp(tickDelta, e.lastRenderZ, e.getZ()));
    }

    // HUD shit ig?


    /**
     * Wrapper method
     */
    //public static void drawCircle(MatrixStack matrices, double centerX, double centerY, double radius, double samples, Color color) {
    //    drawCircle(matrices, centerX, centerY, radius, samples, color);
    //}

    /**
     * Draws a cirlce
     *
     * @param matrices ...
     * @param centerX  CenterX of the Circle
     * @param centerY  CenterY of the circle
     * @param radius   "Bigness" of the circle
     * @param samples  How many pixels should be used
     * @param color    Color
     */
    public static void drawCircle(final MatrixStack matrices, final double centerX, final double centerY, final double radius, final double samples, final Color color) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        for (double r = 0; r < 360; r += (360 / samples)) {
            float rad1 = (float) Math.toRadians(r);
            float sin = (float) (Math.sin(rad1) * radius);
            float cos = (float) (Math.cos(rad1) * radius);
            bufferBuilder.vertex(matrix, (float) centerX + sin, (float) centerY + cos, 0.0F).color(color.r, color.g, color.b, color.a);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

}

