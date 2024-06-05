package optifine;

import java.util.BitSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class RenderEnv {
   private boolean[] borderFlags;
   private static ThreadLocal threadLocalInstance = new ThreadLocal();
   private int blockId = -1;
   private int breakingAnimation = -1;
   private IBlockAccess blockAccess;
   private float[] quadBounds;
   private BlockPos blockPos;
   private BlockModelRenderer.AmbientOcclusionFace aoFace;
   private GameSettings gameSettings;
   private BitSet boundsFlags;
   private IBlockState blockState;
   private BlockPosM colorizerBlockPosM;
   private int metadata = -1;

   public float[] getQuadBounds() {
      return this.quadBounds;
   }

   public boolean isBreakingAnimation() {
      return this.breakingAnimation == 1;
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         if (this.blockState instanceof BlockStateBase) {
            BlockStateBase var1 = (BlockStateBase)this.blockState;
            this.metadata = var1.getMetadata();
         } else {
            this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
         }
      }

      return this.metadata;
   }

   private RenderEnv(IBlockAccess var1, IBlockState var2, BlockPos var3) {
      this.quadBounds = new float[EnumFacing.VALUES.length * 2];
      this.boundsFlags = new BitSet(3);
      this.aoFace = new BlockModelRenderer.AmbientOcclusionFace();
      this.colorizerBlockPosM = null;
      this.borderFlags = null;
      this.blockAccess = var1;
      this.blockState = var2;
      this.blockPos = var3;
      this.gameSettings = Config.getGameSettings();
   }

   public boolean isBreakingAnimation(List var1) {
      if (this.breakingAnimation < 0 && var1.size() > 0) {
         if (var1.get(0) instanceof BreakingFour) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public static RenderEnv getInstance(IBlockAccess var0, IBlockState var1, BlockPos var2) {
      RenderEnv var3 = (RenderEnv)threadLocalInstance.get();
      if (var3 == null) {
         var3 = new RenderEnv(var0, var1, var2);
         threadLocalInstance.set(var3);
         return var3;
      } else {
         var3.reset(var0, var1, var2);
         return var3;
      }
   }

   public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
      return this.aoFace;
   }

   public boolean[] getBorderFlags() {
      if (this.borderFlags == null) {
         this.borderFlags = new boolean[4];
      }

      return this.borderFlags;
   }

   public BlockPosM getColorizerBlockPosM() {
      if (this.colorizerBlockPosM == null) {
         this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
      }

      return this.colorizerBlockPosM;
   }

   public BitSet getBoundsFlags() {
      return this.boundsFlags;
   }

   private void reset(IBlockAccess var1, IBlockState var2, BlockPos var3) {
      this.blockAccess = var1;
      this.blockState = var2;
      this.blockPos = var3;
      this.blockId = -1;
      this.metadata = -1;
      this.breakingAnimation = -1;
      this.boundsFlags.clear();
   }

   public boolean isBreakingAnimation(BakedQuad var1) {
      if (this.breakingAnimation < 0) {
         if (var1 instanceof BreakingFour) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public int getBlockId() {
      if (this.blockId < 0) {
         if (this.blockState instanceof BlockStateBase) {
            BlockStateBase var1 = (BlockStateBase)this.blockState;
            this.blockId = var1.getBlockId();
         } else {
            this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
         }
      }

      return this.blockId;
   }
}
