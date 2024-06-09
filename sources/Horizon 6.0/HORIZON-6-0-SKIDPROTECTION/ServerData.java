package HORIZON-6-0-SKIDPROTECTION;

public class ServerData
{
    public String HorizonCode_Horizon_È;
    public String Â;
    public String Ý;
    public String Ø­áŒŠá;
    public long Âµá€;
    public int Ó;
    public String à;
    public boolean Ø;
    public String áŒŠÆ;
    private HorizonCode_Horizon_È áˆºÑ¢Õ;
    private String ÂµÈ;
    private static final String á = "CL_00000890";
    
    public ServerData(final String p_i1193_1_, final String p_i1193_2_) {
        this.Ó = 47;
        this.à = "1.8";
        this.áˆºÑ¢Õ = ServerData.HorizonCode_Horizon_È.Ý;
        this.HorizonCode_Horizon_È = p_i1193_1_;
        this.Â = p_i1193_2_;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.HorizonCode_Horizon_È("name", this.HorizonCode_Horizon_È);
        var1.HorizonCode_Horizon_È("ip", this.Â);
        if (this.ÂµÈ != null) {
            var1.HorizonCode_Horizon_È("icon", this.ÂµÈ);
        }
        if (this.áˆºÑ¢Õ == ServerData.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var1.HorizonCode_Horizon_È("acceptTextures", true);
        }
        else if (this.áˆºÑ¢Õ == ServerData.HorizonCode_Horizon_È.Â) {
            var1.HorizonCode_Horizon_È("acceptTextures", false);
        }
        return var1;
    }
    
    public HorizonCode_Horizon_È Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È mode) {
        this.áˆºÑ¢Õ = mode;
    }
    
    public static ServerData HorizonCode_Horizon_È(final NBTTagCompound nbtCompound) {
        final ServerData var1 = new ServerData(nbtCompound.áˆºÑ¢Õ("name"), nbtCompound.áˆºÑ¢Õ("ip"));
        if (nbtCompound.Â("icon", 8)) {
            var1.HorizonCode_Horizon_È(nbtCompound.áˆºÑ¢Õ("icon"));
        }
        if (nbtCompound.Â("acceptTextures", 1)) {
            if (nbtCompound.£á("acceptTextures")) {
                var1.HorizonCode_Horizon_È(HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            }
            else {
                var1.HorizonCode_Horizon_È(HorizonCode_Horizon_È.Â);
            }
        }
        else {
            var1.HorizonCode_Horizon_È(HorizonCode_Horizon_È.Ý);
        }
        return var1;
    }
    
    public String Ý() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final String icon) {
        this.ÂµÈ = icon;
    }
    
    public void HorizonCode_Horizon_È(final ServerData serverDataIn) {
        this.Â = serverDataIn.Â;
        this.HorizonCode_Horizon_È = serverDataIn.HorizonCode_Horizon_È;
        this.HorizonCode_Horizon_È(serverDataIn.Â());
        this.ÂµÈ = serverDataIn.ÂµÈ;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("ENABLED", 0, "ENABLED", 0, "enabled"), 
        Â("DISABLED", 1, "DISABLED", 1, "disabled"), 
        Ý("PROMPT", 2, "PROMPT", 2, "prompt");
        
        private final IChatComponent Ø­áŒŠá;
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00001833";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1053_1_, final int p_i1053_2_, final String p_i1053_3_) {
            this.Ø­áŒŠá = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_, new Object[0]);
        }
        
        public IChatComponent HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
    }
}
