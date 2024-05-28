package arsenic.module.impl.visual;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventTick;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.EnumProperty;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "FullBright", category = ModuleCategory.Visual, keybind = Keyboard.KEY_F,hidden = true)
public class FullBright extends Module {
    public final EnumProperty<fEnum> fullbrightmode = new EnumProperty<>("Mode: ",fEnum.Gamma );

    int originalGamma = 0;

    @Override
    protected void onEnable() {
        originalGamma = (int) mc.gameSettings.gammaSetting;
    }
    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if (fullbrightmode.getValue().equals(fEnum.Gamma)) {
            mc.gameSettings.gammaSetting = 1000;
        }
        if (fullbrightmode.getValue().equals(fEnum.Potion)) {
            Potion nightVision = Potion.getPotionFromResourceLocation("night_vision");
            PotionEffect nightVisionEffect = mc.thePlayer.getActivePotionEffect(nightVision);
            if (nightVisionEffect == null) {
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 69420));
            }
        }
    };

    @Override
    protected void onDisable() {
        mc.gameSettings.gammaSetting = originalGamma;
        mc.thePlayer.removePotionEffect(Potion.nightVision.id);
    }

    public enum fEnum {
        Gamma,Potion
    }
}
