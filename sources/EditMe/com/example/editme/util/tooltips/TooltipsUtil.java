package com.example.editme.util.tooltips;

import java.awt.Color;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class TooltipsUtil {
   private static final Map itemId_modName = new HashMap();
   private static final Map formatting_color = new HashMap();

   public static int getRarityColor(Tooltip var0) {
      return (Integer)formatting_color.getOrDefault(var0.formattingColor(), (new Color(255, 255, 255)).getRGB());
   }

   public static Optional getMouseOver() {
      return getMouseOver(Minecraft.func_71410_x().field_71441_e, Minecraft.func_71410_x().field_71439_g, 0.0F);
   }

   public static void post() {
      FontRenderer var0 = Minecraft.func_71410_x().field_71466_p;
      TextFormatting[] var1 = TextFormatting.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         TextFormatting var4 = var1[var3];
         formatting_color.put(var4, var0.func_175064_b(var4.toString().replace("§", "").charAt(0)));
      }

      Map var6 = Loader.instance().getIndexedModList();
      Iterator var7 = var6.entrySet().iterator();

      while(var7.hasNext()) {
         Entry var8 = (Entry)var7.next();
         String var9 = ((String)var8.getKey()).toLowerCase(Locale.ENGLISH);
         String var5 = ((ModContainer)var8.getValue()).getName();
         itemId_modName.put(var9, var5);
      }

   }

   public static int getRarityColor(TextFormatting var0) {
      return (Integer)formatting_color.getOrDefault(var0, (new Color(50, 0, 200)).getRGB());
   }

   public static Optional getMouseOver(World var0, Entity var1, float var2) throws ConcurrentModificationException {
      if (var0 != null && var1 != null) {
         boolean var4 = true;
         Vec3d var5 = var1.func_174824_e(var2);
         Vec3d var6 = var1.func_70676_i(var2);
         Vec3d var7 = var5.func_72441_c(var6.field_72450_a * 10.0D, var6.field_72448_b * 10.0D, var6.field_72449_c * 10.0D);
         double var8 = 0.0D;
         EntityItem var10 = null;
         List var11 = var0.func_72872_a(EntityItem.class, var1.func_174813_aQ().func_72321_a(var6.field_72450_a * 10.0D, var6.field_72448_b * 10.0D, var6.field_72449_c * 10.0D).func_72314_b(1.0D, 1.0D, 1.0D));

         for(int var12 = 0; var12 < var11.size(); ++var12) {
            EntityItem var13 = (EntityItem)var11.get(var12);
            AxisAlignedBB var14 = var13.func_174813_aQ().func_72317_d(0.0D, 0.25D, 0.0D).func_186662_g((double)var13.func_70111_Y() + 0.1D);
            RayTraceResult var15 = var14.func_72327_a(var5, var7);
            if (var14.func_72318_a(var5)) {
               if (var8 > 0.0D) {
                  var10 = var13;
                  var8 = 0.0D;
               }
            } else if (var15 != null) {
               double var16 = var5.func_72438_d(var15.field_72307_f);
               if (var16 < var8 || var8 == 0.0D) {
                  var10 = var13;
                  var8 = var16;
               }
            }
         }

         return var10 == null ? Optional.empty() : Optional.of(var10);
      } else {
         return Optional.empty();
      }
   }
}
