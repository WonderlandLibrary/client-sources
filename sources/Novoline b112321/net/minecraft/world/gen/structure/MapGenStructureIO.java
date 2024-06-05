package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import net.aJt;
import net.qM;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenNetherBridge$Start;
import net.minecraft.world.gen.structure.MapGenScatteredFeature$Start;
import net.minecraft.world.gen.structure.MapGenStronghold$Start;
import net.minecraft.world.gen.structure.MapGenVillage$Start;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraft.world.gen.structure.StructureOceanMonument$StartMonument;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MapGenStructureIO {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map startNameToClassMap = Maps.newHashMap();
   private static final Map startClassToNameMap = Maps.newHashMap();
   private static final Map componentNameToClassMap = Maps.newHashMap();
   private static final Map componentClassToNameMap = Maps.newHashMap();

   private static void registerStructure(Class var0, String var1) {
      startNameToClassMap.put(var1, var0);
      startClassToNameMap.put(var0, var1);
   }

   static void registerStructureComponent(Class var0, String var1) {
      componentNameToClassMap.put(var1, var0);
      componentClassToNameMap.put(var0, var1);
   }

   public static String getStructureStartName(StructureStart var0) {
      return (String)startClassToNameMap.get(var0.getClass());
   }

   public static String getStructureComponentName(StructureComponent var0) {
      return (String)componentClassToNameMap.get(var0.getClass());
   }

   public static StructureStart getStructureStart(NBTTagCompound var0, World var1) {
      StructureStart var2 = null;

      try {
         Class var3 = (Class)startNameToClassMap.get(var0.getString("id"));
         var2 = (StructureStart)var3.newInstance();
      } catch (Exception var4) {
         LOGGER.warn("Failed Start with id " + var0.getString("id"));
         var4.printStackTrace();
      }

      var2.readStructureComponentsFromNBT(var1, var0);
      return var2;
   }

   public static StructureComponent getStructureComponent(NBTTagCompound var0, World var1) {
      StructureComponent var2 = null;

      try {
         Class var3 = (Class)componentNameToClassMap.get(var0.getString("id"));
         var2 = (StructureComponent)var3.newInstance();
      } catch (Exception var4) {
         LOGGER.warn("Failed Piece with id " + var0.getString("id"));
         var4.printStackTrace();
      }

      var2.readStructureBaseNBT(var1, var0);
      return var2;
   }

   static {
      registerStructure(StructureMineshaftStart.class, "Mineshaft");
      registerStructure(MapGenVillage$Start.class, "Village");
      registerStructure(MapGenNetherBridge$Start.class, "Fortress");
      registerStructure(MapGenStronghold$Start.class, "Stronghold");
      registerStructure(MapGenScatteredFeature$Start.class, "Temple");
      registerStructure(StructureOceanMonument$StartMonument.class, "Monument");
      StructureMineshaftPieces.registerStructurePieces();
      StructureVillagePieces.registerVillagePieces();
      StructureNetherBridgePieces.registerNetherFortressPieces();
      StructureStrongholdPieces.registerStrongholdPieces();
      aJt.a();
      qM.a();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
