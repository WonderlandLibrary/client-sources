// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.gui.components.items.buttons;

import me.perry.mcdonalds.features.setting.Bind;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import me.perry.mcdonalds.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.features.gui.McDonaldsGui;
import me.perry.mcdonalds.util.RenderUtil;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.util.ColorUtil;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import me.perry.mcdonalds.features.setting.Setting;

public class BindButton extends Button
{
    private final Setting setting;
    public boolean isListening;
    
    public BindButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final int color = ColorUtil.toARGB(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue(), 255);
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077) : (this.isHovering(mouseX, mouseY) ? McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : McDonalds.colorManager.getColorWithAlpha(McDonalds.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())));
        if (this.isListening) {
            McDonalds.textManager.drawStringWithShadow("Press a Key...", this.x + 2.3f, this.y - 1.7f - McDonaldsGui.getClickGui().getTextOffset(), -1);
        }
        else {
            McDonalds.textManager.drawStringWithShadow(this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue().toString().toUpperCase(), this.x + 2.3f, this.y - 1.7f - McDonaldsGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
    }
    
    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            Util.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    @Override
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isListening) {
            Bind bind = new Bind(keyCode);
            if (bind.toString().equalsIgnoreCase("Escape")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Delete")) {
                bind = new Bind(-1);
            }
            this.setting.setValue(bind);
            this.onMouseClick();
        }
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    @Override
    public void toggle() {
        this.isListening = !this.isListening;
    }
    
    @Override
    public boolean getState() {
        return !this.isListening;
    }
}
