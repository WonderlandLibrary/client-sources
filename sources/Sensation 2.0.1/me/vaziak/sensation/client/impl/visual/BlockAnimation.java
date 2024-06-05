package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;

public class BlockAnimation extends Module {

    public StringsProperty mode = new StringsProperty("Mode", "How this cheat will function.", null,
            false, true, new String[]{"Table", "Virtue", "Sensation", "WaxOnWaxOff", "Skidmix", "Matt", "Retarded"});

    public BlockAnimation() {
        super("Animations", Category.VISUAL);
        registerValue(mode);
    }

    @Collect
    public void onUpdate(PlayerUpdateEvent event) {
        setMode(mode.getSelectedStrings().get(0));
    }

}
