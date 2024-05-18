package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Set<Long> droppedChunksSet = Sets.<Long>newHashSet();
    private final IChunkGenerator chunkGenerator;
    private final IChunkLoader chunkLoader;
    private final Long2ObjectMap<Chunk> id2ChunkMap = new Long2ObjectOpenHashMap(8192);
    private final WorldServer worldObj;

    public ChunkProviderServer(WorldServer worldObjIn, IChunkLoader chunkLoaderIn, IChunkGenerator chunkGeneratorIn)
    {
        this.worldObj = worldObjIn;
        this.chunkLoader = chunkLoaderIn;
        this.chunkGenerator = chunkGeneratorIn;
    }

    public Collection<Chunk> func_189548_a()
    {
        return this.id2ChunkMap.values();
    }

    public void func_189549_a(Chunk p_189549_1_)
    {
        if (this.worldObj.provider.canDropChunk(p_189549_1_.xPosition, p_189549_1_.zPosition))
        {
            this.droppedChunksSet.add(Long.valueOf(ChunkPos.chunkXZ2Int(p_189549_1_.xPosition, p_189549_1_.zPosition)));
            p_189549_1_.field_189550_d = true;
        }
    }

    /**
     * marks all chunks for unload, ignoring those near the spawn
     */
    public void unloadAllChunks()
    {
        for (Chunk chunk : this.id2ChunkMap.values())
        {
            this.func_189549_a(chunk);
        }
    }

    @Nullable
    public Chunk getLoadedChunk(int x, int z)
    {
        long i = ChunkPos.chunkXZ2Int(x, z);
        Chunk chunk = (Chunk)this.id2ChunkMap.get(i);

        if (chunk != null)
        {
            chunk.field_189550_d = false;
        }

        return chunk;
    }

    @Nullable
    public Chunk loadChunk(int x, int z)
    {
        Chunk chunk = this.getLoadedChunk(x, z);

        if (chunk == null)
        {
            chunk = this.loadChunkFromFile(x, z);

            if (chunk != null)
            {
                this.id2ChunkMap.put(ChunkPos.chunkXZ2Int(x, z), chunk);
                chunk.onChunkLoad();
                chunk.populateChunk(this, this.chunkGenerator);
            }
        }

        return chunk;
    }

    public Chunk provideChunk(int x, int z)
    {
        Chunk chunk = this.loadChunk(x, z);

        if (chunk == null)
        {
            long i = ChunkPos.chunkXZ2Int(x, z);

            try
            {
                chunk = this.chunkGenerator.provideChunk(x, z);
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception generating new chunk");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Chunk to be generated");
                crashreportcategory.addCrashSection("Location", String.format("%d,%d", new Object[] {Integer.valueOf(x), Integer.valueOf(z)}));
                crashreportcategory.addCrashSection("Position hash", Long.valueOf(i));
                crashreportcategory.addCrashSection("Generator", this.chunkGenerator);
                throw new ReportedException(crashreport);
            }

            this.id2ChunkMap.put(i, chunk);
            chunk.onChunkLoad();
            chunk.populateChunk(this, this.chunkGenerator);
        }

        return chunk;
    }

    @Nullable
    private Chunk loadChunkFromFile(int x, int z)
    {
        try
        {
            Chunk chunk = this.chunkLoader.loadChunk(this.worldObj, x, z);

            if (chunk != null)
            {
                chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
                this.chunkGenerator.recreateStructures(chunk, x, z);
            }

            return chunk;
        }
        catch (Exception exception)
        {
            LOGGER.error((String)"Couldn\'t load chunk", (Throwable)exception);
            return null;
        }
    }

    private void saveChunkExtraData(Chunk chunkIn)
    {
        try
        {
            this.chunkLoader.saveExtraChunkData(this.worldObj, chunkIn);
        }
        catch (Exception exception)
        {
            LOGGER.error((String)"Couldn\'t save entities", (Throwable)exception);
        }
    }

    private void saveChunkData(Chunk chunkIn)
    {
        try
        {
            chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime());
            this.chunkLoader.saveChunk(this.worldObj, chunkIn);
        }
        catch (IOException ioexception)
        {
            LOGGER.error((String)"Couldn\'t save chunk", (Throwable)ioexception);
        }
        catch (MinecraftException minecraftexception)
        {
            LOGGER.error((String)"Couldn\'t save chunk; already in use by another instance of Minecraft?", (Throwable)minecraftexception);
        }
    }

    public boolean saveChunks(boolean p_186027_1_)
    {
        int i = 0;
        List<Chunk> list = Lists.newArrayList(this.id2ChunkMap.values());

        for (int j = 0; j < ((List)list).size(); ++j)
        {
            Chunk chunk = (Chunk)list.get(j);

            if (p_186027_1_)
            {
                this.saveChunkExtraData(chunk);
            }

            if (chunk.needsSaving(p_186027_1_))
            {
                this.saveChunkData(chunk);
                chunk.setModified(false);
                ++i;

                if (i == 24 && !p_186027_1_)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData()
    {
        this.chunkLoader.saveExtraData();
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        if (!this.worldObj.disableLevelSaving)
        {
            if (!this.droppedChunksSet.isEmpty())
            {
                Iterator<Long> iterator = this.droppedChunksSet.iterator();

                for (int i = 0; i < 100 && iterator.hasNext(); iterator.remove())
                {
                    Long olong = (Long)iterator.next();
                    Chunk chunk = (Chunk)this.id2ChunkMap.get(olong);

                    if (chunk != null && chunk.field_189550_d)
                    {
                        chunk.onChunkUnload();
                        this.saveChunkData(chunk);
                        this.saveChunkExtraData(chunk);
                        this.id2ChunkMap.remove(olong);
                        ++i;
                    }
                }
            }

            this.chunkLoader.chunkTick();
        }

        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return !this.worldObj.disableLevelSaving;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "ServerChunkCache: " + this.id2ChunkMap.size() + " Drop: " + this.droppedChunksSet.size();
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return this.chunkGenerator.getPossibleCreatures(creatureType, pos);
    }

    @Nullable
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        return this.chunkGenerator.getStrongholdGen(worldIn, structureName, position);
    }

    public int getLoadedChunkCount()
    {
        return this.id2ChunkMap.size();
    }

    /**
     * Checks to see if a chunk exists at x, z
     */
    public boolean chunkExists(int x, int z)
    {
        return this.id2ChunkMap.containsKey(ChunkPos.chunkXZ2Int(x, z));
    }
}
