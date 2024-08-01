package wtf.diablo.client.font;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public final class TTFFontRenderer {

    private final boolean antiAlias;

    /**
     * The font to be drawn.
     */
    private final Font font;

    private final float[] charWidth = new float[256];
    private final byte[] glyphWidth = new byte[65536];
    boolean unicodeFlag;


    /**
     * If fractional metrics should be used in the font renderer.
     */
    private final boolean fractionalMetrics;

    /**
     * All the character data information (regular).
     */
    private CharacterData[] regularData;

    /**
     * All the character data information (bold).
     */
    private CharacterData[] boldData;

    /**
     * All the character data information (italics).
     */
    private CharacterData[] italicsData;

    /**
     * All the color codes used in minecraft.
     */
    private final int[] colorCodes = new int[32];

    /**
     * The margin on each texture.
     */
    private static final int MARGIN = 4;

    /**
     * The character that invokes color in a string when rendered.
     */
    private static final char COLOR_INVOKER = '\247';

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font) {
        this(executorService, textureQueue, font, 256);
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount) {
        this(executorService, textureQueue, font, characterCount, true);
    }

    public TTFFontRenderer(final ExecutorService executorService, final ConcurrentLinkedQueue<TextureData> textureQueue, final Font font, final int characterCount, final boolean antiAlias) {
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        int[] regularTexturesIds = new int[characterCount];
        int[] boldTexturesIds = new int[characterCount];
        int[] italicTexturesIds = new int[characterCount];
        for (int i = 0; i < characterCount; i++) {
            regularTexturesIds[i] = GL11.glGenTextures();
            boldTexturesIds[i] = GL11.glGenTextures();
            italicTexturesIds[i] = GL11.glGenTextures();
        }

        executorService.execute(() -> {
            this.regularData = setup(new CharacterData[characterCount], regularTexturesIds, textureQueue, Font.PLAIN);
            this.boldData = setup(new CharacterData[characterCount], boldTexturesIds, textureQueue, Font.BOLD);
            this.italicsData = setup(new CharacterData[characterCount], italicTexturesIds, textureQueue, Font.ITALIC);
        });
    }

    /**
     * Sets up the character data and textures.
     *
     * @param characterData The array of character data that should be filled.
     * @param type          The font type. (Regular, Bold, and Italics)
     */
    private CharacterData[] setup(final CharacterData[] characterData, final int[] texturesIds, final ConcurrentLinkedQueue<TextureData> textureQueue, final int type) {
        // Quickly generates the colors.
        generateColors();

        // Changes the type of the font to the given type.
        Font font = this.font.deriveFont(type);

        // An image just to get font data.
        BufferedImage utilityImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // The graphics of the utility image.
        Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();

        // Sets the font of the utility image to the font.
        utilityGraphics.setFont(font);

        // The font metrics of the utility image.
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();

        // Iterates through all the characters in the character set of the font renderer.
        for (int index = 0; index < characterData.length; index++) {
            // The character at the current index.
            char character = (char) index;

            // The width and height of the character according to the font.
            Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);

            // The width of the character texture.
            float width = (float) characterBounds.getWidth() + (2 * MARGIN);

            // The height of the character texture.
            float height = (float) characterBounds.getHeight();

            // The image that the character will be rendered to.
            BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width), MathHelper.ceiling_double_int(height), BufferedImage.TYPE_INT_ARGB);

            // The graphics of the character image.
            Graphics2D graphics = (Graphics2D) characterImage.getGraphics();

            // Sets the font to the input font/
            graphics.setFont(font);

            // Sets the color to white with no alpha.
            graphics.setColor(new Color(255, 255, 255, 0));

            // Fills the entire image with the color above, makes it transparent.
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());

            // Sets the color to white to draw the character.
            graphics.setColor(Color.WHITE);

            // Enables anti-aliasing so the font doesn't have aliasing.
            if (antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }
            // Draws the character.
            graphics.drawString(character + "", MARGIN, fontMetrics.getAscent());

            // Generates a new texture id.
            int textureId = texturesIds[index];

            // Allocates the texture in opengl.
            createTexture(textureId, characterImage, textureQueue);

            // Initiates the character data and stores it in the data array.
            characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(), textureId);
        }

        // Returns the filled character data array.
        return characterData;
    }

    /**
     * Uploads the opengl texture.
     *
     * @param textureId The texture id to upload to.
     * @param image     The image to upload.
     */
    private void createTexture(final int textureId, final BufferedImage image, final ConcurrentLinkedQueue<TextureData> textureQueue) {
        // Array of all the colors in the image.
        int[] pixels = new int[image.getWidth() * image.getHeight()];

        // Fetches all the colors in the image.
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        // Buffer that will store the texture data.
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

        // Puts all the pixel data into the buffer.
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                // The pixel in the image.
                int pixel = pixels[y * image.getWidth() + x];

                // Puts the data into the byte buffer.
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        // Flips the byte buffer, not sure why this is needed.
        buffer.flip();

        textureQueue.add(new TextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }

    /**
     * Renders the given string.
     *
     * @param text  The text to be rendered.
     * @param x     The x position of the text.
     * @param y     The y position of the text.
     * @param color The color of the text.
     */
    public int drawString(final String text, final double x, final double y, final int color) {
        return renderString(text, x, y, color, -1, -1, false);
    }

    public void drawCenteredString(final String text, final double x, final double y, final int color)
    {
        float width = getWidth(text) / 2;
        renderString(text, x - width, y, color, -1, -1, false);
    }

    public void drawCenteredStringWithShadow(final String text, final double x, final double y, final int color)
    {
        float width = getWidth(text) / 2;
        renderString(text, x - width, y, color, -1, -1, true);
    }

    /**
     * Renders the given string.
     *
     * @param text  The text to be rendered.
     * @param x     The x position of the text.
     * @param y     The y position of the text.
     * @param color The color of the text.
     */
    public void drawStringWithShadow(final String text, final double x, final double y, final int color)
    {
        GL11.glTranslated(0.5, 0.5, 0);
        renderString(text, x, y, new Color(0, 0, 0, 60).getRGB(), -1, -1, true);
        GL11.glTranslated(-0.5, -0.5, 0);
        renderString(text, x, y, color, -1, -1, false);
    }

    public void drawStringWithShadow(final String text, final double x, final double y, final int color, final boolean dropShadow)
    {
        GL11.glTranslated(0.5, 0.5, 0);
        if (dropShadow) renderString(text, x, y, new Color(0, 0, 0, 60).getRGB(), -1, -1, true);
        GL11.glTranslated(-0.5, -0.5, 0);
        renderString(text, x, y, color, -1, -1, false);
    }


    public void drawStringWithShadow(final String text, final float x, final float y, final float offset, final int color) {
        GL11.glTranslated(offset, offset, 0);
        renderString(text, x, y, new Color(0, 0, 0, 100).getRGB(), -1, -1, true);
        GL11.glTranslated(-offset, -offset, 0);
        renderString(text, x, y, color, -1, -1, false);
    }

    public void drawStringWithOutline(String text, double x, double y, int color, int outlineColor, int outlineThickness) {
        renderString(text, x, y, color, outlineColor, outlineThickness, false);
    }

    /**
     * Renders the given string.
     *
     * @param text   The text to be rendered.
     * @param x      The x position of the text.
     * @param y      The y position of the text.
     * @param shadow If the text should be rendered with the shadow color.
     * @param color  The color of the text.
     */
    public int renderString(final String text, double x, double y, final int color, final int outlineColor, final int outlineThickness, final boolean shadow) {
        // Returns if the text is empty.
        if (text.length() == 0) return 0;
        float alpha = (float) (color >> 24 & 255) / 255.0f;

        // Pushes the matrix to store gl values.
        GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.scale(0.5, 0.5, 1);

        GlStateManager.color((float) (color >> 16 & 255) / 255.0f, (float) (color >> 8 & 255) / 255.0f,
                (float) (color & 255) / 255.0f, alpha);
        GlStateManager.enableTexture2D();

        // Scales down to make the font look better.

        // Removes half the margin to render in the right spot.
        x -= MARGIN / 2f;
        y -= MARGIN / 2f;

        // Adds 1 to x and y.
        x += 1;
        y += 1;

        // Doubles the position because of the scaling.
        x *= 2;
        y *= 2;

        // The character texture set to be used. (Regular by default)
        CharacterData[] characterData = regularData;

        // Booleans to handle the style.
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;

        // The length of the text used for the draw loop.
        int length = text.length();


        // Sets the color.

        // Loops through the text.
        for (int i = 0; i < length; i++) {
            // The character at the index of 'i'.
            char character = text.charAt(i);

            // The previous character.
            char previous = i > 0 ? text.charAt(i - 1) : '.';

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue;

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER) {

                // The color index of the character after the current character.
                int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                // If the color character index is of the normal color invoking characters.
                if (index < 16) {
                    // Resets all the styles.
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;

                    // Sets the character data to the regular type.
                    characterData = regularData;

                    // Clamps the index just to be safe in case an odd character somehow gets in here.
                    if (index < 0) index = 15;

                    // Adds 16 to the color index to get the darker shadow color.
                    if (shadow) index += 16;

                    // Gets the text color from the color codes array.
                    int textColor = this.colorCodes[index];

                    // Sets the current color.
                    GL11.glColor4d((textColor >> 16) / 255d, (textColor >> 8 & 255) / 255d, (textColor & 255) / 255d, (color >> 24 & 0xFF) / 255d);
                } else if (index == 16)
                    obfuscated = true;
                else if (index == 17)
                    // Sets the character data to the bold type.
                    characterData = boldData;
                else if (index == 18)
                    strikethrough = true;
                else if (index == 19)
                    underlined = true;
                else if (index == 20)
                    // Sets the character data to the italics type.
                    characterData = italicsData;
                else {
                    // Resets the style.
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;

                    // Sets the character data to the regular type.
                    characterData = regularData;

                    // Sets the color to white
                    GlStateManager.resetColor();
                    GL11.glColor4d(1 * (shadow ? 0.25 : 1), 1 * (shadow ? 0.25 : 1), 1 * (shadow ? 0.25 : 1), (color >> 24 & 0xFF) / 255d);

                }
            } else {
                // Continues to not crash!
                if (character > 255) continue;

                // Sets the character to a random char if obfuscated is enabled.
                int RANDOM_OFFSET = 1;

                if (obfuscated)
                    character = (char) (((int) character) + RANDOM_OFFSET);

                // Draws the character.
                if (outlineThickness > 0)
                    drawOutlineChar(character, characterData, x, y, outlineThickness, outlineColor, color);
                else
                    drawChar(character, characterData, x, y);

                // The character data for the given character.
                final CharacterData charData = characterData[character];

                // Draws the strikethrough line if enabled.
                if (strikethrough)
                    drawLine(new Vector2f(0, charData.height / 2f), new Vector2f(charData.width, charData.height / 2f));

                // Draws the underline if enabled.
                if (underlined)
                    drawLine(new Vector2f(0, charData.height - 15), new Vector2f(charData.width, charData.height - 15));

                // Adds to the offset.
                x += charData.width - (MARGIN * 2);
            }
        }
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
        GlStateManager.enableTexture2D();
        GL11.glPopMatrix();
        return (int) x;
    }

    /**
     * Gets the width of the given text.
     *
     * @param text The text to get the width of.
     * @return The width of the given text.
     */
    public float getWidth(final String text) {

        // The width of the string.
        float width = 0;

        // The character texture set to be used. (Regular by default)
        CharacterData[] characterData = regularData;

        // The length of the text.
        int length = text.length();

        // Loops through the text.
        for (int i = 0; i < length; i++) {
            // The character at the index of 'i'.
            char character = text.charAt(i);

            // The previous character.
            char previous = i > 0 ? text.charAt(i - 1) : '.';

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue;

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER) {

                // The color index of the character after the current character.
                int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                if (index == 17)
                    // Sets the character data to the bold type.
                    characterData = boldData;
                else if (index == 20)
                    // Sets the character data to the italics type.
                    characterData = italicsData;
                else if (index == 21)
                    // Sets the character data to the regular type.
                    characterData = regularData;
            } else {
                // Continues to not crash!
                if (character > 255) continue;

                // The character data for the given character.
                CharacterData charData = characterData[character];

                // Adds to the offset.
                width += (charData.width - (2 * MARGIN)) / 2;
            }
        }

        // Returns the width.
        return width + MARGIN / 2f;
    }

    /**
     * Gets the height of the given text.
     *
     * @param text The text to get the height of.
     * @return The height of the given text.
     */
    public float getHeight(final String text) {

        // The height of the string.
        float height = 0;

        // The character texture set to be used. (Regular by default)
        CharacterData[] characterData = regularData;

        // The length of the text.
        int length = text.length();

        // Loops through the text.
        for (int i = 0; i < length; i++) {
            // The character at the index of 'i'.
            char character = text.charAt(i);

            // The previous character.
            char previous = i > 0 ? text.charAt(i - 1) : '.';

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue;

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER) {

                // The color index of the character after the current character.
                int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

                if (index == 17)
                    // Sets the character data to the bold type.
                    characterData = boldData;
                else if (index == 20)
                    // Sets the character data to the italics type.
                    characterData = italicsData;
                else if (index == 21)
                    // Sets the character data to the regular type.
                    characterData = regularData;
            } else {
                // Continues to not crash!
                if (character > 255) continue;

                // The character data for the given character.
                final CharacterData charData = characterData[character];

                // Sets the height if its bigger.
                height = Math.max(height, charData.height);
            }
        }

        // Returns the height.
        return height / 2 - MARGIN / 2f;
    }

    /**
     * Draws the character.
     *
     * @param character     The character to be drawn.
     * @param characterData The character texture set to be used.
     */
    private void drawChar(final char character, final CharacterData[] characterData, final double x, final double y) {
        // The char data that stores the character data.
        CharacterData charData = characterData[character];

        // Binds the character data texture.
        charData.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        // Begins drawing the quad.
        GL11.glBegin(GL11.GL_QUADS);
        {
            // Maps out where the texture should be drawn.
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2d(x, y);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2d(x, y + charData.height);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2d(x + charData.width, y + charData.height);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2d(x + charData.width, y);
        }
        // Ends the quad.
        GL11.glEnd();
    }

    private void drawOutlineChar(final char character, final CharacterData[] characterData, final double x, final double y, final int width, final int outlineColor, final int color) {
        CharacterData charData = characterData[character];

        charData.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glColor4ub((byte) ((outlineColor >> 16) & 0xFF), (byte) ((outlineColor >> 8) & 0xFF), (byte) (outlineColor & 0xFF), (byte) ((outlineColor >> 24) & 0xFF));
            GL11.glVertex2d(x - width, y - width);

            GL11.glTexCoord2f(0, 1);
            GL11.glColor4ub((byte) ((outlineColor >> 16) & 0xFF), (byte) ((outlineColor >> 8) & 0xFF), (byte) (outlineColor & 0xFF), (byte) ((outlineColor >> 24) & 0xFF));
            GL11.glVertex2d(x - width, y + charData.height + width);

            GL11.glTexCoord2f(1, 1);
            GL11.glColor4ub((byte) ((outlineColor >> 16) & 0xFF), (byte) ((outlineColor >> 8) & 0xFF), (byte) (outlineColor & 0xFF), (byte) ((outlineColor >> 24) & 0xFF));
            GL11.glVertex2d(x + charData.width + width, y + charData.height + width);

            GL11.glTexCoord2f(1, 0);
            GL11.glColor4ub((byte) ((outlineColor >> 16) & 0xFF), (byte) ((outlineColor >> 8) & 0xFF), (byte) (outlineColor & 0xFF), (byte) ((outlineColor >> 24) & 0xFF));
            GL11.glVertex2d(x + charData.width + width, y - width);

            GL11.glTexCoord2f(0, 0);
            GL11.glColor4ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) (color & 0xFF), (byte) ((color >> 24) & 0xFF));
            GL11.glVertex2d(x, y);

            GL11.glTexCoord2f(0, 1);
            GL11.glColor4ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) (color & 0xFF), (byte) ((color >> 24) & 0xFF));
            GL11.glVertex2d(x, y + charData.height);

            GL11.glTexCoord2f(1, 1);
            GL11.glColor4ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) (color & 0xFF), (byte) ((color >> 24) & 0xFF));
            GL11.glVertex2d(x + charData.width, y + charData.height);

            GL11.glTexCoord2f(1, 0);
            GL11.glColor4ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) (color & 0xFF), (byte) ((color >> 24) & 0xFF));
            GL11.glVertex2d(x + charData.width, y);
        }
        // Ends the quad.
        GL11.glEnd();
    }

    /**
     * Draws a line from start to end with the given width.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    private void drawLine(final Vector2f start, final Vector2f end) {
        // Disable textures so we can draw a solid line.
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        // Sets the width.
        GL11.glLineWidth((float) 3);

        // Begins drawing the line.
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex2f(start.x, start.y);
            GL11.glVertex2f(end.x, end.y);
        }
        // Ends drawing the line.
        GL11.glEnd();

        // Enables texturing back on.
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Generates all the colors.
     */
    private void generateColors() {
        // Iterates through 32 colors.
        for (int i = 0; i < 32; i++) {
            // Not sure what this variable is.
            int thingy = (i >> 3 & 1) * 85;

            // The red value of the color.
            int red = (i >> 2 & 1) * 170 + thingy;

            // The green value of the color.
            int green = (i >> 1 & 1) * 170 + thingy;

            // The blue value of the color.
            int blue = (i & 1) * 170 + thingy;

            // Increments the red by 85, not sure why does this in minecraft's font renderer.
            if (i == 6) red += 85;

            // Used to make the shadow darker.
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            // Sets the color in the color code at the index of 'i'.
            this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }
    }

    /**
     * Trims a string to fit a specified Width.
     */
    public String trimStringToWidth(final String text, final int width)
    {
        return this.trimStringToWidth(text, width, false);
    }

    /**
     * Trims a string to a specified width, and will reverse it if par3 is set.
     */
    public String trimStringToWidth(final String text, final int width, final boolean reverse)
    {
        final StringBuilder stringbuilder = new StringBuilder();
        float f = 0.0F;
        final int i = reverse ? text.length() - 1 : 0;
        final int j = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;

        for (int k = i; k >= 0 && k < text.length() && f < (float)width; k += j)
        {
            char c0 = text.charAt(k);
            float f1 = this.getCharWidthFloat(c0);

            if (flag)
            {
                flag = false;

                if (c0 != 108 && c0 != 76)
                {
                    if (c0 == 114 || c0 == 82)
                    {
                        flag1 = false;
                    }
                }
                else
                {
                    flag1 = true;
                }
            }
            else if (f1 < 0.0F)
            {
                flag = true;
            }
            else
            {
                f += f1;

                if (flag1)
                {
                    ++f;
                }
            }

            if (f > (float)width)
            {
                break;
            }

            if (reverse)
            {
                stringbuilder.insert(0, c0);
            }
            else
            {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

    private float getCharWidthFloat(final char p_getCharWidthFloat_1_)
    {
        if (p_getCharWidthFloat_1_ == 167)
        {
            return -1.0F;
        }
        else if (p_getCharWidthFloat_1_ == 32)
        {
            return this.charWidth[32];
        }
        else
        {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_getCharWidthFloat_1_);

            if (p_getCharWidthFloat_1_ > 0 && i != -1 && !this.unicodeFlag)
            {
                return this.charWidth[i];
            }
            else if (this.glyphWidth[p_getCharWidthFloat_1_] != 0)
            {
                int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
                int k = this.glyphWidth[p_getCharWidthFloat_1_] & 15;
                j = j & 15;
                ++k;
                return (float)((k - j) / 2 + 1);
            }
            else
            {
                return 0.0F;
            }
        }
    }

    /**
     * Class that holds the data for each character.
     */
    static class CharacterData {

        /**
         * The character the data belongs to.
         */
        public char character;

        /**
         * The width of the character.
         */
        public float width;

        /**
         * The height of the character.
         */
        public float height;

        /**
         * The id of the character texture.
         */
        private final int textureId;

        public CharacterData(final char character, final float width, final float height, final int textureId)
        {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        /**
         * Binds the texture.
         */
        public void bind() {
            // Binds the opengl texture by the texture id.
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        }

    }

}

