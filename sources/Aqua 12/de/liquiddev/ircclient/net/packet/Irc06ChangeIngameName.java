// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc06ChangeIngameName implements IrcPacket
{
    public String name;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.name = connection._ircIllIllIIlI();
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.name);
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
    }
}
