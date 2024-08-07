package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;



public class SigPolicyQualifierInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier sigPolicyQualifierId;
  private ASN1Encodable sigQualifier;
  
  public SigPolicyQualifierInfo(ASN1ObjectIdentifier sigPolicyQualifierId, ASN1Encodable sigQualifier)
  {
    this.sigPolicyQualifierId = sigPolicyQualifierId;
    this.sigQualifier = sigQualifier;
  }
  

  private SigPolicyQualifierInfo(ASN1Sequence seq)
  {
    sigPolicyQualifierId = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0));
    sigQualifier = seq.getObjectAt(1);
  }
  

  public static SigPolicyQualifierInfo getInstance(Object obj)
  {
    if ((obj instanceof SigPolicyQualifierInfo))
    {
      return (SigPolicyQualifierInfo)obj;
    }
    if (obj != null)
    {
      return new SigPolicyQualifierInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1ObjectIdentifier getSigPolicyQualifierId()
  {
    return new ASN1ObjectIdentifier(sigPolicyQualifierId.getId());
  }
  
  public ASN1Encodable getSigQualifier()
  {
    return sigQualifier;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(sigPolicyQualifierId);
    v.add(sigQualifier);
    
    return new DERSequence(v);
  }
}
