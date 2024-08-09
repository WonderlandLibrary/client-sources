/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;

public class LightTexture
implements AutoCloseable {
    private final DynamicTexture dynamicTexture;
    private final NativeImage nativeImage;
    private final ResourceLocation resourceLocation;
    private boolean needsUpdate;
    private float torchFlicker;
    private final GameRenderer entityRenderer;
    private final Minecraft client;
    private boolean allowed = true;
    private boolean custom = false;
    private Vector3f tempVector = new Vector3f();
    public static final int MAX_BRIGHTNESS = LightTexture.packLight(15, 15);

    public LightTexture(GameRenderer gameRenderer, Minecraft minecraft) {
        this.entityRenderer = gameRenderer;
        this.client = minecraft;
        this.dynamicTexture = new DynamicTexture(16, 16, false);
        this.resourceLocation = this.client.getTextureManager().getDynamicTextureLocation("light_map", this.dynamicTexture);
        this.nativeImage = this.dynamicTexture.getTextureData();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                this.nativeImage.setPixelRGBA(j, i, -1);
            }
        }
        this.dynamicTexture.updateDynamicTexture();
    }

    @Override
    public void close() {
        this.dynamicTexture.close();
    }

    public void tick() {
        this.torchFlicker = (float)((double)this.torchFlicker + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
        this.torchFlicker = (float)((double)this.torchFlicker * 0.9);
        this.needsUpdate = true;
    }

    public void disableLightmap() {
        RenderSystem.activeTexture(33986);
        RenderSystem.disableTexture();
        RenderSystem.activeTexture(33984);
        if (Config.isShaders()) {
            Shaders.disableLightmap();
        }
    }

    public void enableLightmap() {
        if (this.allowed) {
            RenderSystem.activeTexture(33986);
            RenderSystem.matrixMode(5890);
            RenderSystem.loadIdentity();
            float f = 0.00390625f;
            RenderSystem.scalef(0.00390625f, 0.00390625f, 0.00390625f);
            RenderSystem.translatef(8.0f, 8.0f, 8.0f);
            RenderSystem.matrixMode(5888);
            this.client.getTextureManager().bindTexture(this.resourceLocation);
            RenderSystem.texParameter(3553, 10241, 9729);
            RenderSystem.texParameter(3553, 10240, 9729);
            RenderSystem.texParameter(3553, 10242, 33071);
            RenderSystem.texParameter(3553, 10243, 33071);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableTexture();
            RenderSystem.activeTexture(33984);
            if (Config.isShaders()) {
                Shaders.enableLightmap();
            }
        }
    }

    public void updateLightmap(float f) {
        if (this.needsUpdate) {
            this.needsUpdate = false;
            this.client.getProfiler().startSection("lightTex");
            ClientWorld clientWorld = this.client.world;
            if (clientWorld != null) {
                this.custom = false;
                if (Config.isCustomColors()) {
                    boolean bl;
                    boolean bl2 = bl = this.client.player.isPotionActive(Effects.NIGHT_VISION) || this.client.player.isPotionActive(Effects.CONDUIT_POWER);
                    if (CustomColors.updateLightmap(clientWorld, this.torchFlicker, this.nativeImage, bl, f)) {
                        this.dynamicTexture.updateDynamicTexture();
                        this.needsUpdate = false;
                        this.client.getProfiler().endSection();
                        this.custom = true;
                        return;
                    }
                }
                float f2 = clientWorld.getSunBrightness(1.0f);
                float f3 = clientWorld.getTimeLightningFlash() > 0 ? 1.0f : f2 * 0.95f + 0.05f;
                float f4 = this.client.player.getWaterBrightness();
                float f5 = this.client.player.isPotionActive(Effects.NIGHT_VISION) ? GameRenderer.getNightVisionBrightness(this.client.player, f) : (f4 > 0.0f && this.client.player.isPotionActive(Effects.CONDUIT_POWER) ? f4 : 0.0f);
                Vector3f vector3f = new Vector3f(f2, f2, 1.0f);
                vector3f.lerp(new Vector3f(1.0f, 1.0f, 1.0f), 0.35f);
                float f6 = this.torchFlicker + 1.5f;
                Vector3f vector3f2 = new Vector3f();
                for (int i = 0; i < 16; ++i) {
                    for (int j = 0; j < 16; ++j) {
                        float f7;
                        Vector3f vector3f3;
                        float f8;
                        float f9 = this.getLightBrightness(clientWorld, i) * f3;
                        float f10 = this.getLightBrightness(clientWorld, j) * f6;
                        float f11 = f10 * ((f10 * 0.6f + 0.4f) * 0.6f + 0.4f);
                        float f12 = f10 * (f10 * f10 * 0.6f + 0.4f);
                        vector3f2.set(f10, f11, f12);
                        if (clientWorld.func_239132_a_().func_241684_d_()) {
                            vector3f2.lerp(this.getTempVector3f(0.99f, 1.12f, 1.0f), 0.25f);
                        } else {
                            Vector3f vector3f4 = this.getTempCopy(vector3f);
                            vector3f4.mul(f9);
                            vector3f2.add(vector3f4);
                            vector3f2.lerp(this.getTempVector3f(0.75f, 0.75f, 0.75f), 0.04f);
                            if (this.entityRenderer.getBossColorModifier(f) > 0.0f) {
                                f8 = this.entityRenderer.getBossColorModifier(f);
                                vector3f3 = this.getTempCopy(vector3f2);
                                vector3f3.mul(0.7f, 0.6f, 0.6f);
                                vector3f2.lerp(vector3f3, f8);
                            }
                        }
                        vector3f2.clamp(0.0f, 1.0f);
                        if (f5 > 0.0f && (f7 = Math.max(vector3f2.getX(), Math.max(vector3f2.getY(), vector3f2.getZ()))) < 1.0f) {
                            f8 = 1.0f / f7;
                            vector3f3 = this.getTempCopy(vector3f2);
                            vector3f3.mul(f8);
                            vector3f2.lerp(vector3f3, f5);
                        }
                        float f13 = (float)this.client.gameSettings.gamma;
                        Vector3f vector3f5 = this.getTempCopy(vector3f2);
                        vector3f5.apply(this::invGamma);
                        vector3f2.lerp(vector3f5, f13);
                        vector3f2.lerp(this.getTempVector3f(0.75f, 0.75f, 0.75f), 0.04f);
                        vector3f2.clamp(0.0f, 1.0f);
                        vector3f2.mul(255.0f);
                        int n = 255;
                        int n2 = (int)vector3f2.getX();
                        int n3 = (int)vector3f2.getY();
                        int n4 = (int)vector3f2.getZ();
                        this.nativeImage.setPixelRGBA(j, i, 0xFF000000 | n4 << 16 | n3 << 8 | n2);
                    }
                }
                this.dynamicTexture.updateDynamicTexture();
                this.client.getProfiler().endSection();
            }
        }
    }

    private float invGamma(float f) {
        float f2 = 1.0f - f;
        return 1.0f - f2 * f2 * f2 * f2;
    }

    private float getLightBrightness(World world, int n) {
        return world.getDimensionType().getAmbientLight(n);
    }

    public static int packLight(int n, int n2) {
        return n << 4 | n2 << 20;
    }

    public static int getLightBlock(int n) {
        return (n & 0xFFFF) >> 4;
    }

    public static int getLightSky(int n) {
        return n >> 20 & 0xFFFF;
    }

    private Vector3f getTempVector3f(float f, float f2, float f3) {
        this.tempVector.set(f, f2, f3);
        return this.tempVector;
    }

    private Vector3f getTempCopy(Vector3f vector3f) {
        this.tempVector.set(vector3f.getX(), vector3f.getY(), vector3f.getZ());
        return this.tempVector;
    }

    public boolean isAllowed() {
        return this.allowed;
    }

    public void setAllowed(boolean bl) {
        this.allowed = bl;
    }

    public boolean isCustom() {
        return this.custom;
    }
}

