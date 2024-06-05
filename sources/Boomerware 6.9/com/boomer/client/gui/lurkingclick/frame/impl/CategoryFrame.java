package com.boomer.client.gui.lurkingclick.frame.impl;

import com.boomer.client.gui.lurkingclick.frame.component.impl.ModuleComponent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.Client;
import com.boomer.client.gui.lurkingclick.frame.Frame;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class CategoryFrame extends Frame {
    private Module.Category category;
    private ArrayList<ModuleComponent> components = new ArrayList<>();
    private float scrollY;

    public CategoryFrame(Module.Category category, float posX, float posY, float width, float height, boolean pinnable) {
        super(StringUtils.capitalize(category.name().toLowerCase()), posX, posY, width, height, pinnable);
        this.category = category;
        this.scrollY = 0;
    }

    @Override
    public void initGUI() {
        float offset = getHeight();
        for (Module module : Client.INSTANCE.getModuleManager().getCategoryCheats(category)) {
            ModuleComponent moduleComponent = new ModuleComponent(this, module, getPosX(), getPosY(), 2, offset, getWidth() - 4, 20);
            components.add(moduleComponent);
            offset += 20;
        }
        components.forEach(component -> component.initGUI());
    }

    @Override
    public void updatePosition(float posX, float posY) {
        super.updatePosition(posX, posY);
        components.forEach(component -> component.updatePosition(posX, posY));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, ScaledResolution scaledResolution) {
        super.drawScreen(mouseX, mouseY, partialTicks, scaledResolution);
        RenderUtil.drawRect(getPosX(), getPosY(), getWidth(), getHeight(), 0xff4F4FA8);
        mc.fontRendererObj.drawStringWithShadow(getLabel(), getPosX() + getWidth() / 2 - mc.fontRendererObj.getStringWidth(getLabel()) / 2, getPosY() + getHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, isPinned() ? 0xffff0000 : -1);
        float defaultOffset = 0;
        for (Module module : Client.INSTANCE.getModuleManager().getCategoryCheats(category)) {
            defaultOffset += 20;
        }
        float offset = getHeight();
        if (isExtended()) {
            for (ModuleComponent moduleComponent : components) {
                moduleComponent.setOffsetY(offset + getScrollY());
                offset += moduleComponent.getAdditionHeight();
            }
            components.forEach(component -> component.updatePosition(getPosX(), getPosY()));
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(scaledResolution, getPosX() + 2, getPosY() + 20, getWidth() - 4, defaultOffset > 120 ? defaultOffset : 120);
            components.forEach(component -> component.drawScreen(mouseX, mouseY, partialTicks, scaledResolution));
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
            if (offset > 140) {
                float incScrollBar = ((defaultOffset > 120 ? defaultOffset : 120) - 3 + (getHeight() - ((defaultOffset > 120 ? defaultOffset : 120) - 3))) / Math.abs(-((offset - 20) - defaultOffset));
                float scrollBarY = incScrollBar * Math.abs(getScrollY());
                RenderUtil.drawRect(getPosX() + getWidth() - 2, getPosY() + 20 + scrollBarY, 2, Math.abs(getHeight() - ((defaultOffset > 120 ? defaultOffset : 120) - 3)), -1);
            }
        }
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + 2, getPosY() + 20, getWidth() - 4,  defaultOffset > 120 ? defaultOffset: 120) && offset > 120 && Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                if (getScrollY() - 3 < -((offset - 20) - (defaultOffset > 120 ? defaultOffset: 120))) setScrollY(-((offset - 20) - (defaultOffset > 120 ? defaultOffset: 120)));
                else setScrollY(getScrollY() - 3);
            } else if (wheel > 0) {
                setScrollY(getScrollY() + 3);
            }
        }
        if (getScrollY() > 0) setScrollY(0);
        if (getScrollY() < -((offset - 20) - defaultOffset)) setScrollY(-((offset - 20) - defaultOffset));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        float defaultOffset = 0;
        for (Module module : Client.INSTANCE.getModuleManager().getCategoryCheats(category)) {
            defaultOffset += 20;
        }
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + 2, getPosY() + 20, getWidth() - 4,  defaultOffset > 120 ? defaultOffset: 120) && isExtended()) {
            components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (isExtended()) components.forEach(component -> component.keyTyped(typedChar, keyCode));
    }

    public Module.Category getCategory() {
        return category;
    }

    public float getScrollY() {
        return scrollY;
    }

    public void setScrollY(float scrollY) {
        this.scrollY = scrollY;
    }
}