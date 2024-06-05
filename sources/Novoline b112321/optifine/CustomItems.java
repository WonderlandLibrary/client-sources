package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.Uv;
import net.atX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import optifine.Blender;
import optifine.Config;
import optifine.CustomItemProperties;
import optifine.CustomItems$1;
import optifine.CustomItemsComparator;
import optifine.MatchBlock;
import optifine.NbtTagValue;
import optifine.ResUtils;
import optifine.StrUtils;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class CustomItems {
   private static CustomItemProperties[][] itemProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
   private static CustomItemProperties[][] enchantmentProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
   private static Map mapPotionIds = null;
   private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
   private static boolean useGlint = true;
   public static final int MASK_POTION_SPLASH = 16384;
   public static final int MASK_POTION_NAME = 63;
   public static final String c = "texture.potion_overlay";
   public static final String d = "texture.potion_bottle_splash";
   public static final String j = "texture.potion_bottle_drinkable";
   public static final String b = "items/potion_overlay";
   public static final String n = "items/potion_bottle_splash";
   public static final String o = "items/potion_bottle_drinkable";
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];

   public static void updateIcons(TextureMap var0) {
      itemProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
      MatchBlock.b();
      enchantmentProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
      useGlint = true;
      if(Config.isCustomItems()) {
         a("mcpatcher/cit.properties");
         IResourcePack[] var2 = Config.getResourcePacks();
         int var3 = var2.length - 1;
         IResourcePack var4 = var2[var3];
         updateIcons(var0, var4);
         --var3;
         updateIcons(var0, Config.getDefaultResourcePack());
         if(itemProperties.length <= 0) {
            itemProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
         }

         if(enchantmentProperties.length <= 0) {
            enchantmentProperties = (CustomItemProperties[][])((CustomItemProperties[][])null);
         }
      }

   }

   private static void a(String var0) {
      PacketRemapper[] var1 = MatchBlock.b();

      try {
         ResourceLocation var2 = new ResourceLocation(var0);
         InputStream var3 = Config.getResourceStream(var2);
      } catch (FileNotFoundException var4) {
         ;
      } catch (IOException var5) {
         var5.printStackTrace();
      }
   }

   private static void updateIcons(TextureMap param0, IResourcePack param1) {
      // $FF: Couldn't be decompiled
   }

   private static Comparator getPropertiesComparator() {
      CustomItems$1 var0 = new CustomItems$1();
      return var0;
   }

   public static void updateModels() {
      PacketRemapper[] var0 = MatchBlock.b();
      if(itemProperties != null) {
         int var1 = 0;
         if(var1 < itemProperties.length) {
            CustomItemProperties[] var2 = itemProperties[var1];
            int var3 = 0;
            if(var3 < var2.length) {
               CustomItemProperties var4 = var2[var3];
               if(var4.type == 1) {
                  TextureMap var5 = Minecraft.getMinecraft().getTextureMapBlocks();
                  var4.updateModel(var5, itemModelGenerator);
               }

               ++var3;
            }

            ++var1;
         }
      }

   }

   private static Map makeAutoImageProperties(IResourcePack var0) {
      HashMap var1 = new HashMap();
      var1.putAll(makePotionImageProperties(var0, false));
      var1.putAll(makePotionImageProperties(var0, true));
      return var1;
   }

   private static Map makePotionImageProperties(IResourcePack var0, boolean var1) {
      MatchBlock.b();
      HashMap var3 = new HashMap();
      String var4 = "splash/";
      String[] var5 = new String[]{"mcpatcher/cit/potion/" + var4, "mcpatcher/cit/Potion/" + var4};
      String[] var6 = new String[]{".png"};
      String[] var7 = ResUtils.collectFiles(var0, var5, var6);
      int var8 = 0;
      if(var8 < var7.length) {
         String var9 = var7[var8];
         String var10 = StrUtils.removePrefixSuffix(var9, var5, var6);
         Properties var11 = makePotionProperties(var10, var1, var9);
         String var12 = StrUtils.removeSuffix(var9, var6) + ".properties";
         CustomItemProperties var13 = new CustomItemProperties(var11, var12);
         var3.put(var12, var13);
         ++var8;
      }

      return var3;
   }

   private static Properties makePotionProperties(String var0, boolean var1, String var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(StrUtils.c(var0, new String[]{"_n", "_s"})) {
         return null;
      } else if(var0.equals("empty") && !var1) {
         int var6 = Item.b(Items.glass_bottle);
         Properties var7 = new Properties();
         var7.put("type", "item");
         var7.put("items", "" + var6);
         return var7;
      } else {
         int var4 = Item.b(Items.potionitem);
         int[] var5 = (int[])((int[])((int[])getMapPotionIds().get(var0)));
         Config.warn("Potion not found for image: " + var2);
         return null;
      }
   }

   private static Map getMapPotionIds() {
      PacketRemapper[] var0 = MatchBlock.b();
      if(mapPotionIds == null) {
         mapPotionIds = new LinkedHashMap();
         mapPotionIds.put("water", new int[]{0});
         mapPotionIds.put("awkward", new int[]{16});
         mapPotionIds.put("thick", new int[]{32});
         mapPotionIds.put("potent", new int[]{48});
         mapPotionIds.put("regeneration", getPotionIds(1));
         mapPotionIds.put("moveSpeed", getPotionIds(2));
         mapPotionIds.put("fireResistance", getPotionIds(3));
         mapPotionIds.put("poison", getPotionIds(4));
         mapPotionIds.put("heal", getPotionIds(5));
         mapPotionIds.put("nightVision", getPotionIds(6));
         mapPotionIds.put("clear", getPotionIds(7));
         mapPotionIds.put("bungling", getPotionIds(23));
         mapPotionIds.put("charming", getPotionIds(39));
         mapPotionIds.put("rank", getPotionIds(55));
         mapPotionIds.put("weakness", getPotionIds(8));
         mapPotionIds.put("damageBoost", getPotionIds(9));
         mapPotionIds.put("moveSlowdown", getPotionIds(10));
         mapPotionIds.put("diffuse", getPotionIds(11));
         mapPotionIds.put("smooth", getPotionIds(27));
         mapPotionIds.put("refined", getPotionIds(43));
         mapPotionIds.put("acrid", getPotionIds(59));
         mapPotionIds.put("harm", getPotionIds(12));
         mapPotionIds.put("waterBreathing", getPotionIds(13));
         mapPotionIds.put("invisibility", getPotionIds(14));
         mapPotionIds.put("thin", getPotionIds(15));
         mapPotionIds.put("debonair", getPotionIds(31));
         mapPotionIds.put("sparkling", getPotionIds(47));
         mapPotionIds.put("stinky", getPotionIds(63));
      }

      return mapPotionIds;
   }

   private static int[] getPotionIds(int var0) {
      return new int[]{var0, var0 + 16, var0 + 32, var0 + 48};
   }

   private static int getPotionNameDamage(String var0) {
      MatchBlock.b();
      String var2 = "potion." + var0;
      Potion[] var3 = Potion.potionTypes;
      int var4 = 0;
      if(var4 < var3.length) {
         Potion var5 = var3[var4];
         String var6 = var5.getName();
         if(var2.equals(var6)) {
            return var5.getId();
         }

         ++var4;
      }

      return -1;
   }

   private static List makePropertyList(CustomItemProperties[][] var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      int var3 = 0;
      if(var3 < var0.length) {
         CustomItemProperties[] var4 = var0[var3];
         ArrayList var5 = null;
         var5 = new ArrayList(Arrays.asList(var4));
         var2.add(var5);
         ++var3;
      }

      return var2;
   }

   private static CustomItemProperties[][] propertyListToArray(List var0) {
      MatchBlock.b();
      CustomItemProperties[][] var2 = new CustomItemProperties[var0.size()][];
      int var3 = 0;
      if(var3 < var0.size()) {
         List var4 = (List)var0.get(var3);
         CustomItemProperties[] var5 = (CustomItemProperties[])((CustomItemProperties[])((CustomItemProperties[])var4.toArray(new CustomItemProperties[var4.size()])));
         Arrays.sort(var5, new CustomItemsComparator());
         var2[var3] = var5;
         ++var3;
      }

      return var2;
   }

   private static void addToItemList(CustomItemProperties var0, List var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0.items != null) {
         int var3 = 0;
         if(var3 < var0.items.length) {
            int var4 = var0.items[var3];
            Config.warn("Invalid item ID: " + var4);
            addToList(var0, var1, var4);
            ++var3;
         }
      }

   }

   private static void addToEnchantmentList(CustomItemProperties var0, List var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0.type == 2 && var0.enchantmentIds != null) {
         int var3 = 0;
         if(var3 < 256) {
            if(var0.enchantmentIds.isInRange(var3)) {
               addToList(var0, var1, var3);
            }

            ++var3;
         }
      }

   }

   private static void addToList(CustomItemProperties var0, List var1, int var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var2 >= var1.size()) {
         var1.add((Object)null);
      }

      Object var4 = (List)var1.get(var2);
      if(var4 == null) {
         var4 = new ArrayList();
         var1.set(var2, var4);
      }

      ((List)var4).add(var0);
   }

   public static IBakedModel getCustomItemModel(ItemStack var0, IBakedModel var1, ModelResourceLocation var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1.isGui3d()) {
         return var1;
      } else if(itemProperties == null) {
         return var1;
      } else {
         CustomItemProperties var4 = getCustomItemProperties(var0, 1);
         return var4 == null?var1:var4.getModel(var2);
      }
   }

   public static boolean bindCustomArmorTexture(ItemStack var0, int var1, String var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(itemProperties == null) {
         return false;
      } else {
         ResourceLocation var4 = getCustomArmorLocation(var0, var1, var2);
         return false;
      }
   }

   private static ResourceLocation getCustomArmorLocation(ItemStack var0, int var1, String var2) {
      MatchBlock.b();
      CustomItemProperties var4 = getCustomItemProperties(var0, 3);
      if(var4 == null) {
         return null;
      } else if(var4.mapTextureLocations == null) {
         return null;
      } else {
         Item var5 = var0.getItem();
         if(!(var5 instanceof ItemArmor)) {
            return null;
         } else {
            ItemArmor var6 = (ItemArmor)var5;
            String var7 = var6.getArmorMaterial().getName();
            StringBuffer var8 = new StringBuffer();
            var8.append("texture.");
            var8.append(var7);
            var8.append("_layer_");
            var8.append(var1);
            if(var2 != null) {
               var8.append("_");
               var8.append(var2);
            }

            String var9 = var8.toString();
            ResourceLocation var10 = (ResourceLocation)var4.mapTextureLocations.get(var9);
            return var10;
         }
      }
   }

   private static CustomItemProperties getCustomItemProperties(ItemStack var0, int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(itemProperties == null) {
         return null;
      } else if(var0 == null) {
         return null;
      } else {
         Item var3 = var0.getItem();
         int var4 = Item.b(var3);
         if(var4 >= 0 && var4 < itemProperties.length) {
            CustomItemProperties[] var5 = itemProperties[var4];
            int var6 = 0;
            if(var6 < var5.length) {
               CustomItemProperties var7 = var5[var6];
               if(var7.type == var1 && matchesProperties(var7, var0, (int[][])((int[][])null))) {
                  return var7;
               }

               ++var6;
            }
         }

         return null;
      }
   }

   private static boolean matchesProperties(CustomItemProperties var0, ItemStack var1, int[][] var2) {
      MatchBlock.b();
      Item var4 = var1.getItem();
      if(var0.damage != null) {
         int var5 = var1.getItemDamage();
         if(var0.damageMask != 0) {
            var5 &= var0.damageMask;
         }

         if(var0.damagePercent) {
            int var6 = var4.getMaxDamage();
            var5 = (int)((double)(var5 * 100) / (double)var6);
         }

         if(!var0.damage.isInRange(var5)) {
            return false;
         }
      }

      if(var0.stackSize != null && !var0.stackSize.isInRange(var1.stackSize)) {
         return false;
      } else {
         int[][] var9 = var2;
         if(var0.enchantmentIds != null) {
            if(var2 == null) {
               var9 = getEnchantmentIdLevels(var1);
            }

            boolean var10 = false;
            int var7 = 0;
            if(var7 < var9.length) {
               int var8 = var9[var7][0];
               if(var0.enchantmentIds.isInRange(var8)) {
                  var10 = true;
               }

               ++var7;
            }

            if(!var10) {
               return false;
            }
         }

         if(var0.enchantmentLevels != null) {
            if(var9 == null) {
               var9 = getEnchantmentIdLevels(var1);
            }

            boolean var11 = false;
            int var14 = 0;
            if(var14 < var9.length) {
               int var18 = var9[var14][1];
               if(var0.enchantmentLevels.isInRange(var18)) {
                  var11 = true;
               }

               ++var14;
            }

            if(!var11) {
               return false;
            }
         }

         if(var0.nbtTagValues != null) {
            NBTTagCompound var12 = var1.getTagCompound();
            int var16 = 0;
            if(var16 < var0.nbtTagValues.length) {
               NbtTagValue var19 = var0.nbtTagValues[var16];
               if(!var19.matches(var12)) {
                  return false;
               }

               ++var16;
            }
         }

         return true;
      }
   }

   private static int[][] getEnchantmentIdLevels(ItemStack var0) {
      MatchBlock.b();
      Item var2 = var0.getItem();
      NBTTagList var3 = var2 == Items.enchanted_book?Items.enchanted_book.getEnchantments(var0):var0.getEnchantmentTagList();
      if(var3 != null && var3.tagCount() > 0) {
         int[][] var4 = new int[var3.tagCount()][2];
         int var5 = 0;
         if(var5 < var3.tagCount()) {
            NBTTagCompound var6 = var3.getCompoundTagAt(var5);
            short var7 = var6.getShort("id");
            short var8 = var6.getShort("lvl");
            var4[var5][0] = var7;
            var4[var5][1] = var8;
            ++var5;
         }

         return var4;
      } else {
         return EMPTY_INT2_ARRAY;
      }
   }

   public static boolean a(atX var0, ItemStack var1, IBakedModel var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(enchantmentProperties == null) {
         return false;
      } else if(var1 == null) {
         return false;
      } else {
         int[][] var4 = getEnchantmentIdLevels(var1);
         if(var4.length <= 0) {
            return false;
         } else {
            HashSet var5 = null;
            boolean var6 = false;
            Uv var7 = Config.ax();
            int var8 = 0;
            if(var8 < var4.length) {
               int var9 = var4[var8][0];
               if(var9 >= 0 && var9 < enchantmentProperties.length) {
                  CustomItemProperties[] var10 = enchantmentProperties[var9];
                  int var11 = 0;
                  if(var11 < var10.length) {
                     CustomItemProperties var12 = var10[var11];
                     if(var5 == null) {
                        var5 = new HashSet();
                     }

                     if(var5.add(Integer.valueOf(var9)) && matchesProperties(var12, var1, var4) && var12.textureLocation != null) {
                        var7.a(var12.textureLocation);
                        float var13 = var12.b(var7);
                        if(!var6) {
                           var6 = true;
                           GlStateManager.depthMask(false);
                           GlStateManager.depthFunc(514);
                           GlStateManager.disableLighting();
                           GlStateManager.matrixMode(5890);
                        }

                        Blender.setupBlend(var12.blend, 1.0F);
                        GlStateManager.pushMatrix();
                        GlStateManager.scale(var13 / 2.0F, var13 / 2.0F, var13 / 2.0F);
                        float var14 = var12.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
                        GlStateManager.translate(var14, 0.0F, 0.0F);
                        GlStateManager.rotate(var12.rotation, 0.0F, 0.0F, 1.0F);
                        var0.a(var2, -1);
                        GlStateManager.popMatrix();
                     }

                     ++var11;
                  }
               }

               ++var8;
            }

            if(var6) {
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableLighting();
               GlStateManager.depthFunc(515);
               GlStateManager.depthMask(true);
               var7.a(TextureMap.locationBlocksTexture);
            }

            return var6;
         }
      }
   }

   public static boolean renderCustomArmorEffect(EntityLivingBase var0, ItemStack var1, ModelBase var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      PacketRemapper[] var10 = MatchBlock.b();
      if(enchantmentProperties == null) {
         return false;
      } else if(Config.isShaders() && Shaders.isShadowPass) {
         return false;
      } else if(var1 == null) {
         return false;
      } else {
         int[][] var11 = getEnchantmentIdLevels(var1);
         if(var11.length <= 0) {
            return false;
         } else {
            HashSet var12 = null;
            boolean var13 = false;
            Uv var14 = Config.ax();
            int var15 = 0;
            if(var15 < var11.length) {
               int var16 = var11[var15][0];
               if(var16 >= 0 && var16 < enchantmentProperties.length) {
                  CustomItemProperties[] var17 = enchantmentProperties[var16];
                  int var18 = 0;
                  if(var18 < var17.length) {
                     CustomItemProperties var19 = var17[var18];
                     if(var12 == null) {
                        var12 = new HashSet();
                     }

                     if(var12.add(Integer.valueOf(var16)) && matchesProperties(var19, var1, var11) && var19.textureLocation != null) {
                        var14.a(var19.textureLocation);
                        float var20 = var19.b(var14);
                        if(!var13) {
                           var13 = true;
                           if(Config.isShaders()) {
                              ShadersRender.renderEnchantedGlintBegin();
                           }

                           GlStateManager.enableBlend();
                           GlStateManager.depthFunc(514);
                           GlStateManager.depthMask(false);
                        }

                        Blender.setupBlend(var19.blend, 1.0F);
                        GlStateManager.disableLighting();
                        GlStateManager.matrixMode(5890);
                        GlStateManager.loadIdentity();
                        GlStateManager.rotate(var19.rotation, 0.0F, 0.0F, 1.0F);
                        float var21 = var20 / 8.0F;
                        GlStateManager.scale(var21, var21 / 2.0F, var21);
                        float var22 = var19.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
                        GlStateManager.translate(0.0F, var22, 0.0F);
                        GlStateManager.matrixMode(5888);
                        var2.render(var0, var3, var4, var6, var7, var8, var9);
                     }

                     ++var18;
                  }
               }

               ++var15;
            }

            if(var13) {
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.enableLighting();
               GlStateManager.depthMask(true);
               GlStateManager.depthFunc(515);
               GlStateManager.disableBlend();
               if(Config.isShaders()) {
                  ShadersRender.renderEnchantedGlintEnd();
               }
            }

            return var13;
         }
      }
   }

   public static boolean isUseGlint() {
      return useGlint;
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
