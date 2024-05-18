// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

public class User
{
    private String _prefix;
    private String _nick;
    private String _lowerNick;
    
    User(final String prefix, final String nick) {
        this._prefix = prefix;
        this._nick = nick;
        this._lowerNick = nick.toLowerCase();
    }
    
    public String getPrefix() {
        return this._prefix;
    }
    
    public boolean isOp() {
        return this._prefix.indexOf(64) >= 0;
    }
    
    public boolean hasVoice() {
        return this._prefix.indexOf(43) >= 0;
    }
    
    public String getNick() {
        return this._nick;
    }
    
    public String toString() {
        return this.getPrefix() + this.getNick();
    }
    
    public boolean equals(final String s) {
        return s.toLowerCase().equals(this._lowerNick);
    }
    
    public boolean equals(final Object o) {
        return o instanceof User && ((User)o)._lowerNick.equals(this._lowerNick);
    }
    
    public int hashCode() {
        return this._lowerNick.hashCode();
    }
    
    public int compareTo(final Object o) {
        if (o instanceof User) {
            return ((User)o)._lowerNick.compareTo(this._lowerNick);
        }
        return -1;
    }
}
