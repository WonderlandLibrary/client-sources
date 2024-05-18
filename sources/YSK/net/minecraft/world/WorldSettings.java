package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;

public final class WorldSettings
{
    private final boolean hardcoreEnabled;
    private boolean commandsAllowed;
    private final boolean mapFeaturesEnabled;
    private final WorldType terrainType;
    private final GameType theGameType;
    private boolean bonusChestEnabled;
    private String worldName;
    private final long seed;
    private static final String[] I;
    
    public long getSeed() {
        return this.seed;
    }
    
    public GameType getGameType() {
        return this.theGameType;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean areCommandsAllowed() {
        return this.commandsAllowed;
    }
    
    public boolean getHardcoreEnabled() {
        return this.hardcoreEnabled;
    }
    
    public static GameType getGameTypeById(final int n) {
        return GameType.getByID(n);
    }
    
    static {
        I();
    }
    
    public WorldSettings(final long seed, final GameType theGameType, final boolean mapFeaturesEnabled, final boolean hardcoreEnabled, final WorldType terrainType) {
        this.worldName = WorldSettings.I["".length()];
        this.seed = seed;
        this.theGameType = theGameType;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.hardcoreEnabled = hardcoreEnabled;
        this.terrainType = terrainType;
    }
    
    public WorldSettings enableBonusChest() {
        this.bonusChestEnabled = (" ".length() != 0);
        return this;
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public WorldSettings setWorldName(final String worldName) {
        this.worldName = worldName;
        return this;
    }
    
    public boolean isBonusChestEnabled() {
        return this.bonusChestEnabled;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "LKWMP");
    }
    
    public WorldSettings enableCommands() {
        this.commandsAllowed = (" ".length() != 0);
        return this;
    }
    
    public WorldSettings(final WorldInfo worldInfo) {
        this(worldInfo.getSeed(), worldInfo.getGameType(), worldInfo.isMapFeaturesEnabled(), worldInfo.isHardcoreModeEnabled(), worldInfo.getTerrainType());
    }
    
    public enum GameType
    {
        private static final GameType[] ENUM$VALUES;
        
        SURVIVAL(GameType.I["  ".length()], " ".length(), "".length(), GameType.I["   ".length()]);
        
        String name;
        
        SPECTATOR(GameType.I[0x78 ^ 0x70], 0xC6 ^ 0xC2, "   ".length(), GameType.I[0x67 ^ 0x6E]), 
        NOT_SET(GameType.I["".length()], "".length(), -" ".length(), GameType.I[" ".length()]), 
        CREATIVE(GameType.I[0x32 ^ 0x36], "  ".length(), " ".length(), GameType.I[0xBB ^ 0xBE]), 
        ADVENTURE(GameType.I[0x7 ^ 0x1], "   ".length(), "  ".length(), GameType.I[0x78 ^ 0x7F]);
        
        int id;
        private static final String[] I;
        
        public void configurePlayerCapabilities(final PlayerCapabilities playerCapabilities) {
            if (this == GameType.CREATIVE) {
                playerCapabilities.allowFlying = (" ".length() != 0);
                playerCapabilities.isCreativeMode = (" ".length() != 0);
                playerCapabilities.disableDamage = (" ".length() != 0);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else if (this == GameType.SPECTATOR) {
                playerCapabilities.allowFlying = (" ".length() != 0);
                playerCapabilities.isCreativeMode = ("".length() != 0);
                playerCapabilities.disableDamage = (" ".length() != 0);
                playerCapabilities.isFlying = (" ".length() != 0);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                playerCapabilities.allowFlying = ("".length() != 0);
                playerCapabilities.isCreativeMode = ("".length() != 0);
                playerCapabilities.disableDamage = ("".length() != 0);
                playerCapabilities.isFlying = ("".length() != 0);
            }
            int allowEdit;
            if (this.isAdventure()) {
                allowEdit = "".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                allowEdit = " ".length();
            }
            playerCapabilities.allowEdit = (allowEdit != 0);
        }
        
        public int getID() {
            return this.id;
        }
        
        private GameType(final String s, final int n, final int id, final String name) {
            this.id = id;
            this.name = name;
        }
        
        public boolean isCreative() {
            if (this == GameType.CREATIVE) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public boolean isSurvivalOrAdventure() {
            if (this != GameType.SURVIVAL && this != GameType.ADVENTURE) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public String getName() {
            return this.name;
        }
        
        public boolean isAdventure() {
            if (this != GameType.ADVENTURE && this != GameType.SPECTATOR) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String[0xAE ^ 0xA4])["".length()] = I("$\"?\u0013\u001e/9", "jmkLM");
            GameType.I[" ".length()] = I("", "rxjHa");
            GameType.I["  ".length()] = I("\u0014-\u001b\u00179\u00119\u0005", "GxIAp");
            GameType.I["   ".length()] = I("!$\u001e!9$0\u0000", "RQlWP");
            GameType.I[0x8B ^ 0x8F] = I("!*\u00000,+.\u0000", "bxEqx");
            GameType.I[0x36 ^ 0x33] = I("+\u001f2\u0015\u0017!\u001b2", "HmWtc");
            GameType.I[0x92 ^ 0x94] = I(";4\u0005(+.%\u0001(", "zpSme");
            GameType.I[0x50 ^ 0x57] = I("\u0016/83\u0016\u0003><3", "wKNVx");
            GameType.I[0xB1 ^ 0xB9] = I("\u0003\u0004\u001f;\u001a\u0011\u0000\u0015*", "PTZxN");
            GameType.I[0x5C ^ 0x55] = I("\u0004\u0003\u0010 \u001f\u0016\u0007\u001a1", "wsuCk");
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
                if (1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static GameType getByName(final String s) {
            final GameType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < length) {
                final GameType gameType = values[i];
                if (gameType.name.equals(s)) {
                    return gameType;
                }
                ++i;
            }
            return GameType.SURVIVAL;
        }
        
        static {
            I();
            final GameType[] enum$VALUES = new GameType[0x69 ^ 0x6C];
            enum$VALUES["".length()] = GameType.NOT_SET;
            enum$VALUES[" ".length()] = GameType.SURVIVAL;
            enum$VALUES["  ".length()] = GameType.CREATIVE;
            enum$VALUES["   ".length()] = GameType.ADVENTURE;
            enum$VALUES[0x38 ^ 0x3C] = GameType.SPECTATOR;
            ENUM$VALUES = enum$VALUES;
        }
        
        public static GameType getByID(final int n) {
            final GameType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (i < length) {
                final GameType gameType = values[i];
                if (gameType.id == n) {
                    return gameType;
                }
                ++i;
            }
            return GameType.SURVIVAL;
        }
    }
}
