package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;

public class WorldInfo
{
    public static final EnumDifficulty HorizonCode_Horizon_È;
    private long Â;
    private WorldType Ý;
    private String Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private long Ø;
    private long áŒŠÆ;
    private long áˆºÑ¢Õ;
    private long ÂµÈ;
    private NBTTagCompound á;
    private int ˆÏ­;
    private String £á;
    private int Å;
    private int £à;
    private boolean µà;
    private int ˆà;
    private boolean ¥Æ;
    private int Ø­à;
    private WorldSettings.HorizonCode_Horizon_È µÕ;
    private boolean Æ;
    private boolean Šáƒ;
    private boolean Ï­Ðƒà;
    private boolean áŒŠà;
    private EnumDifficulty ŠÄ;
    private boolean Ñ¢á;
    private double ŒÏ;
    private double Çªà¢;
    private double Ê;
    private long ÇŽÉ;
    private double ˆá;
    private double ÇŽÕ;
    private double É;
    private int áƒ;
    private int á€;
    private GameRules Õ;
    private static final String à¢ = "CL_00000587";
    
    static {
        HorizonCode_Horizon_È = EnumDifficulty.Ý;
    }
    
    protected WorldInfo() {
        this.Ý = WorldType.Ý;
        this.Ø­áŒŠá = "";
        this.ŒÏ = 0.0;
        this.Çªà¢ = 0.0;
        this.Ê = 6.0E7;
        this.ÇŽÉ = 0L;
        this.ˆá = 0.0;
        this.ÇŽÕ = 5.0;
        this.É = 0.2;
        this.áƒ = 5;
        this.á€ = 15;
        this.Õ = new GameRules();
    }
    
    public WorldInfo(final NBTTagCompound nbt) {
        this.Ý = WorldType.Ý;
        this.Ø­áŒŠá = "";
        this.ŒÏ = 0.0;
        this.Çªà¢ = 0.0;
        this.Ê = 6.0E7;
        this.ÇŽÉ = 0L;
        this.ˆá = 0.0;
        this.ÇŽÕ = 5.0;
        this.É = 0.2;
        this.áƒ = 5;
        this.á€ = 15;
        this.Õ = new GameRules();
        this.Â = nbt.à("RandomSeed");
        if (nbt.Â("generatorName", 8)) {
            final String var2 = nbt.áˆºÑ¢Õ("generatorName");
            this.Ý = WorldType.HorizonCode_Horizon_È(var2);
            if (this.Ý == null) {
                this.Ý = WorldType.Ý;
            }
            else if (this.Ý.Ó()) {
                int var3 = 0;
                if (nbt.Â("generatorVersion", 99)) {
                    var3 = nbt.Ó("generatorVersion");
                }
                this.Ý = this.Ý.HorizonCode_Horizon_È(var3);
            }
            if (nbt.Â("generatorOptions", 8)) {
                this.Ø­áŒŠá = nbt.áˆºÑ¢Õ("generatorOptions");
            }
        }
        this.µÕ = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(nbt.Ó("GameType"));
        if (nbt.Â("MapFeatures", 99)) {
            this.Æ = nbt.£á("MapFeatures");
        }
        else {
            this.Æ = true;
        }
        this.Âµá€ = nbt.Ó("SpawnX");
        this.Ó = nbt.Ó("SpawnY");
        this.à = nbt.Ó("SpawnZ");
        this.Ø = nbt.à("Time");
        if (nbt.Â("DayTime", 99)) {
            this.áŒŠÆ = nbt.à("DayTime");
        }
        else {
            this.áŒŠÆ = this.Ø;
        }
        this.áˆºÑ¢Õ = nbt.à("LastPlayed");
        this.ÂµÈ = nbt.à("SizeOnDisk");
        this.£á = nbt.áˆºÑ¢Õ("LevelName");
        this.Å = nbt.Ó("version");
        this.£à = nbt.Ó("clearWeatherTime");
        this.ˆà = nbt.Ó("rainTime");
        this.µà = nbt.£á("raining");
        this.Ø­à = nbt.Ó("thunderTime");
        this.¥Æ = nbt.£á("thundering");
        this.Šáƒ = nbt.£á("hardcore");
        if (nbt.Â("initialized", 99)) {
            this.áŒŠà = nbt.£á("initialized");
        }
        else {
            this.áŒŠà = true;
        }
        if (nbt.Â("allowCommands", 99)) {
            this.Ï­Ðƒà = nbt.£á("allowCommands");
        }
        else {
            this.Ï­Ðƒà = (this.µÕ == WorldSettings.HorizonCode_Horizon_È.Ý);
        }
        if (nbt.Â("Player", 10)) {
            this.á = nbt.ˆÏ­("Player");
            this.ˆÏ­ = this.á.Ó("Dimension");
        }
        if (nbt.Â("GameRules", 10)) {
            this.Õ.HorizonCode_Horizon_È(nbt.ˆÏ­("GameRules"));
        }
        if (nbt.Â("Difficulty", 99)) {
            this.ŠÄ = EnumDifficulty.HorizonCode_Horizon_È(nbt.Ø­áŒŠá("Difficulty"));
        }
        if (nbt.Â("DifficultyLocked", 1)) {
            this.Ñ¢á = nbt.£á("DifficultyLocked");
        }
        if (nbt.Â("BorderCenterX", 99)) {
            this.ŒÏ = nbt.áŒŠÆ("BorderCenterX");
        }
        if (nbt.Â("BorderCenterZ", 99)) {
            this.Çªà¢ = nbt.áŒŠÆ("BorderCenterZ");
        }
        if (nbt.Â("BorderSize", 99)) {
            this.Ê = nbt.áŒŠÆ("BorderSize");
        }
        if (nbt.Â("BorderSizeLerpTime", 99)) {
            this.ÇŽÉ = nbt.à("BorderSizeLerpTime");
        }
        if (nbt.Â("BorderSizeLerpTarget", 99)) {
            this.ˆá = nbt.áŒŠÆ("BorderSizeLerpTarget");
        }
        if (nbt.Â("BorderSafeZone", 99)) {
            this.ÇŽÕ = nbt.áŒŠÆ("BorderSafeZone");
        }
        if (nbt.Â("BorderDamagePerBlock", 99)) {
            this.É = nbt.áŒŠÆ("BorderDamagePerBlock");
        }
        if (nbt.Â("BorderWarningBlocks", 99)) {
            this.áƒ = nbt.Ó("BorderWarningBlocks");
        }
        if (nbt.Â("BorderWarningTime", 99)) {
            this.á€ = nbt.Ó("BorderWarningTime");
        }
    }
    
