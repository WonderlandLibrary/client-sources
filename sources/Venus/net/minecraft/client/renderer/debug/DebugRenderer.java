/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.CaveDebugRenderer;
import net.minecraft.client.renderer.debug.ChunkBorderDebugRenderer;
import net.minecraft.client.renderer.debug.ChunkInfoDebugRenderer;
import net.minecraft.client.renderer.debug.CollisionBoxDebugRenderer;
import net.minecraft.client.renderer.debug.EntityAIDebugRenderer;
import net.minecraft.client.renderer.debug.GameTestDebugRenderer;
import net.minecraft.client.renderer.debug.HeightMapDebugRenderer;
import net.minecraft.client.renderer.debug.LightDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateDebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingDebugRenderer;
import net.minecraft.client.renderer.debug.PointOfInterestDebugRenderer;
import net.minecraft.client.renderer.debug.RaidDebugRenderer;
import net.minecraft.client.renderer.debug.SolidFaceDebugRenderer;
import net.minecraft.client.renderer.debug.StructureDebugRenderer;
import net.minecraft.client.renderer.debug.VillageSectionsDebugRender;
import net.minecraft.client.renderer.debug.WaterDebugRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptsDebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3d;

public class DebugRenderer {
    public final PathfindingDebugRenderer pathfinding = new PathfindingDebugRenderer();
    public final IDebugRenderer water;
    public final IDebugRenderer chunkBorder;
    public final IDebugRenderer heightMap;
    public final IDebugRenderer collisionBox;
    public final IDebugRenderer neighborsUpdate;
    public final CaveDebugRenderer cave;
    public final StructureDebugRenderer structure;
    public final IDebugRenderer light;
    public final IDebugRenderer worldGenAttempts;
    public final IDebugRenderer solidFace;
    public final IDebugRenderer field_217740_l;
    public final PointOfInterestDebugRenderer field_239371_m_;
    public final VillageSectionsDebugRender field_239372_n_;
    public final BeeDebugRenderer field_229017_n_;
    public final RaidDebugRenderer field_222927_n;
    public final EntityAIDebugRenderer field_217742_n;
    public final GameTestDebugRenderer field_229018_q_;
    private boolean chunkBorderEnabled;

    public DebugRenderer(Minecraft minecraft) {
        this.water = new WaterDebugRenderer(minecraft);
        this.chunkBorder = new ChunkBorderDebugRenderer(minecraft);
        this.heightMap = new HeightMapDebugRenderer(minecraft);
        this.collisionBox = new CollisionBoxDebugRenderer(minecraft);
        this.neighborsUpdate = new NeighborsUpdateDebugRenderer(minecraft);
        this.cave = new CaveDebugRenderer();
        this.structure = new StructureDebugRenderer(minecraft);
        this.light = new LightDebugRenderer(minecraft);
        this.worldGenAttempts = new WorldGenAttemptsDebugRenderer();
        this.solidFace = new SolidFaceDebugRenderer(minecraft);
        this.field_217740_l = new ChunkInfoDebugRenderer(minecraft);
        this.field_239371_m_ = new PointOfInterestDebugRenderer(minecraft);
        this.field_239372_n_ = new VillageSectionsDebugRender();
        this.field_229017_n_ = new BeeDebugRenderer(minecraft);
        this.field_222927_n = new RaidDebugRenderer(minecraft);
        this.field_217742_n = new EntityAIDebugRenderer(minecraft);
        this.field_229018_q_ = new GameTestDebugRenderer();
    }

    public void clear() {
        this.pathfinding.clear();
        this.water.clear();
        this.chunkBorder.clear();
        this.heightMap.clear();
        this.collisionBox.clear();
        this.neighborsUpdate.clear();
        this.cave.clear();
        this.structure.clear();
        this.light.clear();
        this.worldGenAttempts.clear();
        this.solidFace.clear();
        this.field_217740_l.clear();
        this.field_239371_m_.clear();
        this.field_239372_n_.clear();
        this.field_229017_n_.clear();
        this.field_222927_n.clear();
        this.field_217742_n.clear();
        this.field_229018_q_.clear();
    }

    public boolean toggleChunkBorders() {
        this.chunkBorderEnabled = !this.chunkBorderEnabled;
        return this.chunkBorderEnabled;
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer.Impl impl, double d, double d2, double d3) {
        if (this.chunkBorderEnabled && !Minecraft.getInstance().isReducedDebug()) {
            this.chunkBorder.render(matrixStack, impl, d, d2, d3);
        }
        this.field_229018_q_.render(matrixStack, impl, d, d2, d3);
    }

