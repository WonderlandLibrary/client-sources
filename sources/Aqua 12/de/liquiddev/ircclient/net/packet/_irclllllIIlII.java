// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

final class _irclllllIIlII
{
    private int _ircIllIllIIlI;
    
    _irclllllIIlII(final Irc00Login irc00Login) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = { 0 };
        this._ircIllIllIIlI = 474243980;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        return new String(bytes);
    }
}
