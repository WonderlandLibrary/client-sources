/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.player.Scaffold;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;

public class AntiAim
extends Module {
    public ModeSetting watermarkMode = new ModeSetting("Mode", "Simple", "Simple", "Spin");
    private NumberSetting pitch = new NumberSetting("Pitch", 90.0, -90.0, 90.0, 1.0);
    private NumberSetting jitter = new NumberSetting("Jitter", 90.0, -90.0, 90.0, 1.0);

    public AntiAim() {
        super("AntiAim", "autism", 0, Category.Player);
        this.addSettings(this.watermarkMode, this.pitch, this.jitter);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (KillAura.target == null && !ModuleManager.getModule(Scaffold.class).isToggled() && !AntiAim.mc.gameSettings.keyBindUseItem.pressed) {
            switch (this.watermarkMode.getMode()) {
                case "Simple": {
                    KillAuraHelper.setRotations(e, AntiAim.mc.thePlayer.rotationYaw - 180.0f + (float)MathHelper.getRandInt((int)(-this.jitter.getVal()), (int)this.jitter.getVal()), (float)this.pitch.getVal());
                    break;
                }
                case "Spin": {
                    KillAuraHelper.setRotations(e, AntiAim.mc.thePlayer.ticksExisted * 80, (float)this.pitch.getVal());
                }
            }
        }
    }
}

