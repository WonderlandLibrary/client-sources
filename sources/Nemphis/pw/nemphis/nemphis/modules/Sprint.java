/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-10552159, name="Sprint")
public class Sprint
extends ToggleableModule {
    @EventListener
    public void on(UpdateEvent e) {
        if (Sprint.mc.thePlayer.isMoving()) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}

