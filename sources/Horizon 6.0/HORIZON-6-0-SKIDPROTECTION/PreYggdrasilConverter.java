package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.Agent;
import java.util.Iterator;
import com.google.common.collect.Iterators;
import com.google.common.base.Predicate;
import com.mojang.authlib.ProfileLookupCallback;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter
{
    private static final Logger Âµá€;
    public static final File HorizonCode_Horizon_È;
    public static final File Â;
    public static final File Ý;
    public static final File Ø­áŒŠá;
    private static final String Ó = "CL_00001882";
    
    static {
        Âµá€ = LogManager.getLogger();
        HorizonCode_Horizon_È = new File("banned-ips.txt");
        Â = new File("banned-players.txt");
        Ý = new File("ops.txt");
        Ø­áŒŠá = new File("white-list.txt");
    }
    
    private static void HorizonCode_Horizon_È(final MinecraftServer server, final Collection names, final ProfileLookupCallback callback) {
        final String[] var3 = (String[])Iterators.toArray((Iterator)Iterators.filter((Iterator)names.iterator(), (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001881";
            
            public boolean HorizonCode_Horizon_È(final String p_152733_1_) {
                return !StringUtils.Â(p_152733_1_);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((String)p_apply_1_);
            }
        }), (Class)String.class);
        if (server.Ñ¢Â()) {
            server.ÇªÂµÕ().findProfilesByNames(var3, Agent.MINECRAFT, callback);
        }
        else {
            final String[] var4 = var3;
            for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                final String var7 = var4[var6];
                final UUID var8 = EntityPlayer.HorizonCode_Horizon_È(new GameProfile((UUID)null, var7));
                final GameProfile var9 = new GameProfile(var8, var7);
                callback.onProfileLookupSucceeded(var9);
            }
        }
    }
    
    public static String HorizonCode_Horizon_È(final String p_152719_0_) {
        if (StringUtils.Â(p_152719_0_) || p_152719_0_.length() > 16) {
            return p_152719_0_;
        }
        final MinecraftServer var1 = MinecraftServer.áƒ();
        final GameProfile var2 = var1.áŒŠÏ().HorizonCode_Horizon_È(p_152719_0_);
        if (var2 != null && var2.getId() != null) {
            return var2.getId().toString();
        }
        if (!var1.¥à() && var1.Ñ¢Â()) {
            final ArrayList var3 = Lists.newArrayList();
            final ProfileLookupCallback var4 = (ProfileLookupCallback)new ProfileLookupCallback() {
                private static final String HorizonCode_Horizon_È = "CL_00001880";
                
                public void onProfileLookupSucceeded(final GameProfile p_onProfileLookupSucceeded_1_) {
                    var1.áŒŠÏ().HorizonCode_Horizon_È(p_onProfileLookupSucceeded_1_);
                    var3.add(p_onProfileLookupSucceeded_1_);
                }
                
                public void onProfileLookupFailed(final GameProfile p_onProfileLookupFailed_1_, final Exception p_onProfileLookupFailed_2_) {
                    PreYggdrasilConverter.Âµá€.warn("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName(), (Throwable)p_onProfileLookupFailed_2_);
                }
            };
            HorizonCode_Horizon_È(var1, Lists.newArrayList((Object[])new String[] { p_152719_0_ }), var4);
            return (var3.size() > 0 && var3.get(0).getId() != null) ? var3.get(0).getId().toString() : "";
        }
        return EntityPlayer.HorizonCode_Horizon_È(new GameProfile((UUID)null, p_152719_0_)).toString();
    }
}
