/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.optifine.BetterSnow;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.EmissiveTextures;
import net.optifine.model.BlockModelCustomizer;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.BlockUtils;

public class BlockModelRenderer {
    private final BlockColors blockColors;
    private static final ThreadLocal<Cache> CACHE_COMBINED_LIGHT = ThreadLocal.withInitial(BlockModelRenderer::lambda$static$0);
    private static float aoLightValueOpaque = 0.2f;
    private static boolean separateAoLightValue = false;
    private static final LightCacheOF LIGHT_CACHE_OF = new LightCacheOF();
    private static final RenderType[] OVERLAY_LAYERS = new RenderType[]{RenderTypes.CUTOUT, RenderTypes.CUTOUT_MIPPED, RenderTypes.TRANSLUCENT};
    private boolean forgeModelData = Reflector.ForgeHooksClient.exists();

    public BlockModelRenderer(BlockColors blockColors) {
        this.blockColors = blockColors;
    }

    public boolean renderModel(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n) {
        return this.renderModel(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n, EmptyModelData.INSTANCE);
    }

    public boolean renderModel(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n, IModelData iModelData) {
        boolean bl2;
        boolean bl3 = bl2 = Minecraft.isAmbientOcclusionEnabled() && ReflectorForge.getLightValue(blockState, iBlockDisplayReader, blockPos) == 0 && iBakedModel.isAmbientOcclusion();
        if (this.forgeModelData) {
            iModelData = iBakedModel.getModelData(iBlockDisplayReader, blockPos, blockState, iModelData);
        }
        Vector3d vector3d = blockState.getOffset(iBlockDisplayReader, blockPos);
        matrixStack.translate(vector3d.x, vector3d.y, vector3d.z);
        try {
            boolean bl4;
            if (Config.isShaders()) {
                SVertexBuilder.pushEntity(blockState, iVertexBuilder);
            }
            if (!Config.isAlternateBlocks()) {
                l = 0L;
            }
            RenderEnv renderEnv = iVertexBuilder.getRenderEnv(blockState, blockPos);
            iBakedModel = BlockModelCustomizer.getRenderModel(iBakedModel, blockState, renderEnv);
            boolean bl5 = bl4 = bl2 ? this.renderModelSmooth(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n, iModelData) : this.renderModelFlat(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n, iModelData);
            if (bl4) {
                this.renderOverlayModels(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, n, bl, random2, l, renderEnv, bl2, vector3d);
            }
            if (Config.isShaders()) {
                SVertexBuilder.popEntity(iVertexBuilder);
            }
            return bl4;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo(crashReportCategory, blockPos, blockState);
            crashReportCategory.addDetail("Using AO", bl2);
            throw new ReportedException(crashReport);
        }
    }

    public boolean renderModelSmooth(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n) {
        return this.renderModelSmooth(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n, EmptyModelData.INSTANCE);
    }

    public boolean renderModelSmooth(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n, IModelData iModelData) {
        boolean bl2 = false;
        RenderEnv renderEnv = iVertexBuilder.getRenderEnv(blockState, blockPos);
        RenderType renderType = iVertexBuilder.getRenderType();
        Object object = Direction.VALUES;
        int n2 = ((Direction[])object).length;
        for (int i = 0; i < n2; ++i) {
            Direction direction = object[i];
            if (bl && !BlockUtils.shouldSideBeRendered(blockState, iBlockDisplayReader, blockPos, direction, renderEnv)) continue;
            random2.setSeed(l);
            List<BakedQuad> list = this.forgeModelData ? iBakedModel.getQuads(blockState, direction, random2, iModelData) : iBakedModel.getQuads(blockState, direction, random2);
            list = BlockModelCustomizer.getRenderQuads(list, iBlockDisplayReader, blockState, blockPos, direction, renderType, l, renderEnv);
            this.renderQuadsSmooth(iBlockDisplayReader, blockState, blockPos, matrixStack, iVertexBuilder, list, n, renderEnv);
            bl2 = true;
        }
        random2.setSeed(l);
        Object object2 = object = this.forgeModelData ? iBakedModel.getQuads(blockState, null, random2, iModelData) : iBakedModel.getQuads(blockState, null, random2);
        if (!object.isEmpty()) {
            object = BlockModelCustomizer.getRenderQuads((List<BakedQuad>)object, iBlockDisplayReader, blockState, blockPos, null, renderType, l, renderEnv);
            this.renderQuadsSmooth(iBlockDisplayReader, blockState, blockPos, matrixStack, iVertexBuilder, (List<BakedQuad>)object, n, renderEnv);
            bl2 = true;
        }
        return bl2;
    }

    public boolean renderModelFlat(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n) {
        return this.renderModelFlat(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n, EmptyModelData.INSTANCE);
    }

