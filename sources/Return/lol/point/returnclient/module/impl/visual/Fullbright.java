package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(
        name = "Fullbright",
        description = "takes away the dark :)",
        category = Category.RENDER
)
public class Fullbright extends Module {
    private final StringSetting mode = new StringSetting("Mode", new String[]{"Gamma", "Night vision"});
    private final NumberSetting gamma = new NumberSetting("Brightness", 10, 1, 10, 1);

    private float oldGamma;

    public Fullbright() {
        addSettings(mode, gamma);
    }

    public String getSuffix() {
        return mode.value;
    }

    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
    }

    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        mc.gameSettings.gammaSetting = oldGamma;

        if (mc.thePlayer.isPotionActive(Potion.nightVision.id)) {
            mc.thePlayer.removePotionEffect(Potion.nightVision.id);
        }
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        switch (mode.value) {
            case "Gamma":
                mc.gameSettings.gammaSetting = gamma.value.floatValue();
                break;
            case "Night vision":
                mc.gameSettings.gammaSetting = oldGamma;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 0));
                break;
        }
    });
}
