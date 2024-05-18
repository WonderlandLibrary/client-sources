package net.minecraft.src;

public class ChunkLoader
{
    public static AnvilConverterData load(final NBTTagCompound par0NBTTagCompound) {
        final int var1 = par0NBTTagCompound.getInteger("xPos");
        final int var2 = par0NBTTagCompound.getInteger("zPos");
        final AnvilConverterData var3 = new AnvilConverterData(var1, var2);
        var3.blocks = par0NBTTagCompound.getByteArray("Blocks");
        var3.data = new NibbleArrayReader(par0NBTTagCompound.getByteArray("Data"), 7);
        var3.skyLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("SkyLight"), 7);
        var3.blockLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("BlockLight"), 7);
        var3.heightmap = par0NBTTagCompound.getByteArray("HeightMap");
        var3.terrainPopulated = par0NBTTagCompound.getBoolean("TerrainPopulated");
        var3.entities = par0NBTTagCompound.getTagList("Entities");
        var3.tileEntities = par0NBTTagCompound.getTagList("TileEntities");
        var3.tileTicks = par0NBTTagCompound.getTagList("TileTicks");
        try {
            var3.lastUpdated = par0NBTTagCompound.getLong("LastUpdate");
        }
        catch (ClassCastException var4) {
            var3.lastUpdated = par0NBTTagCompound.getInteger("LastUpdate");
        }
        return var3;
    }
    
    public static void convertToAnvilFormat(final AnvilConverterData par0AnvilConverterData, final NBTTagCompound par1NBTTagCompound, final WorldChunkManager par2WorldChunkManager) {
        par1NBTTagCompound.setInteger("xPos", par0AnvilConverterData.x);
        par1NBTTagCompound.setInteger("zPos", par0AnvilConverterData.z);
        par1NBTTagCompound.setLong("LastUpdate", par0AnvilConverterData.lastUpdated);
        final int[] var3 = new int[par0AnvilConverterData.heightmap.length];
        for (int var4 = 0; var4 < par0AnvilConverterData.heightmap.length; ++var4) {
            var3[var4] = par0AnvilConverterData.heightmap[var4];
        }
        par1NBTTagCompound.setIntArray("HeightMap", var3);
        par1NBTTagCompound.setBoolean("TerrainPopulated", par0AnvilConverterData.terrainPopulated);
        final NBTTagList var5 = new NBTTagList("Sections");
        for (int var6 = 0; var6 < 8; ++var6) {
            boolean var7 = true;
            for (int var8 = 0; var8 < 16 && var7; ++var8) {
                for (int var9 = 0; var9 < 16 && var7; ++var9) {
                    for (int var10 = 0; var10 < 16; ++var10) {
                        final int var11 = var8 << 11 | var10 << 7 | var9 + (var6 << 4);
                        final byte var12 = par0AnvilConverterData.blocks[var11];
                        if (var12 != 0) {
                            var7 = false;
                            break;
                        }
                    }
                }
            }
            if (!var7) {
                final byte[] var13 = new byte[4096];
                final NibbleArray var14 = new NibbleArray(var13.length, 4);
                final NibbleArray var15 = new NibbleArray(var13.length, 4);
                final NibbleArray var16 = new NibbleArray(var13.length, 4);
                for (int var17 = 0; var17 < 16; ++var17) {
                    for (int var18 = 0; var18 < 16; ++var18) {
                        for (int var19 = 0; var19 < 16; ++var19) {
                            final int var20 = var17 << 11 | var19 << 7 | var18 + (var6 << 4);
                            final byte var21 = par0AnvilConverterData.blocks[var20];
                            var13[var18 << 8 | var19 << 4 | var17] = (byte)(var21 & 0xFF);
                            var14.set(var17, var18, var19, par0AnvilConverterData.data.get(var17, var18 + (var6 << 4), var19));
                            var15.set(var17, var18, var19, par0AnvilConverterData.skyLight.get(var17, var18 + (var6 << 4), var19));
                            var16.set(var17, var18, var19, par0AnvilConverterData.blockLight.get(var17, var18 + (var6 << 4), var19));
                        }
                    }
                }
                final NBTTagCompound var22 = new NBTTagCompound();
                var22.setByte("Y", (byte)(var6 & 0xFF));
                var22.setByteArray("Blocks", var13);
                var22.setByteArray("Data", var14.data);
                var22.setByteArray("SkyLight", var15.data);
                var22.setByteArray("BlockLight", var16.data);
                var5.appendTag(var22);
            }
        }
        par1NBTTagCompound.setTag("Sections", var5);
        final byte[] var23 = new byte[256];
        for (int var24 = 0; var24 < 16; ++var24) {
            for (int var8 = 0; var8 < 16; ++var8) {
                var23[var8 << 4 | var24] = (byte)(par2WorldChunkManager.getBiomeGenAt(par0AnvilConverterData.x << 4 | var24, par0AnvilConverterData.z << 4 | var8).biomeID & 0xFF);
            }
        }
        par1NBTTagCompound.setByteArray("Biomes", var23);
        par1NBTTagCompound.setTag("Entities", par0AnvilConverterData.entities);
        par1NBTTagCompound.setTag("TileEntities", par0AnvilConverterData.tileEntities);
        if (par0AnvilConverterData.tileTicks != null) {
            par1NBTTagCompound.setTag("TileTicks", par0AnvilConverterData.tileTicks);
        }
    }
}
