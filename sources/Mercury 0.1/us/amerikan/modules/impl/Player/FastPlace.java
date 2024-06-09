/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class FastPlace
extends Module {
    public FastPlace() {
        super("FastPlace", "FastPlace", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        FastPlace.mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        FastPlace.mc.rightClickDelayTimer = 6;
        EventManager.unregister(this);
    }
}

