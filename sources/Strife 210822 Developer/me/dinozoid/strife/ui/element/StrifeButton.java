package me.dinozoid.strife.ui.element;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.font.CustomFontRenderer;
import me.dinozoid.strife.ui.callback.ClickCallback;
import me.dinozoid.strife.util.render.RenderUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class StrifeButton {

    private Position position;
    private CustomFontRenderer fontRenderer;
    private String text;
    private int color;

    public StrifeButton(float x, float y, float width, float height, int fontSize, int color) {
        this(x, y, width, height, "", fontSize, color);
    }

    public StrifeButton(Position position, int fontSize, int color) {
        this(position.x(), position.y(), position.width(), position.height(), "", fontSize, color);
    }

    public StrifeButton(Position position, String text, int fontSize, int color) {
        this(position.x(), position.y(), position.width(), position.height(), text, fontSize, color);
    }

    public StrifeButton(float x, float y, float width, float height, String text, int fontSize, int color) {
        this.position = new Position(x, y, width, height);
        this.text = text;
        this.color = color;
        this.fontRenderer = StrifeClient.INSTANCE.fontRepository().defaultFont().size(fontSize);
    }

    public void drawScreen(int mouseX, int mouseY) {
        float x = position.x();
        float y = position.y();
        float width = position.width();
        float height = position.height();
        if(RenderUtil.isHovered(x, y, width, height, mouseX, mouseY)) {
            Gui.drawRect(x, y, x + width, y + height, RenderUtil.brighter(new Color(color), 0.85F).getRGB());
        } else {
            Gui.drawRect(x, y, x + width, y + height, color);
        }
        float textX = position.x() + position.width() / 2 - fontRenderer.getWidth(text) / 2;
        float textY = position.y() + position.height() / 2 - fontRenderer.getHeight(text) / 2;
        fontRenderer.drawStringWithShadow(text, textX, textY, -1);
    }

    public void setPosition(float x, float y, float width, float height) {
        position = new Position(x, y, width, height);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton, ClickCallback callback) {
        if(RenderUtil.isHovered(position.x(), position.y(), position.width(), position.height(), mouseX, mouseY)) {
            callback.onClicked(mouseButton);
        }
    }

    public Position position() {
        return position;
    }
    public String text() {
        return text;
    }
    public int color() {
        return color;
    }
}