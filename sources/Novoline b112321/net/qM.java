package net;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleXRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleXYRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleYRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleYZRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleZRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$EntryRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentBuilding;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentCoreRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Penthouse;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$SimpleRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$SimpleTopRoom;

public class qM {
   public static void a() {
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$MonumentBuilding.class, "OMB");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$MonumentCoreRoom.class, "OMCR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$DoubleXRoom.class, "OMDXR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$DoubleXYRoom.class, "OMDXYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$DoubleYRoom.class, "OMDYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$DoubleYZRoom.class, "OMDYZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$DoubleZRoom.class, "OMDZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$EntryRoom.class, "OMEntry");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$Penthouse.class, "OMPenthouse");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$SimpleRoom.class, "OMSimple");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces$SimpleTopRoom.class, "OMSimpleT");
   }
}
