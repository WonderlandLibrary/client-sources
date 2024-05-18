package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.File;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class ServerList
{
    private static final Logger HorizonCode_Horizon_È;
    private final Minecraft Â;
    private final List Ý;
    private static final String Ø­áŒŠá = "CL_00000891";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public ServerList(final Minecraft mcIn) {
        this.Ý = Lists.newArrayList();
        this.Â = mcIn;
        this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È() {
        try {
            this.Ý.clear();
            final NBTTagCompound var1 = CompressedStreamTools.HorizonCode_Horizon_È(new File(this.Â.ŒÏ, "servers.dat"));
            if (var1 == null) {
                return;
            }
            final NBTTagList var2 = var1.Ý("servers", 10);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                this.Ý.add(ServerData.HorizonCode_Horizon_È(var2.Â(var3)));
            }
        }
        catch (Exception var4) {
            ServerList.HorizonCode_Horizon_È.error("Couldn't load server list", (Throwable)var4);
        }
    }
    
    public void Â() {
        try {
            final NBTTagList var1 = new NBTTagList();
            for (final ServerData var3 : this.Ý) {
                var1.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È());
            }
            final NBTTagCompound var4 = new NBTTagCompound();
            var4.HorizonCode_Horizon_È("servers", var1);
            CompressedStreamTools.HorizonCode_Horizon_È(var4, new File(this.Â.ŒÏ, "servers.dat"));
        }
        catch (Exception var5) {
            ServerList.HorizonCode_Horizon_È.error("Couldn't save server list", (Throwable)var5);
        }
    }
    
    public ServerData HorizonCode_Horizon_È(final int p_78850_1_) {
        return this.Ý.get(p_78850_1_);
    }
    
    public void Â(final int p_78851_1_) {
        this.Ý.remove(p_78851_1_);
    }
    
    public void HorizonCode_Horizon_È(final ServerData p_78849_1_) {
        this.Ý.add(p_78849_1_);
    }
    
    public int Ý() {
        return this.Ý.size();
    }
    
    public void HorizonCode_Horizon_È(final int p_78857_1_, final int p_78857_2_) {
        final ServerData var3 = this.HorizonCode_Horizon_È(p_78857_1_);
        this.Ý.set(p_78857_1_, this.HorizonCode_Horizon_È(p_78857_2_));
        this.Ý.set(p_78857_2_, var3);
        this.Â();
    }
    
    public void HorizonCode_Horizon_È(final int p_147413_1_, final ServerData p_147413_2_) {
        this.Ý.set(p_147413_1_, p_147413_2_);
    }
    
    public static void Â(final ServerData p_147414_0_) {
        final ServerList var1 = new ServerList(Minecraft.áŒŠà());
        var1.HorizonCode_Horizon_È();
        for (int var2 = 0; var2 < var1.Ý(); ++var2) {
            final ServerData var3 = var1.HorizonCode_Horizon_È(var2);
            if (var3.HorizonCode_Horizon_È.equals(p_147414_0_.HorizonCode_Horizon_È) && var3.Â.equals(p_147414_0_.Â)) {
                var1.HorizonCode_Horizon_È(var2, p_147414_0_);
                break;
            }
        }
        var1.Â();
    }
}
