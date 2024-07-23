package io.github.liticane.monoxide.ui.screens.clickgui.astolfo.frame;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.Component;
import io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.impl.ModuleComponent;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Frame extends Component {

    private final ModuleCategory category;
    private final float moduleHeight;
    private float draggingX, draggingY;
    private boolean dragging;

    public Frame(ModuleCategory category, float posX, float posY, float width, float height, float moduleHeight) {
        super(posX, posY, width, height);
        this.category = category;
        this.moduleHeight = moduleHeight;

        // The Y position in here is basically useless as the actual Y pos is overwritten in drawScreen
        float moduleY = posY + height;
        List<Module> modules = ModuleManager.getInstance().getModules(this.category);
        FontRenderer normal = FontStorage.getInstance().findFont("SF UI", 19);
        modules.sort(Comparator.comparingInt(o -> normal.getStringWidthInt(((Module)o).getName())).reversed());
        for(Module module : modules) {
            this.subComponents.add(new ModuleComponent(module, posX, moduleY, width, moduleHeight));
            moduleY += moduleHeight;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if(!Mouse.isButtonDown(0))
            dragging = false;
        if(dragging) {
            this.setPosY(mouseY - draggingY);
            this.setPosX(mouseX - draggingX);
        }
        float moduleY = this.getPosY() + this.getBaseHeight();
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if(((ModuleComponent)component).getModule().shouldHide())
                    continue;
                component.setPosX(this.getPosX());
                component.setPosY(moduleY + getAddY());
                component.setAddX(this.getAddX());
                moduleY += component.getFinalHeight();
            }
        }
        FontRenderer mcFont = Minecraft.getMinecraft().fontRendererObj;
        final float finalY = moduleY;
        RenderUtil.drawRect(getPosX() + getAddX() - 3, getPosY() + getAddY() - 3, getBaseWidth() + 6, moduleY - getPosY() + 6, ColorUtil.getAstolfoColor(category));
        RenderUtil.drawRect(getPosX() + getAddX() - 2, getPosY() + getAddY() - 2, getBaseWidth() + 4, moduleY - getPosY() + 4, new Color(26, 26, 26).getRGB());
        RenderUtil.drawRect(getPosX() + getAddX(), getPosY() + getAddY() + getBaseHeight(), getBaseWidth(), moduleY - getPosY() - getBaseHeight(), new Color(37, 37, 37).getRGB());
        mcFont.drawStringWithShadow(category.getName().toLowerCase(), getPosX() + 4 + getAddX(), getPosY() + getBaseHeight() - 12 + getAddY(), -1);
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if(((ModuleComponent)component).getModule().shouldHide())
                    continue;
                component.drawScreen(mouseX, mouseY);
            }
        }
    }

    @Override
    public float getFinalHeight() {
        float totalComponentHeight = 0;
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if (((ModuleComponent) component).getModule().shouldHide())
                    continue;
            }
            totalComponentHeight += component.getFinalHeight();
        }
        return this.getBaseHeight() + totalComponentHeight;
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(mouseX, mouseY, getPosX() + getAddX(), getPosY() + getAddY(), getBaseWidth(), getBaseHeight()) && mouseButton == 0) {
            dragging = true;
            draggingX = mouseX - getPosX();
            draggingY = mouseY - getPosY();
        }
        float moduleY = this.getPosY() + this.getBaseHeight();
        for(Component component : this.subComponents) {
            if(component instanceof ModuleComponent) {
                if(((ModuleComponent)component).getModule().shouldHide())
                    continue;
                component.setPosY(moduleY + getAddY());
                component.mouseClick(mouseX, mouseY, mouseButton);
                moduleY += component.getFinalHeight();
            }
        }
    }

}
