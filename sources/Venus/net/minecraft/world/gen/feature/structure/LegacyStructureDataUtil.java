/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureIndexesSavedData;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class LegacyStructureDataUtil {
    private static final Map<String, String> field_208220_b = Util.make(Maps.newHashMap(), LegacyStructureDataUtil::lambda$static$0);
    private static final Map<String, String> field_208221_c = Util.make(Maps.newHashMap(), LegacyStructureDataUtil::lambda$static$1);
    private final boolean field_208222_d;
    private final Map<String, Long2ObjectMap<CompoundNBT>> field_208223_e = Maps.newHashMap();
    private final Map<String, StructureIndexesSavedData> field_208224_f = Maps.newHashMap();
    private final List<String> field_215132_f;
    private final List<String> field_215133_g;

    public LegacyStructureDataUtil(@Nullable DimensionSavedDataManager dimensionSavedDataManager, List<String> list, List<String> list2) {
        this.field_215132_f = list;
        this.field_215133_g = list2;
        this.func_212184_a(dimensionSavedDataManager);
        boolean bl = false;
        for (String string : this.field_215133_g) {
            bl |= this.field_208223_e.get(string) != null;
        }
        this.field_208222_d = bl;
    }

    public void func_208216_a(long l) {
        for (String string : this.field_215132_f) {
            StructureIndexesSavedData structureIndexesSavedData = this.field_208224_f.get(string);
            if (structureIndexesSavedData == null || !structureIndexesSavedData.func_208023_c(l)) continue;
            structureIndexesSavedData.func_201762_c(l);
            structureIndexesSavedData.markDirty();
        }
    }

    public CompoundNBT func_212181_a(CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Level");
        ChunkPos chunkPos = new ChunkPos(compoundNBT2.getInt("xPos"), compoundNBT2.getInt("zPos"));
        if (this.func_208209_a(chunkPos.x, chunkPos.z)) {
            compoundNBT = this.func_212182_a(compoundNBT, chunkPos);
        }
        CompoundNBT compoundNBT3 = compoundNBT2.getCompound("Structures");
        CompoundNBT compoundNBT4 = compoundNBT3.getCompound("References");
        for (String string : this.field_215133_g) {
            Structure structure = (Structure)Structure.field_236365_a_.get(string.toLowerCase(Locale.ROOT));
            if (compoundNBT4.contains(string, 1) || structure == null) continue;
            int n = 8;
            LongArrayList longArrayList = new LongArrayList();
            for (int i = chunkPos.x - 8; i <= chunkPos.x + 8; ++i) {
                for (int j = chunkPos.z - 8; j <= chunkPos.z + 8; ++j) {
                    if (!this.func_208211_a(i, j, string)) continue;
                    longArrayList.add(ChunkPos.asLong(i, j));
                }
            }
            compoundNBT4.putLongArray(string, longArrayList);
        }
        compoundNBT3.put("References", compoundNBT4);
        compoundNBT2.put("Structures", compoundNBT3);
        compoundNBT.put("Level", compoundNBT2);
        return compoundNBT;
    }

    private boolean func_208211_a(int n, int n2, String string) {
        if (!this.field_208222_d) {
            return true;
        }
        return this.field_208223_e.get(string) != null && this.field_208224_f.get(field_208220_b.get(string)).func_208024_b(ChunkPos.asLong(n, n2));
    }

    private boolean func_208209_a(int n, int n2) {
        if (!this.field_208222_d) {
            return true;
        }
        for (String string : this.field_215133_g) {
            if (this.field_208223_e.get(string) == null || !this.field_208224_f.get(field_208220_b.get(string)).func_208023_c(ChunkPos.asLong(n, n2))) continue;
            return false;
        }
        return true;
    }

    private CompoundNBT func_212182_a(CompoundNBT compoundNBT, ChunkPos chunkPos) {
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Level");
        CompoundNBT compoundNBT3 = compoundNBT2.getCompound("Structures");
        CompoundNBT compoundNBT4 = compoundNBT3.getCompound("Starts");
        for (String string : this.field_215133_g) {
            CompoundNBT compoundNBT5;
            Long2ObjectMap<CompoundNBT> long2ObjectMap = this.field_208223_e.get(string);
            if (long2ObjectMap == null) continue;
            long l = chunkPos.asLong();
            if (!this.field_208224_f.get(field_208220_b.get(string)).func_208023_c(l) || (compoundNBT5 = (CompoundNBT)long2ObjectMap.get(l)) == null) continue;
            compoundNBT4.put(string, compoundNBT5);
        }
        compoundNBT3.put("Starts", compoundNBT4);
        compoundNBT2.put("Structures", compoundNBT3);
        compoundNBT.put("Level", compoundNBT2);
        return compoundNBT;
    }

    private void func_212184_a(@Nullable DimensionSavedDataManager dimensionSavedDataManager) {
        if (dimensionSavedDataManager != null) {
            for (String string : this.field_215132_f) {
                INBT iNBT;
                Object object;
                CompoundNBT compoundNBT = new CompoundNBT();
                try {
                    compoundNBT = dimensionSavedDataManager.load(string, 1493).getCompound("data").getCompound("Features");
                    if (compoundNBT.isEmpty()) {
                        continue;
                    }
                } catch (IOException iOException) {
                    // empty catch block
                }
                for (String string2 : compoundNBT.keySet()) {
                    String string3;
                    String string4;
                    object = compoundNBT.getCompound(string2);
                    long l = ChunkPos.asLong(((CompoundNBT)object).getInt("ChunkX"), ((CompoundNBT)object).getInt("ChunkZ"));
                    iNBT = ((CompoundNBT)object).getList("Children", 10);
                    if (!((ListNBT)iNBT).isEmpty() && (string4 = field_208221_c.get(string3 = ((ListNBT)iNBT).getCompound(0).getString("id"))) != null) {
                        ((CompoundNBT)object).putString("id", string4);
                    }
                    string3 = ((CompoundNBT)object).getString("id");
                    this.field_208223_e.computeIfAbsent(string3, LegacyStructureDataUtil::lambda$func_212184_a$2).put(l, object);
                }
                String string4 = string + "_index";
                StructureIndexesSavedData object22 = dimensionSavedDataManager.getOrCreate(() -> LegacyStructureDataUtil.lambda$func_212184_a$3(string4), string4);
                if (!object22.getAll().isEmpty()) {
                    this.field_208224_f.put(string, object22);
                    continue;
                }
                object = new StructureIndexesSavedData(string4);
                this.field_208224_f.put(string, (StructureIndexesSavedData)object);
                for (String string5 : compoundNBT.keySet()) {
                    iNBT = compoundNBT.getCompound(string5);
                    ((StructureIndexesSavedData)object).func_201763_a(ChunkPos.asLong(((CompoundNBT)iNBT).getInt("ChunkX"), ((CompoundNBT)iNBT).getInt("ChunkZ")));
                }
                ((WorldSavedData)object).markDirty();
            }
        }
    }

    public static LegacyStructureDataUtil func_236992_a_(RegistryKey<World> registryKey, @Nullable DimensionSavedDataManager dimensionSavedDataManager) {
        if (registryKey == World.OVERWORLD) {
            return new LegacyStructureDataUtil(dimensionSavedDataManager, ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"), ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument"));
        }
        if (registryKey == World.THE_NETHER) {
            ImmutableList<String> immutableList = ImmutableList.of("Fortress");
            return new LegacyStructureDataUtil(dimensionSavedDataManager, immutableList, immutableList);
        }
        if (registryKey == World.THE_END) {
            ImmutableList<String> immutableList = ImmutableList.of("EndCity");
            return new LegacyStructureDataUtil(dimensionSavedDataManager, immutableList, immutableList);
        }
        throw new RuntimeException(String.format("Unknown dimension type : %s", registryKey));
    }

    private static StructureIndexesSavedData lambda$func_212184_a$3(String string) {
        return new StructureIndexesSavedData(string);
    }

    private static Long2ObjectMap lambda$func_212184_a$2(String string) {
        return new Long2ObjectOpenHashMap();
    }

    private static void lambda$static$1(HashMap hashMap) {
        hashMap.put("Iglu", "Igloo");
        hashMap.put("TeDP", "Desert_Pyramid");
        hashMap.put("TeJP", "Jungle_Pyramid");
        hashMap.put("TeSH", "Swamp_Hut");
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("Village", "Village");
        hashMap.put("Mineshaft", "Mineshaft");
        hashMap.put("Mansion", "Mansion");
        hashMap.put("Igloo", "Temple");
        hashMap.put("Desert_Pyramid", "Temple");
        hashMap.put("Jungle_Pyramid", "Temple");
        hashMap.put("Swamp_Hut", "Temple");
        hashMap.put("Stronghold", "Stronghold");
        hashMap.put("Monument", "Monument");
        hashMap.put("Fortress", "Fortress");
        hashMap.put("EndCity", "EndCity");
    }
}

