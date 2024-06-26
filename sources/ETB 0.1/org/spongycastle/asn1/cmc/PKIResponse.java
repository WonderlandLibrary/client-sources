package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;















public class PKIResponse
  extends ASN1Object
{
  private final ASN1Sequence controlSequence;
  private final ASN1Sequence cmsSequence;
  private final ASN1Sequence otherMsgSequence;
  
  private PKIResponse(ASN1Sequence seq)
  {
    if (seq.size() != 3)
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    controlSequence = ASN1Sequence.getInstance(seq.getObjectAt(0));
    cmsSequence = ASN1Sequence.getInstance(seq.getObjectAt(1));
    otherMsgSequence = ASN1Sequence.getInstance(seq.getObjectAt(2));
  }
  

  public static PKIResponse getInstance(Object o)
  {
    if ((o instanceof PKIResponse))
    {
      return (PKIResponse)o;
    }
    
    if (o != null)
    {
      return new PKIResponse(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  


  public static PKIResponse getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(controlSequence);
    v.add(cmsSequence);
    v.add(otherMsgSequence);
    
    return new DERSequence(v);
  }
  
  public ASN1Sequence getControlSequence()
  {
    return controlSequence;
  }
  
  public ASN1Sequence getCmsSequence()
  {
    return cmsSequence;
  }
  
  public ASN1Sequence getOtherMsgSequence()
  {
    return otherMsgSequence;
  }
}
