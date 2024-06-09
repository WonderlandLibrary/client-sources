package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiShareToLan extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private GuiButton Â;
    private GuiButton Ý;
    private String Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00000713";
    
    public GuiShareToLan(final GuiScreen p_i1055_1_) {
        this.Ø­áŒŠá = "survival";
        this.HorizonCode_Horizon_È = p_i1055_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiMenuButton(101, GuiShareToLan.Çªà¢ / 2 - 155, GuiShareToLan.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("lanServer.start", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(102, GuiShareToLan.Çªà¢ / 2 + 5, GuiShareToLan.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(this.Ý = new GuiButton(104, GuiShareToLan.Çªà¢ / 2 - 155, 100, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.gameMode", new Object[0])));
        this.ÇŽÉ.add(this.Â = new GuiButton(103, GuiShareToLan.Çªà¢ / 2 + 5, 100, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.allowCommands", new Object[0])));
        this.Ó();
    }
    
    private void Ó() {
        this.Ý.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.gameMode", new Object[0])) + " " + I18n.HorizonCode_Horizon_È("selectWorld.gameMode." + this.Ø­áŒŠá, new Object[0]);
        this.Â.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.allowCommands", new Object[0])) + " ";
        if (this.Âµá€) {
            this.Â.Å = String.valueOf(this.Â.Å) + I18n.HorizonCode_Horizon_È("options.on", new Object[0]);
        }
        else {
            this.Â.Å = String.valueOf(this.Â.Å) + I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 102) {
            GuiShareToLan.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        }
        else if (button.£à == 104) {
            if (this.Ø­áŒŠá.equals("spectator")) {
                this.Ø­áŒŠá = "creative";
            }
            else if (this.Ø­áŒŠá.equals("creative")) {
                this.Ø­áŒŠá = "adventure";
            }
            else if (this.Ø­áŒŠá.equals("adventure")) {
                this.Ø­áŒŠá = "survival";
            }
            else {
                this.Ø­áŒŠá = "spectator";
            }
            this.Ó();
        }
        else if (button.£à == 103) {
            this.Âµá€ = !this.Âµá€;
            this.Ó();
        }
        else if (button.£à == 101) {
            GuiShareToLan.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            final String var2 = GuiShareToLan.Ñ¢á.ˆá().HorizonCode_Horizon_È(WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø­áŒŠá), this.Âµá€);
            Object var3;
            if (var2 != null) {
                var3 = new ChatComponentTranslation("commands.publish.started", new Object[] { var2 });
            }
            else {
                var3 = new ChatComponentText("commands.publish.failed");
            }
            GuiShareToLan.Ñ¢á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È((IChatComponent)var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("lanServer.title", new Object[0]), GuiShareToLan.Çªà¢ / 2, 50, 16777215);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("lanServer.otherPlayers", new Object[0]), GuiShareToLan.Çªà¢ / 2, 82, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
