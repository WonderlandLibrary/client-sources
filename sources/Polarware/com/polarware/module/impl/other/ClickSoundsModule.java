package com.polarware.module.impl.other;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.ClickEvent;
import com.polarware.util.sound.SoundUtil;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.NumberValue;
import com.polarware.value.impl.SubMode;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(name = "module.other.clicksounds.name", description = "module.other.clicksounds.description", category = Category.OTHER)
public final class ClickSoundsModule extends Module {

    private final ModeValue sound = new ModeValue("Sound", this)
            .add(new SubMode("Standard"))
            .add(new SubMode("Double"))
            .add(new SubMode("Alan"))
            .setDefault("Standard");

    private final NumberValue volume = new NumberValue("Volume", this, 0.5, 0.1, 2, 0.1);
    private final NumberValue variation = new NumberValue("Variation", this, 5, 0, 100, 1);

    @EventLink()
    public final Listener<ClickEvent> onClick = event -> {
        String soundName = "rise.click.standard";

        switch (sound.getValue().getName()) {
            case "Double": {
                soundName = "rise.click.double";
                break;
            }

            case "Alan": {
                soundName = "rise.click.alan";
                break;
            }
        }

        SoundUtil.playSound(soundName, volume.getValue().floatValue(), RandomUtils.nextFloat(1.0F, 1 + variation.getValue().floatValue() / 100f));
    };
}