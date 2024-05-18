package net.minecraft.world.storage;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.util.*;

public class WorldInfo
{
    private static final String[] I;
    private int borderWarningTime;
    private long worldTime;
    private WorldSettings.GameType theGameType;
    private double borderSize;
    private boolean allowCommands;
    private long totalTime;
    private int saveVersion;
    private long randomSeed;
    private int borderWarningDistance;
    private WorldType terrainType;
    public static final EnumDifficulty DEFAULT_DIFFICULTY;
    private boolean thundering;
    private double borderSizeLerpTarget;
    private int spawnY;
    private boolean hardcore;
    private double borderCenterX;
    private boolean initialized;
    private String generatorOptions;
    private int thunderTime;
    private String levelName;
    private int rainTime;
    private int cleanWeatherTime;
    private NBTTagCompound playerTag;
    private int dimension;
    private double borderDamagePerBlock;
    private EnumDifficulty difficulty;
    private int spawnX;
    private double borderSafeZone;
    private long borderSizeLerpTime;
    private boolean raining;
    private long lastTimePlayed;
    private boolean mapFeaturesEnabled;
    private long sizeOnDisk;
    private boolean difficultyLocked;
    private GameRules theGameRules;
    private double borderCenterZ;
    private int spawnZ;
    
    public WorldSettings.GameType getGameType() {
        return this.theGameType;
    }
    
    public void getBorderCenterX(final double borderCenterX) {
        this.borderCenterX = borderCenterX;
    }
    
    static long access$7(final WorldInfo worldInfo) {
        return worldInfo.worldTime;
    }
    
    public int getCleanWeatherTime() {
        return this.cleanWeatherTime;
    }
    
    protected WorldInfo() {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = WorldInfo.I["".length()];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = (0x9B ^ 0x9E);
        this.borderWarningTime = (0x98 ^ 0x97);
        this.theGameRules = new GameRules();
    }
    
