package in.momin5.cookieclient.client.modules.render;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {

    public SettingMode mode = register(new SettingMode("Mode",this, "Gamma", "Potion", "Gamma"));

    public FullBright(){
        super("Fullbright", Category.RENDER);
    }


    float oldGamma;

    @Override
    public void onEnable() {
        if(nullCheck())
            return;
        oldGamma = mc.gameSettings.gammaSetting;
    }

    public void onUpdate() {
        if (mode.is("Gamma")) {
            mc.gameSettings.gammaSetting = 666f;
            mc.player.removePotionEffect(Potion.getPotionById(16));
        } else if (mode.is("Potion")) {
            final PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(16), 123456789, 5);
            potionEffect.setPotionDurationMax(true);
            mc.player.addPotionEffect(potionEffect);
        }
    }

    public void onDisable() {
        if(nullCheck())
            return;
        mc.gameSettings.gammaSetting = oldGamma;
        mc.player.removePotionEffect(Potion.getPotionById(16));
    }
}
