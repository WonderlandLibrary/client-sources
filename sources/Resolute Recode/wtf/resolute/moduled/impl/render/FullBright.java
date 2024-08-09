package wtf.resolute.moduled.impl.render;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.Event;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.ModeSetting;

@ModuleAnontion(name = "FullBright", type = Categories.Render,server = "")
public class FullBright extends Module {
    public ModeSetting fbType = new ModeSetting("Type", "Gamma", "Gamma");

    public FullBright() {
        addSettings();
    }


    @Override
    public void onEnable() {
        super.onEnable();
        if (fbType.is("Gamma")) {
            mc.gameSettings.gamma = 1_000_000;
        }
    }

    @Override
    public void onDisable() {
        if (fbType.is("Gamma")) {
            mc.gameSettings.gamma = 1;
        } else {
            mc.player.removePotionEffect(Effects.NIGHT_VISION);
        }
        super.onDisable();
    }
}
