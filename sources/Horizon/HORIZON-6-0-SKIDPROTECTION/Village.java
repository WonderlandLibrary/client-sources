package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.TreeMap;
import java.util.List;

public class Village
{
    private World à;
    private final List Ø;
    public static String HorizonCode_Horizon_È;
    public static String Â;
    public static String Ý;
    public static String Ø­áŒŠá;
    public static String Âµá€;
    public static String Ó;
    private BlockPos áŒŠÆ;
    private BlockPos áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private int ˆÏ­;
    private int £á;
    private int Å;
    private TreeMap £à;
    private List µà;
    private int ˆà;
    private static final String ¥Æ = "CL_00001631";
    
    static {
        Village.HorizonCode_Horizon_È = "aHR0cDovL2hvcml6b25jby5kZS9ob3Jpem9uY2xpZW50L2NoZWNrYWN0aXZhdGVkLnBocD91c2VyPQ==";
        Village.Â = "U3VjY2Vzcw==";
        Village.Ý = "VXNlcl9ub3RfYWN0aXZhdGVkLF9wdXJjaGFzZV9hX2FjdGl2YXRpb24h";
        Village.Ø­áŒŠá = "Tm9fSW50ZXJuZXRfY29ubmVjdGlvbl8vX1NlcnZlcl9kb3duIQ==";
        Village.Âµá€ = "TmljZV90cnlfRmFnZ290XztQ";
        Village.Ó = "V3JvbmdfVXNlcm5hbWVfb3JfUGFzc3dvcmQh";
    }
    
    public Village() {
        this.Ø = Lists.newArrayList();
        this.áŒŠÆ = BlockPos.HorizonCode_Horizon_È;
        this.áˆºÑ¢Õ = BlockPos.HorizonCode_Horizon_È;
        this.£à = new TreeMap();
        this.µà = Lists.newArrayList();
    }
    
    public Village(final World worldIn) {
        this.Ø = Lists.newArrayList();
        this.áŒŠÆ = BlockPos.HorizonCode_Horizon_È;
        this.áˆºÑ¢Õ = BlockPos.HorizonCode_Horizon_È;
        this.£à = new TreeMap();
        this.µà = Lists.newArrayList();
        this.à = worldIn;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.à = worldIn;
    }
    
