package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class VillageSiege
{
    private World HorizonCode_Horizon_È;
    private boolean Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private Village Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001634";
    
    public VillageSiege(final World worldIn) {
        this.Ý = -1;
        this.HorizonCode_Horizon_È = worldIn;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.ÂµÈ()) {
            this.Ý = 0;
        }
        else if (this.Ý != 2) {
            if (this.Ý == 0) {
                final float var1 = this.HorizonCode_Horizon_È.Ý(0.0f);
                if (var1 < 0.5 || var1 > 0.501) {
                    return;
                }
                this.Ý = ((this.HorizonCode_Horizon_È.Å.nextInt(10) == 0) ? 1 : 2);
                this.Â = false;
                if (this.Ý == 2) {
                    return;
                }
            }
            if (this.Ý != -1) {
                if (!this.Â) {
                    if (!this.Â()) {
                        return;
                    }
                    this.Â = true;
                }
                if (this.Âµá€ > 0) {
                    --this.Âµá€;
                }
                else {
                    this.Âµá€ = 2;
                    if (this.Ø­áŒŠá > 0) {
                        this.Ý();
                        --this.Ø­áŒŠá;
                    }
                    else {
                        this.Ý = 2;
                    }
                }
            }
        }
    }
    
    private boolean Â() {
        final List var1 = this.HorizonCode_Horizon_È.Ó;
        for (final EntityPlayer var3 : var1) {
            if (!var3.Ø­áŒŠá()) {
                this.Ó = this.HorizonCode_Horizon_È.È().HorizonCode_Horizon_È(new BlockPos(var3), 1);
                if (this.Ó == null || this.Ó.Ý() < 10 || this.Ó.Ø­áŒŠá() < 20 || this.Ó.Âµá€() < 20) {
                    continue;
                }
                final BlockPos var4 = this.Ó.HorizonCode_Horizon_È();
                final float var5 = this.Ó.Â();
                boolean var6 = false;
                for (int var7 = 0; var7 < 10; ++var7) {
                    final float var8 = this.HorizonCode_Horizon_È.Å.nextFloat() * 3.1415927f * 2.0f;
                    this.à = var4.HorizonCode_Horizon_È() + (int)(MathHelper.Â(var8) * var5 * 0.9);
                    this.Ø = var4.Â();
                    this.áŒŠÆ = var4.Ý() + (int)(MathHelper.HorizonCode_Horizon_È(var8) * var5 * 0.9);
                    var6 = false;
                    for (final Village var10 : this.HorizonCode_Horizon_È.È().Â()) {
                        if (var10 != this.Ó && var10.HorizonCode_Horizon_È(new BlockPos(this.à, this.Ø, this.áŒŠÆ))) {
                            var6 = true;
                            break;
                        }
                    }
                    if (!var6) {
                        break;
                    }
                }
                if (var6) {
                    return false;
                }
                final Vec3 var11 = this.HorizonCode_Horizon_È(new BlockPos(this.à, this.Ø, this.áŒŠÆ));
                if (var11 != null) {
                    this.Âµá€ = 0;
                    this.Ø­áŒŠá = 20;
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean Ý() {
        final Vec3 var1 = this.HorizonCode_Horizon_È(new BlockPos(this.à, this.Ø, this.áŒŠÆ));
        if (var1 == null) {
            return false;
        }
        EntityZombie var2;
        try {
            var2 = new EntityZombie(this.HorizonCode_Horizon_È);
            var2.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ê(new BlockPos(var2)), null);
            var2.ˆÏ­(false);
        }
        catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
        var2.Â(var1.HorizonCode_Horizon_È, var1.Â, var1.Ý, this.HorizonCode_Horizon_È.Å.nextFloat() * 360.0f, 0.0f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        final BlockPos var4 = this.Ó.HorizonCode_Horizon_È();
        var2.HorizonCode_Horizon_È(var4, this.Ó.Â());
        return true;
    }
    
    private Vec3 HorizonCode_Horizon_È(final BlockPos p_179867_1_) {
        for (int var2 = 0; var2 < 10; ++var2) {
            final BlockPos var3 = p_179867_1_.Â(this.HorizonCode_Horizon_È.Å.nextInt(16) - 8, this.HorizonCode_Horizon_È.Å.nextInt(6) - 3, this.HorizonCode_Horizon_È.Å.nextInt(16) - 8);
            if (this.Ó.HorizonCode_Horizon_È(var3) && SpawnerAnimals.HorizonCode_Horizon_È(EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È, var3)) {
                return new Vec3(var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý());
            }
        }
        return null;
    }
}
