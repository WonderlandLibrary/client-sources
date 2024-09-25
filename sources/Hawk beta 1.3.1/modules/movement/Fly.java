package eze.modules.movement;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.modules.player.*;
import net.minecraft.client.entity.*;
import eze.events.*;
import eze.events.listeners.*;

public class Fly extends Module
{
    Timer timer;
    public ModeSetting mode;
    public BooleanSetting DisableOnDeath;
    
    public Fly() {
        super("Fly", 34, Category.MOVEMENT);
        this.timer = new Timer();
        this.mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla", "Redesky", "OldVerus", "Vanilla fast", "Airwalk" });
        this.DisableOnDeath = new BooleanSetting("Disable on death", true);
        this.addSettings(this.mode);
    }
    
    @Override
    public void onEnable() {
        if (this.mode.is("Redesky")) {
            this.mc.thePlayer.jump();
        }
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel && !this.mode.is("Vanilla fast")) {
                this.mode.cycle();
            }
            if (AutoSetting.isMineplex && !this.mode.is("Vanilla fast")) {
                this.mode.cycle();
            }
            if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
                this.mode.cycle();
            }
            if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
                this.mode.cycle();
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.capabilities.setFlySpeed(0.045f);
        if (this.mode.is("OldVerus")) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            thePlayer.motionX *= 0.009999999776482582;
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            thePlayer2.motionZ *= 0.009999999776482582;
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel && !this.mode.is("Vanilla fast")) {
                this.mode.cycle();
            }
            if (AutoSetting.isMineplex && !this.mode.is("Vanilla fast")) {
                this.mode.cycle();
            }
            if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
                this.mode.cycle();
            }
            if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
                this.mode.cycle();
            }
        }
        if (e instanceof EventUpdate) {
            if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.getHealth() <= 0.0f || this.mc.thePlayer.isDead)) {
                this.toggled = false;
            }
            if (this.mode.is("Vanilla")) {
                this.mc.thePlayer.capabilities.isFlying = true;
                this.mc.timer.timerSpeed = 1.0f;
                this.mc.thePlayer.capabilities.setFlySpeed(0.07f);
            }
            if (this.mode.is("Redesky")) {
                this.mc.thePlayer.capabilities.isFlying = true;
                this.mc.timer.timerSpeed = 2.5f;
                this.mc.thePlayer.setSprinting(false);
                this.mc.thePlayer.capabilities.setFlySpeed(0.05f);
            }
            if (this.mode.is("OldVerus") && this.timer.hasTimeElapsed(545L, true)) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.onGround = true;
                this.mc.timer.timerSpeed = 1.0f;
            }
            if (this.mode.is("Vanilla fast")) {
                SpeedModifier.setSpeed(2.8f);
                this.mc.thePlayer.motionY = 0.0;
                this.mc.thePlayer.onGround = true;
            }
            if (this.mode.is("Airwalk")) {
                this.mc.thePlayer.motionY = 0.0;
                this.mc.thePlayer.onGround = true;
            }
        }
    }
}