    public WorldInfo(final WorldSettings settings, final String name) {
        this.Ý = WorldType.Ý;
        this.Ø­áŒŠá = "";
        this.ŒÏ = 0.0;
        this.Çªà¢ = 0.0;
        this.Ê = 6.0E7;
        this.ÇŽÉ = 0L;
        this.ˆá = 0.0;
        this.ÇŽÕ = 5.0;
        this.É = 0.2;
        this.áƒ = 5;
        this.á€ = 15;
        this.Õ = new GameRules();
        this.HorizonCode_Horizon_È(settings);
        this.£á = name;
        this.ŠÄ = WorldInfo.HorizonCode_Horizon_È;
        this.áŒŠà = false;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings settings) {
        this.Â = settings.Ø­áŒŠá();
        this.µÕ = settings.Âµá€();
        this.Æ = settings.à();
        this.Šáƒ = settings.Ó();
        this.Ý = settings.Ø();
        this.Ø­áŒŠá = settings.áˆºÑ¢Õ();
        this.Ï­Ðƒà = settings.áŒŠÆ();
    }
    
    public WorldInfo(final WorldInfo p_i2159_1_) {
        this.Ý = WorldType.Ý;
        this.Ø­áŒŠá = "";
        this.ŒÏ = 0.0;
        this.Çªà¢ = 0.0;
        this.Ê = 6.0E7;
        this.ÇŽÉ = 0L;
        this.ˆá = 0.0;
        this.ÇŽÕ = 5.0;
        this.É = 0.2;
        this.áƒ = 5;
        this.á€ = 15;
        this.Õ = new GameRules();
        this.Â = p_i2159_1_.Â;
        this.Ý = p_i2159_1_.Ý;
        this.Ø­áŒŠá = p_i2159_1_.Ø­áŒŠá;
        this.µÕ = p_i2159_1_.µÕ;
        this.Æ = p_i2159_1_.Æ;
        this.Âµá€ = p_i2159_1_.Âµá€;
        this.Ó = p_i2159_1_.Ó;
        this.à = p_i2159_1_.à;
        this.Ø = p_i2159_1_.Ø;
        this.áŒŠÆ = p_i2159_1_.áŒŠÆ;
        this.áˆºÑ¢Õ = p_i2159_1_.áˆºÑ¢Õ;
        this.ÂµÈ = p_i2159_1_.ÂµÈ;
        this.á = p_i2159_1_.á;
        this.ˆÏ­ = p_i2159_1_.ˆÏ­;
        this.£á = p_i2159_1_.£á;
        this.Å = p_i2159_1_.Å;
        this.ˆà = p_i2159_1_.ˆà;
        this.µà = p_i2159_1_.µà;
        this.Ø­à = p_i2159_1_.Ø­à;
        this.¥Æ = p_i2159_1_.¥Æ;
        this.Šáƒ = p_i2159_1_.Šáƒ;
        this.Ï­Ðƒà = p_i2159_1_.Ï­Ðƒà;
        this.áŒŠà = p_i2159_1_.áŒŠà;
        this.Õ = p_i2159_1_.Õ;
        this.ŠÄ = p_i2159_1_.ŠÄ;
        this.Ñ¢á = p_i2159_1_.Ñ¢á;
        this.ŒÏ = p_i2159_1_.ŒÏ;
        this.Çªà¢ = p_i2159_1_.Çªà¢;
        this.Ê = p_i2159_1_.Ê;
        this.ÇŽÉ = p_i2159_1_.ÇŽÉ;
        this.ˆá = p_i2159_1_.ˆá;
        this.ÇŽÕ = p_i2159_1_.ÇŽÕ;
        this.É = p_i2159_1_.É;
        this.á€ = p_i2159_1_.á€;
        this.áƒ = p_i2159_1_.áƒ;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.HorizonCode_Horizon_È(var1, this.á);
        return var1;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        final NBTTagCompound var2 = new NBTTagCompound();
        this.HorizonCode_Horizon_È(var2, nbt);
        return var2;
    }
    
