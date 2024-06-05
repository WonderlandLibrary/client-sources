package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;

public class GuiCustomizeSkin extends GuiScreen
{
    private final GuiScreen field_175361_a;
    private String field_175360_f;
    
    public GuiCustomizeSkin(final GuiScreen p_i45516_1_) {
        this.field_175361_a = p_i45516_1_;
    }
    
    @Override
    public void initGui() {
        int var1 = 0;
        this.field_175360_f = I18n.format("options.skinCustomisation.title", new Object[0]);
        for (final EnumPlayerModelParts var5 : EnumPlayerModelParts.values()) {
            this.buttonList.add(new ButtonPart(var5.func_179328_b(), GuiCustomizeSkin.width / 2 - 155 + var1 % 2 * 160, GuiCustomizeSkin.height / 6 + 24 * (var1 >> 1), 150, 20, var5, null));
            ++var1;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.buttonList.add(new GuiButton(200, GuiCustomizeSkin.width / 2 - 100, GuiCustomizeSkin.height / 6 + 24 * (var1 >> 1), I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 200) {
                GuiCustomizeSkin.mc.gameSettings.saveOptions();
                GuiCustomizeSkin.mc.displayGuiScreen(this.field_175361_a);
            }
            else if (button instanceof ButtonPart) {
                final EnumPlayerModelParts var2 = ((ButtonPart)button).field_175234_p;
                GuiCustomizeSkin.mc.gameSettings.func_178877_a(var2);
                button.displayString = this.func_175358_a(var2);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_175360_f, GuiCustomizeSkin.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private String func_175358_a(final EnumPlayerModelParts p_175358_1_) {
        String var2;
        if (GuiCustomizeSkin.mc.gameSettings.func_178876_d().contains(p_175358_1_)) {
            var2 = I18n.format("options.on", new Object[0]);
        }
        else {
            var2 = I18n.format("options.off", new Object[0]);
        }
        return p_175358_1_.func_179326_d().getFormattedText() + ": " + var2;
    }
    
    class ButtonPart extends GuiButton
    {
        private final EnumPlayerModelParts field_175234_p;
        
        private ButtonPart(final int p_i45514_2_, final int p_i45514_3_, final int p_i45514_4_, final int p_i45514_5_, final int p_i45514_6_, final EnumPlayerModelParts p_i45514_7_) {
            super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(p_i45514_7_));
            this.field_175234_p = p_i45514_7_;
        }
        
        ButtonPart(final GuiCustomizeSkin this$0, final int p_i45515_2_, final int p_i45515_3_, final int p_i45515_4_, final int p_i45515_5_, final int p_i45515_6_, final EnumPlayerModelParts p_i45515_7_, final Object p_i45515_8_) {
            this(this$0, p_i45515_2_, p_i45515_3_, p_i45515_4_, p_i45515_5_, p_i45515_6_, p_i45515_7_);
        }
    }
}
