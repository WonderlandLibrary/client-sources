// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.util.Timer;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Timer", renderName = "Timer", description = "Makes the game ticks run faster", aliases = {}, category = ModuleCategory.PLAYER)
public final class TimerModule extends Module
{
    private final float cachedTimerValue = 1.0f;
    public final NumberProperty<Float> timerValue;
    private final EventListener<EventUpdate> updatePlayerEventCallback;
    
    public TimerModule() {
        this.timerValue = new NumberProperty<Float>("Speed", 1.6f, 0.1f, 10.0f, 0.2f);
        this.updatePlayerEventCallback = (event -> {
            this.setSuffix(this.timerValue.getValue().toString());
            this.mc.timer.timerSpeed = this.timerValue.getValue();
        });
    }
    
    @Override
    public void onEnable() {
        this.mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }
    
    @Override
    public void init() {
        super.init();
    }
    
    @Override
    public void onDisable() {
        final Timer timer = this.mc.timer;
        this.getClass();
        timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
