package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class StructureVillagePieces
{
    private static final String HorizonCode_Horizon_È = "CL_00000516";
    
    public static void HorizonCode_Horizon_È() {
        MapGenStructureIO.HorizonCode_Horizon_È(Âµá€.class, "ViBH");
        MapGenStructureIO.HorizonCode_Horizon_È(Â.class, "ViDF");
        MapGenStructureIO.HorizonCode_Horizon_È(Ý.class, "ViF");
        MapGenStructureIO.HorizonCode_Horizon_È(£á.class, "ViL");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø­áŒŠá.class, "ViPH");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø.class, "ViSH");
        MapGenStructureIO.HorizonCode_Horizon_È(µà.class, "ViSmH");
        MapGenStructureIO.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class, "ViST");
        MapGenStructureIO.HorizonCode_Horizon_È(Ó.class, "ViS");
        MapGenStructureIO.HorizonCode_Horizon_È(á.class, "ViStart");
        MapGenStructureIO.HorizonCode_Horizon_È(áŒŠÆ.class, "ViSR");
        MapGenStructureIO.HorizonCode_Horizon_È(à.class, "ViTRH");
        MapGenStructureIO.HorizonCode_Horizon_È(£à.class, "ViW");
    }
    
    public static List HorizonCode_Horizon_È(final Random p_75084_0_, final int p_75084_1_) {
        final ArrayList var2 = Lists.newArrayList();
        var2.add(new áˆºÑ¢Õ(Ø.class, 4, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new áˆºÑ¢Õ(HorizonCode_Horizon_È.class, 20, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 0 + p_75084_1_, 1 + p_75084_1_)));
        var2.add(new áˆºÑ¢Õ(Âµá€.class, 20, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new áˆºÑ¢Õ(µà.class, 3, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
        var2.add(new áˆºÑ¢Õ(Ø­áŒŠá.class, 15, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new áˆºÑ¢Õ(Â.class, 3, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 1 + p_75084_1_, 4 + p_75084_1_)));
        var2.add(new áˆºÑ¢Õ(Ý.class, 3, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new áˆºÑ¢Õ(Ó.class, 15, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 0, 1 + p_75084_1_)));
        var2.add(new áˆºÑ¢Õ(à.class, 8, MathHelper.HorizonCode_Horizon_È(p_75084_0_, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
        final Iterator var3 = var2.iterator();
        while (var3.hasNext()) {
            if (var3.next().Ø­áŒŠá == 0) {
                var3.remove();
            }
        }
        return var2;
    }
    
    private static int HorizonCode_Horizon_È(final List p_75079_0_) {
        boolean var1 = false;
        int var2 = 0;
        for (final áˆºÑ¢Õ var4 : p_75079_0_) {
            if (var4.Ø­áŒŠá > 0 && var4.Ý < var4.Ø­áŒŠá) {
                var1 = true;
            }
            var2 += var4.Â;
        }
        return var1 ? var2 : -1;
    }
    
    private static Å HorizonCode_Horizon_È(final á p_176065_0_, final áˆºÑ¢Õ p_176065_1_, final List p_176065_2_, final Random p_176065_3_, final int p_176065_4_, final int p_176065_5_, final int p_176065_6_, final EnumFacing p_176065_7_, final int p_176065_8_) {
        final Class var9 = p_176065_1_.HorizonCode_Horizon_È;
        Object var10 = null;
        if (var9 == Ø.class) {
            var10 = Ø.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == HorizonCode_Horizon_È.class) {
            var10 = StructureVillagePieces.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == Âµá€.class) {
            var10 = Âµá€.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == µà.class) {
            var10 = µà.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == Ø­áŒŠá.class) {
            var10 = Ø­áŒŠá.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == Â.class) {
            var10 = Â.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == Ý.class) {
            var10 = Ý.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == Ó.class) {
            var10 = Ó.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        else if (var9 == à.class) {
            var10 = à.HorizonCode_Horizon_È(p_176065_0_, p_176065_2_, p_176065_3_, p_176065_4_, p_176065_5_, p_176065_6_, p_176065_7_, p_176065_8_);
        }
        return (Å)var10;
    }
    
    private static Å Ý(final á p_176067_0_, final List p_176067_1_, final Random p_176067_2_, final int p_176067_3_, final int p_176067_4_, final int p_176067_5_, final EnumFacing p_176067_6_, final int p_176067_7_) {
        final int var8 = HorizonCode_Horizon_È(p_176067_0_.Ø);
        if (var8 <= 0) {
            return null;
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = p_176067_2_.nextInt(var8);
            for (final áˆºÑ¢Õ var12 : p_176067_0_.Ø) {
                var10 -= var12.Â;
                if (var10 < 0) {
                    if (!var12.HorizonCode_Horizon_È(p_176067_7_)) {
                        break;
                    }
                    if (var12 == p_176067_0_.Ø­áŒŠá && p_176067_0_.Ø.size() > 1) {
                        break;
                    }
                    final Å var13 = HorizonCode_Horizon_È(p_176067_0_, var12, p_176067_1_, p_176067_2_, p_176067_3_, p_176067_4_, p_176067_5_, p_176067_6_, p_176067_7_);
                    if (var13 != null) {
                        final áˆºÑ¢Õ áˆºÑ¢Õ = var12;
                        ++áˆºÑ¢Õ.Ý;
                        p_176067_0_.Ø­áŒŠá = var12;
                        if (!var12.HorizonCode_Horizon_È()) {
                            p_176067_0_.Ø.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = £á.HorizonCode_Horizon_È(p_176067_0_, p_176067_1_, p_176067_2_, p_176067_3_, p_176067_4_, p_176067_5_, p_176067_6_);
        if (var14 != null) {
            return new £á(p_176067_0_, p_176067_7_, p_176067_2_, var14, p_176067_6_);
        }
        return null;
    }
    
    private static StructureComponent Ø­áŒŠá(final á p_176066_0_, final List p_176066_1_, final Random p_176066_2_, final int p_176066_3_, final int p_176066_4_, final int p_176066_5_, final EnumFacing p_176066_6_, final int p_176066_7_) {
        if (p_176066_7_ > 50) {
            return null;
        }
        if (Math.abs(p_176066_3_ - p_176066_0_.Â().HorizonCode_Horizon_È) <= 112 && Math.abs(p_176066_5_ - p_176066_0_.Â().Ý) <= 112) {
            final Å var8 = Ý(p_176066_0_, p_176066_1_, p_176066_2_, p_176066_3_, p_176066_4_, p_176066_5_, p_176066_6_, p_176066_7_ + 1);
            if (var8 != null) {
                final int var9 = (var8.Âµá€.HorizonCode_Horizon_È + var8.Âµá€.Ø­áŒŠá) / 2;
                final int var10 = (var8.Âµá€.Ý + var8.Âµá€.Ó) / 2;
                final int var11 = var8.Âµá€.Ø­áŒŠá - var8.Âµá€.HorizonCode_Horizon_È;
                final int var12 = var8.Âµá€.Ó - var8.Âµá€.Ý;
                final int var13 = (var11 > var12) ? var11 : var12;
                if (p_176066_0_.Âµá€().HorizonCode_Horizon_È(var9, var10, var13 / 2 + 4, MapGenVillage.Âµá€)) {
                    p_176066_1_.add(var8);
                    p_176066_0_.áŒŠÆ.add(var8);
                    return var8;
                }
            }
            return null;
        }
        return null;
    }
    
    private static StructureComponent Âµá€(final á p_176069_0_, final List p_176069_1_, final Random p_176069_2_, final int p_176069_3_, final int p_176069_4_, final int p_176069_5_, final EnumFacing p_176069_6_, final int p_176069_7_) {
        if (p_176069_7_ > 3 + p_176069_0_.Ý) {
            return null;
        }
        if (Math.abs(p_176069_3_ - p_176069_0_.Â().HorizonCode_Horizon_È) <= 112 && Math.abs(p_176069_5_ - p_176069_0_.Â().Ý) <= 112) {
            final StructureBoundingBox var8 = áŒŠÆ.HorizonCode_Horizon_È(p_176069_0_, p_176069_1_, p_176069_2_, p_176069_3_, p_176069_4_, p_176069_5_, p_176069_6_);
            if (var8 != null && var8.Â > 10) {
                final áŒŠÆ var9 = new áŒŠÆ(p_176069_0_, p_176069_7_, p_176069_2_, var8, p_176069_6_);
                final int var10 = (var9.Âµá€.HorizonCode_Horizon_È + var9.Âµá€.Ø­áŒŠá) / 2;
                final int var11 = (var9.Âµá€.Ý + var9.Âµá€.Ó) / 2;
                final int var12 = var9.Âµá€.Ø­áŒŠá - var9.Âµá€.HorizonCode_Horizon_È;
                final int var13 = var9.Âµá€.Ó - var9.Âµá€.Ý;
                final int var14 = (var12 > var13) ? var12 : var13;
                if (p_176069_0_.Âµá€().HorizonCode_Horizon_È(var10, var11, var14 / 2 + 4, MapGenVillage.Âµá€)) {
                    p_176069_1_.add(var9);
                    p_176069_0_.áˆºÑ¢Õ.add(var9);
                    return var9;
                }
            }
            return null;
        }
        return null;
    }
    
    public static class HorizonCode_Horizon_È extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000525";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final á p_i45564_1_, final int p_i45564_2_, final Random p_i45564_3_, final StructureBoundingBox p_i45564_4_, final EnumFacing p_i45564_5_) {
            super(p_i45564_1_, p_i45564_2_);
            this.Ó = p_i45564_5_;
            this.Âµá€ = p_i45564_4_;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final á p_175854_0_, final List p_175854_1_, final Random p_175854_2_, final int p_175854_3_, final int p_175854_4_, final int p_175854_5_, final EnumFacing p_175854_6_, final int p_175854_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, p_175854_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175854_1_, var8) == null) ? new HorizonCode_Horizon_È(p_175854_0_, p_175854_7_, p_175854_2_, var8, p_175854_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 12 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 3, 3, 7, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 1, 3, 9, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 0, 3, 0, 8, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 0, 3, 10, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 10, 3, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 4, 10, 3, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 4, 0, 4, 7, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 0, 4, 4, 4, 7, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 8, 3, 4, 8, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 4, 3, 10, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 5, 3, 5, 7, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 9, 0, 4, 9, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 11, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 11, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 2, 11, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 2, 11, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 1, 1, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 1, 1, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 2, 1, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 3, 1, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 3, 1, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 1, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 2, 1, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 3, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 1)), 1, 2, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 0)), 3, 2, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 6, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 7, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 6, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 7, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 6, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 7, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 6, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 7, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 3, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 3, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.Âµá€()), 2, 4, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.Ó()), 1, 4, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.à()), 3, 4, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó), 2, 4, 5, p_74875_3_);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, 4);
            for (int var5 = 1; var5 <= 9; ++var5) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var4), 3, var5, 3, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            if (this.HorizonCode_Horizon_È(worldIn, 2, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 2, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 2, 0, -1, p_74875_3_);
            }
            for (int var5 = 0; var5 < 9; ++var5) {
                for (int var6 = 0; var6 < 5; ++var6) {
                    this.Â(worldIn, var6, 12, var5, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var6, -1, var5, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 2, 1);
            return true;
        }
        
        @Override
        protected int Ý(final int p_180779_1_, final int p_180779_2_) {
            return 2;
        }
    }
    
    public static class Â extends Å
    {
        private Block HorizonCode_Horizon_È;
        private Block Â;
        private Block Ý;
        private Block Ø­áŒŠá;
        private static final String Ø = "CL_00000518";
        
        public Â() {
        }
        
        public Â(final á p_i45570_1_, final int p_i45570_2_, final Random p_i45570_3_, final StructureBoundingBox p_i45570_4_, final EnumFacing p_i45570_5_) {
            super(p_i45570_1_, p_i45570_2_);
            this.Ó = p_i45570_5_;
            this.Âµá€ = p_i45570_4_;
            this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(p_i45570_3_);
            this.Â = this.HorizonCode_Horizon_È(p_i45570_3_);
            this.Ý = this.HorizonCode_Horizon_È(p_i45570_3_);
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45570_3_);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("CA", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.HorizonCode_Horizon_È));
            p_143012_1_.HorizonCode_Horizon_È("CB", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.Â));
            p_143012_1_.HorizonCode_Horizon_È("CC", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.Ý));
            p_143012_1_.HorizonCode_Horizon_È("CD", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.Ø­áŒŠá));
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CA"));
            this.Â = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CB"));
            this.Ý = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CC"));
            this.Ø­áŒŠá = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CD"));
        }
        
        private Block HorizonCode_Horizon_È(final Random p_151559_1_) {
            switch (p_151559_1_.nextInt(5)) {
                case 0: {
                    return Blocks.Ñ¢È;
                }
                case 1: {
                    return Blocks.Çªáˆºá;
                }
                default: {
                    return Blocks.Ï­Ï­Ï;
                }
            }
        }
        
        public static Â HorizonCode_Horizon_È(final á p_175851_0_, final List p_175851_1_, final Random p_175851_2_, final int p_175851_3_, final int p_175851_4_, final int p_175851_5_, final EnumFacing p_175851_6_, final int p_175851_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, p_175851_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175851_1_, var8) == null) ? new Â(p_175851_0_, p_175851_7_, p_175851_2_, var8, p_175851_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 4 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 12, 4, 8, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 2, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 0, 1, 5, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 0, 1, 8, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 0, 1, 11, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 0, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 0, 0, 6, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 0, 0, 12, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 0, 11, 0, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 8, 11, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 0, 1, 3, 0, 7, Blocks.ÂµÈ.¥à(), Blocks.ÂµÈ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 0, 1, 9, 0, 7, Blocks.ÂµÈ.¥à(), Blocks.ÂµÈ.¥à(), false);
            for (int var4 = 1; var4 <= 7; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, this.HorizonCode_Horizon_È.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 1, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.HorizonCode_Horizon_È.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 2, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Â.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 4, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Â.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 5, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Ý.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 7, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Ý.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 8, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Ø­áŒŠá.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 10, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Ø­áŒŠá.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 11, 1, var4, p_74875_3_);
            }
            for (int var4 = 0; var4 < 9; ++var4) {
                for (int var5 = 0; var5 < 13; ++var5) {
                    this.Â(worldIn, var5, 4, var4, p_74875_3_);
                    this.Â(worldIn, Blocks.Âµá€.¥à(), var5, -1, var4, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    public static class Ý extends Å
    {
        private Block HorizonCode_Horizon_È;
        private Block Â;
        private static final String Ý = "CL_00000519";
        
        public Ý() {
        }
        
        public Ý(final á p_i45569_1_, final int p_i45569_2_, final Random p_i45569_3_, final StructureBoundingBox p_i45569_4_, final EnumFacing p_i45569_5_) {
            super(p_i45569_1_, p_i45569_2_);
            this.Ó = p_i45569_5_;
            this.Âµá€ = p_i45569_4_;
            this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(p_i45569_3_);
            this.Â = this.HorizonCode_Horizon_È(p_i45569_3_);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("CA", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.HorizonCode_Horizon_È));
            p_143012_1_.HorizonCode_Horizon_È("CB", Block.HorizonCode_Horizon_È.Ø­áŒŠá(this.Â));
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CA"));
            this.Â = Block.HorizonCode_Horizon_È(p_143011_1_.Ó("CB"));
        }
        
        private Block HorizonCode_Horizon_È(final Random p_151560_1_) {
            switch (p_151560_1_.nextInt(5)) {
                case 0: {
                    return Blocks.Ñ¢È;
                }
                case 1: {
                    return Blocks.Çªáˆºá;
                }
                default: {
                    return Blocks.Ï­Ï­Ï;
                }
            }
        }
        
        public static Ý HorizonCode_Horizon_È(final á p_175852_0_, final List p_175852_1_, final Random p_175852_2_, final int p_175852_3_, final int p_175852_4_, final int p_175852_5_, final EnumFacing p_175852_6_, final int p_175852_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, p_175852_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175852_1_, var8) == null) ? new Ý(p_175852_0_, p_175852_7_, p_175852_2_, var8, p_175852_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 4 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 6, 4, 8, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 2, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 0, 1, 5, 0, 7, Blocks.£Â.¥à(), Blocks.£Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 0, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 0, 0, 6, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 0, 5, 0, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 8, 5, 0, 8, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 0, 1, 3, 0, 7, Blocks.ÂµÈ.¥à(), Blocks.ÂµÈ.¥à(), false);
            for (int var4 = 1; var4 <= 7; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, this.HorizonCode_Horizon_È.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 1, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.HorizonCode_Horizon_È.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 2, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Â.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 4, 1, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, this.Â.Ý(MathHelper.HorizonCode_Horizon_È(p_74875_2_, 2, 7)), 5, 1, var4, p_74875_3_);
            }
            for (int var4 = 0; var4 < 9; ++var4) {
                for (int var5 = 0; var5 < 7; ++var5) {
                    this.Â(worldIn, var5, 4, var4, p_74875_3_);
                    this.Â(worldIn, Blocks.Âµá€.¥à(), var5, -1, var4, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    public static class Ø­áŒŠá extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000522";
        
        public Ø­áŒŠá() {
        }
        
        public Ø­áŒŠá(final á p_i45567_1_, final int p_i45567_2_, final Random p_i45567_3_, final StructureBoundingBox p_i45567_4_, final EnumFacing p_i45567_5_) {
            super(p_i45567_1_, p_i45567_2_);
            this.Ó = p_i45567_5_;
            this.Âµá€ = p_i45567_4_;
        }
        
        public static Ø­áŒŠá HorizonCode_Horizon_È(final á p_175857_0_, final List p_175857_1_, final Random p_175857_2_, final int p_175857_3_, final int p_175857_4_, final int p_175857_5_, final EnumFacing p_175857_6_, final int p_175857_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, p_175857_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175857_1_, var8) == null) ? new Ø­áŒŠá(p_175857_0_, p_175857_7_, p_175857_2_, var8, p_175857_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 7 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 7, 4, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 6, 8, 4, 10, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 0, 6, 8, 0, 10, Blocks.Âµá€.¥à(), Blocks.Âµá€.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, 0, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 6, 2, 1, 10, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 6, 8, 1, 10, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 10, 7, 1, 10, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 0, 0, 8, 3, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 0, 7, 1, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 5, 7, 1, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 5, 7, 3, 5, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 4, 8, 4, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 0, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 0, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 4, 3, p_74875_3_);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3);
            final int var5 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var4), var7, 4 + var6, var6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var5), var7, 4 + var6, 5 - var6, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 3, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 6, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 2, 1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽá.¥à(), 2, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 1, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3)), 2, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 1)), 1, 1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 0, 1, 7, 0, 3, Blocks.£ÂµÄ.¥à(), Blocks.£ÂµÄ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£ÂµÄ.¥à(), 6, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£ÂµÄ.¥à(), 6, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó), 2, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            if (this.HorizonCode_Horizon_È(worldIn, 2, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 2, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 2, 0, -1, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 6, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 6, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.Âµá€()), 6, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 6, 1, 5, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            for (int var6 = 0; var6 < 5; ++var6) {
                for (int var7 = 0; var7 < 9; ++var7) {
                    this.Â(worldIn, var7, 7, var6, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var7, -1, var6, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 2, 2);
            return true;
        }
        
        @Override
        protected int Ý(final int p_180779_1_, final int p_180779_2_) {
            return (p_180779_1_ == 0) ? 4 : super.Ý(p_180779_1_, p_180779_2_);
        }
    }
    
    public static class Âµá€ extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000517";
        
        public Âµá€() {
        }
        
        public Âµá€(final á p_i45571_1_, final int p_i45571_2_, final Random p_i45571_3_, final StructureBoundingBox p_i45571_4_, final EnumFacing p_i45571_5_) {
            super(p_i45571_1_, p_i45571_2_);
            this.Ó = p_i45571_5_;
            this.Âµá€ = p_i45571_4_;
        }
        
        public static Âµá€ HorizonCode_Horizon_È(final á p_175850_0_, final List p_175850_1_, final Random p_175850_2_, final int p_175850_3_, final int p_175850_4_, final int p_175850_5_, final EnumFacing p_175850_6_, final int p_175850_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, p_175850_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175850_1_, var8) == null) ? new Âµá€(p_175850_0_, p_175850_7_, p_175850_2_, var8, p_175850_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 9 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 7, 5, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 8, 0, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 0, 8, 5, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 6, 1, 8, 6, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 7, 2, 8, 7, 3, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3);
            final int var5 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var4), var7, 6 + var6, var6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var5), var7, 6 + var6, 5 - var6, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 5, 8, 1, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 0, 8, 1, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 0, 7, 1, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 2, 0, 0, 4, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 2, 5, 0, 4, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 2, 5, 8, 4, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 2, 0, 8, 4, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 2, 1, 0, 4, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 5, 7, 4, 5, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 2, 1, 8, 4, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 0, 7, 4, 0, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 6, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 6, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 3, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 6, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 1, 7, 4, 1, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 4, 7, 4, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 4, 7, 3, 4, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 7, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 0)), 7, 1, 3, p_74875_3_);
            int var6 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var6), 6, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var6), 5, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var6), 4, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var6), 3, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 6, 1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽá.¥à(), 6, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽá.¥à(), 4, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÉ.¥à(), 7, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            if (this.HorizonCode_Horizon_È(worldIn, 1, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 1, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 1, 0, -1, p_74875_3_);
            }
            for (int var7 = 0; var7 < 6; ++var7) {
                for (int var8 = 0; var8 < 9; ++var8) {
                    this.Â(worldIn, var8, 9, var7, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var8, -1, var7, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 2, 1);
            return true;
        }
        
        @Override
        protected int Ý(final int p_180779_1_, final int p_180779_2_) {
            return 1;
        }
    }
    
    public static class Ó extends Å
    {
        private static final List HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000526";
        
        static {
            HorizonCode_Horizon_È = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áŒŠÆ, 0, 1, 3, 3), new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 1, 3, 5), new WeightedRandomChestContent(Items.Ç, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Âµá€, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Â, 0, 1, 1, 5), new WeightedRandomChestContent(Items.á, 0, 1, 1, 5), new WeightedRandomChestContent(Items.áˆºáˆºÈ, 0, 1, 1, 5), new WeightedRandomChestContent(Items.Ï­à, 0, 1, 1, 5), new WeightedRandomChestContent(Items.ÇŽá€, 0, 1, 1, 5), new WeightedRandomChestContent(Items.Ï, 0, 1, 1, 5), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽá€), 0, 3, 7, 5), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø), 0, 3, 7, 5), new WeightedRandomChestContent(Items.Û, 0, 1, 1, 3), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 1), new WeightedRandomChestContent(Items.¥Ê, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÐƒÓ, 0, 1, 1, 1) });
        }
        
        public Ó() {
        }
        
        public Ó(final á p_i45563_1_, final int p_i45563_2_, final Random p_i45563_3_, final StructureBoundingBox p_i45563_4_, final EnumFacing p_i45563_5_) {
            super(p_i45563_1_, p_i45563_2_);
            this.Ó = p_i45563_5_;
            this.Âµá€ = p_i45563_4_;
        }
        
        public static Ó HorizonCode_Horizon_È(final á p_175855_0_, final List p_175855_1_, final Random p_175855_2_, final int p_175855_3_, final int p_175855_4_, final int p_175855_5_, final EnumFacing p_175855_6_, final int p_175855_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, p_175855_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175855_1_, var8) == null) ? new Ó(p_175855_0_, p_175855_7_, p_175855_2_, var8, p_175855_6_) : null;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Chest", this.Â);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Â = p_143011_1_.£á("Chest");
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 6 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 9, 4, 6, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 9, 0, 6, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 0, 9, 4, 6, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 0, 9, 5, 6, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 1, 8, 5, 5, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 0, 4, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 0, 3, 4, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 6, 0, 4, 6, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 3, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 2, 3, 3, 2, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 3, 5, 3, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 5, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 6, 5, 3, 6, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 0, 5, 3, 0, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 1, 0, 9, 3, 0, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 1, 4, 9, 4, 6, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.á.¥à(), 7, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.á.¥à(), 8, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), 9, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), 9, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 2, 4, 8, 2, 5, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, 1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£Ó.¥à(), 6, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£Ó.¥à(), 6, 3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£ÂµÄ.¥à(), 8, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 2, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽá.¥à(), 2, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 1, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3)), 2, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 1)), 1, 1, 4, p_74875_3_);
            if (!this.Â && p_74875_3_.HorizonCode_Horizon_È(new BlockPos(this.HorizonCode_Horizon_È(5, 5), this.HorizonCode_Horizon_È(1), this.Â(5, 5)))) {
                this.Â = true;
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 5, 1, 5, Ó.HorizonCode_Horizon_È, 3 + p_74875_2_.nextInt(6));
            }
            for (int var4 = 6; var4 <= 8; ++var4) {
                if (this.HorizonCode_Horizon_È(worldIn, var4, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, var4, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), var4, 0, -1, p_74875_3_);
                }
            }
            for (int var4 = 0; var4 < 7; ++var4) {
                for (int var5 = 0; var5 < 10; ++var5) {
                    this.Â(worldIn, var5, 6, var4, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var5, -1, var4, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 1, 1, 1);
            return true;
        }
        
        @Override
        protected int Ý(final int p_180779_1_, final int p_180779_2_) {
            return 3;
        }
    }
    
    public static class à extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000530";
        
        public à() {
        }
        
        public à(final á p_i45561_1_, final int p_i45561_2_, final Random p_i45561_3_, final StructureBoundingBox p_i45561_4_, final EnumFacing p_i45561_5_) {
            super(p_i45561_1_, p_i45561_2_);
            this.Ó = p_i45561_5_;
            this.Âµá€ = p_i45561_4_;
        }
        
        public static à HorizonCode_Horizon_È(final á p_175849_0_, final List p_175849_1_, final Random p_175849_2_, final int p_175849_3_, final int p_175849_4_, final int p_175849_5_, final EnumFacing p_175849_6_, final int p_175849_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, p_175849_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175849_1_, var8) == null) ? new à(p_175849_0_, p_175849_7_, p_175849_2_, var8, p_175849_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 7 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 7, 4, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 6, 8, 4, 10, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 0, 5, 8, 0, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 0, 0, 8, 3, 10, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 0, 7, 2, 0, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 5, 2, 1, 5, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 0, 6, 2, 3, 10, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 0, 10, 7, 3, 10, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 5, 2, 3, 5, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 4, 3, 4, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 0, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 0, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 4, 4, p_74875_3_);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3);
            final int var5 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var4), var7, 4 + var6, var6, p_74875_3_);
                    if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6)) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var5), var7, 4 + var6, 5 - var6, p_74875_3_);
                    }
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 4, 5, 3, 4, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 4, 2, 7, 4, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 5, 4, 4, 5, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 5, 4, 6, 5, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 6, 3, 5, 6, 10, Blocks.à.¥à(), Blocks.à.¥à(), false);
            int var6 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 0);
            for (int var7 = 4; var7 >= 1; --var7) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), var7, 2 + var7, 7 - var7, p_74875_3_);
                for (int var8 = 8 - var7; var8 <= 10; ++var8) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var6), var7, 2 + var7, var8, p_74875_3_);
                }
            }
            int var7 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 1);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 6, 6, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 7, 5, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var7), 6, 6, 4, p_74875_3_);
            for (int var8 = 6; var8 <= 8; ++var8) {
                for (int var9 = 5; var9 <= 10; ++var9) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÏ.Ý(var7), var8, 12 - var8, var9, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 4, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 6, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 8, 2, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 8, 2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 2, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 2, 2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 4, 4, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 5, 4, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 6, 4, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 5, 5, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó), 2, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, -1, 3, 2, -1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            if (this.HorizonCode_Horizon_È(worldIn, 2, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 2, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 2, 0, -1, p_74875_3_);
            }
            for (int var8 = 0; var8 < 5; ++var8) {
                for (int var9 = 0; var9 < 9; ++var9) {
                    this.Â(worldIn, var9, 7, var8, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var9, -1, var8, p_74875_3_);
                }
            }
            for (int var8 = 5; var8 < 11; ++var8) {
                for (int var9 = 2; var9 < 9; ++var9) {
                    this.Â(worldIn, var9, 7, var8, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var9, -1, var8, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 2, 2);
            return true;
        }
    }
    
    public static class Ø extends Å
    {
        private boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00000523";
        
        public Ø() {
        }
        
        public Ø(final á p_i45566_1_, final int p_i45566_2_, final Random p_i45566_3_, final StructureBoundingBox p_i45566_4_, final EnumFacing p_i45566_5_) {
            super(p_i45566_1_, p_i45566_2_);
            this.Ó = p_i45566_5_;
            this.Âµá€ = p_i45566_4_;
            this.HorizonCode_Horizon_È = p_i45566_3_.nextBoolean();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Terrace", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.£á("Terrace");
        }
        
        public static Ø HorizonCode_Horizon_È(final á p_175858_0_, final List p_175858_1_, final Random p_175858_2_, final int p_175858_3_, final int p_175858_4_, final int p_175858_5_, final EnumFacing p_175858_6_, final int p_175858_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, p_175858_6_);
            return (StructureComponent.HorizonCode_Horizon_È(p_175858_1_, var8) != null) ? null : new Ø(p_175858_0_, p_175858_7_, p_175858_2_, var8, p_175858_6_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 6 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 0, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 1, 3, 4, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 0, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 1, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 4, 3, 3, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 2, 2, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 4, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 1, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 1, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 1, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 2, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 3, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 3, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 3, 1, 0, p_74875_3_);
            if (this.HorizonCode_Horizon_È(worldIn, 2, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 2, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 2, 0, -1, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 3, 3, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            if (this.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 0, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 2, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 3, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 0, 5, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 5, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 2, 5, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 3, 5, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 5, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 5, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 5, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 5, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 0, 5, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 0, 5, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 0, 5, 3, p_74875_3_);
            }
            if (this.HorizonCode_Horizon_È) {
                final int var4 = this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, 3);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var4), 3, 1, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var4), 3, 2, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var4), 3, 3, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var4), 3, 4, 3, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó), 2, 3, 1, p_74875_3_);
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 5; ++var5) {
                    this.Â(worldIn, var5, 6, var4, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var5, -1, var4, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 2, 1);
            return true;
        }
    }
    
    public static class áŒŠÆ extends ÂµÈ
    {
        private int HorizonCode_Horizon_È;
        private static final String Â = "CL_00000528";
        
        public áŒŠÆ() {
        }
        
        public áŒŠÆ(final á p_i45562_1_, final int p_i45562_2_, final Random p_i45562_3_, final StructureBoundingBox p_i45562_4_, final EnumFacing p_i45562_5_) {
            super(p_i45562_1_, p_i45562_2_);
            this.Ó = p_i45562_5_;
            this.Âµá€ = p_i45562_4_;
            this.HorizonCode_Horizon_È = Math.max(p_i45562_4_.Ý(), p_i45562_4_.Âµá€());
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Length", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.Ó("Length");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            boolean var4 = false;
            for (int var5 = p_74861_3_.nextInt(5); var5 < this.HorizonCode_Horizon_È - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
                final StructureComponent var6 = this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.Âµá€.Ý(), var6.Âµá€.Âµá€());
                    var4 = true;
                }
            }
            for (int var5 = p_74861_3_.nextInt(5); var5 < this.HorizonCode_Horizon_È - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
                final StructureComponent var6 = this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.Âµá€.Ý(), var6.Âµá€.Âµá€());
                    var4 = true;
                }
            }
            if (var4 && p_74861_3_.nextInt(3) > 0 && this.Ó != null) {
                switch (ˆÏ­.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ý, EnumFacing.Âµá€, this.Ý());
                        break;
                    }
                    case 2: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ó - 2, EnumFacing.Âµá€, this.Ý());
                        break;
                    }
                    case 3: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                        break;
                    }
                    case 4: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá - 2, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                        break;
                    }
                }
            }
            if (var4 && p_74861_3_.nextInt(3) > 0 && this.Ó != null) {
                switch (ˆÏ­.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ý, EnumFacing.Ó, this.Ý());
                        break;
                    }
                    case 2: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ó - 2, EnumFacing.Ó, this.Ý());
                        break;
                    }
                    case 3: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                        break;
                    }
                    case 4: {
                        Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá - 2, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                        break;
                    }
                }
            }
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final á p_175848_0_, final List p_175848_1_, final Random p_175848_2_, final int p_175848_3_, final int p_175848_4_, final int p_175848_5_, final EnumFacing p_175848_6_) {
            for (int var7 = 7 * MathHelper.HorizonCode_Horizon_È(p_175848_2_, 3, 5); var7 >= 7; var7 -= 7) {
                final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, var7, p_175848_6_);
                if (StructureComponent.HorizonCode_Horizon_È(p_175848_1_, var8) == null) {
                    return var8;
                }
            }
            return null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            final IBlockState var4 = this.HorizonCode_Horizon_È(Blocks.Å.¥à());
            final IBlockState var5 = this.HorizonCode_Horizon_È(Blocks.Ó.¥à());
            for (int var6 = this.Âµá€.HorizonCode_Horizon_È; var6 <= this.Âµá€.Ø­áŒŠá; ++var6) {
                for (int var7 = this.Âµá€.Ý; var7 <= this.Âµá€.Ó; ++var7) {
                    BlockPos var8 = new BlockPos(var6, 64, var7);
                    if (p_74875_3_.HorizonCode_Horizon_È(var8)) {
                        var8 = worldIn.ˆà(var8).Âµá€();
                        worldIn.HorizonCode_Horizon_È(var8, var4, 2);
                        worldIn.HorizonCode_Horizon_È(var8.Âµá€(), var5, 2);
                    }
                }
            }
            return true;
        }
    }
    
    public static class áˆºÑ¢Õ
    {
        public Class HorizonCode_Horizon_È;
        public final int Â;
        public int Ý;
        public int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000521";
        
        public áˆºÑ¢Õ(final Class p_i2098_1_, final int p_i2098_2_, final int p_i2098_3_) {
            this.HorizonCode_Horizon_È = p_i2098_1_;
            this.Â = p_i2098_2_;
            this.Ø­áŒŠá = p_i2098_3_;
        }
        
        public boolean HorizonCode_Horizon_È(final int p_75085_1_) {
            return this.Ø­áŒŠá == 0 || this.Ý < this.Ø­áŒŠá;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá == 0 || this.Ý < this.Ø­áŒŠá;
        }
    }
    
    public abstract static class ÂµÈ extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000532";
        
        public ÂµÈ() {
        }
        
        protected ÂµÈ(final á p_i2108_1_, final int p_i2108_2_) {
            super(p_i2108_1_, p_i2108_2_);
        }
    }
    
    public static class á extends £à
    {
        public WorldChunkManager HorizonCode_Horizon_È;
        public boolean Â;
        public int Ý;
        public áˆºÑ¢Õ Ø­áŒŠá;
        public List Ø;
        public List áŒŠÆ;
        public List áˆºÑ¢Õ;
        private static final String á = "CL_00000527";
        
        public á() {
            this.áŒŠÆ = Lists.newArrayList();
            this.áˆºÑ¢Õ = Lists.newArrayList();
        }
        
        public á(final WorldChunkManager p_i2104_1_, final int p_i2104_2_, final Random p_i2104_3_, final int p_i2104_4_, final int p_i2104_5_, final List p_i2104_6_, final int p_i2104_7_) {
            super(null, 0, p_i2104_3_, p_i2104_4_, p_i2104_5_);
            this.áŒŠÆ = Lists.newArrayList();
            this.áˆºÑ¢Õ = Lists.newArrayList();
            this.HorizonCode_Horizon_È = p_i2104_1_;
            this.Ø = p_i2104_6_;
            this.Ý = p_i2104_7_;
            final BiomeGenBase var8 = p_i2104_1_.HorizonCode_Horizon_È(new BlockPos(p_i2104_4_, 0, p_i2104_5_), BiomeGenBase.ÇªÓ);
            this.HorizonCode_Horizon_È(this.Â = (var8 == BiomeGenBase.ˆà || var8 == BiomeGenBase.ÇŽÕ));
        }
        
        public WorldChunkManager Âµá€() {
            return this.HorizonCode_Horizon_È;
        }
    }
    
    static final class ˆÏ­
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001968";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                ˆÏ­.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                ˆÏ­.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                ˆÏ­.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                ˆÏ­.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
    
    public static class £á extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000520";
        
        public £á() {
        }
        
        public £á(final á p_i45568_1_, final int p_i45568_2_, final Random p_i45568_3_, final StructureBoundingBox p_i45568_4_, final EnumFacing p_i45568_5_) {
            super(p_i45568_1_, p_i45568_2_);
            this.Ó = p_i45568_5_;
            this.Âµá€ = p_i45568_4_;
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final á p_175856_0_, final List p_175856_1_, final Random p_175856_2_, final int p_175856_3_, final int p_175856_4_, final int p_175856_5_, final EnumFacing p_175856_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, p_175856_6_);
            return (StructureComponent.HorizonCode_Horizon_È(p_175856_1_, var7) != null) ? null : var7;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 4 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 2, 3, 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 0, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÂµà.Ý(EnumDyeColor.HorizonCode_Horizon_È.Ý()), 1, 3, 0, p_74875_3_);
            final boolean var4 = this.Ó == EnumFacing.Ó || this.Ó == EnumFacing.Ý;
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.Ó()), var4 ? 2 : 0, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó), 1, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.à()), var4 ? 0 : 2, 3, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, this.Ó.Âµá€()), 1, 3, -1, p_74875_3_);
            return true;
        }
    }
    
    abstract static class Å extends StructureComponent
    {
        protected int ÂµÈ;
        private int HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000531";
        
        public Å() {
            this.ÂµÈ = -1;
        }
        
        protected Å(final á p_i2107_1_, final int p_i2107_2_) {
            super(p_i2107_2_);
            this.ÂµÈ = -1;
            if (p_i2107_1_ != null) {
                this.Â = p_i2107_1_.Â;
            }
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            p_143012_1_.HorizonCode_Horizon_È("HPos", this.ÂµÈ);
            p_143012_1_.HorizonCode_Horizon_È("VCount", this.HorizonCode_Horizon_È);
            p_143012_1_.HorizonCode_Horizon_È("Desert", this.Â);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            this.ÂµÈ = p_143011_1_.Ó("HPos");
            this.HorizonCode_Horizon_È = p_143011_1_.Ó("VCount");
            this.Â = p_143011_1_.£á("Desert");
        }
        
        protected StructureComponent HorizonCode_Horizon_È(final á p_74891_1_, final List p_74891_2_, final Random p_74891_3_, final int p_74891_4_, final int p_74891_5_) {
            if (this.Ó != null) {
                switch (ˆÏ­.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        return Ø­áŒŠá(p_74891_1_, p_74891_2_, p_74891_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74891_4_, this.Âµá€.Ý + p_74891_5_, EnumFacing.Âµá€, this.Ý());
                    }
                    case 2: {
                        return Ø­áŒŠá(p_74891_1_, p_74891_2_, p_74891_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74891_4_, this.Âµá€.Ý + p_74891_5_, EnumFacing.Âµá€, this.Ý());
                    }
                    case 3: {
                        return Ø­áŒŠá(p_74891_1_, p_74891_2_, p_74891_3_, this.Âµá€.HorizonCode_Horizon_È + p_74891_5_, this.Âµá€.Â + p_74891_4_, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                    }
                    case 4: {
                        return Ø­áŒŠá(p_74891_1_, p_74891_2_, p_74891_3_, this.Âµá€.HorizonCode_Horizon_È + p_74891_5_, this.Âµá€.Â + p_74891_4_, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent Â(final á p_74894_1_, final List p_74894_2_, final Random p_74894_3_, final int p_74894_4_, final int p_74894_5_) {
            if (this.Ó != null) {
                switch (ˆÏ­.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        return Ø­áŒŠá(p_74894_1_, p_74894_2_, p_74894_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74894_4_, this.Âµá€.Ý + p_74894_5_, EnumFacing.Ó, this.Ý());
                    }
                    case 2: {
                        return Ø­áŒŠá(p_74894_1_, p_74894_2_, p_74894_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74894_4_, this.Âµá€.Ý + p_74894_5_, EnumFacing.Ó, this.Ý());
                    }
                    case 3: {
                        return Ø­áŒŠá(p_74894_1_, p_74894_2_, p_74894_3_, this.Âµá€.HorizonCode_Horizon_È + p_74894_5_, this.Âµá€.Â + p_74894_4_, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                    }
                    case 4: {
                        return Ø­áŒŠá(p_74894_1_, p_74894_2_, p_74894_3_, this.Âµá€.HorizonCode_Horizon_È + p_74894_5_, this.Âµá€.Â + p_74894_4_, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                    }
                }
            }
            return null;
        }
        
        protected int Â(final World worldIn, final StructureBoundingBox p_74889_2_) {
            int var3 = 0;
            int var4 = 0;
            for (int var5 = this.Âµá€.Ý; var5 <= this.Âµá€.Ó; ++var5) {
                for (int var6 = this.Âµá€.HorizonCode_Horizon_È; var6 <= this.Âµá€.Ø­áŒŠá; ++var6) {
                    final BlockPos var7 = new BlockPos(var6, 64, var5);
                    if (p_74889_2_.HorizonCode_Horizon_È(var7)) {
                        var3 += Math.max(worldIn.ˆà(var7).Â(), worldIn.£à.áŒŠÆ());
                        ++var4;
                    }
                }
            }
            if (var4 == 0) {
                return -1;
            }
            return var3 / var4;
        }
        
        protected static boolean HorizonCode_Horizon_È(final StructureBoundingBox p_74895_0_) {
            return p_74895_0_ != null && p_74895_0_.Â > 10;
        }
        
        protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_74893_2_, final int p_74893_3_, final int p_74893_4_, final int p_74893_5_, final int p_74893_6_) {
            if (this.HorizonCode_Horizon_È < p_74893_6_) {
                for (int var7 = this.HorizonCode_Horizon_È; var7 < p_74893_6_; ++var7) {
                    final int var8 = this.HorizonCode_Horizon_È(p_74893_3_ + var7, p_74893_5_);
                    final int var9 = this.HorizonCode_Horizon_È(p_74893_4_);
                    final int var10 = this.Â(p_74893_3_ + var7, p_74893_5_);
                    if (!p_74893_2_.HorizonCode_Horizon_È(new BlockPos(var8, var9, var10))) {
                        break;
                    }
                    ++this.HorizonCode_Horizon_È;
                    final EntityVillager var11 = new EntityVillager(worldIn);
                    var11.Â(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                    var11.HorizonCode_Horizon_È(worldIn.Ê(new BlockPos(var11)), null);
                    var11.ˆà(this.Ý(var7, var11.ÇŽ()));
                    worldIn.HorizonCode_Horizon_È(var11);
                }
            }
        }
        
        protected int Ý(final int p_180779_1_, final int p_180779_2_) {
            return p_180779_2_;
        }
        
        protected IBlockState HorizonCode_Horizon_È(final IBlockState p_175847_1_) {
            if (this.Â) {
                if (p_175847_1_.Ý() == Blocks.¥Æ || p_175847_1_.Ý() == Blocks.Ø­à) {
                    return Blocks.ŒÏ.¥à();
                }
                if (p_175847_1_.Ý() == Blocks.Ó) {
                    return Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â());
                }
                if (p_175847_1_.Ý() == Blocks.à) {
                    return Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â());
                }
                if (p_175847_1_.Ý() == Blocks.áˆºÏ) {
                    return Blocks.µÏ.¥à().HorizonCode_Horizon_È(BlockStairs.Õ, p_175847_1_.HorizonCode_Horizon_È(BlockStairs.Õ));
                }
                if (p_175847_1_.Ý() == Blocks.ˆÓ) {
                    return Blocks.µÏ.¥à().HorizonCode_Horizon_È(BlockStairs.Õ, p_175847_1_.HorizonCode_Horizon_È(BlockStairs.Õ));
                }
                if (p_175847_1_.Ý() == Blocks.Å) {
                    return Blocks.ŒÏ.¥à();
                }
            }
            return p_175847_1_;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final World worldIn, final IBlockState p_175811_2_, final int p_175811_3_, final int p_175811_4_, final int p_175811_5_, final StructureBoundingBox p_175811_6_) {
            final IBlockState var7 = this.HorizonCode_Horizon_È(p_175811_2_);
            super.HorizonCode_Horizon_È(worldIn, var7, p_175811_3_, p_175811_4_, p_175811_5_, p_175811_6_);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175804_2_, final int p_175804_3_, final int p_175804_4_, final int p_175804_5_, final int p_175804_6_, final int p_175804_7_, final int p_175804_8_, final IBlockState p_175804_9_, final IBlockState p_175804_10_, final boolean p_175804_11_) {
            final IBlockState var12 = this.HorizonCode_Horizon_È(p_175804_9_);
            final IBlockState var13 = this.HorizonCode_Horizon_È(p_175804_10_);
            super.HorizonCode_Horizon_È(worldIn, p_175804_2_, p_175804_3_, p_175804_4_, p_175804_5_, p_175804_6_, p_175804_7_, p_175804_8_, var12, var13, p_175804_11_);
        }
        
        @Override
        protected void Â(final World worldIn, final IBlockState p_175808_2_, final int p_175808_3_, final int p_175808_4_, final int p_175808_5_, final StructureBoundingBox p_175808_6_) {
            final IBlockState var7 = this.HorizonCode_Horizon_È(p_175808_2_);
            super.Â(worldIn, var7, p_175808_3_, p_175808_4_, p_175808_5_, p_175808_6_);
        }
        
        protected void HorizonCode_Horizon_È(final boolean p_175846_1_) {
            this.Â = p_175846_1_;
        }
    }
    
    public static class £à extends Å
    {
        private static final String HorizonCode_Horizon_È = "CL_00000533";
        
        public £à() {
        }
        
        public £à(final á p_i2109_1_, final int p_i2109_2_, final Random p_i2109_3_, final int p_i2109_4_, final int p_i2109_5_) {
            super(p_i2109_1_, p_i2109_2_);
            this.Ó = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i2109_3_);
            switch (ˆÏ­.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                case 1:
                case 2: {
                    this.Âµá€ = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                    break;
                }
                default: {
                    this.Âµá€ = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                    break;
                }
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Âµá€ - 4, this.Âµá€.Ý + 1, EnumFacing.Âµá€, this.Ý());
            Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Âµá€ - 4, this.Âµá€.Ý + 1, EnumFacing.Ó, this.Ý());
            Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Âµá€ - 4, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
            Âµá€((á)p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Âµá€ - 4, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 3, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 4, 12, 4, Blocks.Ó.¥à(), Blocks.áˆºÑ¢Õ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 12, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 3, 12, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, 12, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 3, 12, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 13, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 14, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 13, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 14, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 13, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 14, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 13, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 4, 14, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 15, 1, 4, 15, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            for (int var4 = 0; var4 <= 5; ++var4) {
                for (int var5 = 0; var5 <= 5; ++var5) {
                    if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Å.¥à(), var5, 11, var4, p_74875_3_);
                        this.Â(worldIn, var5, 12, var4, p_74875_3_);
                    }
                }
            }
            return true;
        }
    }
    
    public static class µà extends Å
    {
        private boolean HorizonCode_Horizon_È;
        private int Â;
        private static final String Ý = "CL_00000524";
        
        public µà() {
        }
        
        public µà(final á p_i45565_1_, final int p_i45565_2_, final Random p_i45565_3_, final StructureBoundingBox p_i45565_4_, final EnumFacing p_i45565_5_) {
            super(p_i45565_1_, p_i45565_2_);
            this.Ó = p_i45565_5_;
            this.Âµá€ = p_i45565_4_;
            this.HorizonCode_Horizon_È = p_i45565_3_.nextBoolean();
            this.Â = p_i45565_3_.nextInt(3);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("T", this.Â);
            p_143012_1_.HorizonCode_Horizon_È("C", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Â = p_143011_1_.Ó("T");
            this.HorizonCode_Horizon_È = p_143011_1_.£á("C");
        }
        
        public static µà HorizonCode_Horizon_È(final á p_175853_0_, final List p_175853_1_, final Random p_175853_2_, final int p_175853_3_, final int p_175853_4_, final int p_175853_5_, final EnumFacing p_175853_6_, final int p_175853_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.HorizonCode_Horizon_È(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, p_175853_6_);
            return (Å.HorizonCode_Horizon_È(var8) && StructureComponent.HorizonCode_Horizon_È(p_175853_1_, var8) == null) ? new µà(p_175853_0_, p_175853_7_, p_175853_2_, var8, p_175853_6_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.ÂµÈ < 0) {
                this.ÂµÈ = this.Â(worldIn, p_74875_3_);
                if (this.ÂµÈ < 0) {
                    return true;
                }
                this.Âµá€.HorizonCode_Horizon_È(0, this.ÂµÈ - this.Âµá€.Âµá€ + 6 - 1, 0);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.Ó.¥à(), Blocks.Ó.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.Âµá€.¥à(), Blocks.Âµá€.¥à(), false);
            if (this.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 1, 2, 4, 3, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 1, 4, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 2, 4, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 1, 4, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 2, 4, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 4, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 0, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 3, 4, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 3, 4, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥Æ.¥à(), 3, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.à.¥à(), Blocks.à.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 0, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÈ.¥à(), 3, 2, 2, p_74875_3_);
            if (this.Â > 0) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), this.Â, 1, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽá.¥à(), this.Â, 2, 3, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 1, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 2, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.Â(this.HorizonCode_Horizon_È(Blocks.Ï­Ô, 1)));
            if (this.HorizonCode_Horizon_È(worldIn, 1, 0, -1, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È(worldIn, 1, -1, -1, p_74875_3_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3)), 1, 0, -1, p_74875_3_);
            }
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    this.Â(worldIn, var5, 6, var4, p_74875_3_);
                    this.Â(worldIn, Blocks.Ó.¥à(), var5, -1, var4, p_74875_3_);
                }
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 2, 1);
            return true;
        }
    }
}
