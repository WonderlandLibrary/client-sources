package net.minecraft.world;

import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.border.*;

public class WorldProviderHell extends WorldProvider
{
    private static final String[] I;
    
    @Override
    public float calculateCelestialAngle(final long n, final float n2) {
        return 0.5f;
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        final float n = 0.1f;
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i <= (0x96 ^ 0x99)) {
            final float n2 = 1.0f - i / 15.0f;
            this.lightBrightnessTable[i] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
            ++i;
        }
    }
    
    static {
        I();
    }
    
    @Override
    public String getInternalNameSuffix() {
        return WorldProviderHell.I[" ".length()];
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return "".length() != 0;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return "".length() != 0;
    }
    
    @Override
    public Vec3 getFogColor(final float n, final float n2) {
        return new Vec3(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }
    
    @Override
    public boolean doesXZShowFog(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\"5-\u00076\u001e", "lPYoS");
        WorldProviderHell.I[" ".length()] = I("\u00146\u000b\u0015'.*", "KXnaO");
    }
    
    @Override
    public boolean canRespawnHere() {
        return "".length() != 0;
    }
    
    @Override
    public String getDimensionName() {
        return WorldProviderHell.I["".length()];
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
    }
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f);
        this.isHellWorld = (" ".length() != 0);
        this.hasNoSky = (" ".length() != 0);
        this.dimensionId = -" ".length();
    }
    
    @Override
    public WorldBorder getWorldBorder() {
        return new WorldBorder(this) {
            final WorldProviderHell this$0;
            
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
                    if (4 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public double getCenterX() {
                return super.getCenterX() / 8.0;
            }
            
            @Override
            public double getCenterZ() {
                return super.getCenterZ() / 8.0;
            }
        };
    }
}
