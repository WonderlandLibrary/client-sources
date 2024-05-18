/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Module.Mod(displayName="JanitorBhop")
public class JanitorBhop
extends Module {
    private double moveSpeed;
    private double lastDist;
    private int stage;
    public static Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        this.lastDist = 0.0;
        this.stage = 4;
        Minecraft.thePlayer.onGround = true;
    }

    @EventTarget
    public boolean onMove(MoveEvent event) {
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
                    this.moveSpeed = JanitorBhop.getBaseMoveSpeed();
                }
            }
            this.moveSpeed = Math.max(this.moveSpeed, JanitorBhop.getBaseMoveSpeed());
            ClientUtils.setMoveSpeed(event, this.moveSpeed);
            ++this.stage;
        }
        return true;
    }

    @EventTarget
    public boolean onUpdate(UpdateEvent event) {
        event.getState();
        if (event.getState() == Event.State.PRE) {
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
                    if (!(ClientUtils.player().isSneaking() || ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f || ClientUtils.gamesettings().keyBindJump.isPressed())) {
                        this.stage = 1;
                    } else {
                        this.moveSpeed = JanitorBhop.getBaseMoveSpeed();
                    }
                    if (!Minecraft.thePlayer.onGround || Minecraft.thePlayer.isCollidedHorizontally || Minecraft.thePlayer.moveForward == 0.0f && Minecraft.thePlayer.moveStrafing == 0.0f) break;
                    Minecraft.thePlayer.motionY = 0.4;
                }
            }
        }
        return true;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }
}