    static int access$9(final WorldInfo worldInfo) {
        return worldInfo.saveVersion;
    }
    
    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }
    
    public void populateFromWorldSettings(final WorldSettings worldSettings) {
        this.randomSeed = worldSettings.getSeed();
        this.theGameType = worldSettings.getGameType();
        this.mapFeaturesEnabled = worldSettings.isMapFeaturesEnabled();
        this.hardcore = worldSettings.getHardcoreEnabled();
        this.terrainType = worldSettings.getTerrainType();
        this.generatorOptions = worldSettings.getWorldName();
        this.allowCommands = worldSettings.areCommandsAllowed();
    }
    
    public long getBorderLerpTime() {
        return this.borderSizeLerpTime;
    }
    
    static boolean access$13(final WorldInfo worldInfo) {
        return worldInfo.thundering;
    }
    
    static int access$12(final WorldInfo worldInfo) {
        return worldInfo.thunderTime;
    }
    
    public void setBorderSize(final double borderSize) {
        this.borderSize = borderSize;
    }
    
    public WorldInfo(final WorldSettings worldSettings, final String levelName) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = WorldInfo.I[0x2E ^ 0x15];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = (0xAF ^ 0xAA);
        this.borderWarningTime = (0x84 ^ 0x8B);
        this.theGameRules = new GameRules();
        this.populateFromWorldSettings(worldSettings);
        this.levelName = levelName;
        this.difficulty = WorldInfo.DEFAULT_DIFFICULTY;
        this.initialized = ("".length() != 0);
    }
    
    public double getBorderSafeZone() {
        return this.borderSafeZone;
    }
    
    public void setAllowCommands(final boolean allowCommands) {
        this.allowCommands = allowCommands;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public WorldInfo(final WorldInfo worldInfo) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = WorldInfo.I[0x2D ^ 0x11];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = (0x31 ^ 0x34);
        this.borderWarningTime = (0x81 ^ 0x8E);
        this.theGameRules = new GameRules();
        this.randomSeed = worldInfo.randomSeed;
        this.terrainType = worldInfo.terrainType;
        this.generatorOptions = worldInfo.generatorOptions;
        this.theGameType = worldInfo.theGameType;
        this.mapFeaturesEnabled = worldInfo.mapFeaturesEnabled;
        this.spawnX = worldInfo.spawnX;
        this.spawnY = worldInfo.spawnY;
        this.spawnZ = worldInfo.spawnZ;
        this.totalTime = worldInfo.totalTime;
        this.worldTime = worldInfo.worldTime;
        this.lastTimePlayed = worldInfo.lastTimePlayed;
        this.sizeOnDisk = worldInfo.sizeOnDisk;
        this.playerTag = worldInfo.playerTag;
        this.dimension = worldInfo.dimension;
        this.levelName = worldInfo.levelName;
        this.saveVersion = worldInfo.saveVersion;
        this.rainTime = worldInfo.rainTime;
        this.raining = worldInfo.raining;
        this.thunderTime = worldInfo.thunderTime;
        this.thundering = worldInfo.thundering;
        this.hardcore = worldInfo.hardcore;
        this.allowCommands = worldInfo.allowCommands;
        this.initialized = worldInfo.initialized;
        this.theGameRules = worldInfo.theGameRules;
        this.difficulty = worldInfo.difficulty;
        this.difficultyLocked = worldInfo.difficultyLocked;
        this.borderCenterX = worldInfo.borderCenterX;
        this.borderCenterZ = worldInfo.borderCenterZ;
        this.borderSize = worldInfo.borderSize;
        this.borderSizeLerpTime = worldInfo.borderSizeLerpTime;
        this.borderSizeLerpTarget = worldInfo.borderSizeLerpTarget;
        this.borderSafeZone = worldInfo.borderSafeZone;
        this.borderDamagePerBlock = worldInfo.borderDamagePerBlock;
        this.borderWarningTime = worldInfo.borderWarningTime;
        this.borderWarningDistance = worldInfo.borderWarningDistance;
    }
    
    public void getBorderCenterZ(final double borderCenterZ) {
        this.borderCenterZ = borderCenterZ;
    }
    
    static int access$10(final WorldInfo worldInfo) {
        return worldInfo.rainTime;
    }
    
    public void setSpawnX(final int spawnX) {
        this.spawnX = spawnX;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public int getThunderTime() {
        return this.thunderTime;
    }
    
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound2, nbtTagCompound);
        return nbtTagCompound2;
    }
    
    public void setBorderLerpTarget(final double borderSizeLerpTarget) {
        this.borderSizeLerpTarget = borderSizeLerpTarget;
    }
    
    public void setWorldTotalTime(final long totalTime) {
        this.totalTime = totalTime;
    }
    
    public void setSpawnY(final int spawnY) {
        this.spawnY = spawnY;
    }
    
    public void setSpawnZ(final int spawnZ) {
        this.spawnZ = spawnZ;
    }
    
    public void setCleanWeatherTime(final int cleanWeatherTime) {
        this.cleanWeatherTime = cleanWeatherTime;
    }
    
    public void setDifficultyLocked(final boolean difficultyLocked) {
        this.difficultyLocked = difficultyLocked;
    }
    
    public String getGeneratorOptions() {
        return this.generatorOptions;
    }
    
    static int access$5(final WorldInfo worldInfo) {
        return worldInfo.spawnZ;
    }
    
    private void updateTagCompound(final NBTTagCompound nbtTagCompound, final NBTTagCompound nbtTagCompound2) {
        nbtTagCompound.setLong(WorldInfo.I[0x18 ^ 0x25], this.randomSeed);
        nbtTagCompound.setString(WorldInfo.I[0x51 ^ 0x6F], this.terrainType.getWorldTypeName());
        nbtTagCompound.setInteger(WorldInfo.I[0x7B ^ 0x44], this.terrainType.getGeneratorVersion());
        nbtTagCompound.setString(WorldInfo.I[0xD9 ^ 0x99], this.generatorOptions);
        nbtTagCompound.setInteger(WorldInfo.I[0x19 ^ 0x58], this.theGameType.getID());
        nbtTagCompound.setBoolean(WorldInfo.I[0xD ^ 0x4F], this.mapFeaturesEnabled);
        nbtTagCompound.setInteger(WorldInfo.I[0xD3 ^ 0x90], this.spawnX);
        nbtTagCompound.setInteger(WorldInfo.I[0x6D ^ 0x29], this.spawnY);
        nbtTagCompound.setInteger(WorldInfo.I[0xD6 ^ 0x93], this.spawnZ);
        nbtTagCompound.setLong(WorldInfo.I[0x21 ^ 0x67], this.totalTime);
        nbtTagCompound.setLong(WorldInfo.I[0x74 ^ 0x33], this.worldTime);
        nbtTagCompound.setLong(WorldInfo.I[0xD1 ^ 0x99], this.sizeOnDisk);
        nbtTagCompound.setLong(WorldInfo.I[0x1D ^ 0x54], MinecraftServer.getCurrentTimeMillis());
        nbtTagCompound.setString(WorldInfo.I[0x54 ^ 0x1E], this.levelName);
        nbtTagCompound.setInteger(WorldInfo.I[0x8A ^ 0xC1], this.saveVersion);
        nbtTagCompound.setInteger(WorldInfo.I[0x1A ^ 0x56], this.cleanWeatherTime);
        nbtTagCompound.setInteger(WorldInfo.I[0xC1 ^ 0x8C], this.rainTime);
        nbtTagCompound.setBoolean(WorldInfo.I[0x6F ^ 0x21], this.raining);
        nbtTagCompound.setInteger(WorldInfo.I[0x6B ^ 0x24], this.thunderTime);
        nbtTagCompound.setBoolean(WorldInfo.I[0xD7 ^ 0x87], this.thundering);
        nbtTagCompound.setBoolean(WorldInfo.I[0xE2 ^ 0xB3], this.hardcore);
        nbtTagCompound.setBoolean(WorldInfo.I[0x2 ^ 0x50], this.allowCommands);
        nbtTagCompound.setBoolean(WorldInfo.I[0xD9 ^ 0x8A], this.initialized);
        nbtTagCompound.setDouble(WorldInfo.I[0xF ^ 0x5B], this.borderCenterX);
        nbtTagCompound.setDouble(WorldInfo.I[0x31 ^ 0x64], this.borderCenterZ);
        nbtTagCompound.setDouble(WorldInfo.I[0x4D ^ 0x1B], this.borderSize);
        nbtTagCompound.setLong(WorldInfo.I[0xC8 ^ 0x9F], this.borderSizeLerpTime);
        nbtTagCompound.setDouble(WorldInfo.I[0x4 ^ 0x5C], this.borderSafeZone);
        nbtTagCompound.setDouble(WorldInfo.I[0x4 ^ 0x5D], this.borderDamagePerBlock);
        nbtTagCompound.setDouble(WorldInfo.I[0x4F ^ 0x15], this.borderSizeLerpTarget);
        nbtTagCompound.setDouble(WorldInfo.I[0x16 ^ 0x4D], this.borderWarningDistance);
        nbtTagCompound.setDouble(WorldInfo.I[0xDD ^ 0x81], this.borderWarningTime);
        if (this.difficulty != null) {
            nbtTagCompound.setByte(WorldInfo.I[0xB ^ 0x56], (byte)this.difficulty.getDifficultyId());
        }
        nbtTagCompound.setBoolean(WorldInfo.I[0xDD ^ 0x83], this.difficultyLocked);
        nbtTagCompound.setTag(WorldInfo.I[0xE3 ^ 0xBC], this.theGameRules.writeToNBT());
        if (nbtTagCompound2 != null) {
            nbtTagCompound.setTag(WorldInfo.I[0xF1 ^ 0x91], nbtTagCompound2);
        }
    }
    
    public double getBorderCenterZ() {
        return this.borderCenterZ;
    }
    
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.playerTag;
    }
    
    public String getWorldName() {
        return this.levelName;
    }
    
    public void setDifficulty(final EnumDifficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public void setThunderTime(final int thunderTime) {
        this.thunderTime = thunderTime;
    }
    
    static boolean access$16(final WorldInfo worldInfo) {
        return worldInfo.allowCommands;
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public double getBorderDamagePerBlock() {
        return this.borderDamagePerBlock;
    }
    
    public int getBorderWarningTime() {
        return this.borderWarningTime;
    }
    
    public void setBorderDamagePerBlock(final double borderDamagePerBlock) {
        this.borderDamagePerBlock = borderDamagePerBlock;
    }
    
    static long access$6(final WorldInfo worldInfo) {
        return worldInfo.totalTime;
    }
    
    public void setWorldName(final String levelName) {
        this.levelName = levelName;
    }
    
    static boolean access$1(final WorldInfo worldInfo) {
        return worldInfo.mapFeaturesEnabled;
    }
    
    public int getSpawnY() {
        return this.spawnY;
    }
    
    public GameRules getGameRulesInstance() {
        return this.theGameRules;
    }
    
    static String access$2(final WorldInfo worldInfo) {
        return worldInfo.generatorOptions;
    }
    
    public void setRainTime(final int rainTime) {
        this.rainTime = rainTime;
    }
    
    static int access$4(final WorldInfo worldInfo) {
        return worldInfo.spawnY;
    }
    
    public long getWorldTime() {
        return this.worldTime;
    }
    
    public double getBorderSize() {
        return this.borderSize;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    static WorldSettings.GameType access$14(final WorldInfo worldInfo) {
        return worldInfo.theGameType;
    }
    
    public double getBorderLerpTarget() {
        return this.borderSizeLerpTarget;
    }
    
    public void setBorderSafeZone(final double borderSafeZone) {
        this.borderSafeZone = borderSafeZone;
    }
    
    static {
        I();
        DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
    }
    
    public void setRaining(final boolean raining) {
        this.raining = raining;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }
    
    public void setTerrainType(final WorldType terrainType) {
        this.terrainType = terrainType;
    }
    
    public void setWorldTime(final long worldTime) {
        this.worldTime = worldTime;
    }
    
    static WorldType access$0(final WorldInfo worldInfo) {
        return worldInfo.terrainType;
    }
    
    static int access$3(final WorldInfo worldInfo) {
        return worldInfo.spawnX;
    }
    
    public void setHardcore(final boolean hardcore) {
        this.hardcore = hardcore;
    }
    
    public void addToCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x2A ^ 0x4B], new Callable<String>(this) {
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                return String.valueOf(this.this$0.getSeed());
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (3 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x34 ^ 0x56], new Callable<String>(this) {
            final WorldInfo this$0;
            private static final String[] I;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u000e\u0010EPAu0EXQb'IU\u0007\"&EP\u0015it#\u0010\u00103!\u0017\u0010\u0002g1\u000b\u0014\u0013+1\u0001OQb6", "GTeuq");
            }
            
            @Override
            public String call() throws Exception {
                final String s = WorldInfo$2.I["".length()];
                final Object[] array = new Object[0x3D ^ 0x39];
                array["".length()] = WorldInfo.access$0(this.this$0).getWorldTypeID();
                array[" ".length()] = WorldInfo.access$0(this.this$0).getWorldTypeName();
                array["  ".length()] = WorldInfo.access$0(this.this$0).getGeneratorVersion();
                array["   ".length()] = WorldInfo.access$1(this.this$0);
                return String.format(s, array);
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0xA4 ^ 0xC7], new Callable<String>(this) {
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                return WorldInfo.access$2(this.this$0);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (4 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0xD7 ^ 0xB3], new Callable<String>(this) {
            final WorldInfo this$0;
            
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
                    if (0 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return CrashReportCategory.getCoordinateInfo(WorldInfo.access$3(this.this$0), WorldInfo.access$4(this.this$0), WorldInfo.access$5(this.this$0));
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x42 ^ 0x27], new Callable<String>(this) {
            private static final String[] I;
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                final String s = WorldInfo$5.I["".length()];
                final Object[] array = new Object["  ".length()];
                array["".length()] = WorldInfo.access$6(this.this$0);
                array[" ".length()] = WorldInfo.access$7(this.this$0);
                return String.format(s, array);
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("q\nR(\t9\u000bR;\u00019\u000b^oM0N\u0016.\u0011t\u001a\u001b\"\r", "TnrOh");
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
                    if (3 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x45 ^ 0x23], new Callable<String>(this) {
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                return String.valueOf(WorldInfo.access$8(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x3A ^ 0x5D], new Callable<String>(this) {
            private static final String[] I;
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                String s = WorldInfo$7.I["".length()];
                Label_0087: {
                    try {
                        switch (WorldInfo.access$9(this.this$0)) {
                            case 19132: {
                                s = WorldInfo$7.I[" ".length()];
                                "".length();
                                if (2 < 0) {
                                    throw null;
                                }
                                break Label_0087;
                            }
                            case 19133: {
                                s = WorldInfo$7.I["  ".length()];
                                break;
                            }
                        }
                        "".length();
                        if (0 < 0) {
                            throw null;
                        }
                    }
                    catch (Throwable t) {}
                }
                final String s2 = WorldInfo$7.I["   ".length()];
                final Object[] array = new Object["  ".length()];
                array["".length()] = WorldInfo.access$9(this.this$0);
                array[" ".length()] = s;
                return String.format(s2, array);
            }
            
            private static void I() {
                (I = new String[0x50 ^ 0x54])["".length()] = I("\u0000$\n\u0016\u0005\"$^", "UJaxj");
                WorldInfo$7.I[" ".length()] = I("!\u001b\u00114*\u0005\u0017-", "lxCQM");
                WorldInfo$7.I["  ".length()] = I("\u0003<&3'", "BRPZK");
                WorldInfo$7.I["   ".length()] = I("d\u001fl{~\fGdkn'", "TgIKK");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (2 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0x43 ^ 0x2B], new Callable<String>(this) {
            private static final String[] I;
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u001e6\u0002\u001eA8>\u0006\u0015[lr\u000fPI\"8\u001cJAi5B\\A8?\u001e\u001e\u0005)%K\u0004\b!2QPD(wC\u001e\u000e;mKU\u0003e", "LWkpa");
            }
            
            @Override
            public String call() throws Exception {
                final String s = WorldInfo$8.I["".length()];
                final Object[] array = new Object[0x56 ^ 0x52];
                array["".length()] = WorldInfo.access$10(this.this$0);
                array[" ".length()] = WorldInfo.access$11(this.this$0);
                array["  ".length()] = WorldInfo.access$12(this.this$0);
                array["   ".length()] = WorldInfo.access$13(this.this$0);
                return String.format(s, array);
            }
            
            static {
                I();
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
                    if (3 != 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(WorldInfo.I[0xD6 ^ 0xBF], new Callable<String>(this) {
            final WorldInfo this$0;
            private static final String[] I;
            
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                final String s = WorldInfo$9.I["".length()];
                final Object[] array = new Object[0xAE ^ 0xAA];
                array["".length()] = WorldInfo.access$14(this.this$0).getName();
                array[" ".length()] = WorldInfo.access$14(this.this$0).getID();
                array["  ".length()] = WorldInfo.access$15(this.this$0);
                array["   ".length()] = WorldInfo.access$16(this.this$0);
                return String.format(s, array);
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I(">-4 s\u0014#= iYi*e{0\by`7Pby\r2\u000b(:*!\u001cvy`1Wl\u001a-6\u00188*\u007fs\\.", "yLYES");
            }
        });
    }
    
    public int getRainTime() {
        return this.rainTime;
    }
    
    public boolean isRaining() {
        return this.raining;
    }
    
    static int access$8(final WorldInfo worldInfo) {
        return worldInfo.dimension;
    }
    
    public void setThundering(final boolean thundering) {
        this.thundering = thundering;
    }
    
    public void setServerInitialized(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public void setBorderLerpTime(final long borderSizeLerpTime) {
        this.borderSizeLerpTime = borderSizeLerpTime;
    }
    
    private static void I() {
        (I = new String[0x6 ^ 0x6C])["".length()] = I("", "CITwi");
        WorldInfo.I[" ".length()] = I("", "gWKwq");
        WorldInfo.I["  ".length()] = I("\u0001\u0016\u0006#?>$\r\"4", "SwhGP");
        WorldInfo.I["   ".length()] = I("\u0017\u000e\t)7\u0011\u001f\b>\u000b\u0011\u0006\u0002", "pkgLE");
        WorldInfo.I[0xBD ^ 0xB9] = I("\u0010\u0010\b,>\u0016\u0001\t;\u0002\u0016\u0018\u0003", "wufIL");
        WorldInfo.I[0x52 ^ 0x57] = I("\u0001&\t-6\u00077\b:\u0012\u00031\u0014!+\b", "fCgHD");
        WorldInfo.I[0xC6 ^ 0xC0] = I("\u0011\u0011#(\u001c\u0017\u0000\"?8\u0013\u0006>$\u0001\u0018", "vtMMn");
        WorldInfo.I[0xAF ^ 0xA8] = I("?\u0007\u0007\u000699\u0016\u0006\u0011\u0004(\u0016\u0000\f%+", "XbicK");
        WorldInfo.I[0xBF ^ 0xB7] = I("\u001e'\n\b\u0018\u00186\u000b\u001f%\t6\r\u0002\u0004\n", "yBdmj");
        WorldInfo.I[0x8E ^ 0x87] = I("1\u0007\u00045,\u000f\u0016\f", "vfiPx");
        WorldInfo.I[0x7C ^ 0x76] = I("\u000b\b\u0014\u00154'\u001d\u0011!45", "FidSQ");
        WorldInfo.I[0x66 ^ 0x6D] = I("\u0018$\u001f\u0010\r41\u001a$\r&", "UEoVh");
        WorldInfo.I[0xB6 ^ 0xBA] = I("\u001e\u001a\t\u0006/\u0015", "MjhqA");
        WorldInfo.I[0x46 ^ 0x4B] = I("\u0012)\u0011:6\u0018", "AYpMX");
        WorldInfo.I[0x1C ^ 0x12] = I("#$\u0007\u0015-*", "pTfbC");
        WorldInfo.I[0x81 ^ 0x8E] = I(":=<5", "nTQPd");
        WorldInfo.I[0x87 ^ 0x97] = I("\u000f-\u0015-\n&)", "KLlyc");
        WorldInfo.I[0x87 ^ 0x96] = I("3\b1\u001c\u0001\u001a\f", "wiHHh");
        WorldInfo.I[0x8C ^ 0x9E] = I("'\u0019&\u00054\u0007\u0019,\u0014\u0000", "kxUqd");
        WorldInfo.I[0x74 ^ 0x67] = I("\u0007\u0003,\u0015\u001f:.?\u0003;", "TjVpP");
        WorldInfo.I[0x1C ^ 0x8] = I("\"\u0010\u000f\u0002\u0006 \u0014\u0014\u0002", "nuygj");
        WorldInfo.I[0x85 ^ 0x90] = I("\u0006\u0012\u0017\u0003\u0011\u001f\u0019", "pwepx");
        WorldInfo.I[0x3F ^ 0x29] = I("+%\"\r;\u001f,&\u0018!-;\u0013\u0005$-", "HIGlI");
        WorldInfo.I[0x82 ^ 0x95] = I(">\u000f\u0018\u001b2%\u0003\u0014", "Lnquf");
        WorldInfo.I[0x7E ^ 0x66] = I("\u0014\u000e\u0013&?\b\b", "fozHV");
        WorldInfo.I[0x93 ^ 0x8A] = I("'#\u0010+6691,?6", "SKeER");
        WorldInfo.I[0x98 ^ 0x82] = I("\u0016:\"\u001e2\u0007 >\u001e1", "bRWpV");
        WorldInfo.I[0x30 ^ 0x2B] = I("\u001c\u000b\u000b)\u000f\u001b\u0018\u001c", "tjyMl");
        WorldInfo.I[0x3A ^ 0x26] = I("\u00058 \u001c\u0010\r: \u0012\u001c\b", "lVIhy");
        WorldInfo.I[0xAF ^ 0xB2] = I(",9#\u001f:$;#\u00116!", "EWJkS");
        WorldInfo.I[0x9C ^ 0x82] = I("6\u001c%\b%\u0014\u001f$\n39\u0014:", "WpIgR");
        WorldInfo.I[0x68 ^ 0x77] = I("\u0012\"5\u001a\u00070!4\u0018\u0011\u001d**", "sNYup");
        WorldInfo.I[0xAE ^ 0x8E] = I("?$\u0019\u0018\u000f\u001d", "oHxaj");
        WorldInfo.I[0x1E ^ 0x3F] = I("1-\u0018\u001f6\u0013", "aAyfS");
        WorldInfo.I[0x31 ^ 0x13] = I("\u0003<\u0017\u0010\u001d4<\u0015\u001b", "GUzus");
        WorldInfo.I[0xA1 ^ 0x82] = I(">2\u0014*?\f?\u001c<", "ySyOm");
        WorldInfo.I[0x9 ^ 0x2D] = I("\u0001\u0011\u0002\u001403\u001c\n\u0002", "Fpoqb");
        WorldInfo.I[0x83 ^ 0xA6] = I("\n=\b\n,-!\u0002\u0018<", "NTnlE");
        WorldInfo.I[0x40 ^ 0x66] = I("\u0012:\u0003\u0010\u00065&\t\u0002\u0016", "VSevo");
        WorldInfo.I[0x11 ^ 0x36] = I("\u000f\u001e4!=(\u0002>3-\u0007\u00181,1/", "KwRGT");
        WorldInfo.I[0x74 ^ 0x5C] = I(")\u001d,/=\u000e\u0001&=-!\u001b)\"1\t", "mtJIT");
        WorldInfo.I[0x9B ^ 0xB2] = I("\u0015= <\r%\u001176\u001c2 \n", "WRRXh");
        WorldInfo.I[0x1C ^ 0x36] = I("\u00147\u0001\u0015-$\u001b\u0016\u001f<3*+", "VXsqH");
        WorldInfo.I[0x58 ^ 0x73] = I("\u0007\r+\u0000\u00167!<\n\u0007 \u0010\u0003", "EbYds");
        WorldInfo.I[0x49 ^ 0x65] = I(".\u001f\u001e<5\u001e3\t6$\t\u00026", "lplXP");
        WorldInfo.I[0x86 ^ 0xAB] = I(":\b\u0003\u0011\n\n4\u0018\u000f\n", "xgquo");
        WorldInfo.I[0x21 ^ 0xF] = I("$\u001d\u0015!&\u0014!\u000e?&", "frgEC");
        WorldInfo.I[0x45 ^ 0x6A] = I("\u0017\u0018\u001f\u0005\u000e'$\u0004\u001b\u000e\u0019\u0012\u001f\u0011?<\u001a\b", "Uwmak");
        WorldInfo.I[0x86 ^ 0xB6] = I("\u0007\u001c: 27 !>2\t\u0016:4\u0003,\u001e-", "EsHDW");
        WorldInfo.I[0x94 ^ 0xA5] = I(")'\n!\u0007\u0019\u001b\u0011?\u0007'-\n56\n:\u001f \u0016", "kHxEb");
        WorldInfo.I[0x88 ^ 0xBA] = I("\u0016=4\u0003)&\u0001/\u001d)\u001874\u0017\u00185 !\u00028", "TRFgL");
        WorldInfo.I[0xB8 ^ 0x8B] = I("\u0015\u001a%\u000b\r%&6\t\r\r\u001a9\n", "WuWoh");
        WorldInfo.I[0xF6 ^ 0xC2] = I("*\u001f:\u0013\t\u001a#)\u0011\t2\u001f&\u0012", "hpHwl");
        WorldInfo.I[0x5C ^ 0x69] = I("\u0011=\n\u0017?!\u0016\u0019\u001e;47(\u0016(\u0011>\u0017\u00101", "SRxsZ");
        WorldInfo.I[0x76 ^ 0x40] = I("\n\u001f111:4\"85/\u0015\u00130&\n\u001c,6?", "HpCUT");
        WorldInfo.I[0x7E ^ 0x49] = I("(\t1!!\u00181\"7*\u0003\b$\u0007(\u0005\u0005(6", "jfCED");
        WorldInfo.I[0x44 ^ 0x7C] = I("0);,\u001d\u0000\u0011(:\u0016\u001b(.\n\u0014\u001d%\";", "rFIHx");
        WorldInfo.I[0x19 ^ 0x20] = I("2*\u0016\u001e3\u0002\u0012\u0005\b8\u0019+\u0003.?\u001d ", "pEdzV");
        WorldInfo.I[0x92 ^ 0xA8] = I("\u0014!5*\f$\u0019&<\u0007?  \u001a\u0000;+", "VNGNi");
        WorldInfo.I[0x61 ^ 0x5A] = I("", "xyXJL");
        WorldInfo.I[0x5D ^ 0x61] = I("", "RtcXs");
        WorldInfo.I[0x31 ^ 0xC] = I("82)<-\u0007\u0000\"=&", "jSGXB");
        WorldInfo.I[0x61 ^ 0x5F] = I("?(\"=\u001599#*)9 )", "XMLXg");
        WorldInfo.I[0x22 ^ 0x1D] = I("5\u001d\r\u000b*3\f\f\u001c\u000e7\n\u0010\u00077<", "RxcnX");
        WorldInfo.I[0xC9 ^ 0x89] = I("\u0005\u00036\u000f%\u0003\u00127\u0018\u0018\u0012\u00121\u00059\u0011", "bfXjW");
        WorldInfo.I[0x8 ^ 0x49] = I("\u0002\r9\u00173<\u001c1", "ElTrg");
        WorldInfo.I[0x69 ^ 0x2B] = I("\t\u0005\u0003\u0003\u0014%\u0010\u00067\u00147", "DdsEq");
        WorldInfo.I[0x67 ^ 0x24] = I(";\u0005\u0007;\u00060", "hufLh");
        WorldInfo.I[0x6E ^ 0x2A] = I("\u001c\u0000\b04\u0016", "OpiGZ");
        WorldInfo.I[0x85 ^ 0xC0] = I("\u001f*\u0005% \u0016", "LZdRN");
        WorldInfo.I[0xA ^ 0x4C] = I("\u0001\u0007\u00156", "UnxSd");
        WorldInfo.I[0x56 ^ 0x11] = I("\u0002\u000669\u001e+\u0002", "FgOmw");
        WorldInfo.I[0x69 ^ 0x21] = I("5\u000e\u0000$\u0019\b#\u00132=", "fgzAV");
        WorldInfo.I[0x24 ^ 0x6D] = I("\u00009\u0003.\u0015 9\t?!", "LXpZE");
        WorldInfo.I[0x7A ^ 0x30] = I(";)#7\u00169-87", "wLURz");
        WorldInfo.I[0xDE ^ 0x95] = I("\u0010(?\u0017\u0013\t#", "fMMdz");
        WorldInfo.I[0xD1 ^ 0x9D] = I(")4\u000f1(\u001d=\u000b$2/*>97/", "JXjPZ");
        WorldInfo.I[0xE3 ^ 0xAE] = I("7/9\f\u0004,#5", "ENPbP");
        WorldInfo.I[0x7 ^ 0x49] = I(":\u0019.*\u001e&\u001f", "HxGDw");
        WorldInfo.I[0x34 ^ 0x7B] = I("\u0016.\u0014$\u0001\u000745#\b\u0007", "bFaJe");
        WorldInfo.I[0x1A ^ 0x4A] = I("!=-\u0007=0'1\u0007>", "UUXiY");
        WorldInfo.I[0x4D ^ 0x1C] = I("0\u00110-$7\u0002'", "XpBIG");
        WorldInfo.I[0x26 ^ 0x74] = I("()\u000f\u0004\u0014\n*\u000e\u0006\u0002'!\u0010", "IEckc");
        WorldInfo.I[0xF ^ 0x5C] = I("\b7$\u001b$\u00005$\u0015(\u0005", "aYMoM");
        WorldInfo.I[0xC3 ^ 0x97] = I("$\u0006\u00107\u001d\u0014*\u0007=\f\u0003\u001b:", "fibSx");
        WorldInfo.I[0xB ^ 0x5E] = I("\b':\u0001\u00118\u000b-\u000b\u0000/:\u0012", "JHHet");
        WorldInfo.I[0x27 ^ 0x71] = I("(;\u0011*\u0002\u0018\u0007\n4\u0002", "jTcNg");
        WorldInfo.I[0xCC ^ 0x9B] = I("\t.4\u0007&9\u0012/\u0019&\u0007$4\u0013\u0017\",#", "KAFcC");
        WorldInfo.I[0x4D ^ 0x15] = I("\u001197\u000e\u0016!\u0005$\f\u0016\t9+\u000f", "SVEjs");
        WorldInfo.I[0x19 ^ 0x40] = I("\u0011\">/\u000b!\t-&\u000f4(\u001c.\u001c\u0011!#(\u0005", "SMLKn");
        WorldInfo.I[0x10 ^ 0x4A] = I("\u00189\u00045\u0014(\u0005\u001f+\u0014\u00163\u0004!%;$\u00114\u0005", "ZVvQq");
        WorldInfo.I[0x5B ^ 0x0] = I("$\u0005\")5\u0014=1?>\u000f\u00047\u000f<\t\t;>", "fjPMP");
        WorldInfo.I[0xC0 ^ 0x9C] = I("\u0007\u001d47<7%'!7,\u001c!\u00070(\u0017", "ErFSY");
        WorldInfo.I[0x6E ^ 0x33] = I("\u0007 \r2$ <\u0007 4", "CIkTM");
        WorldInfo.I[0x2D ^ 0x73] = I("+3,)%\f/&;5#5)$)\u000b", "oZJOL");
        WorldInfo.I[0x25 ^ 0x7A] = I("?\u0017\u0005\u0011\u0003\r\u001a\r\u0007", "xvhtQ");
        WorldInfo.I[0xD3 ^ 0xB3] = I("\u0006\n\u0000\u0001+$", "VfaxN");
        WorldInfo.I[0xCF ^ 0xAE] = I("$\n\u001d<!H\u001c\u000e<)", "hokYM");
        WorldInfo.I[0xA6 ^ 0xC4] = I("\u0003\u00138\u0001\u001ao\u0011+\n\u0013=\u0017:\u000b\u0004", "OvNdv");
        WorldInfo.I[0x56 ^ 0x35] = I("\u001b\u0006.5-w\u0004=>$%\u0002,?3w\f($(8\r+", "WcXPA");
        WorldInfo.I[0x17 ^ 0x73] = I("\u0001\u000b\u001e\u0010\rm\u001d\u0018\u0014\u0016#N\u0004\u001a\u0002,\u001a\u0001\u001a\u000f", "Mnhua");
        WorldInfo.I[0x7F ^ 0x1A] = I("\u0015\u0001\u0003\u0000\u0006y\u0010\u001c\b\u000f", "Yduej");
        WorldInfo.I[0xA3 ^ 0xC5] = I("8\u0002\u0001\u0017 T\u0003\u001e\u001f)\u001a\u0014\u001e\u001d\"", "tgwrL");
        WorldInfo.I[0x1B ^ 0x7C] = I("8\u00137\"\u0000T\u00055(\u001e\u0015\u0011$g\u001a\u0011\u00042.\u0003\u001a", "tvAGl");
        WorldInfo.I[0x22 ^ 0x4A] = I("\u0002<\u0003#\u0007n.\u0010'\u001f&<\u0007", "NYuFk");
        WorldInfo.I[0x72 ^ 0x1B] = I("\u0000+\"\u0015.l)5\u001d'l#;\u0014'", "LNTpB");
    }
    
    static boolean access$15(final WorldInfo worldInfo) {
        return worldInfo.hardcore;
    }
    
    public long getSeed() {
        return this.randomSeed;
    }
    
    public int getSpawnZ() {
        return this.spawnZ;
    }
    
    public int getSaveVersion() {
        return this.saveVersion;
    }
    
    public double getBorderCenterX() {
        return this.borderCenterX;
    }
    
    public void setMapFeaturesEnabled(final boolean mapFeaturesEnabled) {
        this.mapFeaturesEnabled = mapFeaturesEnabled;
    }
    
    public boolean areCommandsAllowed() {
        return this.allowCommands;
    }
    
    public void setBorderWarningDistance(final int borderWarningDistance) {
        this.borderWarningDistance = borderWarningDistance;
    }
    
    public int getSpawnX() {
        return this.spawnX;
    }
    
    public NBTTagCompound getNBTTagCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound, this.playerTag);
        return nbtTagCompound;
    }
    
    static boolean access$11(final WorldInfo worldInfo) {
        return worldInfo.raining;
    }
    
    public void setSaveVersion(final int saveVersion) {
        this.saveVersion = saveVersion;
    }
    
    public boolean isThundering() {
        return this.thundering;
    }
    
    public void setGameType(final WorldSettings.GameType theGameType) {
        this.theGameType = theGameType;
    }
    
    public WorldInfo(final NBTTagCompound nbtTagCompound) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = WorldInfo.I[" ".length()];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = (0x9C ^ 0x99);
        this.borderWarningTime = (0xBB ^ 0xB4);
        this.theGameRules = new GameRules();
        this.randomSeed = nbtTagCompound.getLong(WorldInfo.I["  ".length()]);
        if (nbtTagCompound.hasKey(WorldInfo.I["   ".length()], 0x4D ^ 0x45)) {
            this.terrainType = WorldType.parseWorldType(nbtTagCompound.getString(WorldInfo.I[0xA1 ^ 0xA5]));
            if (this.terrainType == null) {
                this.terrainType = WorldType.DEFAULT;
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (this.terrainType.isVersioned()) {
                int n = "".length();
                if (nbtTagCompound.hasKey(WorldInfo.I[0xC7 ^ 0xC2], 0x60 ^ 0x3)) {
                    n = nbtTagCompound.getInteger(WorldInfo.I[0x38 ^ 0x3E]);
                }
                this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(n);
            }
            if (nbtTagCompound.hasKey(WorldInfo.I[0x63 ^ 0x64], 0x84 ^ 0x8C)) {
                this.generatorOptions = nbtTagCompound.getString(WorldInfo.I[0x19 ^ 0x11]);
            }
        }
        this.theGameType = WorldSettings.GameType.getByID(nbtTagCompound.getInteger(WorldInfo.I[0x6C ^ 0x65]));
        if (nbtTagCompound.hasKey(WorldInfo.I[0x75 ^ 0x7F], 0x4F ^ 0x2C)) {
            this.mapFeaturesEnabled = nbtTagCompound.getBoolean(WorldInfo.I[0xAC ^ 0xA7]);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.mapFeaturesEnabled = (" ".length() != 0);
        }
        this.spawnX = nbtTagCompound.getInteger(WorldInfo.I[0x6 ^ 0xA]);
        this.spawnY = nbtTagCompound.getInteger(WorldInfo.I[0x3C ^ 0x31]);
        this.spawnZ = nbtTagCompound.getInteger(WorldInfo.I[0x2B ^ 0x25]);
        this.totalTime = nbtTagCompound.getLong(WorldInfo.I[0x41 ^ 0x4E]);
        if (nbtTagCompound.hasKey(WorldInfo.I[0x55 ^ 0x45], 0xF3 ^ 0x90)) {
            this.worldTime = nbtTagCompound.getLong(WorldInfo.I[0x98 ^ 0x89]);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            this.worldTime = this.totalTime;
        }
        this.lastTimePlayed = nbtTagCompound.getLong(WorldInfo.I[0x7C ^ 0x6E]);
        this.sizeOnDisk = nbtTagCompound.getLong(WorldInfo.I[0x5F ^ 0x4C]);
        this.levelName = nbtTagCompound.getString(WorldInfo.I[0x1A ^ 0xE]);
        this.saveVersion = nbtTagCompound.getInteger(WorldInfo.I[0x6F ^ 0x7A]);
        this.cleanWeatherTime = nbtTagCompound.getInteger(WorldInfo.I[0x29 ^ 0x3F]);
        this.rainTime = nbtTagCompound.getInteger(WorldInfo.I[0x1C ^ 0xB]);
        this.raining = nbtTagCompound.getBoolean(WorldInfo.I[0x6C ^ 0x74]);
        this.thunderTime = nbtTagCompound.getInteger(WorldInfo.I[0x80 ^ 0x99]);
        this.thundering = nbtTagCompound.getBoolean(WorldInfo.I[0x3F ^ 0x25]);
        this.hardcore = nbtTagCompound.getBoolean(WorldInfo.I[0xA4 ^ 0xBF]);
        if (nbtTagCompound.hasKey(WorldInfo.I[0x73 ^ 0x6F], 0xD9 ^ 0xBA)) {
            this.initialized = nbtTagCompound.getBoolean(WorldInfo.I[0xC ^ 0x11]);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            this.initialized = (" ".length() != 0);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x19 ^ 0x7], 0xF ^ 0x6C)) {
            this.allowCommands = nbtTagCompound.getBoolean(WorldInfo.I[0x45 ^ 0x5A]);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            int allowCommands;
            if (this.theGameType == WorldSettings.GameType.CREATIVE) {
                allowCommands = " ".length();
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                allowCommands = "".length();
            }
            this.allowCommands = (allowCommands != 0);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x71 ^ 0x51], 0x5D ^ 0x57)) {
            this.playerTag = nbtTagCompound.getCompoundTag(WorldInfo.I[0x2E ^ 0xF]);
            this.dimension = this.playerTag.getInteger(WorldInfo.I[0x25 ^ 0x7]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x71 ^ 0x52], 0x90 ^ 0x9A)) {
            this.theGameRules.readFromNBT(nbtTagCompound.getCompoundTag(WorldInfo.I[0x3B ^ 0x1F]));
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0xAF ^ 0x8A], 0x77 ^ 0x14)) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(nbtTagCompound.getByte(WorldInfo.I[0x4D ^ 0x6B]));
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x79 ^ 0x5E], " ".length())) {
            this.difficultyLocked = nbtTagCompound.getBoolean(WorldInfo.I[0x1B ^ 0x33]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x4D ^ 0x64], 0x3B ^ 0x58)) {
            this.borderCenterX = nbtTagCompound.getDouble(WorldInfo.I[0x87 ^ 0xAD]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0xA1 ^ 0x8A], 0x4C ^ 0x2F)) {
            this.borderCenterZ = nbtTagCompound.getDouble(WorldInfo.I[0xB8 ^ 0x94]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x21 ^ 0xC], 0x36 ^ 0x55)) {
            this.borderSize = nbtTagCompound.getDouble(WorldInfo.I[0x9F ^ 0xB1]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x17 ^ 0x38], 0xC5 ^ 0xA6)) {
            this.borderSizeLerpTime = nbtTagCompound.getLong(WorldInfo.I[0x83 ^ 0xB3]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x77 ^ 0x46], 0xCE ^ 0xAD)) {
            this.borderSizeLerpTarget = nbtTagCompound.getDouble(WorldInfo.I[0x18 ^ 0x2A]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x80 ^ 0xB3], 0x23 ^ 0x40)) {
            this.borderSafeZone = nbtTagCompound.getDouble(WorldInfo.I[0x14 ^ 0x20]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0xA2 ^ 0x97], 0x51 ^ 0x32)) {
            this.borderDamagePerBlock = nbtTagCompound.getDouble(WorldInfo.I[0x61 ^ 0x57]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0x4D ^ 0x7A], 0xCC ^ 0xAF)) {
            this.borderWarningDistance = nbtTagCompound.getInteger(WorldInfo.I[0x3F ^ 0x7]);
        }
        if (nbtTagCompound.hasKey(WorldInfo.I[0xB7 ^ 0x8E], 0x40 ^ 0x23)) {
            this.borderWarningTime = nbtTagCompound.getInteger(WorldInfo.I[0x16 ^ 0x2C]);
        }
    }
    
    public long getWorldTotalTime() {
        return this.totalTime;
    }
    
    public void setSpawn(final BlockPos blockPos) {
        this.spawnX = blockPos.getX();
        this.spawnY = blockPos.getY();
        this.spawnZ = blockPos.getZ();
    }
    
    public int getBorderWarningDistance() {
        return this.borderWarningDistance;
    }
    
    public void setBorderWarningTime(final int borderWarningTime) {
        this.borderWarningTime = borderWarningTime;
    }
}
