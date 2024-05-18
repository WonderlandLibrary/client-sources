package us.dev.direkt.module.internal.misc.decoder;

import net.minecraft.network.play.server.SPacketChat;
import us.dev.direkt.module.internal.misc.decoder.handler.Driver;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Word Decode", aliases = "decoder", category = ModCategory.MISC)
public class WordDecode extends ToggleableModule {
	
	private Timer timer = new Timer();
	private int anas;
	private String[] anasString;

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if(anas >= anasString.length) {
            anas = 0;
            anasString = null;
        }
        if(timer.hasReach(420)) {
            Wrapper.sendChatMessage(anasString[anas]);
            timer.reset();
            this.anas++;
        }
    });

    @Listener
    protected Link<EventPostReceivePacket> onPostReceivePacket = new Link<>(event -> {
        SPacketChat packet = (SPacketChat) event.getPacket();
        if (packet.getChatComponent().getFormattedText().contains("unscrambled the word"))
            anasString = null;
        String msg = packet.getChatComponent().toString().replace("" + System.lineSeparator(), "");
        msg = msg.substring(msg.indexOf("HoverEvent"));
        msg = msg.substring(msg.indexOf("text=") + 6, msg.indexOf("', siblings=[], style=Style"));
        anasString = Driver.UnScramble(msg).split(" ");     //this can produce an NPE
    }, new PacketFilter<>(SPacketChat.class));
 	
}


