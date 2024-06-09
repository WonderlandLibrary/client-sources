// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

final class _ircIIlIIIlIlI
{
    private int _ircIllIllIIlI;
    
    _ircIIlIIIlIlI(final Irc04AddPlayer irc04AddPlayer) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[4];
        this._ircIllIllIIlI = 477009254;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 138075999;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 15);
        this._ircIllIllIIlI = -1254734140;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = -441202163;
        bytes[3] = (byte)(this._ircIllIllIIlI >>> 18);
        return new String(bytes);
    }
}
