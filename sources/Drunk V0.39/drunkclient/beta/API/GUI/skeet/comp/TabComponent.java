/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.skeet.comp;

import drunkclient.beta.API.GUI.skeet.SkeetUI;
import drunkclient.beta.API.GUI.skeet.comp.Component;
import drunkclient.beta.UTILS.render.LockedResolution;

public abstract class TabComponent
extends Component {
    private final String name;

    public TabComponent(Component parent, String name, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        this.setupChildren();
        this.name = name;
    }

    public abstract void setupChildren();

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        SkeetUI.FONT_RENDERER.drawStringWithShadow(this.name, this.getX() + 8.0f, this.getY() + 8.0f - 3.0f, SkeetUI.getColor(0xFFFFFF));
        float x = 8.0f;
        int i = 0;
        while (i < this.children.size()) {
            Component child = (Component)this.children.get(i);
            child.setX(x);
            if (i < 3) {
                child.setY(14.0f);
            }
            child.drawComponent(resolution, mouseX, mouseY);
            x += 102.333336f;
            if (x + 8.0f + 94.333336f > 315.0f) {
                x = 8.0f;
            }
            if (i > 2) {
                int above = i - 3;
                int totalY = 14;
                do {
                    Component componentAbove = this.getChildren().get(above);
                    totalY = (int)((float)totalY + componentAbove.getHeight() + 8.0f);
                } while ((above -= 3) >= 0);
                child.setY(totalY);
            }
            ++i;
        }
    }
}

