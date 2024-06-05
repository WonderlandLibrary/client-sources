package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.ChestRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel$Builder;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.optifine.Reflector;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private BlockModelShapes blockModelShapes;
   private final GameSettings gameSettings;
   private final BlockModelRenderer blockModelRenderer = new BlockModelRenderer();
   private final ChestRenderer chestRenderer = new ChestRenderer();
   private final BlockFluidRenderer fluidRenderer = new BlockFluidRenderer();
   private static final String b = "CL_00002520";

   public BlockRendererDispatcher(BlockModelShapes var1, GameSettings var2) {
      this.blockModelShapes = var1;
      this.gameSettings = var2;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.blockModelShapes;
   }

   public void renderBlockDamage(IBlockState var1, BlockPos var2, TextureAtlasSprite var3, IBlockAccess var4) {
      Block var5 = var1.getBlock();
      int var6 = var5.getRenderType();
      if(var6 == 3) {
         var1 = var5.getActualState(var1, var4, var2);
         IBakedModel var7 = this.blockModelShapes.getModelForState(var1);
         if(Reflector.ISmartBlockModel.isInstance(var7)) {
            IBlockState var16 = (IBlockState)Reflector.f(var5, Reflector.s, new Object[]{var1, var4, var2});

            for(EnumWorldBlockLayer var12 : EnumWorldBlockLayer.values()) {
               if(Reflector.d(var5, Reflector.cU, new Object[]{var12})) {
                  Reflector.a(Reflector.ck, new Object[]{var12});
                  IBakedModel var13 = (IBakedModel)Reflector.f(var7, Reflector.o, new Object[]{var16});
                  IBakedModel var14 = (new SimpleBakedModel$Builder(var13, var3)).makeBakedModel();
                  this.blockModelRenderer.renderModel(var4, var14, var1, var2, Tessellator.getInstance().getWorldRenderer());
               }
            }

            return;
         }

         IBakedModel var8 = (new SimpleBakedModel$Builder(var7, var3)).makeBakedModel();
         this.blockModelRenderer.renderModel(var4, var8, var1, var2, Tessellator.getInstance().getWorldRenderer());
      }

   }

   public boolean renderBlock(IBlockState param1, BlockPos param2, IBlockAccess param3, WorldRenderer param4) {
      // $FF: Couldn't be decompiled
   }

   public BlockModelRenderer getBlockModelRenderer() {
      return this.blockModelRenderer;
   }

   private IBakedModel getBakedModel(IBlockState var1, BlockPos var2) {
      IBakedModel var3 = this.blockModelShapes.getModelForState(var1);
      if(this.gameSettings.allowBlockAlternatives && var3 instanceof WeightedBakedModel) {
         var3 = ((WeightedBakedModel)var3).getAlternativeModel(MathHelper.getPositionRandom(var2));
      }

      return var3;
   }

   public IBakedModel getModelFromBlockState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var1.getBlock();
      if(var2.getWorldType() != WorldType.DEBUG_WORLD) {
         Block var10000 = var4;
         IBlockState var10001 = var1;
         IBlockAccess var10002 = var2;
         BlockPos var10003 = var3;

         try {
            var1 = var10000.getActualState(var10001, var10002, var10003);
         } catch (Exception var7) {
            ;
         }
      }

      IBakedModel var5 = this.blockModelShapes.getModelForState(var1);
      if(this.gameSettings.allowBlockAlternatives && var5 instanceof WeightedBakedModel) {
         var5 = ((WeightedBakedModel)var5).getAlternativeModel(MathHelper.getPositionRandom(var3));
      }

      if(Reflector.ISmartBlockModel.isInstance(var5)) {
         IBlockState var6 = (IBlockState)Reflector.f(var4, Reflector.s, new Object[]{var1, var2, var3});
         var5 = (IBakedModel)Reflector.f(var5, Reflector.o, new Object[]{var6});
      }

      return var5;
   }

   public void renderBlockBrightness(IBlockState var1, float var2) {
      int var3 = var1.getBlock().getRenderType();
      if(var3 != -1) {
         switch(var3) {
         case 1:
         default:
            break;
         case 2:
            this.chestRenderer.renderChestBrightness(var1.getBlock(), var2);
            break;
         case 3:
            IBakedModel var4 = this.getBakedModel(var1, (BlockPos)null);
            this.blockModelRenderer.renderModelBrightness(var4, var1, var2, true);
         }
      }

   }

   public boolean a(Block var1, int var2) {
      return false;
   }

   public void onResourceManagerReload(IResourceManager var1) {
      this.fluidRenderer.initAtlasSprites();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
