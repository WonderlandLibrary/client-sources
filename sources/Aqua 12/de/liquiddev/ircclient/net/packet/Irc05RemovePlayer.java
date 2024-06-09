// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc05RemovePlayer implements IrcPacket
{
    public byte[] hash;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.hash = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.hash);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        final IrcPlayer byHash;
        if ((byHash = IrcPlayer.getByHash(this.hash)) != null) {
            byHash.unregister();
        }
    }
}
