// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc13UpdateRank implements IrcPacket
{
    public IrcRank rank;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.rank = IrcRank.getById(connection._ircIllIllIIlI());
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.rank.getId());
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        ((SkidIrc)connection._ircIllIllIIlI()).updateRank(this.rank);
    }
}
