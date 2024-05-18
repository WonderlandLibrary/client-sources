package HORIZON-6-0-SKIDPROTECTION;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private int Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000707";
    
    public GuiScreenWorking() {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
    }
    
    @Override
    public void Â(final String message) {
        this.HorizonCode_Horizon_È(message);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_73721_1_) {
        this.HorizonCode_Horizon_È = p_73721_1_;
        this.Ý("Working...");
    }
    
    @Override
    public void Ý(final String message) {
        this.Â = message;
        this.HorizonCode_Horizon_È(0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int progress) {
        this.Ý = progress;
    }
    
    @Override
    public void p_() {
        this.Ø­áŒŠá = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.Ø­áŒŠá) {
            GuiScreenWorking.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
        else {
            this.£á();
            this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiScreenWorking.Çªà¢ / 2, 70, 16777215);
            this.HorizonCode_Horizon_È(this.É, String.valueOf(this.Â) + " " + this.Ý + "%", GuiScreenWorking.Çªà¢ / 2, 90, 16777215);
            super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        }
    }
}
