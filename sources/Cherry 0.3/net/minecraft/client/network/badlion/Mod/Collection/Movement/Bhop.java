// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.potion.Potion;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.client.network.badlion.Events.EventMove;
import net.minecraft.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Bhop extends Mod
{
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    private boolean oldVal;
    private int airTicks;
    private int groundTicks;
    private int headStart;
    private int lastHDistance;
    
    public Bhop() {
        super("Bhop", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
        if (ClientUtils.player() != null) {
            this.moveSpeed = getBaseMoveSpeed();
        }
        this.oldVal = Minecraft.gameSettings.viewBobbing;
        Minecraft.gameSettings.viewBobbing = false;
        this.lastDist = 0.0;
        Bhop.stage = 1;
    }
    
    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        EventManager.unregister(this);
        Minecraft.gameSettings.viewBobbing = this.oldVal;
    }
    
    @EventTarget
    public void onMove(final EventMove event) {
        Timer.timerSpeed = 1.0f;
        if (Bhop.stage == 1 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
            this.moveSpeed = 1.28 * Speed.getBaseMoveSpeed() - 0.01;
        }
        else if (Bhop.stage == 2 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
            event.setY(ClientUtils.player().motionY = 0.4);
            this.moveSpeed *= 2.249;
        }
        else if (Bhop.stage == 3) {
            final double difference = 0.8 * (this.lastDist - Speed.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        }
        else {
            final List collidingList = ClientUtils.world().getCollidingBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
            if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && Bhop.stage > 0) {
                Bhop.stage = ((ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) ? 1 : 0);
            }
            this.moveSpeed = this.lastDist - this.lastDist / 166.0;
        }
        ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed()));
        if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
            ++Bhop.stage;
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (event.state == EventState.PRE) {
            final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
            final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionX *= 1.8085;
        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
        thePlayer2.motionZ *= 1.8085;
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
