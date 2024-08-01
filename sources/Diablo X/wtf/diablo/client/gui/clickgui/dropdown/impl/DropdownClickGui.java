package wtf.diablo.client.gui.clickgui.dropdown.impl;

import org.lwjgl.input.Keyboard;
import wtf.diablo.client.gui.clickgui.dropdown.api.AbstractClickGui;
import wtf.diablo.client.gui.clickgui.dropdown.impl.component.CategoryPanelComponent;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;

public final class DropdownClickGui extends AbstractClickGui {
    private final ModuleRepository moduleRepository;
    private String searchText;
    private boolean typing = false;


    public DropdownClickGui(final ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;

        this.searchText = "";

        int count = 0;
        for (final ModuleCategoryEnum category : ModuleCategoryEnum.values()) {
            final int categoryWidth = 110;
            final int categoryHeight = 20;
            final int categorySpacing = 10;

            final int categoryInitialX = 10 + (count * (categoryWidth + categorySpacing));
            final int categoryInitialY = 10;

            this.guiComponents.add(CategoryPanelComponent.builder()
                    .withParent(this)
                    .withCategory(category)
                    .withX(categoryInitialX)
                    .withY(categoryInitialY)
                    .withWidth(categoryWidth)
                    .withHeight(categoryHeight)
                    .build());

            count++;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.guiComponents.forEach(guiComponent -> guiComponent.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.guiComponents.forEach(guiComponent -> guiComponent.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.guiComponents.forEach(guiComponent -> guiComponent.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }

        if (keyCode == Keyboard.KEY_BACK) {
            if (!this.searchText.isEmpty()) {
                this.searchText = this.searchText.substring(0, this.searchText.length() - 1);
            }
        } else {
            final String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            if (!validChars.contains(String.valueOf(typedChar))) {
                return;
            }
            
            this.searchText += typedChar;
            this.typing = true;
        }

        System.out.println(searchText);
    }

    public ModuleRepository getModuleRepository() {
        return this.moduleRepository;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public boolean isTyping() {
        return typing;
    }
}
