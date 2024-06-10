// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.clickgui.element.elements;

import org.lwjgl.input.Mouse;

import me.kaktuswasser.client.clickgui.element.Element;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.NahrFont;
import me.kaktuswasser.client.utilities.RenderHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class ElementModuleButton extends Element
{
    public static List<ElementValueSliderBox> checkboxes;
    public static List<ElementDropdownMenu> dropDowns;
    public static List<ElementValueSlider> sliders;
    protected int boxy;
    protected Module module;
    protected Module.Category category;
    protected boolean extra;
    protected boolean hover;
    protected boolean clicked;
    protected float textWidth;
    protected float superText;
    
    static {
        ElementModuleButton.checkboxes = new ArrayList<ElementValueSliderBox>();
        ElementModuleButton.dropDowns = new ArrayList<ElementDropdownMenu>();
        ElementModuleButton.sliders = new ArrayList<ElementValueSlider>();
    }
    
    public ElementModuleButton(final Module module, final Module.Category category) {
        this.boxy = this.getY() + 1;
        this.extra = false;
        this.setModule(module);
        this.setCategory(category);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.clicked = true;
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.getModule().toggle();
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 0.75f);
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 0.25f);
            this.extra = !this.extra;
            return;
        }
        if (this.extra) {
            for (final ElementValueSliderBox checkbox : ElementModuleButton.checkboxes) {
                checkbox.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for (final ElementDropdownMenu dropDown : ElementModuleButton.dropDowns) {
                dropDown.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for (final ElementValueSlider slider : ElementModuleButton.sliders) {
                slider.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.extra) {
            for (final ElementValueSliderBox checkbox : ElementModuleButton.checkboxes) {
                checkbox.mouseReleased(mouseX, mouseY, state);
            }
            for (final ElementValueSlider slider : ElementModuleButton.sliders) {
                slider.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        final float width = RenderHelper.getNahrFont().getStringWidth(this.getText());
        RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + 17, 1.0f, -16777216, this.getEnabled() ? 0x9971f442 : Integer.MIN_VALUE);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + 17, 0x4071f442);
        }
        RenderHelper.getNahrFont().drawString(this.getText(), this.getX() - (width - 100.0f) / 2.0f, this.getY() + 2, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
        if (this.extra) {
            RenderHelper.drawBorderedCorneredRect(this.getX() + this.getWidth() + 6, this.getY() + 3, this.getX() + this.getWidth() + this.textWidth + 28.0f, this.boxy + 2, 1.0f, 0x9971f442, -580294295);
            this.textWidth = 0.0f;
            this.boxy = 0;
            int boxy = this.getY() + 1;
            for (final ElementValueSliderBox checkbox : ElementModuleButton.checkboxes) {
                if (checkbox.getValue().getModule().equals(this.module)) {
                    checkbox.setLocation(this.x + this.getWidth() + 8, boxy);
                    checkbox.setWidth(12);
                    checkbox.setHeight(14);
                    checkbox.drawScreen(mouseX, mouseY, button);
                    boxy += checkbox.getHeight();
                    if (this.textWidth < checkbox.getTextWidth()) {
                        this.textWidth = checkbox.getTextWidth();
                    }
                    checkbox.setMaxWidth(this.textWidth + 12.0f);
                }
            }
            for (final ElementValueSlider slider : ElementModuleButton.sliders) {
                if (slider.getValue().getModule().equals(this.module)) {
                    slider.setLocation(this.x + this.getWidth() + 8, boxy);
                    slider.setMinSlide((int)((this.superText > this.textWidth + 18.0f) ? this.superText : (this.textWidth + 18.0f)) - slider.getWidth());
                    slider.setMaxSlide((int)((this.superText > this.textWidth + 18.0f) ? this.superText : (this.textWidth + 18.0f)));
                    slider.setWidth((int)((this.superText > this.textWidth + 18.0f) ? this.superText : (this.textWidth + 18.0f)));
                    slider.setHeight(20);
                    if (this.superText < slider.getTextWidth() + 2.0f) {
                        this.superText = slider.getTextWidth() + 2.0f;
                    }
                    if (this.textWidth < slider.getWidth() - 18) {
                        this.textWidth = slider.getWidth() - 18;
                    }
                    slider.drawScreen(mouseX, mouseY, button);
                    boxy += slider.getHeight();
                }
            }
            for (final ElementDropdownMenu dropDown : ElementModuleButton.dropDowns) {
                if (dropDown.getValue().getModule().equals(this.module)) {
                    dropDown.setLocation(this.x + this.getWidth() + 8, boxy);
                    dropDown.setWidth((int)((this.superText > this.textWidth + 18.0f) ? this.superText : (this.textWidth + 18.0f)));
                    dropDown.setHeight(20);
                    if (dropDown.isOpen()) {
                        for (int i = 0; i < dropDown.getValue().getStringValues().length; ++i) {
                            final String text = RenderHelper.getPrettyName(dropDown.getValue().getStringValues()[i], "_");
                            RenderHelper.drawBorderedCorneredRect(this.x + this.getWidth() + 12, boxy + 24, this.x + this.getWidth() + 4 + dropDown.getWidth(), boxy + dropDown.getHeight() + 16, 1.0f, 0x9971f442, dropDown.getValue().getStringValue().equals(dropDown.getValue().getStringValues()[i]) ? -2137603890 : -2145049307);
                            if (RenderHelper.isHovering(this.x + this.getWidth() + 12, boxy + 24, this.x + this.getWidth() + 4 + dropDown.getWidth(), boxy + dropDown.getHeight() + 16, mouseX, mouseY)) {
                                RenderHelper.drawRect(this.x + this.getWidth() + 12, boxy + 24, this.x + this.getWidth() + 4 + dropDown.getWidth(), boxy + dropDown.getHeight() + 16, 0x8071f442);
                                if (Mouse.isButtonDown(0) && this.clicked) {
                                    Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 0.75f);
                                    dropDown.getValue().setStringValue(dropDown.getValue().getStringValues()[i]);
                                    this.clicked = false;
                                }
                            }
                            RenderHelper.getNahrFont().drawString(text, this.x + this.getWidth() + dropDown.getWidth() / 2 - RenderHelper.getNahrFont().getStringWidth(text) / 2.0f + 10.0f, boxy + dropDown.getHeight() + 2, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
                            boxy += dropDown.getHeight() - 4;
                        }
                    }
                    if (this.superText < dropDown.getTextWidth() + 2.0f) {
                        this.superText = dropDown.getTextWidth() + 2.0f;
                    }
                    if (this.textWidth < dropDown.getWidth() - 18) {
                        this.textWidth = dropDown.getWidth() - 18;
                    }
                    dropDown.drawScreen(mouseX, mouseY, button);
                    boxy += dropDown.getHeight();
                }
            }
            if (this.boxy < boxy) {
                this.boxy = boxy;
            }
        }
    }
    
    @Override
    public int getHeight() {
        return 16;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public String getText() {
        return this.getModule().getName();
    }
    
    public Module.Category getCategory() {
        return this.category;
    }
    
    public boolean getEnabled() {
        return this.getModule().isEnabled();
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.width && mouseY >= this.getY() + 3 && mouseY <= this.getY() + 17;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public void setCategory(final Module.Category category) {
        this.category = category;
    }
}
