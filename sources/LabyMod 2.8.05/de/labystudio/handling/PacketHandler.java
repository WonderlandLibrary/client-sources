package de.labystudio.handling;

import de.labystudio.packets.Packet;
import de.labystudio.packets.PacketChatVisibilityChange;
import de.labystudio.packets.PacketDisconnect;
import de.labystudio.packets.PacketEncryptionRequest;
import de.labystudio.packets.PacketEncryptionResponse;
import de.labystudio.packets.PacketHelloPing;
import de.labystudio.packets.PacketHelloPong;
import de.labystudio.packets.PacketKick;
import de.labystudio.packets.PacketLoginComplete;
import de.labystudio.packets.PacketLoginData;
import de.labystudio.packets.PacketLoginFriend;
import de.labystudio.packets.PacketLoginOptions;
import de.labystudio.packets.PacketLoginRequest;
import de.labystudio.packets.PacketLoginTime;
import de.labystudio.packets.PacketLoginVersion;
import de.labystudio.packets.PacketMessage;
import de.labystudio.packets.PacketMessages;
import de.labystudio.packets.PacketMojangStatus;
import de.labystudio.packets.PacketPing;
import de.labystudio.packets.PacketPlayAcceptFriendRequest;
import de.labystudio.packets.PacketPlayChangeOptions;
import de.labystudio.packets.PacketPlayDenyFriendRequest;
import de.labystudio.packets.PacketPlayFriendPlayingOn;
import de.labystudio.packets.PacketPlayFriendRemove;
import de.labystudio.packets.PacketPlayFriendStatus;
import de.labystudio.packets.PacketPlayPlayerOnline;
import de.labystudio.packets.PacketPlayRequestAddFriend;
import de.labystudio.packets.PacketPlayRequestAddFriendResponse;
import de.labystudio.packets.PacketPlayRequestRemove;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.packets.PacketPlayTyping;
import de.labystudio.packets.PacketPong;
import de.labystudio.packets.PacketServerMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class PacketHandler extends SimpleChannelInboundHandler<Object>
{
    protected void channelRead0(ChannelHandlerContext ctx, Object packet) throws Exception
    {
        this.handlePacket((Packet)packet);
    }

    private void handlePacket(Packet packet)
    {
        packet.handle(this);
    }

    public abstract void handle(PacketLoginData var1);

    public abstract void handle(PacketHelloPing var1);

    public abstract void handle(PacketHelloPong var1);

    public abstract void handle(PacketPlayPlayerOnline var1);

    public abstract void handle(PacketLoginComplete var1);

    public abstract void handle(PacketChatVisibilityChange var1);

    public abstract void handle(PacketKick var1);

    public abstract void handle(PacketDisconnect var1);

    public abstract void handle(PacketPlayRequestAddFriend var1);

    public abstract void handle(PacketLoginFriend var1);

    public abstract void handle(PacketLoginRequest var1);

    public abstract void handle(PacketMessages var1);

    public abstract void handle(PacketPing var1);

    public abstract void handle(PacketPong var1);

    public abstract void handle(PacketServerMessage var1);

    public abstract void handle(PacketMessage var1);

    public abstract void handle(PacketPlayTyping var1);

    public abstract void handle(PacketPlayRequestAddFriendResponse var1);

    public abstract void handle(PacketPlayAcceptFriendRequest var1);

    public abstract void handle(PacketPlayRequestRemove var1);

    public abstract void handle(PacketPlayDenyFriendRequest var1);

    public abstract void handle(PacketPlayFriendRemove var1);

    public abstract void handle(PacketLoginOptions var1);

    public abstract void handle(PacketPlayServerStatus var1);

    public abstract void handle(PacketPlayFriendStatus var1);

    public abstract void handle(PacketPlayFriendPlayingOn var1);

    public abstract void handle(PacketPlayChangeOptions var1);

    public abstract void handle(PacketLoginTime var1);

    public abstract void handle(PacketLoginVersion var1);

    public abstract void handle(PacketEncryptionRequest var1);

    public abstract void handle(PacketEncryptionResponse var1);

    public abstract void handle(PacketMojangStatus var1);
}
