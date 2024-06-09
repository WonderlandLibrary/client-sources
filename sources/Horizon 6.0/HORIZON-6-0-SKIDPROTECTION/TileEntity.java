package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity
{
    private static final Logger Âµá€;
    private static Map Ó;
    private static Map à;
    protected World HorizonCode_Horizon_È;
    protected BlockPos Â;
    protected boolean Ý;
    private int Ø;
    protected Block Ø­áŒŠá;
    private static final String áŒŠÆ = "CL_00000340";
    
    static {
        Âµá€ = LogManager.getLogger();
        TileEntity.Ó = Maps.newHashMap();
        TileEntity.à = Maps.newHashMap();
        HorizonCode_Horizon_È(TileEntityFurnace.class, "Furnace");
        HorizonCode_Horizon_È(TileEntityChest.class, "Chest");
        HorizonCode_Horizon_È(TileEntityEnderChest.class, "EnderChest");
        HorizonCode_Horizon_È(BlockJukebox.HorizonCode_Horizon_È.class, "RecordPlayer");
        HorizonCode_Horizon_È(TileEntityDispenser.class, "Trap");
        HorizonCode_Horizon_È(TileEntityDropper.class, "Dropper");
        HorizonCode_Horizon_È(TileEntitySign.class, "Sign");
        HorizonCode_Horizon_È(TileEntityMobSpawner.class, "MobSpawner");
        HorizonCode_Horizon_È(TileEntityNote.class, "Music");
        HorizonCode_Horizon_È(TileEntityPiston.class, "Piston");
        HorizonCode_Horizon_È(TileEntityBrewingStand.class, "Cauldron");
        HorizonCode_Horizon_È(TileEntityEnchantmentTable.class, "EnchantTable");
        HorizonCode_Horizon_È(TileEntityEndPortal.class, "Airportal");
        HorizonCode_Horizon_È(TileEntityCommandBlock.class, "Control");
        HorizonCode_Horizon_È(TileEntityBeacon.class, "Beacon");
        HorizonCode_Horizon_È(TileEntitySkull.class, "Skull");
        HorizonCode_Horizon_È(TileEntityDaylightDetector.class, "DLDetector");
        HorizonCode_Horizon_È(TileEntityHopper.class, "Hopper");
        HorizonCode_Horizon_È(TileEntityComparator.class, "Comparator");
        HorizonCode_Horizon_È(TileEntityFlowerPot.class, "FlowerPot");
        HorizonCode_Horizon_È(TileEntityBanner.class, "Banner");
    }
    
    public TileEntity() {
        this.Â = BlockPos.HorizonCode_Horizon_È;
        this.Ø = -1;
    }
    
    private static void HorizonCode_Horizon_È(final Class cl, final String id) {
        if (TileEntity.Ó.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate id: " + id);
        }
        TileEntity.Ó.put(id, cl);
        TileEntity.à.put(cl, id);
    }
    
    public World ÇŽÉ() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.HorizonCode_Horizon_È = worldIn;
    }
    
    public boolean Ø() {
        return this.HorizonCode_Horizon_È != null;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        this.Â = new BlockPos(compound.Ó("x"), compound.Ó("y"), compound.Ó("z"));
    }
    
    public void Â(final NBTTagCompound compound) {
        final String var2 = TileEntity.à.get(this.getClass());
        if (var2 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        compound.HorizonCode_Horizon_È("id", var2);
        compound.HorizonCode_Horizon_È("x", this.Â.HorizonCode_Horizon_È());
        compound.HorizonCode_Horizon_È("y", this.Â.Â());
        compound.HorizonCode_Horizon_È("z", this.Â.Ý());
    }
    
    public static TileEntity Ý(final NBTTagCompound nbt) {
        TileEntity var1 = null;
        try {
            final Class var2 = TileEntity.Ó.get(nbt.áˆºÑ¢Õ("id"));
            if (var2 != null) {
                var1 = var2.newInstance();
            }
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
        if (var1 != null) {
            var1.HorizonCode_Horizon_È(nbt);
        }
        else {
            TileEntity.Âµá€.warn("Skipping BlockEntity with id " + nbt.áˆºÑ¢Õ("id"));
        }
        return var1;
    }
    
    public int áˆºÑ¢Õ() {
        if (this.Ø == -1) {
            final IBlockState var1 = this.HorizonCode_Horizon_È.Â(this.Â);
            this.Ø = var1.Ý().Ý(var1);
        }
        return this.Ø;
    }
    
    public void ŠÄ() {
        if (this.HorizonCode_Horizon_È != null) {
            final IBlockState var1 = this.HorizonCode_Horizon_È.Â(this.Â);
            this.Ø = var1.Ý().Ý(var1);
            this.HorizonCode_Horizon_È.Â(this.Â, this);
            if (this.ˆÏ­() != Blocks.Â) {
                this.HorizonCode_Horizon_È.Âµá€(this.Â, this.ˆÏ­());
            }
        }
    }
    
    public double HorizonCode_Horizon_È(final double p_145835_1_, final double p_145835_3_, final double p_145835_5_) {
        final double var7 = this.Â.HorizonCode_Horizon_È() + 0.5 - p_145835_1_;
        final double var8 = this.Â.Â() + 0.5 - p_145835_3_;
        final double var9 = this.Â.Ý() + 0.5 - p_145835_5_;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double ÂµÈ() {
        return 4096.0;
    }
    
    public BlockPos á() {
        return this.Â;
    }
    
    public Block ˆÏ­() {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È.Â(this.Â).Ý();
        }
        return this.Ø­áŒŠá;
    }
    
    public Packet £á() {
        return null;
    }
    
    public boolean Å() {
        return this.Ý;
    }
    
    public void £à() {
        this.Ý = true;
    }
    
    public void µà() {
        this.Ý = false;
    }
    
    public boolean Ý(final int id, final int type) {
        return false;
    }
    
    public void ˆà() {
        this.Ø­áŒŠá = null;
        this.Ø = -1;
    }
    
    public void HorizonCode_Horizon_È(final CrashReportCategory reportCategory) {
        reportCategory.HorizonCode_Horizon_È("Name", new Callable() {
            private static final String Â = "CL_00000341";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(TileEntity.à.get(TileEntity.this.getClass())) + " // " + TileEntity.this.getClass().getCanonicalName();
            }
        });
        if (this.HorizonCode_Horizon_È != null) {
            CrashReportCategory.HorizonCode_Horizon_È(reportCategory, this.Â, this.ˆÏ­(), this.áˆºÑ¢Õ());
            reportCategory.HorizonCode_Horizon_È("Actual block type", new Callable() {
                private static final String Â = "CL_00000343";
                
                public String HorizonCode_Horizon_È() {
                    final int var1 = Block.HorizonCode_Horizon_È(TileEntity.this.HorizonCode_Horizon_È.Â(TileEntity.this.Â).Ý());
                    try {
                        return String.format("ID #%d (%s // %s)", var1, Block.HorizonCode_Horizon_È(var1).Çªà¢(), Block.HorizonCode_Horizon_È(var1).getClass().getCanonicalName());
                    }
                    catch (Throwable var2) {
                        return "ID #" + var1;
                    }
                }
            });
            reportCategory.HorizonCode_Horizon_È("Actual block data value", new Callable() {
                private static final String Â = "CL_00000344";
                
                public String HorizonCode_Horizon_È() {
                    final IBlockState var1 = TileEntity.this.HorizonCode_Horizon_È.Â(TileEntity.this.Â);
                    final int var2 = var1.Ý().Ý(var1);
                    if (var2 < 0) {
                        return "Unknown? (Got " + var2 + ")";
                    }
                    final String var3 = String.format("%4s", Integer.toBinaryString(var2)).replace(" ", "0");
                    return String.format("%1$d / 0x%1$X / 0b%2$s", var2, var3);
                }
            });
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos posIn) {
        this.Â = posIn;
    }
}
