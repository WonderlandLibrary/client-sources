package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;

public class MapGenStronghold$Start extends StructureStart {
   public MapGenStronghold$Start() {
   }

   public MapGenStronghold$Start(World var1, Random var2, int var3, int var4) {
      super(var3, var4);
      StructureStrongholdPieces.prepareStructurePieces();
      StructureStrongholdPieces$Stairs2 var5 = new StructureStrongholdPieces$Stairs2(0, var2, (var3 << 4) + 2, (var4 << 4) + 2);
      this.components.add(var5);
      var5.buildComponent(var5, this.components, var2);
      List var6 = var5.field_75026_c;

      while(!var6.isEmpty()) {
         int var7 = var2.nextInt(var6.size());
         StructureComponent var8 = (StructureComponent)var6.remove(var7);
         var8.buildComponent(var5, this.components, var2);
      }

      this.updateBoundingBox();
      this.markAvailableHeight(var1, var2, 10);
   }
}
