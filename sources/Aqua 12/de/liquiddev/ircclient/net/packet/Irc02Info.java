// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc02Info implements IrcPacket
{
    public String message;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.message = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.message);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onChatMessage(this.message);
    }
}
