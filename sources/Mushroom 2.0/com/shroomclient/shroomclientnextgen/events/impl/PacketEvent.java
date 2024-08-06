package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Cancellable;
import com.shroomclient.shroomclientnextgen.events.Event;
import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.network.packet.Packet;

public class PacketEvent {

    public static class Send {

        @Cancellable
        @Value
        @AllArgsConstructor
        public static class Pre extends Event {

            Packet<?> packet;
            boolean manualSend;
        }

        @Value
        @AllArgsConstructor
        public static class Post extends Event {

            Packet<?> packet;
            boolean manualSend;
        }
    }

    public static class Receive {

        @Cancellable
        @Value
        @AllArgsConstructor
        public static class Pre extends Event {

            Packet<?> packet;
        }

        @Value
        @AllArgsConstructor
        public static class Post extends Event {

            Packet<?> packet;
        }
    }
}
