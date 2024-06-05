package net.minecraft.world.gen.structure;

public class StructureVillagePieces$PieceWeight {
   public Class villagePieceClass;
   public final int villagePieceWeight;
   public int b;
   public int villagePiecesLimit;

   public StructureVillagePieces$PieceWeight(Class var1, int var2, int var3) {
      this.villagePieceClass = var1;
      this.villagePieceWeight = var2;
      this.villagePiecesLimit = var3;
   }

   public boolean canSpawnMoreVillagePiecesOfType(int var1) {
      return this.villagePiecesLimit == 0 || this.b < this.villagePiecesLimit;
   }

   public boolean canSpawnMoreVillagePieces() {
      return this.villagePiecesLimit == 0 || this.b < this.villagePiecesLimit;
   }
}
