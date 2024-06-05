package net.optifine;

import java.util.BitSet;
import java.util.List;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.MatchBlock;

public class RenderEnv {
   private IBlockAccess blockAccess;
   private IBlockState blockState;
   private BlockPos blockPos;
   private GameSettings gameSettings;
   private int blockId = -1;
   private int metadata = -1;
   private int breakingAnimation = -1;
   private float[] quadBounds = new float[EnumFacing.VALUES.length * 2];
   private BitSet boundsFlags = new BitSet(3);
   private BlockModelRenderer$AmbientOcclusionFace aoFace = new BlockModelRenderer$AmbientOcclusionFace();
   private BlockPosM colorizerBlockPosM = null;
   private boolean[] borderFlags = null;
   private static ThreadLocal threadLocalInstance = new ThreadLocal();

   private RenderEnv(IBlockAccess var1, IBlockState var2, BlockPos var3) {
      this.blockAccess = var1;
      this.blockState = var2;
      this.blockPos = var3;
      this.gameSettings = Config.getGameSettings();
   }

   public static RenderEnv getInstance(IBlockAccess var0, IBlockState var1, BlockPos var2) {
      MatchBlock.b();
      RenderEnv var4 = (RenderEnv)threadLocalInstance.get();
      if(var4 == null) {
         var4 = new RenderEnv(var0, var1, var2);
         threadLocalInstance.set(var4);
         return var4;
      } else {
         var4.reset(var0, var1, var2);
         return var4;
      }
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

   public int getBlockId() {
      acE[] var1 = MatchBlock.b();
      if(this.blockId < 0) {
         if(this.blockState instanceof BlockStateBase) {
            BlockStateBase var2 = (BlockStateBase)this.blockState;
            this.blockId = var2.getBlockId();
         }

         this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
      }

      return this.blockId;
   }

   public int getMetadata() {
      acE[] var1 = MatchBlock.b();
      if(this.metadata < 0) {
         if(this.blockState instanceof BlockStateBase) {
            BlockStateBase var2 = (BlockStateBase)this.blockState;
            this.metadata = var2.getMetadata();
         }

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

   public BlockModelRenderer$AmbientOcclusionFace getAoFace() {
      return this.aoFace;
   }

   public boolean isBreakingAnimation(List var1) {
      acE[] var2 = MatchBlock.b();
      if(this.breakingAnimation < 0 && var1.size() > 0) {
         if(var1.get(0) instanceof BreakingFour) {
            this.breakingAnimation = 1;
         }

         this.breakingAnimation = 0;
      }

      return this.breakingAnimation == 1;
   }

   public boolean a(BakedQuad var1) {
      acE[] var2 = MatchBlock.b();
      if(this.breakingAnimation < 0) {
         if(var1 instanceof BreakingFour) {
            this.breakingAnimation = 1;
         }

         this.breakingAnimation = 0;
      }

      return this.breakingAnimation == 1;
   }

   public boolean g() {
      acE[] var1 = MatchBlock.b();
      return this.breakingAnimation == 1;
   }

   public IBlockState getBlockState() {
      return this.blockState;
   }

   public BlockPosM getColorizerBlockPosM() {
      acE[] var1 = MatchBlock.b();
      if(this.colorizerBlockPosM == null) {
         this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
      }

      return this.colorizerBlockPosM;
   }

   public boolean[] h() {
      acE[] var1 = MatchBlock.b();
      if(this.borderFlags == null) {
         this.borderFlags = new boolean[4];
      }

      return this.borderFlags;
   }
}
