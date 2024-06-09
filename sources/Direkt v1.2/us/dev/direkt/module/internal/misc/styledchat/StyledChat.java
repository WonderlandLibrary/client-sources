package us.dev.direkt.module.internal.misc.styledchat;

import org.apache.logging.log4j.core.appender.SyslogAppender;

import net.minecraft.network.play.client.CPacketChatMessage;
import us.dev.api.property.Property;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.misc.styledchat.internal.modes.*;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;


/**
 * @author Foundry
 */
@ModData(label = "Styled Chat", aliases = {"fancychat", "chatmod"}, category = ModCategory.MISC)
public class StyledChat extends ToggleableModule {

    @Exposed(description = "The font style to be used")
    final Property<Mode> mode = new Property<>("Mode", Mode.CAPS);

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        final String originalMessage = ((CPacketChatMessage) event.getPacket()).getMessage();
        String workingMessage = originalMessage, temp = "";
        if (originalMessage.toLowerCase().startsWith("/message ") || originalMessage.toLowerCase().startsWith("/tell ") || originalMessage.toLowerCase().startsWith("/whisper ") || originalMessage.toLowerCase().startsWith("/m ") || originalMessage.toLowerCase().startsWith("/t ") || originalMessage.toLowerCase().startsWith("/w ")) {
            String args[] = originalMessage.split(" ");
            temp = args[0] + " " + args[1] + " ";
            workingMessage = originalMessage.substring(temp.length());
        } else if (originalMessage.toLowerCase().startsWith("/r ")) {
            String args[] = originalMessage.split(" ");
            temp = args[0] + " ";
            workingMessage = originalMessage.substring(temp.length());
        }
        System.out.println(mode.getValue().getBackingMode().formatMessage(workingMessage));
        if (!originalMessage.toLowerCase().startsWith(("/")) || originalMessage.toLowerCase().startsWith("/message ") || originalMessage.toLowerCase().startsWith("/tell ") || originalMessage.toLowerCase().startsWith("/whisper ") || originalMessage.toLowerCase().startsWith("/m ") || originalMessage.toLowerCase().startsWith("/t ") || originalMessage.toLowerCase().startsWith("/w ") || originalMessage.toLowerCase().startsWith("/r ")) {
            event.setPacket(new CPacketChatMessage(mode.getValue().getBackingMode().formatMessage(workingMessage)));
        }
    }, Link.VERY_LOW_PRIORITY, new PacketFilter<>(CPacketChatMessage.class));

    public enum Mode {
        BUBBLE(new BubbleFontMode()), CAPS(new CapsMode()), FANCY(new FancyMode()),
        MINICAPS(new MiniCapsMode()), MIRRORED(new MirroredMode()), SCRIPT(new ScriptMode());

        private final StyledChatMode backingMode;

        Mode(StyledChatMode backingMode) {
            this.backingMode = backingMode;
        }

        private StyledChatMode getBackingMode() {
            return this.backingMode;
        }

        @Override
        public String toString() {
            return backingMode.getLabel();
        }
    }
}
