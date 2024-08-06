package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Cancellable;
import com.shroomclient.shroomclientnextgen.events.Event;
import com.shroomclient.shroomclientnextgen.modules.Module;
import lombok.Data;

@Cancellable
@Data
public class ModuleStateChangeEvent extends Event {

    public final Class<? extends Module> module;
    public final boolean oldState;
    public final boolean newState;
}
