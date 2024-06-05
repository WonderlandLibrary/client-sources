package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$PieceWeight;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$PortalRoom;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs;

public class StructureStrongholdPieces$Stairs2 extends StructureStrongholdPieces$Stairs {
   public StructureStrongholdPieces$PieceWeight strongholdPieceWeight;
   public StructureStrongholdPieces$PortalRoom strongholdPortalRoom;
   public List field_75026_c = Lists.newArrayList();

   public StructureStrongholdPieces$Stairs2() {
   }

   public StructureStrongholdPieces$Stairs2(int var1, Random var2, int var3, int var4) {
      super(0, var2, var3, var4);
   }

   public BlockPos getBoundingBoxCenter() {
      return this.strongholdPortalRoom != null?this.strongholdPortalRoom.getBoundingBoxCenter():super.getBoundingBoxCenter();
   }
}
