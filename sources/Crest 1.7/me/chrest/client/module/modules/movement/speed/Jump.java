// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.speed;

import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.block.BlockAir;
import me.chrest.utils.MathUtils;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.module.modules.movement.Speed;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.Module;
import me.chrest.utils.Timer;

public class Jump extends SpeedMode
{
    public static double yOffset;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private boolean glide;
    Timer timer;
    
    public Jump(final String name, final boolean value, final Module module) {
        super(name, value, module);
        this.timer = new Timer();
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            Vhop.stage = 0;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            if (!ClientUtils.player().onGround) {
                this.timer.reset();
            }
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.481, 3)) {
                final Block pos = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(pos instanceof BlockAir)) {
                    final EntityPlayerSP player = ClientUtils.player();
                    player.motionY -= 0.075;
                    event.setY(-0.075);
                }
            }
            else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.40599999999999997, 3)) {
                final Block pos = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(pos instanceof BlockAir)) {
                    event.setY(ClientUtils.player().motionY = -0.1);
                }
            }
            else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.306, 3) && !ClientUtils.player().isCollidedHorizontally && this.stage != 0) {
                final Block pos = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(pos instanceof BlockAir) && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                    event.setY(ClientUtils.player().motionY = -6.0E-6);
                }
            }
            else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.305, 3) || (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) || ClientUtils.player().isCollidedHorizontally) {
                this.stage = 0;
            }
            if (this.stage == 1 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                this.stage = 2;
                this.moveSpeed = (ClientUtils.player().isPotionActive(Potion.moveSpeed) ? (3.25 * Speed.getBaseMoveSpeed()) : (Speed.boost * Speed.getBaseMoveSpeed()));
            }
            else if (this.stage == 2) {
                this.stage = 3;
                ClientUtils.setMoveSpeed(event, 0.15);
                event.setY(ClientUtils.player().motionY = 0.42);
                this.moveSpeed *= 2.147;
            }
            else if (this.stage == 3) {
                this.stage = 4;
                final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                if (ClientUtils.world().getCollidingBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0)).size() > 0 || ClientUtils.player().isCollidedVertically) {
                    if (this.timer.delay(265.0f)) {
                        this.stage = 1;
                    }
                    else {
                        this.stage = 0;
                    }
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            ClientUtils.setMoveSpeed(event, (this.stage != 0) ? (this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed())) : 0.15);
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
