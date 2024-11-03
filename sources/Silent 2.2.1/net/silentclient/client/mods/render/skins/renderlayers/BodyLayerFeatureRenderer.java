package net.silentclient.client.mods.render.skins.renderlayers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.accessors.skins.PlayerEntityModelAccessor;
import net.silentclient.client.mixin.accessors.skins.PlayerSettings;
import net.silentclient.client.mods.render.skins.SkinUtil;
import net.silentclient.client.mods.render.skins.SkinsMod;
import net.silentclient.client.mods.render.skins.render.CustomizableModelPart;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BodyLayerFeatureRenderer
        implements LayerRenderer<AbstractClientPlayer> {

    private final boolean thinArms;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public BodyLayerFeatureRenderer(
            RenderPlayer playerRenderer) {
        thinArms = ((PlayerEntityModelAccessor)playerRenderer).client$hasThinArms();
        bodyLayers.add(new Layer(0, false, EnumPlayerModelParts.LEFT_PANTS_LEG, Shape.LEGS, () -> playerRenderer.getMainModel().bipedLeftLeg, () -> Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Left Leg").getValBoolean()));
        bodyLayers.add(new Layer(1, false, EnumPlayerModelParts.RIGHT_PANTS_LEG, Shape.LEGS, () -> playerRenderer.getMainModel().bipedRightLeg, () -> Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Right Leg").getValBoolean()));
        bodyLayers.add(new Layer(2, false, EnumPlayerModelParts.LEFT_SLEEVE, thinArms ? Shape.ARMS_SLIM : Shape.ARMS, () -> playerRenderer.getMainModel().bipedLeftArm, () -> Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Left Arm").getValBoolean()));
        bodyLayers.add(new Layer(3, true, EnumPlayerModelParts.RIGHT_SLEEVE, thinArms ? Shape.ARMS_SLIM : Shape.ARMS, () -> playerRenderer.getMainModel().bipedRightArm, () -> Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Right Arm").getValBoolean()));
        bodyLayers.add(new Layer(4, false, EnumPlayerModelParts.JACKET, Shape.BODY, () -> playerRenderer.getMainModel().bipedBody, () -> Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Body").getValBoolean()));
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float paramFloat1, float paramFloat2, float paramFloat3,
                              float deltaTick, float paramFloat5, float paramFloat6, float paramFloat7) {
        if (!player.hasSkin() || player.isInvisible()) {
            return;
        }
        if(mc.theWorld == null) {
            return; // in a menu or something and the model gets rendered
        }
        if(mc.thePlayer.getPositionVector().squareDistanceTo(player.getPositionVector()) > Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Level Of Detail Distance").getValInt()*Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Level Of Detail Distance").getValInt())return;

        PlayerSettings settings = (PlayerSettings) player;
        // check for it being setup first to speedup the rendering
        if(settings.client$getSkinLayers() == null && !setupModel(player, settings)) {
            return; // no head layer setup and wasn't able to setup
        }


        //this.playerRenderer.bindTexture(player.getLocationSkin());
        renderLayers(player, (CustomizableModelPart[]) settings.client$getSkinLayers(), deltaTick);
    }

    private boolean setupModel(AbstractClientPlayer abstractClientPlayerEntity, PlayerSettings settings) {
        if(!SkinUtil.hasCustomSkin(abstractClientPlayerEntity)) {
            return false; // default skin
        }
        SkinUtil.setup3dLayers(abstractClientPlayerEntity, settings, thinArms, null);
        return true;
    }

    private final List<Layer> bodyLayers = new ArrayList<>();

    class Layer{
        int layersId;
        boolean mirrored;
        EnumPlayerModelParts modelPart;
        Shape shape;
        Supplier<ModelRenderer> vanillaGetter;
        Supplier<Boolean> configGetter;
        public Layer(int layersId, boolean mirrored, EnumPlayerModelParts modelPart, Shape shape,
                     Supplier<ModelRenderer> vanillaGetter, Supplier<Boolean> configGetter) {
            this.layersId = layersId;
            this.mirrored = mirrored;
            this.modelPart = modelPart;
            this.shape = shape;
            this.vanillaGetter = vanillaGetter;
            this.configGetter = configGetter;
        }

    }


    private enum Shape {
        HEAD(0), BODY(0.6f), LEGS(-0.2f), ARMS(0.4f), ARMS_SLIM(0.4f)
        ;

        private final float yOffsetMagicValue;

        private Shape(float yOffsetMagicValue) {
            this.yOffsetMagicValue = yOffsetMagicValue;
        }

    }

    public void renderLayers(AbstractClientPlayer abstractClientPlayer, CustomizableModelPart[] layers, float deltaTick) {
        if(layers == null)return;
        float pixelScaling = Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Voxel Size").getValFloat();
        float heightScaling = 1.035f;
        float widthScaling = Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Voxel Size").getValFloat();
        // Overlay refuses to work correctly, this is a workaround for now
        boolean redTint = abstractClientPlayer.hurtTime > 0 || abstractClientPlayer.deathTime > 0;
        for(Layer layer : bodyLayers) {
            if(abstractClientPlayer.isWearing(layer.modelPart) && !layer.vanillaGetter.get().isHidden && layer.configGetter.get()) {
                GlStateManager.pushMatrix();
                if(abstractClientPlayer.isSneaking()) {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }
                layer.vanillaGetter.get().postRender(0.0625F);
                if(layer.shape == Shape.ARMS) {
                    layers[layer.layersId].x = 0.998f*16;
                } else if(layer.shape == Shape.ARMS_SLIM) {
                    layers[layer.layersId].x = 0.499f*16;
                }
                if(layer.shape == Shape.BODY) {
                    widthScaling = Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Body Voxel Width Size").getValFloat();
                }else {
                    widthScaling = Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Voxel Size").getValFloat();
                }
                if(layer.mirrored) {
                    layers[layer.layersId].x *= -1;
                }
                GlStateManager.scale(0.0625, 0.0625, 0.0625);
                GlStateManager.scale(widthScaling, heightScaling, pixelScaling);
                layers[layer.layersId].y = layer.shape.yOffsetMagicValue;

                layers[layer.layersId].render(redTint);
                GlStateManager.popMatrix();
            }
        }

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

}
