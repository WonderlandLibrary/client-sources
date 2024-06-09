// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc17CustomData implements IrcPacket
{
    public String target;
    public String tag;
    public byte[] data;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.target = connection._ircIllIllIIlI();
        this.tag = connection._ircIllIllIIlI();
        this.data = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.target);
        connection._irclllllIIlII(this.tag);
        connection._ircIllIllIIlI(this.data);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onCustomData(this.target, this.tag, this.data);
    }
}
