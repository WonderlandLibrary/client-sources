package net.minecraft.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.storage.*;

public class GuiRenameWorld extends GuiScreen
{
    private final String field_146584_g;
    private GuiScreen field_146585_a;
    private GuiTextField field_146583_f;
    
    public GuiRenameWorld(final GuiScreen p_i46317_1_, final String p_i46317_2_) {
        this.field_146585_a = p_i46317_1_;
        this.field_146584_g = p_i46317_2_;
    }
    
    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiRenameWorld.width / 2 - 100, GuiRenameWorld.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        final ISaveFormat var1 = GuiRenameWorld.mc.getSaveLoader();
        final WorldInfo var2 = var1.getWorldInfo(this.field_146584_g);
        final String var3 = var2.getWorldName();
        (this.field_146583_f = new GuiTextField(2, this.fontRendererObj, GuiRenameWorld.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146583_f.setText(var3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                GuiRenameWorld.mc.displayGuiScreen(this.field_146585_a);
            }
            else if (button.id == 0) {
                final ISaveFormat var2 = GuiRenameWorld.mc.getSaveLoader();
                var2.renameWorld(this.field_146584_g, this.field_146583_f.getText().trim());
                GuiRenameWorld.mc.displayGuiScreen(this.field_146585_a);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
        this.buttonList.get(0).enabled = (this.field_146583_f.getText().trim().length() > 0);
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), GuiRenameWorld.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), GuiRenameWorld.width / 2 - 100, 47, 10526880);
        this.field_146583_f.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
