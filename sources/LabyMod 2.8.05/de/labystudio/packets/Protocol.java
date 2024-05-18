package de.labystudio.packets;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Protocol
{
    private static final Protocol INSTANCE = new Protocol();
    private Map < Class <? extends Packet > , EnumConnectionState > protocol = new HashMap();
    private Map < Integer, Class <? extends Packet >> packets = new HashMap();

    public static Protocol getProtocol()
    {
        return INSTANCE;
    }

    public Protocol()
    {
        this.register(0, PacketHelloPing.class, EnumConnectionState.HELLO);
        this.register(1, PacketHelloPong.class, EnumConnectionState.HELLO);
        this.register(2, PacketLoginStart.class, EnumConnectionState.LOGIN);
        this.register(3, PacketLoginData.class, EnumConnectionState.LOGIN);
        this.register(4, PacketLoginFriend.class, EnumConnectionState.LOGIN);
        this.register(5, PacketLoginRequest.class, EnumConnectionState.LOGIN);
        this.register(6, PacketLoginOptions.class, EnumConnectionState.LOGIN);
        this.register(7, PacketLoginComplete.class, EnumConnectionState.LOGIN);
        this.register(8, PacketLoginTime.class, EnumConnectionState.LOGIN);
        this.register(9, PacketLoginVersion.class, EnumConnectionState.LOGIN);
        this.register(10, PacketEncryptionRequest.class, EnumConnectionState.LOGIN);
        this.register(11, PacketEncryptionResponse.class, EnumConnectionState.LOGIN);
        this.register(14, PacketPlayPlayerOnline.class, EnumConnectionState.PLAY);
        this.register(16, PacketPlayRequestAddFriend.class, EnumConnectionState.PLAY);
        this.register(17, PacketPlayRequestAddFriendResponse.class, EnumConnectionState.PLAY);
        this.register(18, PacketPlayRequestRemove.class, EnumConnectionState.PLAY);
        this.register(19, PacketPlayDenyFriendRequest.class, EnumConnectionState.PLAY);
        this.register(20, PacketPlayFriendRemove.class, EnumConnectionState.PLAY);
        this.register(21, PacketPlayChangeOptions.class, EnumConnectionState.PLAY);
        this.register(22, PacketPlayServerStatus.class, EnumConnectionState.PLAY);
        this.register(23, PacketPlayFriendStatus.class, EnumConnectionState.PLAY);
        this.register(24, PacketPlayFriendPlayingOn.class, EnumConnectionState.PLAY);
        this.register(25, PacketPlayTyping.class, EnumConnectionState.PLAY);
        this.register(26, PacketMojangStatus.class, EnumConnectionState.PLAY);
        this.register(60, PacketDisconnect.class, EnumConnectionState.ALL);
        this.register(61, PacketKick.class, EnumConnectionState.ALL);
        this.register(62, PacketPing.class, EnumConnectionState.ALL);
        this.register(63, PacketPong.class, EnumConnectionState.ALL);
        this.register(64, PacketServerMessage.class, EnumConnectionState.ALL);
        this.register(65, PacketMessage.class, EnumConnectionState.ALL);
        this.register(66, PacketMessages.class, EnumConnectionState.ALL);
        this.register(67, PacketChatVisibilityChange.class, EnumConnectionState.ALL);
    }

    public Map < Integer, Class <? extends Packet >> getPackets()
    {
        return this.packets;
    }

    private final void register(int id, Class <? extends Packet > clazz, EnumConnectionState state)
    {
        try
        {
            clazz.newInstance();
            this.packets.put(Integer.valueOf(id), clazz);
            this.protocol.put(clazz, state);
        }
        catch (Exception var5)
        {
            System.out.println("Class " + clazz.getSimpleName() + " does not contain a default Constructor, this might break the game :/");
        }
    }

    public Packet getPacket(int id) throws IllegalAccessException, InstantiationException
    {
        if (!this.packets.containsKey(Integer.valueOf(id)))
        {
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        }
        else
        {
            return (Packet)((Class)this.packets.get(Integer.valueOf(id))).newInstance();
        }
    }

    public int getPacketId(Packet packet)
    {
        for (Entry < Integer, Class <? extends Packet >> entry : this.packets.entrySet())
        {
            Class <? extends Packet > oclass = (Class)entry.getValue();

            if (oclass.isInstance(packet))
            {
                return ((Integer)entry.getKey()).intValue();
            }
        }

        throw new RuntimeException("Packet " + packet + " is not registered.");
    }
}
