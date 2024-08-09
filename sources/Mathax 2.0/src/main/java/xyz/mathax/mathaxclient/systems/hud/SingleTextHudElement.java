package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public abstract class SingleTextHudElement extends HudElement {
    protected Color textColor;

    protected boolean visible = true;

    private String text;

    private double textWidth;

    public SingleTextHudElement(Hud hud, String name, String description, boolean defaultActive) {
        super(hud, name, description, defaultActive);
        this.textColor = hud.primaryColorSetting.get();
    }

    public SingleTextHudElement(Hud hud, String name, String description) {
        super(hud, name, description);
        this.textColor = hud.primaryColorSetting.get();
    }

    @Override
    public void update(OverlayRenderer renderer) {
        text = getText();
        textWidth = renderer.textWidth(text);

        box.setSize(textWidth, renderer.textHeight());
    }

    @Override
    public void render(OverlayRenderer renderer) {
        if (!visible) {
            return;
        }

        double x = box.getX();
        double y = box.getY();
        renderer.text(text, x, y, textColor);
    }

    protected void setText(String text) {
        this.text = text;
        this.textWidth = 0;
    }

    protected abstract String getText();
}