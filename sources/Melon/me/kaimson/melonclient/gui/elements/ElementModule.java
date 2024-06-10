package me.kaimson.melonclient.gui.elements;

import me.kaimson.melonclient.features.*;
import java.util.function.*;
import java.awt.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;

public class ElementModule extends Element
{
    private final Module module;
    private float scale;
    private int bgFade;
    private final int disabledColor;
    
    public ElementModule(final int x, final int y, final int width, final int height, final Module module, final Consumer<Element> consumer) {
        super(x, y, width, height, true, consumer);
        this.disabledColor = new Color(120, 120, 120, 255).getRGB();
        this.module = module;
    }
    
    @Override
    public void init() {
        this.scale = this.rescale(1.0f);
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        if (ModuleConfig.INSTANCE.isEnabled(this.module)) {
            GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 7.0f, Client.getMainColor(30));
            GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width - 1, this.getY() + this.height - 1, 7.0f, 3.0f, new Color(0, 0, 0, 40).getRGB());
        }
        GLRectUtils.drawRoundedOutline(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 7.0f, 3.0f, ModuleConfig.INSTANCE.isEnabled(this.module) ? Client.getMainColor(255 - this.bgFade) : new Color(120, 120, 120, 255 - this.bgFade).getRGB());
        bfl.a(this.scale, this.scale, 1.0f);
        Client.textRenderer.drawStringScaled(this.module.displayName, (int)((this.getX() + 35) / this.scale), (int)((this.getY() + (this.height - 7.5f) / 2.0f) / this.scale), ModuleConfig.INSTANCE.isEnabled(this.module) ? Client.getMainColor(255) : this.disabledColor, this.scale);
        bfl.a(Math.pow(this.scale, -1.0), Math.pow(this.scale, -1.0), 1.0);
        GuiUtils.a(this.getX() + 30, this.getY() + 5, this.getX() + 31, this.getY() + this.height - 5, ModuleConfig.INSTANCE.isEnabled(this.module) ? Client.getMainColor(255) : this.disabledColor);
        if (this.module.hasIcon()) {
            bfl.E();
            this.mc.P().a(this.module.getIcon());
            final int b = (this.module.getTextureIndex() != -1) ? this.module.getTextureIndex() : 24;
            bfl.l();
            GuiUtils.setGlColor(new Color(0, 0, 0, 75).getRGB());
            GuiUtils.drawModalRectWithCustomSizedTexture(this.getX() + 5 + (24 - b) / 2 + 0.75f, this.getY() - 0.5f + (24 - b) / 2 + 0.75f, 0.0f, 0.0f, b, b, (float)b, (float)b);
            GuiUtils.setGlColor(ModuleConfig.INSTANCE.isEnabled(this.module) ? Client.getMainColor(255) : this.disabledColor);
            GuiUtils.drawModalRectWithCustomSizedTexture((float)(this.getX() + 5 + (24 - b) / 2), this.getY() - 0.5f + (24 - b) / 2, 0.0f, 0.0f, b, b, (float)b, (float)b);
            bfl.F();
        }
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.width), (float)(this.getY() + this.height), 7.0f, new Color(0, 0, 0, this.bgFade).getRGB());
    }
    
    @Override
    public void update() {
        if (this.hovered && this.bgFade + 5 <= 70) {
            this.bgFade += 5;
        }
        else if (!this.hovered && this.bgFade - 5 >= 0) {
            this.bgFade -= 5;
        }
    }
    
    private float rescale(final float scale) {
        if (Client.textRenderer.getWidth(this.module.displayName) / (1.0f / scale) > this.width - 37) {
            return this.rescale(scale - 0.05f);
        }
        return scale;
    }
}
