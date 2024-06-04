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


















public class RecipientKeyIdentifier
  extends ASN1Object
{
  private ASN1OctetString subjectKeyIdentifier;
  private ASN1GeneralizedTime date;
  private OtherKeyAttribute other;
  
  public RecipientKeyIdentifier(ASN1OctetString subjectKeyIdentifier, ASN1GeneralizedTime date, OtherKeyAttribute other)
  {
    this.subjectKeyIdentifier = subjectKeyIdentifier;
    this.date = date;
    this.other = other;
  }
  



  public RecipientKeyIdentifier(byte[] subjectKeyIdentifier, ASN1GeneralizedTime date, OtherKeyAttribute other)
  {
    this.subjectKeyIdentifier = new DEROctetString(subjectKeyIdentifier);
    this.date = date;
    this.other = other;
  }
  

  public RecipientKeyIdentifier(byte[] subjectKeyIdentifier)
  {
    this(subjectKeyIdentifier, null, null);
  }
  

  /**
   * @deprecated
   */
  public RecipientKeyIdentifier(ASN1Sequence seq)
  {
    subjectKeyIdentifier = ASN1OctetString.getInstance(seq
      .getObjectAt(0));
    
    switch (seq.size())
    {
    case 1: 
      break;
    case 2: 
      if ((seq.getObjectAt(1) instanceof ASN1GeneralizedTime))
      {
        date = ASN1GeneralizedTime.getInstance(seq.getObjectAt(1));
      }
      else
      {
        other = OtherKeyAttribute.getInstance(seq.getObjectAt(2));
      }
      break;
    case 3: 
      date = ASN1GeneralizedTime.getInstance(seq.getObjectAt(1));
      other = OtherKeyAttribute.getInstance(seq.getObjectAt(2));
      break;
    default: 
      throw new IllegalArgumentException("Invalid RecipientKeyIdentifier");
    }
    
  }
  








  public static RecipientKeyIdentifier getInstance(ASN1TaggedObject ato, boolean isExplicit)
  {
    return getInstance(ASN1Sequence.getInstance(ato, isExplicit));
  }
  













  public static RecipientKeyIdentifier getInstance(Object obj)
  {
    if ((obj instanceof RecipientKeyIdentifier))
    {
      return (RecipientKeyIdentifier)obj;
    }
    
    if (obj != null)
    {
      return new RecipientKeyIdentifier(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1OctetString getSubjectKeyIdentifier()
  {
    return subjectKeyIdentifier;
  }
  
  public ASN1GeneralizedTime getDate()
  {
    return date;
  }
  
  public OtherKeyAttribute getOtherKeyAttribute()
  {
    return other;
  }
  




  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(subjectKeyIdentifier);
    
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
