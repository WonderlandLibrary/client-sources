/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Aura;
import winter.module.modules.Ragebot;
import winter.utils.value.Value;
import winter.utils.value.types.NumberValue;

public class Spin
extends Module {
    int aimPitch = 89;
    int aimYaw = 0;
    private NumberValue speed;

    public Spin() {
        super("Spinbot", Module.Category.Other, -939616);
        this.setBind(21);
        this.speed = new NumberValue("Spin Speed", 60.0, 1.0, 180.0, 1.0);
        this.addValue(this.speed);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && !this.mc.gameSettings.keyBindUseItem.pressed && Ragebot.target == null) {
            this.aimPitch = -180;
            if ((double)this.aimYaw + this.speed.getValue() > 360.0) {
                this.aimYaw = 0;
            }
            this.aimYaw = (int)((double)this.aimYaw + this.speed.getValue());
            if (Aura.target == null) {
                event.yaw = this.aimYaw;
                event.pitch = this.aimPitch;
            }
        }
    }
}

