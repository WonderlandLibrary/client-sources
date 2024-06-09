/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.nemphis.events.MoveEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-1422200, name="NoClip")
public class NoClip
extends ToggleableModule {
    @Override
    public void onEnabled() {
        EventManager.register(this);
        super.onEnabled();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @EventTarget
    public void on(MoveEvent e) {
        e.y = 0.0;
        NoClip.mc.thePlayer.noClip = true;
    }
}

