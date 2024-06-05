package net.minecraft.src;

import org.lwjgl.input.*;
import java.io.*;

public class GuiScreenInvite extends GuiScreen
{
    private GuiTextField field_96227_a;
    private McoServer field_96223_b;
    private final GuiScreen field_96224_c;
    private final GuiScreenConfigureWorld field_96222_d;
    private final int field_96228_n = 0;
    private final int field_96229_o = 1;
    private String field_101016_p;
    private String field_96226_p;
    private boolean field_96225_q;
    
    public GuiScreenInvite(final GuiScreen par1GuiScreen, final GuiScreenConfigureWorld par2GuiScreenConfigureWorld, final McoServer par3McoServer) {
        this.field_101016_p = "Could not invite the provided name";
        this.field_96225_q = false;
        this.field_96224_c = par1GuiScreen;
        this.field_96222_d = par2GuiScreenConfigureWorld;
        this.field_96223_b = par3McoServer;
    }
    
    @Override
    public void updateScreen() {
        this.field_96227_a.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("mco.configure.world.buttons.invite")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        (this.field_96227_a = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 66, 200, 20)).setFocused(true);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.mc.displayGuiScreen(this.field_96222_d);
            }
            else if (par1GuiButton.id == 0) {
                final McoClient var2 = new McoClient(this.mc.session);
                try {
                    final McoServer var3 = var2.func_96387_b(this.field_96223_b.field_96408_a, this.field_96227_a.getText());
                    if (var3 != null) {
                        this.field_96223_b.field_96402_f = var3.field_96402_f;
                        this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_96224_c, this.field_96223_b));
                    }
                    else {
                        this.func_101015_a(this.field_101016_p);
                    }
                }
                catch (ExceptionMcoService var4) {
                    this.func_101015_a(var4.field_96391_b);
                }
                catch (IOException var5) {
                    this.func_101015_a(this.field_101016_p);
                }
            }
        }
    }
    
    private void func_101015_a(final String par1Str) {
        this.field_96225_q = true;
        this.field_96226_p = par1Str;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.field_96227_a.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            if (this.field_96227_a.isFocused()) {
                this.field_96227_a.setFocused(false);
            }
            else {
                this.field_96227_a.setFocused(true);
            }
        }
        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_96227_a.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey(""), this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.invite.profile.name"), this.width / 2 - 100, 53, 10526880);
        if (this.field_96225_q) {
            this.drawCenteredString(this.fontRenderer, this.field_96226_p, this.width / 2, 100, 16711680);
        }
        this.field_96227_a.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
