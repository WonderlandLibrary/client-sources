package net.minecraft.client.renderer.chunk;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int COUNT_FACES = EnumFacing.values().length;
   private long bits;

   public void setManyVisible(Set<EnumFacing> p_178620_1_) {
      Iterator var2 = p_178620_1_.iterator();

      while(var2.hasNext()) {
         EnumFacing enumfacing = (EnumFacing)var2.next();
         Iterator var4 = p_178620_1_.iterator();

         while(var4.hasNext()) {
            EnumFacing enumfacing1 = (EnumFacing)var4.next();
            this.setVisible(enumfacing, enumfacing1, true);
         }
      }

   }

   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
      this.setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
      this.setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
   }

   public void setAllVisible(boolean visible) {
      if (visible) {
         this.bits = -1L;
      } else {
         this.bits = 0L;
      }

   }

   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
      return this.getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      int var4;
      EnumFacing enumfacing2;
      for(var4 = 0; var4 < var3; ++var4) {
         enumfacing2 = var2[var4];
         stringbuilder.append(' ').append(enumfacing2.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');
      var2 = EnumFacing.values();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         enumfacing2 = var2[var4];
         stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));
         EnumFacing[] var6 = EnumFacing.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            EnumFacing enumfacing1 = var6[var8];
            if (enumfacing2 == enumfacing1) {
               stringbuilder.append("  ");
            } else {
               boolean flag = this.isVisible(enumfacing2, enumfacing1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }

   private boolean getBit(int p_getBit_1_) {
      return (this.bits & (long)(1 << p_getBit_1_)) != 0L;
   }

   private void setBit(int p_setBit_1_, boolean p_setBit_2_) {
      if (p_setBit_2_) {
         this.setBit(p_setBit_1_);
      } else {
         this.clearBit(p_setBit_1_);
      }

   }

   private void setBit(int p_setBit_1_) {
      this.bits |= (long)(1 << p_setBit_1_);
   }

   private void clearBit(int p_clearBit_1_) {
      this.bits &= (long)(~(1 << p_clearBit_1_));
   }
}
