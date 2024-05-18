package wwe;

import net.minecraft.client.gui.*;

public enum DlwW
{
    UyOo("Font Colors", (GuiScreen)new rJxT()), 
    mQOB("Tab Gui Colors", (GuiScreen)new uIsz()), 
    bOQy("Module Element Colors", (GuiScreen)new Knov()), 
    CfSS("TPS Element Colors", (GuiScreen)new fdhW());
    
    String aohU;
    GuiScreen kebz;
    private static final DlwW[] PAfQ;
    
    public static DlwW[] gDJa() {
        return DlwW.PAfQ.clone();
    }
    
    public static DlwW znGt(final String s) {
        return Enum.valueOf(DlwW.class, s);
    }
    
    private DlwW(final String aohU, final GuiScreen kebz) {
        this.aohU = aohU;
        this.kebz = kebz;
    }
    
    static {
        PAfQ = new DlwW[] { DlwW.UyOo, DlwW.mQOB, DlwW.bOQy, DlwW.CfSS };
    }
}
