// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoClicker", renderName = "Auto Clicker", category = ModuleCategory.COMBAT)
public final class AutoClickerModule extends Module
{
    public final NumberProperty<Long> minCPSIntProperty;
    public final NumberProperty<Long> maxCPSIntProperty;
    private final TimerUtil timerUtil;
    private long delay;
    private final EventListener<EventMove> daEvent;
    
    public AutoClickerModule() {
        this.minCPSIntProperty = new NumberProperty<Long>("MinCPS", 10L, 1L, 20L, 1L);
        this.maxCPSIntProperty = new NumberProperty<Long>("MaxCPS", 14L, 1L, 20L, 1L);
        this.timerUtil = new TimerUtil();
        this.delay = 0L;
        this.daEvent = (updatePlayerEvent -> {
            final long minCPS = this.minCPSIntProperty.getValue();
            final long maxCPS = this.maxCPSIntProperty.getValue();
            String suffix;
            if (minCPS > maxCPS) {
                suffix = maxCPS + "-" + minCPS;
            }
            else {
                suffix = minCPS + "-" + maxCPS;
            }
            this.setSuffix(suffix);
            if (maxCPS < minCPS) {
                this.minCPSIntProperty.setValue(maxCPS);
            }
            if (Mouse.isButtonDown(0) && this.timerUtil.hasElapsed((double)this.delay)) {
                this.delay = 1000L / (minCPS + (long)(Math.random() * (maxCPS - minCPS)));
                final int key = this.mc.gameSettings.keyBindAttack.getKeyCode();
                this.mc.leftClickCounter = 0;
                KeyBinding.setKeyBindState(key, false);
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                this.timerUtil.reset();
            }
        });
    }
}
