// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.net.IrcPacket;

public class Irc00Login implements IrcPacket
{
    public int protocolVersion;
    public Irc00Login$LoginState state;
    public String message;
    public ClientType clientType;
    public String token;
    public String minecraftName;
    public String clientVersion;
    public String ircVersion;
    public boolean supportsExtensions;
    
    @Override
    public void read(final _ircIllIllIIlI connection) {
        this.protocolVersion = connection._ircIllIllIIlI();
        this.state = Irc00Login$LoginState._ircIllIllIIlI(connection._ircIllIllIIlI());
        String message;
        if ((message = connection._ircIllIllIIlI()).length() > 60) {
            message = message.substring(0, 60);
        }
        this.message = message;
        if (this.state == Irc00Login$LoginState.LOGIN) {
            this.clientType = ClientType.getById(connection._ircIllIllIIlI());
            this.token = connection._ircIllIllIIlI();
            this.minecraftName = connection._ircIllIllIIlI();
            this.clientVersion = connection._ircIllIllIIlI();
            this.ircVersion = connection._ircIllIllIIlI();
            this.supportsExtensions = connection._irclllllIIlII();
        }
    }
    
    @Override
    public void write(final _ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.protocolVersion);
        connection._ircIllIllIIlI(this.state.getId());
        connection._irclllllIIlII(this.message);
        if (this.state == Irc00Login$LoginState.LOGIN) {
            connection._ircIllIllIIlI(this.clientType.getId());
            connection._irclllllIIlII(this.token);
            connection._irclllllIIlII(this.minecraftName);
            connection._irclllllIIlII(this.clientVersion);
            connection._irclllllIIlII(this.ircVersion);
            connection._ircIllIllIIlI(this.supportsExtensions);
        }
    }
    
    @Override
    public void handle(final _ircIllIllIIlI connection) {
        if (this.state == Irc00Login$LoginState.SUCCESS) {
            final SkidIrc skidIrc;
            (skidIrc = (SkidIrc)connection._ircIllIllIIlI()).updateNickname(this.message);
            if (connection._ircIllIllIIlI().getMcServerIp() != null && connection._ircIllIllIIlI().getMcServerIp().length() > 0) {
                final Irc09JoinServer packet;
                (packet = new Irc09JoinServer()).server = connection._ircIllIllIIlI().getMcServerIp();
                skidIrc.sendPacket(packet);
            }
            if (connection._ircIllIllIIlI().getExtra() != null) {
                final Irc16SetExtra packet2;
                (packet2 = new Irc16SetExtra()).extra = connection._ircIllIllIIlI().getExtra();
                skidIrc.sendPacket(packet2);
            }
        }
        else {
            if (this.state == Irc00Login$LoginState.FAIL) {
                connection._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new de.liquiddev.ircclient.net.packet._ircIllIllIIlI(this).toString()) + "§" + new _irclllllIIlII(this).toString() + this.message);
                connection._ircIllIllIIlI(this.message);
                return;
            }
            throw new IllegalStateException(String.valueOf(new _ircIllIlIlIII(this).toString()) + this.state);
        }
    }
}
