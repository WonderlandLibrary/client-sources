/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.nemphis.events.EventPreMotion;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.RENDER, color=-2474888, name="NoBob")
public class NoBob
extends ToggleableModule {
    @EventListener
    public void on(EventPreMotion e) {
        NoBob.mc.thePlayer.distanceWalkedModified = 0.0f;
    }
}

