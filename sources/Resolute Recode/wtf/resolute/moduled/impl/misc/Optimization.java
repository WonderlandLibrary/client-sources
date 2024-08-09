package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;

@ModuleAnontion(name = "Optimization", type = Categories.Misc,server = "")
public class Optimization extends Module {

    public final BooleanSetting autoJump = new BooleanSetting("Авто прыжок", true);
    public final BooleanSetting ofSky = new BooleanSetting("Оптимизация Облоков", true);
    public final BooleanSetting ofCustomSky = new BooleanSetting("Оптимизация Графики неба", true);
    public final BooleanSetting entityShadows = new BooleanSetting("Оптимизация Энтити", true);

    public Optimization() {
        addSettings(ofCustomSky,ofSky,entityShadows);
    }

    @Subscribe
    private void onDisplay(EventDisplay event) {
        if (autoJump.get()) {
            mc.gameSettings.autoJump = false;
        }
        if (ofSky.get()) {
            mc.gameSettings.ofSky = false;
        }
        if (ofCustomSky.get()) {
            mc.gameSettings.ofCustomSky = false;
        }
        if (entityShadows.get()) {
            mc.gameSettings.entityShadows = false;
        }
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long var10000 = j - k - i;
    }
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.autoJump = true;
        mc.gameSettings.ofSky = true;
        mc.gameSettings.ofCustomSky = true;
        mc.gameSettings.entityShadows = true;
    }
}
