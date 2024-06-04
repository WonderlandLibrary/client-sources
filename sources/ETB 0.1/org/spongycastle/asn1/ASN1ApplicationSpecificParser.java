package org.spongycastle.asn1;

import java.io.IOException;

public abstract interface ASN1ApplicationSpecificParser
  extends ASN1Encodable, InMemoryRepresentable
{
  public abstract ASN1Encodable readObject()
    throws IOException;
}
