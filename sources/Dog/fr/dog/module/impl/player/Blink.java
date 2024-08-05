package fr.dog.module.impl.player;

import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;

public class Blink extends Module {
    public Blink() {
        super("Blink", ModuleCategory.PLAYER);
    }

    public void onEnable() {
        BlinkComponent.onEnable();
    }

    public void onDisable() {
        BlinkComponent.onDisable();
    }
}

