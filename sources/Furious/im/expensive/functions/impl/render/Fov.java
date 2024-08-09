package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "Fov", type = Category.Render)
public class Fov extends Function {
    private final SliderSetting fovSetting = new SliderSetting("Fov", 100f,10f,165,0.1f);

    public Fov() {
        this.addSettings(new Setting[]{this.fovSetting});
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        float fovCustom = (Float) this.fovSetting.get();
        mc.gameSettings.fov = fovCustom;
    }

    public void onDisable() {
        mc.gameSettings.fov = 90;
        super.onDisable();
    }
}
