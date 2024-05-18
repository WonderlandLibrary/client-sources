/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;
import pw.vertexcode.nemphis.events.EventPreMotion;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-12657535, name="Speed")
public class Speed
extends ToggleableModule {
    public Value<String> mode;
    public TimeHelper timer;
    public int value;

    public Speed() {
        this.mode = new Value<String>("mode", "GACOnground", this);
        this.timer = new TimeHelper();
        this.value = 0;
        this.addValue(this.mode);
        this.setRenderMode(this.mode.getValue());
    }

    @EventListener
    public void on(EventPreMotion e) {
        this.renderMode = this.mode.getValue();
        if (this.mode.getValue().equalsIgnoreCase("SpectreTimer") && Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.isMoving()) {
            Speed.mc.timer.timerSpeed = 1.6f;
        }
        if (this.mode.getValue().equalsIgnoreCase("SpectreBhop")) {
            if (!Speed.mc.thePlayer.isMoving()) {
                return;
            }
            ++this.value;
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.jump();
            }
            if (this.value >= 9) {
                this.value = 0;
            }
            if (this.value >= 3) {
                this.setSpeed(0.35f);
            } else {
                this.setSpeed(0.5f);
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("GACJump") && Speed.mc.thePlayer.isMoving()) {
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.motionY = 0.42;
                this.setSpeed(0.5f);
            } else {
                Speed.mc.thePlayer.posY = (int)Speed.mc.thePlayer.posY;
                Speed.mc.thePlayer.motionY = -0.5;
                this.setSpeed(0.3f);
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("New") && Speed.mc.thePlayer.isMoving()) {
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.jump();
                this.setSpeed(0.5f);
            } else {
                Speed.mc.thePlayer.motionY = -1.0;
                Speed.mc.thePlayer.posY = (int)Speed.mc.thePlayer.posY;
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("Old") && Speed.mc.thePlayer.isMoving()) {
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.jump();
            } else {
                this.setSpeed(0.5f);
                Speed.mc.thePlayer.motionY = -1.0;
                Speed.mc.thePlayer.posY = (int)Speed.mc.thePlayer.posY;
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("AACJump") && Speed.mc.thePlayer.isMoving()) {
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.motionY = 0.2;
                this.setSpeed(0.5f);
            } else {
                this.setSpeed(0.5f);
                Speed.mc.thePlayer.motionY = -0.5;
                Speed.mc.thePlayer.posY = (int)Speed.mc.thePlayer.posY;
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("AACBhop") && Speed.mc.thePlayer.isMoving()) {
            if (Speed.mc.thePlayer.onGround) {
                Speed.mc.thePlayer.motionY = 0.42;
                this.setSpeed(0.6f);
            } else {
                this.setSpeed(0.4f);
            }
        }
    }
}

