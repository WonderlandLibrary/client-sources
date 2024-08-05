package fr.dog.ui.clickgui.test;

import fr.dog.module.ModuleCategory;
import fr.dog.ui.clickgui.test.impl.MainCategory;
import fr.dog.ui.clickgui.test.impl.buttons.CategoryButton;
import fr.dog.util.InstanceAccess;
import fr.dog.util.render.RenderUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainScreen extends GuiScreen implements InstanceAccess {

    private final MainCategory mainWindow;
    private ModuleCategory category;
    private final ArrayList<CategoryButton> catButton;

    public MainScreen(){
        category = ModuleCategory.COMBAT;
        mainWindow = new MainCategory(ModuleCategory.COMBAT, scaledResolution().getScaledWidth()/2f, scaledResolution().getScaledHeight()/2f);

        catButton = new ArrayList<>(Arrays.asList(
                new CategoryButton(ModuleCategory.COMBAT, () -> category = ModuleCategory.COMBAT),
                new CategoryButton(ModuleCategory.MOVEMENT, () -> category = ModuleCategory.MOVEMENT),
                new CategoryButton(ModuleCategory.PLAYER, () -> category = ModuleCategory.PLAYER),
                new CategoryButton(ModuleCategory.EXPLOIT, () -> category = ModuleCategory.EXPLOIT),
                new CategoryButton(ModuleCategory.RENDER, () -> category = ModuleCategory.RENDER)
        ));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        mainWindow.draw(mouseX,mouseY);

        RenderUtil.drawLine(scaledResolution().getScaledWidth() / 2f, 0, 0,scaledResolution().getScaledHeight(),1, new Color(-1));

        float offset = 0;
        final int step = 60;
        for(CategoryButton button : catButton){
            button.draw(scaledResolution().getScaledWidth() / 2f + offset - ((float) (step * catButton.size()) / 2), scaledResolution().getScaledHeight() - 55, mouseX, mouseY);

            offset += step;
        }

        RenderUtil.drawGlow(this::drawGlow);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        catButton.forEach(button -> button.mouseClicked(mouseX,mouseY));

        mainWindow.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        mainWindow.mouseReleased(state);
    }

    private void drawGlow(){
        mainWindow.drawGlow();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
