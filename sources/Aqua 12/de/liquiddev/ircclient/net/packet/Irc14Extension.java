// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc14Extension implements IrcPacket
{
    public static final int MIN_PROTOCOL_VERSION = 2;
    private _ircIllIIIIllI _ircIllIllIIlI;
    private String _ircIllIllIIlI;
    private byte[] _ircIllIllIIlI;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this._ircIllIllIIlI = _ircIllIIIIllI.values()[connection._ircIllIllIIlI()];
        this._ircIllIllIIlI = connection._ircIllIllIIlI();
        this._ircIllIllIIlI = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this._ircIllIllIIlI.ordinal());
        connection._irclllllIIlII(this._ircIllIllIIlI);
        connection._ircIllIllIIlI(this._ircIllIllIIlI);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
