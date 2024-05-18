package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Walk on Water.", HorizonCode_Horizon_È = "Jesus")
public class Jesus extends Mod
{
    private boolean Ý;
    
    @Handler
    private void HorizonCode_Horizon_È(final EventBoundingBox event) {
        if (event.Ý() instanceof BlockLiquid && !this.Â.á.Çªà¢() && !£à()) {
            event.HorizonCode_Horizon_È(AxisAlignedBB.HorizonCode_Horizon_È(event.Ø­áŒŠá(), event.Âµá€(), event.Ó(), event.Ø­áŒŠá() + 1, event.Âµá€() + 1, event.Ó() + 1));
        }
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventUpdate event) {
        final EventUpdate.HorizonCode_Horizon_È var10000 = event.Ý();
        event.Ý();
        if (var10000 == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (this.Â.á.Ï­à > 3.0f) {
                event.Â(true);
            }
            if (£à() && !this.Â.á.Çªà¢()) {
                this.Â.á.ˆá = 0.08;
            }
        }
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventPacketSend event) {
        if (event.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer packet = (C03PacketPlayer)event.Ý();
            if (this.Å() && !£à()) {
                this.Ý = !this.Ý;
                if (this.Ý) {
                    final C03PacketPlayer c03PacketPlayer = packet;
                    c03PacketPlayer.Â -= 0.01;
                    event.Â(packet);
                }
            }
        }
    }
    
    public boolean Å() {
        boolean onLiquid = false;
        final int y = (int)this.Â.á.à¢.Ý(0.0, -0.01, 0.0).Â;
        for (int x = MathHelper.Ý(this.Â.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(this.Â.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(this.Â.á.à¢.Ý); z < MathHelper.Ý(this.Â.á.à¢.Ó) + 1; ++z) {
                final Block block = this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        this.HorizonCode_Horizon_È(false);
                        return false;
                    }
                    onLiquid = true;
                    this.HorizonCode_Horizon_È(true);
                }
            }
        }
        return onLiquid;
    }
    
    public static boolean £à() {
        boolean inLiquid = false;
        final int y = (int)Minecraft.áŒŠà().á.à¢.Â;
        for (int x = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ý); z < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
}
