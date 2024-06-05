package net.minecraft.src;

public class ServerData
{
    public String serverName;
    public String serverIP;
    public String populationInfo;
    public String serverMOTD;
    public long pingToServer;
    public int field_82821_f;
    public String gameVersion;
    public boolean field_78841_f;
    private boolean field_78842_g;
    private boolean acceptsTextures;
    private boolean hideAddress;
    
    public ServerData(final String par1Str, final String par2Str) {
        this.field_82821_f = 61;
        this.gameVersion = "1.5.2";
        this.field_78841_f = false;
        this.field_78842_g = true;
        this.acceptsTextures = false;
        this.hideAddress = false;
        this.serverName = par1Str;
        this.serverIP = par2Str;
    }
    
    public NBTTagCompound getNBTCompound() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("name", this.serverName);
        var1.setString("ip", this.serverIP);
        var1.setBoolean("hideAddress", this.hideAddress);
        if (!this.field_78842_g) {
            var1.setBoolean("acceptTextures", this.acceptsTextures);
        }
        return var1;
    }
    
    public boolean getAcceptsTextures() {
        return this.acceptsTextures;
    }
    
    public boolean func_78840_c() {
        return this.field_78842_g;
    }
    
    public void setAcceptsTextures(final boolean par1) {
        this.acceptsTextures = par1;
        this.field_78842_g = false;
    }
    
    public boolean isHidingAddress() {
        return this.hideAddress;
    }
    
    public void setHideAddress(final boolean par1) {
        this.hideAddress = par1;
    }
    
    public static ServerData getServerDataFromNBTCompound(final NBTTagCompound par0NBTTagCompound) {
        final ServerData var1 = new ServerData(par0NBTTagCompound.getString("name"), par0NBTTagCompound.getString("ip"));
        var1.hideAddress = par0NBTTagCompound.getBoolean("hideAddress");
        if (par0NBTTagCompound.hasKey("acceptTextures")) {
            var1.setAcceptsTextures(par0NBTTagCompound.getBoolean("acceptTextures"));
        }
        return var1;
    }
}
