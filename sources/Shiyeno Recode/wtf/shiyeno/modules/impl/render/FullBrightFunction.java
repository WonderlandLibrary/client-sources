package wtf.shiyeno.modules.impl.render;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "FullBright",
        type = Type.Render
)
public class FullBrightFunction extends Function {
    public ModeSetting fbType = new ModeSetting("Тип", "Gamma", new String[]{"Gamma", "Potion"});
    private final SliderSetting gamma = (new SliderSetting("Мощность", 20.0F, 1.0F, 20.0F, 1.0F)).setVisible(() -> {
        return this.fbType.is("Gamma");
    });

    public FullBrightFunction() {
        this.addSettings(new Setting[]{this.fbType, this.gamma});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate && this.fbType.is("Potion")) {
            mc.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1337, 1));
        }
    }

    protected void onEnable() {
        super.onEnable();
        if (this.fbType.is("Gamma")) {
            mc.gameSettings.gamma = (double)this.gamma.getValue().floatValue();
        }
    }

    protected void onDisable() {
        if (this.fbType.is("Gamma")) {
            mc.gameSettings.gamma = 1.0;
        } else {
            mc.player.removePotionEffect(Effects.NIGHT_VISION);
        }

        super.onDisable();
    }
}