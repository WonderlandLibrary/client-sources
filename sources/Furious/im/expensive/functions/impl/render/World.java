package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;

import im.expensive.events.EventPacket;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import lombok.Getter;
import net.minecraft.network.play.server.SUpdateTimePacket;

@Getter
@FunctionRegister(name = "WorldTime", type = Category.Render)
public class World extends Function {

    private final SliderSetting time = new SliderSetting("Кастом время", 25000f, 0f, 230000f, 50f);

    public World() {
        addSettings(time);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SUpdateTimePacket p) {
            p.worldTime = time.get().longValue();
        }
    }
}