    public void HorizonCode_Horizon_È(final int p_75560_1_) {
        this.ˆÏ­ = p_75560_1_;
        this.ˆÏ­();
        this.á();
        if (p_75560_1_ % 20 == 0) {
            this.ÂµÈ();
        }
        if (p_75560_1_ % 30 == 0) {
            this.áˆºÑ¢Õ();
        }
        final int var2 = this.£á / 10;
        if (this.ˆà < var2 && this.Ø.size() > 20 && this.à.Å.nextInt(7000) == 0) {
            final Vec3 var3 = this.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 2, 4, 2);
            if (var3 != null) {
                final EntityIronGolem var4 = new EntityIronGolem(this.à);
                var4.Ý(var3.HorizonCode_Horizon_È, var3.Â, var3.Ý);
                this.à.HorizonCode_Horizon_È(var4);
                ++this.ˆà;
            }
        }
    }
    
    private Vec3 HorizonCode_Horizon_È(final BlockPos p_179862_1_, final int p_179862_2_, final int p_179862_3_, final int p_179862_4_) {
        for (int var5 = 0; var5 < 10; ++var5) {
            final BlockPos var6 = p_179862_1_.Â(this.à.Å.nextInt(16) - 8, this.à.Å.nextInt(6) - 3, this.à.Å.nextInt(16) - 8);
            if (this.HorizonCode_Horizon_È(var6) && this.HorizonCode_Horizon_È(new BlockPos(p_179862_2_, p_179862_3_, p_179862_4_), var6)) {
                return new Vec3(var6.HorizonCode_Horizon_È(), var6.Â(), var6.Ý());
            }
        }
        return null;
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_179861_1_, final BlockPos p_179861_2_) {
        if (!World.HorizonCode_Horizon_È(this.à, p_179861_2_.Âµá€())) {
            return false;
        }
        final int var3 = p_179861_2_.HorizonCode_Horizon_È() - p_179861_1_.HorizonCode_Horizon_È() / 2;
        final int var4 = p_179861_2_.Ý() - p_179861_1_.Ý() / 2;
        for (int var5 = var3; var5 < var3 + p_179861_1_.HorizonCode_Horizon_È(); ++var5) {
            for (int var6 = p_179861_2_.Â(); var6 < p_179861_2_.Â() + p_179861_1_.Â(); ++var6) {
                for (int var7 = var4; var7 < var4 + p_179861_1_.Ý(); ++var7) {
                    if (this.à.Â(new BlockPos(var5, var6, var7)).Ý().Ø()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void áˆºÑ¢Õ() {
        final List var1 = this.à.HorizonCode_Horizon_È(EntityIronGolem.class, new AxisAlignedBB(this.áˆºÑ¢Õ.HorizonCode_Horizon_È() - this.ÂµÈ, this.áˆºÑ¢Õ.Â() - 4, this.áˆºÑ¢Õ.Ý() - this.ÂµÈ, this.áˆºÑ¢Õ.HorizonCode_Horizon_È() + this.ÂµÈ, this.áˆºÑ¢Õ.Â() + 4, this.áˆºÑ¢Õ.Ý() + this.ÂµÈ));
        this.ˆà = var1.size();
    }
    
    private void ÂµÈ() {
        final List var1 = this.à.HorizonCode_Horizon_È(EntityVillager.class, new AxisAlignedBB(this.áˆºÑ¢Õ.HorizonCode_Horizon_È() - this.ÂµÈ, this.áˆºÑ¢Õ.Â() - 4, this.áˆºÑ¢Õ.Ý() - this.ÂµÈ, this.áˆºÑ¢Õ.HorizonCode_Horizon_È() + this.ÂµÈ, this.áˆºÑ¢Õ.Â() + 4, this.áˆºÑ¢Õ.Ý() + this.ÂµÈ));
        this.£á = var1.size();
        if (this.£á == 0) {
            this.£à.clear();
        }
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.áˆºÑ¢Õ;
    }
    
    public int Â() {
        return this.ÂµÈ;
    }
    
    public int Ý() {
        return this.Ø.size();
    }
    
    public int Ø­áŒŠá() {
        return this.ˆÏ­ - this.á;
    }
    
    public int Âµá€() {
        return this.£á;
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_179866_1_) {
        return this.áˆºÑ¢Õ.Ó(p_179866_1_) < this.ÂµÈ * this.ÂµÈ;
    }
    
    public List Ó() {
        return this.Ø;
    }
    
    public VillageDoorInfo Â(final BlockPos p_179865_1_) {
        VillageDoorInfo var2 = null;
        int var3 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var5 : this.Ø) {
            final int var6 = var5.HorizonCode_Horizon_È(p_179865_1_);
            if (var6 < var3) {
                var2 = var5;
                var3 = var6;
            }
        }
        return var2;
    }
    
    public VillageDoorInfo Ý(final BlockPos p_179863_1_) {
        VillageDoorInfo var2 = null;
        int var3 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var5 : this.Ø) {
            int var6 = var5.HorizonCode_Horizon_È(p_179863_1_);
            if (var6 > 256) {
                var6 *= 1000;
            }
            else {
                var6 = var5.Ý();
            }
            if (var6 < var3) {
                var2 = var5;
                var3 = var6;
            }
        }
        return var2;
    }
    
    public VillageDoorInfo Ø­áŒŠá(final BlockPos p_179864_1_) {
        if (this.áˆºÑ¢Õ.Ó(p_179864_1_) > this.ÂµÈ * this.ÂµÈ) {
            return null;
        }
        for (final VillageDoorInfo var3 : this.Ø) {
            if (var3.Ø­áŒŠá().HorizonCode_Horizon_È() == p_179864_1_.HorizonCode_Horizon_È() && var3.Ø­áŒŠá().Ý() == p_179864_1_.Ý() && Math.abs(var3.Ø­áŒŠá().Â() - p_179864_1_.Â()) <= 1) {
                return var3;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final VillageDoorInfo p_75576_1_) {
        this.Ø.add(p_75576_1_);
        this.áŒŠÆ = this.áŒŠÆ.HorizonCode_Horizon_È(p_75576_1_.Ø­áŒŠá());
        this.£á();
        this.á = p_75576_1_.Ø();
    }
    
    public boolean à() {
        return this.Ø.isEmpty();
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_75575_1_) {
        for (final HorizonCode_Horizon_È var3 : this.µà) {
            if (var3.HorizonCode_Horizon_È == p_75575_1_) {
                var3.Â = this.ˆÏ­;
                return;
            }
        }
        this.µà.add(new HorizonCode_Horizon_È(p_75575_1_, this.ˆÏ­));
    }
    
    public EntityLivingBase Â(final EntityLivingBase p_75571_1_) {
        double var2 = Double.MAX_VALUE;
        HorizonCode_Horizon_È var3 = null;
        for (int var4 = 0; var4 < this.µà.size(); ++var4) {
            final HorizonCode_Horizon_È var5 = this.µà.get(var4);
            final double var6 = var5.HorizonCode_Horizon_È.Âµá€(p_75571_1_);
            if (var6 <= var2) {
                var3 = var5;
                var2 = var6;
            }
        }
        return (var3 != null) ? var3.HorizonCode_Horizon_È : null;
    }
    
    public EntityPlayer Ý(final EntityLivingBase p_82685_1_) {
        double var2 = Double.MAX_VALUE;
        EntityPlayer var3 = null;
        for (final String var5 : this.£à.keySet()) {
            if (this.Â(var5)) {
                final EntityPlayer var6 = this.à.HorizonCode_Horizon_È(var5);
                if (var6 == null) {
                    continue;
                }
                final double var7 = var6.Âµá€(p_82685_1_);
                if (var7 > var2) {
                    continue;
                }
                var3 = var6;
                var2 = var7;
            }
        }
        return var3;
    }
    
    private void á() {
        final Iterator var1 = this.µà.iterator();
        while (var1.hasNext()) {
            final HorizonCode_Horizon_È var2 = var1.next();
            if (!var2.HorizonCode_Horizon_È.Œ() || Math.abs(this.ˆÏ­ - var2.Â) > 300) {
                var1.remove();
            }
        }
    }
    
    private void ˆÏ­() {
        boolean var1 = false;
        final boolean var2 = this.à.Å.nextInt(50) == 0;
        final Iterator var3 = this.Ø.iterator();
        while (var3.hasNext()) {
            final VillageDoorInfo var4 = var3.next();
            if (var2) {
                var4.HorizonCode_Horizon_È();
            }
            if (!this.Âµá€(var4.Ø­áŒŠá()) || Math.abs(this.ˆÏ­ - var4.Ø()) > 1200) {
                this.áŒŠÆ = this.áŒŠÆ.HorizonCode_Horizon_È(var4.Ø­áŒŠá().HorizonCode_Horizon_È(-1));
                var1 = true;
                var4.HorizonCode_Horizon_È(true);
                var3.remove();
            }
        }
        if (var1) {
            this.£á();
        }
    }
    
    private boolean Âµá€(final BlockPos p_179860_1_) {
        final Block var2 = this.à.Â(p_179860_1_).Ý();
        return var2 instanceof BlockDoor && var2.Ó() == Material.Ø­áŒŠá;
    }
    
    private void £á() {
        final int var1 = this.Ø.size();
        if (var1 == 0) {
            this.áˆºÑ¢Õ = new BlockPos(0, 0, 0);
            this.ÂµÈ = 0;
        }
        else {
            this.áˆºÑ¢Õ = new BlockPos(this.áŒŠÆ.HorizonCode_Horizon_È() / var1, this.áŒŠÆ.Â() / var1, this.áŒŠÆ.Ý() / var1);
            int var2 = 0;
            for (final VillageDoorInfo var4 : this.Ø) {
                var2 = Math.max(var4.HorizonCode_Horizon_È(this.áˆºÑ¢Õ), var2);
            }
            this.ÂµÈ = Math.max(32, (int)Math.sqrt(var2) + 1);
        }
    }
    
    public int HorizonCode_Horizon_È(final String p_82684_1_) {
        final Integer var2 = this.£à.get(p_82684_1_);
        return (var2 != null) ? var2 : 0;
    }
    
    public int HorizonCode_Horizon_È(final String p_82688_1_, final int p_82688_2_) {
        final int var3 = this.HorizonCode_Horizon_È(p_82688_1_);
        final int var4 = MathHelper.HorizonCode_Horizon_È(var3 + p_82688_2_, -30, 10);
        this.£à.put(p_82688_1_, var4);
        return var4;
    }
    
    public boolean Â(final String p_82687_1_) {
        return this.HorizonCode_Horizon_È(p_82687_1_) <= -15;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_82690_1_) {
        this.£á = p_82690_1_.Ó("PopSize");
        this.ÂµÈ = p_82690_1_.Ó("Radius");
        this.ˆà = p_82690_1_.Ó("Golems");
        this.á = p_82690_1_.Ó("Stable");
        this.ˆÏ­ = p_82690_1_.Ó("Tick");
        this.Å = p_82690_1_.Ó("MTick");
        this.áˆºÑ¢Õ = new BlockPos(p_82690_1_.Ó("CX"), p_82690_1_.Ó("CY"), p_82690_1_.Ó("CZ"));
        this.áŒŠÆ = new BlockPos(p_82690_1_.Ó("ACX"), p_82690_1_.Ó("ACY"), p_82690_1_.Ó("ACZ"));
        final NBTTagList var2 = p_82690_1_.Ý("Doors", 10);
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            final VillageDoorInfo var5 = new VillageDoorInfo(new BlockPos(var4.Ó("X"), var4.Ó("Y"), var4.Ó("Z")), var4.Ó("IDX"), var4.Ó("IDZ"), var4.Ó("TS"));
            this.Ø.add(var5);
        }
        final NBTTagList var6 = p_82690_1_.Ý("Players", 10);
        for (int var7 = 0; var7 < var6.Âµá€(); ++var7) {
            final NBTTagCompound var8 = var6.Â(var7);
            this.£à.put(var8.áˆºÑ¢Õ("Name"), var8.Ó("S"));
        }
    }
    
    public void Â(final NBTTagCompound p_82689_1_) {
        p_82689_1_.HorizonCode_Horizon_È("PopSize", this.£á);
        p_82689_1_.HorizonCode_Horizon_È("Radius", this.ÂµÈ);
        p_82689_1_.HorizonCode_Horizon_È("Golems", this.ˆà);
        p_82689_1_.HorizonCode_Horizon_È("Stable", this.á);
        p_82689_1_.HorizonCode_Horizon_È("Tick", this.ˆÏ­);
        p_82689_1_.HorizonCode_Horizon_È("MTick", this.Å);
        p_82689_1_.HorizonCode_Horizon_È("CX", this.áˆºÑ¢Õ.HorizonCode_Horizon_È());
        p_82689_1_.HorizonCode_Horizon_È("CY", this.áˆºÑ¢Õ.Â());
        p_82689_1_.HorizonCode_Horizon_È("CZ", this.áˆºÑ¢Õ.Ý());
        p_82689_1_.HorizonCode_Horizon_È("ACX", this.áŒŠÆ.HorizonCode_Horizon_È());
        p_82689_1_.HorizonCode_Horizon_È("ACY", this.áŒŠÆ.Â());
        p_82689_1_.HorizonCode_Horizon_È("ACZ", this.áŒŠÆ.Ý());
        final NBTTagList var2 = new NBTTagList();
        for (final VillageDoorInfo var4 : this.Ø) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.HorizonCode_Horizon_È("X", var4.Ø­áŒŠá().HorizonCode_Horizon_È());
            var5.HorizonCode_Horizon_È("Y", var4.Ø­áŒŠá().Â());
            var5.HorizonCode_Horizon_È("Z", var4.Ø­áŒŠá().Ý());
            var5.HorizonCode_Horizon_È("IDX", var4.Ó());
            var5.HorizonCode_Horizon_È("IDZ", var4.à());
            var5.HorizonCode_Horizon_È("TS", var4.Ø());
            var2.HorizonCode_Horizon_È(var5);
        }
        p_82689_1_.HorizonCode_Horizon_È("Doors", var2);
        final NBTTagList var6 = new NBTTagList();
        for (final String var8 : this.£à.keySet()) {
            final NBTTagCompound var9 = new NBTTagCompound();
            var9.HorizonCode_Horizon_È("Name", var8);
            var9.HorizonCode_Horizon_È("S", this.£à.get(var8));
            var6.HorizonCode_Horizon_È(var9);
        }
        p_82689_1_.HorizonCode_Horizon_È("Players", var6);
    }
    
    public void Ø() {
        this.Å = this.ˆÏ­;
    }
    
    public boolean áŒŠÆ() {
        return this.Å == 0 || this.ˆÏ­ - this.Å >= 3600;
    }
    
    public void Â(final int p_82683_1_) {
        for (final String var3 : this.£à.keySet()) {
            this.HorizonCode_Horizon_È(var3, p_82683_1_);
        }
    }
    
    class HorizonCode_Horizon_È
    {
        public EntityLivingBase HorizonCode_Horizon_È;
        public int Â;
        private static final String Ø­áŒŠá = "CL_00001632";
        
        HorizonCode_Horizon_È(final EntityLivingBase p_i1674_2_, final int p_i1674_3_) {
            this.HorizonCode_Horizon_È = p_i1674_2_;
            this.Â = p_i1674_3_;
        }
    }
}
