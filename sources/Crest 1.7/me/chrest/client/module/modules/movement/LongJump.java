// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import me.chrest.event.Event;
import net.minecraft.potion.Potion;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import me.chrest.utils.MathUtils;
import me.chrest.event.events.MoveEvent;
import me.chrest.event.EventTarget;
import me.chrest.event.events.TickEvent;
import me.chrest.utils.ClientUtils;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Long Jump")
public class LongJump extends Module
{
    @Option.Op(min = 3.5, max = 20.0, increment = 0.05, name = "Boost")
    private double boost;
    public static double yOffset;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public LongJump() {
        this.moveSpeed = 0.2873;
        this.boost = 4.5;
    }
    
    public void onEnable() {
        if (ClientUtils.player() != null) {
            this.moveSpeed = this.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        this.stage = 1;
        super.enable();
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        final String boost1 = new Double(this.boost).toString();
        this.setSuffix(boost1);
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (ClientUtils.player().moveStrafing <= 0.0f && ClientUtils.player().moveForward <= 0.0f) {
            this.stage = 1;
        }
        if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.943, 3)) {
            final EntityPlayerSP player2;
            final EntityPlayerSP player = player2 = ClientUtils.player();
            player2.motionY -= 0.03;
            event.setY(-0.03);
        }
        if (this.stage == 1 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
            this.stage = 2;
            this.moveSpeed = (ClientUtils.player().isPotionActive(1) ? (3.5 * this.getBaseMoveSpeed() - 0.1) : (this.boost * this.getBaseMoveSpeed() - 0.01));
        }
        else if (this.stage == 2) {
            this.stage = 3;
            ClientUtils.setMoveSpeed(event, 0.1);
            event.setY(ClientUtils.player().motionY = 0.424);
            this.moveSpeed *= 1.987;
        }
        else if (this.stage == 3) {
            this.stage = 4;
            final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        }
        else {
            if (ClientUtils.world().getCollidingBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0)).size() > 0 || ClientUtils.player().isCollidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        switch (event.getState()) {
            case POST: {
                final double xDist = ClientUtils.player().posX - ClientUtils.player().prevPosX;
                final double zDist = ClientUtils.player().posZ - ClientUtils.player().prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                break;
            }
        }
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public void onDisable() {
        this.moveSpeed = this.getBaseMoveSpeed();
        this.stage = 0;
        LongJump.yOffset = 0.0;
        super.disable();
    }
}
