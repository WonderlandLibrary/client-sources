package com.client.glowclient.sponge.mixin;

import net.minecraft.client.resources.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraftforge.client.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;

@SideOnly(Side.CLIENT)
@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer implements IResourceManagerReloadListener
{
    @Shadow
    private Minecraft field_78531_r;
    @Shadow
    private final float field_78490_B = 4.0f;
    @Shadow
    private float field_78491_C;
    @Shadow
    private boolean field_78500_U;
    @Shadow
    private float field_78530_s;
    
    public MixinEntityRenderer() {
        super();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistancePrev = 4.0f;
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void preHurtCameraEvent(final CallbackInfo callbackInfo) {
        if (HookTranslator.v10) {
            callbackInfo.cancel();
        }
    }
    
    @Overwrite
    public void orientCamera(float n) {
        final Entity renderViewEntity = HookTranslator.mc.getRenderViewEntity();
        n = 0.0f;
        final double posY = renderViewEntity.posY;
        float eyeHeight = renderViewEntity.getEyeHeight();
        final double n2 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n;
        double n3 = renderViewEntity.prevPosY + (posY - renderViewEntity.prevPosY) * n + eyeHeight;
        if (HookTranslator.v3) {
            n3 = renderViewEntity.prevPosY + (posY - renderViewEntity.prevPosY) * n + eyeHeight + 1160.0;
        }
        final double n4 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n;
        if (renderViewEntity instanceof EntityLivingBase && ((EntityLivingBase)renderViewEntity).isPlayerSleeping()) {
            ++eyeHeight;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!HookTranslator.mc.gameSettings.debugCamEnable) {
                final BlockPos blockPos = new BlockPos(renderViewEntity);
                ForgeHooksClient.orientBedCamera((IBlockAccess)HookTranslator.mc.world, blockPos, HookTranslator.mc.world.getBlockState(blockPos), renderViewEntity);
                GlStateManager.rotate(renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (HookTranslator.mc.gameSettings.thirdPersonView > 0) {
            double n5;
            if (HookTranslator.v3) {
                n5 = this.thirdPersonDistancePrev + (HookTranslator.m9() - this.thirdPersonDistancePrev);
            }
            else {
                n5 = this.thirdPersonDistancePrev + (4.0f - this.thirdPersonDistancePrev) * n;
            }
            if (HookTranslator.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
            }
            else {
                final float rotationYaw = renderViewEntity.rotationYaw;
                float rotationPitch = renderViewEntity.rotationPitch;
                if (HookTranslator.mc.gameSettings.thirdPersonView == 2) {
                    rotationPitch += 180.0f;
                }
                final double n6 = -MathHelper.sin(rotationYaw * 0.017453292f) * MathHelper.cos(rotationPitch * 0.017453292f) * n5;
                final double n7 = MathHelper.cos(rotationYaw * 0.017453292f) * MathHelper.cos(rotationPitch * 0.017453292f) * n5;
                final double n8 = -MathHelper.sin(rotationPitch * 0.017453292f) * n5;
                for (int i = 0; i < 8; ++i) {
                    final float n9 = (float)((i & 0x1) * 2 - 1);
                    final float n10 = (float)((i >> 1 & 0x1) * 2 - 1);
                    final float n11 = (float)((i >> 2 & 0x1) * 2 - 1);
                    final float n12 = n9 * 0.1f;
                    final float n13 = n10 * 0.1f;
                    final float n14 = n11 * 0.1f;
                    final RayTraceResult rayTraceBlocks = HookTranslator.mc.world.rayTraceBlocks(new Vec3d(n2 + n12, n3 + n13, n4 + n14), new Vec3d(n2 - n6 + n12 + n14, n3 - n8 + n13, n4 - n7 + n14));
                    if (rayTraceBlocks != null) {
                        final double distanceTo = rayTraceBlocks.hitVec.distanceTo(new Vec3d(n2, n3, n4));
                        if (distanceTo < n5) {
                            n5 = distanceTo;
                        }
                    }
                }
                if (HookTranslator.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(renderViewEntity.rotationPitch - rotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(renderViewEntity.rotationYaw - rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-n5));
                GlStateManager.rotate(rotationYaw - renderViewEntity.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(rotationPitch - renderViewEntity.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, 0.05f);
        }
        if (!HookTranslator.mc.gameSettings.debugCamEnable) {
            float n15 = renderViewEntity.prevRotationYaw + (renderViewEntity.rotationYaw - renderViewEntity.prevRotationYaw) * n + 180.0f;
            final float n16 = renderViewEntity.prevRotationPitch + (renderViewEntity.rotationPitch - renderViewEntity.prevRotationPitch) * n;
            final float n17 = 0.0f;
            if (renderViewEntity instanceof EntityAnimal) {
                final EntityAnimal entityAnimal = (EntityAnimal)renderViewEntity;
                n15 = entityAnimal.prevRotationYawHead + (entityAnimal.rotationYawHead - entityAnimal.prevRotationYawHead) * n + 180.0f;
            }
            final EntityViewRenderEvent.CameraSetup cameraSetup = new EntityViewRenderEvent.CameraSetup((EntityRenderer)EntityRenderer.class.cast(this), renderViewEntity, ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)HookTranslator.mc.world, renderViewEntity, n), (double)n, n15, n16, n17);
            MinecraftForge.EVENT_BUS.post((Event)cameraSetup);
            GlStateManager.rotate(cameraSetup.getRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(cameraSetup.getPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(cameraSetup.getYaw(), 0.0f, 1.0f, 0.0f);
        }
        GlStateManager.translate(0.0f, -eyeHeight, 0.0f);
        this.cloudFog = HookTranslator.mc.renderGlobal.hasCloudFog(renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * n, renderViewEntity.prevPosY + (posY - renderViewEntity.prevPosY) * n + eyeHeight, renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * n, n);
    }
}
