// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient._ircIllIllIIlI;
import de.liquiddev.ircclient.net.packet.Irc16SetExtra;
import de.liquiddev.ircclient.net.packet.Irc10LeaveServer;
import de.liquiddev.ircclient.net.packet.Irc09JoinServer;
import de.liquiddev.ircclient.net.packet.Irc07ClientCommand;
import de.liquiddev.ircclient.net.packet.Irc08Whisper;
import de.liquiddev.ircclient.net.packet.Irc12LocalChat;
import de.liquiddev.ircclient.net.packet.Irc03Chat;
import de.liquiddev.ircclient.net.packet.Irc06ChangeIngameName;
import de.liquiddev.ircclient.net.packet.Irc17CustomData;
import de.liquiddev.ircclient.net.IrcPacket;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import de.liquiddev.ircclient.util.IrcServers;
import java.util.List;
import javax.net.ssl.SSLContext;
import de.liquiddev.ircclient.net._ircIIIIlIlIlI;
import de.liquiddev.ircclient.api.IrcApiManager;
import de.liquiddev.ircclient.net._ircIllIIIlIlI;
import de.liquiddev.ircclient.api.IrcClient;

public class SkidIrc implements IrcClient
{
    private boolean _ircIllIllIIlI;
    private _ircIllIIIlIlI _ircIllIllIIlI;
    private IrcApiManager _ircIllIllIIlI;
    private int _ircIllIllIIlI;
    private String _ircIllIllIIlI;
    private _ircIIIIlIlIlI _ircIllIllIIlI;
    private String _irclllllIIlII;
    private Thread _ircIllIllIIlI;
    private ClientType _ircIllIllIIlI;
    private SSLContext _ircIllIllIIlI;
    private String _ircIllIlIlIII;
    private String _irclIIlIlllll;
    private String _irclllIIlIlIl;
    private IrcRank _ircIllIllIIlI;
    private boolean _irclllllIIlII;
    private String _ircIIlIIIlIlI;
    private String _ircIIIlIlllII;
    
    public SkidIrc(final SSLContext sslContext, final List serversIps, final int port, final String uuid, final ClientType clientType, final String loginToken, final String ingameName, final String clientVersion) {
        this._irclllllIIlII = false;
        this._ircIllIllIIlI = IrcServers.getOnlineServer(serversIps, port);
        this._ircIllIllIIlI = port;
        this._irclllllIIlII = uuid;
        this._ircIllIllIIlI = clientType;
        this._ircIllIllIIlI = sslContext;
        this._ircIllIlIlIII = ingameName;
        this._ircIIIlIlllII = clientVersion;
        this._ircIllIllIIlI = false;
        this._ircIllIllIIlI = new _ircIllIIIlIlI();
        this._ircIllIllIIlI = new IrcApiManager();
        this.connect(loginToken);
    }
    
    public SkidIrc(final SSLContext sslContext, final String serversIp, final int port, final String uuid, final ClientType clientType, final String loginToken, final String ingameName, final String clientVersion) {
        this._irclllllIIlII = false;
        this._ircIllIllIIlI = serversIp;
        this._ircIllIllIIlI = port;
        this._irclllllIIlII = uuid;
        this._ircIllIllIIlI = clientType;
        this._ircIllIllIIlI = sslContext;
        this._ircIllIlIlIII = ingameName;
        this._ircIIIlIlllII = clientVersion;
        this._ircIllIllIIlI = false;
        this._ircIllIllIIlI = new _ircIllIIIlIlI();
        this._ircIllIllIIlI = new IrcApiManager();
        this.connect(loginToken);
    }
    
    public _ircIllIIIlIlI getPacketManager() {
        return this._ircIllIllIIlI;
    }
    
    @Override
    public IrcApiManager getApiManager() {
        return this._ircIllIllIIlI;
    }
    
    public Thread getNetworkThread() {
        return this._ircIllIllIIlI;
    }
    
