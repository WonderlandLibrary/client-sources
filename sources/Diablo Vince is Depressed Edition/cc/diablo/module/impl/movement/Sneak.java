/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak
extends Module {
    public ModeSetting mode = new ModeSetting("Disabler Mode", "Packet", "Packet", "Legit");

    public Sneak() {
        super("Sneak", "Sneaks for you", 0, Category.Movement);
    }

    @Override
    public void onEnable() {
        switch (this.mode.getMode()) {
            case "Packet": {
                PacketHelper.sendPacketNoEvent(new C0BPacketEntityAction(Sneak.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
    }
}

