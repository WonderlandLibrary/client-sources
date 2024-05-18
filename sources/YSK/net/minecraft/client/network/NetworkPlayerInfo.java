package net.minecraft.client.network;

import net.minecraft.util.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.resources.*;
import com.google.common.base.*;
import net.minecraft.scoreboard.*;

public class NetworkPlayerInfo
{
    private long field_178869_m;
    private int field_178873_i;
    private IChatComponent displayName;
    private String skinType;
    private ResourceLocation locationSkin;
    private boolean playerTexturesLoaded;
    private int responseTime;
    private long field_178871_k;
    private WorldSettings.GameType gameType;
    private ResourceLocation locationCape;
    private int field_178870_j;
    private long field_178868_l;
    private final GameProfile gameProfile;
    
    public int func_178860_m() {
        return this.field_178870_j;
    }
    
    static void access$3(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation locationCape) {
        networkPlayerInfo.locationCape = locationCape;
    }
    
    protected void loadPlayerTextures() {
        synchronized (this) {
            if (!this.playerTexturesLoaded) {
                this.playerTexturesLoaded = (" ".length() != 0);
                Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, new SkinManager.SkinAvailableCallback(this) {
                    final NetworkPlayerInfo this$0;
                    private static int[] $SWITCH_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type;
                    private static final String[] I;
                    
                    static int[] $SWITCH_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type() {
                        final int[] $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type = NetworkPlayerInfo$1.$SWITCH_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type;
                        if ($switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type != null) {
                            return $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type;
                        }
                        final int[] $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type2 = new int[MinecraftProfileTexture.Type.values().length];
                        try {
                            $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type2[MinecraftProfileTexture.Type.CAPE.ordinal()] = "  ".length();
                            "".length();
                            if (2 <= 1) {
                                throw null;
                            }
                        }
                        catch (NoSuchFieldError noSuchFieldError) {}
                        try {
                            $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type2[MinecraftProfileTexture.Type.SKIN.ordinal()] = " ".length();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        catch (NoSuchFieldError noSuchFieldError2) {}
                        return NetworkPlayerInfo$1.$SWITCH_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type = $switch_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type2;
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
                            if (1 >= 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    static {
                        I();
                    }
                    
                    private static void I() {
                        (I = new String["  ".length()])["".length()] = I("8\u001d0!6", "UrTDZ");
                        NetworkPlayerInfo$1.I[" ".length()] = I("\u0015\u001c0\u0010\u0004\u001d\r", "qyVqq");
                    }
                    
                    @Override
                    public void skinAvailable(final MinecraftProfileTexture.Type type, final ResourceLocation resourceLocation, final MinecraftProfileTexture minecraftProfileTexture) {
                        switch ($SWITCH_TABLE$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type()[type.ordinal()]) {
                            case 1: {
                                NetworkPlayerInfo.access$0(this.this$0, resourceLocation);
                                NetworkPlayerInfo.access$1(this.this$0, minecraftProfileTexture.getMetadata(NetworkPlayerInfo$1.I["".length()]));
                                if (NetworkPlayerInfo.access$2(this.this$0) != null) {
                                    break;
                                }
                                NetworkPlayerInfo.access$1(this.this$0, NetworkPlayerInfo$1.I[" ".length()]);
                                "".length();
                                if (4 != 4) {
                                    throw null;
                                }
                                break;
                            }
                            case 2: {
                                NetworkPlayerInfo.access$3(this.this$0, resourceLocation);
                                break;
                            }
                        }
                    }
                }, " ".length() != 0);
            }
            // monitorexit(this)
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
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
    
    public NetworkPlayerInfo(final GameProfile gameProfile) {
        this.playerTexturesLoaded = ("".length() != 0);
        this.field_178873_i = "".length();
        this.field_178870_j = "".length();
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.gameProfile = gameProfile;
    }
    
    public NetworkPlayerInfo(final S38PacketPlayerListItem.AddPlayerData addPlayerData) {
        this.playerTexturesLoaded = ("".length() != 0);
        this.field_178873_i = "".length();
        this.field_178870_j = "".length();
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.gameProfile = addPlayerData.getProfile();
        this.gameType = addPlayerData.getGameMode();
        this.responseTime = addPlayerData.getPing();
        this.displayName = addPlayerData.getDisplayName();
    }
    
    public int func_178835_l() {
        return this.field_178873_i;
    }
    
    static String access$2(final NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.skinType;
    }
    
    public void func_178836_b(final int field_178873_i) {
        this.field_178873_i = field_178873_i;
    }
    
    public ResourceLocation getLocationSkin() {
        if (this.locationSkin == null) {
            this.loadPlayerTextures();
        }
        return (ResourceLocation)Objects.firstNonNull((Object)this.locationSkin, (Object)DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
    }
    
    public ResourceLocation getLocationCape() {
        if (this.locationCape == null) {
            this.loadPlayerTextures();
        }
        return this.locationCape;
    }
    
    public ScorePlayerTeam getPlayerTeam() {
        return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
    }
    
    public boolean hasLocationSkin() {
        if (this.locationSkin != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public void setDisplayName(final IChatComponent displayName) {
        this.displayName = displayName;
    }
    
    public void func_178846_a(final long field_178871_k) {
        this.field_178871_k = field_178871_k;
    }
    
    protected void setGameType(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    public void func_178843_c(final long field_178869_m) {
        this.field_178869_m = field_178869_m;
    }
    
    public IChatComponent getDisplayName() {
        return this.displayName;
    }
    
    public String getSkinType() {
        String s;
        if (this.skinType == null) {
            s = DefaultPlayerSkin.getSkinType(this.gameProfile.getId());
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            s = this.skinType;
        }
        return s;
    }
    
    public int getResponseTime() {
        return this.responseTime;
    }
    
    public long func_178847_n() {
        return this.field_178871_k;
    }
    
    public void func_178844_b(final long field_178868_l) {
        this.field_178868_l = field_178868_l;
    }
    
    public void func_178857_c(final int field_178870_j) {
        this.field_178870_j = field_178870_j;
    }
    
    static void access$0(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation locationSkin) {
        networkPlayerInfo.locationSkin = locationSkin;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public long func_178855_p() {
        return this.field_178869_m;
    }
    
    protected void setResponseTime(final int responseTime) {
        this.responseTime = responseTime;
    }
    
    static void access$1(final NetworkPlayerInfo networkPlayerInfo, final String skinType) {
        networkPlayerInfo.skinType = skinType;
    }
    
    public long func_178858_o() {
        return this.field_178868_l;
    }
}
