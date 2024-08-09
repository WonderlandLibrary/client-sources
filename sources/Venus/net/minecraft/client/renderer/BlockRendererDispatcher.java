/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.FluidBlockRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;
import net.optifine.reflect.Reflector;

public class BlockRendererDispatcher
implements IResourceManagerReloadListener {
    private final BlockModelShapes blockModelShapes;
    private final BlockModelRenderer blockModelRenderer;
    private final FluidBlockRenderer fluidRenderer;
    private final Random random = new Random();
    private final BlockColors blockColors;

    public BlockRendererDispatcher(BlockModelShapes blockModelShapes, BlockColors blockColors) {
        this.blockModelShapes = blockModelShapes;
        this.blockColors = blockColors;
        this.blockModelRenderer = Reflector.ForgeBlockModelRenderer_Constructor.exists() ? (BlockModelRenderer)Reflector.newInstance(Reflector.ForgeBlockModelRenderer_Constructor, this.blockColors) : new BlockModelRenderer(this.blockColors);
        this.fluidRenderer = new FluidBlockRenderer();
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }

    public void renderBlockDamage(BlockState blockState, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader, MatrixStack matrixStack, IVertexBuilder iVertexBuilder) {
        this.renderBlockDamage(blockState, blockPos, iBlockDisplayReader, matrixStack, iVertexBuilder, EmptyModelData.INSTANCE);
    }

    public void renderBlockDamage(BlockState blockState, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, IModelData iModelData) {
        if (blockState.getRenderType() == BlockRenderType.MODEL) {
            IBakedModel iBakedModel = this.blockModelShapes.getModel(blockState);
            long l = blockState.getPositionRandom(blockPos);
            this.blockModelRenderer.renderModel(iBlockDisplayReader, iBakedModel, blockState, blockPos, matrixStack, iVertexBuilder, true, this.random, l, OverlayTexture.NO_OVERLAY, iModelData);
        }
    }

    public boolean renderModel(BlockState blockState, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2) {
        return this.renderModel(blockState, blockPos, iBlockDisplayReader, matrixStack, iVertexBuilder, bl, random2, EmptyModelData.INSTANCE);
    }

    public boolean renderModel(BlockState blockState, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, boolean bl, Random random2, IModelData iModelData) {
        try {
            BlockRenderType blockRenderType = blockState.getRenderType();
            return blockRenderType != BlockRenderType.MODEL ? false : this.blockModelRenderer.renderModel(iBlockDisplayReader, this.getModelForState(blockState), blockState, blockPos, matrixStack, iVertexBuilder, bl, random2, blockState.getPositionRandom(blockPos), OverlayTexture.NO_OVERLAY, iModelData);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashReportCategory, blockPos, blockState);
            throw new ReportedException(crashReport);
        }
    }

    public boolean renderFluid(BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader, IVertexBuilder iVertexBuilder, FluidState fluidState) {
        try {
            return this.fluidRenderer.render(iBlockDisplayReader, blockPos, iVertexBuilder, fluidState);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Tesselating liquid in world");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashReportCategory, blockPos, null);
            throw new ReportedException(crashReport);
        }
    }

    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }

    public IBakedModel getModelForState(BlockState blockState) {
        return this.blockModelShapes.getModel(blockState);
    }

    public void renderBlock(BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, n2, EmptyModelData.INSTANCE);
    }

    public void renderBlock(BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2, IModelData iModelData) {
        BlockRenderType blockRenderType = blockState.getRenderType();
        if (blockRenderType != BlockRenderType.INVISIBLE) {
            switch (1.$SwitchMap$net$minecraft$block$BlockRenderType[blockRenderType.ordinal()]) {
                case 1: {
                    IBakedModel iBakedModel = this.getModelForState(blockState);
                    int n3 = this.blockColors.getColor(blockState, null, null, 0);
                    float f = (float)(n3 >> 16 & 0xFF) / 255.0f;
                    float f2 = (float)(n3 >> 8 & 0xFF) / 255.0f;
                    float f3 = (float)(n3 & 0xFF) / 255.0f;
                    this.blockModelRenderer.renderModel(matrixStack.getLast(), iRenderTypeBuffer.getBuffer(RenderTypeLookup.func_239220_a_(blockState, false)), blockState, iBakedModel, f, f2, f3, n, n2, iModelData);
                    break;
                }
                case 2: {
                    if (Reflector.IForgeItem_getItemStackTileEntityRenderer.exists()) {
                        ItemStack itemStack = new ItemStack(blockState.getBlock());
                        ItemStackTileEntityRenderer itemStackTileEntityRenderer = (ItemStackTileEntityRenderer)Reflector.call(itemStack.getItem(), Reflector.IForgeItem_getItemStackTileEntityRenderer, new Object[0]);
                        itemStackTileEntityRenderer.func_239207_a_(itemStack, ItemCameraTransforms.TransformType.NONE, matrixStack, iRenderTypeBuffer, n, n2);
                        break;
                    }
                    ItemStackTileEntityRenderer.instance.func_239207_a_(new ItemStack(blockState.getBlock()), ItemCameraTransforms.TransformType.NONE, matrixStack, iRenderTypeBuffer, n, n2);
                }
            }
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }

    public IResourceType getResourceType() {
        return VanillaResourceType.MODELS;
    }
}

