package org.spongycastle.asn1.eac;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;

public abstract class PublicKeyDataObject extends org.spongycastle.asn1.ASN1Object
{
  public PublicKeyDataObject() {}
  
  public static PublicKeyDataObject getInstance(Object obj)
  {
    if ((obj instanceof PublicKeyDataObject))
    {
      return (PublicKeyDataObject)obj;
    }
    if (obj != null)
    {
      ASN1Sequence seq = ASN1Sequence.getInstance(obj);
      ASN1ObjectIdentifier usage = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0));
      
      if (usage.on(EACObjectIdentifiers.id_TA_ECDSA))
      {
        return new ECDSAPublicKey(seq);
      }
      

      return new RSAPublicKey(seq);
    }
    

    return null;
  }
  
  public abstract ASN1ObjectIdentifier getUsage();
}
