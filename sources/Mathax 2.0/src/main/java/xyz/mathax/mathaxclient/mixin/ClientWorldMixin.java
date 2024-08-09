package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.EntityAddedEvent;
import xyz.mathax.mathaxclient.events.entity.EntityRemovedEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Ambience;
import xyz.mathax.mathaxclient.systems.modules.render.NoRender;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Unique
    private final DimensionEffects endSky = new DimensionEffects.End();

    @Unique
    private final DimensionEffects customSky = new Ambience.Custom();

    @Shadow
    @Nullable
    public abstract Entity getEntityById(int id);

    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
        if (entity != null) {
            MatHax.EVENT_BUS.post(EntityAddedEvent.get(entity));
        }
    }

    @Inject(method = "removeEntity", at = @At("HEAD"))
    private void onRemoveEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo info) {
        if (getEntityById(entityId) != null) {
            MatHax.EVENT_BUS.post(EntityRemovedEvent.get(getEntityById(entityId)));
        }
    }

    @Inject(method = "getDimensionEffects", at = @At("HEAD"), cancellable = true)
    private void onGetSkyProperties(CallbackInfoReturnable<DimensionEffects> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.endSkySetting.get()) {
            infoReturnable.setReturnValue(ambience.customSkyColorSetting.get() ? customSky : endSky);
        }
    }

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void onGetSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customSkyColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.skyColor().getVec3d());
        }
    }

    @Inject(method = "getCloudsColor", at = @At("HEAD"), cancellable = true)
    private void onGetCloudsColor(float tickDelta, CallbackInfoReturnable<Vec3d> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customCloudColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.cloudColorSetting.get().getVec3d());
        }
    }

    @ModifyArgs(method = "doRandomBlockDisplayTicks", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;randomBlockDisplayTick(IIIILnet/minecraft/util/math/random/Random;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos$Mutable;)V"))
    private void doRandomBlockDisplayTicks(Args args) {
        if (Modules.get().get(NoRender.class).noBarrierInvisibility()) {
            args.set(5, Blocks.BARRIER);
        }
    }
}