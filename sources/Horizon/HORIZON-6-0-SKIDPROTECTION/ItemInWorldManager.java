package HORIZON-6-0-SKIDPROTECTION;

public class ItemInWorldManager
{
    public World HorizonCode_Horizon_È;
    public EntityPlayerMP Â;
    private WorldSettings.HorizonCode_Horizon_È Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private BlockPos Ó;
    private int à;
    private boolean Ø;
    private BlockPos áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private static final String á = "CL_00001442";
    
    public ItemInWorldManager(final World worldIn) {
        this.Ý = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.Ó = BlockPos.HorizonCode_Horizon_È;
        this.áŒŠÆ = BlockPos.HorizonCode_Horizon_È;
        this.ÂµÈ = -1;
        this.HorizonCode_Horizon_È = worldIn;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p_73076_1_) {
        (this.Ý = p_73076_1_).HorizonCode_Horizon_È(this.Â.áˆºáˆºáŠ);
        this.Â.Ø­à();
        this.Â.Â.Œ().HorizonCode_Horizon_È(new S38PacketPlayerListItem(S38PacketPlayerListItem.HorizonCode_Horizon_È.Â, new EntityPlayerMP[] { this.Â }));
    }
    
    public WorldSettings.HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public boolean Â() {
        return this.Ý.Âµá€();
    }
    
    public boolean Ý() {
        return this.Ý.Ø­áŒŠá();
    }
    
    public void Â(final WorldSettings.HorizonCode_Horizon_È p_73077_1_) {
        if (this.Ý == WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Ý = p_73077_1_;
        }
        this.HorizonCode_Horizon_È(this.Ý);
    }
    
