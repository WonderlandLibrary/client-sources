package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.player.AutoArmor;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.ScaleUtils;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.Ls;
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

public final class ItemESP extends AbstractModule {
   @Property("names")
   private final BooleanProperty names = PropertyFactory.booleanFalse();
   @Property("needed-only")
   private final BooleanProperty neededOnly = PropertyFactory.booleanFalse();
   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
   private final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);

   public ItemESP(ModuleManager var1) {
      super(var1, "ItemESP", "Item ESP", 0, EnumModuleType.VISUALS, "");
      Manager.put(new Setting("NAMES", "Names", SettingType.CHECKBOX, this, this.names));
      Manager.put(new Setting("NEEDED_ONLY", "Needed Only", SettingType.CHECKBOX, this, this.neededOnly));
   }

   @EventTarget
   public void onRender(Render2DEvent var1) {
      HUD.h();
      Iterator var3 = this.mc.world.getLoadedEntityList().iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(var4 instanceof EntityItem) {
            IBakedModel var5 = this.mc.getRenderItem().getItemModelMesher().getItemModel(((EntityItem)var4).getEntityItem());
            float var6 = MathHelper.sin(((float)((EntityItem)var4).getAge() + var1.getPartialTicks()) / 10.0F + ((EntityItem)var4).hoverStart) * 0.1F + 0.1F;
            float var7 = var5.getItemCameraTransforms().getTransform(ItemCameraTransforms$TransformType.GROUND).scale.y;
            double var8 = RenderUtils.interpolate(var4.posX, var4.lastTickPosX, (double)var1.getPartialTicks());
            double var10 = RenderUtils.interpolate(var4.posY + (double)var6, var4.lastTickPosY + (double)var6, (double)var1.getPartialTicks());
            double var12 = RenderUtils.interpolate(var4.posZ, var4.lastTickPosZ, (double)var1.getPartialTicks());
            double var14 = (double)var4.width / 1.4D;
            double var16 = (double)var4.height + 0.2D;
            AxisAlignedBB var18 = new AxisAlignedBB(var8 - var14, var10, var12 - var14, var8 + var14, var10 + var16, var12 + var14);
            List var19 = Arrays.asList(new Vector3d[]{new Vector3d(var18.minX, var18.minY, var18.minZ), new Vector3d(var18.minX, var18.maxY, var18.minZ), new Vector3d(var18.maxX, var18.minY, var18.minZ), new Vector3d(var18.maxX, var18.maxY, var18.minZ), new Vector3d(var18.minX, var18.minY, var18.maxZ), new Vector3d(var18.minX, var18.maxY, var18.maxZ), new Vector3d(var18.maxX, var18.minY, var18.maxZ), new Vector3d(var18.maxX, var18.maxY, var18.maxZ)});
            this.mc.entityRenderer.setupCameraTransform(var1.getPartialTicks(), 0);
            Vector4d var20 = null;
            Iterator var21 = var19.iterator();
            if(var21.hasNext()) {
               Vector3d var22 = (Vector3d)var21.next();
               var22 = this.project2D(var1.getResolution(), var22.x - this.mc.getRenderManager().viewerPosX, var22.y - this.mc.getRenderManager().viewerPosY, var22.z - this.mc.getRenderManager().viewerPosZ);
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

            this.mc.entityRenderer.setupOverlayRendering();
            if(var20 != null && (!((Boolean)this.neededOnly.get()).booleanValue() || this.isItemSpecial((EntityItem)var4))) {
               double var37 = var20.x;
               double var23 = var20.y;
               double var25 = var20.z;
               double var27 = var20.w;
               RenderUtils.drawCornerBox(var37, var23, var25, var27, this.isItemSpecial((EntityItem)var4)?4.0D:3.0D, Color.BLACK);
               RenderUtils.drawCornerBox(var37, var23, var25, var27, this.isItemSpecial((EntityItem)var4)?2.0D:1.0D, this.getItemColor((EntityItem)var4));
               if(((Boolean)this.names.get()).booleanValue()) {
                  float var29 = 1.0F;
                  switch(this.mc.gameSettings.guiScale) {
                  case 0:
                     var29 = 0.5F;
                  case 1:
                     var29 = 2.0F;
                  case 3:
                     var29 = 0.6666667F;
                  case 2:
                  default:
                     double[] var30 = ScaleUtils.a(this.mc, var37, var23);
                     double[] var31 = ScaleUtils.a(this.mc, var25, var27);
                     double[] var32 = new double[]{var30[0] * 2.0D, var30[1] * 2.0D, var31[0] * 2.0D, var31[1] * 2.0D};
                     GL11.glPushMatrix();
                     GL11.glScalef(0.5F * var29, 0.5F * var29, 0.5F * var29);
                     double var33 = Math.abs(var32[2] - var32[0]);
                     boolean var35 = true;
                     float var36 = (float)(this.mc.fontRendererCrack.getHeight() * 2) - (float)(this.mc.fontRendererCrack.getHeight() / 2);
                     this.mc.fontRendererCrack.drawStringWithShadow(((EntityItem)var4).getEntityItem().getDisplayName(), (float)(var32[0] + var33 / 2.0D - (double)(this.mc.fontRendererCrack.d(((EntityItem)var4).getEntityItem().getDisplayName()) / 2)), (float)var31[1] * 2.0F + var36, this.getItemColor((EntityItem)var4).brighter().getRGB());
                     GL11.glPopMatrix();
                  }
               }
            }
         }
      }

   }

   private boolean isItemSpecial(EntityItem var1) {
      int var2 = HUD.e();
      boolean var3 = var1.getEntityItem().getItem() instanceof ItemArmor || var1.getEntityItem().getItem() == Items.skull && !var1.getEntityItem().getDisplayName().equalsIgnoreCase("Zombie Head") && !var1.getEntityItem().getDisplayName().equalsIgnoreCase("Creeper Head") && !var1.getEntityItem().getDisplayName().equalsIgnoreCase("Skeleton Skull") && !var1.getEntityItem().getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && !var1.getEntityItem().getDisplayName().equalsIgnoreCase(EnumChatFormatting.GREEN + "Frog\'s Hat") || var1.getEntityItem().getItem() instanceof ItemAppleGold;
      if(var1.getEntityItem().getItem() instanceof ItemArmor) {
         AutoArmor var15 = (AutoArmor)this.getModule(AutoArmor.class);
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
               if(this.mc.player.getSlotFromPlayerContainer(4 + var5).getHasStack()) {
                  ItemStack var7 = this.mc.player.getSlotFromPlayerContainer(4 + var5).getStack();
                  if(var7.getItem().getUnlocalizedName().contains(var6) && var1.getEntityItem().getItem().getUnlocalizedName().contains(var6)) {
                     return this.getProtection(var1.getEntityItem()) > this.getProtection(this.mc.player.getSlotFromPlayerContainer(4 + var5).getStack());
                  }
               }

               ++var5;
            }
         }

         return !this.hasItem(var1.getEntityItem());
      } else if(var1.getEntityItem().getItem() instanceof ItemSword) {
         int var13 = 9;
         if(var13 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var13).getHasStack() && this.mc.player.getSlotFromPlayerContainer(var13).getStack().getItem() instanceof ItemSword) {
               return this.getDamage(var1.getEntityItem()) > this.getDamage(this.mc.player.getSlotFromPlayerContainer(var13).getStack());
            }

            ++var13;
         }

         return !this.hasItem(var1.getEntityItem());
      } else if(var1.getEntityItem().getItem() instanceof ItemPickaxe) {
         int var11 = 9;
         if(var11 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var11).getHasStack() && this.mc.player.getSlotFromPlayerContainer(var11).getStack().getItem() instanceof ItemPickaxe) {
               return this.getToolEffect(var1.getEntityItem()) > this.getToolEffect(this.mc.player.getSlotFromPlayerContainer(var11).getStack());
            }

            ++var11;
         }

         return !this.hasItem(var1.getEntityItem());
      } else if(var1.getEntityItem().getItem() instanceof ItemSpade) {
         int var9 = 9;
         if(var9 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var9).getHasStack() && this.mc.player.getSlotFromPlayerContainer(var9).getStack().getItem() instanceof ItemSpade) {
               return this.getToolEffect(var1.getEntityItem()) > this.getToolEffect(this.mc.player.getSlotFromPlayerContainer(var9).getStack());
            }

            ++var9;
         }

         return !this.hasItem(var1.getEntityItem());
      } else if(var1.getEntityItem().getItem() instanceof ItemAxe) {
         int var4 = 9;
         if(var4 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var4).getHasStack() && this.mc.player.getSlotFromPlayerContainer(var4).getStack().getItem() instanceof ItemAxe) {
               return this.getToolEffect(var1.getEntityItem()) > this.getToolEffect(this.mc.player.getSlotFromPlayerContainer(var4).getStack());
            }

            ++var4;
         }

         return !this.hasItem(var1.getEntityItem());
      } else {
         return var3;
      }
   }

   private float getProtection(ItemStack var1) {
      HUD.h();
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

   private float getDamage(ItemStack var1) {
      HUD.e();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).getDamage();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).getAttackDamage();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.01F;
      return var3;
   }

   private float getToolEffect(ItemStack var1) {
      HUD.e();
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

   private boolean hasItem(ItemStack var1) {
      HUD.h();
      int var3 = 0;
      if(var3 < 3) {
         if(this.mc.player.inventory.armorInventory[var3] != null && this.mc.player.inventory.armorInventory[var3].getItem() == var1.getItem()) {
            return true;
         }

         ++var3;
      }

      var3 = 9;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var1.getItem() == var4.getItem()) {
               return true;
            }
         }

         ++var3;
      }

      return false;
   }

   private Color getItemColor(EntityItem var1) {
      HUD.e();
      String var3 = var1.getEntityItem().getDisplayName();
      if(!var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") && !var3.equalsIgnoreCase("aDragon Sword") && !var3.equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") && !var3.equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") && !var3.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis\' Bow") && !var3.equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner\'s Blessing") && !var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") && !var3.equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia")) {
         return !this.isItemSpecial(var1)?new Color(255, 255, 255):(var1.getEntityItem().getItem() instanceof ItemArmor?new Color(75, 189, 193):(var1.getEntityItem().getItem() instanceof ItemAppleGold?new Color(255, 199, 71):(var1.getEntityItem().getItem() instanceof ItemSkull && this.isItemSpecial(var1)?new Color(255, 199, 71):(var1.getEntityItem().getItem() instanceof ItemSword?new Color(255, 117, 117):(var1.getEntityItem().getItem() instanceof ItemPickaxe?new Color(130, 219, 82):(var1.getEntityItem().getItem() instanceof ItemSpade?new Color(130, 219, 82):(var1.getEntityItem().getItem() instanceof ItemAxe?new Color(130, 219, 82):new Color(255, 255, 255))))))));
      } else {
         HUD var4 = (HUD)this.getModule(HUD.class);
         return new Color(255, 255, 255);
      }
   }

   private Vector3d project2D(ScaledResolution var1, double var2, double var4, double var6) {
      GL11.glGetFloat(2982, this.modelView);
      GL11.glGetFloat(2983, this.projection);
      GL11.glGetInteger(2978, this.viewport);
      return Ls.a((float)var2, (float)var4, (float)var6, this.modelView, this.projection, this.viewport, this.vector)?new Vector3d((double)(this.vector.get(0) / (float)var1.getScaleFactor()), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)var1.getScaleFactor()), (double)this.vector.get(2)):null;
   }
}
