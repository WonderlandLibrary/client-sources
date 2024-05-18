package org.bouncycastle.asn1;

public abstract class ASN1Primitive extends ASN1Object
{
    @Override
    public final boolean equals(final Object par1Obj) {
        return this == par1Obj || (par1Obj instanceof ASN1Encodable && this.asn1Equals(((ASN1Encodable)par1Obj).toASN1Primitive()));
    }
    
    @Override
    public ASN1Primitive toASN1Primitive() {
        return this;
    }
    
    @Override
    public abstract int hashCode();
    
    abstract boolean asn1Equals(final ASN1Primitive p0);
}
