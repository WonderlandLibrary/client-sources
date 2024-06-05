package org.bouncycastle.asn1;

public class DERObjectIdentifier extends ASN1Primitive
{
    String identifier;
    private static ASN1ObjectIdentifier[][] cache;
    
    static {
        DERObjectIdentifier.cache = new ASN1ObjectIdentifier[255][];
    }
    
    public DERObjectIdentifier(final String par1Str) {
        if (!isValidIdentifier(par1Str)) {
            throw new IllegalArgumentException("string " + par1Str + " not an OID");
        }
        this.identifier = par1Str;
    }
    
    public String getId() {
        return this.identifier;
    }
    
    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }
    
    @Override
    boolean asn1Equals(final ASN1Primitive par1ASN1Primitive) {
        return par1ASN1Primitive instanceof DERObjectIdentifier && this.identifier.equals(((DERObjectIdentifier)par1ASN1Primitive).identifier);
    }
    
    @Override
    public String toString() {
        return this.getId();
    }
    
    private static boolean isValidIdentifier(final String par0Str) {
        if (par0Str.length() < 3 || par0Str.charAt(1) != '.') {
            return false;
        }
        final char var1 = par0Str.charAt(0);
        if (var1 >= '0' && var1 <= '2') {
            boolean var2 = false;
            for (int var3 = par0Str.length() - 1; var3 >= 2; --var3) {
                final char var4 = par0Str.charAt(var3);
                if ('0' <= var4 && var4 <= '9') {
                    var2 = true;
                }
                else {
                    if (var4 != '.') {
                        return false;
                    }
                    if (!var2) {
                        return false;
                    }
                    var2 = false;
                }
            }
            return var2;
        }
        return false;
    }
}
