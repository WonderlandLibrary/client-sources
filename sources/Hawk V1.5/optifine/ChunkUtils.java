package optifine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.world.chunk.Chunk;

public class ChunkUtils {
   private static boolean fieldHasEntitiesMissing = false;
   private static Field fieldHasEntities = null;

   private static Field findFieldHasEntities(Chunk var0) {
      try {
         ArrayList var1 = new ArrayList();
         ArrayList var2 = new ArrayList();
         Field[] var3 = Chunk.class.getDeclaredFields();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            Field var5 = var3[var4];
            if (var5.getType() == Boolean.TYPE) {
               var5.setAccessible(true);
               var1.add(var5);
               var2.add(var5.get(var0));
            }
         }

         var0.setHasEntities(false);
         ArrayList var16 = new ArrayList();
         Iterator var17 = var1.iterator();

         while(var17.hasNext()) {
            Field var6 = (Field)var17.next();
            var16.add(var6.get(var0));
         }

         var0.setHasEntities(true);
         ArrayList var18 = new ArrayList();
         Iterator var7 = var1.iterator();

         Field var8;
         while(var7.hasNext()) {
            var8 = (Field)var7.next();
            var18.add(var8.get(var0));
         }

         ArrayList var9 = new ArrayList();

         for(int var10 = 0; var10 < var1.size(); ++var10) {
            Field var11 = (Field)var1.get(var10);
            Boolean var12 = (Boolean)var16.get(var10);
            Boolean var13 = (Boolean)var18.get(var10);
            if (!var12 && var13) {
               var9.add(var11);
               Boolean var14 = (Boolean)var2.get(var10);
               var11.set(var0, var14);
            }
         }

         if (var9.size() == 1) {
            var8 = (Field)var9.get(0);
            return var8;
         }
      } catch (Exception var15) {
         Config.warn(String.valueOf((new StringBuilder(String.valueOf(var15.getClass().getName()))).append(" ").append(var15.getMessage())));
      }

      Config.warn("Error finding Chunk.hasEntities");
      return null;
   }

   public static boolean hasEntities(Chunk var0) {
      if (fieldHasEntities == null) {
         if (fieldHasEntitiesMissing) {
            return true;
         }

         fieldHasEntities = findFieldHasEntities(var0);
         if (fieldHasEntities == null) {
            fieldHasEntitiesMissing = true;
            return true;
         }
      }

      try {
         return fieldHasEntities.getBoolean(var0);
      } catch (Exception var2) {
         Config.warn("Error calling Chunk.hasEntities");
         Config.warn(String.valueOf((new StringBuilder(String.valueOf(var2.getClass().getName()))).append(" ").append(var2.getMessage())));
         fieldHasEntitiesMissing = true;
         return true;
      }
   }
}
