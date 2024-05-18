package pw.latematt.xiv.event.events;

import net.minecraft.client.Minecraft;
import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class RenderStringEvent extends Event {
    private final Minecraft mc = Minecraft.getMinecraft();

    private String string;
    private final State state;

    public RenderStringEvent(String string, State state) {
        this.string = string;
        this.state = state;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public State getState() {
        return state;
    }

    public enum State {
        TAB,
        SCOREBOARD,
        CHAT,
        NAMETAG;
    }
}
