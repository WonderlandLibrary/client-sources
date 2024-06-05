package net.minecraft.src;

import java.net.*;
import java.io.*;

public abstract class Request
{
    protected HttpURLConnection field_96367_a;
    private boolean field_96366_c;
    protected String field_96365_b;
    
    public Request(final String par1Str, final int par2, final int par3) {
        try {
            this.field_96365_b = par1Str;
            (this.field_96367_a = (HttpURLConnection)new URL(par1Str).openConnection()).setConnectTimeout(par2);
            this.field_96367_a.setReadTimeout(par3);
        }
        catch (Exception var5) {
            throw new ExceptionMcoHttp("Failed URL: " + par1Str, var5);
        }
    }
    
    public void func_100006_a(final String par1Str, final String par2Str) {
        final String var3 = this.field_96367_a.getRequestProperty("Cookie");
        if (var3 == null) {
            this.field_96367_a.setRequestProperty("Cookie", String.valueOf(par1Str) + "=" + par2Str);
        }
        else {
            this.field_96367_a.setRequestProperty("Cookie", String.valueOf(var3) + ";" + par1Str + "=" + par2Str);
        }
    }
    
    public int func_96362_a() {
        try {
            this.func_96354_d();
            return this.field_96367_a.getResponseCode();
        }
        catch (Exception var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_96365_b, var2);
        }
    }
    
    public McoOption func_98175_b() {
        final String var1 = this.field_96367_a.getHeaderField("Set-Cookie");
        if (var1 != null) {
            final String var2 = var1.substring(0, var1.indexOf("="));
            final String var3 = var1.substring(var1.indexOf("=") + 1, var1.indexOf(";"));
            return McoOption.func_98153_a(McoPair.func_98157_a(var2, var3));
        }
        return McoOption.func_98154_b();
    }
    
    public String func_96364_c() {
        try {
            this.func_96354_d();
            final String var1 = (this.func_96362_a() >= 400) ? this.func_96352_a(this.field_96367_a.getErrorStream()) : this.func_96352_a(this.field_96367_a.getInputStream());
            this.func_96360_f();
            return var1;
        }
        catch (IOException var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_96365_b, var2);
        }
    }
    
    private String func_96352_a(final InputStream par1InputStream) throws IOException {
        if (par1InputStream == null) {
            throw new IllegalArgumentException("input stream cannot be null");
        }
        final StringBuilder var2 = new StringBuilder();
        for (int var3 = par1InputStream.read(); var3 != -1; var3 = par1InputStream.read()) {
            var2.append((char)var3);
        }
        return var2.toString();
    }
    
    private void func_96360_f() {
        final byte[] var1 = new byte[1024];
        try {
            final boolean var2 = false;
            final InputStream var3 = this.field_96367_a.getInputStream();
            while (var3.read(var1) > 0) {}
            var3.close();
        }
        catch (Exception var5) {
            try {
                final InputStream var3 = this.field_96367_a.getErrorStream();
                final boolean var4 = false;
                while (var3.read(var1) > 0) {}
                var3.close();
            }
            catch (IOException ex) {}
        }
    }
    
    protected Request func_96354_d() {
        if (!this.field_96366_c) {
            final Request var1 = this.func_96359_e();
            this.field_96366_c = true;
            return var1;
        }
        return this;
    }
    
    protected abstract Request func_96359_e();
    
    public static Request func_96358_a(final String par0Str) {
        return new RequestGet(par0Str, 5000, 10000);
    }
    
    public static Request func_96361_b(final String par0Str, final String par1Str) {
        return new RequestPost(par0Str, par1Str.getBytes(), 5000, 10000);
    }
    
    public static Request func_104064_a(final String par0Str, final String par1Str, final int par2, final int par3) {
        return new RequestPost(par0Str, par1Str.getBytes(), par2, par3);
    }
    
    public static Request func_96355_b(final String par0Str) {
        return new RequestDelete(par0Str, 5000, 10000);
    }
    
    public static Request func_96363_c(final String par0Str, final String par1Str) {
        return new RequestPut(par0Str, par1Str.getBytes(), 5000, 10000);
    }
    
    public static Request func_96353_a(final String par0Str, final String par1Str, final int par2, final int par3) {
        return new RequestPut(par0Str, par1Str.getBytes(), par2, par3);
    }
}
