package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class MessageNotifier
{
    public String HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    public TimeHelper Ø­áŒŠá;
    public TimeHelper Âµá€;
    private int Ó;
    private boolean à;
    
    public MessageNotifier() {
        this.HorizonCode_Horizon_È = "";
        this.Â = 0;
        this.Ý = 0;
        this.Ø­áŒŠá = new TimeHelper();
        this.Âµá€ = new TimeHelper();
        this.Ó = 0;
        this.à = false;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Ø­áŒŠá.Â(1000L)) {
            if (this.Â == 0) {
                this.Â = 0;
                this.à = true;
            }
            --this.Â;
            this.Ø­áŒŠá.Ø­áŒŠá();
        }
        if (this.Âµá€.Â(40L)) {
            ++this.Ý;
            if (this.Ý == 244) {
                this.Ý = 0;
            }
            this.Âµá€.Ø­áŒŠá();
        }
        final int var7 = this.Ý;
        int var8 = (int)(var7 * 255.0f / 20.0f);
        if (var8 > 255) {
            var8 = 255;
        }
        final int var9 = Color.HSBtoRGB(var7 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        if (this.à) {
            --this.Ó;
            if (this.Ó == 0) {
                this.HorizonCode_Horizon_È = "";
            }
        }
        else {
            ++this.Ó;
        }
        if (this.Ó >= 55) {
            this.Ó = 55;
        }
        if (this.HorizonCode_Horizon_È != null && !this.HorizonCode_Horizon_È.equalsIgnoreCase("")) {
            final int stringW = UIFonts.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È) / 2;
            RenderHelper_1118140819.HorizonCode_Horizon_È(GuiScreen.Çªà¢ / 2 - stringW - 4, -10 + this.Ó, GuiScreen.Çªà¢ / 2 + stringW + 4, -10 + this.Ó + 18, 1.0f, -1879048192, 1610612736);
            UIFonts.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, GuiScreen.Çªà¢ / 2 - stringW, -10 + this.Ó, color);
        }
    }
    
    public void HorizonCode_Horizon_È(final String msg, final int delay) {
        this.Ó = 0;
        this.à = false;
        this.HorizonCode_Horizon_È = msg;
        this.Â = delay;
    }
    
    public void HorizonCode_Horizon_È(final String msg, final int delay, final String sound, final Float volume, final Float pitch) {
        if (Minecraft.áŒŠà().á != null) {
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È(sound, volume, pitch);
            this.Ó = 0;
            this.à = false;
            this.HorizonCode_Horizon_È = msg;
            this.Â = delay;
        }
    }
}
