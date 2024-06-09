// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import java.util.NoSuchElementException;

public enum Irc00Login$LoginState
{
    LOGIN("LOGIN", 0, 0), 
    SUCCESS("SUCCESS", 1, 1), 
    FAIL("FAIL", 2, 2);
    
    private int _ircIllIllIIlI;
    
    static {
        _ircIllIllIIlI = new Irc00Login$LoginState[] { Irc00Login$LoginState.LOGIN, Irc00Login$LoginState.SUCCESS, Irc00Login$LoginState.FAIL };
    }
    
    static Irc00Login$LoginState _ircIllIllIIlI(final int i) {
        Irc00Login$LoginState[] values;
        for (int length = (values = values()).length, j = 0; j < length; ++j) {
            final Irc00Login$LoginState irc00Login$LoginState;
            if ((irc00Login$LoginState = values[j])._ircIllIllIIlI == i) {
                return irc00Login$LoginState;
            }
        }
        throw new NoSuchElementException(String.valueOf(new _irclIIlIlllll().toString()) + i + new _irclllIIlIlIl().toString());
    }
    
    private Irc00Login$LoginState(final String name, final int ordinal, final int id) {
        this._ircIllIllIIlI = id;
    }
    
    public final int getId() {
        return this._ircIllIllIIlI;
    }
}
