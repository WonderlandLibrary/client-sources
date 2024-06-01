package io.github.liticane.electron.module.impl.visual;

import io.github.liticane.electron.module.Module;
import io.github.liticane.electron.module.ModuleType;
import io.github.liticane.electron.property.impl.BooleanProperty;
import io.github.liticane.electron.property.impl.BooleanPropertyBuilder;

public class CustomMinecraftModule extends Module {
    private BooleanProperty customWindowTittle = new BooleanPropertyBuilder().setName("Custom Window Tittle")
            .setValue(true).build();
    private BooleanProperty enderDragonBar = new BooleanPropertyBuilder().setName("Ender Dragon Bar").setValue(true)
            .build();

    public CustomMinecraftModule() {
        super("Custom Minecraft", ModuleType.VISUAL);
    }
}
