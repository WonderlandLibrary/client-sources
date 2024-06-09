package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;

public class EntityPainting extends EntityHanging
{
    public HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00001556";
    
    public EntityPainting(final World worldIn) {
        super(worldIn);
    }
    
    public EntityPainting(final World worldIn, final BlockPos p_i45849_2_, final EnumFacing p_i45849_3_) {
        super(worldIn, p_i45849_2_);
        final ArrayList var4 = Lists.newArrayList();
        for (final HorizonCode_Horizon_È var8 : HorizonCode_Horizon_È.values()) {
            this.Ý = var8;
            this.HorizonCode_Horizon_È(p_i45849_3_);
            if (this.à()) {
                var4.add(var8);
            }
        }
        if (!var4.isEmpty()) {
            this.Ý = var4.get(this.ˆáƒ.nextInt(var4.size()));
        }
        this.HorizonCode_Horizon_È(p_i45849_3_);
    }
    
    public EntityPainting(final World worldIn, final BlockPos p_i45850_2_, final EnumFacing p_i45850_3_, final String p_i45850_4_) {
        this(worldIn, p_i45850_2_, p_i45850_3_);
        for (final HorizonCode_Horizon_È var8 : HorizonCode_Horizon_È.values()) {
            if (var8.ŒÏ.equals(p_i45850_4_)) {
                this.Ý = var8;
                break;
            }
        }
        this.HorizonCode_Horizon_È(p_i45850_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Motive", this.Ý.ŒÏ);
        super.HorizonCode_Horizon_È(tagCompound);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        final String var2 = tagCompund.áˆºÑ¢Õ("Motive");
        for (final HorizonCode_Horizon_È var6 : HorizonCode_Horizon_È.values()) {
            if (var6.ŒÏ.equals(var2)) {
                this.Ý = var6;
            }
        }
        if (this.Ý == null) {
            this.Ý = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        super.Â(tagCompund);
    }
    
    @Override
    public int Ø() {
        return this.Ý.Çªà¢;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Ý.Ê;
    }
    
    @Override
    public void Â(final Entity p_110128_1_) {
        if (this.Ï­Ðƒà.Çªà¢().Â("doTileDrops")) {
            if (p_110128_1_ instanceof EntityPlayer) {
                final EntityPlayer var2 = (EntityPlayer)p_110128_1_;
                if (var2.áˆºáˆºáŠ.Ø­áŒŠá) {
                    return;
                }
            }
            this.HorizonCode_Horizon_È(new ItemStack(Items.ˆÐƒØ­à), 0.0f);
        }
    }
    
    @Override
    public void Â(final double x, final double y, final double z, final float yaw, final float pitch) {
        final BlockPos var9 = new BlockPos(x - this.ŒÏ, y - this.Çªà¢, z - this.Ê);
        final BlockPos var10 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9);
        this.Ý(var10.HorizonCode_Horizon_È(), var10.Â(), var10.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        final BlockPos var11 = new BlockPos(p_180426_1_ - this.ŒÏ, p_180426_3_ - this.Çªà¢, p_180426_5_ - this.Ê);
        final BlockPos var12 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var11);
        this.Ý(var12.HorizonCode_Horizon_È(), var12.Â(), var12.Ý());
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("KEBAB", 0, "KEBAB", 0, "Kebab", 16, 16, 0, 0), 
        Â("AZTEC", 1, "AZTEC", 1, "Aztec", 16, 16, 16, 0), 
        Ý("ALBAN", 2, "ALBAN", 2, "Alban", 16, 16, 32, 0), 
        Ø­áŒŠá("AZTEC_2", 3, "AZTEC_2", 3, "Aztec2", 16, 16, 48, 0), 
        Âµá€("BOMB", 4, "BOMB", 4, "Bomb", 16, 16, 64, 0), 
        Ó("PLANT", 5, "PLANT", 5, "Plant", 16, 16, 80, 0), 
        à("WASTELAND", 6, "WASTELAND", 6, "Wasteland", 16, 16, 96, 0), 
        Ø("POOL", 7, "POOL", 7, "Pool", 32, 16, 0, 32), 
        áŒŠÆ("COURBET", 8, "COURBET", 8, "Courbet", 32, 16, 32, 32), 
        áˆºÑ¢Õ("SEA", 9, "SEA", 9, "Sea", 32, 16, 64, 32), 
        ÂµÈ("SUNSET", 10, "SUNSET", 10, "Sunset", 32, 16, 96, 32), 
        á("CREEBET", 11, "CREEBET", 11, "Creebet", 32, 16, 128, 32), 
        ˆÏ­("WANDERER", 12, "WANDERER", 12, "Wanderer", 16, 32, 0, 64), 
        £á("GRAHAM", 13, "GRAHAM", 13, "Graham", 16, 32, 16, 64), 
        Å("MATCH", 14, "MATCH", 14, "Match", 32, 32, 0, 128), 
        £à("BUST", 15, "BUST", 15, "Bust", 32, 32, 32, 128), 
        µà("STAGE", 16, "STAGE", 16, "Stage", 32, 32, 64, 128), 
        ˆà("VOID", 17, "VOID", 17, "Void", 32, 32, 96, 128), 
        ¥Æ("SKULL_AND_ROSES", 18, "SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128), 
        Ø­à("WITHER", 19, "WITHER", 19, "Wither", 32, 32, 160, 128), 
        µÕ("FIGHTERS", 20, "FIGHTERS", 20, "Fighters", 64, 32, 0, 96), 
        Æ("POINTER", 21, "POINTER", 21, "Pointer", 64, 64, 0, 192), 
        Šáƒ("PIGSCENE", 22, "PIGSCENE", 22, "Pigscene", 64, 64, 64, 192), 
        Ï­Ðƒà("BURNING_SKULL", 23, "BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192), 
        áŒŠà("SKELETON", 24, "SKELETON", 24, "Skeleton", 64, 48, 192, 64), 
        ŠÄ("DONKEY_KONG", 25, "DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112);
        
        public static final int Ñ¢á;
        public final String ŒÏ;
        public final int Çªà¢;
        public final int Ê;
        public final int ÇŽÉ;
        public final int ˆá;
        private static final HorizonCode_Horizon_È[] ÇŽÕ;
        private static final String É = "CL_00001557";
        
        static {
            áƒ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ };
            Ñ¢á = "SkullAndRoses".length();
            ÇŽÕ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ, HorizonCode_Horizon_È.Šáƒ, HorizonCode_Horizon_È.Ï­Ðƒà, HorizonCode_Horizon_È.áŒŠà, HorizonCode_Horizon_È.ŠÄ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1598_1_, final int p_i1598_2_, final String p_i1598_3_, final int p_i1598_4_, final int p_i1598_5_, final int p_i1598_6_, final int p_i1598_7_) {
            this.ŒÏ = p_i1598_3_;
            this.Çªà¢ = p_i1598_4_;
            this.Ê = p_i1598_5_;
            this.ÇŽÉ = p_i1598_6_;
            this.ˆá = p_i1598_7_;
        }
    }
}
