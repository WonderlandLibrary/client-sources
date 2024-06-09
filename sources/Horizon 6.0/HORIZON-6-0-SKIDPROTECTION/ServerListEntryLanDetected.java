package HORIZON-6-0-SKIDPROTECTION;

public class ServerListEntryLanDetected implements GuiListExtended.HorizonCode_Horizon_È
{
    private final GuiMultiplayer Ý;
    protected final Minecraft HorizonCode_Horizon_È;
    protected final LanServerDetector.HorizonCode_Horizon_È Â;
    private long Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000816";
    
    protected ServerListEntryLanDetected(final GuiMultiplayer p_i45046_1_, final LanServerDetector.HorizonCode_Horizon_È p_i45046_2_) {
        this.Ø­áŒŠá = 0L;
        this.Ý = p_i45046_1_;
        this.Â = p_i45046_2_;
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("lanServer.title", new Object[0]), x + 32 + 3, y + 1, 16777215);
        this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È(), x + 32 + 3, y + 12, 8421504);
        if (this.HorizonCode_Horizon_È.ŠÄ.Ðƒà) {
            this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("selectServer.hiddenAddress", new Object[0]), x + 32 + 3, y + 12 + 11, 3158064);
        }
        else {
            this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(this.Â.Â(), x + 32 + 3, y + 12 + 11, 3158064);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        this.Ý.HorizonCode_Horizon_È(p_148278_1_);
        if (Minecraft.áƒ() - this.Ø­áŒŠá < 250L) {
            this.Ý.à();
        }
        this.Ø­áŒŠá = Minecraft.áƒ();
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
    
    public LanServerDetector.HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.Â;
    }
}
