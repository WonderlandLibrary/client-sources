package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
















public class KEKIdentifier
  extends ASN1Object
{
  private ASN1OctetString keyIdentifier;
  private ASN1GeneralizedTime date;
  private OtherKeyAttribute other;
  
  public KEKIdentifier(byte[] keyIdentifier, ASN1GeneralizedTime date, OtherKeyAttribute other)
  {
    this.keyIdentifier = new DEROctetString(keyIdentifier);
    this.date = date;
    this.other = other;
  }
  

  private KEKIdentifier(ASN1Sequence seq)
  {
    keyIdentifier = ((ASN1OctetString)seq.getObjectAt(0));
    
    switch (seq.size())
    {
    case 1: 
      break;
    case 2: 
      if ((seq.getObjectAt(1) instanceof ASN1GeneralizedTime))
      {
        date = ((ASN1GeneralizedTime)seq.getObjectAt(1));
      }
      else
      {
        other = OtherKeyAttribute.getInstance(seq.getObjectAt(1));
      }
      break;
    case 3: 
      date = ((ASN1GeneralizedTime)seq.getObjectAt(1));
      other = OtherKeyAttribute.getInstance(seq.getObjectAt(2));
      break;
    default: 
      throw new IllegalArgumentException("Invalid KEKIdentifier");
    }
    
  }
  










  public static KEKIdentifier getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  














  public static KEKIdentifier getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof KEKIdentifier)))
    {
      return (KEKIdentifier)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new KEKIdentifier((ASN1Sequence)obj);
    }
    
    throw new IllegalArgumentException("Invalid KEKIdentifier: " + obj.getClass().getName());
  }
  
  public ASN1OctetString getKeyIdentifier()
  {
    return keyIdentifier;
  }
  
  public ASN1GeneralizedTime getDate()
  {
    return date;
  }
  
  public OtherKeyAttribute getOther()
  {
    return other;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyIdentifier);
    
    if (date != null)
    {
      v.add(date);
    }
    
    if (other != null)
    {
      v.add(other);
    }
    
    return new DERSequence(v);
  }
}
