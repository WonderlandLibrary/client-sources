/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.TimerUtil;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name="Timer", type=Category.Movement)
public class Timer
extends Function {
    public ModeSetting mode = new ModeSetting("\u0420\u0435\u0436\u0438\u043c", "Matrix", "Matrix", "Grim");
    public BindSetting grimBind = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0431\u0443\u0441\u0442\u0430", 0).setVisible(this::lambda$new$0);
    public SliderSetting timerAmount = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 2.0f, 1.0f, 10.0f, 0.01f);
    public BooleanSetting smart = new BooleanSetting("\u0423\u043c\u043d\u044b\u0439", true);
    public BooleanSetting movingUp = new BooleanSetting("\u0414\u043e\u0431\u0430\u0432\u043b\u044f\u0442\u044c \u0432 \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u0438", false).setVisible(this::lambda$new$1);
    public SliderSetting upValue = new SliderSetting("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435", 0.02f, 0.01f, 0.5f, 0.01f).setVisible(this::lambda$new$2);
    public SliderSetting ticks = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0443\u0431\u044b\u0432\u0430\u043d\u0438\u044f", 1.0f, 0.15f, 3.0f, 0.1f).setVisible(this::lambda$new$3);
    public float maxViolation = 100.0f;
    private float violation = 0.0f;
    private double prevPosX;
    private double prevPosY;
    private double prevPosZ;
    private float yaw;
    private float pitch;
    private boolean isBoost;
    private TimerUtil timerUtil = new TimerUtil();

    public Timer() {
        this.addSettings(this.mode, this.timerAmount, this.grimBind, this.smart, this.movingUp, this.upValue, this.ticks);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        this.handleEventUpdate();
    }

    @Subscribe
    public void onUpdate(EventMotion eventMotion) {
        if (this.mode.is("Grim")) {
            EventMotion eventMotion2 = eventMotion;
            this.updateTimer(eventMotion2.getYaw(), eventMotion2.getPitch(), eventMotion2.getX(), eventMotion2.getY(), eventMotion2.getZ());
        }
    }

    @Subscribe
    public void onUpdate(EventKey eventKey) {
        EventKey eventKey2 = eventKey;
        if (eventKey2.getKey() == ((Integer)this.grimBind.get()).intValue()) {
            this.isBoost = true;
        }
    }

    @Subscribe
    public void onUpdate(EventPacket eventPacket) {
        EventPacket eventPacket2 = eventPacket;
        this.handlePacketEvent(eventPacket2);
    }

    private void handlePacketEvent(EventPacket eventPacket) {
        if (!this.mode.is("Grim")) {
            return;
        }
        if (eventPacket.isReceive()) {
            this.isFlagged(eventPacket);
            this.isDamaged(eventPacket);
        }
        if (eventPacket.isSend()) {
            this.cancelTransaction(eventPacket);
        }
    }

    private void cancelTransaction(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof CConfirmTransactionPacket) {
            eventPacket.cancel();
        }
    }

    private void isDamaged(EventPacket eventPacket) {
        SEntityVelocityPacket sEntityVelocityPacket;
        if (eventPacket.getPacket() instanceof SEntityVelocityPacket && (sEntityVelocityPacket = (SEntityVelocityPacket)eventPacket.getPacket()).getEntityID() == Timer.mc.player.getEntityId()) {
            this.reset();
            this.resetSpeed();
        }
    }

    private void isFlagged(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof SPlayerPositionLookPacket && this.isBoost) {
            this.resetSpeed();
            this.reset();
        }
    }

    private void handleEventUpdate() {
        if (this.timerUtil.hasTimeElapsed(25000L)) {
            this.reset();
            this.timerUtil.reset();
        }
        if (!Timer.mc.player.isOnGround() && !this.isBoost) {
            this.violation += 0.1f;
            this.violation = MathHelper.clamp(this.violation, 0.0f, this.maxViolation / (this.mode.is("Grim") ? 1.0f : ((Float)this.timerAmount.get()).floatValue()));
        }
        if (this.mode.is("Grim") && !this.isBoost) {
            return;
        }
        Timer.mc.timer.timerSpeed = ((Float)this.timerAmount.get()).floatValue();
        if (!((Boolean)this.smart.get()).booleanValue() || Timer.mc.timer.timerSpeed <= 1.0f) {
            return;
        }
        if (this.violation < this.maxViolation / ((Float)this.timerAmount.get()).floatValue()) {
            this.violation += this.mode.is("Grim") ? 0.05f : ((Float)this.ticks.get()).floatValue();
            this.violation = MathHelper.clamp(this.violation, 0.0f, this.maxViolation / (this.mode.is("Grim") ? 1.0f : ((Float)this.timerAmount.get()).floatValue()));
        } else {
            this.resetSpeed();
        }
    }

    public void updateTimer(float f, float f2, double d, double d2, double d3) {
        if (this.notMoving()) {
            this.violation = this.mode.is("Grim") ? (float)((double)this.violation - (double)0.05f) : (float)((double)this.violation - ((double)((Float)this.ticks.get()).floatValue() + 0.4));
        } else if (((Boolean)this.movingUp.get()).booleanValue() && !this.mode.is("Grim")) {
            this.violation -= ((Float)this.upValue.get()).floatValue();
        }
        this.violation = (float)MathHelper.clamp((double)this.violation, 0.0, Math.floor(this.maxViolation));
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
        this.yaw = f;
        this.pitch = f2;
    }

    private boolean notMoving() {
        return this.prevPosX == Timer.mc.player.getPosX() && this.prevPosY == Timer.mc.player.getPosY() && this.prevPosZ == Timer.mc.player.getPosZ() && this.yaw == Timer.mc.player.rotationYaw && this.pitch == Timer.mc.player.rotationPitch;
    }

    public float getViolation() {
        return this.violation;
    }

    public void resetSpeed() {
        this.setState(false, true);
        Timer.mc.timer.timerSpeed = 1.0f;
    }

    public void reset() {
        if (this.mode.is("Grim")) {
            this.violation = this.maxViolation / ((Float)this.timerAmount.get()).floatValue();
            this.isBoost = false;
        }
    }

    @Override
    public void onDisable() {
        this.reset();
        Timer.mc.timer.timerSpeed = 1.0f;
        this.timerUtil.reset();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.reset();
        Timer.mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    private Boolean lambda$new$3() {
        return !this.mode.is("Grim");
    }

    private Boolean lambda$new$2() {
        return (Boolean)this.movingUp.get();
    }

    private Boolean lambda$new$1() {
        return !this.mode.is("Grim");
    }

    private Boolean lambda$new$0() {
        return this.mode.is("Grim");
    }
}

