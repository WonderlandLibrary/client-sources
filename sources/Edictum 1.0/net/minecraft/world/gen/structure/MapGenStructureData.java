package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData
{
    private NBTTagCompound tagCompound = new NBTTagCompound();

    public MapGenStructureData(String name)
    {
        super(name);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.tagCompound = nbt.getCompoundTag("Features");
    }

    public NBTTagCompound func_189551_b(NBTTagCompound p_189551_1_)
    {
        p_189551_1_.setTag("Features", this.tagCompound);
        return p_189551_1_;
    }

    /**
     * Writes the NBT tag of an instance of this structure type to the internal NBT tag, using the chunkcoordinates as
     * the key
     */
    public void writeInstance(NBTTagCompound tagCompoundIn, int chunkX, int chunkZ)
    {
        this.tagCompound.setTag(formatChunkCoords(chunkX, chunkZ), tagCompoundIn);
    }

    public static String formatChunkCoords(int chunkX, int chunkZ)
    {
        return "[" + chunkX + "," + chunkZ + "]";
    }

    public NBTTagCompound getTagCompound()
    {
        return this.tagCompound;
    }
}
