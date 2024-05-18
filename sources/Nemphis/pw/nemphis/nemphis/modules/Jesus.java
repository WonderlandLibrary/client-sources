/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.utils.BlockHelper;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-3724260, name="Jesus")
public class Jesus
extends ToggleableModule {
    double prevY;
    boolean check;

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (BlockHelper.isOnLiquid()) {
            Jesus.mc.thePlayer.motionY = 0.01;
        }
    }
}

