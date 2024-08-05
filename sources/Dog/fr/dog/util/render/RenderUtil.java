package fr.dog.util.render;

import fr.dog.Dog;
import fr.dog.theme.Theme;
import fr.dog.util.InstanceAccess;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.opengl.StencilUtil;
import fr.dog.util.render.shader.Shader;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjglx.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.client.renderer.GlStateManager.*;
import static net.minecraft.client.renderer.GlStateManager.resetColor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

@SuppressWarnings("unused")
public class RenderUtil implements InstanceAccess {

    private static final Frustum frustum = new Frustum();

    //HashMaps
    private static final HashMap<String, Float> blackPeople = new HashMap<>();
    @Getter
    private static final HashMap<String, Integer> cachedHeads = new HashMap<>();

    //Glow
    private static Framebuffer glowStencilFramebuffer = new Framebuffer(1, 1, false);
    public static Framebuffer glowFramebuffer = new Framebuffer(1, 1, true);
    public static Framebuffer blurFramebuffer = new Framebuffer(1, 1, true);
    private static final List<Framebuffer> framebufferList = new ArrayList<>();
    private static int currentIterations;

    //Shaders
    private static final Shader loadingCircleShader2 = new Shader("circleLoading2.frag");
    private static final Shader loadingCircleShader = new Shader("circleLoading.frag");
    private static final Shader roundedShader = new Shader("rounded.frag");
    private static final Shader circleShader = new Shader("circle.frag");
    private static final Shader blurShader = new Shader("gaussian.frag");
    private static final Shader glowShader = new Shader("glow.frag");
    private static final Shader downShader = new Shader("down.frag");

    //Precompute sin and cos for rectangle and circle (which i dont really need so)
    private static final double[] sin;
    private static final double[] cos;

    static {
        sin = new double[181];
        cos = new double[181];
        for (int angle = 0; angle <= 180; angle += 6) {
            double radians = Math.toRadians(angle);
            sin[angle] = Math.sin(radians);
            cos[angle] = Math.cos(radians);
        }
    }

    // could register to use an event but useless
    public static void onPlayerJoin() {
        for(int texture : cachedHeads.values()){
            GL11.glDeleteTextures(texture);
        }

        cachedHeads.clear();
        blackPeople.clear();
    }


    public static boolean isBBInFrustum(final AxisAlignedBB aabb) {
        frustum.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        return frustum.isBoundingBoxInFrustum(aabb);
    }

