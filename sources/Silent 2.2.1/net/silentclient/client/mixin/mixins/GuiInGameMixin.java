package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.EventRenderDamageTint;
import net.silentclient.client.event.impl.RenderEvent;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.mods.hud.BossBarMod;
import net.silentclient.client.mods.hud.ScoreboardMod;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import net.silentclient.client.mods.render.PackTweaksMod;
import net.silentclient.client.mods.render.TitlesMod;
import net.silentclient.client.mods.settings.RenderMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.HUDCaching;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class GuiInGameMixin extends Gui {
    @Shadow @Final private Minecraft mc;

    @Redirect(method = "renderPlayerStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 4))
    public void oldHealth(GuiIngame instance, int i1, int i2, int i3, int i4, int i5, int i6) {
        if(!Client.getInstance().getModInstances().getOldAnimationsMod().isEnabled() || !AnimationsMod.getSettingBoolean("Remove Health Bar Flashing")) {
            instance.drawTexturedModalRect(i1, i2, i3, i4, i5, i6);
        }
    }


    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void renderEvent(float partialTicks, CallbackInfo ci) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        new RenderEvent().call();
        GL11.glColor4f(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Inject(method = "renderTooltip", at = @At("HEAD"))
    public void tintRenderEvent(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        new EventRenderDamageTint().call();
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderBossHealth()V"))
    public void customBossBar(GuiIngame instance) {
        BossBarMod.renderBossHealth(instance);
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;showCrosshair()Z"))
    public boolean renderCrosshair(GuiIngame instance) {
        if(!(Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Crosshair in F5").getValBoolean() || this.mc.gameSettings.thirdPersonView < 1)) {
            return false;
        }
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int x = scaledresolution.getScaledWidth();
        int y = scaledresolution.getScaledHeight();
        if(!Client.getInstance().getModInstances().getModByClass(CrosshairMod.class).isEnabled()) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(x / 2 - 7, y / 2 - 7, 0, 0, 16, 16);
        } else {
            if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
                if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Vanilla Blendering").getValBoolean()) {
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
                    GlStateManager.enableAlpha();
                    GlStateManager.enableDepth();
                } else {
                    ColorUtils.setColor(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Color").getValColor().getRGB());
                }

                mc.getTextureManager().bindTexture(new ResourceLocation("silentclient/mods/crosshair/crosshair" + selected + ".png"));
                GlUtils.startScale(x / 2 - 7, y / 2 - 7, 16, 16, (float) Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Scale").getValDouble());
                Gui.drawModalRectWithCustomSizedTexture(x / 2 - 7, y / 2 - 7, 0, 0, 16, 16, 16, 16);
                GlUtils.stopScale();
                this.mc.getTextureManager().bindTexture(icons);
            }
        }

        ColorUtils.setColor(-1);

        return false;
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"))
    public void renderScoreboard(GuiIngame instance, ScoreObjective s, ScaledResolution score) {
        ScoreboardMod.drawScoreboard(s, score);
    }

    @Inject(method = "renderVignette", at = @At("HEAD"), cancellable = true)
    private void silent$cancelVignette(CallbackInfo ci) {
        if (HUDCaching.renderingCacheOverride) {
            ci.cancel();
        }
    }

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    public void cancelPumpkin(ScaledResolution scaledRes, CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getModByClass(PackTweaksMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(PackTweaksMod.class, "Hide Pumpkin Overlay").getValBoolean()) {
            ci.cancel();
        }
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V", ordinal = 0))
    public void changeScaleOfTitle(float x, float y, float z) {
        if(Client.getInstance().getModInstances().getModByClass(TitlesMod.class).isEnabled()) {
            float scale = (float) Client.getInstance().getSettingsManager().getSettingByClass(TitlesMod.class, "Scale").getValDouble();
            GlStateManager.scale(x - 1 + scale, y - 1 + scale, z - 1 + scale);
        }
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V", ordinal = 1))
    public void changeScaleOfDescription(float x, float y, float z) {
        if(Client.getInstance().getModInstances().getModByClass(TitlesMod.class).isEnabled()) {
            float scale = (float) Client.getInstance().getSettingsManager().getSettingByClass(TitlesMod.class, "Scale").getValDouble();
            GlStateManager.scale(x - 1 + scale, y - 1 + scale, z - 1 + scale);
        }
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I", ordinal = 0))
    private int cancelTitle(FontRenderer instance, String text, float x, float y, int color, boolean dropShadow) {
        if(Client.getInstance().getModInstances().getModByClass(TitlesMod.class).isEnabled()) {
            return instance.drawString(text, x, y, color, dropShadow);
        } else {
            return 0;
        }
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I", ordinal = 1))
    private int cancelDescription(FontRenderer instance, String text, float x, float y, int color, boolean dropShadow) {
        if(Client.getInstance().getModInstances().getModByClass(TitlesMod.class).isEnabled()) {
            return instance.drawString(text, x, y, color, dropShadow);
        } else {
            return 0;
        }
    }
}
