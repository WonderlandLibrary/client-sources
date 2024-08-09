package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;

import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import lombok.Getter;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;

@Getter
@FunctionRegister(name = "CustomWorld", type = Category.Render)
public class World extends Function {
    public static SliderSetting timeSlider = new SliderSetting("Время", 1.0F, 1.0F, 24000.0F, 100.0F);

    public World() {
        this.addSettings(timeSlider);
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        IPacket time = e.getPacket();
        if (time instanceof SUpdateTimePacket p) {
            p.worldTime = Math.round(timeSlider.get());
        }

    }
}
