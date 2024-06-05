package net.minecraft.src;

import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;

public class GuiScreenCreateOnlineWorld extends GuiScreen
{
    private GuiScreen field_96260_a;
    private GuiTextField field_96257_c;
    private GuiTextField field_96255_b;
    private String field_98108_c;
    private String field_98109_n;
    private static int field_96253_d;
    private static int field_96261_n;
    private boolean field_96256_r;
    private String field_96254_s;
    
    static {
        GuiScreenCreateOnlineWorld.field_96253_d = 0;
        GuiScreenCreateOnlineWorld.field_96261_n = 1;
    }
    
    public GuiScreenCreateOnlineWorld(final GuiScreen par1GuiScreen) {
        this.field_96256_r = false;
        this.field_96254_s = "You must enter a name!";
        super.buttonList = Collections.synchronizedList(new ArrayList<Object>());
        this.field_96260_a = par1GuiScreen;
    }
    
    @Override
    public void updateScreen() {
        this.field_96257_c.updateCursorCounter();
        this.field_98108_c = this.field_96257_c.getText();
        this.field_96255_b.updateCursorCounter();
        this.field_98109_n = this.field_96255_b.getText();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(GuiScreenCreateOnlineWorld.field_96253_d, this.width / 2 - 100, this.height / 4 + 120 + 17, 97, 20, var1.translateKey("mco.create.world")));
        this.buttonList.add(new GuiButton(GuiScreenCreateOnlineWorld.field_96261_n, this.width / 2 + 5, this.height / 4 + 120 + 17, 95, 20, var1.translateKey("gui.cancel")));
        (this.field_96257_c = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 65, 200, 20)).setFocused(true);
        if (this.field_98108_c != null) {
            this.field_96257_c.setText(this.field_98108_c);
        }
        this.field_96255_b = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 111, 200, 20);
        if (this.field_98109_n != null) {
            this.field_96255_b.setText(this.field_98109_n);
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == GuiScreenCreateOnlineWorld.field_96261_n) {
                this.mc.displayGuiScreen(this.field_96260_a);
            }
            else if (par1GuiButton.id == GuiScreenCreateOnlineWorld.field_96253_d) {
                this.func_96252_h();
            }
        }
    }
    
    private void func_96252_h() {
        if (this.func_96249_i()) {
            final TaskWorldCreation var1 = new TaskWorldCreation(this, this.field_96257_c.getText(), "Minecraft Realms Server", "NO_LOCATION", this.field_98109_n);
            final GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.mc, this.field_96260_a, var1);
            var2.func_98117_g();
            this.mc.displayGuiScreen(var2);
        }
    }
    
    private boolean func_96249_i() {
        this.field_96256_r = (this.field_96257_c.getText() == null || this.field_96257_c.getText().trim().equals(""));
        return !this.field_96256_r;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.field_96257_c.textboxKeyTyped(par1, par2);
        this.field_96255_b.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            this.field_96257_c.setFocused(!this.field_96257_c.isFocused());
            this.field_96255_b.setFocused(!this.field_96255_b.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_96257_c.mouseClicked(par1, par2, par3);
        this.field_96255_b.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.selectServer.create"), this.width / 2, 11, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.name"), this.width / 2 - 100, 52, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.seed"), this.width / 2 - 100, 98, 10526880);
        if (this.field_96256_r) {
            this.drawCenteredString(this.fontRenderer, this.field_96254_s, this.width / 2, 167, 16711680);
        }
        this.field_96257_c.drawTextBox();
        this.field_96255_b.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    static Minecraft func_96248_a(final GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld) {
        return par0GuiScreenCreateOnlineWorld.mc;
    }
    
    static GuiScreen func_96247_b(final GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld) {
        return par0GuiScreenCreateOnlineWorld.field_96260_a;
    }
    
    static Minecraft func_96246_c(final GuiScreenCreateOnlineWorld par0GuiScreenCreateOnlineWorld) {
        return par0GuiScreenCreateOnlineWorld.mc;
    }
}
