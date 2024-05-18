package org.bouncycastle.asn1;

public abstract class ASN1Object implements ASN1Encodable
{
    @Override
    public int hashCode() {
        return this.toASN1Primitive().hashCode();
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (!(par1Obj instanceof ASN1Encodable)) {
            return false;
        }
        final ASN1Encodable var2 = (ASN1Encodable)par1Obj;
        return this.toASN1Primitive().equals(var2.toASN1Primitive());
    }
    
    @Override
    public abstract ASN1Primitive toASN1Primitive();
}
