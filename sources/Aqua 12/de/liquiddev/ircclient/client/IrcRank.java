// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

public enum IrcRank
{
    USER("USER", 0, 0, 0, new _ircllIlIlIlIl().toString()), 
    VIP("VIP", 1, 1, 10, String.valueOf(new _ircIIlIllIlIl().toString()) + "§" + new _irclllllllllI().toString()), 
    MOD("MOD", 2, 2, 20, String.valueOf(new _ircIIIlIllllI().toString()) + "§" + new _ircIllIIllIII().toString()), 
    ADMIN("ADMIN", 3, 3, 30, String.valueOf(new _irclIIIIIIlII().toString()) + "§" + new _ircIIllllIlIl().toString()), 
    QUERY("QUERY", 4, 4, 40, String.valueOf(new _irclllllIIlIl().toString()) + "§" + new _ircIlIIllIIll().toString()), 
    DEV("DEV", 5, 5, 20, String.valueOf(new _ircllllIllIIl().toString()) + "§" + new _ircIIlllllllI().toString());
    
    private int _ircIllIllIIlI;
    private int _irclllllIIlII;
    private String _ircIllIllIIlI;
    
    static {
        _ircIllIllIIlI = new IrcRank[] { IrcRank.USER, IrcRank.VIP, IrcRank.MOD, IrcRank.ADMIN, IrcRank.QUERY, IrcRank.DEV };
    }
    
    public static IrcRank getById(final int id) {
        IrcRank[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final IrcRank ircRank;
            if ((ircRank = values[i]).getId() == id) {
                return ircRank;
            }
        }
        return IrcRank.USER;
    }
    
    private IrcRank(final String name, final int ordinal, final int id, final int permissionsLevel, final String chatColor) {
        this._ircIllIllIIlI = id;
        this._ircIllIllIIlI = chatColor;
        this._irclllllIIlII = permissionsLevel;
    }
    
    public final int getId() {
        return this._ircIllIllIIlI;
    }
    
    public final int getPermissionLevel() {
        return this._irclllllIIlII;
    }
    
    public final String getChatColor() {
        return this._ircIllIllIIlI;
    }
}
