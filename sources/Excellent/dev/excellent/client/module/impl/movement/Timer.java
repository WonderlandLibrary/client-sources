package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.DragValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2d;

@ModuleInfo(name = "Timer", description = "Ускоряет внутриигровое время.", category = Category.MOVEMENT)
public class Timer extends Module {
    public static Singleton<Timer> singleton = Singleton.create(() -> Module.link(Timer.class));
    public NumberValue timerAmount = new NumberValue("Скорость", this, 2F, 1F, 10F, 0.1F);

    public BooleanValue smart = new BooleanValue("Смарт", this, false);
    public ModeValue mode = new ModeValue("Режим", this)
            .add(SubMode.of("Кружок", "Обычный"));
    public BooleanValue movingUp = new BooleanValue("Прибавлять в движ-ии", this, false);
    public NumberValue upValue = new NumberValue("Значение", this, 0.02F, 0.01F, 0.5F, 0.010F, () -> !movingUp.getValue());

    public NumberValue ticks = new NumberValue("Скорость убывания", this, 1.0F, 0.15F, 10.0F, 0.1F);
    public final DragValue drag = new DragValue("Position", this, new Vector2d(5, 150));
    public float maxViolation = 100.0F;
    private float violation = 0.0F;
    private double prevPosX, prevPosY, prevPosZ;
    private float yaw;
    private float pitch;
    private final Listener<UpdateEvent> onUpdate = event -> handleEventUpdate();

    private void handleEventUpdate() {
        if (!mc.player.isOnGround()) {
            this.violation += 0.1f;
            this.violation = MathHelper.clamp(this.violation, 0.0F, this.maxViolation / this.timerAmount.getValue().floatValue());
        }
        mc.timer.setSpeed(this.timerAmount.getValue().floatValue());

        if (!this.smart.getValue() || mc.timer.getSpeed() <= 1.0F) {
            return;
        }
        if (this.violation < (this.maxViolation) / (this.timerAmount.getValue().floatValue())) {
            this.violation += this.ticks.getValue().floatValue();
            this.violation = MathHelper.clamp(this.violation, 0.0F, this.maxViolation / this.timerAmount.getValue().floatValue());
        } else {
            this.resetTimer();
            this.toggle();
        }
    }

    public void updateTimer(float yaw, float pitch, double posX, double posY, double posZ) {
        if (this.notMoving()) {
            this.violation = (float) ((double) this.violation - ((double) this.ticks.getValue().floatValue() + 0.4));
        } else if (this.movingUp.getValue()) {
            this.violation -= this.upValue.getValue().floatValue();
        }

        this.violation = (float) MathHelper.clamp(this.violation, 0.0, Math.floor(this.maxViolation));

        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private boolean notMoving() {
        return this.prevPosX == mc.player.getPosX()
                && this.prevPosY == mc.player.getPosY()
                && this.prevPosZ == mc.player.getPosZ()
                && this.yaw == mc.player.rotationYaw
                && this.pitch == mc.player.rotationPitch;
    }

    public float getViolation() {
        return this.violation;
    }

    @Override
    public void onDisable() {
        resetTimer();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        resetTimer();
        super.onEnable();
    }

    private void resetTimer() {
        mc.timer.resetSpeed();
    }
}

