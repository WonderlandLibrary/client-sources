package HORIZON-6-0-SKIDPROTECTION;

public class ServerListEntryLanScan implements GuiListExtended.HorizonCode_Horizon_È
{
    private final Minecraft HorizonCode_Horizon_È;
    private static final String Â = "CL_00000815";
    
    public ServerListEntryLanScan() {
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        final int var9 = y + slotHeight / 2 - this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È / 2;
        final FontRenderer µà = this.HorizonCode_Horizon_È.µà;
        final String horizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("lanServer.scanning", new Object[0]);
        final GuiScreen ¥æ = this.HorizonCode_Horizon_È.¥Æ;
        µà.HorizonCode_Horizon_È(horizonCode_Horizon_È, GuiScreen.Çªà¢ / 2 - this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("lanServer.scanning", new Object[0])) / 2, var9, 16777215);
        String var10 = null;
        switch ((int)(Minecraft.áƒ() / 300L % 4L)) {
            default: {
                var10 = "O o o";
                break;
            }
            case 1:
            case 3: {
                var10 = "o O o";
                break;
            }
            case 2: {
                var10 = "o o O";
                break;
            }
        }
        final FontRenderer µà2 = this.HorizonCode_Horizon_È.µà;
        final String text = var10;
        final GuiScreen ¥æ2 = this.HorizonCode_Horizon_È.¥Æ;
        µà2.HorizonCode_Horizon_È(text, GuiScreen.Çªà¢ / 2 - this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var10) / 2, var9 + this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È, 8421504);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        return false;
    }
    
    @Override
    public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
}
