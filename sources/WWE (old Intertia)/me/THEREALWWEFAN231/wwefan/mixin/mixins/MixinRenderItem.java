package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.block.model.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import javax.annotation.*;
import wwe.modules.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import wwe.*;
import net.minecraft.client.entity.*;

@Mixin(value = { RenderItem.class }, priority = 9999)
public abstract class MixinRenderItem
{
    public MixinRenderItem() {
        super();
    }
    
    @Shadow
    public abstract void func_181565_a(final BufferBuilder p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    @ModifyArg(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index = 1)
    private int renderEffect(final int n) {
        return vlwv.hNxW.qHPF((Class<RainbowEnchant>)rPyL.class).IuiN() ? JIfG.JzFV(1L, 1.0f).getRGB() : n;
    }
    
    @Inject(method = { "renderEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void renderEffect(final IBakedModel bakedModel, final CallbackInfo callbackInfo) {
        final hnaB hnaB = new hnaB();
        hSNE.Seqo(hnaB);
        if (hnaB.CEMd()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderItemOverlayIntoGUI" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemOverlayIntoGUI(final FontRenderer fontRenderer, final ItemStack itemStack, final int n, final int n2, @Nullable final String s, final CallbackInfo callbackInfo) {
        if (!itemStack.isEmpty()) {
            if (itemStack.getCount() != 1 || s != null) {
                final String s2 = (s == null) ? String.valueOf(itemStack.getCount()) : s;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fontRenderer.drawStringWithShadow(s2, (float)(n + 19 - 2 - fontRenderer.getStringWidth(s2)), (float)(n2 + 6 + 3), 16777215);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableBlend();
            }
            if (itemStack.getItem().showDurabilityBar(itemStack) && (!vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() || !vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).hudDurability.OnGi())) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
                final double durabilityForDisplay = itemStack.getItem().getDurabilityForDisplay(itemStack);
                final int rgbDurabilityForDisplay = itemStack.getItem().getRGBDurabilityForDisplay(itemStack);
                final int round = Math.round(13.0f - (float)durabilityForDisplay * 13.0f);
                final int n3 = rgbDurabilityForDisplay;
                this.draw(buffer, n + 2, n2 + 13, 13, 2, 0, 0, 0, 255);
                this.draw(buffer, n + 2, n2 + 13, round, 1, n3 >> 16 & 0xFF, n3 >> 8 & 0xFF, n3 & 0xFF, 255);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            final EntityPlayerSP player = Minecraft.getMinecraft().player;
            final float n4 = (player == null) ? 0.0f : player.getCooldownTracker().getCooldown(itemStack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());
            if (n4 > 0.0f) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                this.draw(Tessellator.getInstance().getBuffer(), n, n2 + MathHelper.floor(16.0f * (1.0f - n4)), 16, MathHelper.ceil(16.0f * n4), 255, 255, 255, 127);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            if (itemStack.getItem().showDurabilityBar(itemStack) && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).hudDurability.OnGi()) {
                GL11.glPushMatrix();
                GlStateManager.disableDepth();
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                final String string = itemStack.getMaxDamage() - itemStack.getItemDamage() + "";
                XybC.eTim(string, (n + 8 - XybC.wKEb().getStringWidth(string) * 0.5f / 2.0f) / 0.5f, (n2 + 12) / 0.5f, -1);
                GlStateManager.enableDepth();
                GL11.glPopMatrix();
            }
        }
        callbackInfo.cancel();
    }
}
