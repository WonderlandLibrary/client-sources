// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc09JoinServer implements IrcPacket
{
    public String server;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.server = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.server);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
