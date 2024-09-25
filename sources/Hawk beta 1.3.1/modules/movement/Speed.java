package eze.modules.movement;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.modules.player.*;
import eze.events.*;
import eze.events.listeners.*;

public class Speed extends Module
{
    Timer timer;
    public float MineplexSpeed;
    boolean isWalking;
    public ModeSetting mode;
    public NumberSetting SpeedVanilla;
    
    public Speed() {
        super("Speed", 48, Category.MOVEMENT);
        this.timer = new Timer();
        this.isWalking = false;
        this.mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla", "Mineplex", "Hypixel", "Redesky", "Legit bhop", "OldVerus" });
        this.SpeedVanilla = new NumberSetting("Speed for Vanilla", 0.5, 0.1, 5.0, 0.1);
        this.addSettings(this.mode, this.SpeedVanilla);
    }
    
    @Override
    public void onEnable() {
        this.MineplexSpeed = 0.36f;
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.mode.is("Hypixel");
            }
            if (AutoSetting.isMineplex) {
                this.mode.is("Mineplex");
            }
            if (AutoSetting.isOldVerus) {
                this.mode.is("OldVerus");
            }
            if (AutoSetting.isRedesky) {
                this.mode.is("Redesky");
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel && !this.mode.is("Hypixel")) {
                this.mode.cycle();
            }
            if (AutoSetting.isMineplex && !this.mode.is("Mineplex")) {
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
            if (AutoSetting.enabled) {
                if (AutoSetting.isHypixel && !this.mode.is("Hypixel")) {
                    this.mode.cycle();
                }
                if (AutoSetting.isMineplex && !this.mode.is("Mineplex")) {
                    this.mode.cycle();
                }
                if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
                    this.mode.cycle();
                }
                if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
                    this.mode.cycle();
                }
            }
            if (e.isPre()) {
                if (this.mc.thePlayer.motionX > 0.05 || this.mc.thePlayer.motionX < -0.05 || this.mc.thePlayer.motionZ > 0.025 || this.mc.thePlayer.motionZ < -0.05) {
                    this.isWalking = true;
                }
                else {
                    this.isWalking = false;
                }
                if (this.mode.is("Redesky")) {
                    this.mc.timer.timerSpeed = 2.2f;
                }
                if (this.mode.is("Vanilla")) {
                    this.mc.timer.timerSpeed = 1.0f;
                    SpeedModifier.setSpeed((float)this.SpeedVanilla.getValue());
                }
                if (this.mode.is("Mineplex")) {
                    if (this.mc.thePlayer.onGround) {
                        SpeedModifier.setSpeed(0.12f);
                    }
                    else {
                        SpeedModifier.setSpeed(0.34f);
                    }
                }
                if (this.mode.is("Legit bhop")) {
                    this.mc.timer.timerSpeed = 1.0f;
                    if (this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.jump();
                    }
                    if (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0f) {
                        this.toggled = false;
                        this.mc.timer.timerSpeed = 1.0f;
                    }
                }
                if (this.mode.is("Hypixel")) {
                    this.mc.timer.timerSpeed = 1.0f;
                    SpeedModifier.setSpeed(0.3f);
                    if (this.mc.thePlayer.onGround && this.isWalking && this.timer.hasTimeElapsed(100L, true)) {
                        this.mc.thePlayer.jump();
                    }
                }
                if (this.mode.is("OldVerus")) {
                    this.mc.timer.timerSpeed = 1.0f;
                    if (this.isWalking && this.mc.thePlayer.onGround && this.timer.hasTimeElapsed(100L, true)) {
                        this.mc.thePlayer.jump();
                    }
                    if (this.mc.thePlayer.isSprinting()) {
                        if (this.mc.thePlayer.onGround) {
                            if (this.mc.thePlayer.moveForward > 0.0f) {
                                SpeedModifier.setSpeed(0.19f);
                            }
                            else {
                                SpeedModifier.setSpeed(0.14f);
                            }
                        }
                        else if (this.mc.thePlayer.moveForward > 0.0f) {
                            SpeedModifier.setSpeed(0.295f);
                        }
                        else {
                            SpeedModifier.setSpeed(0.29f);
                        }
                    }
                    else if (this.mc.thePlayer.onGround) {
                        if (this.mc.thePlayer.moveForward > 0.0f) {
                            SpeedModifier.setSpeed(0.16f);
                        }
                        else {
                            SpeedModifier.setSpeed(0.14f);
                        }
                    }
                    else if (this.mc.thePlayer.moveForward > 0.0f) {
                        SpeedModifier.setSpeed(0.25f);
                    }
                    else {
                        SpeedModifier.setSpeed(0.2f);
                    }
                }
            }
        }
    }
}
