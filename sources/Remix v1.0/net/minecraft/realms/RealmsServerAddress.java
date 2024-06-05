package net.minecraft.realms;

import net.minecraft.client.multiplayer.*;

public class RealmsServerAddress
{
    private final String host;
    private final int port;
    
    protected RealmsServerAddress(final String p_i1121_1_, final int p_i1121_2_) {
        this.host = p_i1121_1_;
        this.port = p_i1121_2_;
    }
    
    public static RealmsServerAddress parseString(final String p_parseString_0_) {
        final ServerAddress var1 = ServerAddress.func_78860_a(p_parseString_0_);
        return new RealmsServerAddress(var1.getIP(), var1.getPort());
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
}
