package net.minecraft.world.gen.structure;

class StructureNetherBridgePieces$PieceWeight {
   public Class weightClass;
   public final int field_78826_b;
   public int field_78827_c;
   public int field_78824_d;
   public boolean field_78825_e;

   public StructureNetherBridgePieces$PieceWeight(Class var1, int var2, int var3, boolean var4) {
      this.weightClass = var1;
      this.field_78826_b = var2;
      this.field_78824_d = var3;
      this.field_78825_e = var4;
   }

   public StructureNetherBridgePieces$PieceWeight(Class var1, int var2, int var3) {
      this(var1, var2, var3, false);
   }

   public boolean func_78822_a(int var1) {
      return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
   }

   public boolean func_78823_a() {
      return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
   }
}
