package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.EnumChatFormatting;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.AbstractShieldRenderer;
import net.silentclient.client.cosmetics.BandanaRenderer;
import net.silentclient.client.cosmetics.CapeRenderer;
import net.silentclient.client.cosmetics.HatRenderer;
import net.silentclient.client.cosmetics.wings.WingsModel;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.render.NametagsMod;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.utils.HypixelUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class RenderPlayerMixin extends RendererLivingEntity<AbstractClientPlayer> {
    public RenderPlayerMixin(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V", at = @At("RETURN"))
    public void initComsetics(RenderManager renderManager, boolean useSmallArms, CallbackInfo ci) {
        addLayer(new WingsModel(((RenderPlayer) (Object) this)));
        addLayer(new CapeRenderer(((RenderPlayer) (Object) this)));
        addLayer(new BandanaRenderer(((RenderPlayer) (Object) this)));
        addLayer(new HatRenderer(((RenderPlayer) (Object) this), "hat"));
        addLayer(new HatRenderer(((RenderPlayer) (Object) this), "neck"));
        addLayer(new HatRenderer(((RenderPlayer) (Object) this), "mask"));
        addLayer(new AbstractShieldRenderer(((RenderPlayer) (Object) this)));
    }

    @Inject(method = "renderOffsetLivingLabel(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;FD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;renderOffsetLivingLabel(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;FD)V", shift = At.Shift.BEFORE))
    public void nametags(AbstractClientPlayer entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_, CallbackInfo ci) {
        boolean toggle = Client.getInstance().getModInstances().getLevelHeadMod().isEnabled();
        boolean nametagMessages = Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Show Nametag Messages").getValBoolean() && ((AbstractClientPlayerExt) entityIn).silent$getAccount() != null && ((AbstractClientPlayerExt) entityIn).silent$getAccount().showNametagMessage() && !((AbstractClientPlayerExt) entityIn).silent$getAccount().getNametagMessage().isEmpty();

        if(toggle && Server.isHypixel()) {
            String levelhead = HypixelUtils.getHypixelLevel(entityIn == Minecraft.getMinecraft().thePlayer, entityIn.getDisplayName().getFormattedText(), entityIn.getUniqueID());

            if(levelhead != null && levelhead != "null") {
                renderLivingLabel(entityIn, "§r" + EnumChatFormatting.AQUA + "Level: " + EnumChatFormatting.YELLOW + levelhead + "§r", x, y + ((double) ((float) getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * p_177069_9_) + (nametagMessages ? ((double) ((float) getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * p_177069_9_)) : 0)), z, 64);
            }
        }

        if(nametagMessages) {
            renderLivingLabel(entityIn, "§r" + ((AbstractClientPlayerExt) entityIn).silent$getAccount().getNametagMessage().replaceAll("&", "§") + "§r", x, y + ((double) ((float) getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * p_177069_9_)), z, 64);
        }
    }

    @Redirect(method = "renderRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;isSneak:Z", ordinal = 0))
    private void silent$resetArmState(ModelPlayer modelPlayer, boolean value) {
        modelPlayer.isRiding = modelPlayer.isSneak = false;
    }
}
