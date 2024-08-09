/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.ITickList;
import net.minecraft.world.LightType;
import net.minecraft.world.SerializableTickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerTickList;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.ServerWorldLightManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkSerializer {
    private static final Logger LOGGER = LogManager.getLogger();

    public static ChunkPrimer read(ServerWorld serverWorld, TemplateManager templateManager, PointOfInterestManager pointOfInterestManager, ChunkPos chunkPos, CompoundNBT compoundNBT) {
        int n;
        ListNBT listNBT;
        Object object3;
        ITickList<Block> iTickList;
        Object object4;
        ChunkGenerator chunkGenerator = serverWorld.getChunkProvider().getChunkGenerator();
        BiomeProvider biomeProvider = chunkGenerator.getBiomeProvider();
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Level");
        ChunkPos chunkPos2 = new ChunkPos(compoundNBT2.getInt("xPos"), compoundNBT2.getInt("zPos"));
        if (!Objects.equals(chunkPos, chunkPos2)) {
            LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", (Object)chunkPos, (Object)chunkPos, (Object)chunkPos2);
        }
        BiomeContainer biomeContainer = new BiomeContainer(serverWorld.func_241828_r().getRegistry(Registry.BIOME_KEY), chunkPos, biomeProvider, compoundNBT2.contains("Biomes", 0) ? compoundNBT2.getIntArray("Biomes") : null);
        UpgradeData upgradeData = compoundNBT2.contains("UpgradeData", 1) ? new UpgradeData(compoundNBT2.getCompound("UpgradeData")) : UpgradeData.EMPTY;
        ChunkPrimerTickList<Block> chunkPrimerTickList = new ChunkPrimerTickList<Block>(ChunkSerializer::lambda$read$0, chunkPos, compoundNBT2.getList("ToBeTicked", 9));
        ChunkPrimerTickList<Fluid> chunkPrimerTickList2 = new ChunkPrimerTickList<Fluid>(ChunkSerializer::lambda$read$1, chunkPos, compoundNBT2.getList("LiquidsToBeTicked", 9));
        boolean bl = compoundNBT2.getBoolean("isLightOn");
        ListNBT listNBT2 = compoundNBT2.getList("Sections", 10);
        int n2 = 16;
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        boolean bl2 = serverWorld.getDimensionType().hasSkyLight();
        ServerChunkProvider serverChunkProvider = serverWorld.getChunkProvider();
        WorldLightManager worldLightManager = ((AbstractChunkProvider)serverChunkProvider).getLightManager();
        if (bl) {
            worldLightManager.retainData(chunkPos, false);
        }
        for (int i = 0; i < listNBT2.size(); ++i) {
            CompoundNBT compoundNBT3 = listNBT2.getCompound(i);
            byte by = compoundNBT3.getByte("Y");
            if (compoundNBT3.contains("Palette", 0) && compoundNBT3.contains("BlockStates", 1)) {
                object4 = new ChunkSection(by << 4);
                ((ChunkSection)object4).getData().readChunkPalette(compoundNBT3.getList("Palette", 10), compoundNBT3.getLongArray("BlockStates"));
                ((ChunkSection)object4).recalculateRefCounts();
                if (!((ChunkSection)object4).isEmpty()) {
                    chunkSectionArray[by] = object4;
                }
                pointOfInterestManager.checkConsistencyWithBlocks(chunkPos, (ChunkSection)object4);
            }
            if (!bl) continue;
            if (compoundNBT3.contains("BlockLight", 0)) {
                worldLightManager.setData(LightType.BLOCK, SectionPos.from(chunkPos, by), new NibbleArray(compoundNBT3.getByteArray("BlockLight")), false);
            }
            if (!bl2 || !compoundNBT3.contains("SkyLight", 0)) continue;
            worldLightManager.setData(LightType.SKY, SectionPos.from(chunkPos, by), new NibbleArray(compoundNBT3.getByteArray("SkyLight")), false);
        }
        long l = compoundNBT2.getLong("InhabitedTime");
        ChunkStatus.Type type = ChunkSerializer.getChunkStatus(compoundNBT);
        if (type == ChunkStatus.Type.LEVELCHUNK) {
            iTickList = compoundNBT2.contains("TileTicks", 0) ? SerializableTickList.create(compoundNBT2.getList("TileTicks", 10), Registry.BLOCK::getKey, Registry.BLOCK::getOrDefault) : chunkPrimerTickList;
            object3 = compoundNBT2.contains("LiquidTicks", 0) ? SerializableTickList.create(compoundNBT2.getList("LiquidTicks", 10), Registry.FLUID::getKey, Registry.FLUID::getOrDefault) : chunkPrimerTickList2;
            object4 = new Chunk(serverWorld.getWorld(), chunkPos, biomeContainer, upgradeData, iTickList, (ITickList<Fluid>)object3, l, chunkSectionArray, arg_0 -> ChunkSerializer.lambda$read$2(compoundNBT2, arg_0));
        } else {
            iTickList = new ChunkPrimer(chunkPos, upgradeData, chunkSectionArray, chunkPrimerTickList, chunkPrimerTickList2);
            ((ChunkPrimer)((Object)iTickList)).setBiomes(biomeContainer);
            object4 = iTickList;
            ((ChunkPrimer)((Object)iTickList)).setInhabitedTime(l);
            ((ChunkPrimer)((Object)iTickList)).setStatus(ChunkStatus.byName(compoundNBT2.getString("Status")));
            if (((ChunkPrimer)((Object)iTickList)).getStatus().isAtLeast(ChunkStatus.FEATURES)) {
                ((ChunkPrimer)((Object)iTickList)).setLightManager(worldLightManager);
            }
            if (!bl && ((ChunkPrimer)((Object)iTickList)).getStatus().isAtLeast(ChunkStatus.LIGHT)) {
                for (BlockPos object22 : BlockPos.getAllInBoxMutable(chunkPos.getXStart(), 0, chunkPos.getZStart(), chunkPos.getXEnd(), 255, chunkPos.getZEnd())) {
                    if (object4.getBlockState(object22).getLightValue() == 0) continue;
                    ((ChunkPrimer)((Object)iTickList)).addLightPosition(object22);
                }
            }
        }
        object4.setLight(bl);
        iTickList = compoundNBT2.getCompound("Heightmaps");
        object3 = EnumSet.noneOf(Heightmap.Type.class);
        for (Heightmap.Type type2 : object4.getStatus().getHeightMaps()) {
            String string = type2.getId();
            if (((CompoundNBT)((Object)iTickList)).contains(string, 1)) {
                object4.setHeightmap(type2, ((CompoundNBT)((Object)iTickList)).getLongArray(string));
                continue;
            }
            ((AbstractCollection)object3).add((Heightmap.Type)type2);
        }
        Heightmap.updateChunkHeightmaps((IChunk)object4, object3);
        CompoundNBT compoundNBT3 = compoundNBT2.getCompound("Structures");
        object4.setStructureStarts(ChunkSerializer.func_235967_a_(templateManager, compoundNBT3, serverWorld.getSeed()));
        object4.setStructureReferences(ChunkSerializer.unpackStructureReferences(chunkPos, compoundNBT3));
        if (compoundNBT2.getBoolean("shouldSave")) {
            object4.setModified(true);
        }
        ListNBT listNBT3 = compoundNBT2.getList("PostProcessing", 9);
        for (int i = 0; i < listNBT3.size(); ++i) {
            listNBT = listNBT3.getList(i);
            for (n = 0; n < listNBT.size(); ++n) {
                object4.addPackedPosition(listNBT.getShort(n), i);
            }
        }
        if (type == ChunkStatus.Type.LEVELCHUNK) {
            return new ChunkPrimerWrapper((Chunk)object4);
        }
        ChunkPrimer chunkPrimer = (ChunkPrimer)object4;
        listNBT = compoundNBT2.getList("Entities", 10);
        for (n = 0; n < listNBT.size(); ++n) {
            chunkPrimer.addEntity(listNBT.getCompound(n));
        }
        ListNBT listNBT4 = compoundNBT2.getList("TileEntities", 10);
        for (int i = 0; i < listNBT4.size(); ++i) {
            CompoundNBT compoundNBT4 = listNBT4.getCompound(i);
            object4.addTileEntity(compoundNBT4);
        }
        ListNBT listNBT5 = compoundNBT2.getList("Lights", 9);
        for (int i = 0; i < listNBT5.size(); ++i) {
            ListNBT listNBT6 = listNBT5.getList(i);
            for (int j = 0; j < listNBT6.size(); ++j) {
                chunkPrimer.addLightValue(listNBT6.getShort(j), i);
            }
        }
        CompoundNBT compoundNBT5 = compoundNBT2.getCompound("CarvingMasks");
        for (String string : compoundNBT5.keySet()) {
            GenerationStage.Carving carving = GenerationStage.Carving.valueOf(string);
            chunkPrimer.setCarvingMask(carving, BitSet.valueOf(compoundNBT5.getByteArray(string)));
        }
        return chunkPrimer;
    }

    public static CompoundNBT write(ServerWorld serverWorld, IChunk iChunk) {
        BiomeContainer biomeContainer;
        Object object;
        Object object2;
        Object object4;
        ChunkPos chunkPos = iChunk.getPos();
        CompoundNBT compoundNBT = new CompoundNBT();
        CompoundNBT compoundNBT2 = new CompoundNBT();
        compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
        compoundNBT.put("Level", compoundNBT2);
        compoundNBT2.putInt("xPos", chunkPos.x);
        compoundNBT2.putInt("zPos", chunkPos.z);
        compoundNBT2.putLong("LastUpdate", serverWorld.getGameTime());
        compoundNBT2.putLong("InhabitedTime", iChunk.getInhabitedTime());
        compoundNBT2.putString("Status", iChunk.getStatus().getName());
        UpgradeData upgradeData = iChunk.getUpgradeData();
        if (!upgradeData.isEmpty()) {
            compoundNBT2.put("UpgradeData", upgradeData.write());
        }
        ChunkSection[] chunkSectionArray = iChunk.getSections();
        ListNBT listNBT = new ListNBT();
        ServerWorldLightManager serverWorldLightManager = serverWorld.getChunkProvider().getLightManager();
        boolean bl = iChunk.hasLight();
        for (int i = -1; i < 17; ++i) {
            int n = i;
            object4 = Arrays.stream(chunkSectionArray).filter(arg_0 -> ChunkSerializer.lambda$write$3(n, arg_0)).findFirst().orElse(Chunk.EMPTY_SECTION);
            NibbleArray object32 = serverWorldLightManager.getLightEngine(LightType.BLOCK).getData(SectionPos.from(chunkPos, n));
            object2 = serverWorldLightManager.getLightEngine(LightType.SKY).getData(SectionPos.from(chunkPos, n));
            if (object4 == Chunk.EMPTY_SECTION && object32 == null && object2 == null) continue;
            object = new CompoundNBT();
            ((CompoundNBT)object).putByte("Y", (byte)(n & 0xFF));
            if (object4 != Chunk.EMPTY_SECTION) {
                ((ChunkSection)object4).getData().writeChunkPalette((CompoundNBT)object, "Palette", "BlockStates");
            }
            if (object32 != null && !object32.isEmpty()) {
                ((CompoundNBT)object).putByteArray("BlockLight", object32.getData());
            }
            if (object2 != null && !((NibbleArray)object2).isEmpty()) {
                ((CompoundNBT)object).putByteArray("SkyLight", ((NibbleArray)object2).getData());
            }
            listNBT.add(object);
        }
        compoundNBT2.put("Sections", listNBT);
        if (bl) {
            compoundNBT2.putBoolean("isLightOn", false);
        }
        if ((biomeContainer = iChunk.getBiomes()) != null) {
            compoundNBT2.putIntArray("Biomes", biomeContainer.getBiomeIds());
        }
        ListNBT listNBT2 = new ListNBT();
        for (BlockPos blockPos : iChunk.getTileEntitiesPos()) {
            object2 = iChunk.getTileEntityNBT(blockPos);
            if (object2 == null) continue;
            listNBT2.add(object2);
        }
        compoundNBT2.put("TileEntities", listNBT2);
        object4 = new ListNBT();
        if (iChunk.getStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
            Chunk chunk = (Chunk)iChunk;
            chunk.setHasEntities(true);
            for (int i = 0; i < chunk.getEntityLists().length; ++i) {
                for (Entity entity2 : chunk.getEntityLists()[i]) {
                    CompoundNBT compoundNBT3;
                    if (!entity2.writeUnlessPassenger(compoundNBT3 = new CompoundNBT())) continue;
                    chunk.setHasEntities(false);
                    ((AbstractList)object4).add(compoundNBT3);
                }
            }
        } else {
            ChunkPrimer chunkPrimer = (ChunkPrimer)iChunk;
            ((AbstractCollection)object4).addAll(chunkPrimer.getEntities());
            compoundNBT2.put("Lights", ChunkSerializer.toNbt(chunkPrimer.getPackedLightPositions()));
            object2 = new CompoundNBT();
            for (GenerationStage.Carving carving : GenerationStage.Carving.values()) {
                BitSet bitSet = chunkPrimer.getCarvingMask(carving);
                if (bitSet == null) continue;
                ((CompoundNBT)object2).putByteArray(carving.toString(), bitSet.toByteArray());
            }
            compoundNBT2.put("CarvingMasks", (INBT)object2);
        }
        compoundNBT2.put("Entities", (INBT)object4);
        ITickList<Block> iTickList = iChunk.getBlocksToBeTicked();
        if (iTickList instanceof ChunkPrimerTickList) {
            compoundNBT2.put("ToBeTicked", ((ChunkPrimerTickList)iTickList).write());
        } else if (iTickList instanceof SerializableTickList) {
            compoundNBT2.put("TileTicks", ((SerializableTickList)iTickList).func_234857_b_());
        } else {
            compoundNBT2.put("TileTicks", ((ServerTickList)serverWorld.getPendingBlockTicks()).func_219503_a(chunkPos));
        }
        ITickList<Fluid> iTickList2 = iChunk.getFluidsToBeTicked();
        if (iTickList2 instanceof ChunkPrimerTickList) {
            compoundNBT2.put("LiquidsToBeTicked", ((ChunkPrimerTickList)iTickList2).write());
        } else if (iTickList2 instanceof SerializableTickList) {
            compoundNBT2.put("LiquidTicks", ((SerializableTickList)iTickList2).func_234857_b_());
        } else {
            compoundNBT2.put("LiquidTicks", ((ServerTickList)serverWorld.getPendingFluidTicks()).func_219503_a(chunkPos));
        }
        compoundNBT2.put("PostProcessing", ChunkSerializer.toNbt(iChunk.getPackedPositions()));
        object = new CompoundNBT();
        for (Map.Entry<Heightmap.Type, Heightmap> entry : iChunk.getHeightmaps()) {
            if (!iChunk.getStatus().getHeightMaps().contains(entry.getKey())) continue;
            ((CompoundNBT)object).put(entry.getKey().getId(), new LongArrayNBT(entry.getValue().getDataArray()));
        }
        compoundNBT2.put("Heightmaps", (INBT)object);
        compoundNBT2.put("Structures", ChunkSerializer.writeStructures(chunkPos, iChunk.getStructureStarts(), iChunk.getStructureReferences()));
        return compoundNBT;
    }

    public static ChunkStatus.Type getChunkStatus(@Nullable CompoundNBT compoundNBT) {
        ChunkStatus chunkStatus;
        if (compoundNBT != null && (chunkStatus = ChunkStatus.byName(compoundNBT.getCompound("Level").getString("Status"))) != null) {
            return chunkStatus.getType();
        }
        return ChunkStatus.Type.PROTOCHUNK;
    }

    private static void readEntities(CompoundNBT compoundNBT, Chunk chunk) {
        ListNBT listNBT = compoundNBT.getList("Entities", 10);
        World world = chunk.getWorld();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT2 = listNBT.getCompound(i);
            EntityType.loadEntityAndExecute(compoundNBT2, world, arg_0 -> ChunkSerializer.lambda$readEntities$4(chunk, arg_0));
            chunk.setHasEntities(false);
        }
        ListNBT listNBT2 = compoundNBT.getList("TileEntities", 10);
        for (int i = 0; i < listNBT2.size(); ++i) {
            CompoundNBT compoundNBT3 = listNBT2.getCompound(i);
            boolean bl = compoundNBT3.getBoolean("keepPacked");
            if (bl) {
                chunk.addTileEntity(compoundNBT3);
                continue;
            }
            BlockPos blockPos = new BlockPos(compoundNBT3.getInt("x"), compoundNBT3.getInt("y"), compoundNBT3.getInt("z"));
            TileEntity tileEntity = TileEntity.readTileEntity(chunk.getBlockState(blockPos), compoundNBT3);
            if (tileEntity == null) continue;
            chunk.addTileEntity(tileEntity);
        }
    }

    private static CompoundNBT writeStructures(ChunkPos chunkPos, Map<Structure<?>, StructureStart<?>> map, Map<Structure<?>, LongSet> map2) {
        CompoundNBT compoundNBT = new CompoundNBT();
        CompoundNBT compoundNBT2 = new CompoundNBT();
        for (Map.Entry<Structure<?>, StructureStart<?>> object : map.entrySet()) {
            compoundNBT2.put(object.getKey().getStructureName(), object.getValue().write(chunkPos.x, chunkPos.z));
        }
        compoundNBT.put("Starts", compoundNBT2);
        CompoundNBT compoundNBT3 = new CompoundNBT();
        for (Map.Entry<Structure<?>, LongSet> entry : map2.entrySet()) {
            compoundNBT3.put(entry.getKey().getStructureName(), new LongArrayNBT(entry.getValue()));
        }
        compoundNBT.put("References", compoundNBT3);
        return compoundNBT;
    }

    private static Map<Structure<?>, StructureStart<?>> func_235967_a_(TemplateManager templateManager, CompoundNBT compoundNBT, long l) {
        HashMap<Structure<?>, StructureStart<?>> hashMap = Maps.newHashMap();
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Starts");
        for (String string : compoundNBT2.keySet()) {
            String string2 = string.toLowerCase(Locale.ROOT);
            Structure structure = (Structure)Structure.field_236365_a_.get(string2);
            if (structure == null) {
                LOGGER.error("Unknown structure start: {}", (Object)string2);
                continue;
            }
            StructureStart<?> structureStart = Structure.func_236393_a_(templateManager, compoundNBT2.getCompound(string), l);
            if (structureStart == null) continue;
            hashMap.put(structure, structureStart);
        }
        return hashMap;
    }

    private static Map<Structure<?>, LongSet> unpackStructureReferences(ChunkPos chunkPos, CompoundNBT compoundNBT) {
        HashMap<Structure<?>, LongSet> hashMap = Maps.newHashMap();
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("References");
        for (String string : compoundNBT2.keySet()) {
            hashMap.put((Structure)Structure.field_236365_a_.get(string.toLowerCase(Locale.ROOT)), new LongOpenHashSet(Arrays.stream(compoundNBT2.getLongArray(string)).filter(arg_0 -> ChunkSerializer.lambda$unpackStructureReferences$5(chunkPos, string, arg_0)).toArray()));
        }
        return hashMap;
    }

    public static ListNBT toNbt(ShortList[] shortListArray) {
        ListNBT listNBT = new ListNBT();
        for (ShortList shortList : shortListArray) {
            ListNBT listNBT2 = new ListNBT();
            if (shortList != null) {
                for (Short s : shortList) {
                    listNBT2.add(ShortNBT.valueOf(s));
                }
            }
            listNBT.add(listNBT2);
        }
        return listNBT;
    }

    private static boolean lambda$unpackStructureReferences$5(ChunkPos chunkPos, String string, long l) {
        ChunkPos chunkPos2 = new ChunkPos(l);
        if (chunkPos2.getChessboardDistance(chunkPos) > 8) {
            LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", (Object)string, (Object)chunkPos2, (Object)chunkPos);
            return true;
        }
        return false;
    }

    private static Entity lambda$readEntities$4(Chunk chunk, Entity entity2) {
        chunk.addEntity(entity2);
        return entity2;
    }

    private static boolean lambda$write$3(int n, ChunkSection chunkSection) {
        return chunkSection != null && chunkSection.getYLocation() >> 4 == n;
    }

    private static void lambda$read$2(CompoundNBT compoundNBT, Chunk chunk) {
        ChunkSerializer.readEntities(compoundNBT, chunk);
    }

    private static boolean lambda$read$1(Fluid fluid) {
        return fluid == null || fluid == Fluids.EMPTY;
    }

    private static boolean lambda$read$0(Block block) {
        return block == null || block.getDefaultState().isAir();
    }
}

