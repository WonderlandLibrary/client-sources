// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.phase;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.util.AxisAlignedBB;
import me.chrest.event.events.BoundingBoxEvent;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.modules.movement.Phase;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.module.Module;

public class OLDNCP extends PhaseMode
{
    public OLDNCP(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        return super.enable();
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        final Phase phaseModule = (Phase)this.getModule();
        if (phaseModule.isInsideBlock()) {
            event.setY(ClientUtils.player().motionY = 0.0);
            ClientUtils.setMoveSpeed(event, 0.2);
        }
        return true;
    }
    
    @Override
    public boolean onSetBoundingbox(final BoundingBoxEvent event) {
        final Phase phaseModule = (Phase)this.getModule();
        if (phaseModule.isInsideBlock()) {
            event.setBoundingBox(null);
        }
        return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState().equals(Event.State.POST)) {
            final double multiplier = 0.3;
            final double mx = Math.cos(Math.toRadians(ClientUtils.player().rotationYaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(ClientUtils.player().rotationYaw + 90.0f));
            final double x = ClientUtils.player().movementInput.moveForward * multiplier * mx + ClientUtils.player().movementInput.moveStrafe * multiplier * mz;
            final double z = ClientUtils.player().movementInput.moveForward * multiplier * mz - ClientUtils.player().movementInput.moveStrafe * multiplier * mx;
            final Phase phaseModule = (Phase)this.getModule();
            if (ClientUtils.player().isCollidedHorizontally && !ClientUtils.player().isOnLadder() && !phaseModule.isInsideBlock()) {
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX + x, ClientUtils.player().posY, ClientUtils.player().posZ + z, false));
                for (int i = 1; i < 10; ++i) {
                    ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, 8.988465674311579E307, ClientUtils.player().posZ, false));
                }
                ClientUtils.player().setPosition(ClientUtils.player().posX + x, ClientUtils.player().posY, ClientUtils.player().posZ + z);
            }
        }
        return true;
    }
}
