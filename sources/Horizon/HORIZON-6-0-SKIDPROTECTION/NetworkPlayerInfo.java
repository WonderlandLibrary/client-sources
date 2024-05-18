package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;

public class NetworkPlayerInfo
{
    private final GameProfile HorizonCode_Horizon_È;
    private WorldSettings.HorizonCode_Horizon_È Â;
    private int Ý;
    private boolean Ø­áŒŠá;
    private ResourceLocation_1975012498 Âµá€;
    private ResourceLocation_1975012498 Ó;
    private String à;
    private IChatComponent Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private long ÂµÈ;
    private long á;
    private long ˆÏ­;
    private static final String £á = "CL_00000888";
    
    public NetworkPlayerInfo(final GameProfile p_i46294_1_) {
        this.Ø­áŒŠá = false;
        this.áŒŠÆ = 0;
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = 0L;
        this.á = 0L;
        this.ˆÏ­ = 0L;
        this.HorizonCode_Horizon_È = p_i46294_1_;
    }
    
    public NetworkPlayerInfo(final S38PacketPlayerListItem.Â p_i46295_1_) {
        this.Ø­áŒŠá = false;
        this.áŒŠÆ = 0;
        this.áˆºÑ¢Õ = 0;
        this.ÂµÈ = 0L;
        this.á = 0L;
        this.ˆÏ­ = 0L;
        this.HorizonCode_Horizon_È = p_i46295_1_.HorizonCode_Horizon_È();
        this.Â = p_i46295_1_.Ý();
        this.Ý = p_i46295_1_.Â();
    }
    
    public GameProfile HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public WorldSettings.HorizonCode_Horizon_È Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    protected void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p_178839_1_) {
        this.Â = p_178839_1_;
    }
    
    protected void HorizonCode_Horizon_È(final int p_178838_1_) {
        this.Ý = p_178838_1_;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Âµá€ != null;
    }
    
    public String Âµá€() {
        return (this.à == null) ? DefaultPlayerSkin.Â(this.HorizonCode_Horizon_È.getId()) : this.à;
    }
    
    public ResourceLocation_1975012498 Ó() {
        if (this.Âµá€ == null) {
            this.áŒŠÆ();
        }
        return (ResourceLocation_1975012498)Objects.firstNonNull((Object)this.Âµá€, (Object)DefaultPlayerSkin.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.getId()));
    }
    
    public ResourceLocation_1975012498 à() {
        if (this.Ó == null) {
            this.áŒŠÆ();
        }
        return this.Ó;
    }
    
    public ScorePlayerTeam Ø() {
        return Minecraft.áŒŠà().áŒŠÆ.à¢().Ó(this.HorizonCode_Horizon_È().getName());
    }
    
    protected void áŒŠÆ() {
        synchronized (this) {
            if (!this.Ø­áŒŠá) {
                this.Ø­áŒŠá = true;
                Minecraft.áŒŠà().áˆºáˆºÈ().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, new SkinManager.HorizonCode_Horizon_È() {
                    private static final String Â = "CL_00002619";
                    
                    @Override
                    public void HorizonCode_Horizon_È(final MinecraftProfileTexture.Type p_180521_1_, final ResourceLocation_1975012498 p_180521_2_, final MinecraftProfileTexture p_180521_3_) {
                        switch (NetworkPlayerInfo.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_180521_1_.ordinal()]) {
                            case 1: {
                                NetworkPlayerInfo.HorizonCode_Horizon_È(NetworkPlayerInfo.this, p_180521_2_);
                                NetworkPlayerInfo.HorizonCode_Horizon_È(NetworkPlayerInfo.this, p_180521_3_.getMetadata("model"));
                                if (NetworkPlayerInfo.this.à == null) {
                                    NetworkPlayerInfo.HorizonCode_Horizon_È(NetworkPlayerInfo.this, "default");
                                    break;
                                }
                                break;
                            }
                            case 2: {
                                NetworkPlayerInfo.Â(NetworkPlayerInfo.this, p_180521_2_);
                                break;
                            }
                        }
                    }
                }, true);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent p_178859_1_) {
        this.Ø = p_178859_1_;
    }
    
    public IChatComponent áˆºÑ¢Õ() {
        return this.Ø;
    }
    
    public int ÂµÈ() {
        return this.áŒŠÆ;
    }
    
    public void Â(final int p_178836_1_) {
        this.áŒŠÆ = p_178836_1_;
    }
    
    public int á() {
        return this.áˆºÑ¢Õ;
    }
    
    public void Ý(final int p_178857_1_) {
        this.áˆºÑ¢Õ = p_178857_1_;
    }
    
    public long ˆÏ­() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final long p_178846_1_) {
        this.ÂµÈ = p_178846_1_;
    }
    
    public long £á() {
        return this.á;
    }
    
    public void Â(final long p_178844_1_) {
        this.á = p_178844_1_;
    }
    
    public long Å() {
        return this.ˆÏ­;
    }
    
    public void Ý(final long p_178843_1_) {
        this.ˆÏ­ = p_178843_1_;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation_1975012498 âµá€) {
        networkPlayerInfo.Âµá€ = âµá€;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final NetworkPlayerInfo networkPlayerInfo, final String à) {
        networkPlayerInfo.à = à;
    }
    
    static /* synthetic */ void Â(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation_1975012498 ó) {
        networkPlayerInfo.Ó = ó;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002618";
        
        static {
            HorizonCode_Horizon_È = new int[MinecraftProfileTexture.Type.values().length];
            try {
                NetworkPlayerInfo.HorizonCode_Horizon_È.HorizonCode_Horizon_È[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NetworkPlayerInfo.HorizonCode_Horizon_È.HorizonCode_Horizon_È[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
