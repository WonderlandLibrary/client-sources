package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.LivingEntityMoveEvent;
import xyz.mathax.mathaxclient.events.entity.player.JumpVelocityMultiplierEvent;
import xyz.mathax.mathaxclient.events.entity.player.PlayerMoveEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.movement.Velocity;
import xyz.mathax.mathaxclient.systems.modules.render.ESP;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerEntity;
import xyz.mathax.mathaxclient.utils.render.postprocess.PostProcessShaders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static xyz.mathax.mathaxclient.MatHax.mc;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;

    @Shadow
    public abstract BlockPos getBlockPos();

    @Shadow
    protected abstract BlockPos getVelocityAffectingPos();

    @Redirect(method = "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;getVelocity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d updateMovementInFluidFluidStateGetVelocity(FluidState state, BlockView world, BlockPos pos) {
        Vec3d vec = state.getVelocity(world, pos);
        Velocity velocity = Modules.get().get(Velocity.class);
        if ((Object) this == mc.player && velocity.isEnabled() && velocity.liquidsSetting.get()) {
            vec = vec.multiply(velocity.getHorizontal(velocity.liquidsHorizontalSetting), velocity.getVertical(velocity.liquidsVerticalSetting), velocity.getHorizontal(velocity.liquidsHorizontalSetting));
        }

        return vec;
    }

    @ModifyArgs(method = "pushAwayFrom(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    private void onPushAwayFrom(Args args, Entity entity) {
        Velocity velocity = Modules.get().get(Velocity.class);
        if ((Object) this == mc.player && velocity.isEnabled() && velocity.entityPushSetting.get()) { // Velocity
            double multiplier = velocity.entityPushAmountSetting.get();
            args.set(0, (double) args.get(0) * multiplier);
            args.set(2, (double) args.get(2) * multiplier);
        } else if (entity instanceof FakePlayerEntity player && player.doNotPush) { // FakePlayerEntity
            args.set(0, 0.0);
            args.set(2, 0.0);
        }
    }

    @Inject(method = "getJumpVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> infoReturnable) {
        if ((Object) this == mc.player) {
            float f = world.getBlockState(getBlockPos()).getBlock().getJumpVelocityMultiplier();
            float g = world.getBlockState(getVelocityAffectingPos()).getBlock().getJumpVelocityMultiplier();
            float a = f == 1.0D ? g : f;
            JumpVelocityMultiplierEvent event = MatHax.EVENT_BUS.post(JumpVelocityMultiplierEvent.get());
            infoReturnable.setReturnValue(a * event.multiplier);
        }
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void onMove(MovementType type, Vec3d movement, CallbackInfo info) {
        if ((Object) this == mc.player) {
            MatHax.EVENT_BUS.post(PlayerMoveEvent.get(type, movement));
        } else if ((Object) this instanceof LivingEntity) {
            MatHax.EVENT_BUS.post(LivingEntityMoveEvent.get((LivingEntity) (Object) this, movement));
        }
    }

    @Inject(method = "getTeamColorValue", at = @At("HEAD"), cancellable = true)
    private void onGetTeamColorValue(CallbackInfoReturnable<Integer> infoReturnable) {
        if (PostProcessShaders.rendering) {
            infoReturnable.setReturnValue(Modules.get().get(ESP.class).getColor((Entity) (Object) this).getPacked());
        }
    }

    /*@Redirect(method = "getVelocityMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private Block getVelocityMultiplierGetBlockProxy(BlockState blockState) {
        if ((Object) this != mc.player) {
            return blockState.getBlock();
        }

        if (blockState.getBlock() == Blocks.SOUL_SAND && Modules.get().get(NoSlow.class).soulSand()) {
            return Blocks.STONE;
        }

        if (blockState.getBlock() == Blocks.HONEY_BLOCK && Modules.get().get(NoSlow.class).honeyBlock()) {
            return Blocks.STONE;
        }

        return blockState.getBlock();
    }*/

    @Inject(method = "isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void isInvisibleToCanceller(PlayerEntity player, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (!Utils.canUpdate()) {
            return;
        }

        if (Modules.get().get(NoRender.class).noInvisibility() || !Modules.get().get(ESP.class).shouldSkip((Entity) (Object) this)) {
            infoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void isGlowing(CallbackInfoReturnable<Boolean> info) {
        if (Modules.get().get(NoRender.class).noGlowing()) {
            info.setReturnValue(false);
        }
    }

    /*@Inject(method = "getTargetingMargin", at = @At("HEAD"), cancellable = true)
    private void onGetTargetingMargin(CallbackInfoReturnable<Float> infoReturnable) {
        double v = Modules.get().get(Hitboxes.class).getEntityValue((Entity) (Object) this);
        if (v != 0) {
            infoReturnable.setReturnValue((float) v);
        }
    }*/

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> infoReturnable) {
        if (player == null) {
            infoReturnable.setReturnValue(false);
        }
    }

    /*@Inject(method = "getPose", at = @At("HEAD"), cancellable = true)
    private void getPoseHook(CallbackInfoReturnable<EntityPose> infoReturnable) {
        if ((Object) this == mc.player && Modules.get().get(ElytraFly.class).canPacketEfly()) {
            infoReturnable.setReturnValue(EntityPose.FALL_FLYING);
        }
    }*/
}
