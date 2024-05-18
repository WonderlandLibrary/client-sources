package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMesa;

public class MapGenMineshaft extends MapGenStructure
{
    private double chance = 0.004D;

    public MapGenMineshaft()
    {
    }

    public String getStructureName()
    {
        return "Mineshaft";
    }

    public MapGenMineshaft(Map<String, String> p_i2034_1_)
    {
        for (Entry<String, String> entry : p_i2034_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("chance"))
            {
                this.chance = MathHelper.parseDoubleWithDefault((String)entry.getValue(), this.chance);
            }
        }
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        Biome biome = this.worldObj.getBiomeGenForCoords(new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8));
        MapGenMineshaft.Type mapgenmineshaft$type = biome instanceof BiomeMesa ? MapGenMineshaft.Type.MESA : MapGenMineshaft.Type.NORMAL;
        return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ, mapgenmineshaft$type);
    }

    public static enum Type
    {
        NORMAL,
        MESA;

        public static MapGenMineshaft.Type func_189910_a(int p_189910_0_)
        {
            return p_189910_0_ >= 0 && p_189910_0_ < values().length ? values()[p_189910_0_] : NORMAL;
        }
    }
}
