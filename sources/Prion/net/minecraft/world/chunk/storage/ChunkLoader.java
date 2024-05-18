package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader
{
  private static final String __OBFID = "CL_00000379";
  
  public ChunkLoader() {}
  
  public static AnvilConverterData load(NBTTagCompound nbt)
  {
    int var1 = nbt.getInteger("xPos");
    int var2 = nbt.getInteger("zPos");
    AnvilConverterData var3 = new AnvilConverterData(var1, var2);
    blocks = nbt.getByteArray("Blocks");
    data = new NibbleArrayReader(nbt.getByteArray("Data"), 7);
    skyLight = new NibbleArrayReader(nbt.getByteArray("SkyLight"), 7);
    blockLight = new NibbleArrayReader(nbt.getByteArray("BlockLight"), 7);
    heightmap = nbt.getByteArray("HeightMap");
    terrainPopulated = nbt.getBoolean("TerrainPopulated");
    entities = nbt.getTagList("Entities", 10);
    tileEntities = nbt.getTagList("TileEntities", 10);
    tileTicks = nbt.getTagList("TileTicks", 10);
    
    try
    {
      lastUpdated = nbt.getLong("LastUpdate");
    }
    catch (ClassCastException var5)
    {
      lastUpdated = nbt.getInteger("LastUpdate");
    }
    
    return var3;
  }
  
  public static void convertToAnvilFormat(AnvilConverterData p_76690_0_, NBTTagCompound p_76690_1_, WorldChunkManager p_76690_2_)
  {
    p_76690_1_.setInteger("xPos", x);
    p_76690_1_.setInteger("zPos", z);
    p_76690_1_.setLong("LastUpdate", lastUpdated);
    int[] var3 = new int[heightmap.length];
    
    for (int var4 = 0; var4 < heightmap.length; var4++)
    {
      var3[var4] = heightmap[var4];
    }
    
    p_76690_1_.setIntArray("HeightMap", var3);
    p_76690_1_.setBoolean("TerrainPopulated", terrainPopulated);
    NBTTagList var16 = new NBTTagList();
    

    for (int var5 = 0; var5 < 8; var5++)
    {
      boolean var6 = true;
      
      for (int var7 = 0; (var7 < 16) && (var6); var7++)
      {
        int var8 = 0;
        
        while ((var8 < 16) && (var6))
        {
          int var9 = 0;
          


          while (var9 < 16)
          {
            int var10 = var7 << 11 | var9 << 7 | var8 + (var5 << 4);
            byte var11 = blocks[var10];
            
            if (var11 == 0)
            {
              var9++;
            }
            else
            {
              var6 = false;
            }
          }
          var8++;
        }
      }
      


      if (!var6)
      {
        byte[] var19 = new byte['က'];
        NibbleArray var20 = new NibbleArray();
        NibbleArray var21 = new NibbleArray();
        NibbleArray var22 = new NibbleArray();
        
        for (int var23 = 0; var23 < 16; var23++)
        {
          for (int var12 = 0; var12 < 16; var12++)
          {
            for (int var13 = 0; var13 < 16; var13++)
            {
              int var14 = var23 << 11 | var13 << 7 | var12 + (var5 << 4);
              byte var15 = blocks[var14];
              var19[(var12 << 8 | var13 << 4 | var23)] = ((byte)(var15 & 0xFF));
              var20.set(var23, var12, var13, data.get(var23, var12 + (var5 << 4), var13));
              var21.set(var23, var12, var13, skyLight.get(var23, var12 + (var5 << 4), var13));
              var22.set(var23, var12, var13, blockLight.get(var23, var12 + (var5 << 4), var13));
            }
          }
        }
        
        NBTTagCompound var24 = new NBTTagCompound();
        var24.setByte("Y", (byte)(var5 & 0xFF));
        var24.setByteArray("Blocks", var19);
        var24.setByteArray("Data", var20.getData());
        var24.setByteArray("SkyLight", var21.getData());
        var24.setByteArray("BlockLight", var22.getData());
        var16.appendTag(var24);
      }
    }
    
    p_76690_1_.setTag("Sections", var16);
    byte[] var17 = new byte['Ā'];
    
    for (int var18 = 0; var18 < 16; var18++)
    {
      for (int var7 = 0; var7 < 16; var7++)
      {
        var17[(var7 << 4 | var18)] = ((byte)(func_180300_anet.minecraft.util.BlockPosx << 4 | var18, 0, z << 4 | var7), net.minecraft.world.biome.BiomeGenBase.field_180279_ad).biomeID & 0xFF));
      }
    }
    
    p_76690_1_.setByteArray("Biomes", var17);
    p_76690_1_.setTag("Entities", entities);
    p_76690_1_.setTag("TileEntities", tileEntities);
    
    if (tileTicks != null)
    {
      p_76690_1_.setTag("TileTicks", tileTicks);
    }
  }
  
  public static class AnvilConverterData
  {
    public long lastUpdated;
    public boolean terrainPopulated;
    public byte[] heightmap;
    public NibbleArrayReader blockLight;
    public NibbleArrayReader skyLight;
    public NibbleArrayReader data;
    public byte[] blocks;
    public NBTTagList entities;
    public NBTTagList tileEntities;
    public NBTTagList tileTicks;
    public final int x;
    public final int z;
    private static final String __OBFID = "CL_00000380";
    
    public AnvilConverterData(int p_i1999_1_, int p_i1999_2_)
    {
      x = p_i1999_1_;
      z = p_i1999_2_;
    }
  }
}
