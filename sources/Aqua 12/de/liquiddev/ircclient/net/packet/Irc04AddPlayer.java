// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc04AddPlayer implements IrcPacket
{
    public byte[] hash;
    public String ircName;
    public int rankID;
    public String clientType;
    public String serverIp;
    public String extra;
    public long playtime;
    public String clientVersion;
    public String capeLocation;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.hash = connection._ircIllIllIIlI();
        this.ircName = connection._ircIllIllIIlI();
        this.rankID = connection._ircIllIllIIlI();
        this.clientType = connection._ircIllIllIIlI();
        this.serverIp = connection._ircIllIllIIlI();
        this.extra = connection._ircIllIllIIlI();
        this.playtime = connection._ircIllIllIIlI();
        this.clientVersion = connection._ircIllIllIIlI();
        this.capeLocation = connection._ircIllIllIIlI();
        if (this.capeLocation.equalsIgnoreCase(new _ircIIlIIIlIlI(this).toString())) {
            this.capeLocation = null;
        }
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.hash);
        connection._irclllllIIlII(this.ircName);
        connection._ircIllIllIIlI(this.rankID);
        connection._irclllllIIlII(this.clientType);
        connection._irclllllIIlII(this.serverIp);
        connection._irclllllIIlII(this.extra);
        connection._ircIllIllIIlI(this.playtime);
        connection._irclllllIIlII(this.clientVersion);
        connection._irclllllIIlII(this.capeLocation);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        new IrcPlayer(this.ircName, this.hash, this.serverIp, IrcRank.getById(this.rankID), this.clientType, this.extra, this.playtime, this.clientVersion, this.capeLocation).register();
    }
}
