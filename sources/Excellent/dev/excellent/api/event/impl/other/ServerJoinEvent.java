package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerJoinEvent extends Event {

    public String ip;
    public int port;
}