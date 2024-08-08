package lol.point.returnclient.managers;

import lol.point.Return;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.FastFontTextureData;
import lol.point.returnclient.util.system.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.opengl.GL11.*;

public class FontManager {

    public final Map<String, List<String>> availableFonts = new HashMap<>();

    private final HashMap<String, FastFontRenderer> fonts = new HashMap<>();
    private final FastFontRenderer defaultFont;
    private final ConcurrentLinkedQueue<FastFontTextureData> textureQueue;

    public FastFontRenderer getFont(final String key) {
        if (this.fonts.containsKey(key)) {
            return this.fonts.get(key);
        } else {
            try {
                final String[] split = key.split(" "),
                        nameSplit = split[0].split("-");

                final String family = nameSplit[0],
                        style = nameSplit[1];

                final int size = Integer.parseInt(split[1]);

                // Check both .ttf and .otf font files
                String[] extensions = new String[]{".ttf", ".otf"};
                boolean fontLoaded = false;
                for (String extension : extensions) {
                    final String path = String.format(Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return") + "/fonts/%s/%s-%s%s", family.toLowerCase(), family, style, extension);
                    fontLoaded = loadFont(path, family + "-" + style, new int[]{size});
                    if (fontLoaded) break; // Exit loop if font is loaded successfully
                }

                while (!textureQueue.isEmpty()) {
                    final FastFontTextureData textureData = textureQueue.poll();
                    GlStateManager.bindTexture(textureData.textureId());
                    GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                    GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                    GL11.glTexImage2D(GL_TEXTURE_2D, GL_ZERO, GL_RGBA, textureData.width(), textureData.height(), GL_ZERO, GL_RGBA, GL_UNSIGNED_BYTE, textureData.buffer());
                }

                if (this.fonts.containsKey(key)) {
                    return this.fonts.get(key);
                } else {
                    Return.LOGGER.error("Failed to create font {}", key);
                    return defaultFont;
                }
            } catch (Exception e) {
                Return.LOGGER.error("Failed to create font {} due to {}", key, e);
                return defaultFont;
            }
        }
    }

    public FontManager() {
        this.textureQueue = new ConcurrentLinkedQueue<>();
        this.defaultFont = new FastFontRenderer(textureQueue, new Font("Roboto-Regular", Font.PLAIN, 16));
        Return.LOGGER.info("set the default font to: {}", defaultFont);

        try {
            Return.LOGGER.info("{}", FileUtil.getFilesInDirectory("return/fonts/"));
            for (String family : FileUtil.getFilesInDirectory("return/fonts/")) {
                final List<String> types = new LinkedList<>();

                for (String type : FileUtil.getFilesInDirectory("return/fonts/" + family + "/")) {
                    types.add(type.substring(family.length() + 1, type.length() - 4));
                }

                availableFonts.put(family, types);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Return.LOGGER.info("finished");
    }

    private boolean loadFont(String fontPath, String fontName, int[] sizes) {
        for (int size : sizes) {
            Path fontFile = Paths.get(fontPath);
            if (Files.exists(fontFile)) {
                try (InputStream iStream = Files.newInputStream(fontFile)) {
                    Font myFont = Font.createFont(Font.TRUETYPE_FONT, iStream);
                    myFont = myFont.deriveFont(Font.PLAIN, (float) size);
                    this.fonts.put(fontName + " " + size, new FastFontRenderer(textureQueue, myFont));
                    return true; // Font loaded successfully
                } catch (IOException | FontFormatException e) {
                    Return.LOGGER.error("Failed to load font: {}", fontPath, e);
                }
            } else {
                Return.LOGGER.info("Font file not found: {}", fontPath);
            }
        }
        return false; // Font not loaded
    }

}
