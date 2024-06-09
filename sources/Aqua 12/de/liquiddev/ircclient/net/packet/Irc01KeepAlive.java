// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc01KeepAlive implements IrcPacket
{
    @Override
    public void read(final _ircIllIllIIlI connection) {
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this);
    }
}
