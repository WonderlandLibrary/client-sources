// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc03Chat implements IrcPacket
{
    public String clientType;
    public String player;
    public String message;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.clientType = connection._ircIllIllIIlI();
        this.player = connection._ircIllIllIIlI();
        this.message = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.message);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onPlayerChatMessage(this.player, this.clientType, this.message);
    }
}
