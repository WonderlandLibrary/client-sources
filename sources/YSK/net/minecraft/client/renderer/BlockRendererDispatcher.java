package net.minecraft.client.renderer;

import net.minecraft.client.settings.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private final BlockFluidRenderer fluidRenderer;
    private final GameSettings gameSettings;
    private static final String[] I;
    private BlockModelShapes blockModelShapes;
    private final ChestRenderer chestRenderer;
    private final BlockModelRenderer blockModelRenderer;
    
    public void renderBlockDamage(IBlockState actualState, final BlockPos blockPos, final TextureAtlasSprite textureAtlasSprite, final IBlockAccess blockAccess) {
        final Block block = actualState.getBlock();
        if (block.getRenderType() == "   ".length()) {
            actualState = block.getActualState(actualState, blockAccess, blockPos);
            this.blockModelRenderer.renderModel(blockAccess, new SimpleBakedModel.Builder(this.blockModelShapes.getModelForState(actualState), textureAtlasSprite).makeBakedModel(), actualState, blockPos, Tessellator.getInstance().getWorldRenderer());
        }
    }
    
    public IBakedModel getModelFromBlockState(IBlockState actualState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = actualState.getBlock();
        if (blockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
            try {
                actualState = block.getActualState(actualState, blockAccess, blockPos);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            catch (Exception ex) {}
        }
        IBakedModel bakedModel = this.blockModelShapes.getModelForState(actualState);
        if (blockPos != null && this.gameSettings.allowBlockAlternatives && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
        }
        return bakedModel;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean renderBlock(final IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final WorldRenderer worldRenderer) {
        try {
            final int renderType = blockState.getBlock().getRenderType();
            if (renderType == -" ".length()) {
                return "".length() != 0;
            }
            switch (renderType) {
                case 1: {
                    return this.fluidRenderer.renderFluid(blockAccess, blockState, blockPos, worldRenderer);
                }
                case 2: {
                    return "".length() != 0;
                }
                case 3: {
                    return this.blockModelRenderer.renderModel(blockAccess, this.getModelFromBlockState(blockState, blockAccess, blockPos), blockState, blockPos, worldRenderer);
                }
                default: {
                    return "".length() != 0;
                }
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, BlockRendererDispatcher.I["".length()]);
            CrashReportCategory.addBlockInfo(crashReport.makeCategory(BlockRendererDispatcher.I[" ".length()]), blockPos, blockState.getBlock(), blockState.getBlock().getMetaFromState(blockState));
            throw new ReportedException(crashReport);
        }
    }
    
    public void renderBlockBrightness(final IBlockState blockState, final float n) {
        final int renderType = blockState.getBlock().getRenderType();
        if (renderType != -" ".length()) {
            switch (renderType) {
                default: {
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.chestRenderer.renderChestBrightness(blockState.getBlock(), n);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    this.blockModelRenderer.renderModelBrightness(this.getBakedModel(blockState, null), blockState, n, " ".length() != 0);
                    break;
                }
            }
        }
    }
    
    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }
    
    public BlockRendererDispatcher(final BlockModelShapes blockModelShapes, final GameSettings gameSettings) {
        this.blockModelRenderer = new BlockModelRenderer();
        this.chestRenderer = new ChestRenderer();
        this.fluidRenderer = new BlockFluidRenderer();
        this.blockModelShapes = blockModelShapes;
        this.gameSettings = gameSettings;
    }
    
    static {
        I();
    }
    
    public boolean isRenderTypeChest(final Block block, final int n) {
        if (block == null) {
            return "".length() != 0;
        }
        final int renderType = block.getRenderType();
        int n2;
        if (renderType == "   ".length()) {
            n2 = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (renderType == "  ".length()) {
            n2 = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("'\n\u0015)7\u001f\u000e\u00123<\u0014O\u00046=\u0010\u0004F3<S\u0018\t(>\u0017", "sofZR");
        BlockRendererDispatcher.I[" ".length()] = I("3\u0003 \b\tQ\r*\u0002\f\u0016O;\u000e\u0011\u0002\n#\n\u0016\u0014\u000b", "qoOkb");
    }
    
    private IBakedModel getBakedModel(final IBlockState blockState, final BlockPos blockPos) {
        IBakedModel bakedModel = this.blockModelShapes.getModelForState(blockState);
        if (blockPos != null && this.gameSettings.allowBlockAlternatives && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
        }
        return bakedModel;
    }
    
    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }
}
