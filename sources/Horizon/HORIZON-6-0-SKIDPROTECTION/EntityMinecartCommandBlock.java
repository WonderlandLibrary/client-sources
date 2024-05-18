package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;

public class EntityMinecartCommandBlock extends EntityMinecart
{
    private final CommandBlockLogic HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00001672";
    
    public EntityMinecartCommandBlock(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = new CommandBlockLogic() {
            private static final String Â = "CL_00001673";
            
            @Override
            public void áˆºÑ¢Õ() {
                EntityMinecartCommandBlock.this.É().Â(23, this.áŒŠÆ());
                EntityMinecartCommandBlock.this.É().Â(24, IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø()));
            }
            
            @Override
            public int ÂµÈ() {
                return 1;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(EntityMinecartCommandBlock.this.ˆá());
            }
            
            @Override
            public BlockPos £á() {
                return new BlockPos(EntityMinecartCommandBlock.this.ŒÏ, EntityMinecartCommandBlock.this.Çªà¢ + 0.5, EntityMinecartCommandBlock.this.Ê);
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(EntityMinecartCommandBlock.this.ŒÏ, EntityMinecartCommandBlock.this.Çªà¢, EntityMinecartCommandBlock.this.Ê);
            }
            
            @Override
            public World k_() {
                return EntityMinecartCommandBlock.this.Ï­Ðƒà;
            }
            
            @Override
            public Entity l_() {
                return EntityMinecartCommandBlock.this;
            }
        };
        this.Â = 0;
    }
    
    public EntityMinecartCommandBlock(final World worldIn, final double p_i45322_2_, final double p_i45322_4_, final double p_i45322_6_) {
        super(worldIn, p_i45322_2_, p_i45322_4_, p_i45322_6_);
        this.HorizonCode_Horizon_È = new CommandBlockLogic() {
            private static final String Â = "CL_00001673";
            
            @Override
            public void áˆºÑ¢Õ() {
                EntityMinecartCommandBlock.this.É().Â(23, this.áŒŠÆ());
                EntityMinecartCommandBlock.this.É().Â(24, IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø()));
            }
            
            @Override
            public int ÂµÈ() {
                return 1;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(EntityMinecartCommandBlock.this.ˆá());
            }
            
            @Override
            public BlockPos £á() {
                return new BlockPos(EntityMinecartCommandBlock.this.ŒÏ, EntityMinecartCommandBlock.this.Çªà¢ + 0.5, EntityMinecartCommandBlock.this.Ê);
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(EntityMinecartCommandBlock.this.ŒÏ, EntityMinecartCommandBlock.this.Çªà¢, EntityMinecartCommandBlock.this.Ê);
            }
            
            @Override
            public World k_() {
                return EntityMinecartCommandBlock.this.Ï­Ðƒà;
            }
            
            @Override
            public Entity l_() {
                return EntityMinecartCommandBlock.this;
            }
        };
        this.Â = 0;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.É().HorizonCode_Horizon_È(23, "");
        this.É().HorizonCode_Horizon_È(24, "");
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.HorizonCode_Horizon_È.Â(tagCompund);
        this.É().Â(23, this.áŒŠÆ().áŒŠÆ());
        this.É().Â(24, IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.áŒŠÆ().Ø()));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tagCompound);
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.à;
    }
    
    @Override
    public IBlockState Ø() {
        return Blocks.ŠÑ¢Ó.¥à();
    }
    
    public CommandBlockLogic áŒŠÆ() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        if (p_96095_4_ && this.Œ - this.Â >= 4) {
            this.áŒŠÆ().HorizonCode_Horizon_È(this.Ï­Ðƒà);
            this.Â = this.Œ;
        }
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(playerIn);
        return false;
    }
    
    @Override
    public void áˆºÑ¢Õ(final int p_145781_1_) {
        super.áˆºÑ¢Õ(p_145781_1_);
        if (p_145781_1_ == 24) {
            try {
                this.HorizonCode_Horizon_È.Â(IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.É().Âµá€(24)));
            }
            catch (Throwable t) {}
        }
        else if (p_145781_1_ == 23) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.É().Âµá€(23));
        }
    }
}
