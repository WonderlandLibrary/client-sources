package fr.dog.ui.clickgui.dropdown.component;

import fr.dog.module.ModuleCategory;
import fr.dog.ui.clickgui.dropdown.component.CategoryComponent;
import net.minecraft.client.gui.GuiScreen;
import org.lwjglx.input.Keyboard;
import org.lwjglx.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {

    private List<CategoryComponent> categories = new ArrayList<>();

    public ClickGuiScreen() {
        makeCategories();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        categories.forEach(component -> component.render(mouseX, mouseY));
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (CategoryComponent category : categories) {
            if (category.click(mouseX, mouseY, mouseButton))
                break;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        categories.forEach(component -> component.release(mouseX, mouseY, state));
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int i = Integer.signum(Mouse.getEventDWheel());
        categories.forEach(c -> c.setY(c.getY() - i * 4));
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_F5)
            makeCategories();

        categories.forEach(component -> component.type(typedChar, keyCode));

        if (keyCode == Keyboard.KEY_ESCAPE)
            mc.displayGuiScreen(null);
    }

    public void makeCategories() {
        if (!categories.isEmpty())
            categories.clear();

        float x = 15, y = 15;
        final float width = 100, height = 15;

        for (ModuleCategory category : ModuleCategory.values()) {
            categories.add(new CategoryComponent(category, x, y, width, height));
            x += width + 15;
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
