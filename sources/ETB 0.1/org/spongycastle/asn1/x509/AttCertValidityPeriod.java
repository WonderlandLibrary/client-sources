package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;


public class AttCertValidityPeriod
  extends ASN1Object
{
  ASN1GeneralizedTime notBeforeTime;
  ASN1GeneralizedTime notAfterTime;
  
  public static AttCertValidityPeriod getInstance(Object obj)
  {
    if ((obj instanceof AttCertValidityPeriod))
    {
      return (AttCertValidityPeriod)obj;
    }
    if (obj != null)
    {
      return new AttCertValidityPeriod(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private AttCertValidityPeriod(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    notBeforeTime = ASN1GeneralizedTime.getInstance(seq.getObjectAt(0));
    notAfterTime = ASN1GeneralizedTime.getInstance(seq.getObjectAt(1));
  }
  






  public AttCertValidityPeriod(ASN1GeneralizedTime notBeforeTime, ASN1GeneralizedTime notAfterTime)
  {
    this.notBeforeTime = notBeforeTime;
    this.notAfterTime = notAfterTime;
  }
  
  public ASN1GeneralizedTime getNotBeforeTime()
  {
    return notBeforeTime;
  }
  
  public ASN1GeneralizedTime getNotAfterTime()
  {
    return notAfterTime;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(notBeforeTime);
    v.add(notAfterTime);
    
    return new DERSequence(v);
  }
}
