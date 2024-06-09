// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc15LegacyProtocolVersion implements IrcPacket
{
    public int protocolVersion;
    public String clientVersion;
    public String ircVersion;
    public boolean supportsExtensions;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.protocolVersion = connection._ircIllIllIIlI();
        this.ircVersion = connection._ircIllIllIIlI();
        this.supportsExtensions = connection._irclllllIIlII();
        this.clientVersion = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.protocolVersion);
        connection._irclllllIIlII(this.ircVersion);
        connection._ircIllIllIIlI(this.supportsExtensions);
        connection._irclllllIIlII(this.clientVersion);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
