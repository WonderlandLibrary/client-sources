package me.xatzdevelopments.modules.combat;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.RandomUtils;
import me.xatzdevelopments.util.Timer;
import me.xatzdevelopments.util.Timer2;
import net.minecraft.client.settings.KeyBinding;

public class Autoclicker extends Module
{
    public Timer timer;
    float severYaw;
    float severPitch;
    float nextRotationYaw;
    float nextRotationPitch;
    int bugFixerThisIsSuchBadCode;
    float randomness;
    public NumberSetting MinCps;
    public NumberSetting MaxCps;
    
    public Autoclicker() {
		super("Autoclicker", Keyboard.KEY_NONE, Category.COMBAT, "Clicks for you.");
        this.timer = new Timer();
        this.severYaw = 0.0f;
        this.severPitch = 0.0f;
        this.MinCps = new NumberSetting("MinAps", 200.0, 0.0, 1000.0, 200.0);
        this.MaxCps = new NumberSetting("MaxAps", 800.0, 0.0, 1000.0, 200.0);
        this.addSettings(this.MinCps, this.MaxCps);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.MinCps.getValue() > this.MaxCps.getValue()) {
                this.MinCps.setValue(this.MinCps.getValue() - 2.0);
            }
            if (e.isPre() && Timer2.hasTimeElapsed((long)RandomUtils.RandomBetween(this.MinCps.getValue(), this.MaxCps.getValue()), true)) {
                final KeyBinding keyBindAttack = this.mc.gameSettings.keyBindAttack;
                KeyBinding.onTick(this.mc.gameSettings.keyBindAttack.getKeyCode());
                Timer2.Reset();
            }
        }
    }
}
