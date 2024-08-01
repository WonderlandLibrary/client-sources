package wtf.diablo.client.gui.clickgui.dropdown.impl.component;


import net.minecraft.client.Minecraft;
import wtf.diablo.client.font.FontHandler;
import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.DropdownClickGui;
import wtf.diablo.client.gui.clickgui.dropdown.impl.searchbar.SearchBar;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class CategoryPanelComponent implements IGuiComponent {
    private final List<ModulePanelComponent> modulePanelComponents;

    private final ModuleCategoryEnum category;
    private final DropdownClickGui parent;
    private int x,y, width, height, panelY; //position of category panel
    private boolean collapsed, dragging;
    private int offsetX, offsetY;
    private final SearchBar searchBar;

    private CategoryPanelComponent(final Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.parent = builder.parent;
        this.category = builder.category;

        this.searchBar = new SearchBar(parent);

        this.modulePanelComponents = new ArrayList<>();

        parent.getModuleRepository().getModulesByCategory(category).forEach(module -> {
            this.modulePanelComponents.add(new ModulePanelComponent(this, module));
        });

        this.modulePanelComponents.sort(Comparator.comparing(module -> Minecraft.getMinecraft().fontRendererObj.getStringWidth(((ModulePanelComponent) module).getModule().getName())).reversed());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.panelY = height;

        if(dragging){
            x = mouseX - offsetX;
            y = mouseY - offsetY;
        }

        RenderUtil.drawRoundedRectangle(x,y,width, height, 2, 2, 0, 0, new Color(0xff1C1C1C).getRGB());

        //RenderUtil.drawOutlineRoundedRectangle(x, y, width, this.getHeight(), 2, .1f, new Color(255, 255, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());

        final TTFFontRenderer font = FontHandler.fetch("outfitbold 22");
        final TTFFontRenderer iconFont = FontHandler.fetch("guiicons 28");

        final String iconStr = String.valueOf(category.getIconChar());

        iconFont.drawStringWithShadow(iconStr, x + 94, y + 3, ColorUtil.CATEGORY_TEXT_COLOR.getValue().getRGB());
        font.drawStringWithShadow(category.toString(), x + 4, y + 2, ColorUtil.CATEGORY_TEXT_COLOR.getValue().getRGB());

        if (collapsed)
            return;

        this.searchBar.drawScreen(mouseX, mouseY, partialTicks);

        this.modulePanelComponents.forEach(modulePanelComponent -> {
            if (!modulePanelComponent.getModule().getName().toLowerCase().contains(parent.getSearchText().toLowerCase()))
                return;

            modulePanelComponent.drawScreen(mouseX, mouseY, partialTicks);
        });
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(mouseX,mouseY,x,y,x + width, y + height)){
            if (mouseButton == 0){
                dragging = true;
                offsetX = mouseX - x;
                offsetY = mouseY - y;
            } else if (mouseButton == 1){
                collapsed = !collapsed;
            }
        }

        this.modulePanelComponents.forEach(modulePanelComponent -> {
            if (!modulePanelComponent.getModule().getName().toLowerCase().contains(parent.getSearchText().toLowerCase()))
                return;

            modulePanelComponent.mouseClicked(mouseX, mouseY, mouseButton);
        });
        this.searchBar.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        offsetY = 0;
        offsetX = 0;

        this.modulePanelComponents.forEach(modulePanelComponent -> {
            if (!modulePanelComponent.getModule().getName().toLowerCase().contains(parent.getSearchText().toLowerCase()))
                return;

            modulePanelComponent.mouseReleased(mouseX, mouseY, state);
        });
        this.searchBar.mouseReleased(mouseX, mouseY, state);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPanelY() {
        return panelY;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setPanelY(final int panelY) {
        this.panelY = panelY;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private DropdownClickGui parent;
        private ModuleCategoryEnum category;
        private int x,y, width, height;

        public Builder withParent(final DropdownClickGui parent) {
            this.parent = parent;
            return this;
        }

        public Builder withCategory(final ModuleCategoryEnum category) {
            this.category = category;
            return this;
        }

        public Builder withX(final int x) {
            this.x = x;
            return this;
        }

        public Builder withY(final int y) {
            this.y = y;
            return this;
        }

        public Builder withWidth(final int width) {
            this.width = width;
            return this;
        }

        public Builder withHeight(final int height) {
            this.height = height;
            return this;
        }

        public CategoryPanelComponent build() {
            return new CategoryPanelComponent(this);
        }
    }
}
