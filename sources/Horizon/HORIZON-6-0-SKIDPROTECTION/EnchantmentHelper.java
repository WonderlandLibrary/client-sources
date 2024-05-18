package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;

public class EnchantmentHelper
{
    private static final Random HorizonCode_Horizon_È;
    private static final Ø­áŒŠá Â;
    private static final Âµá€ Ý;
    private static final Â Ø­áŒŠá;
    private static final HorizonCode_Horizon_È Âµá€;
    private static final String Ó = "CL_00000107";
    
    static {
        HorizonCode_Horizon_È = new Random();
        Â = new Ø­áŒŠá(null);
        Ý = new Âµá€(null);
        Ø­áŒŠá = new Â(null);
        Âµá€ = new HorizonCode_Horizon_È(null);
    }
    
    public static int HorizonCode_Horizon_È(final int p_77506_0_, final ItemStack p_77506_1_) {
        if (p_77506_1_ == null) {
            return 0;
        }
        final NBTTagList var2 = p_77506_1_.£à();
        if (var2 == null) {
            return 0;
        }
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final short var4 = var2.Â(var3).Âµá€("id");
            final short var5 = var2.Â(var3).Âµá€("lvl");
            if (var4 == p_77506_0_) {
                return var5;
            }
        }
        return 0;
    }
    
    public static Map HorizonCode_Horizon_È(final ItemStack p_82781_0_) {
        final LinkedHashMap var1 = Maps.newLinkedHashMap();
        final NBTTagList var2 = (p_82781_0_.HorizonCode_Horizon_È() == Items.Çªáˆºá) ? Items.Çªáˆºá.ÂµÈ(p_82781_0_) : p_82781_0_.£à();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final short var4 = var2.Â(var3).Âµá€("id");
                final short var5 = var2.Â(var3).Âµá€("lvl");
                var1.put((int)var4, (int)var5);
            }
        }
        return var1;
    }
    
    public static void HorizonCode_Horizon_È(final Map p_82782_0_, final ItemStack p_82782_1_) {
        final NBTTagList var2 = new NBTTagList();
        for (final int var4 : p_82782_0_.keySet()) {
            final Enchantment var5 = Enchantment.HorizonCode_Horizon_È(var4);
            if (var5 != null) {
                final NBTTagCompound var6 = new NBTTagCompound();
                var6.HorizonCode_Horizon_È("id", (short)var4);
                var6.HorizonCode_Horizon_È("lvl", (short)(int)p_82782_0_.get(var4));
                var2.HorizonCode_Horizon_È(var6);
                if (p_82782_1_.HorizonCode_Horizon_È() != Items.Çªáˆºá) {
                    continue;
                }
                Items.Çªáˆºá.HorizonCode_Horizon_È(p_82782_1_, new EnchantmentData(var5, p_82782_0_.get(var4)));
            }
        }
        if (var2.Âµá€() > 0) {
            if (p_82782_1_.HorizonCode_Horizon_È() != Items.Çªáˆºá) {
                p_82782_1_.HorizonCode_Horizon_È("ench", var2);
            }
        }
        else if (p_82782_1_.£á()) {
            p_82782_1_.Å().Å("ench");
        }
    }
    
    public static int HorizonCode_Horizon_È(final int p_77511_0_, final ItemStack[] p_77511_1_) {
        if (p_77511_1_ == null) {
            return 0;
        }
        int var2 = 0;
        for (final ItemStack var5 : p_77511_1_) {
            final int var6 = HorizonCode_Horizon_È(p_77511_0_, var5);
            if (var6 > var2) {
                var2 = var6;
            }
        }
        return var2;
    }
    
    private static void HorizonCode_Horizon_È(final Ý p_77518_0_, final ItemStack p_77518_1_) {
        if (p_77518_1_ != null) {
            final NBTTagList var2 = p_77518_1_.£à();
            if (var2 != null) {
                for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                    final short var4 = var2.Â(var3).Âµá€("id");
                    final short var5 = var2.Â(var3).Âµá€("lvl");
                    if (Enchantment.HorizonCode_Horizon_È(var4) != null) {
                        p_77518_0_.HorizonCode_Horizon_È(Enchantment.HorizonCode_Horizon_È(var4), var5);
                    }
                }
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final Ý p_77516_0_, final ItemStack[] p_77516_1_) {
        for (final ItemStack var5 : p_77516_1_) {
            HorizonCode_Horizon_È(p_77516_0_, var5);
        }
    }
    
    public static int HorizonCode_Horizon_È(final ItemStack[] p_77508_0_, final DamageSource p_77508_1_) {
        EnchantmentHelper.Â.HorizonCode_Horizon_È = 0;
        EnchantmentHelper.Â.Â = p_77508_1_;
        HorizonCode_Horizon_È(EnchantmentHelper.Â, p_77508_0_);
        if (EnchantmentHelper.Â.HorizonCode_Horizon_È > 25) {
            EnchantmentHelper.Â.HorizonCode_Horizon_È = 25;
        }
        return (EnchantmentHelper.Â.HorizonCode_Horizon_È + 1 >> 1) + EnchantmentHelper.HorizonCode_Horizon_È.nextInt((EnchantmentHelper.Â.HorizonCode_Horizon_È >> 1) + 1);
    }
    
    public static float HorizonCode_Horizon_È(final ItemStack p_152377_0_, final EnumCreatureAttribute p_152377_1_) {
        EnchantmentHelper.Ý.HorizonCode_Horizon_È = 0.0f;
        EnchantmentHelper.Ý.Â = p_152377_1_;
        HorizonCode_Horizon_È(EnchantmentHelper.Ý, p_152377_0_);
        return EnchantmentHelper.Ý.HorizonCode_Horizon_È;
    }
    
    public static void HorizonCode_Horizon_È(final EntityLivingBase p_151384_0_, final Entity p_151384_1_) {
        EnchantmentHelper.Ø­áŒŠá.Â = p_151384_1_;
        EnchantmentHelper.Ø­áŒŠá.HorizonCode_Horizon_È = p_151384_0_;
        if (p_151384_0_ != null) {
            HorizonCode_Horizon_È(EnchantmentHelper.Ø­áŒŠá, p_151384_0_.Ðƒá());
        }
        if (p_151384_1_ instanceof EntityPlayer) {
            HorizonCode_Horizon_È(EnchantmentHelper.Ø­áŒŠá, p_151384_0_.Çª());
        }
    }
    
    public static void Â(final EntityLivingBase p_151385_0_, final Entity p_151385_1_) {
        EnchantmentHelper.Âµá€.HorizonCode_Horizon_È = p_151385_0_;
        EnchantmentHelper.Âµá€.Â = p_151385_1_;
        if (p_151385_0_ != null) {
            HorizonCode_Horizon_È(EnchantmentHelper.Âµá€, p_151385_0_.Ðƒá());
        }
        if (p_151385_0_ instanceof EntityPlayer) {
            HorizonCode_Horizon_È(EnchantmentHelper.Âµá€, p_151385_0_.Çª());
        }
    }
    
    public static int HorizonCode_Horizon_È(final EntityLivingBase p_77501_0_) {
        return HorizonCode_Horizon_È(Enchantment.Å.ŒÏ, p_77501_0_.Çª());
    }
    
    public static int Â(final EntityLivingBase p_90036_0_) {
        return HorizonCode_Horizon_È(Enchantment.£à.ŒÏ, p_90036_0_.Çª());
    }
    
    public static int HorizonCode_Horizon_È(final Entity p_180319_0_) {
        return HorizonCode_Horizon_È(Enchantment.Ø.ŒÏ, p_180319_0_.Ðƒá());
    }
    
    public static int Â(final Entity p_180318_0_) {
        return HorizonCode_Horizon_È(Enchantment.ÂµÈ.ŒÏ, p_180318_0_.Ðƒá());
    }
    
    public static int Ý(final EntityLivingBase p_77509_0_) {
        return HorizonCode_Horizon_È(Enchantment.ˆà.ŒÏ, p_77509_0_.Çª());
    }
    
    public static boolean Ø­áŒŠá(final EntityLivingBase p_77502_0_) {
        return HorizonCode_Horizon_È(Enchantment.¥Æ.ŒÏ, p_77502_0_.Çª()) > 0;
    }
    
    public static int Âµá€(final EntityLivingBase p_77517_0_) {
        return HorizonCode_Horizon_È(Enchantment.µÕ.ŒÏ, p_77517_0_.Çª());
    }
    
    public static int Ó(final EntityLivingBase p_151386_0_) {
        return HorizonCode_Horizon_È(Enchantment.ŠÄ.ŒÏ, p_151386_0_.Çª());
    }
    
    public static int à(final EntityLivingBase p_151387_0_) {
        return HorizonCode_Horizon_È(Enchantment.Ñ¢á.ŒÏ, p_151387_0_.Çª());
    }
    
    public static int Ø(final EntityLivingBase p_77519_0_) {
        return HorizonCode_Horizon_È(Enchantment.µà.ŒÏ, p_77519_0_.Çª());
    }
    
    public static boolean áŒŠÆ(final EntityLivingBase p_77510_0_) {
        return HorizonCode_Horizon_È(Enchantment.áŒŠÆ.ŒÏ, p_77510_0_.Ðƒá()) > 0;
    }
    
    public static ItemStack HorizonCode_Horizon_È(final Enchantment p_92099_0_, final EntityLivingBase p_92099_1_) {
        for (final ItemStack var5 : p_92099_1_.Ðƒá()) {
            if (var5 != null && HorizonCode_Horizon_È(p_92099_0_.ŒÏ, var5) > 0) {
                return var5;
            }
        }
        return null;
    }
    
    public static int HorizonCode_Horizon_È(final Random p_77514_0_, final int p_77514_1_, int p_77514_2_, final ItemStack p_77514_3_) {
        final Item_1028566121 var4 = p_77514_3_.HorizonCode_Horizon_È();
        final int var5 = var4.ˆÏ­();
        if (var5 <= 0) {
            return 0;
        }
        if (p_77514_2_ > 15) {
            p_77514_2_ = 15;
        }
        final int var6 = p_77514_0_.nextInt(8) + 1 + (p_77514_2_ >> 1) + p_77514_0_.nextInt(p_77514_2_ + 1);
        return (p_77514_1_ == 0) ? Math.max(var6 / 3, 1) : ((p_77514_1_ == 1) ? (var6 * 2 / 3 + 1) : Math.max(var6, p_77514_2_ * 2));
    }
    
    public static ItemStack HorizonCode_Horizon_È(final Random p_77504_0_, final ItemStack p_77504_1_, final int p_77504_2_) {
        final List var3 = Â(p_77504_0_, p_77504_1_, p_77504_2_);
        final boolean var4 = p_77504_1_.HorizonCode_Horizon_È() == Items.Ñ¢Ç;
        if (var4) {
            p_77504_1_.HorizonCode_Horizon_È(Items.Çªáˆºá);
        }
        if (var3 != null) {
            for (final EnchantmentData var6 : var3) {
                if (var4) {
                    Items.Çªáˆºá.HorizonCode_Horizon_È(p_77504_1_, var6);
                }
                else {
                    p_77504_1_.HorizonCode_Horizon_È(var6.HorizonCode_Horizon_È, var6.Â);
                }
            }
        }
        return p_77504_1_;
    }
    
    public static List Â(final Random p_77513_0_, final ItemStack p_77513_1_, final int p_77513_2_) {
        final Item_1028566121 var3 = p_77513_1_.HorizonCode_Horizon_È();
        int var4 = var3.ˆÏ­();
        if (var4 <= 0) {
            return null;
        }
        var4 /= 2;
        var4 = 1 + p_77513_0_.nextInt((var4 >> 1) + 1) + p_77513_0_.nextInt((var4 >> 1) + 1);
        final int var5 = var4 + p_77513_2_;
        final float var6 = (p_77513_0_.nextFloat() + p_77513_0_.nextFloat() - 1.0f) * 0.15f;
        int var7 = (int)(var5 * (1.0f + var6) + 0.5f);
        if (var7 < 1) {
            var7 = 1;
        }
        ArrayList var8 = null;
        final Map var9 = Â(var7, p_77513_1_);
        if (var9 != null && !var9.isEmpty()) {
            final EnchantmentData var10 = (EnchantmentData)WeightedRandom.HorizonCode_Horizon_È(p_77513_0_, var9.values());
            if (var10 != null) {
                var8 = Lists.newArrayList();
                var8.add(var10);
                for (int var11 = var7; p_77513_0_.nextInt(50) <= var11; var11 >>= 1) {
                    final Iterator var12 = var9.keySet().iterator();
                    while (var12.hasNext()) {
                        final Integer var13 = var12.next();
                        boolean var14 = true;
                        for (final EnchantmentData var16 : var8) {
                            if (var16.HorizonCode_Horizon_È.HorizonCode_Horizon_È(Enchantment.HorizonCode_Horizon_È(var13))) {
                                continue;
                            }
                            var14 = false;
                            break;
                        }
                        if (!var14) {
                            var12.remove();
                        }
                    }
                    if (!var9.isEmpty()) {
                        final EnchantmentData var17 = (EnchantmentData)WeightedRandom.HorizonCode_Horizon_È(p_77513_0_, var9.values());
                        var8.add(var17);
                    }
                }
            }
        }
        return var8;
    }
    
    public static Map Â(final int p_77505_0_, final ItemStack p_77505_1_) {
        final Item_1028566121 var2 = p_77505_1_.HorizonCode_Horizon_È();
        HashMap var3 = null;
        final boolean var4 = p_77505_1_.HorizonCode_Horizon_È() == Items.Ñ¢Ç;
        for (final Enchantment var8 : Enchantment.Â) {
            if (var8 != null && (var8.Çªà¢.HorizonCode_Horizon_È(var2) || var4)) {
                for (int var9 = var8.Ý(); var9 <= var8.Ø­áŒŠá(); ++var9) {
                    if (p_77505_0_ >= var8.Â(var9) && p_77505_0_ <= var8.Ý(var9)) {
                        if (var3 == null) {
                            var3 = Maps.newHashMap();
                        }
                        var3.put(var8.ŒÏ, new EnchantmentData(var8, var9));
                    }
                }
            }
        }
        return var3;
    }
    
    static final class HorizonCode_Horizon_È implements Ý
    {
        public EntityLivingBase HorizonCode_Horizon_È;
        public Entity Â;
        private static final String Ý = "CL_00000109";
        
        private HorizonCode_Horizon_È() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final Enchantment p_77493_1_, final int p_77493_2_) {
            p_77493_1_.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, p_77493_2_);
        }
        
        HorizonCode_Horizon_È(final Object p_i45359_1_) {
            this();
        }
    }
    
    static final class Â implements Ý
    {
        public EntityLivingBase HorizonCode_Horizon_È;
        public Entity Â;
        private static final String Ý = "CL_00000110";
        
        private Â() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final Enchantment p_77493_1_, final int p_77493_2_) {
            p_77493_1_.Â(this.HorizonCode_Horizon_È, this.Â, p_77493_2_);
        }
        
        Â(final Object p_i45360_1_) {
            this();
        }
    }
    
    static final class Ø­áŒŠá implements Ý
    {
        public int HorizonCode_Horizon_È;
        public DamageSource Â;
        private static final String Ý = "CL_00000114";
        
        private Ø­áŒŠá() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final Enchantment p_77493_1_, final int p_77493_2_) {
            this.HorizonCode_Horizon_È += p_77493_1_.HorizonCode_Horizon_È(p_77493_2_, this.Â);
        }
        
        Ø­áŒŠá(final Object p_i1929_1_) {
            this();
        }
    }
    
    static final class Âµá€ implements Ý
    {
        public float HorizonCode_Horizon_È;
        public EnumCreatureAttribute Â;
        private static final String Ý = "CL_00000112";
        
        private Âµá€() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final Enchantment p_77493_1_, final int p_77493_2_) {
            this.HorizonCode_Horizon_È += p_77493_1_.HorizonCode_Horizon_È(p_77493_2_, this.Â);
        }
        
        Âµá€(final Object p_i1928_1_) {
            this();
        }
    }
    
    interface Ý
    {
        void HorizonCode_Horizon_È(final Enchantment p0, final int p1);
    }
}
