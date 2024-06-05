/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.font;

import cc.diablo.font.TTFFontRenderer;
import cc.diablo.font.TTFRenderer;
import cc.diablo.font.TextureData;
import java.awt.Font;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FontManager {
    public TTFRenderer hud = null;
    private final ResourceLocation darrow = new ResourceLocation("SF-UI-Display-Regular.otf");
    private final FontManager instance;
    private final TTFFontRenderer defaultFont;
    private final HashMap<String, TTFFontRenderer> fonts = new HashMap();

    public FontManager() {
        this.instance = this;
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(8);
        ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<TextureData>();
        this.defaultFont = new TTFFontRenderer(executorService, textureQueue, new Font("Verdana", 0, 18));
        try {
            Font myFont;
            InputStream istream;
            for (int i : new int[]{16, 36, 20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Raleway-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("raleway-1 " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/SigmaThin.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("JELLO1 " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 18, 20, 28, 30, 32}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Applefnt.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("clean " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/arial.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("arial " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Lato-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("lato " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20, 22, 24, 26, 28, 30, 32, 34, 38, 40, 42, 44, 46, 48}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/SF-UI-Display-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("sfui " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20, 22, 24, 26, 28, 30, 32, 34, 38, 40, 42, 44, 46, 48}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/skeetIcons.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("skeeticons " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16, 36, 20, 22, 24, 26, 28, 30, 32, 34, 38, 40, 42, 44, 46, 48}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/guiicons.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("guicons " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{12, 14, 20, 22, 28, 30, 42}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Roboto-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("ROBO " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Comfortaa-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("comfort " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{20, 26}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Karla-Bold.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("karla-bold " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Karla-Regular.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("karla " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{20}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Gilroy-Light.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("gilroy " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{38}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/Icon-Font.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("icon " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{14, 38}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/config.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("config " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/notifications.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("noto " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{16}) {
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Client/fonts/ps-bold.ttf");
                myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(0, i);
                this.fonts.put("bold " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                TextureData textureData = textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());
                GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
                GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)textureData.getWidth(), (int)textureData.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)textureData.getBuffer());
            }
        }
    }

    public FontManager getInstance() {
        return this.instance;
    }

    public TTFFontRenderer getFont(String key) {
        return this.fonts.getOrDefault(key, this.defaultFont);
    }

    public void setup() {
        this.hud = new TTFRenderer("Arial", 0, 18);
    }

    public TTFFontRenderer getDefaultFont() {
        return this.defaultFont;
    }
}

