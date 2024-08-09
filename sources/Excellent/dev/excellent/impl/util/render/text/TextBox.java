package dev.excellent.impl.util.render.text;

import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.GlyphPage;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Data;
import net.minecraft.client.util.InputMappings;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;


@Data
public class TextBox implements IMinecraft, IMouse {
    public String text = "";
    public Vector2f position;
    public boolean selected;
    public int cursor;
    public double animatedCursorPosition;
    public Font font;
    public int color;
    private double lastBackSpace;
    private TextAlign textAlign;
    private double posX;
    private String emptyText;
    private float width;
    private boolean hideCharacters;

    public TextBox(final Vector2f position, final Font font, final int color, final TextAlign textAlign, final String emptyText, final float width, final boolean hideCharacters) {
        this.position = position;
        this.font = font;
        this.color = color;
        this.textAlign = textAlign;
        this.emptyText = emptyText;
        this.width = width;
        this.hideCharacters = hideCharacters;
    }

    public TextBox(final Vector2f position, final Font font, final int color, final TextAlign textAlign, final String emptyText, final float width) {
        this.position = position;
        this.font = font;
        this.color = color;
        this.textAlign = textAlign;
        this.emptyText = emptyText;
        this.width = width;
        this.hideCharacters = false;
    }

    public void draw(MatrixStack matrix) {
        cursor = Math.min(Math.max(cursor, 0), this.text.length());

        mc.keyboardListener.enableRepeatEvents(true);
        StringBuilder drawnString = new StringBuilder(this.text);

        if (this.hideCharacters && !this.isEmpty()) {
            StringBuilder string = new StringBuilder();
            string.append("*".repeat(Math.max(0, drawnString.length())));
            drawnString = new StringBuilder(string);
        }

        switch (this.textAlign) {
            case CENTER -> {
                float fontwidth = (this.font.getWidth(this.text.isEmpty() ? this.emptyText : drawnString.toString()) / 2F);
                posX = Interpolator.lerp(posX, (double) position.x - fontwidth, 0.5D);
            }
            case LEFT -> posX = position.x;
        }

        if (this.isEmpty()) {
            this.font.draw(matrix, this.emptyText, posX, position.y, ColorUtil.replAlpha(color, (int) (ColorUtil.alpha(color) * (this.selected ? 0.6F : 0.4F))));
        } else {
            this.font.draw(matrix, drawnString.toString(), posX, position.y, this.color);
        }

        cursor = Math.min(Math.max(cursor, 0), drawnString.length());

        final StringBuilder textBeforeCursor = new StringBuilder();
        for (int i = 0; i < this.cursor; ++i) {
            textBeforeCursor.append(drawnString.charAt(i));
        }

        float cursorOffset = this.font.getWidth(textBeforeCursor.toString());

        animatedCursorPosition = Interpolator.lerp(animatedCursorPosition, (double) cursorOffset, 0.5D);

        if (this.selected) {
            RectUtil.drawRect(matrix, (float) (posX + animatedCursorPosition), position.y, (float) (posX + animatedCursorPosition) + 0.5F, position.y + font.getHeight(), ColorUtil.replAlpha(color, (int) Mathf.clamp(0, 255, ((Math.sin(System.currentTimeMillis() / 200D) + 1F) / 2F) * 255)));
        }
    }

    public void mouse(double mouseX, double mouseY, int button) {
        final Vector2f position = getPosition();

        this.selected = isLClick(button) && isHover(mouseX, mouseY, position.x + (textAlign == TextAlign.CENTER ? -width / 2f : 0),
                position.y, width, font.getHeight());
    }

    public void keyPressed(int keyCode) {
        if (!this.selected) {
            return;
        }

        cursor = Math.min(Math.max(cursor, 0), this.text.length());

        if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) && keyCode == GLFW.GLFW_KEY_V) {

            final String clipboard = mc.keyboardListener.getClipboardString();
            this.addText(clipboard, cursor);
            cursor += clipboard.length();

        } else if (keyCode == GLFW.GLFW_KEY_DELETE && !this.text.isEmpty()) {
            this.removeText(cursor + 1);
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !this.text.isEmpty()) {

            this.removeText(cursor);
            cursor--;

            if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL)) {
                while (!this.text.isEmpty() && 0 < cursor) {
                    this.removeText(cursor);
                    cursor--;
                }
            }

        } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
            cursor++;

            if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL)) {
                while (this.text.length() > cursor) {
                    cursor++;
                }
            }
        } else if (keyCode == GLFW.GLFW_KEY_LEFT) {
            cursor--;

            if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL)) {
                while (0 < cursor) {
                    cursor--;
                }
            }
        } else if (keyCode == GLFW.GLFW_KEY_END) {
            while (this.text.length() > cursor) {
                cursor++;
            }
        } else if (keyCode == GLFW.GLFW_KEY_HOME) {
            while (0 < cursor) {
                cursor--;
            }
        }

        cursor = Math.min(Math.max(cursor, 0), this.text.length());
    }

    public void charTyped(char codePoint) {
        if (!this.selected) {
            return;
        }
        cursor = Math.min(Math.max(cursor, 0), this.text.length());

        if (GlyphPage.CHARS.contains(Character.toString(codePoint))) {
            this.addText(Character.toString(codePoint), cursor);
            cursor++;
        }

        cursor = Math.min(Math.max(cursor, 0), this.text.length());
    }

    private void addText(final String text, final int position) {
        float fontwidth = font.getWidth(this.text + text);

        if (fontwidth <= width) {
            final StringBuilder newText = new StringBuilder();

            boolean append = false;
            for (int i = 0; i < this.text.length(); i++) {
                final String character = String.valueOf(this.text.charAt(i));

                if (i == position) {
                    append = true;
                    newText.append(text);
                }

                newText.append(character);
            }

            if (!append) {
                newText.append(text);
            }

            this.text = newText.toString();
        }
    }

    private void removeText(final int position) {
        final StringBuilder newText = new StringBuilder();
        for (int i = 0; i < this.text.length(); ++i) {
            final String character = String.valueOf(this.text.charAt(i));

            if (i != position - 1) {
                newText.append(character);
            }
        }

        this.text = newText.toString();
    }

    public boolean isEmpty() {
        return this.text.isEmpty();
    }
}