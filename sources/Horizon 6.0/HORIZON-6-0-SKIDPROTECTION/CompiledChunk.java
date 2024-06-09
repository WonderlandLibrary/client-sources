package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class CompiledChunk
{
    public static final CompiledChunk HorizonCode_Horizon_È;
    private final boolean[] Â;
    private final boolean[] Ý;
    private boolean Ø­áŒŠá;
    private final List Âµá€;
    private SetVisibility Ó;
    private WorldRenderer.HorizonCode_Horizon_È à;
    private static final String Ø = "CL_00002456";
    
    static {
        HorizonCode_Horizon_È = new CompiledChunk() {
            private static final String Â = "CL_00002455";
            
            @Override
            protected void HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178486_1_) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void Ý(final EnumWorldBlockLayer p_178493_1_) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean HorizonCode_Horizon_È(final EnumFacing p_178495_1_, final EnumFacing p_178495_2_) {
                return false;
            }
        };
    }
    
    public CompiledChunk() {
        this.Â = new boolean[EnumWorldBlockLayer.values().length];
        this.Ý = new boolean[EnumWorldBlockLayer.values().length];
        this.Ø­áŒŠá = true;
        this.Âµá€ = Lists.newArrayList();
        this.Ó = new SetVisibility();
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    protected void HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178486_1_) {
        this.Ø­áŒŠá = false;
        this.Â[p_178486_1_.ordinal()] = true;
    }
    
    public boolean Â(final EnumWorldBlockLayer p_178491_1_) {
        return !this.Â[p_178491_1_.ordinal()];
    }
    
    public void Ý(final EnumWorldBlockLayer p_178493_1_) {
        this.Ý[p_178493_1_.ordinal()] = true;
    }
    
    public boolean Ø­áŒŠá(final EnumWorldBlockLayer p_178492_1_) {
        return this.Ý[p_178492_1_.ordinal()];
    }
    
    public List Â() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final TileEntity p_178490_1_) {
        this.Âµá€.add(p_178490_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final EnumFacing p_178495_1_, final EnumFacing p_178495_2_) {
        return this.Ó.HorizonCode_Horizon_È(p_178495_1_, p_178495_2_);
    }
    
    public void HorizonCode_Horizon_È(final SetVisibility p_178488_1_) {
        this.Ó = p_178488_1_;
    }
    
    public WorldRenderer.HorizonCode_Horizon_È Ý() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final WorldRenderer.HorizonCode_Horizon_È p_178494_1_) {
        this.à = p_178494_1_;
    }
}
