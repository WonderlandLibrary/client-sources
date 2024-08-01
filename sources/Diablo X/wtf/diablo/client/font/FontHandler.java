package wtf.diablo.client.font;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class FontHandler {

    private static final Map<String, TTFFontRenderer> fonts = new HashMap<>();
    
    public static TTFFontRenderer fetch(String key) {
        if(fonts.get(key) == null) {
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<>();
            int size = Integer.parseInt(key.split(" ")[1]);
            String name = key.split(" ")[0];
            InputStream inputStream = FontHandler.class.getResourceAsStream("/assets/minecraft/diablo/font/" + name + ".ttf");

            assert inputStream != null;
            Font font = null;
            try {
                font = Font.createFont(Font.PLAIN, inputStream);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            assert font != null;
            font = font.deriveFont(Font.PLAIN, size);

            fonts.put(name + " " + size, new TTFFontRenderer(executorService, textureQueue, font));

            executorService.shutdown();

            while (!executorService.isTerminated()) {
                while (!textureQueue.isEmpty()) {
                    TextureData textureData = textureQueue.poll();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureData.getTextureId());

                    // Sets the texture parameter stuff.
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                    // Uploads the texture to opengl.
                    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
                }
            }
        }

        return fonts.get(key);
    }

}