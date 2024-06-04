package org.spongycastle.asn1;

import java.io.InputStream;

public abstract interface ASN1OctetStringParser
  extends ASN1Encodable, InMemoryRepresentable
{
  public abstract InputStream getOctetStream();
}
