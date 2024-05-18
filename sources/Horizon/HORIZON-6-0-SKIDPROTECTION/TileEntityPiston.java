package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox
{
    private IBlockState Âµá€;
    private EnumFacing Ó;
    private boolean à;
    private boolean Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private List ÂµÈ;
    private static final String á = "CL_00000369";
    
    public TileEntityPiston() {
        this.ÂµÈ = Lists.newArrayList();
    }
    
    public TileEntityPiston(final IBlockState p_i45665_1_, final EnumFacing p_i45665_2_, final boolean p_i45665_3_, final boolean p_i45665_4_) {
        this.ÂµÈ = Lists.newArrayList();
        this.Âµá€ = p_i45665_1_;
        this.Ó = p_i45665_2_;
        this.à = p_i45665_3_;
        this.Ø = p_i45665_4_;
    }
    
    public IBlockState Â() {
        return this.Âµá€;
    }
    
    @Override
    public int áˆºÑ¢Õ() {
        return 0;
    }
    
    public boolean Ý() {
        return this.à;
    }
    
    public EnumFacing Ø­áŒŠá() {
        return this.Ó;
    }
    
    public boolean Âµá€() {
        return this.Ø;
    }
    
    public float HorizonCode_Horizon_È(float p_145860_1_) {
        if (p_145860_1_ > 1.0f) {
            p_145860_1_ = 1.0f;
        }
        return this.áˆºÑ¢Õ + (this.áŒŠÆ - this.áˆºÑ¢Õ) * p_145860_1_;
    }
    
    public float Â(final float p_174929_1_) {
        return this.à ? ((this.HorizonCode_Horizon_È(p_174929_1_) - 1.0f) * this.Ó.Ø()) : ((1.0f - this.HorizonCode_Horizon_È(p_174929_1_)) * this.Ó.Ø());
    }
    
    public float Ý(final float p_174928_1_) {
        return this.à ? ((this.HorizonCode_Horizon_È(p_174928_1_) - 1.0f) * this.Ó.áŒŠÆ()) : ((1.0f - this.HorizonCode_Horizon_È(p_174928_1_)) * this.Ó.áŒŠÆ());
    }
    
    public float Ø­áŒŠá(final float p_174926_1_) {
        return this.à ? ((this.HorizonCode_Horizon_È(p_174926_1_) - 1.0f) * this.Ó.áˆºÑ¢Õ()) : ((1.0f - this.HorizonCode_Horizon_È(p_174926_1_)) * this.Ó.áˆºÑ¢Õ());
    }
    
    private void HorizonCode_Horizon_È(float p_145863_1_, final float p_145863_2_) {
        if (this.à) {
            p_145863_1_ = 1.0f - p_145863_1_;
        }
        else {
            --p_145863_1_;
        }
        final AxisAlignedBB var3 = Blocks.¥à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Âµá€, p_145863_1_, this.Ó);
        if (var3 != null) {
            final List var4 = this.HorizonCode_Horizon_È.Â(null, var3);
            if (!var4.isEmpty()) {
                this.ÂµÈ.addAll(var4);
                for (final Entity var6 : this.ÂµÈ) {
                    if (this.Âµá€.Ý() == Blocks.ÇŽØ­à && this.à) {
                        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[this.Ó.á().ordinal()]) {
                            case 1: {
                                var6.ÇŽÉ = this.Ó.Ø();
                                continue;
                            }
                            case 2: {
                                var6.ˆá = this.Ó.áŒŠÆ();
                                continue;
                            }
                            case 3: {
                                var6.ÇŽÕ = this.Ó.áˆºÑ¢Õ();
                                continue;
                            }
                        }
                    }
                    else {
                        var6.HorizonCode_Horizon_È(p_145863_2_ * this.Ó.Ø(), p_145863_2_ * this.Ó.áŒŠÆ(), p_145863_2_ * this.Ó.áˆºÑ¢Õ());
                    }
                }
                this.ÂµÈ.clear();
            }
        }
    }
    
    public void Ó() {
        if (this.áˆºÑ¢Õ < 1.0f && this.HorizonCode_Horizon_È != null) {
            final float n = 1.0f;
            this.áŒŠÆ = n;
            this.áˆºÑ¢Õ = n;
            this.HorizonCode_Horizon_È.¥Æ(this.Â);
            this.£à();
            if (this.HorizonCode_Horizon_È.Â(this.Â).Ý() == Blocks.¥à) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, this.Âµá€, 3);
                this.HorizonCode_Horizon_È.Ý(this.Â, this.Âµá€.Ý());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.áˆºÑ¢Õ = this.áŒŠÆ;
        if (this.áˆºÑ¢Õ >= 1.0f) {
            this.HorizonCode_Horizon_È(1.0f, 0.25f);
            this.HorizonCode_Horizon_È.¥Æ(this.Â);
            this.£à();
            if (this.HorizonCode_Horizon_È.Â(this.Â).Ý() == Blocks.¥à) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, this.Âµá€, 3);
                this.HorizonCode_Horizon_È.Ý(this.Â, this.Âµá€.Ý());
            }
        }
        else {
            this.áŒŠÆ += 0.5f;
            if (this.áŒŠÆ >= 1.0f) {
                this.áŒŠÆ = 1.0f;
            }
            if (this.à) {
                this.HorizonCode_Horizon_È(this.áŒŠÆ, this.áŒŠÆ - this.áˆºÑ¢Õ + 0.0625f);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = Block.HorizonCode_Horizon_È(compound.Ó("blockId")).Ý(compound.Ó("blockData"));
        this.Ó = EnumFacing.HorizonCode_Horizon_È(compound.Ó("facing"));
        final float ø = compound.Ø("progress");
        this.áŒŠÆ = ø;
        this.áˆºÑ¢Õ = ø;
        this.à = compound.£á("extending");
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("blockId", Block.HorizonCode_Horizon_È(this.Âµá€.Ý()));
        compound.HorizonCode_Horizon_È("blockData", this.Âµá€.Ý().Ý(this.Âµá€));
        compound.HorizonCode_Horizon_È("facing", this.Ó.Â());
        compound.HorizonCode_Horizon_È("progress", this.áˆºÑ¢Õ);
        compound.HorizonCode_Horizon_È("extending", this.à);
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002034";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                TileEntityPiston.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                TileEntityPiston.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                TileEntityPiston.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
