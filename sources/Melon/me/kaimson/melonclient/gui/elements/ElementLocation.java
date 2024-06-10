package me.kaimson.melonclient.gui.elements;

import me.kaimson.melonclient.features.*;
import java.util.function.*;
import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;
import org.lwjgl.input.*;
import me.kaimson.melonclient.gui.settings.*;

public class ElementLocation extends Element
{
    private final Module module;
    
    public ElementLocation(final Module module, final Consumer<Element> consumer) {
        super(0, 0, 0, 0, consumer);
        this.module = module;
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.enabled) {
            final int width = ((IModuleRenderer)this.module).getWidth();
            final int height = ((IModuleRenderer)this.module).getHeight();
            final float x = (float)BoxUtils.getBoxOffX(this.module, (int)ModuleConfig.INSTANCE.getActualX(this.module), width);
            final float y = (float)BoxUtils.getBoxOffY(this.module, (int)ModuleConfig.INSTANCE.getActualY(this.module), height);
            this.hovered = (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height);
            if (this.hovered) {
                GuiHUDEditor.lastDragging = this.module;
            }
            GLRectUtils.drawRect(x, y, x + width, y + height, this.hovered ? Client.getMainColor(50) : new Color(20, 20, 20, 50).getRGB());
            GLRectUtils.drawRectOutline(x, y, x + width, y + height, this.hovered ? Client.getMainColor(255) : new Color(0, 0, 0, 255).getRGB());
            ((IModuleRenderer)this.module).render(x, y);
            if (this.module.settings.size() > 0 && this.hovered) {
                final int b = (height > 10) ? 10 : 8;
                bfl.l();
                GuiUtils.setGlColor(new Color(0, 0, 0, 150).getRGB());
                this.mc.P().a(new jy("melonclient/icons/settings.png"));
                GuiUtils.drawModalRectWithCustomSizedTexture(x + width - b + 1.0f, y + height - b + 1.0f, 0.0f, 0.0f, b, b, (float)b, (float)b);
                if (mouseX > x + width - b && mouseX < x + width && mouseY > y + height - b && mouseY < y + height) {
                    GuiUtils.setGlColor(Client.getMainColor(255));
                    if (Mouse.isButtonDown(0)) {
                        this.mc.a((axu)new GuiModuleSettings(this.module, null));
                    }
                }
                else {
                    bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
                }
                this.mc.P().a(new jy("melonclient/icons/settings.png"));
                GuiUtils.drawModalRectWithCustomSizedTexture(x + width - b, y + height - b, 0.0f, 0.0f, b, b, (float)b, (float)b);
            }
        }
    }
}
