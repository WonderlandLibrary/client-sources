package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
   private final ModelSign model = new ModelSign();

   public void func_180541_a(TileEntitySign var1, double var2, double var4, double var6, float var8, int var9) {
      Block var10 = var1.getBlockType();
      GlStateManager.pushMatrix();
      float var11 = 0.6666667F;
      float var12;
      if (var10 == Blocks.standing_sign) {
         GlStateManager.translate((float)var2 + 0.5F, (float)var4 + 0.75F * var11, (float)var6 + 0.5F);
         float var13 = (float)(var1.getBlockMetadata() * 360) / 16.0F;
         GlStateManager.rotate(-var13, 0.0F, 1.0F, 0.0F);
         this.model.signStick.showModel = true;
      } else {
         int var19 = var1.getBlockMetadata();
         var12 = 0.0F;
         if (var19 == 2) {
            var12 = 180.0F;
         }

         if (var19 == 4) {
            var12 = 90.0F;
         }

         if (var19 == 5) {
            var12 = -90.0F;
         }

         GlStateManager.translate((float)var2 + 0.5F, (float)var4 + 0.75F * var11, (float)var6 + 0.5F);
         GlStateManager.rotate(-var12, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
         this.model.signStick.showModel = false;
      }

      if (var9 >= 0) {
         this.bindTexture(DESTROY_STAGES[var9]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 2.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(field_147513_b);
      }

      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();
      GlStateManager.scale(var11, -var11, -var11);
      this.model.renderSign();
      GlStateManager.popMatrix();
      FontRenderer var20 = this.getFontRenderer();
      var12 = 0.015625F * var11;
      GlStateManager.translate(0.0F, 0.5F * var11, 0.07F * var11);
      GlStateManager.scale(var12, -var12, var12);
      GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
      GlStateManager.depthMask(false);
      int var14 = 0;
      if (Config.isCustomColors()) {
         var14 = CustomColors.getSignTextColor(var14);
      }

      if (var9 < 0) {
         for(int var15 = 0; var15 < var1.signText.length; ++var15) {
            if (var1.signText[var15] != null) {
               IChatComponent var16 = var1.signText[var15];
               List var17 = GuiUtilRenderComponents.func_178908_a(var16, 90, var20, false, true);
               String var18 = var17 != null && var17.size() > 0 ? ((IChatComponent)var17.get(0)).getFormattedText() : "";
               if (var15 == var1.lineBeingEdited) {
                  var18 = String.valueOf((new StringBuilder("> ")).append(var18).append(" <"));
                  var20.drawString(var18, (double)(-var20.getStringWidth(var18) / 2), (double)(var15 * 10 - var1.signText.length * 5), var14);
               } else {
                  var20.drawString(var18, (double)(-var20.getStringWidth(var18) / 2), (double)(var15 * 10 - var1.signText.length * 5), var14);
               }
            }
         }
      }

      GlStateManager.depthMask(true);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      if (var9 >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_180541_a((TileEntitySign)var1, var2, var4, var6, var8, var9);
   }
}