    @Override
    public void connect(String loginToken) {
        while (!this._ircIllIllIIlI) {
            try {
                this._ircIllIllIIlI = new _ircIIIIlIlIlI(this, (SSLSocket)this._ircIllIllIIlI.getSocketFactory().createSocket(this._ircIllIllIIlI, this._ircIllIllIIlI), loginToken);
                this._ircIllIllIIlI = true;
                (this._ircIllIllIIlI = new Thread(this._ircIllIllIIlI)).setName(new _ircIllIlllIlI(this).toString());
                this._ircIllIllIIlI.start();
                this._ircIllIllIIlI._ircIllIllIIlI();
                this._ircIllIllIIlI._ircIllIlIlIII();
            }
            catch (IOException ex) {
                this._ircIllIllIIlI.onChatMessage(String.valueOf(new _irclllIlllllI(this).toString()) + 10 + new _ircIIIlllllll(this).toString());
                ex.printStackTrace();
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException s) {
                    loginToken = s;
                    ((Throwable)s).printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void disconnect() {
        this._irclllllIIlII = true;
        this._ircIllIllIIlI._ircIllIllIIlI(new _ircIIIIIlIlII(this).toString());
    }
    
    public void sendPacket(final IrcPacket packet) {
        if (this._ircIllIllIIlI) {
            this._ircIllIllIIlI._ircIllIllIIlI(packet);
        }
    }
    
    @Override
    public void sendCustomData(final String tag, final byte[] data) {
        final Irc17CustomData packet;
        (packet = new Irc17CustomData()).target = new _ircllllllIIIl(this).toString();
        packet.tag = tag;
        packet.data = data;
        this.sendPacket(packet);
    }
    
    @Override
    public void sendCustomData(final String tag, final byte[] data, final IrcPlayer target) {
        final Irc17CustomData packet;
        (packet = new Irc17CustomData()).target = target.getIrcNick();
        packet.tag = tag;
        packet.data = data;
        this.sendPacket(packet);
    }
    
    @Override
    public void setIngameName(final String name) {
        if (name.equals(this._ircIllIlIlIII)) {
            return;
        }
        this._ircIllIlIlIII = name;
        final Irc06ChangeIngameName packet;
        (packet = new Irc06ChangeIngameName()).name = name;
        this.sendPacket(packet);
    }
    
    @Override
    public void sendChatMessage(final String message) {
        final Irc03Chat packet;
        (packet = new Irc03Chat()).message = message;
        this.sendPacket(packet);
    }
    
    @Override
    public void sendLocalChatMessage(final String message) {
        final Irc12LocalChat packet;
        (packet = new Irc12LocalChat()).message = message;
        this.sendPacket(packet);
    }
    
    @Override
    public void sendWhisperMessage(final String targetPlayer, final String message) {
        final Irc08Whisper packet;
        (packet = new Irc08Whisper()).player = targetPlayer;
        packet.message = message;
        this.sendPacket(packet);
    }
    
    @Override
    public void executeCommand(String command) {
        if (command.toLowerCase().startsWith(new _ircllIlIIlIIl(this).toString())) {
            command = command.substring(5);
        }
        final Irc07ClientCommand packet;
        (packet = new Irc07ClientCommand()).command = command;
        this.sendPacket(packet);
    }
    
    @Override
    public void setMcServerIp(final String ip) {
        if (ip == null || ip.length() == 0) {
            this.leaveMcServer(new _ircIIlllIIIlI(this).toString());
            return;
        }
        if (ip.equals(this._irclllIIlIlIl)) {
            return;
        }
        this._irclllIIlIlIl = ip;
        final Irc09JoinServer packet;
        (packet = new Irc09JoinServer()).server = ip;
        this.sendPacket(packet);
    }
    
    @Override
    public void leaveMcServer(final String reason) {
        if (this._irclllIIlIlIl == null) {
            return;
        }
        this._irclllIIlIlIl = null;
        final Irc10LeaveServer packet;
        (packet = new Irc10LeaveServer()).reason = reason;
        this.sendPacket(packet);
    }
    
    public void updateRank(final IrcRank rank) {
        this._ircIllIllIIlI = rank;
    }
    
    public void updateNickname(final String nick) {
        this._irclIIlIlllll = nick;
    }
    
    public String getHost() {
        return this._ircIllIllIIlI;
    }
    
    public int getServerPort() {
        return this._ircIllIllIIlI;
    }
    
    @Override
    public String getUuid() {
        return this._irclllllIIlII;
    }
    
    @Override
    public ClientType getType() {
        return this._ircIllIllIIlI;
    }
    
    public SSLContext getSSLContext() {
        return this._ircIllIllIIlI;
    }
    
    @Override
    public String getIngameName() {
        return this._ircIllIlIlIII;
    }
    
    @Override
    public String getMcServerIp() {
        return this._irclllIIlIlIl;
    }
    
    @Override
    public IrcRank getRank() {
        return this._ircIllIllIIlI;
    }
    
    @Override
    public boolean isForcedDisconnect() {
        return this._irclllllIIlII;
    }
    
    @Override
    public void setExtra(final String extra) {
        if (extra.equals(this._ircIIlIIIlIlI)) {
            return;
        }
        this._ircIIlIIIlIlI = extra;
        final Irc16SetExtra packet;
        (packet = new Irc16SetExtra()).extra = this._ircIIlIIIlIlI;
        this.sendPacket(packet);
    }
    
    @Override
    public String getExtra() {
        return this._ircIIlIIIlIlI;
    }
    
    @Override
    public String getIrcVersion() {
        return de.liquiddev.ircclient._ircIllIllIIlI._ircIllIllIIlI;
    }
    
    @Override
    public String getClientVersion() {
        return this._ircIIIlIlllII;
    }
    
    @Override
    public int getProtocolVersion() {
        return 11;
    }
    
    @Override
    public String getNickname() {
        if (this._irclIIlIlllll == null) {
            throw new IllegalStateException(new _ircIllIlIIllI(this).toString());
        }
        return this._irclIIlIlllll;
    }
    
    @Override
    public void setCape(final String name) {
        this.sendCustomData(new _ircIlllIIlIIl(this).toString(), name.getBytes());
    }
}
