package net;

import java.util.List;
import net.Gh;
import net.aNT;
import net.acE;
import net.adT;
import net.axs;
import net.g4;
import net.n3;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.EntityTracker1_16;

public class aTf extends MetadataRewriter {
   private static int e;

   public aTf(Protocol1_16To1_15_2 var1) {
      a();
      super(var1, EntityTracker1_16.class);
      this.mapType(g4.ZOMBIE_PIGMAN, axs.ZOMBIFIED_PIGLIN);
      this.mapTypes(g4.values(), axs.class);
   }

   public void handleMetadata(int var1, EntityType var2, Metadata var3, List var4, UserConnection var5) throws Exception {
      int var6 = b();
      if(var3.getMetaType() == n3.Slot) {
         adT.c((Item)var3.getValue());
      }

      if(var3.getMetaType() == n3.BlockID) {
         int var7 = ((Integer)var3.getValue()).intValue();
         var3.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(var7)));
      }

      if(var2 != null) {
         label89: {
            if(var2 == axs.AREA_EFFECT_CLOUD) {
               if(var3.getId() != 10) {
                  break label89;
               }

               this.a((Gh)var3.getValue());
            }

            if(var2.isOrHasParent(axs.ABSTRACT_ARROW)) {
               if(var3.getId() == 8) {
                  var4.remove(var3);
               }

               if(var3.getId() > 8) {
                  var3.setId(var3.getId() - 1);
               }
            }
         }

         if(acE.b() == null) {
            ++var6;
            b(var6);
         }

      }
   }

   protected EntityType getTypeFromId(int var1) {
      return aNT.a(var1);
   }

   public static void b(int var0) {
      e = var0;
   }

   public static int b() {
      return e;
   }

   public static int a() {
      int var0 = b();
      return 56;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      if(a() != 0) {
         b(98);
      }

   }
}
