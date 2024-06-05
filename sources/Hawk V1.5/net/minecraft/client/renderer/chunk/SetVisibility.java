package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private final BitSet field_178622_b;
   private static final String __OBFID = "CL_00002448";
   private static final int field_178623_a = EnumFacing.values().length;

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(' ');
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      int var4;
      EnumFacing var5;
      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         var1.append(' ').append(var5.toString().toUpperCase().charAt(0));
      }

      var1.append('\n');
      var2 = EnumFacing.values();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         var1.append(var5.toString().toUpperCase().charAt(0));
         EnumFacing[] var6 = EnumFacing.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            EnumFacing var9 = var6[var8];
            if (var5 == var9) {
               var1.append("  ");
            } else {
               boolean var10 = this.func_178621_a(var5, var9);
               var1.append(' ').append((char)(var10 ? 'Y' : 'n'));
            }
         }

         var1.append('\n');
      }

      return String.valueOf(var1);
   }

   public void func_178619_a(EnumFacing var1, EnumFacing var2, boolean var3) {
      this.field_178622_b.set(var1.ordinal() + var2.ordinal() * field_178623_a, var3);
      this.field_178622_b.set(var2.ordinal() + var1.ordinal() * field_178623_a, var3);
   }

   public void func_178620_a(Set var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         EnumFacing var3 = (EnumFacing)var2.next();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            EnumFacing var5 = (EnumFacing)var4.next();
            this.func_178619_a(var3, var5, true);
         }
      }

   }

   public void func_178618_a(boolean var1) {
      this.field_178622_b.set(0, this.field_178622_b.size(), var1);
   }

   public SetVisibility() {
      this.field_178622_b = new BitSet(field_178623_a * field_178623_a);
   }

   public boolean func_178621_a(EnumFacing var1, EnumFacing var2) {
      return this.field_178622_b.get(var1.ordinal() + var2.ordinal() * field_178623_a);
   }
}