    private void HorizonCode_Horizon_È(final NBTTagCompound nbt, final NBTTagCompound playerNbt) {
        nbt.HorizonCode_Horizon_È("RandomSeed", this.Â);
        nbt.HorizonCode_Horizon_È("generatorName", this.Ý.HorizonCode_Horizon_È());
        nbt.HorizonCode_Horizon_È("generatorVersion", this.Ý.Ø­áŒŠá());
        nbt.HorizonCode_Horizon_È("generatorOptions", this.Ø­áŒŠá);
        nbt.HorizonCode_Horizon_È("GameType", this.µÕ.HorizonCode_Horizon_È());
        nbt.HorizonCode_Horizon_È("MapFeatures", this.Æ);
        nbt.HorizonCode_Horizon_È("SpawnX", this.Âµá€);
        nbt.HorizonCode_Horizon_È("SpawnY", this.Ó);
        nbt.HorizonCode_Horizon_È("SpawnZ", this.à);
        nbt.HorizonCode_Horizon_È("Time", this.Ø);
        nbt.HorizonCode_Horizon_È("DayTime", this.áŒŠÆ);
        nbt.HorizonCode_Horizon_È("SizeOnDisk", this.ÂµÈ);
        nbt.HorizonCode_Horizon_È("LastPlayed", MinecraftServer.Œà());
        nbt.HorizonCode_Horizon_È("LevelName", this.£á);
        nbt.HorizonCode_Horizon_È("version", this.Å);
        nbt.HorizonCode_Horizon_È("clearWeatherTime", this.£à);
        nbt.HorizonCode_Horizon_È("rainTime", this.ˆà);
        nbt.HorizonCode_Horizon_È("raining", this.µà);
        nbt.HorizonCode_Horizon_È("thunderTime", this.Ø­à);
        nbt.HorizonCode_Horizon_È("thundering", this.¥Æ);
        nbt.HorizonCode_Horizon_È("hardcore", this.Šáƒ);
        nbt.HorizonCode_Horizon_È("allowCommands", this.Ï­Ðƒà);
        nbt.HorizonCode_Horizon_È("initialized", this.áŒŠà);
        nbt.HorizonCode_Horizon_È("BorderCenterX", this.ŒÏ);
        nbt.HorizonCode_Horizon_È("BorderCenterZ", this.Çªà¢);
        nbt.HorizonCode_Horizon_È("BorderSize", this.Ê);
        nbt.HorizonCode_Horizon_È("BorderSizeLerpTime", this.ÇŽÉ);
        nbt.HorizonCode_Horizon_È("BorderSafeZone", this.ÇŽÕ);
        nbt.HorizonCode_Horizon_È("BorderDamagePerBlock", this.É);
        nbt.HorizonCode_Horizon_È("BorderSizeLerpTarget", this.ˆá);
        nbt.HorizonCode_Horizon_È("BorderWarningBlocks", (double)this.áƒ);
        nbt.HorizonCode_Horizon_È("BorderWarningTime", (double)this.á€);
        if (this.ŠÄ != null) {
            nbt.HorizonCode_Horizon_È("Difficulty", (byte)this.ŠÄ.HorizonCode_Horizon_È());
        }
        nbt.HorizonCode_Horizon_È("DifficultyLocked", this.Ñ¢á);
        nbt.HorizonCode_Horizon_È("GameRules", this.Õ.HorizonCode_Horizon_È());
        if (playerNbt != null) {
            nbt.HorizonCode_Horizon_È("Player", playerNbt);
        }
    }
    
