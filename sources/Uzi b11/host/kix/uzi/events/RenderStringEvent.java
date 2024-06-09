package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.Minecraft;

/**
 * Created by myche on 3/1/2017.
 */
public class RenderStringEvent implements Event{

    private final Minecraft mc;
    private String string;

    public RenderStringEvent(final String string) {
        this.mc = Minecraft.getMinecraft();
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    public void setString(final String string) {
        this.string = string;
    }

}
