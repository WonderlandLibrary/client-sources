package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.List;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "automaticly opens Chests.", HorizonCode_Horizon_È = "ChestAura")
public class ChestAura extends Mod
{
    public TimeHelper Ý;
    public List<BlockPos> Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    
    public ChestAura() {
        this.Ý = new TimeHelper();
        this.Ø­áŒŠá = new ArrayList<BlockPos>();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (this.Â.áŒŠÆ == null) {
            return;
        }
        this.Âµá€ = -3;
        while (this.Âµá€ < 4) {
            this.Ó = -3;
            while (this.Ó < 4) {
                this.à = 5;
                while (this.à > -5) {
                    final double x = this.Â.á.ŒÏ + this.Âµá€;
                    final double y = this.Â.á.Çªà¢ + this.à;
                    final double z = this.Â.á.Ê + this.Ó;
                    final int id = Block.HorizonCode_Horizon_È(this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý());
                    Label_0709: {
                        if (id == 54) {
                            if (!(this.Â.¥Æ instanceof GuiContainer)) {
                                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                                if (!ModuleManager.HorizonCode_Horizon_È(Blink.class).áˆºÑ¢Õ() && this.Â.áŒŠÆ != null) {
                                    final BlockChest c = (BlockChest)this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                                    if (this.Ø­áŒŠá.contains(new BlockPos(x, y, z))) {
                                        break Label_0709;
                                    }
                                    this.Ø­áŒŠá.add(new BlockPos(x, y, z));
                                    if (this.Ý.Â(10L)) {
                                        this.Ý.Ø­áŒŠá();
                                        c.HorizonCode_Horizon_È(this.Â.áŒŠÆ, new BlockPos(x, y, z), this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)), this.Â.á, EnumFacing.Â, (float)this.Â.á.ŒÏ, (float)this.Â.á.Çªà¢, (float)this.Â.á.Ê);
                                        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(x, y, z), 0, this.Â.á.áŒŠá(), 0.0f, 0.0f, 0.0f));
                                        break;
                                    }
                                }
                            }
                            return;
                        }
                        if (id == 130) {
                            if (!(this.Â.¥Æ instanceof GuiContainer)) {
                                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                                if (!ModuleManager.HorizonCode_Horizon_È(Blink.class).áˆºÑ¢Õ() && this.Â.áŒŠÆ != null) {
                                    final BlockEnderChest c2 = (BlockEnderChest)this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                                    if (this.Ø­áŒŠá.contains(new BlockPos(x, y, z))) {
                                        break Label_0709;
                                    }
                                    this.Ø­áŒŠá.add(new BlockPos(x, y, z));
                                    if (this.Ý.Â(10L)) {
                                        this.Ý.Ø­áŒŠá();
                                        c2.HorizonCode_Horizon_È(this.Â.áŒŠÆ, new BlockPos(x, y, z), this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)), this.Â.á, EnumFacing.Â, (float)this.Â.á.ŒÏ, (float)this.Â.á.Çªà¢, (float)this.Â.á.Ê);
                                        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(x, y, z), 0, this.Â.á.áŒŠá(), 0.0f, 0.0f, 0.0f));
                                        break;
                                    }
                                }
                            }
                            return;
                        }
                    }
                    --this.à;
                }
                ++this.Ó;
            }
            ++this.Âµá€;
        }
    }
}
