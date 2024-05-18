// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.speed;

import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import me.chrest.utils.MathUtils;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.module.modules.movement.Speed;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.Module;

public class Bhop extends SpeedMode
{
    private double moveSpeed;
    private double lastDist;
    public static double stage;
    
    public Bhop(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            Bhop.stage = 4.0;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.138, 3)) {
                final EntityPlayerSP player3;
                final EntityPlayerSP player = player3 = ClientUtils.player();
                player3.motionY -= 0.08;
                event.setY(event.getY() - 0.0931);
                final EntityPlayerSP player4;
                final EntityPlayerSP player2 = player4 = ClientUtils.player();
                player4.posY -= 0.0931;
            }
            if (Bhop.stage == 2.0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                event.setY(ClientUtils.player().motionY = 0.4);
                this.moveSpeed *= 2.149;
            }
            else if (Bhop.stage == 3.0) {
                final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                final List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if (collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) {
                    Bhop.stage = 1.0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed()));
            ++Bhop.stage;
        }
        return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
            final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return true;
    }
}
