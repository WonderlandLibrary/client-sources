// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc10LeaveServer implements IrcPacket
{
    public String reason;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.reason = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.reason);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