    public long Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Âµá€;
    }
    
    public int Ø­áŒŠá() {
        return this.Ó;
    }
    
    public int Âµá€() {
        return this.à;
    }
    
    public long Ó() {
        return this.Ø;
    }
    
    public long à() {
        return this.áŒŠÆ;
    }
    
    public long Ø() {
        return this.ÂµÈ;
    }
    
    public NBTTagCompound áŒŠÆ() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final int p_76058_1_) {
        this.Âµá€ = p_76058_1_;
    }
    
    public void Â(final int p_76056_1_) {
        this.Ó = p_76056_1_;
    }
    
    public void Ý(final int p_76087_1_) {
        this.à = p_76087_1_;
    }
    
    public void HorizonCode_Horizon_È(final long p_82572_1_) {
        this.Ø = p_82572_1_;
    }
    
    public void Â(final long p_76068_1_) {
        this.áŒŠÆ = p_76068_1_;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos spawnPoint) {
        this.Âµá€ = spawnPoint.HorizonCode_Horizon_È();
        this.Ó = spawnPoint.Â();
        this.à = spawnPoint.Ý();
    }
    
    public String áˆºÑ¢Õ() {
        return this.£á;
    }
    
    public void HorizonCode_Horizon_È(final String p_76062_1_) {
        this.£á = p_76062_1_;
    }
    
    public int ÂµÈ() {
        return this.Å;
    }
    
    public void Ø­áŒŠá(final int p_76078_1_) {
        this.Å = p_76078_1_;
    }
    
    public long á() {
        return this.áˆºÑ¢Õ;
    }
    
    public int ŠÄ() {
        return this.£à;
    }
    
    public void à(final int p_176142_1_) {
        this.£à = p_176142_1_;
    }
    
    public boolean ˆÏ­() {
        return this.¥Æ;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_76069_1_) {
        this.¥Æ = p_76069_1_;
    }
    
    public int £á() {
        return this.Ø­à;
    }
    
    public void Âµá€(final int p_76090_1_) {
        this.Ø­à = p_76090_1_;
    }
    
    public boolean Å() {
        return this.µà;
    }
    
    public void Â(final boolean p_76084_1_) {
        this.µà = p_76084_1_;
    }
    
    public int £à() {
        return this.ˆà;
    }
    
    public void Ó(final int p_76080_1_) {
        this.ˆà = p_76080_1_;
    }
    
    public WorldSettings.HorizonCode_Horizon_È µà() {
        return this.µÕ;
    }
    
    public boolean ˆà() {
        return this.Æ;
    }
    
    public void Ó(final boolean enabled) {
        this.Æ = enabled;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È type) {
        this.µÕ = type;
    }
    
    public boolean ¥Æ() {
        return this.Šáƒ;
    }
    
    public void à(final boolean hardcoreIn) {
        this.Šáƒ = hardcoreIn;
    }
    
    public WorldType Ø­à() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final WorldType p_76085_1_) {
        this.Ý = p_76085_1_;
    }
    
    public String Ñ¢á() {
        return this.Ø­áŒŠá;
    }
    
    public boolean µÕ() {
        return this.Ï­Ðƒà;
    }
    
    public void Ý(final boolean allow) {
        this.Ï­Ðƒà = allow;
    }
    
    public boolean Æ() {
        return this.áŒŠà;
    }
    
    public void Ø­áŒŠá(final boolean initializedIn) {
        this.áŒŠà = initializedIn;
    }
    
    public GameRules Šáƒ() {
        return this.Õ;
    }
    
    public double ŒÏ() {
        return this.ŒÏ;
    }
    
    public double Çªà¢() {
        return this.Çªà¢;
    }
    
    public double Ê() {
        return this.Ê;
    }
    
    public void HorizonCode_Horizon_È(final double p_176145_1_) {
        this.Ê = p_176145_1_;
    }
    
    public long ÇŽÉ() {
        return this.ÇŽÉ;
    }
    
    public void Ý(final long p_176135_1_) {
        this.ÇŽÉ = p_176135_1_;
    }
    
    public double ˆá() {
        return this.ˆá;
    }
    
    public void Â(final double p_176118_1_) {
        this.ˆá = p_176118_1_;
    }
    
    public void Ý(final double p_176141_1_) {
        this.Çªà¢ = p_176141_1_;
    }
    
    public void Ø­áŒŠá(final double p_176124_1_) {
        this.ŒÏ = p_176124_1_;
    }
    
    public double ÇŽÕ() {
        return this.ÇŽÕ;
    }
    
    public void Âµá€(final double p_176129_1_) {
        this.ÇŽÕ = p_176129_1_;
    }
    
    public double É() {
        return this.É;
    }
    
    public void Ó(final double p_176125_1_) {
        this.É = p_176125_1_;
    }
    
    public int áƒ() {
        return this.áƒ;
    }
    
    public int á€() {
        return this.á€;
    }
    
    public void Ø(final int p_176122_1_) {
        this.áƒ = p_176122_1_;
    }
    
    public void áŒŠÆ(final int p_176136_1_) {
        this.á€ = p_176136_1_;
    }
    
    public EnumDifficulty Ï­Ðƒà() {
        return this.ŠÄ;
    }
    
    public void HorizonCode_Horizon_È(final EnumDifficulty newDifficulty) {
        this.ŠÄ = newDifficulty;
    }
    
    public boolean áŒŠà() {
        return this.Ñ¢á;
    }
    
    public void Âµá€(final boolean locked) {
        this.Ñ¢á = locked;
    }
    
    public void HorizonCode_Horizon_È(final CrashReportCategory category) {
        category.HorizonCode_Horizon_È("Level seed", new Callable() {
            private static final String Â = "CL_00000588";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(WorldInfo.this.Â());
            }
        });
        category.HorizonCode_Horizon_È("Level generator", new Callable() {
            private static final String Â = "CL_00000589";
            
            public String HorizonCode_Horizon_È() {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.this.Ý.à(), WorldInfo.this.Ý.HorizonCode_Horizon_È(), WorldInfo.this.Ý.Ø­áŒŠá(), WorldInfo.this.Æ);
            }
        });
        category.HorizonCode_Horizon_È("Level generator options", new Callable() {
            private static final String Â = "CL_00000590";
            
            public String HorizonCode_Horizon_È() {
                return WorldInfo.this.Ø­áŒŠá;
            }
        });
        category.HorizonCode_Horizon_È("Level spawn location", new Callable() {
            private static final String Â = "CL_00000591";
            
            public String HorizonCode_Horizon_È() {
                return CrashReportCategory.HorizonCode_Horizon_È(WorldInfo.this.Âµá€, WorldInfo.this.Ó, WorldInfo.this.à);
            }
        });
        category.HorizonCode_Horizon_È("Level time", new Callable() {
            private static final String Â = "CL_00000592";
            
            public String HorizonCode_Horizon_È() {
                return String.format("%d game time, %d day time", WorldInfo.this.Ø, WorldInfo.this.áŒŠÆ);
            }
        });
        category.HorizonCode_Horizon_È("Level dimension", new Callable() {
            private static final String Â = "CL_00000593";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(WorldInfo.this.ˆÏ­);
            }
        });
        category.HorizonCode_Horizon_È("Level storage version", new Callable() {
            private static final String Â = "CL_00000594";
            
            public String HorizonCode_Horizon_È() {
                String var1 = "Unknown?";
                try {
                    switch (WorldInfo.this.Å) {
                        case 19132: {
                            var1 = "McRegion";
                            break;
                        }
                        case 19133: {
                            var1 = "Anvil";
                            break;
                        }
                    }
                }
                catch (Throwable t) {}
                return String.format("0x%05X - %s", WorldInfo.this.Å, var1);
            }
        });
        category.HorizonCode_Horizon_È("Level weather", new Callable() {
            private static final String Â = "CL_00000595";
            
            public String HorizonCode_Horizon_È() {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.this.ˆà, WorldInfo.this.µà, WorldInfo.this.Ø­à, WorldInfo.this.¥Æ);
            }
        });
        category.HorizonCode_Horizon_È("Level game mode", new Callable() {
            private static final String Â = "CL_00000597";
            
            public String HorizonCode_Horizon_È() {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.this.µÕ.Â(), WorldInfo.this.µÕ.HorizonCode_Horizon_È(), WorldInfo.this.Šáƒ, WorldInfo.this.Ï­Ðƒà);
            }
        });
    }
}
