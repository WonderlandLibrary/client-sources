package lol.point.returnclient.util.render;

import lol.point.returnclient.util.system.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FastFontRenderer {

    private final boolean antiAlias;
    private final Font font;
    private final boolean fractionalMetrics;
    private CharacterData[] regularData;
    private CharacterData[] boldData;
    private CharacterData[] italicsData;
    private final int[] colorCodes;

    public FastFontRenderer(ConcurrentLinkedQueue<FastFontTextureData> textureQueue, Font font) {
        this(textureQueue, font, 256);
    }

    public FastFontRenderer(ConcurrentLinkedQueue<FastFontTextureData> textureQueue, Font font, int characterCount) {
        this(textureQueue, font, characterCount, true);
    }

    public FastFontRenderer(ConcurrentLinkedQueue<FastFontTextureData> textureQueue, Font font, boolean antiAlias) {
        this(textureQueue, font, 256, antiAlias);
    }

    public FastFontRenderer(ConcurrentLinkedQueue<FastFontTextureData> textureQueue, Font font, int characterCount, boolean antiAlias) {
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        int[] regularTexturesIds = new int[characterCount];
        int[] boldTexturesIds = new int[characterCount];
        int[] italicTexturesIds = new int[characterCount];
        for (int i = 0; i < characterCount; ++i) {
            regularTexturesIds[i] = GL11.glGenTextures();
            boldTexturesIds[i] = GL11.glGenTextures();
            italicTexturesIds[i] = GL11.glGenTextures();
        }
        this.regularData = this.setup(new CharacterData[characterCount], regularTexturesIds, textureQueue, 0);
        this.boldData = this.setup(new CharacterData[characterCount], boldTexturesIds, textureQueue, 1);
        this.italicsData = this.setup(new CharacterData[characterCount], italicTexturesIds, textureQueue, 2);
    }

    private CharacterData[] setup(CharacterData[] characterData, int[] texturesIds, ConcurrentLinkedQueue<FastFontTextureData> textureQueue, int type) {
        this.generateColors();
        Font font = this.font.deriveFont(type);
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();
        utilityGraphics.setFont(font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();


        for (int index = 0; index < characterData.length; ++index) {
            char character = (char) index;
            Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
            float width = (float) characterBounds.getWidth() + 8.0f;
            float height = (float) characterBounds.getHeight();
            BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width), MathHelper.ceiling_double_int(height), 2);
            Graphics2D graphics = (Graphics2D) characterImage.getGraphics();
            graphics.setFont(font);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
            graphics.setColor(Color.WHITE);
            if (this.antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }
            graphics.drawString(String.valueOf(character), 4, fontMetrics.getAscent());
            int textureId = texturesIds[index];
            this.createTexture(textureId, characterImage, textureQueue);
            characterData[index] = new CharacterData(character, (float) characterImage.getWidth(), (float) characterImage.getHeight(), textureId);
        }
        return characterData;
    }

    private void createTexture(int textureId, BufferedImage image, ConcurrentLinkedQueue<FastFontTextureData> textureQueue) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }
        }
        buffer.flip();
        textureQueue.add(new FastFontTextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }


    public void drawString(String text, float x, float y, Color color, boolean shadow) {
        this.drawString(text, x, y, color.getRGB(), shadow);
    }

    public void drawString(String text, float x, float y, int color, boolean shadow) {
        if (shadow) {
            this.drawStringWithShadow(text, x, y, color);
        } else {
            this.drawString(text, x, y, color);
        }
    }

    public void drawString(String text, float x, float y, int color) {
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        this.renderString(text, x, y, color, false);
    }

    public void drawString(String text, float x, float y, Color color) {
        this.drawString(text, x, y, color.getRGB());
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        float width = this.getWidth(text) / 2.0f;
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        this.renderString(text, x - width, y, color, false);
    }

    public void drawCenteredString(String text, float x, float y, Color color) {
        this.drawCenteredString(text, x, y, color.getRGB());
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GL11.glTranslated(0.5, 0.5, 0.0);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated(-0.5, -0.5, 0.0);
        this.renderString(text, x, y, color, false);
    }

    public void drawStringWithShadow(String text, float x, float y, Color color) {
        this.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public void drawStringWithFade(String text, float x, float y, int color1, int color2) {
        this.drawStringWithFade(text, x, y, new Color(color1), new Color(color2));
    }

    public void drawStringWithFade(String text, float x, float y, Color color1, Color color2) {
        float charX = x;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            double index = (double) i / text.length();
            int charColor = ColorUtil.fade(color1, color2, index, 0.4);
            this.drawString(String.valueOf(c), charX, y, charColor);
            charX += this.getWidth(String.valueOf(c)) - 2.0f;
        }
    }

    private void renderString(String text, float x, float y, int color, boolean shadow) {
        if (text.isEmpty()) {
            return;
        }

        x = Math.round(x * 10.0F) / 10.0F;
        y = Math.round(y * 10.0F) / 10.0F;

        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        x -= 2.0f;
        y -= 2.0f;
        x += 0.5f;
        y += 0.5f;
        x *= 2.0f;
        y *= 2.0f;

        CharacterData[] characterData = this.regularData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        int length = text.length();
        double multiplier = 255.0 * (shadow ? 4 : 1);
        Color c = new Color(color);
        GL11.glColor4d(c.getRed() / multiplier, c.getGreen() / multiplier, c.getBlue() / multiplier, (color >> 24 & 0xFF) / 255.0);
        for (int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            char previous = (i > 0) ? text.charAt(i - 1) : '.';
            if (previous != '§') {
                if (character == '§') {
                    try {
                        int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                        if (index < 16) {
                            obfuscated = false;
                            strikethrough = false;
                            underlined = false;
                            characterData = this.regularData;
                            if (index < 0) {
                                index = 15;
                            }
                            if (shadow) {
                                index += 16;
                            }
                            int textColor = this.colorCodes[index];
                            GL11.glColor4d((textColor >> 16) / 255.0, (textColor >> 8 & 0xFF) / 255.0, (textColor & 0xFF) / 255.0, (color >> 24 & 0xFF) / 255.0);
                        } else if (index <= 20) {
                            switch (index) {
                                case 16:
                                    obfuscated = true;
                                    break;

                                case 17:
                                    characterData = this.boldData;
                                    break;

                                case 18:
                                    strikethrough = true;
                                    break;

                                case 19:
                                    underlined = true;
                                    break;

                                case 20:
                                    characterData = this.italicsData;
                                    break;
                            }
                        } else {
                            obfuscated = false;
                            strikethrough = false;
                            underlined = false;
                            characterData = this.regularData;
                            GL11.glColor4d((shadow ? 0.25 : 1.0), (shadow ? 0.25 : 1.0), (shadow ? 0.25 : 1.0), (color >> 24 & 0xFF) / 255.0);
                        }

                    } catch (StringIndexOutOfBoundsException ignored) {
                    }
                } else if (character <= 'ÿ') {
                    if (obfuscated) {
                        character += 1;
                    }
                    this.drawChar(character, characterData, x, y);
                    CharacterData charData = characterData[character];
                    if (strikethrough) {
                        this.drawLine(new Vector2f(0.0f, charData.height / 2.0f), new Vector2f(charData.width, charData.height / 2.0f));
                    }
                    if (underlined) {
                        this.drawLine(new Vector2f(0.0f, charData.height - 15.0f), new Vector2f(charData.width, charData.height - 15.0f));
                    }
                    x += charData.width - 8.0f;
                }
            }
        }

        GL11.glPopMatrix();
        GlStateManager.disableBlend();
        GlStateManager.bindTexture(0);
        GlStateManager.resetColor();
    }

    public float getWidth(String text) {
        float width = 0.0f;
        CharacterData[] characterData = this.regularData;

        // Remove color codes from the text
        String strippedText = StringUtils.stripControlCodes(text);

        try {
            for (int length = strippedText.length(), i = 0; i < length; ++i) {
                char character = strippedText.charAt(i);

                CharacterData charData = characterData[character];
                width += (charData.width - 8.0f) / 2.0f;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return getWidth("A");
        }

        return width + 2.0f;
    }

    public float getHeight(String text) {
        float height = 0.0f;
        CharacterData[] characterData = this.regularData;

        for (int length = text.length(), i = 0; i < length; ++i) {
            char character = text.charAt(i);
            CharacterData charData = characterData[character];
            height = Math.max(height, charData.height);
        }

        return height / 2.0f - 2.0f;
    }

    public float getHeight() {
        return getHeight("I");
    }

    private void drawChar(char character, CharacterData[] characterData, float x, float y) {
        if (character >= characterData.length) return;

        CharacterData charData = characterData[character];
        charData.bind();

        GL11.glBegin(6);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(x, y + charData.height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d(x + charData.width, y + charData.height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(x + charData.width, y);
        GL11.glEnd();
    }

    private void drawLine(Vector2f start, Vector2f end) {
        GL11.glDisable(3553);
        GL11.glLineWidth((float) 3.0);
        GL11.glBegin(1);
        GL11.glVertex2f(start.x, start.y);
        GL11.glVertex2f(end.x, end.y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void generateColors() {
        for (int i = 0; i < 32; ++i) {
            int thingy = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + thingy;
            int green = (i >> 1 & 0x1) * 170 + thingy;
            int blue = (i & 0x1) * 170 + thingy;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }

    static class CharacterData {
        public char character;
        public float width;
        public float height;
        private final int textureId;

        public CharacterData(char character, float width, float height, int textureId) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture(3553, this.textureId);
        }
    }

}
