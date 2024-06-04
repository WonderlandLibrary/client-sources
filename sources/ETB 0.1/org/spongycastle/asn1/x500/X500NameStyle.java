package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface X500NameStyle
{
  public abstract ASN1Encodable stringToValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString);
  
  public abstract ASN1ObjectIdentifier attrNameToOID(String paramString);
  
  public abstract RDN[] fromString(String paramString);
  
  public abstract boolean areEqual(X500Name paramX500Name1, X500Name paramX500Name2);
  
  public abstract int calculateHashCode(X500Name paramX500Name);
  
  public abstract String toString(X500Name paramX500Name);
  
  public abstract String oidToDisplayName(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
  
  public abstract String[] oidToAttrNames(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
}
