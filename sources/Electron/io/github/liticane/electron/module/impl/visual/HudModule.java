package io.github.liticane.electron.module.impl.visual;

import io.github.liticane.electron.module.Module;
import io.github.liticane.electron.module.ModuleType;
import io.github.liticane.electron.property.impl.BooleanProperty;
import io.github.liticane.electron.property.impl.BooleanPropertyBuilder;

public class HudModule extends Module {
    private BooleanProperty blur = new BooleanPropertyBuilder().setName("Blur").setValue(true).build();
    private BooleanProperty bloom = new BooleanPropertyBuilder().setName("Bloom").setValue(true).build();

    public HudModule() {
        super("HUD", ModuleType.VISUAL);
        this.setEnabled(true);
    }

}
