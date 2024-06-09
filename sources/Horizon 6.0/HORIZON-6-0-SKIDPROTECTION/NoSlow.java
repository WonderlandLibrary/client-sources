package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Block while youre Sprinting.", HorizonCode_Horizon_È = "NoSlow")
public class NoSlow extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (this.Â.á.£Ø­à()) {
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó, new BlockPos(0, 0, 0), EnumFacing.HorizonCode_Horizon_È));
            }
        }
        else if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.Ý && this.Â.á.£Ø­à()) {
            this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá(), 0.0f, 0.0f, 0.0f));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Blocks.¥Ï.áƒ = 0.39f;
        Blocks.ŠÂµÏ.áƒ = 0.39f;
    }
    
    @Override
    public void á() {
        Blocks.¥Ï.áƒ = 0.98f;
        Blocks.ŠÂµÏ.áƒ = 0.98f;
    }
}
