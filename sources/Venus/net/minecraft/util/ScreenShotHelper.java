/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenShotHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    public static void saveScreenshot(File file, int n, int n2, Framebuffer framebuffer, Consumer<ITextComponent> consumer) {
        ScreenShotHelper.saveScreenshot(file, null, n, n2, framebuffer, consumer);
    }

    public static void saveScreenshot(File file, @Nullable String string, int n, int n2, Framebuffer framebuffer, Consumer<ITextComponent> consumer) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> ScreenShotHelper.lambda$saveScreenshot$0(file, string, n, n2, framebuffer, consumer));
        } else {
            ScreenShotHelper.saveScreenshotRaw(file, string, n, n2, framebuffer, consumer);
        }
    }

    private static void saveScreenshotRaw(File file, @Nullable String string, int n, int n2, Framebuffer framebuffer, Consumer<ITextComponent> consumer) {
        boolean bl;
        Minecraft minecraft = Config.getMinecraft();
        MainWindow mainWindow = minecraft.getMainWindow();
        GameSettings gameSettings = Config.getGameSettings();
        int n3 = mainWindow.getFramebufferWidth();
        int n4 = mainWindow.getFramebufferHeight();
        int n5 = gameSettings.guiScale;
        int n6 = mainWindow.calcGuiScale(minecraft.gameSettings.guiScale, minecraft.gameSettings.forceUnicodeFont);
        int n7 = Config.getScreenshotSize();
        boolean bl2 = bl = GLX.isUsingFBOs() && n7 > 1;
        if (bl) {
            gameSettings.guiScale = n6 * n7;
            mainWindow.resizeFramebuffer(n3 * n7, n4 * n7);
            GlStateManager.pushMatrix();
            GlStateManager.clear(16640);
            minecraft.getFramebuffer().bindFramebuffer(false);
            GlStateManager.enableTexture();
            minecraft.gameRenderer.updateCameraAndRender(minecraft.getRenderPartialTicks(), System.nanoTime(), false);
        }
        NativeImage nativeImage = ScreenShotHelper.createScreenshot(n, n2, framebuffer);
        if (bl) {
            minecraft.getFramebuffer().unbindFramebuffer();
            GlStateManager.popMatrix();
            Config.getGameSettings().guiScale = n5;
            mainWindow.resizeFramebuffer(n3, n4);
        }
        File file2 = new File(file, "screenshots");
        file2.mkdir();
        File file3 = string == null ? ScreenShotHelper.getTimestampedPNGFileForDirectory(file2) : new File(file2, string);
        Object object = null;
        if (Reflector.ForgeHooksClient_onScreenshot.exists()) {
            object = Reflector.call(Reflector.ForgeHooksClient_onScreenshot, nativeImage, file3);
            if (Reflector.callBoolean(object, Reflector.Event_isCanceled, new Object[0])) {
                ITextComponent iTextComponent = (ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getCancelMessage, new Object[0]);
                consumer.accept(iTextComponent);
                return;
            }
            file3 = (File)Reflector.call(object, Reflector.ScreenshotEvent_getScreenshotFile, new Object[0]);
        }
        File file4 = file3;
        Object object2 = object;
        Util.getRenderingService().execute(() -> ScreenShotHelper.lambda$saveScreenshotRaw$2(nativeImage, file4, object2, consumer));
    }

    public static NativeImage createScreenshot(int n, int n2, Framebuffer framebuffer) {
        if (!GLX.isUsingFBOs()) {
            NativeImage nativeImage = new NativeImage(n, n2, false);
            nativeImage.downloadFromFramebuffer(false);
            nativeImage.flip();
            return nativeImage;
        }
        n = framebuffer.framebufferTextureWidth;
        n2 = framebuffer.framebufferTextureHeight;
        NativeImage nativeImage = new NativeImage(n, n2, false);
        RenderSystem.bindTexture(framebuffer.func_242996_f());
        nativeImage.downloadFromTexture(0, false);
        nativeImage.flip();
        return nativeImage;
    }

    private static File getTimestampedPNGFileForDirectory(File file) {
        String string = DATE_FORMAT.format(new Date());
        int n = 1;
        File file2;
        while ((file2 = new File(file, string + (String)(n == 1 ? "" : "_" + n) + ".png")).exists()) {
            ++n;
        }
        return file2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void lambda$saveScreenshotRaw$2(NativeImage nativeImage, File file, Object object, Consumer consumer) {
        try {
            nativeImage.write(file);
            IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(file.getName()).mergeStyle(TextFormatting.UNDERLINE).modifyStyle(arg_0 -> ScreenShotHelper.lambda$saveScreenshotRaw$1(file, arg_0));
            if (object != null && Reflector.call(object, Reflector.ScreenshotEvent_getResultMessage, new Object[0]) != null) {
                consumer.accept((ITextComponent)Reflector.call(object, Reflector.ScreenshotEvent_getResultMessage, new Object[0]));
            } else {
                consumer.accept(new TranslationTextComponent("screenshot.success", iFormattableTextComponent));
            }
        } catch (Exception exception) {
            LOGGER.warn("Couldn't save screenshot", (Throwable)exception);
            consumer.accept(new TranslationTextComponent("screenshot.failure", exception.getMessage()));
        } finally {
            nativeImage.close();
        }
    }

    private static Style lambda$saveScreenshotRaw$1(File file, Style style) {
        return style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath()));
    }

    private static void lambda$saveScreenshot$0(File file, String string, int n, int n2, Framebuffer framebuffer, Consumer consumer) {
        ScreenShotHelper.saveScreenshotRaw(file, string, n, n2, framebuffer, consumer);
    }
}