    public boolean renderModelFlat(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, long l, int n, IModelData iModelData) {
        boolean bl2 = false;
        RenderEnv renderEnv = iVertexBuilder.getRenderEnv(blockState, blockPos);
        RenderType renderType = iVertexBuilder.getRenderType();
        Object object = Direction.VALUES;
        int n2 = ((Direction[])object).length;
        for (int i = 0; i < n2; ++i) {
            Direction direction = object[i];
            if (bl && !BlockUtils.shouldSideBeRendered(blockState, iBlockDisplayReader, blockPos, direction, renderEnv)) continue;
            random2.setSeed(l);
            List<BakedQuad> list = this.forgeModelData ? iBakedModel.getQuads(blockState, direction, random2, iModelData) : iBakedModel.getQuads(blockState, direction, random2);
            int n3 = WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos.offset(direction));
            list = BlockModelCustomizer.getRenderQuads(list, iBlockDisplayReader, blockState, blockPos, direction, renderType, l, renderEnv);
            this.renderQuadsFlat(iBlockDisplayReader, blockState, blockPos, n3, n, false, matrixStack, iVertexBuilder, list, renderEnv);
            bl2 = true;
        }
        random2.setSeed(l);
        Object object2 = object = this.forgeModelData ? iBakedModel.getQuads(blockState, null, random2, iModelData) : iBakedModel.getQuads(blockState, null, random2);
        if (!object.isEmpty()) {
            object = BlockModelCustomizer.getRenderQuads((List<BakedQuad>)object, iBlockDisplayReader, blockState, blockPos, null, renderType, l, renderEnv);
            this.renderQuadsFlat(iBlockDisplayReader, blockState, blockPos, -1, n, true, matrixStack, iVertexBuilder, (List<BakedQuad>)object, renderEnv);
            bl2 = true;
        }
        return bl2;
    }

    private void renderQuadsSmooth(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, List<BakedQuad> list, int n, RenderEnv renderEnv) {
        float[] fArray = renderEnv.getQuadBounds();
        BitSet bitSet = renderEnv.getBoundsFlags();
        AmbientOcclusionFace ambientOcclusionFace = renderEnv.getAoFace();
        int n2 = list.size();
        for (int i = 0; i < n2; ++i) {
            BakedQuad bakedQuad = list.get(i);
            this.fillQuadBounds(iBlockDisplayReader, blockState, blockPos, bakedQuad.getVertexData(), bakedQuad.getFace(), fArray, bitSet);
            ambientOcclusionFace.renderBlockModel(iBlockDisplayReader, blockState, blockPos, bakedQuad.getFace(), fArray, bitSet, bakedQuad.applyDiffuseLighting());
            if (bakedQuad.getSprite().isSpriteEmissive) {
                ambientOcclusionFace.setMaxBlockLight();
            }
            this.renderQuadSmooth(iBlockDisplayReader, blockState, blockPos, iVertexBuilder, matrixStack.getLast(), bakedQuad, ambientOcclusionFace.vertexColorMultiplier[0], ambientOcclusionFace.vertexColorMultiplier[1], ambientOcclusionFace.vertexColorMultiplier[2], ambientOcclusionFace.vertexColorMultiplier[3], ambientOcclusionFace.vertexBrightness[0], ambientOcclusionFace.vertexBrightness[1], ambientOcclusionFace.vertexBrightness[2], ambientOcclusionFace.vertexBrightness[3], n, renderEnv);
        }
    }

    private void renderQuadSmooth(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, IVertexBuilder iVertexBuilder, MatrixStack.Entry entry, BakedQuad bakedQuad, float f, float f2, float f3, float f4, int n, int n2, int n3, int n4, int n5, RenderEnv renderEnv) {
        float f5;
        float f6;
        float f7;
        int n6 = CustomColors.getColorMultiplier(bakedQuad, blockState, iBlockDisplayReader, blockPos, renderEnv);
        if (!bakedQuad.hasTintIndex() && n6 == -1) {
            f7 = 1.0f;
            f6 = 1.0f;
            f5 = 1.0f;
        } else {
            int n7 = n6 != -1 ? n6 : this.blockColors.getColor(blockState, iBlockDisplayReader, blockPos, bakedQuad.getTintIndex());
            f7 = (float)(n7 >> 16 & 0xFF) / 255.0f;
            f6 = (float)(n7 >> 8 & 0xFF) / 255.0f;
            f5 = (float)(n7 & 0xFF) / 255.0f;
        }
        iVertexBuilder.addQuad(entry, bakedQuad, iVertexBuilder.getTempFloat4(f, f2, f3, f4), f7, f6, f5, iVertexBuilder.getTempInt4(n, n2, n3, n4), n5, true);
    }

    private void fillQuadBounds(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, int[] nArray, Direction direction, @Nullable float[] fArray, BitSet bitSet) {
        float f;
        int n;
        float f2 = 32.0f;
        float f3 = 32.0f;
        float f4 = 32.0f;
        float f5 = -32.0f;
        float f6 = -32.0f;
        float f7 = -32.0f;
        int n2 = nArray.length / 4;
        for (n = 0; n < 4; ++n) {
            f = Float.intBitsToFloat(nArray[n * n2]);
            float f8 = Float.intBitsToFloat(nArray[n * n2 + 1]);
            float f9 = Float.intBitsToFloat(nArray[n * n2 + 2]);
            f2 = Math.min(f2, f);
            f3 = Math.min(f3, f8);
            f4 = Math.min(f4, f9);
            f5 = Math.max(f5, f);
            f6 = Math.max(f6, f8);
            f7 = Math.max(f7, f9);
        }
        if (fArray != null) {
            fArray[Direction.WEST.getIndex()] = f2;
            fArray[Direction.EAST.getIndex()] = f5;
            fArray[Direction.DOWN.getIndex()] = f3;
            fArray[Direction.UP.getIndex()] = f6;
            fArray[Direction.NORTH.getIndex()] = f4;
            fArray[Direction.SOUTH.getIndex()] = f7;
            n = Direction.VALUES.length;
            fArray[Direction.WEST.getIndex() + n] = 1.0f - f2;
            fArray[Direction.EAST.getIndex() + n] = 1.0f - f5;
            fArray[Direction.DOWN.getIndex() + n] = 1.0f - f3;
            fArray[Direction.UP.getIndex() + n] = 1.0f - f6;
            fArray[Direction.NORTH.getIndex() + n] = 1.0f - f4;
            fArray[Direction.SOUTH.getIndex() + n] = 1.0f - f7;
        }
        float f10 = 1.0E-4f;
        f = 0.9999f;
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                bitSet.set(1, f2 >= 1.0E-4f || f4 >= 1.0E-4f || f5 <= 0.9999f || f7 <= 0.9999f);
                bitSet.set(0, f3 == f6 && (f3 < 1.0E-4f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
                break;
            }
            case 2: {
                bitSet.set(1, f2 >= 1.0E-4f || f4 >= 1.0E-4f || f5 <= 0.9999f || f7 <= 0.9999f);
                bitSet.set(0, f3 == f6 && (f6 > 0.9999f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
                break;
            }
            case 3: {
                bitSet.set(1, f2 >= 1.0E-4f || f3 >= 1.0E-4f || f5 <= 0.9999f || f6 <= 0.9999f);
                bitSet.set(0, f4 == f7 && (f4 < 1.0E-4f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
                break;
            }
            case 4: {
                bitSet.set(1, f2 >= 1.0E-4f || f3 >= 1.0E-4f || f5 <= 0.9999f || f6 <= 0.9999f);
                bitSet.set(0, f4 == f7 && (f7 > 0.9999f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
                break;
            }
            case 5: {
                bitSet.set(1, f3 >= 1.0E-4f || f4 >= 1.0E-4f || f6 <= 0.9999f || f7 <= 0.9999f);
                bitSet.set(0, f2 == f5 && (f2 < 1.0E-4f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
                break;
            }
            case 6: {
                bitSet.set(1, f3 >= 1.0E-4f || f4 >= 1.0E-4f || f6 <= 0.9999f || f7 <= 0.9999f);
                bitSet.set(0, f2 == f5 && (f5 > 0.9999f || blockState.hasOpaqueCollisionShape(iBlockDisplayReader, blockPos)));
            }
        }
    }

    private void renderQuadsFlat(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, int n, int n2, boolean bl, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, List<BakedQuad> list, RenderEnv renderEnv) {
        BitSet bitSet = renderEnv.getBoundsFlags();
        int n3 = list.size();
        for (int i = 0; i < n3; ++i) {
            BakedQuad bakedQuad = list.get(i);
            if (bl) {
                this.fillQuadBounds(iBlockDisplayReader, blockState, blockPos, bakedQuad.getVertexData(), bakedQuad.getFace(), null, bitSet);
                BlockPos blockPos2 = bitSet.get(1) ? blockPos.offset(bakedQuad.getFace()) : blockPos;
                n = WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos2);
            }
            if (bakedQuad.getSprite().isSpriteEmissive) {
                n = LightTexture.MAX_BRIGHTNESS;
            }
            float f = iBlockDisplayReader.func_230487_a_(bakedQuad.getFace(), bakedQuad.applyDiffuseLighting());
            this.renderQuadSmooth(iBlockDisplayReader, blockState, blockPos, iVertexBuilder, matrixStack.getLast(), bakedQuad, f, f, f, f, n, n, n, n, n2, renderEnv);
        }
    }

    public void renderModelBrightnessColor(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, @Nullable BlockState blockState, IBakedModel iBakedModel, float f, float f2, float f3, int n, int n2) {
        this.renderModel(entry, iVertexBuilder, blockState, iBakedModel, f, f2, f3, n, n2, EmptyModelData.INSTANCE);
    }

    public void renderModel(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, @Nullable BlockState blockState, IBakedModel iBakedModel, float f, float f2, float f3, int n, int n2, IModelData iModelData) {
        Random random2 = new Random();
        long l = 42L;
        for (Direction direction : Direction.VALUES) {
            random2.setSeed(42L);
            if (this.forgeModelData) {
                BlockModelRenderer.renderModelBrightnessColorQuads(entry, iVertexBuilder, f, f2, f3, iBakedModel.getQuads(blockState, direction, random2, iModelData), n, n2);
                continue;
            }
            BlockModelRenderer.renderModelBrightnessColorQuads(entry, iVertexBuilder, f, f2, f3, iBakedModel.getQuads(blockState, direction, random2), n, n2);
        }
        random2.setSeed(42L);
        if (this.forgeModelData) {
            BlockModelRenderer.renderModelBrightnessColorQuads(entry, iVertexBuilder, f, f2, f3, iBakedModel.getQuads(blockState, null, random2, iModelData), n, n2);
        } else {
            BlockModelRenderer.renderModelBrightnessColorQuads(entry, iVertexBuilder, f, f2, f3, iBakedModel.getQuads(blockState, null, random2), n, n2);
        }
    }

    private static void renderModelBrightnessColorQuads(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, float f, float f2, float f3, List<BakedQuad> list, int n, int n2) {
        boolean bl = EmissiveTextures.isActive();
        Iterator<BakedQuad> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            float f4;
            float f5;
            float f6;
            BakedQuad bakedQuad = iterator2.next();
            if (bl && (bakedQuad = EmissiveTextures.getEmissiveQuad(bakedQuad)) == null) continue;
            if (bakedQuad.hasTintIndex()) {
                f6 = MathHelper.clamp(f, 0.0f, 1.0f);
                f5 = MathHelper.clamp(f2, 0.0f, 1.0f);
                f4 = MathHelper.clamp(f3, 0.0f, 1.0f);
            } else {
                f6 = 1.0f;
                f5 = 1.0f;
                f4 = 1.0f;
            }
            iVertexBuilder.addQuad(entry, bakedQuad, f6, f5, f4, n, n2);
        }
        return;
    }

    public static void enableCache() {
        CACHE_COMBINED_LIGHT.get().enable();
    }

    public static void disableCache() {
        CACHE_COMBINED_LIGHT.get().disable();
    }

    public static float fixAoLightValue(float f) {
        return f == 0.2f ? aoLightValueOpaque : f;
    }

    public static void updateAoLightValue() {
        aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
        separateAoLightValue = Config.isShaders() && Shaders.isSeparateAo();
    }

    public static boolean isSeparateAoLightValue() {
        return separateAoLightValue;
    }

    private void renderOverlayModels(IBlockDisplayReader iBlockDisplayReader, IBakedModel iBakedModel, BlockState blockState, BlockPos blockPos, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, boolean bl, Random random2, long l, RenderEnv renderEnv, boolean bl2, Vector3d vector3d) {
        Object object;
        if (renderEnv.isOverlaysRendered()) {
            for (int i = 0; i < OVERLAY_LAYERS.length; ++i) {
                object = OVERLAY_LAYERS[i];
                ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay((RenderType)object);
                if (listQuadsOverlay.size() <= 0) continue;
                RegionRenderCacheBuilder regionRenderCacheBuilder = renderEnv.getRegionRenderCacheBuilder();
                if (regionRenderCacheBuilder != null) {
                    BufferBuilder bufferBuilder = regionRenderCacheBuilder.getBuilder((RenderType)object);
                    if (!bufferBuilder.isDrawing()) {
                        bufferBuilder.begin(7, DefaultVertexFormats.BLOCK);
                    }
                    for (int j = 0; j < listQuadsOverlay.size(); ++j) {
                        BakedQuad bakedQuad = listQuadsOverlay.getQuad(j);
                        List<BakedQuad> list = listQuadsOverlay.getListQuadsSingle(bakedQuad);
                        BlockState blockState2 = listQuadsOverlay.getBlockState(j);
                        if (bakedQuad.getQuadEmissive() != null) {
                            listQuadsOverlay.addQuad(bakedQuad.getQuadEmissive(), blockState2);
                        }
                        renderEnv.reset(blockState2, blockPos);
                        if (bl2) {
                            this.renderQuadsSmooth(iBlockDisplayReader, blockState2, blockPos, matrixStack, bufferBuilder, list, n, renderEnv);
                            continue;
                        }
                        int n2 = WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState2, blockPos.offset(bakedQuad.getFace()));
                        this.renderQuadsFlat(iBlockDisplayReader, blockState2, blockPos, n2, n, false, matrixStack, bufferBuilder, list, renderEnv);
                    }
                }
                listQuadsOverlay.clear();
            }
        }
        if (Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(iBlockDisplayReader, blockState, blockPos)) {
            IBakedModel iBakedModel2 = BetterSnow.getModelSnowLayer();
            object = BetterSnow.getStateSnowLayer();
            matrixStack.translate(-vector3d.x, -vector3d.y, -vector3d.z);
            this.renderModel(iBlockDisplayReader, iBakedModel2, (BlockState)object, blockPos, matrixStack, iVertexBuilder, bl, random2, l, n);
        }
    }

    private static Cache lambda$static$0() {
        return new Cache();
    }

    public static class AmbientOcclusionFace {
        private final float[] vertexColorMultiplier = new float[4];
        private final int[] vertexBrightness = new int[4];
        private BlockPosM blockPos = new BlockPosM();

        public AmbientOcclusionFace() {
            this(null);
        }

        public AmbientOcclusionFace(BlockModelRenderer blockModelRenderer) {
        }

        public void setMaxBlockLight() {
            int n;
            this.vertexBrightness[0] = n = LightTexture.MAX_BRIGHTNESS;
            this.vertexBrightness[1] = n;
            this.vertexBrightness[2] = n;
            this.vertexBrightness[3] = n;
            this.vertexColorMultiplier[0] = 1.0f;
            this.vertexColorMultiplier[1] = 1.0f;
            this.vertexColorMultiplier[2] = 1.0f;
            this.vertexColorMultiplier[3] = 1.0f;
        }

        public void renderBlockModel(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos, Direction direction, float[] fArray, BitSet bitSet, boolean bl) {
            float f;
            int n;
            float f2;
            int n2;
            float f3;
            int n3;
            float f4;
            int n4;
            float f5;
            boolean bl2;
            BlockPos blockPos2 = bitSet.get(1) ? blockPos.offset(direction) : blockPos;
            NeighborInfo neighborInfo = NeighborInfo.getNeighbourInfo(direction);
            BlockPosM blockPosM = this.blockPos;
            LightCacheOF lightCacheOF = LIGHT_CACHE_OF;
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[0]);
            BlockState blockState2 = iBlockDisplayReader.getBlockState(blockPosM);
            int n5 = LightCacheOF.getPackedLight(blockState2, iBlockDisplayReader, blockPosM);
            float f6 = LightCacheOF.getBrightness(blockState2, iBlockDisplayReader, blockPosM);
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[1]);
            BlockState blockState3 = iBlockDisplayReader.getBlockState(blockPosM);
            int n6 = LightCacheOF.getPackedLight(blockState3, iBlockDisplayReader, blockPosM);
            float f7 = LightCacheOF.getBrightness(blockState3, iBlockDisplayReader, blockPosM);
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[2]);
            BlockState blockState4 = iBlockDisplayReader.getBlockState(blockPosM);
            int n7 = LightCacheOF.getPackedLight(blockState4, iBlockDisplayReader, blockPosM);
            float f8 = LightCacheOF.getBrightness(blockState4, iBlockDisplayReader, blockPosM);
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[3]);
            BlockState blockState5 = iBlockDisplayReader.getBlockState(blockPosM);
            int n8 = LightCacheOF.getPackedLight(blockState5, iBlockDisplayReader, blockPosM);
            float f9 = LightCacheOF.getBrightness(blockState5, iBlockDisplayReader, blockPosM);
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[0], direction);
            boolean bl3 = iBlockDisplayReader.getBlockState(blockPosM).getOpacity(iBlockDisplayReader, blockPosM) == 0;
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[1], direction);
            boolean bl4 = iBlockDisplayReader.getBlockState(blockPosM).getOpacity(iBlockDisplayReader, blockPosM) == 0;
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[2], direction);
            boolean bl5 = iBlockDisplayReader.getBlockState(blockPosM).getOpacity(iBlockDisplayReader, blockPosM) == 0;
            blockPosM.setPosOffset(blockPos2, neighborInfo.corners[3], direction);
            boolean bl6 = bl2 = iBlockDisplayReader.getBlockState(blockPosM).getOpacity(iBlockDisplayReader, blockPosM) == 0;
            if (!bl5 && !bl3) {
                f5 = f6;
                n4 = n5;
            } else {
                blockPosM.setPosOffset(blockPos2, neighborInfo.corners[0], neighborInfo.corners[2]);
                BlockState blockState6 = iBlockDisplayReader.getBlockState(blockPosM);
                f5 = LightCacheOF.getBrightness(blockState6, iBlockDisplayReader, blockPosM);
                n4 = LightCacheOF.getPackedLight(blockState6, iBlockDisplayReader, blockPosM);
            }
            if (!bl2 && !bl3) {
                f4 = f6;
                n3 = n5;
            } else {
                blockPosM.setPosOffset(blockPos2, neighborInfo.corners[0], neighborInfo.corners[3]);
                BlockState blockState7 = iBlockDisplayReader.getBlockState(blockPosM);
                f4 = LightCacheOF.getBrightness(blockState7, iBlockDisplayReader, blockPosM);
                n3 = LightCacheOF.getPackedLight(blockState7, iBlockDisplayReader, blockPosM);
            }
            if (!bl5 && !bl4) {
                f3 = f6;
                n2 = n5;
            } else {
                blockPosM.setPosOffset(blockPos2, neighborInfo.corners[1], neighborInfo.corners[2]);
                BlockState blockState8 = iBlockDisplayReader.getBlockState(blockPosM);
                f3 = LightCacheOF.getBrightness(blockState8, iBlockDisplayReader, blockPosM);
                n2 = LightCacheOF.getPackedLight(blockState8, iBlockDisplayReader, blockPosM);
            }
            if (!bl2 && !bl4) {
                f2 = f6;
                n = n5;
            } else {
                blockPosM.setPosOffset(blockPos2, neighborInfo.corners[1], neighborInfo.corners[3]);
                BlockState blockState9 = iBlockDisplayReader.getBlockState(blockPosM);
                f2 = LightCacheOF.getBrightness(blockState9, iBlockDisplayReader, blockPosM);
                n = LightCacheOF.getPackedLight(blockState9, iBlockDisplayReader, blockPosM);
            }
            int n9 = LightCacheOF.getPackedLight(blockState, iBlockDisplayReader, blockPos);
            blockPosM.setPosOffset(blockPos, direction);
            BlockState blockState10 = iBlockDisplayReader.getBlockState(blockPosM);
            if (bitSet.get(1) || !blockState10.isOpaqueCube(iBlockDisplayReader, blockPosM)) {
                n9 = LightCacheOF.getPackedLight(blockState10, iBlockDisplayReader, blockPosM);
            }
            float f10 = bitSet.get(1) ? LightCacheOF.getBrightness(iBlockDisplayReader.getBlockState(blockPos2), iBlockDisplayReader, blockPos2) : LightCacheOF.getBrightness(iBlockDisplayReader.getBlockState(blockPos), iBlockDisplayReader, blockPos);
            VertexTranslations vertexTranslations = VertexTranslations.getVertexTranslations(direction);
            if (bitSet.get(0) && neighborInfo.doNonCubicWeight) {
                f = (f9 + f6 + f4 + f10) * 0.25f;
                var41_45 = (f8 + f6 + f5 + f10) * 0.25f;
                var42_47 = (f8 + f7 + f3 + f10) * 0.25f;
                var43_48 = (f9 + f7 + f2 + f10) * 0.25f;
                float f11 = fArray[neighborInfo.vert0Weights[0].shape] * fArray[neighborInfo.vert0Weights[1].shape];
                float f12 = fArray[neighborInfo.vert0Weights[2].shape] * fArray[neighborInfo.vert0Weights[3].shape];
                float f13 = fArray[neighborInfo.vert0Weights[4].shape] * fArray[neighborInfo.vert0Weights[5].shape];
                float f14 = fArray[neighborInfo.vert0Weights[6].shape] * fArray[neighborInfo.vert0Weights[7].shape];
                float f15 = fArray[neighborInfo.vert1Weights[0].shape] * fArray[neighborInfo.vert1Weights[1].shape];
                float f16 = fArray[neighborInfo.vert1Weights[2].shape] * fArray[neighborInfo.vert1Weights[3].shape];
                float f17 = fArray[neighborInfo.vert1Weights[4].shape] * fArray[neighborInfo.vert1Weights[5].shape];
                float f18 = fArray[neighborInfo.vert1Weights[6].shape] * fArray[neighborInfo.vert1Weights[7].shape];
                float f19 = fArray[neighborInfo.vert2Weights[0].shape] * fArray[neighborInfo.vert2Weights[1].shape];
                float f20 = fArray[neighborInfo.vert2Weights[2].shape] * fArray[neighborInfo.vert2Weights[3].shape];
                float f21 = fArray[neighborInfo.vert2Weights[4].shape] * fArray[neighborInfo.vert2Weights[5].shape];
                float f22 = fArray[neighborInfo.vert2Weights[6].shape] * fArray[neighborInfo.vert2Weights[7].shape];
                float f23 = fArray[neighborInfo.vert3Weights[0].shape] * fArray[neighborInfo.vert3Weights[1].shape];
                float f24 = fArray[neighborInfo.vert3Weights[2].shape] * fArray[neighborInfo.vert3Weights[3].shape];
                float f25 = fArray[neighborInfo.vert3Weights[4].shape] * fArray[neighborInfo.vert3Weights[5].shape];
                float f26 = fArray[neighborInfo.vert3Weights[6].shape] * fArray[neighborInfo.vert3Weights[7].shape];
                this.vertexColorMultiplier[vertexTranslations.vert0] = f * f11 + var41_45 * f12 + var42_47 * f13 + var43_48 * f14;
                this.vertexColorMultiplier[vertexTranslations.vert1] = f * f15 + var41_45 * f16 + var42_47 * f17 + var43_48 * f18;
                this.vertexColorMultiplier[vertexTranslations.vert2] = f * f19 + var41_45 * f20 + var42_47 * f21 + var43_48 * f22;
                this.vertexColorMultiplier[vertexTranslations.vert3] = f * f23 + var41_45 * f24 + var42_47 * f25 + var43_48 * f26;
                int n10 = this.getAoBrightness(n8, n5, n3, n9);
                int n11 = this.getAoBrightness(n7, n5, n4, n9);
                int n12 = this.getAoBrightness(n7, n6, n2, n9);
                int n13 = this.getAoBrightness(n8, n6, n, n9);
                this.vertexBrightness[vertexTranslations.vert0] = this.getVertexBrightness(n10, n11, n12, n13, f11, f12, f13, f14);
                this.vertexBrightness[vertexTranslations.vert1] = this.getVertexBrightness(n10, n11, n12, n13, f15, f16, f17, f18);
                this.vertexBrightness[vertexTranslations.vert2] = this.getVertexBrightness(n10, n11, n12, n13, f19, f20, f21, f22);
                this.vertexBrightness[vertexTranslations.vert3] = this.getVertexBrightness(n10, n11, n12, n13, f23, f24, f25, f26);
            } else {
                f = (f9 + f6 + f4 + f10) * 0.25f;
                var41_45 = (f8 + f6 + f5 + f10) * 0.25f;
                var42_47 = (f8 + f7 + f3 + f10) * 0.25f;
                var43_48 = (f9 + f7 + f2 + f10) * 0.25f;
                this.vertexBrightness[vertexTranslations.vert0] = this.getAoBrightness(n8, n5, n3, n9);
                this.vertexBrightness[vertexTranslations.vert1] = this.getAoBrightness(n7, n5, n4, n9);
                this.vertexBrightness[vertexTranslations.vert2] = this.getAoBrightness(n7, n6, n2, n9);
                this.vertexBrightness[vertexTranslations.vert3] = this.getAoBrightness(n8, n6, n, n9);
                this.vertexColorMultiplier[vertexTranslations.vert0] = f;
                this.vertexColorMultiplier[vertexTranslations.vert1] = var41_45;
                this.vertexColorMultiplier[vertexTranslations.vert2] = var42_47;
                this.vertexColorMultiplier[vertexTranslations.vert3] = var43_48;
            }
            f = iBlockDisplayReader.func_230487_a_(direction, bl);
            int n14 = 0;
            while (n14 < this.vertexColorMultiplier.length) {
                int n15 = n14++;
                this.vertexColorMultiplier[n15] = this.vertexColorMultiplier[n15] * f;
            }
        }

        private int getAoBrightness(int n, int n2, int n3, int n4) {
            if (n == 0) {
                n = n4;
            }
            if (n2 == 0) {
                n2 = n4;
            }
            if (n3 == 0) {
                n3 = n4;
            }
            return n + n2 + n3 + n4 >> 2 & 0xFF00FF;
        }

        private int getVertexBrightness(int n, int n2, int n3, int n4, float f, float f2, float f3, float f4) {
            int n5 = (int)((float)(n >> 16 & 0xFF) * f + (float)(n2 >> 16 & 0xFF) * f2 + (float)(n3 >> 16 & 0xFF) * f3 + (float)(n4 >> 16 & 0xFF) * f4) & 0xFF;
            int n6 = (int)((float)(n & 0xFF) * f + (float)(n2 & 0xFF) * f2 + (float)(n3 & 0xFF) * f3 + (float)(n4 & 0xFF) * f4) & 0xFF;
            return n5 << 16 | n6;
        }
    }

    static class Cache {
        private boolean enabled;
        private final Long2IntLinkedOpenHashMap packedLightCache = Util.make(this::lambda$new$0);
        private final Long2FloatLinkedOpenHashMap brightnessCache = Util.make(this::lambda$new$1);

        private Cache() {
        }

        public void enable() {
            this.enabled = true;
        }

        public void disable() {
            this.enabled = false;
            this.packedLightCache.clear();
            this.brightnessCache.clear();
        }

        public int getPackedLight(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            int n;
            long l = blockPos.toLong();
            if (this.enabled && (n = this.packedLightCache.get(l)) != Integer.MAX_VALUE) {
                return n;
            }
            n = WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos);
            if (this.enabled) {
                if (this.packedLightCache.size() == 100) {
                    this.packedLightCache.removeFirstInt();
                }
                this.packedLightCache.put(l, n);
            }
            return n;
        }

        public float getBrightness(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
            float f;
            long l = blockPos.toLong();
            if (this.enabled && !Float.isNaN(f = this.brightnessCache.get(l))) {
                return f;
            }
            f = blockState.getAmbientOcclusionLightValue(iBlockDisplayReader, blockPos);
            if (this.enabled) {
                if (this.brightnessCache.size() == 100) {
                    this.brightnessCache.removeFirstFloat();
                }
                this.brightnessCache.put(l, f);
            }
            return f;
        }

        private Long2FloatLinkedOpenHashMap lambda$new$1() {
            Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = new Long2FloatLinkedOpenHashMap(this, 100, 0.25f){
                final Cache this$0;
                {
                    this.this$0 = cache;
                    super(n, f);
                }

                @Override
                protected void rehash(int n) {
                }
            };
            long2FloatLinkedOpenHashMap.defaultReturnValue(Float.NaN);
            return long2FloatLinkedOpenHashMap;
        }

        private Long2IntLinkedOpenHashMap lambda$new$0() {
            Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap = new Long2IntLinkedOpenHashMap(this, 100, 0.25f){
                final Cache this$0;
                {
                    this.this$0 = cache;
                    super(n, f);
                }

                @Override
                protected void rehash(int n) {
                }
            };
            long2IntLinkedOpenHashMap.defaultReturnValue(Integer.MAX_VALUE);
            return long2IntLinkedOpenHashMap;
        }
    }

    static enum VertexTranslations {
        DOWN(0, 1, 2, 3),
        UP(2, 3, 0, 1),
        NORTH(3, 0, 1, 2),
        SOUTH(0, 1, 2, 3),
        WEST(3, 0, 1, 2),
        EAST(1, 2, 3, 0);

        private final int vert0;
        private final int vert1;
        private final int vert2;
        private final int vert3;
        private static final VertexTranslations[] VALUES;

        private VertexTranslations(int n2, int n3, int n4, int n5) {
            this.vert0 = n2;
            this.vert1 = n3;
            this.vert2 = n4;
            this.vert3 = n5;
        }

        public static VertexTranslations getVertexTranslations(Direction direction) {
            return VALUES[direction.getIndex()];
        }

        private static void lambda$static$0(VertexTranslations[] vertexTranslationsArray) {
            vertexTranslationsArray[Direction.DOWN.getIndex()] = DOWN;
            vertexTranslationsArray[Direction.UP.getIndex()] = UP;
            vertexTranslationsArray[Direction.NORTH.getIndex()] = NORTH;
            vertexTranslationsArray[Direction.SOUTH.getIndex()] = SOUTH;
            vertexTranslationsArray[Direction.WEST.getIndex()] = WEST;
            vertexTranslationsArray[Direction.EAST.getIndex()] = EAST;
        }

        static {
            VALUES = Util.make(new VertexTranslations[6], VertexTranslations::lambda$static$0);
        }
    }

    public static enum Orientation {
        DOWN(Direction.DOWN, false),
        UP(Direction.UP, false),
        NORTH(Direction.NORTH, false),
        SOUTH(Direction.SOUTH, false),
        WEST(Direction.WEST, false),
        EAST(Direction.EAST, false),
        FLIP_DOWN(Direction.DOWN, true),
        FLIP_UP(Direction.UP, true),
        FLIP_NORTH(Direction.NORTH, true),
        FLIP_SOUTH(Direction.SOUTH, true),
        FLIP_WEST(Direction.WEST, true),
        FLIP_EAST(Direction.EAST, true);

        private final int shape;

        private Orientation(Direction direction, boolean bl) {
            this.shape = direction.getIndex() + (bl ? Direction.values().length : 0);
        }
    }

    public static enum NeighborInfo {
        DOWN(new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}, 0.5f, true, new Orientation[]{Orientation.FLIP_WEST, Orientation.SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_SOUTH, Orientation.WEST, Orientation.FLIP_SOUTH, Orientation.WEST, Orientation.SOUTH}, new Orientation[]{Orientation.FLIP_WEST, Orientation.NORTH, Orientation.FLIP_WEST, Orientation.FLIP_NORTH, Orientation.WEST, Orientation.FLIP_NORTH, Orientation.WEST, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_EAST, Orientation.NORTH, Orientation.FLIP_EAST, Orientation.FLIP_NORTH, Orientation.EAST, Orientation.FLIP_NORTH, Orientation.EAST, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_EAST, Orientation.SOUTH, Orientation.FLIP_EAST, Orientation.FLIP_SOUTH, Orientation.EAST, Orientation.FLIP_SOUTH, Orientation.EAST, Orientation.SOUTH}),
        UP(new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}, 1.0f, true, new Orientation[]{Orientation.EAST, Orientation.SOUTH, Orientation.EAST, Orientation.FLIP_SOUTH, Orientation.FLIP_EAST, Orientation.FLIP_SOUTH, Orientation.FLIP_EAST, Orientation.SOUTH}, new Orientation[]{Orientation.EAST, Orientation.NORTH, Orientation.EAST, Orientation.FLIP_NORTH, Orientation.FLIP_EAST, Orientation.FLIP_NORTH, Orientation.FLIP_EAST, Orientation.NORTH}, new Orientation[]{Orientation.WEST, Orientation.NORTH, Orientation.WEST, Orientation.FLIP_NORTH, Orientation.FLIP_WEST, Orientation.FLIP_NORTH, Orientation.FLIP_WEST, Orientation.NORTH}, new Orientation[]{Orientation.WEST, Orientation.SOUTH, Orientation.WEST, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.SOUTH}),
        NORTH(new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST}, 0.8f, true, new Orientation[]{Orientation.UP, Orientation.FLIP_WEST, Orientation.UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST}, new Orientation[]{Orientation.UP, Orientation.FLIP_EAST, Orientation.UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_EAST, Orientation.DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_WEST, Orientation.DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST}),
        SOUTH(new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP}, 0.8f, true, new Orientation[]{Orientation.UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.UP, Orientation.WEST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.DOWN, Orientation.WEST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.DOWN, Orientation.EAST}, new Orientation[]{Orientation.UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.UP, Orientation.EAST}),
        WEST(new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new Orientation[]{Orientation.UP, Orientation.SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.SOUTH}, new Orientation[]{Orientation.UP, Orientation.NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.NORTH}, new Orientation[]{Orientation.DOWN, Orientation.NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.NORTH}, new Orientation[]{Orientation.DOWN, Orientation.SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.SOUTH}),
        EAST(new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new Orientation[]{Orientation.FLIP_DOWN, Orientation.SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.SOUTH}, new Orientation[]{Orientation.FLIP_DOWN, Orientation.NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_UP, Orientation.NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_UP, Orientation.SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.SOUTH});

        private final Direction[] corners;
        private final boolean doNonCubicWeight;
        private final Orientation[] vert0Weights;
        private final Orientation[] vert1Weights;
        private final Orientation[] vert2Weights;
        private final Orientation[] vert3Weights;
        private static final NeighborInfo[] VALUES;

        private NeighborInfo(Direction[] directionArray, float f, boolean bl, Orientation[] orientationArray, Orientation[] orientationArray2, Orientation[] orientationArray3, Orientation[] orientationArray4) {
            this.corners = directionArray;
            this.doNonCubicWeight = bl;
            this.vert0Weights = orientationArray;
            this.vert1Weights = orientationArray2;
            this.vert2Weights = orientationArray3;
            this.vert3Weights = orientationArray4;
        }

        public static NeighborInfo getNeighbourInfo(Direction direction) {
            return VALUES[direction.getIndex()];
        }

        private static void lambda$static$0(NeighborInfo[] neighborInfoArray) {
            neighborInfoArray[Direction.DOWN.getIndex()] = DOWN;
            neighborInfoArray[Direction.UP.getIndex()] = UP;
            neighborInfoArray[Direction.NORTH.getIndex()] = NORTH;
            neighborInfoArray[Direction.SOUTH.getIndex()] = SOUTH;
            neighborInfoArray[Direction.WEST.getIndex()] = WEST;
            neighborInfoArray[Direction.EAST.getIndex()] = EAST;
        }

        static {
            VALUES = Util.make(new NeighborInfo[6], NeighborInfo::lambda$static$0);
        }
    }
}

