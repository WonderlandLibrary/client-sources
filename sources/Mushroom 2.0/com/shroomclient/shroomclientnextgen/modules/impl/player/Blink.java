package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.BlinkUtil;

@RegisterModule(
    name = "Blink",
    uniqueId = "blink",
    description = "Temporarily Chokes Data Being Sent To Server",
    category = ModuleCategory.Player
)
public class Blink extends Module {

    @ConfigOption(
        name = "Blink Incoming Packets",
        description = "Blocks Packets From Servers",
        order = 1
    )
    public Boolean incoming = false;

    @ConfigOption(
        name = "Blink Outgoing Packets",
        description = "Stops Your Packets From Being Sent",
        order = 2
    )
    public Boolean outgoing = true;

    @Override
    protected void onEnable() {
        if (incoming) BlinkUtil.setIncomingBlink(true);
        if (outgoing) BlinkUtil.setOutgoingBlink(true);
    }

    @Override
    protected void onDisable() {
        if (incoming) BlinkUtil.setIncomingBlink(false);
        if (outgoing) BlinkUtil.setOutgoingBlink(false);
    }
}
