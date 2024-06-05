/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.combat;

import me.zane.basicbus.api.annotations.Listener;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.EnumOption;

@Label(value="Anti Aim")
@Category(value=ModuleCategory.COMBAT)
@Aliases(value={"antiaim", "aa"})
public final class AntiAimMod
extends Module {
    public final EnumOption<YawMode> yawMode = new EnumOption<YawMode>("Yaw mode", YawMode.JITTER);
    public final EnumOption<PitchMode> pitchMode = new EnumOption<PitchMode>("Pitch mode", PitchMode.DOWN);
    private float yaw;
    private float pitch;

    public AntiAimMod() {
        this.addOptions(this.pitchMode, this.yawMode);
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        if (!AntiAimMod.mc.thePlayer.isSwingInProgress) {
            switch ((YawMode)((Object)this.yawMode.getValue())) {
                case SPIN: {
                    this.yaw += 20.0f;
                    if (this.yaw > 180.0f) {
                        this.yaw = -180.0f;
                        break;
                    }
                    if (!(this.yaw < -180.0f)) break;
                    this.yaw = 180.0f;
                    break;
                }
                case JITTER: {
                    this.yaw = AntiAimMod.mc.thePlayer.ticksExisted % 2 == 0 ? 90 : -90;
                }
            }
            event.setYaw(this.yaw);
            switch ((PitchMode)((Object)this.pitchMode.getValue())) {
                case UP: {
                    this.pitch = -90.0f;
                    break;
                }
                case DOWN: {
                    this.pitch = 90.0f;
                    break;
                }
                case JITTER: {
                    this.pitch += 30.0f;
                    if (this.pitch > 90.0f) {
                        this.pitch = -90.0f;
                        break;
                    }
                    if (!(this.pitch < -90.0f)) break;
                    this.pitch = 90.0f;
                }
            }
            event.setPitch(this.pitch);
        }
    }

    private static enum PitchMode {
        DOWN,
        UP,
        JITTER;

    }

    private static enum YawMode {
        JITTER,
        SPIN;

    }
}

