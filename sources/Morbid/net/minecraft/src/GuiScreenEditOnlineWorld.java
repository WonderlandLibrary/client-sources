package net.minecraft.src;

import org.lwjgl.input.*;
import java.util.*;

public class GuiScreenEditOnlineWorld extends GuiScreen
{
    private GuiScreen field_96204_a;
    private GuiScreen field_96202_b;
    private GuiTextField field_96203_c;
    private GuiTextField field_96201_d;
    private McoServer field_96205_n;
    private GuiButton field_96206_o;
    private int field_104054_p;
    private int field_104053_q;
    private int field_104052_r;
    private GuiScreenOnlineServersSubscreen field_104051_s;
    
    public GuiScreenEditOnlineWorld(final GuiScreen par1GuiScreen, final GuiScreen par2GuiScreen, final McoServer par3McoServer) {
        this.field_96204_a = par1GuiScreen;
        this.field_96202_b = par2GuiScreen;
        this.field_96205_n = par3McoServer;
    }
    
    @Override
    public void updateScreen() {
        this.field_96201_d.updateCursorCounter();
        this.field_96203_c.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.field_104054_p = this.width / 4;
        this.field_104053_q = this.width / 4 - 2;
        this.field_104052_r = this.width / 2 + 4;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_96206_o = new GuiButton(0, this.field_104054_p, this.height / 4 + 120 + 22, this.field_104053_q, 20, var1.translateKey("mco.configure.world.buttons.done")));
        this.buttonList.add(new GuiButton(1, this.field_104052_r, this.height / 4 + 120 + 22, this.field_104053_q, 20, var1.translateKey("gui.cancel")));
        (this.field_96201_d = new GuiTextField(this.fontRenderer, this.field_104054_p, 56, 212, 20)).setFocused(true);
        this.field_96201_d.setMaxStringLength(32);
        this.field_96201_d.setText(this.field_96205_n.func_96398_b());
        (this.field_96203_c = new GuiTextField(this.fontRenderer, this.field_104054_p, 96, 212, 20)).setMaxStringLength(32);
        this.field_96203_c.setText(this.field_96205_n.func_96397_a());
        this.field_104051_s = new GuiScreenOnlineServersSubscreen(this.width, this.height, this.field_104054_p, 122);
        this.buttonList.addAll(this.field_104051_s.field_104079_a);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.mc.displayGuiScreen(this.field_96204_a);
            }
            else if (par1GuiButton.id == 0) {
                this.func_96200_g();
            }
            else if (par1GuiButton.id == 2) {
                this.mc.displayGuiScreen(new GuiScreenResetWorld(this, this.field_96205_n));
            }
            else {
                this.field_104051_s.func_104069_a(par1GuiButton);
            }
        }
    }
    
    private void func_96200_g() {
        final McoClient var1 = new McoClient(this.mc.session);
        try {
            final String var2 = (this.field_96203_c.getText() != null && !this.field_96203_c.getText().trim().equals("")) ? this.field_96203_c.getText() : "";
            var1.func_96384_a(this.field_96205_n.field_96408_a, this.field_96201_d.getText(), var2, this.field_104051_s.field_104076_e, this.field_104051_s.field_104073_f);
            this.field_96205_n.func_96399_a(this.field_96201_d.getText());
            this.field_96205_n.func_96400_b(this.field_96203_c.getText());
            this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_96202_b, this.field_96205_n));
        }
        catch (Exception ex) {}
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.field_96201_d.textboxKeyTyped(par1, par2);
        this.field_96203_c.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            if (this.field_96201_d.isFocused()) {
                this.field_96201_d.setFocused(false);
                this.field_96203_c.setFocused(true);
            }
            else {
                this.field_96201_d.setFocused(true);
                this.field_96203_c.setFocused(false);
            }
        }
        if (par1 == '\r') {
            this.func_96200_g();
        }
        this.field_96206_o.enabled = (this.field_96201_d.getText() != null && !this.field_96201_d.getText().trim().equals(""));
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_96203_c.mouseClicked(par1, par2, par3);
        this.field_96201_d.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.configure.world.edit.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.name"), this.field_104054_p, 43, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.description"), this.field_104054_p, 84, 10526880);
        this.field_96201_d.drawTextBox();
        this.field_96203_c.drawTextBox();
        this.field_104051_s.func_104071_a(this, this.fontRenderer);
        super.drawScreen(par1, par2, par3);
    }
}
