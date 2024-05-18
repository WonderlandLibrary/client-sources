package org.bouncycastle.asn1;

public class ASN1ObjectIdentifier extends DERObjectIdentifier
{
    public ASN1ObjectIdentifier(final String par1Str) {
        super(par1Str);
    }
    
    public ASN1ObjectIdentifier branch(final String par1Str) {
        return new ASN1ObjectIdentifier(String.valueOf(this.getId()) + "." + par1Str);
    }
}
