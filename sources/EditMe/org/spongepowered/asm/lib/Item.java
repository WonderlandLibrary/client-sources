package org.spongepowered.asm.lib;

final class Item {
   int index;
   int type;
   int intVal;
   long longVal;
   String strVal1;
   String strVal2;
   String strVal3;
   int hashCode;
   Item next;

   Item() {
   }

   Item(int var1) {
      this.index = var1;
   }

   Item(int var1, Item var2) {
      this.index = var1;
      this.type = var2.type;
      this.intVal = var2.intVal;
      this.longVal = var2.longVal;
      this.strVal1 = var2.strVal1;
      this.strVal2 = var2.strVal2;
      this.strVal3 = var2.strVal3;
      this.hashCode = var2.hashCode;
   }

   void set(int var1) {
      this.type = 3;
      this.intVal = var1;
      this.hashCode = Integer.MAX_VALUE & this.type + var1;
   }

   void set(long var1) {
      this.type = 5;
      this.longVal = var1;
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(float var1) {
      this.type = 4;
      this.intVal = Float.floatToRawIntBits(var1);
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(double var1) {
      this.type = 6;
      this.longVal = Double.doubleToRawLongBits(var1);
      this.hashCode = Integer.MAX_VALUE & this.type + (int)var1;
   }

   void set(int var1, String var2, String var3, String var4) {
      this.type = var1;
      this.strVal1 = var2;
      this.strVal2 = var3;
      this.strVal3 = var4;
      switch(var1) {
      case 7:
         this.intVal = 0;
      case 1:
      case 8:
      case 16:
      case 30:
         this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode();
         return;
      case 12:
         this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode();
         return;
      default:
         this.hashCode = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode() * var4.hashCode();
      }
   }

   void set(String var1, String var2, int var3) {
      this.type = 18;
      this.longVal = (long)var3;
      this.strVal1 = var1;
      this.strVal2 = var2;
      this.hashCode = Integer.MAX_VALUE & 18 + var3 * this.strVal1.hashCode() * this.strVal2.hashCode();
   }

   void set(int var1, int var2) {
      this.type = 33;
      this.intVal = var1;
      this.hashCode = var2;
   }

   boolean isEqualTo(Item var1) {
      switch(this.type) {
      case 1:
      case 7:
      case 8:
      case 16:
      case 30:
         return var1.strVal1.equals(this.strVal1);
      case 2:
      case 9:
      case 10:
      case 11:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      default:
         return var1.strVal1.equals(this.strVal1) && var1.strVal2.equals(this.strVal2) && var1.strVal3.equals(this.strVal3);
      case 3:
      case 4:
         return var1.intVal == this.intVal;
      case 5:
      case 6:
      case 32:
         return var1.longVal == this.longVal;
      case 12:
         return var1.strVal1.equals(this.strVal1) && var1.strVal2.equals(this.strVal2);
      case 18:
         return var1.longVal == this.longVal && var1.strVal1.equals(this.strVal1) && var1.strVal2.equals(this.strVal2);
      case 31:
         return var1.intVal == this.intVal && var1.strVal1.equals(this.strVal1);
      }
   }
}
