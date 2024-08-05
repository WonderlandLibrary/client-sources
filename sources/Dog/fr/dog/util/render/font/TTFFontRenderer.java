package fr.dog.util.render.font;

import fr.dog.Dog;
import fr.dog.module.impl.render.FurrySpeech;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class TTFFontRenderer {

    private static final Random RANDOM = new Random();

    private static final char FORMATTER = '§';
    private final int[] colorCodes = new int[32];

    private final Font font;
    private final CharacterData[] charData = new CharacterData[256];

    private final int margin;
    private final boolean antiAlias;
    private final boolean fractionalMetrics;

    public TTFFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        generateColors();
        this.font = font;
        this.margin = 6;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        generateTextures();
    }

    public void drawString(String text, float x, float y, int color) {
        renderString(text, x, y, color, false);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        glTranslated(0.5, 0.5, 0);
        renderString(text, x, y, color, true);
        glTranslated(-0.5, -0.5, 0);
        renderString(text, x, y, color, false);
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        glTranslated(0.5, 0.5, 0);
        renderString(text, x - this.getWidth(text)/2, y, color, true);
        glTranslated(-0.5, -0.5, 0);
        renderString(text, x - this.getWidth(text)/2, y, color, false);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        renderString(text, x - this.getWidth(text)/2, y, color, false);
    }

    public float getWidth(String text) {
        if (text == null || text.isEmpty())
            return 0;

        if (Dog.getInstance().getModuleManager() != null)
            if (Dog.getInstance().getModuleManager().getModule(FurrySpeech.class).isEnabled()) {
                text = text
                        .replaceAll("O", "OwO")
                        .replaceAll("o", "owo")
                        .replaceAll("U", "UwU")
                        .replaceAll("u", "uwu")
                        .replaceAll("([srlv])", "w")
                        .replaceAll("([SRLV])", "W");

                if (text.length() % 7 == 0)
                    text += " :3";
            }

        float width = 0;
        CharacterData[] characterData = charData;
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);

            if (character == FORMATTER || (i > 0 ? text.charAt(i - 1) : '.') == FORMATTER || !isValid(character))
                continue;

            CharacterData charData = characterData[character];

            width += (charData.width - (2 * margin)) / 2;
        }

        return width;
    }

    public float getHeight(String text) {
        float height = 0;

        if (text == null || text.isEmpty())
            return 0;

        if (Dog.getInstance().getModuleManager() != null)
            if (Dog.getInstance().getModuleManager().getModule(FurrySpeech.class).isEnabled()) {
                text = text
                        .replaceAll("O", "OwO")
                        .replaceAll("o", "owo")
                        .replaceAll("U", "UwU")
                        .replaceAll("u", "uwu")
                        .replaceAll("([srlv])", "w")
                        .replaceAll("([SRLV])", "W");

                if (text.length() % 7 == 0)
                    text += " :3";
            }

        CharacterData[] characterData = charData;

        int length = text.length();

        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);
            if ((i > 0 ? text.charAt(i - 1) : '.') == FORMATTER || character == FORMATTER
                    || !isValid(character))
                continue;

            CharacterData charData = characterData[character];
            height = Math.max(height, charData.height);
        }

        return (height - margin) / 2;
    }

    public void generateTextures() {
        for (int i = 0; i < 256; i++) {
            char c = (char) i;

            if (isValid(c))
                setup(c);
        }
    }

    //gen textures for each character possible
    private void setup(char character) {
        BufferedImage utilityImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();
        utilityGraphics.setFont(font);

        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
        Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
        BufferedImage characterImage = new BufferedImage(
                (int) StrictMath.ceil(characterBounds.getWidth() + (2 * margin)),
                (int) StrictMath.ceil(characterBounds.getHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) characterImage.getGraphics();
        graphics.setFont(font);

        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
        graphics.setColor(Color.WHITE);

        if (antiAlias)
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (fractionalMetrics)
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        graphics.drawString(String.valueOf(character), margin, fontMetrics.getAscent());

        int textureId = glGenTextures();
        createTexture(textureId, characterImage);

        charData[character] = new CharacterData(characterImage.getWidth(), characterImage.getHeight(), textureId);
    }

    private void createTexture(int textureId, BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }

    private void renderString(String text, float x, float y, int color, boolean shadow) {
        if (text == null || text.isEmpty())
            return;

        if (Dog.getInstance().getModuleManager() != null)
            if (Dog.getInstance().getModuleManager().getModule(FurrySpeech.class).isEnabled()) {
                text = text
                        .replaceAll("O", "OwO")
                        .replaceAll("o", "owo")
                        .replaceAll("U", "UwU")
                        .replaceAll("u", "uwu")
                        .replaceAll("([srlv])", "w")
                        .replaceAll("([SRLV])", "W");

                if (text.length() % 7 == 0)
                    text += " :3";
            }

        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();

        glScaled(0.5, 0.5, 1.0);

        x -= margin / 2f;
        y -= 2;

        x *= 2;
        y *= 2;

        CharacterData[] characterData = charData;

        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;

        int length = text.length();

        float multiplier = (shadow ? 4 : 1);

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glColor4f(r / multiplier, g / multiplier, b / multiplier, a);

        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);
            char previous = i > 0 ? text.charAt(i - 1) : '.';
            if (previous == FORMATTER)
                continue;

            if (character == FORMATTER) {
                int index = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                if (index < 16) {
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;
                    if (index < 0)
                        index = 15;
                    if (shadow)
                        index += 16;
                    int textColor = this.colorCodes[index];
                    glColor4f((textColor >> 16) / 255.0F, (textColor >> 8 & 255) / 255.0F, (textColor & 255) / 255.0F, a);
                } else if (index == 16)
                    obfuscated = true;
                else if (index == 18)
                    strikethrough = true;
                else if (index == 19)
                    underlined = true;
                else {
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;

                    glColor4f(1 / multiplier, 1 / multiplier, 1 / multiplier, a);
                }
            } else {
                if (!isValid(character))
                    continue;

                if (obfuscated)
                    character += (char) RANDOM.nextInt(Math.max(0, 256 - character));

                final CharacterData charData = characterData[character];
                drawChar(charData, x, y);

                if (strikethrough)
                    drawLine(0, charData.height / 2f, charData.width, charData.height / 2f, 3);
                if (underlined)
                    drawLine(0, charData.height - 15, charData.width, charData.height - 15, 3);
                x += charData.width - (2 * margin);
            }
        }

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        glColor4f(1, 1,1,1);
    }

    private boolean isValid(char c) {
        return c > 10 && c < 256 && c != 127;
    }

    public void drawChar(CharacterData characterData, float x, float y) {
        characterData.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2d(x, y);
            glTexCoord2f(0, 1);
            glVertex2d(x, y + characterData.height);
            glTexCoord2f(1, 1);
            glVertex2d(x + characterData.width, y + characterData.height);
            glTexCoord2f(1, 0);
            glVertex2d(x + characterData.width, y);
        }
        glEnd();
        GlStateManager.bindTexture(0);
    }

    @SuppressWarnings("SameParameterValue")
    private void drawLine(float x, float y, float x2, float y2, float width) {
        glDisable(GL_TEXTURE_2D);
        glLineWidth(width);
        glBegin(GL_LINES);
        {
            glVertex2f(x, y);
            glVertex2f(x2, y2);
        }
        glEnd();
        glEnable(GL_TEXTURE_2D);
    }

    private void generateColors() {
        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index & 0x1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCodes[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }

    public static class CharacterData {
        private final int textureId;
        public float width;
        public float height;

        private CharacterData(float width, float height, int textureId) {
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GlStateManager.bindTexture(textureId);
        }
    }
}
