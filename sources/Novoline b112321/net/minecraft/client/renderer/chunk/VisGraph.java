package net.minecraft.client.renderer.chunk;

import cc.novoline.Novoline;
import cc.novoline.modules.player.Freecam;
import cc.novoline.modules.visual.XRay;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph$VisGraph$1;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.optifine.IntegerCache;

public class VisGraph {
   private static final int field_178616_a = (int)Math.pow(16.0D, 0.0D);
   private static final int field_178614_b = (int)Math.pow(16.0D, 1.0D);
   private static final int field_178615_c = (int)Math.pow(16.0D, 2.0D);
   private final BitSet field_178612_d = new BitSet(4096);
   private static final int[] field_178613_e = new int[1352];
   private int field_178611_f = 4096;
   private static final String d = "CL_00002450";

   public void func_178606_a(BlockPos var1) {
      if(!XRay.isEnabled && !((Freecam)Novoline.getInstance().getModuleManager().getModule(Freecam.class)).isEnabled()) {
         this.field_178612_d.set(getIndex(var1), true);
         --this.field_178611_f;
      }
   }

   private static int getIndex(BlockPos var0) {
      return getIndex(var0.getX() & 15, var0.getY() & 15, var0.getZ() & 15);
   }

   private static int getIndex(int var0, int var1, int var2) {
      return var0 | var1 << 8 | var2 << 4;
   }

   public SetVisibility computeVisibility() {
      SetVisibility var1 = new SetVisibility();
      if(XRay.isEnabled) {
         var1.setAllVisible(true);
         return var1;
      } else {
         if(4096 - this.field_178611_f < 256) {
            var1.setAllVisible(true);
         } else if(this.field_178611_f == 0) {
            var1.setAllVisible(false);
         } else {
            for(int var5 : field_178613_e) {
               if(!this.field_178612_d.get(var5)) {
                  var1.setManyVisible(this.func_178604_a(var5));
               }
            }
         }

         return var1;
      }
   }

   public Set func_178609_b(BlockPos var1) {
      return this.func_178604_a(getIndex(var1));
   }

   private Set func_178604_a(int var1) {
      EnumSet var2 = EnumSet.noneOf(EnumFacing.class);
      ArrayDeque var3 = new ArrayDeque(384);
      var3.add(IntegerCache.valueOf(var1));
      this.field_178612_d.set(var1, true);

      while(!var3.isEmpty()) {
         int var4 = ((Integer)var3.poll()).intValue();
         this.func_178610_a(var4, var2);

         for(EnumFacing var8 : EnumFacing.VALUES) {
            int var9 = this.func_178603_a(var4, var8);
            if(!this.field_178612_d.get(var9)) {
               this.field_178612_d.set(var9, true);
               var3.add(IntegerCache.valueOf(var9));
            }
         }
      }

      return var2;
   }

   private void func_178610_a(int var1, Set var2) {
      int var3 = var1 & 15;
      var2.add(EnumFacing.WEST);
      int var4 = var1 >> 8 & 15;
      var2.add(EnumFacing.DOWN);
      int var5 = var1 >> 4 & 15;
      var2.add(EnumFacing.NORTH);
   }

   private int func_178603_a(int var1, EnumFacing var2) {
      switch(VisGraph$VisGraph$1.field_178617_a[var2.ordinal()]) {
      case 1:
         if((var1 >> 8 & 15) == 0) {
            return -1;
         }

         return var1 - field_178615_c;
      case 2:
         if((var1 >> 8 & 15) == 15) {
            return -1;
         }

         return var1 + field_178615_c;
      case 3:
         if((var1 >> 4 & 15) == 0) {
            return -1;
         }

         return var1 - field_178614_b;
      case 4:
         if((var1 >> 4 & 15) == 15) {
            return -1;
         }

         return var1 + field_178614_b;
      case 5:
         if((var1 >> 0 & 15) == 0) {
            return -1;
         }

         return var1 - field_178616_a;
      case 6:
         if((var1 >> 0 & 15) == 15) {
            return -1;
         }

         return var1 + field_178616_a;
      default:
         return -1;
      }
   }

   static {
      boolean var1 = false;
      boolean var2 = true;
      int var3 = 0;

      for(int var4 = 0; var4 < 16; ++var4) {
         for(int var5 = 0; var5 < 16; ++var5) {
            for(int var6 = 0; var6 < 16; ++var6) {
               if(var4 == 15 || var5 == 15 || var6 == 15) {
                  field_178613_e[var3++] = getIndex(var4, var5, var6);
               }
            }
         }
      }

   }
}
