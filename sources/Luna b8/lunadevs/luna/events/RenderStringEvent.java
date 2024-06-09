package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.client.Minecraft;

public class RenderStringEvent implements Event{
	
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
