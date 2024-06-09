/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;

public class Lithe
extends Mode {
    private int resetNext;
    public Phase parent;

    public Lithe(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
    }

    @Override
    public void onBB(BBEvent event) {
        if (Phase.isInsideBlock() && Lithe.mc.gameSettings.keyBindJump.pressed || !Phase.isInsideBlock() && event.getBounds() != null && event.getBounds().maxY > Lithe.mc.thePlayer.boundingBox.minY) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        --this.resetNext;
        double xOff = 0.0;
        double zOff = 0.0;
        double multiplier = 1.2;
        double mx2 = Math.cos(Math.toRadians(Lithe.mc.thePlayer.rotationYaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(Lithe.mc.thePlayer.rotationYaw + 90.0f));
        xOff = (double)MovementInput.moveForward * 1.2 * mx2 + (double)MovementInput.moveStrafe * 1.2 * mz2;
        zOff = (double)MovementInput.moveForward * 1.2 * mz2 - (double)MovementInput.moveStrafe * 1.2 * mx2;
        if (Phase.isInsideBlock()) {
            this.resetNext = 1;
        }
        if (this.resetNext > 0) {
            Lithe.mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0, zOff);
        }
    }
}

