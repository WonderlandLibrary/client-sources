// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc18UpdateNickname implements IrcPacket
{
    public String nickname;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.nickname = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.nickname);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        ((SkidIrc)connection._ircIllIllIIlI()).updateNickname(this.nickname);
    }
}
