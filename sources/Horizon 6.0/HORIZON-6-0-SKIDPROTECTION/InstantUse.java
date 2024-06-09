package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "Instant eat and shoot.", HorizonCode_Horizon_È = "InstantUse")
public class InstantUse extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (e.Ý() != EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return;
        }
        try {
            if (this.HorizonCode_Horizon_È(this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá().HorizonCode_Horizon_È())) {
                return;
            }
        }
        catch (Exception var2) {
            return;
        }
        if (this.Â.á.Ø­Æ() > 15) {
            for (int i = 0; i < 30; ++i) {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer(this.Â.á.ŠÂµà));
            }
            if (this.Â.á.áŒŠá().HorizonCode_Horizon_È() instanceof ItemBow) {
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó, new BlockPos(0, 0, 0), EnumFacing.Â));
                this.Â.á.áŒŠÔ();
            }
            else {
                this.Â.á.áŒŠÔ();
            }
        }
    }
    
    public void Å() {
        final int item = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá(), 0.0f, 0.0f, 0.0f));
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C09PacketHeldItemChange(item));
        for (int i = 0; i < 96; ++i) {
            this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer(this.Â.á.ŠÂµà));
        }
        this.Â.µÕ().HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó, new BlockPos(0, 0, 0), EnumFacing.HorizonCode_Horizon_È));
    }
    
    private boolean HorizonCode_Horizon_È(final Item_1028566121 item) {
        return item instanceof ItemSword;
    }
}
