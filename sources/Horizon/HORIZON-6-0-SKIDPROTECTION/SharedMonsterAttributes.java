package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Collection;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes
{
    private static final Logger Ó;
    public static final IAttribute HorizonCode_Horizon_È;
    public static final IAttribute Â;
    public static final IAttribute Ý;
    public static final IAttribute Ø­áŒŠá;
    public static final IAttribute Âµá€;
    private static final String à = "CL_00001695";
    
    static {
        Ó = LogManager.getLogger();
        HorizonCode_Horizon_È = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, Double.MAX_VALUE).HorizonCode_Horizon_È("Max Health").HorizonCode_Horizon_È(true);
        Â = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).HorizonCode_Horizon_È("Follow Range");
        Ý = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0).HorizonCode_Horizon_È("Knockback Resistance");
        Ø­áŒŠá = new RangedAttribute(null, "generic.movementSpeed", 0.699999988079071, 0.0, Double.MAX_VALUE).HorizonCode_Horizon_È("Movement Speed").HorizonCode_Horizon_È(true);
        Âµá€ = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, Double.MAX_VALUE);
    }
    
    public static NBTTagList HorizonCode_Horizon_È(final BaseAttributeMap p_111257_0_) {
        final NBTTagList var1 = new NBTTagList();
        for (final IAttributeInstance var3 : p_111257_0_.HorizonCode_Horizon_È()) {
            var1.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var3));
        }
        return var1;
    }
    
    private static NBTTagCompound HorizonCode_Horizon_È(final IAttributeInstance p_111261_0_) {
        final NBTTagCompound var1 = new NBTTagCompound();
        final IAttribute var2 = p_111261_0_.HorizonCode_Horizon_È();
        var1.HorizonCode_Horizon_È("Name", var2.HorizonCode_Horizon_È());
        var1.HorizonCode_Horizon_È("Base", p_111261_0_.Â());
        final Collection var3 = p_111261_0_.Ý();
        if (var3 != null && !var3.isEmpty()) {
            final NBTTagList var4 = new NBTTagList();
            for (final AttributeModifier var6 : var3) {
                if (var6.Âµá€()) {
                    var4.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var6));
                }
            }
            var1.HorizonCode_Horizon_È("Modifiers", var4);
        }
        return var1;
    }
    
    private static NBTTagCompound HorizonCode_Horizon_È(final AttributeModifier p_111262_0_) {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.HorizonCode_Horizon_È("Name", p_111262_0_.Â());
        var1.HorizonCode_Horizon_È("Amount", p_111262_0_.Ø­áŒŠá());
        var1.HorizonCode_Horizon_È("Operation", p_111262_0_.Ý());
        var1.HorizonCode_Horizon_È("UUIDMost", p_111262_0_.HorizonCode_Horizon_È().getMostSignificantBits());
        var1.HorizonCode_Horizon_È("UUIDLeast", p_111262_0_.HorizonCode_Horizon_È().getLeastSignificantBits());
        return var1;
    }
    
    public static void HorizonCode_Horizon_È(final BaseAttributeMap p_151475_0_, final NBTTagList p_151475_1_) {
        for (int var2 = 0; var2 < p_151475_1_.Âµá€(); ++var2) {
            final NBTTagCompound var3 = p_151475_1_.Â(var2);
            final IAttributeInstance var4 = p_151475_0_.HorizonCode_Horizon_È(var3.áˆºÑ¢Õ("Name"));
            if (var4 != null) {
                HorizonCode_Horizon_È(var4, var3);
            }
            else {
                SharedMonsterAttributes.Ó.warn("Ignoring unknown attribute '" + var3.áˆºÑ¢Õ("Name") + "'");
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final IAttributeInstance p_111258_0_, final NBTTagCompound p_111258_1_) {
        p_111258_0_.HorizonCode_Horizon_È(p_111258_1_.áŒŠÆ("Base"));
        if (p_111258_1_.Â("Modifiers", 9)) {
            final NBTTagList var2 = p_111258_1_.Ý("Modifiers", 10);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final AttributeModifier var4 = HorizonCode_Horizon_È(var2.Â(var3));
                if (var4 != null) {
                    final AttributeModifier var5 = p_111258_0_.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È());
                    if (var5 != null) {
                        p_111258_0_.Ý(var5);
                    }
                    p_111258_0_.Â(var4);
                }
            }
        }
    }
    
    public static AttributeModifier HorizonCode_Horizon_È(final NBTTagCompound p_111259_0_) {
        final UUID var1 = new UUID(p_111259_0_.à("UUIDMost"), p_111259_0_.à("UUIDLeast"));
        try {
            return new AttributeModifier(var1, p_111259_0_.áˆºÑ¢Õ("Name"), p_111259_0_.áŒŠÆ("Amount"), p_111259_0_.Ó("Operation"));
        }
        catch (Exception var2) {
            SharedMonsterAttributes.Ó.warn("Unable to create attribute: " + var2.getMessage());
            return null;
        }
    }
}
