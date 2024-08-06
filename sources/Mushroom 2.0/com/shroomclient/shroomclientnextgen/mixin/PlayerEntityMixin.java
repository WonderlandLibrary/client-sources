package com.shroomclient.shroomclientnextgen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.JumpEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.NoSlow;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Speed;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Sprint;
import com.shroomclient.shroomclientnextgen.modules.impl.render.NameTags;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerEntity.class, priority = 1)
public abstract class PlayerEntityMixin extends LivingEntityMixin {

    protected PlayerEntityMixin(DataTracker dataTracker, EntityType<?> type) {
        super(dataTracker, type);
    }

    /**
     * @author scoliosis
     * @reason nametags
     */
    @Overwrite
    public boolean shouldRenderName() {
        return !ModuleManager.isEnabled(NameTags.class);
    }

    // silly o smth
    @Override
    public void jump() {
        Bus.post(new JumpEvent(C.p().getPos()));
        if (!Speed.overrideJumping()) {
            Vec3d vec3d = C.p().getVelocity();
            C.p().setVelocity(vec3d.x, Speed.jumpVelo(), vec3d.z);
            if (
                C.p().isSprinting() &&
                MovementUtil.isUserMoving(false) &&
                (!isUsingItem() || (ModuleManager.isEnabled(NoSlow.class)))
            ) {
                float f = MovementUtil.getYaw() * 0.017453292F;
                if (!Sprint.shouldOverrideSprint()) f = C.p().getYaw() *
                0.017453292F;
                C.p()
                    .setVelocity(
                        C.p()
                            .getVelocity()
                            .add(
                                (-MathHelper.sin(f) * 0.2F),
                                0.0,
                                (MathHelper.cos(f) * 0.2F)
                            )
                    );
            }

            C.p().velocityDirty = true;

            C.p().incrementStat(Stats.JUMP);
            if (C.p().isSprinting()) {
                C.p().addExhaustion(0.2F);
            } else {
                C.p().addExhaustion(0.05F);
            }
        }
    }

    @ModifyReturnValue(method = "clipAtLedge", at = @At("RETURN"))
    private boolean clipAtLedge(boolean crouching) {
        return crouching || Scaffold.shouldSafeWalk();
    }
}
