package optfine;

import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class RenderEnv
{
    private BlockModelRenderer.AmbientOcclusionFace aoFace;
    private static ThreadLocal threadLocalInstance;
    private boolean[] borderFlags;
    private IBlockState blockState;
    private float[] quadBounds;
    private IBlockAccess blockAccess;
    private BlockPos blockPos;
    private GameSettings gameSettings;
    private BlockPosM colorizerBlockPos;
    private BitSet boundsFlags;
    private int blockId;
    private int breakingAnimation;
    private int metadata;
    
    public boolean isBreakingAnimation(final List list) {
        if (this.breakingAnimation < 0 && list.size() > 0) {
            if (list.get("".length()) instanceof BreakingFour) {
                this.breakingAnimation = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                this.breakingAnimation = "".length();
            }
        }
        if (this.breakingAnimation == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public float[] getQuadBounds() {
        return this.quadBounds;
    }
    
    public boolean isBreakingAnimation() {
        if (this.breakingAnimation == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean[] getBorderFlags() {
        if (this.borderFlags == null) {
            this.borderFlags = new boolean[0x5E ^ 0x5A];
        }
        return this.borderFlags;
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
        }
        return this.metadata;
    }
    
    public boolean isBreakingAnimation(final BakedQuad bakedQuad) {
        if (this.breakingAnimation < 0) {
            if (bakedQuad instanceof BreakingFour) {
                this.breakingAnimation = " ".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                this.breakingAnimation = "".length();
            }
        }
        if (this.breakingAnimation == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
        return this.aoFace;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPosM getColorizerBlockPos() {
        if (this.colorizerBlockPos == null) {
            this.colorizerBlockPos = new BlockPosM("".length(), "".length(), "".length());
        }
        return this.colorizerBlockPos;
    }
    
    public BitSet getBoundsFlags() {
        return this.boundsFlags;
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    static {
        RenderEnv.threadLocalInstance = new ThreadLocal();
    }
    
    private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockId = -" ".length();
        this.metadata = -" ".length();
        this.breakingAnimation = -" ".length();
        this.quadBounds = new float[EnumFacing.VALUES.length * "  ".length()];
        this.boundsFlags = new BitSet("   ".length());
        this.aoFace = new BlockModelRenderer.AmbientOcclusionFace();
        this.colorizerBlockPos = null;
        this.borderFlags = null;
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.gameSettings = Config.getGameSettings();
    }
    
    public static RenderEnv getInstance(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        final RenderEnv renderEnv = RenderEnv.threadLocalInstance.get();
        if (renderEnv == null) {
            final RenderEnv renderEnv2 = new RenderEnv(blockAccess, blockState, blockPos);
            RenderEnv.threadLocalInstance.set(renderEnv2);
            return renderEnv2;
        }
        renderEnv.reset(blockAccess, blockState, blockPos);
        return renderEnv;
    }
    
    private void reset(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.blockId = -" ".length();
        this.metadata = -" ".length();
        this.breakingAnimation = -" ".length();
        this.boundsFlags.clear();
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
        }
        return this.blockId;
    }
}
