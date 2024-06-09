package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Step over Blocks.", HorizonCode_Horizon_È = "Step")
public class Step extends Mod
{
    public boolean Ý;
    public boolean Ø­áŒŠá;
    
    public Step() {
        this.Ý = false;
        this.Ø­áŒŠá = false;
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventBoundingBox e) {
        if (!this.Å()) {
            this.Â.á.Ô = 0.5f;
            return;
        }
        if (this.Â.á.ŠÂµà && (int)(this.Â.á.à¢.Â - 0.01) < e.Âµá€() && (int)this.Â.á.à¢.Â == e.Âµá€() && !(e.Ý() instanceof BlockAir) && !this.Â.á.HorizonCode_Horizon_È(Material.áŒŠÆ) && !this.Â.á.£ÂµÄ() && !(e.Ý() instanceof BlockLiquid) && this.Â.á.Âµà) {
            this.Â.á.Ô = Horizon.Ï­Ðƒà;
            if (!this.Â.ŠÄ.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá() && !this.HorizonCode_Horizon_È(this.Â.á)) {
                final float oldSpped = this.Â.Ø.Ø­áŒŠá;
                final boolean isSprinting = this.Â.á.ÇªÂµÕ();
                this.Â.Ø.Ø­áŒŠá = 1.0f;
                this.Ý = false;
                e.HorizonCode_Horizon_È(AxisAlignedBB.HorizonCode_Horizon_È(e.à().HorizonCode_Horizon_È, e.à().Â, e.à().Ý, e.à().Ø­áŒŠá, (!(e.Ý() instanceof BlockEnderChest) && !(e.Ý() instanceof BlockChest)) ? (e.à().Âµá€ + 0.065) : (e.à().Â + 1.065), e.à().Ó));
                this.Â.Ø.Ø­áŒŠá = oldSpped;
                this.Â.á.Â(isSprinting);
            }
            else {
                this.Â.á.Â(true);
                this.Ý = true;
            }
        }
    }
    
    @Override
    public void á() {
        this.Â.á.Ô = 0.5f;
    }
    
    private boolean HorizonCode_Horizon_È(final EntityPlayer player) {
        boolean onSnow = false;
        final int y = (int)player.à¢.Ý(0.0, -0.01, 0.0).Â;
        for (int x = MathHelper.Ý(player.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(player.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(player.à¢.Ý); z < MathHelper.Ý(player.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockSnow || block instanceof BlockSnowBlock)) {
                    onSnow = true;
                }
            }
        }
        return onSnow;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
    }
    
    public boolean Å() {
        final ArrayList<BlockPos> collisionBlocks = new ArrayList<BlockPos>();
        final EntityPlayer p = this.Â.á;
        final BlockPos var1 = new BlockPos(p.£É().HorizonCode_Horizon_È - 0.001, p.£É().Â - 0.001, p.£É().Ý - 0.001);
        final BlockPos var2 = new BlockPos(p.£É().Ø­áŒŠá + 0.001, p.£É().Âµá€ + 0.001, p.£É().Ó + 0.001);
        if (p.Ï­Ðƒà.HorizonCode_Horizon_È(var1, var2)) {
            for (int var3 = var1.HorizonCode_Horizon_È(); var3 <= var2.HorizonCode_Horizon_È(); ++var3) {
                for (int var4 = var1.Â(); var4 <= var2.Â(); ++var4) {
                    for (int var5 = var1.Ý(); var5 <= var2.Ý(); ++var5) {
                        final BlockPos blockPos = new BlockPos(var3, var4, var5);
                        final IBlockState var6 = p.Ï­Ðƒà.Â(blockPos);
                        try {
                            if (var4 > p.Çªà¢ - 1.0 && var4 <= p.Çªà¢) {
                                collisionBlocks.add(blockPos);
                            }
                        }
                        catch (Throwable t) {}
                    }
                }
            }
        }
        for (final BlockPos pos : collisionBlocks) {
            if (p.Ï­Ðƒà.Â(pos).Ý() instanceof BlockStairs) {
                return false;
            }
        }
        return p.ŠÂµà || this.Ý;
    }
}
