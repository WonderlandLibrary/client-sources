package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;

public class PartyChat extends Module
{
    public PartyChat() {
        super("PartyChat", "/chat party, hypixel moment", 0, Category.REKTSKY);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof ChatEvent && !((ChatEvent)event).getMessage().startsWith("/") && !((ChatEvent)event).getMessage().startsWith(".")) {
            ((ChatEvent)event).setCanceled(true);
            this.mc.thePlayer.sendChatMessage("/p " + ((ChatEvent)event).getMessage());
        }
    }
}
