// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.phase;

import net.minecraft.util.AxisAlignedBB;
import me.chrest.event.events.BoundingBoxEvent;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.client.module.modules.movement.Phase;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

public class Para extends PhaseMode
{
    public Para(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        final Phase phaseModule = (Phase)this.getModule();
        if (super.onUpdate(event) && event.getState().equals(Event.State.PRE) && phaseModule.isInsideBlock() && ClientUtils.player().isSneaking()) {
            final float yaw = ClientUtils.yaw();
            ClientUtils.player().boundingBox.offsetAndUpdate(phaseModule.distance * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, phaseModule.distance * Math.sin(Math.toRadians(yaw + 90.0f)));
        }
        return true;
    }
    
    @Override
    public boolean onSetBoundingbox(final BoundingBoxEvent event) {
        if (super.onSetBoundingbox(event) && event.getBoundingBox() != null && event.getBoundingBox().maxY > ClientUtils.player().boundingBox.minY && ClientUtils.player().isSneaking()) {
            event.setBoundingBox(null);
        }
        return true;
    }
}
