package com.polarware.component.impl.community;


import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.BackendPacketEvent;
import community.Message;
import packet.Packet;
import packet.impl.server.community.ServerCommunityMessageSend;
import packet.impl.server.community.ServerCommunityPopulatePacket;
import util.type.EvictingList;

public class CommunityComponent extends Component {
    public static EvictingList<Message> messages = new EvictingList<>(200);

    @EventLink()
    public final Listener<BackendPacketEvent> onBackend = event -> {
        Packet packet = event.getPacket();

        if (packet instanceof ServerCommunityPopulatePacket) {
            ServerCommunityPopulatePacket serverCommunityPopulatePacket = ((ServerCommunityPopulatePacket) packet);

            messages.clear();
            messages.addAll(serverCommunityPopulatePacket.getMessages());
        } else if (packet instanceof ServerCommunityMessageSend) {
            ServerCommunityMessageSend messageSend = ((ServerCommunityMessageSend) packet);

            messages.add(messageSend.getMessage());
        }
    };
}
