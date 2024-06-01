package com.polarware.event.impl.other;

import com.polarware.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class ServerKickEvent implements Event {
    public List<String> message;
}