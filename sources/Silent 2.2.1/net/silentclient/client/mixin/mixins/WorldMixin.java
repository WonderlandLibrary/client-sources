package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.EntityJoinLevelEvent;
import net.silentclient.client.utils.WorldListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(World.class)
public class WorldMixin {
    @Shadow @Final public boolean isRemote;

    @Inject(method = "checkLightFor", at = @At("HEAD"), cancellable = true)
    private void checkLightFor(CallbackInfoReturnable<Boolean> cir) {
        if (this.canFullbright()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = {"getLightFromNeighborsFor", "getLightFromNeighbors", "getRawLight", "getLight(Lnet/minecraft/util/BlockPos;)I", "getLight(Lnet/minecraft/util/BlockPos;Z)I" }, at = @At("HEAD"), cancellable = true)
    private void getLightFromNeighborsFor(CallbackInfoReturnable<Integer> cir) {
        if (this.canFullbright()) {
            cir.setReturnValue(15);
        }
    }

    @Unique
    private boolean canFullbright() {
        return Minecraft.getMinecraft().isCallingFromMinecraftThread() && Client.getInstance().getModInstances().getFullBrightMod().isEnabled();
    }

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"), cancellable = true)
    public void callEntityJoinLevelEvent1(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        EntityJoinLevelEvent event = new EntityJoinLevelEvent(entityIn);
        event.call();
        if(event.isCancelable()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = {"removeEntity", "removePlayerEntityDangerously"}, at = @At("HEAD"), cancellable = true)
    public void removeEntityFromWorldListener(Entity entityIn, CallbackInfo ci) {
        if(entityIn instanceof EntityPlayer) {
            WorldListener.onPlayerLeave((EntityPlayer) entityIn);
        }
    }

    @Inject(method = "joinEntityInSurroundings", at = @At("HEAD"), cancellable = true)
    public void callEntityJoinLevelEvent2(Entity entityIn, CallbackInfo ci) {
        EntityJoinLevelEvent event = new EntityJoinLevelEvent(entityIn);
        event.call();
        if(event.isCancelable()) {
            ci.cancel();
        }
    }

    @Inject(method = "getCollidingBoundingBoxes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesWithinAABBExcludingEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void silent$filterEntities(Entity entityIn, AxisAlignedBB bb, CallbackInfoReturnable<List<AxisAlignedBB>> cir, List<AxisAlignedBB> list) {
        if (entityIn instanceof EntityTNTPrimed || entityIn instanceof EntityFallingBlock || entityIn instanceof EntityItem
                // particles aren't entities after 1.9
                //#if MC==10809
                || entityIn instanceof EntityFX
            //#endif
        ) {
            cir.setReturnValue(list);
        }
    }
}
