package io.github.nevalackin.radium.gui.click;

import io.github.nevalackin.radium.event.impl.KeyPressEvent;
import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.ExpandableComponent;
import io.github.nevalackin.radium.gui.click.component.impl.panel.impl.CategoryPanel;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ClickGui extends GuiScreen {

    public static boolean escapeKeyInUse;
    private static ClickGui instance;
    private final List<Component> components = new ArrayList<>();
    private final Palette palette;
    private Component selectedPanel;

    public ClickGui() {
        instance = this;
        palette = Palette.PINK;

        int panelX = 2;

        for (ModuleCategory category : ModuleCategory.values()) {
            CategoryPanel panel = new CategoryPanel(category, panelX, 2);
            this.components.add(panel);
            panelX += panel.getWidth() + 2;

            selectedPanel = panel;
        }
}

    public static ClickGui getInstance() {
        return instance;
    }

    public Palette getPalette() {
        return palette;
    }

    @Override
    public void drawScreen(int mouseX,
                           int mouseY,
                           float partialTicks) {
        for (Component component : components) {
            component.drawComponent(RenderingUtils.getScaledResolution(), mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar,
                            int keyCode)
            throws IOException {
        selectedPanel.onKeyPress(keyCode);

        if (!escapeKeyInUse)
            super.keyTyped(typedChar, keyCode);

        escapeKeyInUse = false;
    }

    @Override
    protected void mouseClicked(int mouseX,
                                int mouseY,
                                int mouseButton) {
        // Reversed so that you will click on the panel being drawn on top
        for (int i = components.size() - 1; i >= 0; i--) {
            Component component = components.get(i);
            int x = component.getX();
            int y = component.getY();
            int cHeight = component.getHeight();
            if (component instanceof ExpandableComponent) {
                ExpandableComponent expandableComponent = (ExpandableComponent) component;
                if (expandableComponent.isExpanded())
                    cHeight = expandableComponent.getHeightWithExpand();
            }
            if (mouseX > x && mouseY > y && mouseX < x + component.getWidth() && mouseY < y + cHeight) {
                selectedPanel = component;
                component.onMouseClick(mouseX, mouseY, mouseButton);
                break;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX,
                                 int mouseY,
                                 int state) {
        selectedPanel.onMouseRelease(state);
    }

    @Listener
    private void onKeyPressEvent(KeyPressEvent e) {
        if (e.getKey() == Keyboard.KEY_RSHIFT)
            Wrapper.getMinecraft().displayGuiScreen(this);
    }
}
