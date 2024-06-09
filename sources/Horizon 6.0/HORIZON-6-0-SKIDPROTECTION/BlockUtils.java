package HORIZON-6-0-SKIDPROTECTION;

public class BlockUtils
{
    private static ReflectorClass HorizonCode_Horizon_È;
    private static ReflectorMethod Â;
    private static boolean Ý;
    
    static {
        BlockUtils.HorizonCode_Horizon_È = new ReflectorClass(Block.class);
        BlockUtils.Â = new ReflectorMethod(BlockUtils.HorizonCode_Horizon_È, "setLightOpacity");
        BlockUtils.Ý = true;
    }
    
    public static void HorizonCode_Horizon_È(final Block block, final int opacity) {
        if (BlockUtils.Ý) {
            try {
                block.Ø­áŒŠá(opacity);
                return;
            }
            catch (IllegalAccessError var3) {
                BlockUtils.Ý = false;
                if (!BlockUtils.Â.Â()) {
                    throw var3;
                }
            }
        }
        Reflector.HorizonCode_Horizon_È(block, BlockUtils.Â, opacity);
    }
}
