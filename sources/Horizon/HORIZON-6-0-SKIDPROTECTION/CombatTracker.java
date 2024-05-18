package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class CombatTracker
{
    private final List HorizonCode_Horizon_È;
    private final EntityLivingBase Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private boolean Ó;
    private boolean à;
    private String Ø;
    private static final String áŒŠÆ = "CL_00001520";
    
    public CombatTracker(final EntityLivingBase p_i1565_1_) {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.Â = p_i1565_1_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ø();
        if (this.Â.i_()) {
            final Block var1 = this.Â.Ï­Ðƒà.Â(new BlockPos(this.Â.ŒÏ, this.Â.£É().Â, this.Â.Ê)).Ý();
            if (var1 == Blocks.áŒŠÏ) {
                this.Ø = "ladder";
            }
            else if (var1 == Blocks.ÇŽà) {
                this.Ø = "vines";
            }
        }
        else if (this.Â.£ÂµÄ()) {
            this.Ø = "water";
        }
    }
    
    public void HorizonCode_Horizon_È(final DamageSource p_94547_1_, final float p_94547_2_, final float p_94547_3_) {
        this.Âµá€();
        this.HorizonCode_Horizon_È();
        final CombatEntry var4 = new CombatEntry(p_94547_1_, this.Â.Œ, p_94547_2_, p_94547_3_, this.Ø, this.Â.Ï­à);
        this.HorizonCode_Horizon_È.add(var4);
        this.Ý = this.Â.Œ;
        this.à = true;
        if (var4.Ý() && !this.Ó && this.Â.Œ()) {
            this.Ó = true;
            this.Ø­áŒŠá = this.Â.Œ;
            this.Âµá€ = this.Ø­áŒŠá;
            this.Â.ˆÕ();
        }
    }
    
    public IChatComponent Â() {
        if (this.HorizonCode_Horizon_È.size() == 0) {
            return new ChatComponentTranslation("death.attack.generic", new Object[] { this.Â.Ý() });
        }
        final CombatEntry var1 = this.à();
        final CombatEntry var2 = this.HorizonCode_Horizon_È.get(this.HorizonCode_Horizon_È.size() - 1);
        final IChatComponent var3 = var2.Âµá€();
        final Entity var4 = var2.HorizonCode_Horizon_È().áˆºÑ¢Õ();
        Object var8;
        if (var1 != null && var2.HorizonCode_Horizon_È() == DamageSource.áŒŠÆ) {
            final IChatComponent var5 = var1.Âµá€();
            if (var1.HorizonCode_Horizon_È() != DamageSource.áŒŠÆ && var1.HorizonCode_Horizon_È() != DamageSource.áˆºÑ¢Õ) {
                if (var5 != null && (var3 == null || !var5.equals(var3))) {
                    final Entity var6 = var1.HorizonCode_Horizon_È().áˆºÑ¢Õ();
                    final ItemStack var7 = (var6 instanceof EntityLivingBase) ? ((EntityLivingBase)var6).Çª() : null;
                    if (var7 != null && var7.¥Æ()) {
                        var8 = new ChatComponentTranslation("death.fell.assist.item", new Object[] { this.Â.Ý(), var5, var7.Çªà¢() });
                    }
                    else {
                        var8 = new ChatComponentTranslation("death.fell.assist", new Object[] { this.Â.Ý(), var5 });
                    }
                }
                else if (var3 != null) {
                    final ItemStack var9 = (var4 instanceof EntityLivingBase) ? ((EntityLivingBase)var4).Çª() : null;
                    if (var9 != null && var9.¥Æ()) {
                        var8 = new ChatComponentTranslation("death.fell.finish.item", new Object[] { this.Â.Ý(), var3, var9.Çªà¢() });
                    }
                    else {
                        var8 = new ChatComponentTranslation("death.fell.finish", new Object[] { this.Â.Ý(), var3 });
                    }
                }
                else {
                    var8 = new ChatComponentTranslation("death.fell.killer", new Object[] { this.Â.Ý() });
                }
            }
            else {
                var8 = new ChatComponentTranslation("death.fell.accident." + this.HorizonCode_Horizon_È(var1), new Object[] { this.Â.Ý() });
            }
        }
        else {
            var8 = var2.HorizonCode_Horizon_È().Â(this.Â);
        }
        return (IChatComponent)var8;
    }
    
    public EntityLivingBase Ý() {
        EntityLivingBase var1 = null;
        EntityPlayer var2 = null;
        float var3 = 0.0f;
        float var4 = 0.0f;
        for (final CombatEntry var6 : this.HorizonCode_Horizon_È) {
            if (var6.HorizonCode_Horizon_È().áˆºÑ¢Õ() instanceof EntityPlayer && (var2 == null || var6.Â() > var4)) {
                var4 = var6.Â();
                var2 = (EntityPlayer)var6.HorizonCode_Horizon_È().áˆºÑ¢Õ();
            }
            if (var6.HorizonCode_Horizon_È().áˆºÑ¢Õ() instanceof EntityLivingBase && (var1 == null || var6.Â() > var3)) {
                var3 = var6.Â();
                var1 = (EntityLivingBase)var6.HorizonCode_Horizon_È().áˆºÑ¢Õ();
            }
        }
        if (var2 != null && var4 >= var3 / 3.0f) {
            return var2;
        }
        return var1;
    }
    
    private CombatEntry à() {
        CombatEntry var1 = null;
        CombatEntry var2 = null;
        final byte var3 = 0;
        float var4 = 0.0f;
        for (int var5 = 0; var5 < this.HorizonCode_Horizon_È.size(); ++var5) {
            final CombatEntry var6 = this.HorizonCode_Horizon_È.get(var5);
            final CombatEntry var7 = (var5 > 0) ? this.HorizonCode_Horizon_È.get(var5 - 1) : null;
            if ((var6.HorizonCode_Horizon_È() == DamageSource.áŒŠÆ || var6.HorizonCode_Horizon_È() == DamageSource.áˆºÑ¢Õ) && var6.Ó() > 0.0f && (var1 == null || var6.Ó() > var4)) {
                if (var5 > 0) {
                    var1 = var7;
                }
                else {
                    var1 = var6;
                }
                var4 = var6.Ó();
            }
            if (var6.Ø­áŒŠá() != null && (var2 == null || var6.Â() > var3)) {
                var2 = var6;
            }
        }
        if (var4 > 5.0f && var1 != null) {
            return var1;
        }
        if (var3 > 5 && var2 != null) {
            return var2;
        }
        return null;
    }
    
    private String HorizonCode_Horizon_È(final CombatEntry p_94548_1_) {
        return (p_94548_1_.Ø­áŒŠá() == null) ? "generic" : p_94548_1_.Ø­áŒŠá();
    }
    
    public int Ø­áŒŠá() {
        return this.Ó ? (this.Â.Œ - this.Ø­áŒŠá) : (this.Âµá€ - this.Ø­áŒŠá);
    }
    
    private void Ø() {
        this.Ø = null;
    }
    
    public void Âµá€() {
        final int var1 = this.Ó ? 300 : 100;
        if (this.à && (!this.Â.Œ() || this.Â.Œ - this.Ý > var1)) {
            final boolean var2 = this.Ó;
            this.à = false;
            this.Ó = false;
            this.Âµá€ = this.Â.Œ;
            if (var2) {
                this.Â.ÇªÈ();
            }
            this.HorizonCode_Horizon_È.clear();
        }
    }
    
    public EntityLivingBase Ó() {
        return this.Â;
    }
}
