package net;

import java.util.Iterator;
import java.util.Map.Entry;
import net.Ij;
import net.acW;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeData$Recipe;

class aeK implements ValueCreator {
   final Ij a;

   aeK(Ij var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      q1.b();
      var1.write(Type.VAR_INT, Integer.valueOf(acW.a.size()));
      Iterator var3 = acW.a.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var1.write(Type.STRING, var4.getKey());
         var1.write(Type.STRING, ((RecipeData$Recipe)var4.getValue()).e());
         String var5 = ((RecipeData$Recipe)var4.getValue()).e();
         byte var6 = -1;
         switch(var5.hashCode()) {
         case -571676035:
            if(!var5.equals("crafting_shapeless")) {
               break;
            }

            var6 = 0;
         case 1533084160:
            if(!var5.equals("crafting_shaped")) {
               break;
            }

            var6 = 1;
         case -491776273:
            if(var5.equals("smelting")) {
               var6 = 2;
            }
         }

         switch(var6) {
         case 0:
            var1.write(Type.STRING, ((RecipeData$Recipe)var4.getValue()).i());
            var1.write(Type.VAR_INT, Integer.valueOf(((RecipeData$Recipe)var4.getValue()).getIngredients().length));
            Item[][] var7 = ((RecipeData$Recipe)var4.getValue()).getIngredients();
            int var8 = var7.length;
            int var9 = 0;
            if(var9 < var8) {
               Item[] var10 = var7[var9];
               Item[] var11 = (Item[])var10.clone();
               int var12 = 0;
               if(var12 < var11.length) {
                  if(var11[var12] != null) {
                     var11[var12] = new Item(var11[var12]);
                  }

                  ++var12;
               }

               var1.write(Type.FLAT_ITEM_ARRAY_VAR_INT, var11);
               ++var9;
            }

            var1.write(Type.FLAT_ITEM, new Item(((RecipeData$Recipe)var4.getValue()).getResult()));
         case 1:
            var1.write(Type.VAR_INT, Integer.valueOf(((RecipeData$Recipe)var4.getValue()).d()));
            var1.write(Type.VAR_INT, Integer.valueOf(((RecipeData$Recipe)var4.getValue()).b()));
            var1.write(Type.STRING, ((RecipeData$Recipe)var4.getValue()).i());
            Item[][] var13 = ((RecipeData$Recipe)var4.getValue()).getIngredients();
            int var15 = var13.length;
            int var19 = 0;
            if(var19 < var15) {
               Item[] var21 = var13[var19];
               Item[] var22 = (Item[])var21.clone();
               int var24 = 0;
               if(var24 < var22.length) {
                  if(var22[var24] != null) {
                     var22[var24] = new Item(var22[var24]);
                  }

                  ++var24;
               }

               var1.write(Type.FLAT_ITEM_ARRAY_VAR_INT, var22);
               ++var19;
            }

            var1.write(Type.FLAT_ITEM, new Item(((RecipeData$Recipe)var4.getValue()).getResult()));
         case 2:
            var1.write(Type.STRING, ((RecipeData$Recipe)var4.getValue()).i());
            Item[] var14 = (Item[])((RecipeData$Recipe)var4.getValue()).getIngredient().clone();
            int var16 = 0;
            if(var16 < var14.length) {
               if(var14[var16] != null) {
                  var14[var16] = new Item(var14[var16]);
               }

               ++var16;
            }

            var1.write(Type.FLAT_ITEM_ARRAY_VAR_INT, var14);
            var1.write(Type.FLAT_ITEM, new Item(((RecipeData$Recipe)var4.getValue()).getResult()));
            var1.write(Type.FLOAT, Float.valueOf(((RecipeData$Recipe)var4.getValue()).getExperience()));
            var1.write(Type.VAR_INT, Integer.valueOf(((RecipeData$Recipe)var4.getValue()).f()));
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
