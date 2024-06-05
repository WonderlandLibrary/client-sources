package net.minecraft.src;

import java.util.*;
import java.io.*;
import java.net.*;

public class McoClient
{
    private static McoOption field_98178_a;
    private final String field_96390_a;
    private final String field_100007_c;
    private static String field_96388_b;
    
    static {
        McoClient.field_98178_a = McoOption.func_98154_b();
        McoClient.field_96388_b = "https://mcoapi.minecraft.net/";
    }
    
    public McoClient(final Session par1Session) {
        this.field_96390_a = par1Session.sessionId;
        this.field_100007_c = par1Session.username;
    }
    
    public ValueObjectList func_96382_a() throws ExceptionMcoService {
        final String var1 = this.func_96377_a(Request.func_96358_a(String.valueOf(McoClient.field_96388_b) + "worlds"));
        return ValueObjectList.func_98161_a(var1);
    }
    
    public McoServer func_98176_a(final long par1) throws ExceptionMcoService, IOException {
        final String var3 = this.func_96377_a(Request.func_96358_a(String.valueOf(McoClient.field_96388_b) + "worlds" + "/$ID".replace("$ID", String.valueOf(par1))));
        return McoServer.func_98165_c(var3);
    }
    
    public McoServerAddress func_96374_a(final long par1) throws ExceptionMcoService, IOException {
        final String var3 = String.valueOf(McoClient.field_96388_b) + "worlds" + "/$ID/join".replace("$ID", new StringBuilder().append(par1).toString());
        final String var4 = this.func_96377_a(Request.func_96358_a(var3));
        return McoServerAddress.func_98162_a(var4);
    }
    
    public void func_96386_a(final String par1Str, final String par2Str, final String par3Str, final String par4Str) throws ExceptionMcoService, UnsupportedEncodingException {
        final StringBuilder var5 = new StringBuilder();
        var5.append(McoClient.field_96388_b).append("worlds").append("/$NAME/$LOCATION_ID".replace("$NAME", this.func_96380_a(par1Str)).replace("$LOCATION_ID", par3Str));
        final HashMap var6 = new HashMap();
        if (par2Str != null && !par2Str.trim().equals("")) {
            var6.put("motd", par2Str);
        }
        if (par4Str != null && !par4Str.equals("")) {
            var6.put("seed", par4Str);
        }
        if (!var6.isEmpty()) {
            boolean var7 = true;
            for (final Map.Entry var9 : var6.entrySet()) {
                if (var7) {
                    var5.append("?");
                    var7 = false;
                }
                else {
                    var5.append("&");
                }
                var5.append(var9.getKey()).append("=").append(this.func_96380_a(var9.getValue()));
            }
        }
        this.func_96377_a(Request.func_104064_a(var5.toString(), "", 5000, 30000));
    }
    
    public Boolean func_96375_b() throws ExceptionMcoService, IOException {
        final String var1 = String.valueOf(McoClient.field_96388_b) + "mco" + "/available";
        final String var2 = this.func_96377_a(Request.func_96358_a(var1));
        return Boolean.valueOf(var2);
    }
    
    public int func_96379_c() throws ExceptionMcoService {
        final String var1 = String.valueOf(McoClient.field_96388_b) + "payments" + "/unused";
        final String var2 = this.func_96377_a(Request.func_96358_a(var1));
        return Integer.valueOf(var2);
    }
    
    public void func_96381_a(final long par1, final String par3Str) throws ExceptionMcoService {
        final String var4 = String.valueOf(McoClient.field_96388_b) + "worlds" + "/$WORLD_ID/invites/$USER_NAME".replace("$WORLD_ID", String.valueOf(par1)).replace("$USER_NAME", par3Str);
        this.func_96377_a(Request.func_96355_b(var4));
    }
    
