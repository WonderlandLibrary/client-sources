package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.ResponderID;









public class OcspIdentifier
  extends ASN1Object
{
  private ResponderID ocspResponderID;
  private ASN1GeneralizedTime producedAt;
  
  public static OcspIdentifier getInstance(Object obj)
  {
    if ((obj instanceof OcspIdentifier))
    {
      return (OcspIdentifier)obj;
    }
    if (obj != null)
    {
      return new OcspIdentifier(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private OcspIdentifier(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    ocspResponderID = ResponderID.getInstance(seq.getObjectAt(0));
    producedAt = ((ASN1GeneralizedTime)seq.getObjectAt(1));
  }
  
  public OcspIdentifier(ResponderID ocspResponderID, ASN1GeneralizedTime producedAt)
  {
    this.ocspResponderID = ocspResponderID;
    this.producedAt = producedAt;
  }
  
  public ResponderID getOcspResponderID()
  {
    return ocspResponderID;
  }
  
  public ASN1GeneralizedTime getProducedAt()
  {
    return producedAt;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(ocspResponderID);
    v.add(producedAt);
    return new DERSequence(v);
  }
}
