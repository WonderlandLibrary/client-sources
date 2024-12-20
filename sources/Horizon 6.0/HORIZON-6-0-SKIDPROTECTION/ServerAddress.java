package HORIZON-6-0-SKIDPROTECTION;

import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.net.IDN;

public class ServerAddress
{
    private final String HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00000889";
    
    private ServerAddress(final String p_i1192_1_, final int p_i1192_2_) {
        this.HorizonCode_Horizon_È = p_i1192_1_;
        this.Â = p_i1192_2_;
    }
    
    public String HorizonCode_Horizon_È() {
        return IDN.toASCII(this.HorizonCode_Horizon_È);
    }
    
    public int Â() {
        return this.Â;
    }
    
    public static ServerAddress HorizonCode_Horizon_È(final String p_78860_0_) {
        if (p_78860_0_ == null) {
            return null;
        }
        String[] var1 = p_78860_0_.split(":");
        if (p_78860_0_.startsWith("[")) {
            final int var2 = p_78860_0_.indexOf("]");
            if (var2 > 0) {
                final String var3 = p_78860_0_.substring(1, var2);
                String var4 = p_78860_0_.substring(var2 + 1).trim();
                if (var4.startsWith(":") && var4.length() > 0) {
                    var4 = var4.substring(1);
                    var1 = new String[] { var3, var4 };
                }
                else {
                    var1 = new String[] { var3 };
                }
            }
        }
        if (var1.length > 2) {
            var1 = new String[] { p_78860_0_ };
        }
        String var5 = var1[0];
        int var6 = (var1.length > 1) ? HorizonCode_Horizon_È(var1[1], 25565) : 25565;
        if (var6 == 25565) {
            final String[] var7 = Â(var5);
            var5 = var7[0];
            var6 = HorizonCode_Horizon_È(var7[1], 25565);
        }
        return new ServerAddress(var5, var6);
    }
    
    private static String[] Â(final String p_78863_0_) {
        try {
            final String var1 = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            final Hashtable var2 = new Hashtable();
            var2.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            var2.put("java.naming.provider.url", "dns:");
            var2.put("com.sun.jndi.dns.timeout.retries", "1");
            final InitialDirContext var3 = new InitialDirContext(var2);
            final Attributes var4 = var3.getAttributes("_minecraft._tcp." + p_78863_0_, new String[] { "SRV" });
            final String[] var5 = var4.get("srv").get().toString().split(" ", 4);
            return new String[] { var5[3], var5[2] };
        }
        catch (Throwable var6) {
            return new String[] { p_78863_0_, Integer.toString(25565) };
        }
    }
    
    private static int HorizonCode_Horizon_È(final String p_78862_0_, final int p_78862_1_) {
        try {
            return Integer.parseInt(p_78862_0_.trim());
        }
        catch (Exception var3) {
            return p_78862_1_;
        }
    }
}
