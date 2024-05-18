package net.minecraft.src;

import org.lwjgl.input.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class GuiScreenSubscription extends GuiScreen
{
    private final GuiScreen field_98067_a;
    private final McoServer field_98065_b;
    private final int field_98066_c = 0;
    private final int field_98064_d = 1;
    private int field_98068_n;
    private String field_98069_o;
    
    public GuiScreenSubscription(final GuiScreen par1GuiScreen, final McoServer par2McoServer) {
        this.field_98067_a = par1GuiScreen;
        this.field_98065_b = par2McoServer;
    }
    
    @Override
    public void updateScreen() {
    }
    
    @Override
    public void initGui() {
        this.func_98063_a(this.field_98065_b.field_96408_a);
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
    }
    
    private void func_98063_a(final long par1) {
        final McoClient var3 = new McoClient(this.mc.session);
        try {
            final ValueObjectSubscription var4 = var3.func_98177_f(par1);
            this.field_98068_n = var4.field_98170_b;
            this.field_98069_o = this.func_98062_b(var4.field_98171_a);
        }
        catch (ExceptionMcoService exceptionMcoService) {}
        catch (IOException ex) {}
    }
    
    private String func_98062_b(final long par1) {
        final GregorianCalendar var3 = new GregorianCalendar(TimeZone.getDefault());
        var3.setTimeInMillis(par1);
        return DateFormat.getDateTimeInstance().format(var3.getTime());
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 0) {
                this.mc.displayGuiScreen(this.field_98067_a);
            }
            else if (par1GuiButton.id == 1) {}
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.configure.world.subscription.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.subscription.start"), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRenderer, this.field_98069_o, this.width / 2 - 100, 66, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("mco.configure.world.subscription.daysleft"), this.width / 2 - 100, 85, 10526880);
        this.drawString(this.fontRenderer, String.valueOf(this.field_98068_n), this.width / 2 - 100, 98, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
