package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer$1;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation g = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation f = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
   private static final ResourceLocation h = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final ResourceLocation d = new ResourceLocation("textures/entity/creeper/creeper.png");
   public static TileEntitySkullRenderer instance;
   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
   private final ModelSkeletonHead c = new ModelHumanoidHead();

   public void renderTileEntityAt(TileEntitySkull var1, double var2, double var4, double var6, float var8, int var9) {
      EnumFacing var10 = EnumFacing.getFront(var1.getBlockMetadata() & 7);
      this.renderSkull((float)var2, (float)var4, (float)var6, var10, (float)(var1.getSkullRotation() * 360) / 16.0F, var1.getSkullType(), var1.getPlayerProfile(), var9);
   }

   public void setRendererDispatcher(TileEntityRendererDispatcher var1) {
      super.setRendererDispatcher(var1);
      instance = this;
   }

   public void renderSkull(float var1, float var2, float var3, EnumFacing var4, float var5, int var6, GameProfile var7, int var8) {
      ModelSkeletonHead var9 = this.skeletonHead;
      this.bindTexture(DESTROY_STAGES[var8]);
      GlStateManager.matrixMode(5890);
      GlStateManager.pushMatrix();
      GlStateManager.scale(4.0F, 2.0F, 1.0F);
      GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      if(var4 != EnumFacing.UP) {
         switch(TileEntitySkullRenderer$1.$SwitchMap$net$minecraft$util$EnumFacing[var4.ordinal()]) {
         case 1:
            GlStateManager.translate(var1 + 0.5F, var2 + 0.25F, var3 + 0.74F);
            break;
         case 2:
            GlStateManager.translate(var1 + 0.5F, var2 + 0.25F, var3 + 0.26F);
            var5 = 180.0F;
            break;
         case 3:
            GlStateManager.translate(var1 + 0.74F, var2 + 0.25F, var3 + 0.5F);
            var5 = 270.0F;
            break;
         case 4:
         default:
            GlStateManager.translate(var1 + 0.26F, var2 + 0.25F, var3 + 0.5F);
            var5 = 90.0F;
         }
      } else {
         GlStateManager.translate(var1 + 0.5F, var2, var3 + 0.5F);
      }

      float var10 = 0.0625F;
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      var9.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var10);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5890);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
   }
}
