package xyz.mathax.mathaxclient.systems.hud;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public abstract class TripleTextHudElement extends HudElement {
    protected Color leftColor;
    protected Color centerColor;
    protected Color rightColor;

    protected boolean visible = true;

    private String left, center, right;

    private double leftWidth, centerWidth, rightWidth;

    public TripleTextHudElement(Hud hud, String name, String description, boolean defaultActive) {
        super(hud, name, description, defaultActive);
        this.leftColor = hud.primaryColorSetting.get();
        this.centerColor = hud.secondaryColorSetting.get();
        this.rightColor = hud.primaryColorSetting.get();
    }

    public TripleTextHudElement(Hud hud, String name, String description) {
        super(hud, name, description);
        this.leftColor = hud.primaryColorSetting.get();
        this.centerColor = hud.secondaryColorSetting.get();
        this.rightColor = hud.primaryColorSetting.get();
    }

    @Override
    public void update(OverlayRenderer renderer) {
        left = getLeft();
        leftWidth = renderer.textWidth(left);

        center = getCenter();
        centerWidth = renderer.textWidth(center);

        right = getRight();
        rightWidth = renderer.textWidth(right);

        box.setSize(leftWidth + centerWidth + rightWidth, renderer.textHeight());
    }

    @Override
    public void render(OverlayRenderer renderer) {
        if (!visible) {
            return;
        }

        double x = box.getX();
        double y = box.getY();
        renderer.text(left, x, y, leftColor);
        renderer.text(center, x + leftWidth, y, centerColor);
        renderer.text(right, x + leftWidth + centerWidth, y, rightColor);
    }

    protected void setLeft(String left) {
        this.left = left;
        this.leftWidth = 0;
    }

    protected abstract String getLeft();

    protected void setCenter(String center) {
        this.center = center;
        this.centerWidth = 0;
    }

    protected abstract String getCenter();

    protected void setRight(String right) {
        this.right = right;
        this.rightWidth = 0;
    }

    protected abstract String getRight();
}