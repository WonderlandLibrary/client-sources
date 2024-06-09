package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import com.mojang.authlib.GameProfile;

public final class NBTUtil
{
    private static final String HorizonCode_Horizon_È = "CL_00001901";
    
    public static GameProfile HorizonCode_Horizon_È(final NBTTagCompound compound) {
        String var1 = null;
        String var2 = null;
        if (compound.Â("Name", 8)) {
            var1 = compound.áˆºÑ¢Õ("Name");
        }
        if (compound.Â("Id", 8)) {
            var2 = compound.áˆºÑ¢Õ("Id");
        }
        if (StringUtils.Â(var1) && StringUtils.Â(var2)) {
            return null;
        }
        UUID var3;
        try {
            var3 = UUID.fromString(var2);
        }
        catch (Throwable var12) {
            var3 = null;
        }
        final GameProfile var4 = new GameProfile(var3, var1);
        if (compound.Â("Properties", 10)) {
            final NBTTagCompound var5 = compound.ˆÏ­("Properties");
            for (final String var7 : var5.Âµá€()) {
                final NBTTagList var8 = var5.Ý(var7, 10);
                for (int var9 = 0; var9 < var8.Âµá€(); ++var9) {
                    final NBTTagCompound var10 = var8.Â(var9);
                    final String var11 = var10.áˆºÑ¢Õ("Value");
                    if (var10.Â("Signature", 8)) {
                        var4.getProperties().put((Object)var7, (Object)new Property(var7, var11, var10.áˆºÑ¢Õ("Signature")));
                    }
                    else {
                        var4.getProperties().put((Object)var7, (Object)new Property(var7, var11));
                    }
                }
            }
        }
        return var4;
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(final NBTTagCompound p_180708_0_, final GameProfile p_180708_1_) {
        if (!StringUtils.Â(p_180708_1_.getName())) {
            p_180708_0_.HorizonCode_Horizon_È("Name", p_180708_1_.getName());
        }
        if (p_180708_1_.getId() != null) {
            p_180708_0_.HorizonCode_Horizon_È("Id", p_180708_1_.getId().toString());
        }
        if (!p_180708_1_.getProperties().isEmpty()) {
            final NBTTagCompound var2 = new NBTTagCompound();
            for (final String var4 : p_180708_1_.getProperties().keySet()) {
                final NBTTagList var5 = new NBTTagList();
                for (final Property var7 : p_180708_1_.getProperties().get((Object)var4)) {
                    final NBTTagCompound var8 = new NBTTagCompound();
                    var8.HorizonCode_Horizon_È("Value", var7.getValue());
                    if (var7.hasSignature()) {
                        var8.HorizonCode_Horizon_È("Signature", var7.getSignature());
                    }
                    var5.HorizonCode_Horizon_È(var8);
                }
                var2.HorizonCode_Horizon_È(var4, var5);
            }
            p_180708_0_.HorizonCode_Horizon_È("Properties", var2);
        }
        return p_180708_0_;
    }
}
