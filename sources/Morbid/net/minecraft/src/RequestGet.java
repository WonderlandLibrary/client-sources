package net.minecraft.src;

public class RequestGet extends Request
{
    public RequestGet(final String par1Str, final int par2, final int par3) {
        super(par1Str, par2, par3);
    }
    
    public RequestGet func_96371_f() {
        try {
            this.field_96367_a.setDoInput(true);
            this.field_96367_a.setDoOutput(true);
            this.field_96367_a.setUseCaches(false);
            this.field_96367_a.setRequestMethod("GET");
            return this;
        }
        catch (Exception var2) {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_96365_b, var2);
        }
    }
    
    public Request func_96359_e() {
        return this.func_96371_f();
    }
}
