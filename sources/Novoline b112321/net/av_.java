package net;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.Ls;
import net.UW;
import net.VN;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aEu;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avP;
import net.ava;
import net.axu;
import net.dI;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class av_ extends as0 {
   @VN("names")
   private final aEu C = axu.g();
   @VN("needed-only")
   private final aEu y = axu.g();
   private final IntBuffer B = GLAllocation.createDirectIntBuffer(16);
   private final FloatBuffer x = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer z = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer A = GLAllocation.createDirectFloatBuffer(4);

   public av_(UW var1) {
      super(var1, "ItemESP", "Item ESP", 0, a2V.VISUALS, "");
      ae9.a(new adZ("NAMES", "Names", a6d.CHECKBOX, this, this.C));
      ae9.a(new adZ("NEEDED_ONLY", "Needed Only", a6d.CHECKBOX, this, this.y));
   }

   @agu
   public void a(aSt var1) {
      ava.h();
      Iterator var3 = this.w.theWorld.getLoadedEntityList().iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(var4 instanceof EntityItem) {
            IBakedModel var5 = this.w.a().b().getItemModel(((EntityItem)var4).i());
            float var6 = MathHelper.sin(((float)((EntityItem)var4).getAge() + var1.b()) / 10.0F + ((EntityItem)var4).hoverStart) * 0.1F + 0.1F;
            float var7 = var5.getItemCameraTransforms().getTransform(ItemCameraTransforms$TransformType.GROUND).scale.y;
            double var8 = a6_.a(var4.posX, var4.lastTickPosX, (double)var1.b());
            double var10 = a6_.a(var4.posY + (double)var6, var4.lastTickPosY + (double)var6, (double)var1.b());
            double var12 = a6_.a(var4.posZ, var4.lastTickPosZ, (double)var1.b());
            double var14 = (double)var4.width / 1.4D;
            double var16 = (double)var4.height + 0.2D;
            AxisAlignedBB var18 = new AxisAlignedBB(var8 - var14, var10, var12 - var14, var8 + var14, var10 + var16, var12 + var14);
            List var19 = Arrays.asList(new Vector3d[]{new Vector3d(var18.minX, var18.minY, var18.minZ), new Vector3d(var18.minX, var18.maxY, var18.minZ), new Vector3d(var18.maxX, var18.minY, var18.minZ), new Vector3d(var18.maxX, var18.maxY, var18.minZ), new Vector3d(var18.minX, var18.minY, var18.maxZ), new Vector3d(var18.minX, var18.maxY, var18.maxZ), new Vector3d(var18.maxX, var18.minY, var18.maxZ), new Vector3d(var18.maxX, var18.maxY, var18.maxZ)});
            this.w.entityRenderer.setupCameraTransform(var1.b(), 0);
            Vector4d var20 = null;
            Iterator var21 = var19.iterator();
            if(var21.hasNext()) {
               Vector3d var22 = (Vector3d)var21.next();
               var22 = this.a(var1.a(), var22.x - this.w.getRenderManager().viewerPosX, var22.y - this.w.getRenderManager().viewerPosY, var22.z - this.w.getRenderManager().viewerPosZ);
               if(var22 != null && var22.z >= 0.0D && var22.z < 1.0D) {
                  if(var20 == null) {
                     var20 = new Vector4d(var22.x, var22.y, var22.z, 0.0D);
                  }

                  var20.x = Math.min(var22.x, var20.x);
                  var20.y = Math.min(var22.y, var20.y);
                  var20.z = Math.max(var22.x, var20.z);
                  var20.w = Math.max(var22.y, var20.w);
               }
            }

            this.w.entityRenderer.setupOverlayRendering();
            if(var20 != null && (!((Boolean)this.y.a()).booleanValue() || this.b((EntityItem)var4))) {
               double var37 = var20.x;
               double var23 = var20.y;
               double var25 = var20.z;
               double var27 = var20.w;
               a6_.a(var37, var23, var25, var27, this.b((EntityItem)var4)?4.0D:3.0D, Color.BLACK);
               a6_.a(var37, var23, var25, var27, this.b((EntityItem)var4)?2.0D:1.0D, this.a((EntityItem)var4));
               if(((Boolean)this.C.a()).booleanValue()) {
                  float var29 = 1.0F;
                  switch(this.w.gameSettings.guiScale) {
                  case 0:
                     var29 = 0.5F;
                  case 1:
                     var29 = 2.0F;
                  case 3:
                     var29 = 0.6666667F;
                  case 2:
                  default:
                     double[] var30 = dI.a(this.w, var37, var23);
                     double[] var31 = dI.a(this.w, var25, var27);
                     double[] var32 = new double[]{var30[0] * 2.0D, var30[1] * 2.0D, var31[0] * 2.0D, var31[1] * 2.0D};
                     GL11.glPushMatrix();
                     GL11.glScalef(0.5F * var29, 0.5F * var29, 0.5F * var29);
                     double var33 = Math.abs(var32[2] - var32[0]);
                     boolean var35 = true;
                     float var36 = (float)(this.w.a.f() * 2) - (float)(this.w.a.f() / 2);
                     this.w.a.drawStringWithShadow(((EntityItem)var4).i().getDisplayName(), (float)(var32[0] + var33 / 2.0D - (double)(this.w.a.d(((EntityItem)var4).i().getDisplayName()) / 2)), (float)var31[1] * 2.0F + var36, this.a((EntityItem)var4).brighter().getRGB());
                     GL11.glPopMatrix();
                  }
               }
            }
         }
      }

   }

   private boolean b(EntityItem var1) {
      int var2 = ava.e();
      boolean var3 = var1.i().getItem() instanceof ItemArmor || var1.i().getItem() == Items.skull && !var1.i().getDisplayName().equalsIgnoreCase("Zombie Head") && !var1.i().getDisplayName().equalsIgnoreCase("Creeper Head") && !var1.i().getDisplayName().equalsIgnoreCase("Skeleton Skull") && !var1.i().getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && !var1.i().getDisplayName().equalsIgnoreCase(EnumChatFormatting.GREEN + "Frog\'s Hat") || var1.i().getItem() instanceof ItemAppleGold;
      if(var1.i().getItem() instanceof ItemArmor) {
         avP var15 = (avP)this.b((Class)avP.class);
         int var5 = 1;
         if(var5 < 5) {
            String var6 = "";
            switch(var5) {
            case 1:
               var6 = "helmet";
            case 2:
               var6 = "chestplate";
            case 3:
               var6 = "leggings";
            case 4:
               var6 = "boots";
            default:
               if(this.w.thePlayer.q(4 + var5).getHasStack()) {
                  ItemStack var7 = this.w.thePlayer.q(4 + var5).getStack();
                  if(var7.getItem().getUnlocalizedName().contains(var6) && var1.i().getItem().getUnlocalizedName().contains(var6)) {
                     return this.a(var1.i()) > this.a(this.w.thePlayer.q(4 + var5).getStack());
                  }
               }

               ++var5;
            }
         }

         return !this.b(var1.i());
      } else if(var1.i().getItem() instanceof ItemSword) {
         int var13 = 9;
         if(var13 < 45) {
            if(this.w.thePlayer.q(var13).getHasStack() && this.w.thePlayer.q(var13).getStack().getItem() instanceof ItemSword) {
               return this.d(var1.i()) > this.d(this.w.thePlayer.q(var13).getStack());
            }

            ++var13;
         }

         return !this.b(var1.i());
      } else if(var1.i().getItem() instanceof ItemPickaxe) {
         int var11 = 9;
         if(var11 < 45) {
            if(this.w.thePlayer.q(var11).getHasStack() && this.w.thePlayer.q(var11).getStack().getItem() instanceof ItemPickaxe) {
               return this.c(var1.i()) > this.c(this.w.thePlayer.q(var11).getStack());
            }

            ++var11;
         }

         return !this.b(var1.i());
      } else if(var1.i().getItem() instanceof ItemSpade) {
         int var9 = 9;
         if(var9 < 45) {
            if(this.w.thePlayer.q(var9).getHasStack() && this.w.thePlayer.q(var9).getStack().getItem() instanceof ItemSpade) {
               return this.c(var1.i()) > this.c(this.w.thePlayer.q(var9).getStack());
            }

            ++var9;
         }

         return !this.b(var1.i());
      } else if(var1.i().getItem() instanceof ItemAxe) {
         int var4 = 9;
         if(var4 < 45) {
            if(this.w.thePlayer.q(var4).getHasStack() && this.w.thePlayer.q(var4).getStack().getItem() instanceof ItemAxe) {
               return this.c(var1.i()) > this.c(this.w.thePlayer.q(var4).getStack());
            }

            ++var4;
         }

         return !this.b(var1.i());
      } else {
         return var3;
      }
   }

   private float a(ItemStack var1) {
      ava.h();
      float var3 = 0.0F;
      if(var1.getItem() instanceof ItemArmor) {
         ItemArmor var4 = (ItemArmor)var1.getItem();
         var3 = (float)((double)var3 + (double)var4.damageReduceAmount + (double)((100 - var4.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var1)) * 0.0075D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 50.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, var1) / 100.0D);
      }

      return var3;
   }

   private float d(ItemStack var1) {
      ava.e();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).b();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).a();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.01F;
      return var3;
   }

   private float c(ItemStack var1) {
      ava.e();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemTool)) {
         return 0.0F;
      } else {
         float var6;
         label20: {
            String var4 = var3.getUnlocalizedName();
            ItemTool var5 = (ItemTool)var3;
            if(var3 instanceof ItemPickaxe) {
               var6 = var5.getStrVsBlock(var1, Blocks.stone);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 5.0F;
            }

            if(var3 instanceof ItemSpade) {
               var6 = var5.getStrVsBlock(var1, Blocks.dirt);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 5.0F;
            }

            if(!(var3 instanceof ItemAxe)) {
               return 1.0F;
            }

            var6 = var5.getStrVsBlock(var1, Blocks.log);
            if(var4.toLowerCase().contains("gold")) {
               var6 = var6 - 5.0F;
               return 1.0F;
            }
         }

         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var1) * 0.0075D);
         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 100.0D);
         return var6;
      }
   }

   private boolean b(ItemStack var1) {
      ava.h();
      int var3 = 0;
      if(var3 < 3) {
         if(this.w.thePlayer.bJ.armorInventory[var3] != null && this.w.thePlayer.bJ.armorInventory[var3].getItem() == var1.getItem()) {
            return true;
         }

         ++var3;
      }

      var3 = 9;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var1.getItem() == var4.getItem()) {
               return true;
            }
         }

         ++var3;
      }

      return false;
   }

   private Color a(EntityItem var1) {
      ava.e();
      String var3 = var1.i().getDisplayName();
      if(!var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") && !var3.equalsIgnoreCase("aDragon Sword") && !var3.equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") && !var3.equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") && !var3.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis\' Bow") && !var3.equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner\'s Blessing") && !var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") && !var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia")) {
         return !this.b(var1)?new Color(255, 255, 255):(var1.i().getItem() instanceof ItemArmor?new Color(75, 189, 193):(var1.i().getItem() instanceof ItemAppleGold?new Color(255, 199, 71):(var1.i().getItem() instanceof ItemSkull && this.b(var1)?new Color(255, 199, 71):(var1.i().getItem() instanceof ItemSword?new Color(255, 117, 117):(var1.i().getItem() instanceof ItemPickaxe?new Color(130, 219, 82):(var1.i().getItem() instanceof ItemSpade?new Color(130, 219, 82):(var1.i().getItem() instanceof ItemAxe?new Color(130, 219, 82):new Color(255, 255, 255))))))));
      } else {
         ava var4 = (ava)this.b((Class)ava.class);
         return new Color(255, 255, 255);
      }
   }

   private Vector3d a(ScaledResolution var1, double var2, double var4, double var6) {
      GL11.glGetFloat(2982, this.x);
      GL11.glGetFloat(2983, this.z);
      GL11.glGetInteger(2978, this.B);
      return Ls.a((float)var2, (float)var4, (float)var6, this.x, this.z, this.B, this.A)?new Vector3d((double)(this.A.get(0) / (float)var1.getScaleFactor()), (double)(((float)Display.getHeight() - this.A.get(1)) / (float)var1.getScaleFactor()), (double)this.A.get(2)):null;
   }
}
