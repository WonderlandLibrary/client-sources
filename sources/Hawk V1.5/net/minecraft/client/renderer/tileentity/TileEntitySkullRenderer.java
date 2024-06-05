package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer {
   private static final ResourceLocation field_147532_f = new ResourceLocation("textures/entity/creeper/creeper.png");
   public static TileEntitySkullRenderer instance;
   private static final ResourceLocation field_147537_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private final ModelSkeletonHead field_178468_i = new ModelHumanoidHead();
   private final ModelSkeletonHead field_178467_h = new ModelSkeletonHead(0, 0, 64, 32);
   private static final ResourceLocation field_147534_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
   private static final ResourceLocation field_147535_e = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final String __OBFID = "CL_00000971";

   public void func_180542_a(TileEntitySkull var1, double var2, double var4, double var6, float var8, int var9) {
      EnumFacing var10 = EnumFacing.getFront(var1.getBlockMetadata() & 7);
      this.renderSkull((float)var2, (float)var4, (float)var6, var10, (float)(var1.getSkullRotation() * 360) / 16.0F, var1.getSkullType(), var1.getPlayerProfile(), var9);
   }

   public void setRendererDispatcher(TileEntityRendererDispatcher var1) {
      super.setRendererDispatcher(var1);
      instance = this;
   }

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_180542_a((TileEntitySkull)var1, var2, var4, var6, var8, var9);
   }

   public void renderSkull(float var1, float var2, float var3, EnumFacing var4, float var5, int var6, GameProfile var7, int var8) {
      ModelSkeletonHead var9 = this.field_178467_h;
      if (var8 >= 0) {
         this.bindTexture(DESTROY_STAGES[var8]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 2.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         switch(var6) {
         case 0:
         default:
            this.bindTexture(field_147537_c);
            break;
         case 1:
            this.bindTexture(field_147534_d);
            break;
         case 2:
            this.bindTexture(field_147535_e);
            var9 = this.field_178468_i;
            break;
         case 3:
            var9 = this.field_178468_i;
            ResourceLocation var10 = DefaultPlayerSkin.func_177335_a();
            if (var7 != null) {
               Minecraft var11 = Minecraft.getMinecraft();
               Map var12 = var11.getSkinManager().loadSkinFromCache(var7);
               if (var12.containsKey(Type.SKIN)) {
                  var10 = var11.getSkinManager().loadSkin((MinecraftProfileTexture)var12.get(Type.SKIN), Type.SKIN);
               } else {
                  UUID var13 = EntityPlayer.getUUID(var7);
                  var10 = DefaultPlayerSkin.func_177334_a(var13);
               }
            }

            this.bindTexture(var10);
            break;
         case 4:
            this.bindTexture(field_147532_f);
         }
      }

      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      if (var4 != EnumFacing.UP) {
         switch(var4) {
         case NORTH:
            GlStateManager.translate(var1 + 0.5F, var2 + 0.25F, var3 + 0.74F);
            break;
         case SOUTH:
            GlStateManager.translate(var1 + 0.5F, var2 + 0.25F, var3 + 0.26F);
            var5 = 180.0F;
            break;
         case WEST:
            GlStateManager.translate(var1 + 0.74F, var2 + 0.25F, var3 + 0.5F);
            var5 = 270.0F;
            break;
         case EAST:
         default:
            GlStateManager.translate(var1 + 0.26F, var2 + 0.25F, var3 + 0.5F);
            var5 = 90.0F;
         }
      } else {
         GlStateManager.translate(var1 + 0.5F, var2, var3 + 0.5F);
      }

      float var14 = 0.0625F;
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      var9.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var14);
      GlStateManager.popMatrix();
      if (var8 >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

   }

   static final class SwitchEnumFacing {
      static final int[] field_178458_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002468";

      static {
         try {
            field_178458_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178458_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178458_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178458_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
