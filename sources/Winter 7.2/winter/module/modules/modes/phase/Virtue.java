/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;

public class Virtue
extends Mode {
    public Phase parent;

    public Virtue(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
    }

    @Override
    public void onBB(BBEvent event) {
        if (event.getBounds() != null && event.getBounds().maxY > Virtue.mc.thePlayer.boundingBox.minY && Virtue.mc.thePlayer.isSneaking()) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && Phase.isInsideBlock() && Virtue.mc.thePlayer.isSneaking()) {
            float yaw = Virtue.mc.thePlayer.rotationYaw;
            Virtue.mc.thePlayer.boundingBox.offsetAndUpdate(0.8 * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, 0.8 * Math.sin(Math.toRadians(yaw + 90.0f)));
        }
    }
}

