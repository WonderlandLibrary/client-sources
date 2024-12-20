package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.EnvelopedData;

public class EncryptedKey
  extends ASN1Object
  implements ASN1Choice
{
  private EnvelopedData envelopedData;
  private EncryptedValue encryptedValue;
  
  public static EncryptedKey getInstance(Object o)
  {
    if ((o instanceof EncryptedKey))
    {
      return (EncryptedKey)o;
    }
    if ((o instanceof ASN1TaggedObject))
    {
      return new EncryptedKey(EnvelopedData.getInstance((ASN1TaggedObject)o, false));
    }
    if ((o instanceof EncryptedValue))
    {
      return new EncryptedKey((EncryptedValue)o);
    }
    

    return new EncryptedKey(EncryptedValue.getInstance(o));
  }
  

  public EncryptedKey(EnvelopedData envelopedData)
  {
    this.envelopedData = envelopedData;
  }
  
  public EncryptedKey(EncryptedValue encryptedValue)
  {
    this.encryptedValue = encryptedValue;
  }
  
  public boolean isEncryptedValue()
  {
    return encryptedValue != null;
  }
  
  public ASN1Encodable getValue()
  {
    if (encryptedValue != null)
    {
      return encryptedValue;
    }
    
    return envelopedData;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    if (encryptedValue != null)
    {
      return encryptedValue.toASN1Primitive();
    }
    
    return new DERTaggedObject(false, 0, envelopedData);
  }
}
