package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.List;

public class SpectatorMenu
{
    private static final ISpectatorMenuObject Â;
    private static final ISpectatorMenuObject Ý;
    private static final ISpectatorMenuObject Ø­áŒŠá;
    private static final ISpectatorMenuObject Âµá€;
    public static final ISpectatorMenuObject HorizonCode_Horizon_È;
    private final ISpectatorMenuReciepient Ó;
    private final List à;
    private ISpectatorMenuView Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001927";
    
    static {
        Â = new HorizonCode_Horizon_È(null);
        Ý = new Â(-1, true);
        Ø­áŒŠá = new Â(1, true);
        Âµá€ = new Â(1, false);
        HorizonCode_Horizon_È = new ISpectatorMenuObject() {
            private static final String HorizonCode_Horizon_È = "CL_00001926";
            
            @Override
            public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
            }
            
            @Override
            public IChatComponent Ý() {
                return new ChatComponentText("");
            }
            
            @Override
            public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
            }
            
            @Override
            public boolean Ø­áŒŠá() {
                return false;
            }
        };
    }
    
    public SpectatorMenu(final ISpectatorMenuReciepient p_i45497_1_) {
        this.à = Lists.newArrayList();
        this.Ø = new BaseSpectatorGroup();
        this.áŒŠÆ = -1;
        this.Ó = p_i45497_1_;
    }
    
    public ISpectatorMenuObject HorizonCode_Horizon_È(final int p_178643_1_) {
        final int var2 = p_178643_1_ + this.áˆºÑ¢Õ * 6;
        return (ISpectatorMenuObject)((this.áˆºÑ¢Õ > 0 && p_178643_1_ == 0) ? SpectatorMenu.Ý : ((p_178643_1_ == 7) ? ((var2 < this.Ø.HorizonCode_Horizon_È().size()) ? SpectatorMenu.Ø­áŒŠá : SpectatorMenu.Âµá€) : ((p_178643_1_ == 8) ? SpectatorMenu.Â : ((var2 >= 0 && var2 < this.Ø.HorizonCode_Horizon_È().size()) ? Objects.firstNonNull(this.Ø.HorizonCode_Horizon_È().get(var2), (Object)SpectatorMenu.HorizonCode_Horizon_È) : SpectatorMenu.HorizonCode_Horizon_È))));
    }
    
    public List HorizonCode_Horizon_È() {
        final ArrayList var1 = Lists.newArrayList();
        for (int var2 = 0; var2 <= 8; ++var2) {
            var1.add(this.HorizonCode_Horizon_È(var2));
        }
        return var1;
    }
    
    public ISpectatorMenuObject Â() {
        return this.HorizonCode_Horizon_È(this.áŒŠÆ);
    }
    
    public ISpectatorMenuView Ý() {
        return this.Ø;
    }
    
    public void Â(final int p_178644_1_) {
        final ISpectatorMenuObject var2 = this.HorizonCode_Horizon_È(p_178644_1_);
        if (var2 != SpectatorMenu.HorizonCode_Horizon_È) {
            if (this.áŒŠÆ == p_178644_1_ && var2.Ø­áŒŠá()) {
                var2.HorizonCode_Horizon_È(this);
            }
            else {
                this.áŒŠÆ = p_178644_1_;
            }
        }
    }
    
    public void Ø­áŒŠá() {
        this.Ó.HorizonCode_Horizon_È(this);
    }
    
    public int Âµá€() {
        return this.áŒŠÆ;
    }
    
    public void HorizonCode_Horizon_È(final ISpectatorMenuView p_178647_1_) {
        this.à.add(this.Ó());
        this.Ø = p_178647_1_;
        this.áŒŠÆ = -1;
        this.áˆºÑ¢Õ = 0;
    }
    
    public SpectatorDetails Ó() {
        return new SpectatorDetails(this.Ø, this.HorizonCode_Horizon_È(), this.áŒŠÆ);
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final SpectatorMenu spectatorMenu, final int áˆºÑ¢Õ) {
        spectatorMenu.áˆºÑ¢Õ = áˆºÑ¢Õ;
    }
    
    static class HorizonCode_Horizon_È implements ISpectatorMenuObject
    {
        private static final String HorizonCode_Horizon_È = "CL_00001925";
        
        private HorizonCode_Horizon_È() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
            p_178661_1_.Ø­áŒŠá();
        }
        
        @Override
        public IChatComponent Ý() {
            return new ChatComponentText("Close menu");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(GuiSpectator.HorizonCode_Horizon_È);
            Gui_1808253012.HorizonCode_Horizon_È(0, 0, 128.0f, 0.0f, 16, 16, 256.0f, 256.0f);
        }
        
        @Override
        public boolean Ø­áŒŠá() {
            return true;
        }
        
        HorizonCode_Horizon_È(final Object p_i45496_1_) {
            this();
        }
    }
    
    static class Â implements ISpectatorMenuObject
    {
        private final int HorizonCode_Horizon_È;
        private final boolean Â;
        private static final String Ý = "CL_00001924";
        
        public Â(final int p_i45495_1_, final boolean p_i45495_2_) {
            this.HorizonCode_Horizon_È = p_i45495_1_;
            this.Â = p_i45495_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
            SpectatorMenu.HorizonCode_Horizon_È(p_178661_1_, this.HorizonCode_Horizon_È);
        }
        
        @Override
        public IChatComponent Ý() {
            return (this.HorizonCode_Horizon_È < 0) ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(GuiSpectator.HorizonCode_Horizon_È);
            if (this.HorizonCode_Horizon_È < 0) {
                Gui_1808253012.HorizonCode_Horizon_È(0, 0, 144.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
            else {
                Gui_1808253012.HorizonCode_Horizon_È(0, 0, 160.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
        }
        
        @Override
        public boolean Ø­áŒŠá() {
            return this.Â;
        }
    }
}
