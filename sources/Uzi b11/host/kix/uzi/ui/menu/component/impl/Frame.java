package host.kix.uzi.ui.menu.component.impl;

import host.kix.uzi.ui.menu.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class Frame extends Component {

    /**
     * Checks if the frame is selected
     */
    private boolean selected;
    private int x, y;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;
    private List<Button> buttons = new ArrayList<>();

    public Frame(String label, int x, int y) {
        super(label);
        this.x = x;
        this.y = y;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + WIDTH, y + HEIGHT, selected ? 0xFF000000 : 0xFFFF0000);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(getLabel(), x + 2, y + 2, -1);

        if (selected) {
            buttons
                    .forEach(button -> button.drawScreen(mouseX, mouseY, partialTicks));
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEIGHT && mouseButton == 0) {
            selected = !selected;
        }
        if (selected) {
            buttons
                    .forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
