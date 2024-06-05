package client.metaware.api.clickgui.panel;


import client.metaware.api.clickgui.component.Component;
import client.metaware.api.clickgui.component.implementations.SettingComponent;
import client.metaware.api.clickgui.theme.Theme;
import client.metaware.api.clickgui.theme.implementations.FelixTheme;
import client.metaware.impl.utils.render.RenderUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Panel extends Component {

    protected boolean extended;
    protected List<Component> components = new ArrayList<>();

    public Panel(float x, float y, float width, float height) {
        this(x, y, width, height, new FelixTheme());
    }

    public Panel(float x, float y, float width, float height, boolean visible) {
        this(x, y, width, height, visible, new FelixTheme());
    }

    public Panel(float x, float y, float width, float height, boolean visible, Theme theme) {
        super(x, y, width, height, visible, theme);
        init();
    }

    public Panel(float x, float y, float width, float height, Theme theme) {
        this(x, y, width, height, true, theme);
    }

    @Override
    public void reset() {
        for (Component component : components) {
            component.reset();
        }
    }

    @Override
    public boolean focused() {
        boolean focused = false;
        for (Component component : components) {
            if(component.visible() && component.focused())
                focused = true;
        }
        return focused || this.focused;
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (extended) {
            updatePositions();
            for (Component component : components) {
                if(component.visible()) {
                    component.drawScreen(mouseX, mouseY);
                }
            }
        }
    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(x, y, width, height, mouseX, mouseY)) {
            if (mouseButton == 1) {
                extended = !extended;
                if(extended) reset();
                for (Component component : components) {
                    if(component instanceof SettingComponent) {
                        component.visible(extended && ((SettingComponent<?>) component).setting().isAvailable());
                    } else component.visible(extended);
                }
            }
        } else if(RenderUtil.inBounds(x, y, x + width, y + totalHeight(), mouseX, mouseY)) {
            if(extended) {
                for (Component component : components) {
                    if(component.visible()) {
                        component.mouseClicked(mouseX, mouseY, mouseButton);
                    }
                }
            }
        }
    }
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (extended) {
            for (Component component : components) {
                if(component.visible()) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    public void keyTyped(char typedChar, int keyCode) {
        if(extended) {
            for (Component component : components) {
                if(component.visible()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
    }

    public void updateComponents() {
        for(Component component : components) {
            if(component instanceof SettingComponent) {
                component.visible(((SettingComponent<?>) component).setting().isAvailable());
            }
        }
    }

    public void updatePositions() {
        float yOffset = height;
        for(Component component : components) {
            if(component.visible()) {
                component.setPosition(x, y + yOffset, component.width(), component.height());
                yOffset += component instanceof Panel ? ((Panel) component).extended() ? ((Panel) component).totalHeight() : component.height() : component.height();
            }
        }
    }

    public float totalHeight() {
        float height = this.height;
        for(Component component : components) {
            if(component.visible()) {
                height += component instanceof Panel ? ((Panel) component).extended() ? ((Panel) component).totalHeight() : component.height() : component.height();
            }
        }
        return height;
    }
    public List<Component> components() {
        return components;
    }
    public void components(List<Component> components) {
        this.components = components;
    }
    public boolean extended() {
        return extended;
    }
    public void extended(boolean extended) {
        this.extended = extended;
    }
}
