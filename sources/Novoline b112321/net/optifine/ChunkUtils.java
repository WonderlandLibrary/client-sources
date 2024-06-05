package net.optifine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import net.acE;
import net.minecraft.world.chunk.Chunk;
import net.optifine.Config;
import net.optifine.MatchBlock;

public class ChunkUtils {
   private static Field fieldHasEntities = null;
   private static boolean fieldHasEntitiesMissing = false;

   public static boolean hasEntities(Chunk var0) {
      acE[] var1 = MatchBlock.b();
      if(fieldHasEntities == null) {
         if(fieldHasEntitiesMissing) {
            return true;
         }

         fieldHasEntities = findFieldHasEntities(var0);
         if(fieldHasEntities == null) {
            fieldHasEntitiesMissing = true;
            return true;
         }
      }

      try {
         return fieldHasEntities.getBoolean(var0);
      } catch (Exception var3) {
         Config.warn("Error calling Chunk.hasEntities");
         Config.warn(var3.getClass().getName() + " " + var3.getMessage());
         fieldHasEntitiesMissing = true;
         return true;
      }
   }

   private static Field findFieldHasEntities(Chunk var0) {
      acE[] var1 = MatchBlock.b();

      try {
         ArrayList var2 = new ArrayList();
         ArrayList var3 = new ArrayList();
         Field[] var4 = Chunk.class.getDeclaredFields();
         int var5 = 0;
         if(var5 < var4.length) {
            Field var6 = var4[var5];
            if(var6.getType() == Boolean.TYPE) {
               var6.setAccessible(true);
               var2.add(var6);
               var3.add(var6.get(var0));
            }

            ++var5;
         }

         var0.setHasEntities(false);
         ArrayList var15 = new ArrayList();
         Iterator var16 = var2.iterator();
         if(var16.hasNext()) {
            Object var7 = var16.next();
            var15.add(((Field)var7).get(var0));
         }

         var0.setHasEntities(true);
         ArrayList var17 = new ArrayList();
         Iterator var18 = var2.iterator();
         if(var18.hasNext()) {
            Object var8 = var18.next();
            var17.add(((Field)var8).get(var0));
         }

         ArrayList var19 = new ArrayList();
         int var20 = 0;
         if(var20 < var2.size()) {
            Field var9 = (Field)var2.get(var20);
            Boolean var10 = (Boolean)var15.get(var20);
            Boolean var11 = (Boolean)var17.get(var20);
            if(!var10.booleanValue() && var11.booleanValue()) {
               var19.add(var9);
               Boolean var12 = (Boolean)var3.get(var20);
               var9.set(var0, var12);
            }

            ++var20;
         }

         if(var19.size() == 1) {
            Field var22 = (Field)var19.get(0);
            return var22;
         }
      } catch (Exception var13) {
         Config.warn(var13.getClass().getName() + " " + var13.getMessage());
      }

      Config.warn("Error finding Chunk.hasEntities");
      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
