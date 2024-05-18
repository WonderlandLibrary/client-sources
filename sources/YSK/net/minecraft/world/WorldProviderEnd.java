package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;

public class WorldProviderEnd extends WorldProvider
{
    private static final String[] I;
    
    @Override
    public boolean doesXZShowFog(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    @Override
    public String getInternalNameSuffix() {
        return WorldProviderEnd.I[" ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public String getDimensionName() {
        return WorldProviderEnd.I["".length()];
    }
    
    @Override
    public Vec3 getFogColor(final float n, final float n2) {
        final int n3 = 900590 + 193052 + 5961674 + 3463372;
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(n * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        return new Vec3((n3 >> (0x22 ^ 0x32) & 19 + 21 + 175 + 40) / 255.0f * (clamp_float * 0.0f + 0.15f), (n3 >> (0x44 ^ 0x4C) & 196 + 56 - 120 + 123) / 255.0f * (clamp_float * 0.0f + 0.15f), (n3 & 194 + 132 - 137 + 66) / 255.0f * (clamp_float * 0.0f + 0.15f));
    }
    
    @Override
    public boolean canRespawnHere() {
        return "".length() != 0;
    }
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f);
        this.dimensionId = " ".length();
        this.hasNoSky = (" ".length() != 0);
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return this.worldObj.getGroundAboveSeaLevel(new BlockPos(n, "".length(), n2)).getMaterial().blocksMovement();
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public float getCloudHeight() {
        return 8.0f;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isSkyColored() {
        return "".length() != 0;
    }
    
    @Override
    public float calculateCelestialAngle(final long n, final float n2) {
        return 0.0f;
    }
    
    @Override
    public BlockPos getSpawnCoordinate() {
        return new BlockPos(0x7 ^ 0x63, 0xA5 ^ 0x97, "".length());
    }
    
    @Override
    public float[] calcSunriseSunsetColors(final float n, final float n2) {
        return null;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("?\u001a&a\u0015\u0005\u0016", "krCAP");
        WorldProviderEnd.I[" ".length()] = I("\u0014&*\u001c", "KCDxZ");
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getAverageGroundLevel() {
        return 0x25 ^ 0x17;
    }
}
