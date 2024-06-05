package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapGenStructureIO {
   private static Map field_143038_b = Maps.newHashMap();
   private static Map field_143037_d = Maps.newHashMap();
   private static Map field_143040_a = Maps.newHashMap();
   private static final String __OBFID = "CL_00000509";
   private static final Logger logger = LogManager.getLogger();
   private static Map field_143039_c = Maps.newHashMap();

   static void registerStructureComponent(Class var0, String var1) {
      field_143039_c.put(var1, var0);
      field_143037_d.put(var0, var1);
   }

   public static StructureStart func_143035_a(NBTTagCompound var0, World var1) {
      StructureStart var2 = null;

      try {
         Class var3 = (Class)field_143040_a.get(var0.getString("id"));
         if (var3 != null) {
            var2 = (StructureStart)var3.newInstance();
         }
      } catch (Exception var4) {
         logger.warn(String.valueOf((new StringBuilder("Failed Start with id ")).append(var0.getString("id"))));
         var4.printStackTrace();
      }

      if (var2 != null) {
         var2.func_143020_a(var1, var0);
      } else {
         logger.warn(String.valueOf((new StringBuilder("Skipping Structure with id ")).append(var0.getString("id"))));
      }

      return var2;
   }

   private static void registerStructure(Class var0, String var1) {
      field_143040_a.put(var1, var0);
      field_143038_b.put(var0, var1);
   }

   public static String func_143036_a(StructureComponent var0) {
      return (String)field_143037_d.get(var0.getClass());
   }

   public static StructureComponent func_143032_b(NBTTagCompound var0, World var1) {
      StructureComponent var2 = null;

      try {
         Class var3 = (Class)field_143039_c.get(var0.getString("id"));
         if (var3 != null) {
            var2 = (StructureComponent)var3.newInstance();
         }
      } catch (Exception var4) {
         logger.warn(String.valueOf((new StringBuilder("Failed Piece with id ")).append(var0.getString("id"))));
         var4.printStackTrace();
      }

      if (var2 != null) {
         var2.func_143009_a(var1, var0);
      } else {
         logger.warn(String.valueOf((new StringBuilder("Skipping Piece with id ")).append(var0.getString("id"))));
      }

      return var2;
   }

   static {
      registerStructure(StructureMineshaftStart.class, "Mineshaft");
      registerStructure(MapGenVillage.Start.class, "Village");
      registerStructure(MapGenNetherBridge.Start.class, "Fortress");
      registerStructure(MapGenStronghold.Start.class, "Stronghold");
      registerStructure(MapGenScatteredFeature.Start.class, "Temple");
      registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
      StructureMineshaftPieces.registerStructurePieces();
      StructureVillagePieces.registerVillagePieces();
      StructureNetherBridgePieces.registerNetherFortressPieces();
      StructureStrongholdPieces.registerStrongholdPieces();
      ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
      StructureOceanMonumentPieces.func_175970_a();
   }

   public static String func_143033_a(StructureStart var0) {
      return (String)field_143038_b.get(var0.getClass());
   }
}
