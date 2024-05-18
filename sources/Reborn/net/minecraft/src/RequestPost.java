package net.minecraft.src;

import java.io.*;

public class RequestPost extends Request
{
    private byte[] field_96373_c;
    
    public RequestPost(final String par1Str, final byte[] par2ArrayOfByte, final int par3, final int par4) {
        super(par1Str, par3, par4);
        this.field_96373_c = par2ArrayOfByte;
    }
    
    public RequestPost func_96372_f() {
        try {
            this.field_96367_a.setDoInput(true);
            this.field_96367_a.setDoOutput(true);
            this.field_96367_a.setUseCaches(false);
            this.field_96367_a.setRequestMethod("POST");
            final OutputStream var1 = this.field_96367_a.getOutputStream();
            var1.write(this.field_96373_c);
            var1.flush();
            return this;
        }
        catch (Exception var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_96365_b, var2);
        }
    }
    
    public Request func_96359_e() {
        return this.func_96372_f();
    }
}
