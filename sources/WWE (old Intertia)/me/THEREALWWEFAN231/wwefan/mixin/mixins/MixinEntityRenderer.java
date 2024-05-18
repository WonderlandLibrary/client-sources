package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import wwe.modules.player.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityRenderer.class }, priority = 9999)
public class MixinEntityRenderer
{
    public MixinEntityRenderer() {
        super();
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z") })
    private void renderWorldPassPre(final int n, final float n2, final long n3, final CallbackInfo callbackInfo) {
        vlwv.XHpH();
        hSNE.Seqo(new RTIo(n2));
    }
    
    @ModifyVariable(method = { "orientCamera" }, ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double changeCameraDistance(final double n) {
        return (vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).IuiN() && vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).moreDistance.OnGi()) ? vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).cameraDistance.OnGi() : n;
    }
    
    @ModifyVariable(method = { "orientCamera" }, ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double cameraClip(final double n) {
        return (vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).IuiN() && vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).moreDistance.OnGi()) ? vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).cameraDistance.OnGi() : ((vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).IuiN() && !vlwv.hNxW.qHPF((Class<CameraClip>)ohJc.class).moreDistance.OnGi()) ? 4.0 : n);
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float nausa(final EntityPlayerSP entityPlayerSP) {
        return (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).nausea.OnGi()) ? -3.4028235E38f : entityPlayerSP.prevTimeInPortal;
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void realhurtCameraEffect(final float n, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).IuiN() && vlwv.hNxW.qHPF((Class<NoRender>)myLi.class).hurtCam.OnGi()) {
            callbackInfo.cancel();
        }
    }
    
    @ModifyArg(method = { "renderWorldPass(IFJ)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;setupTerrain(Lnet/minecraft/entity/Entity;DLnet/minecraft/client/renderer/culling/ICamera;IZ)V"))
    public boolean isPlayerSpectator(final boolean b) {
        return (vlwv.hNxW.qHPF((Class<Freecam>)ZTVf.class).IuiN() && vlwv.hNxW.qHPF((Class<Freecam>)ZTVf.class).caveRender.OnGi()) || vlwv.hNxW.qHPF((Class<NoCaveCulling>)bUTj.class).IuiN();
    }
}
