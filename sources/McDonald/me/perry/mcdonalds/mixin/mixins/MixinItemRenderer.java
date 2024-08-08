// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin.mixins;

import me.perry.mcdonalds.features.modules.render.NoRender;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.perry.mcdonalds.features.Feature;
import me.perry.mcdonalds.features.modules.render.ViewModel;
import me.perry.mcdonalds.util.ColorUtil;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import me.perry.mcdonalds.features.modules.render.HandChams;
import me.perry.mcdonalds.features.modules.render.SmallShield;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.EnumHandSide;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemRenderer.class })
public abstract class MixinItemRenderer
{
    @Shadow
    @Final
    public Minecraft mc;
    private boolean injection;
    
    public MixinItemRenderer() {
        this.injection = true;
    }
    
    @Shadow
    public abstract void renderItemInFirstPerson(final AbstractClientPlayer p0, final float p1, final float p2, final EnumHand p3, final float p4, final ItemStack p5, final float p6);
    
    @Shadow
    protected abstract void renderArmFirstPerson(final float p0, final float p1, final EnumHandSide p2);
    
    @Inject(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemInFirstPersonHook(final AbstractClientPlayer player, final float p_187457_2_, final float p_187457_3_, final EnumHand hand, final float p_187457_5_, final ItemStack stack, final float p_187457_7_, final CallbackInfo info) {
        if (this.injection) {
            info.cancel();
            final SmallShield offset = SmallShield.getINSTANCE();
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            this.injection = false;
            if (hand == EnumHand.MAIN_HAND) {
                if (offset.isOn()) {
                    xOffset = offset.mainX.getValue();
                    yOffset = offset.mainY.getValue();
                }
            }
            else if (offset.isOn()) {
                xOffset = offset.offX.getValue();
                yOffset = offset.offY.getValue();
            }
            if (HandChams.getINSTANCE().isOn() && hand == EnumHand.MAIN_HAND && stack.isEmpty()) {
                if (HandChams.getINSTANCE().mode.getValue().equals(HandChams.RenderMode.WIREFRAME)) {
                    this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
                }
                GlStateManager.pushMatrix();
                if (HandChams.getINSTANCE().mode.getValue().equals(HandChams.RenderMode.WIREFRAME)) {
                    GL11.glPushAttrib(1048575);
                }
                else {
                    GlStateManager.pushAttrib();
                }
                if (HandChams.getINSTANCE().mode.getValue().equals(HandChams.RenderMode.WIREFRAME)) {
                    GL11.glPolygonMode(1032, 6913);
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                if (HandChams.getINSTANCE().mode.getValue().equals(HandChams.RenderMode.WIREFRAME)) {
                    GL11.glEnable(2848);
                    GL11.glEnable(3042);
                }
                GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : (HandChams.getINSTANCE().red.getValue() / 255.0f), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : (HandChams.getINSTANCE().green.getValue() / 255.0f), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : (HandChams.getINSTANCE().blue.getValue() / 255.0f), HandChams.getINSTANCE().alpha.getValue() / 255.0f);
                this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
            if (SmallShield.getINSTANCE().isOn() && (!stack.isEmpty || HandChams.getINSTANCE().isOff())) {
                this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
            }
            else if (!stack.isEmpty || HandChams.getINSTANCE().isOff()) {
                this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_, stack, p_187457_7_);
            }
            this.injection = true;
        }
        if (ViewModel.getINSTANCE().enabled.getValue() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !Feature.fullNullCheck()) {
            GlStateManager.scale((float)ViewModel.getINSTANCE().sizeX.getValue(), (float)ViewModel.getINSTANCE().sizeY.getValue(), (float)ViewModel.getINSTANCE().sizeZ.getValue());
            GlStateManager.rotate(ViewModel.getINSTANCE().rotationX.getValue() * 360.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(ViewModel.getINSTANCE().rotationY.getValue() * 360.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(ViewModel.getINSTANCE().rotationZ.getValue() * 360.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate((float)ViewModel.getINSTANCE().positionX.getValue(), (float)ViewModel.getINSTANCE().positionY.getValue(), (float)ViewModel.getINSTANCE().positionZ.getValue());
        }
    }
    
    @Redirect(method = { "renderArmFirstPerson" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0))
    public void translateHook(final float x, final float y, final float z) {
        final SmallShield offset = SmallShield.getINSTANCE();
        final boolean shiftPos = Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getHeldItemMainhand() != ItemStack.EMPTY && offset.isOn();
        GlStateManager.translate(x + (shiftPos ? offset.mainX.getValue() : 0.0f), y + (shiftPos ? offset.mainY.getValue() : 0.0f), z);
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFireInFirstPersonHook(final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fire.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderSuffocationOverlay" }, at = { @At("HEAD") }, cancellable = true)
    public void renderSuffocationOverlay(final CallbackInfo ci) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().blocks.getValue()) {
            ci.cancel();
        }
    }
}
