package mathax.client.systems.hud;

import mathax.client.utils.render.color.Color;

public abstract class TripleTextHudElement extends HudElement {
    protected Color rightColor;
    protected boolean visible = true;

    private String left, right, end;
    private double leftWidth, rightWidth, endWidth;

    public TripleTextHudElement(HUD hud, String name, String description, boolean defaultActive) {
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

        // End
        end = getEnd();
        endWidth = renderer.textWidth(end);

        // Box
        box.setSize(leftWidth + rightWidth + endWidth, renderer.textHeight());
    }

    @Override
    public void render(HudRenderer renderer) {
        if (!visible) return;

        double x = box.getX();
        double y = box.getY();

        renderer.text(left, x, y, hud.primaryColor.get());
        renderer.text(right, x + leftWidth, y, rightColor);
        renderer.text(end, x + leftWidth + rightWidth, y, hud.primaryColor.get());
    }

    protected abstract String getLeft();
    protected abstract String getRight();
    protected abstract String getEnd();
}
