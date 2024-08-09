/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LoadingGui;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.util.PropertiesOrdered;

public class ResourceLoadProgressGui
extends LoadingGui {
    private static final ResourceLocation MOJANG_LOGO_TEXTURE = new ResourceLocation("textures/gui/title/mojangstudios.png");
    private static final int field_238627_b_ = ColorHelper.PackedColor.packColor(255, 239, 50, 61);
    private static final int field_238628_c_ = field_238627_b_ & 0xFFFFFF;
    private final Minecraft mc;
    private final IAsyncReloader asyncReloader;
    private final Consumer<Optional<Throwable>> completedCallback;
    private final boolean reloading;
    private float progress;
    private long fadeOutStart = -1L;
    private long fadeInStart = -1L;
    private int colorBackground = field_238628_c_;
    private int colorBar = field_238628_c_;
    private int colorOutline = 0xFFFFFF;
    private int colorProgress = 0xFFFFFF;
    private GlBlendState blendState = null;
    private boolean fadeOut = false;

    public ResourceLoadProgressGui(Minecraft minecraft, IAsyncReloader iAsyncReloader, Consumer<Optional<Throwable>> consumer, boolean bl) {
        this.mc = minecraft;
        this.asyncReloader = iAsyncReloader;
        this.completedCallback = consumer;
        this.reloading = false;
    }

    public static void loadLogoTexture(Minecraft minecraft) {
        minecraft.getTextureManager().loadTexture(MOJANG_LOGO_TEXTURE, new MojangLogoTexture());
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        float f2;
        int n3;
        float f3;
        int n4 = this.mc.getMainWindow().getScaledWidth();
        int n5 = this.mc.getMainWindow().getScaledHeight();
        long l = Util.milliTime();
        if (this.reloading && (this.asyncReloader.asyncPartDone() || this.mc.currentScreen != null) && this.fadeInStart == -1L) {
            this.fadeInStart = l;
        }
        float f4 = this.fadeOutStart > -1L ? (float)(l - this.fadeOutStart) / 1000.0f : -1.0f;
        float f5 = f3 = this.fadeInStart > -1L ? (float)(l - this.fadeInStart) / 500.0f : -1.0f;
        if (f4 >= 1.0f) {
            this.fadeOut = true;
            if (this.mc.currentScreen != null) {
                this.mc.currentScreen.render(matrixStack, 0, 0, f);
            }
            n3 = MathHelper.ceil((1.0f - MathHelper.clamp(f4 - 1.0f, 0.0f, 1.0f)) * 255.0f);
            ResourceLoadProgressGui.fill(matrixStack, 0, 0, n4, n5, this.colorBackground | n3 << 24);
            f2 = 1.0f - MathHelper.clamp(f4 - 1.0f, 0.0f, 1.0f);
        } else if (this.reloading) {
            if (this.mc.currentScreen != null && f3 < 1.0f) {
                this.mc.currentScreen.render(matrixStack, n, n2, f);
            }
            n3 = MathHelper.ceil(MathHelper.clamp((double)f3, 0.15, 1.0) * 255.0);
            ResourceLoadProgressGui.fill(matrixStack, 0, 0, n4, n5, this.colorBackground | n3 << 24);
            f2 = MathHelper.clamp(f3, 0.0f, 1.0f);
        } else {
            ResourceLoadProgressGui.fill(matrixStack, 0, 0, n4, n5, this.colorBackground | 0xFF000000);
            f2 = 1.0f;
        }
        n3 = (int)((double)this.mc.getMainWindow().getScaledWidth() * 0.5);
        int n6 = (int)((double)this.mc.getMainWindow().getScaledHeight() * 0.5);
        double d = Math.min((double)this.mc.getMainWindow().getScaledWidth() * 0.75, (double)this.mc.getMainWindow().getScaledHeight()) * 0.25;
        int n7 = (int)(d * 0.5);
        double d2 = d * 4.0;
        int n8 = (int)(d2 * 0.5);
        this.mc.getTextureManager().bindTexture(MOJANG_LOGO_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendEquation(32774);
        RenderSystem.blendFunc(770, 1);
        RenderSystem.alphaFunc(516, 0.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f2);
        boolean bl = true;
        if (this.blendState != null) {
            this.blendState.apply();
            if (!this.blendState.isEnabled() && this.fadeOut) {
                bl = false;
            }
        }
        if (bl) {
            ResourceLoadProgressGui.blit(matrixStack, n3 - n8, n6 - n7, n8, (int)d, -0.0625f, 0.0f, 120, 60, 120, 120);
            ResourceLoadProgressGui.blit(matrixStack, n3, n6 - n7, n8, (int)d, 0.0625f, 60.0f, 120, 60, 120, 120);
        }
        RenderSystem.defaultBlendFunc();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.disableBlend();
        int n9 = (int)((double)this.mc.getMainWindow().getScaledHeight() * 0.8325);
        float f6 = this.asyncReloader.estimateExecutionSpeed();
        this.progress = MathHelper.clamp(this.progress * 0.95f + f6 * 0.050000012f, 0.0f, 1.0f);
        Reflector.ClientModLoader_renderProgressText.call(new Object[0]);
        if (f4 < 1.0f) {
            this.func_238629_a_(matrixStack, n4 / 2 - n8, n9 - 5, n4 / 2 + n8, n9 + 5, 1.0f - MathHelper.clamp(f4, 0.0f, 1.0f));
        }
        if (f4 >= 2.0f) {
            this.mc.setLoadingGui(null);
        }
        if (this.fadeOutStart == -1L && this.asyncReloader.fullyDone() && (!this.reloading || f3 >= 2.0f)) {
            this.fadeOutStart = Util.milliTime();
            try {
                this.asyncReloader.join();
                this.completedCallback.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.completedCallback.accept(Optional.of(throwable));
            }
            if (this.mc.currentScreen != null) {
                this.mc.currentScreen.init(this.mc, this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight());
            }
        }
    }

    private void func_238629_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9 = MathHelper.ceil((float)(n3 - n - 2) * this.progress);
        int n10 = Math.round(f * 255.0f);
        if (this.colorBar != this.colorBackground) {
            n8 = this.colorBar >> 16 & 0xFF;
            n7 = this.colorBar >> 8 & 0xFF;
            n6 = this.colorBar & 0xFF;
            n5 = ColorHelper.PackedColor.packColor(n10, n8, n7, n6);
            ResourceLoadProgressGui.fill(matrixStack, n, n2, n3, n4, n5);
        }
        n8 = this.colorOutline >> 16 & 0xFF;
        n7 = this.colorOutline >> 8 & 0xFF;
        n6 = this.colorOutline & 0xFF;
        n5 = ColorHelper.PackedColor.packColor(n10, n8, n7, n6);
        ResourceLoadProgressGui.fill(matrixStack, n + 1, n2, n3 - 1, n2 + 1, n5);
        ResourceLoadProgressGui.fill(matrixStack, n + 1, n4, n3 - 1, n4 - 1, n5);
        ResourceLoadProgressGui.fill(matrixStack, n, n2, n + 1, n4, n5);
        ResourceLoadProgressGui.fill(matrixStack, n3, n2, n3 - 1, n4, n5);
        int n11 = this.colorProgress >> 16 & 0xFF;
        int n12 = this.colorProgress >> 8 & 0xFF;
        int n13 = this.colorProgress & 0xFF;
        n5 = ColorHelper.PackedColor.packColor(n10, n11, n12, n13);
        ResourceLoadProgressGui.fill(matrixStack, n + 2, n2 + 2, n + n9, n4 - 2, n5);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void update() {
        this.colorBackground = field_238628_c_;
        this.colorBar = field_238628_c_;
        this.colorOutline = 0xFFFFFF;
        this.colorProgress = 0xFFFFFF;
        if (Config.isCustomColors()) {
            try {
                String string = "optifine/color.properties";
                ResourceLocation resourceLocation = new ResourceLocation(string);
                if (!Config.hasResource(resourceLocation)) {
                    return;
                }
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                Config.dbg("Loading " + string);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                this.colorBackground = ResourceLoadProgressGui.readColor(propertiesOrdered, "screen.loading", this.colorBackground);
                this.colorOutline = ResourceLoadProgressGui.readColor(propertiesOrdered, "screen.loading.outline", this.colorOutline);
                this.colorBar = ResourceLoadProgressGui.readColor(propertiesOrdered, "screen.loading.bar", this.colorBar);
                this.colorProgress = ResourceLoadProgressGui.readColor(propertiesOrdered, "screen.loading.progress", this.colorProgress);
                this.blendState = ShaderPackParser.parseBlendState(propertiesOrdered.getProperty("screen.loading.blend"));
            } catch (Exception exception) {
                Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
            }
        }
    }

    private static int readColor(Properties properties, String string, int n) {
        String string2 = properties.getProperty(string);
        if (string2 == null) {
            return n;
        }
        int n2 = ResourceLoadProgressGui.parseColor(string2 = string2.trim(), n);
        if (n2 < 0) {
            Config.warn("Invalid color: " + string + " = " + string2);
            return n2;
        }
        Config.dbg(string + " = " + string2);
        return n2;
    }

    private static int parseColor(String string, int n) {
        if (string == null) {
            return n;
        }
        string = string.trim();
        try {
            return Integer.parseInt(string, 16) & 0xFFFFFF;
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public boolean isFadeOut() {
        return this.fadeOut;
    }

    static class MojangLogoTexture
    extends SimpleTexture {
        public MojangLogoTexture() {
            super(MOJANG_LOGO_TEXTURE);
        }

        @Override
        protected SimpleTexture.TextureData getTextureData(IResourceManager iResourceManager) {
            SimpleTexture.TextureData textureData;
            block8: {
                Minecraft minecraft = Minecraft.getInstance();
                VanillaPack vanillaPack = minecraft.getPackFinder().getVanillaPack();
                InputStream inputStream = MojangLogoTexture.getLogoInputStream(iResourceManager, vanillaPack);
                try {
                    textureData = new SimpleTexture.TextureData(new TextureMetadataSection(true, true), NativeImage.read(inputStream));
                    if (inputStream == null) break block8;
                } catch (Throwable throwable) {
                    try {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    } catch (IOException iOException) {
                        return new SimpleTexture.TextureData(iOException);
                    }
                }
                inputStream.close();
            }
            return textureData;
        }

        private static InputStream getLogoInputStream(IResourceManager iResourceManager, VanillaPack vanillaPack) throws IOException {
            return iResourceManager.hasResource(MOJANG_LOGO_TEXTURE) ? iResourceManager.getResource(MOJANG_LOGO_TEXTURE).getInputStream() : vanillaPack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, MOJANG_LOGO_TEXTURE);
        }
    }
}

