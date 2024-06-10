package me.kaimson.melonclient.gui.settings;

import me.kaimson.melonclient.gui.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.config.*;
import org.lwjgl.input.*;
import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.gui.components.*;
import me.kaimson.melonclient.gui.elements.*;
import java.util.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import java.io.*;

public class GuiHUDEditor extends GuiScreen
{
    private Module dragging;
    public static Module lastDragging;
    private int dragClickX;
    private int dragClickY;
    private int offsetX;
    private int offsetY;
    private avr sr;
    private final axu parentScreen;
    
    public GuiHUDEditor(final axu parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    @Override
    public void b() {
        super.b();
        this.sr = new avr(ave.A());
        this.elements.clear();
        for (final Module module : ModuleManager.INSTANCE.modules) {
            if (module.isRender()) {
                if (!ModuleConfig.INSTANCE.isEnabled(module)) {
                    continue;
                }
                final IModuleRenderer module2;
                final float mouseX;
                final float mouseY;
                this.elements.add(new ElementLocation(module, element -> {
                    this.dragging = module2;
                    mouseX = Mouse.getX() / (float)this.sr.e();
                    mouseY = (this.j.e - Mouse.getY()) / (float)this.sr.e();
                    this.offsetX = (int)(-(mouseX - ModuleConfig.INSTANCE.getActualX(module2)) - BoxUtils.getOffsetX(module2, module2.getWidth()));
                    this.offsetY = (int)(-(mouseY - ModuleConfig.INSTANCE.getActualY(module2)) - BoxUtils.getOffsetY(module2, module2.getHeight()));
                    return;
                }));
            }
        }
        this.components.clear();
        this.components.add(new ComponentToolbar(this.l / 2 - 36, this.m - 25, this.l / 2 + 36, this.m - 7, 13, 14, ComponentToolbar.Layout.HORIZONTAL).setOffset(2, 2).required(new ElementButtonIcon(14, 14, "icons/home.png", element -> this.j.a(this.parentScreen)), ComponentToolbar.Position.POSITIVE).optional(new ElementButtonIcon(14, 14, "icons/edit.png", element -> {}), ComponentToolbar.Position.POSITIVE, false).optional(new ElementButtonIcon(14, 14, "icons/settings.png", element -> {}), ComponentToolbar.Position.POSITIVE, false));
    }
    
    @Override
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.a(0, 0, this.l, this.m, new Color(0, 0, 0, 100).getRGB());
        if (this.dragging != null && Mouse.isButtonDown(0)) {
            this.updateModulePosition(mouseX, mouseY);
        }
        if (Mouse.isButtonDown(0) && this.dragClickX != 0 && this.dragClickY != 0 && this.dragClickX != mouseX && this.dragClickY != mouseY) {
            GuiUtils.a(this.dragClickX, this.dragClickY, mouseX, mouseY, new Color(150, 150, 150, 100).getRGB());
            GLRectUtils.drawRectOutline((float)Math.min(this.dragClickX, mouseX), (float)Math.min(this.dragClickY, mouseY), (float)Math.max(this.dragClickX, mouseX), (float)Math.max(this.dragClickY, mouseY), new Color(150, 150, 150, 150).getRGB());
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        if (GuiHUDEditor.lastDragging != null) {
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            switch (keyCode) {
                case 203: {
                    --xOffset;
                    break;
                }
                case 205: {
                    ++xOffset;
                    break;
                }
                case 200: {
                    --yOffset;
                    break;
                }
                case 208: {
                    ++yOffset;
                    break;
                }
            }
            final float x = ModuleConfig.INSTANCE.getPosition(GuiHUDEditor.lastDragging).getX() + xOffset;
            final float y = ModuleConfig.INSTANCE.getPosition(GuiHUDEditor.lastDragging).getY() + yOffset;
            ModuleConfig.INSTANCE.setPosition(GuiHUDEditor.lastDragging, ModuleConfig.INSTANCE.getPosition(GuiHUDEditor.lastDragging).getAnchorPoint(), x, y);
        }
    }
    
    @Override
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (this.elements.stream().filter(element -> element instanceof ElementLocation).noneMatch(element -> element.hovered)) {
            this.dragClickX = mouseX;
            this.dragClickY = mouseY;
        }
    }
    
    @Override
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.dragClickX = 0;
        this.dragClickY = 0;
        if (this.dragging != null) {
            ModuleConfig.INSTANCE.setClosestAnchorPoint(this.dragging);
            this.updateModulePosition(mouseX, mouseY);
            this.dragging = null;
        }
    }
    
    @Override
    public void m() {
        super.m();
        GuiHUDEditor.lastDragging = null;
        ModuleConfig.INSTANCE.saveConfig();
    }
    
    private void updateModulePosition(final int mouseX, final int mouseY) {
        final float x = (float)(mouseX - BoxUtils.getBoxOffX(this.dragging, ModuleConfig.INSTANCE.getPosition(this.dragging).getAnchorPoint().getX(this.sr.a()), ((IModuleRenderer)this.dragging).getWidth()) + this.offsetX);
        final float y = (float)(mouseY - BoxUtils.getBoxOffY(this.dragging, ModuleConfig.INSTANCE.getPosition(this.dragging).getAnchorPoint().getY(this.sr.b()), ((IModuleRenderer)this.dragging).getHeight()) + this.offsetY);
        ModuleConfig.INSTANCE.setPosition(this.dragging, ModuleConfig.INSTANCE.getPosition(this.dragging).getAnchorPoint(), x, y);
    }
}
