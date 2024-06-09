/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;
import winter.utils.value.types.NumberValue;

public class Lemon
extends Mode {
    public Phase parent;

    public Lemon(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
    }

    @Override
    public void onBB(BBEvent event) {
        if ((Lemon.mc.thePlayer.isCollidedHorizontally || Phase.isInsideBlock()) && event.getBounds() != null && event.getBounds().maxY > Lemon.mc.thePlayer.boundingBox.minY) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && Lemon.mc.thePlayer.isCollidedHorizontally && !Phase.isInsideBlock()) {
            double distance = this.parent.lDist.getValue();
            double x2 = (double)MovementInput.moveForward * distance * Math.cos(Math.toRadians(Lemon.mc.thePlayer.rotationYaw + 90.0f)) + (double)MovementInput.moveStrafe * distance * Math.sin(Math.toRadians(Lemon.mc.thePlayer.rotationYaw + 90.0f));
            double z2 = (double)MovementInput.moveForward * distance * Math.sin(Math.toRadians(Lemon.mc.thePlayer.rotationYaw + 90.0f)) - (double)MovementInput.moveStrafe * distance * Math.cos(Math.toRadians(Lemon.mc.thePlayer.rotationYaw + 90.0f));
            Lemon.mc.thePlayer.setPositionAndUpdate(Lemon.mc.thePlayer.posX + x2, Lemon.mc.thePlayer.posY, Lemon.mc.thePlayer.posZ + z2);
            Lemon.mc.thePlayer.boundingBox.offsetAndUpdate(x2, 0.0, z2);
        }
    }
}

