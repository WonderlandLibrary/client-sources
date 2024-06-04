package org.spongycastle.asn1;

import java.io.IOException;

public abstract interface ASN1TaggedObjectParser
  extends ASN1Encodable, InMemoryRepresentable
{
  public abstract int getTagNo();
  
  public abstract ASN1Encodable getObjectParser(int paramInt, boolean paramBoolean)
    throws IOException;
}
