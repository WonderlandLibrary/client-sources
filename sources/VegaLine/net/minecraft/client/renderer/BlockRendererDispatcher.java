/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ChestRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import ru.govno.client.event.EventManager;
import ru.govno.client.event.events.EventRenderBlock;

public class BlockRendererDispatcher
implements IResourceManagerReloadListener {
    private final BlockModelShapes blockModelShapes;
    private final BlockModelRenderer blockModelRenderer;
    private final ChestRenderer chestRenderer = new ChestRenderer();
    private final BlockFluidRenderer fluidRenderer;

    public BlockRendererDispatcher(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
        this.blockModelShapes = p_i46577_1_;
        this.blockModelRenderer = new BlockModelRenderer(p_i46577_2_);
        this.fluidRenderer = new BlockFluidRenderer(p_i46577_2_);
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }

    public void renderBlockDamage(IBlockState state, BlockPos pos, TextureAtlasSprite texture, IBlockAccess blockAccess) {
        if (state.getRenderType() == EnumBlockRenderType.MODEL) {
            state = state.getActualState(blockAccess, pos);
            IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
            IBakedModel ibakedmodel1 = new SimpleBakedModel.Builder(state, ibakedmodel, texture, pos).makeBakedModel();
            this.blockModelRenderer.renderModel(blockAccess, ibakedmodel1, state, pos, Tessellator.getInstance().getBuffer(), true);
        }
    }

    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder worldRendererIn) {
        EventRenderBlock event = new EventRenderBlock(state, pos, blockAccess, worldRendererIn);
        EventManager.register(event);
        try {
            EnumBlockRenderType enumblockrendertype = state.getRenderType();
            if (enumblockrendertype == EnumBlockRenderType.INVISIBLE) {
                return false;
            }
            if (blockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
                try {
                    state = state.getActualState(blockAccess, pos);
                } catch (Exception exception) {
                    // empty catch block
                }
            }
            switch (enumblockrendertype) {
                case MODEL: {
                    return this.blockModelRenderer.renderModel(blockAccess, this.getModelForState(state), state, pos, worldRendererIn, true);
                }
                case ENTITYBLOCK_ANIMATED: {
                    return false;
                }
                case LIQUID: {
                    return this.fluidRenderer.renderFluid(blockAccess, state, pos, worldRendererIn);
                }
            }
            return false;
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
            throw new ReportedException(crashreport);
        }
    }

    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }

    public IBakedModel getModelForState(IBlockState state) {
        return this.blockModelShapes.getModelForState(state);
    }

    public void renderBlockBrightness(IBlockState state, float brightness) {
        EnumBlockRenderType enumblockrendertype = state.getRenderType();
        if (enumblockrendertype != EnumBlockRenderType.INVISIBLE) {
            switch (enumblockrendertype) {
                case MODEL: {
                    IBakedModel ibakedmodel = this.getModelForState(state);
                    this.blockModelRenderer.renderModelBrightness(ibakedmodel, state, brightness, true);
                    break;
                }
                case ENTITYBLOCK_ANIMATED: {
                    this.chestRenderer.renderChestBrightness(state.getBlock(), brightness);
                }
            }
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }
}

