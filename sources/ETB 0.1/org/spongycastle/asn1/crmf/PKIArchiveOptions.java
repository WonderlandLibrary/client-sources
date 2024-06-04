package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;


public class PKIArchiveOptions
  extends ASN1Object
  implements ASN1Choice
{
  public static final int encryptedPrivKey = 0;
  public static final int keyGenParameters = 1;
  public static final int archiveRemGenPrivKey = 2;
  private ASN1Encodable value;
  
  public static PKIArchiveOptions getInstance(Object o)
  {
    if ((o == null) || ((o instanceof PKIArchiveOptions)))
    {
      return (PKIArchiveOptions)o;
    }
    if ((o instanceof ASN1TaggedObject))
    {
      return new PKIArchiveOptions((ASN1TaggedObject)o);
    }
    
    throw new IllegalArgumentException("unknown object: " + o);
  }
  
  private PKIArchiveOptions(ASN1TaggedObject tagged)
  {
    switch (tagged.getTagNo())
    {
    case 0: 
      value = EncryptedKey.getInstance(tagged.getObject());
      break;
    case 1: 
      value = ASN1OctetString.getInstance(tagged, false);
      break;
    case 2: 
      value = ASN1Boolean.getInstance(tagged, false);
      break;
    default: 
      throw new IllegalArgumentException("unknown tag number: " + tagged.getTagNo());
    }
  }
  
  public PKIArchiveOptions(EncryptedKey encKey)
  {
    value = encKey;
  }
  
  public PKIArchiveOptions(ASN1OctetString keyGenParameters)
  {
    value = keyGenParameters;
  }
  
  public PKIArchiveOptions(boolean archiveRemGenPrivKey)
  {
    value = ASN1Boolean.getInstance(archiveRemGenPrivKey);
  }
  
  public int getType()
  {
    if ((value instanceof EncryptedKey))
    {
      return 0;
    }
    
    if ((value instanceof ASN1OctetString))
    {
      return 1;
    }
    
    return 2;
  }
  
  public ASN1Encodable getValue()
  {
    return value;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    if ((value instanceof EncryptedKey))
    {
      return new DERTaggedObject(true, 0, value);
    }
    
    if ((value instanceof ASN1OctetString))
    {
      return new DERTaggedObject(false, 1, value);
    }
    
    return new DERTaggedObject(false, 2, value);
  }
}
