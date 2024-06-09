package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class VillageCollection extends WorldSavedData
{
    private World Â;
    private final List Ý;
    private final List Ø­áŒŠá;
    private final List Âµá€;
    private int Ó;
    private static final String à = "CL_00001635";
    
    public VillageCollection(final String p_i1677_1_) {
        super(p_i1677_1_);
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Lists.newArrayList();
    }
    
    public VillageCollection(final World worldIn) {
        super(HorizonCode_Horizon_È(worldIn.£à));
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Lists.newArrayList();
        this.Â = worldIn;
        this.Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.Â = worldIn;
        for (final Village var3 : this.Âµá€) {
            var3.HorizonCode_Horizon_È(worldIn);
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_176060_1_) {
        if (this.Ý.size() <= 64 && !this.Âµá€(p_176060_1_)) {
            this.Ý.add(p_176060_1_);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        ++this.Ó;
        for (final Village var2 : this.Âµá€) {
            var2.HorizonCode_Horizon_È(this.Ó);
        }
        this.Ý();
        this.Ó();
        this.à();
        if (this.Ó % 400 == 0) {
            this.Ø­áŒŠá();
        }
    }
    
    private void Ý() {
        final Iterator var1 = this.Âµá€.iterator();
        while (var1.hasNext()) {
            final Village var2 = var1.next();
            if (var2.à()) {
                var1.remove();
                this.Ø­áŒŠá();
            }
        }
    }
    
    public List Â() {
        return this.Âµá€;
    }
    
    public Village HorizonCode_Horizon_È(final BlockPos p_176056_1_, final int p_176056_2_) {
        Village var3 = null;
        double var4 = 3.4028234663852886E38;
        for (final Village var6 : this.Âµá€) {
            final double var7 = var6.HorizonCode_Horizon_È().Ó(p_176056_1_);
            if (var7 < var4) {
                final float var8 = p_176056_2_ + var6.Â();
                if (var7 > var8 * var8) {
                    continue;
                }
                var3 = var6;
                var4 = var7;
            }
        }
        return var3;
    }
    
    private void Ó() {
        if (!this.Ý.isEmpty()) {
            this.Â(this.Ý.remove(0));
        }
    }
    
    private void à() {
        for (int var1 = 0; var1 < this.Ø­áŒŠá.size(); ++var1) {
            final VillageDoorInfo var2 = this.Ø­áŒŠá.get(var1);
            Village var3 = this.HorizonCode_Horizon_È(var2.Ø­áŒŠá(), 32);
            if (var3 == null) {
                var3 = new Village(this.Â);
                this.Âµá€.add(var3);
                this.Ø­áŒŠá();
            }
            var3.HorizonCode_Horizon_È(var2);
        }
        this.Ø­áŒŠá.clear();
    }
    
    private void Â(final BlockPos p_180609_1_) {
        final byte var2 = 16;
        final byte var3 = 4;
        final byte var4 = 16;
        for (int var5 = -var2; var5 < var2; ++var5) {
            for (int var6 = -var3; var6 < var3; ++var6) {
                for (int var7 = -var4; var7 < var4; ++var7) {
                    final BlockPos var8 = p_180609_1_.Â(var5, var6, var7);
                    if (this.Ó(var8)) {
                        final VillageDoorInfo var9 = this.Ý(var8);
                        if (var9 == null) {
                            this.Ø­áŒŠá(var8);
                        }
                        else {
                            var9.HorizonCode_Horizon_È(this.Ó);
                        }
                    }
                }
            }
        }
    }
    
    private VillageDoorInfo Ý(final BlockPos p_176055_1_) {
        for (final VillageDoorInfo var3 : this.Ø­áŒŠá) {
            if (var3.Ø­áŒŠá().HorizonCode_Horizon_È() == p_176055_1_.HorizonCode_Horizon_È() && var3.Ø­áŒŠá().Ý() == p_176055_1_.Ý() && Math.abs(var3.Ø­áŒŠá().Â() - p_176055_1_.Â()) <= 1) {
                return var3;
            }
        }
        for (final Village var4 : this.Âµá€) {
            final VillageDoorInfo var5 = var4.Ø­áŒŠá(p_176055_1_);
            if (var5 != null) {
                return var5;
            }
        }
        return null;
    }
    
    private void Ø­áŒŠá(final BlockPos p_176059_1_) {
        final EnumFacing var2 = BlockDoor.à((IBlockAccess)this.Â, p_176059_1_);
        final EnumFacing var3 = var2.Âµá€();
        final int var4 = this.HorizonCode_Horizon_È(p_176059_1_, var2, 5);
        final int var5 = this.HorizonCode_Horizon_È(p_176059_1_, var3, var4 + 1);
        if (var4 != var5) {
            this.Ø­áŒŠá.add(new VillageDoorInfo(p_176059_1_, (var4 < var5) ? var2 : var3, this.Ó));
        }
    }
    
    private int HorizonCode_Horizon_È(final BlockPos p_176061_1_, final EnumFacing p_176061_2_, final int p_176061_3_) {
        int var4 = 0;
        for (int var5 = 1; var5 <= 5; ++var5) {
            if (this.Â.áˆºÑ¢Õ(p_176061_1_.HorizonCode_Horizon_È(p_176061_2_, var5)) && ++var4 >= p_176061_3_) {
                return var4;
            }
        }
        return var4;
    }
    
    private boolean Âµá€(final BlockPos p_176057_1_) {
        for (final BlockPos var3 : this.Ý) {
            if (var3.equals(p_176057_1_)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean Ó(final BlockPos p_176058_1_) {
        final Block var2 = this.Â.Â(p_176058_1_).Ý();
        return var2 instanceof BlockDoor && var2.Ó() == Material.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        this.Ó = nbt.Ó("Tick");
        final NBTTagList var2 = nbt.Ý("Villages", 10);
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final Village var5 = new Village();
            var5.HorizonCode_Horizon_È(var4);
            this.Âµá€.add(var5);
        }
    }
    
    @Override
    public void Ý(final NBTTagCompound nbt) {
        nbt.HorizonCode_Horizon_È("Tick", this.Ó);
        final NBTTagList var2 = new NBTTagList();
        for (final Village var4 : this.Âµá€) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var4.Â(var5);
            var2.HorizonCode_Horizon_È(var5);
        }
        nbt.HorizonCode_Horizon_È("Villages", var2);
    }
    
    public static String HorizonCode_Horizon_È(final WorldProvider p_176062_0_) {
        return "villages" + p_176062_0_.á();
    }
}
