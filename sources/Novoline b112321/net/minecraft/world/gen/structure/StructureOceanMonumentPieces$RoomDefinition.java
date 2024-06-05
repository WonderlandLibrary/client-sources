package net.minecraft.world.gen.structure;

import net.minecraft.util.EnumFacing;

class StructureOceanMonumentPieces$RoomDefinition {
   int field_175967_a;
   StructureOceanMonumentPieces$RoomDefinition[] field_175965_b = new StructureOceanMonumentPieces$RoomDefinition[6];
   boolean[] field_175966_c = new boolean[6];
   boolean field_175963_d;
   boolean field_175964_e;
   int field_175962_f;

   public StructureOceanMonumentPieces$RoomDefinition(int var1) {
      this.field_175967_a = var1;
   }

   public void func_175957_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2) {
      this.field_175965_b[var1.getIndex()] = var2;
      var2.field_175965_b[var1.getOpposite().getIndex()] = this;
   }

   public void func_175958_a() {
      for(int var1 = 0; var1 < 6; ++var1) {
         this.field_175966_c[var1] = this.field_175965_b[var1] != null;
      }

   }

   public boolean func_175959_a(int var1) {
      if(this.field_175964_e) {
         return true;
      } else {
         this.field_175962_f = var1;

         for(int var2 = 0; var2 < 6; ++var2) {
            if(this.field_175965_b[var2] != null && this.field_175966_c[var2] && this.field_175965_b[var2].field_175962_f != var1 && this.field_175965_b[var2].func_175959_a(var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_175961_b() {
      return this.field_175967_a >= 75;
   }

   public int func_175960_c() {
      int var1 = 0;

      for(int var2 = 0; var2 < 6; ++var2) {
         if(this.field_175966_c[var2]) {
            ++var1;
         }
      }

      return var1;
   }
}
