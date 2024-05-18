package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class ServerSelectionList extends GuiListExtended
{
    private final GuiMultiplayer HorizonCode_Horizon_È;
    private final List Â;
    private final List Šáƒ;
    private final HorizonCode_Horizon_È Ï­Ðƒà;
    private int áŒŠà;
    private static final String ŠÄ = "CL_00000819";
    
    public ServerSelectionList(final GuiMultiplayer p_i45049_1_, final Minecraft mcIn, final int p_i45049_3_, final int p_i45049_4_, final int p_i45049_5_, final int p_i45049_6_, final int p_i45049_7_) {
        super(mcIn, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
        this.Â = Lists.newArrayList();
        this.Šáƒ = Lists.newArrayList();
        this.Ï­Ðƒà = new ServerListEntryLanScan();
        this.áŒŠà = -1;
        this.HorizonCode_Horizon_È = p_i45049_1_;
    }
    
    @Override
    public HorizonCode_Horizon_È Â(int p_148180_1_) {
        if (p_148180_1_ < this.Â.size()) {
            return this.Â.get(p_148180_1_);
        }
        p_148180_1_ -= this.Â.size();
        if (p_148180_1_ == 0) {
            return this.Ï­Ðƒà;
        }
        --p_148180_1_;
        return this.Šáƒ.get(p_148180_1_);
    }
    
    @Override
    protected int HorizonCode_Horizon_È() {
        return this.Â.size() + 1 + this.Šáƒ.size();
    }
    
    public void Ý(final int p_148192_1_) {
        this.áŒŠà = p_148192_1_;
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int slotIndex) {
        return slotIndex == this.áŒŠà;
    }
    
    public int Ø­áŒŠá() {
        return this.áŒŠà;
    }
    
    public void HorizonCode_Horizon_È(final ServerList p_148195_1_) {
        this.Â.clear();
        for (int var2 = 0; var2 < p_148195_1_.Ý(); ++var2) {
            this.Â.add(new ServerListEntryNormal(this.HorizonCode_Horizon_È, p_148195_1_.HorizonCode_Horizon_È(var2)));
        }
    }
    
    public void HorizonCode_Horizon_È(final List p_148194_1_) {
        this.Šáƒ.clear();
        for (final LanServerDetector.HorizonCode_Horizon_È var3 : p_148194_1_) {
            this.Šáƒ.add(new ServerListEntryLanDetected(this.HorizonCode_Horizon_È, var3));
        }
    }
    
    @Override
    protected int à() {
        return super.à() + 30;
    }
    
    @Override
    public int o_() {
        return super.o_() + 85;
    }
}
