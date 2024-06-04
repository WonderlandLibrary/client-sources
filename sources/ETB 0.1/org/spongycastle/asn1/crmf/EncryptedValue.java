package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue
  extends ASN1Object
{
  private AlgorithmIdentifier intendedAlg;
  private AlgorithmIdentifier symmAlg;
  private DERBitString encSymmKey;
  private AlgorithmIdentifier keyAlg;
  private ASN1OctetString valueHint;
  private DERBitString encValue;
  
  private EncryptedValue(ASN1Sequence seq)
  {
    int index = 0;
    while ((seq.getObjectAt(index) instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject tObj = (ASN1TaggedObject)seq.getObjectAt(index);
      
      switch (tObj.getTagNo())
      {
      case 0: 
        intendedAlg = AlgorithmIdentifier.getInstance(tObj, false);
        break;
      case 1: 
        symmAlg = AlgorithmIdentifier.getInstance(tObj, false);
        break;
      case 2: 
        encSymmKey = DERBitString.getInstance(tObj, false);
        break;
      case 3: 
        keyAlg = AlgorithmIdentifier.getInstance(tObj, false);
        break;
      case 4: 
        valueHint = ASN1OctetString.getInstance(tObj, false);
        break;
      default: 
        throw new IllegalArgumentException("Unknown tag encountered: " + tObj.getTagNo());
      }
      index++;
    }
    
    encValue = DERBitString.getInstance(seq.getObjectAt(index));
  }
  
  public static EncryptedValue getInstance(Object o)
  {
    if ((o instanceof EncryptedValue))
    {
      return (EncryptedValue)o;
    }
    if (o != null)
    {
      return new EncryptedValue(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  






  public EncryptedValue(AlgorithmIdentifier intendedAlg, AlgorithmIdentifier symmAlg, DERBitString encSymmKey, AlgorithmIdentifier keyAlg, ASN1OctetString valueHint, DERBitString encValue)
  {
    if (encValue == null)
    {
      throw new IllegalArgumentException("'encValue' cannot be null");
    }
    
    this.intendedAlg = intendedAlg;
    this.symmAlg = symmAlg;
    this.encSymmKey = encSymmKey;
    this.keyAlg = keyAlg;
    this.valueHint = valueHint;
    this.encValue = encValue;
  }
  
  public AlgorithmIdentifier getIntendedAlg()
  {
    return intendedAlg;
  }
  
  public AlgorithmIdentifier getSymmAlg()
  {
    return symmAlg;
  }
  
  public DERBitString getEncSymmKey()
  {
    return encSymmKey;
  }
  
  public AlgorithmIdentifier getKeyAlg()
  {
    return keyAlg;
  }
  
  public ASN1OctetString getValueHint()
  {
    return valueHint;
  }
  
  public DERBitString getEncValue()
  {
    return encValue;
  }
  





















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    addOptional(v, 0, intendedAlg);
    addOptional(v, 1, symmAlg);
    addOptional(v, 2, encSymmKey);
    addOptional(v, 3, keyAlg);
    addOptional(v, 4, valueHint);
    
    v.add(encValue);
    
    return new DERSequence(v);
  }
  
  private void addOptional(ASN1EncodableVector v, int tagNo, ASN1Encodable obj)
  {
    if (obj != null)
    {
      v.add(new DERTaggedObject(false, tagNo, obj));
    }
  }
}
