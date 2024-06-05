/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.player.Scaffold;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;

public class Sprint
extends Module {
    public ModeSetting sprintMode = new ModeSetting("Sprint Mode", "Omni", "Omni", "Legit");

    public Sprint() {
        super("Sprint", "Automatic Sprinting!", 0, Category.Movement);
        this.addSettings(this.sprintMode);
    }

    @Override
    public void onDisable() {
        Sprint.mc.gameSettings.keyBindSprint.pressed = false;
        Sprint.mc.thePlayer.setSprinting(false);
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (!(Sprint.mc.thePlayer.isOnLadder() && Sprint.mc.thePlayer.isSneaking() && Sprint.mc.thePlayer.isSprinting() && Sprint.mc.thePlayer.isPlayerSleeping() && Sprint.mc.thePlayer == null && KillAura.stopSprint.isChecked())) {
            switch (this.sprintMode.getMode()) {
                case "Omni": {
                    if (!Sprint.mc.thePlayer.isMoving()) break;
                    Sprint.mc.thePlayer.setSprinting(true);
                    break;
                }
                case "Legit": {
                    if (!Sprint.mc.thePlayer.isMoving() || !(Sprint.mc.thePlayer.moveForward > 0.0f) || ModuleManager.getModule(Scaffold.class).isToggled()) break;
                    Sprint.mc.thePlayer.setSprinting(true);
                }
            }
        }
    }
}

