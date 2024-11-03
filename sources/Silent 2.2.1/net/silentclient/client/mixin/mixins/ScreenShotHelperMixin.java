package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ScreenShotHelper;
import net.silentclient.client.utils.AsyncScreenshots;
import net.silentclient.client.utils.Multithreading;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.IntBuffer;

@Mixin(ScreenShotHelper.class)
public class ScreenShotHelperMixin {
    @Shadow private static IntBuffer pixelBuffer;
    @Shadow private static int[] pixelValues;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject(method = "saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/IChatComponent;", at = @At("HEAD"), cancellable = true)
    private static void silent$screenshotManager(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer, CallbackInfoReturnable<IChatComponent> cir) {
        File screenshotDirectory = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
        if (!screenshotDirectory.exists()) {
            screenshotDirectory.mkdir();
        }
        if (OpenGlHelper.isFramebufferEnabled()) {
            width = buffer.framebufferTextureWidth;
            height = buffer.framebufferTextureHeight;
        }
        int scale = width * height;
        if (pixelBuffer == null || pixelBuffer.capacity() < scale) {
            pixelBuffer = BufferUtils.createIntBuffer(scale);
            pixelValues = new int[scale];
        }
        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(buffer.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        } else {
            GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
        }
        pixelBuffer.get(pixelValues);
        Multithreading.runAsync(new AsyncScreenshots(width, height, pixelValues, screenshotDirectory));

        cir.setReturnValue(null);
    }
}
