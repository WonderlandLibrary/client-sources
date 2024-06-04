package org.spongycastle.asn1;

import java.io.IOException;

public abstract interface InMemoryRepresentable
{
  public abstract ASN1Primitive getLoadedObject()
    throws IOException;
}
