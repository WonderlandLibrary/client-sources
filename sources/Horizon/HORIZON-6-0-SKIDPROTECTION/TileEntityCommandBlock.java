package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic Âµá€;
    private static final String Ó = "CL_00000347";
    
    public TileEntityCommandBlock() {
        this.Âµá€ = new CommandBlockLogic() {
            private static final String Â = "CL_00000348";
            
            @Override
            public BlockPos £á() {
                return TileEntityCommandBlock.this.Â;
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(TileEntityCommandBlock.this.Â.HorizonCode_Horizon_È() + 0.5, TileEntityCommandBlock.this.Â.Â() + 0.5, TileEntityCommandBlock.this.Â.Ý() + 0.5);
            }
            
            @Override
            public World k_() {
                return TileEntityCommandBlock.this.ÇŽÉ();
            }
            
            @Override
            public void HorizonCode_Horizon_È(final String p_145752_1_) {
                super.HorizonCode_Horizon_È(p_145752_1_);
                TileEntityCommandBlock.this.ŠÄ();
            }
            
            @Override
            public void áˆºÑ¢Õ() {
                TileEntityCommandBlock.this.ÇŽÉ().áŒŠÆ(TileEntityCommandBlock.this.Â);
            }
            
            @Override
            public int ÂµÈ() {
                return 0;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(TileEntityCommandBlock.this.Â.HorizonCode_Horizon_È());
                p_145757_1_.writeInt(TileEntityCommandBlock.this.Â.Â());
                p_145757_1_.writeInt(TileEntityCommandBlock.this.Â.Ý());
            }
            
            @Override
            public Entity l_() {
                return null;
            }
        };
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        this.Âµá€.HorizonCode_Horizon_È(compound);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€.Â(compound);
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        return new S35PacketUpdateTileEntity(this.Â, 2, var1);
    }
    
    public CommandBlockLogic HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public CommandResultStats Â() {
        return this.Âµá€.ˆÏ­();
    }
}
