package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

public class CompiledChunk {
   private final boolean[] field_178500_b = new boolean[EnumWorldBlockLayer.values().length];
   private final List field_178499_e = Lists.newArrayList();
   private WorldRenderer.State field_178497_g;
   private SetVisibility field_178496_f = new SetVisibility();
   public static final CompiledChunk field_178502_a = new CompiledChunk() {
      private static final String __OBFID = "CL_00002455";

      protected void func_178486_a(EnumWorldBlockLayer var1) {
         throw new UnsupportedOperationException();
      }

      public void func_178493_c(EnumWorldBlockLayer var1) {
         throw new UnsupportedOperationException();
      }

      public boolean func_178495_a(EnumFacing var1, EnumFacing var2) {
         return false;
      }
   };
   private boolean field_178498_d = true;
   private static final String __OBFID = "CL_00002456";
   private final boolean[] field_178501_c = new boolean[EnumWorldBlockLayer.values().length];

   public void func_178488_a(SetVisibility var1) {
      this.field_178496_f = var1;
   }

   public boolean func_178491_b(EnumWorldBlockLayer var1) {
      return !this.field_178500_b[var1.ordinal()];
   }

   public void func_178490_a(TileEntity var1) {
      this.field_178499_e.add(var1);
   }

   public void func_178494_a(WorldRenderer.State var1) {
      this.field_178497_g = var1;
   }

   public boolean func_178489_a() {
      return this.field_178498_d;
   }

   protected void func_178486_a(EnumWorldBlockLayer var1) {
      this.field_178498_d = false;
      this.field_178500_b[var1.ordinal()] = true;
   }

   public WorldRenderer.State func_178487_c() {
      return this.field_178497_g;
   }

   public boolean func_178492_d(EnumWorldBlockLayer var1) {
      return this.field_178501_c[var1.ordinal()];
   }

   public boolean func_178495_a(EnumFacing var1, EnumFacing var2) {
      return this.field_178496_f.func_178621_a(var1, var2);
   }

   public void func_178493_c(EnumWorldBlockLayer var1) {
      this.field_178501_c[var1.ordinal()] = true;
   }

   public List func_178485_b() {
      return this.field_178499_e;
   }
}
