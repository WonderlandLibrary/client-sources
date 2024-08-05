package fr.dog.module.impl.player;


import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.NumberProperty;

public class Timer extends Module {

    private final NumberProperty timer = NumberProperty.newInstance("Timer", 0.1f, 1f, 10f, 0.01f);

    public Timer() {
        super("Timer", ModuleCategory.PLAYER);
        this.registerProperties(timer);
    }

    public void onEnable() {
        mc.timer.timerSpeed = timer.getValue();
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }
}