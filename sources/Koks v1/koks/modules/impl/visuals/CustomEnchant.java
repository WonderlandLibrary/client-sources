package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EffectTextureEvent;
import koks.modules.Module;
import net.minecraft.util.ResourceLocation;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 18:34
 */
public class CustomEnchant extends Module {

    public CustomEnchant() {
        super("CustomEnchant", "You have a better enchantment texture", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EffectTextureEvent) {
            EffectTextureEvent e = (EffectTextureEvent)event;
            e.setTexture(new ResourceLocation("client/textures/enchants/" + "rainbow" + ".png"));
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
