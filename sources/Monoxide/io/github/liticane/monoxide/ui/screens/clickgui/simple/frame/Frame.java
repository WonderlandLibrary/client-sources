package io.github.liticane.monoxide.ui.screens.clickgui.simple.frame;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.simple.component.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.simple.component.impl.ModuleComponent;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Frame extends Component {

    private final ModuleCategory category;
    private final float moduleHeight;
    private GuiScreen guiScreen;
    int scroll;
    public int dWheel;

    public Frame(ModuleCategory category, float posX, float posY, float width, float height, float moduleHeight, GuiScreen screen) {
        super(posX, posY, width, height);
        this.category = category;
        this.moduleHeight = moduleHeight;
        this.guiScreen = screen;

        // The Y position in here is basically useless as the actual Y pos is overwritten in drawScreen
        float moduleY = posY + height + scroll * moduleHeight;
        List<Module> modules = ModuleManager.getInstance().getModules(this.category);
        FontRenderer normal = FontStorage.getInstance().findFont("SF UI", 19);
        modules.sort(Comparator.comparingInt(o -> normal.getStringWidthInt(((Module)o).getName())).reversed());
        for(Module module : modules) {
            this.subComponents.add(new ModuleComponent(module, posX, moduleY, width, moduleHeight, screen));
            moduleY += moduleHeight;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), getPosY(), getBaseWidth(), moduleHeight * 11) && ModuleManager.getInstance().getModules(category).size() > 11) {
            if(dWheel < 0) {
                scroll -= 1;
            } else if(dWheel > 0) {
                scroll += 1;
            }
            scroll = Math.min(0, scroll);
            scroll = Math.max(-(ModuleManager.getInstance().getModules(category).size() - 10), scroll);
        }
        RenderUtil.drawRect(getPosX() + getAddX(), getPosY() + getAddY(), getBaseWidth(), getBaseHeight(), new Color(0, 0, 0, 180).getRGB());
        FontStorage.getInstance().findFont("SF UI", 19).drawTotalCenteredStringWithShadow(category.getName(), this.getPosX() + this.getBaseWidth() / 2 + getAddX(), this.getPosY() + this.getBaseHeight() / 2 + getAddY(), -1);
        float moduleY = this.getPosY() + this.getBaseHeight() + scroll * moduleHeight;
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if(((ModuleComponent)component).getModule().shouldHide())
                    continue;
                component.setPosX(component.getPosX());
                component.setPosY(moduleY + getAddY());
                component.setAddX(this.getAddX());
                if(!(moduleY < this.getPosY() + this.getBaseHeight()) && !(moduleY > this.getPosY() + this.getBaseHeight() + 9 * moduleHeight)) {
                    component.drawScreen(mouseX, mouseY);
                }
                moduleY += component.getFinalHeight();
            }
        }
    }

    @Override
    public float getFinalHeight() {
        float totalComponentHeight = 0;
        for(Component component : this.subComponents) {
            totalComponentHeight += component.getFinalHeight();
        }
        totalComponentHeight = Math.min(totalComponentHeight, 10 * moduleHeight);
        return this.getBaseHeight() + totalComponentHeight;
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        float moduleY = this.getPosY() + this.getBaseHeight() + scroll * moduleHeight;
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if(((ModuleComponent)component).getModule().shouldHide())
                    continue;
                component.setPosY(moduleY + getAddY());
                if(!(moduleY < this.getPosY() + this.getBaseHeight()) && !(moduleY > this.getPosY() + this.getBaseHeight() + 9 * moduleHeight)) {
                    component.mouseClick(mouseX, mouseY, mouseButton);
                }
                moduleY += component.getFinalHeight();
            }
        }
    }

}
