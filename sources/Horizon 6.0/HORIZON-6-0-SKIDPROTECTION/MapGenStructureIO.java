package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class MapGenStructureIO
{
    private static final Logger HorizonCode_Horizon_È;
    private static Map Â;
    private static Map Ý;
    private static Map Ø­áŒŠá;
    private static Map Âµá€;
    private static final String Ó = "CL_00000509";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        MapGenStructureIO.Â = Maps.newHashMap();
        MapGenStructureIO.Ý = Maps.newHashMap();
        MapGenStructureIO.Ø­áŒŠá = Maps.newHashMap();
        MapGenStructureIO.Âµá€ = Maps.newHashMap();
        Â(StructureMineshaftStart.class, "Mineshaft");
        Â(MapGenVillage.HorizonCode_Horizon_È.class, "Village");
        Â(MapGenNetherBridge.HorizonCode_Horizon_È.class, "Fortress");
        Â(MapGenStronghold.HorizonCode_Horizon_È.class, "Stronghold");
        Â(MapGenScatteredFeature.HorizonCode_Horizon_È.class, "Temple");
        Â(StructureOceanMonument.HorizonCode_Horizon_È.class, "Monument");
        StructureMineshaftPieces.HorizonCode_Horizon_È();
        StructureVillagePieces.HorizonCode_Horizon_È();
        StructureNetherBridgePieces.HorizonCode_Horizon_È();
        StructureStrongholdPieces.HorizonCode_Horizon_È();
        ComponentScatteredFeaturePieces.HorizonCode_Horizon_È();
        StructureOceanMonumentPieces.HorizonCode_Horizon_È();
    }
    
    private static void Â(final Class p_143034_0_, final String p_143034_1_) {
        MapGenStructureIO.Â.put(p_143034_1_, p_143034_0_);
        MapGenStructureIO.Ý.put(p_143034_0_, p_143034_1_);
    }
    
    static void HorizonCode_Horizon_È(final Class p_143031_0_, final String p_143031_1_) {
        MapGenStructureIO.Ø­áŒŠá.put(p_143031_1_, p_143031_0_);
        MapGenStructureIO.Âµá€.put(p_143031_0_, p_143031_1_);
    }
    
    public static String HorizonCode_Horizon_È(final StructureStart p_143033_0_) {
        return MapGenStructureIO.Ý.get(p_143033_0_.getClass());
    }
    
    public static String HorizonCode_Horizon_È(final StructureComponent p_143036_0_) {
        return MapGenStructureIO.Âµá€.get(p_143036_0_.getClass());
    }
    
    public static StructureStart HorizonCode_Horizon_È(final NBTTagCompound p_143035_0_, final World worldIn) {
        StructureStart var2 = null;
        try {
            final Class var3 = MapGenStructureIO.Â.get(p_143035_0_.áˆºÑ¢Õ("id"));
            if (var3 != null) {
                var2 = var3.newInstance();
            }
        }
        catch (Exception var4) {
            MapGenStructureIO.HorizonCode_Horizon_È.warn("Failed Start with id " + p_143035_0_.áˆºÑ¢Õ("id"));
            var4.printStackTrace();
        }
        if (var2 != null) {
            var2.HorizonCode_Horizon_È(worldIn, p_143035_0_);
        }
        else {
            MapGenStructureIO.HorizonCode_Horizon_È.warn("Skipping Structure with id " + p_143035_0_.áˆºÑ¢Õ("id"));
        }
        return var2;
    }
    
    public static StructureComponent Â(final NBTTagCompound p_143032_0_, final World worldIn) {
        StructureComponent var2 = null;
        try {
            final Class var3 = MapGenStructureIO.Ø­áŒŠá.get(p_143032_0_.áˆºÑ¢Õ("id"));
            if (var3 != null) {
                var2 = var3.newInstance();
            }
        }
        catch (Exception var4) {
            MapGenStructureIO.HorizonCode_Horizon_È.warn("Failed Piece with id " + p_143032_0_.áˆºÑ¢Õ("id"));
            var4.printStackTrace();
        }
        if (var2 != null) {
            var2.HorizonCode_Horizon_È(worldIn, p_143032_0_);
        }
        else {
            MapGenStructureIO.HorizonCode_Horizon_È.warn("Skipping Piece with id " + p_143032_0_.áˆºÑ¢Õ("id"));
        }
        return var2;
    }
}
