package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;

public abstract class StructureStart
{
    protected LinkedList HorizonCode_Horizon_È;
    protected StructureBoundingBox Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000513";
    
    public StructureStart() {
        this.HorizonCode_Horizon_È = new LinkedList();
    }
    
    public StructureStart(final int p_i43002_1_, final int p_i43002_2_) {
        this.HorizonCode_Horizon_È = new LinkedList();
        this.Ý = p_i43002_1_;
        this.Ø­áŒŠá = p_i43002_2_;
    }
    
    public StructureBoundingBox Â() {
        return this.Â;
    }
    
    public LinkedList Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_75068_2_, final StructureBoundingBox p_75068_3_) {
        final Iterator var4 = this.HorizonCode_Horizon_È.iterator();
        while (var4.hasNext()) {
            final StructureComponent var5 = var4.next();
            if (var5.Â().HorizonCode_Horizon_È(p_75068_3_) && !var5.HorizonCode_Horizon_È(worldIn, p_75068_2_, p_75068_3_)) {
                var4.remove();
            }
        }
    }
    
    protected void Ø­áŒŠá() {
        this.Â = StructureBoundingBox.HorizonCode_Horizon_È();
        for (final StructureComponent var2 : this.HorizonCode_Horizon_È) {
            this.Â.Â(var2.Â());
        }
    }
    
    public NBTTagCompound HorizonCode_Horizon_È(final int p_143021_1_, final int p_143021_2_) {
        final NBTTagCompound var3 = new NBTTagCompound();
        var3.HorizonCode_Horizon_È("id", MapGenStructureIO.HorizonCode_Horizon_È(this));
        var3.HorizonCode_Horizon_È("ChunkX", p_143021_1_);
        var3.HorizonCode_Horizon_È("ChunkZ", p_143021_2_);
        var3.HorizonCode_Horizon_È("BB", this.Â.à());
        final NBTTagList var4 = new NBTTagList();
        for (final StructureComponent var6 : this.HorizonCode_Horizon_È) {
            var4.HorizonCode_Horizon_È(var6.HorizonCode_Horizon_È());
        }
        var3.HorizonCode_Horizon_È("Children", var4);
        this.HorizonCode_Horizon_È(var3);
        return var3;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_143022_1_) {
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final NBTTagCompound p_143020_2_) {
        this.Ý = p_143020_2_.Ó("ChunkX");
        this.Ø­áŒŠá = p_143020_2_.Ó("ChunkZ");
        if (p_143020_2_.Ý("BB")) {
            this.Â = new StructureBoundingBox(p_143020_2_.á("BB"));
        }
        final NBTTagList var3 = p_143020_2_.Ý("Children", 10);
        for (int var4 = 0; var4 < var3.Âµá€(); ++var4) {
            this.HorizonCode_Horizon_È.add(MapGenStructureIO.Â(var3.Â(var4), worldIn));
        }
        this.Â(p_143020_2_);
    }
    
    public void Â(final NBTTagCompound p_143017_1_) {
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final Random p_75067_2_, final int p_75067_3_) {
        final int var4 = 63 - p_75067_3_;
        int var5 = this.Â.Ø­áŒŠá() + 1;
        if (var5 < var4) {
            var5 += p_75067_2_.nextInt(var4 - var5);
        }
        final int var6 = var5 - this.Â.Âµá€;
        this.Â.HorizonCode_Horizon_È(0, var6, 0);
        for (final StructureComponent var8 : this.HorizonCode_Horizon_È) {
            var8.Â().HorizonCode_Horizon_È(0, var6, 0);
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final Random p_75070_2_, final int p_75070_3_, final int p_75070_4_) {
        final int var5 = p_75070_4_ - p_75070_3_ + 1 - this.Â.Ø­áŒŠá();
        final boolean var6 = true;
        int var7;
        if (var5 > 1) {
            var7 = p_75070_3_ + p_75070_2_.nextInt(var5);
        }
        else {
            var7 = p_75070_3_;
        }
        final int var8 = var7 - this.Â.Â;
        this.Â.HorizonCode_Horizon_È(0, var8, 0);
        for (final StructureComponent var10 : this.HorizonCode_Horizon_È) {
            var10.Â().HorizonCode_Horizon_È(0, var8, 0);
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final ChunkCoordIntPair p_175788_1_) {
        return true;
    }
    
    public void Â(final ChunkCoordIntPair p_175787_1_) {
    }
    
    public int Âµá€() {
        return this.Ý;
    }
    
    public int Ó() {
        return this.Ø­áŒŠá;
    }
}
