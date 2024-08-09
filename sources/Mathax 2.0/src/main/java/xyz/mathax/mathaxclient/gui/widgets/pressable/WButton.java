package xyz.mathax.mathaxclient.gui.widgets.pressable;

import xyz.mathax.mathaxclient.gui.renderer.packer.GuiTexture;

public abstract class WButton extends WPressable {
    protected String text;
    protected double textWidth;

    protected GuiTexture texture;

    public WButton(String text, GuiTexture texture) {
        this.text = text;
        this.texture = texture;
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();
        if (text != null) {
            textWidth = theme.textWidth(text);

            width = pad + textWidth + pad;
            height = pad + theme.textHeight() + pad;
        } else {
            double textHeight = theme.textHeight();
            width = pad + textHeight + pad;
            height = pad + textHeight + pad;
        }
    }

    public void set(String text) {
        if (this.text == null || Math.round(theme.textWidth(text)) != textWidth) {
            invalidate();
        }

        this.text = text;
    }

    public String getText() {
        return text;
    }
}
