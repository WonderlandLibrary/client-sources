// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.src;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.client.renderer.BlockModelRenderer;
import java.util.BitSet;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;

public class RenderEnv
{
    private IBlockAccess blockAccess;
    private IBlockState blockState;
    private BlockPos blockPos;
    private GameSettings gameSettings;
    private int blockId;
    private int metadata;
    private int breakingAnimation;
    private float[] quadBounds;
    private BitSet boundsFlags;
    private BlockModelRenderer.AmbientOcclusionFace aoFace;
    private BlockPosM colorizerBlockPos;
    private boolean[] borderFlags;
    private static ThreadLocal threadLocalInstance;
    
    static {
        RenderEnv.threadLocalInstance = new ThreadLocal();
    }
    
    private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.quadBounds = new float[EnumFacing.VALUES.length * 2];
        this.boundsFlags = new BitSet(3);
        this.aoFace = new BlockModelRenderer.AmbientOcclusionFace();
        this.colorizerBlockPos = null;
        this.borderFlags = null;
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.gameSettings = Config.getGameSettings();
    }
    
    public static RenderEnv getInstance(final IBlockAccess blockAccessIn, final IBlockState blockStateIn, final BlockPos blockPosIn) {
        RenderEnv re = RenderEnv.threadLocalInstance.get();
        if (re == null) {
            re = new RenderEnv(blockAccessIn, blockStateIn, blockPosIn);
            RenderEnv.threadLocalInstance.set(re);
            return re;
        }
        re.reset(blockAccessIn, blockStateIn, blockPosIn);
        return re;
    }
    
    private void reset(final IBlockAccess blockAccessIn, final IBlockState blockStateIn, final BlockPos blockPosIn) {
        this.blockAccess = blockAccessIn;
        this.blockState = blockStateIn;
        this.blockPos = blockPosIn;
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.boundsFlags.clear();
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
        }
        return this.blockId;
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
        }
        return this.metadata;
    }
    
    public float[] getQuadBounds() {
        return this.quadBounds;
    }
    
    public BitSet getBoundsFlags() {
        return this.boundsFlags;
    }
    
    public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
        return this.aoFace;
    }
    
    public boolean isBreakingAnimation(final List listQuads) {
        if (this.breakingAnimation < 0 && listQuads.size() > 0) {
            if (listQuads.get(0) instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public boolean isBreakingAnimation(final BakedQuad quad) {
        if (this.breakingAnimation < 0) {
            if (quad instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public boolean isBreakingAnimation() {
        return this.breakingAnimation == 1;
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    public BlockPosM getColorizerBlockPos() {
        if (this.colorizerBlockPos == null) {
            this.colorizerBlockPos = new BlockPosM(0, 0, 0);
        }
        return this.colorizerBlockPos;
    }
    
    public boolean[] getBorderFlags() {
        if (this.borderFlags == null) {
            this.borderFlags = new boolean[4];
        }
        return this.borderFlags;
    }
}
