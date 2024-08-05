package fr.dog.util.render.img;

import fr.dog.util.player.ChatUtil;
import fr.dog.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.glColor4f;

public class ImageObject {

    public BufferedImage img = null;
    public int textureID = 0;
    private int width, height = 0;
    public boolean loading = false;
    public boolean isLoaded = false;
    private final boolean filter;
    private final ResourceLocation resourceLocation;
    private final File file;

    public ImageObject(File file) {
        this(file, true);
    }

    public ImageObject(File file, boolean filter) {
        this.file = file;
        this.filter = filter;
        this.resourceLocation = null;
    }

    public ImageObject(ResourceLocation resourceLocation) {
        this(resourceLocation, true);
    }

    public ImageObject(ResourceLocation resourceLocation, boolean filter) {
        this.file = null;
        this.filter = filter;
        this.resourceLocation = resourceLocation;
    }

    private void loadImage() throws IOException {
        if (resourceLocation != null) {
            loadImageFromResource();
        } else if (file != null) {
            loadImageFromDisk();
        }
    }

    private void loadImageFromDisk() throws IOException {
        img = ImageIO.read(file);
    }

    private void loadImageFromResource() throws IOException {
        InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
        img = ImageIO.read(inputStream);
    }

    public CompletableFuture<Void> loadAsync() {
        if (loading || isLoaded) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> {
            try {
                loading = true;
                loadImage();
                width = img.getWidth();
                height = img.getHeight();
                int[] pixels = new int[width * height];
                img.getRGB(0, 0, width, height, pixels, 0, width);

                ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = pixels[y * width + x];
                        buffer.put((byte) ((pixel >> 16) & 0xFF));
                        buffer.put((byte) ((pixel >> 8) & 0xFF));
                        buffer.put((byte) (pixel & 0xFF));
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    }
                }

                buffer.flip();

                Minecraft.getMinecraft().addScheduledTask(() -> {
                    textureID = GL11.glGenTextures();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
                    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

                    if (filter) {
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                    } else {
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                    }
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

                    isLoaded = true;
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void unload() {
        if(!isLoaded)
            return;

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GlStateManager.deleteTexture(textureID);
    }

    public void drawImg(float x, float y, float width, float height) {
        if(!isLoaded)
            return;

        enableTexture2D();
        enableBlend();
        glColor4f(1, 1, 1, 1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        resetColor();
    }

    public void drawImg(float x, float y) {
        if(!isLoaded)
            return;

        enableTexture2D();
        enableBlend();
        glColor4f(1, 1, 1, 1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        resetColor();
    }
}
