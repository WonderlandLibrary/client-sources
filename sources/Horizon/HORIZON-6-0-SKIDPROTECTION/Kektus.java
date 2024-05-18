package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -13330213, Â = "Walk on Cactus.", HorizonCode_Horizon_È = "Kektus")
public class Kektus extends Mod
{
    @Handler
    private void HorizonCode_Horizon_È(final EventBoundingBox event) {
        if (event.Ý() instanceof BlockCactus) {
            event.HorizonCode_Horizon_È(AxisAlignedBB.HorizonCode_Horizon_È(event.Ø­áŒŠá(), event.Âµá€(), event.Ó(), event.Ø­áŒŠá() + 1, event.Âµá€() + 1, event.Ó() + 1));
        }
    }
}
