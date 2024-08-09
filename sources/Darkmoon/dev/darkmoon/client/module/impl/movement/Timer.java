package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

@ModuleAnnotation(name = "Timer", category = Category.MOVEMENT)
public class Timer extends Module {
    public final NumberSetting timerSpeed = new NumberSetting("Speed", 1.6F, 1.1F, 5.0F, 0.1F);
    public BooleanSetting smart = new BooleanSetting("Smart", true);
    public final NumberSetting decreaseRate = new NumberSetting("Decrease-Rate", 1.0F, 0.5F, 3.0F, 0.1F, () -> smart.get());
    public final NumberSetting addInMotion = new NumberSetting("Add-Motion", 0.0F, 0.0F, 1.0F, 0.05F, () -> smart.get());
    public final Draggable timerIndicatorDraggable = DragManager.create(this, "Timer Indicator", 10, 350);
    public static float violation = 0.0F;
    public float animWidth;
    private double prevPosX;
    private double prevPosY;
    private double prevPosZ;
    private float yaw;
    private float pitch;

    public Timer() {
        this.timerIndicatorDraggable.setWidth(60.0F);
        this.timerIndicatorDraggable.setHeight(5.0F);
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        super.onDisable();
    }

    @EventTarget
    public void onEventUpdate(EventUpdate event) {
        mc.timer.timerSpeed = this.timerSpeed.get();
        if (this.smart.get() && !(mc.timer.timerSpeed <= 1.0F)) {
            if (violation < 90.0F / this.timerSpeed.get()) {
                violation += this.decreaseRate.get();
                violation = MathHelper.clamp(violation, 0.0F, 100.0F / this.timerSpeed.get());
            } else {
                this.toggle();
            }

        }
    }

    public void updateTimer(float rotationYaw, float rotationPitch, double posX, double posY, double posZ) {
        violation = this.notMoving() ? (float)((double)violation - ((double)this.decreaseRate.get() + 0.4)) : violation - this.addInMotion.get() / 10.0F;
        violation = (float)MathHelper.clamp((double)violation, 0.0, Math.floor((double)(100.0F / mc.timer.timerSpeed)));
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        this.yaw = rotationYaw;
        this.pitch = rotationPitch;
    }

    private boolean notMoving() {
        return this.prevPosX == mc.player.posX && this.prevPosY == mc.player.posY && this.prevPosZ == mc.player.posZ && this.yaw == mc.player.rotationYaw && this.pitch == mc.player.rotationPitch;
    }
}
