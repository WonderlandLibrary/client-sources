package org.spongycastle.jce.interfaces;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface PKCS12BagAttributeCarrier
{
  public abstract void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable);
  
  public abstract ASN1Encodable getBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
  
  public abstract Enumeration getBagAttributeKeys();
}