    public static Optional<Entity> getTargetEntity(@Nullable Entity entity2, int n) {
        int n2;
        Predicate<Entity> predicate;
        AxisAlignedBB axisAlignedBB;
        Vector3d vector3d;
        Vector3d vector3d2;
        if (entity2 == null) {
            return Optional.empty();
        }
        Vector3d vector3d3 = entity2.getEyePosition(1.0f);
        EntityRayTraceResult entityRayTraceResult = ProjectileHelper.rayTraceEntities(entity2, vector3d3, vector3d2 = vector3d3.add(vector3d = entity2.getLook(1.0f).scale(n)), axisAlignedBB = entity2.getBoundingBox().expand(vector3d).grow(1.0), predicate = DebugRenderer::lambda$getTargetEntity$0, n2 = n * n);
        if (entityRayTraceResult == null) {
            return Optional.empty();
        }
        return vector3d3.squareDistanceTo(entityRayTraceResult.getHitVec()) > (double)n2 ? Optional.empty() : Optional.of(entityRayTraceResult.getEntity());
    }

    public static void renderBox(BlockPos blockPos, BlockPos blockPos2, float f, float f2, float f3, float f4) {
        ActiveRenderInfo activeRenderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        if (activeRenderInfo.isValid()) {
            Vector3d vector3d = activeRenderInfo.getProjectedView().inverse();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos, blockPos2).offset(vector3d);
            DebugRenderer.renderBox(axisAlignedBB, f, f2, f3, f4);
        }
    }

    public static void renderBox(BlockPos blockPos, float f, float f2, float f3, float f4, float f5) {
        ActiveRenderInfo activeRenderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        if (activeRenderInfo.isValid()) {
            Vector3d vector3d = activeRenderInfo.getProjectedView().inverse();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos).offset(vector3d).grow(f);
            DebugRenderer.renderBox(axisAlignedBB, f2, f3, f4, f5);
        }
    }

    public static void renderBox(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        DebugRenderer.renderBox(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, f, f2, f3, f4);
    }

    public static void renderBox(double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        WorldRenderer.addChainedFilledBoxVertices(bufferBuilder, d, d2, d3, d4, d5, d6, f, f2, f3, f4);
        tessellator.draw();
    }

    public static void renderText(String string, int n, int n2, int n3, int n4) {
        DebugRenderer.renderText(string, (double)n + 0.5, (double)n2 + 0.5, (double)n3 + 0.5, n4);
    }

    public static void renderText(String string, double d, double d2, double d3, int n) {
        DebugRenderer.renderText(string, d, d2, d3, n, 0.02f);
    }

    public static void renderText(String string, double d, double d2, double d3, int n, float f) {
        DebugRenderer.renderText(string, d, d2, d3, n, f, true, 0.0f, false);
    }

    public static void renderText(String string, double d, double d2, double d3, int n, float f, boolean bl, float f2, boolean bl2) {
        Minecraft minecraft = Minecraft.getInstance();
        ActiveRenderInfo activeRenderInfo = minecraft.gameRenderer.getActiveRenderInfo();
        if (activeRenderInfo.isValid() && minecraft.getRenderManager().options != null) {
            FontRenderer fontRenderer = minecraft.fontRenderer;
            double d4 = activeRenderInfo.getProjectedView().x;
            double d5 = activeRenderInfo.getProjectedView().y;
            double d6 = activeRenderInfo.getProjectedView().z;
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(d - d4), (float)(d2 - d5) + 0.07f, (float)(d3 - d6));
            RenderSystem.normal3f(0.0f, 1.0f, 0.0f);
            RenderSystem.multMatrix(new Matrix4f(activeRenderInfo.getRotation()));
            RenderSystem.scalef(f, -f, f);
            RenderSystem.enableTexture();
            if (bl2) {
                RenderSystem.disableDepthTest();
            } else {
                RenderSystem.enableDepthTest();
            }
            RenderSystem.depthMask(true);
            RenderSystem.scalef(-1.0f, 1.0f, 1.0f);
            float f3 = bl ? (float)(-fontRenderer.getStringWidth(string)) / 2.0f : 0.0f;
            RenderSystem.enableAlphaTest();
            IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            fontRenderer.renderString(string, f3 -= f2 / f, 0.0f, n, false, TransformationMatrix.identity().getMatrix(), impl, bl2, 0, 1);
            impl.finish();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableDepthTest();
            RenderSystem.popMatrix();
        }
    }

    private static boolean lambda$getTargetEntity$0(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }

    public static interface IDebugRenderer {
        public void render(MatrixStack var1, IRenderTypeBuffer var2, double var3, double var5, double var7);

        default public void clear() {
        }
    }
}

