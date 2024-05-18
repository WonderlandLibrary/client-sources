package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer
{
    private String HorizonCode_Horizon_È;
    private Map Â;
    private static final String Ý = "CL_00002570";
    
    public ContainerLocalMenu(final String p_i46276_1_, final IChatComponent p_i46276_2_, final int p_i46276_3_) {
        super(p_i46276_2_, p_i46276_3_);
        this.Â = Maps.newHashMap();
        this.HorizonCode_Horizon_È = p_i46276_1_;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        return this.Â.containsKey(id) ? this.Â.get(id) : 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
        this.Â.put(id, value);
    }
    
    @Override
    public int Âµá€() {
        return this.Â.size();
    }
    
    @Override
    public boolean Ó() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final LockCode code) {
    }
    
    @Override
    public LockCode x_() {
        return LockCode.HorizonCode_Horizon_È;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        throw new UnsupportedOperationException();
    }
}
