package net.minecraft.src;

import org.lwjgl.input.*;
import net.minecraft.client.*;

public class GuiScreenResetWorld extends GuiScreen
{
    private GuiScreen field_96152_a;
    private McoServer field_96150_b;
    private GuiTextField field_96151_c;
    private final int field_96149_d = 1;
    private final int field_96153_n = 2;
    private GuiButton field_96154_o;
    
    public GuiScreenResetWorld(final GuiScreen par1, final McoServer par2) {
        this.field_96152_a = par1;
        this.field_96150_b = par2;
    }
    
    @Override
    public void updateScreen() {
        this.field_96151_c.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_96154_o = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("mco.configure.world.buttons.reset")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        (this.field_96151_c = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 109, 200, 20)).setFocused(true);
        this.field_96151_c.setMaxStringLength(32);
        this.field_96151_c.setText("");
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.field_96151_c.textboxKeyTyped(par1, par2);
        if (par1 == '\r') {
            this.actionPerformed(this.field_96154_o);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 2) {
                this.mc.displayGuiScreen(this.field_96152_a);
            }
            else if (par1GuiButton.id == 1) {
                final TaskResetWorld var2 = new TaskResetWorld(this, this.field_96150_b.field_96408_a, this.field_96151_c.getText());
                final GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this.field_96152_a, var2);
                var3.func_98117_g();
                this.mc.displayGuiScreen(var3);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_96151_c.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.reset.world.title"), this.width / 2, 17, 16777215);
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.reset.world.warning"), this.width / 2, 66, 16711680);
        this.drawString(this.fontRenderer, var4.translateKey("mco.reset.world.seed"), this.width / 2 - 100, 96, 10526880);
        this.field_96151_c.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    static GuiScreen func_96148_a(final GuiScreenResetWorld par0GuiScreenResetWorld) {
        return par0GuiScreenResetWorld.field_96152_a;
    }
    
    static Minecraft func_96147_b(final GuiScreenResetWorld par0GuiScreenResetWorld) {
        return par0GuiScreenResetWorld.mc;
    }
}
