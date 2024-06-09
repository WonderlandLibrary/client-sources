// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement.speed;

import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.MathUtils;

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
            this.stage = 4.0;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.138, 3)) {
                final EntityPlayerSP player = ClientUtils.player();
                player.motionY -= 0.08;
                event.setY(event.getY() - 0.0931);
                final EntityPlayerSP player2 = ClientUtils.player();
                player2.posY -= 0.0931;
            }
            if (this.stage == 2.0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                event.setY(ClientUtils.player().motionY = 0.4);
                this.moveSpeed *= 2.149;
            }
            else if (this.stage == 3.0) {
                final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                final List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if (collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) {
                    this.stage = 1.0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed()));
            ++this.stage;
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
