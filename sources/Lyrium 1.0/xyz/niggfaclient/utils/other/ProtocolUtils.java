// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import viamcp.protocols.ProtocolCollection;
import viamcp.ViaMCP;

public class ProtocolUtils
{
    public static boolean isOneDotEight() {
        return ViaMCP.getInstance().getVersion() == ProtocolCollection.R1_8.getVersion().getVersion();
    }
    
    public static boolean isMoreOrEqual(final ProtocolCollection protocolCollection) {
        return ViaMCP.getInstance().getVersion() >= protocolCollection.getVersion().getVersion();
    }
}
