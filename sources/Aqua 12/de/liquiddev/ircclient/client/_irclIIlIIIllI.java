// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

final class _irclIIlIIIllI
{
    private int _ircIllIllIIlI;
    
    _irclIIlIIIllI(final IrcClientFactory ircClientFactory) {
    }
    
    @Override
    public final String toString() {
        final byte[] bytes = new byte[3];
        this._ircIllIllIIlI = 469259140;
        bytes[0] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = -1857074243;
        bytes[1] = (byte)(this._ircIllIllIIlI >>> 18);
        this._ircIllIllIIlI = 1126485938;
        bytes[2] = (byte)(this._ircIllIllIIlI >>> 12);
        return new String(bytes);
    }
}
