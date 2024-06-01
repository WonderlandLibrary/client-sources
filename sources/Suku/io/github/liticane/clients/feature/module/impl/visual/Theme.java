package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.TickEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.ColorProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;

import java.awt.*;

@Module.Info(name = "Theme", category = Module.Category.VISUAL)
public class Theme extends Module{
    public StringProperty color = new StringProperty("Color", this, "Cosmic",
            "Cosmic", "Fiery", "Flawless", "Lightweight", "Moonada","White", "Pleasant","Pink", "Raspberry","Blue Berry", "Flow", "Aquamarine", "Amethys", "Blood", "Violet", "Bloody","Custom");
    public NumberProperty speed = new NumberProperty("Color Speed", this, 15, 2, 30, 1);
    public ColorProperty color1 = new ColorProperty("First Color",this, new Color(0xffa028d4));
    public ColorProperty color2 = new ColorProperty("Second Color",this, new Color(0xffa028d4));
    public BooleanProperty rainbow = new BooleanProperty("Rainbow",this,false);

    @SubscribeEvent
    private final EventListener<TickEvent> onTick = e -> {
        setSuffix(color.getMode());
        for (io.github.liticane.clients.feature.theme.Theme theme : io.github.liticane.clients.feature.theme.Theme.values())
            if (theme.getThemeName().equals(this.color.getMode()))
                Client.INSTANCE.getThemeManager().setTheme(theme);
    };
}
