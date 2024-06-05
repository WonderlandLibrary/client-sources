/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.apache.commons.lang3.RandomUtils
 *  org.lwjgl.input.Mouse
 */
package cc.diablo.module.impl.ghost;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Mouse;

public class AutoClicker
extends Module {
    public NumberSetting minCPS = new NumberSetting("Min CPS", 9.0, 1.0, 20.0, 1.0);
    public NumberSetting maxCPS = new NumberSetting("Max CPS", 14.0, 1.0, 20.0, 1.0);
    private final Stopwatch timer = new Stopwatch();

    public AutoClicker() {
        super("AutoClicker", "Automatically click for you", 0, Category.Combat);
        this.addSettings(this.minCPS, this.maxCPS);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (Minecraft.getMinecraft().currentScreen == null && Mouse.isButtonDown((int)0)) {
            if (this.timer.hasReached(1000 / RandomUtils.nextInt((int)((int)this.minCPS.getVal()), (int)((int)this.maxCPS.getVal())))) {
                KeyBinding.setKeyBindState(-100, true);
                KeyBinding.onTick(-100);
                this.timer.reset();
            } else {
                KeyBinding.setKeyBindState(-100, false);
            }
        }
    }
}

