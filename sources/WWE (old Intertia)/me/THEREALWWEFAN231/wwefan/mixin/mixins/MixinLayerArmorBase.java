package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import wwe.modules.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { LayerArmorBase.class }, priority = 9999)
public class MixinLayerArmorBase
{
    public MixinLayerArmorBase() {
        super();
    }
    
    @Redirect(method = { "renderEnchantedGlint" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V", ordinal = 1))
    private static void renderEnchantedGlint(final float n, final float n2, final float n3, final float n4) {
        GlStateManager.color(vlwv.hNxW.qHPF((Class<RainbowEnchant>)rPyL.class).IuiN() ? ((float)JIfG.JzFV(1L, 1.0f).getRed()) : n, vlwv.hNxW.qHPF((Class<RainbowEnchant>)rPyL.class).IuiN() ? ((float)JIfG.JzFV(1L, 1.0f).getGreen()) : n2, vlwv.hNxW.qHPF((Class<RainbowEnchant>)rPyL.class).IuiN() ? ((float)JIfG.JzFV(1L, 1.0f).getBlue()) : n3, vlwv.hNxW.qHPF((Class<RainbowEnchant>)rPyL.class).IuiN() ? ((float)JIfG.JzFV(1L, 1.0f).getAlpha()) : n4);
    }
    
    @Inject(method = { "renderEnchantedGlint" }, at = { @At("HEAD") }, cancellable = true)
    private static void renderEnchantedGlint(final RenderLivingBase<?> renderLivingBase, final EntityLivingBase entityLivingBase, final ModelBase modelBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final CallbackInfo callbackInfo) {
        final hnaB hnaB = new hnaB();
        hSNE.Seqo(hnaB);
        if (hnaB.CEMd()) {
            callbackInfo.cancel();
        }
    }
}
