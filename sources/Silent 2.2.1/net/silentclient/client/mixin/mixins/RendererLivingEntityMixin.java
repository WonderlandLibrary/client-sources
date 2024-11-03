package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.silentclient.client.Client;
import net.silentclient.client.admin.AdminRender;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.event.impl.EventHitOverlay;
import net.silentclient.client.event.impl.RenderLivingEvent;
import net.silentclient.client.hooks.NameTagRenderingHooks;
import net.silentclient.client.mods.render.NametagsMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.RenderTransformer;
import net.silentclient.client.utils.culling.EntityCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public class RendererLivingEntityMixin<T extends EntityLivingBase> {
    private float red;
    private float green;
    private float blue;
    private float alpha;

    @Inject(method = "setBrightness", at = @At("HEAD"))
    public void hitColor(T entitylivingbaseIn, float partialTicks, boolean combineTextures, CallbackInfoReturnable<Boolean> cir) {
        EventHitOverlay event = new EventHitOverlay(1, 0, 0, 0.3F);
        event.call();

        red = event.getRed();
        green = event.getGreen();
        blue = event.getBlue();
        alpha = event.getAlpha();
    }

    @ModifyConstant(method = "setBrightness", constant = @Constant(floatValue = 1, ordinal = 0))
    public float setBrightnessRed(float original) {
        return red;
    }

    @ModifyConstant(method = "setBrightness", constant = @Constant(floatValue = 0, ordinal = 0))
    public float setBrightnessGreen(float original) {
        return green;
    }

    @ModifyConstant(method = "setBrightness", constant = @Constant(floatValue = 0, ordinal = 1))
    public float setBrightnessBlue(float original) {
        return blue;
    }

    @ModifyConstant(method = "setBrightness", constant = @Constant(floatValue = 0.3F, ordinal = 0))
    public float setBrightnessAlpha(float original) {
        return alpha;
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void renderEvent(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        RenderLivingEvent event = new RenderLivingEvent(entity, x, y, z, (RendererLivingEntity<EntityLivingBase>) (Object) this);
        event.call();
        if(event.isCancelable()) {
            ci.cancel();
            return;
        }

        final float entityDistance = entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
        if (EntityCulling.shouldPerformCulling) {
            if (entity instanceof IMob && entityDistance > Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hostile Entity Render Distance").getValInt()) {
                ci.cancel();
                return;
            }
            if ((entity instanceof EntityAnimal || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob) && entityDistance > Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Passive Entity Render Distance").getValInt()) {
                ci.cancel();
                return;
            }
            if (entity instanceof EntityPlayer && entityDistance > Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Player Render Distance").getValInt()) {
                ci.cancel();
                return;
            }
            if (entityDistance > Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Misc. Entity Render Distance").getValInt()) {
                ci.cancel();
                return;
            }
        }
        if(entity instanceof EntityArmorStand && Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Armor Stands").getValBoolean()) {
            ((RendererLivingEntity<EntityArmorStand>) (Object) this).renderName((EntityArmorStand) entity, x, y, z);
            ci.cancel();
        }
    }

    @Redirect(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int useShadowedNametagRendering(FontRenderer instance, String text, int x, int y, int color) {
        return NameTagRenderingHooks.drawNametagText(instance, text, x, y, color, Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Font Shadow").getValBoolean());
    }

    @Inject(method = "canRenderName(Lnet/minecraft/entity/EntityLivingBase;)Z", at = @At("HEAD"), cancellable = true)
    public void customCanRenderName(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (!Minecraft.isGuiEnabled() && (!Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() || !Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Show in F1").getValBoolean())) {
            cir.setReturnValue(false);
            cir.cancel();
        } else if(entity == Minecraft.getMinecraft().getRenderManager().livingPlayer && !entity.isInvisible() && (Client.getInstance().getModInstances().getModByClass(NametagsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(NametagsMod.class, "Show in F5").getValBoolean()) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainer) && !(Minecraft.getMinecraft().currentScreen instanceof CosmeticsGui) && !(Minecraft.getMinecraft().currentScreen instanceof AdminRender)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Redirect(
            method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F")
    )
    private float fixNametagPerspectiveX(RenderManager instance) {
        if(Client.getInstance().getModInstances().getSnaplookMod().isActive()) {
            return Client.getInstance().getModInstances().getSnaplookMod().getPitch();
        }
        return instance.playerViewX * RenderTransformer.checkPerspective();
    }

    @Redirect(
            method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewY:F")
    )
    private float fixNametagPerspectiveY(RenderManager instance) {
        if(Client.getInstance().getModInstances().getSnaplookMod().isActive()) {
            return Client.getInstance().getModInstances().getSnaplookMod().getYaw();
        }
        return instance.playerViewY;
    }
}
