package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = 0, Â = "Walk normaly and places blocks under you.", HorizonCode_Horizon_È = "ScaffoldWalk")
public class ScaffoldWalk extends Mod
{
    public boolean Ý;
    private BlockData Ø­áŒŠá;
    private TimeHelper Âµá€;
    
    public ScaffoldWalk() {
        this.Ø­áŒŠá = null;
        this.Âµá€ = new TimeHelper();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ø­áŒŠá = null;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (this.Â.á.áŒŠá() == null) {
            return;
        }
        if (this.Â.á.áŒŠá().HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(0)) {
            return;
        }
        if (!(this.Â.á.áŒŠá().HorizonCode_Horizon_È() instanceof ItemBlock)) {
            return;
        }
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Ø­áŒŠá = null;
            if (this.Â.á.Çª() != null && !this.Â.á.Çªà¢() && this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemBlock) {
                final BlockPos player = new BlockPos(this.Â.á.ŒÏ, this.Â.á.Çªà¢ - 1.0, this.Â.á.Ê);
                if (this.Â.áŒŠÆ.Â(player).Ý() == Blocks.Â) {
                    this.Ø­áŒŠá = this.HorizonCode_Horizon_È(player);
                    if (this.Ø­áŒŠá != null) {
                        final float[] values = EntityHelper.HorizonCode_Horizon_È(this.Ø­áŒŠá.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Â(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Ý(), this.Ø­áŒŠá.Â);
                        e.HorizonCode_Horizon_È(values[0]);
                        e.Â(values[1]);
                    }
                }
            }
        }
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.Ý) {
            if (this.Ø­áŒŠá == null) {
                return;
            }
            if (this.Ý) {
                if (!this.Âµá€.Â(500L)) {
                    this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                    if (this.Â.Âµá€.HorizonCode_Horizon_È(this.Â.á, this.Â.áŒŠÆ, this.Â.á.Çª(), this.Ø­áŒŠá.HorizonCode_Horizon_È, this.Ø­áŒŠá.Â, new Vec3(this.Ø­áŒŠá.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Â(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Ý()))) {
                        this.Â.á.b_();
                    }
                    this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.Â));
                }
                if (this.Âµá€.Â(750L)) {
                    this.Âµá€.Ø­áŒŠá();
                }
            }
            else if (!this.Ý && this.Âµá€.Â(75L)) {
                this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                if (this.Â.Âµá€.HorizonCode_Horizon_È(this.Â.á, this.Â.áŒŠÆ, this.Â.á.Çª(), this.Ø­áŒŠá.HorizonCode_Horizon_È, this.Ø­áŒŠá.Â, new Vec3(this.Ø­áŒŠá.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Â(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Ý()))) {
                    this.Â.á.b_();
                }
                this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.Â));
                this.Âµá€.Ý();
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend e) {
        if (e.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer player1 = (C03PacketPlayer)e.Ý();
            if (this.Ø­áŒŠá == null) {
                return;
            }
            final float[] values = EntityHelper.HorizonCode_Horizon_È(this.Ø­áŒŠá.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Â(), this.Ø­áŒŠá.HorizonCode_Horizon_È.Ý(), this.Ø­áŒŠá.Â);
            player1.Ø­áŒŠá = values[0];
            player1.Âµá€ = values[1];
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventSafeWalk e) {
        e.HorizonCode_Horizon_È(true);
    }
    
    public BlockData HorizonCode_Horizon_È(final BlockPos pos) {
        return (this.Â.áŒŠÆ.Â(pos.Â(0, 0, 1)).Ý() != Blocks.Â) ? new BlockData(pos.Â(0, 0, 1), EnumFacing.Ý) : ((this.Â.áŒŠÆ.Â(pos.Â(0, 0, -1)).Ý() != Blocks.Â) ? new BlockData(pos.Â(0, 0, -1), EnumFacing.Ø­áŒŠá) : ((this.Â.áŒŠÆ.Â(pos.Â(1, 0, 0)).Ý() != Blocks.Â) ? new BlockData(pos.Â(1, 0, 0), EnumFacing.Âµá€) : ((this.Â.áŒŠÆ.Â(pos.Â(-1, 0, 0)).Ý() != Blocks.Â) ? new BlockData(pos.Â(-1, 0, 0), EnumFacing.Ó) : ((this.Â.áŒŠÆ.Â(pos.Â(0, -1, 0)).Ý() != Blocks.Â) ? new BlockData(pos.Â(0, -1, 0), EnumFacing.Â) : null))));
    }
}
