package me.mees.remix.modules.combat;

import java.util.*;
import me.satisfactory.base.utils.timer.*;
import java.lang.reflect.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.events.*;
import org.lwjgl.input.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.entity.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AutoClicker extends Module
{
    private Random random;
    private long nextLeftUp;
    private long nextLeftDown;
    private long nextDrop;
    private long nextExhaust;
    private double dropRate;
    private boolean dropping;
    private TimerUtil timer;
    private Method guiScreenMethod;
    
    public AutoClicker() {
        super("AutoClicker", 0, Category.COMBAT);
        this.timer = new TimerUtil();
        this.random = new Random();
        this.addSetting(new Setting("Min CPS", this, 8.0, 1.0, 20.0, true, 1.0));
        this.addSetting(new Setting("Max CPS", this, 12.0, 1.0, 20.0, true, 1.0));
        this.addSetting(new Setting("Jitter Strength", this, 0.3, 0.1, 1.0, false, 0.1));
        this.addSetting(new Setting("Jitter", this, true));
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        Mouse.poll();
        if (Mouse.isButtonDown(0)) {
            if (this.getSettingByModule(this, "Jitter").booleanValue() && this.random.nextDouble() > 0.65) {
                final double jitterStrength = this.getSettingByModule(this, "Jitter Strength").doubleValue() * 0.5;
                if (this.random.nextBoolean()) {
                    final EntityPlayerSP thePlayer = AutoClicker.mc.thePlayer;
                    thePlayer.rotationYaw += (float)(this.random.nextFloat() * jitterStrength);
                }
                else {
                    final EntityPlayerSP thePlayer2 = AutoClicker.mc.thePlayer;
                    thePlayer2.rotationYaw -= (float)(this.random.nextFloat() * jitterStrength);
                }
                if (this.random.nextBoolean()) {
                    final EntityPlayerSP thePlayer3 = AutoClicker.mc.thePlayer;
                    thePlayer3.rotationPitch += (float)(this.random.nextFloat() * (jitterStrength * 0.75));
                }
                else {
                    final EntityPlayerSP thePlayer4 = AutoClicker.mc.thePlayer;
                    thePlayer4.rotationPitch -= (float)(this.random.nextFloat() * (jitterStrength * 0.75));
                }
            }
            if (this.nextLeftDown > 0L && this.nextLeftUp > 0L) {
                if (this.timer.hasTimeElapsed((double)this.nextLeftDown, true)) {
                    final int attackKeyBind = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                    KeyBinding.setKeyBindState(attackKeyBind, true);
                    KeyBinding.onTick(attackKeyBind);
                    this.sendClick();
                    this.generateLeftDelay();
                }
            }
            else {
                this.generateLeftDelay();
            }
        }
        else {
            final long n2 = 0L;
            this.nextLeftUp = n2;
            this.nextLeftDown = n2;
        }
    }
    
    private void sendClick() {
        if (AutoClicker.mc.currentScreen == null && !AutoClicker.mc.thePlayer.isUsingItem()) {
            AutoClicker.mc.thePlayer.swingItem();
            if (AutoClicker.mc.objectMouseOver.entityHit == null) {
                return;
            }
            if (AutoClicker.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                AutoClicker.mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(AutoClicker.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }
    
    private void generateLeftDelay() {
        final double minCPS = this.getSettingByModule(this, "Min CPS").doubleValue();
        final double maxCPS = this.getSettingByModule(this, "Max CPS").doubleValue();
        if (minCPS > maxCPS) {
            return;
        }
        final double CPS = minCPS + this.random.nextDouble() * (maxCPS - minCPS);
        long delay = (int)Math.round(1000.0 / CPS);
        if (System.currentTimeMillis() > this.nextDrop) {
            if (!this.dropping && this.random.nextInt(100) >= 85) {
                this.dropping = true;
                this.dropRate = 1.1 + this.random.nextDouble() * 0.15;
            }
            else {
                this.dropping = false;
            }
            this.nextDrop = System.currentTimeMillis() + 500L + this.random.nextInt(1500);
        }
        if (this.dropping) {
            delay *= (long)this.dropRate;
        }
        if (System.currentTimeMillis() > this.nextExhaust) {
            if (this.random.nextInt(100) >= 80) {
                delay += 50L + this.random.nextInt(150);
            }
            this.nextExhaust = System.currentTimeMillis() + 500L + this.random.nextInt(1500);
        }
        this.nextLeftDown = delay;
        this.nextLeftUp = delay / 2L - this.random.nextInt(10);
    }
}
