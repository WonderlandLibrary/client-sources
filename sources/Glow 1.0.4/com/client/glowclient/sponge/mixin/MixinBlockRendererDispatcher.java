package com.client.glowclient.sponge.mixin;

import net.minecraft.client.resources.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.init.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ BlockRendererDispatcher.class })
public abstract class MixinBlockRendererDispatcher implements IResourceManagerReloadListener
{
    @Shadow
    private BlockModelShapes field_175028_a;
    @Shadow
    private BlockModelRenderer field_175027_c;
    @Shadow
    private ChestRenderer field_175024_d;
    @Shadow
    private BlockFluidRenderer field_175025_e;
    
    public MixinBlockRendererDispatcher() {
        super();
        this.chestRenderer = new ChestRenderer();
    }
    
    @Shadow
    public IBakedModel getModelForState(final IBlockState blockState) {
        return this.blockModelShapes.getModelForState(blockState);
    }
    
    @Overwrite
    public boolean renderBlock(IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final BufferBuilder bufferBuilder) {
        try {
            final EnumBlockRenderType renderType = blockState.getRenderType();
            if (renderType == EnumBlockRenderType.INVISIBLE) {
                return false;
            }
            if (blockAccess.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
                try {
                    blockState = blockState.getActualState(blockAccess, blockPos);
                }
                catch (Exception ex) {}
            }
            switch (renderType) {
                case MODEL: {
                    final IBakedModel modelForState = this.getModelForState(blockState);
                    blockState = blockState.getBlock().getExtendedState(blockState, blockAccess, blockPos);
                    HookTranslator.m65(blockPos, blockState, blockAccess, bufferBuilder);
                    if (!HookTranslator.v21) {
                        return this.blockModelRenderer.renderModel(blockAccess, modelForState, blockState, blockPos, bufferBuilder, true);
                    }
                    if (blockState.getBlock() == Blocks.DIAMOND_ORE || blockState.getBlock() == Blocks.IRON_ORE || blockState.getBlock() == Blocks.GOLD_ORE || blockState.getBlock() == Blocks.EMERALD_ORE || blockState.getBlock() == Blocks.LAPIS_ORE || blockState.getBlock() == Blocks.REDSTONE_ORE) {
                        return this.blockModelRenderer.renderModel(blockAccess, modelForState, blockState, blockPos, bufferBuilder, false);
                    }
                    return false;
                }
                case ENTITYBLOCK_ANIMATED: {
                    return false;
                }
                case LIQUID: {
                    return this.fluidRenderer.renderFluid(blockAccess, blockState, blockPos, bufferBuilder);
                }
                default: {
                    return false;
                }
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, "Tesselating block in world");
            CrashReportCategory.addBlockInfo(crashReport.makeCategory("Block being tesselated"), blockPos, blockState.getBlock(), blockState.getBlock().getMetaFromState(blockState));
            throw new ReportedException(crashReport);
        }
    }
}
