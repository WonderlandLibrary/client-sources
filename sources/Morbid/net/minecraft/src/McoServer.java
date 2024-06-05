package net.minecraft.src;

import java.net.*;
import java.io.*;
import java.util.*;
import argo.jdom.*;
import argo.saj.*;

public class McoServer extends ValueObject
{
    public long field_96408_a;
    public String field_96406_b;
    public String field_96407_c;
    public String field_96404_d;
    public String field_96405_e;
    public List field_96402_f;
    public String field_96403_g;
    public boolean field_98166_h;
    public int field_104063_i;
    public int field_96415_h;
    public String field_96413_j;
    public String field_96414_k;
    public boolean field_96411_l;
    public boolean field_102022_m;
    public long field_96412_m;
    private String field_96409_n;
    private String field_96410_o;
    
    public McoServer() {
        this.field_96413_j = "";
        this.field_96414_k = "";
        this.field_102022_m = false;
        this.field_96409_n = null;
        this.field_96410_o = null;
    }
    
    public String func_96397_a() {
        if (this.field_96409_n == null) {
            try {
                this.field_96409_n = URLDecoder.decode(this.field_96407_c, "UTF-8");
            }
            catch (UnsupportedEncodingException var2) {
                this.field_96409_n = this.field_96407_c;
            }
        }
        return this.field_96409_n;
    }
    
    public String func_96398_b() {
        if (this.field_96410_o == null) {
            try {
                this.field_96410_o = URLDecoder.decode(this.field_96406_b, "UTF-8");
            }
            catch (UnsupportedEncodingException var2) {
                this.field_96410_o = this.field_96406_b;
            }
        }
        return this.field_96410_o;
    }
    
    public void func_96399_a(final String par1Str) {
        this.field_96406_b = par1Str;
        this.field_96410_o = null;
    }
    
    public void func_96400_b(final String par1Str) {
        this.field_96407_c = par1Str;
        this.field_96409_n = null;
    }
    
    public void func_96401_a(final McoServer par1McoServer) {
        this.field_96414_k = par1McoServer.field_96414_k;
        this.field_96413_j = par1McoServer.field_96413_j;
        this.field_96412_m = par1McoServer.field_96412_m;
        this.field_96411_l = par1McoServer.field_96411_l;
        this.field_96415_h = par1McoServer.field_96415_h;
        this.field_102022_m = true;
    }
    
    public static McoServer func_98163_a(final JsonNode par0JsonNode) {
        final McoServer var1 = new McoServer();
        try {
            var1.field_96408_a = Long.parseLong(par0JsonNode.getNumberValue("id"));
            var1.field_96406_b = par0JsonNode.getStringValue("name");
            var1.field_96407_c = par0JsonNode.getStringValue("motd");
            var1.field_96404_d = par0JsonNode.getStringValue("state");
            var1.field_96405_e = par0JsonNode.getStringValue("owner");
            if (par0JsonNode.isArrayNode("invited")) {
                var1.field_96402_f = func_98164_a(par0JsonNode.getArrayNode("invited"));
            }
            else {
                var1.field_96402_f = new ArrayList();
            }
            var1.field_104063_i = Integer.parseInt(par0JsonNode.getNumberValue("daysLeft"));
            var1.field_96403_g = par0JsonNode.getStringValue("ip");
            var1.field_98166_h = par0JsonNode.getBooleanValue("expired");
        }
        catch (IllegalArgumentException ex) {}
        return var1;
    }
    
    private static List func_98164_a(final List par0List) {
        final ArrayList var1 = new ArrayList();
        for (final JsonNode var3 : par0List) {
            var1.add(var3.getStringValue(new Object[0]));
        }
        return var1;
    }
    
    public static McoServer func_98165_c(final String par0Str) {
        McoServer var1 = new McoServer();
        try {
            var1 = func_98163_a(new JdomParser().parse(par0Str));
        }
        catch (InvalidSyntaxException ex) {}
        return var1;
    }
}
