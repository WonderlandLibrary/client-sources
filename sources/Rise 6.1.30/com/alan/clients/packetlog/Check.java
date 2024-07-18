package com.alan.clients.packetlog;

import com.alan.clients.util.Accessor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public abstract class Check implements Accessor {
    public abstract boolean run();
}