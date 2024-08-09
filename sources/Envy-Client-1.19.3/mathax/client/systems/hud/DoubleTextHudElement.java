package mathax.client.systems.hud;

import mathax.client.utils.render.color.Color;

public abstract class DoubleTextHudElement extends HudElement {
    protected Color rightColor;
    protected boolean visible = true;

    private String left, right;
    private double leftWidth, rightWidth;

    public DoubleTextHudElement(HUD hud, String name, String description, boolean defaultActive) {
        super(hud, name, description, defaultActive);
        this.rightColor = hud.secondaryColor.get();
    }

    @Override
    public void update(HudRenderer renderer) {
        // Left
        left = getLeft();
        leftWidth = renderer.textWidth(left);

        // Right
        right = getRight();
        rightWidth = renderer.textWidth(right);

        // Box
        box.setSize(leftWidth + rightWidth, renderer.textHeight());
    }

    @Override
    public void render(HudRenderer renderer) {
        if (!visible) return;

        double x = box.getX();
        double y = box.getY();

        renderer.text(left, x, y, hud.primaryColor.get());
        renderer.text(right, x + leftWidth, y, rightColor);
    }

    protected abstract String getLeft();
    protected abstract String getRight();
}
