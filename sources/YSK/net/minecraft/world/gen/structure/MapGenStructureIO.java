package net.minecraft.world.gen.structure;

import java.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class MapGenStructureIO
{
    private static Map<Class<? extends StructureComponent>, String> componentClassToNameMap;
    private static Map<String, Class<? extends StructureComponent>> componentNameToClassMap;
    private static Map<String, Class<? extends StructureStart>> startNameToClassMap;
    private static Map<Class<? extends StructureStart>, String> startClassToNameMap;
    private static final Logger logger;
    private static final String[] I;
    
    public static String getStructureComponentName(final StructureComponent structureComponent) {
        return MapGenStructureIO.componentClassToNameMap.get(structureComponent.getClass());
    }
    
    private static void I() {
        (I = new String[0x61 ^ 0x71])["".length()] = I(")\u0007\u000b-\u0001\f\u000f\u0003<", "dneHr");
        MapGenStructureIO.I[" ".length()] = I("?!\u0016\u000b\"\u000e-", "iHzgC");
        MapGenStructureIO.I["  ".length()] = I("6\u0018\u0017\u0005\u001b\u0015\u0004\u0016", "pweqi");
        MapGenStructureIO.I["   ".length()] = I("\u0003\u0001 \u0004\r7\u001d=\u0007\u0007", "PuRkc");
        MapGenStructureIO.I[0xB8 ^ 0xBC] = I(" 6$\u0001\u0000\u0011", "tSIql");
        MapGenStructureIO.I[0x9A ^ 0x9F] = I("+>\u001f?\u0018\u0003?\u0005", "fQqJu");
        MapGenStructureIO.I[0x2D ^ 0x2B] = I("1\u0013", "XwnFg");
        MapGenStructureIO.I[0x78 ^ 0x7F] = I("/#\u000e\u0015-\rb4\r)\u001b6G\u000e!\u001d*G\u0010,I", "iBgyH");
        MapGenStructureIO.I[0x70 ^ 0x78] = I("1\u0000", "XdyRa");
        MapGenStructureIO.I[0x47 ^ 0x4E] = I("$\u0011,8&\u001e\u0014\"h\u0005\u0003\b0+\"\u0002\b h!\u001e\u000e-h?\u0013Z", "wzEHV");
        MapGenStructureIO.I[0x60 ^ 0x6A] = I("8\u0017", "QsckR");
        MapGenStructureIO.I[0x41 ^ 0x4A] = I("\r\u0013", "dwrEm");
        MapGenStructureIO.I[0xCD ^ 0xC1] = I("\u001e9%<0<x\u001c90;=l'<,0l91x", "XXLPU");
        MapGenStructureIO.I[0xCD ^ 0xC0] = I("$\u001d", "Mygyn");
        MapGenStructureIO.I[0xD ^ 0x3] = I(">/\u0004\u00166\u0004*\nF\u0016\u0004!\u000e\u0003f\u001a-\u0019\u000ef\u0004 M", "mDmfF");
        MapGenStructureIO.I[0x99 ^ 0x96] = I("*0", "CTwmz");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static void registerStructureComponent(final Class<? extends StructureComponent> clazz, final String s) {
        MapGenStructureIO.componentNameToClassMap.put(s, clazz);
        MapGenStructureIO.componentClassToNameMap.put(clazz, s);
    }
    
    public static String getStructureStartName(final StructureStart structureStart) {
        return MapGenStructureIO.startClassToNameMap.get(structureStart.getClass());
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        MapGenStructureIO.startNameToClassMap = (Map<String, Class<? extends StructureStart>>)Maps.newHashMap();
        MapGenStructureIO.startClassToNameMap = (Map<Class<? extends StructureStart>, String>)Maps.newHashMap();
        MapGenStructureIO.componentNameToClassMap = (Map<String, Class<? extends StructureComponent>>)Maps.newHashMap();
        MapGenStructureIO.componentClassToNameMap = (Map<Class<? extends StructureComponent>, String>)Maps.newHashMap();
        registerStructure(StructureMineshaftStart.class, MapGenStructureIO.I["".length()]);
        registerStructure(MapGenVillage.Start.class, MapGenStructureIO.I[" ".length()]);
        registerStructure(MapGenNetherBridge.Start.class, MapGenStructureIO.I["  ".length()]);
        registerStructure(MapGenStronghold.Start.class, MapGenStructureIO.I["   ".length()]);
        registerStructure(MapGenScatteredFeature.Start.class, MapGenStructureIO.I[0xAC ^ 0xA8]);
        registerStructure(StructureOceanMonument.StartMonument.class, MapGenStructureIO.I[0x4F ^ 0x4A]);
        StructureMineshaftPieces.registerStructurePieces();
        StructureVillagePieces.registerVillagePieces();
        StructureNetherBridgePieces.registerNetherFortressPieces();
        StructureStrongholdPieces.registerStrongholdPieces();
        ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
        StructureOceanMonumentPieces.registerOceanMonumentPieces();
    }
    
    public static StructureComponent getStructureComponent(final NBTTagCompound nbtTagCompound, final World world) {
        StructureComponent structureComponent = null;
        try {
            final Class<? extends StructureComponent> clazz = MapGenStructureIO.componentNameToClassMap.get(nbtTagCompound.getString(MapGenStructureIO.I[0xBB ^ 0xB0]));
            if (clazz != null) {
                structureComponent = (StructureComponent)clazz.newInstance();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            MapGenStructureIO.logger.warn(MapGenStructureIO.I[0x2C ^ 0x20] + nbtTagCompound.getString(MapGenStructureIO.I[0x5C ^ 0x51]));
            ex.printStackTrace();
        }
        if (structureComponent != null) {
            structureComponent.readStructureBaseNBT(world, nbtTagCompound);
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            MapGenStructureIO.logger.warn(MapGenStructureIO.I[0x89 ^ 0x87] + nbtTagCompound.getString(MapGenStructureIO.I[0x62 ^ 0x6D]));
        }
        return structureComponent;
    }
    
    private static void registerStructure(final Class<? extends StructureStart> clazz, final String s) {
        MapGenStructureIO.startNameToClassMap.put(s, clazz);
        MapGenStructureIO.startClassToNameMap.put(clazz, s);
    }
    
    public static StructureStart getStructureStart(final NBTTagCompound nbtTagCompound, final World world) {
        StructureStart structureStart = null;
        try {
            final Class<? extends StructureStart> clazz = MapGenStructureIO.startNameToClassMap.get(nbtTagCompound.getString(MapGenStructureIO.I[0x32 ^ 0x34]));
            if (clazz != null) {
                structureStart = (StructureStart)clazz.newInstance();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            MapGenStructureIO.logger.warn(MapGenStructureIO.I[0x87 ^ 0x80] + nbtTagCompound.getString(MapGenStructureIO.I[0x90 ^ 0x98]));
            ex.printStackTrace();
        }
        if (structureStart != null) {
            structureStart.readStructureComponentsFromNBT(world, nbtTagCompound);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            MapGenStructureIO.logger.warn(MapGenStructureIO.I[0xA9 ^ 0xA0] + nbtTagCompound.getString(MapGenStructureIO.I[0x4F ^ 0x45]));
        }
        return structureStart;
    }
}
