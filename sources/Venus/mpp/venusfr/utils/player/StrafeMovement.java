/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

public class StrafeMovement
implements IMinecraft {
    private double oldSpeed;
    private double contextFriction;
    private boolean needSwap;
    private boolean needSprintState;
    private int counter;
    private int noSlowTicks;

    public double calculateSpeed(MovingEvent movingEvent, boolean bl, boolean bl2, boolean bl3, float f) {
        float f2;
        float f3;
        boolean bl4 = StrafeMovement.mc.player.isOnGround();
        boolean bl5 = movingEvent.isToGround();
        boolean bl6 = movingEvent.getMotion().y > 0.0;
        float f4 = this.getAIMoveSpeed(StrafeMovement.mc.player);
        float f5 = this.getFrictionFactor(StrafeMovement.mc.player, movingEvent);
        float f6 = f3 = StrafeMovement.mc.player.isPotionActive(Effects.JUMP_BOOST) && StrafeMovement.mc.player.isHandActive() ? 0.88f : 0.91f;
        if (bl4) {
            f3 = f5;
        }
        float f7 = 0.16277136f / (f3 * f3 * f3);
        if (bl4) {
            f2 = f4 * f7;
            if (bl6) {
                f2 += 0.2f;
            }
        } else {
            f2 = bl && bl2 && (bl3 || StrafeMovement.mc.gameSettings.keyBindJump.isKeyDown()) ? f : 0.0255f;
        }
        boolean bl7 = false;
        double d = this.oldSpeed + (double)f2;
        double d2 = 0.0;
        if (StrafeMovement.mc.player.isHandActive() && !bl6) {
            double d3;
            double d4 = this.oldSpeed + (double)f2 * 0.25;
            double d5 = movingEvent.getMotion().y;
            if (d5 != 0.0 && Math.abs(d5) < 0.08) {
                d4 += 0.055;
            }
            d2 = Math.max(0.043, d4);
            if (d > d3) {
                bl7 = true;
                ++this.noSlowTicks;
            } else {
                this.noSlowTicks = Math.max(this.noSlowTicks - 1, 0);
            }
        } else {
            this.noSlowTicks = 0;
        }
        d = this.noSlowTicks > 3 ? d2 - (StrafeMovement.mc.player.isPotionActive(Effects.JUMP_BOOST) && StrafeMovement.mc.player.isHandActive() ? 0.3 : 0.019) : Math.max(bl7 ? 0.0 : 0.25, d) - (this.counter++ % 2 == 0 ? 0.001 : 0.002);
        this.contextFriction = f3;
        if (!bl5 && !bl4) {
            this.needSwap = true;
        }
        if (!bl4 && !bl5) {
            boolean bl8 = this.needSprintState = !StrafeMovement.mc.player.serverSprintState;
        }
        if (bl5 && bl4) {
            this.needSprintState = false;
        }
        return d;
    }

    public void postMove(double d) {
        this.oldSpeed = d * this.contextFriction;
    }

    private float getAIMoveSpeed(ClientPlayerEntity clientPlayerEntity) {
        boolean bl = clientPlayerEntity.isSprinting();
        clientPlayerEntity.setSprinting(true);
        float f = clientPlayerEntity.getAIMoveSpeed() * 1.3f;
        clientPlayerEntity.setSprinting(bl);
        return f;
    }

    private float getFrictionFactor(ClientPlayerEntity clientPlayerEntity, MovingEvent movingEvent) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        mutable.setPos(movingEvent.getFrom().x, movingEvent.getAabbFrom().minY - 1.0, movingEvent.getFrom().z);
        return clientPlayerEntity.world.getBlockState((BlockPos)mutable).getBlock().slipperiness * 0.91f;
    }

    public double getOldSpeed() {
        return this.oldSpeed;
    }

    public double getContextFriction() {
        return this.contextFriction;
    }

    public boolean isNeedSwap() {
        return this.needSwap;
    }

    public boolean isNeedSprintState() {
        return this.needSprintState;
    }

    public int getCounter() {
        return this.counter;
    }

    public int getNoSlowTicks() {
        return this.noSlowTicks;
    }

    public void setOldSpeed(double d) {
        this.oldSpeed = d;
    }

    public void setContextFriction(double d) {
        this.contextFriction = d;
    }

    public void setNeedSwap(boolean bl) {
        this.needSwap = bl;
    }

    public void setNeedSprintState(boolean bl) {
        this.needSprintState = bl;
    }

    public void setCounter(int n) {
        this.counter = n;
    }

    public void setNoSlowTicks(int n) {
        this.noSlowTicks = n;
    }
}

