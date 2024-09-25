package eze.modules.movement;

import eze.modules.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.client.entity.*;

public class Longjump extends Module
{
    public ModeSetting mode;
    public NumberSetting timeroflj;
    
    public Longjump() {
        super("Longjump", 46, Category.MOVEMENT);
        this.mode = new ModeSetting("Mode", "Redesky fast", new String[] { "Redesky slow", "Redesky fast", "Redesky vroom" });
        this.timeroflj = new NumberSetting("Timer", 1.0, 0.1, 4.0, 0.1);
        this.addSettings(this.mode, this.timeroflj);
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.speedInAir = 0.02f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.mode.is("Redesky vroom")) {
                this.mc.thePlayer.speedInAir = 0.12f;
                this.mc.thePlayer.motionY = 0.5;
            }
            if (this.mode.is("Redesky fast")) {
                this.mc.timer.timerSpeed = (float)this.timeroflj.getValue();
                if (this.mc.thePlayer.fallDistance != 0.0f) {
                    this.mc.thePlayer.motionY += 0.039;
                }
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                }
                if (!this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY += 0.075;
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionX *= 1.065000057220459;
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionZ *= 1.065000057220459;
                }
            }
            else {
                this.mc.timer.timerSpeed = (float)this.timeroflj.getValue();
                if (this.mc.thePlayer.fallDistance != 0.0f) {
                    this.mc.thePlayer.motionY += 0.025;
                }
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                }
                if (!this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY += 0.055;
                    final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                    thePlayer3.motionX *= 1.045;
                    final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                    thePlayer4.motionZ *= 1.045;
                }
            }
        }
    }
}
