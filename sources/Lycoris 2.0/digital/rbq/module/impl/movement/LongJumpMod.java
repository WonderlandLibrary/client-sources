/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.MovementUtils;

@Label(value="Long Jump")
@Bind(value="Z")
@Category(value=ModuleCategory.MOVEMENT)
@Aliases(value={"longjump", "lj"})
public final class LongJumpMod
extends Module {
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.NCP);
    public final BoolOption hypixel = new BoolOption("Hypixel", true, () -> this.mode.getValue() == Mode.NCP);
    private double lastDif;
    private double moveSpeed;
    private int stage;
    private int groundTicks;

    public LongJumpMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode, this.hypixel);
    }

    @Override
    public void onEnabled() {
        this.lastDif = 0.0;
        this.moveSpeed = 0.0;
        this.stage = 0;
        this.groundTicks = 1;
    }

    @Override
    public void onDisabled() {
    }

    @Listener(value=MoveEvent.class)
    public final void onMove(MoveEvent event) {
        if (this.isEnabled()) {
            EntityPlayerSP player = LongJumpMod.mc.thePlayer;
            boolean watchdog = this.hypixel.getValue();
            if (player.isMoving()) {
                switch (this.stage) {
                    case 0: 
                    case 1: {
                        this.moveSpeed = 0.0;
                        break;
                    }
                    case 2: {
                        if (!player.onGround || !player.isCollidedVertically) break;
                        event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.3999999463558197);
                        this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.0;
                        break;
                    }
                    case 3: {
                        this.moveSpeed = MovementUtils.getBaseMoveSpeed() * (double)2.149f;
                        break;
                    }
                    case 4: {
                        if (!watchdog) break;
                        this.moveSpeed *= (double)1.6f;
                        break;
                    }
                    default: {
                        if (player.motionY < 0.0) {
                            player.motionY *= 0.5;
                        }
                        this.moveSpeed = this.lastDif - this.lastDif / 159.0;
                    }
                }
                this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                ++this.stage;
            }
            MovementUtils.setSpeed(event, this.moveSpeed);
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityPlayerSP player = LongJumpMod.mc.thePlayer;
        if (event.isPre()) {
            if (player.onGround && player.isCollidedVertically) {
                event.setPosY(event.getPosY() + 7.435E-4);
            }
            double xDif = player.posX - player.prevPosX;
            double zDif = player.posZ - player.prevPosZ;
            this.lastDif = Math.sqrt(xDif * xDif + zDif * zDif);
            if (player.isMoving() && player.onGround && player.isCollidedVertically && this.stage > 2) {
                ++this.groundTicks;
            }
            if (this.groundTicks > 1) {
                this.toggle();
            }
        }
    }

    private static enum Mode {
        NCP;

    }
}

