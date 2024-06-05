package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Crossing3;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$PieceWeight;

public class StructureNetherBridgePieces$Start extends StructureNetherBridgePieces$Crossing3 {
   public StructureNetherBridgePieces$PieceWeight theNetherBridgePieceWeight;
   public List primaryWeights;
   public List secondaryWeights;
   public List field_74967_d = Lists.newArrayList();

   public StructureNetherBridgePieces$Start() {
   }

   public StructureNetherBridgePieces$Start(Random var1, int var2, int var3) {
      super(var1, var2, var3);
      this.primaryWeights = Lists.newArrayList();

      for(StructureNetherBridgePieces$PieceWeight var7 : StructureNetherBridgePieces.access$100()) {
         var7.field_78827_c = 0;
         this.primaryWeights.add(var7);
      }

      this.secondaryWeights = Lists.newArrayList();

      for(StructureNetherBridgePieces$PieceWeight var11 : StructureNetherBridgePieces.access$200()) {
         var11.field_78827_c = 0;
         this.secondaryWeights.add(var11);
      }

   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
   }
}
