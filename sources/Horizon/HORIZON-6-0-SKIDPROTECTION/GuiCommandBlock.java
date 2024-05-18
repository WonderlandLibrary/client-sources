package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private GuiTextField Â;
    private GuiTextField Ý;
    private final CommandBlockLogic Ø­áŒŠá;
    private GuiButton Âµá€;
    private GuiButton Ó;
    private GuiButton à;
    private boolean Ø;
    private static final String áŒŠÆ = "CL_00000748";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public GuiCommandBlock(final CommandBlockLogic p_i45032_1_) {
        this.Ø­áŒŠá = p_i45032_1_;
    }
    
    @Override
    public void Ý() {
        this.Â.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(this.Âµá€ = new GuiMenuButton(0, GuiCommandBlock.Çªà¢ / 2 - 4 - 150, GuiCommandBlock.Ê / 4 + 120 + 12, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.ÇŽÉ.add(this.Ó = new GuiMenuButton(1, GuiCommandBlock.Çªà¢ / 2 + 4, GuiCommandBlock.Ê / 4 + 120 + 12, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(this.à = new GuiMenuButton(4, GuiCommandBlock.Çªà¢ / 2 + 150 - 20, 150, 20, 20, "O"));
        (this.Â = new GuiTextField(2, UIFonts.Ý, GuiCommandBlock.Çªà¢ / 2 - 150, 50, 300, 20)).Ó(32767);
        this.Â.Â(true);
        this.Â.HorizonCode_Horizon_È(this.Ø­áŒŠá.áŒŠÆ());
        (this.Ý = new GuiTextField(3, UIFonts.Ý, GuiCommandBlock.Çªà¢ / 2 - 150, 150, 276, 20)).Ó(32767);
        this.Ý.Ý(false);
        this.Ý.HorizonCode_Horizon_È("-");
        this.Ø = this.Ø­áŒŠá.á();
        this.Ó();
        this.Âµá€.µà = (this.Â.Ý().trim().length() > 0);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Ø);
                GuiCommandBlock.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            }
            else if (button.£à == 0) {
                final PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
                var2.writeByte(this.Ø­áŒŠá.ÂµÈ());
                this.Ø­áŒŠá.HorizonCode_Horizon_È(var2);
                var2.HorizonCode_Horizon_È(this.Â.Ý());
                var2.writeBoolean(this.Ø­áŒŠá.á());
                GuiCommandBlock.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C17PacketCustomPayload("MC|AdvCdm", var2));
                if (!this.Ø­áŒŠá.á()) {
                    this.Ø­áŒŠá.Â((IChatComponent)null);
                }
                GuiCommandBlock.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            }
            else if (button.£à == 4) {
                this.Ø­áŒŠá.HorizonCode_Horizon_È(!this.Ø­áŒŠá.á());
                this.Ó();
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.Â.HorizonCode_Horizon_È(typedChar, keyCode);
        this.Ý.HorizonCode_Horizon_È(typedChar, keyCode);
        this.Âµá€.µà = (this.Â.Ý().trim().length() > 0);
        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.HorizonCode_Horizon_È(this.Ó);
            }
        }
        else {
            this.HorizonCode_Horizon_È(this.Âµá€);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Â.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.setCommand", new Object[0]), GuiCommandBlock.Çªà¢ / 2, 20, 16777215);
        Gui_1808253012.Â(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.command", new Object[0]), GuiCommandBlock.Çªà¢ / 2 - 150, 37, 10526880);
        this.Â.HorizonCode_Horizon_È();
        final byte var4 = 75;
        final byte var5 = 0;
        final FontRenderer var6 = UIFonts.Ý;
        final String var7 = I18n.HorizonCode_Horizon_È("advMode.nearestPlayer", new Object[0]);
        final int var8 = GuiCommandBlock.Çªà¢ / 2 - 150;
        int var9 = var5 + 1;
        Gui_1808253012.Â(var6, var7, var8, var4 + var5 * this.É.HorizonCode_Horizon_È, 10526880);
        Gui_1808253012.Â(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.randomPlayer", new Object[0]), GuiCommandBlock.Çªà¢ / 2 - 150, var4 + var9++ * this.É.HorizonCode_Horizon_È, 10526880);
        Gui_1808253012.Â(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.allPlayers", new Object[0]), GuiCommandBlock.Çªà¢ / 2 - 150, var4 + var9++ * this.É.HorizonCode_Horizon_È, 10526880);
        Gui_1808253012.Â(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.allEntities", new Object[0]), GuiCommandBlock.Çªà¢ / 2 - 150, var4 + var9++ * this.É.HorizonCode_Horizon_È, 10526880);
        Gui_1808253012.Â(UIFonts.Ý, "", GuiCommandBlock.Çªà¢ / 2 - 150, var4 + var9++ * this.É.HorizonCode_Horizon_È, 10526880);
        if (this.Ý.Ý().length() > 0) {
            final int var10 = var4 + var9 * this.É.HorizonCode_Horizon_È + 16;
            Gui_1808253012.Â(UIFonts.Ý, I18n.HorizonCode_Horizon_È("advMode.previousOutput", new Object[0]), GuiCommandBlock.Çªà¢ / 2 - 150, var10, 10526880);
            this.Ý.HorizonCode_Horizon_È();
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    private void Ó() {
        if (this.Ø­áŒŠá.á()) {
            this.à.Å = "O";
            if (this.Ø­áŒŠá.Ø() != null) {
                this.Ý.HorizonCode_Horizon_È(this.Ø­áŒŠá.Ø().Ø());
            }
        }
        else {
            this.à.Å = "X";
            this.Ý.HorizonCode_Horizon_È("-");
        }
    }
}
