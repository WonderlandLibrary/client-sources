package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.layers.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderLivingBase.class }, priority = 9999)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends MixinRender<T>
{
    @Shadow
    protected ModelBase field_77045_g;
    
    public MixinRenderLivingBase() {
        super();
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    public void doRender(final T t, final double n, final double n2, final double n3, final float n4, final float n5, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<Chams>)FHxf.class).IuiN() && t instanceof EntityLivingBase) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    public void doRenderlast(final T t, final double n, final double n2, final double n3, final float n4, final float n5, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<Chams>)FHxf.class).IuiN() && t instanceof EntityLivingBase) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }
    
    @Redirect(method = { "renderLayers" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/entity/layers/LayerRenderer.doRenderLayer(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V"))
    public void renderLayers(final LayerRenderer layerRenderer, final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final dfEB dfEB = new dfEB(entityLivingBase, layerRenderer);
        hSNE.Seqo(dfEB);
        if (!dfEB.CEMd()) {
            layerRenderer.doRenderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7);
        }
    }
}
