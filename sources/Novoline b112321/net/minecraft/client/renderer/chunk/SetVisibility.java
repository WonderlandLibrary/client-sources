package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int COUNT_FACES = EnumFacing.values().length;
   private final BitSet bitSet;

   public SetVisibility() {
      this.bitSet = new BitSet(COUNT_FACES * COUNT_FACES);
   }

   public void setManyVisible(Set var1) {
      for(EnumFacing var3 : var1) {
         for(EnumFacing var5 : var1) {
            this.setVisible(var3, var5, true);
         }
      }

   }

   public void setVisible(EnumFacing var1, EnumFacing var2, boolean var3) {
      this.bitSet.set(var1.ordinal() + var2.ordinal() * COUNT_FACES, var3);
      this.bitSet.set(var2.ordinal() + var1.ordinal() * COUNT_FACES, var3);
   }

   public void setAllVisible(boolean var1) {
      this.bitSet.set(0, this.bitSet.size(), var1);
   }

   public boolean isVisible(EnumFacing var1, EnumFacing var2) {
      return this.bitSet.get(var1.ordinal() + var2.ordinal() * COUNT_FACES);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(' ');

      for(EnumFacing var5 : EnumFacing.values()) {
         var1.append(' ').append(var5.toString().toUpperCase().charAt(0));
      }

      var1.append('\n');

      for(EnumFacing var14 : EnumFacing.values()) {
         var1.append(var14.toString().toUpperCase().charAt(0));

         for(EnumFacing var9 : EnumFacing.values()) {
            if(var14 == var9) {
               var1.append("  ");
            } else {
               boolean var10 = this.isVisible(var14, var9);
               var1.append(' ').append('Y');
            }
         }

         var1.append('\n');
      }

      return var1.toString();
   }
}
