// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc08Whisper implements IrcPacket
{
    public String player;
    public String message;
    public boolean isFromMe;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.player = connection._ircIllIllIIlI();
        this.message = connection._ircIllIllIIlI();
        this.isFromMe = connection._irclllllIIlII();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.player);
        connection._irclllllIIlII(this.message);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onWhisperMessage(this.player, this.message, this.isFromMe);
    }
}
