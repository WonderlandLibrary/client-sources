// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.net;

import de.liquiddev.ircclient.net.packet.Irc18UpdateNickname;
import de.liquiddev.ircclient.net.packet.Irc17CustomData;
import de.liquiddev.ircclient.net.packet.Irc16SetExtra;
import de.liquiddev.ircclient.net.packet.Irc15LegacyProtocolVersion;
import de.liquiddev.ircclient.net.packet.Irc14Extension;
import de.liquiddev.ircclient.net.packet.Irc13UpdateRank;
import de.liquiddev.ircclient.net.packet.Irc12LocalChat;
import de.liquiddev.ircclient.net.packet.Irc11PopupMessage;
import de.liquiddev.ircclient.net.packet.Irc10LeaveServer;
import de.liquiddev.ircclient.net.packet.Irc09JoinServer;
import de.liquiddev.ircclient.net.packet.Irc08Whisper;
import de.liquiddev.ircclient.net.packet.Irc07ClientCommand;
import de.liquiddev.ircclient.net.packet.Irc06ChangeIngameName;
import de.liquiddev.ircclient.net.packet.Irc05RemovePlayer;
import de.liquiddev.ircclient.net.packet.Irc04AddPlayer;
import de.liquiddev.ircclient.net.packet.Irc03Chat;
import de.liquiddev.ircclient.net.packet.Irc02Info;
import de.liquiddev.ircclient.net.packet.Irc01KeepAlive;
import de.liquiddev.ircclient.net.packet.Irc00Login;
import java.util.ArrayList;

public final class _ircIllIIIlIlI
{
    private static int _ircIllIllIIlI = 11;
    private ArrayList _ircIllIllIIlI;
    
    public _ircIllIIIlIlI() {
        (this._ircIllIllIIlI = new ArrayList()).add(Irc00Login.class);
        this._ircIllIllIIlI.add(Irc01KeepAlive.class);
        this._ircIllIllIIlI.add(Irc02Info.class);
        this._ircIllIllIIlI.add(Irc03Chat.class);
        this._ircIllIllIIlI.add(Irc04AddPlayer.class);
        this._ircIllIllIIlI.add(Irc05RemovePlayer.class);
        this._ircIllIllIIlI.add(Irc06ChangeIngameName.class);
        this._ircIllIllIIlI.add(Irc07ClientCommand.class);
        this._ircIllIllIIlI.add(Irc08Whisper.class);
        this._ircIllIllIIlI.add(Irc09JoinServer.class);
        this._ircIllIllIIlI.add(Irc10LeaveServer.class);
        this._ircIllIllIIlI.add(Irc11PopupMessage.class);
        this._ircIllIllIIlI.add(Irc12LocalChat.class);
        this._ircIllIllIIlI.add(Irc13UpdateRank.class);
        this._ircIllIllIIlI.add(Irc14Extension.class);
        this._ircIllIllIIlI.add(Irc15LegacyProtocolVersion.class);
        this._ircIllIllIIlI.add(Irc16SetExtra.class);
        this._ircIllIllIIlI.add(Irc17CustomData.class);
        this._ircIllIllIIlI.add(Irc18UpdateNickname.class);
    }
    
    public final Class _ircIllIllIIlI(final int index) {
        if (index == 255) {
            return Irc00Login.class;
        }
        return this._ircIllIllIIlI.get(index);
    }
    
    public final int _ircIllIllIIlI(final IrcPacket ircPacket) {
        if (ircPacket instanceof Irc00Login) {
            return 255;
        }
        return this._ircIllIllIIlI.indexOf(ircPacket.getClass());
    }
    
    public final int _ircIllIllIIlI() {
        return this._ircIllIllIIlI.size();
    }
}
