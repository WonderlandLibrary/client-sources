package com.client.glowclient.modules.server;

import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class AutoIgnore extends ModuleContainer
{
    public static StringValue string;
    
    @SubscribeEvent
    public void M(final EventChat eventChat) {
        if (eventChat.getMessage().toLowerCase().contains(AutoIgnore.string.e().toLowerCase())) {
            Wrapper.mc.player.sendChatMessage(new StringBuilder().insert(0, "/ignore ").append(eventChat.getSender()).toString());
        }
    }
    
    static {
        AutoIgnore.string = ValueFactory.M("AutoIgnore", "String", "String of text to search for to ignore", "SAMPLE");
    }
    
    public AutoIgnore() {
        super(Category.SERVER, "AutoIgnore", false, -1, "Automatically ignores players typing specific messages");
    }
}