    public void Ø­áŒŠá() {
        ++this.à;
        if (this.Ø) {
            final int var1 = this.à - this.áˆºÑ¢Õ;
            final Block var2 = this.HorizonCode_Horizon_È.Â(this.áŒŠÆ).Ý();
            if (var2.Ó() == Material.HorizonCode_Horizon_È) {
                this.Ø = false;
            }
            else {
                final float var3 = var2.HorizonCode_Horizon_È(this.Â, this.Â.Ï­Ðƒà, this.áŒŠÆ) * (var1 + 1);
                final int var4 = (int)(var3 * 10.0f);
                if (var4 != this.ÂµÈ) {
                    this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), this.áŒŠÆ, var4);
                    this.ÂµÈ = var4;
                }
                if (var3 >= 1.0f) {
                    this.Ø = false;
                    this.Â(this.áŒŠÆ);
                }
            }
        }
        else if (this.Ø­áŒŠá) {
            final Block var5 = this.HorizonCode_Horizon_È.Â(this.Ó).Ý();
            if (var5.Ó() == Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), this.Ó, -1);
                this.ÂµÈ = -1;
                this.Ø­áŒŠá = false;
            }
            else {
                final int var6 = this.à - this.Âµá€;
                final float var3 = var5.HorizonCode_Horizon_È(this.Â, this.Â.Ï­Ðƒà, this.áŒŠÆ) * (var6 + 1);
                final int var4 = (int)(var3 * 10.0f);
                if (var4 != this.ÂµÈ) {
                    this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), this.Ó, var4);
                    this.ÂµÈ = var4;
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180784_1_, final EnumFacing p_180784_2_) {
        if (this.Ý()) {
            if (!this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(null, p_180784_1_, p_180784_2_)) {
                this.Â(p_180784_1_);
            }
        }
        else {
            final Block var3 = this.HorizonCode_Horizon_È.Â(p_180784_1_).Ý();
            if (this.Ý.Ý()) {
                if (this.Ý == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                    return;
                }
                if (!this.Â.ŠáˆºÂ()) {
                    final ItemStack var4 = this.Â.áŒŠá();
                    if (var4 == null) {
                        return;
                    }
                    if (!var4.Ý(var3)) {
                        return;
                    }
                }
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(null, p_180784_1_, p_180784_2_);
            this.Âµá€ = this.à;
            float var5 = 1.0f;
            if (var3.Ó() != Material.HorizonCode_Horizon_È) {
                var3.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_180784_1_, this.Â);
                var5 = var3.HorizonCode_Horizon_È(this.Â, this.Â.Ï­Ðƒà, p_180784_1_);
            }
            if (var3.Ó() != Material.HorizonCode_Horizon_È && var5 >= 1.0f) {
                this.Â(p_180784_1_);
            }
            else {
                this.Ø­áŒŠá = true;
                this.Ó = p_180784_1_;
                final int var6 = (int)(var5 * 10.0f);
                this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), p_180784_1_, var6);
                this.ÂµÈ = var6;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180785_1_) {
        if (p_180785_1_.equals(this.Ó)) {
            final int var2 = this.à - this.Âµá€;
            final Block var3 = this.HorizonCode_Horizon_È.Â(p_180785_1_).Ý();
            if (var3.Ó() != Material.HorizonCode_Horizon_È) {
                final float var4 = var3.HorizonCode_Horizon_È(this.Â, this.Â.Ï­Ðƒà, p_180785_1_) * (var2 + 1);
                if (var4 >= 0.7f) {
                    this.Ø­áŒŠá = false;
                    this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), p_180785_1_, -1);
                    this.Â(p_180785_1_);
                }
                else if (!this.Ø) {
                    this.Ø­áŒŠá = false;
                    this.Ø = true;
                    this.áŒŠÆ = p_180785_1_;
                    this.áˆºÑ¢Õ = this.Âµá€;
                }
            }
        }
    }
    
    public void Âµá€() {
        this.Ø­áŒŠá = false;
        this.HorizonCode_Horizon_È.Ý(this.Â.ˆá(), this.Ó, -1);
    }
    
    private boolean Ý(final BlockPos p_180235_1_) {
        final IBlockState var2 = this.HorizonCode_Horizon_È.Â(p_180235_1_);
        var2.Ý().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_180235_1_, var2, this.Â);
        final boolean var3 = this.HorizonCode_Horizon_È.Ø(p_180235_1_);
        if (var3) {
            var2.Ý().Â(this.HorizonCode_Horizon_È, p_180235_1_, var2);
        }
        return var3;
    }
    
    public boolean Â(final BlockPos p_180237_1_) {
        if (this.Ý.Ø­áŒŠá() && this.Â.Çª() != null && this.Â.Çª().HorizonCode_Horizon_È() instanceof ItemSword) {
            return false;
        }
        final IBlockState var2 = this.HorizonCode_Horizon_È.Â(p_180237_1_);
        final TileEntity var3 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180237_1_);
        if (this.Ý.Ý()) {
            if (this.Ý == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                return false;
            }
            if (!this.Â.ŠáˆºÂ()) {
                final ItemStack var4 = this.Â.áŒŠá();
                if (var4 == null) {
                    return false;
                }
                if (!var4.Ý(var2.Ý())) {
                    return false;
                }
            }
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, 2001, p_180237_1_, Block.HorizonCode_Horizon_È(var2));
        final boolean var5 = this.Ý(p_180237_1_);
        if (this.Ý()) {
            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S23PacketBlockChange(this.HorizonCode_Horizon_È, p_180237_1_));
        }
        else {
            final ItemStack var6 = this.Â.áŒŠá();
            final boolean var7 = this.Â.Â(var2.Ý());
            if (var6 != null) {
                var6.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, var2.Ý(), p_180237_1_, this.Â);
                if (var6.Â == 0) {
                    this.Â.ˆØ();
                }
            }
            if (var5 && var7) {
                var2.Ý().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, p_180237_1_, var2, var3);
            }
        }
        return var5;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_73085_1_, final World worldIn, final ItemStack p_73085_3_) {
        if (this.Ý == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            return false;
        }
        final int var4 = p_73085_3_.Â;
        final int var5 = p_73085_3_.Ø();
        final ItemStack var6 = p_73085_3_.HorizonCode_Horizon_È(worldIn, p_73085_1_);
        if (var6 == p_73085_3_ && (var6 == null || (var6.Â == var4 && var6.á() <= 0 && var6.Ø() == var5))) {
            return false;
        }
        p_73085_1_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[p_73085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý] = var6;
        if (this.Ý()) {
            var6.Â = var4;
            if (var6.Ø­áŒŠá()) {
                var6.Â(var5);
            }
        }
        if (var6.Â == 0) {
            p_73085_1_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[p_73085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
        }
        if (!p_73085_1_.Ñ¢Ó()) {
            ((EntityPlayerMP)p_73085_1_).HorizonCode_Horizon_È(p_73085_1_.ŒÂ);
        }
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_180236_1_, final World worldIn, final ItemStack p_180236_3_, final BlockPos p_180236_4_, final EnumFacing p_180236_5_, final float p_180236_6_, final float p_180236_7_, final float p_180236_8_) {
        if (this.Ý == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            final TileEntity var13 = worldIn.HorizonCode_Horizon_È(p_180236_4_);
            if (var13 instanceof ILockableContainer) {
                final Block var14 = worldIn.Â(p_180236_4_).Ý();
                ILockableContainer var15 = (ILockableContainer)var13;
                if (var15 instanceof TileEntityChest && var14 instanceof BlockChest) {
                    var15 = ((BlockChest)var14).áŒŠÆ(worldIn, p_180236_4_);
                }
                if (var15 != null) {
                    p_180236_1_.HorizonCode_Horizon_È((IInventory)var15);
                    return true;
                }
            }
            else if (var13 instanceof IInventory) {
                p_180236_1_.HorizonCode_Horizon_È((IInventory)var13);
                return true;
            }
            return false;
        }
        if (!p_180236_1_.Çªà¢() || p_180236_1_.Çª() == null) {
            final IBlockState var16 = worldIn.Â(p_180236_4_);
            if (var16.Ý().HorizonCode_Horizon_È(worldIn, p_180236_4_, var16, p_180236_1_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_)) {
                return true;
            }
        }
        if (p_180236_3_ == null) {
            return false;
        }
        if (this.Ý()) {
            final int var17 = p_180236_3_.Ø();
            final int var18 = p_180236_3_.Â;
            final boolean var19 = p_180236_3_.HorizonCode_Horizon_È(p_180236_1_, worldIn, p_180236_4_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_);
            p_180236_3_.Â(var17);
            p_180236_3_.Â = var18;
            return var19;
        }
        return p_180236_3_.HorizonCode_Horizon_È(p_180236_1_, worldIn, p_180236_4_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_);
    }
    
    public void HorizonCode_Horizon_È(final WorldServer p_73080_1_) {
        this.HorizonCode_Horizon_È = p_73080_1_;
    }
}
