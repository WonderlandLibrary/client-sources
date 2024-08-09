package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;

import im.expensive.events.EventPacket;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import lombok.Getter;
import net.minecraft.network.play.server.SUpdateTimePacket;

@Getter
@FunctionRegister(name = "World", type = Category.Render)
public class World extends Function {

    public ModeSetting time = new ModeSetting("Time", "Day", "Day", "Night");

    public World() {
        addSettings(time);
    }
    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SUpdateTimePacket p) {
            if (time.get().equalsIgnoreCase("Day"))
                p.worldTime = 1000L;
            else
                p.worldTime = 13000L;
        }
    }
}
