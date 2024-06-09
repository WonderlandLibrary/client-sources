// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

public enum ClientType
{
    QUERY("QUERY", 0, new _ircIllIllIIlI().toString(), -1), 
    UNKNOWN("UNKNOWN", 1, new _ircIIIIlIlIlI().toString(), 0), 
    SKID("SKID", 2, new _irclIIIIlIIII().toString(), 1), 
    SLOWLY("SLOWLY", 3, new _irclIIlIIIIIl().toString(), 2), 
    FLEX("FLEX", 4, new _ircIlIIlIllIl().toString(), 3), 
    COKEZ("COKEZ", 5, new _irclllIIlIIlI().toString(), 4), 
    VIOLENCE("VIOLENCE", 6, new _ircIlIlllIllI().toString(), 5), 
    SOMNIA("SOMNIA", 7, new _ircIllIllIlII().toString(), 6), 
    FANTA("FANTA", 8, new _ircIIIIIIIlll().toString(), 7), 
    JUSTY("JUSTY", 9, new _irclllllIIlII().toString(), 8), 
    FATALITY("FATALITY", 10, new _ircIllIlIlIII().toString(), 9), 
    ANDROID("ANDROID", 11, new _irclIIlIlllll().toString(), 10), 
    FABRIC("FABRIC", 12, new _irclllIIlIlIl().toString(), 11), 
    CANDY("CANDY", 13, new _ircIIlIIIlIlI().toString(), 12), 
    TIERRA("TIERRA", 14, new _ircIIIlIlllII().toString(), 13), 
    KOKS("KOKS", 15, new _ircIllIIIIllI().toString(), 14), 
    XANX("XANX", 16, new _ircIIIIIlIIll().toString(), 15), 
    SKIDSENSE("SKIDSENSE", 17, new _ircIIIIllIlll().toString(), 16), 
    BASHCLIENT("BASHCLIENT", 18, new _ircIllIIIlIlI().toString(), 17), 
    CECTUS("CECTUS", 19, new _ircIllIlIIIII().toString(), 18), 
    MIDNIGHT("MIDNIGHT", 20, new _ircIlIIIIlIlI().toString(), 19), 
    CENTRUM("CENTRUM", 21, new _ircIllIIlIlll().toString(), 20);
    
    private String _ircIllIllIIlI;
    private int _ircIllIllIIlI;
    
    static {
        _ircIllIllIIlI = new ClientType[] { ClientType.QUERY, ClientType.UNKNOWN, ClientType.SKID, ClientType.SLOWLY, ClientType.FLEX, ClientType.COKEZ, ClientType.VIOLENCE, ClientType.SOMNIA, ClientType.FANTA, ClientType.JUSTY, ClientType.FATALITY, ClientType.ANDROID, ClientType.FABRIC, ClientType.CANDY, ClientType.TIERRA, ClientType.KOKS, ClientType.XANX, ClientType.SKIDSENSE, ClientType.BASHCLIENT, ClientType.CECTUS, ClientType.MIDNIGHT, ClientType.CENTRUM };
    }
    
    public static ClientType getById(final int id) {
        ClientType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ClientType clientType = values[i];
            if (id == clientType.getId()) {
                return clientType;
            }
        }
        return ClientType.UNKNOWN;
    }
    
    public static ClientType getByName(String name) {
        name = name.replace(new _irclIIIlIlllI().toString(), new _ircIIIlIIIIll().toString());
        ClientType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ClientType clientType;
            if ((clientType = values[i]).getName().replace(new _irclIIlIllIII().toString(), new _irclllIllIIII().toString()).equalsIgnoreCase(name)) {
                return clientType;
            }
        }
        return ClientType.UNKNOWN;
    }
    
    private ClientType(final String name2, final int ordinal, final String name, final int id) {
        this._ircIllIllIIlI = id;
        this._ircIllIllIIlI = name;
    }
    
    public final String getName() {
        return this._ircIllIllIIlI;
    }
    
    public final int getId() {
        return this._ircIllIllIIlI;
    }
}
