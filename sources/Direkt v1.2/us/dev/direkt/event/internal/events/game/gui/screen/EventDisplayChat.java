package us.dev.direkt.event.internal.events.game.gui.screen;

import net.minecraft.client.gui.GuiNewChat;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventDisplayChat extends CancellableEvent {
    private GuiNewChat guiNewChat;

    public EventDisplayChat(GuiNewChat guiNewChat) {
        this.guiNewChat = guiNewChat;
    }

    public void setChatGui(GuiNewChat chatGui) {
        this.guiNewChat = chatGui;
    }

    public GuiNewChat getChatGui() {
        return this.guiNewChat;
    }
}
