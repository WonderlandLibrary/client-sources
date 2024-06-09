package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiCustomizeSkin extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private String Â;
    private static final String Ý = "CL_00001932";
    
    public GuiCustomizeSkin(final GuiScreen p_i45516_1_) {
        this.HorizonCode_Horizon_È = p_i45516_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int var1 = 0;
        this.Â = I18n.HorizonCode_Horizon_È("options.skinCustomisation.title", new Object[0]);
        for (final EnumPlayerModelParts var5 : EnumPlayerModelParts.values()) {
            this.ÇŽÉ.add(new HorizonCode_Horizon_È(var5.Â(), GuiCustomizeSkin.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiCustomizeSkin.Ê / 6 + 24 * (var1 >> 1), 150, 20, var5, null));
            ++var1;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.ÇŽÉ.add(new GuiMenuButton(200, GuiCustomizeSkin.Çªà¢ / 2 - 100, GuiCustomizeSkin.Ê / 6 + 24 * (var1 >> 1), I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 200) {
                GuiCustomizeSkin.Ñ¢á.ŠÄ.Â();
                GuiCustomizeSkin.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
            else if (button instanceof HorizonCode_Horizon_È) {
                final EnumPlayerModelParts var2 = ((HorizonCode_Horizon_È)button).Â;
                GuiCustomizeSkin.Ñ¢á.ŠÄ.HorizonCode_Horizon_È(var2);
                button.Å = this.HorizonCode_Horizon_È(var2);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiCustomizeSkin.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiCustomizeSkin.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    private String HorizonCode_Horizon_È(final EnumPlayerModelParts p_175358_1_) {
        String var2;
        if (GuiCustomizeSkin.Ñ¢á.ŠÄ.Ø­áŒŠá().contains(p_175358_1_)) {
            var2 = I18n.HorizonCode_Horizon_È("options.on", new Object[0]);
        }
        else {
            var2 = I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        }
        return String.valueOf(p_175358_1_.Ø­áŒŠá().áŒŠÆ()) + ": " + var2;
    }
    
    class HorizonCode_Horizon_È extends GuiButton
    {
        private final EnumPlayerModelParts Â;
        private static final String Ý = "CL_00001930";
        
        private HorizonCode_Horizon_È(final int p_i45514_2_, final int p_i45514_3_, final int p_i45514_4_, final int p_i45514_5_, final int p_i45514_6_, final EnumPlayerModelParts p_i45514_7_) {
            super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.HorizonCode_Horizon_È(p_i45514_7_));
            this.Â = p_i45514_7_;
        }
        
        HorizonCode_Horizon_È(final GuiCustomizeSkin guiCustomizeSkin, final int p_i45515_2_, final int p_i45515_3_, final int p_i45515_4_, final int p_i45515_5_, final int p_i45515_6_, final EnumPlayerModelParts p_i45515_7_, final Object p_i45515_8_) {
            this(guiCustomizeSkin, p_i45515_2_, p_i45515_3_, p_i45515_4_, p_i45515_5_, p_i45515_6_, p_i45515_7_);
        }
    }
}
