package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.potion.Potion;
import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.network.badlion.Events.EventMove;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Speed extends Mod
{
    private double moveSpeed;
    private double lastDist;
    private int stage;
    public static Minecraft mc;
    
    static {
        Speed.mc = Minecraft.getMinecraft();
    }
    
    public Speed() {
        super("Speed", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
        this.lastDist = 0.0;
        this.stage = 4;
        Speed.mc.thePlayer.onGround = true;
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s", this.getModName()));
    }
    
    @EventTarget
    public boolean onMove(final EventMove event) {
        if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
            switch (this.stage) {
                case 1: {
                    this.moveSpeed = 1.6;
                    break;
                }
                case 2: {
                    this.moveSpeed = 0.06;
                    break;
                }
                default: {
                    this.moveSpeed = getBaseMoveSpeed();
                    break;
                }
            }
            ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed()));
            ++this.stage;
        }
        return true;
    }
    
    @EventTarget
    public boolean onUpdate(final EventUpdate event) {
        if (event.state == EventState.PRE) {
            switch (this.stage) {
                case 1: {
                    event.setY(event.getY());
                    ++this.stage;
                    break;
                }
                case 2: {
                    event.setY(event.getY());
                    ++this.stage;
                    break;
                }
                default: {
                    this.stage = 1;
                    if (!ClientUtils.player().isSneaking() && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) && !ClientUtils.gamesettings().keyBindJump.isPressed()) {
                        this.stage = 1;
                    }
                    else {
                        this.moveSpeed = getBaseMoveSpeed();
                    }
                    if (!Badlion.getWinter().bhop.isEnabled() && Speed.mc.thePlayer.onGround && !Speed.mc.thePlayer.isCollidedHorizontally && (Speed.mc.thePlayer.moveForward != 0.0f || Speed.mc.thePlayer.moveStrafing != 0.0f)) {
                        Speed.mc.thePlayer.motionY = 0.4;
                        break;
                    }
                    break;
                }
            }
        }
        return true;
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