    public static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = mc.getRenderViewEntity();
        frustum.setPosition(current.posX, current.posY, current.posZ);
        return frustum.isBoundingBoxInFrustum(bb);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
    }

    public static void scale(Runnable runnable, float x, float y, double scale) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glScaled(scale, scale, 1);
        glTranslatef(-x, -y, 0);
        runnable.run();
        glPopMatrix();
    }

    public static void translate(final Runnable runnable, float x, float y) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        runnable.run();
        glPopMatrix();
    }

    public static void start() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void stop() {
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void render(final int mode, final Runnable render) {
        glBegin(mode);
        render.run();
        glEnd();
    }

    public static void color(final Color color) {
        final float[] array = ColorUtil.toGLColor(color);
        glColor4f(array[0], array[1], array[2], array[3]);
    }

    public static void drawRect(float x, float y, float width, float height, Color color) {
        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        y += height;
        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

        glBegin(GL_QUADS);
        glVertex2d(x, y);
        glVertex2d(x + width, y);
        glVertex2d(x + width, y - height);
        glVertex2d(x, y - height);
        glEnd();

        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static final void drawRectOutline(float x, float y, float width, float height, float thickness, Color color) {
        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        y += height;
        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

        glLineWidth(thickness);
        glBegin(GL_LINE_SMOOTH);
        glVertex2d(x, y);
        glVertex2d(x + width, y);
        glVertex2d(x + width, y - height);
        glVertex2d(x, y - height);
        glEnd();

        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawRoundedRect(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        float[] colors = ColorUtil.toGLColor(color);

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        roundedShader.start();
        roundedShader.setUniformFloat("size", width * 2, height * 2);
        roundedShader.setUniformFloat("round", radius * 2);
        roundedShader.setUniformFloat("color1", colors[0], colors[1], colors[2], colors[3]);
        roundedShader.setUniformFloat("color2", colors[0], colors[1], colors[2], colors[3]);
        roundedShader.setUniformFloat("color3", colors[0], colors[1], colors[2], colors[3]);
        roundedShader.setUniformFloat("color4", colors[0], colors[1], colors[2], colors[3]);
        roundedShader.drawQuads(x, y, width, height);
        roundedShader.stop();
        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawRoundedRect(final float x, final float y, final float width, final float height, final float radius, final Color color1, final Color color2, final Color color3, final Color color4) {
        float[] colors1 = ColorUtil.toGLColor(color1);
        float[] colors2 = ColorUtil.toGLColor(color2);
        float[] colors3 = ColorUtil.toGLColor(color3);
        float[] colors4 = ColorUtil.toGLColor(color4);

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        roundedShader.start();
        roundedShader.setUniformFloat("size", width * 2, height * 2);
        roundedShader.setUniformFloat("round", radius * 2);
        roundedShader.setUniformFloat("color1", colors1[0], colors1[1], colors1[2], colors1[3]);
        roundedShader.setUniformFloat("color2", colors2[0], colors2[1], colors2[2], colors2[3]);
        roundedShader.setUniformFloat("color3", colors3[0], colors3[1], colors3[2], colors3[3]);
        roundedShader.setUniformFloat("color4", colors4[0], colors4[1], colors4[2], colors4[3]);
        roundedShader.drawQuads(x, y, width, height);
        roundedShader.stop();
        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawRoundedRectGl(float x, float y, float width, float height, float round, Color color) {
        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        round = Math.min(round, Math.min(width / 2, height / 2));


        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glEnable(GL_LINE_SMOOTH);
        glBegin(GL_POLYGON);

        for (int angle = 0; angle <= 90; angle += 6) {
            glVertex2d(x + round - sin[angle] * round, y + round - cos[angle] * round);
        }

        for (int angle = 90; angle <= 180; angle += 6) {
            glVertex2d(x + round - sin[angle] * round, y + height - round - cos[angle] * round);
        }

        for (int angle = 0; angle <= 90; angle += 6) {
            glVertex2d(x + width - round + sin[angle] * round, y + height - round + cos[angle] * round);
        }

        for (int angle = 90; angle <= 180; angle += 6) {
            glVertex2d(x + width - round + sin[angle] * round, y + round + cos[angle] * round);
        }
        glEnd();

        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LINE_SMOOTH);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircleLoading(final float x, final float y, final float size, final float percentage, final Color color) {
        float[] colors = ColorUtil.toGLColor(color);

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        loadingCircleShader.start();
        loadingCircleShader.setUniformFloat("iResolution", size, size);
        loadingCircleShader.setUniformFloat("progress",percentage);
        loadingCircleShader.setUniformInteger("isRound", 1);
        loadingCircleShader.setUniformFloat("color1", colors[0], colors[1], colors[2], colors[3]);
        loadingCircleShader.setUniformFloat("color2", colors[0], colors[1], colors[2], colors[3]);
        loadingCircleShader.drawQuads(x, y, size, size);
        loadingCircleShader.stop();
        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawCircleLoading2(final float x, final float y, final float size) {

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        loadingCircleShader2.start();
        loadingCircleShader2.setUniformFloat("iResolution", size, size);
        loadingCircleShader2.setUniformFloat("iTime", (System.currentTimeMillis() - loadingCircleShader2.startTime)/1000f);
        loadingCircleShader2.drawQuads(x, y, size, size);
        loadingCircleShader2.stop();
        enableTexture2D();
        disableBlend();
        resetColor();
    }


    public static void drawCircleLoading(final float x, final float y, final float size, final float percentage, final Color color1, final Color color2) {
        float[] colors1 = ColorUtil.toGLColor(color1);
        float[] colors2 = ColorUtil.toGLColor(color2);

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        loadingCircleShader.start();
        loadingCircleShader.setUniformFloat("iResolution", size, size);
        loadingCircleShader.setUniformFloat("progress",percentage);
        loadingCircleShader.setUniformFloat("color1", colors1[0], colors1[1], colors1[2], colors1[3]);
        loadingCircleShader.setUniformFloat("color2", colors2[0], colors2[1], colors2[2], colors2[3]);
        loadingCircleShader.drawQuads(x, y, size, size);
        loadingCircleShader.stop();
        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawCircle(final float x, final float y, final float size, final Color color) {
        float[] colors = ColorUtil.toGLColor(color);

        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        circleShader.start();
        circleShader.setUniformFloat("size", size);
        circleShader.setUniformFloat("blur", 1 / size);
        circleShader.setUniformFloat("thickness", 1f);
        circleShader.setUniformFloat("color", colors[0], colors[1], colors[2], colors[3]);
        circleShader.drawQuads(x, y, size, size);
        circleShader.stop();

        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static void drawLine(final float x, final float y, final float x2, final float y2, final float size, final Color color) {
        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

        glEnable(GL11.GL_LINE_SMOOTH);
        glLineWidth(size);

        glBegin(GL11.GL_LINES);

        glVertex2f(x, y);
        glVertex2f(x + x2, y + y2);

        glEnd();

        glDisable(GL11.GL_LINE_SMOOTH);
        enableTexture2D();
        disableBlend();
        resetColor();
    }

    public static CompletableFuture<Float> getBlackAndBrownPercentageAsync(EntityPlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            NetworkPlayerInfo playerInfo = mc.getNetHandler().getPlayerInfo(player.getUniqueID());

            if (playerInfo == null) {
                return 0f;
            }

            if (blackPeople.containsKey(player.getUniqueID().toString())) {
                return blackPeople.get(player.getUniqueID().toString());
            }

            try {
                URL skinURL = new URL("https://mc-heads.net/avatar/" + player.getUniqueID());
                BufferedImage skinImage = ImageIO.read(skinURL);
                if (skinImage == null) {
                    return 0f; // Handle case where skinImage is null
                }

                int width = skinImage.getWidth();
                int height = skinImage.getHeight();
                int blackPixelCount = 0;
                int totalPixels = 0;

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int color = skinImage.getRGB(x, y);
                        int alpha = (color >> 24) & 0xFF;
                        if (alpha == 0) {
                            continue; // Ignore fully transparent pixels
                        }

                        int red = (color >> 16) & 0xFF;
                        int green = (color >> 8) & 0xFF;
                        int blue = color & 0xFF;

                        //black
                        if (red <= 60 && green <= 60 && blue <= 60) {
                            blackPixelCount++;
                        }

                        //brown
                        /*else if (red >= 101 && red <= 150 && green >= 51 && green <= 100 && blue <= 50) {
                            blackPixelCount++;
                        }*/

                        totalPixels++;
                    }
                }

                if (totalPixels == 0) {
                    return 0f;
                }

                float blackPercentage = (blackPixelCount / (float) totalPixels) * 100;
                blackPeople.put(player.getUniqueID().toString(), blackPercentage);
                return blackPercentage;

            } catch (IOException ignored) {/* */}

            return 0f;
        });
    }

    //Will replace with a shader
    public static void drawHead(EntityPlayer player, float x, float y, float size) {
        int textureID;
        if(cachedHeads.containsKey(player.getUniqueID().toString())) {
            textureID = cachedHeads.get(player.getUniqueID().toString());
        }else {
            CompletableFuture<Integer> getHead = getHeadTextureID(player);
            textureID = getHead.getNow(0);
        }

        if(textureID == 0){
            drawCircleLoading2(x, y, size);
            return;
        }

        GL11.glColor4f(1,1,1,1);
        GlStateManager.bindTexture(textureID);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, size, size, size, size);

    }

    public static void drawItemStack(ItemStack stack, float x, float y) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();



        if (stack != null) {
            mc.ingameGUI.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

    private record ImageData(ByteBuffer buffer, int width, int height) {
        //
    }

    private static CompletableFuture<Integer> getHeadTextureID(EntityPlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            if(cachedHeads.containsKey(player.getUniqueID().toString())) {
                return null;
            }
            cachedHeads.put(player.getUniqueID().toString(), 0);
            try {
                URL skinURL = new URL("https://mc-heads.net/avatar/" + player.getUniqueID());
                BufferedImage img = ImageIO.read(skinURL);

                final int width = img.getWidth();
                final int height = img.getHeight();
                int[] pixels = new int[width * height];
                img.getRGB(0, 0, width, height, pixels, 0, width);

                ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height);
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = pixels[y * width + x];
                        buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
                        buffer.put((byte) ((pixel >> 8) & 0xFF));  // Green component
                        buffer.put((byte) (pixel & 0xFF));         // Blue component
                        buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component
                    }
                }
                buffer.flip();

                return new ImageData(buffer, width, height);
            } catch (IOException ignored) {
                return null;
            }
        }).thenApplyAsync(imageData -> {
            if (imageData == null) return 0;
            int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, imageData.width, imageData.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData.buffer);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

            cachedHeads.put(player.getUniqueID().toString(), textureID);
            return textureID;
        }, mc::addScheduledTask);
    }

    public static void drawGlow(Runnable runnable) {
        if (mc.gameSettings.ofFastRender)
            return;

        glowStencilFramebuffer = createFrameBuffer(glowStencilFramebuffer,false);
        glowStencilFramebuffer.framebufferClear();
        glowStencilFramebuffer.bindFramebuffer(false);
        runnable.run();
        glowStencilFramebuffer.unbindFramebuffer();

        Glow(glowStencilFramebuffer.framebufferTexture, 2, 3);
    }
    public static void drawBlur(final Runnable runnable) {
        if (mc.gameSettings.ofFastRender)
            return;

        StencilUtil.renderStencil(runnable, () -> Blur(12));
    }

    public static void drawBlur(final Runnable runnable, final int intensity) {
        if (mc.gameSettings.ofFastRender)
            return;

        StencilUtil.renderStencil(runnable, () -> Blur(intensity));
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer, boolean depth) {
        final boolean needsNewFramebuffer = framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight;

        if (needsNewFramebuffer) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
        }
        return framebuffer;
    }


    private static void Glow(final int framebufferTexture, final int iterations, final int offset) {
        if (currentIterations != iterations || (glowFramebuffer.framebufferWidth != mc.displayWidth || glowFramebuffer.framebufferHeight != mc.displayHeight)) {
            initFramebuffers(iterations);
            currentIterations = iterations;
        }

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (0 * .01));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_ONE, GL_ONE);

        GL11.glClearColor(0, 0, 0, 0);
        renderFBO(framebufferList.get(1), framebufferTexture, downShader, offset);

        //Downsample
        for (int i = 1; i < iterations; i++) {
            renderFBO(framebufferList.get(i + 1), framebufferList.get(i).framebufferTexture, downShader, offset);
        }

        //Upsample
        for (int i = iterations; i > 1; i--) {
            renderFBO(framebufferList.get(i - 1), framebufferList.get(i).framebufferTexture, glowShader, offset);
        }

        Framebuffer lastBuffer = framebufferList.get(0);
        lastBuffer.framebufferClear();
        lastBuffer.bindFramebuffer(false);
        glowShader.start();
        glowShader.setUniformFloat("offset", offset, offset);
        glowShader.setUniformInteger("inTexture", 0);
        glowShader.setUniformInteger("check", 1);
        glowShader.setUniformInteger("textureToCheck", 16);
        glowShader.setUniformFloat("halfpixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
        glowShader.setUniformFloat("iResolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE16);
        glBindTexture(GL_TEXTURE_2D, framebufferTexture);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, framebufferList.get(1).framebufferTexture);
        glowShader.drawQuads();
        glowShader.stop();


        GlStateManager.clearColor(0, 0, 0, 0);
        mc.getFramebuffer().bindFramebuffer(false);
        glBindTexture(GL_TEXTURE_2D, framebufferList.get(0).framebufferTexture);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (0 * .01));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glowShader.drawQuads();
        GlStateManager.bindTexture(0);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (0 * .01));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private static void initFramebuffers(float iterations) {
        for (Framebuffer framebuffer : framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        framebufferList.clear();

        framebufferList.add(glowFramebuffer = RenderUtil.createFrameBuffer(null, true));

        for (int i = 1; i <= iterations; i++) {
            Framebuffer currentBuffer = new Framebuffer((int) (mc.displayWidth / Math.pow(2, i)), (int) (mc.displayHeight / Math.pow(2, i)), true);
            currentBuffer.setFramebufferFilter(GL_LINEAR);

            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
            GlStateManager.bindTexture(0);

            framebufferList.add(currentBuffer);
        }
    }


    private static void renderFBO(Framebuffer framebuffer, int framebufferTexture, Shader shader, float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        shader.start();
        glBindTexture(GL_TEXTURE_2D, framebufferTexture);
        shader.setUniformFloat("offset", offset, offset);
        shader.setUniformInteger("inTexture", 0);
        shader.setUniformInteger("check", 0);
        shader.setUniformFloat("halfpixel", 1.0f / framebuffer.framebufferWidth, 1.0f / framebuffer.framebufferHeight);
        shader.setUniformFloat("iResolution", framebuffer.framebufferWidth, framebuffer.framebufferHeight);
        shader.drawQuads();
        shader.stop();
    }

    public static void Blur(float radius) {
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 1);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);


        blurFramebuffer = createFrameBuffer(blurFramebuffer, false);

        blurFramebuffer.framebufferClear();
        blurFramebuffer.bindFramebuffer(true);
        blurShader.start();
        setupUniformsBlur(1, 0, radius);

        glBindTexture(GL_TEXTURE_2D, mc.getFramebuffer().framebufferTexture);

        blurShader.drawQuads();
        blurFramebuffer.unbindFramebuffer();
        blurShader.stop();

        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.start();
        setupUniformsBlur(0, 1, radius);

        glBindTexture(GL_TEXTURE_2D, blurFramebuffer.framebufferTexture);
        blurShader.drawQuads();
        blurShader.stop();

        glColor4f(1f, 1f, 1f, 1f);
        GlStateManager.bindTexture(0);
    }

    private static void setupUniformsBlur(float dir1, float dir2, float radius) {
        blurShader.setUniformInteger("textureIn", 0);
        blurShader.setUniformFloat("texelSize", 1.0F / (float) mc.displayWidth, 1.0F / (float) mc.displayHeight);
        blurShader.setUniformFloat("direction", dir1, dir2);
        blurShader.setUniformFloat("radius", radius);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(calculateGaussianValue(i, radius / 2));
        }

        weightBuffer.rewind();
        OpenGlHelper.glUniform1(blurShader.getUniformf("weights"), weightBuffer);
    }
    private static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static void triangle(final double x1, final double y1, final double x2, final double y2, final double x3, final double y3, final Color color) {
        start();

        color(color);
        render(GL_TRIANGLES, () -> {
            glVertex2d(x1, y1);
            glVertex2d(x2, y2);
            glVertex2d(x3, y3);
        });

        stop();
    }

    public static void themeRectangle(final double x, final double y, final double width, final double height, float seconds, float saturation, float brightness) {
        start();

        Theme theme = Dog.getInstance().getThemeManager().getCurrentTheme();

        render(GL_QUADS, () -> {
            for (double position = x; position <= x + width - 1; position += 1) {
                color(theme.isRainbow()
                        ? ColorUtil.getRainbow(seconds, saturation, brightness, (long) position)
                        : theme.getColor((int) seconds, (int) position));

                glVertex2d(position, y);
                glVertex2d(position + 1, y);
                glVertex2d(position + 1, y + height);
                glVertex2d(position, y + height);
            }
        });

        stop();
    }

    public static void horizontalGradient(final double x, final double y, final double width, final double height, final Color leftColor, final Color rightColor) {
        start();

        glShadeModel(GL_SMOOTH);
        render(GL_QUADS, () -> {
            color(leftColor);
            glVertex2d(x, y);
            glVertex2d(x, y + height);

            color(rightColor);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        });
        glShadeModel(GL_FLAT);

        stop();
    }

    public static void verticalGradient(final double x, final double y, final double width, final double height, final Color topColor, final Color bottomColor) {
        start();

        glShadeModel(GL_SMOOTH);
        render(GL_QUADS, () -> {
            color(topColor);
            glVertex2d(x, y);
            glVertex2d(x + width, y);

            color(bottomColor);
            glVertex2d(x + width, y + height);
            glVertex2d(x, y + height);
        });
        glShadeModel(GL_FLAT);

        stop();
    }


}