    public McoServer func_96387_b(final long par1, final String par3Str) throws ExceptionMcoService, IOException {
        final String var4 = String.valueOf(McoClient.field_96388_b) + "worlds" + "/$WORLD_ID/invites/$USER_NAME".replace("$WORLD_ID", String.valueOf(par1)).replace("$USER_NAME", par3Str);
        final String var5 = this.func_96377_a(Request.func_96361_b(var4, ""));
        return McoServer.func_98165_c(var5);
    }
    
    public void func_96384_a(final long par1, final String par3Str, final String par4Str, final int par5, final int par6) throws ExceptionMcoService, UnsupportedEncodingException {
        final StringBuilder var7 = new StringBuilder();
        var7.append(McoClient.field_96388_b).append("worlds").append("/$WORLD_ID/$NAME".replace("$WORLD_ID", String.valueOf(par1)).replace("$NAME", this.func_96380_a(par3Str)));
        if (par4Str != null && !par4Str.trim().equals("")) {
            var7.append("?motd=").append(this.func_96380_a(par4Str));
        }
        var7.append("&difficulty=").append(par5).append("&gameMode=").append(par6);
        this.func_96377_a(Request.func_96363_c(var7.toString(), ""));
    }
    
    public Boolean func_96383_b(final long par1) throws ExceptionMcoService, IOException {
        final String var3 = String.valueOf(McoClient.field_96388_b) + "worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(par1));
        final String var4 = this.func_96377_a(Request.func_96363_c(var3, ""));
        return Boolean.valueOf(var4);
    }
    
    public Boolean func_96378_c(final long par1) throws ExceptionMcoService, IOException {
        final String var3 = String.valueOf(McoClient.field_96388_b) + "worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(par1));
        final String var4 = this.func_96377_a(Request.func_96363_c(var3, ""));
        return Boolean.valueOf(var4);
    }
    
    public Boolean func_96376_d(final long par1, final String par3Str) throws ExceptionMcoService, UnsupportedEncodingException {
        final StringBuilder var4 = new StringBuilder();
        var4.append(McoClient.field_96388_b).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(par1)));
        if (par3Str != null && par3Str.length() > 0) {
            var4.append("?seed=").append(this.func_96380_a(par3Str));
        }
        final String var5 = this.func_96377_a(Request.func_96353_a(var4.toString(), "", 30000, 80000));
        return Boolean.valueOf(var5);
    }
    
    public ValueObjectSubscription func_98177_f(final long par1) throws ExceptionMcoService, IOException {
        final String var3 = this.func_96377_a(Request.func_96358_a(String.valueOf(McoClient.field_96388_b) + "subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(par1))));
        return ValueObjectSubscription.func_98169_a(var3);
    }
    
    private String func_96380_a(final String par1Str) throws UnsupportedEncodingException {
        return URLEncoder.encode(par1Str, "UTF-8");
    }
    
    private String func_96377_a(final Request par1Request) throws ExceptionMcoService {
        par1Request.func_100006_a("sid", this.field_96390_a);
        par1Request.func_100006_a("user", this.field_100007_c);
        if (McoClient.field_98178_a instanceof McoOptionSome) {
            final McoPair var2 = (McoPair)McoClient.field_98178_a.func_98155_a();
            par1Request.func_100006_a((String)var2.func_100005_a(), (String)var2.func_100004_b());
        }
        try {
            final int var3 = par1Request.func_96362_a();
            if (var3 == 503) {
                throw new ExceptionRetryCall(10);
            }
            if (var3 >= 200 && var3 < 300) {
                final McoOption var4 = par1Request.func_98175_b();
                if (var4 instanceof McoOptionSome) {
                    McoClient.field_98178_a = var4;
                }
                return par1Request.func_96364_c();
            }
            throw new ExceptionMcoService(par1Request.func_96362_a(), par1Request.func_96364_c());
        }
        catch (ExceptionMcoHttp var5) {
            throw new ExceptionMcoService(500, "Server not available!");
        }
    }
}
